// package com.bharat.nexus_bank.repository;

// public class TransactionRepository {
    
// }

package com.bharat.nexus_bank.repository;
import com.bharat.nexus_bank.model.TransactionLedger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface TransactionRepository extends JpaRepository<TransactionLedger, String> {
    // Ye custom query ek account ki saari history (bheja hua aur aaya hua paisa) nikalegi
    @Query("SELECT t FROM TransactionLedger t WHERE t.senderAccount = :accNo OR t.receiverAccount = :accNo ORDER BY t.txnTimestamp DESC")
    List<TransactionLedger> findStatementByAccountNo(@Param("accNo") Long accNo);
}