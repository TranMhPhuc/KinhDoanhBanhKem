/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.function;

import java.awt.Font;
import java.util.Date;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import view.UserInterface;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import util.swing.checkcombobox.CheckableItem;

/**
 *
 * @author DELL
 */
public class EmployeeManagePanel extends javax.swing.JPanel implements UserInterface {

    /**
     * Creates new form ManageEmployeePanel
     */
    CheckableItem[] defaultCheckedModel = {
            new CheckableItem("Sáng", true),
            new CheckableItem("Trưa", false),
            new CheckableItem("Chiều", false),
            new CheckableItem("Tối", false),
        };
    public EmployeeManagePanel() {
        initComponents();

        // Sample code for check combobox
        
        
        combShift.setModel(new DefaultComboBoxModel<>(defaultCheckedModel));
        buttonGroup_Gender.setSelected(rbtGenderMale.getModel(), true);
        setEditableForAll(false);
        setDefaultTableHeader();
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup_Gender = new javax.swing.ButtonGroup();
        scrollpane = new javax.swing.JScrollPane();
        tableInfo = new javax.swing.JTable();
        panelOpBtn = new javax.swing.JPanel();
        btnExport = new javax.swing.JButton();
        btnModify = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        label_searchEmp = new javax.swing.JLabel();
        textfSearch = new javax.swing.JTextField();
        panelInfo = new javax.swing.JPanel();
        label_emplID = new javax.swing.JLabel();
        label_namePro = new javax.swing.JLabel();
        label_personalID = new javax.swing.JLabel();
        label_mobile = new javax.swing.JLabel();
        textfName = new javax.swing.JTextField();
        textfPhoneNum = new javax.swing.JTextField();
        textfPersonalID = new javax.swing.JTextField();
        label_email = new javax.swing.JLabel();
        label_gender = new javax.swing.JLabel();
        rbtGenderMale = new javax.swing.JRadioButton();
        rbtGenderFemale = new javax.swing.JRadioButton();
        lable_status = new javax.swing.JLabel();
        combStatus = new javax.swing.JComboBox<>();
        lable_position = new javax.swing.JLabel();
        combPosition = new javax.swing.JComboBox<>();
        lable_startDate = new javax.swing.JLabel();
        lable_birthDay = new javax.swing.JLabel();
        lable_shift = new javax.swing.JLabel();
        textfEmployeeID = new javax.swing.JTextField();
        textfEmail = new javax.swing.JTextField();
        dateChooserStartDate = new com.toedter.calendar.JDateChooser();
        dateChooserBirthday = new com.toedter.calendar.JDateChooser();
        combShift = new util.swing.checkcombobox.CheckedComboBox();
        dateChooser_EndDate = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(new java.awt.Color(255, 255, 255));

        tableInfo.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        tableInfo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Mobile", "Birthday", "Email", "Gender", "Shift", "Position", "Start date", "Personal ID", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scrollpane.setViewportView(tableInfo);
        tableInfo.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
        if (tableInfo.getColumnModel().getColumnCount() > 0) {
            tableInfo.getColumnModel().getColumn(0).setResizable(false);
            tableInfo.getColumnModel().getColumn(0).setPreferredWidth(5);
            tableInfo.getColumnModel().getColumn(1).setResizable(false);
            tableInfo.getColumnModel().getColumn(1).setPreferredWidth(150);
            tableInfo.getColumnModel().getColumn(2).setResizable(false);
            tableInfo.getColumnModel().getColumn(2).setPreferredWidth(60);
            tableInfo.getColumnModel().getColumn(3).setResizable(false);
            tableInfo.getColumnModel().getColumn(3).setPreferredWidth(50);
            tableInfo.getColumnModel().getColumn(4).setResizable(false);
            tableInfo.getColumnModel().getColumn(4).setPreferredWidth(150);
            tableInfo.getColumnModel().getColumn(5).setResizable(false);
            tableInfo.getColumnModel().getColumn(5).setPreferredWidth(30);
            tableInfo.getColumnModel().getColumn(6).setResizable(false);
            tableInfo.getColumnModel().getColumn(7).setResizable(false);
            tableInfo.getColumnModel().getColumn(8).setResizable(false);
            tableInfo.getColumnModel().getColumn(8).setPreferredWidth(50);
            tableInfo.getColumnModel().getColumn(9).setResizable(false);
            tableInfo.getColumnModel().getColumn(10).setResizable(false);
            tableInfo.getColumnModel().getColumn(10).setPreferredWidth(50);
        }
        //table_empInfo.getTableHeader().setFont(new Font("Segoe UI",1,15));
        //DefaultTableCellRenderer DTCR = (DefaultTableCellRenderer) tableInfo.getTableHeader().getDefaultRenderer();
        //DTCR.setHorizontalAlignment(0);
        //DefaultTableModel dtm = (DefaultTableModel) tableInfo.getModel();
        //dtm.addRow(new Object[0]);
        //dtm.setValueAt(1, 0, 0);
        //dtm.setValueAt("Nguyễn Ngọc Minh Tú", 0, 1);
        //dtm.setValueAt("0338758008", 0, 2);
        //dtm.setValueAt("13/09/2000", 0, 3);
        //dtm.setValueAt("minhtu@gmail.com", 0, 4);
        //dtm.setValueAt("Male", 0, 5);
        //dtm.setValueAt("M, N, A, E", 0, 6);
        //dtm.setValueAt("Manager", 0, 7);
        //dtm.setValueAt("12/05/2021", 0, 8);
        //dtm.setValueAt("079200011633", 0, 9);
        //dtm.setValueAt("Active", 0, 10);

        //table_empInfo.setRowHeight(30);
        //DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        //center.setHorizontalAlignment(JLabel.CENTER);
        //table_empInfo.getColumnModel().getColumn(0).setCellRenderer(center);

        panelOpBtn.setBackground(new java.awt.Color(255, 255, 255));
        panelOpBtn.setLayout(new java.awt.GridLayout(1, 0, 50, 0));

        btnExport.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        btnExport.setText("Export");
        btnExport.setPreferredSize(new java.awt.Dimension(115, 40));
        panelOpBtn.add(btnExport);

        btnModify.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        btnModify.setText("Modify");
        btnModify.setPreferredSize(new java.awt.Dimension(115, 40));
        panelOpBtn.add(btnModify);

        btnAdd.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        btnAdd.setText("Add");
        btnAdd.setPreferredSize(new java.awt.Dimension(115, 40));
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        panelOpBtn.add(btnAdd);

        label_searchEmp.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_searchEmp.setText("Type name to search");

        textfSearch.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        textfSearch.setPreferredSize(new java.awt.Dimension(190, 30));
        textfSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textfSearchActionPerformed(evt);
            }
        });

        panelInfo.setBackground(new java.awt.Color(255, 255, 255));
        panelInfo.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 2, 14))); // NOI18N

        label_emplID.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_emplID.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        label_emplID.setText("Employee ID");

        label_namePro.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_namePro.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        label_namePro.setText("Name");

        label_personalID.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_personalID.setText("Personal ID");

        label_mobile.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_mobile.setText("Mobile");

        textfName.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        textfName.setPreferredSize(new java.awt.Dimension(160, 30));

        textfPhoneNum.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        textfPhoneNum.setPreferredSize(new java.awt.Dimension(160, 30));

        textfPersonalID.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        textfPersonalID.setPreferredSize(new java.awt.Dimension(160, 30));

        label_email.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_email.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        label_email.setText("Email");

        label_gender.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_gender.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        label_gender.setText("Gender");

        rbtGenderMale.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup_Gender.add(rbtGenderMale);
        rbtGenderMale.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        rbtGenderMale.setText("Male");

        rbtGenderFemale.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup_Gender.add(rbtGenderFemale);
        rbtGenderFemale.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        rbtGenderFemale.setText("Female");

        lable_status.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        lable_status.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lable_status.setText("Status");

        combStatus.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        combStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Active", "Non-active" }));
        combStatus.setPreferredSize(new java.awt.Dimension(160, 30));

        lable_position.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        lable_position.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lable_position.setText("Position");

        combPosition.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        combPosition.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Thu ngân", "Kế toán", "Thợ làm bánh" }));
        combPosition.setPreferredSize(new java.awt.Dimension(160, 30));

        lable_startDate.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        lable_startDate.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lable_startDate.setText("Start date");

        lable_birthDay.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        lable_birthDay.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lable_birthDay.setText("Birthday");

        lable_shift.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        lable_shift.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lable_shift.setText("Shift");

        textfEmployeeID.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        textfEmail.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        dateChooserStartDate.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        dateChooserBirthday.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        dateChooser_EndDate.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel1.setText("End date");

        javax.swing.GroupLayout panelInfoLayout = new javax.swing.GroupLayout(panelInfo);
        panelInfo.setLayout(panelInfoLayout);
        panelInfoLayout.setHorizontalGroup(
            panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInfoLayout.createSequentialGroup()
                .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label_personalID, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panelInfoLayout.createSequentialGroup()
                                .addGap(51, 51, 51)
                                .addComponent(label_namePro)))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelInfoLayout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(label_mobile)))
                    .addGroup(panelInfoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(label_emplID)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textfPersonalID, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(textfPhoneNum, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
                        .addComponent(textfName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(textfEmployeeID, javax.swing.GroupLayout.Alignment.TRAILING)))
                .addGap(94, 94, 94)
                .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelInfoLayout.createSequentialGroup()
                        .addComponent(label_gender, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(rbtGenderMale)
                        .addGap(45, 45, 45)
                        .addComponent(rbtGenderFemale)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panelInfoLayout.createSequentialGroup()
                        .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelInfoLayout.createSequentialGroup()
                                .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(label_email, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lable_status, javax.swing.GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(textfEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
                                    .addGroup(panelInfoLayout.createSequentialGroup()
                                        .addComponent(combStatus, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(75, 75, 75))))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelInfoLayout.createSequentialGroup()
                                .addComponent(lable_position, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(combPosition, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 176, Short.MAX_VALUE)))
                .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lable_startDate, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lable_birthDay, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lable_shift, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(dateChooserBirthday, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dateChooserStartDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(combShift, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                    .addComponent(dateChooser_EndDate, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26))
        );
        panelInfoLayout.setVerticalGroup(
            panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInfoLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelInfoLayout.createSequentialGroup()
                        .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(textfEmployeeID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label_emplID))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(textfName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label_namePro))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(textfPhoneNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label_mobile, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(textfPersonalID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label_personalID)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelInfoLayout.createSequentialGroup()
                        .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label_email, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(textfEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rbtGenderMale)
                            .addComponent(rbtGenderFemale)
                            .addComponent(label_gender))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lable_position)
                            .addComponent(combPosition, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(16, 16, 16)
                        .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lable_status)
                            .addComponent(combStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelInfoLayout.createSequentialGroup()
                        .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(dateChooserStartDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lable_startDate, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dateChooserBirthday, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lable_birthDay, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(dateChooser_EndDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(combShift, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lable_shift))))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(label_searchEmp)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(textfSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(panelOpBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(scrollpane, javax.swing.GroupLayout.DEFAULT_SIZE, 1216, Short.MAX_VALUE)
                    .addComponent(panelInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelOpBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(textfSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(label_searchEmp, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scrollpane, javax.swing.GroupLayout.DEFAULT_SIZE, 547, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    
    public void setEditableForAll(boolean editable){  
        textfEmployeeID.setEditable(false);
        textfName.setEditable(editable);
        textfPersonalID.setEditable(editable);
        textfPhoneNum.setEditable(editable);
        textfEmail.setEditable(editable);
        rbtGenderFemale.setEnabled(editable);
        rbtGenderMale.setEnabled(editable);
        combPosition.setEnabled(editable);
        combShift.setEnabled(editable);
        combStatus.setEnabled(editable);
        dateChooserBirthday.setEnabled(editable);
        dateChooserStartDate.setEnabled(editable);
        dateChooser_EndDate.setEnabled(editable);
    }
    
    public void clearAll(){
        textfName.setText(null);
        textfPersonalID.setText(null);
        textfPhoneNum.setText(null);
        textfEmail.setText(null);
        buttonGroup_Gender.setSelected(rbtGenderMale.getModel(), true);
        combPosition.setSelectedIndex(0);
        combStatus.setSelectedIndex(0);
        combShift.setModel(new DefaultComboBoxModel<>(defaultCheckedModel));
        dateChooserBirthday.setDate(null);
        dateChooserStartDate.setDate(new Date());
        dateChooser_EndDate.setDate(null);
        
    }
    
    private void setDefaultTableHeader() {
            DefaultTableCellRenderer DTCR;
            tableInfo.setRowHeight(30);
            DTCR = (DefaultTableCellRenderer) tableInfo.getTableHeader().getDefaultRenderer();
            DTCR.setHorizontalAlignment(0);
            tableInfo.getTableHeader().setFont(new Font("Segoe UI", 1, 15));

      //  setHorizontalAlignmentForColumn(tableInfo.getColumnModel().getColumn(0), JLabel.CENTER);
    }
    private void setHorizontalAlignmentForColumn(TableColumn column, int Alignment) {
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(Alignment);
        column.setCellRenderer(center);
    }
    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAddActionPerformed

    private void textfSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textfSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textfSearchActionPerformed

    @Override
    public void showErrorMessage(String message) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void showInformationMessage(String message) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void showWarningMessage(String message) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnExport;
    private javax.swing.JButton btnModify;
    private javax.swing.ButtonGroup buttonGroup_Gender;
    private javax.swing.JComboBox<String> combPosition;
    private util.swing.checkcombobox.CheckedComboBox combShift;
    private javax.swing.JComboBox<String> combStatus;
    private com.toedter.calendar.JDateChooser dateChooserBirthday;
    private com.toedter.calendar.JDateChooser dateChooserStartDate;
    private com.toedter.calendar.JDateChooser dateChooser_EndDate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel label_email;
    private javax.swing.JLabel label_emplID;
    private javax.swing.JLabel label_gender;
    private javax.swing.JLabel label_mobile;
    private javax.swing.JLabel label_namePro;
    private javax.swing.JLabel label_personalID;
    private javax.swing.JLabel label_searchEmp;
    private javax.swing.JLabel lable_birthDay;
    private javax.swing.JLabel lable_position;
    private javax.swing.JLabel lable_shift;
    private javax.swing.JLabel lable_startDate;
    private javax.swing.JLabel lable_status;
    private javax.swing.JPanel panelInfo;
    private javax.swing.JPanel panelOpBtn;
    private javax.swing.JRadioButton rbtGenderFemale;
    private javax.swing.JRadioButton rbtGenderMale;
    private javax.swing.JScrollPane scrollpane;
    private javax.swing.JTable tableInfo;
    private javax.swing.JTextField textfEmail;
    private javax.swing.JTextField textfEmployeeID;
    private javax.swing.JTextField textfName;
    private javax.swing.JTextField textfPersonalID;
    private javax.swing.JTextField textfPhoneNum;
    private javax.swing.JTextField textfSearch;
    // End of variables declaration//GEN-END:variables
}
