package Monitor;

import java.util.List;

/**
 * Created by Tincho on 13/11/2017.
 */
public class PoliticaPrioridad implements Politica {
    private int[] prioritarias;

    public PoliticaPrioridad(int[] prioritarias){
        this.prioritarias=prioritarias;
    }
    @Override
    public int cualDisparar(List<Integer> dt) {
     for(int i=0;i<prioritarias.length;i++){
         if(dt.contains(prioritarias[i])){
             return prioritarias[i];
         }
     }
     return randomElection(dt);
    }

    private int randomElection(List<Integer> dt) {
        if(dt.size()!=0) {
            int rd = (int) (Math.random()*100);
            return dt.get(rd % dt.size());
        }
        return dt.get(0);
    }
}
