CREATE TABLE TIPOS
(ID_TIPO NUMBER (3) CONSTRAINT TIPO_ID_TIPO_PK PRIMARY KEY,
NOMBRE VARCHAR2 (14) NOT NULL ,
DESCRIPCION VARCHAR2 (20) NOT NULL
);
CREATE TABLE MISCELANEAS
(ID_MISCELANEA NUMBER (2) CONSTRAINT MISC_ID_MISCELANEA_PK PRIMARY KEY,
NOMBRE VARCHAR2 (20) NOT NULL,
DIRECCION VARCHAR2(20) NOT NULL,
TELEFONO NUMBER (10),
AREA NUMBER (5) NOT NULL
);
CREATE TABLE ESTANTERIAS
(ID_ESTANTERIA NUMBER (2) CONSTRAINT ESTANT_ID_ESTANTERIA_PK PRIMARY KEY,
NOMBRE VARCHAR2 (20) NOT NULL,
ALTURA NUMBER (3,2) NOT NULL, 
ANCHURA NUMBER(3,2) NOT NULL,
CAPACIDAD NUMBER (6,2) NOT NULL,
ID_TIPO NUMBER (3) NOT NULL,
ID_MISCELANEA NUMBER (2) NOT NULL,
CONSTRAINT EST_ID_TIPO_FK FOREIGN KEY (ID_TIPO) REFERENCES TIPOS (ID_TIPO),
CONSTRAINT EST_ID_MISCELANEA_FK FOREIGN KEY (ID_MISCELANEA) REFERENCES MISCELANEAS (ID_MISCELANEA)
);
 
CREATE TABLE CATEGORIAS
(ID_CATEGORIA NUMBER (2) CONSTRAINT CAT_ID_CATEGORIA_PK PRIMARY KEY,
NOMBRE VARCHAR2 (50) NOT NULL,
DESCRIPCION VARCHAR2 (50)
);
CREATE TABLE PRODUCTOS
(ID_PRODUCTO NUMBER (6) CONSTRAINT PROD_CODIGO_BARRAS_PK PRIMARY KEY,
NOMBRE VARCHAR2(50) NOT NULL,
DESCRIPCION VARCHAR2(150),
ID_CATEGORIA NUMBER (2) NOT NULL,
CONSTRAINTS PROD_ID_CATEGORIA_FK FOREIGN KEY (ID_CATEGORIA) REFERENCES CATEGORIAS (ID_CATEGORIA)
);
CREATE TABLE CARGOS
(ID_CARGO NUMBER (2) CONSTRAINT CARG_ID_CARGO_PK PRIMARY KEY,
NOMBRE_CARGO  VARCHAR2 (14) NOT NULL,
SALARIO_HORA NUMBER(6,2)NOT NULL--6 CIFRAS CON 2 DIGITOS
);
CREATE TABLE EMPLEADOS
(ID_EMPLEADO NUMBER(3) CONSTRAINT EMP_ID_EMPLEADO_PK PRIMARY KEY,
NOMBRE VARCHAR2(20) NOT NULL,
APELLIDO VARCHAR2(20) NOT NULL,
DOCUMENTO NUMBER (12) CONSTRAINT EMP_DOCUMENTO_UK UNIQUE NOT NULL,
GENERO CHAR(1) NOT NULL,
FECHA_NACIMIENTO DATE,
TELEFONO NUMBER(12) NOT NULL,
DIRECCION VARCHAR2 (50) NOT NULL,
ID_CARGO NUMBER (2) NOT NULL,
CONSTRAINT EMP_ID_CARGO_FK FOREIGN KEY (ID_CARGO) REFERENCES CARGOS (ID_CARGO),
CONSTRAINT EMP_GENERO_CK CHECK (GENERO IN ('M','F'))
);
 
