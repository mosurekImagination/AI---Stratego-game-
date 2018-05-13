package sample;

import jdk.jshell.spi.ExecutionControl;

import java.util.ArrayList;
import java.util.List;

public class Node {

    int[][] board;
    int moves;

    public List<Touple> getPossibleMoves(int[][]board){
        ArrayList<Touple> possiblemoves = new ArrayList<>();
        for(int i=0; i<board.length; i++){
            for(int j=0; j<board.length; j++){
                if(board[i][j]==0){
                    possiblemoves.add(new Touple(i,j));
                }
            }
        }
        return possiblemoves;
    }

//    public List<Node> getChilds(){
//        return new ExecutionControl.NotImplementedException();
//    }

    public boolean isTerminal(){
        return moves == board.length * board.length; //all fields are filled
    }
}
