
--LA BASE DE DATOS EN LA QUE ESTUVE HACIENDO PRUEBAS SE LLAMA "SILO"
--EL USUARIO CON EL QUE ME CONECTABA SE LLAMA "postgres" Y SU CONTRASEÑA ES "1"
--TENDRÁN QUE CAMBIAR ESOS DATOS POR LOS DE SU SERVIDOR PARA QUE FUNCIONEN LAS PRUEBAS. :p


--CREACION DE ENUMERADOS USADOS EN LAS TABLAS-----------------------------------------------------------------

CREATE TYPE copia_formato as ENUM(
	'BLURAY',
	'DVD'
);

CREATE TYPE copia_estado as ENUM(
	'VENDIDA',
	'EN-STOCK',
	'DAÑADA'
);

CREATE TYPE empleado_puesto as ENUM(
	'Gerente',
	'Vendedor'
);

CREATE TYPE empleado_estado as ENUM(
	'Laborando',
	'Incapacitado',
	'Liquidado'
);

CREATE TYPE pelicula_clasificacion AS ENUM (
	'A', 'B', 'B15', 'C', 'D'
);

--CREACION DE LA ESTRUCTURA DE LA BD-----------------------------------------------------------------

CREATE TABLE "cliente" (

"cliente_id" text NOT NULL,

"cliente_nombre" text NOT NULL,

"cliente_appater" text NOT NULL,

"cliente_apmater" text NOT NULL,

"cliente_fecharegistro" date NOT NULL,

"cliente_fechanacimiento" date NOT NULL,

"cliente_imagen" bytea NOT NULL,

"cliente_imagen_nombre" text NOT NULL,

PRIMARY KEY ("cliente_id") 

);



CREATE TABLE "copia_pelicula" (

"copia_id" serial8 NOT NULL,

"copia_fmto" copia_formato NOT NULL,

"copia_fechaadquisicion" date NOT NULL,

"copia_precio" DOUBLE PRECISION NOT NULL,

"copia_edo" copia_estado NOT NULL,

"pelicula_id" serial8 NOT NULL,

PRIMARY KEY ("copia_id") 

);



CREATE TABLE "detalle_venta" (

"detallevta_id" serial8 NOT NULL,

"venta_id" serial8 NOT NULL,

"copia_id" serial8 NOT NULL,

"detallevta_comentario" text NULL,

PRIMARY KEY ("detallevta_id") 

);



CREATE TABLE "empleado" (

"empleado_id" text NOT NULL,

"empleado_nombre" text NOT NULL,

"empleado_appater" text NOT NULL,

"empleado_apmater" text NOT NULL,

"empleado_horaentrada" time NOT NULL,

"empleado_horasalida" time NOT NULL,

"empleado_fechanacimiento" date NOT NULL,

"empleado_fecharegistro" date NOT NULL,

"empleado_edo" empleado_estado NOT NULL,

"empleado_puesto" empleado_puesto NOT NULL,

"empleado_sueldo" DOUBLE PRECISION NOT NULL,

"empleado_imagen" bytea NOT NULL,

"empleado_imagen_nombre" text NOT NULL,

PRIMARY KEY ("empleado_id") 

);



CREATE TABLE "pelicula" (

"pelicula_id" serial8 NOT NULL,

"pelicula_titulo" text NOT NULL,

"pelicula_anioestreno" INTERVAL year NOT NULL,

"pelicula_director" text NOT NULL,

"pelicula_estelares" text NOT NULL,

"pelicula_duracion" INTERVAL minute NOT NULL,

"pelicula_clasif" pelicula_clasificacion NOT NULL,

"genero_id" serial8 NOT NULL,

"pelicula_portada" bytea NOT NULL,

"pelicula_portada_nombre" text NOT NULL,

PRIMARY KEY ("pelicula_id") 

);



CREATE TABLE "venta" (

"venta_id" serial8 NOT NULL,

"venta_fecha" date NOT NULL,

"venta_neto" DOUBLE PRECISION NOT NULL,

"empleado_id" text NOT NULL,

"cliente_id" text NOT NULL,

PRIMARY KEY ("venta_id") 

);



CREATE TABLE "genero" (

"genero_id" serial8 NOT NULL,

"genero_nombre" text NOT NULL,

"genero_descripcion" text NULL,

PRIMARY KEY ("genero_id") 

);



CREATE TABLE "usuario" (

"usuario_id" serial8 NOT NULL,

"usename" text NOT NULL,

"privilegios" bigint NOT NULL,

PRIMARY KEY ("usuario_id") 

);





ALTER TABLE "detalle_venta" ADD CONSTRAINT "detallevta-pelicula" FOREIGN KEY ("copia_id") REFERENCES "copia_pelicula" ("copia_id");

ALTER TABLE "copia_pelicula" ADD CONSTRAINT "pelicula-copia" FOREIGN KEY ("pelicula_id") REFERENCES "pelicula" ("pelicula_id");

ALTER TABLE "detalle_venta" ADD CONSTRAINT "detallevta-venta" FOREIGN KEY ("venta_id") REFERENCES "venta" ("venta_id");

ALTER TABLE "venta" ADD CONSTRAINT "venta-cliente" FOREIGN KEY ("cliente_id") REFERENCES "cliente" ("cliente_id");

ALTER TABLE "venta" ADD CONSTRAINT "venta-empleado" FOREIGN KEY ("empleado_id") REFERENCES "empleado" ("empleado_id");

ALTER TABLE "pelicula" ADD CONSTRAINT "genero-pelicula" FOREIGN KEY ("genero_id") REFERENCES "genero" ("genero_id");



--CREACION DE GRUPOS DE ROLES Y ASIGNACION DE PRIVILEGIOS-----------------------------------------------------------------

CREATE GROUP cliente;

CREATE GROUP vendedor;

CREATE GROUP gerente IN GROUP vendedor;

GRANT SELECT ON TABLE pelicula, genero TO cliente;

GRANT INSERT ON TABLE cliente, pelicula, copia_pelicula, genero, venta, detalle_venta TO GROUP vendedor;
GRANT UPDATE ON TABLE cliente, pelicula, copia_pelicula, genero TO GROUP vendedor;
--GRANT UPDATE (detallevta_comentario) ON TABLE detalle_venta TO GROUP vendedor;
GRANT DELETE ON TABLE cliente, pelicula, copia_pelicula, genero TO GROUP vendedor;
GRANT SELECT ON TABLE cliente, pelicula, copia_pelicula, genero, venta, detalle_venta TO GROUP vendedor;
GRANT SELECT ON TABLE empleado TO GROUP vendedor;

GRANT SELECT ON ALL TABLES IN SCHEMA public TO GROUP gerente;
GRANT ALL ON TABLE usuario TO GROUP gerente;
GRANT UPDATE ON TABLE venta, detalle_venta TO GROUP gerente;
GRANT ALL ON empleado TO GROUP gerente;
GRANT ALL ON cliente TO GROUP gerente;


--CREACIÓN DE VIEWS----------------------------------------------------------------------------------------------------

CREATE VIEW vista_copia_pelicula AS SELECT * FROM copia_pelicula;

CREATE VIEW vista_cliente AS SELECT * FROM cliente;

CREATE VIEW vista_empleado AS SELECT * FROM empleado;

CREATE VIEW vista_genero AS SELECT * FROM genero;

CREATE VIEW vista_pelicula AS SELECT * FROM pelicula;

CREATE VIEW vista_portada_pelicula AS SELECT pelicula_portada FROM pelicula;

