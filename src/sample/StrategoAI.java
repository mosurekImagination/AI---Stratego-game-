package sample;

import jdk.jshell.spi.ExecutionControl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StrategoAI {

    int size;

    public StrategoAI(int size){
        this.size=size;
    }

//    public Touple getNextMove(int[][] board){
//
//    }

//    public int alphaBeta(Node node, int depth, int alpha, int beta, boolean maximizingPlayer){
//
//        //base case
//        if(depth==0 || node.isTerminal()){
//            return getHeuresticValue(node);
//        }
//
//        List<Node> possibleMoves = node.getChilds();
//        //Maximizing Player
//        if(maximizingPlayer){
//            for (Node n:possibleMoves) {
//                alpha = Math.max(alpha,alphaBeta(n,depth-1, alpha, beta, false));
//
//                if(alpha >= beta)
//                    break;
//            }
//            return alpha;
//        }
//
//        //Minimizing Player
//        else {
//            for (Node n:possibleMoves) {
//                beta = Math.min(beta,alphaBeta(n,depth-1, alpha, beta, true));
//
//                if(alpha >= beta)
//                    break;
//            }
//            return beta;
//        }
//    }

    private int getHeuresticValue(Node node) {
        return 0;
    }

}
