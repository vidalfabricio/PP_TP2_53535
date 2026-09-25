package modelo.actividades;

public class Charla extends Actividad{
    private String disernante;

    public Charla(int id, String titulo, int cupoMaximo, String disernante){
        super(id, titulo, cupoMaximo);
        this.disernante = disernante;
    }
    @Override
    public double calcularCostoMateriales(){

        return 0.0;   //Charlas son gratis
    }
    @Override
    public String getTipo(){
        return "Charla";
    }

    public String getDisernante() {
        return disernante;
    }
}
