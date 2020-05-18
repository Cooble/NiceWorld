-- phpMyAdmin SQL Dump
-- version 4.9.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 17, 2020 at 08:08 AM
-- Server version: 10.4.8-MariaDB
-- PHP Version: 7.1.33

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `fb`
--

-- --------------------------------------------------------

--
-- Table structure for table `chats`
--

CREATE TABLE `chats` (
  `id` int(11) NOT NULL,
  `owner` int(11) NOT NULL,
  `name` varchar(250) COLLATE utf8_bin DEFAULT NULL,
  `receiver` int(11) NOT NULL DEFAULT -1
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `chats`
--

INSERT INTO `chats` (`id`, `owner`, `name`, `receiver`) VALUES
(68, 36, NULL, -1),
(69, 37, NULL, -1),
(70, 38, NULL, -1),
(71, 39, NULL, -1),
(72, 39, 'Amadeus', -1),
(73, 38, '38 and 39', 39),
(74, 38, '38 and 37', 37),
(75, 38, '38 and 36', 36),
(77, 36, '36 and 39', 39),
(78, 36, '36 and 37', 37);

-- --------------------------------------------------------

--
-- Table structure for table `chat_members`
--

CREATE TABLE `chat_members` (
  `id` int(11) NOT NULL,
  `chatid` int(11) NOT NULL,
  `userid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `chat_members`
--

INSERT INTO `chat_members` (`id`, `chatid`, `userid`) VALUES
(51, 72, 39),
(52, 72, 38),
(53, 72, 37),
(54, 72, 36);

-- --------------------------------------------------------

--
-- Table structure for table `friendships`
--

CREATE TABLE `friendships` (
  `id` int(11) NOT NULL,
  `userid0` int(11) NOT NULL,
  `userid1` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `friendships`
--

INSERT INTO `friendships` (`id`, `userid0`, `userid1`) VALUES
(58, 39, 36),
(59, 39, 38),
(60, 39, 37),
(63, 38, 37),
(64, 36, 37),
(65, 36, 38);

-- --------------------------------------------------------

--
-- Table structure for table `likes`
--

CREATE TABLE `likes` (
  `id` int(11) NOT NULL,
  `messageid` int(11) NOT NULL,
  `userid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `likes`
--

INSERT INTO `likes` (`id`, `messageid`, `userid`) VALUES
(55, 136, 36),
(56, 136, 38),
(57, 137, 38),
(58, 139, 38),
(59, 138, 38),
(60, 140, 38),
(61, 137, 36),
(62, 152, 36),
(63, 153, 36),
(65, 154, 36),
(66, 155, 36),
(67, 157, 36),
(68, 163, 36),
(69, 0, 36);

-- --------------------------------------------------------

--
-- Table structure for table `messages`
--

CREATE TABLE `messages` (
  `id` int(11) NOT NULL,
  `message` varchar(500) COLLATE utf8_bin NOT NULL,
  `chatid` int(11) DEFAULT NULL,
  `dated` datetime DEFAULT NULL,
  `userid` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `messages`
--

INSERT INTO `messages` (`id`, `message`, `chatid`, `dated`, `userid`) VALUES
(136, 'ImawaWatashiDa!', 72, '2020-03-16 20:23:50', 39),
(137, 'Dareka?', 72, '2020-03-16 20:24:01', 39),
(138, 'Oreda', 72, '2020-03-16 20:24:37', 36),
(139, 'orenonamaewa hououin kyomaDA!', 72, '2020-03-16 20:25:04', 36),
(140, 'Nanjidesuka', 72, '2020-03-16 20:25:23', 36),
(141, 'This is my personal Hacka webu', 70, '2020-03-16 20:27:04', 38),
(142, 'Yushi Maho!', 73, '2020-03-16 20:27:28', 38),
(143, 'Shiranaiyo', 72, '2020-03-16 20:27:44', 38),
(144, 'demo, omoshiroidesuka', 72, '2020-03-16 20:27:54', 38),
(145, 'sonnohennakotowa', 72, '2020-03-16 20:28:15', 38),
(146, 'sonnohennakotowa', 72, '2020-03-16 20:30:00', 38),
(147, 'sonnohennakotowa', 72, '2020-03-16 20:30:10', 38),
(148, 'whatever man, nemui', 72, '2020-03-16 20:30:33', 38),
(149, 'Ohyou', 74, '2020-03-16 20:34:09', 38),
(150, 'all the limbs went missing', 75, '2020-03-16 20:34:20', 38),
(151, 'oh damn', 70, '2020-03-16 20:34:29', 38),
(152, 'the only problem is, that its prone to SQL INJECTION -&gt; daga, jikanjanai!', 70, '2020-03-16 20:35:10', 38),
(153, 'and maybe even html injection as well.. but whatever man', 70, '2020-03-16 20:35:26', 38),
(154, 'but this is fine &lt;br&gt;', 70, '2020-03-16 20:35:34', 38),
(155, 'ha', 70, '2020-03-16 20:35:38', 38),
(156, 'Shotainzugeitonosentakuda!', 72, '2020-03-16 20:37:57', 36),
(157, 'This is maho', 71, '2020-03-17 07:41:32', 39),
(158, 'hohoho', 71, '2020-03-17 07:41:36', 39),
(159, 'hello there', 68, '2020-03-17 07:45:13', 36),
(160, 'it was awesome', 68, '2020-03-17 07:47:25', 36),
(161, 'blblblblb', 68, '2020-03-17 07:47:29', 36),
(162, 'Hello kurisu', 78, '2020-03-17 08:04:55', 36),
(163, 'orenoJoshu', 78, '2020-03-17 08:05:17', 36),
(164, 'JOSHU JANAI YO', 78, '2020-03-17 08:05:58', 37),
(165, '....', 78, '2020-03-17 08:06:04', 37);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `name` varchar(60) COLLATE utf8_bin NOT NULL,
  `password` varchar(250) COLLATE utf8_bin NOT NULL,
  `email` varchar(250) COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `name`, `password`, `email`) VALUES
(36, 'Okabe', '558963c70b0385f5af48cc658d0e9ea6d6f82e933a252a6b4dcadbe3e6da2fb3', ''),
(37, 'KuriGohan', 'edc192a0305787a6e943dbd461e049e8da0bb731b0864192a39a51f929344f11', ''),
(38, 'Daru', 'e23bb834a0d0dd3fd44f692b0c48c835e9a61c71ba2ba123af56f67c09086b6f', ''),
(39, 'Maho', '41b7cac019067b8b14bbdf9d4d03324cf0846299fa4a5d309674fca90232e658', '');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `chats`
--
ALTER TABLE `chats`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `unique_id` (`id`);

--
-- Indexes for table `chat_members`
--
ALTER TABLE `chat_members`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `unique_id` (`id`);

--
-- Indexes for table `friendships`
--
ALTER TABLE `friendships`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `unique_id` (`id`);

--
-- Indexes for table `likes`
--
ALTER TABLE `likes`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `messages`
--
ALTER TABLE `messages`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `unique_id` (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `unique_id` (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `chats`
--
ALTER TABLE `chats`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=80;

--
-- AUTO_INCREMENT for table `chat_members`
--
ALTER TABLE `chat_members`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=58;

--
-- AUTO_INCREMENT for table `friendships`
--
ALTER TABLE `friendships`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=66;

--
-- AUTO_INCREMENT for table `likes`
--
ALTER TABLE `likes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=71;

--
-- AUTO_INCREMENT for table `messages`
--
ALTER TABLE `messages`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=167;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=40;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
