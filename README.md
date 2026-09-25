# PP_TP2_53535
# Sistema de Gestión de Eventos Universitarios
 
Proyecto en Java que modela un sistema de eventos universitarios, sus salas, actividades (charlas, talleres y cursos), inscripciones de estudiantes y emisión de certificados.
 
## Descripción general
 
El sistema permite:
 
- Registrar estudiantes.
- Construir eventos universitarios, asignarles una sala y crear sus actividades (**Charla**, **Taller** o **Curso**).
- Inscribir estudiantes en actividades, controlando el cupo máximo.
- Persistir y recuperar eventos desde archivo mediante serialización de objetos.
- Emitir certificados de asistencia para las actividades certificables (Talleres y Cursos; las Charlas no certifican).
- Filtrar las actividades de un evento por tipo concreto, y calcular el costo de materiales por tipo o en conjunto.
## Estructura de paquetes
 
```
src/
├── App.java                              (clase ejecutable, sin paquete)
├── excepciones/
│   ├── CupoExcedidoException.java
├── certificacion/
│   └── Certificable.java                 (interfaz)
└── modelo/
    ├── EventoUniversitario.java
    ├── Sala.java
    ├── Estudiante.java
    ├── Inscripcion.java
    └── actividades/
        ├── Actividad.java                (clase abstracta)
        ├── Charla.java
        ├── Taller.java
        └── Curso.java
```
 
## Estructura de clases
 
| Clase | Rol |
|---|---|
| `EventoUniversitario` | Representa un evento. Crea y gestiona sus actividades, tiene una sala asignada, y permite persistir/recuperar el evento y filtrar sus actividades. |
| `Sala` | Representa el lugar físico donde se realiza un evento. |
| `Actividad` | Clase **abstracta** con lo común a toda actividad (id, título, cupo, inscriptos). |
| `Charla` | Subtipo de `Actividad`. Sin costo de materiales. No es certificable. |
| `Taller` | Subtipo de `Actividad`. Costo de materiales según si requiere notebook. Certificable. |
| `Curso` | Subtipo de `Actividad`. Costo de materiales según su nivel. Certificable. |
| `Estudiante` | Representa a la persona que se inscribe en actividades. |
| `Inscripcion` | Representa la inscripción de un estudiante a una actividad puntual (fecha, estado). |
| `Certificable` | Interfaz que declara la capacidad de emitir certificados. Implementada por `Taller` y `Curso`, no por `Charla`. |
| `CupoExcedidoException` | Excepción chequeada, lanzada al intentar inscribir por encima del cupo máximo. |
| `CupoMinimoNoAlcanzadoException` | Excepción chequeada, lanzada cuando una actividad no llega al cupo mínimo. |
| `DatosInvalidosException` | Excepción chequeada, lanzada ante datos inválidos al crear una actividad. |
| `App` | Clase ejecutable con el `main`, arma el escenario de prueba completo. |
 
## Relaciones entre clases
 
- **Composición** (`EventoUniversitario` ◆— `Actividad`): el evento crea y es dueño de sus actividades.
- **Agregación** (`EventoUniversitario` ◇— `Sala`): el evento usa una sala que existe de forma independiente.
- **Herencia** (`Actividad` ← `Charla`, `Taller`, `Curso`): cada subtipo implementa su propia lógica de costo e identificación.
- **Clase de asociación** (`Inscripcion`): representa la relación muchos a muchos entre `Actividad` y `Estudiante`, agregando datos propios (fecha, estado).
- **Interfaz** (`Taller`, `Curso` `implements Certificable`): capacidad de emitir certificados, independiente de la jerarquía de herencia. `Charla` no la implementa.
## Polimorfismo
 
`Actividad` declara los métodos abstractos `calcularCostoMateriales()` y `getTipo()`, que cada subtipo concreto implementa a su manera. El método `mostrarIdentificacion()`, definido como `final` en `Actividad`, llama internamente a esos métodos abstractos, por lo que un mismo recorrido de código funciona para cualquier tipo de actividad:
 
```java
for (Actividad a : actividades) {
    a.mostrarIdentificacion();
}
```
 
La emisión de certificados usa el mismo principio, pero a través de una interfaz:
 
```java
if (actividad instanceof Certificable) {
    Certificable certificable = (Certificable) actividad;
    String certificado = certificable.generarCertificado(estudiante);
}
```
 
## Manejo de excepciones
 
- `inscribir(Estudiante)` lanza `CupoExcedidoException` (chequeada) cuando se supera el cupo máximo, mediante `throw` y `throws`.
- El manejo de estas excepciones se realiza en `App`, con bloques `try-catch-finally`, siguiendo un orden de `catch` desde el más específico al más general.
- Las excepciones vinculadas a la persistencia (`FileNotFoundException`, `NotSerializableException`, `InvalidClassException`, `ClassNotFoundException`, `IOException`) se manejan de forma granular, cada una con su propio mensaje.
## Persistencia (serialización)
 
`EventoUniversitario` y todas las clases de su grafo de objetos (`Sala`, `Actividad` y sus subtipos, `Inscripcion`, `Estudiante`) implementan `Serializable`, con `serialVersionUID` propio.
 
```java
public boolean persistirEvento() throws IOException {
    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(id + ".dat"))) {
        oos.writeObject(this);
        return true;
    }
}
 
public static EventoUniversitario recuperarEvento(String id) throws IOException, ClassNotFoundException {
    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(id + ".dat"))) {
        return (EventoUniversitario) ois.readObject();
    }
}
```
 
## Genéricos: métodos parametrizados y wildcards
 
`EventoUniversitario` incorpora dos métodos para operar sobre sus actividades de forma flexible y con tipado seguro:
 
```java
// Método genérico acotado: filtra y devuelve una lista correctamente tipada
public <T extends Actividad> List<T> filtrarActividadesPorTipo(Class<T> tipo) { ... }
 
// Wildcard acotado: acepta cualquier lista de Actividad o subtipos, solo para lectura
public double calcularCostoMateriales(List<? extends Actividad> actividades) { ... }
```
 
Uso:
 
```java
List<Taller> talleres = expo.filtrarActividadesPorTipo(Taller.class);
double costoTalleres = expo.calcularCostoMateriales(talleres);
double costoTotal = expo.calcularCostoMateriales(expo.getActividades());
```
 
## Salida por consola
<img width="452" height="700" alt="image" src="https://github.com/user-attachments/assets/96d7d4ba-ac6a-46f7-bdd6-85d2ed09328b" />
<img width="1213" height="707" alt="image" src="https://github.com/user-attachments/assets/3fc73006-b95b-4c88-babd-99d108f1621c" />
<img width="279" height="169" alt="image" src="https://github.com/user-attachments/assets/76964c2b-cd67-4511-a1e9-3de7eaef5762" />


## Cómo ejecutar el proyecto
 
1. Cloná el repositorio:
```bash
   git clone (https://github.com/vidalfabricio/PP_TP2_53535)
```
2. Abrilo con IntelliJ IDEA (u otro IDE compatible con Java).
3. Ejecutá la clase `App` (botón ▶️ o `Shift + F10` en IntelliJ).
## Requisitos
 
- JDK 17 o superior.
## Autor
 
_Vidal Fabricio 2k13._
