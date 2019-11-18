package ru.center.inform.docs.client.domain;

import lombok.Data;
import lombok.ToString;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
@ToString(exclude = "content")
public class DocumentDto {

    private Long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
    private String fileName;
    private String contentType;
    @NotEmpty
    private byte[] content;
    @DateTimeFormat(pattern="dd-MM-yyyy HH:mm:ss")
    private LocalDateTime createdDate;

}
