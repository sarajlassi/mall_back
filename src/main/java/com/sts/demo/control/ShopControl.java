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
import org.springframework.web.bind.annotation.ModelAttribute;
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
import com.sts.demo.entity.Product;
import com.sts.demo.entity.Shop;
import com.sts.demo.service.CategoryService;
import com.sts.demo.service.ShopService;

@RestController
@RequestMapping(path="api/mall-1/Shop")

@CrossOrigin(origins = "http://localhost:4200")
public class ShopControl {
@Autowired
private final ShopService shopService;
private final CategoryService categoryService;

@Autowired
public ShopControl(ShopService shopService, CategoryService categoryService) {
    this.shopService = shopService;
    this.categoryService = categoryService;
}
@GetMapping("shops")
public List<Shop> getShops(){
	return  shopService.getShops();
}

@GetMapping("/shops/{nameShop}")  
private List<Shop> getShop(@PathVariable("nameShop") String nameShop)   
{  
	return shopService.getShopByName(nameShop);  
}
@GetMapping("/shops/category/{categoryId}")
 
public List<Shop> getShopsByCategory(@PathVariable("categoryId")  Category category) {
    return shopService.getShopsByCategory( category);
}
private String saveImage(MultipartFile image) throws IOException {
    String uploadDir = "C:\\Users\\DELL\\Downloads"; 
    String filename = UUID.randomUUID().toString() + "." + StringUtils.getFilenameExtension(image.getOriginalFilename());
    Path imagePath = Paths.get(uploadDir).resolve(filename);

    Files.copy(image.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);

    return filename;
}

@PostMapping("/add")
public ResponseEntity<Shop> addShop(@RequestParam("image") MultipartFile image,
                                    @RequestParam("nameShop") String nameShop,
                                    @RequestParam("localisationShop") MultipartFile localisationShop,
                                    @RequestParam("idCategory") Long idCategory) {
    try {
        String imageUrl = saveImage(image);
        String localisationUrl = saveImage(localisationShop); 

        Category category = new Category();
        category.setIdCategory(idCategory);

        Shop shop = new Shop();
        shop.setNameShop(nameShop);
        shop.setLocalisationShop(localisationUrl); 
        shop.setCategory(category);
        shop.setImageShop(imageUrl);

        Shop addedShop = shopService.addShop(shop);

        return ResponseEntity.ok(addedShop);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
    }
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
public ResponseEntity<Void> deleteshop(@PathVariable Integer id) {
    Optional<Shop> shop = shopService.getShopById(id);
    if (shop.isPresent()) {
    	shopService.deleteShopById(id);
        return ResponseEntity.noContent().build();
    } else {
        return ResponseEntity.notFound().build();
    }
} public ResponseEntity<String> deleteShop(@PathVariable("id") int shopId) {
    try {
        shopService.deleteShopById(shopId);
        return new ResponseEntity<>("Shop with ID " + shopId + " deleted successfully.", HttpStatus.OK);
    } catch (Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
@PutMapping("update/{id}")
public ResponseEntity<Shop> updateShop(
        @PathVariable int id,
        @RequestParam(name = "newImage", required = false) MultipartFile newImage,
        @RequestParam(name = "newLocalisationImage", required = false) MultipartFile newLocalisationImage,
        @ModelAttribute Shop updatedShop
) {
    Optional<Shop> shopOptional = shopService.getShopById(id);

    if (shopOptional.isPresent()) {
        Shop shop = shopOptional.get();
        shop.setNameShop(updatedShop.getNameShop());
        shop.setLocalisationShop(updatedShop.getLocalisationShop());

        // Update the category if it's provided in the updatedShop
        if (updatedShop.getCategory() != null) {
            Category category = categoryService.getCategoryById(updatedShop.getCategory().getIdCategory())
                    .orElseThrow();

            shop.setCategory(category);
        }

        if (newImage != null && !newImage.isEmpty()) {
            try {
                String imageUrl = saveImage(newImage);
                shop.setImageShop(imageUrl);
            } catch (IOException e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        if (newLocalisationImage != null && !newLocalisationImage.isEmpty()) {
            try {
                String localisationImageUrl = saveImage(newLocalisationImage);
                shop.setLocalisationShop(localisationImageUrl);
            } catch (IOException e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        // Save the updated shop
        Shop savedShop = shopService.updateShop(id, shop);

        return ResponseEntity.ok(savedShop);
    } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

}
