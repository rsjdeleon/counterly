package com.counterly.user.repository;

import com.counterly.user.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
}
