/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Controllers.CustomerController;
import Controllers.JobController;
import DatabasePackage.DBConnect;
import Delegates.AddInterface;
import Entities.Customer;
import Entities.Item;
import Delegates.ItemsInterface;
import Delegates.PaymentInterface;
import Entities.Invoice;
import Entities.Item.ItemType;
import Entities.Job;
import Entities.JobStock;
import Entities.JobType;
import Entities.Mechanic;
import Entities.Stock;
import Entities.Task;
import Entities.User;
import Entities.Vehicle;
import Helpers.ConfigHelper;
import Helpers.PrintHelper;
import Panels.BasePanel;
import Panels.BasePanel.PanelType;
import TableModels.JobPartsTableModel;
import TableModels.JobTasksTableModel;
import TableModels.MechanicsTableModel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Daven
 */
public class AddJobFrame extends javax.swing.JFrame implements ItemsInterface, AddInterface, PaymentInterface {

    Job job = new Job();
    JobController controller = new JobController();
    BasePanel delegate;
    JobPartsTableModel partsModel = new JobPartsTableModel();
    JobTasksTableModel tasksModel = new JobTasksTableModel();
    MechanicsTableModel mechsModel = new MechanicsTableModel();
    String[] statusStrings = {"Pending", "In Progress", "Completed"};
    boolean edit;
    Double markup = 0.0;

    /**
     * Creates new form AddJobFrame
     */
    public AddJobFrame() {
        initComponents();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        markup = new ConfigHelper().getMarkupRate();

        for (String type : statusStrings) {
            this.statusComboBox.addItem(type);
        }

        partsModel.setDelegate(this);
        tasksModel.setDelegate(this);

        User user = DBConnect.sharedInstance().user();

        if (user.userType() != User.UserType.MECHANIC) {
            pickJobTickBox.setEnabled(false);
            mechHoursTextField.setEnabled(false);
            mechMinsTextField.setEnabled(false);
        } else {
            printInvoiceButton.setVisible(false);
        }

        pickJobTickBox.addItemListener((ItemEvent e) -> {
            tickBoxChanged();
        });

        titleLabel.setOpaque(true);

        statusComboBox.addActionListener((ActionEvent e) -> {
            switch (statusComboBox.getSelectedIndex()) {
                case 0:
                    titleLabel.setBackground(Color.WHITE);
                    break;
                case 1:
                    titleLabel.setBackground(Color.yellow);
                    break;
                case 2:
                    titleLabel.setBackground(Color.green);
                    break;
            }
        });

        statusComboBox.setSelectedIndex(0);

        setToToday();

        this.job.setItems(controller.getStockForJob(job));
        this.job.setTasks(controller.getTasksForJob(job));
        this.job.setMechanics(controller.getMechanicsForJob(job));
    }

