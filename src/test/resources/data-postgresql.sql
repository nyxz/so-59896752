CREATE EXTENSION "uuid-ossp";

CREATE TABLE book (
  id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  title varchar NOT NULL
);
