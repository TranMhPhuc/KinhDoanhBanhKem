/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.messages;

import model.setting.AppSetting;
import model.setting.AppSetting.Language;
import model.setting.SettingUpdateObserver;

/**
 *
 * @author Minh Tu
 */
public class Messages implements SettingUpdateObserver {

    //=============Employee=============
    public String EMPLOYEE_NAME_EMPTY;
    public String EMPLOYEE_NAME_INVALID_FORMAT;
    public String EMPLOYEE_PHONE_NUMBER_EMPTY;
    public String EMPLOYEE_PHONE_NUMBER_FORMAT;
    public String EMPLOYEE_PHONE_NUMBER_DIGITS_1;
    public String EMPLOYEE_PHONE_NUMBER_DIGITS_2;
    public String EMPLOYEE_PID_EMPTY;
    public String EMPLOYEE_PID_FORMAT;
    public String EMPLOYEE_PID_DIGITS_1;
    public String EMPLOYEE_PID_DIGITS_2;
    public String EMPLOYEE_PID_EXISTS;
    public String EMPLOYEE_EMAIL_EMPTY;
    public String EMPLOYEE_EMAIL_INVALID;
    public String EMPLOYEE_EMAIL_EXISTS;
    public String EMPLOYEE_BIRTHDAY_EMPTY;
    public String EMPLOYEE_UNDER_18;
    public String EMPLOYEE_START_DATE_EMPTY;
    public String EMPLOYEE_START_DATE_INVALID;
    public String EMPLOYEE_END_DATE_INVALID;
    public String EMPLOYEE_SHIFT_EMPTY;
    public String EMPLOYEE_INSERTED_SUCCESSFULLY;
    public String EMPLOYEE_UPDATED_SUCCESSFULLY;
    public String EMPLOYEE_TABLE_EMPTY;
    public String EMPLOYEE_CANCEL_EDITING;
    public String PID_CONS;
    public String EMPLOYEE_INVALID_CUSTOM_PID_CONS_NUM;
    public String PHONE_CONS;
    public String EMPLOYEE_INVALID_CUSTOM_PHONE_NUMBER_CONS_NUM;
    public String EMPLOYEE_NO_EMPLOYEE_CHOSEN;
    //=============Profile=============
    public String PROFILE_EMAIL_EMPTY;
    public String PROFILE_EMAIL_INVALID;
    public String PROFILE_PHONE_NUMBER_EMPTY;
    public String PROFILE_PHONE_NUMBER_FORMAT;
    public String PROFILE_PHONE_NUMBER_DIGITS_1;
    public String PROFILE_PHONE_NUMBER_DIGITS_2;
    public String PROFILE_OLD_PASSWORD_EMPTY;
    public String PROFILE_OLD_PASSWORD_INCORRECT;
    public String PROFILE_NEW_PASSWORD_EMPTY;
    public String PROFILE_NEW_PASSWORD_VERIFICATION_EMPTY;
    public String PROFILE_NEW_PASSWORD_VERIFICATION_INCORRECT;
    public String PROFILE_CHANGE_PASSWORD_SUCCESSFULLY;
    public String PROFILE_SIGN_OUT_CONFIRMATION;
    public String PROFILE_EXIT_CONFIRMATION;
    public String PROFILE_CANCEL_EDITING;
    public String PROFILE_UPDATED_SUCCESSFULLY;

    //=============Bill=============
    public String BILL_NO_PRODUCT_CHOSEN;
    public String BILL_PRODUCT_OOS_1;
    public String BILL_PRODUCT_OOS_2;
    public String BILL_CANT_REMOVE_PRODUCT;
    public String BILL_CANT_CLEAR_PRODUCTS;
    public String BILL_CANT_EDIT_PRODUCT;
    public String BILL_NOT_ENOUGH_AMOUNT;
    public String BILL_CANT_EXPORT;
    public String BILL_NOT_ENOUGH_MONEY;
    public String BILL_GUEST_MONEY_WRONG_FORMAT;
    public String BILL_EXPORT_SUCCESSFULLY;

    //=============Bill History=============
    public String BILLH_LIST_EMPTY;
    public String BILLH_NO_BILL_CHOSEN;
    public String BILLH_DATE_RANGE_INVALID;

    //=============Ingredient=============
    public String INGR_DATE_TO_BEFORE;
    public String INGR_NAME_INVALID_FORMAT;
    public String INGR_DATA_LIST_EMPTY;
    public String INGR_NO_INGR_CHOSEN;
    public String INGR_IMPORT_DATE_INVALID;
    public String INGR_IMPORTED_SUCCESSFULLY;
    public String INGR_REQUEST_CREATE_PROVIDER;
    public String INGR_REQUEST_CREATE_ING_TYPE;
    public String INGR_CANT_LOAD_UNIT;
    public String INGR_NAME_EMPTY;
    public String INGR_NAME_EXISTS;
    public String INGR_COST_GREATER_THAN_0;
    public String INGR_COST_FORMAT;
    public String INGR_INSERTED_SUCCESSFULLY;
    public String INGR_UPDATED_SUCCESSFULLY;
    public String INGR_CANT_REMOVE_1;
    public String INGR_CANT_REMOVE_2;
    public String INGR_REMOVE_SUCCESSFULLY;
    public String INGR_TYPE_EMPTY;
    public String INGR_TYPE_EXISTS;
    public String INGR_TYPE_INSERTED_SUCCESSFULLY;
    public String INGR_TABLE_EMPTY;
    public String INGR_TYPE_NAME_INVALID_FORMAT;

