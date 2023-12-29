set foreign_key_checks = 0;

delete from tb_cidade;
delete from tb_cozinha;
delete from tb_estado;
delete from tb_forma_pagamento;
delete from tb_grupo;
delete from tb_grupo_permissao;
delete from tb_permissao;
delete from tb_produto;
delete from tb_restaurante;
delete from tb_restaurante_forma_pagamento;
delete from tb_usuario;
delete from tb_usuario_grupos;

set foreign_key_checks = 1;

alter table tb_cidade auto_increment = 1;
alter table tb_cozinha auto_increment = 1;
alter table tb_estado auto_increment = 1;
alter table tb_forma_pagamento auto_increment = 1;
alter table tb_grupo auto_increment = 1;
alter table tb_permissao auto_increment = 1;
alter table tb_produto auto_increment = 1;
alter table tb_restaurante auto_increment = 1;
alter table tb_usuario auto_increment = 1;

insert ignore into tb_cozinha (nome) values ('Americana');
insert ignore into tb_cozinha (nome) values ('Brasileira');
insert ignore into tb_cozinha (nome) values ('Europeia');

insert ignore into tb_forma_pagamento (descricao) values ('Dinheiro');
insert ignore into tb_forma_pagamento (descricao) values ('Debito');
insert ignore into tb_forma_pagamento (descricao) values ('Credito');
insert ignore into tb_forma_pagamento (descricao) values ('Pix');
insert ignore into tb_forma_pagamento (descricao) values ('Cheque');

insert ignore into tb_estado (id, nome) values (1, 'Minas Gerais');
insert ignore into tb_estado (id, nome) values (2, 'São Paulo');
insert ignore into tb_estado (id, nome) values (3, 'Ceará');
insert ignore into tb_estado (id, nome) values (4, 'Distrito Federal');

insert ignore into tb_cidade (id, nome, estado_id) values (1, 'Uberlândia', 1);
insert ignore into tb_cidade (id, nome, estado_id) values (2, 'Belo Horizonte', 1);
insert ignore into tb_cidade (id, nome, estado_id) values (3, 'São Paulo', 2);
insert ignore into tb_cidade (id, nome, estado_id) values (4, 'Campinas', 2);
insert ignore into tb_cidade (id, nome, estado_id) values (5, 'Fortaleza', 3);
insert ignore into tb_cidade (id, nome, estado_id) values (6, 'Brasília', 4);

insert ignore into tb_restaurante (nome, taxa_frete, cozinha_id, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro, endereco_complemento, data_cadastro, data_atualizacao) values('Tavares',10.5, 2, 6, '72.910-121', 'QS 416 Conjunto 22 Lote','10', 'Samambaia Norte', 'Comercio Local', utc_timestamp, utc_timestamp);
insert ignore into tb_restaurante (nome, taxa_frete, cozinha_id, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro, endereco_complemento, data_cadastro, data_atualizacao) values('Comida Mineira',8.5,2, 2,'72.312-100','QS 402 Conjunto A lote','1', 'Samambaia Norte', 'Comercio Local', utc_timestamp, utc_timestamp);
insert ignore into tb_restaurante (nome, taxa_frete, cozinha_id, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro, endereco_complemento, data_cadastro, data_atualizacao) values('Coco Bambu',22.5, 3, 4, '81.319-019' , 'Rua Barão de Sorocaba', '1038', 'Centro', 'Comercial', utc_timestamp, utc_timestamp);
insert ignore into tb_restaurante (nome, taxa_frete, cozinha_id, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro, endereco_complemento, data_cadastro, data_atualizacao) values('Pão Recheado',5.5, 1, 5, '57.708-102','Rua Amaciante Del Brabo','10430','Zona Sul','Avenida Principal', utc_timestamp, utc_timestamp);
insert ignore into tb_restaurante (nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) values ('Java Steakhouse', 12, 3, utc_timestamp, utc_timestamp);
insert ignore into tb_restaurante (nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) values ('Lanchonete do Tio Sam', 11, 2, utc_timestamp, utc_timestamp);
insert ignore into tb_restaurante (nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) values ('Bar da Maria', 6, 1, utc_timestamp, utc_timestamp);

update tb_restaurante set ativo = true where id <= 10;

insert ignore into tb_permissao (id, nome, descricao) values (1, 'CONSULTAR_COZINHAS', 'Permite consultar cozinhas');
insert ignore into tb_permissao (id, nome, descricao) values (2, 'EDITAR_COZINHAS', 'Permite editar cozinhas');

insert ignore into tb_restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values(1, 1), (1, 2), (1, 3), (2, 1), (2, 2), (2, 3), (3, 1), (3, 2), (3, 3), (3, 4), (4, 1), (4, 2), (4, 3), (4, 4), (5, 1), (5, 2), (5, 3), (5, 4), (6, 1), (6, 2), (6, 3), (6, 4), (7, 1), (7, 2), (7, 3), (7, 4);

insert ignore into tb_produto (ativo, produto_descricao, produto_nome, produto_preco, restaurante_id) values (1, 'Água da Casa 500ml', 'Água da Casa', 1, 1), (1, 'Água da Casa 500ml', 'Água da Casa', 1, 2), (1, 'Água da Casa 500ml', 'Água da Casa', 1, 3), (1, 'Água da Casa 500ml', 'Água da Casa', 1, 4);
insert ignore into tb_produto (produto_nome, produto_descricao, produto_preco, ativo, restaurante_id) values ('Porco com molho agridoce', 'Deliciosa carne suína ao molho especial', 78.90, 1, 1);
insert ignore into tb_produto (produto_nome, produto_descricao, produto_preco, ativo, restaurante_id) values ('Camarão tailandês', '16 camarões grandes ao molho picante', 110, 1, 1);
insert ignore into tb_produto (produto_nome, produto_descricao, produto_preco, ativo, restaurante_id) values ('Salada picante com carne grelhada', 'Salada de folhas com cortes finos de carne bovina grelhada e nosso molho especial de pimenta vermelha', 87.20, 1, 2);
insert ignore into tb_produto (produto_nome, produto_descricao, produto_preco, ativo, restaurante_id) values ('Garlic Naan', 'Pão tradicional indiano com cobertura de alho', 21, 1, 3);
insert ignore into tb_produto (produto_nome, produto_descricao, produto_preco, ativo, restaurante_id) values ('Murg Curry', 'Cubos de frango preparados com molho curry e especiarias', 43, 1, 3);
insert ignore into tb_produto (produto_nome, produto_descricao, produto_preco, ativo, restaurante_id) values ('Bife Ancho', 'Corte macio e suculento, com dois dedos de espessura, retirado da parte dianteira do contrafilé', 79, 1, 4);
insert ignore into tb_produto (produto_nome, produto_descricao, produto_preco, ativo, restaurante_id) values ('T-Bone', 'Corte muito saboroso, com um osso em formato de T, sendo de um lado o contrafilé e do outro o filé mignon', 89, 1, 4);
insert ignore into tb_produto (produto_nome, produto_descricao, produto_preco, ativo, restaurante_id) values ('Sanduíche X-Tudo', 'Sandubão com muito queijo, hamburger bovino, bacon, ovo, salada e maionese', 19, 1, 5);
insert ignore into tb_produto (produto_nome, produto_descricao, produto_preco, ativo, restaurante_id) values ('Espetinho de Cupim', 'Acompanha farinha, mandioca e vinagrete', 8, 1, 6);

insert ignore into tb_grupo (nome) values ('Gerente'),('Vendedor'),('Secretaria'),('Cadastrador');