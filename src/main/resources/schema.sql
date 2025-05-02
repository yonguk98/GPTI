drop table question;
CREATE TABLE question (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    question_type INTEGER not null,
    question_contents TEXT not null
);