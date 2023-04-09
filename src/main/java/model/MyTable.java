package model;

import javax.swing.table.DefaultTableModel;
import java.util.HashMap;
import java.util.List;

public class MyTable extends DefaultTableModel {
    private HashMap<Integer, List<String>> records = new HashMap<>();
    private List<String> head;
    private int columnCount;

    public MyTable(List<String> head) {
        this.head = head;
        this.columnCount = head.size();
    }

    public int getRowCount() {
        return this.records != null ? this.records.size() : 0;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public String getColumnName(int column) {
        return head.get(column);
    }

    public Object getValueAt(int row, int column) {
        int listSize = this.records.get(row).size();
        return getCellValue(listSize, column, row);
    }

    public void add(List<String> s) {
        this.records.put(records.size(), s);
        this.fireTableRowsInserted(this.records.size() - 1, this.records.size() - 1);
    }

    private Object getCellValue(int listSize, int column, int row) {
        if (column < listSize) {
            return this.records.get(row).get(column);
        } else {
            return "";
        }
    }

    public void clearData() {
        this.records.clear();
    }

    public HashMap<Integer, List<String>> getRecords() {
        return records;
    }
}