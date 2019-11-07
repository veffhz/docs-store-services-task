package ru.center.inform.docs.client.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import ru.center.inform.docs.client.domain.DocumentDto;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@FeignClient(value = "docs-server", url = "http://localhost:8081")
public interface DocumentClient {

    @RequestMapping(method = GET, value = "/api/document", consumes = "application/json")
    List<Long> getDocumentIds();

    @RequestMapping(method = GET, value = "/api/document/{id}", consumes = "application/json")
    DocumentDto getDocument(@PathVariable Long id);

    @RequestMapping(method = GET, value = "/api/document/{id}/content", consumes = "application/json")
    DocumentDto getDocumentFull(@PathVariable Long id);

    @RequestMapping(method = POST, value = "/api/document", consumes = "application/json")
    DocumentDto sendDocument(@RequestBody DocumentDto documentDto);
}
