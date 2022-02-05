package modelo.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase que contiene todas los métodos y propiedades comunes a los DAO relacionados con
 * el concesionario.
 * 
 * @author Ignacio Pineda Martín
 *
 */
public class DAOConcesionarioMySql {
    
    /**
     * Constante para controlar posibles duplicados en la BB.DD.
     */
    protected static final int DUPLICATE_ENTRY = 1062;
    
    /**
     * Constante para controlar violaciones de referencia en la BB.DD.
     */
    protected static final int FOREIGN_KEY_PARENT = 1451;
    
    protected Connection conexion;

    /**
     * Método para la apertura de la conexión con la BB.DD.
     * 
     * @return <i>True</i>: la conexión se ha abierto.<br>
     *         <i>False</i>: la conexión <b>no</b> se ha abierto.
     */
    protected boolean abrirConexion() {
        String url = "jdbc:mysql://localhost:3306/concesionario";
        String usuario = "root";
        String password = "";

        try {
            conexion = DriverManager.getConnection(url, usuario, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Método para el cierre de la conexión con la BB.DD.
     * 
     * @return <i>True</i>: la conexión se ha cerrado.<br>
     *         <i>False</i>: la conexión <b>no</b> se ha cerrado.
     */
    protected boolean cerrarConexion() {
        try {
            conexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    /**
     * Método para recuperar el último ID asignado automáticamente por la BB.DD
     * 
     * @return El último ID asignado automáticamente por la BB.DD.<br>
     *         -1 en caso de no poder recuperar la información.
     */
    protected int getLastInsertID() {
        // Creamos la variable que devolverá la función.
        int maxID = -1;

        String query = "SELECT LAST_INSERT_ID();";
        try {
            // Preparamos la sentencia de modificación.
            PreparedStatement ps = conexion.prepareStatement(query);

            // Ejecutamos la sentencia y recogemos el resultado.
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                maxID = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("getLastInsertID-> Error al consultar último ID: " +
                               " SQLCODE: " + e.getErrorCode() + 
                               " SLQSTATE: " + e.getSQLState());
        }

        return maxID;
    }
    
}
