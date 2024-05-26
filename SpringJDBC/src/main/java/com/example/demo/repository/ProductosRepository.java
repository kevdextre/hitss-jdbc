package com.example.demo.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.dialect.OracleTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Productos;

@Repository
public class ProductosRepository {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
    private final SimpleJdbcCall jdbcCall;

    @Autowired
    public ProductosRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("sp_insertAndListProducts")
                .declareParameters(
                        new SqlParameter("idProducto", Types.INTEGER),
                        new SqlParameter("nombre", Types.VARCHAR),
                        new SqlParameter("fecRegistro", Types.DATE),
                        new SqlOutParameter("cursorProductos", OracleTypes.CURSOR),
                        new SqlOutParameter("codigoRespuesta", Types.INTEGER),
                        new SqlOutParameter("mensajeRespuesta", Types.VARCHAR)
                );
    }

    public Map<String, Object> insertAndListProductos(int idProducto, String nombre, Date fecRegistro) {
        MapSqlParameterSource inParams = new MapSqlParameterSource()
                .addValue("idProducto", idProducto)
                .addValue("nombre", nombre)
                .addValue("fecRegistro", fecRegistro);

        return jdbcCall.execute(inParams);
    }
}
