/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj3som;

import java.util.ArrayList;

/**
 *
 * @author raf
 */

// pomocne funkcie, predovsetkym na jednoduchsiu pracu s maticami
public class Helpers {
    
    // vyratame priemery pre jednotlive stlpce a vratime ako stlpcovy vektor
    public static Matrix matrixAverage(Matrix data) {
        int rows = data.numRows();
        int cols = data.numCols();
        
        Matrix ret = new Matrix();
        
        for (int c = 0; c < cols; c++) {
            ret.addRow();
            double avg = 0;
            for (int r = 0; r < rows; r++) {
                avg += data.get(r).get(c);
            }
            avg /= data.numRows();
            ret.get(c).add(avg);
        }
        
        return ret;
    }
    
    public static double mean(ArrayList<Double> a) {
        double mean = 0;
        for (Double elem : a) {
            mean += elem;
        }
        return mean / a.size();
    }
    
    public static double stdDev(ArrayList<Double> a) {
        double mean = Helpers.mean(a);
        double stdev = 0;
        
        for (Double elem : a) {
            stdev += (elem - mean) * (elem - mean);
        }
        
        stdev = Math.sqrt(0.5 * stdev);
        return Math.sqrt(0.5 * stdev);
    }
    
    // vratime ako stlpcovy vektor standardne odchylky
    public static Matrix matrixStdDev(Matrix data) {
        Matrix dataAvg = Helpers.matrixAverage(data);
        int rows = data.numRows();
        int cols = data.numCols();
        
        Matrix ret = new Matrix();
        
        for (int c = 0; c < cols; c++) {
            ret.addRow();
            double stdDev = 0;
            for (int r = 0; r < rows; r++) {
                stdDev += Math.pow((data.get(r).get(c) - dataAvg.get(c).get(0)), 2);
            }
            stdDev = Math.sqrt(stdDev / rows);
            ret.get(c).add(stdDev);
        }
        
        return ret;
    }
    
    static Double letterToNumber(char c) {
        return 0.0 + c - 'A' + 1; 
    }
    
    static char numberToLetter(int n) {
        return (char) ('A' + n  -1);
    }
    
    // overi, ci vektory maju rovnake velkosti
    private static void checkVectorSizesForEquality(ArrayList<Double> v1, ArrayList<Double> v2){
        if (v1.size() != v2.size()){
            System.err.println("vector sizes do not match!!!");
            System.exit(-1);
        }
    }
    
    static double crossProduct(ArrayList<Double> v1, ArrayList<Double> v2){
        checkVectorSizesForEquality(v1, v2);
        
        Double ret = 0.0;
        for (int i = 0; i < v1.size(); i++){
            ret += v1.get(i) * v2.get(i);
        }
        
        return ret;
    }
    
    static ArrayList<Double> sumVectors(ArrayList<Double> v1, ArrayList<Double> v2){
        checkVectorSizesForEquality(v1, v2);
        
        ArrayList<Double> ret = new ArrayList<>();
        for(int i = 0; i < v1.size(); i++){
            ret.add(v1.get(i) + v2.get(i));
        }
        
        return ret;
    }
    
    static Matrix scalarProduct(Matrix matrix, double scalar){
        Matrix ret = new Matrix();
        int rows = matrix.numRows();
        int cols = matrix.numCols();
        
        for (int r = 0; r < rows; r++) {
            ret.addRow();
            for (int c = 0; c < cols; c++) {
                ret.get(r).add(matrix.get(r).get(c) * scalar);
            }
        }
        
        return ret;
    }
    
    static double average(ArrayList<Double> numbers){
        double sum = 0;
        for (double x : numbers){
            sum += x;
        }
        return sum / numbers.size();
    }
    
    static double sigmoid(double y){
        return 1.0/(1.0 + Math.pow(Math.E, -y));
    }
    
    static double sigmoidDerivative(double y){
        return sigmoid(y) * (1 - sigmoid(y));
    }
    
