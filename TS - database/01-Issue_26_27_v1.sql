SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

ALTER TABLE `ts`.`hotel_parameters` DROP FOREIGN KEY `fk_hotel_parameters_point1` ;

ALTER TABLE `ts`.`favourite_route` CHANGE COLUMN `rating` `rating` DECIMAL(1) NULL DEFAULT NULL  ;

ALTER TABLE `ts`.`hotel_parameters` CHANGE COLUMN `point_type` `idpoints` INT(11) NULL DEFAULT NULL  , 
  ADD CONSTRAINT `fk_hotel_parameters_point1`
  FOREIGN KEY (`idpoints` )
  REFERENCES `ts`.`point` (`idpoints` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
, ADD INDEX `fk_hotel_parameters_point1_idx` (`idpoints` ASC) 
, DROP INDEX `fk_hotel_parameters_point1_idx` ;

UPDATE `ts`.`hotel_parameters` SET `idpoints`= `ts`.`hotel_parameters`.`id_hotel` ;

CREATE  TABLE IF NOT EXISTS `ts`.`image` (
  `idimage` INT(11) NOT NULL AUTO_INCREMENT ,
  `url` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`idimage`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

CREATE  TABLE IF NOT EXISTS `ts`.`point_image` (
  `idpoint` INT(11) NOT NULL ,
  `idimage` INT(11) NOT NULL ,
  INDEX `fk_point_image_point1_idx` (`idpoint` ASC) ,
  INDEX `fk_point_image_image1_idx` (`idimage` ASC) ,
  CONSTRAINT `fk_point_image_point1`
    FOREIGN KEY (`idpoint` )
    REFERENCES `ts`.`point` (`idpoints` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_point_image_image1`
    FOREIGN KEY (`idimage` )
    REFERENCES `ts`.`image` (`idimage` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
