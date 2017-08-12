/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.6.21-log : Database - luckystar1
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`luckystar1` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `luckystar1`;

/*Table structure for table `DATABASECHANGELOG` */

DROP TABLE IF EXISTS `DATABASECHANGELOG`;

CREATE TABLE `DATABASECHANGELOG` (
  `ID` varchar(255) NOT NULL,
  `AUTHOR` varchar(255) NOT NULL,
  `FILENAME` varchar(255) NOT NULL,
  `DATEEXECUTED` datetime NOT NULL,
  `ORDEREXECUTED` int(11) NOT NULL,
  `EXECTYPE` varchar(10) NOT NULL,
  `MD5SUM` varchar(35) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `COMMENTS` varchar(255) DEFAULT NULL,
  `TAG` varchar(255) DEFAULT NULL,
  `LIQUIBASE` varchar(20) DEFAULT NULL,
  `CONTEXTS` varchar(255) DEFAULT NULL,
  `LABELS` varchar(255) DEFAULT NULL,
  `DEPLOYMENT_ID` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `DATABASECHANGELOG` */

insert  into `DATABASECHANGELOG`(`ID`,`AUTHOR`,`FILENAME`,`DATEEXECUTED`,`ORDEREXECUTED`,`EXECTYPE`,`MD5SUM`,`DESCRIPTION`,`COMMENTS`,`TAG`,`LIQUIBASE`,`CONTEXTS`,`LABELS`,`DEPLOYMENT_ID`) values ('00000000000001','jhipster','config/liquibase/changelog/00000000000000_initial_schema.xml','2017-08-11 13:49:23',1,'EXECUTED','7:8ee4629d37d7ed5d538205127bc4e4b5','createTable tableName=jhi_user; createIndex indexName=idx_user_login, tableName=jhi_user; createIndex indexName=idx_user_email, tableName=jhi_user; createTable tableName=jhi_authority; createTable tableName=jhi_user_authority; addPrimaryKey tableN...','',NULL,'3.5.3',NULL,NULL,'2430558032'),('20170809155514-1','jhipster','config/liquibase/changelog/20170809155514_added_entity_LaborUnion.xml','2017-08-11 13:49:23',2,'EXECUTED','7:388c75520e5eca58cd85afd2ca4c8dbe','createTable tableName=labor_union; createTable tableName=labor_union_user; addPrimaryKey tableName=labor_union_user','',NULL,'3.5.3',NULL,NULL,'2430558032'),('20170809162244-1','jhipster','config/liquibase/changelog/20170809162244_added_entity_ChickenInfo.xml','2017-08-11 13:49:23',3,'EXECUTED','7:37b7bd50411d62be003a630b2611484f','createTable tableName=chicken_info','',NULL,'3.5.3',NULL,NULL,'2430558032'),('20170809162245-1','jhipster','config/liquibase/changelog/20170809162245_added_entity_TaskInfo.xml','2017-08-11 13:49:23',4,'EXECUTED','7:26eca2402fa220e2d84043145a24107c','createTable tableName=task_info','',NULL,'3.5.3',NULL,NULL,'2430558032'),('20170809162246-1','jhipster','config/liquibase/changelog/20170809162246_added_entity_WorkInfo.xml','2017-08-11 13:49:23',5,'EXECUTED','7:d270d071959d796004589e704baaca4f','createTable tableName=work_info; dropDefaultValue columnName=last_time, tableName=work_info','',NULL,'3.5.3',NULL,NULL,'2430558032'),('20170809155514-2','jhipster','config/liquibase/changelog/20170809155514_added_entity_constraints_LaborUnion.xml','2017-08-11 13:49:23',6,'EXECUTED','7:f47fbc614a19b7ba5d523a5c3405bb64','addForeignKeyConstraint baseTableName=labor_union_user, constraintName=fk_labor_union_user_labor_unions_id, referencedTableName=labor_union; addForeignKeyConstraint baseTableName=labor_union_user, constraintName=fk_labor_union_user_users_id, refer...','',NULL,'3.5.3',NULL,NULL,'2430558032'),('20170809162244-2','jhipster','config/liquibase/changelog/20170809162244_added_entity_constraints_ChickenInfo.xml','2017-08-11 13:49:23',7,'EXECUTED','7:0b024fc149e4271f90ba90642e8052e6','addForeignKeyConstraint baseTableName=chicken_info, constraintName=fk_chicken_info_labor_union_id, referencedTableName=labor_union','',NULL,'3.5.3',NULL,NULL,'2430558032'),('20170809162245-2','jhipster','config/liquibase/changelog/20170809162245_added_entity_constraints_TaskInfo.xml','2017-08-11 13:49:24',8,'EXECUTED','7:f67274b9837a50e187ce049709259ebd','addForeignKeyConstraint baseTableName=task_info, constraintName=fk_task_info_chicken_info_id, referencedTableName=chicken_info','',NULL,'3.5.3',NULL,NULL,'2430558032'),('20170809162246-2','jhipster','config/liquibase/changelog/20170809162246_added_entity_constraints_WorkInfo.xml','2017-08-11 13:49:24',9,'EXECUTED','7:0b22fd4248204d96d65e8855c58bf529','addForeignKeyConstraint baseTableName=work_info, constraintName=fk_work_info_task_info_id, referencedTableName=task_info','',NULL,'3.5.3',NULL,NULL,'2430558032');

/*Table structure for table `DATABASECHANGELOGLOCK` */

DROP TABLE IF EXISTS `DATABASECHANGELOGLOCK`;

CREATE TABLE `DATABASECHANGELOGLOCK` (
  `ID` int(11) NOT NULL,
  `LOCKED` bit(1) NOT NULL,
  `LOCKGRANTED` datetime DEFAULT NULL,
  `LOCKEDBY` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `DATABASECHANGELOGLOCK` */

insert  into `DATABASECHANGELOGLOCK`(`ID`,`LOCKED`,`LOCKGRANTED`,`LOCKEDBY`) values (1,'','2017-08-11 18:00:19','10.12.194.236 (10.12.194.236)');

/*Table structure for table `chicken_info` */

DROP TABLE IF EXISTS `chicken_info`;

CREATE TABLE `chicken_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(10) NOT NULL COMMENT '真名',
  `nick_name` varchar(50) DEFAULT NULL COMMENT '艺名',
  `phone_number` bigint(20) DEFAULT NULL COMMENT '手机号',
  `qq` bigint(20) DEFAULT NULL COMMENT 'QQ号',
  `wei_chat` varchar(50) DEFAULT NULL COMMENT '微信号',
  `star_id` bigint(20) NOT NULL COMMENT '繁星id',
  `reg_date` date NOT NULL COMMENT '注册时间',
  `login_name` varchar(50) NOT NULL COMMENT '繁星登录名',
  `password` varchar(50) NOT NULL COMMENT '繁星登录密码',
  `cookie` varchar(10480) DEFAULT NULL COMMENT '登录后的cookie信息，需要人工定期维护',
  `time_rate` float(2,1) NOT NULL DEFAULT '1.0' COMMENT '考勤倍率',
  `bean_rate` float(2,1) NOT NULL DEFAULT '1.0' COMMENT '星豆倍率',
  `state` varchar(255) NOT NULL COMMENT '0：停用 1：在用',
  `labor_union_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_chicken_info_labor_union_id` (`labor_union_id`),
  CONSTRAINT `fk_chicken_info_labor_union_id` FOREIGN KEY (`labor_union_id`) REFERENCES `labor_union` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `chicken_info` */

insert  into `chicken_info`(`id`,`user_name`,`nick_name`,`phone_number`,`qq`,`wei_chat`,`star_id`,`reg_date`,`login_name`,`password`,`cookie`,`time_rate`,`bean_rate`,`state`,`labor_union_id`) values (1,'陈怡','JD走运求秒榜一',NULL,NULL,NULL,365997694,'2017-08-09','18683443665','INyoureyes22','kg_mid=f245e8b93bd77a4079dba4d535a4dd04; KuGoo=KugooID=1067527567&KugooPwd=35F3FC5E282631877D7E900040ADED45&NickName=%u0031%u0030%u0036%u0037%u0035%u0032%u0037%u0035%u0036%u0037&Pic=http://imge.kugou.com/kugouicon/165/20100101/20100101192931478054.jpg&RegState=1&RegFrom=&t=56959ea30d774b684f31dc64589d8116098e2d7cec637052b6b0497ae6cf3e4f&a_id=1010&ct=1502243400&UserName=%u0031%u0038%u0036%u0038%u0033%u0034%u0034%u0033%u0036%u0036%u0035; _fxNickName=JD%E8%B5%B0%E8%BF%90%E6%B1%82%E7%A7%92%E6%A6%9C%E4%B8%80; _fxRichLevel=6; Hm_lvt_a8cea520bce1646202b6709aa9f11956=1502002639,1502105592,1502158620,1502243401; _fx_coin=1548.00; fxClientInfo=%7B%22userId%22%3A%22365997694%22%2C%22kugouId%22%3A%221067527567%22%2C%22ip%22%3A%220.0.0.0%22%7D; ACK_SERVER_10004=%7B%22list%22%3A%5B%5B%22apibj.fanxing.kugou.com%22%5D%5D%7D; FANXING=%257B%2522kugouId%2522%253A%25221067527567%2522%252C%2522coin%2522%253A%25221548.00%2522%252C%2522atime%2522%253A1502274853%252C%2522isRemember%2522%253A0%252C%2522sign%2522%253A%2522fe349936b65d1fa012f713b762f18e31%2522%257D; FANXING_COIN=1548.00; ACK_SERVER_10013=%7B%22list%22%3A%5B%5B%22visitor.fanxing.kugou.com%22%5D%2C%5B%22visitorfx2.kugou.com%22%5D%5D%7D; ACK_SERVER_10021=%7B%22list%22%3A%5B%5B%22service01.fanxing.com%22%5D%2C%5B%22service03.fanxing.com%22%5D%2C%5B%22service02.fanxing.com%22%5D%2C%5B%22service04.fanxing.com%22%5D%5D%7D; ACK_SERVER_10012=%7B%22list%22%3A%5B%5B%22task.fanxing.kugou.com%22%5D%2C%5B%22taskfx2.kugou.com%22%5D%5D%7D; ACK_SERVER_10009=%7B%22list%22%3A%5B%5B%22act.fanxing.kugou.com%22%5D%2C%5B%22actfx2.kugou.com%22%5D%5D%7D; ACK_SERVER_10011=%7B%22list%22%3A%5B%5B%22fanxing.kugou.com%22%5D%2C%5B%22sparefx2.kugou.com%22%5D%5D%7D; ACK_SERVER_10020=%7B%22list%22%3A%5B%5B%22fx.service.kugou.com%22%5D%2C%5B%22fxservice1.kugou.com%22%5D%2C%5B%22fxservice2.kugou.com%22%5D%5D%7D; ACK_SERVER_10010=%7B%22list%22%3A%5B%5B%22api.fanxing.kugou.com%22%5D%2C%5B%22apifx2.kugou.com%22%5D%5D%7D; ACK_SERVER_10014=%7B%22list%22%3A%5B%5B%22service.fanxing.kugou.com%22%5D%2C%5B%22servicefx2.kugou.com%22%5D%5D%7D; fx_manifest=1; Hm_lvt_9903bd4a6f77b8537ecb17a75dd2e187=1502105130,1502158616,1502243398,1502274852; Hm_lpvt_9903bd4a6f77b8537ecb17a75dd2e187=1502274852; Hm_lvt_e0a7c5eaf6994884c4376a64da96825a=1502105129,1502158615,1502243398,1502274853; Hm_lpvt_e0a7c5eaf6994884c4376a64da96825a=1502274853; Hm_lvt_52e69492bce68bf637c6f3a2f099ae08=1502105129,1502158616,1502243398,1502274853; Hm_lpvt_52e69492bce68bf637c6f3a2f099ae08=1502274853',1.0,1.0,'ON',1),(2,'测试','多点test',0,0,'0',1131331064,'2017-08-11','18980868096','\\`1234567890-','kg_mid=f245e8b93bd77a4079dba4d535a4dd04; _fxNickName=JD%E8%B5%B0%E8%BF%90%E6%B1%82%E7%A7%92%E6%A6%9C%E4%B8%80; _fxRichLevel=6; Hm_lvt_a8cea520bce1646202b6709aa9f11956=1502002639,1502105592,1502158620,1502243401; _fx_coin=3.00; fxClientInfo=%7B%22userId%22%3A%22365997694%22%2C%22kugouId%22%3A%221067527567%22%2C%22ip%22%3A%220.0.0.0%22%7D; FANXING=%257B%2522kugouId%2522%253A%25221067527567%2522%252C%2522coin%2522%253A%25223.00%2522%252C%2522atime%2522%253A1502439688%252C%2522isRemember%2522%253A0%252C%2522sign%2522%253A%252201deff1f4fa33914409b19f866831b8d%2522%257D; FANXING_COIN=3.00; ACK_SERVER_10004=%7B%22list%22%3A%5B%5B%22apibj.fanxing.kugou.com%22%5D%5D%7D; ACK_SERVER_10013=%7B%22list%22%3A%5B%5B%22visitor.fanxing.kugou.com%22%5D%2C%5B%22visitorfx2.kugou.com%22%5D%5D%7D; ACK_SERVER_10021=%7B%22list%22%3A%5B%5B%22service01.fanxing.com%22%5D%2C%5B%22service03.fanxing.com%22%5D%2C%5B%22service02.fanxing.com%22%5D%2C%5B%22service04.fanxing.com%22%5D%5D%7D; ACK_SERVER_10012=%7B%22list%22%3A%5B%5B%22task.fanxing.kugou.com%22%5D%2C%5B%22taskfx2.kugou.com%22%5D%5D%7D; ACK_SERVER_10009=%7B%22list%22%3A%5B%5B%22act.fanxing.kugou.com%22%5D%2C%5B%22actfx2.kugou.com%22%5D%5D%7D; ACK_SERVER_10011=%7B%22list%22%3A%5B%5B%22fanxing.kugou.com%22%5D%2C%5B%22sparefx2.kugou.com%22%5D%5D%7D; ACK_SERVER_10020=%7B%22list%22%3A%5B%5B%22fx.service.kugou.com%22%5D%2C%5B%22fxservice1.kugou.com%22%5D%2C%5B%22fxservice2.kugou.com%22%5D%5D%7D; ACK_SERVER_10010=%7B%22list%22%3A%5B%5B%22api.fanxing.kugou.com%22%5D%2C%5B%22apifx2.kugou.com%22%5D%5D%7D; ACK_SERVER_10014=%7B%22list%22%3A%5B%5B%22service.fanxing.kugou.com%22%5D%2C%5B%22servicefx2.kugou.com%22%5D%5D%7D; fx_manifest=1; Hm_lvt_e0a7c5eaf6994884c4376a64da96825a=1502158615,1502243398,1502274853,1502439687; Hm_lpvt_e0a7c5eaf6994884c4376a64da96825a=1502439687; Hm_lvt_9903bd4a6f77b8537ecb17a75dd2e187=1502158616,1502243398,1502274852,1502439687; Hm_lpvt_9903bd4a6f77b8537ecb17a75dd2e187=1502439688; Hm_lvt_52e69492bce68bf637c6f3a2f099ae08=1502158616,1502243398,1502274853,1502439687; Hm_lpvt_52e69492bce68bf637c6f3a2f099ae08=1502439688; LoginCheckCode=128e84ebeabc434ba8573ee668b36c992eb9; KuGoo=KugooID=1131331064&KugooPwd=E602088D7DC2F53AADF6BB29E38A5D86&NickName=%u0031%u0031%u0033%u0031%u0033%u0033%u0031%u0030%u0036%u0034&Pic=http://imge.kugou.com/kugouicon/165/20100101/20100101192931478054.jpg&RegState=1&RegFrom=&t=8b3daf543e5664ee2d47c7170b84fcd66e74b5eb6f88d9632bb5f92c76308c56&a_id=1010&ct=1502439725&UserName=%u0031%u0038%u0039%u0038%u0030%u0038%u0036%u0038%u0030%u0039%u0036',1.0,1.0,'ON',1);

/*Table structure for table `jhi_authority` */

DROP TABLE IF EXISTS `jhi_authority`;

CREATE TABLE `jhi_authority` (
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `jhi_authority` */

insert  into `jhi_authority`(`name`) values ('ROLE_ADMIN'),('ROLE_USER');

/*Table structure for table `jhi_persistent_audit_event` */

DROP TABLE IF EXISTS `jhi_persistent_audit_event`;

CREATE TABLE `jhi_persistent_audit_event` (
  `event_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `principal` varchar(50) NOT NULL,
  `event_date` timestamp NULL DEFAULT NULL,
  `event_type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`event_id`),
  KEY `idx_persistent_audit_event` (`principal`,`event_date`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

/*Data for the table `jhi_persistent_audit_event` */

insert  into `jhi_persistent_audit_event`(`event_id`,`principal`,`event_date`,`event_type`) values (1,'admin','2017-08-11 14:02:21','AUTHENTICATION_SUCCESS'),(2,'admin','2017-08-11 23:17:28','AUTHENTICATION_SUCCESS'),(3,'admin','2017-08-12 09:48:05','AUTHENTICATION_SUCCESS'),(4,'admin','2017-08-12 09:49:34','AUTHENTICATION_SUCCESS'),(5,'admin','2017-08-12 10:41:03','AUTHENTICATION_SUCCESS'),(6,'admin','2017-08-12 13:56:33','AUTHENTICATION_SUCCESS'),(7,'admin','2017-08-12 13:56:33','AUTHENTICATION_SUCCESS'),(8,'admin','2017-08-12 14:16:05','AUTHENTICATION_SUCCESS'),(9,'admin','2017-08-12 14:16:06','AUTHENTICATION_SUCCESS'),(10,'admin','2017-08-12 14:16:08','AUTHENTICATION_SUCCESS'),(11,'admin','2017-08-12 17:13:40','AUTHENTICATION_SUCCESS');

/*Table structure for table `jhi_persistent_audit_evt_data` */

DROP TABLE IF EXISTS `jhi_persistent_audit_evt_data`;

CREATE TABLE `jhi_persistent_audit_evt_data` (
  `event_id` bigint(20) NOT NULL,
  `name` varchar(150) NOT NULL,
  `value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`event_id`,`name`),
  KEY `idx_persistent_audit_evt_data` (`event_id`),
  CONSTRAINT `fk_evt_pers_audit_evt_data` FOREIGN KEY (`event_id`) REFERENCES `jhi_persistent_audit_event` (`event_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `jhi_persistent_audit_evt_data` */

/*Table structure for table `jhi_user` */

DROP TABLE IF EXISTS `jhi_user`;

CREATE TABLE `jhi_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `login` varchar(50) NOT NULL,
  `password_hash` varchar(60) DEFAULT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `image_url` varchar(256) DEFAULT NULL,
  `activated` bit(1) NOT NULL,
  `lang_key` varchar(5) DEFAULT NULL,
  `activation_key` varchar(20) DEFAULT NULL,
  `reset_key` varchar(20) DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` timestamp NOT NULL,
  `reset_date` timestamp NULL DEFAULT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `last_modified_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `login` (`login`),
  UNIQUE KEY `idx_user_login` (`login`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `idx_user_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `jhi_user` */

insert  into `jhi_user`(`id`,`login`,`password_hash`,`first_name`,`last_name`,`email`,`image_url`,`activated`,`lang_key`,`activation_key`,`reset_key`,`created_by`,`created_date`,`reset_date`,`last_modified_by`,`last_modified_date`) values (1,'system','$2a$10$mE.qmcV0mFU5NcKh73TZx.z4ueI/.bDWbj0T1BYyqP481kGGarKLG','System','System','system@localhost','','','zh-cn',NULL,NULL,'system','2017-08-11 13:49:22',NULL,'system',NULL),(2,'anonymoususer','$2a$10$j8S5d7Sr7.8VTOYNviDPOeWX8KcYILUVJBsYV83Y5NtECayypx9lO','Anonymous','User','anonymous@localhost','','','zh-cn',NULL,NULL,'system','2017-08-11 13:49:22',NULL,'system',NULL),(3,'admin','$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC','Administrator','Administrator','admin@localhost','','','zh-cn',NULL,NULL,'system','2017-08-11 13:49:22',NULL,'system',NULL),(4,'user','$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K','User','User','user@localhost','','','zh-cn',NULL,NULL,'system','2017-08-11 13:49:22',NULL,'system',NULL);

/*Table structure for table `jhi_user_authority` */

DROP TABLE IF EXISTS `jhi_user_authority`;

CREATE TABLE `jhi_user_authority` (
  `user_id` bigint(20) NOT NULL,
  `authority_name` varchar(50) NOT NULL,
  PRIMARY KEY (`user_id`,`authority_name`),
  KEY `fk_authority_name` (`authority_name`),
  CONSTRAINT `fk_authority_name` FOREIGN KEY (`authority_name`) REFERENCES `jhi_authority` (`name`),
  CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `jhi_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `jhi_user_authority` */

insert  into `jhi_user_authority`(`user_id`,`authority_name`) values (1,'ROLE_ADMIN'),(3,'ROLE_ADMIN'),(1,'ROLE_USER'),(3,'ROLE_USER'),(4,'ROLE_USER');

/*Table structure for table `labor_union` */

DROP TABLE IF EXISTS `labor_union`;

CREATE TABLE `labor_union` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `l_id` int(11) NOT NULL COMMENT '公会id',
  `name` varchar(50) NOT NULL COMMENT '公会名称',
  `reg_date` date NOT NULL COMMENT '注册时间',
  `state` varchar(255) NOT NULL COMMENT '0：停用 1：在用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `labor_union` */

insert  into `labor_union`(`id`,`l_id`,`name`,`reg_date`,`state`) values (1,5544,'聚点','2017-08-10','ON');

/*Table structure for table `labor_union_user` */

DROP TABLE IF EXISTS `labor_union_user`;

CREATE TABLE `labor_union_user` (
  `users_id` bigint(20) NOT NULL,
  `labor_unions_id` bigint(20) NOT NULL,
  PRIMARY KEY (`labor_unions_id`,`users_id`),
  KEY `fk_labor_union_user_users_id` (`users_id`),
  CONSTRAINT `fk_labor_union_user_labor_unions_id` FOREIGN KEY (`labor_unions_id`) REFERENCES `labor_union` (`id`),
  CONSTRAINT `fk_labor_union_user_users_id` FOREIGN KEY (`users_id`) REFERENCES `jhi_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `labor_union_user` */

/*Table structure for table `task_info` */

DROP TABLE IF EXISTS `task_info`;

CREATE TABLE `task_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `min_task` int(11) NOT NULL COMMENT '任务数',
  `max_task` int(11) NOT NULL COMMENT '目标数',
  `cur_month` int(11) NOT NULL COMMENT '月份',
  `chicken_info_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_task_info_chicken_info_id` (`chicken_info_id`),
  CONSTRAINT `fk_task_info_chicken_info_id` FOREIGN KEY (`chicken_info_id`) REFERENCES `chicken_info` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `task_info` */

insert  into `task_info`(`id`,`min_task`,`max_task`,`cur_month`,`chicken_info_id`) values (1,150,200,201708,1),(2,100,123,201708,2);

/*Table structure for table `work_info` */

DROP TABLE IF EXISTS `work_info`;

CREATE TABLE `work_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `star_id` bigint(20) NOT NULL COMMENT '繁星id',
  `star_level` int(11) DEFAULT NULL COMMENT '繁星等级',
  `star_name` varchar(20) DEFAULT NULL COMMENT '繁星等级名称',
  `rich_level` int(11) DEFAULT NULL COMMENT '财富等级',
  `rich_name` varchar(20) DEFAULT NULL COMMENT '财富等级名称',
  `fisrt_bean` float(20,2) DEFAULT NULL COMMENT '当天初始星豆数',
  `bean_total` float(20,2) DEFAULT NULL COMMENT '星豆总数',
  `coin` float(20,2) DEFAULT NULL COMMENT '星币数',
  `coin_total` float(20,2) DEFAULT NULL COMMENT '星币总数',
  `fans_count` int(11) DEFAULT NULL COMMENT '被关注数',
  `follow_count` int(11) DEFAULT NULL COMMENT '关注数',
  `experience` float(20,2) DEFAULT NULL COMMENT '经验值',
  `work_time` int(11) DEFAULT NULL COMMENT '工作时长',
  `cur_month` int(6) NOT NULL COMMENT '当前月份',
  `cur_day` date NOT NULL COMMENT '当前天',
  `last_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `task_info_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `star_id` (`star_id`,`cur_day`),
  UNIQUE KEY `star_id_2` (`star_id`,`cur_day`),
  KEY `fk_work_info_task_info_id` (`task_info_id`),
  CONSTRAINT `fk_work_info_task_info_id` FOREIGN KEY (`task_info_id`) REFERENCES `task_info` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

/*Data for the table `work_info` */

insert  into `work_info`(`id`,`star_id`,`star_level`,`star_name`,`rich_level`,`rich_name`,`fisrt_bean`,`bean_total`,`coin`,`coin_total`,`fans_count`,`follow_count`,`experience`,`work_time`,`cur_month`,`cur_day`,`last_time`,`task_info_id`) values (13,1131331064,0,'无',0,'平民',0.00,0.00,0.00,0.00,0,0,0.00,0,201708,'2017-08-12','2017-08-12 18:55:46',2),(14,365997694,15,'5皇冠',6,'6富',6692161.50,6692161.50,3.00,13939786.00,4190,8,120360.50,0,201708,'2017-08-12','2017-08-12 18:55:46',1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
