package TaskExecutor;

import Monitor.Monitor;

import java.io.PrintStream;
import java.util.List;

/**
 * Created by Tincho on 22/11/2017.
 */

/**
 * Esta Accion permite que se dispare una transicion perteneciente a un conjunto.
 * intentando dispararlas hasta que una logra ser disparada.
 * Ademas permite imprimir un mensaje en un archivo a travez de un PrintStream
 */
public class ForkAndJoin implements Accion {

    private List<Integer> transiciones;
    private Monitor monitor;
    private String log;
    private PrintStream out;

    public ForkAndJoin(List<Integer> transiciones, Monitor monitor, String log, PrintStream out) {
        this.transiciones = transiciones;
        this.monitor = monitor;
        this.log = log;
        this.out = out;
    }

    @Override
    public void ejecutar() {

        while (true) {
            for (int transicion : transiciones) {
                if(monitor.intentarDispararTransicion(transicion)){
                    if(out !=null){out.println(log);System.out.println(log);}
                    return;
                }
            }
        }
    }
}
