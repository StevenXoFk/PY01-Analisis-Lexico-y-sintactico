package compilador;

import compilador.generated.Lexer;
import compilador.generated.sym;
import java_cup.runtime.Symbol;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

public class PruebaLexer {
    public static void main(String[] args) throws Exception {
        Reader lector = new BufferedReader(new InputStreamReader(
            new FileInputStream(args[0]), StandardCharsets.UTF_8
        ));

        Lexer lexer = new Lexer(lector);
        Symbol t = lexer.next_token();

        while (t.sym != sym.EOF) {
            System.out.printf("%4d:%-4d %-18s %s%n", 
            t.left, t.right, sym.terminalNames[t.sym], t.value);

            t = lexer.next_token();
        }

        ErrorReporter.mostrarErrores();
    }
}
