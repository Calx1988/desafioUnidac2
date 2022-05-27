package desafio_unidac_sb.resources;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
	public String saveRecipe(@Valid @ModelAttribute("recipe") Recipe recipe, BindingResult bindingResult, Model model) {
		System.out.print("Has error: " + bindingResult);
		if(bindingResult.hasErrors()) {
			return "newRecipe";
		}
		List<Recipe> list = recipeRepository.findAll();
		for(Recipe x: list) {
			if(x.hashCode()==recipe.hashCode()) {
				boolean recipeAlreadyExists = true;
				model.addAttribute("recipeAlreadyExists", recipeAlreadyExists);
				return "newRecipe";  
			}
		}
		recipeRepository.save(recipe);
		return "redirect:/recipes";		
	}
	
	@GetMapping("/recipes/editRecipe/{id}")
	public String editRecipe(@PathVariable("id") long id, Model model) {
		Optional<Recipe> recipeOpt = recipeRepository.findById(id);
		model.addAttribute("recipe", recipeOpt.get());
		return "newRecipe";
	}
	
	@GetMapping("recipes/deleteRecipe/{id}")
	public String deleteRecipe(@PathVariable("id") long id, Model model) {
		Recipe recipe = recipeRepository.findById(id).get();
		List<Employee> listEmployees = employeeRepository.findAll();
		for(Employee e: listEmployees) {
			if(e.getRecipe()==recipe) {
				boolean recipeBeingUsed = true;
				model.addAttribute("listRecipes", recipeRepository.findAll());
				model.addAttribute("recipeBeingUsed", recipeBeingUsed);
				return "recipes";
			}
		}
		recipeRepository.delete(recipe);
		return "redirect:/recipes";
	}
		
}

