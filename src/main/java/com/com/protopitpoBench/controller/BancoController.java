package com.com.protopitpoBench.controller;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.com.protopitpoBench.entity.Cliente;
import com.com.protopitpoBench.entity.Empleados;
import com.com.protopitpoBench.entity.Usuario;
import com.com.protopitpoBench.service.BancoService;

@RestController
@RequestMapping("/banco")
public class BancoController {

	@Autowired
	BancoService bancoService;

	@GetMapping("/getCliente")
	public ResponseEntity<?> consultar(@RequestBody Cliente cliente) {
		Optional<Cliente> consulta = bancoService.consultaClientes(cliente);
		if (consulta.isEmpty()) {
			String messager = "registro no encontrado";
			return new ResponseEntity<>(messager, HttpStatus.BAD_REQUEST);

		}
		return new ResponseEntity<>(consulta, HttpStatus.OK);
	}

	@GetMapping("/getClienteCedula")
	public ResponseEntity<?> consultarCedula(@RequestBody Cliente cliente) {
		Cliente consulta = bancoService.consultaClientesPorcedula(cliente);
		if (consulta == null) {
			String messager = "registro no encontrado";
			return new ResponseEntity<>(messager, HttpStatus.BAD_REQUEST);

		}
		return new ResponseEntity<>(consulta, HttpStatus.OK);
	}

	@GetMapping("/consul")
	public ResponseEntity<?> consultarCedula() {
		List<Cliente> consulta = bancoService.getClientes();

		return new ResponseEntity<>(consulta, HttpStatus.OK);
	}

	@PostMapping("/crearUsuario")
	public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario) throws NoSuchAlgorithmException {
		if (usuario.getUsername() == null || usuario.getUsername().isBlank()) {
			String messager = "Datos vacios o en null";
			return new ResponseEntity<>(messager, HttpStatus.BAD_REQUEST);

		}
		Usuario response = bancoService.createUsuario(usuario);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/getUsuarios")
	public ResponseEntity<?> getUsuarios() throws NoSuchAlgorithmException {

		List<Usuario> response = bancoService.getUsuarios();

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/updateCliente")
	public ResponseEntity<?> updateCliente(@RequestBody Cliente cliente) {
		try {

			String response = bancoService.updateClient(cliente);

			if (response.equals(" actualiacion exitosa")) {

				return new ResponseEntity<>(response, HttpStatus.OK);
			}
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			String mensaje = " error no esperado";
			return new ResponseEntity<>(mensaje, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/deleteCliente")
	public ResponseEntity<?> deleteCliente(@RequestBody Cliente cliente) {
		try {

			String response = bancoService.delete(cliente.getId());

			if (response.equals("se elimino correctamente")) {

				return new ResponseEntity<>(response, HttpStatus.OK);
			}
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			String mensaje = " error no esperado";
			return new ResponseEntity<>(mensaje, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/getEmpleados")
	public ResponseEntity<?> getEmpleados() {
		List<Empleados> consulta = bancoService.getEmpleados();

		return new ResponseEntity<>(consulta, HttpStatus.OK);
	}

	@PostMapping("/getClienteExcel")
	public ResponseEntity<?> getClienteExcel() {
		bancoService.generarExcelClientes();

		return new ResponseEntity<>(HttpStatus.OK);
	}

}
