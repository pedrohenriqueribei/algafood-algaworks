CREATE TABLE tb_pedido (
	id BIGINT not null AUTO_INCREMENT,
    subtotal DECIMAL(10,2) NOT NULL,
    taxaFrete DECIMAL(10,2) NOT NULL,
    valorTotal DECIMAL(10,2) NOT NULL,
    data_criacao DATETIME NOT NULL,
    data_confirmacao DATETIME,
    data_cancelamento DATETIME,
    data_entrega DATETIME,
    
    status VARCHAR(10) NOT NULL,
    
    endereco_cidade_id bigint(20) not null,
    endereco_cep varchar(9) not null,
    endereco_logradouro varchar(100) not null,
    endereco_numero varchar(20) not null,
    endereco_complemento varchar(60) null,
    endereco_bairro varchar(60) not null,
    
    usuario_id BIGINT NOT NULL,
    restaurante_id BIGINT NOT NULL,
    forma_pagamento_id BIGINT NOT NULL,
    
    PRIMARY KEY (id),
    KEY fk_pedido_endereco_cidade (endereco_cidade_id), 
    KEY fk_pedido_usuario (usuario_id),
    KEY fk_pedido_restaurante (restaurante_id),
    KEY fk_pedido_forma_pagamento (forma_pagamento_id),
    
    CONSTRAINT fk_pedido_endereco_cidade FOREIGN KEY (endereco_cidade_id) REFERENCES tb_cidade (id),
    CONSTRAINT fk_pedido_usuario FOREIGN KEY (usuario_id) REFERENCES tb_usuario (id),
    CONSTRAINT fk_pedido_restaurante FOREIGN KEY (restaurante_id) REFERENCES tb_restaurante (id),
    CONSTRAINT fk_pedido_forma_pagamento FOREIGN KEY(forma_pagamento_id) REFERENCES tb_forma_pagamento(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE tb_item_pedido (
	id BIGINT NOT NULL AUTO_INCREMENT,
    quantidade SMALLINT(6) NOT NULL,
    preco_unitario DECIMAL(10,2) NOT NULL,
    preco_total DECIMAL(10,2) NOT NULL,
    observacao VARCHAR(60) NOT NULL,
    pedido_id BIGINT NOT NULL,
    produto_id BIGINT NOT NULL,
    
    PRIMARY KEY (id),
    UNIQUE KEY uk_item_pedido_produto (pedido_id, produto_id),
    
    CONSTRAINT fk_item_pedido_pedido FOREIGN KEY (pedido_id) REFERENCES tb_pedido(id),
    CONSTRAINT fk_item_pedido_produto FOREIGN KEY (produto_id) REFERENCES tb_produto (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


