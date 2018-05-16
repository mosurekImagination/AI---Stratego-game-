package sample;

import jdk.jshell.spi.ExecutionControl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Node {

    int[][] board;

    private int[] points;

    private int maximizePlayerScore;

    private int minimisePlayerScore;
    int value=0;
    private int x;
    private int y;

    List<Node> childs;
    StrategoLogic logic;

    public Node(int [][]board){
        this.board = board;
        childs= new LinkedList<>();
        logic = new StrategoLogic(board);
        x = -1;
        y = -1;
    }

    public void setXY(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getEarnedPoints(){
        //if root (no points gained)
        return x==-1 || y ==-1 ? 0 : logic.getPoints(x,y);
    }

    //function used to create Node Childs
    public List<Touple> getPossibleMoves(int[][]board){
        ArrayList<Touple> possibleMoves = new ArrayList<>();
        for(int i=0; i<board.length; i++){
            for(int j=0; j<board.length; j++){
                if(board[i][j]==0){
                    possibleMoves.add(new Touple(i,j));
                }
            }
        }
        return possibleMoves;
    }

    public List<Node> getChilds(){
        //if Function is called first time for node
        if(childs.size()==0){
            //make Node child of all possible moves
            List<Touple> possibleMoves = getPossibleMoves(board);

            //for every child
            for (Touple t:possibleMoves) {

                //Make ONE child with filled ONE of all valid moves
                Node child = new Node(logic.fillBoardField(board, t.x,t.y));
                child.setXY(t.x,t.y);
                childs.add(child);
                child.setPoints(maximizePlayerScore, minimisePlayerScore);
            }
        }
        //printChildBoards();
        return childs;
    }

    //check if game ends
    //all fields have to not be equal 0

    public boolean isTerminal(){
        boolean isTerminal = true;
        for(int i=0; i<logic.getSize() && isTerminal; i++){
            for(int j=0; j<logic.getSize() && isTerminal; j++){
                if(board[i][j] == 0) isTerminal = false;
            }
        }
        return isTerminal;
    }
    //get Child node with the higher value
    public Node getBestChild() {
        int maxValue=0;
        int index=0;
        for (Node child: childs) {
            if( child.value > maxValue){
                maxValue = child.value;
                index = childs.indexOf(child);
            }
        }
        return childs.get(index);
    }

    private void printChildBoards(){
        System.out.println("Board:");
        logic.printBoard();
        System.out.println("has Childs:");
        for (Node n: childs) {
            n.logic.printBoard();
            System.out.println("---------------------------------");
        }
    }
    public int[] getPoints() {
        return points;
    }

    public void setPoints(int points, boolean maximizePlayer) {
       if(maximizePlayer) maximizePlayerScore = points;
       else {
           minimisePlayerScore = points;
       }
    }

    public void setPoints(int maxP, int minP) {
        maximizePlayerScore = maxP;
        minimisePlayerScore = minP;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getMaximizePlayerScore() {
        return maximizePlayerScore;
    }

    public void setMaximizePlayerScore(int maximizePlayerScore) {
        this.maximizePlayerScore = maximizePlayerScore;
    }

    public int getMinimisePlayerScore() {
        return minimisePlayerScore;
    }

    public void setMinimisePlayerScore(int minimisePlayerScore) {
        this.minimisePlayerScore = minimisePlayerScore;
    }
}
