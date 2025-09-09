package com.example.API.repository;

import com.example.API.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    // You can add custom query methods here if needed, for example:
    // Optional<Account> findByHolderName(String holderName);
}
