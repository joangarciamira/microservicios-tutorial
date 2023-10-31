package com.usuario.service.controlador;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usuario.service.entidades.Usuario;
import com.usuario.service.modelos.Coche;
import com.usuario.service.modelos.Moto;
import com.usuario.service.servicio.UsuarioService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping
	public ResponseEntity<List<Usuario>> listarUsuarios(){
		//return new ResponseEntity<List<Usuario>>(usuarioService.getAll(), HttpStatus.OK);
		
		List<Usuario> usuarios = usuarioService.getAll();
		
		if(usuarios.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.ok(usuarios);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Usuario> obtenerUsuario(@PathVariable int id){
		
		Usuario usuario = usuarioService.getUsuarioById(id);
		
		if (usuario == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(usuario);
	}
	
	@PostMapping
	public ResponseEntity<Usuario> guardarUsuario(@RequestBody Usuario usuario){
		Usuario nuevoUsuario = usuarioService.save(usuario);
		return ResponseEntity.ok(nuevoUsuario);
		
	}
	
	@CircuitBreaker(name = "cocheCB", fallbackMethod = "fallbackListarCoches")
	@GetMapping("/coche/{usuarioId}")
	public ResponseEntity<List<Coche>> listarCoches(@PathVariable("usuarioId") int usuarioId){
		
		Usuario usuario = usuarioService.getUsuarioById(usuarioId);
		if (usuario == null) {
			return ResponseEntity.notFound().build();
		}
		
		List<Coche> coches = usuarioService.getCoches(usuarioId);
		if (coches.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(coches);
	}
	
	@CircuitBreaker(name = "cocheCB", fallbackMethod = "fallbackListarMotos")
	@GetMapping("/moto/{usuarioId}")
	public ResponseEntity<List<Moto>> listarMotos(@PathVariable("usuarioId") int usuarioId){
		
		Usuario usuario = usuarioService.getUsuarioById(usuarioId);
		if (usuario == null) {
			return ResponseEntity.notFound().build();
		}
		
		List<Moto> motos = usuarioService.getMotos(usuarioId);
		if (motos.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(motos);
	}
	
	@CircuitBreaker(name = "cocheCB", fallbackMethod = "fallbackGuardarCoche")
 	@PostMapping("/coche/{usuarioId}")
 	public ResponseEntity<Coche> guardarCoche(@PathVariable int usuarioId,
 												@RequestBody Coche coche){
 		Coche cocheNuevo = usuarioService.saveCoche(usuarioId, coche);
 		return ResponseEntity.ok(cocheNuevo);
 	}
 	
	@CircuitBreaker(name = "cocheCB", fallbackMethod = "fallbackGuardarMoto")
 	@PostMapping("/moto/{usuarioId}")
 	public ResponseEntity<Moto> guardarMoto(@PathVariable int usuarioId,
 			@RequestBody Moto moto){
 		Moto motoNueva = usuarioService.saveMoto(usuarioId, moto);
 		return ResponseEntity.ok(motoNueva);
 	}
	
	@CircuitBreaker(name = "allCB", fallbackMethod = "fallbackGetAll")
	@GetMapping("/todos/{usuarioId}")
	public ResponseEntity<Map<String, Object>> listarVehiculos(@PathVariable int usuarioId){
		Map<String, Object> mapa = usuarioService.getUsuarioAndVehiculos(usuarioId);
		return ResponseEntity.ok(mapa);
	}
	
	private ResponseEntity<List<Coche>> fallbackGetCoches(@PathVariable("usuarioId") int usuarioId) {
		return new ResponseEntity("El usuario " +usuarioId+ " tiene los coches en el taller", HttpStatus.OK);
	}
}
