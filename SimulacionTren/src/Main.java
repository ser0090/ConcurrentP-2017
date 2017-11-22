import Monitor.Monitor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 * Created by Tincho on 17/8/2017.
 */
public class Main {


    public static void main(String[] args) {
        final String subeBajaA="8";
        final String subeBajaB="9";
        final String subeBajaC="10";
        final String subeBajaD="11";

        Monitor monitor = Monitor.getInstance();
        monitor.setRdP("C:\\Users\\Tincho\\Documents\\GitHub\\ConcurrentP-2017\\Matrices2.xls");
        //monitor.setPolitica(new PoliticaPrioridadSubida());

        PrintStream out;
        try {
            out=new PrintStream(new File("log.txt"));
        }catch(FileNotFoundException e){e.printStackTrace();return;}



        Thread tren = new Thread(new Tren(monitor,out));
        Thread maquina = new Thread(new Maquina(monitor,out));
        Thread vagon = new Thread(new Vagon(monitor,out));

        Thread generadorA = new Thread(new Generador(0, monitor,out));
        Thread generadorB = new Thread(new Generador(10, monitor,out));
        Thread generadorC = new Thread(new Generador(14, monitor,out));
        Thread generadorD = new Thread(new Generador(22, monitor,out));

        Thread barreraAB = new Thread(new Barrera(5, 4, monitor,out));
        Thread barreraCD = new Thread(new Barrera(18, 17, monitor,out));

        //subir-bajar gente en estacion A
        Thread subirMaquinaA= new Thread(new DisparadorSimple(24,monitor,subeBajaA,out));
        Thread subirVagonA = new Thread(new DisparadorSimple(25,monitor,subeBajaA,out));
        Thread bajarMaquinaA = new Thread(new DisparadorSimple(27,monitor,subeBajaA,out));
        Thread bajarVagonA = new Thread(new DisparadorSimple(26,monitor,subeBajaA,out));
        //subir-bajar gente en estacion A
        Thread subirMaquinaB= new Thread(new DisparadorSimple(32,monitor,subeBajaB,out));
        Thread subirVagonB = new Thread(new DisparadorSimple(33,monitor,subeBajaB,out));
        Thread bajarMaquinaB = new Thread(new DisparadorSimple(30,monitor,subeBajaB,out));
        Thread bajarVagonB = new Thread(new DisparadorSimple(31,monitor,subeBajaB,out));
        //subir-bajar gente en estacion C
        Thread subirMaquinaC= new Thread(new DisparadorSimple(36,monitor,subeBajaC,out));
        Thread subirVagonC = new Thread(new DisparadorSimple(37,monitor,subeBajaC,out));
        Thread bajarMaquinaC = new Thread(new DisparadorSimple(34,monitor,subeBajaC,out));
        Thread bajarVagonC = new Thread(new DisparadorSimple(35,monitor,subeBajaC,out));
        //subir-bajar gente en estacion D
        Thread subirMaquinaD= new Thread(new DisparadorSimple(41,monitor,subeBajaD,out));
        Thread subirVagonD = new Thread(new DisparadorSimple(40,monitor,subeBajaD,out));
        Thread bajarMaquinaD = new Thread(new DisparadorSimple(39,monitor,subeBajaD,out));
        Thread bajarVagonD = new Thread(new DisparadorSimple(38,monitor,subeBajaD,out));



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
        bajarVagonD.start();
    }
}
