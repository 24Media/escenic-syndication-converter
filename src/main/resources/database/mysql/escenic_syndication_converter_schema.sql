CREATE DATABASE  IF NOT EXISTS `escenic_syndication_converter` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `escenic_syndication_converter`;
-- MySQL dump 10.13  Distrib 5.5.37, for debian-linux-gnu (x86_64)
--
-- Host: 127.0.0.1    Database: escenic_syndication_converter
-- ------------------------------------------------------
-- Server version	5.5.37-0ubuntu0.14.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `author`
--

DROP TABLE IF EXISTS `author`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `author` (
  `applicationId` int(11) NOT NULL AUTO_INCREMENT,
  `contentApplicationId` int(11) NOT NULL,
  `idRef` varchar(100) DEFAULT NULL,
  `source` varchar(100) DEFAULT NULL,
  `sourceId` varchar(100) DEFAULT NULL,
  `dbId` varchar(100) DEFAULT NULL,
  `firstName` varchar(100) DEFAULT NULL,
  `lastName` varchar(100) DEFAULT NULL,
  `userName` varchar(100) DEFAULT NULL,
  `emailAddress` varchar(100) DEFAULT NULL,
  `applicationDateUpdated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`applicationId`)
) ENGINE=InnoDB AUTO_INCREMENT=7849 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `content`
--

DROP TABLE IF EXISTS `content`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `content` (
  `applicationId` int(11) NOT NULL AUTO_INCREMENT,
  `id` varchar(100) DEFAULT NULL,
  `source` varchar(100) DEFAULT NULL,
  `sourceId` varchar(100) DEFAULT NULL,
  `dbId` varchar(100) DEFAULT NULL,
  `exportedDbId` varchar(100) DEFAULT NULL,
  `state` varchar(9) DEFAULT NULL,
  `type` varchar(100) DEFAULT NULL,
  `publishDate` varchar(100) DEFAULT NULL,
  `deleteRelations` varchar(100) DEFAULT NULL,
  `activateDate` varchar(100) DEFAULT NULL,
  `expireDate` varchar(100) DEFAULT NULL,
  `creationDate` varchar(100) DEFAULT NULL,
  `newSource` varchar(100) DEFAULT NULL,
  `newSourceId` varchar(100) DEFAULT NULL,
  `creator` int(11) DEFAULT NULL,
  `priority` int(11) DEFAULT NULL,
  `uri` varchar(100) DEFAULT NULL,
  `applicationDateUpdated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`applicationId`)
) ENGINE=InnoDB AUTO_INCREMENT=8045 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `creator`
--

DROP TABLE IF EXISTS `creator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `creator` (
  `applicationId` int(11) NOT NULL AUTO_INCREMENT,
  `idRef` varchar(100) DEFAULT NULL,
  `source` varchar(100) DEFAULT NULL,
  `sourceId` varchar(100) DEFAULT NULL,
  `dbId` varchar(100) DEFAULT NULL,
  `firstName` varchar(100) DEFAULT NULL,
  `lastName` varchar(100) DEFAULT NULL,
  `userName` varchar(100) DEFAULT NULL,
  `emailAddress` varchar(100) DEFAULT NULL,
  `applicationDateUpdated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`applicationId`)
) ENGINE=InnoDB AUTO_INCREMENT=8045 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `field`
--

