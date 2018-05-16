package sample;

import jdk.jshell.spi.ExecutionControl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StrategoAI {

    public int DEPTH;
    private int heurestic_value;

    public static final int HEURESTIC_MAX_POINTS = 1;
    public static final int HEURESTIC_MAX_DIFFERENCE = 2;

    private int algorithm_type;
    public static final int TYPE_MIN_MAX_SIMPLY = 1;
    public static final int TYPE_MIN_MAX_ALFABETA = 2;

    Node root;

    public void setDepth(int depth){
        this.DEPTH = depth;
    }

    public Touple getNextMove(int[][] board){
        root = new Node(board);
        root.setPoints(0,0);
        if(algorithm_type == TYPE_MIN_MAX_ALFABETA) {
            alphaBeta(root, DEPTH, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
        }
        else if(algorithm_type == TYPE_MIN_MAX_SIMPLY){
            minMax(root, DEPTH, true);
        }
        Node bestNode = root.getBestChild();
        return new Touple(bestNode.getX(), bestNode.getY());
    }

    private int minMax(Node node, int depth, boolean maximizingPlayer) {

        //base case
        if(depth==0 || node.isTerminal()){
            return getHeuresticValue(node);
        }

        //initialise necessary things for algorithm
        int bestValue;
        List<Node> children = node.getChilds();

        if(maximizingPlayer){
            bestValue = Integer.MIN_VALUE;
            for (Node n:children) {
                //set points (maxPlayer + new Points, minPlayer)
                n.setPoints(node.getMaximizePlayerScore()+n.getEarnedPoints(), node.getMinimisePlayerScore());
                bestValue=Math.max(bestValue, minMax(n, depth-1, false));
            }
        }
        else{
            bestValue = Integer.MAX_VALUE;

            for (Node n:children) {
                //set points (maxPlayer, minPlayer + new Points)
                n.setPoints(node.getMaximizePlayerScore(), node.getMinimisePlayerScore()+n.getEarnedPoints());
                bestValue=Math.min(bestValue, minMax(n, depth-1, true));
            }
        }
        node.value = bestValue;
        return bestValue;
    }


    public int alphaBeta(Node node, int depth, int alpha, int beta, boolean maximizingPlayer){

        //base case
        if(depth==0 || node.isTerminal()){
            return getHeuresticValue(node);
        }

        List<Node> children = node.getChilds();
        //Maximizing Player
        if(maximizingPlayer){
            for (Node n:children) {
                n.setPoints(node.getMaximizePlayerScore() + n.getEarnedPoints(),node.getMinimisePlayerScore());
                alpha = Math.max(alpha,alphaBeta(n,depth-1, alpha, beta, false));
                if(alpha >= beta)
                    break;
            }
            node.value = alpha;
            return alpha;
        }

        //Minimizing Player
        else {
            for (Node n:children) {
                n.setPoints(node.getMaximizePlayerScore(), node.getMinimisePlayerScore()+ n.getEarnedPoints());
                beta = Math.min(beta,alphaBeta(n,depth-1, alpha, beta, true));
                node.value = beta;
                if(alpha >= beta)
                    break;
            }
            return beta;
        }
    }

    private int getHeuresticValue(Node node) {
        if(heurestic_value == HEURESTIC_MAX_POINTS){
            return node.getMinimisePlayerScore();
        }
        if(heurestic_value == HEURESTIC_MAX_DIFFERENCE){
            return node.getMaximizePlayerScore()-node.getMinimisePlayerScore();
        }

        return -1;
    }


    public void setHeurestic_value(int heurestic){
        heurestic_value = heurestic;
    }

    public void setAlgorithm_type(int type){
        algorithm_type = type;
    }
}
