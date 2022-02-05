package modelo.persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.entidad.Pasajero;
import modelo.persistencia.interfaz.DAOPasajero;

/**
 * Clase que contiene todas los métodos DAO relacionados con el objeto "Pasajero"
 * del concesionario.
 * 
 * @author Ignacio Pineda Martín
 *
 */
public class DAOPasajeroMySql extends DAOConcesionarioMySql implements DAOPasajero {

    /**
     * Método para el alta de un objeto Pasajero en la BB.DD.
     * 
     * @param p Objeto Pasajero a insertar.
     * @return <i>True</i>: el pasajero se ha insertado correctamente.<br>
     *         <i>False</i>: el pasajero <b>no</b> se ha insertado.
     */
    @Override
    public boolean alta(Pasajero p) {
        // Abrimos la conexión con la BB.DD.
        if (!abrirConexion()) {
            return false;
        }
        // Creamos la variable que devolverá la función.
        boolean insertado = true;

        String sINSERT = "INSERT INTO PASAJEROS (NOMBRE, EDAD, PESO) VALUES (?, ?, ?)";
        try {
            // Preparamos la sentencia de inserción. 
            PreparedStatement ps = conexion.prepareStatement(sINSERT);
            // Damos valor a los parámetros que espera la sentencia.
            ps.setString(1, p.getNombre());
            ps.setInt(2, p.getEdad());
            ps.setDouble(3, p.getPeso());

            // Ejecutamos la sentencia y comprobamos el número de filas afectadas.
            int numeroFilasAfectadas = ps.executeUpdate();
            // Si el número de filas es 0, la función devolverá "false".
            // En caso contrario, recuperamos el ID asignado por la BB.DD.
            if (numeroFilasAfectadas == 0) {
                insertado = false;
            } else {
                p.setId(getLastInsertID());
            }
        } catch (SQLException e) {
            insertado = false;
            // Si obtenemos el código correspondiente a duplicados, es porque hemos
            // introducido el mismo nombre en 2 pasajeros diferentes.
            if (e.getErrorCode() == DUPLICATE_ENTRY) {
                System.out.println("Error: el nombre " + p.getNombre() + " ya existe. Pasajero NO insertada." );
            } else {
                // Mostramos el resto de errores no controlados.
                System.out.println("alta -> Error al insertar: " + p + 
                                   " SQLCODE: " + e.getErrorCode() + 
                                   " SLQSTATE: " + e.getSQLState());                
            }
        } finally {
            cerrarConexion();
        }

        return insertado;
    }
    
    /**
     * Método para el borrado de un objeto Pasajero en la BB.DD.
     * 
     * @param id El ID del objeto Pasajero a borrar.
     * @return <i>True</i>: el pasajero se ha borrado correctamente.<br>
     *         <i>False</i>: el pasajero <b>no</b> se ha borrado.
     */
    @Override
    public boolean baja(int id) {
        // Abrimos la conexión con la BB.DD.
        if (!abrirConexion()) {
            return false;
        }
        // Creamos la variable que devolverá la función.
        boolean borrado = true;

        String query = "DELETE FROM PASAJEROS WHERE ID = ?";
        try {
            // Preparamos la sentencia de borrado. 
            PreparedStatement ps = conexion.prepareStatement(query);
            // Damos valor a los parámetros que espera la sentencia.
            ps.setInt(1, id);

            // Ejecutamos la sentencia y comprobamos el número de filas afectadas.
            int numeroFilasAfectadas = ps.executeUpdate();            
            // Si el número de filas es 0, la función devolverá "false".           
            if (numeroFilasAfectadas == 0)
                borrado = false;
        } catch (SQLException e) {
            borrado = false;
            // Mostramos el error producido.
            System.out.println("baja -> Error al borrar por ID: " + id + 
                               " SQLCODE: " + e.getErrorCode() + 
                               " SLQSTATE: " + e.getSQLState());              
        } finally {
            cerrarConexion();
        }
        return borrado;
    }

