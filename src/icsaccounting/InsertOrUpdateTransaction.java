package icsaccounting;

import com.toedter.calendar.JDateChooser;
import mymethods.MyMethods;
import java.awt.Color;
import java.awt.event.KeyEvent;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class InsertOrUpdateTransaction extends java.awt.Dialog {

    public InsertOrUpdateTransaction(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        mm.initDateEditor(jDateChooser1);
    }
    
    MyMethods mm = new MyMethods();

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        isItUpdateCB = new javax.swing.JCheckBox();
        jPanel1 = new javax.swing.JPanel();
        idLabel = new javax.swing.JLabel();
        descriptionTXT = new javax.swing.JTextField();
        creditTXT = new javax.swing.JTextField();
        debtTXT = new javax.swing.JTextField();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        submitBTN = new javax.swing.JButton();
        idLabel1 = new javax.swing.JLabel();

        isItUpdateCB.setText("isItUpdate");

        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        descriptionTXT.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        descriptionTXT.setForeground(new java.awt.Color(153, 153, 153));
        descriptionTXT.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        descriptionTXT.setText("Description");
        descriptionTXT.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                descriptionTXTFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                descriptionTXTFocusLost(evt);
            }
        });
        descriptionTXT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                descriptionTXTKeyPressed(evt);
            }
        });

        creditTXT.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        creditTXT.setForeground(new java.awt.Color(153, 153, 153));
        creditTXT.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        creditTXT.setText("Credit");
        creditTXT.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                creditTXTFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                creditTXTFocusLost(evt);
            }
        });
        creditTXT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                creditTXTKeyPressed(evt);
            }
        });

        debtTXT.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        debtTXT.setForeground(new java.awt.Color(153, 153, 153));
        debtTXT.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        debtTXT.setText("Debt");
        debtTXT.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                debtTXTFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                debtTXTFocusLost(evt);
            }
        });
        debtTXT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                debtTXTKeyPressed(evt);
            }
        });

        jDateChooser1.setDateFormatString("yyyy-MM-dd");
        jDateChooser1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        submitBTN.setText("Submit");
        submitBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitBTNActionPerformed(evt);
            }
        });
        submitBTN.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                submitBTNKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(debtTXT)
                    .addComponent(creditTXT)
                    .addComponent(descriptionTXT)
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(submitBTN))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(idLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(idLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(idLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(idLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(descriptionTXT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(creditTXT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(debtTXT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(submitBTN)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        dispose();
        
        emptyTXT();
    }//GEN-LAST:event_closeDialog

    private void descriptionTXTFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_descriptionTXTFocusGained
        // TODO add your handling code here:
        mm.focusGained(descriptionTXT, "Description");
    }//GEN-LAST:event_descriptionTXTFocusGained

    private void descriptionTXTFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_descriptionTXTFocusLost
        // TODO add your handling code here:
        mm.focusLost(descriptionTXT, "Description");
    }//GEN-LAST:event_descriptionTXTFocusLost

    private void creditTXTFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_creditTXTFocusGained
        // TODO add your handling code here:
        mm.focusGained(creditTXT, "Credit");
    }//GEN-LAST:event_creditTXTFocusGained

    private void creditTXTFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_creditTXTFocusLost
        // TODO add your handling code here:
        mm.focusLost(creditTXT, "Credit");
    }//GEN-LAST:event_creditTXTFocusLost

    private void debtTXTFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_debtTXTFocusGained
        // TODO add your handling code here:
        mm.focusGained(debtTXT, "Debt");
    }//GEN-LAST:event_debtTXTFocusGained

    private void debtTXTFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_debtTXTFocusLost
        // TODO add your handling code here:
        mm.focusLost(debtTXT, "Debt");
    }//GEN-LAST:event_debtTXTFocusLost

    int code;
    String values;
    int response;
    void insertOrUpdateMethod()
    {
        values = null;
        
        if(creditTXT.getText().equals("Credit"))
            creditTXT.setText("0");
        
        if(debtTXT.getText().equals("Debt") || debtTXT.getText().equals(""))
            debtTXT.setText("0");
        
        try
        {
            if(Integer.parseInt(creditTXT.getText()) == 0 && Integer.parseInt(debtTXT.getText()) == 0)
                    JOptionPane.showMessageDialog(this, "Apply value in credit or debt...", "message", 2);
            else
            {
                if(isItUpdateCB.isSelected())
                {
                    values = confirmMessage("Update Transaction no (" + idLabel.getText() + ") set \n\n");
                    
                    response = JOptionPane.showConfirmDialog(this, values);
                    
                    if(response == JOptionPane.YES_OPTION)
                    {
                        mm.insertOrUpdate("icsAccounting", "icsAccounting", "update transactions set description = '" +
                                descriptionTXT.getText() + "', credit = " +
                                creditTXT.getText() + ", debt = " +
                                debtTXT.getText() + ", datee = (to_date('" +
                                ((JTextField)jDateChooser1.getDateEditor().getUiComponent()).getText() + "','yyyy-MM-dd')) where code = " +
                                idLabel.getText(), "Transaction Updated Successfully...");
                    }
                    emptyTXT();
                    
                    this.closeDialog(null);
                }
                else
                {
                    values = confirmMessage("Insert This Transaction\n\n");
                    
                    response = JOptionPane.showConfirmDialog(this, values);

                    if(response == JOptionPane.YES_OPTION)
                    {
                        code = mm.getNewIDFromDatabase2("icsAccounting", "icsAccounting", "select code from transactions", "code");
                        
                        mm.insertOrUpdate("icsAccounting", "icsAccounting", "insert into transactions (id, description, datee, credit, debt, code) values (" + 
                                idLabel.getText() + ", '" +
                                descriptionTXT.getText() + "', (to_date('" +
                                ((JTextField)jDateChooser1.getDateEditor().getUiComponent()).getText() + "','yyyy-MM-dd')), " +
                                creditTXT.getText() + ", " +
                                debtTXT.getText() + ", " +
                                code + ")" ,
                                "Transaction Inserted Successfully...");
                    }
                    emptyTXT();
                }
            }
        }
        catch (NumberFormatException ex)
        {
            if(!isItUpdateCB.isSelected())
                emptyTXT();
            JOptionPane.showMessageDialog(this, ex, "error", 0);
        }
    }
    
    String confirmMessage(String a)
    {
        String values = "Are You Sure That You Wanna " + a +
                                            "Date: (" + ((JTextField)jDateChooser1.getDateEditor().getUiComponent()).getText() + ")" +
                                            "\nDescription: (" + descriptionTXT.getText() + ")" +
                                            "\nCredit: (" + creditTXT.getText() + ")" +
                                            "\nDebt: (" + debtTXT.getText() + ")";;
        return values;
    }
    
    private void submitBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitBTNActionPerformed
        // TODO add your handling code here:
        insertOrUpdateMethod();
    }//GEN-LAST:event_submitBTNActionPerformed

    private void descriptionTXTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_descriptionTXTKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
            creditTXT.requestFocus();
    }//GEN-LAST:event_descriptionTXTKeyPressed

    private void creditTXTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_creditTXTKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
            debtTXT.requestFocus();
    }//GEN-LAST:event_creditTXTKeyPressed

    private void debtTXTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_debtTXTKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
            submitBTN.requestFocus();
    }//GEN-LAST:event_debtTXTKeyPressed

    private void submitBTNKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_submitBTNKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
            insertOrUpdateMethod();
    }//GEN-LAST:event_submitBTNKeyPressed

    void emptyTXT()
    {
        descriptionTXT.requestFocus();
        
        isItUpdateCB.setSelected(false);
        
        mm.initDateEditor(jDateChooser1);
        
        debtTXT.setText("Debt");
        debtTXT.setForeground(new Color(153, 153, 153));
        
        creditTXT.setText("Credit");
        creditTXT.setForeground(new Color(153, 153, 153));
        
        descriptionTXT.setText("Description");
        descriptionTXT.setForeground(new Color(153, 153, 153));
    }
    
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                InsertOrUpdateTransaction dialog = new InsertOrUpdateTransaction(new java.awt.Frame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField creditTXT;
    private javax.swing.JTextField debtTXT;
    private javax.swing.JTextField descriptionTXT;
    private javax.swing.JLabel idLabel;
    private javax.swing.JLabel idLabel1;
    private javax.swing.JCheckBox isItUpdateCB;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton submitBTN;
    // End of variables declaration//GEN-END:variables

    public JLabel getIdLabel() {
        return idLabel;
    }

    public JCheckBox getjCheckBox1() {
        return isItUpdateCB;
    }

    public JTextField getCreditTXT() {
        return creditTXT;
    }

    public JTextField getDebtTXT() {
        return debtTXT;
    }

    public JTextField getDescriptionTXT() {
        return descriptionTXT;
    }

    public JDateChooser getjDateChooser1() {
        return jDateChooser1;
    }

    public JLabel getIdLabel1() {
        return idLabel1;
    }
}
