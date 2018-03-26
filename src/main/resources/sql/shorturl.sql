/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : shorturl

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2018-03-26 17:39:57
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for shorturl
-- ----------------------------
DROP TABLE IF EXISTS `shorturl`;
CREATE TABLE `shorturl` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `short_code` varchar(255) NOT NULL,
  `org_url` varchar(255) NOT NULL,
  `create_time` varchar(255) NOT NULL,
  `update_time` varchar(255) NOT NULL,
  `click_times` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of shorturl
-- ----------------------------
