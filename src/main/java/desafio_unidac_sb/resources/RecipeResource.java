package desafio_unidac_sb.resources;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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


}
