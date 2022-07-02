-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 02 Jul 2022 pada 15.11
-- Versi server: 10.4.20-MariaDB
-- Versi PHP: 8.0.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_penilaian`
--

DELIMITER $$
--
-- Prosedur
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `checkDuplicateJadwal` (IN `kodeMatkul` VARCHAR(100), IN `idkelas` INT(11))  SELECT * FROM jadwal WHERE kode_matkul = kodeMatkul AND id_kelas = idKelas$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `checkJadwal` (IN `kodeMatkul` VARCHAR(100), IN `nipGuru` VARCHAR(15), IN `idkelas` INT(11))  SELECT * FROM jadwal WHERE kode_matkul = kodeMatkul AND id_kelas = idKelas$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `checkKodeKelas` (IN `kodeKelas` CHAR(2))  SELECT kode_kelas FROM kelas WHERE kode_kelas = kodeKelas$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `checkKodeMatkul` (IN `kodeMatkul` VARCHAR(10))  SELECT kode_matkul FROM matapelajaran WHERE kode_matkul = kodeMatkul$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `checkNilai` (IN `kodeMatkul` VARCHAR(100), IN `nisSiswa` VARCHAR(10))  SELECT * FROM nilai WHERE kode_matkul = kodeMatkul AND nis = nisSiswa$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `checkNIP` (IN `nipGuru` VARCHAR(100))  SELECT nip FROM dataGuru WHERE nip = nipGuru$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `checkNIS` (IN `nisSiswa` INT(100))  SELECT nis FROM datasiswa WHERE nis = nisSiswa$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `createGuru` (IN `nipGuru` VARCHAR(15), IN `namaGuru` VARCHAR(100), IN `tlGuru` DATE, IN `jkGuru` ENUM('1','2'), IN `agamaGuru` VARCHAR(25), IN `notelpGuru` VARCHAR(15), IN `statusGuru` ENUM('1','2'), IN `passwordGuru` VARCHAR(255))  BEGIN
    INSERT INTO `dataguru` (`nip`, `nama`, `tanggal_lahir`, `jenis_kelamin`, `agama`, `no_telp`, `status`) VALUES (nipGuru, namaGuru, tlGuru, jkGuru, agamaGuru, notelpGuru, statusGuru);
    INSERT INTO `user` (`username`, `password`, `role`) VALUES (nipGuru, passwordGuru, '2');
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `createSiswa` (IN `nisSiswa` VARCHAR(15), IN `namaSiswa` VARCHAR(100), IN `tlSiswa` DATE, IN `jkSiswa` ENUM('1','2'), IN `agamaSiswa` VARCHAR(25), IN `notelSiswa` VARCHAR(15), IN `namaAyah` VARCHAR(100), IN `namaIbu` VARCHAR(100), IN `asalSekolah` VARCHAR(50), IN `idKelas` INT(11), IN `passwordSiswa` INT(255))  BEGIN
    INSERT INTO `datasiswa` (`nis`, `nama`, `tanggal_lahir`, `jenis_kelamin`, `agama`, `no_telp`, `nama_ayah`, `nama_ibu`, `asal_sekolah`, 	`id_kelas`) VALUES (nisSiswa, namaSiswa, tlSiswa, jkSiswa, agamaSiswa, notelSiswa, namaAyah,  namaIbu, asalSekolah, idKelas);
    INSERT INTO `user` (`username`, `password`, `role`) VALUES (nisSiswa, passwordSiswa, '3');
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteGuru` (IN `nipGuru` VARCHAR(100))  BEGIN
    DELETE FROM dataguru WHERE nip = nipGuru;
    DELETE FROM user WHERE username = nipGuru;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteSiswa` (IN `nisSiswa` VARCHAR(100))  BEGIN
    DELETE FROM datasiswa WHERE nis = nisSiswa;
    DELETE FROM user WHERE username = nisSiswa;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `editGuru` (IN `nipGuru` VARCHAR(15), IN `namaGuru` VARCHAR(100), IN `tlGuru` DATE, IN `jkGuru` ENUM('1','2'), IN `agamaGuru` VARCHAR(25), IN `notelpGuru` VARCHAR(15), IN `statusGuru` ENUM('1','2'), IN `passwordGuru` VARCHAR(255), IN `nipOld` VARCHAR(15))  BEGIN
    UPDATE dataguru SET `nip` = nipGuru, `nama` = namaGuru, `tanggal_lahir` = tlGuru, `jenis_kelamin` = jkGuru, `agama` = agamaGuru, `no_telp` = notelpGuru, `status` = statusGuru WHERE `nip` = nipOld;
    UPDATE user SET `username` = nipGuru, `password` = passwordGuru WHERE `username` = nipOld;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `editSiswa` (IN `nisSiswa` VARCHAR(15), IN `namaSiswa` VARCHAR(100), IN `tlSiswa` DATE, IN `jkSiswa` ENUM('1','2'), IN `agamaSiswa` VARCHAR(25), IN `notelSiswa` VARCHAR(15), IN `namaAyah` VARCHAR(100), IN `namaIbu` VARCHAR(100), IN `asalSekolah` VARCHAR(50), IN `idKelas` INT(11), IN `passwordSiswa` VARCHAR(255), IN `nisOld` VARCHAR(15))  BEGIN
    UPDATE datasiswa SET `nis` = nisSiswa, `nama` = namaSiswa, `tanggal_lahir` = tlSiswa, `jenis_kelamin` = jkSiswa, `agama` = agamaSiswa, `no_telp` = notelSiswa, `nama_ayah` = namaAyah, `nama_ibu` = namaIbu, `asal_sekolah` = asalSekolah, id_kelas = idKelas WHERE `nis` = nisOld;
    UPDATE user SET `username` = nisSiswa, `password` = passwordSiswa WHERE `username` = nisOld;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Struktur dari tabel `dataguru`
--

CREATE TABLE `dataguru` (
  `nip` varchar(15) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `tanggal_lahir` date NOT NULL,
  `jenis_kelamin` enum('1','2') NOT NULL,
  `agama` varchar(25) NOT NULL,
  `no_telp` varchar(15) NOT NULL,
  `status` enum('1','2') NOT NULL,
  `gambar` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `dataguru`
--

INSERT INTO `dataguru` (`nip`, `nama`, `tanggal_lahir`, `jenis_kelamin`, `agama`, `no_telp`, `status`, `gambar`) VALUES
('112234', 'Guru-1', '2022-05-30', '1', 'Islam', '08877665544', '1', 'IMG_20200722_074512.jpg-20220531042658.jpg'),
('112235', 'Guru-2', '2022-05-30', '1', 'Islam', '08877665544', '1', ''),
('112236', 'Guru-3', '2022-05-30', '1', 'Islam', '08877665544', '1', ''),
('112237', 'Guru-4', '2022-05-30', '1', 'Islam', '08877665544', '1', ''),
('112238', 'Guru-5', '2022-05-30', '1', 'Islam', '08877665544', '1', '');

-- --------------------------------------------------------

--
-- Struktur dari tabel `datasiswa`
--

CREATE TABLE `datasiswa` (
  `nis` varchar(15) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `tanggal_lahir` date NOT NULL,
  `jenis_kelamin` smallint(2) NOT NULL,
  `agama` varchar(25) NOT NULL,
  `nama_ayah` varchar(100) NOT NULL,
  `nama_ibu` varchar(100) NOT NULL,
  `asal_sekolah` varchar(50) NOT NULL,
  `no_telp` varchar(15) NOT NULL,
  `gambar` varchar(255) NOT NULL,
  `id_kelas` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `datasiswa`
--

INSERT INTO `datasiswa` (`nis`, `nama`, `tanggal_lahir`, `jenis_kelamin`, `agama`, `nama_ayah`, `nama_ibu`, `asal_sekolah`, `no_telp`, `gambar`, `id_kelas`) VALUES
('55201201', 'Siswa-1', '2022-05-30', 1, 'Islam', 'Ayah', 'Ibu', 'SMPN 1 Cianjur', '0887766', '1.jpg-20220531110844.jpg', 9),
('55201202', 'Siswa-2', '2022-05-30', 1, 'Islam', 'Ayah', 'Ibu', 'SMPN 1 Cianjur', '08778899', '', 9),
('55201203', 'Siswa-3', '2022-05-30', 1, 'Islam', 'Ayah', 'Ibu', 'SMPN 1 Cianjur', '08776655', '', 9);

-- --------------------------------------------------------

--
-- Struktur dari tabel `info`
--

CREATE TABLE `info` (
  `id_pesan` int(11) NOT NULL,
  `pesan` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `info`
--

INSERT INTO `info` (`id_pesan`, `pesan`) VALUES
(2, 'Libur 1'),
(6, 'Libur2');

-- --------------------------------------------------------

--
-- Struktur dari tabel `jadwal`
--

CREATE TABLE `jadwal` (
  `id_jadwal` int(11) NOT NULL,
  `kode_matkul` varchar(10) NOT NULL,
  `nip` varchar(15) NOT NULL,
  `id_kelas` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `jadwal`
--

INSERT INTO `jadwal` (`id_jadwal`, `kode_matkul`, `nip`, `id_kelas`) VALUES
(4, '2020', '112234', 9),
(9, '2022', '112234', 9),
(10, '2021', '112234', 9);

-- --------------------------------------------------------

--
-- Struktur dari tabel `kelas`
--

CREATE TABLE `kelas` (
  `id_kelas` int(11) NOT NULL,
  `tingkat` char(3) NOT NULL,
  `jurusan` varchar(15) NOT NULL,
  `kode_kelas` char(2) NOT NULL,
  `wali_kelas` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `kelas`
--

INSERT INTO `kelas` (`id_kelas`, `tingkat`, `jurusan`, `kode_kelas`, `wali_kelas`) VALUES
(9, '10', 'IPA', '1', '112234'),
(10, '10', 'IPA', '2', '112235'),
(11, '10', 'IPA', '3', '112236');

-- --------------------------------------------------------

--
-- Struktur dari tabel `matapelajaran`
--

CREATE TABLE `matapelajaran` (
  `kode_matkul` varchar(10) NOT NULL,
  `nama_matkul` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `matapelajaran`
--

INSERT INTO `matapelajaran` (`kode_matkul`, `nama_matkul`) VALUES
('2020', 'Fisika'),
('2021', 'Kimia'),
('2022', 'Bahasa');

-- --------------------------------------------------------

--
-- Struktur dari tabel `nilai`
--

CREATE TABLE `nilai` (
  `nilai_absen` float NOT NULL,
  `nilai_uts` float NOT NULL,
  `nilai_uas` float NOT NULL,
  `nilai_tugas` float NOT NULL,
  `catatan` varchar(255) NOT NULL,
  `nis` varchar(15) NOT NULL,
  `nip` varchar(10) NOT NULL,
  `kode_matkul` varchar(10) NOT NULL,
  `kelas_id` int(11) NOT NULL,
  `id_nilai` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `nilai`
--

INSERT INTO `nilai` (`nilai_absen`, `nilai_uts`, `nilai_uas`, `nilai_tugas`, `catatan`, `nis`, `nip`, `kode_matkul`, `kelas_id`, `id_nilai`) VALUES
(90, 90, 90, 90, 'Bagus', '55201201', '112234', '2020', 9, 10),
(80, 80, 80, 80, 'Sedang', '55201201', '112234', '2022', 9, 11),
(90, 90, 90, 90, 'Bagus', '55201201', '112234', '2021', 9, 12),
(80, 80, 80, 80, '', '55201202', '112234', '2020', 9, 13),
(80, 80, 80, 80, '', '55201203', '112234', '2020', 9, 14),
(90, 90, 90, 90, '', '55201202', '112234', '2022', 9, 15),
(90, 90, 90, 90, '', '55201203', '112234', '2022', 9, 16);

-- --------------------------------------------------------

--
-- Struktur dari tabel `user`
--

CREATE TABLE `user` (
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` enum('1','2','3') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `user`
--

