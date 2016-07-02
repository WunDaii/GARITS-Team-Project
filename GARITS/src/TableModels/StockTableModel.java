/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TableModels;

import Entities.Item;
import Entities.Stock;
import java.util.List;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 *
 * @author Daven
 */
public class StockTableModel extends BaseTableModel {

    public StockTableModel(List<Stock> stock) {
        super(stock);
        init();
    }

    public StockTableModel() {
        super();
        init();
    }

    public void init() {
        columns = new Class[]{String.class, String.class, String.class, int.class, int.class, String.class};
        addTableModelListener(this);
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Item Number";
            case 1:
                return "Name";
            case 2:
                return "Manufacturer";
            case 3:
                return "Quantity";
            case 4:
                return "Threshold";
            case 5:
                return "Cost";
            default:
                return "Description";
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Stock stock = (Stock) items().get(rowIndex);

        switch (columnIndex) {
            case 0:
                return stock.itemNumber();
            case 1:
                return stock.part().name();
            case 2:
                return stock.part().manufacturer();
            case 3:
                return stock.quantity();
            case 4:
                return stock.part().threshold();
            case 5:
                return String.format("£%.2f", stock.cost());
            default:
                return "";
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
