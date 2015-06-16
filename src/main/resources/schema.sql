-- 企业ID映射表
DROP TABLE IF EXISTS t_idmap;
CREATE TABLE t_idmap (
  id                BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
  name              VARCHAR(30)  COMMENT '企业名',
  link              VARCHAR(150)  COMMENT '查询链接',
  created_time      DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  updated_time      DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT '企业ID映射表';
ALTER TABLE t_idmap ADD INDEX idname (name);

-- 重做表
DROP TABLE IF EXISTS t_redo;
CREATE TABLE t_redo (
  id                BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
  name              VARCHAR(30)  COMMENT '企业名',
  created_time      DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  updated_time      DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT '重做表';
ALTER TABLE t_redo ADD INDEX iname (name);

-- 企业搜索表
DROP TABLE IF EXISTS t_scompany;
CREATE TABLE t_scompany (
  id                BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
  result_type       INTEGER COMMENT '爬取结果分类 1:正常, 2:吊销, 3:不存在',
  company_name      VARCHAR(30) COMMENT '企业名称',
  taxno             VARCHAR(30) COMMENT '注册号',
  law_person        VARCHAR(10) COMMENT '法定代表人',
  reg_date          DATETIME COMMENT '成立日期',
  location          VARCHAR(80) COMMENT '住所',
  business          VARCHAR(100) COMMENT '经营范围',
  stockholder       VARCHAR(100) COMMENT '股东/发起人',
  detail            VARCHAR(300) COMMENT '具体经营项目',
  illegal           VARCHAR(20) COMMENT '是否有违法',
  penalty           VARCHAR(20) COMMENT '是否有行政处罚',
  exception         VARCHAR(20) COMMENT '是否经常异常',
  status            VARCHAR(10) COMMENT '登记状态',
  link              VARCHAR(120) COMMENT '链接',
  created_time      DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  updated_time      DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT '企业搜索表';
ALTER TABLE t_scompany ADD INDEX idname (company_name);