    /**
     * Método para la modificación de un objeto Pasajero en la BB.DD.
     * 
     * @param p Objeto Pasajero con los nuevos datos.
     * @return <i>True</i>: el objeto Pasajero se ha modificado correctamente.<br>
     *         <i>False</i>: el objeto Pasajero <b>no</b> se ha modificado.
     */
    @Override
    public boolean modificar(Pasajero p) {
        // Abrimos la conexión con la BB.DD.
        if (!abrirConexion()) {
            return false;
        }
        // Creamos la variable que devolverá la función.
        boolean modificado = true;

        String query = "UPDATE PASAJEROS SET NOMBRE = ?, EDAD = ?, PESO = ?, ID_COCHE = ? WHERE ID = ?";
        try {
            // Preparamos la sentencia de modificación.
            PreparedStatement ps = conexion.prepareStatement(query);
            // Damos valor a los parámetros que espera la sentencia.
            ps.setString(1, p.getNombre());
            ps.setInt(2, p.getEdad());
            ps.setDouble(3, p.getPeso());

            // El campo "ID_COCHE" será "NULL" en la BB.DD. si la propiedad equivalente
            // del objeto Persona es 0.
            if (p.getIdCoche() != 0) {
                ps.setInt(4, p.getIdCoche());               
            } else {
                ps.setNull(4, java.sql.Types.INTEGER);                
            }
            
            ps.setInt(5, p.getId());

            // Ejecutamos la sentencia y comprobamos el número de filas afectadas.
            int numeroFilasAfectadas = ps.executeUpdate();
            // Si el número de filas es 0, la función devolverá "false". 
            if (numeroFilasAfectadas == 0)
                modificado = false;
        } catch (SQLException e) {
            modificado = false;
            // Si obtenemos el código correspondiente a duplicados, es porque hemos
            // introducido el mismo nombre en 2 pasajeros diferentes.
            if (e.getErrorCode() == DUPLICATE_ENTRY) {
                System.out.println("Error: el nombre " + p.getNombre() + " ya existe. Pasajero NO modificado." );
            } else {
                // Mostramos el resto de errores no controlados.
                System.out.println("modificar -> Error al modificar: " + p + 
                                   " SQLCODE: " + e.getErrorCode() + 
                                   " SLQSTATE: " + e.getSQLState());                
            }
        } finally {
            cerrarConexion();
        }

        return modificado;
    }

    /**
     * Método para la consulta de un objeto Pasajero en la BB.DD.
     * 
     * @param id El ID del objeto Pasajero a consultar.
     * @return Objeto Pasajero con los datos recuperados.
     */
    @Override
    public Pasajero consultar(int id) {
        // Abrimos la conexión con la BB.DD.
        if (!abrirConexion()) {
            return null;
        }
        // Creamos la variable que devolverá la función.
        Pasajero pasajero = null;

        String query = "SELECT ID, NOMBRE, EDAD, PESO, ID_COCHE FROM PASAJEROS WHERE ID = ?";
        try {
            // Preparamos la sentencia de modificación.
            PreparedStatement ps = conexion.prepareStatement(query);
            // Damos valor a los parámetros que espera la sentencia.
            ps.setInt(1, id);

            // Ejecutamos la sentencia y recogemos el resultado para dar
            // valores a las propiedades del objeto Pasajero.
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                pasajero = new Pasajero();
                pasajero.setId(rs.getInt(1));
                pasajero.setNombre(rs.getString(2));
                pasajero.setEdad(rs.getInt(3));
                pasajero.setPeso(rs.getDouble(4));
                pasajero.setIdCoche(rs.getInt(5));
            }
        } catch (SQLException e) {
            System.out.println("consultar-> Error al consultar por ID: " + id + 
                               " SQLCODE: " + e.getErrorCode() + 
                               " SLQSTATE: " + e.getSQLState());
        } finally {
            cerrarConexion();
        }

