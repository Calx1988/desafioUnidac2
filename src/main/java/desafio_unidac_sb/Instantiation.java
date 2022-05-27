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

	public void run(String... args) throws Exception {
		
		Employee e1 = new Employee(null, "Cássio Alexsander", "12345678901");
		Employee e2 = new Employee(null, "Allana Caroline", "13579135791");
		Employee e3 = new Employee(null, "Karoline Branicki", "09876543210");
		Employee e4 = new Employee(null, "Karla Luana", "24680246802");


		employeeRepository.saveAll(Arrays.asList(e1,e2,e3,e4));
		
		Recipe r1 = new Recipe(null, "Sanduíche");
		Recipe r2 = new Recipe(null, "Bolo de Chocolate");
		Recipe r3 = new Recipe(null, "Croissant");
		Recipe r4 = new Recipe(null, "Cuca");
		Recipe r5 = new Recipe(null, "Rissole");

		recipeRepository.saveAll(Arrays.asList(r1,r2,r3,r4,r5));				
	}

}
