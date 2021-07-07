package control.employee;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import model.employee.EmployeeManageModelInterface;
import model.employee.EmployeeModel;
import model.employee.EmployeeModelInterface;
import model.employee.shift.detail.ShiftDetailModel;
import model.employee.shift.detail.ShiftDetailModelInterface;
import util.common.string.StringUtil;
import util.constant.AppConstant;
import util.excel.ExcelTransfer;
import util.mail.MailUtility;
import util.messages.Messages;
import util.validator.EmailValidator;
import util.validator.PersonalIDValidator;
import util.validator.PhoneValidator;
import view.employee.EmployeePanel;

public class EmployeeController implements EmployeeControllerInterface {

    private List<EmployeeModelInterface> searchList;

    private EmployeeManageModelInterface employeeManageModel;
    private EmployeePanel employeePanel;

    public EmployeeController(EmployeeManageModelInterface model) {
        this.searchList = new ArrayList<>();
        this.employeeManageModel = model;
    }

    @Override
    public void setEmployeePanelView(EmployeePanel employeePanel) {
        if (employeePanel == null) {
            throw new NullPointerException();
        }
        this.employeePanel = employeePanel;
        employeePanel.setEmployeeController(this);
        employeePanel.setEmployeeManageModel(employeeManageModel);
    }

    private boolean isEmployeeNameValid(String employeeName) {
        if (employeeName.isEmpty()) {
            employeePanel.showErrorMessage(Messages.getInstance().EMPLOYEE_NAME_EMPTY);
            return false;
        }
        return true;
    }

    private boolean isEmployeePhoneNumValid(String employeePhoneNum) {
        PhoneValidator.PhoneValidateResult phoneValidateResult
                = PhoneValidator.validate(employeePhoneNum);

        switch (phoneValidateResult) {
            case EMPTY: {
                employeePanel.showErrorMessage(Messages.getInstance().EMPLOYEE_PHONE_NUMBER_EMPTY);
                return false;
            }
            case ERROR_FORMAT: {
                employeePanel.showErrorMessage(Messages.getInstance().EMPLOYEE_PHONE_NUMBER_FORMAT);
                return false;
            }
            case INVALLID: {
                employeePanel.showErrorMessage(Messages.getInstance().EMPLOYEE_PHONE_NUMBER_DIGITS_1
                        + PhoneValidator.getPhoneNumValid() + Messages.getInstance().EMPLOYEE_PHONE_NUMBER_DIGITS_2);
                return false;
            }
        }
        return true;
    }

    private boolean isEmployeePersonalIDValid(String employeePersonalID) {
        PersonalIDValidator.PersonalIDValidateResult personalIDValidateResult
                = PersonalIDValidator.validate(employeePersonalID);

        switch (personalIDValidateResult) {
            case EMPTY: {
                employeePanel.showErrorMessage(Messages.getInstance().EMPLOYEE_PID_EMPTY);
                return false;
            }
            case ERROR_FORMAT: {
                employeePanel.showErrorMessage(Messages.getInstance().EMPLOYEE_PID_FORMAT);
                return false;
            }
            case INVALLID: {
                employeePanel.showErrorMessage(Messages.getInstance().EMPLOYEE_PID_DIGITS_1
                        + PersonalIDValidator.getPhoneNumValid() + Messages.getInstance().EMPLOYEE_PID_DIGITS_2);
                return false;
            }
        }

        Iterator<EmployeeModelInterface> iterator = employeeManageModel.getAllEmployeeData();
        while (iterator.hasNext()) {
            EmployeeModelInterface employee = iterator.next();
            if (employee.getPersonalID().equals(employeePersonalID)) {
                employeePanel.showErrorMessage(Messages.getInstance().EMPLOYEE_PID_EXISTS);
                return false;
            }
        }

        return true;
    }

