package Monitor;

/**
 * Created by Tincho on 31/8/2017.
 * Libreria de operacoines basicas con matrices
 */
public class Matrix {
    /**
     * Metodo copiado y adaptado para matrices enteras de la libreria Jama 1.0.3
     * retorna el producto matricial A*B
     * es posible el producto de una matrix con un vector pero el valor retornado sera una matrix int[][1]
     * de igual manera el producto de un vector con una matriz sera una matriz int[1][]
     * @param A matriz a la derecha int[][]
     * @param B matriz a la izquierda int[][]
     * @return A*B (producto matricial)
     * @exception IllegalArgumentException "Matrix inner dimensions must agree.
     */
    public static int[][] matrixProduct (int[][] A, int[][] B) {
        if (B.length != A[0].length) {
            throw new IllegalArgumentException("Matrix inner dimensions must agree.");
        }
        //Matrix X = new Matrix(m,B.n);
        int[][] C = new int[A.length][B[0].length];
        int [] Bcolj = new int[A[0].length];
        for (int j = 0; j < B[0].length; j++) {
            for (int k = 0; k < A[0].length; k++) {
                Bcolj[k] = B[k][j];
            }
            for (int i = 0; i < A.length; i++) {
                int[] Arowi = A[i];
                int s = 0;
                for (int k = 0; k < A[0].length; k++) {
                    s += Arowi[k]*Bcolj[k];
                }
                C[i][j] = s;
            }
        }
        return C;

    }

    /**Suma de matrices
     *
     * @param A primer sumando
     * @param B segundo sumando
     * @return Suma de matrices A+B
     * @throws IllegalArgumentException Las Matrices deben ser del mismo tamaño
     */
    public static int[][] MatrixAdition(int[][]A,int[][]B){
        if(A.length!=B.length || A[0].length!=B[0].length){throw new IllegalArgumentException("las matrices deben ser del mismo tamaño");}
        int[][] C = new int[A.length][A[0].length];
        for(int i=0;i<A.length;i++){
            for(int k=0;k<A[0].length;k++){
                C[i][k]=A[i][k]+B[i][k];
            }
        }
        return C;
    }

    /**
     * Este mertodo genera una matriz con las mismas dimensiones que A,
     * donde el valor de la celda sera 1 si el valor de la misma celda en A es igual a cero
     * de otro modo el valor de la celda es 0
     * @param A matrix int[][]
     * @return  matriz de la misma dimension que A
     */
    public static int[][] cero(int[][]A){
        int[][]C =new int[A.length][A[0].length];
        for(int i=0;i<A.length;i++){
            for(int k=0;k<A[0].length;k++){
                if(A[i][k]==0){C[i][k]=1;}
            }
        }
        return C;
    }
    public static int[][] uno(int[][]A){
        int[][]C =new int[A.length][A[0].length];
        for(int i=0;i<A.length;i++){
            for(int k=0;k<A[0].length;k++){
                if(A[i][k]!=0){C[i][k]=1;}
            }
        }
        return C;

    }

    /**
     * Calcula la matriz Transpuesta
     * @param A matrix int[][]
     * @return matrix transpuesta
     */
    public static int[][] Transpuesta(int[][]A){
        int[][]C=new int[A[0].length][A.length];
        for(int i=0;i<A.length;i++){
            for(int k=0;k<A[0].length;k++){
                C[k][i]=A[i][k];
            }
        }
        return C;
    }
}
