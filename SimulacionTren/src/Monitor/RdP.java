package Monitor;

import java.io.File;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.*;

import Monitor.Matrix;
import jxl.*;



/**
 * Created by Tincho on 31/8/2017.
 */
public class RdP {

    private int[][] I;// matriz de incidencia (PxT)
    private int[][] Ip; //I+ (PxT)
    private int[][] Im;//I- (PxT)
    private int[][] H;//Matriz de arcos innibidores (TxP)
    private int[][] J;//Matriz auxiliar para calculo de innibiciones(columna)
    private int[][] m;//matriz de marcado(columna)
    private int[][] B;//matriz de innibicion (columna)
    private int[][] Z;//Matriz de intervalos
    private Calendar[] T;//vector que contiene los tiempos en q se sensibilizo la transicion
    private boolean[] Contadores;//indica si el contador de tiempo esta activo

    public RdP() {
        cargarMatrices("C:\\Users\\Tincho\\Documents\\GitHub\\ConcurrentP-2017\\MatricesTesting.xls");
        J = new int[H.length][1]; //auxiliar de innibicion
        for (int i = 0; i < H.length; i++) {
            for (int j = 0; j < H[0].length; j++) {
                if (H[i][j] != 0) {
                    J[i][0] = 1;
                }
            }
        }

        B = Matrix.uno(Matrix.matrixProduct(H, Matrix.cero(m)));//calcula columna de innibicion
        T = new Calendar[H.length];
        for (int i = 0; i < T.length; i++) {
            T[i] = new GregorianCalendar();
        }

        Contadores = new boolean[T.length];
    }

    /**
     * Dispara la transicion indicada en la RdP.
     *
     * @param transicion, transicion a disparar
     * @return 0 si la red se disparo con exito, -1 si la transicion no pudo dispararse porque no estaba sensibilizada.
     * si la transicion no se puede disparar porque le falta para el intervalo de tiempo retornara el tiempo que le falta en milisegundos.
     */
    public int disparar(int transicion) {
        try {
            int[][] marcadoSiguiente = calcularDisparo(transicion);
            if (verificarDisparo(marcadoSiguiente)) {
                int aux = verificarTiempo(transicion);
                if (aux == 0) {
                    m = marcadoSiguiente;
                    B = Matrix.uno(Matrix.matrixProduct(H, Matrix.cero(m)));//actualiza la matriz de innibicion
                    Contadores[transicion] = false; // reseteo el timestamp
                    actualizarTimeStamp();
                    return 0;
                } else {
                    return aux;
                }
            }
        } catch (TransicionInnibidaException e) {
            return -1;
        } catch (TransicionFueraDeTiempoException e) {
            return -1;
        }

        return -1;
    }

    /**
     * calcularDisparo(int transicion)
     * calcula el vector de marcado al disparar la transicion indicada
     *
     * @param transicion a disparar
     * @return vector(matriz) de marcado calculado
     */
    private int[][] calcularDisparo(int transicion) throws TransicionInnibidaException {
        if (transicion >= I[0].length) {
            throw new IllegalArgumentException("no esxite la transicion en esta RdP");
        }

        ///////////////inclusion de los arcos innibidores///////////////////
        if ((B[transicion][0] != J[transicion][0])) {
            throw new TransicionInnibidaException();
        }


        int[][] d = new int[I[0].length][1];

        d[transicion][0] = 1;// conformo el vector delta de disparo(d es un vector auxiliar)

        int[][] dispar = Matrix.MatrixAdition(m, Matrix.matrixProduct(I, d));
        ///////////////inclusion de transiciones temporales/////////////////

        return dispar;


    }

    /**
     * Devuelve la cantidad de transiciones q posee la red
     *
     * @return cantidad de transiciones en la RdP
     */
    public int cantidadDeTransiciones() {
        return 0;
    }

    /**
     * @return ArrayList<Integer> con las transiciones sensibilizadas
     */
    public List<Integer> getSensibilizadas() {
        ArrayList<Integer> sensibilizadas = new ArrayList<>();
        for (int transicion = 0; transicion < I[0].length; transicion++) {
            try {
                if (verificarDisparo(calcularDisparo(transicion))) {
                    if (verificarTiempo(transicion) == 0)
                        sensibilizadas.add(transicion);
                }
            } catch (Exception e) {
            }

        }
        return sensibilizadas;
    }

    private void actualizarTimeStamp() {
        List<Integer> sens = getSensibilizadas();
        boolean flag = false;
        for (int i = 0; i < T.length; i++) {
            flag = false;
            for (Integer j : sens) {
                if (i == j) {
                    if (Contadores[i] == false) {
                        T[i] = new GregorianCalendar();
                        Contadores[i] = true;
                    }
                    flag = true;
                }
            }
            if (!flag) {
                Contadores[i] = false;
            }
        }
    }

