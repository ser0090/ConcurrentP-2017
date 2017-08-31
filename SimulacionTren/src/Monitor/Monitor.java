package Monitor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tincho on 17/8/2017.
 */

 public class Monitor { // clase SINGLETON
    private RdP rdp;
    private Politica politica;
    private Transicion transiciones[];
    private static final Monitor instance = new Monitor();

    private Monitor(){
        rdp = new RdP();
        politica = new Politica();
        int ct = rdp.cantidadDeTransiciones();//inicio el vector de variables condicion
        transiciones= new Transicion[ct];
        for(int i=0;i<ct;i++){ //inicializo las variables condicion
            transiciones[i]=new Transicion();
        }
    }
    public static Monitor getInstance(){return instance;}

    public synchronized void dispararTransicion(int transicion) {
        while (true) {
            if (rdp.disparar(transicion)) {//si se pudo disparar verifica el estado de la red
                List<Integer> st=rdp.getSensibilizadas(); //obtengo las transiciones sensibilizadas
                try {
                    List<Integer> dt= filtroTransciones(st); //filtro las tranciciones q tienen hilos en espera
                    int transicionADespertar=politica.cualDisparar(dt);//la politica decide cual variable condicion liberar
                    transiciones[transicionADespertar].resume();
                }catch(NullPointerException e){}
                return;
            }
            //si no se pudo disparar se encola en la VC
            try {
                transiciones[transicion].delay();
            }catch(InterruptedException e){}
        }
    }

    private List<Integer> filtroTransciones(List<Integer> st) { // lo que en el diagrama de secuencia se llama quienesEstan()
        List<Integer>dt = new ArrayList<>();
        for(Integer i : st){
            if(!transiciones[i.intValue()].empty()){
                dt.add(i);
            }
        }
        return dt;
    }
}
