package view.employee;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Iterator;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import static util.swing.UIControl.setDefaultTableHeader;
import util.swing.checkcombobox.CheckableItem;
import control.employee.EmployeeControllerInterface;
import java.awt.CardLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import model.employee.EmployeeManageModelInterface;
import model.employee.EmployeeModelInterface;
import model.employee.shift.detail.ShiftDetailModelInterface;
import model.setting.AppSetting;
import model.setting.SettingUpdateObserver;
import util.constant.AppConstant;
import view.MessageShowing;

public class EmployeePanel extends javax.swing.JPanel implements ActionListener,
        MessageShowing, InsertedEmployeeObserver, ModifiedEmployeeObserver,
        SettingUpdateObserver {

    public static final int EMPLOYEE_ID_COLUMN_INDEX = 0;
    public static final int EMPLOYEE_NAME_COLUMN_INDEX = 1;
    public static final int EMPLOYEE_GENDER_COLUMN_INDEX = 2;
    public static final int EMPLOYEE_PERSONAL_ID_COLUMN_INDEX = 3;
    public static final int EMPLOYEE_EMAIL_COLUMN_INDEX = 4;
    public static final int EMPLOYEE_MOBILE_COLUMN_INDEX = 5;
    public static final int EMPLOYEE_BIRTHDAY_COLUMN_INDEX = 6;
    public static final int EMPLOYEE_POSITION_COLUMN_INDEX = 7;
    public static final int EMPLOYEE_WORKING_COLUMN_INDEX = 8;
    public static final int EMPLOYEE_START_DATE_COLUMN_INDEX = 9;
    public static final int EMPLOYEE_END_DATE_COLUMN_INDEX = 10;

    public enum EditState {
        ADD,
        MODIFY,
    }

    private JFrame mainFrame;

    private EmployeeManageModelInterface employeeManageModel;
    private EmployeeControllerInterface employeeController;

    private DefaultTableModel tableEmployeeModel;

    private EditState editState;

    private CheckableItem[] shiftModel;

    public EmployeePanel() {
        initComponents();
        this.tableEmployeeModel = (DefaultTableModel) tableEmployee.getModel();
        this.editState = null;
        createView();
        createControl();
    }

    public void setEmployeeManageModel(EmployeeManageModelInterface model) {
        if (model == null) {
            throw new NullPointerException("Employee manage model instance is null.");
        }
        this.employeeManageModel = model;
        this.employeeManageModel.registerInsertedEmployeeObserver(this);
        this.employeeManageModel.registerModifiedEmployeeObserver(this);

        resetEmployeeList();
        loadEmployeeShiftInput();
        loadEmployeePositionInput();
        resetEmployeeInput();
    }

    public void setEmployeeController(EmployeeControllerInterface controller) {
        if (controller == null) {
            throw new NullPointerException("Employee controller instance is null.");
        }
        this.employeeController = controller;
    }

    public void setMainFrame(JFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public JFrame getMainFrame() {
        return this.mainFrame;
    }

    public EditState getEditState() {
        return this.editState;
    }

    private void createView() {
        btnGroupGender.setSelected(rbtGenderMale.getModel(), true);
        textfEmployeeID.setEditable(false);
        setEmployeeInputEditable(false);
        setDefaultTableHeader(tableEmployee);
    }

    private void createControl() {
        btnSearchClear.addActionListener(this);
        btnAdd.addActionListener(this);
        btnModify.addActionListener(this);
        btnExport.addActionListener(this);
        btnOK.addActionListener(this);
        btnCancel.addActionListener(this);
        btnReset.addActionListener(this);

        tableEmployee.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                employeeController.requestShowEmployeeInfo();
            }
        });

        textfSearchName.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent event) {
                String searchText = textfSearchName.getText().trim();
                showEmployeeList(employeeController.getEmployeeBySearchName(searchText));
            }

            @Override
            public void removeUpdate(DocumentEvent event) {
                String searchText = textfSearchName.getText().trim();
                if (searchText.isEmpty()) {
                    resetEmployeeList();
                } else {
                    showEmployeeList(employeeController.getEmployeeBySearchName(searchText));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent event) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });

        labelSettingPersonalID.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                employeeController.requestChangePersonalIDConstraint();
            }
        });

        labelSettingPhoneNum.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                employeeController.requestChangePhoneNumConstraint();
            }
        });
    }

    public int getTableEmployeeRowCount() {
        return tableEmployeeModel.getRowCount();
    }

    public JTable getTableEmployee() {
        return tableEmployee;
    }

    public void showEmployeeInfo(EmployeeModelInterface employee) {
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

        this.combPositionName.setSelectedItem(employee.getPositionName());

        if (employee.getStatus()) {
            this.combEmployeeStatus.setSelectedIndex(0);
        } else {
            this.combEmployeeStatus.setSelectedIndex(1);
        }

        dateChooserBirthday.setDate(employee.getBirthday());
        dateChooserStartDate.setDate(employee.getStartDate());
        dateChooserEndDate.setDate(employee.getEndDate());

        List<ShiftDetailModelInterface> shiftDetailOfEmployee = employee.getShiftDetails();

        for (int i = 0; i < shiftModel.length; i++) {
            shiftModel[i].setSelected(false);

            String itemValue = shiftModel[i].getValue();

            for (ShiftDetailModelInterface shiftDetail : shiftDetailOfEmployee) {
                if (shiftDetail.getShiftName().equals(itemValue)) {
                    shiftModel[i].setSelected(true);
                    break;
                }
            }
        }

        combEmployeeShift.repaint();
    }

    public void loadEmployeePositionInput() {
        List<String> positionNames = this.employeeManageModel.getAllPositionName();

        this.combPositionName.removeAllItems();

        for (String positionName : positionNames) {
            this.combPositionName.addItem(positionName);
        }

        if (combPositionName.getItemCount() != 0) {
            combPositionName.setSelectedIndex(0);
        }
    }

    public void resetEmployeeList() {
        Iterator<EmployeeModelInterface> iterator = this.employeeController.getAllEmployeeData();
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
        Object[] record = new Object[]{
            employee.getEmployeeIDText(),
            employee.getName(),
            employee.getGender() ? "Nam" : "Nữ",
            employee.getPersonalID(),
            employee.getEmail(),
            employee.getPhoneNum(),
            employee.getBirthday().toLocalDate().format(AppConstant.GLOBAL_DATE_FORMATTER),
            employee.getPositionName(),
            employee.getStatus(),
            employee.getStartDate().toLocalDate().format(AppConstant.GLOBAL_DATE_FORMATTER),
            (employee.getEndDate() != null ? employee.getEndDate().toLocalDate().format(AppConstant.GLOBAL_DATE_FORMATTER) : null)
        };
        this.tableEmployeeModel.addRow(record);
    }

    private void updateRowTableEmployee(EmployeeModelInterface employee) {
        String employeeIDText = employee.getEmployeeIDText();
        for (int i = 0; i < this.tableEmployeeModel.getRowCount(); i++) {
            String employeeIDInTable = (String) this.tableEmployeeModel.getValueAt(i, EMPLOYEE_ID_COLUMN_INDEX);

            if (employeeIDInTable.equals(employeeIDText)) {
                Object[] record = new Object[]{
                    employee.getEmployeeIDText(),
                    employee.getName(),
                    employee.getGender() ? "Nam" : "Nữ",
                    employee.getPersonalID(),
                    employee.getEmail(),
                    employee.getPhoneNum(),
                    employee.getBirthday().toLocalDate().format(AppConstant.GLOBAL_DATE_FORMATTER),
                    employee.getPositionName(),
                    employee.getStatus(),
                    employee.getStartDate().toLocalDate().format(AppConstant.GLOBAL_DATE_FORMATTER),
                    (employee.getEndDate() != null ? employee.getEndDate().toLocalDate().format(AppConstant.GLOBAL_DATE_FORMATTER) : null)
                };
                for (int j = 0; j < record.length; j++) {
                    this.tableEmployeeModel.setValueAt(record[j], i, j);
                }
                break;
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

        if (combPositionName.getItemCount() != 0) {
            combPositionName.setSelectedIndex(0);
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
        List<String> shiftNames = this.employeeManageModel.getAllShiftName();

        int size = shiftNames.size();

        shiftModel = new CheckableItem[size];

        for (int i = 0; i < shiftNames.size(); i++) {
            shiftModel[i] = new CheckableItem(shiftNames.get(i), false);
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

        combPositionName.setEnabled(editable);
        combEmployeeStatus.setEnabled(editable);

        dateChooserBirthday.setEnabled(editable);
        dateChooserStartDate.setEnabled(editable);
        dateChooserEndDate.setEnabled(editable);

        combEmployeeShift.setEnabled(editable);
    }

    public String getEmployeeIDText() {
        return this.textfEmployeeID.getText();
    }

    public String getEmployeeNameInput() {
        return this.textfEmployeeName.getText();
    }

    public String getEmployeePhoneNumInput() {
        return this.textfPhoneNum.getText();
    }

    public String getEmployeePersonalIDInput() {
        return this.textfPersonalID.getText();
    }

    public String getEmployeeEmailInput() {
        return this.textfEmployeeEmail.getText();
    }

    public boolean getEmployeeGenderInput() {
        return rbtGenderMale.isSelected();
    }

    public String getEmployeePositionNameSelected() {
        Object ret = this.combPositionName.getSelectedItem();
        if (ret == null) {
            return null;
        }
        return (String) ret;
    }

    public boolean getEmployeeStatusInput() {
        return this.combEmployeeStatus.getSelectedIndex() == 0;
    }

    public Date getEmployeeBirthdayInput() {
        return this.dateChooserBirthday.getDate();
    }

    public Date getEmployeeStartDateInput() {
        return this.dateChooserStartDate.getDate();
    }

    public Date getEmployeeEndDateInput() {
        return this.dateChooserEndDate.getDate();
    }

    public List<String> getEmployeeShiftNameSelected() {
        List<String> ret = new ArrayList<>();
        for (int i = 0; i < shiftModel.length; i++) {
            if (shiftModel[i].isSelected()) {
                ret.add(shiftModel[i].getValue());
            }
        }
        return ret;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();

        if (source == btnSearchClear) {
            if (!textfSearchName.getText().trim().isEmpty()) {
                setTextfSearch("");
                resetEmployeeList();
                resetEmployeeInput();
            }
        } else if (source == btnAdd) {
            resetEmployeeInput();
            setEmployeeInputEditable(true);
            if (btnAdd.getText().equals("Add")) {
                btnOK.setText("Add");
            } else {
                btnOK.setText("Thêm");
            }
            showCardOption();
            String nextEmployeeIDText = this.employeeManageModel.getNextEmployeeIDText();
            this.textfEmployeeID.setText(nextEmployeeIDText);
            this.editState = EditState.ADD;
        } else if (source == btnModify) {
            String employeeIDText = textfEmployeeID.getText();
            if (employeeIDText.isEmpty()) {
                showErrorMessage("You should choose one employee first.");
            } else {
                setEmployeeInputEditable(true);
                if (btnModify.getText().equals("Modify")) {
                    btnOK.setText("Save");
                } else {
                    btnOK.setText("Lưu");
                }
                showCardOption();
                this.editState = EditState.MODIFY;
            }
        } else if (source == btnExport) {
            this.employeeController.requestExportExcel();
        } else if (source == btnOK) {
            switch (editState) {
                case ADD: {
                    this.employeeController.requestCreateEmployee();
                    break;
                }
                case MODIFY: {
                    this.employeeController.requestUpdateEmployee();
                    break;
                }
            }
        } else if (source == btnCancel) {
            exitEditState();
        } else if (source == btnReset) {
            resetEmployeeInput();
        }
    }

    public void exitEditState() {
        if (this.editState == EditState.ADD) {
            this.textfEmployeeID.setText("");
            resetEmployeeInput();
        }
        this.editState = null;
        setEmployeeInputEditable(false);
        showCardFunction();
        employeeController.requestShowEmployeeInfo();
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
        ((MessageShowing) mainFrame).showErrorMessage(message);
    }

    @Override
    public void showInfoMessage(String message) {
        ((MessageShowing) mainFrame).showInfoMessage(message);
    }

    @Override
    public void showWarningMessage(String message) {
        ((MessageShowing) mainFrame).showWarningMessage(message);
    }

    @Override
    public void updateInsertedEmployee(EmployeeModelInterface insertedEmployee) {
        String searchText = textfSearchName.getText().trim();
        if (this.employeeController.isSearchMatching(searchText, insertedEmployee)) {
            if (searchText.isEmpty()) {
                addRowTableEmployee(insertedEmployee);
            } else {
                showEmployeeList(employeeController.getEmployeeBySearchName(searchText));
            }
        }
        this.tableEmployee.repaint();
    }

    @Override
    public void updateModifiedEmployee(EmployeeModelInterface updatedEmployee) {
        updateRowTableEmployee(updatedEmployee);
    }

    @Override
    public void updateSettingObserver() {
        AppSetting.Language language = AppSetting.getInstance().getAppLanguage();

        switch (language) {
            case ENGLISH: {
                labelEmployeeID.setText("Employee ID:");
                labelName.setText("Name:");
                labelMobile.setText("Mobile:");
                labelPersonalID.setText("Personal ID:");
                labelGender.setText("Gender:");
                rbtGenderMale.setText("Male");
                rbtGenderFemale.setText("Female");
                labelPosition.setText("Position:");
                labelStatus.setText("Status:");
                labelShift.setText("Shift:");
                labelBirthday.setText("Birthday:");
                labelStartDate.setText("Start date:");
                labelEndDate.setText("End date:");
                labelSearchEmployee.setText("Search name:");
                btnSearchClear.setText("Clear");
                btnAdd.setText("Add");
                btnModify.setText("Modify");
                btnExport.setText("Export");
                btnReset.setText("Reset");
                btnCancel.setText("Cancel");

                TableColumnModel columnModel = tableEmployee.getColumnModel();
                columnModel.getColumn(EMPLOYEE_NAME_COLUMN_INDEX).setHeaderValue("Name");
                columnModel.getColumn(EMPLOYEE_GENDER_COLUMN_INDEX).setHeaderValue("Gender");
                columnModel.getColumn(EMPLOYEE_PERSONAL_ID_COLUMN_INDEX).setHeaderValue("Personal ID");
                columnModel.getColumn(EMPLOYEE_MOBILE_COLUMN_INDEX).setHeaderValue("Mobile");
                columnModel.getColumn(EMPLOYEE_BIRTHDAY_COLUMN_INDEX).setHeaderValue("Birthday");
                columnModel.getColumn(EMPLOYEE_POSITION_COLUMN_INDEX).setHeaderValue("Position");
                columnModel.getColumn(EMPLOYEE_WORKING_COLUMN_INDEX).setHeaderValue("Working");
                columnModel.getColumn(EMPLOYEE_START_DATE_COLUMN_INDEX).setHeaderValue("Start date");
                columnModel.getColumn(EMPLOYEE_END_DATE_COLUMN_INDEX).setHeaderValue("End date");

                break;
            }
            case VIETNAMESE: {
                labelEmployeeID.setText("Nhân viên ID:");
                labelName.setText("Họ tên:");
                labelMobile.setText("Số điện thoại:");
                labelPersonalID.setText("Số CCCD:");
                labelGender.setText("Giới tính:");
                rbtGenderMale.setText("Nam");
                rbtGenderFemale.setText("Nữ");
                labelPosition.setText("Chức vụ:");
                labelStatus.setText("Trạng thái:");
                labelShift.setText("Ca làm việc:");
                labelBirthday.setText("Ngày sinh:");
                labelStartDate.setText("Ngày bắt đầu:");
                labelEndDate.setText("Ngày kết thúc:");
                labelSearchEmployee.setText("Tìm theo tên:");
                btnSearchClear.setText("Xóa");
                btnAdd.setText("Thêm");
                btnModify.setText("Chỉnh sửa");
                btnExport.setText("Xuất");
                btnReset.setText("Làm mới");
                btnCancel.setText("Thoát");

                TableColumnModel columnModel = tableEmployee.getColumnModel();
                columnModel.getColumn(EMPLOYEE_NAME_COLUMN_INDEX).setHeaderValue("Họ tên");
                columnModel.getColumn(EMPLOYEE_GENDER_COLUMN_INDEX).setHeaderValue("Giới tính");
                columnModel.getColumn(EMPLOYEE_PERSONAL_ID_COLUMN_INDEX).setHeaderValue("Số CCCD");
                columnModel.getColumn(EMPLOYEE_MOBILE_COLUMN_INDEX).setHeaderValue("Số điện thoại");
                columnModel.getColumn(EMPLOYEE_BIRTHDAY_COLUMN_INDEX).setHeaderValue("Ngày sinh");
                columnModel.getColumn(EMPLOYEE_POSITION_COLUMN_INDEX).setHeaderValue("Chức vụ");
                columnModel.getColumn(EMPLOYEE_WORKING_COLUMN_INDEX).setHeaderValue("Trạng thái");
                columnModel.getColumn(EMPLOYEE_START_DATE_COLUMN_INDEX).setHeaderValue("Ngày bắt đầu");
                columnModel.getColumn(EMPLOYEE_END_DATE_COLUMN_INDEX).setHeaderValue("Ngày kết thúc");

                break;
            }
        }

        repaint();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGroupGender = new javax.swing.ButtonGroup();
        scrollpane = new javax.swing.JScrollPane();
        tableEmployee = new javax.swing.JTable();
        panelInfo = new javax.swing.JPanel();
        labelEmployeeID = new javax.swing.JLabel();
        labelName = new javax.swing.JLabel();
        labelPersonalID = new javax.swing.JLabel();
        labelMobile = new javax.swing.JLabel();
        textfEmployeeName = new javax.swing.JTextField();
        textfPhoneNum = new javax.swing.JTextField();
        textfPersonalID = new javax.swing.JTextField();
        label_email = new javax.swing.JLabel();
        labelGender = new javax.swing.JLabel();
        rbtGenderMale = new javax.swing.JRadioButton();
        rbtGenderFemale = new javax.swing.JRadioButton();
        labelStatus = new javax.swing.JLabel();
        combEmployeeStatus = new javax.swing.JComboBox<>();
        labelPosition = new javax.swing.JLabel();
        combPositionName = new javax.swing.JComboBox<>();
        labelStartDate = new javax.swing.JLabel();
        labelBirthday = new javax.swing.JLabel();
        labelShift = new javax.swing.JLabel();
        textfEmployeeID = new javax.swing.JTextField();
        textfEmployeeEmail = new javax.swing.JTextField();
        dateChooserStartDate = new com.toedter.calendar.JDateChooser();
        dateChooserBirthday = new com.toedter.calendar.JDateChooser();
        combEmployeeShift = new util.swing.checkcombobox.CheckedComboBox();
        dateChooserEndDate = new com.toedter.calendar.JDateChooser();
        labelEndDate = new javax.swing.JLabel();
        labelSettingPersonalID = new javax.swing.JLabel();
        labelSettingPhoneNum = new javax.swing.JLabel();
        panelCard = new javax.swing.JPanel();
        panelBtnFunction = new javax.swing.JPanel();
        btnAdd = new javax.swing.JButton();
        btnModify = new javax.swing.JButton();
        btnExport = new javax.swing.JButton();
        panelBtnOption = new javax.swing.JPanel();
        btnOK = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        labelSearchEmployee = new javax.swing.JLabel();
        textfSearchName = new javax.swing.JTextField();
        btnSearchClear = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(new java.awt.Color(255, 255, 255));
        setName("Employee"); // NOI18N

        tableEmployee.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        tableEmployee.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Gender", "Personal ID", "Email", "Mobile", "Birthday", "Position", "Working", "Start date", "End date"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Boolean.class, java.lang.String.class, java.lang.String.class
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
        tableEmployee.setSelectionBackground(new java.awt.Color(113, 168, 255));
        tableEmployee.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
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

        panelInfo.setBackground(new java.awt.Color(255, 255, 255));
        panelInfo.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Employee Information", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(153, 153, 153))); // NOI18N

        labelEmployeeID.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelEmployeeID.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelEmployeeID.setText("Employee ID");

        labelName.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelName.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelName.setText("Name");

        labelPersonalID.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelPersonalID.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelPersonalID.setText("Personal ID");

        labelMobile.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelMobile.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelMobile.setText("Mobile");

        textfEmployeeName.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        textfEmployeeName.setPreferredSize(new java.awt.Dimension(160, 30));

        textfPhoneNum.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        textfPhoneNum.setPreferredSize(new java.awt.Dimension(160, 30));

        textfPersonalID.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        textfPersonalID.setPreferredSize(new java.awt.Dimension(160, 30));

        label_email.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_email.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        label_email.setText("Email");

        labelGender.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelGender.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelGender.setText("Gender");

        rbtGenderMale.setBackground(new java.awt.Color(255, 255, 255));
        btnGroupGender.add(rbtGenderMale);
        rbtGenderMale.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        rbtGenderMale.setText("Male");

        rbtGenderFemale.setBackground(new java.awt.Color(255, 255, 255));
        btnGroupGender.add(rbtGenderFemale);
        rbtGenderFemale.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        rbtGenderFemale.setText("Female");

        labelStatus.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelStatus.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelStatus.setText("Status");

        combEmployeeStatus.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        combEmployeeStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Working", "No-working" }));
        combEmployeeStatus.setPreferredSize(new java.awt.Dimension(160, 30));

        labelPosition.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelPosition.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelPosition.setText("Position");

        combPositionName.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        combPositionName.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Thu ngân", "Kế toán", "Thợ làm bánh" }));
        combPositionName.setPreferredSize(new java.awt.Dimension(160, 30));

        labelStartDate.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelStartDate.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelStartDate.setText("Start date");

        labelBirthday.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelBirthday.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelBirthday.setText("Birthday");

        labelShift.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelShift.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelShift.setText("Shift");

        textfEmployeeID.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        textfEmployeeEmail.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        dateChooserStartDate.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        dateChooserBirthday.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        combEmployeeShift.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        dateChooserEndDate.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        labelEndDate.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelEndDate.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelEndDate.setText("End date");

        labelSettingPersonalID.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Slider_20px_1.png"))); // NOI18N

        labelSettingPhoneNum.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Slider_20px_1.png"))); // NOI18N

        javax.swing.GroupLayout panelInfoLayout = new javax.swing.GroupLayout(panelInfo);
        panelInfo.setLayout(panelInfoLayout);
        panelInfoLayout.setHorizontalGroup(
            panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInfoLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(labelMobile, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelEmployeeID, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                    .addComponent(labelPersonalID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(textfPhoneNum, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                        .addComponent(textfEmployeeName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                        .addComponent(textfEmployeeID, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE))
                    .addComponent(textfPersonalID, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelSettingPersonalID, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelSettingPhoneNum, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(labelPosition, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                    .addComponent(labelGender, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(label_email, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rbtGenderMale)
                    .addComponent(textfEmployeeEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(rbtGenderFemale)
                        .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(combEmployeeStatus, javax.swing.GroupLayout.Alignment.LEADING, 0, 200, Short.MAX_VALUE)
                            .addComponent(combPositionName, javax.swing.GroupLayout.Alignment.LEADING, 0, 200, Short.MAX_VALUE))))
                .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(labelShift, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelBirthday, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(labelEndDate, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE))
                    .addComponent(labelStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(dateChooserStartDate, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                        .addComponent(dateChooserEndDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(dateChooserBirthday, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(combEmployeeShift, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(126, 126, 126))
        );
        panelInfoLayout.setVerticalGroup(
            panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelInfoLayout.createSequentialGroup()
                        .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dateChooserBirthday, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelBirthday, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(dateChooserStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(dateChooserEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelInfoLayout.createSequentialGroup()
                        .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(combEmployeeShift, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(labelShift))
                            .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(textfEmployeeID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(labelEmployeeID)
                                .addComponent(label_email)
                                .addComponent(textfEmployeeEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelInfoLayout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(textfEmployeeName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(labelName)
                                    .addComponent(labelGender)
                                    .addComponent(rbtGenderMale))
                                .addGap(20, 20, 20)
                                .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelInfoLayout.createSequentialGroup()
                                        .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(labelSettingPhoneNum, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                                            .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(textfPhoneNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(labelMobile, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(labelPosition)
                                                .addComponent(combPositionName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(20, 20, 20)
                                        .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(labelSettingPersonalID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(textfPersonalID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(labelPersonalID))))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(labelStatus)
                                        .addComponent(combEmployeeStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(panelInfoLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(rbtGenderFemale)))))
                .addContainerGap())
        );

        panelCard.setLayout(new java.awt.CardLayout());

        panelBtnFunction.setBackground(new java.awt.Color(255, 255, 255));
        panelBtnFunction.setName("Function"); // NOI18N
        panelBtnFunction.setPreferredSize(new java.awt.Dimension(390, 40));
        panelBtnFunction.setLayout(new java.awt.GridLayout(1, 0, 10, 0));

        btnAdd.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnAdd.setForeground(new java.awt.Color(51, 51, 51));
        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Add_30px.png"))); // NOI18N
        btnAdd.setText("Add");
        btnAdd.setFocusPainted(false);
        btnAdd.setPreferredSize(new java.awt.Dimension(115, 40));
        panelBtnFunction.add(btnAdd);

        btnModify.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnModify.setForeground(new java.awt.Color(51, 51, 51));
        btnModify.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Edit_30px.png"))); // NOI18N
        btnModify.setText("Modify");
        btnModify.setFocusPainted(false);
        btnModify.setPreferredSize(new java.awt.Dimension(115, 40));
        panelBtnFunction.add(btnModify);

        btnExport.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnExport.setForeground(new java.awt.Color(51, 51, 51));
        btnExport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Excel_30px.png"))); // NOI18N
        btnExport.setText("Export");
        btnExport.setFocusPainted(false);
        btnExport.setPreferredSize(new java.awt.Dimension(115, 40));
        panelBtnFunction.add(btnExport);

        panelCard.add(panelBtnFunction, "Function");

        panelBtnOption.setBackground(new java.awt.Color(255, 255, 255));
        panelBtnOption.setName("Option"); // NOI18N
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

        panelCard.add(panelBtnOption, "Option");

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setPreferredSize(new java.awt.Dimension(300, 30));

        labelSearchEmployee.setBackground(new java.awt.Color(255, 255, 255));
        labelSearchEmployee.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelSearchEmployee.setText("Search name:");
        labelSearchEmployee.setOpaque(true);
        labelSearchEmployee.setPreferredSize(new java.awt.Dimension(100, 30));
        jPanel3.add(labelSearchEmployee);

        textfSearchName.setPreferredSize(new java.awt.Dimension(250, 30));
        jPanel3.add(textfSearchName);

        btnSearchClear.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnSearchClear.setForeground(new java.awt.Color(51, 51, 51));
        btnSearchClear.setText("Clear");
        btnSearchClear.setFocusPainted(false);
        btnSearchClear.setPreferredSize(new java.awt.Dimension(90, 30));
        jPanel3.add(btnSearchClear);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(scrollpane)
                            .addComponent(panelInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 457, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(panelCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelCard, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scrollpane, javax.swing.GroupLayout.DEFAULT_SIZE, 525, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnExport;
    private javax.swing.ButtonGroup btnGroupGender;
    private javax.swing.JButton btnModify;
    private javax.swing.JButton btnOK;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSearchClear;
    private util.swing.checkcombobox.CheckedComboBox combEmployeeShift;
    private javax.swing.JComboBox<String> combEmployeeStatus;
    private javax.swing.JComboBox<String> combPositionName;
    private com.toedter.calendar.JDateChooser dateChooserBirthday;
    private com.toedter.calendar.JDateChooser dateChooserEndDate;
    private com.toedter.calendar.JDateChooser dateChooserStartDate;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel labelBirthday;
    private javax.swing.JLabel labelEmployeeID;
    private javax.swing.JLabel labelEndDate;
    private javax.swing.JLabel labelGender;
    private javax.swing.JLabel labelMobile;
    private javax.swing.JLabel labelName;
    private javax.swing.JLabel labelPersonalID;
    private javax.swing.JLabel labelPosition;
    private javax.swing.JLabel labelSearchEmployee;
    private javax.swing.JLabel labelSettingPersonalID;
    private javax.swing.JLabel labelSettingPhoneNum;
    private javax.swing.JLabel labelShift;
    private javax.swing.JLabel labelStartDate;
    private javax.swing.JLabel labelStatus;
    private javax.swing.JLabel label_email;
    private javax.swing.JPanel panelBtnFunction;
    private javax.swing.JPanel panelBtnOption;
    private javax.swing.JPanel panelCard;
    private javax.swing.JPanel panelInfo;
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
