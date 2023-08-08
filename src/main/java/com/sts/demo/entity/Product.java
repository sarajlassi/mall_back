package com.sts.demo.entity;
import com.sts.demo.entity.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="Product")
public class Product {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int idProduct;
	
	private String nameProduct;
	@ManyToOne
	@JoinColumn(name="idShop", nullable=false)
	private Shop shop;
	
	private float price;
	private String descriptionProduct;
	 
	 
	private String imageProduct;
	
	
	
	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}



 

	public Product(String imageProduct) {
		super();
		this.imageProduct = imageProduct;
	}





	public int getIdProduct() {
		return idProduct;
	}



	 
	public void setIdProduct(int idProduct) {
		this.idProduct = idProduct;
	}



	 
	public String getNameProduct() {
		return nameProduct;
	}




	public void setNameProduct(String nameProduct) {
		this.nameProduct = nameProduct;
	}


	 
	public Shop getShop() {
		return shop;
	}




	public void setShop(Shop shop) {
		this.shop = shop;
	}



	 
	public float getPrice() {
		return price;
	}




	public void setPrice(float price) {
		this.price = price;
	}



	 
	public String getDescriptionProduct() {
		return descriptionProduct;
	}




	public void setDescriptionProduct(String descriptionProduct) {
		this.descriptionProduct = descriptionProduct;
	}



	 
 



	  




	public String getImageProduct() {
		return imageProduct;
	}





	public void setImageProduct(String imageProduct) {
		this.imageProduct = imageProduct;
	}
	
	
	

}
