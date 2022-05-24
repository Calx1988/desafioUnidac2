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
	
	public RecipeResource(RecipeRepository recipeRepository) {
		this.recipeRepository = recipeRepository;
	}
	
	@GetMapping(value = "/recipes")
	public String recipes(Model model) {
		model.addAttribute("listRecipes", recipeRepository.findAll());
		return "recipes";
	}
	
	@GetMapping(value="/recipes/{id}")
	public Recipe findById(Long id) {
		Recipe recipe = recipeRepository.findById(id).get();
		return recipe;
	}
	
	@GetMapping(value="/newRecipe")
	public String newRecipe(@ModelAttribute("recipe") Recipe recipe) {
		return "newRecipe";
	}
	
	@PostMapping(value ="/newRecipe/saveRecipe")
	public String saveRecipe(@ModelAttribute("recipe") Recipe recipe) {
		List<Recipe> list = recipeRepository.findAll();
		for(Recipe x: list) {
			if(x.hashCode()==recipe.hashCode()) {
				return "newRecipe";  
			}
		}
		recipeRepository.save(recipe);
		return "redirect:/recipes";		
	}
	
	@GetMapping("recipes/deleteRecipe/{id}")
	public String deleteRecipe(@PathVariable("id") long id) {
		Recipe recipe = recipeRepository.findById(id).get();
		recipeRepository.delete(recipe);
		return "redirect:/recipes";
	}
		
}

