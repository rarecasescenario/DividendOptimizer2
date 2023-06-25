-- root / sea4me
-- DateTime Format 'YYYY-MM-DD hh:mm:ss'
CREATE TABLE `horse`.`position` (
  `ID` INT NOT NULL AUTO_INCREMENT COMMENT 'Record ID',
  `USER` INT NOT NULL COMMENT 'User unique id',
  `SYMBOL` VARCHAR(10) NOT NULL COMMENT 'Stock symbol',
  `SHARES` INT NULL COMMENT 'Number of shares',
  `PRICE` DECIMAL(9,3) NULL COMMENT 'Buy price  per share',
  `COMMISSION` DECIMAL(5,2) NULL COMMENT 'Broker\'s commission',
  `BUY_DATE` DATETIME NULL COMMENT 'Date and time when shares bought',
  `SOLD_DATE` DATETIME NULL COMMENT 'Date and time when shares cold',
  `UPDATED_ON` DATETIME NULL COMMENT 'date and time when the record was inserted or updated',
  PRIMARY KEY (`ID`))
COMMENT = 'User current positions';
