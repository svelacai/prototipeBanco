package com.com.protopitpoBench.respository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.com.protopitpoBench.entity.Cliente;

@Repository
public interface BancoRespository extends CrudRepository<Cliente, Integer> {

	@Query(value = "select * from Banco.clientes c where c.numero_documento  =:numeroDocumen", nativeQuery = true)
	Cliente getCliente(String numeroDocumen);

	@Query(value = "select * from Banco.clientes", nativeQuery = true)
	List<Cliente> getClientes();

}
