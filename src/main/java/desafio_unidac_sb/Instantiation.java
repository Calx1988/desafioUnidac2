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
		
		Employee e1 = new Employee(null, "Cássio Alexsander", "12345");
		Employee e2 = new Employee(null, "Allana Caroline", "23456");
		Employee e3 = new Employee(null, "Karla Luana", "34567");
		Employee e4 = new Employee(null, "Karla Luana", "45678");


		employeeRepository.saveAll(Arrays.asList(e1,e2,e3,e4));
		
		Recipe r1 = new Recipe(null, "Nega Maluca");
		Recipe r2 = new Recipe(null, "Torta de Morango");
		Recipe r3 = new Recipe(null, "Croissant");
		Recipe r4 = new Recipe(null, "Pastel de Carne");
		Recipe r5 = new Recipe(null, "Risólis");

		recipeRepository.saveAll(Arrays.asList(r1,r2,r3,r4,r5));				
	}

}