CREATE TABLE HORARIOS
(ID_HORARIO NUMBER (3) CONSTRAINT HOR_ID_HORARIO_PK PRIMARY KEY,
INICIO_TURNO DATE NOT NULL,
FIN_TURNO DATE NOT NULL
);
CREATE TABLE HORARIOS_ASIGNADOS
(ID_HORARIO_ASIGNADO NUMBER (3) CONSTRAINT HOR_ASIG_ID_HORARIO_ASIG_PK PRIMARY KEY,
ID_HORARIO NUMBER (3) NOT NULL,
ID_EMPLEADO NUMBER (3) NOT NULL,
DIA VARCHAR2(12) NOT NULL,
CONSTRAINT HOR_ASIGN_ID_EMPLEADO_FK FOREIGN KEY(ID_EMPLEADO) REFERENCES EMPLEADOS (ID_EMPLEADO),
CONSTRAINT HOR_ASIG_ID_HORARIO_FK FOREIGN KEY (ID_HORARIO) REFERENCES HORARIOS (ID_HORARIO)
);
CREATE TABLE CLIENTES
(ID_CLIENTE NUMBER (3) CONSTRAINT CLI_ID_CLIENTE_PK PRIMARY KEY,
DOCUMENTO NUMBER(12),
NOMBRE VARCHAR2 (20) NOT NULL,
GENERO CHAR (1) NOT NULL,
TELEFONO NUMBER (12),
CONSTRAINT CLI_DOCUMENTO_UK UNIQUE (DOCUMENTO),
CONSTRAINT CLI_GENERO_CK CHECK (GENERO IN ('M','F'))
);
 
CREATE TABLE PROVEEDORES
(ID_PROVEEDOR NUMBER (3) CONSTRAINT PROV_ID_PROVEEDOR_PK PRIMARY KEY,
NOMBRE VARCHAR2(20) NOT NULL,
DIRECCION VARCHAR2(70) NOT NULL,
TELEFONO NUMBER(12) NOT NULL
);
CREATE TABLE PEDIDOS
(ID_PEDIDO NUMBER(3) CONSTRAINT PED_ID_PEDIDO_PK PRIMARY KEY,
ID_PROVEEDOR NUMBER(3) NOT NULL,
ID_EMPLEADO NUMBER (3) NOT NULL,
FECHA DATE,
CONSTRAINT PED_ID_PROVEEDOR_FK FOREIGN KEY (ID_PROVEEDOR) REFERENCES PROVEEDORES (ID_PROVEEDOR),
CONSTRAINT PED_ID_EMPLEADO_FK FOREIGN KEY (ID_EMPLEADO) REFERENCES EMPLEADOS (ID_EMPLEADO)
);

CREATE TABLE DETALLE_PEDIDOS 
(ID_DETALLE_PEDIDO NUMBER (3) CONSTRAINT DETPED_ID_DETALLE_PEDIDO PRIMARY KEY,
ID_PEDIDO NUMBER (3) NOT NULL,
ID_PRODUCTO NUMBER (6) NOT NULL,
CONSTRAINT DETPED_ID_PRODUCTO_FK FOREIGN KEY (ID_PRODUCTO) REFERENCES PRODUCTOS (ID_PRODUCTO),
CONSTRAINT DETPED_ID_PEDIDO_FK FOREIGN KEY (ID_PEDIDO) REFERENCES PEDIDOS (ID_PEDIDO)
);

CREATE TABLE INVEARIOS
(CODIGO_BARRAS NUMBER (12) CONSTRAINT INV_COD_BARRAS_PK PRIMARY KEY,
FECHA_VENCIMIENTO DATE,
FECHA_INGRESO DATE NOT NULL,
ESTADO VARCHAR2(10) NOT NULL,
PESO NUMBER(3,2) NOT NULL, --999.99 gramos
ID_PRODUCTO NUMBER (NT6) NOT NULL,
PRECIO_COMPRA NUMBER (10,2) NOT NULL,--10 DIGITOS CON 2 CIFRAS
PRECIO_VENTA NUMBER (10,2) NOT NULL,--10 DIGITOS CON 2 CIFRAS
ID_ESTANTERIA NUMBER(2) NOT NULL,
ID_DETALLE_PEDIDO NUMBER (3) NOT NULL,
CONSTRAINT INV_ID_DETALLE_PEDIDO_FK FOREIGN KEY (ID_DETALLE_PEDIDO) REFERENCES DETALLE_PEDIDOS (ID_DETALLE_PEDIDO),
CONSTRAINT INV_ID_PRODUCTO_FK FOREIGN KEY (ID_PRODUCTO) REFERENCES PRODUCTOS (ID_PRODUCTO),
CONSTRAINT INV_ID_ESTANTERIA_FK FOREIGN KEY (ID_ESTANTERIA) REFERENCES ESTANTERIAS (ID_ESTANTERIA)
);

