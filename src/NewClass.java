
import java.util.logging.Level;
import java.util.logging.Logger;
import org.silo.modelos.dao.PeliculaDAO;


public class NewClass {

    public static void main(String[] args) {
        try {
            throw new RuntimeException("Excepcion de prueba");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(Long.MAX_VALUE);
        System.out.println(new Long(Long.MAX_VALUE).doubleValue());
        System.out.println(Float.MAX_VALUE);
        System.out.println(new Float(Float.MAX_VALUE).doubleValue());
        System.out.println(pruebaExcepcion(true));
    }

    public static boolean pruebaExcepcion(boolean v) {
        boolean resultado = false;
        try {
            resultado = true;
            if(v)
            throw new Exception();
        } catch (Exception e) {
            Logger.getLogger(PeliculaDAO.class.getName()).log(Level.SEVERE, null, e);
            throw new RuntimeException(e);
        }
        return resultado;
    }
}
