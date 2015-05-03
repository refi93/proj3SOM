/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj3som;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 *
 * @author raf
 */
public class Proj3SOM {

    public static Matrix loadData(String fileName) throws FileNotFoundException{
        Scanner in = new Scanner(new FileReader(fileName));
        Matrix ret = new Matrix();
        while(in.hasNext()){
            ret.addRow();
            for (int i = 0; i < 5; i++){
                Double input = Double.parseDouble(in.next());
                ret.get(ret.numRows() - 1).add(input);
            }
        }
        
        return ret;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        int neuronRows = 10;
        int neuronCols = 10;
        int dataDimensionsCount = 4;
        
        double alpha = 0.05;
        double lambdaInitial = 15;
        double lambdaFinal = 1;
        int epochCount = 100;
        
        // inicializujeme vahove matice
        ArrayList<Matrix> weightMatrices = new ArrayList<>();
        for (int i = 0; i < dataDimensionsCount; i++) {
            Matrix weightMatrix = Helpers.randMatrix(neuronRows, neuronCols);
            weightMatrices.add(weightMatrix);
        }
        
        Matrix data = loadData("iris.dat");
        
        // trenovanie
        for (int ep = 0; ep < epochCount; ep++) {
            data.shuffleRows();
            double lambda = lambdaInitial * Math.pow(lambdaFinal / lambdaInitial, ep / epochCount);
            double winnerAvgDist = 0.0;
            
            for (int i = 0; i < data.numRows(); i++) {
                ArrayList<Double> x = new ArrayList<>(data.get(i));
                x.remove(x.size() - 1); // aby sme nevyuzili pri trenovani prislusnost k triede
                int winnerRow = 0;
                int winnerCol = 0;
                double winnerScore = 100000;
                
                // najdeme vitazny neuron
                for (int r = 0; r < neuronRows; r++) {
                    for (int c = 0; c < neuronCols; c++) {
                        ArrayList<Double> neuronCoordinates = new ArrayList<>();
                        for (int k = 0; k < dataDimensionsCount; k++) {
                           neuronCoordinates.add(weightMatrices.get(k).get(r).get(c));
                        }
                       
                        double score = Helpers.vectorNorm(Helpers.vectorOperation(x, neuronCoordinates, '-'));
                        System.out.println(score);
                        if (score < winnerScore) {
                            winnerRow = r;
                            winnerCol = c;
                            winnerScore = score;
                        }

                    }
                }
                System.out.println(winnerRow + " " + winnerCol);
                winnerAvgDist += winnerScore;
                // updatujeme vahy
                ArrayList<Double> coordWinner = new ArrayList<>();
                coordWinner.add(new Double(winnerRow));
                coordWinner.add(new Double(winnerCol));
                
                for (int r = 0; r < neuronRows; r++) {
                    for (int c = 0; c < neuronCols; c++) {
                        ArrayList<Double> coordNeuron = new ArrayList<>();
                        coordNeuron.add(new Double(r));
                        coordNeuron.add(new Double(c));
                        for (int k = 0; k < dataDimensionsCount; k++ ) {
                            double oldWeight = weightMatrices.get(k).get(r).get(c);
                            double diff = x.get(k) - oldWeight;
                            double deltaW =  alpha * Helpers.gaussianNeighborhood(coordWinner, coordNeuron, lambda) * diff;
                            
                            weightMatrices.get(k).get(r).set(c, oldWeight + diff);
                        }
                    }
                }
            }
            winnerAvgDist /= data.numRows();
            System.out.println(winnerAvgDist);
        }
    }
    
}