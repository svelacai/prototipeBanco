package com.com.protopitpoBench.service;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.com.protopitpoBench.entity.Cliente;
import com.com.protopitpoBench.entity.Empleados;
import com.com.protopitpoBench.entity.Usuario;
import com.com.protopitpoBench.respository.BancoRespository;
import com.com.protopitpoBench.respository.EmpleadoRepository;
import com.com.protopitpoBench.respository.UsuarioRespository;

@Service
public class BancoService {

	private static final String ENCRYPTION_ALGORITHM = "AES";
	private static final int KEY_SIZE = 128;

	@Autowired
	BancoRespository bancoRespository;

	@Autowired
	UsuarioRespository usuarioRespository;

	@Autowired
	EmpleadoRepository empleadoRepository;

	public Optional<Cliente> consultaClientes(Cliente cliente) {

		Optional<Cliente> consulta = bancoRespository.findById(cliente.id);
		return consulta;

	}

	public Cliente consultaClientesPorcedula(Cliente cliente) {

		Cliente consulta = bancoRespository.getCliente(cliente.numeroDocumento);
		return consulta;

	}

	public List<Cliente> getClientes() {

		List<Cliente> consulta = bancoRespository.getClientes();
		return consulta;

	}

	public static String encrypt(String plaintext, SecretKey secretKey) {
		try {
			Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
			return Base64.getEncoder().encodeToString(encryptedBytes);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String decrypt(String ciphertext, SecretKey secretKey) {
		try {
			Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(ciphertext));
			return new String(decryptedBytes, StandardCharsets.UTF_8);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Usuario createUsuario(Usuario usuario) throws NoSuchAlgorithmException {
		KeyGenerator keyGenerator = KeyGenerator.getInstance(ENCRYPTION_ALGORITHM);
		keyGenerator.init(KEY_SIZE);
		SecretKey secretKey = keyGenerator.generateKey();
		String name = encrypt(usuario.getUsername(), secretKey);
		String pass = encrypt(usuario.getPassword(), secretKey);
		Usuario usuario2 = new Usuario();
		usuario2.setUsername(name);
		usuario2.setPassword(pass);
		Usuario create = usuarioRespository.save(usuario2);
		return create;

	}

	public List<Usuario> getUsuarios() throws NoSuchAlgorithmException {
		List<Usuario> list = new ArrayList<>();

		List<Usuario> consul = usuarioRespository.getUsuarios();
		for (Usuario consultas : consul) {
			KeyGenerator keyGenerator = KeyGenerator.getInstance(ENCRYPTION_ALGORITHM);
			keyGenerator.init(KEY_SIZE);
			SecretKey secretKey = keyGenerator.generateKey();
			Integer id = consultas.getId();
			String name = decrypt(consultas.getUsername(), secretKey);
			String pass = decrypt(consultas.getPassword(), secretKey);

			Usuario user = new Usuario();
			user.setId(id);
			user.setUsername(name);

			user.setPassword(pass);
			list.add(user);

		}
		return list;

	}

	public String updateClient(Cliente cliente) {

		Boolean consultar = bancoRespository.findById(cliente.getId()).isPresent();
		if (consultar.equals(true)) {
			Cliente consulta = new Cliente();
			consulta.setId(cliente.getId());
			consulta.setNombre(cliente.getNombre());
			consulta.setApellido(cliente.getApellido());
			consulta.setCiudad(cliente.getCiudad());
			consulta.setDireccion(cliente.getDireccion());
			consulta.setEmail(cliente.getEmail());
			consulta.setFechaCreacion(cliente.getFechaCreacion());
			consulta.setFechaNacimiento(cliente.getFechaNacimiento());
			consulta.setNumeroDocumento(cliente.getNumeroDocumento());
			consulta.setTipoDocumento(cliente.getTipoDocumento());
			consulta.setPais(cliente.getPais());
			consulta.setTelefono(cliente.getTelefono());
			bancoRespository.save(consulta);
			String mensaje = " actualiacion exitosa";
			return mensaje;
		}
		String mensaje = " id no encontrado";
		return mensaje;

	}

	public String delete(Integer id) {
		Boolean consultar = bancoRespository.findById(id).isPresent();

		if (consultar.equals(true)) {
			bancoRespository.deleteById(id);
			return "se elimino correctamente";
		}
		return " id no encontrado";

	}

	public List<Empleados> getEmpleados() {

		List<Empleados> consulta = empleadoRepository.empleados();
		List<Empleados> empleado = new ArrayList<>();
		consulta.forEach(itera -> {
			if (itera.getSalario() != null && !itera.getSalario().toString().isBlank() && itera.getSalario() > 0) {
				empleado.add(itera);
			}
		});

		return empleado;

	}

	public void generarExcelClientes() {
		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet("Clientes");

		List<Cliente> datos = bancoRespository.getClientes();

		int numeroRenglon = 0;
		Row headerRow = sheet.createRow(numeroRenglon++);
		int numeroCelda = 0;
		headerRow.createCell(numeroCelda++).setCellValue("Nombre");
		headerRow.createCell(numeroCelda++).setCellValue("Apellido");
		headerRow.createCell(numeroCelda++).setCellValue("Fecha de Nacimiento");
		headerRow.createCell(numeroCelda++).setCellValue("Ciudad");
		headerRow.createCell(numeroCelda++).setCellValue("Dirección");
		headerRow.createCell(numeroCelda++).setCellValue("Email");
		headerRow.createCell(numeroCelda++).setCellValue("Fecha de Creación");
		headerRow.createCell(numeroCelda++).setCellValue("País");
		headerRow.createCell(numeroCelda++).setCellValue("Teléfono");
		headerRow.createCell(numeroCelda++).setCellValue("Tipo de Documento");
		headerRow.createCell(numeroCelda++).setCellValue("Número de Documento");
		headerRow.createCell(numeroCelda++).setCellValue("ID");

		for (Cliente cliente : datos) {
			Row row = sheet.createRow(numeroRenglon++);
			numeroCelda = 0;

			row.createCell(numeroCelda++).setCellValue(cliente.getNombre());
			row.createCell(numeroCelda++).setCellValue(cliente.getApellido());
			row.createCell(numeroCelda++).setCellValue(cliente.getFechaNacimiento());
			row.createCell(numeroCelda++).setCellValue(cliente.getCiudad());
			row.createCell(numeroCelda++).setCellValue(cliente.getDireccion());
			row.createCell(numeroCelda++).setCellValue(cliente.getEmail());
			row.createCell(numeroCelda++).setCellValue(cliente.getFechaCreacion());
			row.createCell(numeroCelda++).setCellValue(cliente.getPais());
			row.createCell(numeroCelda++).setCellValue(cliente.getTelefono());
			row.createCell(numeroCelda++).setCellValue(cliente.getTipoDocumento());
			row.createCell(numeroCelda++).setCellValue(cliente.getNumeroDocumento());
			row.createCell(numeroCelda++).setCellValue(cliente.getId());
		}

		try {
			// Se genera el documento
			FileOutputStream out = new FileOutputStream(
					new File("C://Users//user//Desktop//udemy//SC//Santiago//excel/clientes.xls"));
			workbook.write(out);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
