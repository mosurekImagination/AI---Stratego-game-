package sample;

import java.lang.reflect.Array;
import java.util.Arrays;

public class StrategoLogic {

    int[][] board;

    public StrategoLogic(int[][] board){
        this.board = board;
    }
    //return points get by user with move to field X,Y
    public int getPoints(int x,int y){
        int rowsConn = checkRow(x);
        int columsConn = checkColumn(y);
        int biasConn = checkBias(x,y);
        return (rowsConn+columsConn)*getSize()+biasConn;
    }

    private int checkBias(int x, int y) {
        int sum=0;
        int sum1=0;
        int sum2=0;
        int sum3=0;
        int sum4=0;
        final int EMPTY = 0;
        //lewo, góra
        boolean isCorrect = true;
        for(int i=x-1, j=y-1; i>=0 && j >= 0 && isCorrect; i--, j--){
            if(board[i][j] != EMPTY){
                sum1++;
            }
            else{
                isCorrect = false;
            }
        }
        //prawo doł
        boolean isCorrect2 = true;
        for(int i=x+1, j=y+1; i<getSize() && j< getSize() && isCorrect2; i++, j++){
            if(board[i][j] != EMPTY){
                sum2++;
            }
            else{
                isCorrect2 = false;
            }
        }
        sum += isCorrect && isCorrect2 && sum1+sum2 > 0 ? sum1+sum2+1: 0;
        isCorrect = true;


        //lewo doł
        for(int i=x+1, j=y-1; i<getSize() && j>=0 && isCorrect; i++, j--){
            if(board[i][j] != EMPTY){
                sum3++;
            }
            else{
                isCorrect = false;
            }
        }
        isCorrect2 = true;

        //prawo góra
        for(int i=x-1, j=y+1; i>= 0 && j<getSize() && isCorrect2; i--, j++){
            if(board[i][j] != EMPTY){
                sum4++;
            }
            else{
                isCorrect2 = false;
            }
        }
        sum += isCorrect && isCorrect2 && sum3+sum4 >0 ? sum3+sum4+1: 0;
        return sum;
    }

    private int checkColumn(int y) {
        boolean connected = true;

        for(int i=0; i<getSize() && connected; i++){
            connected = (board[i][y]!= 0);
        }
        return connected ? 1:0;
    }
    private int checkRow(int x){
        boolean connected = true;

        for(int i=0; i<getSize() && connected; i++) {
            connected = (board[x][i] != 0);
        }
        return connected ? 1:0;
    }

    public int getSize(){
        return board.length;
    }

    public int[][] fillBoardField(int[][] board, int x, int y){
        int[][] newBoard = new int[board.length][board.length];
        for(int i=0; i<board.length; i++){
            newBoard[i]= Arrays.copyOf(board[i],board.length);
        }
        newBoard[x][y]=1;
        return newBoard;
    }

    public void printBoard(){
        for (int[] a: board) {
            for (int b: a) {
                System.out.print(b+" ");
            }
            System.out.println();
        }
    }
}
