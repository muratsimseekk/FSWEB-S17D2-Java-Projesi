package rest;

import com.workintech.s17d2.model.*;
import com.workintech.s17d2.tax.Taxable;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/workintech")
@RestController
@CrossOrigin("*")
public class DeveloperController {

    Map<Integer , Developer> developers ;

    private Taxable developerTax ;
    @PostConstruct
    public void init(){
        developers = new HashMap<>();
    }

    @GetMapping("/developers")
    public Map<Integer,Developer> getDevelopers(){
        return developers;
    }

    @Autowired
    public DeveloperController(Taxable taxable){
        this.developerTax=taxable;
    }

    @GetMapping("/developers/{id}")
    public Developer getDeveloperByID (@PathVariable int id ){
        return developers.get(id);
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


    @PutMapping("/developers/{id}")
    public Developer updateDeveloperById(@PathVariable int id , @RequestBody Developer updatedDeveloper){
        if (developers.containsKey(id)){
            developers.put(id,updatedDeveloper);
        }
        return updatedDeveloper;
    }

    @DeleteMapping("/developer/{id}")
    public Developer deleteDeveloper(@PathVariable int id){
        return  developers.remove(id);
    }


}
