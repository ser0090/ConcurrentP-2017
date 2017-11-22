package TaskExecutor;

import Monitor.Monitor;
import TaskExecutor.Acciones;

import java.util.List;
import java.util.Map;

/**
 * Created by Tincho on 14/11/2017.
 */
public class Task implements Runnable{
    private Map<Integer, Acciones> acciones;
    private List<Integer> transiciones;
    private boolean infiniteLoop;
    private Monitor monitor;

    public Task(Map<Integer,Acciones> acciones , List<Integer> transiciones,Monitor monitor, boolean infiniteLoop) {
        this.transiciones=transiciones;
        this.acciones = acciones;
        this.monitor=monitor;
        this.infiniteLoop=infiniteLoop;
    }

    @Override
    public void run() {
        do{
            for (int transicion : transiciones) {
                monitor.dispararTransicion(transicion);
                acciones.get(transicion).ejecutar();
            }
        }while (infiniteLoop);
    }
}
