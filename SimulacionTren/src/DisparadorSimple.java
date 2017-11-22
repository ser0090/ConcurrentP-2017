import Monitor.Monitor;

import java.io.PrintStream;

/**
 * Created by Tincho on 11/9/2017.
 */
public class DisparadorSimple implements Runnable{
    private int TRANSICION;
    private Monitor monitor;
    private String mensaje;
    private PrintStream out;

    public DisparadorSimple(int TRANSICION, Monitor monitor, String mensaje, PrintStream out) {
        this.TRANSICION=TRANSICION;
        this.monitor=monitor;
        this.mensaje=mensaje;
        this.out=out;
    }

    /**
     * mueve las personas de la transicion indicada
     */
    @Override
    public void run() {
        while(true){
            monitor.dispararTransicion(TRANSICION);
            out.println(mensaje);
            System.out.println(mensaje);
            if(Thread.interrupted()){return;}
        }
    }
}
