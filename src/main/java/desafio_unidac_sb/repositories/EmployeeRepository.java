package desafio_unidac_sb.repositories;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import desafio_unidac_sb.entities.Employee;

@Repository
public interface EmployeeRepository  extends JpaRepository<Employee, Long>{
	
	@Query(value="Select * from employee where name like %:nameEmployee%", nativeQuery = true)
	Iterable<Employee> findByName(@Param("nameEmployee") String nameEmployee);

	@Query(value="Select * from employee where cpf like %:cpfEmployee%", nativeQuery = true)
	Iterable<Employee> findByCpf(@Param("cpfEmployee") String cpfEmployee);

}
