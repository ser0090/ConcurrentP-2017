import Monitor.Monitor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;
import Monitor.PoliticaPrioridadSubida;
import TaskExecutor.*;

/**
 * Created by Tincho on 22/11/2017.
 */
public class Main {

    public static void main(String[] args) {

        Monitor monitor = Monitor.getInstance();
        monitor.setRdP("C:\\Users\\Tincho\\Documents\\GitHub\\ConcurrentP-2017\\Matrices2.xls");
        monitor.setPolitica(new PoliticaPrioridadSubida());

        PrintStream out;
        try {
            out=new PrintStream(new File("log.txt"));
        }catch(FileNotFoundException e){e.printStackTrace();return;}

        Map<Integer, Accion> acciones = getTablaDeAcciones(out,monitor); //***Mapa de transicion->accion
        List<List<Integer>> listasDeTransiciones= getListasDeTransiciones();

        //** ThrenThread**/
        Accion accionInicial=acciones.get(1);
        Thread trenThread = new Thread(new Task(acciones,Arrays.asList(6,7,8,12,16,19,20,1),accionInicial,true));
        trenThread.start();

        for (List<Integer> listaTransiciones:listasDeTransiciones) {
            Thread thread =new Thread(new Task(acciones,listaTransiciones,null,true));
            thread.start();
        }

    }

    private static List<List<Integer>> getListasDeTransiciones(){

        return Arrays.asList(   Arrays.asList(29),//Maquina
                                Arrays.asList(28),//Vagon
                                Arrays.asList(0), //generadorA
                                Arrays.asList(10),//generadorB
                                Arrays.asList(14),//generadorC
                                Arrays.asList(22),//generadorD
                                Arrays.asList(4,5),//barreraAB
                                Arrays.asList(17,18),//barreraCD
                                Arrays.asList(24),//subir a la maquina A
                                Arrays.asList(25),//subir al vagon A
                                Arrays.asList(27),//bajar de la maquina A
                                Arrays.asList(26),//bajar de la maquina A
                                Arrays.asList(32),//subir a la maquina B
                                Arrays.asList(33),//subir al vagon B
                                Arrays.asList(30),//bajar de la maquina B
                                Arrays.asList(31),//bajar de la maquina B
                                Arrays.asList(36),//subir a la maquina C
                                Arrays.asList(37),//subir al vagon C
                                Arrays.asList(34),//bajar de la maquina C
                                Arrays.asList(35),//bajar de la maquina C
                                Arrays.asList(41),//subir a la maquina D
                                Arrays.asList(40),//subir al vagon D
                                Arrays.asList(39),//bajar de la maquina D
                                Arrays.asList(38)//bajar de la maquina D
                                );
    }

    private static Map<Integer,Accion> getTablaDeAcciones(PrintStream out,Monitor monitor){
        final String subeBajaA="8";
        final String subeBajaB="9";
        final String subeBajaC="10";
        final String subeBajaD="11";
        final String generadorLog ="14";

        final String arriboA="7";
        final String viajeAB="0";
        final String arriboB="1";
        final String viajeBC="2";
        final String arriboC="3";
        final String viajeCD="4";
        final String arriboD="5";
        final String viajeDA="6";

        final String vagonLog = "12";
        final String maquinaLog = "13";

        Map<Integer,Accion> acciones = new HashMap<>(); //***Mapa de transicion->accion
        //*********Se Llena la tabla de acciones***//
        //******Generadores*****//
        acciones.put(0,new Log(generadorLog,out));
        acciones.put(10,new Log(generadorLog,out));
        acciones.put(14,new Log(generadorLog,out));
        acciones.put(22,new Log(generadorLog,out));

        //***subir-bajar gente en estacion A******////
        acciones.put(24,new Log(subeBajaA,out));
        acciones.put(25,new Log(subeBajaA,out));
        acciones.put(26,new Log(subeBajaA,out));
        acciones.put(27,new Log(subeBajaA,out));

        //***subir-bajar gente en estacion B***//
        acciones.put(30,new Log(subeBajaB,out));
        acciones.put(31,new Log(subeBajaB,out));
        acciones.put(32,new Log(subeBajaB,out));
        acciones.put(33,new Log(subeBajaB,out));

        //***subir-bajar gente en estacion C***//
        acciones.put(34,new Log(subeBajaC,out));
        acciones.put(35,new Log(subeBajaC,out));
        acciones.put(36,new Log(subeBajaC,out));
        acciones.put(37,new Log(subeBajaC,out));

        //***subir-bajar gente en estacion D***//
        acciones.put(38,new Log(subeBajaD,out));
        acciones.put(39,new Log(subeBajaD,out));
        acciones.put(40,new Log(subeBajaD,out));
        acciones.put(41,new Log(subeBajaD,out));

        //***Barreras***//
        //Las barreras no tienen un log asociado

        //***Pasajeros en la maquina que van a bajar**//
        acciones.put(29,new Log(maquinaLog,out));

        //***Pasajeros en el vagon que van a bajar**//
        acciones.put(28,new Log(vagonLog,out));

        //***Tren ***///
        List<Accion> listaDeAcciones=Arrays.asList(new Log(arriboA,out),
                new ForkAndJoin(Arrays.asList(2,3),monitor,viajeAB,out));
        acciones.put(1,new MultipleActions(listaDeAcciones));

        listaDeAcciones=Arrays.asList(new Log(arriboB,out),
                new ForkAndJoin(Arrays.asList(11,15),monitor,viajeBC,out));
        acciones.put(8,new MultipleActions(listaDeAcciones));

        listaDeAcciones=Arrays.asList(new Log(arriboC,out),
                new ForkAndJoin(Arrays.asList(9,13),monitor,viajeCD,out));
        acciones.put(12,new MultipleActions(listaDeAcciones));

        listaDeAcciones=Arrays.asList(new Log(arriboD,out),
                new ForkAndJoin(Arrays.asList(21,23),monitor,viajeDA,out));
        acciones.put(20,new MultipleActions(listaDeAcciones));

        return acciones;

    }

}
