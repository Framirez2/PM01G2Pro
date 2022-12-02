package com.example.configuracion.config;

public class API {
    public static final String ipAddress = "192.168.0.10";

    public static String webApi = "/test";

    //Rests
    private static final String upldFile = "/uploadFile.php";
    /*private static final String FindContact = "/listacontactosingle.php?nombre=";
    private static final String UpdateContact = "/actualizarcontacto.php";
    private static final String CreateContact = "/crearContacto.php";
    private static final String DeleteContact = "/borrarContacto.php?id=";*/

    //Llamados a la API
    public static final String apiGetList =  "http://"+ ipAddress + webApi + upldFile;
}