CREATE TABLE FACTURAS
(ID_FACTURA NUMBER (4) CONSTRAINT FAC_ID_FACTURA_PK PRIMARY KEY,
ID_CLIENTE NUMBER (3) NOT NULL,
ID_EMPLEADO NUMBER (3) NOT NULL,
FECHA_FACTURA DATE NOT NULL,
CONSTRAINT FACT_ID_CLIENTE_FK FOREIGN KEY (ID_CLIENTE) REFERENCES CLIENTES (ID_CLIENTE),
CONSTRAINT FACT_ID_EMPLEADO_FK FOREIGN KEY (ID_EMPLEADO) REFERENCES EMPLEADOS (ID_EMPLEADO)
);
CREATE TABLE DETALLE_FACTURAS
(ID_DETALLE_FACTURA NUMBER (4) CONSTRAINT DETFAC_ID_DETFAC_PK PRIMARY KEY,
ID_FACTURA NUMBER (4) NOT NULL,
CODIGO_BARRAS NUMBER (12) NOT NULL,
CONSTRAINT DETFAC_ID_FACTURA_FK FOREIGN KEY (ID_FACTURA) REFERENCES FACTURAS (ID_FACTURA),
CONSTRAINT DETFAC_CODIGO_BARRAS_FK FOREIGN KEY (CODIGO_BARRAS) REFERENCES INVENTARIOS (CODIGO_BARRAS)
);







INSERT INTO TIPOS (ID_TIPO,DESCRIPCION,NOMBRE) VALUES (001,'ALMACENAR LUQIDOS','METALICA');
INSERT INTO TIPOS VALUES (002,'VIDRIO','PAPELERIA EXHIBIDA'); 
INSERT INTO TIPOS (ID_TIPO,NOMBRE,DESCRIPCION) VALUES (003,'MADERA','ALMACENAR PAPELERIA');
INSERT INTO TIPOS (ID_TIPO,NOMBRE,DESCRIPCION) VALUES (004,'REFRIGERADA','PRODUCTOS CONGELADOS');

INSERT INTO MISCELANEAS (ID_MISCELANEA,NOMBRE,DIRECCION,TELEFONO,AREA) VALUES (001,'CID','CALLE 39 SUR NO 87D',1234567890,50);

INSERT INTO ESTANTERIAS (ID_ESTANTERIA,NOMBRE,ALTURA,ANCHURA,CAPACIDAD,ID_TIPO,ID_MISCELANEA)
VALUES (01,'A',2.5,1.75,75.2,001,001);
INSERT INTO ESTANTERIAS (ID_ESTANTERIA,NOMBRE,ALTURA,ANCHURA,CAPACIDAD,ID_TIPO,ID_MISCELANEA)
VALUES (02,'B',1,2.15,15.7,002,001);
INSERT INTO ESTANTERIAS (ID_ESTANTERIA,NOMBRE,ALTURA,ANCHURA,CAPACIDAD,ID_TIPO,ID_MISCELANEA)
VALUES (03,'C',2.5,1.12,40.85,003,001);
INSERT INTO ESTANTERIAS (ID_ESTANTERIA,NOMBRE,ALTURA,ANCHURA,CAPACIDAD,ID_TIPO,ID_MISCELANEA)
VALUES (04,'D',1,2.5,30.36,004,001);


INSERT INTO CATEGORIAS (ID_CATEGORIA,NOMBRE,DESCRIPCION) 
VALUES (01,'UTILES ESCOLARES','NULL');
INSERT INTO CATEGORIAS (ID_CATEGORIA,NOMBRE,DESCRIPCION) 
VALUES (02,'PAPELES ADESHIVOS Y BLOCKS','MATERIAL DE PAPELERIA');
INSERT INTO CATEGORIAS (ID_CATEGORIA,NOMBRE,DESCRIPCION) 
VALUES (03,'ARCHIVO Y CLASIFICACION','ORGANIZADORES');
INSERT INTO CATEGORIAS (ID_CATEGORIA,NOMBRE,DESCRIPCION) 
VALUES (04,'CONSUMIBLES','COMIDAS Y BEBIAS');

