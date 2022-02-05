package modelo.persistencia.interfaz;

import java.util.List;
import modelo.entidad.Coche;

/**
 * Definimos los métodos para ejecutar las operaciones CRUD de un objeto <i>Coche</i>.
 * 
 * @author Ignacio Pineda Martín
 *
 */
public interface DAOCoche {

    public boolean alta(Coche c);

    public boolean baja(int id);

    public boolean modificar(Coche c);

    public Coche consultar(int id);

    public List<Coche> listar(String modo);
    
}