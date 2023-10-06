package com.com.protopitpoBench.dto;

import java.util.Date;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
@Getter
@Setter
public class ClienteDTO {

	public Integer id;
	public String nombre;
	public String apellido;
	public String direccion;
	public String ciudad;
	public String pais;
	public String telefono;
	public String email;
	public Date fechaNacimiento;
	public String tipoDocumento;
	public String numeroDocumento;
	public Date fechaCreacion;

}
