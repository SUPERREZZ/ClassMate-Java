# ClassMate-Java

Aplikasi Class Mate adalah sebuah aplikasi untuk membantu administrasi sekolah dalam pencatatan absensi dan manajemen keuangan siswa. Dikembangkan oleh siswa kelas X RPL 3 Tahun Pelajaran 2023/2024 dari SMK Negeri 2 Mojokerto sebagai tugas akhir.

## Daftar Isi

- [Tentang Proyek](#tentang-proyek)
  - [Fitur Utama](#fitur-utama)
  - [Teknologi yang Digunakan](#teknologi-yang-digunakan)
- [Instalasi](#instalasi)
  - [Prasyarat](#prasyarat)
  - [Langkah-langkah Instalasi](#langkah-langkah-instalasi)
- [Penggunaan](#penggunaan)
- [Struktur Proyek](#struktur-proyek)
- [Kontribusi](#kontribusi)
- [Tim Pengembang](#tim-pengembang)
- [Lisensi](#lisensi)

## Tentang Proyek

Class Mate dikembangkan untuk meningkatkan efisiensi administrasi sekolah dengan mengotomatisasi proses pencatatan absensi dan manajemen keuangan siswa. Aplikasi ini juga memungkinkan komunikasi yang lebih baik antara siswa, guru, dan orang tua.

### Fitur Utama

- Pencatatan absensi siswa secara otomatis
- Manajemen keuangan siswa
- Room chat untuk komunikasi antara siswa dan guru
- Integrasi dengan Supabase untuk autentikasi dan penyimpanan data
- Antarmuka pengguna yang intuitif menggunakan JavaFX

### Teknologi yang Digunakan

- Java
- JavaFX
- PostgreSQL
- Supabase
- Maven
- Git

## Instalasi

### Prasyarat

- Java Development Kit (JDK) 21 atau lebih baru
- Maven
- PostgreSQL

### Langkah-langkah Instalasi

1. Clone repository ini
    ```bash
    git clone https://github.com/SUPERREZZ/ClassMate-Java.git
    cd ClassMate-Java
    ```
2. Install dependensi dengan Maven
    ```bash
    mvn clean install
    ```
3. Konfigurasi database PostgreSQL sesuai dengan `application.properties`
4. Jalankan aplikasi
    ```bash
    mvn javafx:run
    ```

## Penggunaan

1. Buka aplikasi dan login dengan akun yang telah terdaftar.
2. Gunakan menu navigasi untuk mengakses fitur absensi, manajemen keuangan, dan komunikasi.
3. Untuk melihat laporan absensi dan keuangan, pilih opsi yang sesuai di menu.

## Struktur Proyek

```ClassMate-Java/
├── src/
│ ├── main/
│ │ ├── java/
│ │ │ ├── com/eduapp/edumanagerapp/
│ │ │ │ ├── Controller/
│ │ │ │ ├── Model/
│ │ │ │ ├── View/
│ │ ├── resources/
│ │ │ ├── com/eduapp/edumanagerapp/
│ │ │ │ ├── fxml/
│ │ │ │ ├── css/
├── pom.xml
├── README.md
```

## Kontribusi

Kami menerima kontribusi dari siapa saja. Silakan buat pull request atau buka issue untuk melaporkan bug atau mengusulkan fitur baru.

## Tim Pengembang

- **Navilatuz Zahra** - Ketua Tim - [navilatuzzahra@gmail.com](mailto:navilatuzzahra@gmail.com)
- **Reza Ghiyats Fikri** - Programmer - [rezaghiyatsfikri@gmail.com](mailto:rezaghiyatsfikri@gmail.com)
- **M. Gusti Phalevi** - Front End Designer - [phalevigusti@gmail.com](mailto:phalevigusti@gmail.com)
- **Febril Wahyu Pertiwi** - Marketing - [febrilwp16@gmail.com](mailto:febrilwp16@gmail.com)
- **Destiana Putri Natalia** - Sekretaris - [destianaputrinatalia25@gmail.com](mailto:destianaputrinatalia25@gmail.com)

