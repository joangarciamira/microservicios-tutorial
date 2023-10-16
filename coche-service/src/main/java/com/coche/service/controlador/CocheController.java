package com.coche.service.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coche.service.entidades.Coche;
import com.coche.service.servicio.CocheService;

@RestController
@RequestMapping("/coche")
public class CocheController {

	@Autowired
	private CocheService cocheService;
	
	@GetMapping
	public ResponseEntity<List<Coche>> listarCoches(){
		//return new ResponseEntity<List<Coche>>(cocheService.getAll(), HttpStatus.OK);
		
		List<Coche> coches = cocheService.getAll();
		
		if(coches.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.ok(coches);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Coche> obtenerCoche(@PathVariable int id){
		
		Coche coche = cocheService.getCocheById(id);
		
		if (coche == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(coche);
	}
	
	@PostMapping
	public ResponseEntity<Coche> guardarCoche(@RequestBody Coche coche){
		Coche nuevoCoche = cocheService.save(coche);
		return ResponseEntity.ok(nuevoCoche);
		
	}
	
	@GetMapping("/usuario/{usuarioId}")
	public ResponseEntity<List<Coche>> listarCoches(@PathVariable("usuarioId") int id){
		
		List<Coche> coches = cocheService.byUsuarioId(id);
		if (coches.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(coches);
	}
	
}
