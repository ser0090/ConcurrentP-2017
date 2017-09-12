import Monitor.Monitor;

/**
 * Created by Tincho on 17/8/2017.
 */
public class Maquina implements Runnable {
    private Monitor monitor;

    public Maquina(Monitor monitor) {
        this.monitor = monitor;
    }

    @Override
    public void run() {
        while(true){
            monitor.dispararTransicion(29);
            System.out.println("\033[32mDisparo Maquina\033[37m");
        }
    }
}
