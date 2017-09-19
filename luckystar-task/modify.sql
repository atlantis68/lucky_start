ALTER TABLE `user_info` 
   CHANGE `user_name` `user_name` VARCHAR(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '真名', 
   CHANGE `nick_name` `nick_name` VARCHAR(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL  COMMENT '艺名', 
   CHANGE `phone_number` `phone_number` bigint(20) NULL  comment '手机号', 
   CHANGE `qq` `qq` bigint(20) NULL  comment 'QQ号', 
   CHANGE `wei_chat` `wei_chat` varchar(50) character set utf8 collate utf8_general_ci NULL  comment '微信号',
   CHANGE `star_id` `star_id` BIGINT(20) NOT NULL COMMENT '繁星id', 
   CHANGE `reg_date` `reg_date` DATE NOT NULL COMMENT '注册时间', 
   CHANGE `login_name` `login_name` VARCHAR(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '繁星登录名',
   CHANGE `jhi_password` `jhi_password` VARCHAR(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '繁星登录密码', 
   CHANGE `cookie` `cookie` VARCHAR(10480) CHARACTER SET utf8 COLLATE utf8_general_ci NULL  COMMENT '登录后的cookie信息，需要人工定期维护', 
   CHANGE `time_rate` `time_rate` FLOAT(2,1) DEFAULT '1.0' NOT NULL COMMENT '考勤倍率', 
   CHANGE `bean_rate` `bean_rate` FLOAT(2,1) DEFAULT '1.0' NOT NULL COMMENT '星豆倍率', 
   CHANGE `last_maintain` `last_maintain` date NULL  comment '上次一维护cookie时间',  
   CHANGE `state` `state` VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '0：停用 1：在用';
   
ALTER TABLE `labor_union` 
   CHANGE `l_id` `l_id` INT(11) NOT NULL COMMENT '公会id', 
   CHANGE `name` `name` VARCHAR(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '公会名称', 
   CHANGE `reg_date` `reg_date` DATE NOT NULL COMMENT '注册时间', 
   CHANGE `state` `state` VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '0：停用 1：在用',
   CHANGE `jhi_type` `jhi_type` VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL comment '数据采集来源 0：繁星';

ALTER TABLE `task_info` 
   CHANGE `min_task` `min_task` INT(11) NOT NULL COMMENT '任务数', 
   CHANGE `max_task` `max_task` INT(11) NOT NULL COMMENT '目标数', 
   CHANGE `cur_month` `cur_month` INT(11) NOT NULL COMMENT '月份',
   ADD UNIQUE KEY(`user_info_id`, `cur_month`);
   
ALTER TABLE `work_info` 
   CHANGE `star_id` `star_id` BIGINT(20) NOT NULL COMMENT '繁星id', 
   CHANGE `star_level` `star_level` INT(11) NULL  COMMENT '繁星等级', 
   CHANGE `star_name` `star_name` varchar(20) character set utf8 collate utf8_general_ci NULL  comment '繁星等级名称',    
   CHANGE `rich_level` `rich_level` INT(11) NULL  COMMENT '财富等级', 
   CHANGE `rich_name` `rich_name` varchar(20) character set utf8 collate utf8_general_ci NULL  comment '财富等级名称',      
   CHANGE `fisrt_bean` `fisrt_bean` FLOAT(20,2) NULL  COMMENT '当天初始星豆数', 
   CHANGE `bean_total` `bean_total` FLOAT(20,2) NULL  COMMENT '星豆总数', 
   CHANGE `coin` `coin` FLOAT(20,2) NULL  COMMENT '星币数', 
   CHANGE `coin_total` `coin_total` FLOAT(20,2) NULL  COMMENT '星币总数', 
   CHANGE `fans_count` `fans_count` INT(11) NULL  COMMENT '被关注数', 
   CHANGE `follow_count` `follow_count` INT(11) NULL  COMMENT '关注数', 
   CHANGE `experience` `experience` FLOAT(20,2) NULL  COMMENT '经验值', 
   CHANGE `work_time` `work_time` INT(11) NULL  COMMENT '工作时长', 
   CHANGE `cur_month` `cur_month` INT(6) NOT NULL COMMENT '当前月份', 
   CHANGE `cur_day` `cur_day` DATE NOT NULL COMMENT '当前天', 
   CHANGE `last_time` `last_time` TIMESTAMP NOT NULL COMMENT '最后更新时间',
   ADD UNIQUE KEY(`star_id`, `cur_day`);
