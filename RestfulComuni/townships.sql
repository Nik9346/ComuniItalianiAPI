-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Giu 23, 2023 alle 07:14
-- Versione del server: 10.4.24-MariaDB
-- Versione PHP: 7.4.29

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `townships`
--
CREATE DATABASE IF NOT EXISTS `townships` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `townships`;

-- --------------------------------------------------------

--
-- Struttura della tabella `comuni`
--

CREATE TABLE `comuni` (
  `id` int(11) NOT NULL,
  `nome` varchar(255) NOT NULL,
  `codice_catastale` varchar(4) NOT NULL,
  `provincia` varchar(10) NOT NULL,
  `cap` varchar(5) NOT NULL,
  `prefisso` varchar(4) NOT NULL,
  `mail` varchar(255) NOT NULL,
  `pec` varchar(255) NOT NULL,
  `telefono` varchar(30) NOT NULL,
  `fax` varchar(30) NOT NULL,
  `id_coordinata` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `comuni`
--

INSERT INTO `comuni` (`id`, `nome`, `codice_catastale`, `provincia`, `cap`, `prefisso`, `mail`, `pec`, `telefono`, `fax`, `id_coordinata`) VALUES
(1, 'Roma', 'H501', '058', '00186', '06', 'ld.gabinetto@comune.roma.it', 'protocollo.segretariato@pec.comune.roma.it', '+39 06/67102001', '+39 06/67103590', 1),
(2, 'Milano', 'F205', '015', '20141', '02', 'stl.informagiovani@comune.milano.it', 'protocollo@postacert.comune.milano.it', '+39 02 0202', '+39 02 88452170', 2),
(3, 'Napoli', 'F839', '063', '80127', '081', 'sindaco@comune.napoli.it', 'protocollo@pec.comune.napoli.it', '+39 081 7951111', '+39 081 7955509', 3);

-- --------------------------------------------------------

--
-- Struttura della tabella `coordinate`
--

CREATE TABLE `coordinate` (
  `id` int(11) NOT NULL,
  `lat` double NOT NULL,
  `lng` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `coordinate`
--

INSERT INTO `coordinate` (`id`, `lat`, `lng`) VALUES
(1, 41.8930549621582, 12.48277759552002),
(2, 45.46694564819336, 9.1899995803833),
(3, 40.83333206176758, 14.25);

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `comuni`
--
ALTER TABLE `comuni`
  ADD PRIMARY KEY (`id`),
  ADD KEY `coordinata` (`id_coordinata`);

--
-- Indici per le tabelle `coordinate`
--
ALTER TABLE `coordinate`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `comuni`
--
ALTER TABLE `comuni`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT per la tabella `coordinate`
--
ALTER TABLE `coordinate`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `comuni`
--
ALTER TABLE `comuni`
  ADD CONSTRAINT `coordinata` FOREIGN KEY (`id_coordinata`) REFERENCES `coordinate` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
