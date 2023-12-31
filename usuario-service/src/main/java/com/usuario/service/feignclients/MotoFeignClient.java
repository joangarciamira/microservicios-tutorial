 package com.usuario.service.feignclients;
 
 import org.springframework.cloud.openfeign.FeignClient;
 import org.springframework.web.bind.annotation.PostMapping;
 import org.springframework.web.bind.annotation.RequestBody;

import com.usuario.service.modelos.Moto;
 
 @FeignClient(name = "moto-service", url = "http://localhost:8003", path = "/moto")
 public interface MotoFeignClient {
 
 	@PostMapping
 	public Moto save(@RequestBody Moto moto);
 }
