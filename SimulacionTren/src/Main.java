import Monitor.RdP;

import java.util.List;

/**
 * Created by Tincho on 17/8/2017.
 */
public class Main {
    public static void main(String[] args){
        RdP rdp = new RdP();
        System.out.println("Matriz I:");
        rdp.printI();
        System.out.printf("\n Marcado Inicial M0: \n");
        rdp.printM();
        List<Integer> sens =rdp.getSensibilizadas();
        System.out.println("Sensibilizadas");
        if(sens.size()==0){System.out.println("no hay transiciones sensibilizadas");}
        for(Integer t:sens){
            System.out.println(t);
        }
        rdp.disparar(1);
        sens =rdp.getSensibilizadas();
        System.out.println("Sensibilizadas");
        if(sens.size()==0){System.out.println("no hay transiciones sensibilizadas");}
        for(Integer t:sens){
            System.out.println(t);
        }
        rdp.disparar(2);
        sens =rdp.getSensibilizadas();
        System.out.println("Sensibilizadas");
        if(sens.size()==0){System.out.println("no hay transiciones sensibilizadas");}
        for(Integer t:sens){
            System.out.println(t);
        }
        rdp.disparar(3);
        sens =rdp.getSensibilizadas();
        System.out.println("Sensibilizadas");
        if(sens.size()==0){System.out.println("no hay transiciones sensibilizadas");}
        for(Integer t:sens){
            System.out.println(t);
        }
        rdp.disparar(0);
        sens =rdp.getSensibilizadas();
        System.out.println("Sensibilizadas");
        if(sens.size()==0){System.out.println("no hay transiciones sensibilizadas");}
        for(Integer t:sens){
            System.out.println(t);
        }
    }


}
