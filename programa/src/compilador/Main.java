package compilador;

import compilador.generated.Lexer;
import compilador.generated.Parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;


public class Main {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Uso: run.sh <ruta_archivo_fuente>");
            System.exit(2);
        }

        File archivoFuente = new File(args[0]);
        if (!archivoFuente.exists() || !archivoFuente.isFile()) {
            System.err.println("El archivo fuente no existe o no es un archivo: " + args[0]);
            System.exit(2);
        }

        String nombreBase = archivoFuente.getName().replaceFirst("[.][^.]+$", "");
        Path rutaTokens = Path.of("salida", nombreBase + "_tokens.txt");
        Path rutaErrores = Path.of("salida", nombreBase + "_errores.txt");

        System.out.println("Analizando: " + archivoFuente.getPath());
        System.out.println();

        ErrorReporter.limpiarErrores();

        try (Reader lector = new BufferedReader(new InputStreamReader(
                new FileInputStream(archivoFuente), StandardCharsets.UTF_8));
             TokenReporter tokenReporter = new TokenReporter(rutaTokens)) {

            Lexer lexer = new Lexer(lector);
            ReportingLexer reportingLexer = new ReportingLexer(lexer, tokenReporter);
            Parser parser = new Parser(reportingLexer);
            parser.parse();

        } catch (Exception e) {
            ErrorReporter.sintactico(
                    "Excepción durante el análisis: " + e.getMessage(), 0, 0);
        } catch (Throwable t) {
            ErrorReporter.sintactico(
                    "Error fatal durante el análisis: " + t.getMessage(), 0, 0);
        }

        ErrorReporter.mostrarErrores();

        System.out.println();

        if (ErrorReporter.hayErrores()) {
            System.out.println("Se genero errores durante el análisis.");
            System.out.println("Total de errores: " + ErrorReporter.getErrores().size());
            System.exit(1);
        } else {
            System.out.println("Se completó el análisis sin errores.");
            System.exit(0);
        }
    }
}