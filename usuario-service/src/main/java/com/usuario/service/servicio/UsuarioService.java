package com.usuario.service.servicio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.usuario.service.entidades.Usuario;
import com.usuario.service.feignclients.CocheFeignClient;
import com.usuario.service.feignclients.MotoFeignClient;
import com.usuario.service.modelos.Coche;
import com.usuario.service.modelos.Moto;
import com.usuario.service.repositorio.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
 	@Autowired
 	private CocheFeignClient cocheFeignClient;
 	
 	@Autowired
 	private MotoFeignClient motoFeignClient;
	
	public List<Coche> getCoches(int usuarioId) {
		
		Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);
		if (usuario == null) {
			return null;
		}
		
		List<Coche> coches = restTemplate.getForObject("http://localhost:8002/coche/usuario/" +usuarioId, List.class);
		return coches;
	}
	
	public List<Moto> getMotos(int usuarioId) {
		
		Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);
		if (usuario == null) {
			return null;
		}
		
		List<Moto> motos = restTemplate.getForObject("http://localhost:8003/moto/usuario/" +usuarioId, List.class);
		return motos;
	}
	
 	public Coche saveCoche(int usuarioId, Coche coche) {
 		coche.setUsuarioId(usuarioId);
 		Coche nuevoCoche = cocheFeignClient.save(coche);
 		return nuevoCoche;
 	}
 	
 	public Moto saveMoto(int usuarioId, Moto moto) {
 		moto.setUsuarioId(usuarioId);
 		Moto nuevaMoto = motoFeignClient.save(moto);
 		return nuevaMoto;
 	}
 	
	public List<Usuario> getAll(){
		return usuarioRepository.findAll();
	}
	
	public Usuario getUsuarioById(int id) {
		return usuarioRepository.findById(id).orElse(null);
	}
	
	public Usuario save(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}
	
	public Map<String, Object> getUsuarioAndVehiculos(int usuarioId){
		Map<String, Object> mapa = new HashMap<String, Object>();
		Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);
		
		if (usuario == null) {
			mapa.put("Mensaje", "El usuario no existe");
			return mapa;
		}
		mapa.put("Usuario", usuario);
		
		List<Coche> coches = getCoches(usuarioId);
		if(coches.isEmpty()) {
			mapa.put("Coches", "El usuario no tiene coches");
		}
		
		List<Moto> motos = getMotos(usuarioId);
		if(motos.isEmpty()) {
			mapa.put("Motos", "El usuario no tiene motos");
		}
		
		if (coches.isEmpty() && motos.isEmpty()) {
			return mapa;
		}
		
		mapa.put("Coches", coches);
		mapa.put("Motos", motos);
		return mapa;
	}
}
