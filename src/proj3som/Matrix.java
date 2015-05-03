/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj3som;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author raf
 */
public class Matrix {
    private ArrayList<ArrayList<Double> > representation;
    
    public Matrix() {
        representation = new ArrayList< >();
    }
    
    public Matrix(List l) {
        this.representation = new ArrayList<>(l);
    }
    
    public void addRow() {
        representation.add(new ArrayList<Double>());
    }
    
    public ArrayList<Double> get(int r) {
        return representation.get(r);
    }
    
    public void addRow(ArrayList<Double> row) {
        representation.add(row);
    }
    
    public int numRows() {
        return representation.size();
    }
    
    public int numCols() {
        return representation.get(0).size();
    }
    
    public Matrix subMatrix(int rowFrom, int rowTo) {
        return new Matrix(representation.subList(rowFrom, rowTo));
    }
    
    public void shuffleRows() {
        java.util.Collections.shuffle(representation);
    }

    @Override
    public String toString() {
        return representation.toString();
    }
    
    public void appendMatrix(Matrix m) {
        this.representation.addAll(m.representation);
    }
}
