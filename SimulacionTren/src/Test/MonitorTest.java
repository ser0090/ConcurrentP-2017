package Test;

import Monitor.Monitor;

import static junit.framework.Assert.*;

/**
 * Created by Tincho on 10/10/2017.
 */
public class MonitorTest {
    Monitor monitor = Monitor.getInstance();
    @org.junit.Before
    public void setUp() throws Exception {
        monitor.setRdP("C:\\Users\\Tincho\\Documents\\GitHub\\ConcurrentP-2017\\ProductorConsumidorTEST.xls");
    }

    @org.junit.After
    public void tearDown() throws Exception {
        int[][] m= monitor.getM();

        assert (m[0][0]==1 &&  //control del marcado final
                m[1][0]==0 &&
                m[2][0]==3 &&
                m[3][0]==0 &&
                m[4][0]==0 &&
                m[5][0]==1000 &&
                m[6][0]==1 &&
                m[7][0]==1 &&
                m[8][0]==0);

    }

    @org.junit.Test
    public void dispararTransicion() throws Exception {
        Thread productor = new Thread(new Productor());
        Thread consumidor= new Thread(new Consumidor());
        productor.start();
        consumidor.start();
        while(productor.getState()!= Thread.State.TERMINATED && consumidor.getState()!= Thread.State.TERMINATED) {
            int[][] m = monitor.getM();///asserciones de invariantes
            assert ((m[0][0] + m[3][0] + m[4][0]) == 1);
            assert ((m[1][0] + m[2][0]) == 3);
            assert ((m[3][0] + m[7][0]) == 1);
            assert ((m[4][0] + m[6][0]) == 1);
        }
        productor.join();
        consumidor.join();

    }

    @org.junit.Test
    public void intentarDispararTransicion() throws Exception {
        Thread productor = new Thread(new ProductorIntento());
        Thread consumidor= new Thread(new ConsumidorIntento());
        productor.start();
        consumidor.start();
        while(productor.getState()!= Thread.State.TERMINATED && consumidor.getState()!= Thread.State.TERMINATED) {
            int[][] m = monitor.getM();///asserciones de invariantes
            assert ((m[0][0] + m[3][0] + m[4][0]) == 1);
            assert ((m[1][0] + m[2][0]) == 3);
            assert ((m[3][0] + m[7][0]) == 1);
            assert ((m[4][0] + m[6][0]) == 1);
        }
        productor.join();
        consumidor.join();
    }

    class Productor implements Runnable{

        @Override
        public void run() {
            for(int i=0;i<1000;i++){
                try {
                    monitor.dispararTransicion(0);
                    Thread.sleep(Math.round(Math.random()));
                    monitor.dispararTransicion(2);
                }catch (Exception e){e.printStackTrace();}
            }
        }
    }

    class Consumidor implements Runnable{

        @Override
        public void run() {
            for(int i=0;i<1000;i++){
                try{
                    monitor.dispararTransicion(3);
                    Thread.sleep(Math.round(Math.random()));
                    monitor.dispararTransicion(1);
                }catch (Exception e){e.printStackTrace();}
            }
        }
    }

    class ProductorIntento implements Runnable{

        @Override
        public void run() {
            for(int i=0;i<1000;i++){
                try{
                    while(!monitor.intentarDispararTransicion(0));
                    Thread.sleep(Math.round(Math.random()));
                    while(!monitor.intentarDispararTransicion(2));
                }catch (Exception e){e.printStackTrace();}
            }
        }
    }

    class ConsumidorIntento implements Runnable{

        @Override
        public void run() {
            for(int i=0;i<1000;i++){

                try{
                    while(!monitor.intentarDispararTransicion(3));
                    Thread.sleep(Math.round(Math.random()));
                    while(!monitor.intentarDispararTransicion(1));
                }catch (Exception e){e.printStackTrace();}

            }
        }
    }
}