INSERT INTO PRODUCTOS (ID_PRODUCTO,NOMBRE,DESCRIPCION,ID_CATEGORIA)
VALUES (100001,'Cuaderno camion cuadriculado A4 ','cosido 80 hojas',01);
INSERT INTO PRODUCTOS (ID_PRODUCTO,NOMBRE,DESCRIPCION,ID_CATEGORIA)
VALUES (100002,'Resma papel impresora carta',' 500 hojas',02); 
INSERT INTO PRODUCTOS (ID_PRODUCTO,NOMBRE,DESCRIPCION,ID_CATEGORIA)
VALUES (100003,'Carpeta legajadora Carta ','Ventanilla pl�stica en 2 posiciones para se�alizar,con capacidad m�xima de 200 hojas',03); 
INSERT INTO PRODUCTOS (ID_PRODUCTO,NOMBRE,DESCRIPCION,ID_CATEGORIA)
VALUES (100004,'Choco Cono','Helado ',04);
INSERT INTO PRODUCTOS (ID_PRODUCTO,NOMBRE,DESCRIPCION,ID_CATEGORIA)
VALUES (100005,'Cuaderno Barbie cuadriculado A4 ','cosido 100 hojas',01);


INSERT INTO CARGOS (ID_CARGO,NOMBRE_CARGO,SALARIO_HORA)
VALUES (01,'Administrador',6253);
INSERT INTO CARGOS (ID_CARGO,NOMBRE_CARGO,SALARIO_HORA)
VALUES (02,'Vendedor',3255);
INSERT INTO CARGOS (ID_CARGO,NOMBRE_CARGO,SALARIO_HORA)
VALUES (03,'Organizdor',4255);

INSERT INTO EMPLEADOS (ID_EMPLEADO,NOMBRE,APELLIDO,DOCUMENTO,GENERO,FECHA_NACIMIENTO,TELEFONO,DIRECCION,ID_CARGO)
VALUES(1,'Marina','Mesa',1234567890,'F',to_date('2-4-1981','dd-mm-yyyy'),312345678910,'CALLE 39 SUR NO 7D',01);
INSERT INTO EMPLEADOS (ID_EMPLEADO,NOMBRE,APELLIDO,DOCUMENTO,GENERO,FECHA_NACIMIENTO,TELEFONO,DIRECCION,ID_CARGO)
VALUES(2,'Juan','Gomez',2334567890,'M',to_date('25-11-1991','dd-mm-yyyy'),314158967524,'Carrera 12 SUR NO 4A',02);
INSERT INTO EMPLEADOS (ID_EMPLEADO,NOMBRE,APELLIDO,DOCUMENTO,GENERO,FECHA_NACIMIENTO,TELEFONO,DIRECCION,ID_CARGO)
VALUES(3,'Ana','Perez',3434567890,'F',to_date('12-6-1997','dd-mm-yyyy'),326158967527,'Av chile 18 SUR NO 4A',02);
INSERT INTO EMPLEADOS (ID_EMPLEADO,NOMBRE,APELLIDO,DOCUMENTO,GENERO,FECHA_NACIMIENTO,TELEFONO,DIRECCION,ID_CARGO)
VALUES (4,'Mario','Murillo',43412345678,'M',to_date('28-9-1987','dd-mm-yyyy'),336123456789,'Carrera 7 No 7F',03);

INSERT INTO HORARIOS (ID_HORARIO,INICIO_TURNO,FIN_TURNO)
VALUES (101,TO_DATE('8:30:00','HH24:MI:SS'),TO_DATE('19:30:00','HH24:MI:SS'));
INSERT INTO HORARIOS (ID_HORARIO,INICIO_TURNO,FIN_TURNO)
VALUES (102,TO_DATE('8:30:00','HH24:MI:SS'),TO_DATE('13:30:00','HH24:MI:SS'));
INSERT INTO HORARIOS (ID_HORARIO,INICIO_TURNO,FIN_TURNO)
VALUES (103,TO_DATE('14:00:00','HH24:MI:SS'),TO_DATE('19:00:00','HH24:MI:SS'));
INSERT INTO HORARIOS (ID_HORARIO,INICIO_TURNO,FIN_TURNO)
VALUES (104,TO_DATE('10:00:00','HH24:MI:SS'),TO_DATE('14:00:00','HH24:MI:SS'));

