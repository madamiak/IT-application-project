-- phpMyAdmin SQL Dump
-- version 3.4.5
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Czas wygenerowania: 11 Lis 2013, 20:33
-- Wersja serwera: 5.5.16
-- Wersja PHP: 5.3.8

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Baza danych: `hotels`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla  `data`
--

CREATE TABLE IF NOT EXISTS `data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `hotel_id` int(10) unsigned DEFAULT NULL,
  `name` text COLLATE utf8_polish_ci,
  `price_min` int(10) unsigned DEFAULT NULL,
  `price_max` int(10) unsigned DEFAULT NULL,
  `lat` double DEFAULT NULL,
  `long` double DEFAULT NULL,
  `rating` smallint(5) unsigned DEFAULT NULL,
  `stars` smallint(6) DEFAULT NULL,
  `phone` varchar(30) COLLATE utf8_polish_ci DEFAULT NULL,
  `fax` varchar(30) COLLATE utf8_polish_ci DEFAULT NULL,
  `contact` text COLLATE utf8_polish_ci,
  `description` text COLLATE utf8_polish_ci,
  `kind` text COLLATE utf8_polish_ci,
  `rooms` int(11) DEFAULT NULL,
  `check_in` varchar(10) COLLATE utf8_polish_ci DEFAULT NULL,
  `check_out` varchar(10) COLLATE utf8_polish_ci DEFAULT NULL,
  `payment` text COLLATE utf8_polish_ci,
  `target` text COLLATE utf8_polish_ci,
  `comforts` text COLLATE utf8_polish_ci,
  `equipment` text COLLATE utf8_polish_ci,
  `sports` text COLLATE utf8_polish_ci,
  `offerUrl` text COLLATE utf8_polish_ci,
  `infoUrl` text COLLATE utf8_polish_ci,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci AUTO_INCREMENT=4026 ;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
