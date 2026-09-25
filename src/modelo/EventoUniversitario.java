package modelo;
import modelo.actividades.Actividad;
import modelo.actividades.Charla;
import modelo.actividades.Curso;
import modelo.actividades.Taller;
import modelo.certificacion.Certificable;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EventoUniversitario implements Serializable {
    private final String id;
    private String titulo;
    private double costoBase;
    private boolean gratuito;
    private static int cantEvento;
    private List<Actividad> actividades = new ArrayList<>(); //Composicion con modelo.actividades.Actividad
    //Agregacion con sala
    private Sala sala;


    static {
        cantEvento = 0;
    }
    public EventoUniversitario(String id, String titulo, double costoBase, boolean gratuito){
        this.id = id;
        this.titulo = titulo;
        if (gratuito){
            this.costoBase = 0;
        } else {
            this.costoBase = costoBase;
        }
        this.gratuito = gratuito;
        cantEvento ++;
    }
    public EventoUniversitario(EventoUniversitario copia){
        this.id = copia.id;
        this.titulo = copia.titulo;
        this.costoBase = copia.costoBase;
        this.gratuito = copia.gratuito;
        cantEvento ++;
    }
    public void crearActividad(int id, String titulo, int cupo, String tipo, String disernante, boolean requiereNotebook, int nivel){
        Actividad nueva;
        switch (tipo){
            case "modelo.actividades.Curso":
                nueva = new Curso(id, titulo, cupo, nivel);
                break;
            case "modelo.actividades.Charla":
                nueva = new Charla(id, titulo, cupo, disernante);
                break;
            case "modelo.actividades.Taller":
                nueva = new Taller(id, titulo, cupo, requiereNotebook);
                break;
            default:
                System.out.println("Tipo de actividad no reconocida, ingrese una valida");
                return;
        }
        actividades.add(nueva);
    }
    public void mostrar_Datos(){
        System.out.println("      DATOS DEL EVENTO     ");
        System.out.println("El Id del evento es: " + id);
        System.out.println("El Titulo del evento es: " + titulo);
        System.out.println("El Costo base del evento es: " + costoBase);
        if (gratuito) {
            System.out.println("El evento es gratuito");
        }else {
            System.out.println("El evento es de pago");
        }
        System.out.println("Nombre de sala: " + (sala != null ? sala.getNombre(): "sala sin asignar"));
        System.out.println("cantidad de actividades: " + actividades.size());
        for (Actividad i: actividades){
            i.mostrarIdentificacion();
            i.mostrarInscripciones();
        }
        System.out.println("==========================");

    }
    public static void Contador(){
        if (cantEvento == 0){
            System.out.println("No hay eventos creados");
        } else{
            System.out.println("La cantidad de eventos creados es: " + cantEvento);
        }
    }

    public double calcularCostoEstimado(){
        if (gratuito){
            return 0.0;
        }
        double costoActividades = 0.0;
        for (Actividad a: actividades){
            costoActividades += a.calcularCostoMateriales();
        }
        return (costoBase + costoActividades)*1.21;
    }
    //Aplicando serializacion
    public void persistir() throws FileNotFoundException, IOException{
        FileOutputStream fos = new FileOutputStream("C:/Users/xxxx/Documents/Paradigma" + id + ".dat");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this);
        oos.close();
        fos.close();

    }
    public static EventoUniversitario deserializar(String id) throws ClassNotFoundException, IOException{
        FileInputStream fis = new FileInputStream("C:/Users/xxxx/Documents/Paradigma" + id + ".dat");
        ObjectInputStream ois = new ObjectInputStream(fis);
        EventoUniversitario eventoAleer = null;
        eventoAleer = (EventoUniversitario) ois.readObject();
        ois.close();
        fis.close();
    return eventoAleer;
    }


    //Metodos parametrizados y uso de wilcards

    public <T extends Actividad> List<T> filtrarActividadesporTipo (Class<T> tipo){
        List<T> resultado = new ArrayList<>();
        for (Actividad a: actividades){
            if (tipo.isInstance(a)){
                resultado.add(tipo.cast(a));
            }
        }
        return resultado;
    }
    public double calcularCostoMateriales (List<? extends Actividad> actividades){
        double total = 0.0;
        for (Actividad a: actividades){
            total += a.calcularCostoMateriales();
        }
        return total;
    }

    //Getter and Setters
    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        if(titulo.isEmpty()){
            System.out.println("Error titulo vacio");
        }else {
            this.titulo = titulo;
        }
    }

    public double getCostoBase() {
        return costoBase;
    }

    public void setCostoBase(double costoBase) {
        this.costoBase = costoBase;

    }

    public boolean isGratuito() {
        return gratuito;
    }

    public void setGratuito(boolean gratuito) {
        this.gratuito = gratuito;
    }

    public static int getCantEvento() {
        return cantEvento;
    }

    public void AsignarSala(Sala sala){

        this.sala = sala;
    }

    public List<Actividad> getActividades() {

        return actividades;
    }


    public Sala getSala() {

        return sala;
    }
}
