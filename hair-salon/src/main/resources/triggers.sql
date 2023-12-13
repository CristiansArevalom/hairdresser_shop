CREATE TABLE AUD_PRECIOSINVENTARIO
(ID_INVENTARIO NUMBER (12) CONSTRAINT ID_INVENTARIO_PK PRIMARY KEY,
PRECIO_VENTA_ANT NUMBER (10,2),
PRECIO_VENTA_DESP NUMBER (10,2),
PRECIO_COMPRA_ANT NUMBER (10,2),
PRECIO_COMPRA_DESP NUMBER (10,2),
USUARIO VARCHAR2 (20),
DIA DATE );

CREATE SEQUENCE AUD_PRECIOSINVENTARIO_ID
INCREMENT BY 1
START WITH 1
MAXVALUE 999999999999;

CREATE OR REPLACE TRIGGER TRIG_PRECIONV
AFTER DELETE OR INSERT OR UPDATE OF PRECIO_VENTA,PRECIO_COMPRA ON INVENTARIOS
FOR EACH ROW
BEGIN 
IF INSERTING THEN
INSERT INTO AUD_PRECIOSINVENTARIO
VALUES (AUD_PRECIOSINVENTARIO_ID.NEXTVAL,NULL,:NEW.PRECIO_VENTA,NULL,:NEW.PRECIO_COMPRA,
USER,SYSDATE);
ELSIF UPDATING THEN
INSERT INTO AUD_PRECIOSINVENTARIO
Values (AUD_PRECIOSINVENTARIO_ID.NEXTVAL,:OLD.PRECIO_VENTA,:NEW.PRECIO_VENTA,
:OLD.PRECIO_COMPRA,:NEW.PRECIO_COMPRA,USER,SYSDATE);
ELSE
INSERT INTO AUD_PRECIOSINVENTARIO
VALUES (AUD_PRECIOSINVENTARIO_ID.NEXTVAL,:OLD.PRECIO_VENTA,NULL,
:OLD.PRECIO_COMPRA,NULL,USER,SYSDATE);
END IF;
END TRIG_PRECIOINV;
/


CREATE TABLE AUD_ESTADOINV
(ID_ESTADOINV NUMBER (10) CONSTRAINT ID_ESTADOINV_PK PRIMARY KEY,
ESTADO_ANT VARCHAR2 (60),
ESTADO_DESP VARCHAR2 (60),
USUARIO VARCHAR2 (20),
descripcion varchar2 (100),
DIA DATE );

CREATE SEQUENCE AUD_ESTADOINV_ID
INCREMENT BY 1
START WITH 1
MAXVALUE 999999999999;

CREATE OR REPLACE TRIGGER TRIG_ESTADOINV
AFTER DELETE OR INSERT OR UPDATE OF estado ON inventarios
FOR EACH ROW
DECLARE
BEGIN
IF inserting THEN
INSERT INTO AUD_ESTADOINV
VALUES (AUD_ESTADOINV_ID.NEXTVAL,null,:NEW.ESTADO,user,'Se ha insertado el codigo: ' || :new.CODIGO_BARRAS,sysdate);
ELSIF updating THEN
INSERT INTO AUD_ESTADOINV
VALUES (AUD_ESTADOINV_ID.NEXTVAL,:old.estado,:new.estado,user,'Se ha modificado el codigo: ' || :new.CODIGO_BARRAS, sysdate);
ELSE
INSERT INTO AUD_ESTADOINV
VALUES (AUD_ESTADOINV_ID.NEXTVAL,:old.estado,null,user,'Se ha elimio el estado del codigo: ' || :new.CODIGO_BARRAS, sysdate);
END IF;
END TRIG_ESTADOINV;
/

CREATE SEQUENCE AUD_HORARIO_ID
INCREMENT BY 1
START WITH 1
MAXVALUE 999999999999;


CREATE TABLE AUD_HORARIO
(ID_AUDHORARIO NUMBER (10) CONSTRAINT ID_AUDHORARIO_PK PRIMARY KEY,
INICIO_TURNO_ANT DATE,
INICIO_TURNO_DESP DATE,
FIN_TURNO_ANT DATE,
FIN_TURNO_DESP DATE,
USUARIO VARCHAR2 (20),
DIA DATE );

