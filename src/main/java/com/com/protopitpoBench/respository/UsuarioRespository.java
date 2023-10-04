package com.com.protopitpoBench.respository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.com.protopitpoBench.entity.Usuario;

public interface UsuarioRespository  extends CrudRepository<Usuario, Integer> {
	
	@Query (value = "select * from banco.usuario ", nativeQuery = true)
	List<Usuario> getUsuarios();


}
