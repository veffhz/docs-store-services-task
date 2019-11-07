package ru.center.inform.docs.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;

import ru.center.inform.docs.server.domain.Document;

import java.util.List;
import java.util.Optional;

public interface DocumentRepository extends JpaRepository<Document, Long> {

    @Query(value = "select doc.id, doc.name, doc.description, doc.file_name, " +
            "doc.content_type, doc.created_date, CAST(X'' AS BLOB)" +
            " as content from documents doc where doc.id = ?1", nativeQuery = true)
    Optional<Document> findByIdLazy(Long id);

    @Query("select id from Document")
    List<Long> getAllIds();
}
