package com.sts.demo.repository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sts.demo.entity.Category;
import com.sts.demo.entity.Product;
import com.sts.demo.entity.Reduction;
import com.sts.demo.entity.Shop;

@Repository
public interface ShopRepository extends JpaRepository<Shop,Integer> {
	@Query(value="SELECT * FROM Shop WHERE nameShop = ?")
	   public List<Shop> findByNameShop(String nameShop);
	
	
	@Query(value="SELECT * FROM Shop,Category WHERE Shop.category.idCategory == Category.idCategory")
	List<Shop> findByCategory( Category category );
}
