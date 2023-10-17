CREATE TABLE tb_cidade(
	id bigint not null AUTO_INCREMENT,
    nome VARCHAR(60) NOT NULL,
    nome_estado VARCHAR(80),
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;