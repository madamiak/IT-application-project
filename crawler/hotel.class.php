<?php
	class Hotel {
	
		private $attributes = array();
		
		public function setAttr($name, $value) {
			$this->attributes[$name] = $value;
		}
		
		public function getAttrList() {
			return $this->attributes;
		}
		
		public function getAttr($name) {
			return (isSet($this->attributes[$name]) ? $this->attributes[$name] : "");
		}
		
		public function getAttrSql($name, $isText=false) {
			$value = mysql_real_escape_string($this->getAttr($name));
			if ($value=="")
				return "NULL";
			if ($isText)
				return "'$value'";
			return $value;
		}
	}
?>