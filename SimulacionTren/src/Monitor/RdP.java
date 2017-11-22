package Monitor;

import java.io.File;

import java.util.*;

import jxl.*;



/**
 * Created by Tincho on 31/8/2017.
 */
public class RdP {

    private int[][] I;// matriz de incidencia (PxT)
    private int[][] H;//Matriz de arcos innibidores (TxP). Esta matriz se almacena en forma complementada para facilitar el calculo de B
    private int[][] m;//matriz de marcado(columna)
    private int[][] B;//matriz de innibicion (columna)
    private int[][] Z;//Matriz de intervalos
    private Calendar[] T;//vector que contiene los tiempos en q se sensibilizo la transicion
    private boolean[] contadores;//indica si el contador de tiempo esta activo
    private int [][] pInvariantes;

    public RdP(String path) {
        cargarMatrices(path);

        B= new int[H.length][1];
        calcularB();
        crearT();
        contadores = new boolean[T.length];
    }

    /**
     * inicializa el vector de timestamp T
     */
    private void crearT(){
        T = new Calendar[H.length];
        for (int i = 0; i < T.length; i++) {
            T[i] = Calendar.getInstance();
        }
    }

    /**
     * Dispara la transicion indicada en la RdP.
     *
     * @param transicion, transicion a disparar
     * @return 0 si la red se disparo con exito, -1 si la transicion no pudo dispararse porque no estaba sensibilizada.
     * si la transicion no se puede disparar porque le falta para el intervalo de tiempo retornara el tiempo que le falta en milisegundos.
     */
    public int disparar(int transicion) {

            int[][] marcadoSiguiente = calcularDisparo(transicion);
            if (verificarDisparo(marcadoSiguiente)) {
                int aux = verificarTiempo(transicion);
                if (aux == 0) {
                    m = marcadoSiguiente;

                    calcularB();
                    contadores[transicion] = false; // reseteo el timestamp
                    actualizarTimeStamp();
                    verificarInvariantes();
                    return 0;
                } else {
                    return aux;
                }
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
    private int[][] calcularDisparo(int transicion) {
        if (transicion >= I[0].length) {
            throw new IllegalArgumentException("no exite la transicion en esta RdP");
        }

        ///////////////inclusion de los arcos innibidores///////////////////
        if(B[transicion][0]==0){return null;}

        int[][] d = new int[I[0].length][1];

        d[transicion][0] = 1;// conformo el vector delta de disparo(d es un vector auxiliar

        return Matrix.MatrixAdition(m, Matrix.matrixProduct(I, d));




    }

    private void calcularB(){
        int[][]cero = Matrix.cero(m);

        for (int i=0;i<H.length;i++){
            B[i][0]=1;
            for(int j=0;j<H[0].length;j++){
               B[i][0] &= H[i][j] | cero[j][0];
            }
        }
    }

    /**
     * Devuelve la cantidad de transiciones q posee la red
     *
     * @return cantidad de transiciones en la RdP
     */
    public int cantidadDeTransiciones() {
        return I[0].length;
    }

    /**
     * @return ArrayList<Integer> con las transiciones sensibilizadas
     */
    public List<Integer> getSensibilizadas() {
        ArrayList<Integer> sensibilizadas = new ArrayList<>();
        for (int transicion = 0; transicion < I[0].length; transicion++) {
            try {
                if (verificarDisparo(calcularDisparo(transicion))) {

                        sensibilizadas.add(transicion);
                }
            } catch (Exception e) {
            }

        }
        return sensibilizadas;
    }

    private void actualizarTimeStamp() {
        List<Integer> sensibilizadas = getSensibilizadas();

        for (int i = 0; i < T.length; i++) { // recorre todas las transiciones actualizando su timestamp
                if (sensibilizadas.contains(i) &&
                        !contadores[i]) { // si esta sensibilizada, debo actualizar su timeStamp solo si no lo estaba antes
                        T[i] = Calendar.getInstance();
                        contadores[i] = true;

                }else{
                     contadores[i] = false;
                }
        }
    }

    /**
     * carga las matrices de la RdP desde un libro de Exel 97-2003 (formato .xls)
     * las matrices deben colocarse en paginas del libro de exel de la siguiente manera:
     *
     * 1ra  Hoja)I (matriz de incidencia)
     * 2ta  Hoja)H (matriz de innibicion)
     * 3ta  Hoja)M0 (marcado inicial)
     * 4ta hoja)Z (matriz de tiempos)
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
            I = new int[row - 1][columns - 1];
            for (int i = 1; i < columns; i++) {
                for (int j = 1; j < row; j++) {
                    I[j - 1][i - 1] = Integer.parseInt(pagina.getCell(i, j).getContents());
                }
            }

            pagina = libroMatrices.getSheet(1);
            columns = pagina.getColumns();
            row = pagina.getRows();
            H = new int[row - 1][columns - 1];
            for (int i = 1; i < columns; i++) {
                for (int j = 1; j < row; j++) {
                    H[j - 1][i - 1] = Integer.parseInt(pagina.getCell(i, j).getContents());
                }
            }
            H = Matrix.Transpuesta(H);
            H= Matrix.cero(H); // complemento de la matriz H


            pagina = libroMatrices.getSheet(2);
            columns = pagina.getColumns();
            m = new int[columns - 1][1];
            for (int j = 1; j < columns; j++) {
                m[j - 1][0] = Integer.parseInt(pagina.getCell(j, 1).getContents());
            }

            pagina = libroMatrices.getSheet(3);
            columns = pagina.getColumns();
            row = pagina.getRows();
            Z = new int[columns - 1][row - 1];
            for (int i = 1; i < columns; i++) {
                for (int j = 1; j < row; j++) {
                    Z[i - 1][j - 1] = Integer.parseInt(pagina.getCell(i, j).getContents());
                }
            }

            pagina = libroMatrices.getSheet(4);//carga los P-invariantes
            columns = pagina.getColumns();
            row = pagina.getRows();
            pInvariantes = new int[row-1][columns];
            for (int i = 1; i < row; i++) {
                for (int j = 1; j < columns; j++) {
                    pInvariantes[i - 1][j - 1] = Integer.parseInt(pagina.getCell(j,i).getContents());
                    if(pInvariantes[i - 1][j - 1]!=0){
                        pInvariantes[i-1][columns-1]+=m[j-1][0];}//suma los tokens para obtener el invariante
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void verificarInvariantes(){
        int valor;
        for(int i = 0; i< pInvariantes.length; i++){
            valor=0;
            for(int j = 0; j< pInvariantes[0].length-1; j++){
                valor+=m[j][0]* pInvariantes[i][j];
            }
            assert(valor== pInvariantes[i][pInvariantes[i].length-1]);
        }
    }

    /**
     * Verifica que el vector no posea valores negativos
     *
     * @param marcado, el vector a ser analizado
     * @return true si no existen valores negativos(disparo exitoso)
     */
    private boolean verificarDisparo(int[][] marcado) {
        if (marcado==null){return false;}
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

    public int[][] getM() {

       return m;
    }

    /**
     * analiza la temporizacion de las transiciones indicando si la transicion esta en la ventana de disparo
     * @param transicion, transicion a analizar
     * @return 0 si la transicion esta en la ventana de tiempo. si falta para que la transicion se pueda disparar retorna el tiempo que falta en ms
     *
     */
    private int verificarTiempo(int transicion){

        Calendar actual = new GregorianCalendar();

        int diferenciaEnMilis = (int) (actual.getTimeInMillis() - T[transicion].getTimeInMillis());
        if (diferenciaEnMilis < (Z[transicion][0] * 1000)) {

            if ((Z[transicion][1] > 0) && (diferenciaEnMilis>Z[transicion][1])) {
               return -1;
            }
            else {return (Z[transicion][0] * 1000) - diferenciaEnMilis;}
        }
            return 0;
        }

    /**
     * Carga la red, las matrices en el archivo exel.
     * @param path, ruta del archivo .xls
     */

    }
