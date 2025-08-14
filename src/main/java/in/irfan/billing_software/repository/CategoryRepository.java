package in.irfan.billing_software.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import in.irfan.billing_software.entity.CategoryEntity;



public interface CategoryRepository extends JpaRepository<CategoryEntity,Long> {
 Optional<CategoryEntity> findByCategoryId(String categoryId);   
}
