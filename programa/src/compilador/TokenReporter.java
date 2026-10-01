package compilador;
import compilador.generated.sym;
import java_cup.runtime.Symbol;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;


public class TokenReporter implements AutoCloseable {
    private final BufferedWriter writer;

    public TokenReporter(Path ruta) throws IOException {
        Path carpeta = ruta.getParent();
        if (carpeta != null) {
            Files.createDirectories(carpeta);
        }
        writer = Files.newBufferedWriter(ruta, StandardCharsets.UTF_8);
        writer.write("LINEA\tCOLUMNA\tTOKEN\tLEXEMA");
        writer.newLine();
    }

    public void register(Symbol token) throws IOException {
        if (token == null || token.sym == sym.EOF) {
            return;
        }

        String nombreToken = sym.terminalNames[token.sym];
        String lexema = token.value != null ? token.value.toString() : "";
        lexema = escape(lexema);
        writer.write(String.format("%d\t%d\t%s\t%s", token.left, token.right, nombreToken, lexema));
        writer.newLine();
    }

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
