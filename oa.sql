/*
Navicat MySQL Data Transfer

Source Server         : DB
Source Server Version : 50527
Source Host           : localhost:3306
Source Database       : oa

Target Server Type    : MYSQL
Target Server Version : 50527
File Encoding         : 65001

Date: 2020-02-13 17:02:53
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for claim_voucher
-- ----------------------------
DROP TABLE IF EXISTS `claim_voucher`;
CREATE TABLE `claim_voucher` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cause` varchar(100) DEFAULT NULL,
  `create_sn` char(5) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `next_deal_sn` char(5) DEFAULT NULL,
  `total_amount` double DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Reference_2` (`next_deal_sn`),
  KEY `FK_Reference_3` (`create_sn`),
  CONSTRAINT `FK_Reference_2` FOREIGN KEY (`next_deal_sn`) REFERENCES `employee` (`sn`),
  CONSTRAINT `FK_Reference_3` FOREIGN KEY (`create_sn`) REFERENCES `employee` (`sn`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of claim_voucher
-- ----------------------------
INSERT INTO `claim_voucher` VALUES ('8', '1', '0333', '2020-02-02 10:28:18', '10003', '52641', '以提交');
INSERT INTO `claim_voucher` VALUES ('9', '测试事由', '0333', '2020-02-02 10:58:06', '10003', '530', '已提交');
INSERT INTO `claim_voucher` VALUES ('10', '最终员工报销单1', '0333', '2020-02-02 22:08:11', '10001', '500', '已审核');
INSERT INTO `claim_voucher` VALUES ('11', '最终员工报销单大于5000', '0333', '2020-02-02 22:09:23', null, '6000', '已打款');

-- ----------------------------
-- Table structure for claim_voucher_item
-- ----------------------------
DROP TABLE IF EXISTS `claim_voucher_item`;
CREATE TABLE `claim_voucher_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `claim_voucher_id` int(11) DEFAULT NULL,
  `item` varchar(20) DEFAULT NULL,
  `amount` double DEFAULT NULL,
  `comment` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Reference_4` (`claim_voucher_id`),
  CONSTRAINT `FK_Reference_4` FOREIGN KEY (`claim_voucher_id`) REFERENCES `claim_voucher` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of claim_voucher_item
-- ----------------------------
INSERT INTO `claim_voucher_item` VALUES ('9', '8', '交通', '52641', '');
INSERT INTO `claim_voucher_item` VALUES ('10', '9', '交通', '200', '嗯');
INSERT INTO `claim_voucher_item` VALUES ('11', '9', '餐饮', '330', '嗯2');
INSERT INTO `claim_voucher_item` VALUES ('12', '10', '交通', '500', '测试');
INSERT INTO `claim_voucher_item` VALUES ('13', '11', '交通', '6000', '');

-- ----------------------------
-- Table structure for deal_record
-- ----------------------------
DROP TABLE IF EXISTS `deal_record`;
CREATE TABLE `deal_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `claim_voucher_id` int(11) DEFAULT NULL,
  `deal_sn` char(5) DEFAULT NULL,
  `deal_time` datetime DEFAULT NULL,
  `deal_way` varchar(20) DEFAULT NULL,
  `deal_result` varchar(20) DEFAULT NULL,
  `comment` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Reference_5` (`claim_voucher_id`),
  KEY `FK_Reference_6` (`deal_sn`),
  CONSTRAINT `FK_Reference_5` FOREIGN KEY (`claim_voucher_id`) REFERENCES `claim_voucher` (`id`),
  CONSTRAINT `FK_Reference_6` FOREIGN KEY (`deal_sn`) REFERENCES `employee` (`sn`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of deal_record
-- ----------------------------
INSERT INTO `deal_record` VALUES ('3', '9', '0333', '2020-02-02 10:58:09', '提交', '已提交', '无');
INSERT INTO `deal_record` VALUES ('4', '10', '0333', '2020-02-02 22:09:25', '提交', '已提交', '无');
INSERT INTO `deal_record` VALUES ('5', '11', '0333', '2020-02-02 22:09:27', '提交', '已提交', '无');
INSERT INTO `deal_record` VALUES ('6', '10', '10003', '2020-02-02 22:11:13', '打回', '已打回', '');
INSERT INTO `deal_record` VALUES ('7', '11', '10003', '2020-02-02 22:11:21', '通过', '待复审', '');
INSERT INTO `deal_record` VALUES ('8', '11', '10000', '2020-02-02 22:12:23', '打回', '已打回', '');
INSERT INTO `deal_record` VALUES ('9', '10', '0333', '2020-02-02 22:12:38', '提交', '已提交', '无');
INSERT INTO `deal_record` VALUES ('10', '11', '0333', '2020-02-02 22:12:39', '提交', '已提交', '无');
INSERT INTO `deal_record` VALUES ('11', '11', '10003', '2020-02-02 22:16:10', '通过', '待复审', '');
INSERT INTO `deal_record` VALUES ('12', '10', '10003', '2020-02-02 22:16:14', '通过', '已审核', '');
INSERT INTO `deal_record` VALUES ('13', '11', '10000', '2020-02-02 22:17:08', '通过', '已审核', '');
INSERT INTO `deal_record` VALUES ('14', '11', '10001', '2020-02-02 22:17:25', '打款', '已打款', '');

-- ----------------------------
-- Table structure for department
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department` (
  `sn` char(5) NOT NULL,
  `name` varchar(20) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`sn`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of department
-- ----------------------------
INSERT INTO `department` VALUES ('10001', '总经理办公室', '星星大厦E座1201');
INSERT INTO `department` VALUES ('10002', '财务部', '星星大厦E座1202');
INSERT INTO `department` VALUES ('10003', '事业部', '星星大厦E座1101');

-- ----------------------------
-- Table structure for employee
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee` (
  `sn` char(5) NOT NULL,
  `password` varchar(20) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `department_sn` char(5) DEFAULT NULL,
  `post` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`sn`),
  KEY `FK_Reference_1` (`department_sn`),
  CONSTRAINT `FK_Reference_1` FOREIGN KEY (`department_sn`) REFERENCES `department` (`sn`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of employee
-- ----------------------------
INSERT INTO `employee` VALUES ('0333', 'songzijun', '员工001', '10003', '员工');
INSERT INTO `employee` VALUES ('10000', '000000', '总经理001', '10003', '总经理');
INSERT INTO `employee` VALUES ('10001', '000000', '财务001', '10002', '财务');
INSERT INTO `employee` VALUES ('10003', '000000', '部门经理001', '10003', '部门经理');
