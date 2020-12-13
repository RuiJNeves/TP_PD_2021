CREATE SCHEMA IF NOT EXISTS `PD_20-21` DEFAULT CHARACTER SET utf8 ;
USE `PD_20-21` ;

-- -----------------------------------------------------
-- Table `PD_20-21`.`User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `PD_20-21`.`User` (
  idUser INT NOT NULL AUTO_INCREMENT,
  Name VARCHAR(45) UNIQUE NOT NULL,
  Email VARCHAR(45) NOT NULL,
  Password VARCHAR(45) NOT NULL,
  PRIMARY KEY (idUser));

-- -----------------------------------------------------
-- Table `PD_20-21`.`Channel`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `PD_20-21`.`Channel` (
  `idChannel` INT NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(20) NOT NULL UNIQUE,
  `Password` VARCHAR(45) NOT NULL,
  `Description` VARCHAR(500) NULL,
  `idCreator` INT NOT NULL,
  PRIMARY KEY (`idChannel`),
  CONSTRAINT `fk_Channel_User1`
    FOREIGN KEY (`idCreator`)
    REFERENCES `PD_20-21`.`User` (`idUser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table `PD_20-21`.`Message`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `PD_20-21`.`Message` (
  `idMessage` INT NOT NULL,
  `Text` VARCHAR(1000) NULL,
  `Date` DATE NOT NULL,
  PRIMARY KEY (`idMessage`));


-- -----------------------------------------------------
-- Table `PD_20-21`.`File`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `PD_20-21`.`File` (
  `idFile` INT NOT NULL AUTO_INCREMENT,
  `Directory` VARCHAR(45) NOT NULL,
  `FileName` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`idFile`));


-- -----------------------------------------------------
-- Table `PD_20-21`.`Channel_has_User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `PD_20-21`.`Channel_has_User` (
  `idChannel` INT NOT NULL,
  `idUser` INT NOT NULL,
  PRIMARY KEY (`idChannel`, `idUser`),
  CONSTRAINT `fk_Channel_has_User_Channel`
    FOREIGN KEY (`idChannel`)
    REFERENCES `PD_20-21`.`Channel` (`idChannel`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Channel_has_User_User1`
    FOREIGN KEY (`idUser`)
    REFERENCES `PD_20-21`.`User` (`idUser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table `PD_20-21`.`SentMessageChannel`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `PD_20-21`.`SentMessageChannel` (
  `idSentMessageChannel` INT NOT NULL AUTO_INCREMENT,
  `idUser` INT NOT NULL,
  `idChannel` INT NOT NULL,
  `idMessage` INT NOT NULL,
  PRIMARY KEY (`idSentMessageChannel`),
  CONSTRAINT `fk_User_has_Channel_User1`
    FOREIGN KEY (`idUser`)
    REFERENCES `PD_20-21`.`User` (`idUser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_User_has_Channel_Channel1`
    FOREIGN KEY (`idChannel`)
    REFERENCES `PD_20-21`.`Channel` (`idChannel`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_SentMessageChannel_Message1`
    FOREIGN KEY (`idMessage`)
    REFERENCES `PD_20-21`.`Message` (`idMessage`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table `PD_20-21`.`SentMessageUser`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `PD_20-21`.`SentMessageUser` (
  `idSentMessageuser` INT NOT NULL AUTO_INCREMENT,
  `idMessage` INT NOT NULL,
  `idSender` INT NOT NULL,
  `idReceiver` INT NOT NULL,
  PRIMARY KEY (`idSentMessageuser`),
  CONSTRAINT `fk_User_has_User_Message1`
    FOREIGN KEY (`idMessage`)
    REFERENCES `PD_20-21`.`Message` (`idMessage`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_SentMessageUser_User1`
    FOREIGN KEY (`idSender`)
    REFERENCES `PD_20-21`.`User` (`idUser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_SentMessageUser_User2`
    FOREIGN KEY (`idReceiver`)
    REFERENCES `PD_20-21`.`User` (`idUser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table `PD_20-21`.`SentFileUser`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `PD_20-21`.`SentFileUser` (
  `idSentFileUser` INT NOT NULL AUTO_INCREMENT,
  `File_idFile` INT NOT NULL,
  `idSender` INT NOT NULL,
  `idReceiver` INT NOT NULL,
  PRIMARY KEY (`idSentFileUser`),
  CONSTRAINT `fk_SentFileUser_File1`
    FOREIGN KEY (`File_idFile`)
    REFERENCES `PD_20-21`.`File` (`idFile`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_SentFileUser_User1`
    FOREIGN KEY (`idSender`)
    REFERENCES `PD_20-21`.`User` (`idUser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_SentFileUser_User2`
    FOREIGN KEY (`idReceiver`)
    REFERENCES `PD_20-21`.`User` (`idUser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table `PD_20-21`.`SentFileChannel`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `PD_20-21`.`SentFileChannel` (
  `idSentFileChannel` INT NOT NULL AUTO_INCREMENT,
  `idFile` INT NOT NULL,
  `idChannel` INT NOT NULL,
  `User_idUser` INT NOT NULL,
  PRIMARY KEY (`idSentFileChannel`),
  CONSTRAINT `fk_File_has_Channel_File1`
    FOREIGN KEY (`idFile`)
    REFERENCES `PD_20-21`.`File` (`idFile`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_File_has_Channel_Channel1`
    FOREIGN KEY (`idChannel`)
    REFERENCES `PD_20-21`.`Channel` (`idChannel`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_SentFileChannel_User1`
    FOREIGN KEY (`User_idUser`)
    REFERENCES `PD_20-21`.`User` (`idUser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
