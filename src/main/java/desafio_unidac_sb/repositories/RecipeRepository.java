package desafio_unidac_sb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import desafio_unidac_sb.entities.Recipe;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long>{
	

}
