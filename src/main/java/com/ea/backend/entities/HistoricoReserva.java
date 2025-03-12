package com.ea.backend.entities;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

public class HistoricoReserva implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private int id;
    private int reservaId;
    private int alteradoPor;
    private String statusAnterior;
    private String statusNovo;
    private Timestamp dataAlteracao;

    private Reserva reservaHistorico;

    public HistoricoReserva() {
    }

    public HistoricoReserva(int id, int reservaId, int alteradoPor, String statusAnterior, String statusNovo, Timestamp dataAlteracao) {
        this.id = id;
        this.reservaId = reservaId;
        this.alteradoPor = alteradoPor;
        this.statusAnterior = statusAnterior;
        this.statusNovo = statusNovo;
        this.dataAlteracao = dataAlteracao;
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

    public int getAlteradoPor() {
        return alteradoPor;
    }

    public void setAlteradoPor(int alteradoPor) {
        this.alteradoPor = alteradoPor;
    }

    public String getStatusAnterior() {
        return statusAnterior;
    }

    public void setStatusAnterior(String statusAnterior) {
        this.statusAnterior = statusAnterior;
    }

    public String getStatusNovo() {
        return statusNovo;
    }

    public void setStatusNovo(String statusNovo) {
        this.statusNovo = statusNovo;
    }

    public Timestamp getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(Timestamp dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public Reserva getReservaHistorico() {
        return reservaHistorico;
    }

    public void setReservaHistorico(Reserva reservaHistorico) {
        this.reservaHistorico = reservaHistorico;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        HistoricoReserva that = (HistoricoReserva) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
