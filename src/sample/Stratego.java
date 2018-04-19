package sample;

import java.util.Arrays;

public class Stratego {


    int[][] board;
    int player;
    private final int FIRSTP = 1;
    private final int SECONDP = 2;

    public boolean makeMove(int x, int y){
        return true;
    }
    public Stratego(int size){
        board = new int[size][size];
        player = 0;
    }

    private void changePlayer(){
        player = player == FIRSTP ? SECONDP : FIRSTP;
    }

    private void takeField(int x, int y){
        board[x][y] = player;
    }

    public void printBoard(){
        for (int[] a: board) {
            for (int b: a) {
                System.out.print(b+" ");
            }
            System.out.println();
        }
    }

    public int getSize(){
        return board.length;
    }
}
