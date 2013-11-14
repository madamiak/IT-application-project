<?php
	function replace_unicode_escape_sequence($match) {
		return mb_convert_encoding(pack('H*', $match[1]), 'UTF-8', 'UCS-2BE');
	}
	
	class Crawler {

		private $regexpCustomSet = array(
			'main_groups' => '/fLatitude(.*?)nose_inner_area/smx',
			'li_splitter' =>  '/<li.*?>(.*?),{0,1}<\/li>/s',
		);
		private $regexpHotelSet = array(
			'id' => "/iItemId[^0-9]+([0-9\.]+)/s",
			'name' => "/alt=\"(.*?)\"/s",
			'price_min' => "/price_min[^0-9]+([0-9]+)/s",
			'price_max' => "/price_max[^0-9]+([0-9]+)/s",
			'lat' => "/([0-9\.]+).{1,20}fLongitude/s",
			'long' => "/fLongitude[^0-9]+([0-9\.]+)/s",
			'rating' => "/rating_icon.*?g>([0-9\.]+)/s",
			'stars' => "/category.cat([0-9])/s",
		);
		private $regexpInfoHotelSet = array(
			'phone' => "/Telefon:([^<]+)/s",
			'fax' => "/Fax:([^<]+)/s",
			'contact' => "/h3>.{0,20}Kontakt.*?em>([^<]+)<\/em>/s",
			'description' => "/data-fulltext=.([^\"]+)/s",
			'kind' => "/em>.{0,20}Rodzaj.zakwaterowania.*?t\">([^<]+)/s",
			'rooms' => "/em>.{0,20}pokoi:.*?t\">([^<]+)/s",
			'check_in' => "/em>.{0,30}Check-in.*?t\">([^<]+)/s",
			'check_out' => "/em>.{0,30}Check-out.*?t\">([^<]+)/s",
			'payment' => "/block.payment.*?<ul[^>]+(.*?)<\/ul/s",
			'target' => "/block.suitablefor.*?<ul[^>]+(.*?)<\/ul/s",
			'comforts' => "/block.top9features.*?<ul[^>]+(.*?)<\/ul/s",
			'equipment' => "/block.room_facilities.*?<ul[^>]+(.*?)<\/ul/s",
			'sports' => "/block.sports.*?<ul[^>]+(.*?)<\/ul/s",
		);
		private $offersOnPage = 25;
		private $maxOffers = 4000;
		
		private $hotelList = array();
		
		public function __construct() {
		
			$this->baseUrl = "http://www.trivago.pl/".
				"?aCategoryRange=5,4,3,2,1".
				"&aOverallLiking=1,2,3,4,5".
				"&aPathList=86484".
				"&aRooms=".
				"&iRequestIdent=0".
				"&bIsSeoPage=false".
				"&sError=".
				"&sFilterEdit=".
				"&sMapNavigation=[object%20Object]".
				"&sPoiSearch=31480::::::0".
				"&sPathName=polska".
				"&bIsCity=false".
				"&iTotalCount=4000".
				"&iResultCount=".
				"&iPathId=31480".
				"&iIncludeAll=1".
				"&bTopDealsOnly=false".
				"&iClickoutType=1".
				"&iLimit=25".
				"&iRoomType=7".
				"&sLeftBarState=navigation".
				"&sQueryPathName=Polska".
				"&iOffset=#OFFSET#".
				"&iViewType=0".
				"&sOrderBy=relevance%20desc".
				"&bSharedRooms=false".
				"&bIsHotelTestHotel=false".
				"&aDateRange%5Barr%5D=2013-01-01".
				"&aDateRange%5Bdep%5D=2015-12-30".
				"&aDefaultDateRange%5Barr%5D=2013-01-01".
				"&aDefaultDateRange%5Bdep%5D=2015-12-30".
				"&aGeoCode%5Blng%5D=19.145136".
				"&aGeoCode%5Blat%5D=51.919437".
				"&aPriceRange%5Bfrom%5D=0".
				"&aPriceRange%5Bto%5D=0".
				"&aPartner=&&".
				"&bIsMysteryGuestOnly=false".
				"&bIsTotalPrice=true".
				"&iMemberProfileId=0".
				"&bIsSitemap=false".
				"&bDispMoreFilter=false".
				"&bHotelTestContext=false".
				"&aHotelTestClassifier=".
				"&aMapViewport=".
				"&aRecommendedBy=".
				"&iRequestIdent=1".
				"&bIsFirstPollBySearch=1".
				"&_=1381890704738".
				"&sLog=0%3A31480%7C100%3A2155%3A2";
			
			$this->baseInfoUrl = 
				"http://www.trivago.pl/search/slideout/pl-PL-PL/v7_11_1_jd_bt0_cache/info/".
				"#HOTELID#.html".
				"?aDateRange[arr]=2000-12-01".
				"&aDateRange[dep]=2015-12-02".
				"&iRoomType=7";
				
			$this->baseOfferUrl = "http://www.trivago.pl/".
				"?aDateRange%5Barr%5D=2000-12-01".
				"&aDateRange%5Bdep%5D=2015-12-02".
				"&iRoomType=7".
				"&iViewType=0".
				"&iGeoDistanceItem=#HOTELID#".
				"&iPathId=86484";
			
			$this->curl_core = new Curl_core(false);
		}

		private function getNextSiteUrl($pageNumber) {
			return str_replace("#OFFSET#", ($pageNumber * $this->offersOnPage ), $this->baseUrl);
		}
		private function getNextPriceUrl($pageNumber) {
			return str_replace("#OFFSET#", ($pageNumber * $this->offersOnPage ), $this->baseUrl);
		}
		private function getInfoSiteUrl($hotelId) {
			return str_replace("#HOTELID#", $hotelId, $this->baseInfoUrl);
		}
		private function getOfferUrl($hotelId) {
			return str_replace("#HOTELID#", $hotelId, $this->baseOfferUrl);
		}
		
		private function extractAllValues(&$source, $regexp, $offset = 0) {
			if ($regexp != "") {
				if (preg_match_all($regexp, $source, $matches) !== false) {
					$result = (count($matches)>1 ? array() : "");
					for ($i=0; $i<count($matches[1]); $i++) {
						$result[] = (isSet($matches[1][$i]) ? trim($matches[1][$i]) : "");
					}
					return $result;
				}
			}
			return array();
		}
		
		private function extractValue(&$source, $regexp) {
			if ($regexp != "") {
				if (preg_match($regexp, $source, $match) !== false) {
					return (count($match)>1 ? trim($match[1]) : "");
				}
			}
			return "";
		}

		private function replaceEncoding($str) {
			$result = preg_replace_callback('/\\\\u([0-9a-f]{4})/i', 'replace_unicode_escape_sequence', $str);
			return stripslashes($result);
		}
		
		public function retriveData() {
		
			for ($pageNumber=0; $pageNumber<20; $pageNumber++) {
			
				$siteUrl = $this->getNextSiteUrl($pageNumber);

				do {
					$source = $this->curl_core->getUrl($siteUrl);
					$__groups = $this->extractAllValues($source, $this->regexpCustomSet['main_groups']);
				} while (
					count($__groups) == 0 ||
					strpos($source, "prices_container") === false
				);
				
				for ($i=0; $i<count($__groups); $i++) {
					$__groups[$i] = $this->replaceEncoding($__groups[$i]);
				}
				
				foreach ($__groups as $group) {
					
					$hotel = new Hotel();
					
					foreach ($this->regexpHotelSet as $key => $regexpHotel) {
						$value = $this->extractValue($group, $regexpHotel);
						$hotel->setAttr($key, $value);
					}
					
					$infoSiteUrl = $this->getInfoSiteUrl( $hotel->getAttr('id') );
					$infoSource = $this->curl_core->getUrl($infoSiteUrl);
					foreach ($this->regexpInfoHotelSet as $key => $regexpInfoHotel) {
						$value = $this->extractValue($infoSource, $regexpInfoHotel);
						
						if ($value!="" && strpos($value, "</li>")!==false) {
							
							$list = $this->extractAllValues($value, $this->regexpCustomSet['li_splitter']);
							$value = "";
							
							foreach ($list as $item) {
								$value .= ($value!=""?"|":"").trim(strip_tags($item));
							}
						}
						
						$hotel->setAttr($key, $value);
					}
					
					$hotel->setAttr('offerUrl', $this->getOfferUrl( $hotel->getAttr('id') ));
					$hotel->setAttr('infoUrl', $infoSiteUrl);
					
					$this->hotelList[] = $hotel;
				}
			}
			//echo "</pre>";
		}
		
		public function getHotelList() {
			return $this->hotelList;
		}
	}
?>