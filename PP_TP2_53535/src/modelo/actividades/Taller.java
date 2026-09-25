package modelo.actividades;

import modelo.Estudiante;
import modelo.certificacion.Certificable;

public class Taller extends Actividad implements Certificable {
    private boolean requiereNotebook;

    public Taller(int id, String titulo, int cupoMaximo, boolean requiereNotebook){
        super(id, titulo, cupoMaximo);
        this.requiereNotebook = requiereNotebook;
    }
    @Override
    public double calcularCostoMateriales(){

        return requiereNotebook ? 5000.0 : 2000.0;   //Depende si tiene o no
    }
    @Override
    public String getTipo(){
        return "Taller";
    }
    @Override
    public String generarCertificado(Estudiante estudiante){
        return "La institucion: " + ENTIDAD_EMISORA + "\n" + "Certifica al estudiante: " + estudiante.getNombre() + "\n" + "Legajo: " + estudiante.getLegajo() + "\n" + "Realizo y aprobo el taller de la actividad: " + getTitulo() ;
    }
}
