
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.silo.modelos.dao.PeliculaDAO;

public class NewClass {

    public static void main(String[] args) throws IOException {
        try {
            throw new RuntimeException("Excepcion de prueba");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(Long.MAX_VALUE);
        System.out.println(new Long(Long.MAX_VALUE).doubleValue());
        System.out.println(Float.MAX_VALUE);
        System.out.println(new Float(Float.MAX_VALUE).doubleValue());
//        System.out.println(pruebaExcepcion(true));
        Iterable<Path> dirs = FileSystems.getDefault().getRootDirectories();
        for (Path name : dirs) {
            System.err.println(name);
        }
        Path p = Files.createTempDirectory("temp2");
        System.out.println(p.toFile().getAbsolutePath());
        Path p2 = new File(p.toFile().getAbsolutePath() + "/NewClass.java").toPath();
        Files.copy(new File("src/NewClass.java").toPath(),p2);
        try {
            Thread.sleep(15000);
        } catch (InterruptedException ex) {
            Logger.getLogger(NewClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        Files.delete(p2);
        Files.delete(p);
    }

    public static boolean pruebaExcepcion(boolean v) {
        boolean resultado = false;
        try {
            resultado = true;
            if (v) {
                throw new Exception();
            }
        } catch (Exception e) {
            Logger.getLogger(PeliculaDAO.class.getName()).log(Level.SEVERE, null, e);
            throw new RuntimeException(e);
        }
        return resultado;
    }
}
