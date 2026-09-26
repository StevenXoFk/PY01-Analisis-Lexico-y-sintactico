package compilador;

//import compilador.generador.Lexer;
//import compilador.generador.Parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;


public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Uso: run.bat <ruta_archivo_fuente>");
            System.exit(1);
        }

        File archivoFuente = new File(args[0]);
        if (!archivoFuente.exists()) {
            System.err.println("El archivo fuente no existe: " + args[0]);
            System.exit(1);
        }

        System.out.println("Analizando: " + archivoFuente.getPath());
        System.out.println();

        try (Reader lector = new BufferedReader(new InputStreamReader(
            new FileInputStream(archivoFuente), StandardCharsets.UTF_8))) {
                // encargese de crear el lexer y parser para analizar el archivo fuente lol y lo pone aca 
        } catch (Exception e) {
            System.out.println("El analisis se detuvo" + e.getMessage());
            System.out.println();
        }

        ErrorReporter.mostrarErrores();
        System.out.println();

        if (ErrorReporter.hayErrores()) {
            System.out.println("El analisis finalizo con errores");
        } else {
            System.out.println("El analisis finalizo sin errores");
        }
    }
}
