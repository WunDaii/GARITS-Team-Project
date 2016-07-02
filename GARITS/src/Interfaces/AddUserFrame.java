/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Controllers.UserController;
import Delegates.AddInterface;
import Entities.Item;
import Entities.Mechanic;
import Entities.User;
import Panels.BasePanel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JFrame;

/**
 *
 * @author Daven
 */
public class AddUserFrame extends javax.swing.JFrame implements AddInterface {

    /**
     * Creates new form AddUserFrame
     */
    String[] typeStrings = {"Administrator", "Franchisee", "Foreperson", "Mechanic", "Receptionist"};

    BasePanel delegate;
    UserController userController = new UserController();
    User user = new User();
    boolean add = true;

    public AddUserFrame() {
        initComponents();

        for (String type : typeStrings) {
            this.typeComboBox.addItem(type);
        }

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        typeComboBox.addActionListener((ActionEvent e) -> {
            resetMechFields();
        });

        resetMechFields();
    }

    public void resetMechFields() {
        if (typeComboBox.getSelectedIndex() != 3) {
            hourlyRateLabel.setVisible(false);
            poundLabel.setVisible(false);
            rateTextField.setVisible(false);
        } else {
            hourlyRateLabel.setVisible(true);
            poundLabel.setVisible(true);
            rateTextField.setVisible(true);
        }
    }

    @Override
    public void setDelegate(BasePanel panel) {
        this.delegate = panel;
    }

    public void editUser(User user) {
        add = false;
        addButton.setText("Update User");
        titleLabel.setText("Edit User");

        this.user = user;

        usernameTextField.setText(this.user.username());
        passwordTextField.setText("");
        firstNameTextField.setText(this.user.firstName());
        lastNameTextField.setText(this.user.lastName());
        typeComboBox.setSelectedIndex(this.user.userTypeInt());
        if (this.user.userType() == User.UserType.MECHANIC) {
            this.rateTextField.setText("" + ((Mechanic) this.user).hourlyRate());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titleLabel = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        usernameTextField = new javax.swing.JTextField();
        passwordTextField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        typeComboBox = new javax.swing.JComboBox<>();
        addButton = new javax.swing.JButton();
        messageLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        firstNameTextField = new javax.swing.JTextField();
        lastNameTextField = new javax.swing.JTextField();
        hourlyRateLabel = new javax.swing.JLabel();
        rateTextField = new javax.swing.JFormattedTextField();
        poundLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        titleLabel.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        titleLabel.setText("Add User");

        jLabel2.setText("Username:");

        jLabel3.setText("Password:");

        jLabel4.setText("Type:");

        typeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                typeComboBoxActionPerformed(evt);
            }
        });

        addButton.setText("Add User");
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        messageLabel.setForeground(new java.awt.Color(204, 0, 51));
        messageLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel1.setText("First Name:");

        jLabel5.setText("Last Name:");

        hourlyRateLabel.setText("Hourly Rate:");

        rateTextField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00;(¤#,##0.00)"))));

        poundLabel.setText("£");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(messageLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(addButton))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(passwordTextField)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(typeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(hourlyRateLabel)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(poundLabel)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(rateTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE))))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(firstNameTextField))
                    .addComponent(titleLabel)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel2))
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(usernameTextField)
                            .addComponent(lastNameTextField))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titleLabel)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(firstNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(lastNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(usernameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(passwordTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(typeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hourlyRateLabel)
                    .addComponent(rateTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(poundLabel))
                .addGap(4, 4, 4)
                .addComponent(addButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(messageLabel))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void typeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_typeComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_typeComboBoxActionPerformed

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        // TODO add your handling code here:

        user.setPassword(this.passwordTextField.getText());
        user.setUsername(this.usernameTextField.getText());
        user.setType(this.typeComboBox.getSelectedIndex());
        user.setFirstName(firstNameTextField.getText());
        user.setLastName(lastNameTextField.getText());

        if (passwordTextField.getText().equals("") || usernameTextField.getText().equals("") || firstNameTextField.getText().equals("") || lastNameTextField.getText().equals("")) {
            this.messageLabel.setText("Please complete all fields.");
            this.messageLabel.setForeground(Color.red);
            return;
        }

        if (typeComboBox.getSelectedIndex() == 3) {
            double rate = 0;
            if (this.rateTextField.getValue() instanceof Double) {
                rate = (double) this.rateTextField.getValue();
            } else if (this.rateTextField.getValue() instanceof Long) {
                rate = ((Long) this.rateTextField.getValue()).doubleValue();
            }
            Mechanic mech = new Mechanic(user, rate);
            mech.setType(3);
            user = mech;
        }

        if (add) {
            if (userController.addItem(user)) {
                this.delegate.reset();
                this.messageLabel.setText("Added user.");
                this.messageLabel.setForeground(Color.black);
            } else {
                this.messageLabel.setText("User already exists");
                this.messageLabel.setForeground(Color.red);
            }
        } else {
            userController.updateItem(user);
            this.delegate.reset();
            this.messageLabel.setText("Saved user.");
            this.messageLabel.setForeground(Color.black);
        }
    }//GEN-LAST:event_addButtonActionPerformed

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
            java.util.logging.Logger.getLogger(AddUserFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddUserFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddUserFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddUserFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddUserFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JTextField firstNameTextField;
    private javax.swing.JLabel hourlyRateLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JTextField lastNameTextField;
    private javax.swing.JLabel messageLabel;
    private javax.swing.JTextField passwordTextField;
    private javax.swing.JLabel poundLabel;
    private javax.swing.JFormattedTextField rateTextField;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JComboBox<String> typeComboBox;
    private javax.swing.JTextField usernameTextField;
    // End of variables declaration//GEN-END:variables

    @Override
    public void editItem(Item item) {
        editUser((User) item);
    }
}
