
    create table tb_cidade (
       id bigint not null auto_increment,
        nome varchar(255),
        estado_id bigint not null,
        primary key (id)
    ) engine=InnoDB;

    create table tb_cozinha (
       id bigint not null auto_increment,
        nome varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table tb_estado (
       id bigint not null auto_increment,
        nome varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table tb_forma_pagamento (
       id bigint not null auto_increment,
        descricao varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table tb_grupo (
       id bigint not null auto_increment,
        nome varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;

    create table tb_grupo_permissao (
       grupo_id bigint not null,
        permissao_id bigint not null
    ) engine=InnoDB;

    create table tb_permissao (
       id bigint not null auto_increment,
        descricao varchar(255),
        nome varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table tb_produtos (
       id bigint not null auto_increment,
        ativo bit not null,
        produto_descricao varchar(255) not null,
        produto_nome varchar(255) not null,
        produto_preco decimal(19,2) not null,
        restaurante_id bigint not null,
        primary key (id)
    ) engine=InnoDB;

    create table tb_restaurante_forma_pagamento (
       restaurante_id bigint not null,
        forma_pagamento_id bigint not null
    ) engine=InnoDB;

    create table tb_restaurantes (
       id bigint not null auto_increment,
        data_atualizacao datetime not null,
        data_cadastro datetime not null,
        endereco_bairro varchar(255),
        endereco_cep varchar(255),
        endereco_complemento varchar(255),
        endereco_logradouro varchar(255),
        endereco_numero varchar(255),
        nome varchar(255),
        taxa_frete decimal(19,2),
        cozinha_id bigint not null,
        endereco_cidade_id bigint,
        primary key (id)
    ) engine=InnoDB;

    create table tb_usuario_grupos (
       usuario_id bigint not null,
        grupo_id bigint not null
    ) engine=InnoDB;

    create table tb_usuarios (
       id bigint not null auto_increment,
        data_cadastro datetime not null,
        email varchar(255) not null,
        nome varchar(255) not null,
        senha varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;

    alter table tb_cidade 
       add constraint FKlxge3ne91xrep1oe4cvrjldmm 
       foreign key (estado_id) 
       references tb_estado (id);

    alter table tb_grupo_permissao 
       add constraint FKebkjqmqseopi0eipijfo06vh8 
       foreign key (permissao_id) 
       references tb_permissao (id);

    alter table tb_grupo_permissao 
       add constraint FKc35tefcxk6t0b4u5qaenlv63e 
       foreign key (grupo_id) 
       references tb_grupo (id);

    alter table tb_produtos 
       add constraint FK3a3t359ltbws3h157hbtlelf2 
       foreign key (restaurante_id) 
       references tb_restaurantes (id);

    alter table tb_restaurante_forma_pagamento 
       add constraint FKsi78wf2v77p8yv9swve1qd4bx 
       foreign key (forma_pagamento_id) 
       references tb_forma_pagamento (id);

    alter table tb_restaurante_forma_pagamento 
       add constraint FKpj94ribbow6ftpe5d3jw06cng 
       foreign key (restaurante_id) 
       references tb_restaurantes (id);

    alter table tb_restaurantes 
       add constraint FKeoyq7nkifm36j3y2ehwaikefr 
       foreign key (cozinha_id) 
       references tb_cozinha (id);

    alter table tb_restaurantes 
       add constraint FKjg30pyl5tgghtm014ua5dts86 
       foreign key (endereco_cidade_id) 
       references tb_cidade (id);

    alter table tb_usuario_grupos 
       add constraint FKgnufttn621ij55s4p5bck5cd7 
       foreign key (grupo_id) 
       references tb_grupo (id);

    alter table tb_usuario_grupos 
       add constraint FK3o0jttpcfq6a9hgklm1spvlfp 
       foreign key (usuario_id) 
       references tb_usuarios (id);
insert into tb_cozinhas (nome_cozinha) values ('Americana');
insert into tb_cozinhas (nome_cozinha) values ('Brasileira');
insert into tb_cozinhas (nome_cozinha) values ('Europeia');
insert into tb_forma_pagamento (descricao) values ('Dinheiro');
insert into tb_forma_pagamento (descricao) values ('Debito');
insert into tb_forma_pagamento (descricao) values ('Credito');
insert into tb_forma_pagamento (descricao) values ('Pix');
insert into tb_forma_pagamento (descricao) values ('Cheque');
insert into tb_estado (id, nome) values (1, 'Minas Gerais');
insert into tb_estado (id, nome) values (2, 'São Paulo');
insert into tb_estado (id, nome) values (3, 'Ceará');
insert into tb_estado (id, nome) values (4, 'Distrito Federal');
insert into tb_cidade (id, nome, estado_id) values (1, 'Uberlândia', 1);
insert into tb_cidade (id, nome, estado_id) values (2, 'Belo Horizonte', 1);
insert into tb_cidade (id, nome, estado_id) values (3, 'São Paulo', 2);
insert into tb_cidade (id, nome, estado_id) values (4, 'Campinas', 2);
insert into tb_cidade (id, nome, estado_id) values (5, 'Fortaleza', 3);
insert into tb_cidade (id, nome, estado_id) values (6, 'Brasília', 4);
insert into tb_restaurantes (nome, taxa_frete, cozinha_id, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro, endereco_complemento, data_cadastro, data_atualizacao) values('Tavares',10.5, 2, 6, '72.910-121', 'QS 416 Conjunto 22 Lote','10', 'Samambaia Norte', 'Comercio Local', utc_timestamp, utc_timestamp);
insert into tb_restaurantes (nome, taxa_frete, cozinha_id, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro, endereco_complemento, data_cadastro, data_atualizacao) values('Comida Mineira',8.5,2, 2,'72.312-100','QS 402 Conjunto A lote','1', 'Samambaia Norte', 'Comercio Local', utc_timestamp, utc_timestamp);
insert into tb_restaurantes (nome, taxa_frete, cozinha_id, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro, endereco_complemento, data_cadastro, data_atualizacao) values('Coco Bambu',22.5, 3, 4, '81.319-019' , 'Rua Barão de Sorocaba', '1038', 'Centro', 'Comercial', utc_timestamp, utc_timestamp);
insert into tb_restaurantes (nome, taxa_frete, cozinha_id, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro, endereco_complemento, data_cadastro, data_atualizacao) values('Pão Recheado',5.5, 1, 5, '57.708-102','Rua Amaciante Del Brabo','10430','Zona Sul','Avenida Principal', utc_timestamp, utc_timestamp);
insert into tb_restaurantes (nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) values ('Java Steakhouse', 12, 3, utc_timestamp, utc_timestamp);
insert into tb_restaurantes (nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) values ('Lanchonete do Tio Sam', 11, 2, utc_timestamp, utc_timestamp);
insert into tb_restaurantes (nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) values ('Bar da Maria', 6, 1, utc_timestamp, utc_timestamp);
insert into tb_permissao (id, nome, descricao) values (1, 'CONSULTAR_COZINHAS', 'Permite consultar cozinhas');
insert into tb_permissao (id, nome, descricao) values (2, 'EDITAR_COZINHAS', 'Permite editar cozinhas');
insert into tb_restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values(1, 1), (1, 2), (1, 3), (2, 1), (2, 2), (2, 3), (3, 1), (3, 2), (3, 3), (3, 4), (4, 1), (4, 2), (4, 3), (4, 4), (5, 1), (5, 2), (5, 3), (5, 4), (6, 1), (6, 2), (6, 3), (6, 4), (7, 1), (7, 2), (7, 3), (7, 4);
insert into tb_produtos (ativo, produto_descricao, produto_nome, produto_preco, restaurante_id) values (1, 'Água da Casa 500ml', 'Água da Casa', 1, 1), (1, 'Água da Casa 500ml', 'Água da Casa', 1, 2), (1, 'Água da Casa 500ml', 'Água da Casa', 1, 3), (1, 'Água da Casa 500ml', 'Água da Casa', 1, 4);
insert into tb_produtos (produto_nome, produto_descricao, produto_preco, ativo, restaurante_id) values ('Porco com molho agridoce', 'Deliciosa carne suína ao molho especial', 78.90, 1, 1);
insert into tb_produtos (produto_nome, produto_descricao, produto_preco, ativo, restaurante_id) values ('Camarão tailandês', '16 camarões grandes ao molho picante', 110, 1, 1);
insert into tb_produtos (produto_nome, produto_descricao, produto_preco, ativo, restaurante_id) values ('Salada picante com carne grelhada', 'Salada de folhas com cortes finos de carne bovina grelhada e nosso molho especial de pimenta vermelha', 87.20, 1, 2);
insert into tb_produtos (produto_nome, produto_descricao, produto_preco, ativo, restaurante_id) values ('Garlic Naan', 'Pão tradicional indiano com cobertura de alho', 21, 1, 3);
insert into tb_produtos (produto_nome, produto_descricao, produto_preco, ativo, restaurante_id) values ('Murg Curry', 'Cubos de frango preparados com molho curry e especiarias', 43, 1, 3);
insert into tb_produtos (produto_nome, produto_descricao, produto_preco, ativo, restaurante_id) values ('Bife Ancho', 'Corte macio e suculento, com dois dedos de espessura, retirado da parte dianteira do contrafilé', 79, 1, 4);
insert into tb_produtos (produto_nome, produto_descricao, produto_preco, ativo, restaurante_id) values ('T-Bone', 'Corte muito saboroso, com um osso em formato de T, sendo de um lado o contrafilé e do outro o filé mignon', 89, 1, 4);
insert into tb_produtos (produto_nome, produto_descricao, produto_preco, ativo, restaurante_id) values ('Sanduíche X-Tudo', 'Sandubão com muito queijo, hamburger bovino, bacon, ovo, salada e maionese', 19, 1, 5);
insert into tb_produtos (produto_nome, produto_descricao, produto_preco, ativo, restaurante_id) values ('Espetinho de Cupim', 'Acompanha farinha, mandioca e vinagrete', 8, 1, 6);