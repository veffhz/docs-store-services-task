package ru.center.inform.docs.server.domain;

public class Views {

    public interface Document {}
    public interface DocumentContent {}

    public interface DocumentFull extends Document, DocumentContent {}

}
