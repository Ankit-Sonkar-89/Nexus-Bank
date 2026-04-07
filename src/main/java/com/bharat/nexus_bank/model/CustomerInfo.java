// package com.bharat.nexus_bank.model;

// public class CustomerInfo {
    
// }

package com.bharat.nexus_bank.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "CUSTOMER_INFO")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class CustomerInfo {
    @Id
    @Column(name = "CUSTOMER_ID")
    private String customerId;

    @Column(name = "CUSTOMER_NAME")
    private String customerName;

    @Column(name = "DOB")
    private LocalDate dob;

    @Column(name = "CONTACT_NO")
    private String contactNo;

    @Column(name = "MAIL_ID")
    private String mailId;

    @Column(name = "AADHAR_NO")
    private String aadharNo;

    @Column(name = "KYC_STATUS")
    private String kycStatus;

    @Column(name = "CREATED_AT", insertable = false, updatable = false)
    private LocalDateTime createdAt;
}