INSERT INTO HORARIOS_ASIGNADOS (ID_HORARIO_ASIGNADO,ID_HORARIO,ID_EMPLEADO,DIA)
VALUES(001,101,1,'Lunes');
INSERT INTO HORARIOS_ASIGNADOS (ID_HORARIO_ASIGNADO,ID_HORARIO,ID_EMPLEADO,DIA)
VALUES(002,102,1,'Jueves');
INSERT INTO HORARIOS_ASIGNADOS (ID_HORARIO_ASIGNADO,ID_HORARIO,ID_EMPLEADO,DIA)
VALUES(003,104,1,'Viernes');
INSERT INTO HORARIOS_ASIGNADOS (ID_HORARIO_ASIGNADO,ID_HORARIO,ID_EMPLEADO,DIA)
VALUES(004,103,1,'Domingo');
INSERT INTO HORARIOS_ASIGNADOS (ID_HORARIO_ASIGNADO,ID_HORARIO,ID_EMPLEADO,DIA)
VALUES(005,102,2,'Lunes');
INSERT INTO HORARIOS_ASIGNADOS (ID_HORARIO_ASIGNADO,ID_HORARIO,ID_EMPLEADO,DIA)
VALUES(006,103,2,'Lunes');
INSERT INTO HORARIOS_ASIGNADOS (ID_HORARIO_ASIGNADO,ID_HORARIO,ID_EMPLEADO,DIA)
VALUES(007,103,2,'Miercoles');
INSERT INTO HORARIOS_ASIGNADOS (ID_HORARIO_ASIGNADO,ID_HORARIO,ID_EMPLEADO,DIA)
VALUES(008,104,2,'Viernes');
INSERT INTO HORARIOS_ASIGNADOS (ID_HORARIO_ASIGNADO,ID_HORARIO,ID_EMPLEADO,DIA)
VALUES(009,101,3,'Martes');
INSERT INTO HORARIOS_ASIGNADOS (ID_HORARIO_ASIGNADO,ID_HORARIO,ID_EMPLEADO,DIA)
VALUES(010,103,3,'Jueves');
INSERT INTO HORARIOS_ASIGNADOS (ID_HORARIO_ASIGNADO,ID_HORARIO,ID_EMPLEADO,DIA)
VALUES(011,102,3,'Sabado');
INSERT INTO HORARIOS_ASIGNADOS (ID_HORARIO_ASIGNADO,ID_HORARIO,ID_EMPLEADO,DIA)
VALUES(012,103,3,'Sabado');
INSERT INTO HORARIOS_ASIGNADOS (ID_HORARIO_ASIGNADO,ID_HORARIO,ID_EMPLEADO,DIA)
VALUES(013,101,4,'Domingo');


INSERT INTO CLIENTES (ID_CLIENTE,DOCUMENTO,NOMBRE,GENERO,TELEFONO)
VALUES (1,null,'Maria','F',null);
INSERT INTO CLIENTES (ID_CLIENTE,DOCUMENTO,NOMBRE,GENERO,TELEFONO)
VALUES (2,123456755,'Gabriel','M',null);
INSERT INTO CLIENTES (ID_CLIENTE,DOCUMENTO,NOMBRE,GENERO,TELEFONO)
VALUES (3,null,'Pedro','F', 31287951236);
INSERT INTO CLIENTES (ID_CLIENTE,DOCUMENTO,NOMBRE,GENERO,TELEFONO)
VALUES (4,1234567866,'Juan','M',3186458972);

INSERT INTO PROVEEDORES (ID_PROVEEDOR,NOMBRE,DIRECCION,TELEFONO)
VALUES(1,'Panamericana','Calle 12 # 34 - 30', 3649000);
INSERT INTO PROVEEDORES (ID_PROVEEDOR,NOMBRE,DIRECCION,TELEFONO)
VALUES(2,'Cipe','Calle 11 No. 21-60 cuarto piso ',5185517);
INSERT INTO PROVEEDORES (ID_PROVEEDOR,NOMBRE,DIRECCION,TELEFONO)
VALUES(3,'AbmGrupo','Carrera 27 No.71B - 77',2253682);
INSERT INTO PROVEEDORES (ID_PROVEEDOR,NOMBRE,DIRECCION,TELEFONO)
VALUES(4,'papelerialagran12','Carrera 12a Nro 10 - 61',3223697762);

