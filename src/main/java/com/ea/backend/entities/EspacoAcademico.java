package com.ea.backend.entities;

import reactor.core.publisher.Timed;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EspacoAcademico implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private int id;
    private String sigla;
    private String nome;
    private String descricao;
    private int capacidade;
    private boolean disponivel;
    private Timestamp criadoEm;

    private Administrador administradorEspaco;
    private List<Reserva> reservasEspaco = new ArrayList<>();

    public EspacoAcademico() {
    }

    public EspacoAcademico(int id, String sigla, String nome, String descricao, int capacidade, boolean disponivel, Timestamp criadoEm) {
        this.id = id;
        this.sigla = sigla;
        this.nome = nome;
        this.descricao = descricao;
        this.capacidade = capacidade;
        this.disponivel = disponivel;
        this.criadoEm = criadoEm;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public Timestamp getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(Timestamp criadoEm) {
        this.criadoEm = criadoEm;
    }

    public Administrador getAdministradorEspaco() {
        return administradorEspaco;
    }

    public void setAdministradorEspaco(Administrador administradorEspaco) {
        this.administradorEspaco = administradorEspaco;
    }

    public List<Reserva> getReservasEspaco() {
        return reservasEspaco;
    }

    public void setReservasEspaco(List<Reserva> reservasEspaco) {
        this.reservasEspaco = reservasEspaco;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        EspacoAcademico that = (EspacoAcademico) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
