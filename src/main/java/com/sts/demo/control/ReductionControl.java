package com.sts.demo.control;

import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sts.demo.entity.Category;
import com.sts.demo.entity.Reduction;
import com.sts.demo.service.ReductionService;

@RestController
@RequestMapping(path="api/mall-1/Reduction")

@CrossOrigin(origins = "http://localhost:4200")
public class ReductionControl {
@Autowired 
public final ReductionService reductionservice;

public ReductionControl(ReductionService reductionservice) {
	super();
	this.reductionservice = reductionservice;
}

@GetMapping("reductions")
public List<Reduction> getReductions(){
	return reductionservice.getReductions();
}


@GetMapping("reductions/{nameReduction}")  
private List<Reduction> getReduction(@PathVariable("nameReduction") String nameReduction)   
{  
	return reductionservice.getReductionByName(nameReduction);  
}

@PostMapping("/add")
public ResponseEntity<Reduction> addReduction(@RequestBody Reduction reduction) {
    Reduction savedReduction = reductionservice.addReduction(reduction);
    return new ResponseEntity<>(savedReduction, HttpStatus.CREATED);
}

@PutMapping("update/{id}")
public Reduction updateReduction(@PathVariable int id, @RequestBody Reduction updatedReduction) {
    return reductionservice.updateReduction(id, updatedReduction);
}

@DeleteMapping("delete/{id}")
public ResponseEntity<String> deleteReduction(@PathVariable int id) {
	reductionservice.deleteReduction(id);
    return ResponseEntity.ok("Reduction with ID " + id + " deleted successfully");
}

}
