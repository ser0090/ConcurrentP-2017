import Monitor.Monitor;

import java.io.PrintStream;

/**
 * Created by Tincho on 17/8/2017.
 */
public class Vagon implements Runnable {
    private Monitor monitor;
    private PrintStream out;

    public Vagon(Monitor monitor, PrintStream out) {
        this.monitor = monitor;
        this.out=out;
    }

    @Override
    public void run() {
        final String log = "12";
        while(true){
            monitor.dispararTransicion(28);
            out.println(log);
            System.out.println("\033[32mDisparoVagon\033[37m");
        }
    }
}
