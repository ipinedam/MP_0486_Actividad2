package practica;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import java.util.List;
import java.util.Scanner;

import modelo.entidad.Coche;
import modelo.persistencia.DAOCocheMySql;
//import modelo.persistencia.DAOPersonaMySql;
import modelo.persistencia.interfaz.DAOCoche;

/**
 * <b>Actividad 2. Tarea de equipo (2 peronas). Gestión de un concesionario de coches (2)</b>
 * <br><br>
 * Siguiendo la instrucciones de la práctica, implementamos una aplicación para
 * la gestión del almacen de coches de un concesionario, gestionado en una
 * BB.DD.<br>
 * El menú que aparecerá será el siguiente:<br>
 * <br>
 * <i>1. Añadir nuevo coche</i><br>
 * <i>2. Borrar coche por ID</i><br>
 * <i>3. Consultar coche por ID</i><br>
 * <i>4. Modificar coche por ID</i><br>
 * <i>5. Listado de coches</i><br>
 * <i>6. Exportar coches a archivo</i><br>
 * <i>7. Gestión de pasajeros</i><br>
 * <i>8. Salir</i><br>
 * 
 * @author Ignacio Pineda Martín
 *
 */
public class Principal {
    /**
     * Constante para definir el nombre completo del fichero que almacena los coche
     * del concesionario en formato texto.
     */
    final static String ARCHIVO_COCHES_TXT = ".\\test\\coches.txt";

    /**
     * Abrimos un Scanner para captura de datos por pantalla.
     */
    private static Scanner lector = new Scanner(System.in);

    /**
     * Función principal de la clase.
     * @param args Sin uso.
     */
    public static void main(String[] args) {
        // Variables auxiliares.
        int opcion = 0;
        boolean ok = false;
        String texto = "";
        
        // Bucle principal de tratamiento.
        do {
            // Mostramos las opciones.
            System.out.println();
            System.out.println("       CONCESIONARIO - COCHES        ");
            System.out.println("-------------------------------------");
            System.out.println("1. Añadir nuevo coche");
            System.out.println("2. Borrar coche por ID");
            System.out.println("3. Consultar coche por ID");
            System.out.println("4. Modificar coche por ID");
            System.out.println("5. Listado de coches");
            System.out.println("6. Exportar coches a archivo de texto");
            System.out.println("7. Gestión de pasajeros");
            System.out.println("8. Salir");
            System.out.println();

            // Capturamos opción.
            ok = false;
            while (!ok) {                 
                try {
                    System.out.print("Elija opción: ");
                    texto = lector.nextLine();
                    opcion = Integer.parseInt(texto);
                    ok = true;
                } catch (Exception e) {                        
                    System.out.println("Número no válido. Intentelo de nuevo.");
                    ok = false;
                }
            }                
            
            // Tratamos la opción elegida.
            switch(opcion) {
            case 1:
                // Añadir nuevo coche.
                altaModificacionCoche("ALTA");
                System.out.println("Pulse [ENTER] para continuar.");  
                lector.nextLine(); 
                break;
                
            case 2:
                // Borrar coche por ID.
                bajaConsultaCoche("BAJA");
                System.out.println("Pulse [ENTER] para continuar.");  
                lector.nextLine();
                break;
 
            case 3:
                // Consultar coche por ID.
                bajaConsultaCoche("CONSULTA");
                System.out.println("Pulse [ENTER] para continuar.");  
                lector.nextLine();
                break;
                
            case 4:
                // Modificar coche por ID.
                altaModificacionCoche("MODIFICACION");
                System.out.println("Pulse [ENTER] para continuar.");  
                lector.nextLine();
                break;

            case 5:
                // Listar coches.
                listarCoches("TODOS");
                System.out.println("Pulse [ENTER] para continuar.");  
                lector.nextLine(); 
                break;
                
            case 6:
                // Exportar coches a archivo de texto.
                exportarCochesTxt();
                System.out.println("Pulse [ENTER] para continuar.");  
                lector.nextLine();   
                break;
                
            case 7:
                // Gestión de pasajeros.
                gestionarPasajeros();
                break;
                
            case 8:
                // Salir.
                break;
                
            default:
                System.out.println("Opción no válida. Intentelo de nuevo.");
                System.out.println();
                break;
            } // switch
            
        } while ( opcion != 8 ); // do
        
        // Fin de programa. Cerramos el Scanner de entrada.
        lector.close(); 
    }

