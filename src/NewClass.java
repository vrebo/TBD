
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NewClass {

    public static void main(String[] args) {
        // TODO code application logic here

        final List<String> baseCmds = new ArrayList<>();
        baseCmds.add("C:\\Program Files\\PostgreSQL\\9.3\\bin\\pg_dump");
        baseCmds.add("-h");
        baseCmds.add("localhost");
        baseCmds.add("-p");
        baseCmds.add("5432");
        baseCmds.add("-U");
        baseCmds.add("postgres");
        baseCmds.add("-b");
        baseCmds.add("-v");
        baseCmds.add("-F=p");
        baseCmds.add("C:\\Users\\Vrebo\\Documents\\Backup.sql");
        baseCmds.add("SILO");
        final ProcessBuilder pb = new ProcessBuilder(baseCmds);

        final Map<String, String> env = pb.environment();
        env.put("PGPASSWORD", "1");

        try {
            final Process process = pb.start();

            final BufferedReader r = new BufferedReader(
                    new InputStreamReader(process.getErrorStream()));
            String line = r.readLine();
            while (line != null) {
                System.err.println(line);
                line = r.readLine();
            }
            r.close();

            final int dcertExitCode = process.waitFor();
            System.out.println("Huebos!!!");
        } catch (IOException | InterruptedException e) {
            System.out.println(e + "\n" + e.getMessage());
        }

    }
}
