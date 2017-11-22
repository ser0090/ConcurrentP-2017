package Test;

import Monitor.Monitor;
import jxl.Sheet;
import jxl.Workbook;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import Monitor.RdP;

/**
 * Created by Tincho on 24/10/2017.
 */
public class SimulacionTrenTest {
    private Monitor monitor=Monitor.getInstance();
    private String[][]t_invariantes;

    @org.junit.Before
    public void setUp() throws Exception {
        monitor.setRdP("C:\\Users\\Tincho\\Documents\\GitHub\\ConcurrentP-2017\\Matrices2-t-invariantes.xls");
        File archivo = new File("C:\\Users\\Tincho\\Documents\\GitHub\\ConcurrentP-2017\\Matrices2-t-invariantes.xls");
        Workbook libroMatrices;
        try {
            libroMatrices = Workbook.getWorkbook(archivo);

            Sheet pagina = libroMatrices.getSheet(5);
            int columns = pagina.getColumns();
            int row = pagina.getRows();
            t_invariantes = new String[row - 1][columns - 1];
            for (int i = 1; i < columns; i++) {
                for (int j = 1; j < row; j++) {
                    t_invariantes[j - 1][i - 1] = pagina.getCell(i, j).getContents();
                }
            }
        }catch(Exception e){e.printStackTrace();throw new Exception("error en la carga de T invariantes");}
    }

    @Test
    public void t_invariantTest(){

        for(int i=0;i<t_invariantes.length;i++){

            int[][] m = monitor.getM();
            List<Thread> threads = new ArrayList<>();
            System.out.println("\033[34mtest "+i+"\033[37m");
            String[] transiciones= t_invariantes[i][0].split(" ");
            for(int j=0;j<transiciones.length;j++){ //carga las transiciones del invariante
            try{
                String[] t=transiciones[j].split("T");
                threads.add(new Thread(new Disparador(Integer.parseInt(t[1]))));
            }catch(Exception e){}
            }

            for(Thread t:threads){ //dispara las transiiones del invariante
                t.start();
            }
            for (Thread t: threads){ //espera a que todas se hayan disparado
                try {
                    t.join();
                }catch (InterruptedException e){e.printStackTrace();}
            }
            int[][]nuevo_m= monitor.getM(); //controla que se haya regresado al mismo marcado
            for(int k=0;k<m.length;k++){
                assert(m[k][0]==nuevo_m[k][0]);
            }

        }

    }

    @Test
    public void logTest() throws FileNotFoundException, IOException {
        RdP reglas = new RdP("src\\Test\\LogAnaliser\\logAnaliser.xls");
        FileReader logs=new FileReader("log.txt");

        BufferedReader reader = new BufferedReader(logs);
        String log;
        while((log = reader.readLine())!=null) {
            assert(reglas.disparar(Integer.parseInt(log))==0);
        }
        reader.close();

        System.out.println("Cantidad de vueltas del tren = "+ (reglas.getM())[8][0]);

    }

    class Disparador implements Runnable{
        int transicion;
        Monitor monitor;
        public Disparador(int transicion){
            this.transicion=transicion;
            monitor=Monitor.getInstance();
        }

        @Override
        public void run(){
            monitor.dispararTransicion(transicion);
            System.out.println("transicion " +transicion+" disparada");
        }
    }
}