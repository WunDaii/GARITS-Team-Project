/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Controllers.BaseController;
import Controllers.CustomerController;
import Delegates.AddInterface;
import Delegates.ItemsInterface;
import Entities.Customer;
import Entities.Item;
import Entities.Vehicle;
import Panels.BasePanel;
import TableModels.VehiclesTableModel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 *
 * @author Daven
 */
public class AddCustomerUI extends javax.swing.JFrame implements AddInterface, ItemsInterface {

    BasePanel delegate;
    boolean add = true;
    Customer customer = new Customer();
    CustomerController controller = new CustomerController();
    VehiclesTableModel model = new VehiclesTableModel();

    String[] typeStrings = {"None", "Fixed", "Flexible", "Variable"};

    /**
     * Creates new form AddCustomerUI
     */
    public AddCustomerUI() {
        initComponents();
        System.out.println("AddCustomerUI()");

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        for (String type : typeStrings) {
            this.discountComboBox.addItem(type);
        }

        this.typeComboBox.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int selected = typeComboBox.getSelectedIndex();
                JComponent[] array = {addressTextArea, postcodeTextField, faxTextField, discountComboBox, discountTextArea};
                if (selected == 0) {
                    for (JComponent comp : array) {
                        comp.setBackground(Color.white);
                        comp.setEnabled(true);
                    }
                } else {
                    for (JComponent comp : array) {
                        comp.setBackground(Color.lightGray);
                        comp.setEnabled(false);
                    }
                }
            }
        });

        this.discountComboBox.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setDiscountFields();
            }
        });

        reset();
        System.out.println("AddCustomerUI() - end");
    }

    public void editItem(Item item) {
        editCustomer((Customer) item);
    }

    public void editCustomer(Customer customer) {
        this.customer = customer;
        add = false;
        this.customer = customer;
        this.addButton.setText("Update Customer");
        this.titleLabel.setText("Edit Customer");

        titleTextField.setText(this.customer.title());
        firstNameTextField.setText(this.customer.firstName());
        lastNameTextField.setText(this.customer.lastName());
        emailTextField.setText(this.customer.email());
        addressTextArea.setText(this.customer.address());
        postcodeTextField.setText(this.customer.postcode());
        phoneTextField.setText(this.customer.telephone());
        faxTextField.setText(this.customer.fax());
        discountComboBox.setSelectedIndex(this.customer.discountTypeInt());
        typeComboBox.setSelectedIndex(customer.customerType());
        customer.setVehicles(controller.getVehiclesForCustomer(customer));
        setDiscountFields();
        reset();
    }

    private void setDiscountFields() {
        int disc = this.discountComboBox.getSelectedIndex();
        System.out.println("SetDiscountFields");
        if (disc == 0) {
            this.discountTextArea.setVisible(false);
        } else {
            this.discountTextArea.setVisible(true);
        }

        if (disc == customer.discountTypeInt()) {
            discountTextArea.setText(customer.discountStr());
        } else {
            this.discountTextArea.setText("");
        }
    }

    @Override
    public void setDelegate(BasePanel panel) {
        delegate = panel;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jRadioButtonMenuItem1 = new javax.swing.JRadioButtonMenuItem();
        jRadioButtonMenuItem2 = new javax.swing.JRadioButtonMenuItem();
        jRadioButtonMenuItem3 = new javax.swing.JRadioButtonMenuItem();
        firstNameTextField = new javax.swing.JTextField();
        lastNameTextField = new javax.swing.JTextField();
        addButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        addressLabel = new javax.swing.JLabel();
        postcodeTextField = new javax.swing.JTextField();
        phoneTextField = new javax.swing.JTextField();
        emailTextField = new javax.swing.JTextField();
        faxTextField = new javax.swing.JTextField();
        postcodeLabel = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        faxLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        addressTextArea = new javax.swing.JTextArea();
        cancelButton = new javax.swing.JButton();
        titleLabel = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        discountComboBox = new javax.swing.JComboBox<>();
        messageLabel = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        typeComboBox = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        vehiclesTable = new javax.swing.JTable();
        deleteVehicleButton = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        titleTextField = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        discountTextArea = new javax.swing.JTextArea();

        jRadioButtonMenuItem1.setSelected(true);
        jRadioButtonMenuItem1.setText("jRadioButtonMenuItem1");

        jRadioButtonMenuItem2.setSelected(true);
        jRadioButtonMenuItem2.setText("jRadioButtonMenuItem2");

        jRadioButtonMenuItem3.setSelected(true);
        jRadioButtonMenuItem3.setText("jRadioButtonMenuItem3");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        firstNameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                firstNameTextFieldActionPerformed(evt);
            }
        });

        lastNameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lastNameTextFieldActionPerformed(evt);
            }
        });

        addButton.setText("Add Customer");
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("Last Name:");

        jLabel2.setText("First Name:");

        addressLabel.setText("Address:");

        phoneTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                phoneTextFieldActionPerformed(evt);
            }
        });

        postcodeLabel.setText("Postcode:");

        jLabel6.setText("Phone:");

        jLabel7.setText("Email*:");

        faxLabel.setText("Fax:");

        addressTextArea.setColumns(20);
        addressTextArea.setRows(5);
        jScrollPane1.setViewportView(addressTextArea);

        cancelButton.setText("Cancel");

        titleLabel.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        titleLabel.setText("Add New Customer");

        jLabel10.setText("Discount:");

        jLabel9.setText("Customer Type:");

        typeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Account Holder", "Casual Customer" }));
        typeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                typeComboBoxActionPerformed(evt);
            }
        });

        jLabel11.setText("Vehicles:");

        vehiclesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(vehiclesTable);

        deleteVehicleButton.setText("Delete Vehicle(s)");
        deleteVehicleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteVehicleButtonActionPerformed(evt);
            }
        });

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("<html>A receptionist can add a new vehicle.</html>");

        jLabel3.setText("Title:");

        discountTextArea.setColumns(20);
        discountTextArea.setRows(5);
        jScrollPane3.setViewportView(discountTextArea);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(cancelButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addButton))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(messageLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addGap(61, 61, 61)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lastNameTextField)
                            .addComponent(phoneTextField)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(33, 33, 33)
                        .addComponent(firstNameTextField))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(105, 105, 105)
                        .addComponent(emailTextField))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(addressLabel)
                            .addComponent(postcodeLabel)
                            .addComponent(faxLabel))
                        .addGap(44, 44, 44)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(postcodeTextField)
                            .addComponent(jScrollPane1)
                            .addComponent(faxTextField)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(typeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(jLabel3)
                                    .addGap(73, 73, 73)
                                    .addComponent(titleTextField))
                                .addComponent(titleLabel)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel10)
                            .addComponent(jLabel5))
                        .addGap(50, 50, 50)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(discountComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane2)
                            .addComponent(jScrollPane3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(deleteVehicleButton, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titleLabel)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(titleTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(firstNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lastNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(phoneTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(emailTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(typeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addressLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(postcodeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(postcodeLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(faxTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(faxLabel))
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)
                        .addComponent(deleteVehicleButton))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(discountComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(messageLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addButton)
                    .addComponent(cancelButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void firstNameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_firstNameTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_firstNameTextFieldActionPerformed

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        // TODO add your handling code here:

        if (this.emailTextField.getText().equals("")) {
            this.messageLabel.setText("Please enter an e-mail address.");
            this.messageLabel.setForeground(Color.red);
            return;
        }

        int type = this.typeComboBox.getSelectedIndex();

        customer.setFirstName(this.firstNameTextField.getText());
        customer.setLastName(this.lastNameTextField.getText());
        customer.setEmail(this.emailTextField.getText());
        customer.setTelephone(this.phoneTextField.getText());
        customer.setTitle(this.titleTextField.getText());
        if (type == 0) {
            customer.setAddress(this.addressTextArea.getText());
            customer.setFax(this.faxTextField.getText());
            customer.setPostcode(this.postcodeTextField.getText());
            int discountType = this.discountComboBox.getSelectedIndex();
            String discountStr = "";
            if (discountType == 0) {
                discountStr = "";
            } else {
                discountStr = this.discountTextArea.getText();
            }

            discountStr = discountStr.replaceAll("\\s+", "");

            customer.setDiscountInt(discountType, discountStr);

        } else {
            customer.setAddress("");
            customer.setFax("");
            customer.setPostcode("");
            customer.setDiscountInt(0, "");
        }

        customer.setCustomerType(this.typeComboBox.getSelectedIndex());

        if (add) {
            if (controller.addCustomer(customer)) {
                this.messageLabel.setText("Added Customer");
                this.messageLabel.setForeground(Color.black);
            } else {
                this.messageLabel.setText("Email already exists.");
                this.messageLabel.setForeground(Color.red);
            }

        } else {
            controller.updateCustomer(customer);
        }
        delegate.reset();

    }//GEN-LAST:event_addButtonActionPerformed

    private void lastNameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lastNameTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lastNameTextFieldActionPerformed

    private void phoneTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_phoneTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_phoneTextFieldActionPerformed

    private void typeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_typeComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_typeComboBoxActionPerformed

    private void deleteVehicleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteVehicleButtonActionPerformed
        // TODO add your handling code here:

        BaseController vehicleController = new BaseController();

        int[] rows = vehiclesTable.getSelectedRows();

        ArrayList<Vehicle> vehicles = customer.vehicles();

        for (int i = rows.length - 1; i >= 0; i--) {
            vehicleController.deleteItem(vehicles.get(rows[i]));
            vehicles.remove(i);
        }

        customer.setVehicles(vehicles);
        customer.setVehicles(controller.getVehiclesForCustomer(customer));

        reset();
    }//GEN-LAST:event_deleteVehicleButtonActionPerformed

    public void reset() {

        if (customer.vehicles() != null) {
            System.out.println("AddCustomerUI > reset() > cusotmerVehicles != null");
            this.model.setItems(customer.vehicles());
            this.vehiclesTable.setModel(model);
            vehiclesTable.revalidate();
        }
        System.out.println("AddCustomerUI > reset() - end");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AddCustomerUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddCustomerUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddCustomerUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddCustomerUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddCustomerUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JLabel addressLabel;
    private javax.swing.JTextArea addressTextArea;
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton deleteVehicleButton;
    private javax.swing.JComboBox<String> discountComboBox;
    private javax.swing.JTextArea discountTextArea;
    private javax.swing.JTextField emailTextField;
    private javax.swing.JLabel faxLabel;
    private javax.swing.JTextField faxTextField;
    private javax.swing.JTextField firstNameTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem1;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem2;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField lastNameTextField;
    private javax.swing.JLabel messageLabel;
    private javax.swing.JTextField phoneTextField;
    private javax.swing.JLabel postcodeLabel;
    private javax.swing.JTextField postcodeTextField;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JTextField titleTextField;
    private javax.swing.JComboBox<String> typeComboBox;
    private javax.swing.JTable vehiclesTable;
    // End of variables declaration//GEN-END:variables

    @Override
    public void selectedItems(ArrayList<? extends Item> items) {

        System.out.println("AddCustomerUI > selectedItems");

        Item first = items.get(0);
        System.out.println("Got first");
        switch (first.type()) {
            case VEHICLE:

                ArrayList<Vehicle> vehicles = new ArrayList<>();

                if (customer.vehicles() != null) {
                    vehicles = customer.vehicles();
                }

                ArrayList<Vehicle> newVehicles = vehicles;

                for (Item item : items) {
                    Vehicle vehicle = (Vehicle) item;
                    boolean exists = false;
                    for (Vehicle vehicle_ : vehicles) {
                        if (vehicle_.regNo().equals(vehicle.regNo())) {
                            exists = true;
                            break;
                        }
                    }

                    if (!exists) {
                        newVehicles.add(vehicle);
                    }
                }

                updatedVehicles((List<Vehicle>) newVehicles);
                break;
            default:
                break;
        }
    }

    @Override
    public void updatedItems(ArrayList<? extends Item> items) {
        Item first = items.get(0);

        switch (first.type()) {
            case VEHICLE:
                updatedVehicles((List<Vehicle>) items);
                break;
            default:
                break;
        }
    }

    public void updatedVehicles(List<Vehicle> vehicles) {
        customer.setVehicles((ArrayList<Vehicle>) vehicles);
        reset();
    }
}