/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TableModels;

import Entities.Part;
import java.util.List;
import javax.swing.event.TableModelEvent;

/**
 *
 * @author Daven
 */
public class PartsTableModel extends BaseTableModel {

    public PartsTableModel(List<Part> parts) {
        super(parts);
        init();
    }

    public PartsTableModel() {
        super();
        init();
    }

    public void init() {
        columns = new Class[]{String.class, String.class, String.class, String.class, String.class};
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Part Number";
            case 1:
                return "Name";
            case 2:
                return "Manufacturer";
            case 3:
                return "Vehicle Type";
            case 4:
                return "Years";
            default:
                return "Info";
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        Part part = (Part) items().get(rowIndex);

        switch (columnIndex) {
            case 0:
                return part.number();
            case 1:
                return part.name();
            case 2:
                return part.manufacturer();
            case 3:
                return part.vehicleType();
            case 4:
                return part.years();
            default:
                return part.number();
        }
    }

    @Override
    public void tableChanged(TableModelEvent e) {
    }
}
