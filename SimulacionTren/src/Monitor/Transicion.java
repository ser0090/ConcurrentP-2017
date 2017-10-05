package Monitor;

/**
 * Created by Tincho on 31/8/2017.
 */
public class Transicion{
    int enCola=0;

    /**
     *
     * @param tiempo si la transicion es temporal y falta para disparar la transicion representa el tiempo en ms que falta,
     *               si la transicion no estaba sensibilizada o es inmediata tiempo debe ser <=0
     * @throws InterruptedException
     */
    public synchronized void delay(int tiempo) throws InterruptedException {

        if(tiempo>0 & empty()){
            enCola++;
            wait(tiempo);}
        else{
            enCola++;
            wait();}
        enCola--;
    }

    public void delay() throws InterruptedException{
        enCola++;
        super.wait();
        enCola--;
    }

    public synchronized void resume(){
        notify();
    }


    public boolean empty(){
        return enCola==0;
    }
}
