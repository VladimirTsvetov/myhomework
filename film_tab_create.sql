CREATE TABLE `film` (
  `id` int NOT NULL,
  `name` varchar(45) NOT NULL,
  `duration` datetime NOT NULL,
  `description` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci