import Monitor.RdP;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.Buffer;
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
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input;
        while(true) {
            System.out.println("Disparar Transicion:");
            try {
                input = br.readLine();
            }catch (Exception e){e.printStackTrace();input="";}
            if(input.equals("exit") || input.equals("")){return;}
            rdp.disparar(Integer.parseInt(input));
            sens = rdp.getSensibilizadas();
            System.out.println("Sensibilizadas");
            if (sens.size() == 0) {
                System.out.println("no hay transiciones sensibilizadas");
            }
            for (Integer t : sens) {
                System.out.println(t);
            }
            System.out.println("Marcado");
            rdp.printM();

        }
    }


}
