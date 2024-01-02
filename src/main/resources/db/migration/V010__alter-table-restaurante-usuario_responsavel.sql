create table tb_restaurante_usuarios_responsaveis (
    restaurante_id bigint not null,
    usuario_id bigint not null,
    
    primary key (restaurante_id, usuario_id)
) engine=InnoDB default charset=utf8;

alter table tb_restaurante_usuarios_responsaveis add constraint fk_restaurante_usuario_restaurante
foreign key (restaurante_id) references tb_restaurante (id);

alter table tb_restaurante_usuarios_responsaveis add constraint fk_restaurante_usuario_usuario
foreign key (usuario_id) references tb_usuario (id);