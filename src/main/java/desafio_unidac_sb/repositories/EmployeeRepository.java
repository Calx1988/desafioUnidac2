package desafio_unidac_sb.repositories;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import desafio_unidac_sb.entities.Employee;

@Repository
public interface EmployeeRepository  extends JpaRepository<Employee, Long>{
	
	//@Query(value="Select * from employees where name like :name", nativeQuery = true);
	
	

}
