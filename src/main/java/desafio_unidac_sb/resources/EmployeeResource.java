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

import desafio_unidac_sb.entities.Employee;
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
	
	@GetMapping(value = "/employees")
	public String employees(Model model) {
		model.addAttribute("listEmployees", employeeRepository.findAll());
		model.addAttribute("recipes", recipeRepository.findAll());
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
	
	@GetMapping("employees/editEmployee/{id}")
	public String editEmployee(@PathVariable("id") long id, Model model) {
		Optional<Employee> employeeOpt = employeeRepository.findById(id);
		model.addAttribute("employee", employeeOpt.get());
		return "newEmployee";
	}
	
	@GetMapping("employees/deleteEmployee/{id}")
	public String deleteEmployee(@PathVariable("id") long id) {
		Optional<Employee> employeeOpt = employeeRepository.findById(id);
		employeeRepository.delete(employeeOpt.get());
		return "redirect:/employees";
	}
	

}
