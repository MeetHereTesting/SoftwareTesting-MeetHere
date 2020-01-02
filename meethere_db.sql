/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 80013
Source Host           : localhost:3306
Source Database       : meethere_db

Target Server Type    : MYSQL
Target Server Version : 80013
File Encoding         : 65001

Date: 2020-01-02 22:39:17
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `messageID` int(11) NOT NULL AUTO_INCREMENT,
  `state` int(11) DEFAULT NULL,
  `userID` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `content` varchar(5000) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  PRIMARY KEY (`messageID`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES ('2', '2', 'test', 'sssssssss', '2019-12-25 17:52:12');
INSERT INTO `message` VALUES ('3', '2', 'test', 'test3', '2019-12-22 18:29:10');
INSERT INTO `message` VALUES ('7', '3', 'test', 'this is a leave message', '2019-12-27 22:28:19');
INSERT INTO `message` VALUES ('9', '3', 'test', 'this is a new message', '2019-12-31 15:19:24');
INSERT INTO `message` VALUES ('10', '3', 'test', 'this is a new message', '2019-12-31 15:19:24');
INSERT INTO `message` VALUES ('15', '2', 'test', 'this is a new message', '2019-12-31 15:39:56');
INSERT INTO `message` VALUES ('16', '2', 'test', 'this is a new message', '2019-12-31 15:41:00');
INSERT INTO `message` VALUES ('18', '2', 'test11', '没什么想分享的', '2020-01-02 17:42:36');
INSERT INTO `message` VALUES ('19', '2', 'yonghuming', '分享点啥呢', '2020-01-02 17:48:04');
INSERT INTO `message` VALUES ('20', '2', 'yonghuming', '场馆2还有时间能被预约吗', '2020-01-02 17:52:42');
INSERT INTO `message` VALUES ('24', '2', 'yonghu', '场馆怎么预约', '2020-01-02 18:16:38');

-- ----------------------------
-- Table structure for news
-- ----------------------------
DROP TABLE IF EXISTS `news`;
CREATE TABLE `news` (
  `newsID` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `content` varchar(5000) DEFAULT NULL,
  `time` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`newsID`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of news
-- ----------------------------
INSERT INTO `news` VALUES ('12', '关于ECNU公共体育俱乐部所有室内培训课程', '根据闵行校区体育馆物业办通知，因麻疹疾控需要，体育馆近期限制进出，故11月23日ECNU公共体育俱乐部所有室内培训课程暂停，积分不会因此产生变动，恢复日期另行通知。天气逐渐转寒，请各位同学运动后及时保暖，寝室多开窗门，保持空气流通。', '2019-12-17 17:15:26.000000');
INSERT INTO `news` VALUES ('13', '健步走安全提示', '健步走安全提示：\r\n      根据本学期至今高校体育后台数据显示，部分同学在23：00至01：00深夜时间段任进行健步走运动，考虑到深夜时段闵行、中北两校区皆存在局部路段光线过暗、路面不平整等安全隐患，现进行如下变动：\r\n      华东师范大学闵行、中北两校区健步走运动于晚22：00后均视为无效运动，请各位同学在该时间点之前完成健步走。', '2019-11-11 17:15:42.000000');
INSERT INTO `news` VALUES ('14', '关于暂停闵行校区第14周体质测试的通知', '关于暂停闵行校区第14周体质测试的通知\r\n \r\n各位同学们好：\r\n由于特殊原因，学院所有场馆本周暂停对外开放，因此本周周一、周三无法进行体质测试。由于事出突然，给各位同学带来了不便，还请见谅。我们会线上取消各位的预约，还请本周预约的同学后面重新进行预约测试。\r\n谢谢配合！', '2019-12-02 17:17:35.000000');

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `orderID` int(11) NOT NULL AUTO_INCREMENT,
  `userID` varchar(25) NOT NULL,
  `venueID` int(11) NOT NULL,
  `order_time` datetime DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `hours` int(2) DEFAULT NULL,
  `state` int(1) DEFAULT NULL,
  `total` int(5) DEFAULT NULL,
  PRIMARY KEY (`orderID`),
  KEY `userID` (`userID`),
  KEY `gymID` (`venueID`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of order
-- ----------------------------
INSERT INTO `order` VALUES ('1', 'test', '2', '2020-01-02 17:24:01', '2020-01-20 15:00:00', '3', '2', '600');
INSERT INTO `order` VALUES ('12', 'test', '16', '2020-01-02 17:23:46', '2020-01-17 12:00:00', '5', '2', '2500');
INSERT INTO `order` VALUES ('13', 'test', '18', '2020-01-02 17:23:35', '2020-01-08 15:00:00', '4', '2', '4000');
INSERT INTO `order` VALUES ('14', 'test', '2', '2020-01-02 17:40:12', '2020-01-17 09:00:00', '3', '2', '600');
INSERT INTO `order` VALUES ('15', 'test11', '16', '2020-01-02 17:42:02', '2020-01-14 09:00:00', '5', '4', '2500');
INSERT INTO `order` VALUES ('16', 'test11', '21', '2020-01-02 17:42:19', '2020-01-24 14:00:00', '5', '2', '3500');
INSERT INTO `order` VALUES ('19', 'yonghuming', '16', '2020-01-02 17:51:47', '2020-01-22 10:00:00', '6', '4', '3000');
INSERT INTO `order` VALUES ('20', 'yonghuming', '18', '2020-01-02 17:52:04', '2020-01-27 14:00:00', '3', '2', '3000');
INSERT INTO `order` VALUES ('21', 'yonghuming', '16', '2020-01-02 17:52:21', '2020-01-18 11:00:00', '4', '2', '2000');
INSERT INTO `order` VALUES ('29', 'yonghu', '16', '2020-01-02 18:16:08', '2020-01-24 11:00:00', '3', '2', '1500');
INSERT INTO `order` VALUES ('30', 'yonghu', '17', '2020-01-02 18:16:21', '2020-01-25 11:00:00', '3', '2', '900');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `userID` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `isadmin` int(10) NOT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `picture` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'test', 'test', '', '', '0', 'test', '');
INSERT INTO `user` VALUES ('8', 'admin', 'admin', '', '', '1', '111', '');
INSERT INTO `user` VALUES ('18', 'test1', '123', '', '', '0', 'test1', '');
INSERT INTO `user` VALUES ('19', 'name', '123', '', '', '0', 'name', '');
INSERT INTO `user` VALUES ('20', 'test11', 'test', '', '', '0', '555', '');
INSERT INTO `user` VALUES ('22', 'yonghuming', 'mimamima', '', '', '0', '5555', '');
INSERT INTO `user` VALUES ('27', 'yonghu', 'mimamima', '', '', '0', '1234', '');

-- ----------------------------
-- Table structure for venue
-- ----------------------------
DROP TABLE IF EXISTS `venue`;
CREATE TABLE `venue` (
  `venueID` int(5) NOT NULL AUTO_INCREMENT,
  `description` varchar(1000) DEFAULT NULL,
  `price` int(5) DEFAULT NULL,
  `picture` varchar(255) DEFAULT NULL,
  `venue_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `close_time` varchar(255) DEFAULT NULL,
  `open_time` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`venueID`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of venue
-- ----------------------------
INSERT INTO `venue` VALUES ('2', ' 该场馆是是中国华东地区举办体育项目的专业平台，是保障正常教学、训练和学校大型活动的顺利进行，服务师生健身和文体娱乐的服务性设施。运动条件优越，能承担各种大型的运动比赛。', '200', '', '2222', '上海市普陀区中山北路3664号', '20:00', '09:00 ');
INSERT INTO `venue` VALUES ('16', ' 可根据使用需求进行多种布置，舞台、主席台可灵活设置，具有很高的综合使用性能。馆内日常布置1片标准篮球场，14片羽毛球场，8张乒乓球桌。体育馆共设有固定座位6785座，其中一楼960座，二楼看台2437座，三楼3388座。\r\n\r\n   体育馆穹顶采用轮辐式张拉梁结构，屋盖跨度100米，馆内配备有专用运动木地板、中央空调、高清LED大屏、视频监控、自动消防系统，灯光音响的设置标准满 足比赛现场直播的要求，体育馆荣获“中国钢结构金刚奖”和“建设工程鲁班奖”两个建设工程领域国家级奖项。', '500', '', '场馆2', '上海市黄浦区', '18:00', '09:00 ');
INSERT INTO `venue` VALUES ('17', ' 体育馆占地面积达13900平方米，总建筑面积达23950平方米，地下1层，地上1-3层，最大高度达27.87米。体育馆的切平面是一个82.4米*96米的矩形，整个屋面外形为独特的反对称折面，采用门式钢架结构，跨度为82.4米。主馆比赛场地为南北70米，东西40米，并设计座席8724个（其中固定座席6051个，活动座席2673个），室内空间非常宽敞。馆顶安装了400多块高低错落的玻璃窗，利用自然采光原理，按照太阳运行规律，使主体馆内形成了效果极佳的自然采光和通风效果。副馆部分由羽毛球场地和数个功能房间构成，馆内设置了2套空调系统，运用热回收空调技术，室内空气净化技术，空调采暖加湿、除湿和控制技术，直燃型溴化锂机组等多项科技创新技术的绿色高效节能空调。另外馆内淋浴使用的是地下温泉热水。场馆内部各功能分区流线清晰，比赛场区声控、灯光设备齐全，处于国际先进水平，馆内外空间富裕。\r\n\r\n     体育馆自投入使用以来，坚持科学管理、优质服务，成功举办了好运北京、奥运会、残奥会赛事，得到了国际奥委会赞许，被誉为是奥运会摔跤项目有史以来最完美的一届。同时也得到了全校师生及社会各界的热心关注和大力支持，各项工作有序进行，为学校的体育教学、艺术团体训练、文化展览、各类大型活动以体育比赛的开展发挥着积极的作用。    ', '300', '', '场馆3', '上海市松江区', '17:00', '09:00 ');
INSERT INTO `venue` VALUES ('18', ' 可根据使用需求进行多种布置，舞台、主席台可灵活设置，具有很高的综合使用性能。馆内日常布置1片标准篮球场，14片羽毛球场，8张乒乓球桌。体育馆共设有固定座位6785座，其中一楼960座，二楼看台2437座，三楼3388座。\r\n\r\n   体育馆穹顶采用轮辐式张拉梁结构，屋盖跨度100米，馆内配备有专用运动木地板、中央空调、高清LED大屏、视频监控、自动消防系统，灯光音响的设置标准满 足比赛现场直播的要求，体育馆荣获“中国钢结构金刚奖”和“建设工程鲁班奖”两个建设工程领域国家级奖项。', '1000', '', '场馆4', '上海市静安区', '20:00', '09:00 ');
INSERT INTO `venue` VALUES ('20', '体育中心占地面积 30.87万平方米，总建筑面积23.83万平方米，由两场两馆组成（即体育场，网球场，体育馆，游泳场馆）； 下设综合管理部、游泳场馆管理部、体育馆管理部、体育场管理部，等四个部门。', '800', '', '场馆5', '上海市闵行区', '22:00', '08:00 ');
INSERT INTO `venue` VALUES ('21', ' 综合训练馆是体育教学、运动训练基地，总建筑面积1.2万平方米，高度23米，屋盖为钢结构网架。室内运动 场地面积约1万平方米，馆内场地可根据使用需求进行多种布置，具有很高的综合使用性能。馆内主运动场地长153米，宽56米，日常布置3片配备专业运动木 地板的篮球场、2片塑胶五人制足球场，21片塑胶地面羽毛球场，40张乒乓球桌。馆内其他功能房间还设有1个健身室，1个体操健美操室、2个壁球室，8个 室内高尔夫教学间、1个瑜伽室、1个体育教室和多间教学办公辅助用房。', '700', '', '场馆6', '上海市浦东新区', '20:00', '08:00 ');
