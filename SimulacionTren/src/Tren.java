import Monitor.Monitor;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by Tincho on 17/8/2017.
 */
public class Tren implements Runnable {
    private Monitor monitor;


    /**
     * esta tarea esta mal implementada ya que no permite verificar las dos condiciones de salida de las estaciones
     *
     * @param monitor
     */
    public Tren(Monitor monitor) {
        this.monitor = monitor;
    }

    @Override
    public void run() {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);

        //subir-bajar gente en estacion A
        Runnable subirMaquinaA=new MoverGente(24,monitor);
        Runnable subirVagonA = new MoverGente(25,monitor);
        Runnable bajarMaquinaA = new MoverGente(27,monitor);
        Runnable bajarVagonA = new MoverGente(26,monitor);
        //subir-bajar gente en estacion A
        Runnable subirMaquinaB= new MoverGente(32,monitor);
        Runnable subirVagonB =new MoverGente(33,monitor);
        Runnable bajarMaquinaB =new MoverGente(30,monitor);
        Runnable bajarVagonB = new MoverGente(31,monitor);
        //subir-bajar gente en estacion C
        Runnable subirMaquinaC=new MoverGente(36,monitor);
        Runnable subirVagonC = new MoverGente(37,monitor);
        Runnable bajarMaquinaC = new MoverGente(34,monitor);
        Runnable bajarVagonC = new MoverGente(35,monitor);
        //subir-bajar gente en estacion D
        Runnable subirMaquinaD= new MoverGente(41,monitor);
        Runnable subirVagonD = new MoverGente(40,monitor);
        Runnable bajarMaquinaD =new MoverGente(39,monitor);
        Runnable bajarVagonD = new MoverGente(38,monitor);


        System.out.println("\033[31mTren en A\033[37m");
        while(true){
            executor.execute(subirMaquinaA);
            executor.execute(subirVagonA);
            executor.execute(bajarMaquinaA);
            executor.execute(bajarVagonA);

            while(!monitor.intentarDispararTransicion(2)){ //transiciones para salir de la estacion A
                if(monitor.intentarDispararTransicion(3)){break;}
            }
            System.out.println("\033[34mSALE DE A\033[37m");
            executor.remove(subirMaquinaA);
            executor.remove(subirVagonA);
            executor.remove(bajarMaquinaA);
            executor.remove(bajarVagonA);


            monitor.dispararTransicion(6); // antes del paso a nivel AB
            monitor.dispararTransicion(7); //despues del paso a nivel AB
            monitor.dispararTransicion(8); // entrada a la estacion B

            System.out.println("\033[31mTren en B\033[37m");
            executor.execute(subirMaquinaB);
            executor.execute(subirVagonB);
            executor.execute(bajarMaquinaB);
            executor.execute(bajarVagonB);
            while(!monitor.intentarDispararTransicion(11)){ //transiciones para salir de la estacion B
                if(monitor.intentarDispararTransicion(15)){break;}
            }
            System.out.println("\033[34mSALE DE B\033[37m");
            executor.remove(subirMaquinaB);
            executor.remove(subirVagonB);
            executor.remove(bajarMaquinaB);
            executor.remove(bajarVagonB);

            monitor.dispararTransicion(12); // entrada a la estacion C
            System.out.println("\033[31mTren en C\033[37m");
            executor.execute(subirMaquinaC);
            executor.execute(subirVagonC);
            executor.execute(bajarMaquinaC);
            executor.execute(bajarVagonC);
            while(!monitor.intentarDispararTransicion(9)){ //transiciones para salir de la estacion C
                if(monitor.intentarDispararTransicion(13)){break;}
            }
            System.out.println("\033[34mSALE DE C\033[37m");
            executor.remove(subirMaquinaC);
            executor.remove(subirVagonC);
            executor.remove(bajarMaquinaC);
            executor.remove(bajarVagonC);

            monitor.dispararTransicion(16); // antes del paso a nivel CD
            monitor.dispararTransicion(19); //despues del paso a nivel CD
            monitor.dispararTransicion(20); // entrada a la estacion D
            System.out.println("\033[31mTren en D\033[37m");
            executor.execute(subirMaquinaD);
            executor.execute(subirVagonD);
            executor.execute(bajarMaquinaD);
            executor.execute(bajarVagonD);
            while(!monitor.intentarDispararTransicion(21)){ //transiciones para salir de la estacion D
                if(monitor.intentarDispararTransicion(23)){break;}
            }
            System.out.println("\033[34mSALE DE D\033[37m");
            executor.remove(subirMaquinaD);
            executor.remove(subirVagonD);
            executor.remove(bajarMaquinaD);
            executor.remove(bajarVagonD);

            monitor.dispararTransicion(1); // entrada a la estacion A
            System.out.println("\033[31mTren en A\033[37m");
        }

    }
}
