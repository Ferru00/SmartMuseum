package com.example.smart_museum;

public class Musei {
    private String nome,indirizzo,id;

    public Musei(String nome, String indirizzo, String id)
    {
        this.nome = nome;
        this.indirizzo = indirizzo;
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
