package ru.center.inform.docs.client.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import ru.center.inform.docs.client.config.FeignConfiguration;
import ru.center.inform.docs.client.domain.DocumentDto;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@FeignClient(value = "docs-server", url = DocumentClient.SERVER_URL, configuration = FeignConfiguration.class)
public interface DocumentClient {

    String SERVER_URL = "https://localhost:8081";

    @RequestMapping(method = GET, value = "/api/document", consumes = "application/json")
    List<Long> getDocumentIds();

    @RequestMapping(method = GET, value = "/api/document/{id}", consumes = "application/json")
    DocumentDto getDocument(@PathVariable Long id);

    @RequestMapping(method = GET, value = "/api/document/{id}/content", consumes = "application/json")
    DocumentDto getDocumentFull(@PathVariable Long id);

    @RequestMapping(method = POST, value = "/api/document", consumes = "application/json")
    DocumentDto sendDocument(@RequestBody DocumentDto documentDto);

    @Secured("ROLE_ADMIN")
    @RequestMapping(method = DELETE, value = "/api/document/{id}", consumes = "application/json")
    ResponseEntity<String> deleteDocument(@PathVariable Long id);
}
