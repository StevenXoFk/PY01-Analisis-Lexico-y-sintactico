package compilador;

import compilador.generated.Lexer;
import java_cup.runtime.Symbol;
import java_cup.runtime.Scanner;

public class ReportingLexer implements Scanner {
    private final Lexer lexer;
    private final TokenReporter report;

    public ReportingLexer(Lexer lexer, TokenReporter tokenReporter) {
        this.lexer = lexer;
        this.report = tokenReporter;
    }

    @Override
    public Symbol next_token() throws Exception {
        Symbol token = lexer.next_token();
        report.register(token);
        return token;
    }
}
