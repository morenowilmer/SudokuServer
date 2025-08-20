package server;

import Implement.SudokuImplement;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class SudokuServer {

    public static void main(String[] args) throws Exception {
        Registry registry = LocateRegistry.createRegistry(1099);
        SudokuImplement sudokuImplement = new SudokuImplement();
        registry.rebind("Sudoku", sudokuImplement);
        System.out.println("Servidor de sudoku iniciado");
    }
}
