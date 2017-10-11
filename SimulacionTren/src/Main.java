import Monitor.Monitor;
import Monitor.RdP;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import Monitor.RdP;

/**
 * Created by Tincho on 17/8/2017.
 */
public class Main {
    public static void main(String[] args) {


        Monitor monitor = Monitor.getInstance();

/*
        Thread tren = new Thread(new Tren(monitor));
        Thread maquina = new Thread(new Maquina(monitor));
        Thread vagon = new Thread(new Vagon(monitor));

        Thread generadorA = new Thread(new Generador(0, monitor));
        Thread generadorB = new Thread(new Generador(10, monitor));
        Thread generadorC = new Thread(new Generador(14, monitor));
        Thread generadorD = new Thread(new Generador(22, monitor));

        Thread barreraAB = new Thread(new Barrera(5, 4, monitor));
        Thread barreraCD = new Thread(new Barrera(18, 17, monitor));

        //subir-bajar gente en estacion A
        Thread subirMaquinaA= new Thread(new MoverGente(24,monitor));
        Thread subirVagonA = new Thread(new MoverGente(25,monitor));
        Thread bajarMaquinaA = new Thread(new MoverGente(27,monitor));
        Thread bajarVagonA = new Thread(new MoverGente(26,monitor));
        //subir-bajar gente en estacion A
        Thread subirMaquinaB= new Thread(new MoverGente(32,monitor));
        Thread subirVagonB = new Thread(new MoverGente(33,monitor));
        Thread bajarMaquinaB = new Thread(new MoverGente(30,monitor));
        Thread bajarVagonB = new Thread(new MoverGente(31,monitor));
        //subir-bajar gente en estacion C
        Thread subirMaquinaC= new Thread(new MoverGente(36,monitor));
        Thread subirVagonC = new Thread(new MoverGente(37,monitor));
        Thread bajarMaquinaC = new Thread(new MoverGente(34,monitor));
        Thread bajarVagonC = new Thread(new MoverGente(35,monitor));
        //subir-bajar gente en estacion D
        Thread subirMaquinaD= new Thread(new MoverGente(41,monitor));
        Thread subirVagonD = new Thread(new MoverGente(40,monitor));
        Thread bajarMaquinaD = new Thread(new MoverGente(39,monitor));
        Thread bajarVagonD = new Thread(new MoverGente(38,monitor));


        tren.start();
        maquina.start();
        vagon.start();
        generadorA.start();
        generadorB.start();
        generadorC.start();
        generadorD.start();
        barreraAB.start();
        barreraCD.start();

        subirMaquinaA.start();
        subirMaquinaB.start();
        subirMaquinaC.start();
        subirMaquinaD.start();
        subirVagonA.start();
        subirVagonB.start();
        subirVagonC.start();
        subirVagonD.start();
        bajarMaquinaA.start();
        bajarMaquinaB.start();
        bajarMaquinaC.start();
        bajarMaquinaD.start();
        bajarVagonA.start();
        bajarVagonB.start();
        bajarVagonC.start();
        bajarVagonD.start();*/
    }
}
