package com.workintech.s17d2.rest;

import com.workintech.s17d2.model.*;
import com.workintech.s17d2.tax.Taxable;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/workintech")
public class DeveloperController {

    private Map<Integer , Developer> developers ;

    private Taxable developerTax ;

    @Autowired
    public DeveloperController(Taxable taxable){
        this.developerTax=taxable;
    }


    @PostConstruct
    public void init(){
        developers = new HashMap<>();
    }

    @PostMapping("/developers")
    public Developer addDeveloper(@RequestBody Developer newDeveloper){
        switch (newDeveloper.getExperience()){
            case JUNIOR -> {
                newDeveloper.setSalary(newDeveloper.getSalary() - newDeveloper.getSalary()* developerTax.getSimpleTaxRate());
                break;
            }
            case MID -> {
                newDeveloper.setSalary(newDeveloper.getSalary() - newDeveloper.getSalary()* developerTax.getMiddleTaxRate());
                break;
            }
            case SENIOR -> {
                newDeveloper.setSalary(newDeveloper.getSalary() - newDeveloper.getSalary()* developerTax.getUpperTaxRate());
                break;
            }
            default -> {
                break;
            }
        }
        developers.put(newDeveloper.getId(), newDeveloper);
        return newDeveloper;
    }

    @GetMapping("/developers")
    public List<Developer> getDevelopers(){
        return developers.values().stream().toList();

    }

    @GetMapping("/developers/{id}")
    public Developer getDeveloperByID (@PathVariable Integer id ){
        return developers.get(id);
    }

    @PutMapping("/developers/{id}")
    public Developer updateDeveloperById(@PathVariable Integer id , @RequestBody Developer updatedDeveloper){
        if (developers.containsKey(id)){
            developers.put(id,updatedDeveloper);
        }
        return updatedDeveloper;
    }

    @DeleteMapping("/developer/{id}")
    public Developer deleteDeveloper(@PathVariable Integer id){
        return  developers.remove(id);
    }


}