INSERT INTO `user` (`username`, `password`, `role`) VALUES
('112234', '827ccb0eea8a706c4c34a16891f84e7b', '2'),
('112235', '0c714e7f6a8d08afdd97c48219357c7c', '2'),
('112236', 'b1525bba7fa872a7cea1c50071dd681d', '2'),
('112237', '2158e6b3c82b73af894c4c52036cf960', '2'),
('112238', 'ed218b475b8f4b935472c00b253db211', '2'),
('55201201', '1ae1155f6159294e1cc29bbb4311870b', '3'),
('55201202', 'a03c89847629404c68ecc78d815c3b58', '3'),
('55201203', '4444f2ed9153eba7bda377d4963240ce', '3'),
('admin', '21232f297a57a5a743894a0e4a801fc3', '1');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `dataguru`
--
ALTER TABLE `dataguru`
  ADD PRIMARY KEY (`nip`);

--
-- Indeks untuk tabel `datasiswa`
--
ALTER TABLE `datasiswa`
  ADD PRIMARY KEY (`nis`),
  ADD KEY `fk_id_kelas` (`id_kelas`);

--
-- Indeks untuk tabel `info`
--
ALTER TABLE `info`
  ADD PRIMARY KEY (`id_pesan`);

--
-- Indeks untuk tabel `jadwal`
--
ALTER TABLE `jadwal`
  ADD PRIMARY KEY (`id_jadwal`),
  ADD KEY `fk_id_kelas_jadwal` (`id_kelas`),
  ADD KEY `fk_kode_matkul_jadwal` (`kode_matkul`),
  ADD KEY `fk_nip_jadwal` (`nip`);

