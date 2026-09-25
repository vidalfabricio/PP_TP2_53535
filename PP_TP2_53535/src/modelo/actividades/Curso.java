package modelo.actividades;

import modelo.Estudiante;
import modelo.certificacion.Certificable;

import java.util.StringTokenizer;

public class Curso extends Actividad implements Certificable {
    private int nivel;

    public Curso(int id, String titulo, int cupo, int nivel){
        super(id,titulo, cupo);
        this.nivel = nivel;

    }
    @Override
    public double calcularCostoMateriales(){
        return 0.0;
    }
    @Override
    public String getTipo(){
        return "Curso";
    }
    @Override
    public String generarCertificado(Estudiante estudiante){
        return "La institucion: " + ENTIDAD_EMISORA + "\n" + "certifica que el estudiante: " + estudiante.getNombre() + "\n" + "Legajo: " + estudiante.getLegajo() + "\n" + "Realizo y aprobo el curso de la actividad: " + getTitulo();
    }
}
