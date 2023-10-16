package com.coche.service.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coche.service.entidades.Coche;
import com.coche.service.repositorio.CocheRepository;

@Service
public class CocheService {

	@Autowired
	private CocheRepository cocheRepository;
	
	public List<Coche> getAll(){
		return cocheRepository.findAll();
	}
	
	public Coche getCocheById(int id) {
		return cocheRepository.findById(id).orElse(null);
	}
	
	public Coche save(Coche coche) {
		return cocheRepository.save(coche);
	}
	
	public List<Coche> byUsuarioId(int usuarioId) {
		return cocheRepository.findByUsuarioId(usuarioId);
	}
	
}
