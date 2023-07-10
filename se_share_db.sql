-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               8.0.30 - MySQL Community Server - GPL
-- Server OS:                    Win64
-- HeidiSQL Version:             12.1.0.6537
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for se_share
CREATE DATABASE IF NOT EXISTS `se_share` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `se_share`;

-- Dumping structure for table se_share.course
CREATE TABLE IF NOT EXISTS `course` (
  `course` char(20) DEFAULT NULL,
  `courseCode` char(20) NOT NULL,
  `semester` int DEFAULT NULL,
  `year` int DEFAULT NULL,
  PRIMARY KEY (`courseCode`),
  UNIQUE KEY `course` (`course`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table se_share.course: ~0 rows (approximately)
INSERT INTO `course` (`course`, `courseCode`, `semester`, `year`) VALUES
	('distributedsystems', 'ist123', 2, 2023);

-- Dumping structure for table se_share.coursematerial
CREATE TABLE IF NOT EXISTS `coursematerial` (
  `courseCode` char(20) DEFAULT NULL,
  `filename` varchar(1000) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `filetype` varchar(1000) DEFAULT NULL,
  `fileUri` varchar(200) DEFAULT NULL,
  UNIQUE KEY `courseCode` (`courseCode`),
  CONSTRAINT `coursematerial_ibfk_1` FOREIGN KEY (`courseCode`) REFERENCES `course` (`courseCode`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table se_share.coursematerial: ~3 rows (approximately)
INSERT INTO `coursematerial` (`courseCode`, `filename`, `filetype`, `fileUri`) VALUES
	('ist123', 'Clean Architecture_ A Craftsman’s Guide to Software Structure and Design-Pearson Education (2018)[7615523].pdf', 'pdf', 'D:\\TUTORIALS\\Books\\Clean Architecture_ A Craftsman’s Guide to Software Structure and Design-Pearson Education (2018)[7615523].pdf'),
	(NULL, 'Simple C# Data Access with Dapper and SQL - Minimal API Project Part 1.mp4', NULL, 'D:\\Videos\\Simple C# Data Access with Dapper and SQL - Minimal API Project Part 1.mp4'),
	(NULL, 'charm-down-core-3.8.6.jar', NULL, 'C:\\Users\\User\\Downloads\\Compressed\\jar_files_3\\charm-down-core-3.8.6.jar'),
	(NULL, 'jakarta-servlet-api-4.jar', NULL, 'C:\\Users\\User\\Downloads\\Compressed\\jakarta-servlet-api-4.jar\\jakarta-servlet-api-4.jar'),
	(NULL, 'idman641build3f.exe', NULL, 'C:\\Users\\User\\Downloads\\_Getintopc.com_Internet_Download_Manager_6.41_Build_3\\Internet_Download_Manager_6.41_Build_3\\idman641build3f.exe');

-- Dumping structure for table se_share.student
CREATE TABLE IF NOT EXISTS `student` (
  `name` char(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `regno` char(20) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `course` char(20) DEFAULT NULL,
  `year` int DEFAULT NULL,
  `sem` int DEFAULT NULL,
  `password` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  UNIQUE KEY `course` (`course`),
  CONSTRAINT `student_ibfk_1` FOREIGN KEY (`course`) REFERENCES `course` (`course`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table se_share.student: ~8 rows (approximately)
INSERT INTO `student` (`name`, `regno`, `course`, `year`, `sem`, `password`) VALUES
	('mujabieriyasam', '2000/U/7793/PS', 'distributedsystems', 3, 2, 'samsonmujabi125@gmail.com'),
	('samson', NULL, NULL, NULL, NULL, 'ddddd'),
	('samson', NULL, NULL, NULL, NULL, 'eriya'),
	('samson', NULL, NULL, NULL, NULL, 'ddddd'),
	('jlubwama', NULL, NULL, NULL, NULL, '123'),
	('jovita', NULL, NULL, NULL, NULL, 'jovitasam'),
	('eriya', NULL, NULL, NULL, NULL, 'samson'),
	('nakiyemba', NULL, NULL, NULL, NULL, 'favour'),
	('mary', NULL, NULL, NULL, NULL, '123'),
	('ddd', NULL, NULL, NULL, NULL, '12');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
