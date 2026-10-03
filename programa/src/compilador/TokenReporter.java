package compilador;
import compilador.generated.sym;
import java_cup.runtime.Symbol;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Clase que permite registrar los tokens generados por el lexer y escribirlos en un archivo.
 * Implementa AutoCloseable para asegurar el cierre del BufferedWriter al finalizar su uso.
 * @Entrada: Ruta del archivo donde se registrarán los tokens.
 * @Salida: Tokens generados por el lexer, registrados en el archivo especificado.
 * @Restricciones:
 * - La ruta del archivo debe ser válida y accesible para escritura.
 * - El lexer debe estar correctamente generado y disponible en el classpath.
 * @Objetivo: Proporcionar un mecanismo para registrar los tokens generados por el lexer
 * y almacenarlos en un archivo para su análisis posterior.
 */
public class TokenReporter implements AutoCloseable {
    private final BufferedWriter writer;

    public TokenReporter(Path ruta) throws IOException {
        Path carpeta = ruta.getParent();
        if (carpeta != null) {
            Files.createDirectories(carpeta);
        }
        writer = Files.newBufferedWriter(ruta, StandardCharsets.UTF_8);
        writer.write(String.format("%-8s%-10s%-25s%-30s","Linea", "Columna", "Token", "Lexema"));
        writer.newLine();
    }

    /**
     * Registra un token generado por el lexer en el archivo.
     * @Entrada: Token generado por el lexer.
     * @Salida: Se escribe el token en el archivo especificado.
     */
    public void register(Symbol token) throws IOException {
        if (token == null || token.sym == sym.EOF) {
            return;
        }

        String nombreToken = sym.terminalNames[token.sym];
        String lexema = token.value != null ? token.value.toString() : "";
        lexema = escape(lexema);
        writer.write(String.format("%-8s%-10s%-25s%-30s", token.left, token.right, nombreToken, lexema));
        writer.newLine();
    }

    /**
     * Escapa caracteres especiales en el lexema para su correcta visualización.
     * @Entrada: Lexema a escapar.
     * @Salida: Lexema con caracteres especiales escapados.
     */
    private String escape(String lexema) {
        return lexema.replace("\n", "\\n")
                     .replace("\t", "\\t")
                     .replace("\r", "\\r");
    }

    @Override
    public void close() throws IOException {
        writer.close();
    }
}