    //=============Login=============
    public String LOGIN_EMAIL_EMPTY;
    public String LOGIN_EMAIL_INVALID;
    public String LOGIN_PASSWORD_EMPTY;
    public String LOGIN_EMAIL_PASSWORD_INCORRECT;
    public String LOGIN_SENT_PASSWORD_SUCCESSFULLY;
    public String LOGIN_EMAIL_NOT_AVILABLE;
    public String LOGIN_NO_PERMISSON;

    //=============Product=============
    public String PRODUCT_NAME_EMPTY;
    public String PRODUCT_NAME_INVALID_FORMAT;
    public String PRODUCT_EXISTS;
    public String PRODUCT_COST_LESS_THAN_1;
    public String PRODUCT_PRICE_LESS_THAN_1;
    public String PRODUCT_PRICE_10_COST;
    public String PRODUCT_COST_LESS_SMALLER_SIZE;
    public String PRODUCT_PRICE_LESS_SMALLER_SIZE;
    public String PRODUCT_COST_GREATER_BIGGER_SIZE;
    public String PRODUCT_PRICE_GREATER_BIGGER_SIZE;
    public String PRODUCT_COST_EMPTY;
    public String PRODUCT_COST_INVALID;
    public String PRODUCT_PRICE_EMPTY;
    public String PRODUCT_PRICE_INVALID;
    public String PRODUCT_INGR_LIST_EMPTY;
    public String PRODUCT_SAVE_INGR_LIST_EMPTY;
    public String PRODUCT_INSERTED_SUCCESSFULLY;
    public String PRODUCT_UPDATED_SUCCESSFULLY;
    public String PRODUCT_CANT_REMOVE;
    public String PRODUCT_REMOVED_SUCCESSFULLY;
    public String PRODUCT_NO_PRODUCT_CHOSEN;
    public String PRODUCT_NOT_ENOUGH_INGR_1;
    public String PRODUCT_NOT_ENOUGH_INGR_2;
    public String PRODUCT_PRODUCED_SUCCESSFULLY;
    public String PRODUCT_TABLE_EMPTY;
    public String PRODUCT_NO_INGR_CHOSEN;
    public String PRODUCT_NO_UNIT_CHOSEN;
    public String PRODUCT_AMOUNT_AL_1;
    public String PRODUCT_CANEL_EDITING;
    public String PRODUCT_DISCARD_CHANGE;

    //=============Provider=============
    public String PROVIDER_NAME_EMPTY;
    public String PROVIDER_NAME_INVALID_FORMAT;
    public String PROVIDER_NAME_EXISTS;
    public String PROVIDER_PHONE_NUMBER_EMPTY;
    public String PROVIDER_PHONE_NUMBER_FORMAT;
    public String PROVIDER_PHONE_NUMBER_DIGITS_1;
    public String PROVIDER_PHONE_NUMBER_DIGITS_2;
    public String PROVIDER_PHONE_NUMBER_EXISTS;
    public String PROVIDER_EMAIL_EMPTY;
    public String PROVIDER_EMAIL_INVALID;
    public String PROVIDER_EMAIL_EXISTS;
    public String PROVIDER_ADDRESS_EMPTY;
    public String PROVIDER_ADDRESS_EXISTS;
    public String PROVIDER_INSERTED_SUCCESSFULLY;
    public String PROVIDER_UPDATED_SUCCESSFULLY;
    public String PROVIDER_CANT_REMOVE;
    public String PROVIDER_REMOVED_SUCCESSFULLY;
    public String PROVIDER_TABLE_EMPTY;
    public String PROVIDER_CANCEL_EDITING;
    public String PROVIDER_NO_PROVIDER_CHOSEN;

    //=============Others=============
    public String OTHERS_PRINT_ERROR;
    public String OTHERS_REPLACE_EXCEL;
    public String OTHERS_REMOVE_INGR;
    public String OTHERS_REMOVE_PRODUCT;
    public String OTHERS_REMOVE_PROVIDER;
    public String CONS_ANNOUNCEMENT;

    public String DIAGNOSTIC_ROOT_STRING_FORMAT;
    public String DIAGNOSTIC_PRODUCT_PROBLEM_SUMMARY_STRING_FORMAT;
    public String DIAGNOSTIC_INGREDIENT_PROBLEM_SUMMARY_STRING_FORMAT;
    public String DIAGNOSTIC_PROVIDER_PROBLEM_SUMMARY_STRING_FORMAT;

    private volatile static Messages uniqueInstance;

