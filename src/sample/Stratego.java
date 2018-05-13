package sample;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class Stratego {


    int[][] board;
    int player;
    public final int FIRST_PLAYER = 1;
    public final int SECOND_PLAYER = 2;
    public final int DRAW = -1;
    public final int CUR_PLAYING = 0;
    public final int[] ai_players; 
    int[] points;
    int winner;
    int moves =0;
    StrategoAI ai;

    public Stratego(int size){
        points = new int[2];
        board = new int[size][size];
        ai_players = new int[2];
        player = FIRST_PLAYER;
        winner = 0;

        //INIT AI CORE
        ai = new StrategoAI(size);
    }

    public boolean makeMove(int x, int y){
        return true;
    }

    public boolean clickField(int x, int y){
        if(!isEnd()) {
            if (isTurnValid(x, y)) {

                //GAME CORE INSTRUCTIONS
                takeField(x, y);
                checkLines(x, y);
                changePlayer();
                countMove();
                printBoard();

                //AI INSTRUCTIONS
//                if(isAIControlled(player)){
//                    clickField(ai.getNextMove(board,))
//                }
                return true;
            }
        }
        return false;
    }

    private boolean isAIControlled(int player) {
        return ai_players[player-1] == 1;
    }

    public void newGame(int size, int AI1PLAYER, int AI2PLAYER){
        points = new int[2];
        board = new int[size][size];

        //INITIALISE WHICH PLAYER WILL BE CONTROLLED BY AI
        ai_players[0]=AI1PLAYER;
        ai_players[1]=AI2PLAYER;

        winner = 0;
        moves = 0;

        //SET FIRST PLAYER TURN
        player = FIRST_PLAYER;
    }

    private void changePlayer(){
        player = player == FIRST_PLAYER ? SECOND_PLAYER : FIRST_PLAYER;
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
        final int currpl = 0;
        //lewo, góra
        boolean isCorrect = true;
        for(int i=x-1, j=y-1; i>=0 && j >= 0 && isCorrect; i--, j--){
            if(board[i][j] != currpl){
                sum1++;
            }
            else{
                isCorrect = false;
            }
        }
        //prawo doł
        boolean isCorrect2 = true;
        for(int i=x+1, j=y+1; i<getSize() && j< getSize() && isCorrect2; i++, j++){
            if(board[i][j] != currpl){
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
            if(board[i][j] != currpl){
                sum3++;
            }
            else{
                isCorrect = false;
            }
        }
        isCorrect2 = true;

        //prawo góra
        for(int i=x-1, j=y+1; i>= 0 && j<getSize() && isCorrect2; i--, j++){
            if(board[i][j] != currpl){
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
        if(getCurrentPlayer()==FIRST_PLAYER){
            for(int i=0; i<getSize() && connected; i++){
                connected = (board[i][y]!= 0);
            }
        }
        if(getCurrentPlayer()==SECOND_PLAYER){
            for(int i=0; i<getSize() && connected; i++){
                connected = (board[i][y]!= 0);
            }
        }
        return connected ? 1:0;
    }
    private int checkRow(int x){
        boolean connected = true;
        if(getCurrentPlayer()==FIRST_PLAYER){
            for(int i=0; i<getSize() && connected; i++){
                connected = (board[x][i]!= 0);
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
            if(points[0]==points[1]) winner = DRAW;
            else{
                winner = points[0]>points[1] ? 1:2;
            }
        }
    }

    public boolean isEnd(){
        return moves == getSize()*getSize();
    }


}
