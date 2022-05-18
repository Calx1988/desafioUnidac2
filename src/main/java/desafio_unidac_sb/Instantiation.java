package desafio_unidac_sb;

import java.util.Arrays;

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
		
		employeeRepository.saveAll(Arrays.asList(e1,e2));
		recipeRepository.saveAll(Arrays.asList(r1,r2));
		
		
	}
	

}
