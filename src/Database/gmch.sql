-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 09, 2021 at 07:18 AM
-- Server version: 10.4.17-MariaDB
-- PHP Version: 8.0.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `gmch`
--

-- --------------------------------------------------------

--
-- Table structure for table `adoption`
--

CREATE TABLE `adoption` (
  `ParentID` int(10) NOT NULL,
  `Name` varchar(30) NOT NULL,
  `Age` int(10) NOT NULL,
  `Occupation` varchar(30) NOT NULL,
  `Phone` int(20) NOT NULL,
  `Email` varchar(30) NOT NULL,
  `PhysicalAddress` varchar(30) NOT NULL,
  `AdoptionDate` date NOT NULL,
  `ChildAdmNo` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `adoption`
--

INSERT INTO `adoption` (`ParentID`, `Name`, `Age`, `Occupation`, `Phone`, `Email`, `PhysicalAddress`, `AdoptionDate`, `ChildAdmNo`) VALUES
(9001, 'Evans Kambuthia', 39, 'Financial Advisor', 736553218, 'evansk@gmail.com', '2543 Lang\'ata', '2013-10-03', 1005),
(9002, 'Dolly Cinta', 34, 'Software Developer', 715384759, 'dollycinta@gmail.com', '3424 Kafoka', '2012-07-03', 1007),
(9003, 'Hilda Nyakundi', 28, 'Marketer', 724850394, 'hildanyaks@gmail.com', '8494 Nairobi', '2012-07-24', 1024),
(9004, 'Brian Mwakio', 37, 'Actuarialist', 721034528, 'bmwakio@gmail.com', '8494 Mombasa', '2012-09-12', 1002);

-- --------------------------------------------------------

--
-- Table structure for table `children`
--

CREATE TABLE `children` (
  `AdmNo` int(10) NOT NULL,
  `Name` varchar(30) NOT NULL,
  `Age` int(10) NOT NULL,
  `AdmissionDate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `children`
--

INSERT INTO `children` (`AdmNo`, `Name`, `Age`, `AdmissionDate`) VALUES
(1, 'John Doe', 10, '2011-08-02'),
(1001, 'Njoky Gachwe', 2, '2011-02-10'),
(1002, 'Evans Gichia', 5, '2011-02-24'),
(1003, 'Keddy Njuguna', 6, '2011-03-16'),
(1004, 'Steven Karuga', 4, '2011-03-24'),
(1005, 'Alonso Kabuthist', 3, '2011-04-02'),
(1006, 'Wilfred Mbogholi', 4, '2011-05-11'),
(1007, 'Edwin Mbuthia', 3, '2011-05-06'),
(1008, 'Collone Kinyanjui', 2, '2011-06-14'),
(1009, 'Bill Nyaga', 5, '2011-06-17'),
(1010, 'Dande Dande', 2, '2011-06-19'),
(1011, 'Timothy Mwamburi', 4, '2011-06-22'),
(1012, 'Esther-Irene Maina', 1, '2011-06-27'),
(1013, 'Mary-Anne Wakesho', 4, '2011-06-30'),
(1014, 'Anne-Lynn Wangui', 6, '2011-07-03'),
(1015, 'Lynn Wacu', 7, '2011-07-18'),
(1016, 'Jessica Monyoncho', 2, '2011-07-25'),
(1017, 'Coni Wamuyu', 4, '2011-08-02'),
(1018, 'Amos Gakere', 5, '2011-08-13'),
(1019, 'Bildad Kagia', 2, '2011-08-20'),
(1020, 'Anita Kimuya', 2, '2011-08-29'),
(1021, 'Christopher Chibalonzo', 6, '2011-09-17'),
(1022, 'Charity Mumo', 4, '2011-09-30'),
(1023, 'Festus Siwaka', 3, '2011-10-14'),
(1024, 'Grace Mtunguyaz', 6, '2011-10-26'),
(1025, 'Linda Owenda', 3, '2011-11-20'),
(1026, 'Testinh Child', 10, '2020-01-01');

-- --------------------------------------------------------

--
-- Table structure for table `contacts`
--

CREATE TABLE `contacts` (
  `ContactID` int(10) NOT NULL,
  `Name` varchar(30) NOT NULL,
  `Phone` int(20) NOT NULL,
  `Email` varchar(30) DEFAULT NULL,
  `PhysicalAddress` varchar(30) NOT NULL,
  `ChildAdmNo` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `contacts`
--

INSERT INTO `contacts` (`ContactID`, `Name`, `Phone`, `Email`, `PhysicalAddress`, `ChildAdmNo`) VALUES
(2001, 'Richard Mwakio', 712334590, 'richie@gmail.com', '343 Nairobi', 1001),
(2002, 'Siblinah Majala', 728755797, 'smajala7@gmail.com', '343 Mgange Dawida', 1002),
(2003, 'Tony Wayne', 723453098, 'tonywayne7@gmail.com', '8584 MWatate', 1003),
(2004, 'Mary Anne Nyambane', 727635290, 'manyambz7@gmail.com', '9203 Mwanda', 1004);

-- --------------------------------------------------------

--
-- Table structure for table `donations`
--

CREATE TABLE `donations` (
  `DonationID` int(10) NOT NULL,
  `Origin` varchar(30) NOT NULL,
  `Type` varchar(30) NOT NULL,
  `Quantity` varchar(30) NOT NULL,
  `Date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `donations`
--

INSERT INTO `donations` (`DonationID`, `Origin`, `Type`, `Quantity`, `Date`) VALUES
(1, 'Strathmore School', 'Books', '55', '2011-01-05'),
(2, 'Mwandau s Family', 'Clothes and books', '75', '2011-01-05'),
(3, 'Bernard Muturi', 'Snacks', '5 kg', '2011-01-19'),
(4, 'Kim Hooks', 'Shoes', '2 pairs', '2011-01-21'),
(5, 'Tomilee Jones ', 'Kids Movies , DVD players', '26, 5', '2011-02-01'),
(6, 'Burhma Groceries Limited', 'Groceries', '93 Kg ', '2011-02-06'),
(7, 'Hillsong United', 'Children Bibles', '62', '2011-03-01'),
(8, 'Tuskys', 'Food Supplies', '26 Kg', '2011-03-05');

-- --------------------------------------------------------

--
-- Table structure for table `fostering`
--

CREATE TABLE `fostering` (
  `ParentID` int(10) NOT NULL,
  `Name` varchar(30) NOT NULL,
  `Age` int(10) NOT NULL,
  `Occupation` varchar(30) NOT NULL,
  `Phone` int(20) NOT NULL,
  `Email` varchar(30) DEFAULT NULL,
  `PhysicalAddress` varchar(30) NOT NULL,
  `FosteringDate` date NOT NULL,
  `ChildAdmNo` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `fostering`
--

INSERT INTO `fostering` (`ParentID`, `Name`, `Age`, `Occupation`, `Phone`, `Email`, `PhysicalAddress`, `FosteringDate`, `ChildAdmNo`) VALUES
(5001, 'Joe Kiambuthi', 42, 'ICT Manager', 722334455, 'jkiambz@ymail.com', '23432 Runda', '2013-10-02', 1003),
(5002, 'David Solomon', 34, 'Health Worker', 721345673, 'dsolo@gmail.com', '08762 Mombasa', '2012-01-08', 1009),
(5003, 'Andy Mwas', 29, 'Gospel Artist', 721145732, 'andymwas@gmail.com', '08762 Buru', '2012-03-16', 1015),
(5004, 'Anish Mavji', 30, 'IT Consultant', 720345739, 'anishnish@yahoomail.com', '08762 Parkie', '2012-05-11', 1021),
(5005, 'Fiona Nguiye', 36, 'Bank Attendant', 714243709, 'fiona@yahoomail.com', '4322 Gachie', '2012-06-17', 1024);

-- --------------------------------------------------------

--
-- Table structure for table `loggedin`
--

CREATE TABLE `loggedin` (
  `title` varchar(10) NOT NULL DEFAULT 'user'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `loggedin`
--

INSERT INTO `loggedin` (`title`) VALUES
('admin');

-- --------------------------------------------------------

--
-- Table structure for table `login`
--

CREATE TABLE `login` (
  `User ID` int(10) NOT NULL,
  `Username` varchar(30) NOT NULL,
  `Password` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `login`
--

INSERT INTO `login` (`User ID`, `Username`, `Password`) VALUES
(1, 'admin', 'admin');

-- --------------------------------------------------------

--
-- Table structure for table `staff`
--

CREATE TABLE `staff` (
  `StaffID` int(10) NOT NULL,
  `Name` varchar(30) NOT NULL,
  `Phone` int(20) NOT NULL,
  `Email` varchar(30) NOT NULL,
  `PhysicalAddress` varchar(30) NOT NULL,
  `Department` varchar(30) NOT NULL,
  `Password` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `staff`
--

INSERT INTO `staff` (`StaffID`, `Name`, `Phone`, `Email`, `PhysicalAddress`, `Department`, `Password`) VALUES
(44553, 'Kimani Sam', 735472992, 'samkim@gmail.com', '3243 Ngumo', 'Kitchen', 'samkim'),
(69280, 'Abraham Nzau', 734949322, 'abrahamnzau@yahoo.com', '23445 Tao', 'Kitchen', 'triplee'),
(69283, 'Brian Njogholo Mwandau', 714431590, 'briannjogholo@gmail.com', '415 Karuri', 'Stores', 'bibo'),
(71157, 'Benjamin Odonya', 733445566, 'benjaminowenda@gmail.com', '574 Ngumo', 'Stores', '13enj');

-- --------------------------------------------------------

--
-- Table structure for table `volunteers`
--

CREATE TABLE `volunteers` (
  `VolunteerID` int(10) NOT NULL,
  `Name` varchar(30) NOT NULL,
  `Age` int(10) NOT NULL,
  `Institution` varchar(30) DEFAULT NULL,
  `Phone` int(20) NOT NULL,
  `Email` varchar(30) DEFAULT NULL,
  `PhysicalAddress` varchar(30) NOT NULL,
  `StartDate` date NOT NULL,
  `EndDate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `volunteers`
--

INSERT INTO `volunteers` (`VolunteerID`, `Name`, `Age`, `Institution`, `Phone`, `Email`, `PhysicalAddress`, `StartDate`, `EndDate`) VALUES
(23444, 'Fiona Nguiye', 21, 'Mount Kenya University ', 721352738, 'fionaaa@ymail.com', '2422 Wundanyi', '2013-01-07', '2013-02-25'),
(32226, 'Manuel Mwashimba', 25, 'Kenyatta University', 721273495, 'mwashmanu@gmail.com', '2444 Mwanda', '2013-04-16', '2013-09-10'),
(33422, 'Steven Karuga', 20, 'Nairobi University', 722334473, 'stevenkaruga@gmail.com', '23433 Kabete', '2012-06-12', '2012-09-10'),
(33488, 'Nellie Gitau', 19, 'Strathmore University', 711188885, 'nellygits@gmail.com', '5442 Wangige', '2013-06-18', '2013-10-22'),
(64646, 'Priscilla Kabicho', 19, 'Strathmore University', 721564729, 'pkngaruro@gmail.com', '2342 Mwatate', '2012-09-08', '2012-12-09'),
(77338, 'Gwen Oduk', 22, 'Masinde Muliro University', 722345901, 'gwenoduk7@ymail.com', '2338 Bura', '2013-01-28', '2013-02-27'),
(99483, 'Papa San', 31, 'Mombasa Technial College', 733546373, 'papasan@yahoomail.com', '2342 Mwapa', '2013-07-23', '2013-09-30'),
(99484, 'New Volunteer', 20, 'JKUAT', 0, 'email', '343fdd', '2020-01-02', '2022-01-02');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `adoption`
--
ALTER TABLE `adoption`
  ADD PRIMARY KEY (`ParentID`),
  ADD UNIQUE KEY `ChildAdmNo` (`ChildAdmNo`);

--
-- Indexes for table `children`
--
ALTER TABLE `children`
  ADD PRIMARY KEY (`AdmNo`);

--
-- Indexes for table `contacts`
--
ALTER TABLE `contacts`
  ADD PRIMARY KEY (`ContactID`),
  ADD KEY `ChildAdmNo` (`ChildAdmNo`),
  ADD KEY `ChildAdmNo_2` (`ChildAdmNo`);

--
-- Indexes for table `donations`
--
ALTER TABLE `donations`
  ADD PRIMARY KEY (`DonationID`);

--
-- Indexes for table `fostering`
--
ALTER TABLE `fostering`
  ADD PRIMARY KEY (`ParentID`),
  ADD UNIQUE KEY `ChildAdmNo` (`ChildAdmNo`);

--
-- Indexes for table `login`
--
ALTER TABLE `login`
  ADD PRIMARY KEY (`User ID`);

--
-- Indexes for table `staff`
--
ALTER TABLE `staff`
  ADD UNIQUE KEY `StaffID` (`StaffID`),
  ADD UNIQUE KEY `StaffID_2` (`StaffID`),
  ADD KEY `StaffID_3` (`StaffID`),
  ADD KEY `StaffID_4` (`StaffID`);

--
-- Indexes for table `volunteers`
--
ALTER TABLE `volunteers`
  ADD PRIMARY KEY (`VolunteerID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `adoption`
--
ALTER TABLE `adoption`
  MODIFY `ParentID` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9006;

--
-- AUTO_INCREMENT for table `children`
--
ALTER TABLE `children`
  MODIFY `AdmNo` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1027;

--
-- AUTO_INCREMENT for table `contacts`
--
ALTER TABLE `contacts`
  MODIFY `ContactID` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2006;

--
-- AUTO_INCREMENT for table `fostering`
--
ALTER TABLE `fostering`
  MODIFY `ParentID` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5006;

--
-- AUTO_INCREMENT for table `login`
--
ALTER TABLE `login`
  MODIFY `User ID` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `staff`
--
ALTER TABLE `staff`
  MODIFY `StaffID` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=71159;

--
-- AUTO_INCREMENT for table `volunteers`
--
ALTER TABLE `volunteers`
  MODIFY `VolunteerID` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=99485;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `adoption`
--
ALTER TABLE `adoption`
  ADD CONSTRAINT `adoption_ibfk_1` FOREIGN KEY (`ChildAdmNo`) REFERENCES `children` (`AdmNo`);

--
-- Constraints for table `contacts`
--
ALTER TABLE `contacts`
  ADD CONSTRAINT `contacts_ibfk_1` FOREIGN KEY (`ChildAdmNo`) REFERENCES `children` (`AdmNo`);

--
-- Constraints for table `fostering`
--
ALTER TABLE `fostering`
  ADD CONSTRAINT `fostering_ibfk_1` FOREIGN KEY (`ChildAdmNo`) REFERENCES `children` (`AdmNo`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
