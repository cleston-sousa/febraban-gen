
create table `estado` (
    `id` tinyint not null,
    `codigo` varchar(2) not null,
    `nome` varchar(100) not null
);--engine=innodb default charset=utf8

alter table `estado` add constraint `pk_estado` primary key (`id`);
-- alter table `estado` add constraint `uq_estado_0` unique (`codigo`);
