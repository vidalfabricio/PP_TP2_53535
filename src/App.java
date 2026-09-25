import excepciones.CupoExcedidoException;
import modelo.actividades.Actividad;
import modelo.Estudiante;
import modelo.Sala;
import modelo.EventoUniversitario;
import modelo.actividades.Charla;
import modelo.actividades.Curso;
import modelo.actividades.Taller;
import modelo.certificacion.Certificable;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class App {
    public static void main(String[] args) {
        List<Estudiante> estudiantes = new ArrayList<>(); //Creo lista de estudiantes
        Estudiante juan =new Estudiante("EVV", "Juan");
        Estudiante maria =  new Estudiante("EVB", "Maria");
        Estudiante fabricio = new Estudiante("FRT", "Fabricio");
        Estudiante ani = (new Estudiante("GBH", "Ani"));
        Estudiante carla = new Estudiante("ERT", "Carla");
        //Añado los estudiantes a la lista
        estudiantes.add(juan);
        estudiantes.add(maria);
        estudiantes.add(fabricio);
        estudiantes.add(ani);
        estudiantes.add(carla);
        //Creacion de eventos
        EventoUniversitario expo = new EventoUniversitario("ERST", "Inteligencia Artificial", 2300.0, false);
        EventoUniversitario representacion = new EventoUniversitario("JMD", "Representacion Grafica", 0.0, true);
        //Creacion de salas
        Sala auditorio = new Sala(120, "Auditorio");
        Sala zoom = new Sala(200, "Zoom");
        //Asigno Salas a los eventos
        expo.AsignarSala(auditorio);
        representacion.AsignarSala(zoom);
        //Creacion de actividades
        //Creo actividades para expo
        expo.crearActividad(1, "Programacion", 20, "modelo.actividades.Taller", null, true, 0 );
        expo.crearActividad(2, "Exposicion IA", 40, "modelo.actividades.Charla", "Ig. Lopez", false, 0);
        expo.crearActividad(3, "ChatGPT", 10, "modelo.actividades.Curso", null, false, 2);
        //Creo actividades para representacion
        representacion.crearActividad(1, "Inicializacion A.CAD", 10, "modelo.actividades.Charla", "Ig. Matus", false, 0);
        representacion.crearActividad(2, "AutoCad", 3, "modelo.actividades.Taller", null, true, 0 );
        representacion.crearActividad(3, "Perspectiva", 2, "modelo.actividades.Curso", null, false, 1);

        //Las creo
        Actividad programacion = expo.getActividades().get(0);
        Actividad exposicion = expo.getActividades().get(1);
        Actividad chatgpt = expo.getActividades().get(2);
        Actividad inicializacion = representacion.getActividades().get(0);
        Actividad autocad = representacion.getActividades().get(1);
        Actividad perspectiva = representacion.getActividades().get(2);

        //Inscribo estudiantes
        try {
            programacion.inscribir(juan);
            exposicion.inscribir(maria);
            inicializacion.inscribir(fabricio);
            autocad.inscribir(ani);
            autocad.inscribir(fabricio);
            chatgpt.inscribir(carla);
            chatgpt.inscribir(ani);
            chatgpt.inscribir(fabricio);
            perspectiva.inscribir(carla);
            perspectiva.inscribir(ani);
            perspectiva.inscribir(fabricio);
        } catch (CupoExcedidoException e){
            System.out.println(e.getMessage());
        }
        //Muestro datos de los eventos
        expo.mostrar_Datos();
        representacion.mostrar_Datos();
        //Muestro cantidad de eventos
        EventoUniversitario.Contador();

        //Persistir
        try {
            expo.persistir();
            EventoUniversitario UTN = EventoUniversitario.deserializar("UTN");
        }catch (FileNotFoundException e){
            System.out.println("[ERROR01] No se encuetra o puede acceder al archivo: " + e.getMessage());
        }catch (ClassNotFoundException e){
            System.out.println("[ERROR02] Clase no coincidente: " + e.getMessage());
        }catch (IOException e){
            System.out.println("[ERROR03] Error entrada/salida: " + e.getMessage());
        }
        List <String> certicadoAlumno = new ArrayList<>();

        //Certificados
        for (Actividad actividad : expo.getActividades()){
            if (actividad instanceof Certificable){
                Certificable certificable = (Certificable) actividad;
                    for (var inscripcion : actividad.getInscripciones()){
                        String certificado = certificable.generarCertificado(inscripcion.getEstudiante());
                        certicadoAlumno.add(certificado);

                    }
            }
        }
        //Mostrar certificados
        System.out.println("-----Certificados emitidos-----");
        for (String c : certicadoAlumno){
            System.out.println(c);
            System.out.println("--------------------------------");

        }
        //Se crean las listas filtradas
        //EXPO
        List<Taller> talleres = expo.filtrarActividadesporTipo(Taller.class);
        List<Charla> charlas = expo.filtrarActividadesporTipo(Charla.class);
        List<Curso> cursos = expo.filtrarActividadesporTipo(Curso.class);
        //Cantidad de actividades por tipo
        System.out.println("----CANTIDAD DE ACTIVIDADES DEL EVENTO: " + expo.getTitulo() + "-----" + "\n" +
                "Charlas: " + charlas.size() + "\n" +
                "Talleres: " + talleres.size() + "\n" +
                "Cursos: " + cursos.size());
        System.out.println("----COSTO DE ACTIVIDADES DEL EVENTO: " + expo.getTitulo() + "-----" + "\n" +
                "Charlas: " + expo.calcularCostoMateriales(charlas) + "\n" +
                "Talleres: " + expo.calcularCostoMateriales(talleres) + "\n" +
                "Cursos: " + expo.calcularCostoMateriales(cursos) + "\n" +
                "Costo total de las actividades: " + expo.calcularCostoMateriales(expo.getActividades()));
        //REPRESENTACION
        List<Taller> talleresR = representacion.filtrarActividadesporTipo(Taller.class);
        List<Curso> cursosR = representacion.filtrarActividadesporTipo(Curso.class);
        List<Charla> charlasR = representacion.filtrarActividadesporTipo(Charla.class);
        //Cantidad de actividades por tipo

        //Se calcula el costo de todas las actividades de un evento
        System.out.println("----CANTIDAD DE ACTIVIDADES DEL EVENTO: " + representacion.getTitulo() + "-----" + "\n" +
                "Charlas: " + charlasR.size() + "\n" +
                "Talleres: " + talleresR.size() + "\n" +
                "Cursos: " + cursosR.size());
        System.out.println("----COSTO DE ACTIVIDADES DEL EVENTO: " + representacion.getTitulo() + "-----" + "\n" +
                "Charlas: " + representacion.calcularCostoMateriales(charlas) + "\n" +
                "Talleres: " + representacion.calcularCostoMateriales(talleres) + "\n" +
                "Cursos: " + representacion.calcularCostoMateriales(cursos) + "\n" +
                "Costo total de las actividades: " + representacion.calcularCostoMateriales(representacion.getActividades()));

        System.out.println("Verficacion de tipado de listas");
        for (Taller r: talleres){
            System.out.println("Taller: " + r.getTitulo());
        }
        for (Taller r: talleresR){
            System.out.println("Taller: " + r.getTitulo());
        }
        for (Charla c: charlas){
            System.out.println("Charla: " + c.getTitulo());
        }
        for (Charla c: charlasR){
            System.out.println("Charla: " + c.getTitulo());
        }
        for (Curso c: cursos){
            System.out.println("Curso: " + c.getTitulo());
        }
        for (Curso c: cursosR){
            System.out.println("Curso: " + c.getTitulo());
        }







    }
}