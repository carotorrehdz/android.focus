package com.android.focus.model;

import java.util.Date;
import java.util.List;

/**
 * Encuesta class.
 */

public class Encuesta {

    private int id;
    private String nombre;
    private Date fechaInicio;
    private Date fechaFin;
    private boolean contestada;
    private List<Pregunta> preguntas;

    // region Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public boolean isContestada() {
        return contestada;
    }

    public void setContestada(boolean contestada) {
        this.contestada = contestada;
    }

    public List<Pregunta> getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(List<Pregunta> preguntas) {
        this.preguntas = preguntas;
    }
    // endregion

    // region Utility methods
    public static List<Encuesta> getUserEncuestas(int panelId) {
        return Panel.getUserPaneles().get(panelId).getEncuestas();
    }
    // endregion
}
