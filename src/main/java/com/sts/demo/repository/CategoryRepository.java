package com.sts.demo.repository;
import com.sts.demo.entity.*;

import org.springframework.stereotype.Repository;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long>{
	 @Query(value="SELECT * FROM Category WHERE nameCategory = ?")
	   public List<Category> findByNameCategory(String nameCategory);
}
