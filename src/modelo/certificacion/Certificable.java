package modelo.certificacion;

import modelo.Estudiante;

public interface Certificable {
    //atributos por defecto publicc static y final
    String ENTIDAD_EMISORA = "UTN-FRM";

    String generarCertificado(Estudiante estudiante);

}
