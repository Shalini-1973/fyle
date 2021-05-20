package com.Shalini.fyle.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Shalini.fyle.entities.Banks;

public interface BankRepository extends JpaRepository<Banks, Integer> {

	Banks findByName(String name);

}
