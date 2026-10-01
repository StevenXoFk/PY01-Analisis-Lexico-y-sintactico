@echo off

cd /d "%~dp0"
if "%~1"=="0" (
    for %%f in (tests\validos\*.txt) do (
        chcp 65001 > nul
        java -Dfile.encoding=UTF-8 -Dstdout.encoding=UTF-8 ^
             -cp "bin;lib\java-cup-11b-runtime.jar" compilador.PruebaLexer "%%f"
        timeout /t 1 > nul
    )
) else if "%~1"=="1" (
    for %%f in (tests\errores_lexicos\*.txt) do (
        chcp 65001 > nul
        java -Dfile.encoding=UTF-8 -Dstdout.encoding=UTF-8 ^
             -cp "bin;lib\java-cup-11b-runtime.jar" compilador.PruebaLexer "%%f"
        timeout /t 1 > nul
    )
) else if "%~1"=="2" (
    for %%f in (tests\mixtos\*.txt) do (
        chcp 65001 > nul
        java -Dfile.encoding=UTF-8 -Dstdout.encoding=UTF-8 ^
             -cp "bin;lib\java-cup-11b-runtime.jar" compilador.PruebaLexer "%%f"
        timeout /t 1 > nul
    )
) else (
    echo Uso: validos: tester.bat 0
    echo Uso: errores lexicos: tester.bat 1
    echo Uso: mixtos: tester.bat 2
    exit /b 1
)
echo Pruebas completadas.