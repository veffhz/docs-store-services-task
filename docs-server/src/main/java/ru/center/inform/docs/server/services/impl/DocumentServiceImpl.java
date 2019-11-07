package ru.center.inform.docs.server.services.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import ru.center.inform.docs.server.domain.Document;
import ru.center.inform.docs.server.exception.DocumentNotFoundException;
import ru.center.inform.docs.server.repository.DocumentRepository;
import ru.center.inform.docs.server.services.DocumentService;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository repository;

    @Override
    public Document save(Document document) {
        return repository.save(document);
    }

    @Override
    public Document getLazy(Long id) {
        return repository.findByIdLazy(id)
                .orElseThrow(DocumentNotFoundException::new);
    }

    @Override
    public Document get(Long id) {
        return repository.findById(id)
                .orElseThrow(DocumentNotFoundException::new);
    }

    @Override
    public List<Long> getAllIds() {
        return repository.getAllIds();
    }

}
