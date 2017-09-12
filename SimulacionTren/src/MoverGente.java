import Monitor.Monitor;

/**
 * Created by Tincho on 11/9/2017.
 */
public class MoverGente implements Runnable{
    private int TRANSICION;
    private Monitor monitor;

    public MoverGente(int TRANSICION,Monitor monitor) {
        this.TRANSICION=TRANSICION;
        this.monitor=monitor;
    }

    /**
     * mueve las personas de la transicion indicada
     */
    @Override
    public void run() {
        while(true){
            monitor.dispararTransicion(TRANSICION);
            System.out.println("token movido" + TRANSICION);
        }
    }
}
