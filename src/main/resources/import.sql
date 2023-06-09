insert into tb_cozinhas (nome_cozinha) values ('Americana');
insert into tb_cozinhas (nome_cozinha) values ('Brasileira');
insert into tb_cozinhas (nome_cozinha) values ('Europeia');

insert into tb_restaurantes(nome, taxa_frete, cozinha_id) values('Tavares',10.5, 2);
insert into tb_restaurantes(nome, taxa_frete, cozinha_id) values('Comida Mineira',8.5,2);
insert into tb_restaurantes(nome, taxa_frete, cozinha_id) values('Coco Bambu',22.5, 3);
insert into tb_restaurantes(nome, taxa_frete, cozinha_id) values('Pão Recheado',5.5, 1);

insert into tb_forma_pagamento (descricao) values ('Dinheiro');
insert into tb_forma_pagamento (descricao) values ('Debito');
insert into tb_forma_pagamento (descricao) values ('Credito');
insert into tb_forma_pagamento (descricao) values ('Pix');

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

insert into tb_permissao (id, nome, descricao) values (1, 'CONSULTAR_COZINHAS', 'Permite consultar cozinhas');
insert into tb_permissao (id, nome, descricao) values (2, 'EDITAR_COZINHAS', 'Permite editar cozinhas');
