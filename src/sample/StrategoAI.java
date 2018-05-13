package sample;

import jdk.jshell.spi.ExecutionControl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StrategoAI {

    public int DEPTH;
    Node root;

    public void setDepth(int depth){
        this.DEPTH = depth;
    }

    public Touple getNextMove(int[][] board){
        root = new Node(board);
        root.setPoints(0);
        alphaBeta(root,DEPTH,Integer.MIN_VALUE, Integer.MAX_VALUE, true, 0);
        Node bestNode = root.getBestChild();
        return new Touple(bestNode.getX(), bestNode.getY());
    }

    public int alphaBeta(Node node, int depth, int alpha, int beta, boolean maximizingPlayer, int points){

        //base case
        if(depth==0 || node.isTerminal()){
            return getHeuresticValue(node);
        }

        List<Node> children = node.getChilds();
        //Maximizing Player
        if(maximizingPlayer){
            for (Node n:children) {
                n.setPoints(node.getPoints()+n.getEarnedPoints());
                alpha = Math.max(alpha,alphaBeta(n,depth-1, alpha, beta, false,0));
                if(alpha >= beta)
                    break;
            }
            node.value = alpha;
            return alpha;
        }

        //Minimizing Player
        else {
            for (Node n:children) {
                n.setPoints(node.getPoints());
                beta = Math.min(beta,alphaBeta(n,depth-1, alpha, beta, true,0));
                node.value = beta;
                if(alpha >= beta)
                    break;
            }
            return beta;
        }
    }

    private int getHeuresticValue(Node node) {
        return node.getPoints();
    }

}
