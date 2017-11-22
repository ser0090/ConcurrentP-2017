import Monitor.Monitor;
import com.sun.org.apache.xml.internal.dtm.ref.sax2dtm.SAX2DTM2;

import java.io.PrintStream;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by Tincho on 17/8/2017.
 */
public class Tren implements Runnable {
    private Monitor monitor;
    private PrintStream out;


    /**
     * @param monitor
     */
    public Tren(Monitor monitor, PrintStream out) {
        this.monitor = monitor;
        this.out=out;
    }

    /**
     * Recorrido del tren entre estaciones
     */
    @Override
    public void run() {
        final String arriboA="7";
        final String viajeAB="0";
        final String arriboB="1";
        final String viajeBC="2";
        final String arriboC="3";
        final String viajeCD="4";
        final String arriboD="5";
        final String viajeDA="6";


        do{

            System.out.println("\033[31mTren en A\033[37m");
            out.println(arriboA);

            while(!monitor.intentarDispararTransicion(2)){ //transiciones para salir de la estacion A
                if(monitor.intentarDispararTransicion(3)){break;}
            }
            System.out.println("\033[34mSALE DE A\033[37m");
            out.println(viajeAB);



            monitor.dispararTransicion(6); // antes del paso a nivel AB
            monitor.dispararTransicion(7); //despues del paso a nivel AB
            monitor.dispararTransicion(8); // entrada a la estacion B


            System.out.println("\033[31mTren en B\033[37m");
            out.println(arriboB);

            while(!monitor.intentarDispararTransicion(11)){ //transiciones para salir de la estacion B
                if(monitor.intentarDispararTransicion(15)){break;}
            }
            System.out.println("\033[34mSALE DE B\033[37m");
            out.println(viajeBC);


            monitor.dispararTransicion(12); // entrada a la estacion C

            System.out.println("\033[31mTren en C\033[37m");
            out.println(arriboC);

            while(!monitor.intentarDispararTransicion(9)){ //transiciones para salir de la estacion C
                if(monitor.intentarDispararTransicion(13)){break;}
            }
            System.out.println("\033[34mSALE DE C\033[37m");
            out.println(viajeCD);

            monitor.dispararTransicion(16); // antes del paso a nivel CD
            monitor.dispararTransicion(19); //despues del paso a nivel CD
            monitor.dispararTransicion(20); // entrada a la estacion D

            System.out.println("\033[31mTren en D\033[37m");
            out.println(arriboD);

            while(!monitor.intentarDispararTransicion(21)){ //transiciones para salir de la estacion D
                if(monitor.intentarDispararTransicion(23)){break;}
            }
            System.out.println("\033[34mSALE DE D\033[37m");
            out.println(viajeDA);

            monitor.dispararTransicion(1); // entrada a la estacion A
        }while(true);

    }
}