DROP TABLE IF EXISTS `field`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `field` (
  `applicationId` int(11) NOT NULL AUTO_INCREMENT,
  `contentApplicationId` int(11) DEFAULT NULL,
  `relationApplicationId` int(11) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `expandEntities` varchar(5) DEFAULT NULL,
  `title` varchar(100) DEFAULT NULL,
  `field` text,
  `applicationDateUpdated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`applicationId`)
) ENGINE=InnoDB AUTO_INCREMENT=38851 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `mirrorSource`
--

DROP TABLE IF EXISTS `mirrorSource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mirrorSource` (
  `applicationId` int(11) NOT NULL AUTO_INCREMENT,
  `idRef` varchar(100) DEFAULT NULL,
  `source` varchar(100) DEFAULT NULL,
  `sourceId` varchar(100) DEFAULT NULL,
  `dbId` varchar(100) DEFAULT NULL,
  `exportedDbId` varchar(100) DEFAULT NULL,
  `publicationName` varchar(100) DEFAULT NULL,
  `uniqueName` varchar(100) DEFAULT NULL,
  `applicationDateUpdated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`applicationId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `parent`
--

DROP TABLE IF EXISTS `parent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `parent` (
  `applicationId` int(11) NOT NULL AUTO_INCREMENT,
  `idRef` varchar(45) DEFAULT NULL,
  `source` varchar(45) DEFAULT NULL,
  `sourceId` varchar(45) DEFAULT NULL,
  `dbId` varchar(45) DEFAULT NULL,
  `exportedDbId` varchar(45) DEFAULT NULL,
  `uniqueName` varchar(45) DEFAULT NULL,
  `inheritAccessControlList` varchar(5) DEFAULT NULL,
  `applicationDateUpdated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`applicationId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `relation`
--

DROP TABLE IF EXISTS `relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `relation` (
  `applicationId` int(11) NOT NULL AUTO_INCREMENT,
  `contentApplicationId` int(11) NOT NULL,
  `idRef` varchar(100) DEFAULT NULL,
  `source` varchar(100) DEFAULT NULL,
  `sourceId` varchar(100) DEFAULT NULL,
  `dbId` varchar(100) DEFAULT NULL,
  `exportedDbId` varchar(100) DEFAULT NULL,
  `type` varchar(100) DEFAULT NULL,
  `applicationDateUpdated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`applicationId`)
) ENGINE=InnoDB AUTO_INCREMENT=339 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `relationCheck`
--

DROP TABLE IF EXISTS `relationCheck`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `relationCheck` (
  `applicationId` int(11) NOT NULL AUTO_INCREMENT,
  `contentApplicationId` int(11) NOT NULL,
  `contentType` varchar(100) DEFAULT NULL,
  `source` varchar(100) DEFAULT NULL,
  `sourceId` varchar(100) DEFAULT NULL,
  `relationType` varchar(100) DEFAULT NULL,
  `applicationDateUpdated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`applicationId`)
) ENGINE=InnoDB AUTO_INCREMENT=3698 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `section`
--

DROP TABLE IF EXISTS `section`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `section` (
  `applicationId` int(11) NOT NULL AUTO_INCREMENT,
  `id` varchar(100) DEFAULT NULL,
  `source` varchar(100) DEFAULT NULL,
  `sourceId` varchar(100) DEFAULT NULL,
  `dbId` varchar(100) DEFAULT NULL,
  `exportedDbId` varchar(100) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `uniqueNameAttribute` varchar(100) DEFAULT NULL,
  `mirrorSourceAttribute` varchar(5) DEFAULT NULL,
  `recursive` varchar(5) DEFAULT NULL,
  `deleteContent` varchar(5) DEFAULT NULL,
  `moveContent` varchar(5) DEFAULT NULL,
  `parent` int(11) DEFAULT NULL,
  `mirrorSourceElement` int(11) DEFAULT NULL,
  `uniqueNameElement` varchar(100) DEFAULT NULL,
  `directory` varchar(100) DEFAULT NULL,
  `sectionLayout` varchar(100) DEFAULT NULL,
  `articleLayout` varchar(100) DEFAULT NULL,
  `priority` int(11) DEFAULT NULL,
  `applicationDateUpdated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`applicationId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sectionRef`
--

DROP TABLE IF EXISTS `sectionRef`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sectionRef` (
  `applicationId` int(11) NOT NULL AUTO_INCREMENT,
  `contentApplicationId` int(11) NOT NULL,
  `idRef` varchar(100) DEFAULT NULL,
  `source` varchar(100) DEFAULT NULL,
  `sourceId` varchar(100) DEFAULT NULL,
  `dbId` varchar(100) DEFAULT NULL,
  `uniqueName` varchar(100) DEFAULT NULL,
  `toDesk` varchar(5) DEFAULT NULL,
  `homeSection` varchar(5) DEFAULT NULL,
  `applicationDateUpdated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`applicationId`)
) ENGINE=InnoDB AUTO_INCREMENT=11456 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-06-29 10:34:15
