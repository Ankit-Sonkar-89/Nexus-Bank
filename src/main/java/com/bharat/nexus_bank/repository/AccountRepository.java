// package com.bharat.nexus_bank.repository;

// public class AccountRepository {
    
// }

package com.bharat.nexus_bank.repository;
import com.bharat.nexus_bank.model.AccountInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AccountRepository extends JpaRepository<AccountInfo, Long> {
    List<AccountInfo> findByCustomerId(String customerId); // Ek customer ke kitne accounts hain
}