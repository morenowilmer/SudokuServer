package client;

import interfaces.SudokuInterface;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SudokuClient {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int opcion = 0;

        do {
            System.out.println("GENERADOR SUDOKU");
            System.out.println("1. Matriz 4x4");
            System.out.println("2. Matriz 9x9");
            System.out.println("3. Matriz 16x16");
            System.out.println("4. Salir");

            try {
                opcion = Integer.parseInt(br.readLine());
            } catch (NumberFormatException e) {
                opcion = 0;
            } catch (Exception e) {
                System.err.println("Ocurrió un error al leer la opción ingresada");
            }

            if (opcion != 0 && opcion != 4) {
                try {
                    int[][] sudoku = null;
                    int tamanioCuadrante = 0;
                    SudokuInterface juegoSudoku = (SudokuInterface) Naming.lookup("Sudoku");
                    sudoku = switch (opcion) {
                        case 1 -> {
                            tamanioCuadrante = 2;
                            yield juegoSudoku.generarSudoku(4, tamanioCuadrante);
                        }
                        case 2 -> {
                            tamanioCuadrante = 3;
                            yield juegoSudoku.generarSudoku(9, tamanioCuadrante);
                        }
                        case 3 -> {
                            tamanioCuadrante = 4;
                            yield juegoSudoku.generarSudoku(16, tamanioCuadrante);
                        }
                        default -> sudoku;
                    };
                    juegoSudoku.imprimirSudoku(sudoku, tamanioCuadrante);
                } catch (NotBoundException | MalformedURLException | RemoteException ex) {
                    Logger.getLogger(SudokuClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } while (opcion != 4);
    }
}