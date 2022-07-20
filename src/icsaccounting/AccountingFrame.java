package icsaccounting;

import Objectes.Customers;
import Objectes.Transactions;
import mymethods.MyMethods;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Toolkit;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.PatternSyntaxException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

public class AccountingFrame extends javax.swing.JFrame {

    public AccountingFrame() {
        initComponents();
        
        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        
        TotalTreasuryMoney();
        
        setImageIco();

        displayCustomers();
        
        setTotalCredit();
        
        custTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        transactionsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        mm.modificationJtablesHeader(transactionsTable);
        mm.modificationJtablesHeader(custTable);
        
        mm.setTableRowsTextToCenter(custTable, 2);
        mm.setTableRowsTextToCenter(transactionsTable, 4);
        
        mm.setJDateChooserValue(jDateChooser1, "2021-1-1");
    }
    
    MyMethods mm = new MyMethods();
    DefaultTableModel model;
    Object[] row;
    String sql;
    
    int TotalDebt;
    int TotalCreditForID0;
    void TotalTreasuryMoney()
    {
        //Start Code
        TotalDebt = 0;
        TotalCreditForID0 = 0;
        
        try
        {    
            mm.getConnectionWithStatementAndResultSet("icsAccounting", "icsAccounting", "select debt from transactions where id <> 1");
            
            while(mm.rs.next())
                TotalDebt = TotalDebt + mm.rs.getInt("debt");

            //new statement -----------------------------------------------------------------------------------------------
            
            mm.rs = mm.st.executeQuery("select Credit from transactions where id = 0");
            
            while(mm.rs.next())
                TotalCreditForID0 = TotalCreditForID0 + mm.rs.getInt("credit");
            
            mm.closeConnection(1);
            
        }
        catch (SQLException ex)
        {
            mm.closeConnection(1);
            JOptionPane.showMessageDialog(this, ex);
        }
        
        TotalTreasuryMoneyLabel.setText(Integer.toString(TotalDebt - TotalCreditForID0));
    }
    
    int totalCredit;
    void setTotalCredit()
    {
        totalCredit = 0;
        
        for(int i = 1; i < custTable.getRowCount(); i++)
        {
            totalCredit = totalCredit + Integer.parseInt(custTable.getValueAt(i, 2).toString());
        }
        
        jLabel2.setText(Integer.toString(totalCredit));
    }
    
