/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TableModels;

import Entities.Item;
import Entities.Vehicle;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 *
 * @author Daven
 */
public class VehiclesTableModel extends BaseTableModel {

    public VehiclesTableModel(List<Vehicle> vehicles) {
        super(vehicles);
        init();
    }

    public VehiclesTableModel() {
        super();
        init();
    }

    public void init() {
        columns = new Class[]{String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class};
        addTableModelListener(this);
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Registration Number";
            case 1:
                return "Make";
            case 2:
                return "Model";
            case 3:
                return "Engine Serial";
            case 4:
                return "Chassis Number";
            case 5:
                return "Colour";
            case 6:
                return "Customer Name";
            case 7:
                return "MOT Date";
            default:
                return "None";
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (columnIndex == 1) {
            return true;
        }
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        Vehicle vehicle = (Vehicle) items().get(rowIndex);

        switch (columnIndex) {
            case 0:
                return vehicle.regNo();
            case 1:
                return vehicle.make();
            case 2:
                return vehicle.model();
            case 3:
                return vehicle.engSerial();
            case 4:
                return vehicle.chassisNumber();
            case 5:
                return vehicle.colour();
            case 6:
                return vehicle.customer().firstName() + " " + vehicle.customer().lastName();
            case 7:
                if (vehicle.MOTDate() != null) {
                    DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                    return df.format(vehicle.MOTDate());
                } else {
                    return "";
                }
            default:
                return "None";
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

    }

    @Override
    public void addTableModelListener(TableModelListener l) {
        System.out.println("addTableModeListener");
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
    }

    @Override
    public void tableChanged(TableModelEvent e) {
    }

    @Override
    public List<? extends Item> items() {
        return items;
    }

    @Override
    public Class[] columns() {
        return columns;
    }
}