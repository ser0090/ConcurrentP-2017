import Monitor.Monitor;

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
        System.out.println("\033[31mTren en A\033[37m");
        while(true){
            while(!monitor.intentarDispararTransicion(2)){ //transiciones para salir de la estacion A
                if(monitor.intentarDispararTransicion(3)){break;}
            }
            System.out.println("\033[34mSALE DE A\033[37m");

            monitor.dispararTransicion(6); // antes del paso a nivel AB
            monitor.dispararTransicion(7); //despues del paso a nivel AB
            monitor.dispararTransicion(8); // entrada a la estacion B
            System.out.println("\033[31mTren en B\033[37m");
            while(!monitor.intentarDispararTransicion(11)){ //transiciones para salir de la estacion B
                if(monitor.intentarDispararTransicion(15)){break;}
            }
            System.out.println("\033[34mSALE DE B\033[37m");

            monitor.dispararTransicion(12); // entrada a la estacion C
            System.out.println("\033[31mTren en C\033[37m");
            while(!monitor.intentarDispararTransicion(9)){ //transiciones para salir de la estacion C
                if(monitor.intentarDispararTransicion(13)){break;}
            }
            System.out.println("\033[34mSALE DE C\033[37m");

            monitor.dispararTransicion(16); // antes del paso a nivel CD
            monitor.dispararTransicion(19); //despues del paso a nivel CD
            monitor.dispararTransicion(20); // entrada a la estacion D
            System.out.println("\033[31mTren en D\033[37m");
            while(!monitor.intentarDispararTransicion(21)){ //transiciones para salir de la estacion D
                if(monitor.intentarDispararTransicion(23)){break;}
            }
            System.out.println("\033[34mSALE DE D\033[37m");

            monitor.dispararTransicion(1); // entrada a la estacion A
            System.out.println("\033[31mTren en A\033[37m");
        }

    }
}
