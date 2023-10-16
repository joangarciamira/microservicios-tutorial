package com.usuario.service.feignclients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.usuario.service.modelos.Coche;
 
@FeignClient(name = "coche-service", url = "http://localhost:8002", path = "/coche")
public interface CocheFeignClient {
  
  @PostMapping()
  public Coche save(@RequestBody Coche coche);
  
}


	