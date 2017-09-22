# --- !Ups

-- 이름: 'Users'
-- 설명: 사용자 정보를 관리하는 테이블.
CREATE TABLE "Users" (
  "id"        CHAR(16) PRIMARY KEY,
  "password"  CHAR(16) NOT NULL,
  "name"      CHAR(16) NOT NULL
);

-- 이름: 'Boards'
-- 설명: 게시글 정보를 관리하는 테이블.
CREATE TABLE "Boards" (
  "index"    INTEGER PRIMARY KEY,
  "title"    CHAR(100) NOT NULL,
  "context"  CHAR(2000) NOT NULL,
  "writer"   CHAR(16) NOT NULL
);

INSERT INTO "Users" VALUES('admin', '1234', 'admin');

INSERT INTO "Boards" VALUES(1, 'title', 'context', 'admin');

# --- !Downs

DROP TABLE "Boards";
DROP TABLE "Users";
