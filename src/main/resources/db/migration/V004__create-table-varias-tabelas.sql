create table tb_forma_pagamento (
	id bigint not null auto_increment, 
	descricao varchar(60), 
	primary key (id)
) engine=InnoDB DEFAULT CHARSET=utf8;

create table tb_grupo (
   id bigint not null auto_increment,
    nome varchar(60) not null,
    primary key (id)
) engine=InnoDB DEFAULT CHARSET=utf8;

create table tb_grupo_permissao (
   grupo_id bigint not null,
    permissao_id bigint not null,
    primary key (grupo_id, permissao_id)
) engine=InnoDB DEFAULT CHARSET=utf8;

create table tb_permissao (
   id bigint not null auto_increment,
    descricao varchar(80),
    nome varchar(100),
    primary key (id)
) engine=InnoDB DEFAULT CHARSET=utf8;

create table tb_produtos (
   id bigint not null auto_increment,
    ativo bit not null,
    produto_descricao varchar(255) not null,
    produto_nome varchar(255) not null,
    produto_preco decimal(19,2) not null,
    restaurante_id bigint not null,
    primary key (id)
) engine=InnoDB DEFAULT CHARSET=utf8;


create table tb_restaurantes (
   id bigint not null auto_increment,
    data_atualizacao datetime not null,
    data_cadastro datetime not null,
    endereco_bairro varchar(60),
    endereco_cep varchar(9),
    endereco_complemento varchar(255),
    endereco_logradouro varchar(255),
    endereco_numero varchar(20),
    nome varchar(80),
    taxa_frete decimal(19,2),
    cozinha_id bigint not null,
    endereco_cidade_id bigint,
    primary key (id)
) engine=InnoDB DEFAULT CHARSET=utf8;

create table tb_restaurante_forma_pagamento (
   restaurante_id bigint not null,
    forma_pagamento_id bigint not null,
    
    primary key (restaurante_id, forma_pagamento_id)
) engine=InnoDB DEFAULT CHARSET=utf8;

create table tb_usuarios (
   id bigint not null auto_increment,
    data_cadastro datetime not null,
    email varchar(255) not null,
    nome varchar(255) not null,
    senha varchar(255) not null,
    primary key (id)
) engine=InnoDB DEFAULT CHARSET=utf8;

create table tb_usuario_grupos (
   usuario_id bigint not null,
    grupo_id bigint not null,
    
    primary key (usuario_id, grupo_id)
) engine=InnoDB DEFAULT CHARSET=utf8;


alter table tb_grupo_permissao 
   add constraint fk_grupo_permissao_permissao 
   foreign key (permissao_id) 
   references tb_permissao (id);

alter table tb_grupo_permissao 
   add constraint fk_grupo_permissao_grupo 
   foreign key (grupo_id) 
   references tb_grupo (id);

alter table tb_produtos 
   add constraint fk_produto_restaurante 
   foreign key (restaurante_id) 
   references tb_restaurantes (id);

alter table tb_restaurante_forma_pagamento 
   add constraint fk_rest_forma_pagto_forma_pagto 
   foreign key (forma_pagamento_id) 
   references tb_forma_pagamento (id);

alter table tb_restaurante_forma_pagamento 
   add constraint fk_rest_forma_pagto_restaurante 
   foreign key (restaurante_id) 
   references tb_restaurantes (id);

alter table tb_restaurantes 
   add constraint fk_restaurante_cozinha 
   foreign key (cozinha_id) 
   references tb_cozinha (id);

alter table tb_restaurantes 
   add constraint fk_restaurante_cidade 
   foreign key (endereco_cidade_id) 
   references tb_cidade (id);

alter table tb_usuario_grupos 
   add constraint fk_usuario_grupo_grupo 
   foreign key (grupo_id) 
   references tb_grupo (id);

alter table tb_usuario_grupos 
   add constraint fk_usuario_grupo_usuario 
   foreign key (usuario_id) 
   references tb_usuarios (id);

