package com.contextlogic.wish.tool_dialogs;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class FieldTable extends JPanel {

    private JTable mTable;

    private int id = 0;

    public FieldTable(String[] columnNames) {
        TableModel tableModel = new TableModel(columnNames);
        mTable = new JTable(tableModel);

        JButton add = new JButton("Add");
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.addNewRow();
            }
        });

        JButton remove = new JButton("Remove");
        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.removeSelectedRows(mTable.getSelectedRows());
            }
        });

        JPanel controls =  new JPanel(new BorderLayout());
        controls.add(add, BorderLayout.WEST);
        controls.add(remove, BorderLayout.EAST);

        this.setLayout(new BorderLayout());
        this.add(controls, BorderLayout.SOUTH);
        this.add(new JScrollPane(mTable));
    }

    public ArrayList<String[]> getDataRows() {
        return ((TableModel) mTable.getModel()).getDataRows();
    }

    public class TableModel extends AbstractTableModel {

        private String[] mColumnNames;
        private ArrayList<String[]> mDataRows;

        public TableModel(String[] columnNames) {
            mDataRows = new ArrayList<>(24);
            mColumnNames = columnNames;
        }

        public ArrayList<String[]> getDataRows() {
            return mDataRows;
        }

        public void addNewRow() {
            mDataRows.add(new String[getColumnCount()]);
            fireTableRowsInserted(mDataRows.size() - 1, mDataRows.size() - 1);
        }

        public void removeSelectedRows(int[] selectedRows) {
            for(int i = 0; i < selectedRows.length; i++){
                ((TableModel) mTable.getModel()).removeRow(selectedRows[i] - i);
            }
        }

        public void removeRow(int rowIndex) {
            System.out.println(getRowCount()+"\t"+rowIndex);
            if (rowIndex < getRowCount()) {
                mDataRows.remove(rowIndex);
                fireTableRowsDeleted(rowIndex, rowIndex);
            }
        }

        @Override
        public int getRowCount() {
            return mDataRows.size();
        }

        @Override
        public int getColumnCount() {
            return mColumnNames.length;
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            //return getValueAt(0, columnIndex).getClass();
            return String.class;
        }

        @Override
        public String getColumnName(int columnIndex) {
            return mColumnNames[columnIndex];
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return mDataRows.get(rowIndex)[columnIndex];
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return true;
        }

        @Override
        public void setValueAt(Object value, int rowIndex, int columnIndex) {
            mDataRows.get(rowIndex)[columnIndex] = (String) value;
        }
    }
}