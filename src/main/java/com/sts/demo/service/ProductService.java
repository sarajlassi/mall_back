package com.sts.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sts.demo.entity.Category;
import com.sts.demo.entity.Product;
import com.sts.demo.repository.ProductRepository;

@Service
public class ProductService {
	@Autowired 
	public   ProductRepository productRepository;

	
	 


	public List<Product> getProducts(){
		List<Product> product = new ArrayList<Product>();  
		productRepository.findAll().forEach(product1 -> product.add(product1));  
		return product;  
	}
	public List<Product> getProductByName(String nameProduct)   
	{  List<Product> productfind = new ArrayList<Product>();  
	productRepository.findByNameProduct(nameProduct);
		  return  productfind;
	}  
	
	public Product addProduct(Product product) {
        return productRepository.save(product);
    }
	public void deletePRODUCT(Integer id) {

		productRepository.deleteById(id);
	    }
		  public Optional<Product> getProductById(Integer id) {
		        return productRepository.findById(id);
		    }

		    public Product updateProduct(Product updatedProduct) {
		        Product existingProduct = productRepository.findById(updatedProduct.getIdProduct())
		                                                  .orElseThrow();

		        existingProduct.setNameProduct(updatedProduct.getNameProduct());
		        existingProduct.setShop(updatedProduct.getShop());
		        existingProduct.setPrice(updatedProduct.getPrice());
		        existingProduct.setDescriptionProduct(updatedProduct.getDescriptionProduct());
		        existingProduct.setImageProduct(updatedProduct.getImageProduct());

		        return productRepository.save(existingProduct);
		    }
	
}
