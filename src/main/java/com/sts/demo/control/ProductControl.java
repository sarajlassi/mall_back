package com.sts.demo.control;

import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sts.demo.entity.Category;
import com.sts.demo.entity.Product;
import com.sts.demo.service.ProductService;
import java.util.Optional;
@RestController
@RequestMapping(path="api/mall-1/Product")


@CrossOrigin(origins = "http://localhost:4200")
public class ProductControl {

	
	@Autowired 
	public final ProductService productservice;

	public ProductControl(ProductService productservice) {
		 
		this.productservice = productservice;
	}

	@GetMapping("/products")
	public List<Product> getProducts(){
		return  productservice.getProducts();
	}

	@GetMapping("/products/{nameProduct}")  
	private List<Product> getProduct(@PathVariable("nameProduct") String nameProduct)   
	{  
		return productservice.getProductByName(nameProduct);  
	}
	 
	@PostMapping("/add")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
		Product addedProduct = productservice.addProduct(product);
        return ResponseEntity.ok(addedProduct);
    }
	 @DeleteMapping("delete/{idProduct}")
	    public ResponseEntity<Void> deletePRODUCT(@PathVariable int idProduct) {
	        Optional<Product> product = productservice.getProductById(idProduct);
	        if (product.isPresent()) {
	        	productservice.deletePRODUCT(idProduct);
	            return ResponseEntity.noContent().build();
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }
	 
	 @PutMapping("update/{productId}")
	    public ResponseEntity<Product> updateProduct(@PathVariable int productId, @RequestBody Product updatedProduct) {
	        updatedProduct.setIdProduct(productId);
	        Product updatedProductResult = productservice.updateProduct(updatedProduct);
	        return ResponseEntity.ok(updatedProductResult);
	    }
}
