package com.sts.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sts.demo.entity.Category;
import com.sts.demo.repository.CategoryRepository;

@Service
public class CategoryService {
	
	@Autowired 
	
	public CategoryRepository categoryRepository;
	public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }
	 public Optional<Category> getCategoryById(Long id) {
	        return categoryRepository.findById(id);
	    }
	public List<Category> getCategories(){
		List<Category> category = new ArrayList<Category>();  
		categoryRepository.findAll().forEach(category1 -> category.add(category1));  
		return category;  
	}
	public List<Category> getCategoryByName(String nameCategory)   
	{  List<Category> categoryfind = new ArrayList<Category>();  
		  categoryRepository.findByNameCategory(nameCategory).forEach(category1 -> categoryfind.add(category1));
		  return categoryfind;
	} 
	  public void deleteCategory(Long id) {
	        categoryRepository.deleteById(id);
	    }
	  public Optional<Category> getCategoryById1(Long id) {
	        return categoryRepository.findById(id);
	    }

	    public Category updateCategory(Category updatedCategory) {
	        return categoryRepository.save(updatedCategory);
	    }
}