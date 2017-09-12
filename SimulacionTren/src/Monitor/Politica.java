package Monitor;

import java.util.List;

/**
 * Created by Tincho on 31/8/2017.
 */
public class Politica {
    public int cualDisparar(List<Integer> dt) {
        if(dt.size()!=0) {
            int rd = (int) (Math.random()*100);
            return dt.get(rd % dt.size());
        }
        return dt.get(0);
    }
}
