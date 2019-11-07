package ru.center.inform.docs.server.domain;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "content")
@Entity
@Table(name = "documents")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonView(Views.Document.class)
    private Long id;

    @Column(name = "name")
    @JsonView(Views.Document.class)
    private String name;

    @Column(name = "description")
    @JsonView(Views.Document.class)
    private String description;

    @Column(name = "file_name")
    @JsonView(Views.Document.class)
    private String fileName;

    @Column(name = "content_type")
    @JsonView(Views.Document.class)
    private String contentType;

    @Column(name="content")
    @JsonView(Views.DocumentContent.class)
    private byte[] content;

    @Column(name = "created_date")
    @JsonView(Views.Document.class)
    private LocalDateTime createdDate;

}
