package Monitor;

/**
 * Created by Tincho on 31/8/2017.
 */
public class Transicion {
    int enCola=0;

    public void delay() throws InterruptedException {
    enCola++;
    super.wait();
    enCola--;
    }
    public void resume(){
        notifyAll();
    }

    public boolean empty(){
        return enCola==0;
    }
}
