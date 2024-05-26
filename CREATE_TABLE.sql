CREATE TABLE productos (
    id_producto   NUMBER(10) NOT NULL,
    nombre        VARCHAR2(100) NOT NULL,
    fec_registro  DATE NOT NULL,
    CONSTRAINT pk_productos PRIMARY KEY (id_producto)
);