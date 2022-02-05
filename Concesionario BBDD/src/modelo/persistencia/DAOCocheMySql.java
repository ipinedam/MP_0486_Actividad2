package modelo.persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import modelo.entidad.Coche;
import modelo.persistencia.interfaz.DAOCoche;

/**
 * Clase que contiene todas los métodos DAO relacionados con el objeto "Coche"
 * del concesionario.
 * 
 * @author Ignacio Pineda Martín
 *
 */
public class DAOCocheMySql extends DAOConcesionarioMySql implements DAOCoche {

    /**
     * Método para el alta de un objeto Coche en la BB.DD.
     * 
     * @param c Objeto Coche a insertar.
     * @return <i>True</i>: el Coche se ha insertado correctamente.<br>
     *         <i>False</i>: el Coche <b>no</b> se ha insertado.
     */
    @Override
    public boolean alta(Coche c) {
        // Abrimos la conexión con la BB.DD.
        if (!abrirConexion()) {
            return false;
        }
        // Creamos la variable que devolverá la función.
        boolean insertado = true;

        String sINSERT = "INSERT INTO COCHES (MATRICULA, MARCA, MODELO, COLOR) VALUES (?, ?, ?, ?)";
        try {
            // Preparamos la sentencia de inserción. 
            PreparedStatement ps = conexion.prepareStatement(sINSERT);
            // Damos valor a los parámetros que espera la sentencia.
            ps.setString(1, c.getMatricula());
            ps.setString(2, c.getMarca());
            ps.setString(3, c.getModelo());
            ps.setString(4, c.getColor());

            // Ejecutamos la sentencia y comprobamos el número de filas afectadas.
            int numeroFilasAfectadas = ps.executeUpdate();
            // Si el número de filas es 0, la función devolverá "false".
            // En caso contrario, recuperamos el ID asignado por la BB.DD.
            if (numeroFilasAfectadas == 0) {
                insertado = false;
            } else {
                c.setId(getLastInsertID());
            }
        } catch (SQLException e) {
            insertado = false;
            // Si obtenemos el código correspondiente a duplicados, es porque hemos
            // introducido la misma matrícula en 2 coches diferentes.
            if (e.getErrorCode() == DUPLICATE_ENTRY) {
                System.out.println("Error: la matrícula " + c.getMatricula() + " ya existe. Coche NO insertado." );
            } else {
                // Mostramos el resto de errores no controlados.
                System.out.println("alta -> Error al insertar: " + c + 
                                   " SQLCODE: " + e.getErrorCode() + 
                                   " SLQSTATE: " + e.getSQLState());                
            }          
        } finally {
            cerrarConexion();
        }

        return insertado;
    }
    
