package TaskExecutor;

import TaskExecutor.Acciones;

import java.io.PrintStream;

/**
 * Created by Tincho on 14/11/2017.
 */


public class Logs implements Acciones {
    private String message;
    private PrintStream out;
    public Logs(String message, PrintStream out) {
        this.message=message;
        this.out=out;
    }

    @Override
    public void ejecutar() {
        out.println(message);
        System.out.println(message);

    }

}
