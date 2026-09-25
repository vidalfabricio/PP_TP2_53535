package modelo;

import modelo.actividades.Actividad;

import java.awt.*;
import java.io.Serializable;
import java.time.LocalDate;

public class Inscripcion implements Serializable {
    private LocalDate fecha;
    private Actividad actividad;
    private Estudiante estudiante;

    public Inscripcion(Estudiante estudiante, Actividad actividad){
        this.fecha = LocalDate.now();
        this.estudiante = estudiante;
        this.actividad = actividad;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }
}
