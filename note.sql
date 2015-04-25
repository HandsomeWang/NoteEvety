/*
Navicat MySQL Data Transfer

Source Server         : wang
Source Server Version : 50521
Source Host           : localhost:3306
Source Database       : note

Target Server Type    : MYSQL
Target Server Version : 50521
File Encoding         : 65001

Date: 2014-12-03 21:56:54
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `note`
-- ----------------------------
DROP TABLE IF EXISTS `note`;
CREATE TABLE `note` (
  `nid` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL,
  `ndate` datetime NOT NULL,
  `ncontent` varchar(200) NOT NULL,
  PRIMARY KEY (`nid`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of note
-- ----------------------------

INSERT INTO `note` VALUES ('4', '1', '2014-11-03 14:06:54', '晚上参加晚会');
INSERT INTO `note` VALUES ('6', '1', '2014-11-10 14:07:32', '按时睡觉！');
INSERT INTO `note` VALUES ('45', '1', '2014-12-03 21:55:14', '这是一条新的笔记……');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `uname` varchar(20) NOT NULL,
  `pwd` varchar(20) NOT NULL,
  PRIMARY KEY (`uid`),
  UNIQUE KEY `uname` (`uname`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'a', 'a');
INSERT INTO `user` VALUES ('2', 'b', 'b');

