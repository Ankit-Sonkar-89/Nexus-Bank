package com.bharat.nexus_bank.service;

import com.bharat.nexus_bank.model.AccountInfo;
import com.bharat.nexus_bank.model.CustomerInfo;
import com.bharat.nexus_bank.model.TransactionLedger;
import com.bharat.nexus_bank.repository.AccountRepository;
import com.bharat.nexus_bank.repository.CustomerRepository;
import com.bharat.nexus_bank.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@Service
public class BankingService {

    private final AccountRepository accountRepo;
    private final TransactionRepository txnRepo;
    private final CustomerRepository customerRepo; // ✅ Added Customer Repo

    public BankingService(AccountRepository accountRepo, TransactionRepository txnRepo, CustomerRepository customerRepo) {
        this.accountRepo = accountRepo;
        this.txnRepo = txnRepo;
        this.customerRepo = customerRepo;
    }

    public AccountInfo getAccountDetails(Long accountNo) {
        return accountRepo.findById(accountNo).orElse(null);
    }

    public List<TransactionLedger> getMiniStatement(Long accountNo) {
        return txnRepo.findStatementByAccountNo(accountNo);
    }

    @Transactional
    public Map<String, Object> transferFunds(Long senderAccNo, Long receiverAccNo, Double amount, String remarks) {
        // ... (Aapka purana Transfer logic as it is rahega)
        if (amount <= 0) return Map.of("status", "FAILED", "message", "Amount must be greater than zero!");
        if (senderAccNo.equals(receiverAccNo)) return Map.of("status", "FAILED", "message", "Cannot transfer to same account!");

        AccountInfo sender = accountRepo.findById(senderAccNo).orElse(null);
        AccountInfo receiver = accountRepo.findById(receiverAccNo).orElse(null);

        if (sender == null || receiver == null) return Map.of("status", "FAILED", "message", "Account not found!");
        if (sender.getCurrentBalance() < amount) return Map.of("status", "FAILED", "message", "Insufficient Balance!");

        sender.setCurrentBalance(sender.getCurrentBalance() - amount);
        receiver.setCurrentBalance(receiver.getCurrentBalance() + amount);

        accountRepo.save(sender);
        accountRepo.save(receiver);

        String txnId = "IMPS-" + UUID.randomUUID().toString().substring(0, 10).toUpperCase();
        TransactionLedger ledger = TransactionLedger.builder()
                .txnId(txnId).senderAccount(senderAccNo).receiverAccount(receiverAccNo)
                .amount(amount).txnType("IMPS_TRANSFER").status("SUCCESS").remarks(remarks).build();
        txnRepo.save(ledger);

        return Map.of("status", "SUCCESS", "message", "Transfer successful!", "txnId", txnId);
    }

    // 🔥 NEW FEATURE: ADMIN OPENS A NEW ACCOUNT WITH ₹5000 BONUS
    @Transactional
    public Map<String, Object> openNewAccount(CustomerInfo customer) {
        // 1. Generate Customer ID
        String custId = "C-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        customer.setCustomerId(custId);
        customer.setKycStatus("VERIFIED");
        customerRepo.save(customer);

        // 2. Generate 12-digit Unique Account Number
        long accNo = 100000000000L + (long)(new Random().nextDouble() * 90000000000L);

        // 3. Create Account with ₹5000 Default Balance
        AccountInfo account = AccountInfo.builder()
                .accountNo(accNo)
                .customerId(custId)
                .ifscCode("NEXUS0001")
                .accountType("SAVINGS")
                .currentBalance(5000.00) // 💰 Welcome Bonus
                .interestRate(4.00)
                .accountStatus("ACTIVE")
                .build();
        accountRepo.save(account);

        // 4. Create Initial Transaction Ledger Entry (Audit Trail)
        String txnId = "DEP-" + UUID.randomUUID().toString().substring(0, 10).toUpperCase();
        TransactionLedger initialDeposit = TransactionLedger.builder()
                .txnId(txnId)
                .senderAccount(null) // Bank is funding this
                .receiverAccount(accNo)
                .amount(5000.00)
                .txnType("ACCOUNT_OPENING")
                .status("SUCCESS")
                .remarks("Welcome Bonus / Initial Deposit")
                .build();
        txnRepo.save(initialDeposit);

        return Map.of(
                "status", "SUCCESS", 
                "message", "Account created successfully!",
                "accountNo", accNo,
                "customerId", custId
        );
    }
}