package desafio_unidac_sb.resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
	
	@GetMapping(value="/findByName")
	public String findByName(Model model, @Param ("nameEmployee") String nameEmployee) {
		System.out.println(nameEmployee);
		if(nameEmployee==null) {
			model.addAttribute("listEmployees", employeeRepository.findAll());
			return "employees";
		}
		model.addAttribute("listEmployees", employeeRepository.findByName(nameEmployee));
		return "employees";
	}
	
	
	@GetMapping(value="/findByCpf")
	public String findByCpf(Model model, @Param("cpfEmployee") String cpfEmployee) {
		model.addAttribute("listEmployees", employeeRepository.findByCpf(cpfEmployee));
		return "employees";
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
		List<Recipe> usedRecipes = new ArrayList<Recipe>();
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
	public String saveEmployee(@Valid @ModelAttribute("employee") Employee employee, BindingResult bidingResult, Model model) {
		if(bidingResult.hasErrors()) {
			List<Recipe> listRecipe = recipeRepository.findAll();
			List<Employee> listEmployees = employeeRepository.findAll();
			List<Recipe> usedRecipes = new ArrayList<Recipe>();
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
		
		List<Employee> listEmployee = employeeRepository.findAll();
		for(Employee e: listEmployee) {
			if(e.getId()!=employee.getId() && e.hashCode()==employee.hashCode()) {
				boolean cpfAlreadyExists = true;
				model.addAttribute("cpfAlreadyExists", cpfAlreadyExists);
				List<Recipe> listRecipe = recipeRepository.findAll();
				List<Employee> listEmployees = employeeRepository.findAll();
				List<Recipe> usedRecipes = new ArrayList<Recipe>();
				for(Recipe recipe: listRecipe) {
					for(Employee x: listEmployees) {
						if(x.getRecipe()==recipe) {
							usedRecipes.add(recipe);
						}
					}			
				}
				List<Recipe> notUsedRecipes= listRecipe;
				notUsedRecipes.removeAll(usedRecipes);
				model.addAttribute("notUsedRecipes", notUsedRecipes);
				return "newEmployee";
			}
		}
		
		//valida????o de cpf
		char firstDigitChar = employee.getCpf().charAt(9);
		char secondDigitChar = employee.getCpf().charAt(10);
		int firstDigit = Character.getNumericValue(firstDigitChar);
		int secondDigit = Character.getNumericValue(secondDigitChar);
	
		int sumFirstDigit = 0;
		int sumSecondDigit = 0;
		
		int y = 10;
		for(int x=0; x<9; x++) {
			char charDigit = employee.getCpf().charAt(x);
			sumFirstDigit += Character.getNumericValue(charDigit) * y;
			y=y-1;
		}
		
		y=11;
		for(int x = 0; x<10; x++) {
			char charDigit = employee.getCpf().charAt(x);
			sumSecondDigit += Character.getNumericValue(charDigit) * y;
			y=y-1;
		}
		
		int supposedFirstDigit;
		int supposedSecondDigit;
		if(sumFirstDigit%11 == 0) {
			supposedFirstDigit = 1;
		}else {
			supposedFirstDigit = 11 - (sumFirstDigit%11);
			if(supposedFirstDigit == 10) {
				supposedFirstDigit = 0;
			}
		}
		if(sumSecondDigit%11 == 0) {
			supposedSecondDigit = 1;
		}else {
			supposedSecondDigit = 11 - (sumSecondDigit%11);
		}
		
		List<String> listInvalidCpf= new ArrayList<String>();
		listInvalidCpf.addAll(Arrays.asList("11111111111", 
				"22222222222", 
				"33333333333", 
				"44444444444", 
				"55555555555", 
				"66666666666",
				"77777777777",
				"88888888888",
				"99999999999",
				"00000000000"));
		
		boolean test = false;
		boolean cpfInvalid = false;
		for(String s: listInvalidCpf) {
			if(employee.getCpf().contentEquals(s)) {
				cpfInvalid=true;
			}
		}
			
		
		if(supposedFirstDigit != firstDigit || supposedSecondDigit != secondDigit) {
			cpfInvalid = true;
		}
		
		if(cpfInvalid) {
			model.addAttribute("cpfInvalid", cpfInvalid);
			List<Recipe> listRecipe = recipeRepository.findAll();
			List<Employee> listEmployees = employeeRepository.findAll();
			List<Recipe> usedRecipes = new ArrayList<Recipe>();
			for(Recipe recipe: listRecipe) {
				for(Employee x: listEmployees) {
					if(x.getRecipe()==recipe) {
						usedRecipes.add(recipe);
					}
				}			
			}
			List<Recipe> notUsedRecipes= listRecipe;
			notUsedRecipes.removeAll(usedRecipes);
			model.addAttribute("notUsedRecipes", notUsedRecipes);
			return "newEmployee";
		}
		//fim valida????o cpf
		System.out.println(cpfInvalid);
		employeeRepository.save(employee);
		return "redirect:/employees";		
	}
	
	@GetMapping("/employees/editEmployee/{id}")
	public String editEmployee(@PathVariable("id") long id, Model model) {
		Optional<Employee> employeeOpt = employeeRepository.findById(id);
		List<Recipe> listRecipe = recipeRepository.findAll();
		List<Employee> listEmployees = employeeRepository.findAll();
		List<Recipe> usedRecipes = new ArrayList<Recipe>();
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
