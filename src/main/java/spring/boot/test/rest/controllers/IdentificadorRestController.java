package spring.boot.test.rest.controllers;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;



import spring.boot.test.rest.entity.Identificador;
import spring.boot.test.rest.service.IdentidicadorServiceImp;

@RestController
@RequestMapping("/api")
public class IdentificadorRestController {
	
	@Autowired
	private IdentidicadorServiceImp identidicadorServiceImp;
	
	
	@GetMapping("/identificador")
	public List<Identificador> index(){
		
		return identidicadorServiceImp.findAll();
	}
	
/*	@GetMapping("/identificador/{id}")	
	public Identificador show(@PathVariable Long id) {
		return identidicadorServiceImp.findById(id);
	}*/
	
	@GetMapping("/identificador/{id}")	
	public ResponseEntity<?> show(@PathVariable Long id) {
		
		Identificador identificador=null;
		Map<String, Object> response=new HashMap<>();		
		
		try {
			identificador=identidicadorServiceImp.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos.");
			response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
		}
		
		if(identificador==null) {
			response.put("mensaje","El ID el registro: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_FOUND);
		}
		
		
		return new ResponseEntity<Identificador>(identificador,HttpStatus.OK);	
	}	
	
	/*@PostMapping("/identificador")
	public Identificador create(@RequestBody Identificador identificador) {
		return identidicadorServiceImp.save(identificador);
	}*/
	@PostMapping("/identificador")
	public ResponseEntity<?> create(@Valid @RequestBody Identificador identificador, BindingResult result) {
		
		Identificador identificadorNew = null;
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {

			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			identificadorNew = identidicadorServiceImp.save(identificador);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El registro ha sido creado con éxito!");
		response.put("registro", identificadorNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	/*
	@PutMapping("/identificador")
	public Identificador update(@RequestBody Identificador identificador) {
		return identidicadorServiceImp.save(identificador);
	}*/
	@PutMapping("/identificador")
	public ResponseEntity<?> update(@Valid @RequestBody Identificador identificador, BindingResult result) {

		Identificador identificadorActual = identidicadorServiceImp.findById(identificador.getId());

		Identificador identificadorUpdated = null;

		Map<String, Object> response = new HashMap<>();

		if(result.hasErrors()) {

			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if (identificadorActual == null) {
			response.put("mensaje", "Error: no se pudo editar, el registro ID: "
					.concat(identificador.getId().toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {

			identificadorActual.setId(identificador.getId());
			identificadorActual.setDescripcion(identificador.getDescripcion());
			identificadorActual.setVigente(identificador.isVigente());
			//identificadorActual.setFechaCreacion(identificadorActual.getFechaCreacion());
			

			identificadorUpdated = identidicadorServiceImp.save(identificadorActual);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el registro en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El registro ha sido actualizado con éxito!");
		response.put("registro", identificadorUpdated);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}	
	
	/*@DeleteMapping("/identificador/{id}")	
	public void delete(@PathVariable Long id) {
		identidicadorServiceImp.delete(id);
	}	*/
	
	@DeleteMapping("/identificador/{id}")	
	public ResponseEntity<?> delete(@PathVariable Long id) {
		
		Map<String, Object> response=new HashMap<>();
		
		try {
					
					
			identidicadorServiceImp.delete(id);
			
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el registro en la base de datos.");
			response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}		
		
		response.put("mensaje","El registro ha sido eliminado con éxito!");
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	}	

}
