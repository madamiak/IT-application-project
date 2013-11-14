<?php

class Curl_core {

    private $ch;
    private $cookiePath;
    private $follow = true;
    private $dirName;
    private $userAgent = "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.0; SLCC1)";
    private $returnHeader = 0;
    private $returnTransfer = true;
    private $debugContent = "";
	private $useCookie = true;

    private $cdi;

    public function __construct($loadWithCodeIgniter = true) {

		$this->dirName = dirname($_SERVER['SCRIPT_FILENAME']) . "/";
		$this->setUseCookie(false);
    }

    public function setFollowLocation($followMode) {
        $this->follow = $followMode;
    }

    public function setCookieName($cookieName) {
        $this->cookiePath = $this->dirName . $cookieName;
    }

	public function setUseCookie($useIt) {
		$this->useCookie = $useIt;
	}
	
    public function setReturnHeader($state) {
        if ($state)
            $this->returnHeader = 1;
        else
            $this->returnHeader = 0;
    }

    public function setReturnTransfer($state) {
        $this->returnTransfer = $state;
    }

    public function convertToUTF8($content_type, $data) {

        preg_match( '@([\w/+]+)(;\s*charset=(\S+))?@i', $content_type, $matches );
        if ( isset( $matches[3] ) )
            $charset = $matches[3];

        if (!isset($charset)) {
            preg_match( '@<meta\s+http-equiv="Content-Type"\s+content="([\w/]+)(;\s*charset=([^\s"]+))?@i', $data, $matches );
            if ( isset( $matches[3] ) )
                $charset = $matches[3];
        }

        if (!isset($charset)) {
            preg_match( '@<\?xml.+encoding="([^\s"]+)@si', $data, $matches );
            if ( isset( $matches[1] ) )
                $charset = $matches[1];
        }

        if (!isset($charset)) {
            $encoding = mb_detect_encoding($data);
            if ($encoding)
                $charset = $encoding;
        }

        if (!isset($charset)) {
            if (strstr($content_type, "text/html") === 0)
                $charset = "ISO 8859-1";
        }

        if (isset($charset) && strtoupper($charset) != "UTF-8")
            $data = iconv($charset, 'UTF-8', $data);
        
        return $data;
    }
    
    public function initCurl() {
        $this->ch = curl_init();
    }

    //opakowanie standardowej metody CURL'a w metodę
    public function getUrl($url, $postdata = "", $timeout = 180000, $referer = "") {

        if ($this->ch == null) {
            $this->initCurl();
        }
		
        //ustalanie parametrów połączenia
        curl_setopt($this->ch, CURLOPT_USERAGENT, $this->userAgent);
        curl_setopt($this->ch, CURLOPT_URL, $url);

		if ($this->useCookie) {
			curl_setopt($this->ch, CURLOPT_COOKIEFILE, $this->cookiePath);
			curl_setopt($this->ch, CURLOPT_COOKIEJAR, $this->cookiePath);
		}

        curl_setopt($this->ch, CURLOPT_FOLLOWLOCATION, $this->follow);
        curl_setopt($this->ch, CURLOPT_ENCODING, 'gzip,deflate');

        curl_setopt($this->ch, CURLOPT_HEADER, $this->returnHeader);
        curl_setopt($this->ch, CURLOPT_RETURNTRANSFER, $this->returnTransfer);

        curl_setopt($this->ch, CURLOPT_FRESH_CONNECT, 1);

        curl_setopt($this->ch, CURLOPT_SSL_VERIFYPEER, false);
        curl_setopt($this->ch, CURLOPT_CONNECTTIMEOUT, $timeout);
        curl_setopt($this->ch, CURLOPT_TIMEOUT, $timeout);
        curl_setopt($this->ch, CURLOPT_MAXREDIRS, 10);

        if ($postdata != "") {  //możliwość wysłania parametrów POSTem
            curl_setopt($this->ch, CURLOPT_POST, 1);
            curl_setopt($this->ch, CURLOPT_POSTFIELDS, $postdata);
        }

        if ($referer != "") {   //możliwość podmiany referera (skąd pochodzi zapytanie)
            curl_setopt($this->ch, CURLOPT_REFERER, $referer);
        } else {
            curl_setopt($this->ch, CURLOPT_AUTOREFERER, true);
        }

        //pobranie źródła strony inernetowej
        $this->debugContent = curl_exec($this->ch);
        $content_type = curl_getinfo($this->ch, CURLINFO_CONTENT_TYPE);

        $this->closeCurl();
        
        //konwersja kodowania na UTF8
        $encodedSource = $this->convertToUTF8($content_type, $this->debugContent);
        
        return $encodedSource;
    }

    public function closeCurl() {
        curl_close($this->ch);
        $this->ch = null;
    }

    public function eraseCookie() {
        file_put_contents($this->cookiePath, "");
    }

    public function getCookie() {
        return "";
        if (file_exists($this->cookiePath)) {
            $content = file_get_contents($this->cookiePath, FILE_USE_INCLUDE_PATH);
            return $content;
        }
        else
            return "";
    }

    public function setCookie($cookieContent) {
        file_put_contents($this->cookiePath, $cookieContent);
    }

    public function printCookie() {
        $this->pr($this->getCookie());
    }
}

?>