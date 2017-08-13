ALTER TABLE `user_info` 
   CHANGE `user_name` `user_name` VARCHAR(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '����', 
   CHANGE `nick_name` `nick_name` VARCHAR(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL  COMMENT '����', 
   CHANGE `phone_number` `phone_number` bigint(20) NULL  comment '�ֻ���', 
   CHANGE `qq` `qq` bigint(20) NULL  comment 'QQ��', 
   CHANGE `wei_chat` `wei_chat` varchar(50) character set utf8 collate utf8_general_ci NULL  comment '΢�ź�',
   CHANGE `star_id` `star_id` BIGINT(20) NOT NULL COMMENT '����id', 
   CHANGE `reg_date` `reg_date` DATE NOT NULL COMMENT 'ע��ʱ��', 
   CHANGE `login_name` `login_name` VARCHAR(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '���ǵ�¼��',
   CHANGE `jhi_password` `jhi_password` VARCHAR(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '���ǵ�¼����', 
   CHANGE `cookie` `cookie` VARCHAR(10480) CHARACTER SET utf8 COLLATE utf8_general_ci NULL  COMMENT '��¼���cookie��Ϣ����Ҫ�˹�����ά��', 
   CHANGE `time_rate` `time_rate` FLOAT(2,1) DEFAULT '1.0' NOT NULL COMMENT '���ڱ���', 
   CHANGE `bean_rate` `bean_rate` FLOAT(2,1) DEFAULT '1.0' NOT NULL COMMENT '�Ƕ�����', 
   CHANGE `last_maintain` `last_maintain` date NULL  comment '�ϴ�һά��cookieʱ��',  
   CHANGE `state` `state` VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '0��ͣ�� 1������';
   
ALTER TABLE `labor_union` 
   CHANGE `l_id` `l_id` INT(11) NOT NULL COMMENT '����id', 
   CHANGE `name` `name` VARCHAR(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '��������', 
   CHANGE `reg_date` `reg_date` DATE NOT NULL COMMENT 'ע��ʱ��', 
   CHANGE `state` `state` VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '0��ͣ�� 1������',
   CHANGE `jhi_type` `jhi_type` VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL comment '���ݲɼ���Դ 0������';

ALTER TABLE `task_info` 
   CHANGE `min_task` `min_task` INT(11) NOT NULL COMMENT '������', 
   CHANGE `max_task` `max_task` INT(11) NOT NULL COMMENT 'Ŀ����', 
   CHANGE `cur_month` `cur_month` INT(11) NOT NULL COMMENT '�·�';
   
ALTER TABLE `work_info` 
   CHANGE `star_id` `star_id` BIGINT(20) NOT NULL COMMENT '����id', 
   CHANGE `star_level` `star_level` INT(11) NULL  COMMENT '���ǵȼ�', 
   CHANGE `star_name` `star_name` varchar(20) character set utf8 collate utf8_general_ci NULL  comment '���ǵȼ�����',    
   CHANGE `rich_level` `rich_level` INT(11) NULL  COMMENT '�Ƹ��ȼ�', 
   CHANGE `rich_name` `rich_name` varchar(20) character set utf8 collate utf8_general_ci NULL  comment '�Ƹ��ȼ�����',      
   CHANGE `fisrt_bean` `fisrt_bean` FLOAT(20,2) NULL  COMMENT '�����ʼ�Ƕ���', 
   CHANGE `bean_total` `bean_total` FLOAT(20,2) NULL  COMMENT '�Ƕ�����', 
   CHANGE `coin` `coin` FLOAT(20,2) NULL  COMMENT '�Ǳ���', 
   CHANGE `coin_total` `coin_total` FLOAT(20,2) NULL  COMMENT '�Ǳ�����', 
   CHANGE `fans_count` `fans_count` INT(11) NULL  COMMENT '����ע��', 
   CHANGE `follow_count` `follow_count` INT(11) NULL  COMMENT '��ע��', 
   CHANGE `experience` `experience` FLOAT(20,2) NULL  COMMENT '����ֵ', 
   CHANGE `work_time` `work_time` INT(11) NULL  COMMENT '����ʱ��', 
   CHANGE `cur_month` `cur_month` INT(6) NOT NULL COMMENT '��ǰ�·�', 
   CHANGE `cur_day` `cur_day` DATE NOT NULL COMMENT '��ǰ��', 
   CHANGE `last_time` `last_time` TIMESTAMP NOT NULL COMMENT '������ʱ��',
   ADD UNIQUE KEY(`star_id`, `cur_day`);
