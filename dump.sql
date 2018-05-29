-- MySQL dump 10.13  Distrib 8.0.11, for macos10.13 (x86_64)
--
-- Host: localhost    Database: back2schooldb
-- ------------------------------------------------------
-- Server version	8.0.11

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8mb4 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Appointments`
--

DROP TABLE IF EXISTS `Appointments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `Appointments` (
  `APPOINTMENTID` varchar(255) NOT NULL,
  `DATE` datetime DEFAULT NULL,
  `RESOURCES` longblob,
  `PARENT_USERID` varchar(255) DEFAULT NULL,
  `TEACHER_USERID` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`APPOINTMENTID`),
  KEY `FK_Appointments_TEACHER_USERID` (`TEACHER_USERID`),
  KEY `FK_Appointments_PARENT_USERID` (`PARENT_USERID`),
  CONSTRAINT `FK_Appointments_PARENT_USERID` FOREIGN KEY (`PARENT_USERID`) REFERENCES `users` (`userid`),
  CONSTRAINT `FK_Appointments_TEACHER_USERID` FOREIGN KEY (`TEACHER_USERID`) REFERENCES `users` (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Appointments`
--

LOCK TABLES `Appointments` WRITE;
/*!40000 ALTER TABLE `Appointments` DISABLE KEYS */;
/*!40000 ALTER TABLE `Appointments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Classroom_and_students`
--

DROP TABLE IF EXISTS `Classroom_and_students`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `Classroom_and_students` (
  `Classroom_CLASSROOMID` varchar(255) NOT NULL,
  `students_USERID` varchar(255) NOT NULL,
  PRIMARY KEY (`Classroom_CLASSROOMID`,`students_USERID`),
  KEY `FK_Classroom_and_students_students_USERID` (`students_USERID`),
  CONSTRAINT `FK_Classroom_and_students_Classroom_CLASSROOMID` FOREIGN KEY (`Classroom_CLASSROOMID`) REFERENCES `classrooms` (`classroomid`),
  CONSTRAINT `FK_Classroom_and_students_students_USERID` FOREIGN KEY (`students_USERID`) REFERENCES `users` (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Classroom_and_students`
--

LOCK TABLES `Classroom_and_students` WRITE;
/*!40000 ALTER TABLE `Classroom_and_students` DISABLE KEYS */;
INSERT INTO `Classroom_and_students` VALUES ('1A','4418784412032670391');
/*!40000 ALTER TABLE `Classroom_and_students` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Classrooms`
--

DROP TABLE IF EXISTS `Classrooms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `Classrooms` (
  `CLASSROOMID` varchar(255) NOT NULL,
  `RESOURCES` longblob,
  PRIMARY KEY (`CLASSROOMID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Classrooms`
--

LOCK TABLES `Classrooms` WRITE;
/*!40000 ALTER TABLE `Classrooms` DISABLE KEYS */;
INSERT INTO `Classrooms` VALUES ('1A','¨\Ì\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0sr\0$it.polimi.rest_project.entities.Linkå\Ï·ø¨7B\Û\0L\0relt\0Ljava/lang/String;L\0uriq\0~\0xpt\0selft\03http://localhost:8080/back2school/b2s/classrooms/1Ax'),('1B','¨\Ì\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0sr\0$it.polimi.rest_project.entities.Linkå\Ï·ø¨7B\Û\0L\0relt\0Ljava/lang/String;L\0uriq\0~\0xpt\0selft\03http://localhost:8080/back2school/b2s/classrooms/1Bx'),('1C','¨\Ì\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0sr\0$it.polimi.rest_project.entities.Linkå\Ï·ø¨7B\Û\0L\0relt\0Ljava/lang/String;L\0uriq\0~\0xpt\0selft\03http://localhost:8080/back2school/b2s/classrooms/1Cx'),('2A','¨\Ì\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0sr\0$it.polimi.rest_project.entities.Linkå\Ï·ø¨7B\Û\0L\0relt\0Ljava/lang/String;L\0uriq\0~\0xpt\0selft\03http://localhost:8080/back2school/b2s/classrooms/2Ax'),('2B','¨\Ì\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0sr\0$it.polimi.rest_project.entities.Linkå\Ï·ø¨7B\Û\0L\0relt\0Ljava/lang/String;L\0uriq\0~\0xpt\0selft\03http://localhost:8080/back2school/b2s/classrooms/2Bx'),('2C','¨\Ì\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0sr\0$it.polimi.rest_project.entities.Linkå\Ï·ø¨7B\Û\0L\0relt\0Ljava/lang/String;L\0uriq\0~\0xpt\0selft\03http://localhost:8080/back2school/b2s/classrooms/2Cx'),('3A','¨\Ì\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0sr\0$it.polimi.rest_project.entities.Linkå\Ï·ø¨7B\Û\0L\0relt\0Ljava/lang/String;L\0uriq\0~\0xpt\0selft\03http://localhost:8080/back2school/b2s/classrooms/3Ax'),('3B','¨\Ì\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0sr\0$it.polimi.rest_project.entities.Linkå\Ï·ø¨7B\Û\0L\0relt\0Ljava/lang/String;L\0uriq\0~\0xpt\0selft\03http://localhost:8080/back2school/b2s/classrooms/3Bx'),('3C','¨\Ì\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0sr\0$it.polimi.rest_project.entities.Linkå\Ï·ø¨7B\Û\0L\0relt\0Ljava/lang/String;L\0uriq\0~\0xpt\0selft\03http://localhost:8080/back2school/b2s/classrooms/3Cx');
/*!40000 ALTER TABLE `Classrooms` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Grades`
--

DROP TABLE IF EXISTS `Grades`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `Grades` (
  `GRADEID` varchar(255) NOT NULL,
  `MARK` float DEFAULT NULL,
  `RESOURCES` longblob,
  `SUBJECT` varchar(255) DEFAULT NULL,
  `TEACHER_USERID` varchar(255) DEFAULT NULL,
  `GRADES_USERID` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`GRADEID`),
  KEY `FK_Grades_GRADES_USERID` (`GRADES_USERID`),
  KEY `FK_Grades_TEACHER_USERID` (`TEACHER_USERID`),
  CONSTRAINT `FK_Grades_GRADES_USERID` FOREIGN KEY (`GRADES_USERID`) REFERENCES `users` (`userid`),
  CONSTRAINT `FK_Grades_TEACHER_USERID` FOREIGN KEY (`TEACHER_USERID`) REFERENCES `users` (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Grades`
--

LOCK TABLES `Grades` WRITE;
/*!40000 ALTER TABLE `Grades` DISABLE KEYS */;
INSERT INTO `Grades` VALUES ('13772849526542169633',8,'¨\Ì\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0sr\0$it.polimi.rest_project.entities.Linkå\Ï·ø¨7B\Û\0L\0relt\0Ljava/lang/String;L\0uriq\0~\0xpt\0selft\0Ahttp://localhost:8080/back2school/b2s/grades/13772849526542169633x','math','5298769399403851194','4418784412032670391');
/*!40000 ALTER TABLE `Grades` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Lectures`
--

DROP TABLE IF EXISTS `Lectures`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `Lectures` (
  `ID` varchar(255) NOT NULL,
  `DAY` int(11) DEFAULT NULL,
  `HOUR` int(11) DEFAULT NULL,
  `RESOURCES` longblob,
  `SUBJECT` varchar(255) DEFAULT NULL,
  `TEACHER_USERID` varchar(255) DEFAULT NULL,
  `LECTURES_CLASSROOMID` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_Lectures_TEACHER_USERID` (`TEACHER_USERID`),
  KEY `FK_Lectures_LECTURES_CLASSROOMID` (`LECTURES_CLASSROOMID`),
  CONSTRAINT `FK_Lectures_LECTURES_CLASSROOMID` FOREIGN KEY (`LECTURES_CLASSROOMID`) REFERENCES `classrooms` (`classroomid`),
  CONSTRAINT `FK_Lectures_TEACHER_USERID` FOREIGN KEY (`TEACHER_USERID`) REFERENCES `users` (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Lectures`
--

LOCK TABLES `Lectures` WRITE;
/*!40000 ALTER TABLE `Lectures` DISABLE KEYS */;
INSERT INTO `Lectures` VALUES ('11436027205067337257',0,12,'¨\Ì\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0sr\0$it.polimi.rest_project.entities.Linkå\Ï·ø¨7B\Û\0L\0relt\0Ljava/lang/String;L\0uriq\0~\0xpt\0selft\0Chttp://localhost:8080/back2school/b2s/lectures/11436027205067337257x','english','16297156856575917826','1A'),('13796633207874695047',0,11,'¨\Ì\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0sr\0$it.polimi.rest_project.entities.Linkå\Ï·ø¨7B\Û\0L\0relt\0Ljava/lang/String;L\0uriq\0~\0xpt\0selft\0Chttp://localhost:8080/back2school/b2s/lectures/13796633207874695047x','english','16297156856575917826','1A'),('18192378329431577435',0,9,'¨\Ì\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0sr\0$it.polimi.rest_project.entities.Linkå\Ï·ø¨7B\Û\0L\0relt\0Ljava/lang/String;L\0uriq\0~\0xpt\0selft\0Chttp://localhost:8080/back2school/b2s/lectures/18192378329431577435x','math','5298769399403851194','1A'),('2338954257255033597',0,8,'¨\Ì\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0sr\0$it.polimi.rest_project.entities.Linkå\Ï·ø¨7B\Û\0L\0relt\0Ljava/lang/String;L\0uriq\0~\0xpt\0selft\0Bhttp://localhost:8080/back2school/b2s/lectures/2338954257255033597x','math','5298769399403851194','1A'),('2781205963696354646',0,10,'¨\Ì\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0sr\0$it.polimi.rest_project.entities.Linkå\Ï·ø¨7B\Û\0L\0relt\0Ljava/lang/String;L\0uriq\0~\0xpt\0selft\0Bhttp://localhost:8080/back2school/b2s/lectures/2781205963696354646x','english','16297156856575917826','1A');
/*!40000 ALTER TABLE `Lectures` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Notifications`
--

DROP TABLE IF EXISTS `Notifications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `Notifications` (
  `ID` varchar(255) NOT NULL,
  `DTYPE` varchar(31) DEFAULT NULL,
  `DATE` datetime DEFAULT NULL,
  `RESOURCES` longblob,
  `TEXT` varchar(255) DEFAULT NULL,
  `USER_USERID` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_Notifications_USER_USERID` (`USER_USERID`),
  CONSTRAINT `FK_Notifications_USER_USERID` FOREIGN KEY (`USER_USERID`) REFERENCES `users` (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Notifications`
--

LOCK TABLES `Notifications` WRITE;
/*!40000 ALTER TABLE `Notifications` DISABLE KEYS */;
INSERT INTO `Notifications` VALUES ('10838789188348444101','GeneralNotification','2018-05-29 00:00:36','¨\Ì\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0sr\0$it.polimi.rest_project.entities.Linkå\Ï·ø¨7B\Û\0L\0relt\0Ljava/lang/String;L\0uriq\0~\0xpt\0selft\0Hhttp://localhost:8080/back2school/b2s/notifications/10838789188348444101x','test class notification','16607028086750647380'),('11760931692324545493','SpecificNotification','2018-05-29 00:01:04','¨\Ì\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0sr\0$it.polimi.rest_project.entities.Linkå\Ï·ø¨7B\Û\0L\0relt\0Ljava/lang/String;L\0uriq\0~\0xpt\0selft\0Hhttp://localhost:8080/back2school/b2s/notifications/11760931692324545493x','test specific notification','12589753239724039485'),('14481826984611378830','GeneralNotification','2018-05-29 00:00:10','¨\Ì\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0sr\0$it.polimi.rest_project.entities.Linkå\Ï·ø¨7B\Û\0L\0relt\0Ljava/lang/String;L\0uriq\0~\0xpt\0selft\0Hhttp://localhost:8080/back2school/b2s/notifications/14481826984611378830x','test general notification','16297156856575917826'),('16095914342063712876','GeneralNotification','2018-05-29 00:00:36','¨\Ì\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0sr\0$it.polimi.rest_project.entities.Linkå\Ï·ø¨7B\Û\0L\0relt\0Ljava/lang/String;L\0uriq\0~\0xpt\0selft\0Hhttp://localhost:8080/back2school/b2s/notifications/16095914342063712876x','test class notification','16297156856575917826'),('17774289404126574224','GeneralNotification','2018-05-29 00:00:10','¨\Ì\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0sr\0$it.polimi.rest_project.entities.Linkå\Ï·ø¨7B\Û\0L\0relt\0Ljava/lang/String;L\0uriq\0~\0xpt\0selft\0Hhttp://localhost:8080/back2school/b2s/notifications/17774289404126574224x','test general notification','7443476398790807455'),('1922854769619955189','GeneralNotification','2018-05-29 00:00:10','¨\Ì\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0sr\0$it.polimi.rest_project.entities.Linkå\Ï·ø¨7B\Û\0L\0relt\0Ljava/lang/String;L\0uriq\0~\0xpt\0selft\0Ghttp://localhost:8080/back2school/b2s/notifications/1922854769619955189x','test general notification','12943214522866073430'),('4875525883128446225','GeneralNotification','2018-05-29 00:00:36','¨\Ì\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0sr\0$it.polimi.rest_project.entities.Linkå\Ï·ø¨7B\Û\0L\0relt\0Ljava/lang/String;L\0uriq\0~\0xpt\0selft\0Ghttp://localhost:8080/back2school/b2s/notifications/4875525883128446225x','test class notification','5298769399403851194'),('7758047364919538997','GeneralNotification','2018-05-29 00:00:10','¨\Ì\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0sr\0$it.polimi.rest_project.entities.Linkå\Ï·ø¨7B\Û\0L\0relt\0Ljava/lang/String;L\0uriq\0~\0xpt\0selft\0Ghttp://localhost:8080/back2school/b2s/notifications/7758047364919538997x','test general notification','5298769399403851194'),('8614270457823878040','GeneralNotification','2018-05-29 00:00:09','¨\Ì\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0sr\0$it.polimi.rest_project.entities.Linkå\Ï·ø¨7B\Û\0L\0relt\0Ljava/lang/String;L\0uriq\0~\0xpt\0selft\0Ghttp://localhost:8080/back2school/b2s/notifications/8614270457823878040x','test general notification','12589753239724039485'),('9507372048301470296','GeneralNotification','2018-05-29 00:00:10','¨\Ì\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0sr\0$it.polimi.rest_project.entities.Linkå\Ï·ø¨7B\Û\0L\0relt\0Ljava/lang/String;L\0uriq\0~\0xpt\0selft\0Ghttp://localhost:8080/back2school/b2s/notifications/9507372048301470296x','test general notification','16607028086750647380');
/*!40000 ALTER TABLE `Notifications` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Payments`
--

DROP TABLE IF EXISTS `Payments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `Payments` (
  `PAYMENTID` varchar(255) NOT NULL,
  `AMOUNT` int(11) DEFAULT NULL,
  `DATE` datetime DEFAULT NULL,
  `DONE` tinyint(1) DEFAULT '0',
  `REASON` varchar(255) DEFAULT NULL,
  `RESOURCES` longblob,
  `USER_USERID` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`PAYMENTID`),
  KEY `FK_Payments_USER_USERID` (`USER_USERID`),
  CONSTRAINT `FK_Payments_USER_USERID` FOREIGN KEY (`USER_USERID`) REFERENCES `users` (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Payments`
--

LOCK TABLES `Payments` WRITE;
/*!40000 ALTER TABLE `Payments` DISABLE KEYS */;
INSERT INTO `Payments` VALUES ('11055551150573712563',50,'2018-05-29 00:02:02',0,'test payment','¨\Ì\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0sr\0$it.polimi.rest_project.entities.Linkå\Ï·ø¨7B\Û\0L\0relt\0Ljava/lang/String;L\0uriq\0~\0xpt\0selft\0Chttp://localhost:8080/back2school/b2s/payments/11055551150573712563x','12589753239724039485');
/*!40000 ALTER TABLE `Payments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Users`
--

DROP TABLE IF EXISTS `Users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `Users` (
  `USERID` varchar(255) NOT NULL,
  `DTYPE` varchar(31) DEFAULT NULL,
  `DATEOFBIRTH` datetime DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `PASSWORD` longblob,
  `RESOURCES` longblob,
  `SURNAME` varchar(255) DEFAULT NULL,
  `STUDENTS_USERID` varchar(255) DEFAULT NULL,
  `PARENTS_USERID` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`USERID`),
  KEY `FK_Users_STUDENTS_USERID` (`STUDENTS_USERID`),
  KEY `FK_Users_PARENTS_USERID` (`PARENTS_USERID`),
  CONSTRAINT `FK_Users_PARENTS_USERID` FOREIGN KEY (`PARENTS_USERID`) REFERENCES `users` (`userid`),
  CONSTRAINT `FK_Users_STUDENTS_USERID` FOREIGN KEY (`STUDENTS_USERID`) REFERENCES `users` (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Users`
--

LOCK TABLES `Users` WRITE;
/*!40000 ALTER TABLE `Users` DISABLE KEYS */;
INSERT INTO `Users` VALUES ('1','Administrator','2018-05-28 23:30:05',NULL,'åiv\ÂµAΩ\ÈΩM\Óﬂ±g©\»s¸K∏®o*¥H©','¨\Ì\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0\0w\0\0\0\0x',NULL,NULL,NULL),('11144909316113058025','Student','1998-12-28 00:00:00','student_name_1','üÜ–ÅàL}eö/\Í†\≈Z\–£øO+Ç,\—]l∞\\n','¨\Ì\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0sr\0$it.polimi.rest_project.entities.Linkå\Ï·ø¨7B\Û\0L\0relt\0Ljava/lang/String;L\0uriq\0~\0xpt\0selft\0Chttp://localhost:8080/back2school/b2s/students/11144909316113058025x','student_surname_1',NULL,NULL),('12589753239724039485','Parent','1975-12-21 00:00:00','parent_name_4','üÜ–ÅàL}eö/\Í†\≈Z\–£øO+Ç,\—]l∞\\n','¨\Ì\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0sr\0$it.polimi.rest_project.entities.Linkå\Ï·ø¨7B\Û\0L\0relt\0Ljava/lang/String;L\0uriq\0~\0xpt\0selft\0Bhttp://localhost:8080/back2school/b2s/parents/12589753239724039485sq\0~\0t\0childrent\0.http://localhost:8080/back2school/b2s/studentssq\0~\0t\0paymentst\0.http://localhost:8080/back2school/b2s/paymentssq\0~\0t\0\rnotificationst\03http://localhost:8080/back2school/b2s/notificationssq\0~\0t\0appointmentst\02http://localhost:8080/back2school/b2s/appointmentsx','parent_surname_4',NULL,NULL),('12943214522866073430','Parent','1970-11-10 00:00:00','parent_name_3','üÜ–ÅàL}eö/\Í†\≈Z\–£øO+Ç,\—]l∞\\n','¨\Ì\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0sr\0$it.polimi.rest_project.entities.Linkå\Ï·ø¨7B\Û\0L\0relt\0Ljava/lang/String;L\0uriq\0~\0xpt\0selft\0Bhttp://localhost:8080/back2school/b2s/parents/12943214522866073430sq\0~\0t\0childrent\0.http://localhost:8080/back2school/b2s/studentssq\0~\0t\0paymentst\0.http://localhost:8080/back2school/b2s/paymentssq\0~\0t\0\rnotificationst\03http://localhost:8080/back2school/b2s/notificationssq\0~\0t\0appointmentst\02http://localhost:8080/back2school/b2s/appointmentsx','parent_surname_3',NULL,NULL),('16297156856575917826','Teacher','1963-03-23 00:00:00','teacher_name_1','üÜ–ÅàL}eö/\Í†\≈Z\–£øO+Ç,\—]l∞\\n','¨\Ì\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0sr\0$it.polimi.rest_project.entities.Linkå\Ï·ø¨7B\Û\0L\0relt\0Ljava/lang/String;L\0uriq\0~\0xpt\0selft\0Chttp://localhost:8080/back2school/b2s/teachers/16297156856575917826sq\0~\0t\0\nclassroomst\00http://localhost:8080/back2school/b2s/classroomssq\0~\0t\0gradest\0,http://localhost:8080/back2school/b2s/gradessq\0~\0t\0appointmentst\02http://localhost:8080/back2school/b2s/appointmentsx','teacher_surname_1',NULL,NULL),('16607028086750647380','Parent','1979-12-18 00:00:00','parent_name_2','üÜ–ÅàL}eö/\Í†\≈Z\–£øO+Ç,\—]l∞\\n','¨\Ì\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0sr\0$it.polimi.rest_project.entities.Linkå\Ï·ø¨7B\Û\0L\0relt\0Ljava/lang/String;L\0uriq\0~\0xpt\0selft\0Bhttp://localhost:8080/back2school/b2s/parents/16607028086750647380sq\0~\0t\0childrent\0.http://localhost:8080/back2school/b2s/studentssq\0~\0t\0paymentst\0.http://localhost:8080/back2school/b2s/paymentssq\0~\0t\0\rnotificationst\03http://localhost:8080/back2school/b2s/notificationssq\0~\0t\0appointmentst\02http://localhost:8080/back2school/b2s/appointmentsx','parent_surname_2',NULL,'4418784412032670391'),('4418784412032670391','Student','2003-04-17 00:00:00','student_name_3','üÜ–ÅàL}eö/\Í†\≈Z\–£øO+Ç,\—]l∞\\n','¨\Ì\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0sr\0$it.polimi.rest_project.entities.Linkå\Ï·ø¨7B\Û\0L\0relt\0Ljava/lang/String;L\0uriq\0~\0xpt\0selft\0Bhttp://localhost:8080/back2school/b2s/students/4418784412032670391x','student_surname_3','16607028086750647380',NULL),('5298769399403851194','Teacher','1971-02-23 00:00:00','teacher_name_2','üÜ–ÅàL}eö/\Í†\≈Z\–£øO+Ç,\—]l∞\\n','¨\Ì\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0sr\0$it.polimi.rest_project.entities.Linkå\Ï·ø¨7B\Û\0L\0relt\0Ljava/lang/String;L\0uriq\0~\0xpt\0selft\0Bhttp://localhost:8080/back2school/b2s/teachers/5298769399403851194sq\0~\0t\0\nclassroomst\00http://localhost:8080/back2school/b2s/classroomssq\0~\0t\0gradest\0,http://localhost:8080/back2school/b2s/gradessq\0~\0t\0appointmentst\02http://localhost:8080/back2school/b2s/appointmentsx','teacher_surname_2',NULL,NULL),('7443476398790807455','Parent','1975-09-23 00:00:00','parent_name_1','üÜ–ÅàL}eö/\Í†\≈Z\–£øO+Ç,\—]l∞\\n','¨\Ì\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0sr\0$it.polimi.rest_project.entities.Linkå\Ï·ø¨7B\Û\0L\0relt\0Ljava/lang/String;L\0uriq\0~\0xpt\0selft\0Ahttp://localhost:8080/back2school/b2s/parents/7443476398790807455sq\0~\0t\0childrent\0.http://localhost:8080/back2school/b2s/studentssq\0~\0t\0paymentst\0.http://localhost:8080/back2school/b2s/paymentssq\0~\0t\0\rnotificationst\03http://localhost:8080/back2school/b2s/notificationssq\0~\0t\0appointmentst\02http://localhost:8080/back2school/b2s/appointmentsx','parent_surname_1',NULL,NULL),('8143801565681175768','Administrator','1985-01-05 00:00:00','admin_name_1','üÜ–ÅàL}eö/\Í†\≈Z\–£øO+Ç,\—]l∞\\n','¨\Ì\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0sr\0$it.polimi.rest_project.entities.Linkå\Ï·ø¨7B\Û\0L\0relt\0Ljava/lang/String;L\0uriq\0~\0xpt\0selft\0@http://localhost:8080/back2school/b2s/admins/8143801565681175768sq\0~\0t\0studentst\0.http://localhost:8080/back2school/b2s/studentssq\0~\0t\0paymentst\0.http://localhost:8080/back2school/b2s/paymentssq\0~\0t\0\rnotificationst\03http://localhost:8080/back2school/b2s/notificationssq\0~\0t\0teacherst\0.http://localhost:8080/back2school/b2s/teacherssq\0~\0t\0parentst\0-http://localhost:8080/back2school/b2s/parentssq\0~\0t\0\nclassroomst\00http://localhost:8080/back2school/b2s/classroomsx','admin_surname_1',NULL,NULL),('8657062132054817386','Student','2004-04-26 00:00:00','student_name_4','üÜ–ÅàL}eö/\Í†\≈Z\–£øO+Ç,\—]l∞\\n','¨\Ì\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0sr\0$it.polimi.rest_project.entities.Linkå\Ï·ø¨7B\Û\0L\0relt\0Ljava/lang/String;L\0uriq\0~\0xpt\0selft\0Bhttp://localhost:8080/back2school/b2s/students/8657062132054817386x','student_surname_4',NULL,NULL),('9437828127473589839','Student','2002-11-21 00:00:00','student_name_2','üÜ–ÅàL}eö/\Í†\≈Z\–£øO+Ç,\—]l∞\\n','¨\Ì\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0sr\0$it.polimi.rest_project.entities.Linkå\Ï·ø¨7B\Û\0L\0relt\0Ljava/lang/String;L\0uriq\0~\0xpt\0selft\0Bhttp://localhost:8080/back2school/b2s/students/9437828127473589839x','student_surname_2','16607028086750647380',NULL);
/*!40000 ALTER TABLE `Users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-05-29 11:57:07
