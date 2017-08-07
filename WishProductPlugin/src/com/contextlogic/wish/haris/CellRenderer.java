package com.contextlogic.wish.haris;

// Display an icon and a string for each object in the list.

import javax.swing.*;
import java.awt.*;

class CellRenderer extends JPanel implements ListCellRenderer<Object> {

    private JTextField mFieldType;
    private JTextField mFieldName;
    private JTextField mJsonFieldName;

    public Component getListCellRendererComponent(
            JList<?> list,           // the list
            Object value,            // value to display
            int index,               // cell index
            boolean isSelected,      // is the cell selected
            boolean cellHasFocus)    // does the cell have focus
    {

        this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridy = 0;

        this.removeAll();
        for (int column = 0; column < 3; ++column) {
            constraints.gridx = column;
            switch (column) {
                case 0:
                    mFieldType = new JTextField(10);
                    this.add(mFieldType, constraints);
                    break;
                case 1:
                    mFieldName = new JTextField(10);
                    this.add(mFieldName, constraints);
                    break;
                case 2:
                    mJsonFieldName = new JTextField(10);
                    this.add(mJsonFieldName, constraints);
                    break;
            }
        }

        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
            mFieldType.getCaret().setSelectionVisible(true);

        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
        setEnabled(list.isEnabled());
        setFont(list.getFont());
        setOpaque(true);

        //String s = value.toString();

        return this;
    }
}