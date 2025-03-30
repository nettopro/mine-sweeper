package cm.model;

import java.util.ArrayList;
import java.util.List;

public class Field {

    private final int row;
    private final int column;

    private boolean clear = false;
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

}
