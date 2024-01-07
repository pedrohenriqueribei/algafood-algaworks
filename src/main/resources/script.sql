create DATABASE db_algafood;

use db_algafood;
show DATABASES;
use db_algafood_test;
/*
drop database db_algafood;
*/
drop database db_algafood;
SHOW TABLES;

SELECT * FROM tb_cidade;
SELECT * FROM tb_cozinha;
SELECT * FROM tb_estado;
SELECT * FROM tb_restaurante;
SELECT * FROM tb_forma_pagamento;
SELECT * FROM tb_permissao;
SELECT * FROM tb_restaurante_forma_pagamento;
SELECT * FROM tb_produto WHERE restaurante_id = 1;
SELECT * FROM flyway_schema_history;

delete from flyway_schema_history WHERE installed_rank = 7;
DROP TABLE IF EXISTS tb_pedido;
delete from tb_item_pedido;
select utc_timestamp;

desc tb_restaurante;
DESC tb_cozinha;
DESC tb_produto;
DESC tb_grupo_permissao;
DESC tb_usuarios;
DESC tb_usuario_grupos;
DESC tb_restaurante_usuarios_responsaveis;
desc flyway_schema_history;
SELECT * FROM db_algafood.tb_cozinhas;
DESC tb_pedido;

SELECT * FROM tb_usuario;
SELECT * FROM tb_grupo;
SELECT * FROM tb_permissao;
SELECT * FROM tb_grupo_permissao;
SELECT * FROM tb_restaurante_usuarios_responsaveis;

SELECT * FROM tb_pedido;
SELECT * FROM tb_item_pedido;
SELECT * FROM tb_produto;
DESC tb_pedido;
SHOW tables;