    static Matrix transpose(Matrix matrix){
        int rows = matrix.numRows();
        int cols = matrix.numCols();
        
        Matrix ret = new Matrix();
        
        for (int c = 0; c < cols; c++){
            ret.addRow();
            for (int r = 0; r < rows; r++){
                ret.get(c).add(matrix.get(r).get(c));
            }
        }
        
        return ret;
    }
    
    static Matrix matrixProduct(Matrix matrix1, Matrix matrix2){
        Matrix ret = new Matrix();
        int matrix1Rows = matrix1.numRows();
        int matrix1Cols = matrix1.numCols();
        int matrix2Rows = matrix2.numRows();
        int matrix2Cols = matrix2.numCols();
        
        if (matrix1Cols != matrix2Rows){
            System.err.println(matrix1);
            System.err.println(matrix2);
            System.err.println("NESEDIA VELKOSTI MATIC PRI NASOBENI");
            System.exit(0);
        }
        
        for (int r = 0; r < matrix1Rows; r++){
            ret.addRow();
            for (int c = 0; c < matrix2Cols; c++){
                Double result = 0.0;
                for (int i = 0; i < matrix1Cols; i++){
                    result += matrix1.get(r).get(i) * matrix2.get(i).get(c);
                }
                ret.get(r).add(result);
            }
        }
        
        return ret;
    }
    
    static Matrix randMatrix (int rows, int cols) {
        Matrix ret = new Matrix();
        for (int r = 0; r < rows; r++) {
            ret.addRow();
            for (int c = 0; c < cols; c++) {
                ret.get(r).add(Math.random());
            }
        }
        
        return ret;
    }
    
    static Matrix vectorToMatrix (ArrayList<Double> vector) {
        Matrix ret = new Matrix();
        for (int i = 0; i < vector.size(); i++) {
            ret.addRow();
            ret.get(i).add(vector.get(i));
        }
        
        return ret;
    }
    
    static Matrix matrixSigmoid(Matrix netMatrix) {
        Matrix ret = new Matrix();
        for (int r = 0; r < netMatrix.numRows(); r++) {
            ret.addRow();
            for (int c = 0; c < netMatrix.get(r).size(); c++) {
                ret.get(r).add(Helpers.sigmoid(netMatrix.get(r).get(c)));
            }
        }
        return ret;
    }
    
    static Matrix matrixDeepCopy(Matrix matrix) {
        Matrix ret = new Matrix();
        for (int r = 0; r < matrix.numRows(); r++) {
            ret.addRow();
            for (int c = 0; c < matrix.get(r).size(); c++) {
                ret.get(r).add(matrix.get(r).get(c));
            }
        }
        
        return ret;
    }
    
    static Matrix appendBias(Matrix matrix) {
        Matrix ret = Helpers.matrixDeepCopy(matrix);
        int cols = ret.numCols();
        ret.addRow();
        for (int c = 0; c < cols; c++) {
           ret.get(ret.numRows() - 1).add(-1.0);
        }
        
        return ret;
    }
    
    static Matrix numberMatrix(int rows, int cols, double number) {
        Matrix ret = new Matrix();
        for (int r = 0; r < rows; r++) {
            ret.addRow();
            for (int c = 0; c < cols; c++) {
                ret.get(r).add(number);
            }
        }
        
        return ret;
    }
    
    static Matrix matrixSum(Matrix matrix1, Matrix matrix2){
        int rows = matrix1.numRows();
        int cols = matrix1.numCols();
        
        Matrix ret = new Matrix();
        for (int r = 0; r < rows; r++) {
            ret.addRow();
            for (int c = 0; c < cols; c++) {
                ret.get(r).add(matrix1.get(r).get(c) + matrix2.get(r).get(c));
            }
        }
        
        return ret;
    }
    
    static Matrix matrixSubstract(Matrix matrix1, Matrix matrix2){
        int rows = matrix1.numRows();
        int cols = matrix1.numCols();
        
        Matrix ret = new Matrix();
        for (int r = 0; r < rows; r++) {
            ret.addRow();
            for (int c = 0; c < cols; c++) {
                ret.get(r).add(matrix1.get(r).get(c) - matrix2.get(r).get(c));
            }
        }
        
        return ret;
    }
    
