package domain;

public enum TipoSociedad {
  SA,
  SL,
  SLNE,
  SLL,
  SAL,
  SC,
  SCom,
  SCoop
}

/*
    Este enum lo agregamos por el hecho de razon social
    Razon social esta compuesta por:

    Nombre Organizacion + tipoDeSociedad

    Para tener una consistencia de datos y poder sabe
     bien de que organizacion se trata agregamos este enum

    Se usaria como "nombreOrganizacion" + tipoSociedad lo tendriamos que
    pasar de tipo string sino teriamos un error de tipado de datos
    EJ : "HOLA" + TipoSociedad.SA.toString()
 */