    /**
     * Alta o modificación de un coche (por su ID).
     * 
     * @param modo <i>ALTA</i>: para el alta de un nuevo coche<br>
     *             <i>MODIFICACION</i>: para modificar un coche por su ID.
     */
    private static void altaModificacionCoche(String modo) {
        // Variables auxiliares.
        boolean ok = false;
        String texto = "";
        
        // Creamos un nuevo objeto coche.
        Coche coche = new Coche();
        // Creamos un objeto DAO para acceder a los coches de la BB.DD.
        DAOCoche dc = new DAOCocheMySql();
        
        // Mostramos la operación que estamos realizando.
        if (modo.equals("ALTA")) {
            System.out.println("Introduce los datos del nuevo coche");
            System.out.println("-----------------------------------");
        }
        else {
            // En el caso de modificación, además, solicitamos el coche a 
            // modificar.
            System.out.println("Introduce los datos del coche a modificar");
            System.out.println("-----------------------------------------");
            listarCoches("TODOS");

            // Variable auxiliar para capturar el ID.
            int ID = introducirID();

            // Con el ID proporcionado, lanzamos una consulta en la BB.DD.
            coche = dc.consultar(ID);
            
            // Comprobamos si la consulta ha devuelto algún objeto.
            if (coche != null) {
                // Mostramos el coche encontrado.
                System.out.println("Modificando coche " + coche);        

            } else {
                // Si no hemos podido encontrar el coche con el ID introducido, mostramos 
                // un mensaje de aviso y salimos del método.
                System.out.println("Coche con ID " + ID + " no encontrado.");
                return;
            }         
        }

        //
        // Capturamos la matrícula.
        //
        ok = false;
        while (!ok) {                     
            System.out.print("Introduce la matrícula[9999XXX]: ");
            texto = lector.nextLine();
            texto = texto.toUpperCase().trim();
            // Comprobamos que la matrícula introducida no esta vacía y
            // que coincida con el patrón solicitado.
            if ( texto.equals("") || !texto.matches("^\\d{4}[A-Z]{3}") ) {
                System.out.println("Matrícula en vacío/mal formateada. Intentelo de nuevo.");
                ok = false;                           
            }
            else {
                coche.setMatricula(texto);
                ok = true;
            }
        }
        //
        // Capturamos la marca.
        //
        ok = false;
        while (!ok) {
            System.out.print("Introduce la marca: ");
            texto = lector.nextLine();
            coche.setMarca(texto.toUpperCase().trim());
            if ( coche.getMarca().equals("") ) {
                System.out.println("Marca en vacío. Intentelo de nuevo.");
                ok = false;                           
            }
            else {
                ok = true;
            }                            
        } 
        //
        // Capturamos el modelo.
        //
        ok = false;
        while (!ok) {
            System.out.print("Introduce el modelo: ");
            texto = lector.nextLine();
            coche.setModelo(texto.toUpperCase().trim());
            if ( coche.getModelo().equals("") ) {
                System.out.println("Modelo en vacío. Intentelo de nuevo.");
                ok = false;                           
            }
            else {
                ok = true;
            }                            
        }
        //
        // Capturamos el color.
        //
        ok = false;
        while (!ok) {
            System.out.print("Introduce el color: ");
            texto = lector.nextLine();
            coche.setColor(texto.toUpperCase().trim());
            if ( coche.getColor().equals("") ) {
                System.out.println("Modelo en vacío. Intentelo de nuevo.");
                ok = false;                           
            }
            else {
                ok = true;
            } 
        }
        
        // En función de la operación seleccionada, añadimos o modificamos el coche.
        if (modo.equals("ALTA")) {
            // Añadimos el nuevo coche a la BB.DD.
            if (dc.alta(coche)) {
                System.out.println("Añadido coche con " + coche);
            }
        }
        else {
            // Actualizamos el coche en la BB.DD.
            if (dc.modificar(coche)) {
                System.out.println("Coche modificado a " + coche);                
            }
        }
    }

    /**
     * Baja (borrado) o consulta de un coche por su id.
     * 
     * @param modo <i>BAJA</i>: para el borrado de un coche<br>
     *             <i>CONSULTA</i>: para mostrar en pantalla los datos
     */
    private static void bajaConsultaCoche(String modo) {
        // Variables auxiliares.
        int ID = 0;

        System.out.println();
        // Mostramos la operación que estamos realizando.
        if (modo.equals("BAJA")) {
            System.out.println("Borrar coche por ID");     
            System.out.println("-------------------");
            listarCoches("NOASIGNADOS");
        } else {
            System.out.println("Consultar coche por ID");
            System.out.println("----------------------");
        }  
        
        // Capturamos el ID del coche a borrar/consultar.
        ID = introducirID();
        // Creamos un objeto DAO para acceder a los coches de la BB.DD.        
        DAOCoche dc = new DAOCocheMySql();
        // Con el ID proporcionado, lanzamos una consulta en la BB.DD.
        Coche coche = dc.consultar(ID);
        
        // Comprobamos si la consulta ha devuelto algún objeto.
        if (coche != null) {
            // Si estamos procesando una "BAJA", ejecutamos un borrado.
            if (modo.equals("BAJA")) {
                if (dc.baja(ID)) {
                    // Si el borrado se ha ejecutado correctamente, mostramos
                    // un mensaje con los datos del coche borrado.
                    System.out.println(coche + " ***BORRADO***");                 
                }                
            } else {
                // En el caso de "CONSULTA", mostramos el coche encontrado.
                System.out.println(coche);                
            }
        } else {          
            // Si no hemos podido encontrar el ID introducido, mostramos 
            // un mensaje de aviso.
            System.out.println("Coche con ID " + ID + " no encontrado.");             
        }
    }
    
