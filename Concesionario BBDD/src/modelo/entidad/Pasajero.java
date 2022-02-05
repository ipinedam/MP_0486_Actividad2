package modelo.entidad;

/**
 * Clase para implementar un objeto <i>Pasajero</i>.
 * 
 * @author Ignacio Pineda Martín
 *
 */
public class Pasajero {

    /**
     * Propiedades de la clase <i>Pasajero</i>.
     */
    private int id;
    private String nombre;
    private int edad;
    private double peso;
    private int idCoche;

    /**
     * Constructor por defecto de la clase.
     */
    public Pasajero() {
    }

    /**
     * Constructor de la clase parametrizado por sus propiedades.
     * 
     * @param nombre Nombre del pasajero
     * @param edad   Edad del pasajero
     * @param peso   Peso del pasajero
     */
    public Pasajero(String nombre, int edad, double peso) {
        super();
        this.nombre = nombre;
        this.edad = edad;
        this.peso = peso;
    }

    /**
     * "Getter" para la propiedad <i>id</i>
     * 
     * @return El id del pasajero
     */
    public int getId() {
        return id;
    }

    /**
     * "Setter" para la propiedad <i>id</i>
     * 
     * @param id El id a utilizar (sólo para consultas). Este atributo será asignado
     *           automáticamente por la BB.DD.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * "Getter" para la propiedad <i>nombre</i>
     * 
     * @return El nombre del pasajero
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * "Setter" para la propiedad <i>nombre</i>
     * 
     * @param nombre El nombre del pasajero
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * "Getter" para la propiedad <i>edad</i>
     * 
     * @return La edad del pasajero
     */
    public int getEdad() {
        return edad;
    }

    /**
     * "Setter" para la propiedad <i>edad</i>
     * 
     * @param edad La edad del pasajero
     */
    public void setEdad(int edad) {
        this.edad = edad;
    }

    /**
     * "Getter" para la propiedad <i>peso</i>
     * 
     * @return El peso del pasajero
     */
    public double getPeso() {
        return peso;
    }

    /**
     * "Setter" para la propiedad <i>peso</i>
     * 
     * @param peso El peso del pasajero
     */
    public void setPeso(double peso) {
        this.peso = peso;
    }

    /**
     * "Getter" para la propiedad <i>idCoche</i>
     * 
     * @return El id del coche al que se pertenece como pasajero. Será "NULL" cuando
     *         el pasajero no esté asignado a ningún coche.
     */
    public int getIdCoche() {
        return idCoche;
    }

    /**
     * "Setter" para la propiedad <i>idCoche</i>
     * 
     * @param idCoche El id del coche al que se pertenece como pasajero.
     */
    public void setIdCoche(int idCoche) {
        this.idCoche = idCoche;
    }

    /**
     * Salida formateada con todas las propiedades del pasajero.
     * 
     * @return cadena formateada con todas las propiedades del pasajero
     */
    @Override
    public String toString() {
        return "ID: " + id + " Nombre: " + nombre + " Edad: " + edad + " Peso: " + peso;
    }

    /**
     * Salida en formato abreviado, con todas las propiedades del pasajero.
     * 
     * @return cadena formateada y abreviada con todas las propiedades del pasajero
     */
    public String toBriefString() {
        return "(ID: " + id + ") " + nombre + " - " + edad + " años, " + peso + " kg.";
    }
}
