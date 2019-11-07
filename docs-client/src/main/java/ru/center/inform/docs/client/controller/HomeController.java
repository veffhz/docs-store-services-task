package ru.center.inform.docs.client.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.IOUtils;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import ru.center.inform.docs.client.domain.DocumentDto;
import ru.center.inform.docs.client.feign.DocumentClient;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final DocumentClient documentClient;

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

    @GetMapping("/download/{id}")
    @ResponseBody
    public String download(@PathVariable("id") Long id, HttpServletResponse response) {
        log.info("download document by {}", id);

        DocumentDto documentDto = documentClient.getDocumentFull(id);
        try {
            String contentHeader = String.format("attachment;filename=\"%s\"",
                    documentDto.getFileName());
            response.setHeader("Content-Disposition", contentHeader);
            OutputStream out = response.getOutputStream();
            response.setContentType(documentDto.getContentType());
            response.setContentLength(documentDto.getContent().length);
            IOUtils.write(documentDto.getContent(), out);
            out.flush();
            out.close();

        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @GetMapping("/new")
    public String newFile(Model model) {
        log.info("get /new");
        DocumentDto documentDto = new DocumentDto();
        model.addAttribute("documentDto", documentDto);
        return "document/new";
    }

    @PostMapping("/document")
    public RedirectView sendDocumentDto(@ModelAttribute("documentDto") DocumentDto documentDto,
                                        @RequestParam("file") MultipartFile file) {
        log.info("post documentDto: {}, file {}", documentDto, file);

        try {
            documentDto.setFileName(file.getName());
            documentDto.setContentType(file.getContentType());
            documentDto.setContent(file.getBytes());
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