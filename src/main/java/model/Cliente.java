package model;

import java.util.Objects;

public class Cliente {
    private int id;
    private String nome;
    private String tipo;
    private String cpf;
    private String passaporte;
    private String telefone;
    private String email;


    public Cliente(String nome, String email, String documento, String tipo) {
        this.nome = nome;
        this.email = email;
        this.tipo = tipo;

        if ("nacional".equalsIgnoreCase(tipo)) {
            this.cpf = documento;
            this.passaporte = "";
        } else {
            this.cpf = "";
            this.passaporte = documento;
        }
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPassaporte() {
        return passaporte;
    }

    public void setPassaporte(String passaporte) {
        this.passaporte = passaporte;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // toString
    @Override
    public String toString() {
        return nome; // ou return nome + " - " + email;
    }

    // equals e hashCode
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Cliente cliente = (Cliente) obj;
        return id == cliente.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