    private boolean isEmployeeEmailValid(String employeeEmail) {
        EmailValidator.EmailValidateResult emailValidateResult
                = EmailValidator.validate(employeeEmail);

        switch (emailValidateResult) {
            case EMPTY: {
                employeePanel.showErrorMessage(Messages.getInstance().EMPLOYEE_EMAIL_EMPTY);
                return false;
            }
            case INVALLID: {
                employeePanel.showErrorMessage(Messages.getInstance().EMPLOYEE_EMAIL_INVALID);
                return false;
            }
        }

        Iterator<EmployeeModelInterface> iterator = employeeManageModel.getAllEmployeeData();
        while (iterator.hasNext()) {
            EmployeeModelInterface employee = iterator.next();
            if (employee.getEmail().equals(employeeEmail)) {
                employeePanel.showErrorMessage(Messages.getInstance().EMPLOYEE_EMAIL_EXISTS);
                return false;
            }
        }

        return true;
    }

    private boolean isEmployeeBirthdayValid(Date birthday) {
        if (birthday == null) {
            employeePanel.showErrorMessage(Messages.getInstance().EMPLOYEE_BIRTHDAY_EMPTY);
            return false;
        }

        LocalDate birthdayLocal = birthday.toInstant().atZone(ZoneId
                .systemDefault()).toLocalDate();

        LocalDate nowLocal = LocalDate.now();

        int yearDistance = nowLocal.getYear() - birthdayLocal.getYear();

        if (yearDistance > 18) {
            return true;
        }

        if (yearDistance == 18) {
            int monthDistance = nowLocal.getMonthValue() - birthdayLocal.getMonthValue();
            if (monthDistance > 0) {
                return true;
            }

            if (monthDistance == 0) {
                int dateDistance = nowLocal.getDayOfMonth() - birthdayLocal.getDayOfMonth();
                if (dateDistance >= 0) {
                    return true;
                }
            }
        }

        employeePanel.showErrorMessage(Messages.getInstance().EMPLOYEE_UNDER_18);
        return false;
    }

    private boolean isEmployeeStartDateValid(Date employeeStartDate) {
        if (employeeStartDate == null) {
            employeePanel.showErrorMessage(Messages.getInstance().EMPLOYEE_START_DATE_EMPTY);
            return false;
        }

        LocalDate startDateLocal = employeeStartDate.toInstant().atZone(ZoneId
                .systemDefault()).toLocalDate();

        LocalDate nowLocal = LocalDate.now();

        if (startDateLocal.isBefore(nowLocal)) {
            employeePanel.showErrorMessage(Messages.getInstance().EMPLOYEE_START_DATE_INVALID);
            return false;
        }

        return true;
    }

    private boolean isEmployeeEndDateValid(Date employeeStartDate, Date employeeEndDate) {
        if (employeeEndDate == null) {
            return true;
        }

        LocalDate startDateLocal = employeeStartDate.toInstant().atZone(ZoneId
                .systemDefault()).toLocalDate();

        LocalDate endDateLocal = employeeEndDate.toInstant().atZone(ZoneId
                .systemDefault()).toLocalDate();

        if (endDateLocal.isBefore(startDateLocal)) {
            employeePanel.showErrorMessage(Messages.getInstance().EMPLOYEE_END_DATE_INVALID);
            return false;
        }

        return true;
    }

    private boolean isEmployeeShiftValid(List<String> shiftNameSelected) {
        if (shiftNameSelected.isEmpty()) {
            employeePanel.showErrorMessage(Messages.getInstance().EMPLOYEE_SHIFT_EMPTY);
            return false;
        }
        return true;
    }

    private boolean haveDigitsInName(String name) {
        for (int i = 0; i < name.length(); i++) {
            if (Character.isDigit(name.charAt(i))) {
                employeePanel.showErrorMessage(Messages.getInstance().EMPLOYEE_NAME_INVALID);
                return true;
            }
        }
        return false;
    }

