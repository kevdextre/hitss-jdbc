import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.demo.controller.AdminController;
import com.example.demo.service.AdminService;

@ExtendWith(MockitoExtension.class)
public class AdminControllerTest {

    @Mock
    private AdminService adminService;

    @InjectMocks
    private AdminController adminController;

    private MockMvc mockMvc;

    @Test
    public void testInsertAndListProductosSuccess() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();

        // Simulamos el comportamiento del servicio para una inserción exitosa
        when(adminService.insertAndListProductos(1, "Producto de ejemplo", new Date()))
                .thenReturn(Collections.singletonList(new Productos(1, "Producto de ejemplo", new Date())));

        mockMvc.perform(post("/admin/insertAndListProductos")
                .param("idProducto", "1")
                .param("nombre", "Producto de ejemplo")
                .param("fecRegistro", "2024-05-25")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.codigoRespuesta").value(0))
                .andExpect(jsonPath("$.cursorProductos[0].ID_PRODUCTO").value(1))
                .andExpect(jsonPath("$.cursorProductos[0].NOMBRE").value("Producto de ejemplo"))
                .andExpect(jsonPath("$.cursorProductos[0].FEC_REGISTRO").value("2024-05-25"));
    }

    @Test
    public void testInsertAndListProductosError() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();

        // Simulamos el comportamiento del servicio para un error al insertar
        when(adminService.insertAndListProductos(1, "Producto de ejemplo", new Date()))
                .thenReturn(null); // Simulamos un fallo en la inserción

        mockMvc.perform(post("/admin/insertAndListProductos")
                .param("idProducto", "1")
                .param("nombre", "Producto de ejemplo")
                .param("fecRegistro", "2024-05-25")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.codigoRespuesta").value(1))
                .andExpect(jsonPath("$.mensajeRespuesta").value("Error: No se pudo insertar el producto"));
    }
}