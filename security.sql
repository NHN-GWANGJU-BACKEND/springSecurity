-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema dbApp2
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema dbApp2
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `dbApp2` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `dbApp2` ;

-- -----------------------------------------------------
-- Table `dbApp2`.`resident`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dbApp2`.`resident` (
  `resident_serial_number` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `resident_registration_number` VARCHAR(300) NOT NULL,
  `gender_code` VARCHAR(20) NOT NULL,
  `birth_date` DATETIME NOT NULL,
  `birth_place_code` VARCHAR(20) NOT NULL,
  `registration_base_address` VARCHAR(500) NOT NULL,
  `death_date` DATETIME NULL DEFAULT NULL,
  `death_place_code` VARCHAR(20) NULL DEFAULT NULL,
  `death_place_address` VARCHAR(500) NULL DEFAULT NULL,
  `login_id` VARCHAR(100) NOT NULL,
  `login_pwd` VARCHAR(500) NOT NULL,
  `email` VARCHAR(100) NULL,
  PRIMARY KEY (`resident_serial_number`))
ENGINE = InnoDB
AUTO_INCREMENT = 10
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `dbApp2`.`birth_death_report_resident`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dbApp2`.`birth_death_report_resident` (
  `resident_serial_number` INT NOT NULL,
  `birth_death_type_code` VARCHAR(20) NOT NULL,
  `report_resident_serial_number` INT NOT NULL,
  `birth_death_report_date` DATE NOT NULL,
  `birth_report_qualifications_code` VARCHAR(20) NULL DEFAULT NULL,
  `death_report_qualifications_code` VARCHAR(20) NULL DEFAULT NULL,
  `email_address` VARCHAR(50) NULL DEFAULT NULL,
  `phone_number` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`resident_serial_number`, `birth_death_type_code`),
  INDEX `fk_birth_death_report_resident_resident1_idx` (`resident_serial_number` ASC) VISIBLE,
  INDEX `fk_birth_death_report_resident_resident2_idx` (`report_resident_serial_number` ASC) VISIBLE,
  CONSTRAINT `fk_birth_death_report_resident_resident1`
    FOREIGN KEY (`resident_serial_number`)
    REFERENCES `dbApp2`.`resident` (`resident_serial_number`),
  CONSTRAINT `fk_birth_death_report_resident_resident2`
    FOREIGN KEY (`report_resident_serial_number`)
    REFERENCES `dbApp2`.`resident` (`resident_serial_number`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `dbApp2`.`certificate_issue`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dbApp2`.`certificate_issue` (
  `certificate_confirmation_number` BIGINT NOT NULL AUTO_INCREMENT,
  `resident_serial_number` INT NOT NULL,
  `certificate_type_code` VARCHAR(20) NOT NULL,
  `certificate_issue_date` DATE NOT NULL,
  PRIMARY KEY (`certificate_confirmation_number`),
  INDEX `fk_certificate_issue_resident1_idx` (`resident_serial_number` ASC) VISIBLE,
  CONSTRAINT `fk_certificate_issue_resident1`
    FOREIGN KEY (`resident_serial_number`)
    REFERENCES `dbApp2`.`resident` (`resident_serial_number`))
ENGINE = InnoDB
AUTO_INCREMENT = 9876543210987743
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `dbApp2`.`family_relationship`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dbApp2`.`family_relationship` (
  `family_resident_serial_number` INT NOT NULL,
  `base_resident_serial_number` INT NOT NULL,
  `family_relationship_code` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`family_resident_serial_number`, `base_resident_serial_number`),
  INDEX `fk_family_relationship_resident_idx` (`base_resident_serial_number` ASC) VISIBLE,
  CONSTRAINT `fk_family_relationship_resident`
    FOREIGN KEY (`base_resident_serial_number`)
    REFERENCES `dbApp2`.`resident` (`resident_serial_number`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `dbApp2`.`household`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dbApp2`.`household` (
  `household_serial_number` INT NOT NULL AUTO_INCREMENT,
  `household_resident_serial_number` INT NOT NULL,
  `household_composition_date` DATE NOT NULL,
  `household_composition_reason_code` VARCHAR(20) NOT NULL,
  `current_house_movement_address` VARCHAR(500) NOT NULL,
  PRIMARY KEY (`household_serial_number`),
  INDEX `fk_household_resident1_idx` (`household_resident_serial_number` ASC) VISIBLE,
  CONSTRAINT `fk_household_resident1`
    FOREIGN KEY (`household_resident_serial_number`)
    REFERENCES `dbApp2`.`resident` (`resident_serial_number`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `dbApp2`.`household_composition_resident`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dbApp2`.`household_composition_resident` (
  `household_serial_number` INT NOT NULL,
  `resident_serial_number` INT NOT NULL,
  `report_date` DATE NOT NULL,
  `household_relationship_code` VARCHAR(20) NOT NULL,
  `household_composition_change_reason_code` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`household_serial_number`, `resident_serial_number`),
  INDEX `fk_household_composition_resident_household1_idx` (`household_serial_number` ASC) VISIBLE,
  INDEX `fk_household_composition_resident_resident1_idx` (`resident_serial_number` ASC) VISIBLE,
  CONSTRAINT `fk_household_composition_resident_household1`
    FOREIGN KEY (`household_serial_number`)
    REFERENCES `dbApp2`.`household` (`household_serial_number`),
  CONSTRAINT `fk_household_composition_resident_resident1`
    FOREIGN KEY (`resident_serial_number`)
    REFERENCES `dbApp2`.`resident` (`resident_serial_number`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `dbApp2`.`household_movement_address`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dbApp2`.`household_movement_address` (
  `house_movement_report_date` DATE NOT NULL,
  `household_serial_number` INT NOT NULL,
  `house_movement_address` VARCHAR(500) NOT NULL,
  `last_address_yn` VARCHAR(1) NOT NULL DEFAULT 'Y',
  PRIMARY KEY (`house_movement_report_date`, `household_serial_number`),
  INDEX `fk_household_movement_address_household1` (`household_serial_number` ASC) VISIBLE,
  CONSTRAINT `fk_household_movement_address_household1`
    FOREIGN KEY (`household_serial_number`)
    REFERENCES `dbApp2`.`household` (`household_serial_number`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


-- 4. birth_death_report_resident 테이블 데이터 추가
insert into birth_death_report_resident values (7, '출생', 4, '20120317', '부', null, 'nam@nhnad.co.kr', '010-1234-5678');
insert into birth_death_report_resident values (1, '사망', 2, '20200502', '비동거친족', null, null, '010-2345-6789');

commit;


-- 5. family_relationship 테이블 데이터 추가
insert into family_relationship values(1, 2, '자녀');
insert into family_relationship values(2, 1, '부');
insert into family_relationship values(2, 3, '배우자');
insert into family_relationship values(2, 4, '자녀');
insert into family_relationship values(3, 2, '배우자');
insert into family_relationship values(3, 4, '자녀');
insert into family_relationship values(4, 2, '부');
insert into family_relationship values(4, 3, '모');
insert into family_relationship values(4, 5, '배우자');
insert into family_relationship values(4, 7, '자녀');
insert into family_relationship values(5, 4, '배우자');
insert into family_relationship values(5, 7, '자녀');
insert into family_relationship values(7, 4, '부');
insert into family_relationship values(7, 5, '모');

commit;

select * from family_relationship;

-- 6. household 테이블 데이터 추가
insert into household values(1, 4, '20091002', '세대분리', '경기도 성남시 분당구 대왕판교로 645번길');

commit;


-- 7. household_movement_address 테이블 데이터 추가
insert into household_movement_address values('20071031', 1, '서울시 동작구 상도로 940번길', 'N');
insert into household_movement_address values('20091031', 1, '경기도 성남시 분당구 불정로 90번길', 'N');
insert into household_movement_address values('20130305', 1, '경기도 성남시 분당구 대왕판교로 645번길', 'Y');

commit;

-- 8. household_composition_resident 테이블 데이터 추가
insert into household_composition_resident values(1, 4, '20091002', '본인', '세대분리');
insert into household_composition_resident values(1, 5, '20100215', '배우자', '전입');
insert into household_composition_resident values(1, 7, '20120317', '자녀', '출생등록');
insert into household_composition_resident values(1, 6, '20151129', '동거인', '전입');

commit;


-- 9. certificate_issue 테이블 데이터 추가
insert into certificate_issue values(1234567891011121, 4, '가족관계증명서', '20211025');
insert into certificate_issue values(1234567891011122, 1, '가족관계증명서', '20211025');
insert into certificate_issue values(1234567891011123, 2, '가족관계증명서', '20211025');
insert into certificate_issue values(1234567891011124, 3, '가족관계증명서', '20211025');
insert into certificate_issue values(1234567891011125, 5, '가족관계증명서', '20211025');
insert into certificate_issue values(1234567891011126, 6, '가족관계증명서', '20211025');
insert into certificate_issue values(1234567891011127, 7, '가족관계증명서', '20211025');
insert into certificate_issue values(9876543210987654, 4, '주민등록등본', '20211025');