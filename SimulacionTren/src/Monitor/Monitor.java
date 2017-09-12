package Monitor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * Created by Tincho on 17/8/2017.
 */

 public class Monitor { // clase SINGLETON
    public RdP rdp;
    private Politica politica;
    private Transicion transiciones[];
    private static final Monitor instance = new Monitor();
    private Semaphore semaphore;

    private Monitor(){
        rdp = new RdP();
        politica = new Politica();
        int ct = rdp.cantidadDeTransiciones();//inicio el vector de variables condicion
        transiciones= new Transicion[ct];
        for(int i=0;i<ct;i++){ //inicializo las variables condicion
            transiciones[i]=new Transicion();
        }
        semaphore = new Semaphore(1,true);
    }
    public static Monitor getInstance(){return instance;}

    /**
     * dispara la transicion indicada sobre la RdP cargada. si la transicion no esta sensibilizada, el hilo se encola(wait) en la variable condicion.
     * @param transicion
     */
    public void dispararTransicion(int transicion) {
        while (true) {
            try {
                semaphore.acquire();
            }catch (InterruptedException e){dispararTransicion(transicion);return;}

            int resultado = rdp.disparar(transicion);
            if (resultado == 0) {//si se pudo disparar verifica el estado de la red
                List<Integer> st=rdp.getSensibilizadas(); //obtengo las transiciones sensibilizadas
                try {
                    List<Integer> dt= filtroTransciones(st); //filtro las tranciciones q tienen hilos en espera
                    int transicionADespertar=politica.cualDisparar(dt);//la politica decide cual variable condicion liberar
                    transiciones[transicionADespertar].resume();
                }catch(IndexOutOfBoundsException e){}
                semaphore.release();
                return;
            }
            //si no se pudo disparar se encola en la VC
            try {
                semaphore.release();
                transiciones[transicion].delay(resultado);
            }catch(InterruptedException e){}
        }

    }

    /**
     * similar al "dispararTransicion(int transicion)" pero si la transicion no se puede disparar
     * el hilo no se duerme, retornara con un false.
     * @param transicion
     * @return false, si la transicion no se puede disparar. true si la transicion se disparo con exito.
     */
    public  boolean intentarDispararTransicion(int transicion){
        try {
            semaphore.acquire();
        }catch (InterruptedException e){return intentarDispararTransicion(transicion);}

            int resultado = rdp.disparar(transicion);
            if (resultado == 0) {//si se pudo disparar verifica el estado de la red
                List<Integer> st=rdp.getSensibilizadas(); //obtengo las transiciones sensibilizadas
                try {
                    List<Integer> dt= filtroTransciones(st); //filtro las tranciciones q tienen hilos en espera
                    int transicionADespertar=politica.cualDisparar(dt);//la politica decide cual variable condicion liberar
                    transiciones[transicionADespertar].resume();
                }catch(IndexOutOfBoundsException e){}
                semaphore.release();
                return true;
            }
            semaphore.release();
            return false;
        }


    /**
     * retorna las transiciones indicadas que ademas tienen hilos en la cola.
     * @param st
     * @return
     */
    private List<Integer> filtroTransciones(List<Integer> st) { // lo que en el diagrama de secuencia se llama quienesEstan()
        List<Integer>dt = new ArrayList<>();
        for(Integer i : st){
            if(!transiciones[i].empty()){
                dt.add(i);
            }
        }
        return dt;
    }
}
