ALTER TABLE `ts`.`point_list` DROP INDEX `number_UNIQUE` ,
ADD UNIQUE INDEX `number_UNIQUE` ( `point_index` ASC , `route_ID` ASC ) 