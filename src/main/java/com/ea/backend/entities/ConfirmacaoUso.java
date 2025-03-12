package com.ea.backend.entities;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

public class ConfirmacaoUso implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private int id;
    private int reservaId;
    private boolean confirmado;
    private Timestamp dataConfirmacao;

    private Reserva reservaConfirmacao;
    private Professor professorConfirmacao;

    public ConfirmacaoUso() {
    }

    public ConfirmacaoUso(int id, int reservaId, boolean confirmado, Timestamp dataConfirmacao) {
        this.id = id;
        this.reservaId = reservaId;
        this.confirmado = confirmado;
        this.dataConfirmacao = dataConfirmacao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReservaId() {
        return reservaId;
    }

    public void setReservaId(int reservaId) {
        this.reservaId = reservaId;
    }

    public boolean isConfirmado() {
        return confirmado;
    }

    public void setConfirmado(boolean confirmado) {
        this.confirmado = confirmado;
    }

    public Timestamp getDataConfirmacao() {
        return dataConfirmacao;
    }

    public void setDataConfirmacao(Timestamp dataConfirmacao) {
        this.dataConfirmacao = dataConfirmacao;
    }

    public Reserva getReservaConfirmacao() {
        return reservaConfirmacao;
    }

    public void setReservaConfirmacao(Reserva reservaConfirmacao) {
        this.reservaConfirmacao = reservaConfirmacao;
    }

    public Professor getProfessorConfirmacao() {
        return professorConfirmacao;
    }

    public void setProfessorConfirmacao(Professor professorConfirmacao) {
        this.professorConfirmacao = professorConfirmacao;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ConfirmacaoUso that = (ConfirmacaoUso) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
