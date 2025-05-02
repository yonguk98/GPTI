CREATE TABLE if not exists question (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    questionType INTEGER not null,
    questionContents TEXT not null
);