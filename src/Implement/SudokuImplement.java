package Implement;

import interfaces.SudokuInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SudokuImplement extends UnicastRemoteObject implements SudokuInterface {

    public int[][] sudoku;
    public int tamanioCuadrante;
    public Random random;

    public SudokuImplement() throws RemoteException {
        this.random = new Random();
    }

    @Override
    public int[][] generarSudoku(int tamanioSudoku, int tamanioCuadrante) throws RemoteException {
        this.sudoku = new int[tamanioSudoku][tamanioSudoku];
        this.tamanioCuadrante = tamanioCuadrante;

        this.inicializarSudoku();
        this.llenarPrimeraFila();
        this.resolverJuego();
        return this.sudoku;
    }

    @Override
    public void imprimirSudoku(int[][] sudokuImprimir, int tamanioCuadrante) throws RemoteException {
        int espaciosLineas = (sudokuImprimir[0].length*4) + (tamanioCuadrante*2);
        System.out.println("Imprimiendo Sudoku " + sudokuImprimir.length + "x" + sudokuImprimir[0].length);
        System.out.println("-".repeat(espaciosLineas));

        for (int fila = 0; fila < sudokuImprimir[0].length; fila++) {
            if (fila > 0 && fila % tamanioCuadrante == 0) {
                System.out.println("-".repeat(espaciosLineas));
            }

            System.out.print("| ");
            for (int columna = 0; columna < sudokuImprimir[0].length; columna++) {
                if (columna > 0 && columna % tamanioCuadrante == 0) {
                    System.out.print("| ");
                }

                if (sudokuImprimir[fila][columna] < 10) {
                    System.out.print(" ");
                }
                System.out.print(" " + sudokuImprimir[fila][columna] + " ");
            }
            System.out.println("|");
        }
        System.out.println("-".repeat(espaciosLineas));
    }

    public void inicializarSudoku() throws RemoteException {
        for (int i = 0; i < this.sudoku[0].length; i++) {
            for (int j = 0; j < this.sudoku[0].length; j++) {
                this.sudoku[i][j] = 0;
            }
        }
    }

    public void llenarPrimeraFila() throws RemoteException {
        List<Integer> numeros = this.generarNumerosAleatorios();
        for (int i = 0; i < this.sudoku[0].length; i++) {
            this.sudoku[0][i] = numeros.get(i);
        }
    }

    public boolean resolverJuego() throws RemoteException {
        for (int fila = 0; fila < this.sudoku[0].length; fila++) {
            for (int columna = 0; columna < this.sudoku[0].length; columna++) {
                if (this.sudoku[fila][columna] == 0) {
                    List<Integer> numeros = this.generarNumerosAleatorios();

                    for (Integer numero : numeros) {
                        if (movimientoEsValido(fila, columna, numero)) {
                            this.sudoku[fila][columna] = numero;

                            if (resolverJuego()) {
                                return true;
                            }

                            this.sudoku[fila][columna] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    public boolean movimientoEsValido(int fila, int columna, int numero) throws RemoteException {
        return !this.numeroEstaEnFila(fila, numero) &&
                !this.numeroEstaEnColumna(columna, numero) &&
                !this.numeroEstaEnCuadrante(fila, columna, numero);
    }

    public List<Integer> generarNumerosAleatorios() throws RemoteException {
        List<Integer> numeros = new ArrayList<>();
        for (int i = 1; i <= this.sudoku[0].length; i++) {
            numeros.add(i);
        }
        Collections.shuffle(numeros, random);
        return numeros;
    }

    public boolean numeroEstaEnFila(int fila, int numero) throws RemoteException {
        for (int columna = 0; columna < this.sudoku[0].length; columna++) {
            if ( this.sudoku[fila][columna] == numero) {
                return true;
            }
        }
        return false;
    }

    public boolean numeroEstaEnColumna(int columna, int numero) throws RemoteException {
        for (int fila = 0; fila < this.sudoku[0].length; fila++) {
            if (this.sudoku[fila][columna] == numero) {
                return true;
            }
        }
        return false;
    }

    public boolean numeroEstaEnCuadrante(int fila, int columna, int numero) throws RemoteException {
        int filaInicio = (fila / this.tamanioCuadrante) * this.tamanioCuadrante;
        int columnaInicio = (columna / this.tamanioCuadrante) * this.tamanioCuadrante;

        for (int i = filaInicio; i < filaInicio + this.tamanioCuadrante; i++) {
            for (int j = columnaInicio; j < columnaInicio + this.tamanioCuadrante; j++) {
                if (this.sudoku[i][j] == numero) {
                    return true;
                }
            }
        }
        return false;
    }
}
