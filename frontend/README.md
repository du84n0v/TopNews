# TopNews Frontend

TopNews yangiliklar portali uchun React frontend.

## Texnologiyalar

- **React 18** - UI framework
- **Vite** - Build tool
- **Tailwind CSS** - Styling
- **React Router DOM** - Routing
- **Axios** - HTTP client
- **React Icons** - Ikonkalar
- **React Toastify** - Bildirishnomalar
- **date-fns** - Sana formatlash

## O'rnatish va ishga tushirish

### 1. Dependencylarni o'rnatish

```bash
cd frontend
npm install
```

### 2. Development serverni ishga tushirish

```bash
npm run dev
```

Frontend `http://localhost:3000` da ishga tushadi va barcha `/api` so'rovlarini `http://localhost:8080` ga proxy qiladi.

### 3. Backend ni ishga tushiring

Backend Spring Boot server `http://localhost:8080` portida ishlayotgan bo'lishi kerak.

### 4. Production build

```bash
npm run build
```

Build `dist/` papkaga chiqariladi.

## Loyiha strukturasi

```
frontend/
├── public/              # Statik fayllar
├── src/
│   ├── api/             # API service layer (axios)
│   │   ├── index.js         # Axios instance + interceptors
│   │   ├── authService.js   # Auth API
│   │   ├── articleService.js # Article API
│   │   ├── categoryService.js
│   │   ├── sectionService.js
│   │   ├── regionService.js
│   │   ├── commentService.js
│   │   ├── profileService.js
│   │   ├── attachService.js
│   │   └── likeService.js
│   ├── components/      # Qayta ishlatiladigan komponentlar
│   │   ├── Navbar.jsx
│   │   ├── Footer.jsx
│   │   ├── Sidebar.jsx
│   │   ├── Layout.jsx
│   │   ├── AdminLayout.jsx
│   │   ├── ArticleCard.jsx
│   │   ├── Pagination.jsx
│   │   ├── LoadingSpinner.jsx
│   │   └── ProtectedRoute.jsx
│   ├── context/         # React Context
│   │   └── AuthContext.jsx
│   ├── pages/           # Sahifalar
│   │   ├── public/      # Ochiq sahifalar
│   │   ├── auth/        # Auth sahifalar
│   │   ├── user/        # User sahifalar
│   │   └── admin/       # Admin panel
│   ├── utils/           # Yordamchi funksiyalar
│   │   └── helpers.js
│   ├── App.jsx          # Asosiy routing
│   ├── main.jsx         # Entry point
│   └── index.css        # Tailwind + custom styles
├── index.html
├── vite.config.js
├── tailwind.config.js
├── postcss.config.js
└── package.json
```

## Sahifalar

### Ochiq sahifalar (Public)
- `/` - Bosh sahifa (so'nggi yangiliklar, bo'limlar)
- `/article/:id` - Maqola to'liq ko'rinishi
- `/category/:id` - Kategoriya bo'yicha maqolalar
- `/section/:id` - Bo'lim bo'yicha maqolalar
- `/region/:id` - Region bo'yicha maqolalar
- `/categories` - Kategoriyalar ro'yxati
- `/sections` - Bo'limlar ro'yxati
- `/regions` - Regionlar ro'yxati

### Auth sahifalar
- `/login` - Kirish
- `/register` - Ro'yxatdan o'tish
- `/verify` - Email tasdiqlash

### Himoyalangan sahifalar
- `/profile` - Profil
- `/settings` - Sozlamalar

### Admin panel
- `/admin` - Dashboard
- `/admin/articles` - Maqolalar boshqaruvi
- `/admin/categories` - Kategoriyalar boshqaruvi (ADMIN)
- `/admin/sections` - Bo'limlar boshqaruvi (ADMIN)
- `/admin/regions` - Regionlar boshqaruvi (ADMIN)
- `/admin/profiles` - Foydalanuvchilar boshqaruvi (ADMIN)
- `/admin/comments` - Izohlar boshqaruvi (ADMIN)

## Foydalanuvchi rollari

| Rol | Imkoniyatlar |
|-----|------|
| ROLE_USER | Maqolalarni o'qish, izoh yozish, like bosish |
| ROLE_MODERATOR | Maqolalarni yaratish/tahrirlash |
| ROLE_PUBLISHER | Maqolalarni nashr qilish |
| ROLE_ADMIN | Barcha imkoniyatlar |

## Til qo'llab-quvvatlashi

Frontend 3 ta tilni qo'llab-quvvatlaydi:
- **UZ** - O'zbek tili (default)
- **RU** - Rus tili
- **EN** - Ingliz tili

Til `Accept-Language` header orqali backendga yuboriladi.
