package com.ea.backend.entities;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Professor implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private int id;
    private String nome;
    private String email;
    private String senhaHash;
    private String escola;
    private Timestamp criadoEm;

    private List<Reserva> reservasProfessor = new ArrayList<Reserva>();

    public Professor() {
    }

    public Professor(int id, String nome, String email, String senhaHash, String escola, Timestamp criadoEm) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senhaHash = senhaHash;
        this.escola = escola;
        this.criadoEm = criadoEm;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenhaHash() {
        return senhaHash;
    }

    public void setSenhaHash(String senhaHash) {
        this.senhaHash = senhaHash;
    }

    public String getEscola() {
        return escola;
    }

    public void setEscola(String escola) {
        this.escola = escola;
    }

    public Timestamp getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(Timestamp criadoEm) {
        this.criadoEm = criadoEm;
    }

    public List<Reserva> getReservasProfessor() {
        return reservasProfessor;
    }

    public void setReservasProfessor(List<Reserva> reservasProfessor) {
        this.reservasProfessor = reservasProfessor;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Professor professor = (Professor) o;
        return id == professor.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
