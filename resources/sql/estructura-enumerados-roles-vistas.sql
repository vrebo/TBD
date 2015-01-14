
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

-- tablas log

CREATE TABLE cliente_log
(
    cliente_log_operation text NOT NULL,
    cliente_log_stamp timestamp without time zone NOT NULL,  
    cliente_log_userid text NOT NULL,
    cliente_log_cliente_id text NOT NULL,
    cliente_log_cliente_nombre text NOT NULL,  
    cliente_log_cliente_appater text NOT NULL,
    cliente_log_cliente_apmater text NOT NULL,
    cliente_log_cliente_fecharegistro date NOT NULL,
    cliente_log_cliente_fechanacimiento date NOT NULL,
    cliente_log_cliente_imagen bytea NOT NULL,
    cliente_log_cliente_imagen_nombre text NOT NULL
)WITH (
  OIDS=FALSE
);

ALTER TABLE cliente_log
  OWNER TO postgres;

CREATE TABLE copia_pelicula_log
(
    copia_pelicula_log_operation text NOT NULL,
    copia_pelicula_log_stamp timestamp without time zone NOT NULL,
    coipa_pelicula_log_userid text NOT NULL,
    coipa_pelicula_log_copia_id bigserial NOT NULL,
    coipa_pelicula_log_copia_fmto copia_formato NOT NULL,
    coipa_pelicula_log_copia_fechaadquisicion date NOT NULL,
    coipa_pelicula_log_copia_precio double precision NOT NULL,
    coipa_pelicula_log_copia_edo copia_estado NOT NULL,
    coipa_pelicula_log_pelicula_id bigserial NOT NULL
)
WITH (
  OIDS=FALSE
);
ALTER TABLE copia_pelicula_log
  OWNER TO postgres;


CREATE TABLE detalle_venta_log
(
  detalle_venta_log_operation text NOT NULL,
  detalle_venta_log_stamp timestamp without time zone NOT NULL,
  detalle_venta_log_userid text NOT NULL,
  detalle_venta_log_detallevta_id bigserial NOT NULL,
  detalle_venta_log_venta_id bigserial NOT NULL,
  detalle_venta_log_copia_id bigserial NOT NULL,
  detalle_venta_log_detallevta_comentario text
)
WITH (
  OIDS=FALSE
);
ALTER TABLE detalle_venta_log
  OWNER TO postgres;


CREATE TABLE empleado_log
(
    empleado_log_operation text NOT NULL,
    empleado_log_stamp timestamp without time zone NOT NULL,
    empleado_log_userid text NOT NULL,
    empleado_log_empleado_id text NOT NULL,
    empleado_log_empleado_nombre text NOT NULL,
    empleado_log_empleado_appater text NOT NULL,
    empleado_log_empleado_apmater text NOT NULL,
    empleado_log_empleado_horaentrada time without time zone NOT NULL,
    empleado_log_empleado_horasalida time without time zone NOT NULL,
    empleado_log_empleado_fechanacimiento date NOT NULL,
    empleado_log_empleado_fecharegistro date NOT NULL,
    empleado_log_empleado_edo empleado_estado NOT NULL,
    empleado_log_empleado_puesto empleado_puesto NOT NULL,
    empleado_log_empleado_sueldo double precision NOT NULL,
    empleado_log_empleado_imagen bytea NOT NULL,
    empleado_log_empleado_imagen_nombre text NOT NULL
)
WITH (
  OIDS=FALSE
);

ALTER TABLE empleado_log
  OWNER TO postgres;


CREATE TABLE genero_log
(
  genero_log_operation text NOT NULL,
  genero_log_stamp timestamp without time zone NOT NULL,
  genero_log_userid text NOT NULL,
  genero_log_genero_id bigserial NOT NULL,
  genero_log_genero_nombre text NOT NULL,
  genero_log_genero_descripcion text
)WITH (
  OIDS=FALSE
);
ALTER TABLE genero_log
  OWNER TO postgres;


CREATE TABLE pelicula_log
(
  pelicula_log_operation text NOT NULL,
  pelicula_log_stamp timestamp without time zone NOT NULL,
  pelicula_log_userid text NOT NULL,
  pelicula_log_pelicula_id bigserial NOT NULL,
  pelicula_log_pelicula_titulo text NOT NULL,
  pelicula_log_pelicula_anioestreno interval year NOT NULL,
  pelicula_log_pelicula_director text NOT NULL,
  pelicula_log_pelicula_estelares text NOT NULL,
  pelicula_log_pelicula_duracion interval minute NOT NULL,
  pelicula_log_pelicula_clasif pelicula_clasificacion NOT NULL,
  pelicula_log_genero_id bigserial NOT NULL,
  pelicula_log_pelicula_portada bytea NOT NULL,
    pelicula_log_pelicula_portada_nombre text NOT NULL
)
WITH (
  OIDS=FALSE
);
ALTER TABLE pelicula_log
  OWNER TO postgres;


