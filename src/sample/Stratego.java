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
    public final boolean[] ai_players;
    int[] points;
    int winner;
    int moves =0;
    StrategoAI ai;
    StrategoAI secondAi;
    boolean turnament_mode;

    //TIME
    long FirstPlayerTime = 0;
    long SecondPlayerTime = 0;
    long timer;

    public Stratego(int size){
        points = new int[2];
        board = new int[size][size];
        ai_players = new boolean[2];
        player = FIRST_PLAYER;
        winner = 0;
        timer = System.currentTimeMillis();
    }

    public boolean clickField(int x, int y){
        if(!isEnd()) {
            if (isTurnValid(x, y)) {

                //GAME CORE INSTRUCTIONS
                takeField(x, y);
                checkLines(x, y);
                endPlayerTimer(player);
                changePlayer();
                countMove();
                printBoard();
                return true;
            }
        }
        return false;
    }
    public void setAiHeurestic(int firstPHeurestic, int secondPHeurestic){
        if(!turnament_mode){
            turnament_mode = true;
            secondAi = new StrategoAI();
        }
        ai.setHeurestic_value(firstPHeurestic);
        secondAi.setHeurestic_value(secondPHeurestic);

    }

    public void setAiAlgorithm(int firstPAlgorithm, int secondPAlgorithm){
        if(!turnament_mode){
            turnament_mode = true;
            secondAi = new StrategoAI();
        }
        ai.setAlgorithm_type(firstPAlgorithm);
        secondAi.setAlgorithm_type(secondPAlgorithm);
    }
    public void setDepths(int depth1AI, int depth2AI){
        ai.setDepth(depth1AI);
        secondAi.setDepth(depth2AI);
    }

    public void setDepth(int depth1AI){
        ai.setDepth(depth1AI);
    }
    public Touple getAImove(){
        Touple move = null;
        if(turnament_mode){
            if (isAIControlled(player)) {
                move = player == FIRST_PLAYER ? ai.getNextMove(board): secondAi.getNextMove(board);
            }
        }
        if(isAIControlled(player)){
            move = ai.getNextMove(board);
        }
        return move;
    }
    private boolean isAIControlled(int player) {
        return ai_players[player-1];
    }

    private void endPlayerTimer(int player){
        if(player == FIRST_PLAYER)
            FirstPlayerTime+= System.currentTimeMillis()-timer;
        if(player == SECOND_PLAYER)
            SecondPlayerTime+= System.currentTimeMillis()-timer;

        timer = System.currentTimeMillis();
    }

    public void newGame(int size, boolean AI1PLAYER, boolean AI2PLAYER){
        resetTimers();

        points = new int[2];
        board = new int[size][size];

        //INITIALISE WHICH PLAYER WILL BE CONTROLLED BY AI
        ai_players[0]=AI1PLAYER;
        ai_players[1]=AI2PLAYER;

        winner = 0;
        moves = 0;

        //SET FIRST PLAYER TURN
        player = FIRST_PLAYER;

        //INIT AI
        ai = new StrategoAI();
        ai.setHeurestic_value(StrategoAI.HEURESTIC_MAX_DIFFERENCE);

        if( AI1PLAYER && AI2PLAYER){
            secondAi = new StrategoAI();
        }
        turnament_mode = AI1PLAYER && AI2PLAYER;
    }

    private void resetTimers() {
        timer = System.currentTimeMillis();
        FirstPlayerTime = 0;
        SecondPlayerTime = 0;
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

    public boolean isAITurn(){
        return isAIControlled(player);
    }

    public void setAiHeurestic(int heurestic){
        ai.setHeurestic_value(heurestic);
    }
    public void setAiAlgorithm(int algorithm){
        ai.setAlgorithm_type(algorithm);
    }
    public boolean makeMove(int x, int y){
        return true;
    }
    public double getFirstPlayerTime(){
        return FirstPlayerTime/1000;
    }
    public double getSecondPlayerTime(){
        return SecondPlayerTime/1000;
    }

    public void setOrderType(int orderType){
        ai.setOrderType(orderType);
    }

    public void setOrderTypes(int firstPlayerOrderType, int secondPlayerOrderType){
        ai.setOrderType(firstPlayerOrderType);
        secondAi.setOrderType(secondPlayerOrderType);
    }
}
