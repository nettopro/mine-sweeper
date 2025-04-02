package cm.model;

import java.util.ArrayList;
import java.util.List;

import cm.controller.ExplosionException;

public class Field {

    private final int row;
    private final int column;

    private boolean open = false;
    private boolean mined = false;
    private boolean flagged = false;

    private List<Field> neighbors = new ArrayList<>();

    Field(int row, int column){
        this.row = row;
        this.column = column;
    }

    boolean addNeighbor(Field neighbor){
        boolean differentColumn = column != neighbor.column;
        boolean differentRow = row != neighbor.row;
        boolean diagonal = differentColumn && differentRow;

        int deltaRow = Math.abs(row - neighbor.row);
        int deltaColumn = Math.abs(column - neighbor.column);
        int defaultDelta = deltaColumn + deltaRow;

        if(defaultDelta == 1 && !diagonal){
            neighbors.add(neighbor);
            return true;
        }
        else if(defaultDelta == 2 && diagonal){
            neighbors.add(neighbor);
            return true;
        }else { return false; }

    }

    void switchFlag(){
        if(!open){
            flagged = !flagged;
        }
    }

    boolean clear(){
        if(!open && !flagged){
            open = true;

            if(mined){
                throw new ExplosionException();
            }

            if(safeNeighbors()){
                neighbors.forEach(n -> n.clear());
            }

            return true;
        } else { return false; }
    }

    boolean safeNeighbors(){
        return neighbors.stream().noneMatch(n -> n.mined);
    }

    void mine(){
        mined = true;
    }

    public boolean isFlagged(){
        return flagged;
    }

    void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isClear(){
        return open;
    }

    public boolean isMined(){
        return mined;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    boolean objectiveReached(){
        boolean cleared = !mined && open;
        boolean secured = mined && flagged;
        return cleared || secured;
    }

    long minesInNeighbors(){
        return neighbors.stream().filter(n -> n.mined).count();
    }

    void restart(){
        open = false;
        mined = false;
        flagged = false;
    }

    public String toString(){
        if(flagged){
            return "x";
        }else if(open && mined){
            return "*";
        }else if(open && minesInNeighbors() > 0){
            return Long.toString(minesInNeighbors());
        }else if(open){
            return " ";
        }else{
            return "?";
        }

    }
}
