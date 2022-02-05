package modelo.persistencia.interfaz;

import java.util.List;
import modelo.entidad.Pasajero;

/**
 * Definimos los métodos para ejecutar las operaciones CRUD de un objeto
 * <i>Pasajero</i>.
 * 
 * @author Ignacio Pineda Martín
 *
 */
public interface DAOPasajero {

    public boolean alta(Pasajero p);

    public boolean baja(int id);

    public boolean modificar(Pasajero p);

    public Pasajero consultar(int id);

    public List<Pasajero> listar(String modo);
    
    public List<Pasajero> listar(int idCoche);
    
}