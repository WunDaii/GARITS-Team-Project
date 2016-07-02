/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.sql.ResultSet;
import java.util.Map;
import javax.swing.JOptionPane;

/**
 *
 * @author Daven
 */
public class JobStock extends Stock {

    int jobQuantity = 0;

    public JobStock(ResultSet rs, int jobQuantity) {
        super(rs);
        this.jobQuantity = jobQuantity;
    }

    public JobStock(Stock stock) {
        super();

        this.map = stock.map();
        setPart(stock.part());
        setJobQuantity(1);
    }

    public void setJobQuantity(int qua) {
        int usedQua = qua - jobQuantity();
        int originalQua = quantity();
        int newQua = originalQua - usedQua;
        if (newQua < 0) {
            JOptionPane.showMessageDialog(null, "There is not enough of this item.", "Too Many Requested", JOptionPane.ERROR_MESSAGE);
            return;
        } else {
            setQuantity(newQua);
            this.jobQuantity = qua;
        }
    }

    public int jobQuantity() {
        return jobQuantity;
    }

    @Override
    public Map<String, Object> map() {
        return map;
    }

    @Override
    public String IDStr() {
        return super.IDStr();
    }

    @Override
    public String uniqueStr() {
        return super.uniqueStr();
    }
}
