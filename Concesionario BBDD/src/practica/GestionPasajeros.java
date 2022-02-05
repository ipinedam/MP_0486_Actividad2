package practica;

import java.util.List;
import java.util.Scanner;

import modelo.entidad.Coche;
import modelo.entidad.Pasajero;
import modelo.persistencia.DAOCocheMySql;
import modelo.persistencia.DAOPasajeroMySql;
import modelo.persistencia.interfaz.DAOCoche;
import modelo.persistencia.interfaz.DAOPasajero;

/**
 * <b>Actividad 2. Tarea de equipo (2 personas). Gestión de un concesionario de
 * coches (2)</b> <br><br>
 * En esta clase, implementamos la gestión de los pasajeros que usan los coches
 * del concesionario gestionado en BB.DD.<br>
 * La relación <i>Pasajero-{@literal >}Coche</i> será manejada mediante un
 * "foreign key" en la tabla <i>Pasajeros</i>.<br>
 * El menú que aparecerá será el siguiente:<br>
 * <br>
 * <i>1. Añadir nuevo pasajero</i><br>
 * <i>2. Borrar pasajero por ID</i><br>
 * <i>3. Consultar pasajero por ID</i><br>
 * <i>4. Modificar pasajero por ID</i><br>
 * <i>5. Listado de pasajeros</i><br>
 * <i>6. Añadir pasajero a coche</i><br>
 * <i>7. Borrar pasajero de coche</i><br>
 * <i>8. Listado de pasajeros en coche</i><br>
 * <i>9. Salir</i><br>
 * 
 * @author Ignacio Pineda Martín
 *
 */
public class GestionPasajeros {
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
        boolean cerrarScanner = true;
        
        // Admitimos que se pase como parámetro un "flag" para cerrar (o no)
        // el scanner que apunta a System.in
        if (args != null && args.length != 0) {
            if (args[0].toString().toUpperCase().trim() == "NOCLOSE") {
                cerrarScanner = false;
            }
        }
        
