import Monitor.Monitor;

/**
 * Created by Tincho on 17/8/2017.
 */
public class Barrera implements Runnable {
    Monitor monitor;
    private  int SUBIR_BARRERA = 4;//transicion que sube la barrera AB
    private  int BAJAR_BARRERA = 5;//transicion que baja la barrera AB

    /**
     *
     * @param SUBIR_BARRERA transicion a disparar para subir la barrera
     * @param BAJAR_BARRERA transicion a disparar para bajar la barrera
     */
    public Barrera(int SUBIR_BARRERA, int BAJAR_BARRERA,Monitor monitor){
        this.monitor=monitor;
        this.BAJAR_BARRERA=BAJAR_BARRERA;
        this.SUBIR_BARRERA=SUBIR_BARRERA;
    }
    @Override
    public void run() {
        while(true) {
            monitor.dispararTransicion(BAJAR_BARRERA);
            monitor.dispararTransicion(SUBIR_BARRERA);
        }
    }
}
