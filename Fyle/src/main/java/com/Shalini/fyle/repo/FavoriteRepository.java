package com.Shalini.fyle.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Shalini.fyle.entities.Favorite;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {


	boolean existsByBank(String name);

}
