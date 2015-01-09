package org.silo.vista.componentes;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public abstract class MyTableModel<E> extends AbstractTableModel {

    protected String[] columnNames;
    protected List<E> data = new ArrayList<>();

    public List<E> getData() {
        return data;
    }

    public void setData(List<E> data) {
        this.data = data;
        System.out.println("nuevos datos en el modelo de la tabla");
        fireTableDataChanged();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    /*
     * Don't need to implement this method unless your table's
     * editable.
     */
    @Override
    public boolean isCellEditable(int row, int col) {
        //Note that the data/cell address is constant,
        //no matter where the cell appears onscreen.
        return false;
    }

    /*
     * Don't need to implement this method unless your table's
     * data can change.
     */
    @Override
    public void setValueAt(Object value, int row, int col) {
        data.add((E)value);
        fireTableDataChanged();
    }
    
    public void remove(int row){
        data.remove(row);
        fireTableRowsDeleted(0, data.size());
        fireTableDataChanged();
    }
}
