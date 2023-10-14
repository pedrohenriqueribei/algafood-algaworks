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

insert into tb_restaurantes(nome, taxa_frete, cozinha_id, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro, endereco_complemento, data_cadastro, data_atualizacao) values('Tavares',10.5, 2, 6, '72.910-121', 'QS 416 Conjunto 22 Lote','10', 'Samambaia Norte', 'Comercio Local', utc_timestamp, utc_timestamp);
insert into tb_restaurantes(nome, taxa_frete, cozinha_id, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro, endereco_complemento, data_cadastro, data_atualizacao) values('Comida Mineira',8.5,2, 2,'72.312-100','QS 402 Conjunto A lote','1', 'Samambaia Norte', 'Comercio Local', utc_timestamp, utc_timestamp);
insert into tb_restaurantes(nome, taxa_frete, cozinha_id, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro, endereco_complemento, data_cadastro, data_atualizacao) values('Coco Bambu',22.5, 3, 4, '81.319-019' , 'Rua Barão de Sorocaba', '1038', 'Centro', 'Comercial', utc_timestamp, utc_timestamp);
insert into tb_restaurantes(nome, taxa_frete, cozinha_id, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro, endereco_complemento, data_cadastro, data_atualizacao) values('Pão Recheado',5.5, 1, 5, '57.708-102','Rua Amaciante Del Brabo','10430','Zona Sul','Avenida Principal', utc_timestamp, utc_timestamp);


insert into tb_permissao (id, nome, descricao) values (1, 'CONSULTAR_COZINHAS', 'Permite consultar cozinhas');
insert into tb_permissao (id, nome, descricao) values (2, 'EDITAR_COZINHAS', 'Permite editar cozinhas');

insert into tb_restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values(1, 1), (1, 2), (1, 3), (2, 1), (2, 2), (2, 3), (3, 1), (3, 2), (3, 3), (3, 4), (4, 1), (4, 2), (4, 3), (4, 4);
