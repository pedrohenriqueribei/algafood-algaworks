create DATABASE db_algafood;

use db_algafood;

drop database db_algafood;

SHOW TABLES;

SELECT * FROM tb_cidade;
SELECT * FROM tb_cozinha;
SELECT * FROM tb_estado;
SELECT * FROM tb_restaurante;
SELECT * FROM tb_forma_pagamento;
SELECT * FROM tb_permissao;
SELECT * FROM tb_restaurante_forma_pagamento;
SELECT * FROM tb_produto;
SELECT * FROM tb_pedido;
SELECT * FROM tb_item_pedido;
SELECT * FROM flyway_schema_history;

delete from flyway_schema_history WHERE installed_rank = 6;
select utc_timestamp;

desc tb_restaurantes;
DESC tb_cozinha;
DESC tb_produtos;
DESC tb_grupo_permissao;
DESC tb_usuarios;
desc flyway_schema_history;
SELECT * FROM db_algafood.tb_cozinhas;