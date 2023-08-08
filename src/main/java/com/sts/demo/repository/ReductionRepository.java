package com.sts.demo.repository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sts.demo.entity.Category;
import com.sts.demo.entity.Product;
import com.sts.demo.entity.Reduction;

@Repository
public interface ReductionRepository extends JpaRepository<Reduction,Integer>  {
	@Query(value="SELECT * FROM Reduction  WHERE nameReduction = ?")
	   public List<Reduction> findByNameReduction(String nameReduction);
}
