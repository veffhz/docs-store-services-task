package ru.center.inform.docs.server.config;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import ru.center.inform.docs.server.domain.Document;
import ru.center.inform.docs.server.repository.DocumentRepository;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

@Slf4j
@Configuration
public class AppConfig {

    @Autowired
    private DocumentRepository documentRepository;

    @PostConstruct
    public void init() {
        Document document = documentRepository.save(new Document(null, "file2", "description",
                "test2.txt", "text/plain", "follow the white rabbit!".getBytes(), LocalDateTime.now()));

        log.info(document.toString());
    }

}
