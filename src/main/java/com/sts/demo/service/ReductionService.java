package com.sts.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sts.demo.entity.Category;
import com.sts.demo.entity.Reduction;
import com.sts.demo.repository.ReductionRepository;

@Service
public class ReductionService {
	
	@Autowired
public ReductionRepository reductionRepository;

	public Reduction getReductionById(int id) {
	    return reductionRepository.findById(id)
	                             .orElseThrow();
	}
public List<Reduction> getReductions(){
	List<Reduction> reduction = new ArrayList<Reduction>();  
	reductionRepository.findAll().forEach(reduction1 -> reduction.add(reduction1));  
	return reduction;  
}

public List<Reduction> getReductionByName(String nameReduction)   
{  List<Reduction> reductionfind = new ArrayList<Reduction>();  
reductionRepository.findByNameReduction(nameReduction);
	  return reductionfind;
}  
public Reduction addReduction(Reduction reduction) {
    return reductionRepository.save(reduction);
}

public Reduction updateReduction(int idReduction, Reduction updatedReduction) {
    Reduction existingReduction = getReductionById(idReduction);
    existingReduction.setNameReduction(updatedReduction.getNameReduction());
    existingReduction.setNewprice(updatedReduction.getNewprice());
    existingReduction.setPourcentage(updatedReduction.getPourcentage());
     
    existingReduction.setProduct(updatedReduction.getProduct());
    return reductionRepository.save(existingReduction);
}

public void deleteReduction(int idReduction) {
    Reduction existingReduction = getReductionById(idReduction);
    reductionRepository.delete(existingReduction);
}
}
