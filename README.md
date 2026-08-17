# TopNews — Backend

**TopNews** — bu Kun.uz uslubidagi onlayn yangiliklar platformasining **backend (server) qismi**. Spring Boot asosida qurilgan REST API bo'lib, maqolalar, kategoriyalar, izohlar, foydalanuvchi profillari va autentifikatsiyani boshqaradi.

## 📌 Loyiha haqida

Loyiha maqolalarni yaratish, tahrirlash va nashr qilish, kategoriya/bo'lim/region bo'yicha filtrlash, izoh qoldirish, like/dislike qo'yish, fayl (rasm) yuklash va rol asosidagi ruxsatlar (foydalanuvchi, admin, moderator, publisher) kabi imkoniyatlarni taqdim etadi.

## 🛠 Texnologiyalar

|Texnologiya|Vazifasi|
|-|-|
|**Java 21**|Asosiy dasturlash tili|
|**Spring Boot 3.5**|Asosiy framework|
|**Spring Data JPA / Hibernate**|Ma'lumotlar bazasi bilan ishlash (ORM)|
|**Spring Security + JWT (jjwt)**|Autentifikatsiya va avtorizatsiya|
|**PostgreSQL**|Ma'lumotlar bazasi|
|**Flyway**|DB migratsiyalarini boshqarish|
|**Lombok**|Boilerplate kodni qisqartirish|
|**Spring Mail**|Email orqali tasdiqlash kodlarini yuborish|
|**Maven**|Build tool|

## ✨ Asosiy imkoniyatlar

* 🔐 **Autentifikatsiya** — ro'yxatdan o'tish, email orqali tasdiqlash (verification), qayta kod yuborish, login (JWT token)
* 📰 **Maqolalar (Article)** — yaratish, tahrirlash, holatini o'zgartirish (PUBLISHED / NOT\_PUBLISHED), ID bo'yicha olish, bo'lim/kategoriya/region bo'yicha so'nggi maqolalar, eng ko'p o'qilganlar, ko'rishlar/ulashishlar sonini oshirish, filtrlash
* 🗂 **Kategoriya, Bo'lim (Section), Region** — CRUD va til bo'yicha (UZ/RU/EN) olish
* 💬 **Izohlar (Comment)** — yozish, tahrirlash, o'chirish, javob (reply) yozish, maqola bo'yicha izohlar ro'yxati
* 👍 **Like/Dislike** — maqola va izohlar uchun
* 👤 **Profil** — admin tomonidan yaratish/tahrirlash/o'chirish, shaxsiy ma'lumotlarni va parolni yangilash, rasm qo'yish
* 📎 **Fayl yuklash (Attach)** — rasm/fayl yuklash, ochish, yuklab olish, o'chirish
* 🏷 **Teglar (Tag)**
* 💾 **Saqlangan maqolalar (Saved Article)**
* 📧 **Email tarixi (Email History)** — yuborilgan email'larni email yoki sana bo'yicha kuzatish

## 👥 Foydalanuvchi rollari

* `ROLE\_USER` — oddiy foydalanuvchi
* `ROLE\_ADMIN` — administrator
* `ROLE\_MODERATOR` — moderator
* `ROLE\_PUBLISHER` — kontent nashr qiluvchi

## 📁 Loyiha strukturasi

```
src/main/java/top/news/
├── config/security/    # JWT filter, Spring Security konfiguratsiyasi
├── controller/          # REST API controller'lar
├── dto/                 # Request/Response ma'lumot obyektlari
├── entity/               # JPA entity (DB jadval) klasslari
├── enums/                # Enum'lar (rol, status, til)
├── exception/            # Maxsus exception klasslari
├── mapper/               # Entity <-> DTO mapper'lari
├── repository/           # Spring Data JPA repository'lari
├── service/              # Biznes logika
└── util/                 # Yordamchi klasslar (JWT, MD5, va h.k.)

src/main/resources/
├── application.properties
├── db/migration/         # Flyway SQL migratsiyalari
└── http/                 # API'larni test qilish uchun .http fayllar
```

## 🔌 API endpoint'lari (qisqacha)

|Modul|Base path|
|-|-|
|Auth|`/api/v1/auth`|
|Article|`/api/v1/article`|
|Category|`/api/v1/category`|
|Comment|`/api/v1/comment`|
|Profile|`/api/v1/profile`|
|Region|`/api/v1/region`|
|Section|`/api/v1/section`|
|Tag|`/api/v1/tag`|
|Attach|`/api/v1/attaches`|
|Article Like|`/api/v1/article-like`|
|Comment Like|`/api/v1/comment-like`|
|Saved Article|`/api/v1/saved-article`|
|Email History|`/api/v1/email-history`|

Barcha endpoint namunalari `src/main/resources/http/` papkasidagi `.http` fayllarda mavjud (IntelliJ IDEA yoki VS Code REST Client bilan sinab ko'rish mumkin).

## ⚙️ O'rnatish va ishga tushirish

### Talablar

* Java 21+
* Maven (yoki loyihadagi `mvnw` wrapper)
* PostgreSQL

### 1\. Repozitoriyani klonlash

```bash
git clone https://github.com/du84n0v/TopNews.git
cd TopNews
```

### 2\. Ma'lumotlar bazasini sozlash

PostgreSQL'da baza yarating va `src/main/resources/application.properties` faylida quyidagilarni o'zingizga moslang:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/top\_news\_db
spring.datasource.username=<sizning\_username>
spring.datasource.password=<sizning\_parolingiz>
```

### 3\. Loyihani ishga tushirish

```bash
./mvnw spring-boot:run
```

Server manzili: `http://localhost:8080`

## 🗄 Ma'lumotlar bazasi migratsiyasi

Loyihada Flyway orqali boshlang'ich ma'lumotlar (admin, kategoriya, bo'lim, region va like trigger'lari) yuklanadi (`src/main/resources/db/migration`). Migratsiyani yoqish uchun `application.properties`da:

```properties
spring.flyway.enabled=true
```

## 🧪 Testlash

```bash
./mvnw test
```

## 📄 Litsenziya

Litsenziya belgilanmagan.

