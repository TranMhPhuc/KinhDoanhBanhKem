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
    public String INGR_COST_EMPTY;
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
    public String PROVIDER_NUM_EXISTS;

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
                INGR_COST_EMPTY = "Ingredient cost is required";
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
                PROVIDER_NUM_EXISTS = "Provider number already exists";
                
                OTHERS_PRINT_ERROR = "Print error: ";
                OTHERS_REPLACE_EXCEL = " already existsss.\nDo you want to replace it?";
                OTHERS_REMOVE_INGR = "Remove ingredient?";
                OTHERS_REMOVE_PRODUCT = "Remove product?";
                OTHERS_REMOVE_PROVIDER = "Remove provider?";
                CONS_ANNOUNCEMENT = "Enter a number (0..20)";

                DIAGNOSTIC_ROOT_STRING_FORMAT = "%d total problem(s) found";
                DIAGNOSTIC_PRODUCT_PROBLEM_SUMMARY_STRING_FORMAT = "%d product(s) having zero amount";
                DIAGNOSTIC_INGREDIENT_PROBLEM_SUMMARY_STRING_FORMAT = "%d ingredient(s) having zero amount";
                DIAGNOSTIC_PROVIDER_PROBLEM_SUMMARY_STRING_FORMAT = "%d provider(s) having no ingredient belongs to";
                break;
            }
            case VIETNAMESE: {
                EMPLOYEE_NAME_EMPTY = "T??n nh??n vi??n kh??ng ???????c ????? tr???ng.";

                PROFILE_EMAIL_EMPTY = "Kh??ng ???????c b??? tr???ng email";
                PROFILE_EMAIL_INVALID = "Email kh??ng h???p l???";
                PROFILE_PHONE_NUMBER_EMPTY = "Kh??ng ???????c b??? tr???ng SDT";
                PROFILE_PHONE_NUMBER_FORMAT = "SDT kh??ng h???p l???";
                PROFILE_PHONE_NUMBER_DIGITS_1 = "SDT c???n c?? ????ng ";
                PROFILE_PHONE_NUMBER_DIGITS_2 = " ch??? s???. R??ng bu???c m???c ?????nh l?? 10 ch??? s???, t???i ??a l?? 20. B???n c?? th??? thay ?????i"
                        + " r??ng bu???c n??y b???ng c??ch nh???n v??o bi???u t?????ng c???nh ?? nh???p li???u.\nL??u ??: R??ng bu???c"
                        + " b???n nh???p s??? ???????c t??nh l?? ngo???i l???, ??p d???ng duy nh???t cho l???n nh???p li???u n??y.";
                PROFILE_OLD_PASSWORD_EMPTY = "C???n nh???p m???t kh???u c??";
                PROFILE_OLD_PASSWORD_INCORRECT = "M???t kh???u c?? kh??ng ????ng";
                PROFILE_NEW_PASSWORD_EMPTY = "C???n nh???p m???t kh???u m???i";
                PROFILE_NEW_PASSWORD_VERIFICATION_EMPTY = "C???n x??c nh???n m???t kh???u m???i";
                PROFILE_NEW_PASSWORD_VERIFICATION_INCORRECT = "X??c nh???n m???t kh???u m???i kh??ng tr??ng kh???p";
                PROFILE_CHANGE_PASSWORD_SUCCESSFULLY = "?????i m???t kh???u th??nh c??ng";
                PROFILE_SIGN_OUT_CONFIRMATION = "B???n c?? ch???c mu???n ????ng xu???t";
                PROFILE_EXIT_CONFIRMATION = "B???n c?? ch???c mu???n tho??t?";
                PROFILE_CANCEL_EDITING = "H???y ch???nh s???a h??? s???";
                PROFILE_UPDATED_SUCCESSFULLY = "Ch???nh s???a h??? s?? th??nh c??ng";

                BILL_NO_PRODUCT_CHOSEN = "B???n n??n ch???n m???t s???n ph???m tr?????c";
                BILL_PRODUCT_OOS_1 = "S???n ph???m ";
                BILL_PRODUCT_OOS_2 = " ???? h???t h??ng";
                BILL_CANT_REMOVE_PRODUCT = "B???n n??n ch???n m???t s???n ph???m tr?????c";
                BILL_CANT_CLEAR_PRODUCTS = "H??a ????n kh??ng c?? s???n ph???m n??o!";
                BILL_CANT_EDIT_PRODUCT = "B???n n??n ch???n m???t s???n ph???m ???? th??m v??o h??a ????n tr?????c";
                BILL_NOT_ENOUGH_AMOUNT = "S???n ph???m %s - %s kh??ng ????? s??? l?????ng";
                BILL_CANT_EXPORT = "H??a ????n kh??ng c?? s???n ph???m n??o!";
                BILL_NOT_ENOUGH_MONEY = "Ti???n c???a kh??ch kh??ng ?????";
                BILL_GUEST_MONEY_WRONG_FORMAT = "Ti???n kh??ch nh???p kh??ng ????ng ?????nh d???ng";
                BILL_EXPORT_SUCCESSFULLY = "T???o h??a ????n th??nh c??ng";

                EMPLOYEE_PHONE_NUMBER_EMPTY = "C???n nh???p SDT c???a nh??n vi??n";
                EMPLOYEE_PHONE_NUMBER_FORMAT = "SDT nh??n vi??n kh??ng h???p l???";
                EMPLOYEE_PHONE_NUMBER_DIGITS_1 = "SDT nh??n vi??n c???n c?? ????ng ";
                EMPLOYEE_PHONE_NUMBER_DIGITS_2 = " ch??? s???. R??ng bu???c m???c ?????nh l?? 10 ch??? s???, t???i ??a l?? 20. B???n c?? th??? thay ?????i"
                        + " r??ng bu???c n??y b???ng c??ch nh???n v??o bi???u t?????ng c???nh ?? nh???p li???u.\nL??u ??: R??ng bu???c"
                        + " b???n nh???p s??? ???????c t??nh l?? ngo???i l???, ??p d???ng duy nh???t cho l???n nh???p li???u n??y.";
                EMPLOYEE_PID_EMPTY = "C???n nh???p CCCD c???a nh??n vi??n";
                EMPLOYEE_PID_FORMAT = "CCCD c???a nh??n vi??n kh??ng h???p l???";
                EMPLOYEE_PID_DIGITS_1 = "CCCD c???a nh??n vi??n c???n c?? ????ng  ";
                EMPLOYEE_PID_DIGITS_2 = " ch??? s???. R??ng bu???c m???c ?????nh l?? 12 ch??? s???, t???i ??a l?? 20. B???n c?? th??? thay ?????i"
                        + " r??ng bu???c n??y b???ng c??ch nh???n v??o bi???u t?????ng c???nh ?? nh???p li???u.\nL??u ??: R??ng bu???c"
                        + " b???n nh???p s??? ???????c t??nh l?? ngo???i l???, ??p d???ng duy nh???t cho l???n nh???p li???u n??y.";
                EMPLOYEE_PID_EXISTS = "CCCD c???a nh??n vi??n ???? t???n t???i";
                EMPLOYEE_EMAIL_EMPTY = "C???n nh???p email c???a nh??n vi??n";
                EMPLOYEE_EMAIL_INVALID = "Email c???a nh??n vi??n kh??ng h???p l???";
                EMPLOYEE_EMAIL_EXISTS = "Email c???a nh??n vi??n ???? t???n t???i";
                EMPLOYEE_BIRTHDAY_EMPTY = "C???n nh???p ng??y sinh c???a nh??n vi??n";
                EMPLOYEE_UNDER_18 = "Nh??n vi??n kh??ng ????? 18 tu???i";
                EMPLOYEE_START_DATE_EMPTY = "C???n nh???p ng??y b???t ?????u l??m vi???c c???a nh??n vi??n";
                EMPLOYEE_START_DATE_INVALID = "Ng??y b???t ?????u l??m vi???c kh??ng h???p l???";
                EMPLOYEE_END_DATE_INVALID = "Ng??y k???t th??c l??m vi???c kh??ng h???p l???";
                EMPLOYEE_SHIFT_EMPTY = "C???n nh???p ca l??m vi???c c???a nh??n vi??n";
                EMPLOYEE_INSERTED_SUCCESSFULLY = "Th??m nh??n vi??n m???i th??nh c??ng";
                EMPLOYEE_TABLE_EMPTY = "B???ng nh??n vi??n kh??ng c?? d??? li???u";
                EMPLOYEE_CANCEL_EDITING = "H???y ch???nh s???a nh??n vi??n?";
                PID_CONS = "R??ng bu???c s??? l?????ng c???a CCCD:";
                EMPLOYEE_INVALID_CUSTOM_PID_CONS_NUM = "Gi?? tr??? nh???p v??o kh??ng h???p l???";
                PHONE_CONS = "R??ng bu???c s??? l?????ng c???a SDT";
                EMPLOYEE_INVALID_CUSTOM_PHONE_NUMBER_CONS_NUM = "Gi?? tr??? nh???p v??o kh??ng h???p l???";
                EMPLOYEE_UPDATED_SUCCESSFULLY = "C???p nh???t nh??n vi??n ???? ch???n th??nh c??ng";
                EMPLOYEE_NO_EMPLOYEE_CHOSEN = "B???n n??n ch???n m???t nh??n vi??n tr?????c";
                EMPLOYEE_NAME_INVALID_FORMAT = "T??n nh??n vi??n kh??ng th??? ch???a g?? kh??c ngo??i ch???";

                BILLH_LIST_EMPTY = "Danh s??ch h??a ????n tr???ng";
                BILLH_NO_BILL_CHOSEN = "B???n n??n ch???n m???t h??a ????n tr?????c";
                BILLH_DATE_RANGE_INVALID = "Ng??y gi???i h???n kh??ng h???p l???";

                INGR_DATE_TO_BEFORE = "L???I: Ng??y b???t ?????u sau ng??y k???t th??c";
                INGR_DATA_LIST_EMPTY = "Danh s??ch nguy??n li???u r???ng";
                INGR_NO_INGR_CHOSEN = "B???n n??n ch???n m???t nguy??n li???u tr?????c";
                INGR_IMPORT_DATE_INVALID = "Ng??y nh???p kh??ng h???p l???";
                INGR_IMPORTED_SUCCESSFULLY = "Nh???p nguy??n li???u ???? ch???n th??nh c??ng";
                INGR_REQUEST_CREATE_PROVIDER = "Vui l??ng th??m nh?? cung c???p m???i tr?????c";
                INGR_REQUEST_CREATE_ING_TYPE = "Vui l??ng th??m lo???i nguy??n li???u m???i tr?????c";
                INGR_CANT_LOAD_UNIT = "L???I: Kh??ng th??? t???i b???ng ????n v???";
                INGR_NAME_EMPTY = "C???n nh???p t??n nguy??n li???u";
                INGR_NAME_EXISTS = "T??n nguy??n li???u ???? t???n t???i";
                INGR_COST_GREATER_THAN_0 = "Gi?? nguy??n li???u ph???i l???n h??n 0";
                INGR_COST_FORMAT = "Gi?? nguy??n li???u kh??ng ph???i l?? s???";
                INGR_COST_EMPTY = "Gi?? nguy??n li???u kh??ng ???????c ????? tr???ng";
                INGR_INSERTED_SUCCESSFULLY = "Th??m nguy??n li???u m???i th??nh c??ng";
                INGR_UPDATED_SUCCESSFULLY = "C???p nh???t nguy??n li???u th??nh c??ng";
                INGR_CANT_REMOVE_1 = "Kh??ng th??? x??a nguy??n li???u ???? thu???c s???n ph???m";
                INGR_CANT_REMOVE_2 = "Kh??ng th??? x??a nguy??n li???u ???? nh???p v??o kho";
                INGR_REMOVE_SUCCESSFULLY = "X??a nguy??n li???u th??nh c??ng";
                INGR_TYPE_EMPTY = "C???n nh???p lo???i nguy??n li???u";
                INGR_TYPE_EXISTS = "Lo???i nguy??n li???u ???? t???n t???i";
                INGR_TYPE_INSERTED_SUCCESSFULLY = "Th??m lo???i nguy??n li???u th??nh c??ng";
                INGR_TABLE_EMPTY = "B???ng lo???i nguy??n li???u tr???ng";
                INGR_NAME_INVALID_FORMAT = "T??n nguy??n li???u kh??ng th??? ch???a k?? t??? ?????c bi???t";
                INGR_TYPE_NAME_INVALID_FORMAT = "T??n lo???i nguy??n li???u kh??ng th??? ch???a k?? t??? ?????c bi???t";

                LOGIN_EMAIL_EMPTY = "Vui l??ng nh???p email";
                LOGIN_EMAIL_INVALID = "Email kh??ng h???p l???";
                LOGIN_PASSWORD_EMPTY = "Vui l??ng nh???p m???t kh???u";
                LOGIN_EMAIL_PASSWORD_INCORRECT = "Email ho???c m???t kh???u kh??ng ch??nh x??c, vui l??ng th??? l???i";
                LOGIN_SENT_PASSWORD_SUCCESSFULLY = "M???t kh???u ???? ???????c g???i qua email th??nh c??ng";
                LOGIN_EMAIL_NOT_AVILABLE = "Email n??y kh??ng ???????c k???t n???i v???i CSDL, vui l??ng th??? l???i";
                LOGIN_NO_PERMISSON = "B???n kh??ng c?? quy???n ????ng nh???p v??o ch????ng tr??nh";

                PRODUCT_NAME_EMPTY = "C???n nh???p t??n s???n ph???m";
                PRODUCT_EXISTS = "S???n ph???m c?? c??ng t??n kh??ng th??? c?? c??ng c??? k??ch th?????c";
                PRODUCT_COST_LESS_THAN_1 = "Chi ph?? s???n ph???m kh??ng th??? nh??? h??n 1";
                PRODUCT_PRICE_LESS_THAN_1 = "Gi?? s???n ph???m kh??ng th??? nh??? h??n 1";
                PRODUCT_PRICE_10_COST = "Gi?? s???n ph???m c???n ph???i l???n h??n chi ph?? ??t nh???t 10%";
                PRODUCT_COST_LESS_SMALLER_SIZE = "Chi ph?? s???n ph???m kh??ng th??? nh??? h??n c??i c?? c??ng t??n nh??ng k??ch th?????c nh??? h??n";
                PRODUCT_PRICE_LESS_SMALLER_SIZE = "Gi?? s???n ph???m kh??ng th??? nh??? h??n c??i c?? c??ng t??n nh??ng k??ch th?????c nh??? h??n";
                PRODUCT_COST_GREATER_BIGGER_SIZE = "Chi ph?? s???n ph???m kh??ng th??? l???n h??n c??i c?? c??ng t??n nh??ng k??ch th?????c l???n h??n";
                PRODUCT_PRICE_GREATER_BIGGER_SIZE = "Gi?? s???n ph???m kh??ng th??? nh??? h??n c??i c?? c??ng t??n nh??ng k??ch th?????c l???n h??n";
                PRODUCT_COST_EMPTY = "C???n nh???p chi ph?? s???n ph???m";
                PRODUCT_COST_INVALID = "Chi ph?? s???n ph???m kh??ng h???p l???";
                PRODUCT_PRICE_EMPTY = "C???n nh???p gi?? s???n ph???m";
                PRODUCT_PRICE_INVALID = "Gi?? s???n ph???m kh??ng h???p l???";
                PRODUCT_INGR_LIST_EMPTY = "Danh s??ch th??nh ph???n nguy??n li???u ???? tr???ng, kh??ng th??? x??a";
                PRODUCT_SAVE_INGR_LIST_EMPTY = "S???n ph???m c???n c?? ??t nh???t 1 nguy??n li???u";
                PRODUCT_INSERTED_SUCCESSFULLY = "Th??m s???n ph???m m???i th??nh c??ng";
                PRODUCT_UPDATED_SUCCESSFULLY = "Ch???nh s???a s???n ph???m th??nh c??ng";
                PRODUCT_CANT_REMOVE = "Kh??ng th??? x??a s???n ph???m ???? thu???c h??a ????n";
                PRODUCT_REMOVED_SUCCESSFULLY = "X??a s???n ph???m th??nh c??ng";
                PRODUCT_NO_PRODUCT_CHOSEN = "Xin h??y ch???n m???t s???n ph???m tr?????c";
                PRODUCT_NOT_ENOUGH_INGR_1 = "Nguy??n li???u t??n '";
                PRODUCT_NOT_ENOUGH_INGR_2 = "' kh??ng ????? s??? l?????ng ????? s???n xu???t s???n ph???m";
                PRODUCT_PRODUCED_SUCCESSFULLY = "S???n xu???t s???n ph???m th??nh c??ng";
                PRODUCT_TABLE_EMPTY = "B???ng s???n ph???m tr???ng";
                PRODUCT_NO_INGR_CHOSEN = "B???n n??n ch???n 1 nguy??n li???u th??nh ph???n tr?????c";
                PRODUCT_NO_UNIT_CHOSEN = "L???I: Kh??ng c?? ????n v??? n??o ???????c ch???n";
                PRODUCT_AMOUNT_AL_1 = "S??? l?????ng ??t nh???t ph???i b???ng 1";
                PRODUCT_CANEL_EDITING = "H???y ch???nh s???a?";
                PRODUCT_DISCARD_CHANGE = "B??? nh???ng thay ?????i ???? th???c hi???n?";
                PRODUCT_NAME_INVALID_FORMAT = "T??n s???n ph???m kh??ng th??? ch???a k?? t??? ?????c bi???t";

                PROVIDER_NAME_EMPTY = "C???n nh???p t??n nh?? cung c???p";
                PROVIDER_NAME_EXISTS = "T??n nh?? cung c???p ???? t???n t???i";
                PROVIDER_PHONE_NUMBER_EMPTY = "C???n nh???p SDT nh?? cung c???p";
                PROVIDER_PHONE_NUMBER_FORMAT = "SDT nh?? cung c???p kh??ng h???p l???";
                PROVIDER_PHONE_NUMBER_DIGITS_1 = "SDT nh?? cung c???p c???n c?? ????ng ";
                PROVIDER_PHONE_NUMBER_DIGITS_2 = " ch??? s???.\nR??ng bu???c t???i thi???u l?? 10 ch??? s??? (m???c ?????nh), t???i ??a l?? 20.\nB???n c?? th??? thay ?????i"
                        + " r??ng bu???c n??y b???ng c??ch nh???n v??o bi???u t?????ng c???nh ?? nh???p li???u.\nL??u ??: R??ng bu???c"
                        + " b???n nh???p s??? ???????c t??nh l?? ngo???i l???, ??p d???ng duy nh???t cho l???n nh???p li???u n??y.";
                PROVIDER_PHONE_NUMBER_EXISTS = "SDT nh?? cung c???p ???? t???n t???i";
                PROVIDER_EMAIL_EMPTY = "C???n nh???p email nh?? cung c???p";
                PROVIDER_EMAIL_INVALID = "Email nh?? cung c???p kh??ng h???p l???";
                PROVIDER_EMAIL_EXISTS = "Email nh?? cung c???p ???? t???n t???i";
                PROVIDER_ADDRESS_EMPTY = "C???n nh???p ?????a ch??? nh?? cung c???p";
                PROVIDER_ADDRESS_EXISTS = "?????a ch??? nh?? cung c???p ???? t???n t???i";
                PROVIDER_INSERTED_SUCCESSFULLY = "Th??m nh?? cung c???p m???i th??nh c??ng";
                PROVIDER_UPDATED_SUCCESSFULLY = "Ch???nh s???a nh?? cung c???p th??nh c??ng";
                PROVIDER_CANT_REMOVE = "Kh??ng th??? x??a nh?? cung c???p ??ang cung c???p nguy??n li???u trong CSDL";
                PROVIDER_REMOVED_SUCCESSFULLY = "X??a nh?? cung c???p th??nh c??ng";
                PROVIDER_TABLE_EMPTY = "B???ng nh?? cung c???p tr???ng";
                PROVIDER_CANCEL_EDITING = "H???y ch???nh s???a nh?? cung c???p?";
                PROVIDER_NO_PROVIDER_CHOSEN = "B???n n??n ch???n m???t nh?? cung c???p tr?????c";
                PROVIDER_NAME_INVALID_FORMAT = "T??n nh?? cung c???p kh??ng th??? ch???a k?? t??? ?????c bi???t";
                PROVIDER_NUM_EXISTS = "SDT nh?? cung c???p ???? t???n t???i";
                
                OTHERS_PRINT_ERROR = "L???i in: ";
                OTHERS_REPLACE_EXCEL = " ???? t???n t???i.\nB???n c?? mu???n thay th??? n???";
                OTHERS_REMOVE_INGR = "X??a nguy??n li???u?";
                OTHERS_REMOVE_PRODUCT = "X??a s???n ph???m?";
                OTHERS_REMOVE_PROVIDER = "X??a nh?? cung c???p?";
                CONS_ANNOUNCEMENT = "Nh???p s??? (0..20)";

                DIAGNOSTIC_ROOT_STRING_FORMAT = "%d v???n ????? ???????c t??m th???y";
                DIAGNOSTIC_PRODUCT_PROBLEM_SUMMARY_STRING_FORMAT = "%d s???n ph???m kh??ng c?? s??? l?????ng";
                DIAGNOSTIC_INGREDIENT_PROBLEM_SUMMARY_STRING_FORMAT = "%d nguy??n li???u kh??ng c?? s??? l?????ng";
                DIAGNOSTIC_PROVIDER_PROBLEM_SUMMARY_STRING_FORMAT = "%d nh?? cung c???p kh??ng cung c???p nguy??n li???u n??o";
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
