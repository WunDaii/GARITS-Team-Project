/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TableModels;

import Entities.Customer;
import Entities.Item;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 *
 * @author Daven
 */
public class CustomersTableModel extends BaseTableModel {

    public CustomersTableModel(List<Customer> users) {
        super(users);
        init();
    }

    public CustomersTableModel() {
        super();
        init();
    }

    public void init() {
        columns = new Class[]{int.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, int.class};
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
                return "Telephone";
            case 4:
                return "Address";
            case 5:
                return "PostCode";
            case 6:
                return "Email";
            case 7:
                return "Fax";
            case 8:
                return "Date Created";
            case 9:
                return "Discount Type";
            default:
                return "None";
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columns[columnIndex];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Customer customer = (Customer) items.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return customer.ID();
            case 1:
                return customer.firstName();
            case 2:
                return customer.lastName();
            case 3:
                return customer.telephone();
            case 4:
                return customer.address();
            case 5:
                return customer.postcode();
            case 6:
                return customer.email();
            case 7:
                return customer.fax();
            case 8:

                DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                return df.format(customer.dateCreated());
            case 9:
                return customer.discountType();
            default:
                return customer.firstName();
        }
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
