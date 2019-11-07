package ru.center.inform.docs.client.domain;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Blob;
import java.time.LocalDateTime;

@Data
@ToString(exclude = "content")
public class DocumentDto {

    private Long id;
    private String name;
    private String description;
    private String fileName;
    private String contentType;
    private byte[] content;
    @DateTimeFormat(pattern="dd-MM-yyyy HH:mm:ss")
    private LocalDateTime createdDate;

}