INSERT INTO PEDIDOS (ID_PEDIDO,ID_PROVEEDOR,ID_EMPLEADO,FECHA)
VALUES (1,1,1,TO_DATE('20-9-2018 12:25:11','DD-MM-YYYY HH24:MI:SS'));
INSERT INTO PEDIDOS (ID_PEDIDO,ID_PROVEEDOR,ID_EMPLEADO,FECHA)
VALUES (2,2,1,TO_DATE(SYSDATE));
INSERT INTO PEDIDOS (ID_PEDIDO,ID_PROVEEDOR,ID_EMPLEADO,FECHA)
VALUES (3,3,2,TO_DATE('21-9-2018 17:12:1','DD-MM-YYYY HH24:MI:SS'));
INSERT INTO PEDIDOS (ID_PEDIDO,ID_PROVEEDOR,ID_EMPLEADO,FECHA)
VALUES (4,4,1,TO_DATE(sysdate));

INSERT INTO DETALLE_PEDIDOS (ID_DETALLE_PEDIDO,ID_PEDIDO,ID_PRODUCTO)
VALUES (101,1,100001);
INSERT INTO DETALLE_PEDIDOS (ID_DETALLE_PEDIDO,ID_PEDIDO,ID_PRODUCTO)
VALUES (102,2,100002);
INSERT INTO DETALLE_PEDIDOS (ID_DETALLE_PEDIDO,ID_PEDIDO,ID_PRODUCTO)
VALUES (103,3,100003);
INSERT INTO DETALLE_PEDIDOS (ID_DETALLE_PEDIDO,ID_PEDIDO,ID_PRODUCTO)
VALUES (104,4,100004);
INSERT INTO DETALLE_PEDIDOS (ID_DETALLE_PEDIDO,ID_PEDIDO,ID_PRODUCTO)
VALUES (105,1,100002);
INSERT INTO DETALLE_PEDIDOS (ID_DETALLE_PEDIDO,ID_PEDIDO,ID_PRODUCTO)
VALUES (106,1,100005);

