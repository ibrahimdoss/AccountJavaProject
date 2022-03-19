package com.SpringBoot.account.repository;

import com.SpringBoot.account.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
}
