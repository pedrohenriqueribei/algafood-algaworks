CREATE TABLE tb_cidade(
	id bigint not null AUTO_INCREMENT,
    nome VARCHAR(60) NOT NULL,
    estado_id bigint not null,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE tb_estado (
	id bigint not null AUTO_INCREMENT,
	nome VARCHAR(60) NOT NULL,
	PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE tb_cidade ADD CONSTRAINT fk_cidade_estado FOREIGN KEY (estado_id) REFERENCES tb_estado (id);