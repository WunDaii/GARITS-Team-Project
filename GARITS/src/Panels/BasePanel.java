/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Panels;

import Controllers.BaseController;
import DatabasePackage.DBConnect;
import Delegates.AddInterface;
import Delegates.ItemsInterface;
import Entities.*;
import Entities.Item.ItemType;
import Entities.User.UserType;
import Helpers.GetListHelper;
import Helpers.SearchHelper;
import Interfaces.*;
import TableModels.*;
import java.awt.Color;
import java.awt.Container;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Daven
 */
public class BasePanel extends javax.swing.JPanel {

    BaseTableModel model;
    GetListHelper listHelper = new GetListHelper();
    List<? extends Item> items, tableItems;
    BaseController controller = new BaseController();
    ItemsInterface delegate;
    SearchHelper searchHelper = new SearchHelper();

    public enum PanelType {
        USERS, MECHANICS, CUSTOMERS, JOBS, PARTS, TASKS, VEHICLES, STOCK, PART_ORDERS, SUPPLIERS, JOB_TYPES, NONE
    }

    PanelType type = PanelType.NONE;

    /**
     * Creates new form BasePanel
     */
    public BasePanel() {
        initComponents();
        this.statusComboBox.setVisible(false);
        comboBoxExplanationLabel.setVisible(false);
        this.selectButton.setVisible(false);

        this.table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                JTable table = (JTable) me.getSource();
                Point p = me.getPoint();
                int row = table.rowAtPoint(p);
                if (me.getClickCount() == 2) {
                    if (selectButton.isVisible()) {
                        selectButton.doClick();
                    } else {
                        editButton.doClick();
                    }
                }
            }
        });

        this.searchTextField.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                refreshTable();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                refreshTable();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                refreshTable();
            }
        });
    }

    public void setDelegate(ItemsInterface delegate) {
        this.delegate = delegate;
        this.titleLabel.setText("Select " + this.titleLabel.getText());
        this.selectButton.setVisible(true);
    }

    /**
     * Hides certain buttons depending on the current User. This is because
     * Users have differing permissions.
     *
     * @see User
     */
    public void hideNonSelectButtons() {

        if (type == PanelType.VEHICLES || type == PanelType.CUSTOMERS || type == PanelType.TASKS) {
            this.addButton.setVisible(true);
        } else {
            this.addButton.setVisible(false);
        }

        this.editButton.setVisible(false);
        this.deleteButton.setVisible(false);
    }

    /**
     * Sets up the BasePanel for a specific PanelType.
     *
     * @param type_ the PanelType that dictates what to set up.
     * @see PanelType
     */
    public void setupType(PanelType type_) {
        type = type_;
        String title = "Items";

        switch (type) {
            case USERS:
                model = new AdminsTableModel();
                title = "Users";
                break;
            case MECHANICS:
                model = new AdminsTableModel();
                title = "Mechanics";
                break;
            case CUSTOMERS:
                model = new CustomersTableModel();
                title = "Customers";
                break;
            case JOBS:
                model = new JobsTableModel();
                title = "Jobs";
                statusComboBox.setVisible(true);
                comboBoxExplanationLabel.setVisible(true);
                String[] statusStrings = {"All", "Pending", "In Progress", "Completed"};

                for (String type : statusStrings) {
                    this.statusComboBox.addItem(type);
                }

                statusComboBox.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        reset();
                    }
                });

                User user = DBConnect.sharedInstance().user();

                if (user.userType() == UserType.MECHANIC) {
                    addButton.setVisible(false);
                    deleteButton.setVisible(false);
                    statusComboBox.setSelectedIndex(1);
                }

                break;
            case PARTS:
                model = new PartsTableModel();
                title = "Parts";
                break;
            case TASKS:
                model = new TasksTableModel();
                title = "Tasks";
                break;
            case VEHICLES:
                model = new VehiclesTableModel();
                title = "Vehicles";
                break;
            case STOCK:
                title = "Stock";
                model = new StockTableModel();
                break;
            case PART_ORDERS:
                title = "Part Orders";
                model = new PartOrdersTableModel();
                break;
            case SUPPLIERS:
                title = "Suppliers";
                model = new SuppliersTableModel();
                break;
            case JOB_TYPES:
                title = "Job Types";
                model = new JobTypesTableModel();
                break;
            default:
                break;
        }

        this.titleLabel.setText(title);

        reset();
    }

    public void reset() {

        ItemType listType = ItemType.NONE;

        switch (type) {
            case USERS:
                listType = ItemType.USER;
                break;
            case CUSTOMERS:
                listType = ItemType.CUSTOMER;
                break;
            case MECHANICS:
                listType = ItemType.MECHANIC;
                break;
            case JOBS:
                listType = ItemType.JOB;
                break;
            case PARTS:
                listType = ItemType.PART;
                break;
            case TASKS:
                listType = ItemType.TASK;
                break;
            case VEHICLES:
                listType = ItemType.VEHICLE;
                break;
            case STOCK:
                listType = ItemType.STOCK;
                break;
            case PART_ORDERS:
                listType = ItemType.PART_ORDER;
                break;
            case SUPPLIERS:
                listType = ItemType.SUPPLIER;
                break;
            case JOB_TYPES:
                listType = ItemType.JOB_TYPE;
                break;
            default:
                break;
        }

        System.out.println("BasePanel > reset > getting list...");
        items = listHelper.getListForListType(listType);
        System.out.println("Base Panel > reset > got list count: " + items.size());

        if (type == PanelType.JOBS) {
            int status = this.statusComboBox.getSelectedIndex();

            if (status > 0) {

                ArrayList<Job> newJobs = new ArrayList<>();
                ArrayList<Job> jobs = (ArrayList<Job>) items;
                for (Job job : jobs) {
                    if (job.statusInt() == (status - 1)) {
                        newJobs.add(job);
                    }
                }
                items = newJobs;
            }

        }

        refreshTable();
    }

    /**
     * Refreshes the table's data.
     */
    public void refreshTable() {
        if (searchTextField.getText().length() > 0 && !searchTextField.getText().equals("")) {
            tableItems = searchHelper.searchItems(items, searchTextField.getText());
        } else {
            tableItems = items;
        }

        model.setItems(tableItems);
        table.setModel(model);
        table.revalidate();
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
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        addButton = new javax.swing.JButton();
        editButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        selectButton = new javax.swing.JButton();
        statusComboBox = new javax.swing.JComboBox<>();
        comboBoxExplanationLabel = new javax.swing.JLabel();
        searchTextField = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setOpaque(false);

        titleLabel.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        titleLabel.setText("BasePanel");

        table.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(table);

        addButton.setText("Add");
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        editButton.setText("View/Edit");
        editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editButtonActionPerformed(evt);
            }
        });

        deleteButton.setText("Delete");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        selectButton.setText("Select");
        selectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectButtonActionPerformed(evt);
            }
        });

        comboBoxExplanationLabel.setText("View Jobs by Status:");

        jButton1.setText("Refresh");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setText("Search:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 669, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(titleLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addComponent(selectButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(editButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(deleteButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboBoxExplanationLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(titleLabel)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(searchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addButton)
                    .addComponent(editButton)
                    .addComponent(deleteButton)
                    .addComponent(selectButton)
                    .addComponent(statusComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboBoxExplanationLabel)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 476, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        // TODO add your handling code here:
        presentEdit(false);
    }//GEN-LAST:event_addButtonActionPerformed

    private void editButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editButtonActionPerformed
        // TODO add your handling code here:
        presentEdit(true);
    }//GEN-LAST:event_editButtonActionPerformed

    /**
     * Presents the appropriate JFrame to edit the Item.
     *
     * @param edit whether the Item is to be edited or to create a new one.
     */
    public void presentEdit(boolean edit) {

        int[] rows = {0};

        if (edit == true && this.table.getSelectedRowCount() > 0) {
            rows = this.table.getSelectedRows();
        }

        if (!edit) {
            rows[0] = 0;
        }

        AddInterface frame = null;

        for (int i : rows) {

            switch (type) {
                case USERS:
                    frame = new AddUserFrame();
                    break;
                case CUSTOMERS:
                    frame = new AddCustomerUI();
                    break;
                case JOBS:
                    frame = new AddJobFrame();
                    break;
                case PARTS:
                case STOCK:
                    frame = new AddPartFrame();
                    break;
                case TASKS:
                    frame = new AddTaskFrame();
                    break;
                case VEHICLES:
                    frame = new AddVehicleFrame();
                    break;
                case PART_ORDERS:
                    frame = new AddPartOrderFrame();
                    break;
                case SUPPLIERS:
                    frame = new AddSupplierFrame();
                    break;
                case JOB_TYPES:
                    frame = new AddJobTypeFrame();
                    break;
                default:
                    break;
            }

            if (edit) {
                frame.editItem(tableItems.get(i));
            }

            frame.setDelegate(this);
            JFrame frame_ = (JFrame) frame;
            Container panel = frame_.getContentPane();
            panel.setBackground(Color.white);
            JScrollPane pane = new JScrollPane(panel);
            pane.setVisible(true);
            JFrame newFrame = new JFrame();
            newFrame.add(pane);
            newFrame.validate();
            newFrame.setSize(frame_.getSize());
            newFrame.repaint();
            newFrame.setVisible(true);

        }
    }

    /**
     * Deletes the selected rows of Items on the table.
     *
     * @param evt the action when clicking the delete button.
     * @see BaseController
     */
    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        // TODO add your handling code here:

        int[] rows = table.getSelectedRows();

        for (int i : rows) {
            controller.deleteItem(tableItems.get(i));
        }

        reset();
    }//GEN-LAST:event_deleteButtonActionPerformed

    /**
     * Passes the selected rows as a List of Items to the delegate.
     *
     * @param evt the action when clicking the Select button.
     * @see ItemsInterface
     * @see BasePanel
     */
    private void selectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectButtonActionPerformed
        // TODO add your handling code here:

        ArrayList<Item> newItems = new ArrayList<>();

        int[] rows = this.table.getSelectedRows();

        for (int i : rows) {
            Item item = tableItems.get(i);

            if (type == PanelType.STOCK) {
                Stock stock = (Stock) item;
                if (stock.quantity() <= 0) {

                    JOptionPane.showMessageDialog(null, "There is not enough of this item.", "Not Enough", JOptionPane.ERROR_MESSAGE);

                    continue;
                }
            }

            newItems.add(item);
        }

        this.delegate.selectedItems(newItems);
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }//GEN-LAST:event_selectButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        reset();
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JLabel comboBoxExplanationLabel;
    private javax.swing.JButton deleteButton;
    private javax.swing.JButton editButton;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField searchTextField;
    private javax.swing.JButton selectButton;
    private javax.swing.JComboBox<String> statusComboBox;
    private javax.swing.JTable table;
    private javax.swing.JLabel titleLabel;
    // End of variables declaration//GEN-END:variables
}
