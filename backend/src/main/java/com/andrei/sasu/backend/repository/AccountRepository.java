package com.andrei.sasu.backend.repository;

import com.andrei.sasu.backend.model.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long>  {
}
