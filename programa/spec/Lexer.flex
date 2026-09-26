package compilador.generador;

import java_cup.runtime.Symbol;
import compilador.ErrorReporter;

%%

%class Lexer
%public
%unicode
%cup
%line
%column

%{
    private Symbol symbol(int type) {
        return new Symbol(type, yyline + 1, yycolumn + 1);
    }
}

%%

[ \t\n\r\f]+ {
    // Ignorar espacios en blanco
}

[^] { 
    ErrorReporter.lexico("Caracter no reconocido '" + yytext() + "'", yycolumn +1, yyline +1); 
}