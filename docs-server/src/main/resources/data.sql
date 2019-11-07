insert into documents (name, description, file_name, content, content_type, created_date)
values ('file', 'description', 'test.bin', CAST(X'FFFF' AS BLOB), 'application/octet-stream', CURRENT_TIMESTAMP);
