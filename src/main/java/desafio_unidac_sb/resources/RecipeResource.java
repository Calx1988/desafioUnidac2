package desafio_unidac_sb.resources;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import desafio_unidac_sb.entities.Recipe;
import desafio_unidac_sb.repositories.EmployeeRepository;
import desafio_unidac_sb.repositories.RecipeRepository;

@Controller
@Transactional
public class RecipeResource {
	
	@Autowired
	private RecipeRepository recipeRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	public RecipeResource(RecipeRepository recipeRepository, EmployeeRepository employeeRepository) {
		this.recipeRepository = recipeRepository;
		this.employeeRepository = employeeRepository;
	}
	
	@GetMapping(value = "/recipes")
	public String recipes(Model model) {
		model.addAttribute("listRecipes", recipeRepository.findAll());
		return "recipes";
	}
	
	@GetMapping(value="/newRecipe")
	public String newRecipe(Model model) {
		model.addAttribute("recipe", new Recipe());
		model.addAttribute("employees", employeeRepository.findAll());
		return "newRecipe";
	}
	
	@PostMapping(value ="/recipes/saveRecipe")
	public String saveRecipe(@ModelAttribute("recipe") Recipe recipe, Model model) {
		List<Recipe> list = recipeRepository.findAll();
		for(Recipe x: list) {
			if(x.hashCode()==recipe.hashCode()) {
				return "newRecipe";  
			}
		}
		model.addAttribute("recipe", recipe);
		model.addAttribute("employees", employeeRepository.findAll());
		recipeRepository.save(recipe);
		employeeRepository.flush();
		return "redirect:/recipes";		
	}
	
	


}
