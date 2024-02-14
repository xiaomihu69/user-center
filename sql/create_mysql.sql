-- auto-generated definition
create table user
(
    id           bigint                             not null comment '主键ID'
        primary key,
    username     varchar(30)                        null comment '姓名',
    userAccount  varchar(256)                       null comment '账号',
    age          int                                null comment '年龄',
    email        varchar(50)                        null comment '邮箱',
    gender       tinyint                            null comment '性别',
    userPassword varchar(512)                       null comment '密码',
    phone        varchar(128)                       null comment '电话',
    userStatus   int      default 0                 null comment '用户状态 0 -正常',
    avatarUrl    varchar(1024)                      null comment '用户头像',
    createTime   datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime   datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '修改时间',
    isDelete     tinyint                            null comment '是否删除',
    userRole     int      default 0                 not null comment '0普通用户 1管理员',
    planetCode   int                                null comment '星球编号'
);

