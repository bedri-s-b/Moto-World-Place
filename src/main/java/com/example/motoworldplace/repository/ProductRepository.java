package com.example.motoworldplace.repository;

import com.example.motoworldplace.model.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity,Long> {

    @Query("SELECT p FROM ProductEntity p LEFT JOIN FETCH p.pictures WHERE p.id = :id")
    Optional<ProductEntity> findByCurrentId(@Param("id") Long id);
}
