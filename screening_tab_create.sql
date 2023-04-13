CREATE TABLE `screening` (
  `id` int NOT NULL,
  `film_id` int NOT NULL,
  `time` datetime NOT NULL,
  `final` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fFilmId_idx` (`film_id`),
  CONSTRAINT `film_id` FOREIGN KEY (`film_id`) REFERENCES `film` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci