package ru.netology.loanproducer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.netology.loanproducer.entity.LoanEntity;

public interface LoanRepository extends JpaRepository<LoanEntity, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE LoanEntity l SET l.status = :newStatus WHERE l.id = :loanId")
    int updateStatus(@Param("newStatus") String newStatus, @Param("loanId") Long loanId);

}
