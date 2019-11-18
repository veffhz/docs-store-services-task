package ru.center.inform.docs.server.services;

import ru.center.inform.docs.server.domain.Document;

import java.util.List;

public interface DocumentService {
    Document save(Document document);
    Document get(Long id);
    Document getLazy(Long id);
    List<Long> getAllIds();
    void delete(Long id);
}
