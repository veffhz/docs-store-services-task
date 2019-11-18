package ru.center.inform.docs.client.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.IOUtils;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import ru.center.inform.docs.client.domain.DocumentDto;
import ru.center.inform.docs.client.feign.DocumentClient;
import ru.center.inform.docs.client.service.CryptService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final DocumentClient documentClient;
    private final CryptService cryptService;

    @GetMapping("/")
    public String home() {
        log.info("get /");
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String index(Model model) {
        log.info("get /index");
        List<Long> documentIds = documentClient.getDocumentIds();
        model.addAttribute("documentIds", documentIds);
        return "index";
    }

    @GetMapping("/details/{id}")
    public String getDocumentDto(@PathVariable("id") Long id, Model model) {
        log.info("Get document by {}", id);
        DocumentDto documentDto = documentClient.getDocument(id);
        model.addAttribute("documentDto", documentDto);
        return "document/details";
    }

    @PostMapping("/delete/{id}")
    public RedirectView delete(@PathVariable("id") Long id) {
        documentClient.deleteDocument(id);
        RedirectView rv = new RedirectView();
        rv.setContextRelative(true);
        rv.setUrl("/index");
        return rv;
    }

    @GetMapping("/download/{id}")
    @ResponseBody
    public ResponseEntity<String> download(@PathVariable("id") Long id, HttpServletResponse response) {
        log.info("download document by {}", id);

        DocumentDto documentDto = documentClient.getDocumentFull(id);
        try {
            String contentHeader = String.format("attachment;filename=\"%s\"",
                    documentDto.getFileName());
            response.setHeader("Content-Disposition", contentHeader);
            OutputStream out = response.getOutputStream();
            response.setContentType(documentDto.getContentType());

            byte[] decryptData = cryptService.decryptData(documentDto.getContent());
            byte[] originalData = cryptService.getOriginalData(decryptData);

            response.setContentLength(originalData.length);

            IOUtils.write(originalData, out);

            out.flush();
            out.close();

        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("Error download file!", e);
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/new")
    public String newFile(Model model) {
        log.info("get /new");
        DocumentDto documentDto = new DocumentDto();
        model.addAttribute("documentDto", documentDto);
        return "document/new";
    }

    @PostMapping("/document")
    public RedirectView sendDocumentDto(@Valid @ModelAttribute("documentDto") DocumentDto documentDto,
                                        @RequestParam("file") MultipartFile file) {
        log.info("post documentDto: {}, file {}", documentDto, file);

        try {
            documentDto.setFileName(file.getOriginalFilename());
            documentDto.setContentType(file.getContentType());

            byte[] signedData = cryptService.signData(file.getBytes());
            byte[] encryptData = cryptService.encryptData(signedData);

            documentDto.setContent(encryptData);

            documentDto.setCreatedDate(LocalDateTime.now());

            DocumentDto newDocumentDto = documentClient.sendDocument(documentDto);
            log.info("saved documentDto: id {}", newDocumentDto.getId());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("Error upload file!", e);
        }

        RedirectView rv = new RedirectView();
        rv.setContextRelative(true);
        rv.setUrl("/index");
        return rv;
    }
}
