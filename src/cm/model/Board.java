package cm.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import cm.controller.ExplosionException;

public class Board {
    private int rows;
    private int columns;
    private int mines;
    
    private final List<Field> fields = new ArrayList<>();

    public Board(int rows, int columns, int mines) {
        this.rows = rows;
        this.columns = columns;
        this.mines = mines;

        generateFields();
        linkNeighbors();
        generateMines();
    }

    public void clear(int row, int column){
        try {
            fields.parallelStream()
            .filter(f -> f.getRow() == row && f.getColumn() == column)
            .findFirst()
            .ifPresent(f -> f.clear());
        } catch (ExplosionException e) {
            fields.forEach(f -> f.setOpen(true));
            throw e;
        }
    }

    public void flag(int row, int column){
        fields.parallelStream()
            .filter(f -> f.getRow() == row && f.getColumn() == column)
            .findFirst()
            .ifPresent(f -> f.switchFlag());
    }

    private void generateFields() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                fields.add(new Field(i, j));
            }
        }
    }
    
    private void linkNeighbors() {
        for(Field f1: fields){
            for(Field f2: fields){
                f1.addNeighbor(f2);
            }
        }
    }

    private void generateMines() {
        long placedMines = 0;
        Predicate<Field> mined = f -> f.isMined(); 

        do{
            int random = (int) (Math.random() * fields.size());
            fields.get(random).mine();
            placedMines = fields.stream().filter(mined).count();
        }while(placedMines< mines);
    }

    public boolean objectiveReached(){
        return fields.stream().allMatch(f -> f.objectiveReached());
    }

    public void restart(){
        fields.stream().forEach(f -> f.restart());
        generateMines();
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        
        sb.append("  ");
        
        for (int i = 0; i < columns; i++) {
            sb.append(" ");
            sb.append(i);
            sb.append(" ");
        }

        sb.append("\n");

        int count = 0;
        for (int i = 0; i < rows; i++) {
            sb.append(i);
            sb.append(" ");
            for (int j = 0; j < columns; j++) {
                sb.append(" ");
                sb.append(fields.get(count));
                sb.append(" ");
                count++;
            }
            sb.append("\n");
        }

        return sb.toString(); 
    }
}
