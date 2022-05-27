package desafio_unidac_sb.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import desafio_unidac_sb.entities.Employee;
import desafio_unidac_sb.entities.Recipe;
import desafio_unidac_sb.repositories.EmployeeRepository;
import desafio_unidac_sb.repositories.RecipeRepository;

@Controller
@Transactional
public class EmployeeResource {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private RecipeRepository recipeRepository;
	
	public EmployeeResource(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}
	
	@GetMapping(value="/employees/{id}")
	public Employee findById(Long id) {
		Employee employee = employeeRepository.findById(id).get();
		return employee;
	}
	
	@GetMapping(value = "/employees")
	public String employees(Model model) {
		model.addAttribute("listEmployees", employeeRepository.findAll());
		return "employees";
	}
	
	@GetMapping(value="/newEmployee")
	public String newEmployee(@ModelAttribute("employee") Employee employee, Model model) {
		List<Recipe> listRecipe = recipeRepository.findAll();
		List<Employee> listEmployees = employeeRepository.findAll();
		List<Recipe> usedRecipes = new ArrayList<>();
		for(Recipe recipe: listRecipe) {
			for(Employee e: listEmployees) {
				if(e.getRecipe()==recipe) {
					usedRecipes.add(recipe);
				}
			}			
		}
		List<Recipe> notUsedRecipes= listRecipe;
		notUsedRecipes.removeAll(usedRecipes);
		model.addAttribute("notUsedRecipes", notUsedRecipes);
		return "newEmployee";
	}
	
	@PostMapping(value ="/employees/saveEmployee")
	public String saveEmployee(@ModelAttribute("employee") Employee employee) {
		List<Employee> listEmployee = employeeRepository.findAll();
		for(Employee e: listEmployee) {
			if(e.getId()!=employee.getId() && e.hashCode()==employee.hashCode()) {
				return "redirect:/newEmployee";
			}
		}
		employeeRepository.save(employee);
		return "redirect:/employees";		
	}
	
	@GetMapping("/employees/editEmployee/{id}")
	public String editEmployee(@PathVariable("id") long id, Model model) {
		Optional<Employee> employeeOpt = employeeRepository.findById(id);
		List<Recipe> listRecipe = recipeRepository.findAll();
		List<Employee> listEmployees = employeeRepository.findAll();
		List<Recipe> usedRecipes = new ArrayList<>();
		for(Recipe recipe: listRecipe) {
			for(Employee e: listEmployees) {
				if(e.getRecipe()==recipe) {
					usedRecipes.add(recipe);
					if(e.hashCode()==employeeOpt.hashCode()) {
						usedRecipes.remove(recipe);
					}
				}
			}			
		}
		List<Recipe> notUsedRecipes= listRecipe;
		notUsedRecipes.removeAll(usedRecipes);
		model.addAttribute("notUsedRecipes", notUsedRecipes);
		model.addAttribute("employee", employeeOpt.get());
		return "newEmployee";
	}
	
	
	@GetMapping("employees/deleteEmployee/{id}")
	public String deleteEmployee(@PathVariable("id") long id) {
		Employee employee = employeeRepository.findById(id).get();
		employeeRepository.delete(employee);
		return "redirect:/employees";
	}

}
