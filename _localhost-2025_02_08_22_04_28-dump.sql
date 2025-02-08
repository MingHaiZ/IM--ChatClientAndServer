-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: easychat
-- ------------------------------------------------------
-- Server version	8.0.34

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `app_update`
--

DROP TABLE IF EXISTS `app_update`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `app_update` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `version` varchar(10) DEFAULT NULL COMMENT '版本号',
  `update_desc` varchar(500) DEFAULT NULL COMMENT '更新描述',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `status` tinyint DEFAULT NULL COMMENT '0: 未发布 1: 灰度发布 2: 全网发布',
  `grayscale_uid` longtext COMMENT '灰度uid',
  `file_type` tinyint DEFAULT NULL COMMENT '文件类型 0:本地文件 1:外链文件',
  `outer_link` varchar(200) DEFAULT NULL COMMENT '外链地址',
  PRIMARY KEY (`id`),
  UNIQUE KEY `app_update_version_uindex` (`version`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='app发布';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `app_update`
--

LOCK TABLES `app_update` WRITE;
/*!40000 ALTER TABLE `app_update` DISABLE KEYS */;
/*!40000 ALTER TABLE `app_update` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chat_message`
--

DROP TABLE IF EXISTS `chat_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chat_message` (
  `message_id` bigint NOT NULL AUTO_INCREMENT COMMENT '消息自增ID',
  `session_id` varchar(32) NOT NULL COMMENT '会话id',
  `message_type` tinyint NOT NULL COMMENT '消息类型',
  `message_content` varchar(500) DEFAULT NULL COMMENT '消息内容',
  `send_user_id` varchar(12) DEFAULT NULL COMMENT '发送人id',
  `send_user_nick_name` varchar(20) DEFAULT NULL COMMENT '发送人昵称',
  `send_time` bigint DEFAULT NULL COMMENT '发送时间',
  `contact_id` varchar(12) NOT NULL COMMENT '接收联系人id',
  `contact_type` tinyint DEFAULT NULL COMMENT '联系人类型 0:单聊 1:群聊',
  `file_size` bigint DEFAULT NULL COMMENT '文件大小',
  `file_name` varchar(50) DEFAULT NULL COMMENT '文件名',
  `file_type` tinyint DEFAULT NULL COMMENT '文件类型',
  `status` tinyint DEFAULT NULL COMMENT '状态 0:正在发送 1:已发送',
  PRIMARY KEY (`message_id`),
  KEY `chat_message_contact_id_index` (`contact_id`) COMMENT '接收人id',
  KEY `chat_message_send_time_index` (`send_time`),
  KEY `chat_message_send_user_id_index` (`send_user_id`),
  KEY `chat_message_session_id_index` (`session_id`)
) ENGINE=InnoDB AUTO_INCREMENT=223 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='聊天消息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chat_message`
--

LOCK TABLES `chat_message` WRITE;
/*!40000 ALTER TABLE `chat_message` DISABLE KEYS */;
INSERT INTO `chat_message` VALUES (2,'a9dc4b8652b74c01c7b0f817a4690f67',2,'欢迎使用Easychat','Urobot','EasyChat',1738750068375,'U33333333333',0,NULL,NULL,NULL,1),(3,'22d82a2d9e14ab2e50e544058eafa9b0',2,'欢迎使用Easychat','Urobot','EasyChat',1738836734333,'U11111111111',0,NULL,NULL,NULL,1),(4,'ce8ede326c3fc9f6e147753b6e4ee3c7',1,'我是test1','U11111111111','MingHaiTest',1738845217010,'U33333333333',0,NULL,NULL,NULL,NULL),(5,'ce8ede326c3fc9f6e147753b6e4ee3c7',1,'我是test1','U11111111111','MingHaiTest',1738845405945,'U33333333333',0,NULL,NULL,NULL,NULL),(6,'ce8ede326c3fc9f6e147753b6e4ee3c7',1,'我是MingHaiTest','U33333333333','test1',1738845721515,'U11111111111',0,NULL,NULL,NULL,NULL),(7,'ce8ede326c3fc9f6e147753b6e4ee3c7',1,'我是test1','U11111111111','MingHaiTest',1738845885064,'U33333333333',0,NULL,NULL,NULL,NULL),(8,'ce8ede326c3fc9f6e147753b6e4ee3c7',1,'我是test1','U11111111111','test1',1738846189944,'U33333333333',0,NULL,NULL,NULL,NULL),(9,'e561759cf1c6e9790e07014b32ada30f',3,'群组已经创建好,可以和好友一起畅聊了!','U33333333333','测试群聊',1738910730731,'G97704533557',1,NULL,NULL,NULL,1),(10,'5dccf7b37d464e94a8b8820a6425c9ee',3,'群组已经创建好,可以和好友一起畅聊了!','U33333333333','测试2',1738911059218,'G78620679228',1,NULL,NULL,NULL,1),(11,'5526dc7a63400529681c83a37a387f9f',3,'群组已经创建好,可以和好友一起畅聊了!','U33333333333','测试3',1738911164148,'G22249753780',1,NULL,NULL,NULL,1),(12,'fa2b8b266ba041e76fe5e4e09d06a55e',3,'群组已经创建好,可以和好友一起畅聊了!','U33333333333','测试04',1738911189137,'G58256176322',1,NULL,NULL,NULL,1),(13,'a35d0661095dd35f55ade9332145a42e',3,'群组已经创建好,可以和好友一起畅聊了!',NULL,NULL,1738912059397,'G81959705026',1,NULL,NULL,NULL,1),(14,'e561759cf1c6e9790e07014b32ada30f',9,'test1加入了群组',NULL,NULL,1738917531965,'G97704533557',1,NULL,NULL,NULL,1),(15,'a35d0661095dd35f55ade9332145a42e',9,'test1加入了群组',NULL,NULL,1738917700270,'G81959705026',1,NULL,NULL,NULL,1),(16,'b9e651a59d62ccb8125eb661b65b7f07',3,'群组已经创建好,可以和好友一起畅聊了!',NULL,NULL,1738919434140,'G42069377180',1,NULL,NULL,NULL,1),(17,'b9e651a59d62ccb8125eb661b65b7f07',9,'test1加入了群组',NULL,NULL,1738919623572,'G42069377180',1,NULL,NULL,NULL,1),(18,'fa2b8b266ba041e76fe5e4e09d06a55e',9,'test1加入了群组',NULL,NULL,1738919938448,'G58256176322',1,NULL,NULL,NULL,1),(19,'5526dc7a63400529681c83a37a387f9f',9,'test1加入了群组',NULL,NULL,1738919963601,'G22249753780',1,NULL,NULL,NULL,1),(20,'5dccf7b37d464e94a8b8820a6425c9ee',9,'test1加入了群组',NULL,NULL,1738920191180,'G78620679228',1,NULL,NULL,NULL,1),(23,'a9dc4b8652b74c01c7b0f817a4690f67',2,'1','U33333333333','MingHaiTest',1738940455761,'Urobot',0,NULL,'',NULL,1),(24,'a9dc4b8652b74c01c7b0f817a4690f67',2,'我只是一个机器人无法识别你的消息','Urobot','EasyChat',1738940455789,'U33333333333',0,NULL,NULL,NULL,1),(25,'a9dc4b8652b74c01c7b0f817a4690f67',2,'1','U33333333333','MingHaiTest',1738940458959,'Urobot',0,NULL,'',NULL,1),(26,'a9dc4b8652b74c01c7b0f817a4690f67',2,'我只是一个机器人无法识别你的消息','Urobot','EasyChat',1738940458971,'U33333333333',0,NULL,NULL,NULL,1),(27,'a9dc4b8652b74c01c7b0f817a4690f67',2,'1','U33333333333','MingHaiTest',1738940461283,'Urobot',0,NULL,'',NULL,1),(28,'a9dc4b8652b74c01c7b0f817a4690f67',2,'我只是一个机器人无法识别你的消息','Urobot','EasyChat',1738940461290,'U33333333333',0,NULL,NULL,NULL,1),(29,'22d82a2d9e14ab2e50e544058eafa9b0',2,'1','U11111111111','test01',1738940514606,'Urobot',0,NULL,'',NULL,1),(30,'22d82a2d9e14ab2e50e544058eafa9b0',2,'我只是一个机器人无法识别你的消息','Urobot','EasyChat',1738940514618,'U11111111111',0,NULL,NULL,NULL,1),(31,'ce8ede326c3fc9f6e147753b6e4ee3c7',5,'[图片]','U33333333333','MingHaiTest',1738940530208,'U11111111111',0,4716876,'khl20230622154812904.png',0,0),(32,'ce8ede326c3fc9f6e147753b6e4ee3c7',2,'?','U11111111111','test01',1738940596701,'U33333333333',0,NULL,'',NULL,1),(33,'ce8ede326c3fc9f6e147753b6e4ee3c7',2,'?','U33333333333','MingHaiTest',1738940706043,'U11111111111',0,NULL,'',NULL,1),(34,'5dccf7b37d464e94a8b8820a6425c9ee',2,'?','U33333333333','MingHaiTest',1738940784518,'G78620679228',1,NULL,'',NULL,1),(35,'5dccf7b37d464e94a8b8820a6425c9ee',2,'?','U11111111111','test01',1738940805515,'G78620679228',1,NULL,'',NULL,1),(36,'ce8ede326c3fc9f6e147753b6e4ee3c7',2,'?','U33333333333','MingHaiTest',1738940849116,'U11111111111',0,NULL,'',NULL,1),(37,'ce8ede326c3fc9f6e147753b6e4ee3c7',2,'1','U33333333333','MingHaiTest',1738940906671,'U11111111111',0,NULL,'',NULL,1),(38,'ce8ede326c3fc9f6e147753b6e4ee3c7',2,'hello','U33333333333','MingHaiTest',1738940974024,'U11111111111',0,NULL,'',NULL,1),(39,'ce8ede326c3fc9f6e147753b6e4ee3c7',2,'hello','U33333333333','MingHaiTest',1738941011792,'U11111111111',0,NULL,'',NULL,1),(40,'ce8ede326c3fc9f6e147753b6e4ee3c7',2,'我是奶龙','U33333333333','MingHaiTest',1738941214535,'U11111111111',0,NULL,'',NULL,1),(41,'ce8ede326c3fc9f6e147753b6e4ee3c7',2,'我才是奶龙','U11111111111','test01',1738941227316,'U33333333333',0,NULL,'',NULL,1),(42,'ce8ede326c3fc9f6e147753b6e4ee3c7',2,'你无敌了','U33333333333','MingHaiTest',1738941237700,'U11111111111',0,NULL,'',NULL,1),(43,'ce8ede326c3fc9f6e147753b6e4ee3c7',2,'哈哈','U11111111111','test01',1738941241283,'U33333333333',0,NULL,'',NULL,1),(44,'a9dc4b8652b74c01c7b0f817a4690f67',2,'hello','U33333333333','MingHaiTest',1738942070988,'Urobot',0,NULL,'',NULL,1),(45,'a9dc4b8652b74c01c7b0f817a4690f67',2,'我只是一个机器人无法识别你的消息','Urobot','EasyChat',1738942071563,'U33333333333',0,NULL,NULL,NULL,1),(46,'a9dc4b8652b74c01c7b0f817a4690f67',2,'hello','U33333333333','MingHaiTest',1738942104450,'Urobot',0,NULL,'',NULL,1),(47,'a9dc4b8652b74c01c7b0f817a4690f67',2,'我只是一个机器人无法识别你的消息','Urobot','EasyChat',1738942104894,'U33333333333',0,NULL,NULL,NULL,1),(48,'a9dc4b8652b74c01c7b0f817a4690f67',2,'hello','U33333333333','MingHaiTest',1738942112252,'Urobot',0,NULL,'',NULL,1),(49,'a9dc4b8652b74c01c7b0f817a4690f67',2,'Unauthorized','Urobot','EasyChat',1738942112556,'U33333333333',0,NULL,NULL,NULL,1),(50,'a9dc4b8652b74c01c7b0f817a4690f67',2,'hello','U33333333333','MingHaiTest',1738942140259,'Urobot',0,NULL,'',NULL,1),(51,'a9dc4b8652b74c01c7b0f817a4690f67',2,'Unauthorized','Urobot','EasyChat',1738942140725,'U33333333333',0,NULL,NULL,NULL,1),(52,'a9dc4b8652b74c01c7b0f817a4690f67',2,'hello','U33333333333','MingHaiTest',1738942166638,'Urobot',0,NULL,'',NULL,1),(53,'a9dc4b8652b74c01c7b0f817a4690f67',2,'Unauthorized','Urobot','EasyChat',1738942167069,'U33333333333',0,NULL,NULL,NULL,1),(54,'a9dc4b8652b74c01c7b0f817a4690f67',2,'hello','U33333333333','MingHaiTest',1738942198592,'Urobot',0,NULL,'',NULL,1),(55,'a9dc4b8652b74c01c7b0f817a4690f67',2,'OK','Urobot','EasyChat',1738942199003,'U33333333333',0,NULL,NULL,NULL,1),(56,'a9dc4b8652b74c01c7b0f817a4690f67',2,'hello','U33333333333','MingHaiTest',1738942275328,'Urobot',0,NULL,'',NULL,1),(57,'a9dc4b8652b74c01c7b0f817a4690f67',2,'{\"id\":\"2bd49115-6539-46b9-b237-761900dccb4b\",\"object\":\"chat.completion\",\"created\":1738942277,\"model\":\"deepseek-chat\",\"choices\":[{\"index\":0,\"message\":{\"role\":\"assistant\",\"content\":\"Hello! How can I assist you today? 😊\"},\"logprobs\":null,\"finish_reason\":\"stop\"}],\"usage\":{\"prompt_tokens\":9,\"completion_tokens\":11,\"total_tokens\":20,\"prompt_tokens_details\":{\"cached_tokens\":0},\"prompt_cache_hit_tokens\":0,\"prompt_cache_miss_tokens\":9},\"system_fingerprint\":\"fp_3a5770e1b4\"}','Urobot','EasyChat',1738942281958,'U33333333333',0,NULL,NULL,NULL,1),(58,'a9dc4b8652b74c01c7b0f817a4690f67',2,'你好','U33333333333','MingHaiTest',1738943186248,'Urobot',0,NULL,'',NULL,1),(59,'a9dc4b8652b74c01c7b0f817a4690f67',2,'你好','Urobot','EasyChat',1738943192452,'U33333333333',0,NULL,NULL,NULL,1),(60,'a9dc4b8652b74c01c7b0f817a4690f67',2,'你好','U33333333333','MingHaiTest',1738943279300,'Urobot',0,NULL,'',NULL,1),(61,'a9dc4b8652b74c01c7b0f817a4690f67',2,'你好','Urobot','EasyChat',1738943485639,'U33333333333',0,NULL,NULL,NULL,1),(62,'a9dc4b8652b74c01c7b0f817a4690f67',2,'你好','U33333333333','MingHaiTest',1738943528742,'Urobot',0,NULL,'',NULL,1),(63,'a9dc4b8652b74c01c7b0f817a4690f67',2,'你好！很高兴见到你。有什么我可以帮忙的吗？','Urobot','EasyChat',1738943534091,'U33333333333',0,NULL,NULL,NULL,1),(64,'a9dc4b8652b74c01c7b0f817a4690f67',2,'你好','U33333333333','MingHaiTest',1738943554339,'Urobot',0,NULL,'',NULL,1),(65,'a9dc4b8652b74c01c7b0f817a4690f67',2,'你好！很高兴见到你。有什么我可以帮忙的吗？','Urobot','EasyChat',1738943592052,'U33333333333',0,NULL,NULL,NULL,1),(66,'22d82a2d9e14ab2e50e544058eafa9b0',2,'hello','U11111111111','test01',1738943602147,'Urobot',0,NULL,'',NULL,1),(67,'22d82a2d9e14ab2e50e544058eafa9b0',2,'Hello! How can I assist you today? 😊','Urobot','EasyChat',1738943609562,'U11111111111',0,NULL,NULL,NULL,1),(68,'a9dc4b8652b74c01c7b0f817a4690f67',2,'你好','U33333333333','MingHaiTest',1738943762410,'Urobot',0,NULL,'',NULL,1),(69,'a9dc4b8652b74c01c7b0f817a4690f67',2,'调用服务器失败','Urobot','EasyChat',1738943772839,'U33333333333',0,NULL,NULL,NULL,1),(70,'a9dc4b8652b74c01c7b0f817a4690f67',2,'你好','U33333333333','MingHaiTest',1738943765389,'Urobot',0,NULL,'',NULL,1),(71,'a9dc4b8652b74c01c7b0f817a4690f67',2,'你好！很高兴见到你，有什么我可以帮忙的吗？','Urobot','EasyChat',1738943782118,'U33333333333',0,NULL,NULL,NULL,1),(72,'a9dc4b8652b74c01c7b0f817a4690f67',2,'你好','U33333333333','MingHaiTest',1738943780975,'Urobot',0,NULL,'',NULL,1),(73,'a9dc4b8652b74c01c7b0f817a4690f67',2,'你好！很高兴见到你。有什么我可以帮忙的吗？','Urobot','EasyChat',1738943787676,'U33333333333',0,NULL,NULL,NULL,1),(74,'a9dc4b8652b74c01c7b0f817a4690f67',2,'请给我详细讲解一下Electron','U33333333333','MingHaiTest',1738943802273,'Urobot',0,NULL,'',NULL,1),(75,'a9dc4b8652b74c01c7b0f817a4690f67',2,'调用服务器失败','Urobot','EasyChat',1738943812685,'U33333333333',0,NULL,NULL,NULL,1),(76,'a9dc4b8652b74c01c7b0f817a4690f67',2,'请给我详细讲解一下Electron','U33333333333','MingHaiTest',1738943824438,'Urobot',0,NULL,'',NULL,1),(77,'a9dc4b8652b74c01c7b0f817a4690f67',2,'调用服务器失败','Urobot','EasyChat',1738943834776,'U33333333333',0,NULL,NULL,NULL,1),(78,'a9dc4b8652b74c01c7b0f817a4690f67',2,'请给我详细讲解一下Electron','U33333333333','MingHaiTest',1738943848205,'Urobot',0,NULL,'',NULL,1),(79,'a9dc4b8652b74c01c7b0f817a4690f67',2,'调用服务器失败','Urobot','EasyChat',1738943858589,'U33333333333',0,NULL,NULL,NULL,1),(80,'a9dc4b8652b74c01c7b0f817a4690f67',2,'你好','U33333333333','MingHaiTest',1738943862862,'Urobot',0,NULL,'',NULL,1),(81,'a9dc4b8652b74c01c7b0f817a4690f67',2,'调用服务器失败','Urobot','EasyChat',1738943873235,'U33333333333',0,NULL,NULL,NULL,1),(82,'a9dc4b8652b74c01c7b0f817a4690f67',2,'你好','U33333333333','MingHaiTest',1738943876044,'Urobot',0,NULL,'',NULL,1),(83,'a9dc4b8652b74c01c7b0f817a4690f67',2,'调用服务器失败','Urobot','EasyChat',1738943886403,'U33333333333',0,NULL,NULL,NULL,1),(84,'a9dc4b8652b74c01c7b0f817a4690f67',2,'你好','U33333333333','MingHaiTest',1738943925319,'Urobot',0,NULL,'',NULL,1),(85,'a9dc4b8652b74c01c7b0f817a4690f67',2,'调用服务器失败','Urobot','EasyChat',1738943935740,'U33333333333',0,NULL,NULL,NULL,1),(86,'a9dc4b8652b74c01c7b0f817a4690f67',2,'你好','U33333333333','MingHaiTest',1738943971036,'Urobot',0,NULL,'',NULL,1),(87,'a9dc4b8652b74c01c7b0f817a4690f67',2,'调用服务器失败','Urobot','EasyChat',1738943981551,'U33333333333',0,NULL,NULL,NULL,1),(88,'a9dc4b8652b74c01c7b0f817a4690f67',2,'你好','U33333333333','MingHaiTest',1738944005539,'Urobot',0,NULL,'',NULL,1),(89,'a9dc4b8652b74c01c7b0f817a4690f67',2,'调用服务器失败','Urobot','EasyChat',1738944015950,'U33333333333',0,NULL,NULL,NULL,1),(90,'a9dc4b8652b74c01c7b0f817a4690f67',2,'你好','U33333333333','MingHaiTest',1738944022997,'Urobot',0,NULL,'',NULL,1),(91,'a9dc4b8652b74c01c7b0f817a4690f67',2,'调用服务器失败','Urobot','EasyChat',1738944033369,'U33333333333',0,NULL,NULL,NULL,1),(92,'a9dc4b8652b74c01c7b0f817a4690f67',2,'你好','U33333333333','MingHaiTest',1738944234067,'Urobot',0,NULL,'',NULL,1),(93,'a9dc4b8652b74c01c7b0f817a4690f67',2,'服务器繁忙','Urobot','EasyChat',1738944234425,'U33333333333',0,NULL,NULL,NULL,1),(94,'a9dc4b8652b74c01c7b0f817a4690f67',2,'你好','U33333333333','MingHaiTest',1738944393105,'Urobot',0,NULL,'',NULL,1),(95,'a9dc4b8652b74c01c7b0f817a4690f67',2,'服务器繁忙','Urobot','EasyChat',1738944424965,'U33333333333',0,NULL,NULL,NULL,1),(96,'a9dc4b8652b74c01c7b0f817a4690f67',2,'你好','U33333333333','MingHaiTest',1738944766040,'Urobot',0,NULL,'',NULL,1),(97,'a9dc4b8652b74c01c7b0f817a4690f67',2,'调用服务器失败','Urobot','EasyChat',1738944766557,'U33333333333',0,NULL,NULL,NULL,1),(98,'a9dc4b8652b74c01c7b0f817a4690f67',2,'你好','U33333333333','MingHaiTest',1738944781661,'Urobot',0,NULL,'',NULL,1),(99,'a9dc4b8652b74c01c7b0f817a4690f67',2,'调用服务器失败','Urobot','EasyChat',1738944793542,'U33333333333',0,NULL,NULL,NULL,1),(100,'a9dc4b8652b74c01c7b0f817a4690f67',2,'你好','U33333333333','MingHaiTest',1738944818266,'Urobot',0,NULL,'',NULL,1),(101,'a9dc4b8652b74c01c7b0f817a4690f67',2,'调用服务器失败','Urobot','EasyChat',1738944880910,'U33333333333',0,NULL,NULL,NULL,1),(102,'a9dc4b8652b74c01c7b0f817a4690f67',2,'你好','U33333333333','MingHaiTest',1738944830984,'Urobot',0,NULL,'',NULL,1),(103,'a9dc4b8652b74c01c7b0f817a4690f67',2,'调用服务器失败','Urobot','EasyChat',1738944941044,'U33333333333',0,NULL,NULL,NULL,1),(104,'a9dc4b8652b74c01c7b0f817a4690f67',2,'你好','U33333333333','MingHaiTest',1738945006494,'Urobot',0,NULL,'',NULL,1),(105,'a9dc4b8652b74c01c7b0f817a4690f67',2,'你好！很高兴见到你，有什么我可以帮忙的吗？无论是关于学习、工作还是生活中的问题，或者只是想聊聊天，都很欢迎告诉我。 😊','Urobot','EasyChat',1738945014671,'U33333333333',0,NULL,NULL,NULL,1),(106,'a9dc4b8652b74c01c7b0f817a4690f67',2,'请你自我介绍一下,20字以内','U33333333333','MingHaiTest',1738945051996,'Urobot',0,NULL,'',NULL,1),(107,'a9dc4b8652b74c01c7b0f817a4690f67',2,'你好！很高兴见到你。有什么我可以帮忙的吗？无论是学习问题、生活建议，还是其他问题，我都乐意提供帮助。😊','Urobot','EasyChat',1738945056141,'U33333333333',0,NULL,NULL,NULL,1),(108,'a9dc4b8652b74c01c7b0f817a4690f67',2,'请你做一个20字以内的自我介绍','U33333333333','MingHaiTest',1738945105037,'Urobot',0,NULL,'',NULL,1),(109,'a9dc4b8652b74c01c7b0f817a4690f67',2,'你好，我是一个热爱学习和分享的智能助手。','Urobot','EasyChat',1738945107243,'U33333333333',0,NULL,NULL,NULL,1),(110,'a9dc4b8652b74c01c7b0f817a4690f67',2,'请你自我介绍一下,20字以内','U33333333333','MingHaiTest',1738945258816,'Urobot',0,NULL,'',NULL,1),(111,'a9dc4b8652b74c01c7b0f817a4690f67',2,'您好！我是您的智能助手，随时为您提供帮助。','Urobot','EasyChat',1738945259986,'U33333333333',0,NULL,NULL,NULL,1),(112,'a9dc4b8652b74c01c7b0f817a4690f67',2,'你好!!!!','U33333333333','MingHaiTest',1738945418684,'Urobot',0,NULL,'',NULL,1),(113,'a9dc4b8652b74c01c7b0f817a4690f67',2,'你好！很高兴见到你！有什么我可以帮忙的吗？😊','Urobot','EasyChat',1738945420448,'U33333333333',0,NULL,NULL,NULL,1),(114,'a9dc4b8652b74c01c7b0f817a4690f67',2,'请你简短的自我介绍一下','U33333333333','MingHaiTest',1738945457884,'Urobot',0,NULL,'',NULL,1),(115,'a9dc4b8652b74c01c7b0f817a4690f67',2,'大家好，我是一个AI助手，专为解答问题、提供信息和辅助学习而设计。我具备广泛的知识库，能帮助大家解决各种疑问，从简单的知识查询到复杂的逻辑分析。希望能够通过交流为大家提供有价值的帮助！','Urobot','EasyChat',1738945473085,'U33333333333',0,NULL,NULL,NULL,1),(116,'a9dc4b8652b74c01c7b0f817a4690f67',2,'要求简短一些','U33333333333','MingHaiTest',1738945499827,'Urobot',0,NULL,'',NULL,1),(117,'a9dc4b8652b74c01c7b0f817a4690f67',2,'好的，请说！','Urobot','EasyChat',1738945500375,'U33333333333',0,NULL,NULL,NULL,1),(118,'a9dc4b8652b74c01c7b0f817a4690f67',2,'今天是几号','U33333333333','MingHaiTest',1738945518888,'Urobot',0,NULL,'',NULL,1),(119,'a9dc4b8652b74c01c7b0f817a4690f67',2,'您好！要获取实时日期，请确保开启联网功能哦！你可以查看手机、电脑或其他设备来获取今天的日期。由于我无法获取实时数据，建议您通过联网的方式查询。今天是2023年10月日，具体日期需要依赖您的设备来确认！ 📅','Urobot','EasyChat',1738945526706,'U33333333333',0,NULL,NULL,NULL,1),(120,'a9dc4b8652b74c01c7b0f817a4690f67',2,'给我介绍一下mybatis','U33333333333','MingHaiTest',1738945562428,'Urobot',0,NULL,'',NULL,1),(121,'a9dc4b8652b74c01c7b0f817a4690f67',2,'调用服务器失败','Urobot','EasyChat',1738945622564,'U33333333333',0,NULL,NULL,NULL,1),(122,'a9dc4b8652b74c01c7b0f817a4690f67',2,'50字以内介绍一下mybatis','U33333333333','MingHaiTest',1738945607653,'Urobot',0,NULL,'',NULL,1),(123,'a9dc4b8652b74c01c7b0f817a4690f67',2,'MyBatis 是一款轻量级持久层框架，支持定制化 SQL、存储过程和高级映射。通过 XML 或注解配置，简化数据库操作，提升开发效率。','Urobot','EasyChat',1738945659668,'U33333333333',0,NULL,NULL,NULL,1),(124,'a9dc4b8652b74c01c7b0f817a4690f67',2,'你好','U33333333333','MingHaiTest',1738945744179,'Urobot',0,NULL,'',NULL,1),(125,'a9dc4b8652b74c01c7b0f817a4690f67',2,'你好！很高兴见到你。今天有什么我可以帮忙的吗？无论是关于学习、工作还是生活中的问题，我都愿意为你提供建议和信息。😊','Urobot','EasyChat',1738945748934,'U33333333333',0,NULL,NULL,NULL,1),(126,'22d82a2d9e14ab2e50e544058eafa9b0',2,'helloDS','U11111111111','test01',1738945829162,'Urobot',0,NULL,'',NULL,1),(127,'22d82a2d9e14ab2e50e544058eafa9b0',2,'Hello! 👋 It seems like you typed \"helloDS.\" Is there something specific you\'d like to discuss or ask about? Whether it\'s related to data science, programming, or anything else, feel free to share, and I\'ll do my best to assist! 😊','Urobot','EasyChat',1738945836326,'U11111111111',0,NULL,NULL,NULL,1),(128,'22d82a2d9e14ab2e50e544058eafa9b0',2,'你好DeepSeek','U11111111111','test01',1738946507085,'Urobot',0,NULL,'',NULL,1),(129,'22d82a2d9e14ab2e50e544058eafa9b0',2,'调用服务器失败','Urobot','EasyChat',1738946507473,'U11111111111',0,NULL,NULL,NULL,1),(130,'22d82a2d9e14ab2e50e544058eafa9b0',2,'你是奶龙','U11111111111','test01',1738946698384,'Urobot',0,NULL,'',NULL,1),(131,'22d82a2d9e14ab2e50e544058eafa9b0',2,'调用服务器失败','Urobot','EasyChat',1738946698742,'U11111111111',0,NULL,NULL,NULL,1),(132,'22d82a2d9e14ab2e50e544058eafa9b0',2,'你好','U11111111111','test01',1738946825803,'Urobot',0,NULL,'',NULL,1),(133,'22d82a2d9e14ab2e50e544058eafa9b0',2,'调用服务器失败','Urobot','EasyChat',1738946826173,'U11111111111',0,NULL,NULL,NULL,1),(134,'22d82a2d9e14ab2e50e544058eafa9b0',2,'你是奶龙','U11111111111','test01',1738947206166,'Urobot',0,NULL,'',NULL,1),(135,'22d82a2d9e14ab2e50e544058eafa9b0',2,'哇！看来你对“奶龙”这个概念很感兴趣呢！“奶龙”通常是指那种可爱、萌系的小龙形象，通常是温柔、软萌、甚至有点憨憨的感觉，特别适合用在二次元、插画或者表情包里。如果你想让我扮演“奶龙”，那我就是一只超级可爱的小龙啦！我会用奶声奶气的声音和你聊天，还会时不时卖萌哦~<br><br>**奶龙：**  <br>“嗷呜~ 你好呀！我是奶龙小龙，今天也是元气满满的一天！有什么想和我分享的吗？或者需要我的帮助？我虽然是小龙，但超厉害的！”  <br>🌟🐉<br><br>要不要和我聊点有趣的事情？我可以讲故事、卖萌，甚至帮你出主意哦！','Urobot','EasyChat',1738947220295,'U11111111111',0,NULL,NULL,NULL,1),(136,'22d82a2d9e14ab2e50e544058eafa9b0',2,'你好啊','U11111111111','test01',1738947432945,'Urobot',0,NULL,'',NULL,1),(137,'22d82a2d9e14ab2e50e544058eafa9b0',2,'你好！很高兴见到你！有什么我可以帮你的吗？ 😊','Urobot','EasyChat',1738947434046,'U11111111111',0,NULL,NULL,NULL,1),(138,'ce8ede326c3fc9f6e147753b6e4ee3c7',2,'1','U33333333333','MingHaiTest',1738998624097,'U11111111111',0,NULL,'',NULL,1),(139,'ce8ede326c3fc9f6e147753b6e4ee3c7',5,'[图片]','U11111111111','test01',1738998668699,'U33333333333',0,4716876,'khl20230622154812904.png',0,0),(140,'ce8ede326c3fc9f6e147753b6e4ee3c7',5,'[图片]','U11111111111','test01',1738998874211,'U33333333333',0,4716876,'khl20230622154812904.png',0,0),(141,'ce8ede326c3fc9f6e147753b6e4ee3c7',5,'[图片]','U11111111111','test01',1738998907126,'U33333333333',0,4716876,'khl20230622154812904.png',0,0),(142,'ce8ede326c3fc9f6e147753b6e4ee3c7',5,'[图片]','U11111111111','test01',1738998932623,'U33333333333',0,4716876,'khl20230622154812904.png',0,0),(143,'ce8ede326c3fc9f6e147753b6e4ee3c7',5,'[图片]','U11111111111','test01',1738999168810,'U33333333333',0,13483,'Screenshot 2023-12-18 140742.png',0,1),(144,'ce8ede326c3fc9f6e147753b6e4ee3c7',5,'[图片]','U11111111111','test01',1738999243895,'U33333333333',0,52233,'屏幕截图 2024-10-24 113721.png',0,1),(145,'ce8ede326c3fc9f6e147753b6e4ee3c7',5,'[图片]','U11111111111','test01',1738999418340,'U33333333333',0,52233,'屏幕截图 2024-10-24 113721.png',0,1),(146,'ce8ede326c3fc9f6e147753b6e4ee3c7',5,'[图片]','U11111111111','test01',1738999433098,'U33333333333',0,52233,'屏幕截图 2024-10-24 113721.png',0,1),(147,'ce8ede326c3fc9f6e147753b6e4ee3c7',5,'[图片]','U11111111111','test01',1738999648794,'U33333333333',0,52233,'屏幕截图 2024-10-24 113721.png',0,1),(148,'ce8ede326c3fc9f6e147753b6e4ee3c7',5,'[图片]','U11111111111','test01',1738999747703,'U33333333333',0,7862,'Screenshot 2024-03-13 095132.png',0,1),(149,'5dccf7b37d464e94a8b8820a6425c9ee',5,'[图片]','U33333333333','MingHaiTest',1738999778429,'G78620679228',1,52233,'屏幕截图 2024-10-24 113721.png',0,1),(150,'5dccf7b37d464e94a8b8820a6425c9ee',5,'[图片]','U33333333333','MingHaiTest',1739000952353,'G78620679228',1,4716876,'khl20230622154812904.png',0,1),(151,'5dccf7b37d464e94a8b8820a6425c9ee',5,'[视频]','U11111111111','test01',1739000961767,'G78620679228',1,8163539,'序列 01.mp4',1,1),(152,'5dccf7b37d464e94a8b8820a6425c9ee',5,'[视频]','U33333333333','MingHaiTest',1739000998949,'G78620679228',1,13928269,'序列 01_1.mp4',1,1),(153,'9f487428c1f4cc933d8425bcc327a9e1',3,'群组已经创建好,可以和好友一起畅聊了!',NULL,NULL,1739004088054,'G18021972270',1,NULL,NULL,NULL,1),(154,'9f487428c1f4cc933d8425bcc327a9e1',8,'群聊已解散',NULL,NULL,1739004367937,'G18021972270',1,NULL,NULL,NULL,1),(155,'e561759cf1c6e9790e07014b32ada30f',8,'群聊已解散',NULL,NULL,1739004392936,'G97704533557',1,NULL,NULL,NULL,1),(156,'a35d0661095dd35f55ade9332145a42e',8,'群聊已解散',NULL,NULL,1739004416807,'G81959705026',1,NULL,NULL,NULL,1),(157,'b9e651a59d62ccb8125eb661b65b7f07',8,'群聊已解散',NULL,NULL,1739004429659,'G42069377180',1,NULL,NULL,NULL,1),(158,'fa2b8b266ba041e76fe5e4e09d06a55e',8,'群聊已解散',NULL,NULL,1739004433055,'G58256176322',1,NULL,NULL,NULL,1),(159,'5526dc7a63400529681c83a37a387f9f',8,'群聊已解散',NULL,NULL,1739004436165,'G22249753780',1,NULL,NULL,NULL,1),(160,'48090054f1e2875d1894d316642d29fa',3,'群组已经创建好,可以和好友一起畅聊了!',NULL,NULL,1739004622643,'G64078236286',1,NULL,NULL,NULL,1),(161,'48090054f1e2875d1894d316642d29fa',2,'121','U33333333333','MingHaiTest',1739004743030,'G64078236286',1,NULL,'',NULL,1),(165,'cdaec2bc9b796c2991761b801c9f0c2d',3,'群组已经创建好,可以和好友一起畅聊了!',NULL,NULL,1739004956401,'G09166282166',1,NULL,NULL,NULL,1),(166,'cdaec2bc9b796c2991761b801c9f0c2d',9,'test01加入了群组',NULL,NULL,1739005069222,'G09166282166',1,NULL,NULL,NULL,1),(167,'cdaec2bc9b796c2991761b801c9f0c2d',2,'test','U33333333333','MingHaiTest',1739005074735,'G09166282166',1,NULL,'',NULL,1),(168,'cdaec2bc9b796c2991761b801c9f0c2d',2,'test','U11111111111','test01',1739005389922,'G09166282166',1,NULL,'',NULL,1),(169,'cdaec2bc9b796c2991761b801c9f0c2d',2,'1','U11111111111','test01',1739010737838,'G09166282166',1,NULL,'',NULL,1),(170,'48090054f1e2875d1894d316642d29fa',9,'test01加入了群组',NULL,NULL,1739010779755,'G64078236286',1,NULL,NULL,NULL,1),(171,'48090054f1e2875d1894d316642d29fa',12,'test01被管理员移出了群聊',NULL,NULL,1739011553102,'G64078236286',1,NULL,NULL,NULL,1),(172,'48090054f1e2875d1894d316642d29fa',2,'1','U11111111111','test01',1739011566841,'G64078236286',1,NULL,'',NULL,1),(173,'48090054f1e2875d1894d316642d29fa',2,'1','U11111111111','test01',1739011574365,'G64078236286',1,NULL,'',NULL,1),(174,'cdaec2bc9b796c2991761b801c9f0c2d',12,'test01被管理员移出了群聊',NULL,NULL,1739012358704,'G09166282166',1,NULL,NULL,NULL,1),(175,'8657dd42b234d78d85f8c2efc228d273',3,'群组已经创建好,可以和好友一起畅聊了!',NULL,NULL,1739012520849,'G60056221672',1,NULL,NULL,NULL,1),(176,'8657dd42b234d78d85f8c2efc228d273',9,'test01加入了群组',NULL,NULL,1739012532348,'G60056221672',1,NULL,NULL,NULL,1),(177,'8657dd42b234d78d85f8c2efc228d273',2,'1','U11111111111','test01',1739012537894,'G60056221672',1,NULL,'',NULL,1),(178,'8657dd42b234d78d85f8c2efc228d273',2,'1','U33333333333','MingHaiTest',1739012994224,'G60056221672',1,NULL,'',NULL,1),(179,'8657dd42b234d78d85f8c2efc228d273',2,'1','U11111111111','test01',1739012999647,'G60056221672',1,NULL,'',NULL,1),(180,'ce8ede326c3fc9f6e147753b6e4ee3c7',2,'1','U33333333333','MingHaiTest',1739013008947,'U11111111111',0,NULL,'',NULL,1),(181,'8657dd42b234d78d85f8c2efc228d273',12,'test01被管理员移出了群聊',NULL,NULL,1739013063872,'G60056221672',1,NULL,NULL,NULL,1),(183,'ce8ede326c3fc9f6e147753b6e4ee3c7',2,'1','U11111111111','test01',1739013320994,'U33333333333',0,NULL,'',NULL,1),(184,'ce8ede326c3fc9f6e147753b6e4ee3c7',2,'11','U11111111111','test01',1739013344441,'U33333333333',0,NULL,'',NULL,1),(185,'ce8ede326c3fc9f6e147753b6e4ee3c7',2,'1','U33333333333','MingHaiTest',1739013360370,'U11111111111',0,NULL,'',NULL,1),(186,'ce8ede326c3fc9f6e147753b6e4ee3c7',2,'😀','U11111111111','test01',1739013367282,'U33333333333',0,NULL,'',NULL,1),(187,'ce8ede326c3fc9f6e147753b6e4ee3c7',2,'😀','U11111111111','test01',1739013395812,'U33333333333',0,NULL,'',NULL,1),(188,'ce8ede326c3fc9f6e147753b6e4ee3c7',2,'😀','U33333333333','MingHaiTest',1739013396996,'U11111111111',0,NULL,'',NULL,1),(189,'8657dd42b234d78d85f8c2efc228d273',9,'test01加入了群组',NULL,NULL,1739013406461,'G60056221672',1,NULL,NULL,NULL,1),(190,'8657dd42b234d78d85f8c2efc228d273',2,'?','U33333333333','MingHaiTest',1739013556737,'G60056221672',1,NULL,'',NULL,1),(191,'8657dd42b234d78d85f8c2efc228d273',11,'test01退出了群聊',NULL,NULL,1739013563211,'G60056221672',1,NULL,NULL,NULL,1),(192,'8657dd42b234d78d85f8c2efc228d273',2,'1','U33333333333','MingHaiTest',1739014093845,'G60056221672',1,NULL,'',NULL,1),(193,'8657dd42b234d78d85f8c2efc228d273',9,'test01加入了群组',NULL,NULL,1739014100358,'G60056221672',1,NULL,NULL,NULL,1),(194,'8657dd42b234d78d85f8c2efc228d273',2,'1','U33333333333','MingHaiTest',1739014103002,'G60056221672',1,NULL,'',NULL,1),(195,'8657dd42b234d78d85f8c2efc228d273',11,'test01退出了群聊',NULL,NULL,1739014123165,'G60056221672',1,NULL,NULL,NULL,1),(196,'8657dd42b234d78d85f8c2efc228d273',2,'1','U33333333333','MingHaiTest',1739014230576,'G60056221672',1,NULL,'',NULL,1),(197,'8657dd42b234d78d85f8c2efc228d273',9,'test01加入了群组',NULL,NULL,1739014238337,'G60056221672',1,NULL,NULL,NULL,1),(198,'8657dd42b234d78d85f8c2efc228d273',2,'1','U11111111111','test01',1739014241151,'G60056221672',1,NULL,'',NULL,1),(199,'8657dd42b234d78d85f8c2efc228d273',8,'群聊已解散',NULL,NULL,1739014246850,'G60056221672',1,NULL,NULL,NULL,1),(200,'51bbd9d0ebcb7caa431849e22d1df3ed',1,'我是MingHaiTest','U33333333333','MingHaiTest',1739015398250,'U11111111111',0,NULL,NULL,NULL,NULL),(201,'ce8ede326c3fc9f6e147753b6e4ee3c7',2,'半糖是最难受的','U11111111111','test01',1739015503079,'U33333333333',0,NULL,'',NULL,1),(202,'ce8ede326c3fc9f6e147753b6e4ee3c7',2,'🤤','U33333333333','MingHaiTest',1739015510128,'U11111111111',0,NULL,'',NULL,1),(203,'51bbd9d0ebcb7caa431849e22d1df3ed',1,'我是MingHaiTest','U33333333333','MingHaiTest',1739015529569,'U11111111111',0,NULL,NULL,NULL,NULL),(204,'ce8ede326c3fc9f6e147753b6e4ee3c7',2,'?','U33333333333','MingHaiTest',1739015613484,'U11111111111',0,NULL,'',NULL,1),(205,'ce8ede326c3fc9f6e147753b6e4ee3c7',1,'我是奶龙','U33333333333','MingHaiTest',1739015915301,'U11111111111',0,NULL,NULL,NULL,NULL),(206,'ce8ede326c3fc9f6e147753b6e4ee3c7',2,'我才是奶龙','U11111111111','test01',1739015951638,'U33333333333',0,NULL,'',NULL,1),(207,'ce8ede326c3fc9f6e147753b6e4ee3c7',2,'?','U33333333333','MingHaiTest',1739016073998,'U11111111111',0,NULL,'',NULL,1),(208,'ce8ede326c3fc9f6e147753b6e4ee3c7',2,'1','U11111111111','test01',1739016077946,'U33333333333',0,NULL,'',NULL,1),(209,'5dccf7b37d464e94a8b8820a6425c9ee',2,'1','U33333333333','MingHaiTest',1739016124164,'G78620679228',1,NULL,'',NULL,1),(210,'ce8ede326c3fc9f6e147753b6e4ee3c7',1,'我是MingHaiTest','U33333333333','MingHaiTest',1739016141706,'U11111111111',0,NULL,NULL,NULL,NULL),(211,'ce8ede326c3fc9f6e147753b6e4ee3c7',2,'1','U11111111111','test01',1739016148950,'U33333333333',0,NULL,'',NULL,1),(212,'ce8ede326c3fc9f6e147753b6e4ee3c7',2,'1','U33333333333','MingHaiTest',1739016150296,'U11111111111',0,NULL,'',NULL,1),(213,'5dccf7b37d464e94a8b8820a6425c9ee',2,'2','U33333333333','MingHaiTest',1739016489931,'G78620679228',1,NULL,'',NULL,1),(214,'ce8ede326c3fc9f6e147753b6e4ee3c7',2,'1','U33333333333','MingHaiTest',1739016650966,'U11111111111',0,NULL,'',NULL,1),(215,'ce8ede326c3fc9f6e147753b6e4ee3c7',2,'1','U33333333333','MingHaiTest',1739016657350,'U11111111111',0,NULL,'',NULL,1),(216,'ce8ede326c3fc9f6e147753b6e4ee3c7',2,'2','U33333333333','MingHaiTest',1739016659542,'U11111111111',0,NULL,'',NULL,1),(217,'ce8ede326c3fc9f6e147753b6e4ee3c7',2,'?','U11111111111','test01',1739016675259,'U33333333333',0,NULL,'',NULL,1),(218,'ce8ede326c3fc9f6e147753b6e4ee3c7',2,'1','U33333333333','MingHaiTest',1739016854976,'U11111111111',0,NULL,'',NULL,1),(219,'ce8ede326c3fc9f6e147753b6e4ee3c7',2,'.','U11111111111','test01',1739016869535,'U33333333333',0,NULL,'',NULL,1),(220,'ce8ede326c3fc9f6e147753b6e4ee3c7',1,'我是test01','U11111111111','test01',1739016984038,'U33333333333',0,NULL,NULL,NULL,NULL),(221,'ce8ede326c3fc9f6e147753b6e4ee3c7',2,'1','U33333333333','MingHaiTest',1739016987782,'U11111111111',0,NULL,'',NULL,1),(222,'ce8ede326c3fc9f6e147753b6e4ee3c7',2,'1','U11111111111','test01',1739016993725,'U33333333333',0,NULL,'',NULL,1);
/*!40000 ALTER TABLE `chat_message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chat_session`
--

DROP TABLE IF EXISTS `chat_session`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chat_session` (
  `session_id` varchar(32) NOT NULL COMMENT '会话ID',
  `last_message` varchar(500) DEFAULT NULL COMMENT '最后接受的消息',
  `last_receive_time` bigint DEFAULT NULL COMMENT '最后接受消息时间毫秒',
  PRIMARY KEY (`session_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='会话信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chat_session`
--

LOCK TABLES `chat_session` WRITE;
/*!40000 ALTER TABLE `chat_session` DISABLE KEYS */;
INSERT INTO `chat_session` VALUES ('22d82a2d9e14ab2e50e544058eafa9b0','你好！很高兴见到你！有什么我可以帮你的吗？ 😊',1738947434046),('48090054f1e2875d1894d316642d29fa','test01: 1',1739011574365),('5526dc7a63400529681c83a37a387f9f','群聊已解散',1739004436165),('5dccf7b37d464e94a8b8820a6425c9ee','MingHaiTest: 2',1739016489931),('8657dd42b234d78d85f8c2efc228d273','群聊已解散',1739014246850),('9f487428c1f4cc933d8425bcc327a9e1','群聊已解散',1739004367937),('a35d0661095dd35f55ade9332145a42e','群聊已解散',1739004416807),('a9dc4b8652b74c01c7b0f817a4690f67','你好！很高兴见到你。今天有什么我可以帮忙的吗？无论是关于学习、工作还是生活中的问题，我都愿意为你提供建议和信息。😊',1738945748934),('b9e651a59d62ccb8125eb661b65b7f07','群聊已解散',1739004429659),('cdaec2bc9b796c2991761b801c9f0c2d','test01被管理员移出了群聊',1739012358704),('ce8ede326c3fc9f6e147753b6e4ee3c7','1',1739016993725),('e561759cf1c6e9790e07014b32ada30f','群聊已解散',1739004392936),('fa2b8b266ba041e76fe5e4e09d06a55e','群聊已解散',1739004433055);
/*!40000 ALTER TABLE `chat_session` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chat_session_user`
--

DROP TABLE IF EXISTS `chat_session_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chat_session_user` (
  `user_id` varchar(12) NOT NULL COMMENT '用户ID',
  `contact_id` varchar(12) NOT NULL COMMENT '联系人ID',
  `session_id` varchar(32) NOT NULL COMMENT '会话ID',
  `contact_name` varchar(20) DEFAULT NULL COMMENT '联系人名称',
  PRIMARY KEY (`user_id`,`contact_id`),
  KEY `chat_session_user_session_id_index` (`session_id`),
  KEY `chat_session_user_user_id_index` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='会话用户';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chat_session_user`
--

LOCK TABLES `chat_session_user` WRITE;
/*!40000 ALTER TABLE `chat_session_user` DISABLE KEYS */;
INSERT INTO `chat_session_user` VALUES ('U11111111111','G09166282166','cdaec2bc9b796c2991761b801c9f0c2d','21321412321'),('U11111111111','G22249753780','5526dc7a63400529681c83a37a387f9f','测试003'),('U11111111111','G42069377180','b9e651a59d62ccb8125eb661b65b7f07','测试群聊001'),('U11111111111','G58256176322','fa2b8b266ba041e76fe5e4e09d06a55e','测试04'),('U11111111111','G60056221672','8657dd42b234d78d85f8c2efc228d273','321321321'),('U11111111111','G64078236286','48090054f1e2875d1894d316642d29fa','3213213'),('U11111111111','G78620679228','5dccf7b37d464e94a8b8820a6425c9ee','测试002'),('U11111111111','G81959705026','a35d0661095dd35f55ade9332145a42e','测试群005modifyyyyyy'),('U11111111111','G97704533557','e561759cf1c6e9790e07014b32ada30f','测试群聊001'),('U11111111111','U33333333333','ce8ede326c3fc9f6e147753b6e4ee3c7','MingHaiTest'),('U11111111111','Urobot','22d82a2d9e14ab2e50e544058eafa9b0','EasyChat'),('U33333333333','G09166282166','cdaec2bc9b796c2991761b801c9f0c2d','21321412321'),('U33333333333','G18021972270','9f487428c1f4cc933d8425bcc327a9e1','test000000001'),('U33333333333','G22249753780','5526dc7a63400529681c83a37a387f9f','测试003'),('U33333333333','G42069377180','b9e651a59d62ccb8125eb661b65b7f07','测试群聊001'),('U33333333333','G58256176322','fa2b8b266ba041e76fe5e4e09d06a55e','测试04'),('U33333333333','G60056221672','8657dd42b234d78d85f8c2efc228d273','321321321'),('U33333333333','G64078236286','48090054f1e2875d1894d316642d29fa','3213213'),('U33333333333','G78620679228','5dccf7b37d464e94a8b8820a6425c9ee','测试002'),('U33333333333','G81959705026','a35d0661095dd35f55ade9332145a42e','测试群005modifyyyyyy'),('U33333333333','G97704533557','e561759cf1c6e9790e07014b32ada30f','测试群聊001'),('U33333333333','U11111111111','ce8ede326c3fc9f6e147753b6e4ee3c7','test01'),('U33333333333','Urobot','a9dc4b8652b74c01c7b0f817a4690f67','EasyChat');
/*!40000 ALTER TABLE `chat_session_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group_info`
--

DROP TABLE IF EXISTS `group_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `group_info` (
  `group_id` varchar(12) NOT NULL COMMENT '群ID',
  `group_name` varchar(20) DEFAULT NULL COMMENT '群组名',
  `group_owner_id` varchar(12) DEFAULT NULL COMMENT '群主ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `group_notice` varchar(500) DEFAULT NULL COMMENT '群公告',
  `join_type` tinyint DEFAULT NULL COMMENT '0:直接加入 1:管理员同意后加入',
  `status` tinyint DEFAULT '1' COMMENT '状态 1:正常 0:解散',
  PRIMARY KEY (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group_info`
--

LOCK TABLES `group_info` WRITE;
/*!40000 ALTER TABLE `group_info` DISABLE KEYS */;
INSERT INTO `group_info` VALUES ('G18021972270','test000000001','U33333333333','2025-02-08 16:41:28','432423423',1,0),('G22249753780','测试003','U33333333333','2025-02-07 14:52:44','321321321321',1,0),('G42069377180','测试群聊001','U33333333333','2025-02-07 17:10:34','12321312321',1,0),('G58256176322','测试04','U33333333333','2025-02-07 14:53:09','321312321321',1,0),('G60056221672','321321321','U33333333333','2025-02-08 19:02:01','321312312312',1,0),('G64078236286','3213213','U33333333333','2025-02-08 16:50:23','12321321312',1,1),('G78620679228','测试002','U33333333333','2025-02-07 14:50:59','321321321321',1,1),('G81959705026','测试群005modifyyyyyy','U33333333333','2025-02-07 15:07:39','231321321321',1,0),('G97704533557','测试群聊001','U33333333333','2025-02-07 14:45:31','321321312321',1,0);
/*!40000 ALTER TABLE `group_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_contact`
--

DROP TABLE IF EXISTS `user_contact`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_contact` (
  `user_id` varchar(12) NOT NULL COMMENT '用户ID',
  `contact_id` varchar(12) NOT NULL COMMENT '联系人ID或群组ID',
  `contact_type` tinyint DEFAULT NULL COMMENT '联系人类型 0:好友 1:群组',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `status` tinyint DEFAULT NULL COMMENT '状态 0:非好友 1:好友 2:已删除好友 3:被好友删除 4:已拉黑好友 5:被好友拉黑',
  `last_update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`user_id`,`contact_id`),
  KEY `user_contact_contact_id_index` (`contact_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='联系人';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_contact`
--

LOCK TABLES `user_contact` WRITE;
/*!40000 ALTER TABLE `user_contact` DISABLE KEYS */;
INSERT INTO `user_contact` VALUES ('U11111111111','G22249753780',1,'2025-02-07 17:19:24',2,'2025-02-08 08:47:16'),('U11111111111','G42069377180',1,'2025-02-07 17:13:44',2,'2025-02-08 08:47:09'),('U11111111111','G58256176322',1,'2025-02-07 17:18:58',2,'2025-02-08 08:47:13'),('U11111111111','G60056221672',1,'2025-02-08 19:30:38',2,'2025-02-08 11:30:46'),('U11111111111','G78620679228',1,'2025-02-07 17:23:11',1,'2025-02-07 09:23:11'),('U11111111111','G81959705026',1,'2025-02-07 16:41:40',2,'2025-02-08 08:46:56'),('U11111111111','G97704533557',1,'2025-02-07 16:38:52',2,'2025-02-08 08:46:32'),('U11111111111','U33333333333',0,'2025-02-08 20:16:24',1,'2025-02-08 12:16:24'),('U11111111111','Urobot',0,'2025-02-06 18:12:14',1,'2025-02-06 10:12:14'),('U33333333333','G18021972270',1,'2025-02-08 16:41:28',2,'2025-02-08 08:46:07'),('U33333333333','G22249753780',1,'2025-02-07 14:52:44',2,'2025-02-08 08:47:16'),('U33333333333','G42069377180',1,'2025-02-07 17:10:34',2,'2025-02-08 08:47:09'),('U33333333333','G58256176322',1,'2025-02-07 14:53:09',2,'2025-02-08 08:47:13'),('U33333333333','G60056221672',1,'2025-02-08 19:02:01',2,'2025-02-08 11:30:46'),('U33333333333','G64078236286',1,'2025-02-08 16:50:23',1,'2025-02-08 08:50:23'),('U33333333333','G78620679228',1,'2025-02-07 14:50:59',1,'2025-02-07 06:50:59'),('U33333333333','G81959705026',1,'2025-02-07 15:07:39',2,'2025-02-08 08:46:56'),('U33333333333','G97704533557',1,'2025-02-07 14:45:31',2,'2025-02-08 08:46:32'),('U33333333333','U11111111111',0,'2025-02-08 20:16:24',1,'2025-02-08 12:16:24'),('U33333333333','Urobot',0,'2025-02-05 18:07:48',1,'2025-02-05 10:07:48');
/*!40000 ALTER TABLE `user_contact` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_contact_apply`
--

DROP TABLE IF EXISTS `user_contact_apply`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_contact_apply` (
  `apply_id` int NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `apply_user_id` varchar(12) NOT NULL COMMENT '申请人id',
  `receivce_user_id` varchar(12) NOT NULL COMMENT '接收人id',
  `contact_type` tinyint NOT NULL COMMENT '联系人类型 0:好友 1:群组',
  `contact_id` varchar(12) DEFAULT NULL COMMENT '联系人群组id',
  `last_apply_time` bigint DEFAULT NULL COMMENT '最后申请时间',
  `status` tinyint DEFAULT '0' COMMENT '状态 0:待处理 1:已同意 2:已拒绝 3:已拉黑',
  `apply_info` varchar(100) DEFAULT NULL COMMENT '申请信息',
  PRIMARY KEY (`apply_id`),
  UNIQUE KEY `user_id_receivce_user_id_contact_id_uindex` (`apply_user_id`,`receivce_user_id`,`contact_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_contact_apply`
--

LOCK TABLES `user_contact_apply` WRITE;
/*!40000 ALTER TABLE `user_contact_apply` DISABLE KEYS */;
INSERT INTO `user_contact_apply` VALUES (4,'U11111111111','U33333333333',0,'U33333333333',1739016980752,1,'我是test01'),(5,'U11111111111','U33333333333',1,'G97704533557',1738917096367,1,'我是test1'),(6,'U11111111111','U33333333333',1,'G81959705026',1738917646158,1,'我是test1'),(7,'U11111111111','U33333333333',1,'G42069377180',1738919612199,1,'我是test1'),(8,'U11111111111','U33333333333',1,'G58256176322',1738919932243,1,'我是test1'),(9,'U11111111111','U33333333333',1,'G22249753780',1738919953942,1,'我是test1'),(10,'U11111111111','U33333333333',1,'G78620679228',1738920169016,1,'我是test1'),(11,'U11111111111','U33333333333',1,'G09166282166',1739005064055,1,'我是test01'),(12,'U33333333333','U11111111111',0,'U11111111111',1739016138719,1,'我是MingHaiTest');
/*!40000 ALTER TABLE `user_contact_apply` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_info`
--

DROP TABLE IF EXISTS `user_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_info` (
  `user_id` varchar(12) NOT NULL COMMENT '用户ID',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `nick_name` varchar(20) DEFAULT NULL COMMENT '昵称',
  `join_type` tinyint DEFAULT NULL COMMENT '0:直接加入 1:同意后加好友',
  `sex` tinyint DEFAULT NULL COMMENT '性别',
  `password` varchar(32) NOT NULL COMMENT '密码',
  `personal_signature` varchar(50) DEFAULT NULL COMMENT '个性签名',
  `status` tinyint DEFAULT NULL COMMENT '状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `area_name` varchar(50) DEFAULT NULL COMMENT '地区',
  `area_code` varchar(50) DEFAULT NULL COMMENT '地区编号',
  `last_off_time` bigint DEFAULT NULL COMMENT '最后离线时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_info_email_uindex` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_info`
--

LOCK TABLES `user_info` WRITE;
/*!40000 ALTER TABLE `user_info` DISABLE KEYS */;
INSERT INTO `user_info` VALUES ('U11111111111','503191518@163.com','test01',1,1,'47ec2dd791e31e2ef2076caf64ed9b3d','测试签名',1,'2025-02-06 18:12:14','2025-02-08 19:59:05','北京市','10000',1739018598619),('U33333333333','1163094232@163.com','MingHaiTest',1,1,'de88e3e4ab202d87754078cbb2df6063','测试签名',1,'2025-02-05 18:07:48','2025-02-08 20:01:30','北京市','10000',1739018598639);
/*!40000 ALTER TABLE `user_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_info_beauty`
--

DROP TABLE IF EXISTS `user_info_beauty`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_info_beauty` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `email` varchar(50) NOT NULL,
  `user_id` varchar(12) NOT NULL,
  `status` tinyint DEFAULT NULL COMMENT '0:未使用 1:已使用',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_info_beauty_email_uindex` (`email`),
  UNIQUE KEY `user_info_beauty_user_id_uindex` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户靓号表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_info_beauty`
--

LOCK TABLES `user_info_beauty` WRITE;
/*!40000 ALTER TABLE `user_info_beauty` DISABLE KEYS */;
INSERT INTO `user_info_beauty` VALUES (1,'1163094232@163.com','U33333333333',1),(2,'503191518@163.com','U11111111111',1),(7,'1163094232@hotmail.com','U12345678901',0);
/*!40000 ALTER TABLE `user_info_beauty` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-02-08 22:04:28