    /**
     * Método para el borrado de un objeto Coche en la BB.DD.
     * 
     * @param id El ID del Coche a borrar.
     * @return <i>True</i>: el Coche se ha borrado correctamente.<br>
     *         <i>False</i>: el Coche <b>no</b> se ha borrado.
     */
    @Override
    public boolean baja(int id) {
        // Abrimos la conexión con la BB.DD.
        if (!abrirConexion()) {
            return false;
        }
        // Creamos la variable que devolverá la función.
        boolean borrado = true;

        String query = "DELETE FROM COCHES WHERE ID = ?";
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
            // Si obtenemos el código correspondiente a violación de FOREIGN_KEY, es 
            // porque hemos intentado borrar un coche con pasajeros asignados.
            if (e.getErrorCode() == FOREIGN_KEY_PARENT) {
                System.out.println("Error: coche con ID " + id + " asignado a pasajeros." + 
                                   " Coche NO puede borrarse." );
            } else {
                // Mostramos el resto de errores no controlados.
                System.out.println("baja -> Error al borrar por ID: " + id + 
                                   " SQLCODE: " + e.getErrorCode() + 
                                   " SLQSTATE: " + e.getSQLState());              
            }             
        } finally {
            cerrarConexion();
        }
        return borrado;
    }

    /**
     * Método para la modificación de un objeto Coche en la BB.DD.
     * 
     * @param c Objeto Coche con los nuevos datos.
     * @return <i>True</i>: el Coche se ha modificado correctamente.<br>
     *         <i>False</i>: el Coche <b>no</b> se ha modificado.
     */
    @Override
    public boolean modificar(Coche c) {
        // Abrimos la conexión con la BB.DD.
        if (!abrirConexion()) {
            return false;
        }
        // Creamos la variable que devolverá la función.
        boolean modificado = true;

        String query = "UPDATE COCHES SET MATRICULA = ?, MARCA = ?, MODELO= ?, COLOR= ? WHERE ID = ?";
        try {
            // Preparamos la sentencia de modificación.
            PreparedStatement ps = conexion.prepareStatement(query);
            // Damos valor a los parámetros que espera la sentencia.
            ps.setString(1, c.getMatricula());
            ps.setString(2, c.getMarca());
            ps.setString(3, c.getModelo());
            ps.setString(4, c.getColor());
            ps.setInt(5, c.getId());

            // Ejecutamos la sentencia y comprobamos el número de filas afectadas.
            int numeroFilasAfectadas = ps.executeUpdate();
            // Si el número de filas es 0, la función devolverá "false". 
            if (numeroFilasAfectadas == 0)
                modificado = false;
        } catch (SQLException e) {
            modificado = false;
            // Si obtenemos el código correspondiente a duplicados, es porque hemos
            // introducido la misma matrícula en 2 coches diferentes.
            if (e.getErrorCode() == DUPLICATE_ENTRY) {
                System.out.println("Error: la matrícula " + c.getMatricula() + " ya existe. Coche NO modificado." );
            } else {
                // Mostramos el resto de errores no controlados.
                System.out.println("modificar -> Error al modificar: " + c + 
                                   " SQLCODE: " + e.getErrorCode() + 
                                   " SLQSTATE: " + e.getSQLState());                
            } 
        } finally {
            cerrarConexion();
        }

        return modificado;
    }

    /**
     * Método para la consulta de un objeto Coche en la BB.DD.
     * 
     * @param id El ID del Coche a consultar.
     * @return Objeto Coche con los datos recuperados.
     */
    @Override
    public Coche consultar(int id) {
        // Abrimos la conexión con la BB.DD.
        if (!abrirConexion()) {
            return null;
        }
        // Creamos la variable que devolverá la función.
        Coche coche = null;

        String query = "SELECT ID, MATRICULA, MARCA, MODELO, COLOR FROM COCHES WHERE ID = ?";
        try {
            // Preparamos la sentencia de modificación.
            PreparedStatement ps = conexion.prepareStatement(query);
            // Damos valor a los parámetros que espera la sentencia.
            ps.setInt(1, id);

            // Ejecutamos la sentencia y recogemos el resultado para dar
            // valores a las propiedades del objeto Coche.
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                coche = new Coche();
                coche.setId(rs.getInt(1));
                coche.setMatricula(rs.getString(2));
                coche.setMarca(rs.getString(3));
                coche.setModelo(rs.getString(4));
                coche.setColor(rs.getString(5));
            }
        } catch (SQLException e) {
            System.out.println("consutar-> Error al consultar por ID: " + id + 
                               " SQLCODE: " + e.getErrorCode() + 
                               " SLQSTATE: " + e.getSQLState());
        } finally {
            cerrarConexion();
        }

        return coche;
    }

    /**
     * Método para listar todos los coches existentes en la BB.DD.
     * 
     * @return Lista de objetos Coche almacenados en la BB.DD.
     */
    @Override
    public List<Coche> listar(String modo) {
        // Abrimos la conexión con la BB.DD.
        if (!abrirConexion()) {
            return null;
        }
        // Creamos la variable que devolverá la función.
        List<Coche> listaCoches = new ArrayList<>();
        String query = "";
        
        if (modo.equalsIgnoreCase("TODOS")) {
            query = "SELECT ID, MATRICULA, MARCA, MODELO, COLOR FROM COCHES";
        } else if (modo.equalsIgnoreCase("ASIGNADOS")) {
            query = "SELECT ID, MATRICULA, MARCA, MODELO, COLOR FROM COCHES WHERE ID IN "
                  + "(SELECT DISTINCT ID_COCHE FROM PASAJEROS WHERE ID_COCHE IS NOT NULL)";
        } else if (modo.equalsIgnoreCase("NOASIGNADOS")) {
            query = "SELECT ID, MATRICULA, MARCA, MODELO, COLOR FROM COCHES WHERE ID NOT IN "
                  + "(SELECT DISTINCT ID_COCHE FROM PASAJEROS WHERE ID_COCHE IS NOT NULL)";
        }
        
        try {
            // Preparamos la sentencia de modificación.
            PreparedStatement ps = conexion.prepareStatement(query);
            
            // Ejecutamos la sentencia y recogemos el resultado para dar
            // valores a las propiedades del objeto Coche.
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Coche Coche = new Coche();

                Coche.setId(rs.getInt(1));
                Coche.setMatricula(rs.getString(2));
                Coche.setMarca(rs.getString(3));
                Coche.setModelo(rs.getString(4));
                Coche.setColor(rs.getString(5));

                listaCoches.add(Coche);
            }
        } catch (SQLException e) {
            System.out.println("listar -> Error al consultar tabla." + 
                               " SQLCODE: " + e.getErrorCode() + 
                               " SLQSTATE: " + e.getSQLState());
            e.printStackTrace();
        } finally {
            cerrarConexion();
        }

        return listaCoches;
    }
        
}
    
