package model;

import java.io.File;

/**
 * Created by dev on 30/8/18.
 */

public class SalesBankDetailModel {

    private static SalesBankDetailModel instance;
    public String countryCode;
    public String currency = "";
    public String accountNo;
    public String sortCode = "";
    public String firstName = "";
    public String lastName = "";
    public String address = "";
    public String city = "";
    public String state = "";
    public String postalCode = "";
    public String dob = "";
    public boolean updateData;
    public String documentPath = "";
    public File documnetFile;
    public String swift = "";
    public String account_type = "";

    private SalesBankDetailModel() {
    }

    public static synchronized SalesBankDetailModel getInstance() {
        if (instance == null) {
            instance = new SalesBankDetailModel();
        }
        return instance;
    }

    public static void setSalesBankDetailModel(SalesBankDetailModel mModel) {
        instance = mModel;
    }

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }

    public String getSwift() {
        return swift;
    }

    public void setSwift(String swift) {
        this.swift = swift;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getSortCode() {
        return sortCode;
    }

    public void setSortCode(String sortCode) {
        this.sortCode = sortCode;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public boolean isUpdateData() {
        return updateData;
    }

    public void setUpdateData(boolean updateData) {
        this.updateData = updateData;
    }

    public String getDocumentPath() {
        return documentPath;
    }

    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath;
    }

    public File getDocumnetFile() {
        return documnetFile;
    }

    public void setDocumnetFile(File documnetFile) {
        this.documnetFile = documnetFile;
    }

    public void clearData() {
        instance.countryCode = "";
        instance.currency = "";
        instance.accountNo = null;
        instance.sortCode = "";
        instance.firstName = "";
        instance.lastName = "";
        instance.address = "";
        instance.city = "";
        instance.state = "";
        instance.postalCode = "";
        instance.dob = "";
        instance.swift = "";
        instance.documentPath = "";
        instance.documnetFile = null;
        SalesBankDetailModel.setSalesBankDetailModel(instance);
    }

}
