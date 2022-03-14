package com.example.pocketmark.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import com.example.pocketmark.domain.auth.Authority;

public interface AuthorityRepository extends JpaRepository<Authority,Long> {

}
