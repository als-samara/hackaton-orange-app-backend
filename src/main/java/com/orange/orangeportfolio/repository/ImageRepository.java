package com.orange.orangeportfolio.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.orange.orangeportfolio.model.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long>{
	Optional<Image> findByName(String name);
	
	public List<Image> findAllByUserId(Long userId);
}
