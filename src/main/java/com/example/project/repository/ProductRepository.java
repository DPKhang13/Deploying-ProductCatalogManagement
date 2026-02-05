package com.example.project.repository;

import com.example.project.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findByNameContainingIgnoreCaseOrCategory_NameContainingIgnoreCase(
            String productName,
            String categoryName
    );

    boolean existsByName(String name);

    List<Product> findByCategory_Id(Long id);
    List<Product> findByCategory_Name(String name);
}
