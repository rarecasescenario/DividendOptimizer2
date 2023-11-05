CREATE TABLE `watch_symbol` (
  `symbol` varchar(10) NOT NULL,
  `quoterly_dividend_amount` decimal(38,2) DEFAULT NULL COMMENT 'Quoterly dividend amount',
  `upper_yield` decimal(38,2) DEFAULT NULL COMMENT 'Upper dividend yield',
  `lower_yield` decimal(38,2) DEFAULT NULL COMMENT 'Lower dividend yield',
  `updated_on` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`symbol`),
  UNIQUE KEY `symbol_UNIQUE` (`symbol`),
  UNIQUE KEY `UK1eg9q32y95hn0r7gx0bemfd6k` (`symbol`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='A list of symbol with divident payments';


CREATE TABLE `horse`.`user_data` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT 'Record Id',
  `user_id` INT NOT NULL,
  `invested_amount` DECIMAL(12,2) NULL,
  `available_cash` DECIMAL(12,2) NULL,
  `updated_on` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
COMMENT = 'User current data';


CREATE TABLE `scenario` (
 `id` bigint NOT NULL AUTO_INCREMENT,  
 `available_cash` decimal(38,2) DEFAULT NULL,
  `invested_amount` decimal(38,2) DEFAULT NULL,
   `updated_on` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
   PRIMARY KEY (`id`)
) engine=InnoDB COMMENT = 'List of scenarios';

CREATE TABLE `scenario` (
 `id` bigint NOT NULL AUTO_INCREMENT,  
 `available_cash` decimal(38,2) DEFAULT NULL,
  `invested_amount` decimal(38,2) DEFAULT NULL,
  `updated_on` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='A list of user scenarios';


    