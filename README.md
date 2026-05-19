# Kotlin Ktor Server

> **Kotlin Ktor ile lightweight asenkron API — mobil backend için ideal**

![Zorluk](https://img.shields.io/badge/Zorluk-Zor-red)
![Puan](https://img.shields.io/badge/Puan-55-blue)
![Hafta](https://img.shields.io/badge/Hafta-4-gray)
![Lisans](https://img.shields.io/badge/License-MIT-green)
![Durum](https://img.shields.io/badge/Durum-Development-yellow)

<!-- Kodunuzu yazdıktan sonra aşağıdaki bölümleri doldurun ve ekleyin:
![CI](https://github.com/KULLANICI_ADI/final-p31-kotlin-ktor-ser/actions/workflows/ci.yml/badge.svg)
![Deploy](https://img.shields.io/website?url=https://kotlin-ktor-server.vercel.app)
-->

## 🎯 Özet

[*1-2 paragraf: Ürün ne yapıyor, kime, hangi problemi çözüyor? Jargon yok.*]

## 🎥 Demo

🔗 **Canlı Demo:** https://kotlin-ktor-server.vercel.app  
👤 **Demo Hesap:** `demo@example.com` · `demo123`

![Demo GIF](repo/docs/demo.gif)

> _Not: Ekran görüntülerini ve demo GIF'ini kendi `repo/` içinde istediğiniz klasör yapısında tutabilirsiniz. Aşağıdaki görsel yolları örnektir; gerçek dosya konumlarınıza göre güncelleyin._

### Ekran Görüntüleri (Örnek yerleşim)

| Landing | Dashboard | Mobile |
|---------|-----------|--------|
| ![landing](repo/docs/screenshots/01-landing.png) | ![dashboard](repo/docs/screenshots/02-dashboard.png) | ![mobile](repo/docs/screenshots/03-mobile.png) |

## ✨ Ana Özellikler

- ✅ Product Hunt benzeri 'ürün tanıtımı platformu'
- ✅ Kullanıcı register/login (JWT)
- ✅ Ürün CRUD + upvote
- ✅ Yorum + nested reply
- ✅ Image upload (multipart)
- ✅ Real-time feed (SSE)
- ✅ API Doc (OpenAPI plugin)
- ✅ Rate limiting + caching

## 🧰 Tech Stack

**Framework:** `Kotlin 2 + Ktor 3`  
**Database:** `PostgreSQL + Exposed ORM veya Ktorm`  
**Serialization:** `kotlinx.serialization`  
**Auth:** `JWT (Ktor Auth plugin)`  
**Coroutines:** `structured concurrency`  
**Test:** `Ktor Test + Testcontainers`  
**Deployment:** `Docker + Fly.io / Railway`  

> Teknoloji seçimlerinin detaylı gerekçesi: [PROJE-RAPORU.md · Bölüm 7](PROJE-RAPORU.md#7-teknoloji-yığını-tech-stack)

## 🏗 Mimari

[*Mimari diyagramınızı buraya ekleyiniz — örn. `repo/docs/diagrams/container.png`*]

[Detaylı mimari ve ADR'lar →](PROJE-RAPORU.md#8-sistem-mimarisi)

## 🚀 Kurulum

### Gereksinimler

- JDK ≥ 21
- Kotlin ≥ 2.0
- PostgreSQL

### Adım Adım

```bash
# 1) Repo'yu klonla
git clone https://github.com/KULLANICI_ADI/final-p31-kotlin-ktor-ser.git
cd final-p31-kotlin-ktor-ser

# 2) Environment dosyası
cp .env.example .env
# .env içindekileri doldurun (DATABASE_URL, JWT_SECRET, ...)

# 3) Bağımlılıkları yükle
./gradlew build

# 4) Veritabanını hazırla (varsa)
./gradlew flywayMigrate

# 5) Çalıştır
./gradlew run
```

Proje: http://localhost:8080

## 🧪 Test

```bash
./gradlew test
```

## 📁 Klasör Yapısı (bu teslimde)

```
.
├── README.md                   (bu dosya — özet, kurulum, demo)
├── PROJE-RAPORU.md             (uzun form final raporu — markdown)
├── PROJE-RAPORU-SABLON.docx    (uzun form final raporu — Word)
├── LICENSE
├── .env.example
└── repo/                       (projenizin kaynak kodu — kendi yapınız)
    └── README.md               (repo'nuzun kendi README'si)
```

> Ekran görüntüleri, diyagramlar, API dokümantasyonu gibi ek dosyaları `repo/` içinde **kendi tercih ettiğiniz alt klasör yapısında** tutabilirsiniz. Rapor belgesinde bu dosyalara referans verirsiniz.

## 🛣 Roadmap

- [x] V1 — MVP (bu teslim)
- [ ] V2 — Android/iOS client (Kotlin Multiplatform), GraphQL endpoint
- [ ] V3 — AI içerik moderasyonu, öneri motoru

## 🤝 Katkı

Bu proje **BMU1208 Web Tabanlı Programlama** dersi kapsamında **Bitlis Eren Üniversitesi** — **Bilgisayar Mühendisliği** bölümünde bir final ödevi olarak geliştirilmiştir.

Ders yürütücüsü: **Dr. Öğr. Üyesi Davut ARI**

Kod katkısı beklenmez, ancak fikir / feedback için issue açabilirsiniz.

## 📜 Lisans

MIT © 2026 **EREN İRVEN** — Tam metin için [LICENSE](LICENSE).

## 🙋‍♂️ İletişim

- **Öğrenci:** EREN İRVEN
- **Öğrenci No:** 25080410219
- **E-posta:** erenirven231@gmail.com
- **Ders:** BMU1208 · Web Tabanlı Programlama
- **Kurum:** Bitlis Eren Üniversitesi — Mühendislik-Mimarlık Fakültesi

---

<sub>🤖 Bu projede [Claude Code](https://claude.com/claude-code) ve [Cursor](https://cursor.sh) gibi AI asistanları kullanılmıştır. Tüm mimari kararlar ve kullanım tercihleri öğrenci tarafından yapılmıştır.</sub>