CREATE OR REPLACE TRIGGER TRIG_HORARIO
AFTER DELETE OR INSERT OR UPDATE OF INICIO_TURNO,FIN_TURNO ON HORARIOS
FOR EACH ROW
BEGIN 
IF INSERTING THEN
INSERT INTO AUD_HORARIO
VALUES (AUD_HORARIO_ID.NEXTVAL,NULL,:NEW.INICIO_TURNO,NULL,:NEW.FIN_TURNO,
USER,SYSDATE);
ELSIF UPDATING THEN
INSERT INTO AUD_HORARIO
Values (AUD_HORARIO_ID.NEXTVAL,:OLD.INICIO_TURNO,:NEW.INICIO_TURNO,
:OLD.FIN_TURNO,:NEW.FIN_TURNO,USER,SYSDATE);
ELSE
INSERT INTO AUD_HORARIO
VALUES (AUD_HORARIO_ID.NEXTVAL,:OLD.INICIO_TURNO,NULL,
:OLD.FIN_TURNO,NULL,USER,SYSDATE);
END IF;
END TRIG_HORARIO;
/

CREATE TABLE AUD_SALCARGOS
(ID_SALCARGOS NUMBER (10) CONSTRAINT ID_SALCARGOS_PK PRIMARY KEY,
SALARIO_HORA_ANT NUMBER(6,3),
SALARIO_HORA_DESP NUMBER(6,3),
USUARIO VARCHAR2 (20),
descripcion varchar2 (100),
DIA DATE );

CREATE SEQUENCE AUD_SALCARGOS_ID
INCREMENT BY 1
START WITH 1
MAXVALUE 99999;

CREATE OR REPLACE TRIGGER TRIG_SALCARGOS
AFTER DELETE OR INSERT OR UPDATE OF SALARIO_HORA ON CARGOS
FOR EACH ROW
BEGIN
IF inserting THEN
INSERT INTO AUD_SALCARGOS
VALUES (AUD_SALCARGOS_ID.NEXTVAL,null,:NEW.SALARIO_HORA,user,'Se ha insertado el codigo: ' || :new.ID_CARGO,sysdate);
ELSIF updating THEN
INSERT INTO AUD_SALCARGOS
VALUES (AUD_SALCARGOS_ID.NEXTVAL,:old.SALARIO_HORA,:new.SALARIO_HORA,user,'Se ha modificado el codigo: ' || :new.ID_CARGO, sysdate);
ELSE
INSERT INTO AUD_SALCARGOS
VALUES (AUD_SALCARGOS_ID.NEXTVAL,:old.SALARIO_HORA,null,user,'Se ha elimio el estado del codigo: ' || :new.ID_CARGO, sysdate);
END IF;
END TRIG_SALCARGOS;
/

CREATE TABLE AUD_NUMEMPLEADO
(ID_NUMEMPLEADO NUMBER (10) CONSTRAINT ID_NUMEMPLEADO_PK PRIMARY KEY,
DOCUMENTO_ANT NUMBER(12),
DOCUMENTO_DESP NUMBER(12),
USUARIO VARCHAR2 (20),
descripcion varchar2 (100),
DIA DATE);

CREATE SEQUENCE AUD_NUMEMPLEADO_ID
INCREMENT BY 1
START WITH 1
MAXVALUE 99999;

CREATE OR REPLACE TRIGGER TRIG_NUMEMPLEADO
AFTER DELETE OR INSERT OR UPDATE OF DOCUMENTO ON EMPLEADOS
FOR EACH ROW
BEGIN
IF inserting THEN
INSERT INTO AUD_NUMEMPLEADO
VALUES (AUD_NUMEMPLEADO_ID.NEXTVAL,null,:NEW.DOCUMENTO,user,'Se ha insertado el codigo: ' || :new.ID_EMPLEADO,sysdate);
ELSIF updating THEN
INSERT INTO AUD_NUMEMPLEADO
VALUES (AUD_NUMEMPLEADO_ID.NEXTVAL,:old.DOCUMENTO,:new.DOCUMENTO,user,'Se ha modificado el codigo: ' || :new.ID_EMPLEADO , sysdate);
ELSE
INSERT INTO AUD_NUMEMPLEADO
VALUES (AUD_NUMEMPLEADO_ID.NEXTVAL,:old.DOCUMENTO,null,user,'Se ha elimio el estado del codigo: ' || :new.ID_EMPLEADO, sysdate);
END IF;
END TRIG_NUMEMPLEADO;
/
