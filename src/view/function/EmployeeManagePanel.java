package view.function;

import control.employee.EmployeeManageControllerInterface;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Iterator;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import model.employee.EmployeeDataStorage;
import model.employee.EmployeeDataStorageInterface;
import model.employee.shift.EmployeeShiftDataStorage;
import model.employee.shift.EmployeeShiftDataStorageInterface;
import model.employee.shift.EmployeeShiftModelInterface;
import static util.swing.UIControl.setDefaultTableHeader;
import util.swing.checkcombobox.CheckableItem;

public class EmployeeManagePanel extends javax.swing.JPanel implements ActionListener {

    private volatile static EmployeeManagePanel uniqueInstance;

    private EmployeeDataStorageInterface model;
    private EmployeeManageControllerInterface controller;

    private CheckableItem[] defaultCheckedModel;

    private EmployeeManagePanel(EmployeeManageControllerInterface controller) {
        initComponents();

        this.model = EmployeeDataStorage.getInstance();
        this.controller = controller;

        createView();
        createControl();
    }
    
    public static EmployeeManagePanel getInstance(EmployeeManageControllerInterface controller) {
        if (uniqueInstance == null) {
            synchronized (EmployeeManagePanel.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new EmployeeManagePanel(controller);
                }
            }
        }
        return uniqueInstance;
    }

    private void createView() {
        btnGroupGender.setSelected(rbtGenderMale.getModel(), true);
        loadDefaultShiftData();
        setUIInputEditable(false);
        setDefaultTableHeader(tableInfo);
    }

    private void createControl() {
        btnAdd.addActionListener(this);
        btnModify.addActionListener(this);
        btnMore.addActionListener(this);
        mnImport.addActionListener(this);
        mnExport.addActionListener(this);
    }

    private void loadDefaultShiftData() {
        EmployeeShiftDataStorageInterface employeeShiftDataStorage = EmployeeShiftDataStorage.getInstance();

        Iterator<EmployeeShiftModelInterface> iterator = employeeShiftDataStorage.createIterator();

        int size = employeeShiftDataStorage.getSize();

        defaultCheckedModel = new CheckableItem[size];
        int i = 0;

        while (iterator.hasNext()) {
            defaultCheckedModel[i++] = new CheckableItem(iterator.next().getShiftName(), false);
        }

        combShift.setModel(new DefaultComboBoxModel<>(defaultCheckedModel));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGroupGender = new javax.swing.ButtonGroup();
        popupBtnMore = new javax.swing.JPopupMenu();
        mnImport = new javax.swing.JMenuItem();
        mnExport = new javax.swing.JMenuItem();
        scrollpane = new javax.swing.JScrollPane();
        tableInfo = new javax.swing.JTable();
        panelOpBtn = new javax.swing.JPanel();
        btnAdd = new javax.swing.JButton();
        btnModify = new javax.swing.JButton();
        btnMore = new javax.swing.JButton();
        label_searchEmp = new javax.swing.JLabel();
        textfSearchName = new javax.swing.JTextField();
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
        dateChooserEndDate = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        btnSearchClear = new javax.swing.JButton();

        mnImport.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        mnImport.setText("Import");
        popupBtnMore.add(mnImport);

        mnExport.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        mnExport.setText("Export");
        popupBtnMore.add(mnExport);

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

        panelOpBtn.setBackground(new java.awt.Color(255, 255, 255));
        panelOpBtn.setLayout(new java.awt.GridLayout(1, 0, 10, 0));

        btnAdd.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnAdd.setForeground(new java.awt.Color(51, 51, 51));
        btnAdd.setText("Add");
        btnAdd.setFocusPainted(false);
        btnAdd.setPreferredSize(new java.awt.Dimension(115, 40));
        panelOpBtn.add(btnAdd);

        btnModify.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnModify.setForeground(new java.awt.Color(51, 51, 51));
        btnModify.setText("Modify");
        btnModify.setFocusPainted(false);
        btnModify.setPreferredSize(new java.awt.Dimension(115, 40));
        panelOpBtn.add(btnModify);

        btnMore.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnMore.setForeground(new java.awt.Color(51, 51, 51));
        btnMore.setText("More ▼");
        btnMore.setFocusPainted(false);
        btnMore.setPreferredSize(new java.awt.Dimension(115, 40));
        panelOpBtn.add(btnMore);

        label_searchEmp.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_searchEmp.setText("Search name:");

        textfSearchName.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        textfSearchName.setPreferredSize(new java.awt.Dimension(190, 30));

        panelInfo.setBackground(new java.awt.Color(255, 255, 255));
        panelInfo.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Employee Information", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 2, 14), new java.awt.Color(153, 153, 153))); // NOI18N

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
        btnGroupGender.add(rbtGenderMale);
        rbtGenderMale.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        rbtGenderMale.setText("Male");

        rbtGenderFemale.setBackground(new java.awt.Color(255, 255, 255));
        btnGroupGender.add(rbtGenderFemale);
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

        combShift.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        dateChooserEndDate.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

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
                .addGap(112, 112, 112)
                .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelInfoLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(label_gender, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
                    .addComponent(dateChooserEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26))
        );
        panelInfoLayout.setVerticalGroup(
            panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInfoLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelInfoLayout.createSequentialGroup()
                            .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(dateChooserBirthday, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lable_birthDay, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(dateChooserStartDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lable_startDate, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(dateChooserEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(combShift, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lable_shift)))
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
                                .addComponent(combStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );

        btnSearchClear.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnSearchClear.setForeground(new java.awt.Color(51, 51, 51));
        btnSearchClear.setText("Clear");
        btnSearchClear.setFocusPainted(false);
        btnSearchClear.setPreferredSize(new java.awt.Dimension(89, 29));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(scrollpane, javax.swing.GroupLayout.DEFAULT_SIZE, 1216, Short.MAX_VALUE)
                    .addComponent(panelInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(label_searchEmp)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(textfSearchName, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSearchClear, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(panelOpBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(panelOpBtn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(textfSearchName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSearchClear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(label_searchEmp, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(scrollpane, javax.swing.GroupLayout.DEFAULT_SIZE, 544, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    public void setUIInputEditable(boolean editable) {
        textfEmployeeID.setEditable(false);
        textfName.setEditable(editable);
        textfPhoneNum.setEditable(editable);
        textfPersonalID.setEditable(editable);
        textfEmail.setEditable(editable);
        rbtGenderFemale.setEnabled(editable);
        rbtGenderMale.setEnabled(editable);
        combPosition.setEnabled(editable);
        combStatus.setEnabled(editable);
        dateChooserBirthday.setEnabled(editable);
        dateChooserStartDate.setEnabled(editable);
        dateChooserEndDate.setEnabled(editable);
        combShift.setEnabled(editable);
    }

    public void resetUIInput() {
        textfName.setText(null);
        textfPhoneNum.setText(null);
        textfPersonalID.setText(null);
        textfEmail.setText(null);
        btnGroupGender.setSelected(rbtGenderMale.getModel(), true);
        combPosition.setSelectedIndex(0);
        combStatus.setSelectedIndex(0);
        dateChooserBirthday.setDate(null);
        dateChooserStartDate.setDate(new Date());
        dateChooserEndDate.setDate(null);
        combShift.setModel(new DefaultComboBoxModel<>(defaultCheckedModel));
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();

        if (source == btnAdd) {

        } else if (source == btnModify) {

        } else if (source == btnMore) {
            popupBtnMore.show(btnMore, 0, btnMore.getY() + btnMore.getHeight());
        } else if (source == btnSearchClear) {

        } else if (source == mnImport) {

        } else if (source == mnExport) {

        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.ButtonGroup btnGroupGender;
    private javax.swing.JButton btnModify;
    private javax.swing.JButton btnMore;
    private javax.swing.JButton btnSearchClear;
    private javax.swing.JComboBox<String> combPosition;
    private util.swing.checkcombobox.CheckedComboBox combShift;
    private javax.swing.JComboBox<String> combStatus;
    private com.toedter.calendar.JDateChooser dateChooserBirthday;
    private com.toedter.calendar.JDateChooser dateChooserEndDate;
    private com.toedter.calendar.JDateChooser dateChooserStartDate;
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
    private javax.swing.JMenuItem mnExport;
    private javax.swing.JMenuItem mnImport;
    private javax.swing.JPanel panelInfo;
    private javax.swing.JPanel panelOpBtn;
    private javax.swing.JPopupMenu popupBtnMore;
    private javax.swing.JRadioButton rbtGenderFemale;
    private javax.swing.JRadioButton rbtGenderMale;
    private javax.swing.JScrollPane scrollpane;
    private javax.swing.JTable tableInfo;
    private javax.swing.JTextField textfEmail;
    private javax.swing.JTextField textfEmployeeID;
    private javax.swing.JTextField textfName;
    private javax.swing.JTextField textfPersonalID;
    private javax.swing.JTextField textfPhoneNum;
    private javax.swing.JTextField textfSearchName;
    // End of variables declaration//GEN-END:variables

}
