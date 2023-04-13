CREATE TABLE `tickets` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_sc` int NOT NULL,
  `seat_row` int NOT NULL,
  `seat_place` int NOT NULL,
  `cost` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_sc_idx` (`id_sc`),
  CONSTRAINT `id_sc` FOREIGN KEY (`id_sc`) REFERENCES `screening` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='билеты'