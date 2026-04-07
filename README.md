# 🏦 NexusBank: Core Digital Banking & Ledger System

## 📌 Project Overview
NexusBank is an enterprise-grade NeoBanking application simulating real-world financial operations. It features a completely custom-built Double-Entry Ledger system ensuring 100% ACID compliance during Peer-to-Peer (P2P) money transfers.

## 🚀 Technology Stack
*   **Backend:** Java 17, Spring Boot 3.x
*   **Database:** MySQL (Spring Data JPA / Hibernate)
*   **Security:** Real-time Email OTP Authentication (JavaMailSender)
*   **Frontend:** HTML5, CSS3 (Premium Glassmorphism Dark Theme), Vanilla JavaScript

## 💡 Core Engineering Features
1. **ACID-Compliant Transactions:** Utilized Spring's `@Transactional` annotation to guarantee atomicity during fund transfers. If a receiver's account is invalid or the server crashes, the sender's debit is automatically rolled back.
2. **Automated Account Provisioning:** Admin portal securely creates KYC-verified accounts, generating unique 12-digit Account Numbers and automatically crediting a ₹5000 welcome bonus.
3. **Secure 2FA Login:** Implemented a custom Two-Factor Authentication system that sends 6-digit dynamic OTPs to the customer's registered email via Google SMTP.
4. **Digital Passbook & Dashboard:** A highly responsive, zero-refresh UI that fetches live balances and transaction ledgers asynchronously using the JavaScript Fetch API.

## ⚙️ Setup Instructions
1. Create a MySQL database named `NEXUS_BANK_DB`.
2. Update `application.properties` with your database credentials and Gmail App Password.
3. Run the Spring Boot application on Port `8086`.
4. Access the Customer Portal at `http://localhost:8086/`.
5. Access the Admin Vault at `http://localhost:8086/admin.html`.
