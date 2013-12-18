DROP TABLE `ts`.`point_list`;
CREATE TABLE IF NOT EXISTS `ts`.`point_list` (
  `route_ID` INT NOT NULL,
  `point_ID` INT NOT NULL,
  `point_index` INT NOT NULL,
  UNIQUE INDEX `number_UNIQUE` (`point_index` ASC),
  INDEX `fk_point_list_point1_idx` (`point_ID` ASC),
  INDEX `fk_point_list_route1_idx` (`route_ID` ASC),
  PRIMARY KEY (`route_ID`, `point_index`),
  CONSTRAINT `fk_point_list_point1`
    FOREIGN KEY (`point_ID`)
    REFERENCES `ts`.`point` (`idpoints`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_point_list_route1`
    FOREIGN KEY (`route_ID`)
    REFERENCES `ts`.`route` (`id_route`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB