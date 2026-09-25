package modelo.actividades;

import excepciones.CupoExcedidoException;
import modelo.Estudiante;
import modelo.Inscripcion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Actividad implements Serializable {
    private int id;
    private String titulo;
    private int cupoMaximo;
    public final static int CupoMinimo;
    private List<Inscripcion> inscripciones = new ArrayList<>();

    public Actividad(int id, String titulo, int cupoMaximo){
        this.id = id;
        this.titulo = titulo;
        this.cupoMaximo = cupoMaximo;

    }
    static {
        CupoMinimo = 2;
    }
    public Inscripcion inscribir(Estudiante estudiante) throws CupoExcedidoException {
        if(inscripciones.size() >= cupoMaximo){
            throw new CupoExcedidoException("[ERROR] No hay cupo disponible en: " + titulo);
        }else{
            Inscripcion nuevaInscripcion = new Inscripcion(estudiante, this);
            inscripciones.add(nuevaInscripcion);
            return nuevaInscripcion;
        }
    }
    public void mostrarInscripciones(){
        System.out.println("------Incripciones de la actividad " + titulo + "------");
        for (Inscripcion i: inscripciones){
            System.out.println("Estudiante: " + i.getEstudiante().getNombre());
        }
    }
    public final void  mostrarIdentificacion(){
        System.out.println("------DATOS DE LA ACTIVIDAD " + titulo + "------");
        System.out.println(" Tipo: [" + getTipo() + "]" + "\n" +
                            "Id: " + id + "\n" +
                            "Costo de materiales: $" + calcularCostoMateriales() + "\n" +
                            "Cupo maximo: " + cupoMaximo );
    }
    public abstract double calcularCostoMateriales(); //Los linkeo con charla y taller
    public abstract String getTipo();

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public List<Inscripcion> getInscripciones() {
        return inscripciones;
    }

    public int getCupoMaximo() {
        return cupoMaximo;
    }
}