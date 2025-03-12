package com.ea.backend.entities;

import org.hibernate.type.descriptor.jdbc.TimestampJdbcType;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Reserva implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private int id;
    private int professorId;
    private int espacoId;
    private LocalDate dataReserva;
    private LocalTime horaInicio;
    private LocalTime horaFim;
    private String status;
    private Timestamp criadoEm;
    private Timestamp atualizadoEm;

    private Administrador administradorReserva;
    private EspacoAcademico espacoAcademicoReserva;
    private Professor professorReserva;
    private ConfirmacaoUso confirmacaoReserva;
    private List<HistoricoReserva> historicoReservas = new ArrayList<>();

    public Reserva() {
    }

    public Reserva(int id, int professorId, int espacoId, LocalDate dataReserva, LocalTime horaInicio, LocalTime horaFim, String status, Timestamp criadoEm, Timestamp atualizadoEm) {
        this.id = id;
        this.professorId = professorId;
        this.espacoId = espacoId;
        this.dataReserva = dataReserva;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.status = status;
        this.criadoEm = criadoEm;
        this.atualizadoEm = atualizadoEm;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProfessorId() {
        return professorId;
    }

    public void setProfessorId(int professorId) {
        this.professorId = professorId;
    }

    public int getEspacoId() {
        return espacoId;
    }

    public void setEspacoId(int espacoId) {
        this.espacoId = espacoId;
    }

    public LocalDate getDataReserva() {
        return dataReserva;
    }

    public void setDataReserva(LocalDate dataReserva) {
        this.dataReserva = dataReserva;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(LocalTime horaFim) {
        this.horaFim = horaFim;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(Timestamp criadoEm) {
        this.criadoEm = criadoEm;
    }

    public Timestamp getAtualizadoEm() {
        return atualizadoEm;
    }

    public void setAtualizadoEm(Timestamp atualizadoEm) {
        this.atualizadoEm = atualizadoEm;
    }

    public Administrador getAdministradorReserva() {
        return administradorReserva;
    }

    public void setAdministradorReserva(Administrador administradorReserva) {
        this.administradorReserva = administradorReserva;
    }

    public EspacoAcademico getEspacoAcademicoReserva() {
        return espacoAcademicoReserva;
    }

    public void setEspacoAcademicoReserva(EspacoAcademico espacoAcademicoReserva) {
        this.espacoAcademicoReserva = espacoAcademicoReserva;
    }

    public Professor getProfessorReserva() {
        return professorReserva;
    }

    public void setProfessorReserva(Professor professorReserva) {
        this.professorReserva = professorReserva;
    }

    public ConfirmacaoUso getConfirmacaoReserva() {
        return confirmacaoReserva;
    }

    public void setConfirmacaoReserva(ConfirmacaoUso confirmacaoReserva) {
        this.confirmacaoReserva = confirmacaoReserva;
    }

    public List<HistoricoReserva> getHistoricoReservas() {
        return historicoReservas;
    }

    public void setHistoricoReservas(List<HistoricoReserva> historicoReservas) {
        this.historicoReservas = historicoReservas;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Reserva reserva = (Reserva) o;
        return id == reserva.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