CREATE TABLE usuario_log
(
  usuario_log_operation text NOT NULL,
  usuario_log_stamp timestamp without time zone NOT NULL,
  usuario_log_userid text NOT NULL,
  usuario_log_usuario_id bigserial NOT NULL,
  usuario_log_usename text NOT NULL,
  usuario_log_privilegios bigint NOT NULL
)
WITH (
  OIDS=FALSE
);
ALTER TABLE usuario_log
  OWNER TO postgres;


CREATE TABLE venta_log
(
  venta_log_operation text NOT NULL,
  venta_log_stamp timestamp without time zone NOT NULL,
  venta_log_userid text NOT NULL,
  venta_log_venta_id bigserial NOT NULL,
  venta_log_venta_fecha date NOT NULL,
  venta_log_venta_neto double precision NOT NULL,
  venta_log_empleado_id text NOT NULL,
  venta_log_cliente_id text NOT NULL
)
WITH (
  OIDS=FALSE
);
ALTER TABLE venta_log
  OWNER TO postgres;




CREATE OR REPLACE FUNCTION log_cliente() RETURNS TRIGGER AS $cliente_log$
BEGIN
  IF(TG_OP = 'DELETE') THEN
    INSERT INTO cliente_log SELECT 'DELETE', now(), user, OLD.*;
    RETURN OLD;
  ELSIF(TG_OP = 'INSERT') THEN
    INSERT INTO cliente_log SELECT 'INSERT', now(), user, NEW.*;
    RETURN NEW;
  ELSIF(TG_OP = 'UPDATE') THEN
    INSERT INTO cliente_log SELECT 'UPDATE', now(), user, NEW.*;
    RETURN NEW;
  END IF;
  RETURN NULL;
END;
$cliente_log$ LANGUAGE plpgsql;

CREATE TRIGGER cliente_log
AFTER INSERT OR DELETE OR UPDATE ON cliente
FOR EACH ROW EXECUTE PROCEDURE log_cliente();

CREATE OR REPLACE FUNCTION log_copia_pelicula() RETURNS TRIGGER AS $copia_pelicula_log$
BEGIN
  IF(TG_OP = 'DELETE') THEN
    INSERT INTO copia_pelicula_log SELECT 'DELETE', now(), user, OLD.*;
    RETURN OLD;
  ELSIF(TG_OP = 'INSERT') THEN
    INSERT INTO copia_pelicula_log SELECT 'INSERT', now(), user, NEW.*;
    RETURN NEW;
  ELSIF(TG_OP = 'UPDATE') THEN
    INSERT INTO copia_pelicula_log SELECT 'UPDATE', now(), user, NEW.*;
    RETURN NEW;
  END IF;
  RETURN NULL;
END;
$copia_pelicula_log$ LANGUAGE plpgsql;

CREATE TRIGGER copia_pelicula_log
 AFTER INSERT OR DELETE OR UPDATE ON copia_pelicula
FOR EACH ROW EXECUTE PROCEDURE log_copia_pelicula();

CREATE OR REPLACE FUNCTION log_detalle_venta() RETURNS TRIGGER AS $detalle_venta_log$
BEGIN
  IF(TG_OP = 'DELETE') THEN
    INSERT INTO detalle_venta_log SELECT 'DELETE', now(), user, OLD.*;
    RETURN OLD;
  ELSIF(TG_OP = 'INSERT') THEN
    INSERT INTO detalle_venta_log SELECT 'INSERT', now(), user, NEW.*;
    RETURN NEW;
  ELSIF(TG_OP = 'UPDATE') THEN
    INSERT INTO detalle_venta_log SELECT 'UPDATE', now(), user, NEW.*;
    RETURN NEW;
  END IF;
  RETURN NULL;
END;
$detalle_venta_log$ LANGUAGE plpgsql;

CREATE TRIGGER detalle_venta_log
AFTER INSERT OR DELETE OR UPDATE ON detalle_venta
FOR EACH ROW EXECUTE PROCEDURE log_detalle_venta();

