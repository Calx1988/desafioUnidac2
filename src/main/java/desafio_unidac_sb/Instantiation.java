package desafio_unidac_sb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import desafio_unidac_sb.entities.Employee;
import desafio_unidac_sb.entities.Recipe;
import desafio_unidac_sb.repositories.EmployeeRepository;
import desafio_unidac_sb.repositories.RecipeRepository;

@Component
public class Instantiation implements CommandLineRunner{
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private RecipeRepository recipeRepository;

	@Override
	public void run(String... args) throws Exception {
		
		Employee e1 = new Employee(null, "cassio correa", "12345");
		Employee e2 = new Employee(null, "allana correa", "23456");

		Recipe r1 = new Recipe(null, "Torta de Morango");
		Recipe r2 = new Recipe(null, "Pastel de Carne");
		Recipe r3 = new Recipe(null, "Torta de Chocolate");
		Recipe r4 = new Recipe(null, "Risólis de Frango");
		
		employeeRepository.saveAll(Arrays.asList(e1,e2));
		recipeRepository.saveAll(Arrays.asList(r1,r2,r3,r4));
		
		r1.setEmployee(e2);
		r2.setEmployee(e1);
		r3.setEmployee(e2);
		r4.setEmployee(e2);
		
		recipeRepository.saveAll(Arrays.asList(r1,r2,r3,r4));
		
		List<Employee> listEmployee = new ArrayList<>();
		listEmployee.addAll(employeeRepository.findAll());
	}
	

}
