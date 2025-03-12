package com.ea.backend.entities;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Administrador implements Serializable{
    @Serial
    private static final long serialVersionUID = 1L;

    private int id;
    private String nome;
    private String email;
    private String senhaHash;
    private Timestamp criadoEm;

    private List<Reserva> reservasAdministrador = new ArrayList<Reserva>();
    private List<EspacoAcademico> espacosAcademicosAdministrador = new ArrayList<>();

    public Administrador() {
    }

    public Administrador(int id, String nome, String email, String senhaHash, Timestamp criadoEm) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senhaHash = senhaHash;
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

    public String getSenhaHash() {
        return senhaHash;
    }

    public void setSenhaHash(String senhaHash) {
        this.senhaHash = senhaHash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Timestamp getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(Timestamp criadoEm) {
        this.criadoEm = criadoEm;
    }

    public List<Reserva> getReservas() {
        return reservasAdministrador;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservasAdministrador = reservas;
    }

    public List<EspacoAcademico> getEspacosAcademicosAdministrador() {
        return espacosAcademicosAdministrador;
    }

    public void setEspacosAcademicosAdministrador(List<EspacoAcademico> espacosAcademicosAdministrador) {
        this.espacosAcademicosAdministrador = espacosAcademicosAdministrador;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Administrador that = (Administrador) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