    /**
     * Muestra en pantalla la lista de coches almacenados en la BB.DD.
     * 
     * @param modo <i>"TODOS"</i> Se listarán todos los coches.<br>
     *             <i>"ASIGNADOS"</i> Se listarán sólo los coches que tengan
     *             pasajeros asignados.<br>
     *             <i>"NOASIGNADOS"</i> Se listarán sólo los coches que NO tengan
     *             pasajeros asignados.
     */
    public static void listarCoches(String modo) {
        // Mostramos la operación que estamos realizando.
        System.out.println();
        
        if (modo.equalsIgnoreCase("TODOS")) {
            System.out.println("Listado de coches");
            System.out.println("--------------------");   
        } else if (modo.equalsIgnoreCase("ASIGNADOS")) {
            System.out.println("Listado de coches (CON PASAJEROS)");
            System.out.println("---------------------------------");   
        } else if (modo.equalsIgnoreCase("NOASIGNADOS")) {
            System.out.println("Listado de coches (SIN PASAJEROS)");
            System.out.println("---------------------------------");   
        }
        
        // Lanzamos una consulta en la BB.DD. para recuperar todos los coches
        // en la BB.DD.
        DAOCoche dc = new DAOCocheMySql();
        List<Coche> coches = dc.listar(modo);
        
        // Comprobamos si la lista de coches tiene elementos para ser listados.
        if (coches.size() > 0) {            
            // Recorremos la lista e invocamos (indirectamente) al método toString() del objeto Coche.
            for (Coche coche : coches) {
                System.out.println(coche.toBriefString());
            }        
        } else {
            System.out.println("No hay coches para listar.");             
        }
    }

    /**
     * Exporta la lista de objetos coches del concesionario, en modo texto, al fichero definido por 
     * la constante <b><i>ARCHIVO_COCHES_TXT</i></b>.
     */
    private static void exportarCochesTxt() {
        System.out.println();
        System.out.println("Exportar coches a archivo de texto");
        System.out.println("----------------------------------");
        
        // Objeto "File" para exportar la lista de coches en modo texto.
        File archivo = new File(ARCHIVO_COCHES_TXT);
        
        // Comprobamos si el archivo existe.
        if (archivo.exists()) {
            // Si el archivo existe, lo borramos antes de crearlo de nuevo.
            // Esto lo hacemos para asegurarnos de que, en caso de que la lista de coches haya
            // quedado vacía (por ejemplo, por haber eliminado manualmente todos los coches
            // de la lista), no dejaremos una versión anterior obsoleta y/o vacía.
            try {
                if (!archivo.delete()) {
                    System.out.println("Error al borrar el archivo " + ARCHIVO_COCHES_TXT);
                }                
            } catch (Exception e) {
                System.out.println(e.getMessage());
            } // try
        }
        
        // Lanzamos una consulta en la BB.DD. para recuperar todos los coches
        // en la BB.DD.
        DAOCoche dc = new DAOCocheMySql();
        List<Coche> coches = dc.listar("TODOS");
        
        // Comprobamos si la lista de coches tiene elementos para ser exportados a modo texto.
        if (coches.size() > 0) {
            try {
                // Abrir fichero y buffer para escritura.    
                FileWriter farchivo = new FileWriter(ARCHIVO_COCHES_TXT);
                BufferedWriter salida = new BufferedWriter(farchivo);

                // Recorremos la lista e invocamos al método toString() del objeto Coche.
                for (Coche coche : coches) {
                    salida.write(coche.toString());
                    salida.newLine();
                }        

                // Cerrar el buffer y el fichero.
                salida.close();
                farchivo.close();
                
                System.out.println("Exportación a " + ARCHIVO_COCHES_TXT + " finalizada [" + coches.size() + " coches].");
                
            } catch (Exception e) {
                System.out.println("Ha ocurrido un problema al exportar " + ARCHIVO_COCHES_TXT + " " + e.getMessage());
            } // try
            
        } else {
            System.out.println("No hay coches para exportar a texto.");             
        }
    }
    
    /**
     * Llamamos a la clase <i>GestionPasajeros</i> para realizar todas las funciones
     * relacionadas con los pasajeros.
     */
    private static void gestionarPasajeros() {
        // Pasamos un argumento a la clase GestionPasajeros para evitar
        // que se cierre el scanner que apunta a System.in
        GestionPasajeros.main(new String[]{"NOCLOSE"});
    }
    
    /**
     * Método para capturar por pantalla el ID de un coche.
     * 
     * @return El ID introducido por pantalla.
     */
    private static int introducirID() {
        // Variables auxiliares.
        boolean ok;
        String texto;
        int ID = 0;
        
        //
        // Capturamos el ID.
        //
        ok = false;
        while (!ok) {
            try {
                System.out.print("Introduzca el ID: ");
                texto = lector.nextLine();
                ID = Integer.parseInt(texto);
                if ( ID == 0 ) {
                    System.out.println("El ID no puede ser 0. Intentelo de nuevo.");
                    ok = false;                           
                }
                else {
                    ok = true;
                }                            
            } catch (NumberFormatException e) {
                // 
                System.out.println("ID mal formateado. Intentelo de nuevo.");                           
            }
        }
        return ID;
    }
    
}
