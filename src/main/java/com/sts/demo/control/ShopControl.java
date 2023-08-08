package com.sts.demo.control;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
public final ShopService shopService;
 

public ShopControl(ShopService shopService ) {
	super();
	this.shopService = shopService;
	 
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
 
@PostMapping("/add")
public ResponseEntity<Shop> addShop(@RequestBody Shop shop) {
	Shop addedShop = shopService.addShop(shop);
    return ResponseEntity.ok(addedShop);
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
public ResponseEntity<Shop> updateShop(@PathVariable int id, @RequestBody Shop updatedShop) {
    Shop updatedShopEntity = shopService.updateShop(id, updatedShop);
    return ResponseEntity.ok(updatedShopEntity);
}

}
