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
  
cliente_log_cliente_imagen bytea NOT NULL
)

WITH (
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
  empleado_log_empleado_imagen bytea NOT NULL
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
)
WITH (
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
  pelicula_log_pelicula_portada bytea NOT NULL
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
