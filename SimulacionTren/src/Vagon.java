import Monitor.Monitor;

/**
 * Created by Tincho on 17/8/2017.
 */
public class Vagon implements Runnable {
    private Monitor monitor;

    public Vagon(Monitor monitor) {
        this.monitor = monitor;
    }

    @Override
    public void run() {
        while(true){
            monitor.dispararTransicion(28);
            System.out.println("\033[32mDisparoVagon\033[37m");
        }
    }
}
