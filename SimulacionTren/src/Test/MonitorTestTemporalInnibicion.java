package Test;

import Monitor.Monitor;

import java.util.GregorianCalendar;

import static org.junit.Assert.*;

/**
 * Created by Tincho on 13/10/2017.
 */
public class MonitorTestTemporalInnibicion {
    Monitor monitor = Monitor.getInstance();
    @org.junit.Before
    public void setUp() throws Exception {
        monitor.setRdP("C:\\Users\\Tincho\\Documents\\GitHub\\ConcurrentP-2017\\ProductorConsumidorPeriodicoInnibicionTEST.xls");
    }

    @org.junit.After
    public void tearDown() throws Exception {
        int[][] m= monitor.getM();

         //control del marcado final
        assert (m[0][0]==0);
        assert (m[1][0]==3);
        assert (m[2][0]==0);
        assert (m[3][0]==0);
        assert (m[4][0]==10);
        assert (m[5][0]==1);
        assert (m[6][0]==1);
        assert (m[7][0]==0);
        assert (m[8][0]==0);

    }

    @org.junit.Test
    public void dispararTransicionTest() throws Exception {
        Thread productor = new Thread(new Productor());
        Thread consumidor= new Thread(new Consumidor());
        Thread generador = new Thread(new Generador());
        productor.start();
        consumidor.start();
        generador.start();
        while(productor.getState()!= Thread.State.TERMINATED && consumidor.getState()!= Thread.State.TERMINATED && generador.getState()!= Thread.State.TERMINATED) {
            int[][] m = monitor.getM();///asserciones de invariantes
            assert ((m[0][0] + m[1][0]) == 3);
            assert ((m[2][0] + m[6][0]) == 1);
            assert ((m[3][0] + m[5][0]) == 1);
        }
        productor.join();
        consumidor.join();

    }

    @org.junit.Test
    public void intentarDispararTransicion() throws Exception {
        Thread productor = new Thread(new ProductorIntento());
        Thread consumidor= new Thread(new ConsumidorIntento());
        Thread generador = new Thread(new GeneradorIntento());
        productor.start();
        consumidor.start();
        generador.start();
        while(productor.getState()!= Thread.State.TERMINATED && consumidor.getState()!= Thread.State.TERMINATED && generador.getState()!= Thread.State.TERMINATED) {
            int[][] m = monitor.getM();///asserciones de invariantes
            assert ((m[0][0] + m[1][0]) == 3);
            assert ((m[2][0] + m[6][0]) == 1);
            assert ((m[3][0] + m[5][0]) == 1);
        }
        productor.join();
        consumidor.join();
    }

    class Productor implements Runnable{

        @Override
        public void run() {
            for(int i=0;i<10;i++){
                try {
                    monitor.dispararTransicion(0);
                    //Thread.sleep(Math.round(Math.random()));
                    monitor.dispararTransicion(2);
                }catch (Exception e){e.printStackTrace();}
            }
        }
    }

    class Consumidor implements Runnable{

        @Override
        public void run() {
            for(int i=0;i<10;i++){
                try{
                    monitor.dispararTransicion(3);
                    //     Thread.sleep(Math.round(Math.random()));
                    monitor.dispararTransicion(1);
                }catch (Exception e){e.printStackTrace();}
            }
        }
    }

    class Generador implements Runnable{
        @Override
        public void run() {
            for (int i=0;i<10;i++) {
                monitor.dispararTransicion(4);
                System.out.println(new GregorianCalendar().getTimeInMillis());
            }
        }
    }

    class ProductorIntento implements Runnable{

        @Override
        public void run() {
            for(int i=0;i<10;i++){

                while(!monitor.intentarDispararTransicion(0));

                while(!monitor.intentarDispararTransicion(2));

            }
        }
    }

    class ConsumidorIntento implements Runnable{

        @Override
        public void run() {
            for(int i=0;i<10;i++){

                while(!monitor.intentarDispararTransicion(3));

                while(!monitor.intentarDispararTransicion(1));

            }
        }
    }

    class GeneradorIntento implements Runnable{
        @Override
        public void run() {
            for (int i=0;i<10;i++) {
                while(!monitor.intentarDispararTransicion(4));
                System.out.println(new GregorianCalendar().getTimeInMillis());
            }
        }
    }


}