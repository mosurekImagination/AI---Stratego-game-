package sample;

import java.util.Arrays;

public class Stratego {


    int[][] board;
    int player;
    public final int FIRSTPTURN = 1;
    public final int SECONDPTURN = 2;
    int[] points;
    int winner;
    int moves =0;

    public boolean makeMove(int x, int y){
        return true;
    }

    public void newGame(int size){
        points = new int[2];
        board = new int[size][size];
        player = FIRSTPTURN;
        winner = 0;
        moves = 0;
    }
    public Stratego(int size){
        points = new int[2];
        board = new int[size][size];
        player = FIRSTPTURN;
        winner = 0;
    }

    private void changePlayer(){
        player = player == FIRSTPTURN ? SECONDPTURN : FIRSTPTURN;
    }

    private void checkLines(int x,int y){
        int rowsConn = checkRow(x);
        int columsConn = checkColumn(y);
        int biasConn = checkBias(x,y);
        points[getCurrentPlayer()-1]+=(rowsConn+columsConn)*getSize()+biasConn;
    }

    private int checkBias(int x, int y) {
        int sum=0;
        int sum1=0;
        int sum2=0;
        int sum3=0;
        int sum4=0;
        final int currpl = getCurrentPlayer();
        //przekątna lewo, góra
        boolean isCorrect = true;
        for(int i=x-1, j=y-1; i>=0 && j >= 0 && isCorrect; i--, j--){
            if(board[i][j] == currpl){
                sum1++;
            }
            else{
                isCorrect = false;
            }
        }
        boolean isCorrect2 = true;
        for(int i=x+1, j=y+1; i<getSize() && j< getSize() && isCorrect2; i++, j++){
            if(board[i][j] == currpl){
                sum2++;
            }
            else{
                isCorrect2 = false;
            }
        }
        sum += isCorrect && isCorrect2 ? sum1+sum2: 0;
        isCorrect = true;
        for(int i=x+1, j=y-1; i<getSize() && j>=0 && isCorrect; i++, j--){
            if(board[i][j] == currpl){
                sum3++;
            }
            else{
                isCorrect = false;
            }
        }
        isCorrect2 = true;
        for(int i=x-1, j=y+1; i>= 0 && j<getSize() && isCorrect2; i--, j++){
            if(board[i][j] == currpl){
                sum4++;
            }
            else{
                isCorrect2 = false;
            }
        }
        sum += isCorrect && isCorrect2 ? sum3+sum4: 0;
        return sum > 0 ? sum+1 : 0;
    }

    private int checkColumn(int y) {
        boolean connected = true;
        if(getCurrentPlayer()==FIRSTPTURN){
            for(int i=0; i<getSize() && connected; i++){
                connected = (board[i][y]== 1 || board[i][y]==3);
            }
        }
        if(getCurrentPlayer()==SECONDPTURN){
            for(int i=0; i<getSize() && connected; i++){
                connected = (board[i][y]== 2 || board[i][y]==4);
            }
        }
        return connected ? 1:0;
    }

    private int checkRow(int x){
        boolean connected = true;
        if(getCurrentPlayer()==FIRSTPTURN){
            for(int i=0; i<getSize() && connected; i++){
                connected = (board[x][i]== 1 || board[x][i]==3);
            }
        }
        if(getCurrentPlayer()==SECONDPTURN){
            for(int i=0; i<getSize() && connected; i++){
                connected = (board[x][i]== 2 || board[x][i]==4);
            }
        }
        return connected ? 1:0;
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

    public int getCurrentPlayer(){
        return player;
    }

    public boolean clickField(int x, int y){
        if(isTurnValid(x,y)) {
            takeField(x,y);
            checkLines(x,y);
            changePlayer();
            countMove();
            printBoard();
            return true;
        }
        return false;
    }

    private boolean isTurnValid(int x, int y) {
        return board[x][y] == 0;
    }

    public int[] getPoints(){
        return points;
    }

    public int getWinner() {
        return winner;
    }
    private void countMove(){
        moves++;
        if(isEnd()){
            if(points[0]==points[1]) winner = -1;
            else{
                winner = points[0]>points[1] ? 1:2;
            }
        }
    }

    public boolean isEnd(){
        return moves == getSize()*getSize();
    }
}
