package com.com.protopitpoBench.respository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.com.protopitpoBench.entity.Empleados;

public interface EmpleadoRepository extends CrudRepository<Empleados, Integer> {

	@Query(value = "select * from Banco.empleados e ", nativeQuery = true)
	List<Empleados> empleados();

}
