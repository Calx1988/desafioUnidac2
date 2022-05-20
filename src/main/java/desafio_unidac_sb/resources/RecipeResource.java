package desafio_unidac_sb.resources;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import desafio_unidac_sb.entities.Employee;
import desafio_unidac_sb.entities.Recipe;
import desafio_unidac_sb.repositories.RecipeRepository;

@Controller
@Transactional
public class RecipeResource {
	
	@Autowired
	private RecipeRepository recipeRepository;
	
	public RecipeResource(RecipeRepository recipeRepository) {
		this.recipeRepository = recipeRepository;
	}
	
	@GetMapping(value = "/recipes")
	public String recipes(Model model) {
		model.addAttribute("listRecipes", recipeRepository.findAll());
		return "recipes";
	}
	
	@GetMapping(value="/newRecipe")
	public String newRecipe(@ModelAttribute("recipe") Recipe recipe) {
		return "newRecipe";
	}
	
	@PostMapping(value ="/recipes/saveRecipe")
	public String saveEmployee(@ModelAttribute("recipe") Recipe recipe) {
		List<Recipe> list = recipeRepository.findAll();
		for(Recipe x: list) {
			if(x.hashCode()==recipe.hashCode()) {
				return "newRecipe";  
			}
		}
		recipeRepository.save(recipe);
		return "redirect:/recipes";		
	}


}