--
-- Indeks untuk tabel `kelas`
--
ALTER TABLE `kelas`
  ADD PRIMARY KEY (`id_kelas`),
  ADD KEY `fk_walikelas_dataguru` (`wali_kelas`);

--
-- Indeks untuk tabel `matapelajaran`
--
ALTER TABLE `matapelajaran`
  ADD PRIMARY KEY (`kode_matkul`);

--
-- Indeks untuk tabel `nilai`
--
ALTER TABLE `nilai`
  ADD PRIMARY KEY (`id_nilai`),
  ADD KEY `fk_nilai_kode_matkul` (`kode_matkul`),
  ADD KEY `fk_nip_dataguru` (`nip`),
  ADD KEY `fk_nis_datasiswa` (`nis`),
  ADD KEY `fk_idkelas_kelas` (`kelas_id`);

--
-- Indeks untuk tabel `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`username`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `info`
--
ALTER TABLE `info`
  MODIFY `id_pesan` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT untuk tabel `jadwal`
--
ALTER TABLE `jadwal`
  MODIFY `id_jadwal` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT untuk tabel `kelas`
--
ALTER TABLE `kelas`
  MODIFY `id_kelas` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT untuk tabel `nilai`
--
ALTER TABLE `nilai`
  MODIFY `id_nilai` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `datasiswa`
--
ALTER TABLE `datasiswa`
  ADD CONSTRAINT `fk_id_kelas` FOREIGN KEY (`id_kelas`) REFERENCES `kelas` (`id_kelas`);

--
-- Ketidakleluasaan untuk tabel `jadwal`
--
ALTER TABLE `jadwal`
  ADD CONSTRAINT `fk_id_kelas_jadwal` FOREIGN KEY (`id_kelas`) REFERENCES `kelas` (`id_kelas`),
  ADD CONSTRAINT `fk_kode_matkul_jadwal` FOREIGN KEY (`kode_matkul`) REFERENCES `matapelajaran` (`kode_matkul`),
  ADD CONSTRAINT `fk_nip_jadwal` FOREIGN KEY (`nip`) REFERENCES `dataguru` (`nip`);

--
-- Ketidakleluasaan untuk tabel `kelas`
--
ALTER TABLE `kelas`
  ADD CONSTRAINT `fk_walikelas_dataguru` FOREIGN KEY (`wali_kelas`) REFERENCES `dataguru` (`nip`);

--
-- Ketidakleluasaan untuk tabel `nilai`
--
ALTER TABLE `nilai`
  ADD CONSTRAINT `fk_idkelas_kelas` FOREIGN KEY (`kelas_id`) REFERENCES `kelas` (`id_kelas`),
  ADD CONSTRAINT `fk_nilai_kode_matkul` FOREIGN KEY (`kode_matkul`) REFERENCES `matapelajaran` (`kode_matkul`),
  ADD CONSTRAINT `fk_nip_dataguru` FOREIGN KEY (`nip`) REFERENCES `dataguru` (`nip`),
  ADD CONSTRAINT `fk_nis_datasiswa` FOREIGN KEY (`nis`) REFERENCES `datasiswa` (`nis`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
