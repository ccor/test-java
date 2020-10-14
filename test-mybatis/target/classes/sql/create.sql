CREATE TABLE `test`.`t_user` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_name` VARCHAR(45) NOT NULL COMMENT '用户名',
  `password` VARCHAR(45) NOT NULL COMMENT '密码',
  `nick_name` VARCHAR(45) NULL COMMENT '昵称',
  `real_name` VARCHAR(45) NULL COMMENT '姓名,
  `age` TINYINT NOT NULL COMMENT '年龄',
  `sex` TINYINT NOT NULL COMMENT '性别',
  `is_deleted` TINYINT NULL COMMENT '是否删除',
  `created_time` TIMESTAMP NULL COMMENT '创建时间',
  `updated_time` TIMESTAMP NULL COMMENT '更新时间',
  PRIMARY KEY (`id`));