        // Bucle principal de tratamiento.
        do {
            // Mostramos las opciones.
            System.out.println();
            System.out.println("    CONCESIONARIO - PASAJEROS    ");
            System.out.println("---------------------------------");
            System.out.println("1. Añadir nuevo pasajero");
            System.out.println("2. Borrar pasajero por ID");
            System.out.println("3. Consultar pasajero por ID");
            System.out.println("4. Modificar pasajero por ID");
            System.out.println("5. Listado de pasajeros");
            System.out.println("6. Añadir pasajero a coche");
            System.out.println("7. Borrar pasajero de coche");
            System.out.println("8. Listado de pasajeros en coche");
            System.out.println("9. Salir");
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
                // Añadir nuevo pasajero.
                altaModificacionPasajero("ALTA");
                System.out.println("Pulse [ENTER] para continuar.");  
                lector.nextLine(); 
                break;
                
            case 2:
                // Borrar pasajero por ID.
                bajaConsultaPasajero("BAJA");
                System.out.println("Pulse [ENTER] para continuar.");  
                lector.nextLine();
                break;
 
            case 3:
                // Consultar pasajero por ID.
                bajaConsultaPasajero("CONSULTA");
                System.out.println("Pulse [ENTER] para continuar.");  
                lector.nextLine();
                break;
                
            case 4:
                // Modificar pasajero por ID.
                altaModificacionPasajero("MODIFICACION");
                System.out.println("Pulse [ENTER] para continuar.");  
                lector.nextLine();
                break;

            case 5:
                // Listado de pasajeros.
                listarPasajeros("TODOS");
                System.out.println("Pulse [ENTER] para continuar.");  
                lector.nextLine(); 
                break;
                
            case 6:
                // Añadir pasajero a coche.
                altaPasajeroCoche();
                System.out.println("Pulse [ENTER] para continuar.");  
                lector.nextLine();   
                break;

            case 7:
                // Borrar pasajero de coche
                bajaPasajeroCoche();
                System.out.println("Pulse [ENTER] para continuar.");  
                lector.nextLine();   
                break;
                
            case 8:
                // Listado de pasajeros en coche.
                listarPasajerosCoche();
                System.out.println("Pulse [ENTER] para continuar.");  
                lector.nextLine(); 
                break;
                
            case 9:
                // Salir.             
                break;
                
            default:
                System.out.println("Opción no válida. Intentelo de nuevo.");
                System.out.println();
                break;
            } // switch
            
        } while ( opcion != 9 ); // do
        
        // Fin de programa. Cerramos el Scanner de entrada.
        if (cerrarScanner) {
            lector.close();            
        }
    }
    
    /**
     * Alta o modificación de un pasajero (por su ID).
     * 
     * @param modo <i>ALTA</i>: para el alta de un nuevo pasajero<br>
     *             <i>MODIFICACION</i>: para modificar un pasajero por su ID.
     */
    private static void altaModificacionPasajero(String modo) {
        // Variables auxiliares.
        boolean ok = false;
        String texto = "";
        int edad = 0;
        double peso = 0.0;
        
        // Creamos un nuevo objeto Pasajero.
        Pasajero pasajero = new Pasajero();
        // Creamos un objeto DAO para acceder a las pasajeros de la BB.DD.
        DAOPasajero dp = new DAOPasajeroMySql();
        
        System.out.println();
        // Mostramos la operación que estamos realizando.
        if (modo.equals("ALTA")) {
            System.out.println("Añadir nuevo pasajero");
            System.out.println("---------------------");
        }
        else {
            // En el caso de modificación, además, solicitamos el pasajero a 
            // modificar.
            System.out.println("Modificar pasajero por ID");
            System.out.println("-------------------------");

            // Variable auxiliar para capturar el ID.
            int ID = introducirID();

            // Con el ID proporcionado, lanzamos una consulta en la BB.DD.
            pasajero = dp.consultar(ID);
            
            // Comprobamos si la consulta ha devuelto algún objeto.
            if (pasajero != null) {
                // Mostramos el pasajero encontrado.
                System.out.println("Modificando pasajero " + pasajero);        

            } else {
                // Si no hemos podido encontrar el pasajero con el ID introducido, mostramos 
                // un mensaje de aviso y salimos del método.
                System.out.println("Pasajero con ID " + ID + " no encontrado.");
                return;
            }         
        }

        //
        // Capturamos el nombre.
        //
        ok = false;
        while (!ok) {
            System.out.print("Introduce el nombre: ");
            texto = lector.nextLine();
            pasajero.setNombre(texto.toUpperCase().trim());
            if ( pasajero.getNombre().equals("") ) {
                System.out.println("Nombre en vacío. Intentelo de nuevo.");
                ok = false;                           
            }
            else {
                ok = true;
            }                            
        } 
        //
        // Capturamos la edad.
        //
        ok = false;
        while (!ok) {
            try {
                System.out.print("Introduzca la edad: ");
                texto = lector.nextLine();
                edad = Integer.parseInt(texto);
                if ( edad == 0 ) {
                    System.out.println("La edad no puede ser 0. Intentelo de nuevo.");
                    ok = false;                           
                }
                else {
                    ok = true;
                }                            
            } catch (NumberFormatException e) {
                // 
                System.out.println("Edad mal formateada. Intentelo de nuevo.");                           
            }
        }
        pasajero.setEdad(edad);
        //
        // Capturamos el peso
        //
        ok = false;
        while (!ok) {
            try {
                System.out.print("Introduzca el peso: ");
                texto = lector.nextLine();
                peso = Double.parseDouble(texto);
                if ( peso == 0 ) {
                    System.out.println("El peso no puede ser 0. Intentelo de nuevo.");
                    ok = false;                           
                }
                else {
                    ok = true;
                }                            
            } catch (NumberFormatException e) {
                // 
                System.out.println("Peso mal formateado. Intentelo de nuevo.");                           
            }
        }
        pasajero.setPeso(peso);
                
        // En función de la operación seleccionada, añadimos o modificamos el pasajero.
        if (modo.equals("ALTA")) {
            // Añadimos el nuevo pasajero a la BB.DD.
            if (dp.alta(pasajero)) {
                System.out.println("Añadido pasajero con " + pasajero);
            }
        }
        else {
            // Actualizamos el pasajero en la BB.DD.
            if (dp.modificar(pasajero)) {
                System.out.println("Pasajero modificado a " + pasajero);                
            }
        }
    }

    /**
     * Baja (borrado) o consulta de un pasajero por su ID.
     * 
     * @param modo <i>BAJA</i>: para el borrado de un pasajero<br>
     *             <i>CONSULTA</i>: para mostrar en pantalla los datos
     */
    private static void bajaConsultaPasajero(String modo) {
        // Variables auxiliares.
        int ID = 0;

        System.out.println();
        // Mostramos la operación que estamos realizando.
        if (modo.equals("BAJA")) {
            System.out.println("Borrar pasajero por ID");   
            System.out.println("----------------------"); 
        } else {
            System.out.println("Consultar pasajero por ID");
            System.out.println("-------------------------");
        }
        
        // Capturamos el ID de el pasajero a borrar/consultar.
        ID = introducirID();
        // Creamos un objeto DAO para acceder a las pasajeros de la BB.DD.        
        DAOPasajero dp = new DAOPasajeroMySql();
        // Con el ID proporcionado, lanzamos una consulta en la BB.DD.
        Pasajero pasajero = dp.consultar(ID);
        
        // Comprobamos si la consulta ha devuelto algún objeto.
        if (pasajero != null) {
            // Si estamos procesando una "BAJA", ejecutamos un borrado.
            if (modo.equals("BAJA")) {
                if (dp.baja(ID)) {
                    // Si el borrado se ha ejecutado correctamente, mostramos
                    // un mensaje con los datos del pasajero borrado.
                    System.out.println(pasajero + " ***BORRADO***");                 
                }                
            } else {
                // En el caso de "CONSULTA", mostramos el pasajero encontrada.
                System.out.println(pasajero);                
            }
        } else {          
            // Si no hemos podido encontrar el ID introducido, mostramos 
            // un mensaje de aviso.
            System.out.println("Pasajero con ID " + ID + " no encontrada.");             
        }
    }
    
    /**
     * Muestra en pantalla la lista de pasajeros almacenadas en la BB.DD.
     * @param modo <i>"TODOS"</i> Se listarán todos los pasajeros.<br>
     *             <i>"ASIGNADOS"</i> Se listarán sólo los pasajeros que se hayan asignado a un coche.<br>
     *             <i>"NOASIGNADOS"</i> Se listarán sólo los pasajeros que NO se hayan
     *             asignado a un coche.
     */
    private static void listarPasajeros(String modo) {
        System.out.println();
        
        if (modo.equalsIgnoreCase("TODOS")) {
            System.out.println("Listado de pasajeros");
            System.out.println("--------------------");   
        } else if (modo.equalsIgnoreCase("ASIGNADOS")) {
            System.out.println("Listado de pasajeros (ASIGNADOS)");
            System.out.println("--------------------------------");   
        } else if (modo.equalsIgnoreCase("NOASIGNADOS")) {
            System.out.println("Listado de pasajeros (NO ASIGNADOS)");
            System.out.println("-----------------------------------");   
        }
        
        // Lanzamos una consulta en la BB.DD. para recuperar todos las pasajeros
        // en la BB.DD.
        DAOPasajero dp = new DAOPasajeroMySql();
        List<Pasajero> pasajeros = dp.listar(modo);
        
        // Comprobamos si la lista de pasajeros tiene elementos para ser listados.
        if (pasajeros.size() > 0) {            
            // Recorremos la lista e invocamos (indirectamente) al método toString() 
            // del objeto Pasajero, o al método toBriefString()
            for (Pasajero pasajero : pasajeros) {
                System.out.println(pasajero.toBriefString());
            }   
        } else {
            System.out.println("No hay pasajeros para listar.");             
        }
    }

    
    /**
     * Alta de un pasajero (añadir relación coche-&gt;pasajero) en la BB.DD.
     */
    private static void altaPasajeroCoche() {
        // Variables auxiliares.
        boolean ok = false;
        int ID = 0;
        
        // Creamos un objeto DAO para acceder a las pasajeros de la BB.DD.
        DAOPasajero dp = new DAOPasajeroMySql();
        Pasajero pasajero = null;
        
        // Creamos un objeto DAO para acceder a los coches de la BB.DD.
        DAOCoche dc = new DAOCocheMySql();
        Coche coche = null;
        
        System.out.println();
        // Mostramos la operación que estamos realizando.
        System.out.println("Añadir pasajero a coche");
        System.out.println("-----------------------");
        // Mostramos los pasajeros NO asignados a coches, para elegir más facilmente.
        listarPasajeros("NOASIGNADOS");

        //
        // Capturamos el ID del pasajero hasta obtener un ID válido.
        // 
        ok = false;
        while (!ok) {
            System.out.println();
            System.out.println("*** Datos del pasajero a asignar ***");
            ID = introducirID();
            
            // Lanzamos una consulta contra la BB.DD. para comprobar si el pasajero
            // cuyo ID hemos introducido existe.
            pasajero = dp.consultar(ID);
            if (pasajero == null) {
                System.out.println("El pasajero con ID " + ID + " NO existe.");
            } else {
                ok = true;
            }                
        }
        
        // Controlamos que el pasajero no esté asignado ya a otro coche.
        // Eso sólo ocurrirá si elegimos un ID que no esté en la lista de pasajeros
        // disponibles mostrada previamente.
        if (pasajero.getIdCoche() != 0) {
            System.out.println(pasajero.toBriefString() + " ya está asignado al coche con ID " + pasajero.getIdCoche());
            return;
        } else {
            // Mostramos los datos de el pasajero que se va a asignar a un coche.
            System.out.println(pasajero.toBriefString());
        }
        
        // Mostramos los coches almacenados en la BB.DD. para facilitar su selección.
        Principal.listarCoches("TODOS");

        //
        // Capturamos el ID del coche hasta obtener un ID válido.
        //
        ok = false;
        while (!ok) {
            System.out.println();
            System.out.println("*** Datos del coche a asignar ***");
            ID = introducirID();

            // Lanzamos una consulta contra la BB.DD. para comprobar si el coche
            // cuyo ID hemos introducido existe.
            coche = dc.consultar(ID);
            if (coche == null) {
                System.out.println("El coche con ID " + ID + " NO existe.");
            } else {
                ok = true;
            }
        }

        // Mostramos los datos del coche en el que se añadiránpasajeros.
        System.out.println(coche.toBriefString());
        
        System.out.println();
        // Añadimos la relación coche->persona (pasajero) en la BB.DD.
        // Para ello, simplemente asignamos el ID del coche al campo que sirve 
        // como "foreign key" en la tabla PASAJEROS.
        pasajero.setIdCoche(coche.getId());
        if (dp.modificar(pasajero)) {
            System.out.println("Pasajero " + pasajero.toBriefString() + 
                               "\nañadido al coche " + coche.toBriefString() + ".");
        }
    }
    
    /**
     * Baja de un pasajero (eliminar relación coche-&gt;pasajero) en la BB.DD. .
     */
    private static void bajaPasajeroCoche() {
        // Variables auxiliares.
        boolean ok = false;
        int ID = 0;
        
        // Creamos un objeto DAO para acceder a las pasajeros de la BB.DD.
        DAOPasajero dp = new DAOPasajeroMySql();
        Pasajero pasajero = null;
        
        // Creamos un objeto DAO para acceder a los coches de la BB.DD.
        DAOCoche dc = new DAOCocheMySql();
        Coche coche = null;
        
        System.out.println();
        // Mostramos la operación que estamos realizando.
        System.out.println("Borrar pasajero de coche");
        System.out.println("------------------------");
        // Mostramos los pasajeros asignados a coches, para elegir más facilmente.
        listarPasajeros("ASIGNADOS");
        
        //
        // Capturamos el ID del pasajero hasta obtener un ID válido.
        // 
        ok = false;
        while (!ok) {
            System.out.println();
            System.out.println("*** Datos del pasajero a borrar de un coche ***");
            ID = introducirID();
            
            // Lanzamos una consulta contra la BB.DD. para comprobar si el pasajero
            // cuyo ID hemos introducido existe.
            pasajero = dp.consultar(ID);
            if (pasajero == null) {
                System.out.println("El pasajero con ID " + ID + " NO existe.");
            } else {
                ok = true;
            }                
        }

        // Controlamos que el pasajero esté asignado a un coche.
        // Eso sólo ocurrirá si elegimos un ID que no esté en la lista de pasajeros
        // asignados a coches mostrada previamente.
        if (pasajero.getIdCoche() == 0) {
            System.out.println(pasajero.toBriefString() + " NO está asignado a ningún coche.");
            return;
        } else {
            // Mostramos los datos de el pasajero a quitar.
            System.out.println(pasajero.toBriefString());
        }        

        // Lanzamos una consulta contra la BB.DD. para capturar los datos
        // del coche asociado al pasajero.
        coche = dc.consultar(pasajero.getIdCoche());
        
        System.out.println();
        // Borramos la relación coche->persona (pasajero) en la BB.DD.
        // Para ello, simplemente dejamos a 0 el ID del coche en el campo que sirve 
        // como "foreign key" en la tabla PASAJEROS.
        pasajero.setIdCoche(0);
        if (dp.modificar(pasajero)) {
            System.out.println("Pasajero " + pasajero.toBriefString() + 
                               "\nborrado del coche " + coche.toBriefString() + ".");
        }
    }


    /**
     * Listado de todos los pasajero (relación coche-&gt;persona) en la BB.DD. de un
     * coche determinado.
     */
    private static void listarPasajerosCoche() {
        // Variables auxiliares.
        boolean ok = false;
        int ID = 0;

        // Creamos un objeto DAO para acceder a las pasajeros de la BB.DD.
        DAOPasajero dp = new DAOPasajeroMySql();
        
        // Creamos un objeto DAO para acceder a los coches de la BB.DD.
        DAOCoche dc = new DAOCocheMySql();
        Coche coche = null;
        
        // Mostramos la operación que estamos realizando.
        System.out.println();
        System.out.println("Listado de pasajeros en coche");
        System.out.println("-----------------------------");
        Principal.listarCoches("ASIGNADOS");

        //
        // Capturamos el ID del coche hasta obtener un ID válido.
        // 
        ok = false;
        while (!ok) {
            System.out.println();
            System.out.println("*** Datos del coche ***");
            ID = introducirID();
            
            // Lanzamos una consulta contra la BB.DD. para comprobar si el coche
            // cuyo ID hemos introducido existe.
            coche = dc.consultar(ID);
            if (coche == null) {
                System.out.println("El coche con ID " + ID + " NO existe.");
            } else {
                ok = true;
            }                
        }
        
        // Consultamos todos los pasajeros asignados al coche obtenido.
        List<Pasajero> pasajeros = dp.listar(ID);
        
        // Comprobamos si la lista de pasajeros tiene elementos para ser listados.
        if (pasajeros.size() > 0) {
            ok = true;
            // Recorremos la lista y obtenemos información del coche y sus personas.
            for (Pasajero pasajero: pasajeros) {
                // Mostramos la información del coche sólo una vez.
                if (ok) {
                    coche = dc.consultar(pasajero.getIdCoche());  
                    System.out.println("Pasajeros del coche " + coche.toBriefString());
                    ok = false;
                }                
                // Mostramos la información de las personas asignadas al coche.
                System.out.println("\t" + pasajero.toBriefString());
            }        
        } else {
            System.out.println("No hay pasajeros asignados al coche " + coche);             
        }
        
    }
    
    /**
     * Método para capturar por pantalla el ID de una pasajero o un coche.
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
