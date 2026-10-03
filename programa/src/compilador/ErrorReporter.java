package compilador;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ErrorReporter {
    public static class ErrorAnalisis {
        public final String mensaje;
        public final int linea;
        public final int columna;
        public final String tipo;

        public ErrorAnalisis(String mensaje, int linea, int columna, String tipo) {
            this.mensaje = mensaje;
            this.linea = linea;
            this.columna = columna;
            this.tipo = tipo;
        }

        @Override
        public String toString() {
            return String.format("%s: %s en la línea %d, columna %d", tipo, mensaje, linea, columna);
        }
    }

    private static final List<ErrorAnalisis> errores = new ArrayList<>();

    public static void lexico(String msj, int columna, int linea) {
        errores.add(new ErrorAnalisis(msj, linea, columna, "ERROR LÉXICO"));
    }

    public static void sintactico(String msj, int columna, int linea) {
        errores.add(new ErrorAnalisis(msj, linea, columna, "ERROR SINTÁCTICO"));
    }

    public static void semantico(String msj, int columna, int linea) {
        errores.add(new ErrorAnalisis(msj, linea, columna, "ERROR SEMÁNTICO"));
    }

    public static boolean hayErrores() {
        return !errores.isEmpty();
    }

    public static List<ErrorAnalisis> getErrores() {
        List<ErrorAnalisis> copia = new ArrayList<>(errores);
        copia.sort(Comparator.comparingInt((ErrorAnalisis e) -> e.linea).thenComparingInt(e -> e.columna));
        return copia;
    }

    public static void mostrarErrores() {
        if (errores.isEmpty()) {
            System.out.println("No se encontraron errores.");
            return;
        }
        System.out.println("Se encontraron " + errores.size() + " error(es):");
        for (ErrorAnalisis error : getErrores()) {
            System.out.println(error);
        }
    }

    public static void limpiarErrores() {
        errores.clear();
    }
}
