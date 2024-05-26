package com.example.demo.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Productos;
import com.example.demo.service.ProductosService;

@RestController
@RequestMapping("v1/productos")
public class ProcutosController {
	
	@Autowired
	ProductosService productosService;
	
	
	   @PostMapping
	    public ResponseEntity<Map<String, Object>> insertAndListProductos(@RequestBody Map<String, Object> request) throws Exception {
	        int idProducto = (int) request.get("idProducto");
	        String nombre = (String) request.get("nombre");
	        String fecRegistroStr = (String) request.get("fecRegistro");

	        // Convertir la cadena de fecha a un objeto Date
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	        Date fecRegistro = sdf.parse(fecRegistroStr);

	        Map<String, Object> response = productosService.insertAndListProductos(idProducto, nombre, fecRegistro);
	        int codigoRespuesta = (int) response.get("codigoRespuesta");

	        if (codigoRespuesta == 0) {
	            return ResponseEntity.ok(response);
	        } else {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	        }
	    }}