    @Override
    public void requestCreateEmployee() {
        String employeeIDText = employeePanel.getEmployeeIDText();

        String employeeName = employeePanel.getEmployeeNameInput();
        employeeName = StringUtil.getCapitalizeWord(employeeName.trim());

        if (haveDigitsInName(employeeName)) {
            return;
        }
        if (!isEmployeeNameValid(employeeName)) {
            return;
        }

        String employeePhoneNum = employeePanel.getEmployeePhoneNumInput();
        employeePhoneNum = employeePhoneNum.trim();

        if (!isEmployeePhoneNumValid(employeePhoneNum)) {
            return;
        }

        String employeePersonalID = employeePanel.getEmployeePersonalIDInput();
        employeePersonalID = employeePersonalID.trim();

        if (!isEmployeePersonalIDValid(employeePersonalID)) {
            return;
        }

        String employeeEmail = employeePanel.getEmployeeEmailInput();
        employeeEmail = employeeEmail.trim();

        if (!isEmployeeEmailValid(employeeEmail)) {
            return;
        }

        boolean isMale = employeePanel.getEmployeeGenderInput();

        String employeePositionName = employeePanel.getEmployeePositionNameSelected();

        boolean status = employeePanel.getEmployeeStatusInput();

        Date birthday = employeePanel.getEmployeeBirthdayInput();

        if (!isEmployeeBirthdayValid(birthday)) {
            return;
        }

        Date startDate = employeePanel.getEmployeeStartDateInput();

        if (!isEmployeeStartDateValid(startDate)) {
            return;
        }

        Date endDate = employeePanel.getEmployeeEndDateInput();

        if (!isEmployeeEndDateValid(startDate, endDate)) {
            return;
        }

        List<String> shiftNameSelected = employeePanel.getEmployeeShiftNameSelected();

        if (!isEmployeeShiftValid(shiftNameSelected)) {
            return;
        }

        EmployeeModelInterface employee = new EmployeeModel();
        employee.setEmployeeID(employeeIDText);
        employee.setName(employeeName);
        employee.setPhoneNum(employeePhoneNum);
        employee.setPersonalID(employeePersonalID);
        employee.setEmail(employeeEmail);
        employee.setGender(isMale);
        employee.setPositionName(employeePositionName);
        employee.setStatus(status);

        employee.setBirthday(new java.sql.Date(birthday.getTime()));

        employee.setStartDate(new java.sql.Date(startDate.getTime()));

        if (endDate != null) {
            employee.setEndDate(new java.sql.Date(endDate.getTime()));
        }

        String randomPlaintextPassword = employee.randomPassword();

        this.employeeManageModel.addEmployee(employee);

        // if save to data successfully, then notify employee generated password sent to email
        MailUtility.sendPasswordNewEmployee(employeeEmail, randomPlaintextPassword);
        randomPlaintextPassword = "";
        for (int i = 0; i < shiftNameSelected.size(); i++) {
            ShiftDetailModelInterface shiftDetail = new ShiftDetailModel();
            shiftDetail.setEmployee(employee);
            shiftDetail.setShiftName(shiftNameSelected.get(i));
            employee.addShiftDetail(shiftDetail);
        }

        employeePanel.exitEditState();
        employeePanel.showInfoMessage(Messages.getInstance().EMPLOYEE_INSERTED_SUCCESSFULLY);
        PhoneValidator.setValidDigitNum(10);
        PersonalIDValidator.setValidDigitNum(12);
    }