        return pasajero;
    }

    /**
     * Método para listar los pasajeros existentes en la BB.DD.
     * 
     * @param modo <i>"TODOS"</i> Se listarán todos los pasajeros.<br>
     *             <i>"ASIGNADOS"</i> Se listarán sólo los pasajeros que se hayan asignado a un coche.<br>
     *             <i>"NOASIGNADOS"</i> Se listarán sólo los pasajeros que NO se hayan
     *             asignado a un coche.
     * 
     * @return Lista de objetos Pasajero almacenados en la BB.DD., según la consulta seleccionada.
     */
    @Override
    public List<Pasajero> listar(String modo) {
        // Abrimos la conexión con la BB.DD.
        if (!abrirConexion()) {
            return null;
        }
        // Creamos la variable que devolverá la función.
        List<Pasajero> listaPasajeros = new ArrayList<>();
        String query = "";
        
        if (modo.equalsIgnoreCase("TODOS")) {
            query = "SELECT ID, NOMBRE, EDAD, PESO, ID_COCHE FROM PASAJEROS";
        } else if (modo.equalsIgnoreCase("ASIGNADOS")) {
            query = "SELECT ID, NOMBRE, EDAD, PESO, ID_COCHE FROM PASAJEROS WHERE ID_COCHE IS NOT NULL";
        } else if (modo.equalsIgnoreCase("NOASIGNADOS")) {
            query = "SELECT ID, NOMBRE, EDAD, PESO, ID_COCHE FROM PASAJEROS WHERE ID_COCHE IS NULL";
        }

        try {
            // Preparamos la sentencia de consulta.
            PreparedStatement ps = conexion.prepareStatement(query);
            
            // Ejecutamos la sentencia y recogemos el resultado para dar
            // valores a las propiedades del objeto Pasajero.
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Pasajero pasajero = new Pasajero();

                pasajero.setId(rs.getInt(1));
                pasajero.setNombre(rs.getString(2));
                pasajero.setEdad(rs.getInt(3));
                pasajero.setPeso(rs.getDouble(4));
                pasajero.setIdCoche(rs.getInt(5));

                listaPasajeros.add(pasajero);
            }
        } catch (SQLException e) {
            System.out.println("listar -> Error al consultar tabla." + 
                    " SQLCODE: " + e.getErrorCode() + 
                    " SLQSTATE: " + e.getSQLState());
            e.printStackTrace();
        } finally {
            cerrarConexion();
        }

        return listaPasajeros;
    }
    
    /**
     * Método para listar los pasajeros asignados al coche con el ID proporcionado.
     * 
     * @param idCoche ID del coche del que se quieren listar sus pasajeros.
     * 
     * @return Lista de objetos Pasajero asignados al coche con el ID proporcionado.
     */
    @Override
    public List<Pasajero> listar(int idCoche) {
        // Abrimos la conexión con la BB.DD.
        if (!abrirConexion()) {
            return null;
        }
        // Creamos la variable que devolverá la función.
        List<Pasajero> listaPasajeros = new ArrayList<>();
        String query = "SELECT ID, NOMBRE, EDAD, PESO, ID_COCHE FROM PASAJEROS WHERE ID_COCHE = ?";

        try {
            // Preparamos la sentencia de consulta.
            PreparedStatement ps = conexion.prepareStatement(query);
            // Damos valor a los parámetros que espera la sentencia.
            ps.setInt(1, idCoche);
            
            // Ejecutamos la sentencia y recogemos los resultados.
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Pasajero pasajero = new Pasajero();

                pasajero.setId(rs.getInt(1));
                pasajero.setNombre(rs.getString(2));
                pasajero.setEdad(rs.getInt(3));
                pasajero.setPeso(rs.getDouble(4));
                pasajero.setIdCoche(rs.getInt(5));

                listaPasajeros.add(pasajero);
            }
        } catch (SQLException e) {
            System.out.println("listar -> Error al consultar tabla." + 
                    " SQLCODE: " + e.getErrorCode() + 
                    " SLQSTATE: " + e.getSQLState());
            e.printStackTrace();
        } finally {
            cerrarConexion();
        }

        return listaPasajeros;
    }
    
}
