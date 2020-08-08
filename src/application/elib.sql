-- phpMyAdmin SQL Dump
-- version 4.8.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 17, 2019 at 03:32 PM
-- Server version: 10.1.34-MariaDB
-- PHP Version: 5.6.37

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `elib`
--

-- --------------------------------------------------------

--
-- Table structure for table `book`
--

CREATE TABLE `book` (
  `isbn` int(11) NOT NULL,
  `title` varchar(100) NOT NULL,
  `author` varchar(200) NOT NULL,
  `price` int(11) NOT NULL,
  `cover` varchar(200) DEFAULT NULL,
  `Description` varchar(2000) DEFAULT NULL,
  `image1` varchar(200) DEFAULT NULL,
  `image2` varchar(200) DEFAULT NULL,
  `paths` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `book`
--

INSERT INTO `book` (`isbn`, `title`, `author`, `price`, `cover`, `Description`, `image1`, `image2`, `paths`) VALUES
(2, 'Chemistry class 12 part1', 'NCERT', 50, 'file:///C:/Users/Ashish Goel/eclipse-workspace/e_library/src/application/images/chem 12 part1.jpg', 'bibibisdfcgvhbjnkm,   ghjkl  nmml  ', 'file:///C:/Users/Ashish Goel/eclipse-workspace/e_library/src/application/images/chem 1.png', 'file:///C:/Users/Ashish Goel/eclipse-workspace/e_library/src/application/images/chem.jpg', 'https://www.flexiprep.com/d/pdf/9528b3c3/NCERT-Class-12-Chemistry-Part-1.pdf'),
(3, 'Chem 11 part1', 'NCERT', 30, 'file:///C:/Users/Ashish Goel/eclipse-workspace/e_library/src/application/images/chem 11 part1.jpg', 'Chemisrty Part1 for class 11 students', 'file:///C:/Users/Ashish Goel/eclipse-workspace/e_library/src/application/images/chem 1.png', 'file:///C:/Users/Ashish Goel/eclipse-workspace/e_library/src/application/images/chem.jpg', 'https://www.flexiprep.com/d/pdf/3046f8fc/NCERT-Class-11-Chemistry-Part-1.pdf'),
(4, 'Chem 11 part2', 'NCERT', 40, 'file:///C:/Users/Ashish Goel/eclipse-workspace/e_library/src/application/images/chem 11 part2.jpg', 'Chemisrty book for class 11 students Part2', 'file:///C:/Users/Ashish Goel/eclipse-workspace/e_library/src/application/images/chem.jpg', 'file:///C:/Users/Ashish Goel/eclipse-workspace/e_library/src/application/images/chem 1.png', 'https://www.flexiprep.com/d/pdf/1a6e9f6d/NCERT-Class-11-Chemistry-Part-2.pdf'),
(5, 'Maths 11', 'NCERT', 60, 'file:///C:/Users/Ashish Goel/eclipse-workspace/e_library/src/application/images/math 11.jpg', 'Maths Book for class 11 students', 'file:///C:/Users/Ashish Goel/eclipse-workspace/e_library/src/application/images/math1.jpg', 'file:///C:/Users/Ashish Goel/eclipse-workspace/e_library/src/application/images/math2.jpg', 'https://www.flexiprep.com/d/pdf/c57e7fd2/NCERT-Class-11-Mathematics.pdf'),
(6, 'Maths 12 part1', 'NCERT', 70, 'file:///C:/Users/Ashish Goel/eclipse-workspace/e_library/src/application/images/math 12 part1.jpg', 'Maths Book for class 12 students part1', 'file:///C:/Users/Ashish Goel/eclipse-workspace/e_library/src/application/images/math1.jpg', 'file:///C:/Users/Ashish Goel/eclipse-workspace/e_library/src/application/images/math2.jpg', 'https://www.flexiprep.com/d/pdf/da25bfd9/NCERT-Class-12-Mathematics-Part-1.pdf'),
(7, 'Maths 12 part2', 'NCERT', 70, 'file:///C:/Users/Ashish Goel/eclipse-workspace/e_library/src/application/images/maths 12 part2.jpg', 'Maths book for class 12 students part2', 'file:///C:/Users/Ashish Goel/eclipse-workspace/e_library/src/application/images/math2.jpg', 'file:///C:/Users/Ashish Goel/eclipse-workspace/e_library/src/application/images/math1.jpg', 'https://www.flexiprep.com/d/pdf/5b9c4b92/NCERT-Class-12-Mathematics-Part-2.pdf');

-- --------------------------------------------------------

--
-- Table structure for table `cart`
--

CREATE TABLE `cart` (
  `userid` int(11) NOT NULL,
  `isbn` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `orderid` int(11) NOT NULL,
  `userid` int(11) NOT NULL,
  `isbn` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`orderid`, `userid`, `isbn`) VALUES
(1, 1, 1),
(2, 1, 2);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `userid` int(11) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `pass` varchar(50) DEFAULT NULL,
  `phone` char(10) NOT NULL DEFAULT '1234567890',
  `email` varchar(100) NOT NULL DEFAULT 'aa@gmail.com',
  `ccn` char(16) NOT NULL DEFAULT '1234567893692581',
  `balance` int(11) NOT NULL DEFAULT '2000'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`userid`, `name`, `pass`, `phone`, `email`, `ccn`, `balance`) VALUES
(1, 'aashish', 'aashish', '1234567890', 'aa123@gmail.com', '1234567893692581', 2000),
(3, 'Aashish', 'aashish', '147852', 'aa@gmail.com', '14785239', 2000),
(4, 'aa', 'aas', '78', 'ass@gmail.com', '789456', 2000),
(5, 'aas', 'asd', '123', 'aas@gmailc.o', '1478', 2000);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `book`
--
ALTER TABLE `book`
  ADD PRIMARY KEY (`isbn`);

--
-- Indexes for table `cart`
--
ALTER TABLE `cart`
  ADD PRIMARY KEY (`userid`,`isbn`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`orderid`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`userid`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `book`
--
ALTER TABLE `book`
  MODIFY `isbn` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `orderid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `userid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