    public void editJob(Job job) {

        this.titleLabel.setText("Edit Job");
        this.addButton.setText("Update Job");
        edit = true;
        this.job = job;

        if (this.job.date() != null) {
            this.dateTextField.setText(new SimpleDateFormat("dd/MM/yyyy").format(this.job.date()));
        }

        this.customerTextField.setText(this.job.customer().firstName() + " " + this.job.customer().lastName());

        int estTime = this.job.estTime();

        this.estDaysTextField.setText("" + estTime / 24 / 60);
        this.estHoursTextField.setText("" + estTime / 60 % 24);
        this.estMinsTextField.setText("" + estTime % 60);

        int actTime = this.job.actualTime();

        this.actDaysTextField.setText("" + actTime / 24 / 60);
        this.actHoursTextField.setText("" + actTime / 60 % 24);
        this.actMinsTextField.setText("" + actTime % 60);

        this.workRequiredTextArea.setText(this.job.workRequired());
        this.workCarriedTextArea.setText(this.job.workCarried());

        this.regTextField.setText(this.job.vehicleReg());
        this.costTextField.setValue(this.job.cost());
        this.job.setItems(controller.getStockForJob(job));
        this.job.setTasks(controller.getTasksForJob(job));
        this.job.setMechanics(controller.getMechanicsForJob(job));

        User user = DBConnect.sharedInstance().user();

        if (user.type() == ItemType.MECHANIC) {
            ArrayList<Mechanic> mechs = job.mechanics();
            Mechanic mech = null;
            for (Mechanic mech_ : mechs) {
                if (mech_.ID() == user.ID()) {
                    mech = mech_;
                    break;
                }
            }

            if (mech != null) {
                int mechTime = mech.minsWorked();

                this.mechHoursTextField.setText("" + mechTime / 60 % 24);
                this.mechMinsTextField.setText("" + mechTime % 60);
                pickJobTickBox.setSelected(true);
            }
        }

        jobTypeLabel.setText(job.jobType().name());

        if (job.paid()) {
            paidCheckBox.setSelected(true);
        }

        statusComboBox.setSelectedIndex(job.statusInt());

        refreshTable();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        titleLabel = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        dateTextField = new javax.swing.JFormattedTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        customerTextField = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        addButton = new javax.swing.JButton();
        statusComboBox = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        workRequiredTextArea = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        workCarriedTextArea = new javax.swing.JTextArea();
        selectVehicleButton = new javax.swing.JButton();
        viewItemsButton = new javax.swing.JButton();
        costTextField = new javax.swing.JFormattedTextField();
        addItemButton = new javax.swing.JButton();
        deleteItemsButton = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        partsTable = new javax.swing.JTable();
        todayButton = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        estDaysTextField = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        estHoursTextField = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        estMinsTextField = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        actDaysTextField = new javax.swing.JTextField();
        actHoursTextField = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        actMinsTextField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tasksTable = new javax.swing.JTable();
        jLabel21 = new javax.swing.JLabel();
        addTaskButton = new javax.swing.JButton();
        editTaskButton = new javax.swing.JButton();
        deleteTaskButton = new javax.swing.JButton();
        regTextField = new javax.swing.JLabel();
        printInvoiceButton = new javax.swing.JButton();
        pickJobTickBox = new javax.swing.JCheckBox();
        mechHoursTextField = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        mechMinsTextField = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        mechanicsTable = new javax.swing.JTable();
        jLabel25 = new javax.swing.JLabel();
        jobTypeLabel = new javax.swing.JLabel();
        selectJobTypeButton = new javax.swing.JButton();
        paidCheckBox = new javax.swing.JCheckBox();
        jButton1 = new javax.swing.JButton();

        jLabel2.setText("jLabel2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        titleLabel.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        titleLabel.setText("Add New Job");

        jLabel3.setText("Date (dd/mm/yyyy):");

        dateTextField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("dd/MM/yyyy"))));

        jLabel4.setText("Customer:");

        jLabel5.setText("Estimated Time:");

        customerTextField.setText("None");

        jLabel7.setText("Actual Time:");

        jLabel8.setText("Work Required:");

        jLabel9.setText("Work Carried:");

        jLabel10.setText("Vehicle Reg:");

        jLabel12.setText("Mechanics:");

        jLabel13.setText("Items:");

        jLabel14.setText("Cost:");

        jLabel15.setText("Status:");

        addButton.setText("Add Job");
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        workRequiredTextArea.setColumns(20);
        workRequiredTextArea.setRows(5);
        jScrollPane1.setViewportView(workRequiredTextArea);

        workCarriedTextArea.setColumns(20);
        workCarriedTextArea.setRows(5);
        jScrollPane2.setViewportView(workCarriedTextArea);

        selectVehicleButton.setText("Select Vehicle");
        selectVehicleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectVehicleButtonActionPerformed(evt);
            }
        });

        viewItemsButton.setText("View Items");

        costTextField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###.00"))));
        costTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                costTextFieldActionPerformed(evt);
            }
        });

        addItemButton.setText("Add Item");
        addItemButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addItemButtonActionPerformed(evt);
            }
        });

        deleteItemsButton.setText("Delete");
        deleteItemsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteItemsButtonActionPerformed(evt);
            }
        });

        partsTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(partsTable);

        todayButton.setText("Set to Today");
        todayButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                todayButtonActionPerformed(evt);
            }
        });

        jLabel6.setText("Days:");

        estDaysTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                estDaysTextFieldActionPerformed(evt);
            }
        });

        jLabel16.setText("Hours:");

        estHoursTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                estHoursTextFieldActionPerformed(evt);
            }
        });

        jLabel17.setText("Minutes:");

        jLabel18.setText("Days:");

        actDaysTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actDaysTextFieldActionPerformed(evt);
            }
        });

        actHoursTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actHoursTextFieldActionPerformed(evt);
            }
        });

        jLabel19.setText("Hours:");

        jLabel20.setText("Minutes:");

        jLabel1.setText("£");

        tasksTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane4.setViewportView(tasksTable);

        jLabel21.setText("Tasks:");

        addTaskButton.setText("Add Task");
        addTaskButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addTaskButtonActionPerformed(evt);
            }
        });

        editTaskButton.setText("View/Edit Task");

        deleteTaskButton.setText("Delete Task");
        deleteTaskButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteTaskButtonActionPerformed(evt);
            }
        });

        regTextField.setText("None");

        printInvoiceButton.setText("Print Invoice");
        printInvoiceButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printInvoiceButtonActionPerformed(evt);
            }
        });

        pickJobTickBox.setText("Pick Job");

        mechHoursTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mechHoursTextFieldActionPerformed(evt);
            }
        });

        jLabel22.setText("Time worked:");

        jLabel23.setText("Hours:");

        jLabel24.setText("Minutes:");

        mechanicsTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane5.setViewportView(mechanicsTable);

        jLabel25.setText("Job Type:");

        jobTypeLabel.setText("None");

        selectJobTypeButton.setText("Select Job Type");
        selectJobTypeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectJobTypeButtonActionPerformed(evt);
            }
        });

        paidCheckBox.setText("Paid");

        jButton1.setText("Print Job Sheet");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(regTextField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(dateTextField)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(customerTextField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(16, 16, 16)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(selectVehicleButton)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(todayButton)
                                .addContainerGap())))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addGap(92, 92, 92)
                        .addComponent(jScrollPane4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(editTaskButton, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(addTaskButton, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(deleteTaskButton, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(38, 38, 38)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(pickJobTickBox)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel22)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel23)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(mechHoursTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel24)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(mechMinsTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(jScrollPane5))))
                        .addGap(136, 136, 136))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(printInvoiceButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addButton))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel12)
                            .addComponent(jLabel9)
                            .addComponent(jLabel14)
                            .addComponent(jLabel15))
                        .addGap(48, 48, 48)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(statusComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(paidCheckBox)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(addItemButton, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(viewItemsButton, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(deleteItemsButton, javax.swing.GroupLayout.Alignment.TRAILING)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(costTextField))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(38, 38, 38)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel18)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(actDaysTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel19)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(actHoursTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel20)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(actMinsTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(estDaysTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel16)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(estHoursTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel17)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(estMinsTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(133, 133, 133)
                                        .addComponent(jobTypeLabel))
                                    .addComponent(jLabel25))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(selectJobTypeButton)))
                        .addContainerGap())))
            .addComponent(titleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(dateTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(todayButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(regTextField)
                    .addComponent(selectVehicleButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(customerTextField))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(jobTypeLabel)
                    .addComponent(selectJobTypeButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(estDaysTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(estHoursTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(estMinsTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel18)
                        .addComponent(actDaysTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel19)
                        .addComponent(actHoursTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel20)
                        .addComponent(actMinsTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel21)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(pickJobTickBox)
                            .addComponent(mechHoursTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel22)
                            .addComponent(jLabel23)
                            .addComponent(jLabel24)
                            .addComponent(mechMinsTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(addTaskButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editTaskButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteTaskButton)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(addItemButton))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(viewItemsButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteItemsButton))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(costTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(statusComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(paidCheckBox))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addButton)
                    .addComponent(printInvoiceButton)
                    .addComponent(jButton1))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        // TODO add your handling code here:

        addButtonTapped();
    }//GEN-LAST:event_addButtonActionPerformed

    private void addButtonTapped() {
        int debugCount = 0;

        if (this.dateTextField.getText().length() > 9) {
            try {
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                java.util.Date parsed = format.parse(this.dateTextField.getText());
                java.sql.Date sqlDate = new java.sql.Date(parsed.getTime());
                job.setDate(sqlDate);
            } catch (ParseException ex) {
            }
        }
        int estTotal = 0;
        if (this.estDaysTextField.getText().length() > 0) {
            estTotal = estTotal + (Integer.parseInt(this.estDaysTextField.getText()) * 24 * 60);
        }
        if (this.estHoursTextField.getText().length() > 0) {
            estTotal = estTotal + (Integer.parseInt(this.estHoursTextField.getText()) * 60);
        }
        if (this.estMinsTextField.getText().length() > 0) {
            estTotal = estTotal + Integer.parseInt(this.estMinsTextField.getText());
        }
        job.setEstTime(estTotal);
        int actTotal = 0;
        if (this.actDaysTextField.getText().length() > 0) {
            actTotal = actTotal + (Integer.parseInt(this.actDaysTextField.getText()) * 24 * 60);
        }
        if (this.actHoursTextField.getText().length() > 0) {
            actTotal = actTotal + (Integer.parseInt(this.actHoursTextField.getText()) * 60);
        }
        if (this.actMinsTextField.getText().length() > 0) {
            actTotal = actTotal + Integer.parseInt(this.actMinsTextField.getText());
        }

        job.setActualTime(actTotal);

        job.setWorkRequired(this.workRequiredTextArea.getText());
        job.setWorkCarried(this.workCarriedTextArea.getText());
        job.setVehicleReg(this.regTextField.getText());

        job.setStatus(this.statusComboBox.getSelectedIndex());

        double cost = 0;

        if (DBConnect.sharedInstance().user().type() == ItemType.MECHANIC) {
            tickBoxChanged();
        }

        if (this.costTextField.getText().length() > 0 || !(this.costTextField.getText().equals("null"))) {
            if (this.costTextField.getValue() instanceof Double) {
                cost = (double) this.costTextField.getValue();
            } else if (this.costTextField.getValue() instanceof Long) {
                cost = ((Long) this.costTextField.getValue()).doubleValue();
            }
        }

        job.setCost(cost);
        job.setPaid(paidCheckBox.isSelected());

        if (edit) {
            controller.updateJob(job);
        } else {
            ArrayList<Job> unpaid = new CustomerController().unpaidJobsForCustomer(job.customer());

            if (unpaid.size() > 0) {

                int dialogResult = JOptionPane.showConfirmDialog(this, "Customer has outstanding invoices to be paid.  Would you like to view them?");
                if (dialogResult == JOptionPane.YES_OPTION) {
                    AddJobFrame frame = new AddJobFrame();
                    frame.editJob(unpaid.get(0));
                    frame.setVisible(true);
                    return;
                } else {
                    return;
                }

            } else {
                controller.addJob(job);
            }
        }
        this.delegate.reset();
    }

    private void selectVehicleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectVehicleButtonActionPerformed
        // TODO add your handling code here:
        SelectFrame frame = new SelectFrame(PanelType.VEHICLES, this);
        frame.setVisible(true);
    }//GEN-LAST:event_selectVehicleButtonActionPerformed

    private void todayButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_todayButtonActionPerformed
        // TODO add your handling code here:
        setToToday();
    }//GEN-LAST:event_todayButtonActionPerformed

    private void estDaysTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_estDaysTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_estDaysTextFieldActionPerformed

    private void estHoursTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_estHoursTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_estHoursTextFieldActionPerformed

    private void actDaysTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_actDaysTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_actDaysTextFieldActionPerformed

    private void actHoursTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_actHoursTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_actHoursTextFieldActionPerformed

    private void addItemButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addItemButtonActionPerformed
        // TODO add your handling code here:

        SelectFrame frame = new SelectFrame(PanelType.STOCK, this);
        frame.setVisible(true);
    }//GEN-LAST:event_addItemButtonActionPerformed

    private void deleteItemsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteItemsButtonActionPerformed
        // TODO add your handling code here:
        int[] rows = this.partsTable.getSelectedRows();

        List<JobStock> parts = this.job.items();

        for (int i = rows.length - 1; i >= 0; i--) {
            parts.remove(rows[i]);
        }

        this.job.setItems(parts);
        refreshTable();
    }//GEN-LAST:event_deleteItemsButtonActionPerformed

    private void costTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_costTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_costTextFieldActionPerformed

    private void addTaskButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addTaskButtonActionPerformed
        // TODO add your handling code here:
        System.out.print("addTaskButtonActionPerformed");
        SelectFrame frame = new SelectFrame(PanelType.TASKS, this);
        frame.setVisible(true);
    }//GEN-LAST:event_addTaskButtonActionPerformed

    private void deleteTaskButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteTaskButtonActionPerformed
        // TODO add your handling code here:
        int[] rows = tasksTable.getSelectedRows();

        ArrayList<Task> tasks = (ArrayList<Task>) job.tasks();

        for (int i = rows.length - 1; i >= 0; i--) {
            tasks.remove(rows[i]);
        }
        job.setTasks(tasks);
        refreshTable();
    }//GEN-LAST:event_deleteTaskButtonActionPerformed

    private void mechHoursTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mechHoursTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mechHoursTextFieldActionPerformed

    private void printInvoiceButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printInvoiceButtonActionPerformed
        // TODO add your handling code here:

        InvoiceFrame frame = new InvoiceFrame();
        frame.invoicePanel().setDelegate(this);
        Invoice invoice = new Invoice();
        invoice.setJob(job);

        frame.invoicePanel().setInvoice(invoice);
        frame.setVisible(true);
    }//GEN-LAST:event_printInvoiceButtonActionPerformed

    private void selectJobTypeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectJobTypeButtonActionPerformed
        // TODO add your handling code here:
        SelectFrame frame = new SelectFrame(PanelType.JOB_TYPES, this);
        frame.setVisible(true);
    }//GEN-LAST:event_selectJobTypeButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        PrintHelper printHelper = new PrintHelper();
        printHelper.printJobSheet(job, true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void setToToday() {
        java.text.SimpleDateFormat todaysDate
                = new java.text.SimpleDateFormat("dd/MM/yyyy");
        String dateStr = todaysDate.format((java.util.Calendar.getInstance()).getTime());
        this.dateTextField.setText(dateStr);
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
            java.util.logging.Logger.getLogger(AddJobFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddJobFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddJobFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddJobFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddJobFrame().setVisible(true);
            }
        });
    }

    @Override
    public void selectedItems(ArrayList<? extends Item> items) {

        System.out.println("selectedItems: " + items.size());

        if (!(items.size() > 0)) {
            return;
        }

        Item first = items.get(0);
        System.out.println("Got type");
        switch (first.type()) {
            case CUSTOMER:
                System.out.println("Type is customer");

                Customer customer = (Customer) first;
                this.customerTextField.setText(customer.firstName() + " " + customer.lastName());
                job.setCustomer(customer);
                break;
            case STOCK:
                System.out.println("Type is stock");

                List<JobStock> jobParts = job.items();

                for (Item item : items) {
                    Stock part = (Stock) item;
                    boolean exists = false;
                    for (JobStock jobPart : jobParts) {

                        if (part.itemNumber().equals(jobPart.itemNumber())) {
                            jobPart.setJobQuantity(jobPart.jobQuantity() + 1);
                            exists = true;
                            break;
                        }
                    }
                    if (!exists) {
                        System.out.println("Does not exist");
                        JobStock stock = new JobStock(part);
                        stock.setJobQuantity(1);
                        jobParts.add(stock);
                    }
                }

                System.out.println("Selected Parts, number: " + jobParts.size());
                System.out.println("Part number: " + jobParts.get(0).part().number());
                updatedParts(jobParts);
                break;
            case TASK:
                System.out.println("Type is task");

                ArrayList<Task> tasks = job.tasks();
                ArrayList<Task> newTasks = job.tasks();

                for (Item item : items) {
                    Task task = (Task) item;
                    boolean exists = false;
                    for (Task task_ : tasks) {
                        if (task_.ID() == task.ID()) {
                            exists = true;
                            break;
                        }
                    }
                    if (!exists) {
                        newTasks.add(task);
                    }
                }

                updatedTasks((ArrayList<Task>) newTasks);
                break;
            case VEHICLE:
                System.out.println("Type is vehicle");

                Vehicle vehicle = (Vehicle) first;
                Customer customer_ = vehicle.customer();

                job.setVehicle(vehicle);
                job.setCustomer(customer_);

                regTextField.setText(vehicle.regNo());
                customerTextField.setText(customer_.firstName() + " " + customer_.lastName());

                return;
            case JOB_TYPE:
                System.out.println("Type is job type");

                JobType type = (JobType) first;
                job.setJobType(type);
                jobTypeLabel.setText(type.name());
            default:
                break;
        }
    }

    public void updatedParts(List<JobStock> jobParts) {
        job.setItems(jobParts);
        refreshTable();
    }

    public void updatedTasks(ArrayList<Task> jobTasks) {
        job.setTasks(jobTasks);
        refreshTable();
    }

    public void tickBoxChanged() {
        if (pickJobTickBox.isSelected()) {
            addMechanic();
        } else {
            removeMechanic();
        }
    }

    public void addMechanic() {

        Mechanic mech = (Mechanic) DBConnect.sharedInstance().user();
        int estTotal = 0;

        if (this.mechHoursTextField.getText().length() > 0) {
            estTotal = estTotal + (Integer.parseInt(this.mechHoursTextField.getText()) * 60);
        }
        if (this.mechMinsTextField.getText().length() > 0) {
            estTotal = estTotal + Integer.parseInt(this.mechMinsTextField.getText());
        }
        mech.setMinutesWorked(estTotal);
        ArrayList<Mechanic> mechs = job.mechanics();

        boolean exists = false;
        for (Mechanic mech_ : mechs) {
            if (mech.ID() == mech_.ID()) {
                if (mech.minsWorked() != mech_.minsWorked()) {
                    mech_.setMinutesWorked(mech.minsWorked());
                }
                exists = true;
                break;
            }
        }
        if (exists == false) {
            mechs.add(mech);
        }

        job.setMechanics(mechs);
        job.setMechanics(controller.getMechanicsForJob(job));
        refreshTable();
    }

    public void removeMechanic() {
        Mechanic mech = (Mechanic) DBConnect.sharedInstance().user();

        ArrayList<Mechanic> mechs = job.mechanics();
        for (Mechanic mech_ : mechs) {
            if (mech.ID() == mech_.ID()) {
                mechs.remove(mech_);
                break;
            }
        }

        job.setMechanics(mechs);
        job.setMechanics(controller.getMechanicsForJob(job));

        refreshTable();
    }

    public void refreshTable() {
        partsModel.setItems(this.job.items());
        this.partsTable.setModel(partsModel);

        tasksModel.setTasks(this.job.tasks());
        this.tasksTable.setModel(tasksModel);

        mechsModel.setMechanics(this.job.mechanics());

        job.mechanics().stream().forEach((mech) -> {
            System.out.println("Got Mechanic: " + mech.firstName());
        });

        this.mechanicsTable.setModel(mechsModel);

        this.partsTable.revalidate();
        this.tasksTable.revalidate();
        this.mechanicsTable.revalidate();

        calculateCost();
    }

    public void calculateCost() {
        Double totalCost = 0.0;

        for (JobStock stock : job.items()) {
            totalCost = totalCost + (stock.cost() * ((100 + markup) / 100) * stock.jobQuantity());
        }

        for (Mechanic mech : job.mechanics()) {
            totalCost = totalCost + ((mech.hourlyRate() / 60) * mech.minsWorked());
        }

        costTextField.setValue(totalCost);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField actDaysTextField;
    private javax.swing.JTextField actHoursTextField;
    private javax.swing.JTextField actMinsTextField;
    private javax.swing.JButton addButton;
    private javax.swing.JButton addItemButton;
    private javax.swing.JButton addTaskButton;
    private javax.swing.JFormattedTextField costTextField;
    private javax.swing.JLabel customerTextField;
    private javax.swing.JFormattedTextField dateTextField;
    private javax.swing.JButton deleteItemsButton;
    private javax.swing.JButton deleteTaskButton;
    private javax.swing.JButton editTaskButton;
    private javax.swing.JTextField estDaysTextField;
    private javax.swing.JTextField estHoursTextField;
    private javax.swing.JTextField estMinsTextField;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel jobTypeLabel;
    private javax.swing.JTextField mechHoursTextField;
    private javax.swing.JTextField mechMinsTextField;
    private javax.swing.JTable mechanicsTable;
    private javax.swing.JCheckBox paidCheckBox;
    private javax.swing.JTable partsTable;
    private javax.swing.JCheckBox pickJobTickBox;
    private javax.swing.JButton printInvoiceButton;
    private javax.swing.JLabel regTextField;
    private javax.swing.JButton selectJobTypeButton;
    private javax.swing.JButton selectVehicleButton;
    private javax.swing.JComboBox<String> statusComboBox;
    private javax.swing.JTable tasksTable;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JButton todayButton;
    private javax.swing.JButton viewItemsButton;
    private javax.swing.JTextArea workCarriedTextArea;
    private javax.swing.JTextArea workRequiredTextArea;
    // End of variables declaration//GEN-END:variables

    @Override
    public void setDelegate(BasePanel panel) {
        this.delegate = panel;
    }

    @Override
    public void editItem(Item item) {
        editJob((Job) item);
    }

    @Override
    public void updatedItems(ArrayList<? extends Item> items) {
        Item first = items.get(0);
        switch (first.type()) {
            case PART:
                updatedParts((List<JobStock>) items);
                break;
            case TASK:
                updatedTasks((ArrayList<Task>) items);
                break;
            default:
                break;
        }
    }

    @Override
    public void paid() {
        paidCheckBox.setSelected(true);
        addButtonTapped();
    }
}
