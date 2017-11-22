package TaskExecutor;

import java.io.PrintStream;

/**
 * Created by Tincho on 14/11/2017.
 */

/**
 * esta accion permite imprimir un mensaje en un archivo a travez de un PrintSream
 */
public class Log implements Accion {
    private String message;
    private PrintStream out;
    public Log(String message, PrintStream out) {
        this.message=message;
        this.out=out;
    }

    @Override
    public void ejecutar() {
        out.println(message);
        System.out.println(message);

    }

}
