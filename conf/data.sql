CREATE TABLE "Users" (
  id        CHAR(16) PRIMARY KEY,
  password  CHAR(16) NOT NULL,
  name      CHAR(16) NOT NULL
);

CREATE TABLE "Boards" (
  index    INTEGER PRIMARY KEY,
  title    CHAR(100) NOT NULL,
  context  CHAR(2000) NOT NULL,
  writer   CHAR(16) NOT NULL
);

INSERT INTO "Users" VALUES('admin', '1234', 'admin');

INSERT INTO "Boards" VALUES(1, 'title', 'context', 'admin');
INSERT INTO "Boards" VALUES(2, 'test title', 'context', 'admin');
INSERT INTO "Boards" VALUES(3, 'test title two', 'context', 'admin');
INSERT INTO "Boards" VALUES(4, 'this is test title', 'context', 'admin');

DROP TABLE "Boards";
DROP TABLE "Users";