    void setImageIco() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("03.jpg")));
    }
    
    int custSRow() {
        return custTable.getSelectedRow();
    }
    
    int transSRow() {
        return transactionsTable.getSelectedRow();
    }
    
    ArrayList<Transactions> transactionslist;
    Transactions transactions;
    void displayTransactions()
    {
        transactionslist = new ArrayList<>();
        sql = " select * from transactions where id = " +
                mm.getValueAt(custTable, 0) + " and datee >= (to_date('" +
                ((JTextField)jDateChooser1.getDateEditor().getUiComponent()).getText() + "','yyyy-MM-dd')) order by datee desc";//+ idTXT.getText();

        try
        {
            mm.getConnectionWithStatementAndResultSet("icsAccounting", "icsAccounting", sql);
            
            while (mm.rs.next())
            {
                transactions = new Transactions(
                        mm.rs.getInt("code"),
                        mm.rs.getDate("datee"),
                        mm.rs.getInt("credit"),
                        mm.rs.getInt("debt"),
                        mm.rs.getString("description")
                        );
                
                transactionslist.add(transactions);
            }
            
            mm.closeConnection(1);
        }
        catch (SQLException ex)
        {
            mm.closeConnection(1);
        }
        
        
        
        model = mm.getDefaultTableModel(transactionsTable);
        row = new Object[5];
        for(int i = 0; i < transactionslist.size(); i++)
        {
            row[0] = transactionslist.get(i).getCode();
            row[1] = transactionslist.get(i).getDate();
            row[2] = transactionslist.get(i).getDescription();
            row[3] = transactionslist.get(i).getCredit();
            row[4] = transactionslist.get(i).getDebt();
            
            model.addRow(row);
        }
    }
    
    ArrayList<Customers> customerslist;
    Customers customer;
    void displayCustomers() {
        
        customerslist = new ArrayList<>();
        sql = " select * from customers order by id asc";

        try
        {
            mm.getConnectionWithStatementAndResultSet("icsAccounting", "icsAccounting", sql);
            
            while (mm.rs.next())
            {
                customer = new Customers(
                        mm.rs.getInt("id"),
                        mm.rs.getString("name"),
                        mm.rs.getInt("indebt"));
                
                customerslist.add(customer);
            }
            
            mm.closeConnection(1);
        }
        catch (SQLException ex)
        {
            mm.closeConnection(1);
        }
        
        
        
        model = mm.getDefaultTableModel(custTable);
        row = new Object[3];
        for(int i = 0; i < customerslist.size(); i++)
        {
            row[0] = customerslist.get(i).getId();
            row[1] = customerslist.get(i).getName();
            row[2] = customerslist.get(i).getInDebt();
            
            model.addRow(row);
        }
    }
    
    int credit;
    int debt;
    int indebt;
    void getTotalCustomerCreditAndUpdateCreditValue(int id)
    {
        credit = 0;
        debt = 0;
        sql = "select credit, debt from transactions where id = " + id;
        
        try
        {
            mm.getConnectionWithStatementAndResultSet("icsAccounting", "icsAccounting", sql);
            
            while(mm.rs.next())
            {
                credit = credit + mm.rs.getInt("credit");
                debt = debt + mm.rs.getInt("debt");
            }
            
            mm.closeConnection(1);
        }
        catch (SQLException ex)
        {
            mm.closeConnection(1);
            JOptionPane.showMessageDialog(this, ex);
        }
        
        indebt = credit - debt;
        
        sql = "update customers set indebt = " + indebt + " where id = " + id;

        mm.insertOrUpdate("icsAccounting", "icsAccounting", sql, null);
        
        custTable.setValueAt(indebt, custSRow(), 2);
        
        setTotalCredit();
    }
      
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        custPanel = new javax.swing.JPanel();
        updateCustomerBTN = new javax.swing.JButton();
        newCustomerBTN = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        deleteCustomerBTN1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        transactionsTable = new javax.swing.JTable();
        transactionFilterTXT = new javax.swing.JTextField();
        transPanel = new javax.swing.JPanel();
        insertTransactionBTN = new javax.swing.JButton();
        updateTransactionBTN = new javax.swing.JButton();
        deleteBTN = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        custTable = new javax.swing.JTable();
        custFilterTXT = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        TotalTreasuryMoneyLabel = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        reportPathTXT = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ICS-Accounting");

        jPanel3.setBackground(new java.awt.Color(0, 0, 153));

        custPanel.setBackground(java.awt.Color.blue);

        updateCustomerBTN.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        updateCustomerBTN.setText("Update C");
        updateCustomerBTN.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        updateCustomerBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateCustomerBTNActionPerformed(evt);
            }
        });

        newCustomerBTN.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        newCustomerBTN.setText("Insert C");
        newCustomerBTN.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        newCustomerBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newCustomerBTNActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Credit Total: ");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        deleteCustomerBTN1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        deleteCustomerBTN1.setText("Delete C");
        deleteCustomerBTN1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        deleteCustomerBTN1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteCustomerBTN1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout custPanelLayout = new javax.swing.GroupLayout(custPanel);
        custPanel.setLayout(custPanelLayout);
        custPanelLayout.setHorizontalGroup(
            custPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(custPanelLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(custPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(custPanelLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
                        .addGap(167, 167, 167))
                    .addGroup(custPanelLayout.createSequentialGroup()
                        .addComponent(newCustomerBTN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(updateCustomerBTN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteCustomerBTN1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        custPanelLayout.setVerticalGroup(
            custPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(custPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(custPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(newCustomerBTN)
                    .addGroup(custPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(updateCustomerBTN)
                        .addComponent(deleteCustomerBTN1)))
                .addGap(11, 11, 11)
                .addGroup(custPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12))
        );

        jPanel2.setBackground(java.awt.Color.blue);

        transactionsTable.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        transactionsTable.setForeground(new java.awt.Color(0, 0, 204));
        transactionsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Date", "Description", "Credit", "Debt"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        transactionsTable.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        transactionsTable.setOpaque(false);
        transactionsTable.setRowHeight(22);
        transactionsTable.setRowMargin(3);
        transactionsTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(transactionsTable);
        if (transactionsTable.getColumnModel().getColumnCount() > 0) {
            transactionsTable.getColumnModel().getColumn(1).setPreferredWidth(220);
            transactionsTable.getColumnModel().getColumn(2).setPreferredWidth(800);
            transactionsTable.getColumnModel().getColumn(3).setPreferredWidth(180);
            transactionsTable.getColumnModel().getColumn(4).setPreferredWidth(180);
        }

        transactionFilterTXT.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        transactionFilterTXT.setForeground(new java.awt.Color(153, 153, 153));
        transactionFilterTXT.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        transactionFilterTXT.setText("Search");
        transactionFilterTXT.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                transactionFilterTXTFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                transactionFilterTXTFocusLost(evt);
            }
        });
        transactionFilterTXT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                transactionFilterTXTKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(transactionFilterTXT)
                    .addComponent(jScrollPane3))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(transactionFilterTXT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3)
                .addContainerGap())
        );

        transPanel.setBackground(java.awt.Color.blue);

        insertTransactionBTN.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        insertTransactionBTN.setText("Insert T");
        insertTransactionBTN.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        insertTransactionBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertTransactionBTNActionPerformed(evt);
            }
        });

        updateTransactionBTN.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        updateTransactionBTN.setText("Update T");
        updateTransactionBTN.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        updateTransactionBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateTransactionBTNActionPerformed(evt);
            }
        });

        deleteBTN.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        deleteBTN.setText("Delete T");
        deleteBTN.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        deleteBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBTNActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout transPanelLayout = new javax.swing.GroupLayout(transPanel);
        transPanel.setLayout(transPanelLayout);
        transPanelLayout.setHorizontalGroup(
            transPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(transPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(insertTransactionBTN, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(updateTransactionBTN, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(deleteBTN, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                .addGap(11, 11, 11))
        );
        transPanelLayout.setVerticalGroup(
            transPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, transPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(transPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(insertTransactionBTN)
                    .addComponent(updateTransactionBTN)
                    .addComponent(deleteBTN))
                .addContainerGap())
        );

        jPanel5.setBackground(java.awt.Color.blue);

        custTable.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        custTable.setForeground(new java.awt.Color(0, 0, 204));
        custTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Credit"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        custTable.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        custTable.setOpaque(false);
        custTable.setRowHeight(22);
        custTable.setRowMargin(3);
        custTable.getTableHeader().setReorderingAllowed(false);
        custTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                custTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(custTable);

        custFilterTXT.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        custFilterTXT.setForeground(new java.awt.Color(153, 153, 153));
        custFilterTXT.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        custFilterTXT.setText("Search");
        custFilterTXT.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                custFilterTXTFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                custFilterTXTFocusLost(evt);
            }
        });
        custFilterTXT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                custFilterTXTKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(custFilterTXT)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 457, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(custFilterTXT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 521, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));

        TotalTreasuryMoneyLabel.setFont(new java.awt.Font("Times New Roman", 1, 15)); // NOI18N
        TotalTreasuryMoneyLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 15)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Cash On Hand");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(TotalTreasuryMoneyLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(TotalTreasuryMoneyLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );

        jLabel7.setFont(new java.awt.Font("Times New Roman", 3, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 0, 0));
        jLabel7.setText("v2020.12.30    Created & Developed By Salah Muhammad");

        jButton1.setText("Run Report");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        reportPathTXT.setEditable(false);
        reportPathTXT.setForeground(new java.awt.Color(153, 153, 153));
        reportPathTXT.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        reportPathTXT.setText("Report Path");
        reportPathTXT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                reportPathTXTMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(183, 183, 183)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(custPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(transPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(0, 91, Short.MAX_VALUE)
                                .addComponent(reportPathTXT, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1)))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(custPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(reportPathTXT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(transPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    
    InsertOrUpdateTransaction nd;
    void initilizeInsertOrUpdateTransaction(JTable tableName, String afterSelect)
    {
        if(tableName.getSelectedRow() < 0) 
            JOptionPane.showMessageDialog(this, "Select " + afterSelect + "..", "message", 2);
        
        else
        {
            nd = new InsertOrUpdateTransaction(this, true);
            
            
            nd.getIdLabel().setText(tableName.getValueAt(tableName.getSelectedRow(), 0).toString());
            
            
            if(tableName == transactionsTable)
            {
               nd.setTitle("Update Transaction");
               
               nd.getIdLabel1().setText("Update Transaction No ");
                
               nd.getjCheckBox1().setSelected(true);
               
               nd.getjDateChooser1().setDate(Date.valueOf(transactionsTable.getValueAt(transSRow(), 1).toString()));
               
               nd.getDescriptionTXT().setText(transactionsTable.getValueAt(transSRow(), 2).toString());
               nd.getDescriptionTXT().setForeground(new Color(0, 0, 0));
               
               nd.getCreditTXT().setText(transactionsTable.getValueAt(transSRow(), 3).toString());
               nd.getCreditTXT().setForeground(new Color(0, 0, 0));
               
               nd.getDebtTXT().setText(transactionsTable.getValueAt(transSRow(), 4).toString());
               nd.getDebtTXT().setForeground(new Color(0, 0, 0));
            }
            else
            {
                nd.getIdLabel1().setText("New Transaction For Customer No ");
                nd.setTitle("Insert Transaction");
            }
            
            nd.setLocationRelativeTo(null);
            nd.setVisible(true);
            
            nd.dispose();

            mm.refrichTable(transactionsTable);
            displayTransactions();

            getTotalCustomerCreditAndUpdateCreditValue(Integer.parseInt(custTable.getValueAt(custSRow(), 0).toString()));

            TotalTreasuryMoney();          
        }
    }
    
    private void insertTransactionBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertTransactionBTNActionPerformed
        // TODO add your handling code here:
        initilizeInsertOrUpdateTransaction(custTable, "Customer");
    }//GEN-LAST:event_insertTransactionBTNActionPerformed

    private void updateTransactionBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateTransactionBTNActionPerformed
        // TODO add your handling code here:
        initilizeInsertOrUpdateTransaction(transactionsTable, "Transaction");
    }//GEN-LAST:event_updateTransactionBTNActionPerformed

    private void custTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_custTableMouseClicked
        // TODO add your handling code here:
        mm.refrichTable(transactionsTable);
        displayTransactions();
    }//GEN-LAST:event_custTableMouseClicked

    int response;
    private void updateCustomerBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateCustomerBTNActionPerformed
        // TODO add your handling code here:
        if(custSRow() < 0)
            JOptionPane.showMessageDialog(this, "Please Specify Customer...", "Message", 2);
        
        else
        {
            if(custTable.getValueAt(custSRow(), 1) == null || custTable.getValueAt(custSRow(), 1).equals(""))
                JOptionPane.showMessageDialog(this, "Please Specify Name..", "Message", 2);

            else
            {
                response = JOptionPane.showConfirmDialog(this, "Are You Sure That You Wanna Update Record No (" + custTable.getValueAt(custSRow(), 0)+ ")" +
                                                            "\n Set Name: (" + custTable.getValueAt(custSRow(), 1) + ")" , "update customer", 0);
                
                if(response == JOptionPane.YES_OPTION) {
                
                    mm.insertOrUpdate("icsAccounting", "icsAccounting", "update customers set name = '" + mm.getValueAt(custTable, 1) + "' where id = " + custTable.getValueAt(custSRow(), 0),
                            "Customer Updated Successfully...");
                    
                    mm.refrichTable(custTable);
                    displayCustomers();
                }
            }
        }
    }//GEN-LAST:event_updateCustomerBTNActionPerformed
    
    String newCustomer = null;
    int id;
    private void newCustomerBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newCustomerBTNActionPerformed
        // TODO add your handling code here:
        newCustomer = JOptionPane.showInputDialog(this, "Name", "new customer", 3);
        
        for(int i = 0; i < 1; i+=0)
        {
            if(newCustomer == null)
                break;
            
            else if(newCustomer.equals(""))
            {
                JOptionPane.showMessageDialog(this, "Specify Name...", "message", 2);
                newCustomer = JOptionPane.showInputDialog(this, "Name", "new customer", 3);
            }
            
            else
            {
                response = JOptionPane.showConfirmDialog(this, "Are You Sure That You Wanna Insert New Customer" +
                                                            "\n Set Name: (" + newCustomer + ")" , "new customer", 0);
                if(response == JOptionPane.YES_OPTION)
                {
                    id = mm.getNewIDFromDatabase2("icsAccounting", "icsAccounting", "select id from customers", "id");
                    
                    mm.insertOrUpdate("icsAccounting", "icsAccounting", "insert into customers (id, name) values (" + id + ", '" + newCustomer + "')",
                            "Customer Inserted Successfully...");
                    
                    mm.refrichTable(custTable);
                    displayCustomers();
                    
                    break;
                }
                else
                    break;
            }
        }
    }//GEN-LAST:event_newCustomerBTNActionPerformed

    private void deleteBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBTNActionPerformed
        // TODO add your handling code here:
        if(transSRow() < 0)
            JOptionPane.showMessageDialog(this, "Select Transaction..", "message", 2);
        
        else {
            response = JOptionPane.showConfirmDialog(this, "Are You Sure That You Want Delete this Record With id => (" +
                    mm.getValueAt(transactionsTable, 0) + ")", "message", 0);
            
            if(response == JOptionPane.YES_OPTION)
            {
                mm.deletRecord("icsAccounting", "icsAccounting", "delete from transactions where code = " + mm.getValueAt(transactionsTable, 0), this);
                
                mm.displayMessageDialog("Transaction Deleted Successfully...");
                
                mm.refrichTable(transactionsTable);
                displayTransactions();
                
                getTotalCustomerCreditAndUpdateCreditValue(Integer.parseInt(custTable.getValueAt(custSRow(), 0).toString()));
                
                TotalTreasuryMoney();
            }
        }
    }//GEN-LAST:event_deleteBTNActionPerformed

    private void custFilterTXTKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_custFilterTXTKeyReleased
        // TODO add your handling code here:
        try
        {
            mm.filter(custTable, custFilterTXT.getText().toLowerCase());
        }
        catch (PatternSyntaxException pse)
        {
            JOptionPane.showMessageDialog(this, pse, "Error", 0);
            mm.refrichTable(transactionsTable);
        }
    }//GEN-LAST:event_custFilterTXTKeyReleased

    private void transactionFilterTXTKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_transactionFilterTXTKeyReleased
        // TODO add your handling code here:
        try
        {
            mm.filter(transactionsTable, transactionFilterTXT.getText().toLowerCase());
        }
        catch (PatternSyntaxException pse)
        {
            JOptionPane.showMessageDialog(this, pse, "Error", 0);
        }
    }//GEN-LAST:event_transactionFilterTXTKeyReleased

    private void custFilterTXTFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_custFilterTXTFocusGained
        // TODO add your handling code here:
        mm.focusGained(custFilterTXT, "Search");
    }//GEN-LAST:event_custFilterTXTFocusGained

    private void custFilterTXTFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_custFilterTXTFocusLost
        // TODO add your handling code here:
        mm.focusLost(custFilterTXT, "Search");
    }//GEN-LAST:event_custFilterTXTFocusLost

    private void transactionFilterTXTFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_transactionFilterTXTFocusGained
        // TODO add your handling code here:
        mm.focusGained(transactionFilterTXT, "Search");
    }//GEN-LAST:event_transactionFilterTXTFocusGained

    private void transactionFilterTXTFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_transactionFilterTXTFocusLost
        // TODO add your handling code here:
        mm.focusLost(transactionFilterTXT, "Search");
    }//GEN-LAST:event_transactionFilterTXTFocusLost

    public void runReport(int paramValue, String param2Value) {
        //Start Code
        HashMap a = new HashMap();
        
        a.put("param", paramValue);
        a.put("param2", param2Value);
        a.put("param3", jDateChooser1.getDate());
        
        try
        {
            mm.con = mm.getConnection("icsAccounting", "icsAccounting");
            
            String reportPath = reportPathTXT.getText();
            
            JasperReport jr = JasperCompileManager.compileReport(reportPath);
            JasperPrint jp = JasperFillManager.fillReport(jr, a, mm.con);
            JasperViewer.viewReport(jp, false);
            
            mm.con.close();
        }
        catch (SQLException ex)
        {
            mm.closeConnection(4);
            JOptionPane.showMessageDialog(null, ex, "Error", 2);
            
        }
        catch (JRException ex)
        {
            mm.closeConnection(4);
            JOptionPane.showMessageDialog(null, "Wrong Location OR File Not Found..", "Error", 0);
        }
    }
    
    JFileChooser file = new JFileChooser();
    FileNameExtensionFilter filter = new FileNameExtensionFilter("jrxml", "jrxml");;
    private void reportPathTXTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reportPathTXTMouseClicked
        // TODO add your handling code here:
        file.addChoosableFileFilter(filter);

        response = file.showSaveDialog(null);

        if(response == JFileChooser.APPROVE_OPTION)
            reportPathTXT.setText(file.getSelectedFile().getAbsolutePath());
    }//GEN-LAST:event_reportPathTXTMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        try
        {
            runReport(Integer.parseInt(custTable.getValueAt(custSRow(), 0).toString()), custTable.getValueAt(custSRow(), 1).toString());
        }
        catch (ArrayIndexOutOfBoundsException ex)
        {
            runReport(0, custTable.getValueAt(0, 1).toString());
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void deleteCustomerBTN1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteCustomerBTN1ActionPerformed
        // TODO add your handling code here:
        if(custTable.getSelectedRow() < 0)
            JOptionPane.showMessageDialog(this, "Select Customer.", "message", 2);
        else
        {
            mm.deletRecord("icsAccounting", "icsAccounting", "delete from customers where id = " +
                    mm.getValueAt(custTable, 0), this);
            
            mm.refrichTable(transactionsTable);
            mm.refrichTable(custTable);
            displayCustomers();
        }
    }//GEN-LAST:event_deleteCustomerBTN1ActionPerformed

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
            java.util.logging.Logger.getLogger(AccountingFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AccountingFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AccountingFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AccountingFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AccountingFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel TotalTreasuryMoneyLabel;
    private javax.swing.JTextField custFilterTXT;
    private javax.swing.JPanel custPanel;
    private javax.swing.JTable custTable;
    private javax.swing.JButton deleteBTN;
    private javax.swing.JButton deleteCustomerBTN1;
    private javax.swing.JButton insertTransactionBTN;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton newCustomerBTN;
    private javax.swing.JTextField reportPathTXT;
    private javax.swing.JPanel transPanel;
    private javax.swing.JTextField transactionFilterTXT;
    private javax.swing.JTable transactionsTable;
    private javax.swing.JButton updateCustomerBTN;
    private javax.swing.JButton updateTransactionBTN;
    // End of variables declaration//GEN-END:variables
}
