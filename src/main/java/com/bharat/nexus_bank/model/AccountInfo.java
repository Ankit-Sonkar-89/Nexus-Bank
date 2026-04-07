// package com.bharat.nexus_bank.model;

// public class AccountInfo {
    
// }
 
// The Vault 

package com.bharat.nexus_bank.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ACCOUNT_INFO")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class AccountInfo {
    @Id
    @Column(name = "ACCOUNT_NO")
    private Long accountNo;

    @Column(name = "CUSTOMER_ID")
    private String customerId;

    @Column(name = "IFSC_CODE")
    private String ifscCode;

    @Column(name = "ACCOUNT_TYPE")
    private String accountType;

    @Column(name = "CURRENT_BALANCE")
    private Double currentBalance;

    @Column(name = "INTEREST_RATE")
    private Double interestRate;

    @Column(name = "ACCOUNT_STATUS")
    private String accountStatus;

    @Column(name = "ACTIVATION_DATE", insertable = false, updatable = false)
    private LocalDateTime activationDate;
}