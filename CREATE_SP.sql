create or replace PROCEDURE sp_insertAndListProducts (
    idProducto      IN  NUMBER,
    nombre          IN  VARCHAR2,
    fec_registro    IN  DATE,
    cursorProductos OUT SYS_REFCURSOR,
    codigoRespuesta OUT NUMBER,
    mensajeRespuesta OUT VARCHAR2
) 
IS
BEGIN
    BEGIN
        -- Insertar el nuevo producto
        INSERT INTO productos (id_producto, nombre, fec_registro)
        VALUES (idProducto, nombre, fec_registro);
        
        commit;
        -- Código de respuesta y mensaje en caso de éxito
        codigoRespuesta = 0;
        mensajeRespuesta = 'Ejecución con éxito';

    EXCEPTION
        WHEN OTHERS THEN
            -- Manejo de errores
            codigoRespuesta = 1;
            mensajeRespuesta = 'Error '  SQLERRM;
            RETURN;
    END;

    -- Abrir el cursor para listar todos los productos
    OPEN cursorProductos FOR
    SELECT id_producto, nombre, fec_registro
    FROM productos
    WHERE TRUNC(fec_registro) = TRUNC(SYSDATE);
END;