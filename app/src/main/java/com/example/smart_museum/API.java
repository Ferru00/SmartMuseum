package com.example.smart_museum;

public class API {

    //URLs
    static private String url_login = "http://192.168.178.69/App/Login.php";
    static private String url_signup = "http://192.168.178.69/App/Signup.php";
    static private String url_lista_musei = "http://192.168.178.69/App/Lista_musei.php";
    static private String url_checkLogin= "http://192.168.178.69/App/check_login.php";
    static private String url_InfoOpera= "http://192.168.178.69/App/InfoOpera.php";
    static private String url_immagini = "http://192.168.178.69/App/Immagini/";


    static private String museoSelez = "";

    public static String getUrl_lista_musei() { return url_lista_musei; }
    public static String getUrl_login() { return url_login; }
    public static String getUrl_signup() { return url_signup; }



    static public String nome;
    static public String email;
    static public String cookie;

    public static String getEmail() { return email; }

    public static void setEmail(String email) { API.email = email; }

    public static String getNome() { return nome; }

    public static void setNome(String nome) { API.nome = nome; }

    public static String getCookie() { return cookie; }

    public static void setCookie(String cookie) { API.cookie = cookie; }

    public static String getUrl_checkLogin() { return url_checkLogin; }

    public static String getUrl_InfoOpera() { return url_InfoOpera; }

    public static void setUrl_InfoOpera(String url_InfoOpera) { API.url_InfoOpera = url_InfoOpera; }

    public static String getMuseoSelez() { return museoSelez; }

    public static void setMuseoSelez(String museoSelez) { API.museoSelez = museoSelez; }

    public static String getUrl_immagini() { return url_immagini; }

    public static void setUrl_immagini(String url_immagini) { API.url_immagini = url_immagini; }

}