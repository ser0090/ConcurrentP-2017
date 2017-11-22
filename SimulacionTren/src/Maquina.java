import Monitor.Monitor;

import java.io.PrintStream;

/**
 * Created by Tincho on 17/8/2017.
 */
public class Maquina implements Runnable {
    private Monitor monitor;
    private PrintStream out;

    public Maquina(Monitor monitor,PrintStream out) {
        this.monitor = monitor;
        this.out=out;
    }

    @Override
    public void run() {
        final String log = "13";
        while(true){
            monitor.dispararTransicion(29);
            out.println(log);
            System.out.println("\033[32mDisparo Maquina\033[37m");
        }
    }
}
