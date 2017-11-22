package TaskExecutor;

import Monitor.Monitor;

import java.util.List;
import java.util.Map;

/**
 * Created by Tincho on 14/11/2017.
 */
public class Task implements Runnable{
    private Map<Integer, Accion> acciones;
    private List<Integer> transiciones;
    private boolean infiniteLoop;
    private static Monitor monitor= Monitor.getInstance();
    private Accion accionInicial;

    public Task(Map<Integer, Accion> acciones , List<Integer> transiciones, Accion accionInicial , boolean infiniteLoop) {
        this.transiciones=transiciones;
        this.acciones = acciones;
        this.monitor=monitor;
        this.infiniteLoop=infiniteLoop;
        this.accionInicial=accionInicial;
    }

    @Override
    public void run() {
        if(accionInicial!=null)accionInicial.ejecutar();
        do{
            for (int transicion : transiciones) {
                monitor.dispararTransicion(transicion);
                Accion action =acciones.get(transicion);
                if(action!=null){action.ejecutar();}
            }
        }while (infiniteLoop);
    }
}
