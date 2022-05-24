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
		
		Employee e1 = new Employee(null, "Cássio Corrêa", "12345");
		Employee e2 = new Employee(null, "Allana Caroline", "23456");
		
		employeeRepository.saveAll(Arrays.asList(e1,e2));
		
		Recipe r1 = new Recipe(null, "Torta de Chocolate");
		Recipe r2 = new Recipe(null, "Torta de Morango");
		Recipe r3 = new Recipe(null, "Croissant");

		recipeRepository.saveAll(Arrays.asList(r1,r2,r3));

				
	}
	

}