INSERT INTO INVENTARIOS
(CODIGO_BARRAS,FECHA_INGRESO,ESTADO,PESO,ID_PRODUCTO,PRECIO_COMPRA,PRECIO_VENTA,ID_ESTANTERIA,ID_DETALLE_PEDIDO)
VALUES (723698439756,TO_DATE('25,02,2018','DD-MM-YYYY'),'Disponible',0.300,100001,1272,1800,03,105);
INSERT INTO INVENTARIOS
(CODIGO_BARRAS,FECHA_VENCIMIENTO,FECHA_INGRESO,ESTADO,PESO,ID_PRODUCTO,PRECIO_COMPRA,PRECIO_VENTA,ID_ESTANTERIA,ID_DETALLE_PEDIDO)
VALUES (35769712589,NULL,TO_DATE('25,02,2018','DD-MM-YYYY'),'Disponible',0.300,100001,1272,1800,03,105);
INSERT INTO INVENTARIOS
(CODIGO_BARRAS,FECHA_VENCIMIENTO,FECHA_INGRESO,ESTADO,PESO,ID_PRODUCTO,PRECIO_COMPRA,PRECIO_VENTA,ID_ESTANTERIA,ID_DETALLE_PEDIDO)
VALUES (235486712869,NULL,TO_DATE('25,02,2018','DD-MM-YYYY'),'Disponible',0.75,100002,9900,11200,02,102);
INSERT INTO INVENTARIOS
(CODIGO_BARRAS,FECHA_VENCIMIENTO,FECHA_INGRESO,ESTADO,PESO,ID_PRODUCTO,PRECIO_COMPRA,PRECIO_VENTA,ID_ESTANTERIA,ID_DETALLE_PEDIDO)
VALUES (235486712879,NULL,TO_DATE('25,04,2018','DD-MM-YYYY'),'Disponible',0.15,100003,1200,1800,03,103);
INSERT INTO INVENTARIOS
(CODIGO_BARRAS,FECHA_VENCIMIENTO,FECHA_INGRESO,ESTADO,PESO,ID_PRODUCTO,PRECIO_COMPRA,PRECIO_VENTA,ID_ESTANTERIA,ID_DETALLE_PEDIDO)
VALUES (435486712864,TO_DATE('15,12,2019','DD-MM-YYYY'),TO_DATE('25,02,2018','DD-MM-YYYY'),'Disponible',0.150,100004,1200,1800,04,104);
INSERT INTO INVENTARIOS
(CODIGO_BARRAS,FECHA_VENCIMIENTO,FECHA_INGRESO,ESTADO,PESO,ID_PRODUCTO,PRECIO_COMPRA,PRECIO_VENTA,ID_ESTANTERIA,ID_DETALLE_PEDIDO)
VALUES (435486712865,TO_DATE('15,11,2019','DD-MM-YYYY'),TO_DATE('25,02,2018','DD-MM-YYYY'),'Disponible',0.150,100004,1200,1800,04,104);
INSERT INTO INVENTARIOS
(CODIGO_BARRAS,FECHA_VENCIMIENTO,FECHA_INGRESO,ESTADO,PESO,ID_PRODUCTO,PRECIO_COMPRA,PRECIO_VENTA,ID_ESTANTERIA,ID_DETALLE_PEDIDO)
VALUES (435486535489,TO_DATE('08,10,2021','DD-MM-YYYY'),TO_DATE('25,02,2018','DD-MM-YYYY'),'Disponible',0.150,100004,1200,1800,04,104);
INSERT INTO INVENTARIOS
(CODIGO_BARRAS,FECHA_INGRESO,ESTADO,PESO,ID_PRODUCTO,PRECIO_COMPRA,PRECIO_VENTA,ID_ESTANTERIA,ID_DETALLE_PEDIDO)
VALUES (72369843964,TO_DATE('25,02,2018','DD-MM-YYYY'),'Disponible',0.300,100005,1272,1800,03,105);
INSERT INTO INVENTARIOS
(CODIGO_BARRAS,FECHA_INGRESO,ESTADO,PESO,ID_PRODUCTO,PRECIO_COMPRA,PRECIO_VENTA,ID_ESTANTERIA,ID_DETALLE_PEDIDO)
VALUES (72369843968,TO_DATE('25,02,2018','DD-MM-YYYY'),'Disponible',0.300,100005,1272,1800,03,105);


INSERT INTO FACTURAS(ID_FACTURA,ID_CLIENTE,ID_EMPLEADO,FECHA_FACTURA)
VALUES (2001,1,2,TO_DATE('20,02,2019','DD-MM-YYYY'));
INSERT INTO FACTURAS(ID_FACTURA,ID_CLIENTE,ID_EMPLEADO,FECHA_FACTURA)
VALUES (2002,2,3,TO_DATE('20,02,2019','DD-MM-YYYY'));
INSERT INTO FACTURAS(ID_FACTURA,ID_CLIENTE,ID_EMPLEADO,FECHA_FACTURA)
VALUES (2003,3,2,TO_DATE('21,02,2019','DD-MM-YYYY'));
INSERT INTO FACTURAS(ID_FACTURA,ID_CLIENTE,ID_EMPLEADO,FECHA_FACTURA)
VALUES (2004,4,2,TO_DATE('23,02,2019','DD-MM-YYYY'));


INSERT INTO DETALLE_FACTURAS(ID_DETALLE_FACTURA,ID_FACTURA,CODIGO_BARRAS)
VALUES (4001,2001,723698439756);
INSERT INTO DETALLE_FACTURAS(ID_DETALLE_FACTURA,ID_FACTURA,CODIGO_BARRAS)
VALUES (4002,2002,35769712589);
INSERT INTO DETALLE_FACTURAS(ID_DETALLE_FACTURA,ID_FACTURA,CODIGO_BARRAS)
VALUES (4003,2002,435486712865);
INSERT INTO DETALLE_FACTURAS(ID_DETALLE_FACTURA,ID_FACTURA,CODIGO_BARRAS)
VALUES (4004,2003,235486712869);
INSERT INTO DETALLE_FACTURAS(ID_DETALLE_FACTURA,ID_FACTURA,CODIGO_BARRAS)
VALUES (4005,2003,235486712879);
INSERT INTO DETALLE_FACTURAS(ID_DETALLE_FACTURA,ID_FACTURA,CODIGO_BARRAS)
VALUES (4006,2004,435486712864);
