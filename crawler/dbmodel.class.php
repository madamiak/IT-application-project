<?php

	class DBModel {
		
		private $connection;
		
		public function __construct() {
		
			$this->connection = new mysqli('127.0.0.1:3306', 'portier', '', 'hotels');
			if (mysqli_connect_error()) {
				die('Connect Error (' . mysqli_connect_errno() . ') '. mysqli_connect_error());
			}
		}
		
		public function storeHotels($hotelList) {
		
			$this->connection->set_charset("utf8");
			
			foreach ($hotelList as $hotel) {
			
				$sql = "
					INSERT INTO `hotels`.`data` VALUES (".
					"null, ".
					$hotel->getAttrSql('id').", ".
					$hotel->getAttrSql('name', true).", ".
					$hotel->getAttrSql('price_min').", ".
					$hotel->getAttrSql('price_max').", ".
					$hotel->getAttrSql('lat').", ".
					$hotel->getAttrSql('long').", ".
					$hotel->getAttrSql('rating').", ".
					$hotel->getAttrSql('stars').", ".
					$hotel->getAttrSql('phone', true).", ".
					$hotel->getAttrSql('fax', true).", ".
					$hotel->getAttrSql('contact', true).", ".
					$hotel->getAttrSql('description', true).", ".
					$hotel->getAttrSql('kind', true).", ".
					$hotel->getAttrSql('rooms', true).", ".
					$hotel->getAttrSql('check_in', true).", ".
					$hotel->getAttrSql('check_out', true).", ".
					$hotel->getAttrSql('payment', true).", ".
					$hotel->getAttrSql('target', true).", ".
					$hotel->getAttrSql('comforts', true).", ".
					$hotel->getAttrSql('equipment', true).", ".
					$hotel->getAttrSql('sports', true).", ".
					$hotel->getAttrSql('offerUrl', true).", ".
					$hotel->getAttrSql('infoUrl', true).")";
					
				if (!$this->connection->query($sql)) {
					printf("Error: %s\n", $this->connection->error);
					//echo "[[$sql]]".print_r($hotel,true)."<br>===================================<br>";
				}
			}
		}
	}
?>