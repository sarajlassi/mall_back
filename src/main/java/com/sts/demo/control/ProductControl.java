package com.sts.demo.control;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.sts.demo.entity.Shop; 

import com.sts.demo.entity.Category;
import com.sts.demo.entity.Product;
import com.sts.demo.service.ProductService;

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
    public ResponseEntity<Product> addProduct(@RequestParam("image") MultipartFile image,
                                              @RequestParam("nameProduct") String nameProduct,
                                              @RequestParam("price") float price,
                                              @RequestParam("idShop") int idShop,
                                              @RequestParam("descriptionProduct") String descriptionProduct) {
        try {
            String imageUrl = saveImage(image);

            Shop shop = new Shop();
            shop.setIdShop(idShop);

            Product product = new Product();
            product.setNameProduct(nameProduct);
            product.setPrice(price);
            product.setShop(shop);
            product.setDescriptionProduct(descriptionProduct);
            product.setImageProduct(imageUrl);

            Product addedProduct = productservice.addProduct(product);

            return ResponseEntity.ok(addedProduct);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }
    }

    private String saveImage(MultipartFile image) throws IOException {
        String uploadDir = "C:\\Users\\DELL\\Downloads"; // Update with your actual directory path
        String filename = UUID.randomUUID().toString() + "." + StringUtils.getFilenameExtension(image.getOriginalFilename());
        Path imagePath = Paths.get(uploadDir).resolve(filename);

        Files.copy(image.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);

        return filename;
    }
    @GetMapping("/images/{imageName:.+}")
	 public ResponseEntity<Resource> getImage(@PathVariable String imageName) throws IOException {
	     Path imagePath = Paths.get("C:\\Users\\DELL\\Downloads").resolve(imageName);
	     Resource resource = new UrlResource(imagePath.toUri());

	     if (resource.exists() && resource.isReadable()) {
	         return ResponseEntity.ok()
	             .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_PNG_VALUE) 
	             .body(resource);
	     } else {
	         return ResponseEntity.notFound().build();
	     }
	 }
	 @DeleteMapping("delete/{id}")
	    public ResponseEntity<Void> deletePRODUCT(@PathVariable Integer id) {
	        Optional<Product> Product = productservice.getProductById(id);
	        if (Product.isPresent()) {
	        	productservice.deletePRODUCT(id);
	            return ResponseEntity.noContent().build();
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }
	 @PutMapping("update/{productId}")
	 public ResponseEntity<Product> updateProduct(@PathVariable int productId, @ModelAttribute Product updatedProduct,
	                                              @RequestParam(name = "newImage", required = false) MultipartFile newImage) {
	     Optional<Product> productOptional = productservice.getProductById(productId);

	     if (productOptional.isPresent()) {
	         Product product = productOptional.get();
	         product.setNameProduct(updatedProduct.getNameProduct());
	         product.setPrice(updatedProduct.getPrice());
	         product.setShop(updatedProduct.getShop());
	         product.setDescriptionProduct(updatedProduct.getDescriptionProduct());

	         if (newImage != null && !newImage.isEmpty()) {
	             try {
	                 String imageUrl = saveImage(newImage);
	                 product.setImageProduct(imageUrl);
	             } catch (IOException e) {
	                 return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	             }
	         }

	         // Save the updated product
	         Product savedProduct = productservice.updateProduct(product);

	         return new ResponseEntity<>(savedProduct, HttpStatus.OK);
	     } else {
	         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	     }
	 }}
	 