    static Matrix matrixComponentProduct(Matrix matrix1, Matrix matrix2) {
        int rows = matrix1.numRows();
        int cols = matrix1.numCols();
        if (rows != matrix2.numRows() || cols != matrix2.numCols()) {
            System.err.println("MATICE NEMAJU ROVNAKE ROZMERY");
            System.exit(0);
        }
        
        Matrix ret = new Matrix();
        
        for (int r = 0; r < rows; r++) {
            ret.addRow();
            for (int c = 0; c < cols; c++) {
                ret.get(r).add(matrix1.get(r).get(c) * matrix2.get(r).get(c));
            }
        }
        
        return ret;
    }
    
    static Matrix MatrixComponentDivision(Matrix matrix1, Matrix matrix2) {
        int rows = matrix1.numRows();
        int cols = matrix1.numCols();
        if (rows != matrix2.numRows() || cols != matrix2.numCols()) {
            System.err.println("MATICE NEMAJU ROVNAKE ROZMERY");
            System.exit(0);
        }
        
        Matrix ret = new Matrix();
        
        for (int r = 0; r < rows; r++) {
            ret.addRow();
            for (int c = 0; c < cols; c++) {
                ret.get(r).add(matrix1.get(r).get(c) / matrix2.get(r).get(c));
            }
        }
        
        return ret;
    }
    
    static Matrix removeLastColumn(Matrix matrix) {
        int rows = matrix.numRows();
        int cols = matrix.numCols();
        Matrix ret = new Matrix();
        
        for (int r = 0; r < rows; r++) {
            ret.addRow();
            for (int c = 0; c < cols - 1; c++) {
                ret.get(r).add(matrix.get(r).get(c));
            }
        }
        
        return ret;
    }
    
    static int getCategory(Matrix net) {
        //System.out.println(net);
        int rows = net.numRows();
        double max = 0; int maxPos = 0;
        for (int r = 0; r < rows; r++) {
            double cur = net.get(r).get(0);
            if (cur > max) {
                max = cur;
                maxPos = r;
            }
        }
        return maxPos;
    }
    
    // vrati euklidovsku normu vektora
    static Double vectorNorm(ArrayList<Double> vector) {
        Double ret = 0.0;
        for (int i = 0; i < vector.size(); i++) {
            ret += vector.get(i) * vector.get(i);
        }
        
        return Math.sqrt(ret);
    }
    
    static ArrayList<Double> vectorOperation(ArrayList<Double> v1, ArrayList<Double> v2, char operand) {
        ArrayList<Double> ret = new ArrayList<>();
        if (v1.size() != v2.size()) {
            System.err.println("Nesedia velkosti vektorov! Prvy je " + v1.size() + ", druhy " + v2.size());
            System.exit(1);
        }
        for (int i = 0; i < v1.size(); i++) {
            if (operand == '+')
                ret.add(v1.get(i) + v2.get(i));
            else if (operand == '-') {
                ret.add(v1.get(i) - v2.get(i));
            }
        }
        
        return ret;
    }
    
    static Double gaussianNeighborhood (ArrayList<Double> v1, ArrayList<Double> v2, double lambda) {
        ArrayList dif = vectorOperation(v2, v1, '-');
        double normDif = vectorNorm(dif);
        double ret = Math.pow(Math.E, -(normDif * normDif) / (lambda * lambda)  ); 
        return ret;
    }
    
    static Matrix normalizeData (Matrix data, int numCols) {
        Matrix ret = numberMatrix(data.numRows(), data.numCols(), 0);
        for (int c = 0; c < numCols; c++) {
            double avg = 0.0;
            for (int r = 0; r < data.numRows(); r++) {
                avg += data.get(r).get(c);
            }
            avg = avg / data.numRows();
            
            double stdev = 0;
            for (int r = 0; r < data.numRows(); r++) {
                stdev += (data.get(r).get(c) - avg) * (data.get(r).get(c) - avg);
            }
            stdev = Math.sqrt(stdev / data.numRows());
            
            for (int r = 0; r < data.numRows(); r++) {
                ret.get(r).set(c, (data.get(r).get(c) - avg) / stdev);
            }
        }
        return ret;
    }
}

