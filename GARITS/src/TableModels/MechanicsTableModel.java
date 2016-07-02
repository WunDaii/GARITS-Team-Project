/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TableModels;

import Entities.Mechanic;
import java.util.List;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 *
 * @author Daven
 */
public class MechanicsTableModel extends BaseTableModel {

    public MechanicsTableModel(List<Mechanic> mechs) {
        super(mechs);
        init();
    }

    public void setMechanics(List<Mechanic> mechs) {
        this.items = mechs;
    }

    public MechanicsTableModel() {
        super();
        init();
    }

    public void init() {
        columns = new Class[]{int.class, String.class, String.class, String.class, int.class};
        addTableModelListener(this);
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "ID";
            case 1:
                return "First Name";
            case 2:
                return "Last Name";
            case 3:
                return "Hourly Rate";
            case 4:
                return "Minutes Worked";
            default:
                return "None";
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Mechanic user = (Mechanic) items().get(rowIndex);

        switch (columnIndex) {
            case 0:
                return user.ID();
            case 1:
                return user.firstName();
            case 2:
                return user.lastName();
            case 3:
                return "£" + user.hourlyRate();
            case 4:
                return user.minsWorked();
            default:
                return user.username();
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
}
