package com.example.demo.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.ProductosRepository;

@Service
public class ProductosService {
	
	@Autowired
	ProductosRepository productosRepository;
	
    @Autowired
    public ProductosService(ProductosRepository productosRepository) {
        this.productosRepository = productosRepository;
    }

    public Map<String, Object> insertAndListProductos(int idProducto, String nombre, Date fecRegistro) {
        Map<String, Object> response = productosRepository.insertAndListProductos(idProducto, nombre, fecRegistro);
        formatFecha(response);
        return response;
    }
    
    private void formatFecha(Map<String, Object> response) {
        List<Map<String, Object>> cursorProductos = (List<Map<String, Object>>) response.get("cursorProductos");
        if (cursorProductos == null) {
            // Si es null, inicializarlo como una lista vacía
            cursorProductos = new ArrayList<>();
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

        for (Map<String, Object> producto : cursorProductos) {
            Date fecha = (Date) producto.get("FEC_REGISTRO");
            String fechaFormateada = dateFormat.format(fecha);
            producto.put("FEC_REGISTRO", fechaFormateada);
        }
    }
    
}
