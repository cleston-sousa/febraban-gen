
create table `cidade` (
    `id` int unsigned not null,
    `nome` varchar(100) not null,
    `estado_id` tinyint not null
);--engine=innodb default charset=utf8

alter table `cidade` add constraint `pk_cidade` primary key (`id`);
--alter table `cidade` add constraint `fk_cidade_0` foreign key (`estado_id`) references `estado`(`id`);
