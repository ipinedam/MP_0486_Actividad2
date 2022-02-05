package modelo.entidad;

/**
 * Clase para implementar un objeto <i>Coche</i>.
 * 
 * @author Ignacio Pineda Martín
 *
 */
public class Coche {
    
    private int id;
    private String matricula;
    private String marca;
    private String modelo;
    private String color;
    
    /**
     * Constructor por defecto de la clase.
     */
    public Coche() { 
    }

    /**
     * Constructor de la clase parametrizado por sus propiedades.
     * 
     * @param matricula Matrícula del coche.
     * @param marca     Marca del coche.
     * @param modelo    Modelo del coche.
     * @param color     Color del coche.
     */
    public Coche(String matricula, String marca, String modelo, String color) {
        super();
        this.matricula = matricula;
        this.marca = marca;
        this.modelo = modelo;
        this.color = color;
    }

    /**
     * "Getter" para la propiedad <i>id</i> del coche
     * 
     * @return el identificador (<i>id</i>) del coche
     */
    public int getId() {
        return id;
    }
  
    /**
     * "Getter" para la propiedad <i>id</i> del coche.
     * 
     * @param id el ID del coche
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * "Getter" para la propiedad <i>matricula</i> del coche
     * 
     * @return la matricula del coche
     */
    public String getMatricula() {
        return matricula;
    }

    /**
     * "Setter" para la propiedad <i>matricula</i> del coche
     * 
     * @param matricula la matrícula a asignar al coche
     */
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    /**
     * "Getter" para la propiedad <i>marca</i>
     * 
     * @return la marca del coche
     */
    public String getMarca() {
        return marca;
    }

    /**
     * "Setter" para la propiedad <i>marca</i>
     * 
     * @param marca la marca a asignar al coche
     */
    public void setMarca(String marca) {
        this.marca = marca;
    }

    /**
     * "Getter" para la propiedad <i>modelo</i>
     * 
     * @return el modelo del coche
     */
    public String getModelo() {
        return modelo;
    }

    /**
     * "Setter" para la propiedad <i>modelo</i>
     * 
     * @param modelo el modelo a asignar al coche
     */
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    /**
     * "Getter" para la propiedad <i>color</i>
     * 
     * @return el color del coche
     */
    public String getColor() {
        return color;
    }

    /**
     * "Setter" para la propiedad <i>color</i>
     * 
     * @param color el color a asignar al coche
     */
    public void setColor(String color) {
        this.color = color;
    }
  
    /**
     * Salida formateada con todas las propiedades del coche.
     * 
     * @return cadena formateada con todas las propiedades del coche
     */
    @Override
    public String toString() {
        return "ID: " + id + " Matricula: " + matricula + " Marca: " + marca + " Modelo: " + modelo + " Color: " + color;
    }
    
    /**
     * Salida en formato abreviado, con todas las propiedades del coche.
     * 
     * @return cadena formateada y abreviada con todas las propiedades del coche
     */
    public String toBriefString() {
        return "(ID: " + id + ") " + marca + " " + modelo + " " + color + " - " + matricula;
    }
     
}
