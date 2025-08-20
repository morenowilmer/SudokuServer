package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SudokuInterface extends Remote {

    int[][] generarSudoku(int tamanioSudoku, int tamanioCuadrante) throws RemoteException;
    void imprimirSudoku(int[][] sudoku, int tamanioCuadrante) throws RemoteException;
}
