package desafio_unidac_sb.resources;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	public String newEmployee(@ModelAttribute("employee") Employee employee) {
		return "newEmployee";
	}
	
	@PostMapping(value ="/employees/saveEmployee")
	public String saveEmployee(@ModelAttribute("employee") Employee employee) {
		List<Employee> list = employeeRepository.findAll();
		for(Employee x: list) {
			if(x.hashCode()==employee.hashCode()) {
				return "newEmployee";  
			}
		}
		employeeRepository.save(employee);
		return "redirect:/employees";		
	}
	
	@PutMapping("employees/editEmployee/{id}")
	public String editEmployee(@PathVariable("id") long id, Model model) {
		Optional<Employee> employeeOpt = employeeRepository.findById(id);
		model.addAttribute("employee", employeeOpt.get());
		return "newEmployee";
	}
	
	@GetMapping("employees/deleteEmployee/{id}")
	public String deleteEmployee(@PathVariable("id") long id) {
		Employee employee = employeeRepository.findById(id).get();
		employeeRepository.delete(employee);
		return "redirect:/employees";
	}
	
	@GetMapping("employees/addRecipe/{id}")
	public String addRecipe(@PathVariable("id") long id, Model model) {
		List<Recipe> listRecipe = recipeRepository.findAll();
		model.addAttribute("listRecipe", listRecipe);
		Employee employee = employeeRepository.findById(id).get();
		model.addAttribute("employee", employee);
		return "addRecipe";
	}
	
	@PutMapping("employees/addRecipe/{id}/save")
	public String saveRecipeEmployee(@PathVariable ("id") Long id, Model model, @ModelAttribute ("recipe") Recipe recipe) {
		Employee employee = employeeRepository.findById(id).get();
		recipe = recipeRepository.findById(id).get();
		employee.setRecipe(recipe);
		recipe.setEmployee(employee);
		employeeRepository.save(employee);
		recipeRepository.save(recipe);
		return "employees";
	}

}
