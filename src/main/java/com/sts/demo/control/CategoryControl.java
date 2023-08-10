package com.sts.demo.control;

 
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;

import com.sts.demo.entity.Category;
import com.sts.demo.service.CategoryService;

@RestController
@RequestMapping(path="api/mall-1/Category")

@CrossOrigin(origins = "http://localhost:4200")
public class CategoryControl {

	@Autowired
	private final CategoryService categoryservice;
	
	
	public CategoryControl(CategoryService categoryservice) {
		 
		this.categoryservice = categoryservice;
	}
	 @PostMapping("/add")
	    public ResponseEntity<Category> addCategory(@RequestParam("image") MultipartFile image,
	                                                @RequestParam("nameCategory") String nameCategory) {
	        try {
	            String imageUrl = saveImage(image);

	            Category category = new Category();
	            category.setNameCategory(nameCategory);
	            category.setImageCategory(imageUrl);

	            Category addedCategory = categoryservice.addCategory(category);
	            
	            return ResponseEntity.ok(addedCategory);
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
	        }
	    }
	 private String saveImage(MultipartFile image) throws IOException {
	        String uploadDir = "C:\\Users\\DELL\\Downloads"; // Update with your actual directory path
	        String filename = UUID.randomUUID().toString() + "." + StringUtils.getFilenameExtension(image.getOriginalFilename());
	        Path imagePath = Paths.get(uploadDir).resolve(filename);

	        Files.copy(image.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);

	        return  filename; 
	    }
	 @GetMapping("/images/{imageName:.+}")
	 public ResponseEntity<Resource> getImage(@PathVariable String imageName) throws IOException {
	     Path imagePath = Paths.get("C:\\Users\\DELL\\Downloads").resolve(imageName);
	     Resource resource = new UrlResource(imagePath.toUri());

	     if (resource.exists() && resource.isReadable()) {
	         return ResponseEntity.ok()
	             .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_PNG_VALUE) // Adjust content type based on your image type
	             .body(resource);
	     } else {
	         return ResponseEntity.notFound().build();
	     }
	 }

	@GetMapping("/categories")
	public List<Category> getCategories(){
		return categoryservice.getCategories();
	}
	
	@GetMapping("/categories/{nameCategory}")  
	private List<Category> getCategory(@PathVariable("nameCategory") String nameCategory)   
	{  
		return categoryservice.getCategoryByName(nameCategory);  
	}
	 @DeleteMapping("delete/{id}")
	    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
	        Optional<Category> category = categoryservice.getCategoryById(id);
	        if (category.isPresent()) {
	        	categoryservice.deleteCategory(id);
	            return ResponseEntity.noContent().build();
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }
	 @PutMapping("update/{id}")
	    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category updatedCategory) {
	        Optional<Category> categoryOptional = categoryservice.getCategoryById(id);

	        if (categoryOptional.isPresent()) {
	            Category category = categoryOptional.get();
	            category.setNameCategory(updatedCategory.getNameCategory());
	            category.setImageCategory(updatedCategory.getImageCategory());

	            // Save the updated category
	            Category savedCategory = categoryservice.updateCategory(category);

	            return new ResponseEntity<>(savedCategory, HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	    }
	
}