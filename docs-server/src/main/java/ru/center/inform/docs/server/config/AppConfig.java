package ru.center.inform.docs.server.config;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import ru.center.inform.docs.server.domain.Document;
import ru.center.inform.docs.server.repository.DocumentRepository;
import ru.center.inform.docs.server.services.CryptService;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Configuration
public class AppConfig {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private CryptService cryptService;

    @PostConstruct
    public void insertDbTestData() {
        documents().forEach(document -> {
            Document saved = documentRepository.save(document);
            log.info(String.valueOf(saved));
        });
    }

    private List<Document> documents() {
        return Arrays.asList(
                new Document(
                        null,
                        "file",
                        "Description",
                        "test.csv",
                        "text/csv",
                        cryptService.encryptData("#1,#2,#3\naaa,bbb,ccc".getBytes()),
                        LocalDateTime.now()
                ),
                new Document(
                        null,
                        "file2",
                        "Description",
                        "test2.txt",
                        "text/plain",
                        cryptService.encryptData("follow the white rabbit!".getBytes()),
                        LocalDateTime.now()
                )
        );
    }

}
