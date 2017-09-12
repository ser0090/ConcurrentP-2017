import Monitor.Monitor;

/**
 * Created by Tincho on 17/8/2017.
 */
public class Generador implements Runnable {
    private int GENERAR_TOKEN;
    private Monitor monitor;

    /**
     *
     * @param GENERAR_TOKEN transicion a disparar
     */
    public Generador(int GENERAR_TOKEN,Monitor monitor) {
        this.GENERAR_TOKEN = GENERAR_TOKEN;
        this.monitor = monitor;
    }

    @Override
    public void run() {
    while(true){
        monitor.dispararTransicion(GENERAR_TOKEN);
        System.out.println("Generador : " + GENERAR_TOKEN);
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e){}
    }
    }
}
