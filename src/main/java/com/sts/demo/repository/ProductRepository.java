package com.sts.demo.repository;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sts.demo.entity.Category;
import com.sts.demo.entity.Product;
import com.sts.demo.entity.Shop;

@Repository
public interface ProductRepository  extends JpaRepository<Product,Integer>{
	 @Query(value="SELECT * FROM Product WHERE nameProduct = ?")
	   public List<Product> findByNameProduct(String nameProduct);
	 List<Product> findByShop(Shop shop);
}
