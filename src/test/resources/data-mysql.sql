CREATE TABLE book (
  id BINARY(16) PRIMARY KEY DEFAULT (UUID_TO_BIN(UUID())),
  title varchar(255) NOT NULL
);