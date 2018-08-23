/*
Navicat MySQL Data Transfer

Source Server         : sunyalong
Source Server Version : 50549
Source Host           : 192.168.1.102:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50549
File Encoding         : 65001

Date: 2018-08-23 23:50:00
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_hehe
-- ----------------------------
DROP TABLE IF EXISTS `t_hehe`;
CREATE TABLE `t_hehe` (
  `tid` int(11) NOT NULL AUTO_INCREMENT,
  `year` int(11) DEFAULT NULL,
  `month` int(11) DEFAULT NULL,
  `avg` int(11) DEFAULT NULL,
  PRIMARY KEY (`tid`)
) ENGINE=InnoDB AUTO_INCREMENT=1001 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_hehe
-- ----------------------------
INSERT INTO `t_hehe` VALUES ('1', '0', '1', '100');
INSERT INTO `t_hehe` VALUES ('2', '2017', '1', '200');
INSERT INTO `t_hehe` VALUES ('3', '2017', '1', '200');
INSERT INTO `t_hehe` VALUES ('4', '2017', '1', '10');
INSERT INTO `t_hehe` VALUES ('5', '2017', '1', '200');
INSERT INTO `t_hehe` VALUES ('6', '2017', '1', '100');
INSERT INTO `t_hehe` VALUES ('7', '2017', '1', '200');
INSERT INTO `t_hehe` VALUES ('1000', '201888', '3', '12');