    /**
     * carga las matrices de la RdP desde un libro de Exel 97-2003 (formato .xls)
     * las matrices deben colocarse en paginas del libro de exel de la siguiente manera:
     * 1era Hoja)I-
     * 2da  Hoja)I+
     * 3ra  Hoja)I (matriz de incidencia)
     * 4ta  Hoja)H (matriz de innibicion)
     * 5ta  Hoja)M0 (marcado inicial)
     *
     * @param path, la ruta Absoluta al archivo de exel (.xls)
     */
    private void cargarMatrices(String path) {
        File archivo = new File(path);
        Workbook libroMatrices;
        try {
            libroMatrices = Workbook.getWorkbook(archivo);
            Sheet pagina = libroMatrices.getSheet(0);
            int columns = pagina.getColumns();
            int row = pagina.getRows();
            Im = new int[row - 1][columns - 1];
            //System.out.println("filas:"+Im.length +"  Columnas:"+Im[0].length);
            for (int i = 1; i < columns; i++) {
                for (int j = 1; j < row; j++) {
                    //System.out.println(j +";"+i);
                    Im[j - 1][i - 1] = Integer.parseInt(pagina.getCell(i, j).getContents());
                }
            }

            pagina = libroMatrices.getSheet(1);
            columns = pagina.getColumns();
            row = pagina.getRows();
            Ip = new int[row - 1][columns - 1];
            for (int i = 1; i < columns; i++) {
                for (int j = 1; j < row; j++) {
                    Ip[j - 1][i - 1] = Integer.parseInt(pagina.getCell(i, j).getContents());
                }
            }

            pagina = libroMatrices.getSheet(2);
            columns = pagina.getColumns();
            row = pagina.getRows();
            I = new int[row - 1][columns - 1];
            for (int i = 1; i < columns; i++) {
                for (int j = 1; j < row; j++) {
                    I[j - 1][i - 1] = Integer.parseInt(pagina.getCell(i, j).getContents());
                }
            }

            pagina = libroMatrices.getSheet(3);
            columns = pagina.getColumns();
            row = pagina.getRows();
            H = new int[row - 1][columns - 1];
            for (int i = 1; i < columns; i++) {
                for (int j = 1; j < row; j++) {
                    H[j - 1][i - 1] = Integer.parseInt(pagina.getCell(i, j).getContents());
                }
            }
            H = Matrix.Transpuesta(H);


            pagina = libroMatrices.getSheet(4);
            columns = pagina.getColumns();
            row = pagina.getRows();
            m = new int[row - 1][1];
            for (int j = 1; j < row; j++) {
                m[j - 1][0] = Integer.parseInt(pagina.getCell(1, j).getContents());
            }

            pagina = libroMatrices.getSheet(5);
            columns = pagina.getColumns();
            row = pagina.getRows();
            Z = new int[row - 1][columns - 1];
            for (int i = 1; i < columns; i++) {
                for (int j = 1; j < row; j++) {
                    Z[j - 1][i - 1] = Integer.parseInt(pagina.getCell(i, j).getContents());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Verifica que el vector no posea valores negativos
     *
     * @param marcado, el vector a ser analizado
     * @return true si no existen valores negativos(disparo exitoso)
     */
    private boolean verificarDisparo(int[][] marcado) {
        for (int i = 0; i < marcado.length; i++) {
            for (int k = 0; k < marcado[0].length; k++) {
                if (marcado[i][k] < 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public void printI() {
        for (int i = 0; i < I.length; i++) {
            for (int k = 0; k < I[0].length; k++) {
                System.out.printf("%d", I[i][k]);
            }
            System.out.printf("\n");
        }
    }

    public void printM() {
        for (int i = 0; i < m.length; i++) {
            for (int k = 0; k < m[0].length; k++) {
                System.out.printf("%d", m[i][k]);
            }
            System.out.printf("\n");
        }
    }

    /**
     * analiza la temporizacion de las transiciones indicando si la transicion esta en la ventana de disparo
     * @param transicion, transicion a analizar
     * @return 0 si la transicion esta en la ventana de tiempo. si falta para que la transicion se pueda disparar retorna el tiempo que falta en ms
     * @throws TransicionFueraDeTiempoException, si la transicion ya esta fuera de la ventana de tiempo, >beta
     */
    public int verificarTiempo(int transicion) throws TransicionFueraDeTiempoException {

        Calendar actual = new GregorianCalendar();

        int diferenciaEnMilis = (int) (actual.getTimeInMillis() - T[transicion].getTimeInMillis());
        if (diferenciaEnMilis < (Z[transicion][0] * 1000)) {

            if ((Z[transicion][1] > 0) && (diferenciaEnMilis>Z[transicion][1])) {
                throw new TransicionFueraDeTiempoException();
            }
            else {return (Z[transicion][0] * 1000) - diferenciaEnMilis;}
        }
            return 0;
        }

        class TransicionNoSensibilizadaExeption extends Exception {
        }
        class TransicionInnibidaException extends Exception {
        }
        class TransicionFueraDeTiempoException extends Exception {
        }
    }
