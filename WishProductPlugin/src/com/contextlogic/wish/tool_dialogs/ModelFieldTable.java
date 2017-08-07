package com.contextlogic.wish.tool_dialogs;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ModelFieldTable extends JPanel {

    private JTable mTable;

    private int id = 0;

    public ModelFieldTable() {
        ModelFieldTableModel tableModel = new ModelFieldTableModel();
        mTable = new JTable(tableModel);

        JButton add = new JButton("Add Row");
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.addNewRow();
            }
        });

        JButton remove = new JButton("Remove Selected Rows");
        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.removeSelectedRows(mTable.getSelectedRows());
            }
        });

        JPanel controls =  new JPanel(new FlowLayout());
        controls.add(add);
        controls.add(remove);

        this.setLayout(new BorderLayout());
        this.add(controls, BorderLayout.SOUTH);
        this.add(new JScrollPane(mTable));
    }

    public ArrayList<ModelField> getDataRows() {
        return ((ModelFieldTableModel) mTable.getModel()).getDataRows();
    }

    public class ModelField {
        private String mFieldType;
        private String mFieldName;
        private String mJsonKey;

        public ModelField() {
            this.mFieldType = null;
            this.mFieldName = null;
            this.mJsonKey = null;
        }

        public ModelField(String fieldType, String fieldName, String jsonKey) {
            this.mFieldType = fieldType;
            this.mFieldName = fieldName;
            this.mJsonKey = jsonKey;
        }

        public  boolean isValidField() {
            return mFieldType != null  && mFieldName != null && mJsonKey != null;
        }

        public String getFieldType() {
            return mFieldType;
        }
        public String getFieldName() {
            return mFieldName;
        }
        public String getJsonKey() {
            return mJsonKey;
        }

        public void setFieldType(String fieldType) {
            this.mFieldType = fieldType;
        }
        public void setFieldName(String fieldName) {
            this.mFieldName = fieldName;
        }
        public void setJsonKey(String jsonKey) {
            this.mJsonKey = jsonKey;
        }
    }

    public class ModelFieldTableModel extends AbstractTableModel {

        private String[] mColumnNames = {"Field Type", "Field Name", "JSON Key"};
        private ArrayList<ModelField> mDataRows;

        public ModelFieldTableModel() {
            mDataRows = new ArrayList<>(24);
        }

        public ArrayList<ModelField> getDataRows() {
            return mDataRows;
        }

        public void addNewRow() {
            mDataRows.add(new ModelField());
            fireTableRowsInserted(mDataRows.size() - 1, mDataRows.size() - 1);
        }

        public void removeSelectedRows(int[] selectedRows) {
            for(int i = 0; i < selectedRows.length; i++){
                ((ModelFieldTableModel) mTable.getModel()).removeRow(selectedRows[i] - i);
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
            ModelField modelField = mDataRows.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return modelField.getFieldType();
                case 1:
                    return modelField.getFieldName();
                case 2:
                    return modelField.getJsonKey();
            }
            return null;
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return true;
        }

        @Override
        public void setValueAt(Object value, int rowIndex, int columnIndex) {
            String sValue =  (String) value;
            ModelField modelField = mDataRows.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    modelField.setFieldType(sValue);
                case 1:
                    modelField.setFieldName(sValue);
                case 2:
                    modelField.setJsonKey(sValue);
            }
        }
    }
}