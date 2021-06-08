package view.function.employee;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Iterator;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import model.employee.shift.EmployeeShiftDataStorage;
import model.employee.shift.EmployeeShiftDataStorageInterface;
import model.employee.shift.EmployeeShiftModelInterface;
import static util.swing.UIControl.setDefaultTableHeader;
import util.swing.checkcombobox.CheckableItem;
import control.employee.EmployeeControllerInterface;
import java.awt.CardLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import model.employee.EmployeeManageModel;
import model.employee.EmployeeManageModelInterface;
import model.employee.EmployeeModelInterface;
import model.employee.position.EmployeePositionModelInterface;
import view.MessageShowing;
import view.main.MainFrame;

public class EmployeeManagePanel extends javax.swing.JPanel implements ActionListener,
        MessageShowing, InsertedEmployeeObserver, ModifiedEmployeeObserver {

    enum EditState {
        ADD,
        MODIFY,
    }

    public static final int EMPLOYEE_ID_COLUMN_INDEX = 0;

    private volatile static EmployeeManagePanel uniqueInstance;

    private EmployeeManageModelInterface model;
    private EmployeeControllerInterface controller;

    private DefaultTableModel tableEmployeeModel;

    private EditState editState;

    private CheckableItem[] shiftModel;

    private EmployeeManagePanel(EmployeeControllerInterface controller) {
        if (controller == null) {
            throw new IllegalArgumentException("Controller object is null.");
        }
        initComponents();

        this.model = EmployeeManageModel.getInstance();
        this.model.registerInsertedEmployeeObserver(this);
        this.model.registerModifiedEmployeeObserver(this);

        this.controller = controller;

        this.tableEmployeeModel = (DefaultTableModel) tableEmployee.getModel();

        this.editState = null;

        createView();
        createControl();
    }

    public static EmployeeManagePanel getInstance(EmployeeControllerInterface controller) {
        if (uniqueInstance == null) {
            synchronized (EmployeeManagePanel.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new EmployeeManagePanel(controller);
                }
            }
        }
        return uniqueInstance;
    }

    public static EmployeeManagePanel getInstance() {
        if (uniqueInstance == null) {
            throw new NullPointerException();
        }
        return uniqueInstance;
    }

    private void createView() {
        btnGroupGender.setSelected(rbtGenderMale.getModel(), true);
        textfEmployeeID.setEditable(false);
        setEmployeeInputEditable(false);
        setDefaultTableHeader(tableEmployee);
        resetEmployeeList();
        loadEmployeeShiftInput();
        loadEmployeePositionInput();
        resetEmployeeInput();
    }

    private void createControl() {
        btnSearchClear.addActionListener(this);
        btnAdd.addActionListener(this);
        btnModify.addActionListener(this);
        btnMore.addActionListener(this);
        mnImport.addActionListener(this);
        mnExport.addActionListener(this);
        btnOK.addActionListener(this);
        btnCancel.addActionListener(this);
        btnReset.addActionListener(this);

        tableEmployee.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                controller.requestShowEmployeeInfo();
            }
        });

        textfSearchName.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent event) {
                String searchText = textfSearchName.getText().trim();
                showEmployeeList(controller.getEmployeeBySearchName(searchText));
            }

            @Override
            public void removeUpdate(DocumentEvent event) {
                String searchText = textfSearchName.getText().trim();
                if (searchText.isEmpty()) {
                    resetEmployeeList();
                } else {
                    showEmployeeList(controller.getEmployeeBySearchName(searchText));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent event) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
    }

    public void showEmployeeInfo(EmployeeModelInterface employee) {
        if (editState == EditState.ADD) {
            return;
        }

        this.textfEmployeeID.setText(employee.getEmployeeIDText());
        this.textfEmployeeName.setText(employee.getName());
        this.textfPhoneNum.setText(employee.getPhoneNum());
        this.textfPersonalID.setText(employee.getPersonalID());
        this.textfEmployeeEmail.setText(employee.getEmail());

        if (employee.getGender()) {
            this.btnGroupGender.setSelected(rbtGenderMale.getModel(), true);
        } else {
            this.btnGroupGender.setSelected(rbtGenderFemale.getModel(), true);
        }

        int positionIndex = this.model.getEmployeePositionIndex(employee);
        this.combEmployeePosition.setSelectedIndex(positionIndex);

        boolean status = employee.getStatus();
        if (status) {
            this.combEmployeeStatus.setSelectedIndex(0);
        } else {
            this.combEmployeeStatus.setSelectedIndex(1);
        }

        dateChooserBirthday.setDate(Date.from(employee.getBirthday().toInstant()));
        dateChooserStartDate.setDate(Date.from(employee.getStartDate().toInstant()));
        dateChooserEndDate.setDate(Date.from(employee.getEndDate().toInstant()));

        for (int i = 0; i < shiftModel.length; i++) {
            shiftModel[i].setSelected(false);
        }

        List<EmployeeShiftModelInterface> shifts = employee.getShift();
        for (EmployeeShiftModelInterface shift : shifts) {
            int id = this.model.getShiftIndex(shift);
            shiftModel[id].setSelected(true);
        }
    }

    public void loadEmployeePositionInput() {
        Iterator<EmployeePositionModelInterface> iterator = this.model.getAllPositionData();
        this.combEmployeePosition.removeAllItems();
        while (iterator.hasNext()) {
            EmployeePositionModelInterface position = iterator.next();
            combEmployeePosition.addItem(position.getName());
        }
        if (combEmployeePosition.getItemCount() != 0) {
            combEmployeePosition.setSelectedIndex(0);
        }
    }

    public void resetEmployeeList() {
        Iterator<EmployeeModelInterface> iterator = this.controller.getAllEmployeeData();
        showEmployeeList(iterator);
    }

    private void showEmployeeList(Iterator<EmployeeModelInterface> iterator) {
        clearTableEmployee();
        while (iterator.hasNext()) {
            addRowTableEmployee(iterator.next());
        }
    }

    public void clearTableEmployee() {
        this.tableEmployeeModel.setRowCount(0);
    }

    private void addRowTableEmployee(EmployeeModelInterface employee) {
        String shiftArray = "";
        List<EmployeeShiftModelInterface> shifts = employee.getShift();
        for (int i = 0; i < shifts.size() - 1; i++) {
            shiftArray += shifts.get(i).getName() + ", ";
        }
        shiftArray += shifts.get(shifts.size() - 1).getName();

        Object[] record = new Object[]{
            employee.getEmployeeIDText(),
            employee.getName(),
            employee.getGender() ? "Nam" : "Nữ",
            employee.getPersonalID(),
            employee.getEmail(),
            employee.getPhoneNum(),
            employee.getBirthday(),
            employee.getPosition().getName(),
            shiftArray,
            employee.getStatus() ? "Active" : "UnActive",
            employee.getStartDate(),
            employee.getEndDate()
        };
        this.tableEmployeeModel.addRow(record);
    }

    private void updateRowTableEmployee(EmployeeModelInterface employee) {
        String employeeIDText = employee.getEmployeeIDText();
        for (int i = 0; i < this.tableEmployeeModel.getRowCount(); i++) {
            String employeeIDInTable = (String) this.tableEmployeeModel.getValueAt(i, EMPLOYEE_ID_COLUMN_INDEX);
            if (employeeIDInTable.equals(employeeIDText)) {
                String shiftArray = "";
                List<EmployeeShiftModelInterface> shifts = employee.getShift();
                for (int k = 0; k < shifts.size() - 1; k++) {
                    shiftArray += shifts.get(k) + ", ";
                }
                shiftArray += shifts.get(shifts.size() - 1);

                Object[] record = new Object[]{
                    employee.getEmployeeIDText(),
                    employee.getName(),
                    employee.getGender() ? "Nam" : "Nữ",
                    employee.getPersonalID(),
                    employee.getEmail(),
                    employee.getPhoneNum(),
                    employee.getBirthday(),
                    employee.getPosition().getName(),
                    shiftArray,
                    employee.getStatus() ? "Active" : "UnActive",
                    employee.getStartDate(),
                    employee.getEndDate()
                };
                for (int j = 0; j < record.length; j++) {
                    this.tableEmployeeModel.setValueAt(record[j], i, j);
                }
                break;
            }
        }
    }

    private void removeRowTableEmployee(EmployeeModelInterface employee) {
        if (this.controller.deleteIngredientInSearchList(employee)) {
            String employeeIDText = employee.getEmployeeIDText();
            for (int i = 0; i < this.tableEmployeeModel.getRowCount(); i++) {
                String employeeIDInTable = (String) this.tableEmployeeModel.getValueAt(i, EMPLOYEE_ID_COLUMN_INDEX);
                if (employeeIDInTable.equals(employeeIDText)) {
                    this.tableEmployeeModel.removeRow(i);
                    break;
                }
            }
        }
    }

    public void setTextfSearch(String text) {
        this.textfSearchName.setText(text);
    }

    public String getTextSearch() {
        return this.textfSearchName.getText().trim();
    }

    public int getSelectdRow() {
        return this.tableEmployee.getSelectedRow();
    }

    public void resetEmployeeInput() {
        textfEmployeeName.setText("");
        textfPhoneNum.setText("");
        textfPersonalID.setText("");
        textfEmployeeEmail.setText("");
        
        btnGroupGender.setSelected(rbtGenderMale.getModel(), true);
        
        if (combEmployeePosition.getItemCount() != 0) {
            combEmployeePosition.setSelectedIndex(0);
        }
        combEmployeeStatus.setSelectedIndex(0);
        
        dateChooserBirthday.setDate(null);
        dateChooserStartDate.setDate(null);
        dateChooserEndDate.setDate(null);
        
        for (int i = 0; i < shiftModel.length; i++) {
            shiftModel[i].setSelected(false);
        }
    }

    private void loadEmployeeShiftInput() {
        EmployeeShiftDataStorageInterface employeeShiftDataStorage = EmployeeShiftDataStorage.getInstance();

        Iterator<EmployeeShiftModelInterface> iterator = employeeShiftDataStorage.createIterator();

        int size = employeeShiftDataStorage.getSize();

        shiftModel = new CheckableItem[size];
        int i = 0;

        while (iterator.hasNext()) {
            shiftModel[i++] = new CheckableItem(iterator.next().getName(), false);
        }

        combEmployeeShift.setModel(new DefaultComboBoxModel<>(shiftModel));
    }

    public void setEmployeeInputEditable(boolean editable) {
        textfEmployeeName.setEditable(editable);
        textfPhoneNum.setEditable(editable);
        textfPersonalID.setEditable(editable);
        textfEmployeeEmail.setEditable(editable);
        
        rbtGenderFemale.setEnabled(editable);
        rbtGenderMale.setEnabled(editable);
        
        combEmployeePosition.setEnabled(editable);
        combEmployeeStatus.setEnabled(editable);
        
        dateChooserBirthday.setEnabled(editable);
        dateChooserStartDate.setEnabled(editable);
        dateChooserEndDate.setEnabled(editable);
        
        combEmployeeShift.setEnabled(editable);
    }

    public void resetUIInput() {
        textfEmployeeName.setText(null);
        textfPhoneNum.setText(null);
        textfPersonalID.setText(null);
        textfEmployeeEmail.setText(null);
        btnGroupGender.setSelected(rbtGenderMale.getModel(), true);
        combEmployeePosition.setSelectedIndex(0);
        combEmployeeStatus.setSelectedIndex(0);
        dateChooserBirthday.setDate(null);
        dateChooserStartDate.setDate(new Date());
        dateChooserEndDate.setDate(null);
        combEmployeeShift.setModel(new DefaultComboBoxModel<>(shiftModel));
    }

    public String getEmployeeNameInput() {
        return this.textfEmployeeName.getText().trim();
    }

    public String getEmployeePhoneNumInput() {
        return this.textfPhoneNum.getText().trim();
    }

    public String getEmployeePersonalIDInput() {
        return this.textfPersonalID.getText().trim();
    }

    public String getEmployeeEmailInput() {
        return this.textfEmployeeEmail.getText().trim();
    }

    public boolean getGenderInput() {
        return rbtGenderMale.isSelected();
    }

    public int getPositionSelectIndex() {
        return this.combEmployeePosition.getSelectedIndex();
    }

    public boolean getStatusInput() {
        return this.combEmployeeStatus.getSelectedIndex() == 0;
    }

    public Date getBirthdayInput() {
        return this.dateChooserBirthday.getDate();
    }

    public Date getStartDateInput() {
        return this.dateChooserStartDate.getDate();
    }

    public Date getEndDateInput() {
        return this.dateChooserEndDate.getDate();
    }

    public boolean[] getShiftSelect() {
        boolean[] ret = new boolean[this.shiftModel.length];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = shiftModel[i].isSelected();
        }
        return ret;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        System.out.println("view.function.EmployeeManagePanel.actionPerformed()");
        Object source = event.getSource();

        if (source == btnSearchClear) {
            if (!textfSearchName.getText().trim().isEmpty()) {
                setTextfSearch("");

            }
        } else if (source == btnAdd) {

        } else if (source == btnModify) {

        } else if (source == btnMore) {
            popupBtnMore.show(btnMore, 0, btnMore.getY() + btnMore.getHeight());
        } else if (source == btnSearchClear) {

        } else if (source == mnImport) {

        } else if (source == mnExport) {

        } else if (source == btnSearchClear) {

        }
    }

    public void showCardOption() {
        CardLayout cardLayout = (CardLayout) panelCard.getLayout();
        cardLayout.show(panelCard, panelBtnOption.getName());
    }

    public void showCardFunction() {
        CardLayout cardLayout = (CardLayout) panelCard.getLayout();
        cardLayout.show(panelCard, panelBtnFunction.getName());
    }

    @Override
    public void showErrorMessage(String message) {
        MainFrame.getInstance().showErrorMessage(message);
    }

    @Override
    public void showInfoMessage(String message) {
        MainFrame.getInstance().showInfoMessage(message);
    }

    @Override
    public void showWarningMessage(String message) {
        MainFrame.getInstance().showWarningMessage(message);
    }

    @Override
    public void updateInsertedEmployee(EmployeeModelInterface insertedEmployee) {
        String searchText = textfSearchName.getText().trim();
        if (this.controller.insertToSearchListByMatchingName(searchText, insertedEmployee)) {
            addRowTableEmployee(insertedEmployee);
        }
        this.tableEmployee.repaint();
    }

    @Override
    public void updateModifiedEmployee(EmployeeModelInterface updatedEmployee) {
        updateRowTableEmployee(updatedEmployee);
    }
    
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGroupGender = new javax.swing.ButtonGroup();
        popupBtnMore = new javax.swing.JPopupMenu();
        mnImport = new javax.swing.JMenuItem();
        mnExport = new javax.swing.JMenuItem();
        scrollpane = new javax.swing.JScrollPane();
        tableEmployee = new javax.swing.JTable();
        label_searchEmp = new javax.swing.JLabel();
        textfSearchName = new javax.swing.JTextField();
        panelInfo = new javax.swing.JPanel();
        label_emplID = new javax.swing.JLabel();
        label_namePro = new javax.swing.JLabel();
        label_personalID = new javax.swing.JLabel();
        label_mobile = new javax.swing.JLabel();
        textfEmployeeName = new javax.swing.JTextField();
        textfPhoneNum = new javax.swing.JTextField();
        textfPersonalID = new javax.swing.JTextField();
        label_email = new javax.swing.JLabel();
        label_gender = new javax.swing.JLabel();
        rbtGenderMale = new javax.swing.JRadioButton();
        rbtGenderFemale = new javax.swing.JRadioButton();
        lable_status = new javax.swing.JLabel();
        combEmployeeStatus = new javax.swing.JComboBox<>();
        lable_position = new javax.swing.JLabel();
        combEmployeePosition = new javax.swing.JComboBox<>();
        lable_startDate = new javax.swing.JLabel();
        lable_birthDay = new javax.swing.JLabel();
        lable_shift = new javax.swing.JLabel();
        textfEmployeeID = new javax.swing.JTextField();
        textfEmployeeEmail = new javax.swing.JTextField();
        dateChooserStartDate = new com.toedter.calendar.JDateChooser();
        dateChooserBirthday = new com.toedter.calendar.JDateChooser();
        combEmployeeShift = new util.swing.checkcombobox.CheckedComboBox();
        dateChooserEndDate = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        btnSearchClear = new javax.swing.JButton();
        panelCard = new javax.swing.JPanel();
        panelBtnFunction = new javax.swing.JPanel();
        btnAdd = new javax.swing.JButton();
        btnModify = new javax.swing.JButton();
        btnMore = new javax.swing.JButton();
        panelBtnOption = new javax.swing.JPanel();
        btnOK = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();

        mnImport.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        mnImport.setText("Import");
        popupBtnMore.add(mnImport);

        mnExport.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        mnExport.setText("Export");
        popupBtnMore.add(mnExport);

        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(new java.awt.Color(255, 255, 255));

        tableEmployee.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        tableEmployee.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Gender", "Personal ID", "Email", "Mobile", "Birthday", "Position", "Shift", "Status", "Start date", "End date"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableEmployee.getTableHeader().setReorderingAllowed(false);
        scrollpane.setViewportView(tableEmployee);
        tableEmployee.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
        if (tableEmployee.getColumnModel().getColumnCount() > 0) {
            tableEmployee.getColumnModel().getColumn(0).setResizable(false);
            tableEmployee.getColumnModel().getColumn(0).setPreferredWidth(5);
            tableEmployee.getColumnModel().getColumn(1).setResizable(false);
            tableEmployee.getColumnModel().getColumn(1).setPreferredWidth(150);
            tableEmployee.getColumnModel().getColumn(2).setResizable(false);
            tableEmployee.getColumnModel().getColumn(2).setPreferredWidth(60);
            tableEmployee.getColumnModel().getColumn(3).setResizable(false);
            tableEmployee.getColumnModel().getColumn(3).setPreferredWidth(50);
            tableEmployee.getColumnModel().getColumn(4).setResizable(false);
            tableEmployee.getColumnModel().getColumn(4).setPreferredWidth(150);
            tableEmployee.getColumnModel().getColumn(5).setResizable(false);
            tableEmployee.getColumnModel().getColumn(5).setPreferredWidth(30);
            tableEmployee.getColumnModel().getColumn(6).setResizable(false);
            tableEmployee.getColumnModel().getColumn(7).setResizable(false);
            tableEmployee.getColumnModel().getColumn(8).setResizable(false);
            tableEmployee.getColumnModel().getColumn(8).setPreferredWidth(50);
            tableEmployee.getColumnModel().getColumn(9).setResizable(false);
            tableEmployee.getColumnModel().getColumn(10).setResizable(false);
            tableEmployee.getColumnModel().getColumn(10).setPreferredWidth(50);
        }

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

        textfEmployeeName.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        textfEmployeeName.setPreferredSize(new java.awt.Dimension(160, 30));

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

        combEmployeeStatus.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        combEmployeeStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Active", "Non-active" }));
        combEmployeeStatus.setPreferredSize(new java.awt.Dimension(160, 30));

        lable_position.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        lable_position.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lable_position.setText("Position");

        combEmployeePosition.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        combEmployeePosition.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Thu ngân", "Kế toán", "Thợ làm bánh" }));
        combEmployeePosition.setPreferredSize(new java.awt.Dimension(160, 30));

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

        textfEmployeeEmail.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        dateChooserStartDate.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        dateChooserBirthday.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        combEmployeeShift.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

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
                        .addComponent(textfEmployeeName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                                    .addComponent(textfEmployeeEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
                                    .addGroup(panelInfoLayout.createSequentialGroup()
                                        .addComponent(combEmployeeStatus, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(75, 75, 75))))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelInfoLayout.createSequentialGroup()
                                .addComponent(lable_position, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(combEmployeePosition, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                    .addComponent(combEmployeeShift, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
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
                            .addComponent(textfEmployeeName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                                .addComponent(combEmployeeShift, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lable_shift)))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelInfoLayout.createSequentialGroup()
                            .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(label_email, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(textfEmployeeEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(rbtGenderMale)
                                .addComponent(rbtGenderFemale)
                                .addComponent(label_gender))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lable_position)
                                .addComponent(combEmployeePosition, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(16, 16, 16)
                            .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lable_status)
                                .addComponent(combEmployeeStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );

        btnSearchClear.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnSearchClear.setForeground(new java.awt.Color(51, 51, 51));
        btnSearchClear.setText("Clear");
        btnSearchClear.setFocusPainted(false);
        btnSearchClear.setPreferredSize(new java.awt.Dimension(89, 29));

        panelCard.setLayout(new java.awt.CardLayout());

        panelBtnFunction.setBackground(new java.awt.Color(255, 255, 255));
        panelBtnFunction.setLayout(new java.awt.GridLayout(1, 0, 10, 0));

        btnAdd.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnAdd.setForeground(new java.awt.Color(51, 51, 51));
        btnAdd.setText("Add");
        btnAdd.setFocusPainted(false);
        btnAdd.setPreferredSize(new java.awt.Dimension(115, 40));
        panelBtnFunction.add(btnAdd);

        btnModify.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnModify.setForeground(new java.awt.Color(51, 51, 51));
        btnModify.setText("Modify");
        btnModify.setFocusPainted(false);
        btnModify.setPreferredSize(new java.awt.Dimension(115, 40));
        panelBtnFunction.add(btnModify);

        btnMore.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnMore.setForeground(new java.awt.Color(51, 51, 51));
        btnMore.setText("More ▼");
        btnMore.setFocusPainted(false);
        btnMore.setPreferredSize(new java.awt.Dimension(115, 40));
        panelBtnFunction.add(btnMore);

        panelCard.add(panelBtnFunction, "card2");

        panelBtnOption.setBackground(new java.awt.Color(255, 255, 255));
        panelBtnOption.setLayout(new java.awt.GridLayout(1, 0, 10, 0));

        btnOK.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnOK.setForeground(new java.awt.Color(51, 51, 51));
        btnOK.setText("OK");
        btnOK.setFocusPainted(false);
        btnOK.setPreferredSize(new java.awt.Dimension(115, 40));
        panelBtnOption.add(btnOK);

        btnCancel.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnCancel.setForeground(new java.awt.Color(51, 51, 51));
        btnCancel.setText("Cancel");
        btnCancel.setFocusPainted(false);
        btnCancel.setPreferredSize(new java.awt.Dimension(115, 40));
        panelBtnOption.add(btnCancel);

        btnReset.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnReset.setForeground(new java.awt.Color(51, 51, 51));
        btnReset.setText("Reset");
        btnReset.setFocusPainted(false);
        btnReset.setPreferredSize(new java.awt.Dimension(115, 40));
        panelBtnOption.add(btnReset);

        panelCard.add(panelBtnOption, "card3");

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
                        .addComponent(textfSearchName, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSearchClear, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(panelCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(textfSearchName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSearchClear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(label_searchEmp, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(panelCard, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(scrollpane, javax.swing.GroupLayout.DEFAULT_SIZE, 548, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCancel;
    private javax.swing.ButtonGroup btnGroupGender;
    private javax.swing.JButton btnModify;
    private javax.swing.JButton btnMore;
    private javax.swing.JButton btnOK;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSearchClear;
    private javax.swing.JComboBox<String> combEmployeePosition;
    private util.swing.checkcombobox.CheckedComboBox combEmployeeShift;
    private javax.swing.JComboBox<String> combEmployeeStatus;
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
    private javax.swing.JPanel panelBtnFunction;
    private javax.swing.JPanel panelBtnOption;
    private javax.swing.JPanel panelCard;
    private javax.swing.JPanel panelInfo;
    private javax.swing.JPopupMenu popupBtnMore;
    private javax.swing.JRadioButton rbtGenderFemale;
    private javax.swing.JRadioButton rbtGenderMale;
    private javax.swing.JScrollPane scrollpane;
    private javax.swing.JTable tableEmployee;
    private javax.swing.JTextField textfEmployeeEmail;
    private javax.swing.JTextField textfEmployeeID;
    private javax.swing.JTextField textfEmployeeName;
    private javax.swing.JTextField textfPersonalID;
    private javax.swing.JTextField textfPhoneNum;
    private javax.swing.JTextField textfSearchName;
    // End of variables declaration//GEN-END:variables

}
