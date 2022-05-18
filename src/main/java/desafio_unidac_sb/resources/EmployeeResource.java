package desafio_unidac_sb.resources;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import desafio_unidac_sb.repositories.EmployeeRepository;

@Controller
@Transactional
public class EmployeeResource {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	public EmployeeResource(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}
	
	@GetMapping(value = "/employees")
	public String employees(Model model) {
		model.addAttribute("listEmployees", employeeRepository.findAll());
		return "employees";
	}
	
	
	

}
