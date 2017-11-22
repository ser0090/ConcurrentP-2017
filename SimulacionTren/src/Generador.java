import Monitor.Monitor;

import java.io.PrintStream;

/**
 * Created by Tincho on 17/8/2017.
 */
public class Generador implements Runnable {
    private int GENERAR_TOKEN;
    private Monitor monitor;
    private PrintStream out;

    /**
     *
     * @param GENERAR_TOKEN transicion a disparar
     */
    public Generador(int GENERAR_TOKEN,Monitor monitor,PrintStream out) {
        this.GENERAR_TOKEN = GENERAR_TOKEN;
        this.monitor = monitor;
        this.out=out;
    }

    @Override
    public void run() {
        final String log = "14";
    while(true){
        monitor.dispararTransicion(GENERAR_TOKEN);
        out.println(log);
        System.out.println("Generador : " + GENERAR_TOKEN);

    }
    }
}
