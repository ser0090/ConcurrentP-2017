import Monitor.Monitor;

import java.io.PrintStream;

/**
 * Created by Tincho on 17/8/2017.
 */
public class Barrera implements Runnable {
    private Monitor monitor;
    private  int SUBIR_BARRERA = 4;//transicion que sube la barrera AB
    private  int BAJAR_BARRERA = 5;//transicion que baja la barrera AB
    private PrintStream out;

    /**
     *
     * @param SUBIR_BARRERA transicion a disparar para subir la barrera
     * @param BAJAR_BARRERA transicion a disparar para bajar la barrera
     */
    public Barrera(int SUBIR_BARRERA, int BAJAR_BARRERA,Monitor monitor,PrintStream out){
        this.monitor=monitor;
        this.BAJAR_BARRERA=BAJAR_BARRERA;
        this.SUBIR_BARRERA=SUBIR_BARRERA;
        this.out=out;
    }
    @Override
    public void run() {
        while(true) {
            monitor.dispararTransicion(BAJAR_BARRERA);
            //out.println("Barrera Baja");
            System.out.println("\033[46mBarrera Baja\033[37m");
            monitor.dispararTransicion(SUBIR_BARRERA);
            System.out.println("\033[46mBarrera Alta\033[37m");
            //out.println("Barrera Alta");
        }
    }
}
