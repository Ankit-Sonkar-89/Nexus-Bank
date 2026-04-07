// package com.bharat.nexus_bank.model;

// public class TransactionLedger {
    
// }

// The Ledger

package com.bharat.nexus_bank.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TRANSACTIONS_LEDGER")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class TransactionLedger {
    @Id
    @Column(name = "TXN_ID")
    private String txnId;

    @Column(name = "SENDER_ACCOUNT")
    private Long senderAccount;

    @Column(name = "RECEIVER_ACCOUNT")
    private Long receiverAccount;

    @Column(name = "AMOUNT")
    private Double amount;

    @Column(name = "TXN_TYPE")
    private String txnType; // IMPS, NEFT, DEPOSIT

    @Column(name = "STATUS")
    private String status; // SUCCESS, FAILED

    @Column(name = "REMARKS")
    private String remarks;

    @Column(name = "TXN_TIMESTAMP", insertable = false, updatable = false)
    private LocalDateTime txnTimestamp;
}