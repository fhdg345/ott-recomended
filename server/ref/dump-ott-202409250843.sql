

DROP TABLE IF EXISTS `member_ott`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `member_ott` (
  `member_ott_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `last_modified_at` datetime(6) DEFAULT NULL,
  `member_ott_name` varchar(255) DEFAULT NULL,
  `member_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`member_ott_id`),
  KEY `FKgc3sl7gm8hs6glabrge6mqdmh` (`member_id`),
  CONSTRAINT `FKgc3sl7gm8hs6glabrge6mqdmh` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member_ott`
--

LOCK TABLES `member_ott` WRITE;
/*!40000 ALTER TABLE `member_ott` DISABLE KEYS */;
/*!40000 ALTER TABLE `member_ott` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member_roles`
--

DROP TABLE IF EXISTS `member_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `member_roles` (
  `member_member_id` bigint(20) NOT NULL,
  `roles` varchar(255) DEFAULT NULL,
  KEY `FKruptm2dtwl95mfks4bnhv828k` (`member_member_id`),
  CONSTRAINT `FKruptm2dtwl95mfks4bnhv828k` FOREIGN KEY (`member_member_id`) REFERENCES `member` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member_roles`
--

LOCK TABLES `member_roles` WRITE;
/*!40000 ALTER TABLE `member_roles` DISABLE KEYS */;
/*!40000 ALTER TABLE `member_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bookmark`
--

DROP TABLE IF EXISTS `bookmark`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bookmark` (
  `bookmark_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `last_modified_at` datetime(6) DEFAULT NULL,
  `media_id` bigint(20) DEFAULT NULL,
  `member_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`bookmark_id`),
  KEY `FK4vqwm3ooypfm7v7ipmogvw6gg` (`media_id`),
  KEY `FK5bm7rup91j277mc7gg63akie2` (`member_id`),
  CONSTRAINT `FK4vqwm3ooypfm7v7ipmogvw6gg` FOREIGN KEY (`media_id`) REFERENCES `media` (`media_id`),
  CONSTRAINT `FK5bm7rup91j277mc7gg63akie2` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bookmark`
--

LOCK TABLES `bookmark` WRITE;
/*!40000 ALTER TABLE `bookmark` DISABLE KEYS */;
/*!40000 ALTER TABLE `bookmark` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `media`
--

DROP TABLE IF EXISTS `media`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `media` (
  `media_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `last_modified_at` datetime(6) DEFAULT NULL,
  `age_rate` varchar(255) DEFAULT NULL,
  `cast` varchar(255) NOT NULL,
  `category` varchar(255) NOT NULL,
  `content` varchar(1000) NOT NULL,
  `creator` varchar(255) DEFAULT NULL,
  `data_type` varchar(255) DEFAULT NULL,
  `main_poster` varchar(1000) NOT NULL,
  `recent` tinyint(1) NOT NULL DEFAULT 0,
  `release_date` int(11) NOT NULL,
  `title` varchar(50) NOT NULL,
  `title_poster` varchar(1000) NOT NULL,
  `tmdb_id` int(11) NOT NULL,
  PRIMARY KEY (`media_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `media`
--

LOCK TABLES `media` WRITE;
/*!40000 ALTER TABLE `media` DISABLE KEYS */;
/*!40000 ALTER TABLE `media` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `media_ott`
--

DROP TABLE IF EXISTS `media_ott`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `media_ott` (
  `media_ott_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `last_modified_at` datetime(6) DEFAULT NULL,
  `ott_address` varchar(1000) DEFAULT NULL,
  `ott_name` varchar(255) DEFAULT NULL,
  `media_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`media_ott_id`),
  KEY `FKexrncly492folqsbukwu0pgt3` (`media_id`),
  CONSTRAINT `FKexrncly492folqsbukwu0pgt3` FOREIGN KEY (`media_id`) REFERENCES `media` (`media_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `media_ott`
--

LOCK TABLES `media_ott` WRITE;
/*!40000 ALTER TABLE `media_ott` DISABLE KEYS */;
/*!40000 ALTER TABLE `media_ott` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recommendation`
--

DROP TABLE IF EXISTS `recommendation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `recommendation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `last_modified_at` datetime(6) DEFAULT NULL,
  `media_id` bigint(20) DEFAULT NULL,
  `member_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKowj2ve9c9x9gamqixaiftfnbn` (`media_id`),
  KEY `FKk1qbgdq26hj2d7d81fhc9n5k9` (`member_id`),
  CONSTRAINT `FKk1qbgdq26hj2d7d81fhc9n5k9` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`),
  CONSTRAINT `FKowj2ve9c9x9gamqixaiftfnbn` FOREIGN KEY (`media_id`) REFERENCES `media` (`media_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recommendation`
--

LOCK TABLES `recommendation` WRITE;
/*!40000 ALTER TABLE `recommendation` DISABLE KEYS */;
/*!40000 ALTER TABLE `recommendation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `review`
--

DROP TABLE IF EXISTS `review`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `review` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `last_modified_at` datetime(6) DEFAULT NULL,
  `media_media_id` bigint(20) DEFAULT NULL,
  `member_member_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1roimc4p04rd6rh89avenn63e` (`media_media_id`),
  KEY `FKqjum51a6dqt9boojdj2jm1dpg` (`member_member_id`),
  CONSTRAINT `FK1roimc4p04rd6rh89avenn63e` FOREIGN KEY (`media_media_id`) REFERENCES `media` (`media_id`),
  CONSTRAINT `FKqjum51a6dqt9boojdj2jm1dpg` FOREIGN KEY (`member_member_id`) REFERENCES `member` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `review`
--

LOCK TABLES `review` WRITE;
/*!40000 ALTER TABLE `review` DISABLE KEYS */;
/*!40000 ALTER TABLE `review` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `genre`
--

DROP TABLE IF EXISTS `genre`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `genre` (
  `genre_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `last_modified_at` datetime(6) DEFAULT NULL,
  `genre_name` varchar(255) DEFAULT NULL,
  `media_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`genre_id`),
  KEY `FK2sk272g53thik67dftgk6y47x` (`media_id`),
  CONSTRAINT `FK2sk272g53thik67dftgk6y47x` FOREIGN KEY (`media_id`) REFERENCES `media` (`media_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `genre`
--

LOCK TABLES `genre` WRITE;
/*!40000 ALTER TABLE `genre` DISABLE KEYS */;
/*!40000 ALTER TABLE `genre` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `interest`
--

DROP TABLE IF EXISTS `interest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `interest` (
  `interest_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `last_modified_at` datetime(6) DEFAULT NULL,
  `interest_name` varchar(255) DEFAULT NULL,
  `member_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`interest_id`),
  KEY `FKmejotk04k93xwh9v101agbduv` (`member_id`),
  CONSTRAINT `FKmejotk04k93xwh9v101agbduv` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `interest`
--

LOCK TABLES `interest` WRITE;
/*!40000 ALTER TABLE `interest` DISABLE KEYS */;
/*!40000 ALTER TABLE `interest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `report`
--

DROP TABLE IF EXISTS `report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `report` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `last_modified_at` datetime(6) DEFAULT NULL,
  `completion` bit(1) DEFAULT NULL,
  `content` varchar(255) NOT NULL,
  `title` varchar(255) NOT NULL,
  `media_id` bigint(20) DEFAULT NULL,
  `member_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3e2uwl26kkila0vobivpeux9x` (`media_id`),
  KEY `FKel7y5wyx42a6njav1dbe2torl` (`member_id`),
  CONSTRAINT `FK3e2uwl26kkila0vobivpeux9x` FOREIGN KEY (`media_id`) REFERENCES `media` (`media_id`),
  CONSTRAINT `FKel7y5wyx42a6njav1dbe2torl` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `report`
--

LOCK TABLES `report` WRITE;
/*!40000 ALTER TABLE `report` DISABLE KEYS */;
/*!40000 ALTER TABLE `report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member`
--

DROP TABLE IF EXISTS `member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `member` (
  `member_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `last_modified_at` datetime(6) DEFAULT NULL,
  `avatar_uri` varchar(500) DEFAULT NULL,
  `category` varchar(100) DEFAULT NULL,
  `email` varchar(30) NOT NULL,
  `nickname` varchar(10) NOT NULL,
  `password` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`member_id`),
  UNIQUE KEY `UKmbmcqelty0fbrvxp1q58dn57t` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member`
--

LOCK TABLES `member` WRITE;
/*!40000 ALTER TABLE `member` DISABLE KEYS */;
/*!40000 ALTER TABLE `member` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-09-25  8:43:10
