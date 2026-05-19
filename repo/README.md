# Note Sharing API (Ktor + Kotlin)

Bu proje, Kotlin ve Ktor kullanılarak geliştirilmiş, JWT doğrulamalı ve PostgreSQL tabanlı bir not paylaşım API'sidir.

## 🛠 Kullanılan Teknolojiler
- **Kotlin** & **Ktor** (Backend Framework)
- **Exposed ORM** (Veritabanı Yönetimi)
- **PostgreSQL** (Veritabanı)
- **JWT** (Kimlik Doğrulama)
- **BCrypt** (Şifre Güvenliği)
- **Docker** (Konteynerleştirme)

## 🚀 Çalıştırma Talimatları

En hızlı şekilde ayağa kaldırmak için Docker kullanmanız önerilir:

```bash
docker-compose up --build
```

Bu komut:
1. PostgreSQL veritabanını başlatır.
2. Uygulamayı derler ve çalıştırır.
3. API, `http://localhost:8080` adresinden erişilebilir hale gelir.

## 📝 API Uç Noktaları

### 🔑 Kimlik Doğrulama
- `POST /auth/register` - Kullanıcı kaydı
- `POST /auth/login` - Kullanıcı girişi (Token döner)

### 📓 Not İşlemleri (Auth Gerekli)
- `GET /notes` - Notlarımı listele
- `POST /notes` - Yeni not oluştur (`title`, `content`, `isPublic`)
- `GET /notes/{id}` - Not detayı
- `PUT /notes/{id}` - Not güncelle
- `DELETE /notes/{id}` - Not sil
- `GET /notes/search?q=...` - Notlarda arama yap

### 🌍 Herkese Açık İşlemler
- `GET /notes/public` - Tüm açık notları gör
- `GET /notes/shared/{id}` - Paylaşım linki ile erişim
