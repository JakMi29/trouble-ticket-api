# Trouble Ticket API

Trouble Ticket API to aplikacja do zarządzania zgłoszeniami serwisowymi. Umożliwia tworzenie zgłoszeń, dodawanie notatek, zarządzanie statusami zgłoszeń oraz ich pobieranie. Aplikacja wykorzystuje JWT do uwierzytelniania użytkowników. Domyślni użytkownicy to:
- **User**: login: `user`, hasło: `password`
- **Admin**: login: `admin`, hasło: `password`

Do zarządzania migracjami bazy danych użyto Flyway. W aplikacji zaimplementowano własne wyjątki oraz globalny handler do ich obsługi.

## Wymagania systemowe
- Java 17 lub nowsza.
- Gradle 7.0 lub nowszy.
- Docker (do uruchamiania aplikacji i bazy danych).

## Instrukcja uruchomienia projektu

### 1. Klonowanie repozytorium
Skopiuj repozytorium na swój lokalny komputer:
```bash
git clone https://github.com/JakMi29/trouble-ticket-api
cd trouble-ticket-api
```

### 2. Konfiguracja i uruchamianie projektu
Projekt korzysta z bazy danych, której konfiguracja znajduje się w pliku `application.yaml`. Możesz uruchomić całą aplikację (Spring Boot oraz bazę danych PostgreSQL) za pomocą Dockera:
```bash
docker-compose up -d
```
Plik `docker-compose.yaml` zawiera konfigurację zarówno dla PostgreSQL, jak i aplikacji Spring Boot. Po uruchomieniu aplikacja będzie dostępna pod adresem: `http://localhost:8080`.

### 3. Testowanie aplikacji
Aplikacja będzie dostępna pod adresem: `http://localhost:8080`. Możesz przetestować API za pomocą narzędzi takich jak Postman lub cURL. Przykładowe endpointy:
- Tworzenie zgłoszenia: `POST /api/v1/troubleTicket`
- Pobieranie zgłoszenia: `GET /api/v1/troubleTicket/{id}`
- Pobieranie zgłoszeń: `GET /api/v1/troubleTicket`
- Dodawanie notatki: `POST /api/v1/troubleTicket/{id}/note`
- Zamknięcie zgłoszenia: `PATCH /api/v1/troubleTicket/{id}`

## Tworzenie notatek
Tworzenie notatek w aplikacji jest dostępne wyłącznie dla użytkowników z rolą ADMIN. Upewnij się, że użytkownik posiada odpowiednie uprawnienia przed próbą wykonania tej operacji.