CREATE OR REPLACE FUNCTION log_empleado() RETURNS TRIGGER AS $empleado_log$
BEGIN
  IF(TG_OP = 'DELETE') THEN
    INSERT INTO empleado_log SELECT 'DELETE', now(), user, OLD.*;
    RETURN OLD;
  ELSIF(TG_OP = 'INSERT') THEN
    INSERT INTO empleado_log SELECT 'INSERT', now(), user, NEW.*;
    RETURN NEW;
  ELSIF(TG_OP = 'UPDATE') THEN
    INSERT INTO empleado_log SELECT 'UPDATE', now(), user, NEW.*;
    RETURN NEW;
  END IF;
  RETURN NULL;
END;
$empleado_log$ LANGUAGE plpgsql;

CREATE TRIGGER empleado_log
AFTER INSERT OR DELETE OR UPDATE ON empleado
FOR EACH ROW EXECUTE PROCEDURE log_empleado();

CREATE OR REPLACE FUNCTION log_genero() RETURNS TRIGGER AS $genero_log$
BEGIN
  IF(TG_OP = 'DELETE') THEN
    INSERT INTO genero_log SELECT 'DELETE', now(), user, OLD.*;
    RETURN OLD;
  ELSIF(TG_OP = 'INSERT') THEN
    INSERT INTO genero_log SELECT 'INSERT', now(), user, NEW.*;
    RETURN NEW;
  ELSIF(TG_OP = 'UPDATE') THEN
    INSERT INTO genero_log SELECT 'UPDATE', now(), user, NEW.*;
    RETURN NEW;
  END IF;
  RETURN NULL;
END;
$genero_log$ LANGUAGE plpgsql;

CREATE TRIGGER genero_log
AFTER INSERT OR DELETE OR UPDATE ON genero
FOR EACH ROW EXECUTE PROCEDURE log_genero();

CREATE OR REPLACE FUNCTION log_pelicula() RETURNS TRIGGER AS $pelicula_log$
BEGIN
  IF(TG_OP = 'DELETE') THEN
    INSERT INTO pelicula_log SELECT 'DELETE', now(), user, OLD.*;
    RETURN OLD;
  ELSIF(TG_OP = 'INSERT') THEN
    INSERT INTO pelicula_log SELECT 'INSERT', now(), user, NEW.*;
    RETURN NEW;
  ELSIF(TG_OP = 'UPDATE') THEN
    INSERT INTO pelicula_log SELECT 'UPDATE', now(), user, NEW.*;
    RETURN NEW;
  END IF;
  RETURN NULL;
END;
$pelicula_log$ LANGUAGE plpgsql;

CREATE TRIGGER pelicula_log
AFTER INSERT OR DELETE OR UPDATE ON pelicula
FOR EACH ROW EXECUTE PROCEDURE log_pelicula();

CREATE OR REPLACE FUNCTION log_usuario() RETURNS TRIGGER AS $usuario_log$
BEGIN
  IF(TG_OP = 'DELETE') THEN
    INSERT INTO usuario_log SELECT 'DELETE', now(), user, OLD.*;
    RETURN OLD;
  ELSIF(TG_OP = 'INSERT') THEN
    INSERT INTO usuario_log SELECT 'INSERT', now(), user, NEW.*;
    RETURN NEW;
  ELSIF(TG_OP = 'UPDATE') THEN
    INSERT INTO usuario_log SELECT 'UPDATE', now(), user, NEW.*;
    RETURN NEW;
  END IF;
  RETURN NULL;
END;
$usuario_log$ LANGUAGE plpgsql;

CREATE TRIGGER usuario_log
AFTER INSERT OR DELETE OR UPDATE ON usuario
FOR EACH ROW EXECUTE PROCEDURE log_usuario();

CREATE OR REPLACE FUNCTION log_venta() RETURNS TRIGGER AS $venta_log$
BEGIN
  IF(TG_OP = 'DELETE') THEN
    INSERT INTO venta_log SELECT 'DELETE', now(), user, OLD.*;
    RETURN OLD;
  ELSIF(TG_OP = 'INSERT') THEN
    INSERT INTO venta_log SELECT 'INSERT', now(), user, NEW.*;
    RETURN NEW;
  ELSIF(TG_OP = 'UPDATE') THEN
    INSERT INTO venta_log SELECT 'UPDATE', now(), user, NEW.*;
    RETURN NEW;
  END IF;
  RETURN NULL;
END;
$venta_log$ LANGUAGE plpgsql;

CREATE TRIGGER venta_log
AFTER INSERT OR DELETE OR UPDATE ON venta
FOR EACH ROW EXECUTE PROCEDURE log_venta();
