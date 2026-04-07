// package com.bharat.nexus_bank.repository;

// public class CustomerRepository {
    
// }

package com.bharat.nexus_bank.repository;
import com.bharat.nexus_bank.model.CustomerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CustomerRepository extends JpaRepository<CustomerInfo, String> {}