    @Override
    public void updateSettingObserver() {
        Language language = AppSetting.getInstance().getAppLanguage();
        switch (language) {
            case ENGLISH: {
                EMPLOYEE_NAME_EMPTY = "Employee name must not be empty";

                PROFILE_EMAIL_EMPTY = "Email can't not be empty";
                PROFILE_EMAIL_INVALID = "Email is invalid";
                PROFILE_PHONE_NUMBER_EMPTY = "Phone number can't be empty";
                PROFILE_PHONE_NUMBER_FORMAT = "Phone number format is invalid";
                PROFILE_PHONE_NUMBER_DIGITS_1 = "Phone number must have exactly ";
                PROFILE_PHONE_NUMBER_DIGITS_2 = " digits. Default number of digits is 10, max is 20. You can "
                        + "change this constraint by clicking on the icon next to the text field. \n Note: It counts"
                        + " your constraint input as an exception for this time only.";
                PROFILE_OLD_PASSWORD_EMPTY = "Old password is required";
                PROFILE_OLD_PASSWORD_INCORRECT = "Old password is incorrect";
                PROFILE_NEW_PASSWORD_EMPTY = "New password is required";
                PROFILE_NEW_PASSWORD_VERIFICATION_EMPTY = "Verifying password is required";
                PROFILE_NEW_PASSWORD_VERIFICATION_INCORRECT = "Verifying password is incorrect";
                PROFILE_CHANGE_PASSWORD_SUCCESSFULLY = "Changed password successfully";
                PROFILE_SIGN_OUT_CONFIRMATION = "Are you sure you want to sign out?";
                PROFILE_EXIT_CONFIRMATION = "Are you sure you want to exit?";
                PROFILE_CANCEL_EDITING = "Cancel editing profile?";
                PROFILE_UPDATED_SUCCESSFULLY = "Updated the profile successfully";

                BILL_NO_PRODUCT_CHOSEN = "You should choose one product first";
                BILL_PRODUCT_OOS_1 = "Product ";
                BILL_PRODUCT_OOS_2 = " is out of stock";
                BILL_CANT_REMOVE_PRODUCT = "You should choose one selected product first";
                BILL_CANT_CLEAR_PRODUCTS = "Bill has no product";
                BILL_CANT_EDIT_PRODUCT = "You should choose one selected product first";
                BILL_NOT_ENOUGH_AMOUNT = "Product %s - %s has not enough amount";
                BILL_CANT_EXPORT = "Bill has no product!";
                BILL_NOT_ENOUGH_MONEY = "Guest's money is insufficient";
                BILL_GUEST_MONEY_WRONG_FORMAT = "Guest's money is format error";
                BILL_EXPORT_SUCCESSFULLY = "Created bill successfully";

                EMPLOYEE_PHONE_NUMBER_EMPTY = "Employee phone number is required";
                EMPLOYEE_PHONE_NUMBER_FORMAT = "Employee phone number format is invalid";
                EMPLOYEE_PHONE_NUMBER_DIGITS_1 = "Employee phone number must have exactly ";
                EMPLOYEE_PHONE_NUMBER_DIGITS_2 = " digits. Default number of digits is 10, max is 20. You can "
                        + "change this constraint by clicking on the icon next to the text field. \n Note: It counts"
                        + " your constraint input as an exception for this time only.";
                EMPLOYEE_PID_EMPTY = "Employee personal ID is required";
                EMPLOYEE_PID_FORMAT = "Employee personal ID format is invalid";
                EMPLOYEE_PID_DIGITS_1 = "Employee personal ID must have exactly ";
                EMPLOYEE_PID_DIGITS_2 = " digits. Default number of digits is 12, max is 20. You can "
                        + "change this constraint by clicking on the icon next to the text field. \n Note: It counts"
                        + " your constraint input as an exception for this time only.";
                EMPLOYEE_PID_EXISTS = "Employee personal ID already exists";
                EMPLOYEE_EMAIL_EMPTY = "Employee email is required";
                EMPLOYEE_EMAIL_INVALID = "Employee email format is invalid";
                EMPLOYEE_EMAIL_EXISTS = "Employee email already exists";
                EMPLOYEE_BIRTHDAY_EMPTY = "Employee birthday is required";
                EMPLOYEE_UNDER_18 = "Employee age is not enough 18";
                EMPLOYEE_START_DATE_EMPTY = "Employee start date is required";
                EMPLOYEE_START_DATE_INVALID = "Employee start date input is invalid";
                EMPLOYEE_END_DATE_INVALID = "Employee end date format is invalid";
                EMPLOYEE_SHIFT_EMPTY = "Employee shift is required";
                EMPLOYEE_INSERTED_SUCCESSFULLY = "Inserted new employee successfully";
                EMPLOYEE_TABLE_EMPTY = "Employee table is empty";
                EMPLOYEE_CANCEL_EDITING = "Cancel editting employee?";
                PID_CONS = "PID quantity constraint";
                EMPLOYEE_INVALID_CUSTOM_PID_CONS_NUM = "Input value is invalid";
                PHONE_CONS = "Mobile quantity constraint:";
                EMPLOYEE_INVALID_CUSTOM_PHONE_NUMBER_CONS_NUM = "Input value is invalid";
                EMPLOYEE_UPDATED_SUCCESSFULLY = "Updated the employee successfully";
                EMPLOYEE_NO_EMPLOYEE_CHOSEN = "You should choose one employee first";
                EMPLOYEE_NAME_INVALID_FORMAT = "Employee name can't contain anything but letter";

                BILLH_LIST_EMPTY = "Bill list is empty";
                BILLH_NO_BILL_CHOSEN = "You should choose one bill first";
                BILLH_DATE_RANGE_INVALID = "Date range is invalid";

                INGR_DATE_TO_BEFORE = "ERROR: DATE TO is before DATE FROM";
                INGR_DATA_LIST_EMPTY = "Ingredient data list is empty";
                INGR_NO_INGR_CHOSEN = "You should choose one ingredient first";
                INGR_IMPORT_DATE_INVALID = "Import date is invalid";
                INGR_IMPORTED_SUCCESSFULLY = "Imported the ingredient successfully";
                INGR_REQUEST_CREATE_PROVIDER = "Please insert new provider first";
                INGR_REQUEST_CREATE_ING_TYPE = "Please insert new ingredient type first";
                INGR_CANT_LOAD_UNIT = "ERROR: Can't load unit data";
                INGR_NAME_EMPTY = "Ingredient name is required";
                INGR_NAME_EXISTS = "Ingredient name already exists";
                INGR_COST_GREATER_THAN_0 = "Ingredient cost must be greater than 0";
                INGR_COST_FORMAT = "Ingredient cost format is not a number";
                INGR_INSERTED_SUCCESSFULLY = "Inserted new ingredient successfully";
                INGR_UPDATED_SUCCESSFULLY = "Updated the ingredient successfully";
                INGR_CANT_REMOVE_1 = "Can't remove the ingredient included in product";
                INGR_CANT_REMOVE_2 = "Can't remove the ingredient having been imported";
                INGR_REMOVE_SUCCESSFULLY = "Removed the ingredient successfully";
                INGR_TYPE_EMPTY = "Ingredient type is required";
                INGR_TYPE_EXISTS = "Ingredient type already exists";
                INGR_TYPE_INSERTED_SUCCESSFULLY = "Inserted new ingredient type successfully";
                INGR_TABLE_EMPTY = "Ingredient table is empty";
                INGR_NAME_INVALID_FORMAT = "Ingredient name can't contain special characters";
                INGR_TYPE_NAME_INVALID_FORMAT = "Ingredient type name can't contain special characters";

                LOGIN_EMAIL_EMPTY = "Please enter your email";
                LOGIN_EMAIL_INVALID = "Email format is invalid";
                LOGIN_PASSWORD_EMPTY = "Please enter your password";
                LOGIN_EMAIL_PASSWORD_INCORRECT = "Email or password is incorrect. Please try again!";
                LOGIN_SENT_PASSWORD_SUCCESSFULLY = "Your password has been sent to email successfully";
                LOGIN_EMAIL_NOT_AVILABLE = "This email is not linked to the database, please try again!";
                LOGIN_NO_PERMISSON = "You don't have permisson to login the program";

                PRODUCT_NAME_EMPTY = "Product name is required";
                PRODUCT_EXISTS = "Product with the same name can't have the same size value either";
                PRODUCT_COST_LESS_THAN_1 = "Product cost can't be less than 1";
                PRODUCT_PRICE_LESS_THAN_1 = "Product price can't be less than 1";
                PRODUCT_PRICE_10_COST = "Price must be at least 10% greater than cost";
                PRODUCT_COST_LESS_SMALLER_SIZE = "Product cost can't be less than the one having same name and smaller size.";
                PRODUCT_PRICE_LESS_SMALLER_SIZE = "Product price can't be less than the one having same name and smaller size ";
                PRODUCT_COST_GREATER_BIGGER_SIZE = "Product cost can't be greater than the one having same name and larger size ";
                PRODUCT_PRICE_GREATER_BIGGER_SIZE = "Product price can't be greater than the one having same name and larger size ";
                PRODUCT_COST_EMPTY = "Product cost is required";
                PRODUCT_COST_INVALID = "Product cost format is invalid";
                PRODUCT_PRICE_EMPTY = "Product price is required";
                PRODUCT_PRICE_INVALID = "Product price format is invalid";
                PRODUCT_INGR_LIST_EMPTY = "Ingredient list is already empty";
                PRODUCT_SAVE_INGR_LIST_EMPTY = "Product must have at least 1 ingredient";
                PRODUCT_INSERTED_SUCCESSFULLY = "Inserted new product successfully";
                PRODUCT_UPDATED_SUCCESSFULLY = "Updated the product successfully";
                PRODUCT_CANT_REMOVE = "Can't remove product included in bill";
                PRODUCT_REMOVED_SUCCESSFULLY = "Removed the product successfully";
                PRODUCT_NO_PRODUCT_CHOSEN = "Please choose one product first";
                PRODUCT_NOT_ENOUGH_INGR_1 = "Ingredient named '";
                PRODUCT_NOT_ENOUGH_INGR_2 = "' has not enough amount to produce";
                PRODUCT_PRODUCED_SUCCESSFULLY = "Request to produce product successfully";
                PRODUCT_TABLE_EMPTY = "Table product is empty";
                PRODUCT_NO_INGR_CHOSEN = "You should choose one ingredient first";
                PRODUCT_NO_UNIT_CHOSEN = "ERROR: No unit chosen";
                PRODUCT_AMOUNT_AL_1 = "Amount must be at least 1";
                PRODUCT_CANEL_EDITING = "Cancel editing product?";
                PRODUCT_DISCARD_CHANGE = "Discard your change?";
                PRODUCT_NAME_INVALID_FORMAT = "Product name can't contain special characters";

                PROVIDER_NAME_EMPTY = "Provider name is required";
                PROVIDER_NAME_EXISTS = "Provider name already exists";
                PROVIDER_PHONE_NUMBER_EMPTY = "Provider phone number is required";
                PROVIDER_PHONE_NUMBER_FORMAT = "Provider phone number format is invalid";
                PROVIDER_PHONE_NUMBER_DIGITS_1 = "Provider phone number must have exactly ";
                PROVIDER_PHONE_NUMBER_DIGITS_2 = " digits. Default number of digits is 10, max is 20. You can "
                        + "change this constraint by clicking on the icon next to the text field. \n Note: It counts"
                        + " your constraint input as an exception for this time only.";
                PROVIDER_PHONE_NUMBER_EXISTS = "Provider phone number already exists";
                PROVIDER_EMAIL_EMPTY = "Provider email is required";
                PROVIDER_EMAIL_INVALID = "Provider email is invalid";
                PROVIDER_EMAIL_EXISTS = "Provider email already exists";
                PROVIDER_ADDRESS_EMPTY = "Provider address is required";
                PROVIDER_ADDRESS_EXISTS = "Provider address already exists";
                PROVIDER_INSERTED_SUCCESSFULLY = "Inserted new provider successfully";
                PROVIDER_UPDATED_SUCCESSFULLY = "Updated the provider successfully";
                PROVIDER_CANT_REMOVE = "Can't remove the provider who provides existsting ingredient in database";
                PROVIDER_REMOVED_SUCCESSFULLY = "Removed the provider successfully";
                PROVIDER_TABLE_EMPTY = "Provider table is empty";
                PROVIDER_CANCEL_EDITING = "Cancel editing provider?";
                PROVIDER_NO_PROVIDER_CHOSEN = "You should choose one ingredient first";
                PROVIDER_NAME_INVALID_FORMAT = "Provider name can't contain special characters";

                OTHERS_PRINT_ERROR = "Print error: ";
                OTHERS_REPLACE_EXCEL = " already existsss.\nDo you want to replace it?";
                OTHERS_REMOVE_INGR = "Remove ingredient?";
                OTHERS_REMOVE_PRODUCT = "Remove product?";
                OTHERS_REMOVE_PROVIDER = "Remove provider?";
                CONS_ANNOUNCEMENT = "Enter a number (max = 20, min = 0)";

                DIAGNOSTIC_ROOT_STRING_FORMAT = "%d total problem(s) found";
                DIAGNOSTIC_PRODUCT_PROBLEM_SUMMARY_STRING_FORMAT = "%d product(s) having zero amount";
                DIAGNOSTIC_INGREDIENT_PROBLEM_SUMMARY_STRING_FORMAT = "%d ingredient(s) having zero amount";
                DIAGNOSTIC_PROVIDER_PROBLEM_SUMMARY_STRING_FORMAT = "%d provider(s) having no ingredient belongs to";
                break;
            }
            case VIETNAMESE: {
                EMPLOYEE_NAME_EMPTY = "Tên nhân viên không được để trống.";

                PROFILE_EMAIL_EMPTY = "Không được bỏ trống email";
                PROFILE_EMAIL_INVALID = "Email không hợp lệ";
                PROFILE_PHONE_NUMBER_EMPTY = "Không được bỏ trống SDT";
                PROFILE_PHONE_NUMBER_FORMAT = "SDT không hợp lệ";
                PROFILE_PHONE_NUMBER_DIGITS_1 = "SDT cần có đúng ";
                PROFILE_PHONE_NUMBER_DIGITS_2 = " chữ số. Ràng buộc mặc định là 10 chữ số, tối đa là 20. Bạn có thể thay đổi"
                        + " ràng buộc này bằng cách nhấn vào biểu tượng cạnh ô nhập liệu.\nLưu ý: Ràng buộc"
                        + " bạn nhập sẽ được tính là ngoại lệ, áp dụng duy nhất cho lần nhập liệu này.";
                PROFILE_OLD_PASSWORD_EMPTY = "Cần nhập mật khẩu cũ";
                PROFILE_OLD_PASSWORD_INCORRECT = "Mật khẩu cũ không đúng";
                PROFILE_NEW_PASSWORD_EMPTY = "Cần nhập mật khẩu mới";
                PROFILE_NEW_PASSWORD_VERIFICATION_EMPTY = "Cần xác nhận mật khẩu mới";
                PROFILE_NEW_PASSWORD_VERIFICATION_INCORRECT = "Xác nhận mật khẩu mới không trùng khớp";
                PROFILE_CHANGE_PASSWORD_SUCCESSFULLY = "Đổi mật khẩu thành công";
                PROFILE_SIGN_OUT_CONFIRMATION = "Bạn có chắc muốn đăng xuất";
                PROFILE_EXIT_CONFIRMATION = "Bạn có chắc muốn thoát?";
                PROFILE_CANCEL_EDITING = "Hủy chỉnh sửa hồ sơ?";
                PROFILE_UPDATED_SUCCESSFULLY = "Chỉnh sửa hồ sơ thành công";

                BILL_NO_PRODUCT_CHOSEN = "Bạn nên chọn một sản phẩm trước";
                BILL_PRODUCT_OOS_1 = "Sản phẩm ";
                BILL_PRODUCT_OOS_2 = " đã hết hàng";
                BILL_CANT_REMOVE_PRODUCT = "Bạn nên chọn một sản phẩm trước";
                BILL_CANT_CLEAR_PRODUCTS = "Hóa đơn không có sản phẩm nào!";
                BILL_CANT_EDIT_PRODUCT = "Bạn nên chọn một sản phẩm đã thêm vào hóa đơn trước";
                BILL_NOT_ENOUGH_AMOUNT = "Sản phẩm %s - %s không đủ số lượng";
                BILL_CANT_EXPORT = "Hóa đơn không có sản phẩm nào!";
                BILL_NOT_ENOUGH_MONEY = "Tiền của khách không đủ";
                BILL_GUEST_MONEY_WRONG_FORMAT = "Tiền khách nhập không đúng định dạng";
                BILL_EXPORT_SUCCESSFULLY = "Tạo hóa đơn thành công";

                EMPLOYEE_PHONE_NUMBER_EMPTY = "Cần nhập SDT của nhân viên";
                EMPLOYEE_PHONE_NUMBER_FORMAT = "SDT nhân viên không hợp lệ";
                EMPLOYEE_PHONE_NUMBER_DIGITS_1 = "SDT nhân viên cần có đúng ";
                EMPLOYEE_PHONE_NUMBER_DIGITS_2 = " chữ số. Ràng buộc mặc định là 10 chữ số, tối đa là 20. Bạn có thể thay đổi"
                        + " ràng buộc này bằng cách nhấn vào biểu tượng cạnh ô nhập liệu.\nLưu ý: Ràng buộc"
                        + " bạn nhập sẽ được tính là ngoại lệ, áp dụng duy nhất cho lần nhập liệu này.";
                EMPLOYEE_PID_EMPTY = "Cần nhập CCCD của nhân viên";
                EMPLOYEE_PID_FORMAT = "CCCD của nhân viên không hợp lệ";
                EMPLOYEE_PID_DIGITS_1 = "CCCD của nhân viên cần có đúng  ";
                EMPLOYEE_PID_DIGITS_2 = " chữ số. Ràng buộc mặc định là 12 chữ số, tối đa là 20. Bạn có thể thay đổi"
                        + " ràng buộc này bằng cách nhấn vào biểu tượng cạnh ô nhập liệu.\nLưu ý: Ràng buộc"
                        + " bạn nhập sẽ được tính là ngoại lệ, áp dụng duy nhất cho lần nhập liệu này.";
                EMPLOYEE_PID_EXISTS = "CCCD của nhân viên đã tồn tại";
                EMPLOYEE_EMAIL_EMPTY = "Cần nhập email của nhân viên";
                EMPLOYEE_EMAIL_INVALID = "Email của nhân viên không hợp lệ";
                EMPLOYEE_EMAIL_EXISTS = "Email của nhân viên đã tồn tại";
                EMPLOYEE_BIRTHDAY_EMPTY = "Cần nhập ngày sinh của nhân viên";
                EMPLOYEE_UNDER_18 = "Nhân viên không đủ 18 tuổi";
                EMPLOYEE_START_DATE_EMPTY = "Cần nhập ngày bắt đầu làm việc của nhân viên";
                EMPLOYEE_START_DATE_INVALID = "Ngày bắt đầu làm việc không hợp lệ";
                EMPLOYEE_END_DATE_INVALID = "Ngày kết thúc làm việc không hợp lệ";
                EMPLOYEE_SHIFT_EMPTY = "Cần nhập ca làm việc của nhân viên";
                EMPLOYEE_INSERTED_SUCCESSFULLY = "Thêm nhân viên mới thành công";
                EMPLOYEE_TABLE_EMPTY = "Bảng nhân viên không có dữ liệu";
                EMPLOYEE_CANCEL_EDITING = "Hủy chỉnh sửa nhân viên?";
                PID_CONS = "Ràng buộc số lượng của CCCD:";
                EMPLOYEE_INVALID_CUSTOM_PID_CONS_NUM = "Giá trị nhập vào không hợp lệ";
                PHONE_CONS = "Ràng buộc số lượng của SDT";
                EMPLOYEE_INVALID_CUSTOM_PHONE_NUMBER_CONS_NUM = "Giá trị nhập vào không hợp lệ";
                EMPLOYEE_UPDATED_SUCCESSFULLY = "Cập nhật nhân viên đã chọn thành công";
                EMPLOYEE_NO_EMPLOYEE_CHOSEN = "Bạn nên chọn một nhân viên trước";
                EMPLOYEE_NAME_INVALID_FORMAT = "Tên nhân viên không thể chứa gì khác ngoài chữ";

                BILLH_LIST_EMPTY = "Danh sách hóa đơn trống";
                BILLH_NO_BILL_CHOSEN = "Bạn nên chọn một hóa đơn trước";
                BILLH_DATE_RANGE_INVALID = "Ngày giới hạn không hợp lệ";

                INGR_DATE_TO_BEFORE = "LỖI: Ngày bắt đầu sau ngày kết thúc";
                INGR_DATA_LIST_EMPTY = "Danh sách nguyên liệu rỗng";
                INGR_NO_INGR_CHOSEN = "Bạn nên chọn một nguyên liệu trước";
                INGR_IMPORT_DATE_INVALID = "Ngày nhập không hợp lệ";
                INGR_IMPORTED_SUCCESSFULLY = "Nhập nguyên liệu đã chọn thành công";
                INGR_REQUEST_CREATE_PROVIDER = "Vui lòng thêm nhà cung cấp mới trước";
                INGR_REQUEST_CREATE_ING_TYPE = "Vui lòng thêm loại nguyên liệu mới trước";
                INGR_CANT_LOAD_UNIT = "LỖI: Không thể tải bảng đơn vị";
                INGR_NAME_EMPTY = "Cần nhập tên nguyên liệu";
                INGR_NAME_EXISTS = "Tên nguyên liệu đã tồn tại";
                INGR_COST_GREATER_THAN_0 = "Giá nguyên liệu phải lớn hơn 0";
                INGR_COST_FORMAT = "Giá nguyên liệu không phải là số";
                INGR_INSERTED_SUCCESSFULLY = "Thêm nguyên liệu mới thành công";
                INGR_UPDATED_SUCCESSFULLY = "Cập nhật nguyên liệu thành công";
                INGR_CANT_REMOVE_1 = "Không thể xóa nguyên liệu đã thuộc sản phẩm";
                INGR_CANT_REMOVE_2 = "Không thể xóa nguyên liệu đã nhập trước đó";
                INGR_REMOVE_SUCCESSFULLY = "Xóa nguyên liệu thành công";
                INGR_TYPE_EMPTY = "Cần nhập loại nguyên liệu";
                INGR_TYPE_EXISTS = "Loại nguyên liệu đã tồn tại";
                INGR_TYPE_INSERTED_SUCCESSFULLY = "Thêm loại nguyên liệu thành công";
                INGR_TABLE_EMPTY = "Bảng loại nguyên liệu trống";
                INGR_NAME_INVALID_FORMAT = "Tên nguyên liệu không thể chứa ký tự đặc biệt";
                INGR_TYPE_NAME_INVALID_FORMAT = "Tên loại nguyên liệu không thể chứa ký tự đặc biệt";

                LOGIN_EMAIL_EMPTY = "Vui lòng nhập email";
                LOGIN_EMAIL_INVALID = "Email không hợp lệ";
                LOGIN_PASSWORD_EMPTY = "Vui lòng nhập mật khẩu";
                LOGIN_EMAIL_PASSWORD_INCORRECT = "Email hoặc mật khẩu không chính xác, vui lòng thử lại";
                LOGIN_SENT_PASSWORD_SUCCESSFULLY = "Mật khẩu đã được gửi qua email thành công";
                LOGIN_EMAIL_NOT_AVILABLE = "Email này không được kết nối với CSDL, vui lòng thử lại";
                LOGIN_NO_PERMISSON = "Bạn không có quyền đăng nhập vào chương trình";

                PRODUCT_NAME_EMPTY = "Cần nhập tên sản phẩm";
                PRODUCT_EXISTS = "Sản phẩm có cùng tên không thể có cùng cả kích thước";
                PRODUCT_COST_LESS_THAN_1 = "Chi phí sản phẩm không thể nhỏ hơn 1";
                PRODUCT_PRICE_LESS_THAN_1 = "Giá sản phẩm không thể nhỏ hơn 1";
                PRODUCT_PRICE_10_COST = "Giá sản phẩm cần phải lớn hơn chi phí ít nhất 10%";
                PRODUCT_COST_LESS_SMALLER_SIZE = "Chi phí sản phẩm không thể nhỏ hơn cái có cùng tên nhưng kích thước nhỏ hơn";
                PRODUCT_PRICE_LESS_SMALLER_SIZE = "Giá sản phẩm không thể nhỏ hơn cái có cùng tên nhưng kích thước nhỏ hơn";
                PRODUCT_COST_GREATER_BIGGER_SIZE = "Chi phí sản phẩm không thể lớn hơn cái có cùng tên nhưng kích thước lớn hơn";
                PRODUCT_PRICE_GREATER_BIGGER_SIZE = "Giá sản phẩm không thể nhỏ hơn cái có cùng tên nhưng kích thước lớn hơn";
                PRODUCT_COST_EMPTY = "Cần nhập chi phí sản phẩm";
                PRODUCT_COST_INVALID = "Chi phí sản phẩm không hợp lệ";
                PRODUCT_PRICE_EMPTY = "Cần nhập giá sản phẩm";
                PRODUCT_PRICE_INVALID = "Giá sản phẩm không hợp lệ";
                PRODUCT_INGR_LIST_EMPTY = "Danh sách thành phần nguyên liệu đã trống, không thể xóa";
                PRODUCT_SAVE_INGR_LIST_EMPTY = "Sản phẩm cần có ít nhất 1 nguyên liệu";
                PRODUCT_INSERTED_SUCCESSFULLY = "Thêm sản phẩm mới thành công";
                PRODUCT_UPDATED_SUCCESSFULLY = "Chỉnh sửa sản phẩm thành công";
                PRODUCT_CANT_REMOVE = "Không thể xóa sản phẩm đã thuộc hóa đơn";
                PRODUCT_REMOVED_SUCCESSFULLY = "Xóa sản phẩm thành công";
                PRODUCT_NO_PRODUCT_CHOSEN = "Xin hãy chọn một sản phẩm trước";
                PRODUCT_NOT_ENOUGH_INGR_1 = "Nguyên liệu tên '";
                PRODUCT_NOT_ENOUGH_INGR_2 = "' không đủ số lượng để sản xuất sản phẩm";
                PRODUCT_PRODUCED_SUCCESSFULLY = "Sản xuất sản phẩm thành công";
                PRODUCT_TABLE_EMPTY = "Bảng sản phẩm trống";
                PRODUCT_NO_INGR_CHOSEN = "Bạn nên chọn 1 nguyên liệu thành phần trước";
                PRODUCT_NO_UNIT_CHOSEN = "LỖI: Không có đơn vị nào được chọn";
                PRODUCT_AMOUNT_AL_1 = "Số lượng ít nhất phải bằng 1";
                PRODUCT_CANEL_EDITING = "Hủy chỉnh sửa?";
                PRODUCT_DISCARD_CHANGE = "Bỏ những thay đổi đã thực hiện?";
                PRODUCT_NAME_INVALID_FORMAT = "Tên sản phẩm không thể chứa ký tự đặc biệt";

                PROVIDER_NAME_EMPTY = "Cần nhập tên nhà cung cấp";
                PROVIDER_NAME_EXISTS = "Tên nhà cung cấp đã tồn tại";
                PROVIDER_PHONE_NUMBER_EMPTY = "Cần nhập SDT nhà cung cấp";
                PROVIDER_PHONE_NUMBER_FORMAT = "SDT nhà cung cấp không hợp lệ";
                PROVIDER_PHONE_NUMBER_DIGITS_1 = "SDT nhà cung cấp cần có đúng ";
                PROVIDER_PHONE_NUMBER_DIGITS_2 = " chữ số. Ràng buộc mặc định là 10 chữ số, tối đa là 20. Bạn có thể thay đổi"
                        + " ràng buộc này bằng cách nhấn vào biểu tượng cạnh ô nhập liệu.\nLưu ý: Ràng buộc"
                        + " bạn nhập sẽ được tính là ngoại lệ, áp dụng duy nhất cho lần nhập liệu này.";
                PROVIDER_PHONE_NUMBER_EXISTS = "SDT nhà cung cấp đã tồn tại";
                PROVIDER_EMAIL_EMPTY = "Cần nhập email nhà cung cấp";
                PROVIDER_EMAIL_INVALID = "Email nhà cung cấp không hợp lệ";
                PROVIDER_EMAIL_EXISTS = "Email nhà cung cấp đã tồn tại";
                PROVIDER_ADDRESS_EMPTY = "Cần nhập địa chỉ nhà cung cấp";
                PROVIDER_ADDRESS_EXISTS = "Địa chỉ nhà cung cấp đã tồn tại";
                PROVIDER_INSERTED_SUCCESSFULLY = "Thêm nhà cung cấp mới thành công";
                PROVIDER_UPDATED_SUCCESSFULLY = "Chỉnh sửa nhà cung cấp thành công";
                PROVIDER_CANT_REMOVE = "Không thể xóa nhà cung cấp đang cung cấp nguyên liệu trong CSDL";
                PROVIDER_REMOVED_SUCCESSFULLY = "Xóa nhà cung cấp thành công";
                PROVIDER_TABLE_EMPTY = "Bảng nhà cung cấp trống";
                PROVIDER_CANCEL_EDITING = "Hủy chỉnh sửa nhà cung cấp?";
                PROVIDER_NO_PROVIDER_CHOSEN = "Bạn nên chọn một nhà cung cấp trước";
                PROVIDER_NAME_INVALID_FORMAT = "Tên nhà cung cấp không thể chứa ký tự đặc biệt";

                OTHERS_PRINT_ERROR = "Lỗi in: ";
                OTHERS_REPLACE_EXCEL = " đã tồn tại.\nBạn có muốn thay thế nó?";
                OTHERS_REMOVE_INGR = "Xóa nguyên liệu?";
                OTHERS_REMOVE_PRODUCT = "Xóa sản phẩm?";
                OTHERS_REMOVE_PROVIDER = "Xóa nhà cung cấp?";
                CONS_ANNOUNCEMENT = "Nhập số (Lớn nhất 20, nhỏ nhất 0)";

                DIAGNOSTIC_ROOT_STRING_FORMAT = "%d vấn đề được tìm thấy";
                DIAGNOSTIC_PRODUCT_PROBLEM_SUMMARY_STRING_FORMAT = "%d sản phẩm không có số lượng";
                DIAGNOSTIC_INGREDIENT_PROBLEM_SUMMARY_STRING_FORMAT = "%d nguyên liệu không có số lượng";
                DIAGNOSTIC_PROVIDER_PROBLEM_SUMMARY_STRING_FORMAT = "%d nhà cung cấp không cung cấp nguyên liệu nào";
                break;
            }
        }
    }

    public static Messages
            getInstance() {
        if (uniqueInstance == null) {
            synchronized (Messages.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new Messages();
                }
            }
        }
        return uniqueInstance;
    }
}
