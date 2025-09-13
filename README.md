

# Food Order System

**Food Order System** adalah aplikasi berbasis **Spring Boot 3** dengan **PostgreSQL** sebagai database utama.
Aplikasi ini sudah mendukung **JWT Authentication** untuk proses login, dilengkapi dengan **role-based access** (Admin, Penjual, dan Pembeli), serta menerapkan **security**, **validasi data**, dan **unit test** untuk menjaga keamanan serta kualitas aplikasi.

---
## Teknologi & Dependencies

* Java 17
* Spring Boot 
* Maven 3.6+
* Spring Web, Spring Data JPA, Spring Security, Validation
* PostgreSQL Driver
* Lombok
* JWT (jjwt-api, jjwt-impl, jjwt-jackson)
* SpringDoc OpenAPI (Swagger UI)
* Commons Lang3
* JUnit 5 & Mockito

---
## Dokumentasi API

* **Postman Collection**:
  [Food Order System - Postman](https://documenter.getpostman.com/view/45884667/2sB3HoqKpg)

* **Swagger UI**:
  [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

### User Akses untuk Testing

Aplikasi sudah menyediakan akun default untuk setiap role, yang bisa digunakan saat uji coba:

**Admin, Penjual, dan Pembeli**
```json
{
  "username": "admin",
  "password": "admin123"
}
{
  "username": "penjual",
  "password": "penjual123"
}
{
  "username": "pembeli",
  "password": "pembeli123"
}
```
---

## Flowchart Sistem

<img width="3840" height="2109" alt="Untitled diagram _ Mermaid Chart-2025-09-13-161212" src="https://github.com/user-attachments/assets/e79cc4a0-bb32-46ac-92a7-11abb00a2f5e" />

---

## Fitur

### Authentication & Authorization

* Login menggunakan JWT
* Hak akses berdasarkan role (Admin, Penjual, Pembeli)


### Admin

* Mengelola user penjual dan pembeli (CRUD)
* Melihat seluruh pesanan yang masuk

### Penjual

* Mengelola produk (CRUD)
* Melihat seluruh pesanan yang masuk

### Pembeli
* Membuat pesanan (stok produk otomatis berkurang setelah pesanan tersimpan di database) 
* Melihat riwayat pesanan

---

## Konfigurasi Database

Aplikasi menggunakan **PostgreSQL** (hosted di Neondb).

<img width="1171" height="765" alt="image" src="https://github.com/user-attachments/assets/5e3c9fc1-fe34-4c37-85f5-61ac259f8567" />


---