    @Override
    public void requestUpdateEmployee() {
        String employeeIDText = employeePanel.getEmployeeIDText();

        EmployeeModelInterface employee = employeeManageModel
                .getEmployeeByID(employeeIDText);

        String employeeName = employeePanel.getEmployeeNameInput();
        employeeName = StringUtil.getCapitalizeWord(employeeName.trim());

        if (haveDigitsInName(employeeName)) {
            return;
        }

        if (!isEmployeeNameValid(employeeName)) {
            return;
        }

        String employeePhoneNum = employeePanel.getEmployeePhoneNumInput();
        employeePhoneNum = employeePhoneNum.trim();

        if (!employeePhoneNum.equals(employee.getPhoneNum())) {
            if (!isEmployeePhoneNumValid(employeePhoneNum)) {
                return;
            }
        }

        String employeePersonalID = employeePanel.getEmployeePersonalIDInput();
        employeePersonalID = employeePersonalID.trim();

        if (!employeePersonalID.equals(employee.getPersonalID())) {
            if (!isEmployeePersonalIDValid(employeePersonalID)) {
                return;
            }
        }

        String employeeEmail = employeePanel.getEmployeeEmailInput();
        employeeEmail = employeeEmail.trim();

        if (!employee.getEmail().equals(employeeEmail)) {
            if (!isEmployeeEmailValid(employeeEmail)) {
                return;
            }
        }

        boolean isMale = employeePanel.getEmployeeGenderInput();

        String employeePositionName = employeePanel.getEmployeePositionNameSelected();

        boolean status = employeePanel.getEmployeeStatusInput();

        Date birthday = employeePanel.getEmployeeBirthdayInput();

        if (!isEmployeeBirthdayValid(birthday)) {
            return;
        }

        Date startDate = employeePanel.getEmployeeStartDateInput();

        if (startDate == null) {
            employeePanel.showErrorMessage(Messages.getInstance().EMPLOYEE_START_DATE_EMPTY);
            return;
        }

        LocalDate startDateLocal = startDate.toInstant().atZone(ZoneId
                .systemDefault()).toLocalDate();

        if (startDateLocal.isBefore(employee.getStartDate().toLocalDate())) {
            employeePanel.showErrorMessage(Messages.getInstance().EMPLOYEE_START_DATE_INVALID);
            return;
        }

        Date endDate = employeePanel.getEmployeeEndDateInput();

        if (!isEmployeeEndDateValid(startDate, endDate)) {
            return;
        }

        List<String> shiftNameSelected = employeePanel.getEmployeeShiftNameSelected();

        if (!isEmployeeShiftValid(shiftNameSelected)) {
            return;
        }

        employee.setName(employeeName);
        employee.setPhoneNum(employeePhoneNum);
        employee.setPersonalID(employeePersonalID);
        employee.setEmail(employeeEmail);
        employee.setGender(isMale);
        employee.setPositionName(employeePositionName);
        employee.setStatus(status);

        employee.setBirthday(new java.sql.Date(birthday.getTime()));

        employee.setStartDate(new java.sql.Date(startDate.getTime()));

        if (endDate != null) {
            employee.setEndDate(new java.sql.Date(endDate.getTime()));
        }

        this.employeeManageModel.updateEmployee(employee);

        List<ShiftDetailModelInterface> selectedShiftDetails = new ArrayList<>();

        for (String shiftName : shiftNameSelected) {
            ShiftDetailModelInterface shiftDetail = new ShiftDetailModel();
            shiftDetail.setEmployee(employee);
            shiftDetail.setShiftName(shiftName);
            selectedShiftDetails.add(shiftDetail);
        }

        List<ShiftDetailModelInterface> employeeShiftDetails = employee.getShiftDetails();

        // Find deleted shift detail
        List<ShiftDetailModelInterface> deletedShiftDetailList = new ArrayList<>();

        for (int i = 0; i < employeeShiftDetails.size(); i++) {
            ShiftDetailModelInterface shiftDetail = employeeShiftDetails.get(i);
            int id = selectedShiftDetails.indexOf(shiftDetail);
            if (id == -1) {
                deletedShiftDetailList.add(shiftDetail);
            }
        }

        deletedShiftDetailList.forEach(shiftDetail -> {
            employee.removeShiftDetail(shiftDetail);
        });

        // Find inserted shift detail
        for (int i = 0; i < selectedShiftDetails.size(); i++) {
            boolean found = false;
            for (ShiftDetailModelInterface shiftDetail : employeeShiftDetails) {
                if (selectedShiftDetails.get(i).equals(shiftDetail)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                ShiftDetailModelInterface insertedShiftDetail = selectedShiftDetails.get(i);
                employee.addShiftDetail(insertedShiftDetail);
            }
        }

        employeePanel.exitEditState();
        employeePanel.showInfoMessage(Messages.getInstance().EMPLOYEE_UPDATED_SUCCESSFULLY);
        PhoneValidator.setValidDigitNum(10);
        PersonalIDValidator.setValidDigitNum(12);
    }

    @Override
    public void requestImportExcel() {
        ExcelTransfer.importExcelFileToTable(employeePanel.getTableEmployee());
    }

    @Override
    public void requestExportExcel() {
        if (employeePanel.getTableEmployeeRowCount() == 0) {
            employeePanel.showErrorMessage(Messages.getInstance().EMPLOYEE_TABLE_EMPTY);
        } else {
            ExcelTransfer.exportTableToExcel(employeePanel.getTableEmployee());
        }
    }

    @Override
    public void requestCreateTemplateExcel() {
        ExcelTransfer.createExcelFileTemplate(employeePanel.getTableEmployee());
    }

    @Override
    public void requestShowEmployeeInfo() {
        if (employeePanel.getEditState() == EmployeePanel.EditState.ADD) {
            return;
        }
        int rowID = employeePanel.getSelectdRow();
        if (rowID == -1) {
            return;
        }
        if (rowID >= searchList.size()) {
            throw new IllegalArgumentException("Row index is not in bound.");
        }
        EmployeeModelInterface employee = this.searchList.get(rowID);
        employeePanel.showEmployeeInfo(employee);
    }

    @Override
    public boolean isSearchMatching(String searchText, EmployeeModelInterface employee) {
        if (employee == null) {
            throw new NullPointerException("Employee instance is null.");
        }
        boolean ret = searchText.isEmpty()
                || (FuzzySearch.weightedRatio(searchText, employee.getName())
                >= AppConstant.SEARCH_SCORE_CUT_OFF);
        if (ret) {
            this.searchList.add(employee);
        }
        return ret;
    }

    @Override
    public boolean deleteIngredientInSearchList(EmployeeModelInterface employee) {
        if (employee == null) {
            throw new NullPointerException("Product instance is null.");
        }
        int id = this.searchList.indexOf(employee);
        if (id >= 0) {
            this.searchList.remove(id);
            return true;
        }
        return false;
    }

    @Override
    public Iterator<EmployeeModelInterface> getAllEmployeeData() {
        Iterator<EmployeeModelInterface> iterator = this.employeeManageModel
                .getAllEmployeeData();
        this.searchList.clear();
        while (iterator.hasNext()) {
            this.searchList.add(iterator.next());
        }
        return this.searchList.iterator();
    }

    @Override
    public Iterator<EmployeeModelInterface> getEmployeeBySearchName(String searchText) {
        Iterator<EmployeeModelInterface> iterator = this.employeeManageModel
                .getEmployeeSearchByName(searchText);
        this.searchList.clear();
        while (iterator.hasNext()) {
            this.searchList.add(iterator.next());
        }
        return this.searchList.iterator();
    }

    @Override
    public boolean canCloseEmployeeManagePanel() {
        if (employeePanel.getEditState() == EmployeePanel.EditState.ADD
                || employeePanel.getEditState() == EmployeePanel.EditState.MODIFY) {
            int ret = JOptionPane.showConfirmDialog(employeePanel.getMainFrame(),
                    Messages.getInstance().EMPLOYEE_CANCEL_EDITING,
                    "BakeryMS",
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, AppConstant.IMAGE_ICON_MESSAGE_DIALOG_WARNING);
            if (ret == JOptionPane.YES_OPTION) {
                employeePanel.exitEditState();
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    @Override
    public void requestChangePersonalIDConstraint() {
        String inputText = (String) JOptionPane.showInputDialog(employeePanel
                .getMainFrame(), Messages.getInstance().EMPLOYEE_CUSTOM_PID_CONS,
                "BakeryMS", JOptionPane.PLAIN_MESSAGE, null, null, null);
        if (inputText != null && !inputText.isEmpty()) {
            int num;
            try {
                num = Integer.parseInt(inputText);
                if (num < 0) {
                    throw new NumberFormatException("Input value is invalid");
                }
            } catch (NumberFormatException ex) {
                employeePanel.showErrorMessage(Messages.getInstance().EMPLOYEE_INVALID_CUSTOM_PID_CONS_NUM);
                return;
            }
            PersonalIDValidator.setValidDigitNum(num);
        }
    }

    @Override
    public void requestChangePhoneNumConstraint() {
        String inputText = (String) JOptionPane.showInputDialog(employeePanel
                .getMainFrame(), Messages.getInstance().EMPLOYEE_CUSTOM_PHONE_NUMBER_CONS,
                "BakeryMS", JOptionPane.PLAIN_MESSAGE, null, null, null);
        if (inputText != null && !inputText.isEmpty()) {
            int num;
            try {
                num = Integer.parseInt(inputText);
                if (num < 0) {
                    throw new NumberFormatException("Input value is invalid");
                }
            } catch (NumberFormatException ex) {
                employeePanel.showErrorMessage(Messages.getInstance().EMPLOYEE_INVALID_CUSTOM_PHONE_NUMBER_CONS_NUM);
                return;
            }
            PhoneValidator.setValidDigitNum(num);
        }
    }

}
