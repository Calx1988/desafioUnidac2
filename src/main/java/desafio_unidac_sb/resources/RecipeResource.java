package desafio_unidac_sb.resources;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import desafio_unidac_sb.repositories.RecipeRepository;

@Controller
public class RecipeResource {
	
	private RecipeRepository recipeRepository;
	
	public RecipeResource(RecipeRepository recipeRepository) {
		this.recipeRepository = recipeRepository;
	}
	
	@GetMapping("/recipes")
	public String recipes(Model model) {
		model.addAttribute("listRecipes", recipeRepository.findAll());
		return "recipes";
	}


}
