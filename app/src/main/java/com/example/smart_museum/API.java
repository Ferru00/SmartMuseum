package com.example.smart_museum;

public class API {

    //URLs
    static private String url_login = "http://192.168.178.69/App/Login.php";
    static private String url_signup = "http://192.168.178.69/App/Signup.php";

    public static String getUrl_login() {
        return url_login;
    }
    public static String getUrl_signup() {
        return url_signup;
    }

    //Nome e cookie

    static public String nome;
    static public String email;
    static public String cookie;

    public static String getEmail() { return email; }

    public static void setEmail(String email) { API.email = email; }

    public static String getNome() { return nome; }

    public static void setNome(String nome) { API.nome = nome; }

    public static String getCookie() { return cookie; }

    public static void setCookie(String cookie) { API.cookie = cookie; }


}