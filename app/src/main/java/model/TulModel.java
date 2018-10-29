package model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by applify on 10/13/2017.
 */

public class TulModel {

    public static ListTulModel mListTul;
    public static PreferencesTul mPrefrencesTul;
    public static BankDetailsTul mBankDetailsTul;

    public static ListTulModel getListTul() {
        if (mListTul == null)
            mListTul = new ListTulModel();
        return mListTul;
    }

    public static void setListTul(ListTulModel mListTul) {
        TulModel.mListTul = mListTul;
    }

    public static PreferencesTul getPrefrencesTul() {
        if (mPrefrencesTul == null)
            mPrefrencesTul = new PreferencesTul();
        return mPrefrencesTul;
    }

    public static void setPrefrencesTul(PreferencesTul mPrefrencesTul) {
        TulModel.mPrefrencesTul = mPrefrencesTul;
    }

    public static BankDetailsTul getBankDetailsTul() {
        if (mBankDetailsTul == null)
            mBankDetailsTul = new BankDetailsTul();
        return mBankDetailsTul;
    }

    public static void setBankDetailsTul(BankDetailsTul mPrefrencesTul) {
        TulModel.mBankDetailsTul = mBankDetailsTul;
    }

    public static class ListTulModel implements Parcelable {
        public static final Creator<ListTulModel> CREATOR = new Creator<ListTulModel>() {
            @Override
            public ListTulModel createFromParcel(Parcel source) {
                return new ListTulModel(source);
            }

            @Override
            public ListTulModel[] newArray(int size) {
                return new ListTulModel[size];
            }
        };
        public String title;
        public String category;
        public int categoryId;
        public String description;
        public String noOfTuls;
        public String price;
        public String earningAmount;
        public String tranastionPercentage;
        public String booking_days;
        public String discount;
        public String securityFee;
        public String convienceFee;
        public int active_bookings;
        public String address;
        public Double latitude;
        public Double longitude;
        public ArrayList<String> rules;
        public ArrayList<String> attachments_ids;
        public ArrayList<TulImageModel> imagesVideo;
        public Boolean updateData;
        public boolean isEdit;

        public ListTulModel() {
        }

        protected ListTulModel(Parcel in) {
            this.title = in.readString();
            this.category = in.readString();
            this.categoryId = in.readInt();
            this.description = in.readString();
            this.noOfTuls = in.readString();
            this.price = in.readString();
            this.earningAmount = in.readString();
            this.tranastionPercentage = in.readString();
            this.booking_days = in.readString();
            this.discount = in.readString();
            this.securityFee = in.readString();
            this.convienceFee = in.readString();
            this.active_bookings = in.readInt();
            this.address = in.readString();
            this.latitude = (Double) in.readValue(Double.class.getClassLoader());
            this.longitude = (Double) in.readValue(Double.class.getClassLoader());
            this.rules = in.createStringArrayList();
            this.attachments_ids = in.createStringArrayList();
            this.imagesVideo = in.createTypedArrayList(TulImageModel.CREATOR);
            this.updateData = (Boolean) in.readValue(Boolean.class.getClassLoader());
            this.isEdit = in.readByte() != 0;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.title);
            dest.writeString(this.category);
            dest.writeInt(this.categoryId);
            dest.writeString(this.description);
            dest.writeString(this.noOfTuls);
            dest.writeString(this.price);
            dest.writeString(this.earningAmount);
            dest.writeString(this.tranastionPercentage);
            dest.writeString(this.booking_days);
            dest.writeString(this.discount);
            dest.writeString(this.securityFee);
            dest.writeString(this.convienceFee);
            dest.writeInt(this.active_bookings);
            dest.writeString(this.address);
            dest.writeValue(this.latitude);
            dest.writeValue(this.longitude);
            dest.writeStringList(this.rules);
            dest.writeStringList(this.attachments_ids);
            dest.writeTypedList(this.imagesVideo);
            dest.writeValue(this.updateData);
            dest.writeByte(this.isEdit ? (byte) 1 : (byte) 0);
        }
    }

    public static class PreferencesTul implements Parcelable {
        public static final Creator<PreferencesTul> CREATOR = new Creator<PreferencesTul>() {
            @Override
            public PreferencesTul createFromParcel(Parcel source) {
                return new PreferencesTul(source);
            }

            @Override
            public PreferencesTul[] newArray(int size) {
                return new PreferencesTul[size];
            }
        };
        public String availbiltyMode;
        public String startTime;
        public String endTime;
        public String deliveryCharges;
        public String startPickUpTime;
        public String endPickUpTime;
        public boolean deleiveryAvailable;
        public boolean updateData;
        public boolean isEdit;

        public PreferencesTul() {
        }

        protected PreferencesTul(Parcel in) {
            this.availbiltyMode = in.readString();
            this.startTime = in.readString();
            this.endTime = in.readString();
            this.deliveryCharges = in.readString();
            this.startPickUpTime = in.readString();
            this.endPickUpTime = in.readString();
            this.deleiveryAvailable = in.readByte() != 0;
            this.updateData = in.readByte() != 0;
            this.isEdit = in.readByte() != 0;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.availbiltyMode);
            dest.writeString(this.startTime);
            dest.writeString(this.endTime);
            dest.writeString(this.deliveryCharges);
            dest.writeString(this.startPickUpTime);
            dest.writeString(this.endPickUpTime);
            dest.writeByte(this.deleiveryAvailable ? (byte) 1 : (byte) 0);
            dest.writeByte(this.updateData ? (byte) 1 : (byte) 0);
            dest.writeByte(this.isEdit ? (byte) 1 : (byte) 0);
        }
    }


    public static class BankDetailsTul implements Parcelable {
        public static final Creator<BankDetailsTul> CREATOR = new Creator<BankDetailsTul>() {
            @Override
            public BankDetailsTul createFromParcel(Parcel source) {
                return new BankDetailsTul(source);
            }

            @Override
            public BankDetailsTul[] newArray(int size) {
                return new BankDetailsTul[size];
            }
        };
        public String countryCode;
        public String currency;
        public String accountNo;
        public String sortCode;
        public String firstName;
        public String lastName;
        public String address;
        public String city;
        public String state;
        public String postalCode;
        public String dob;
        public boolean updateData;
        public String documentPath;
        public File documnetFile;
        public String swift;
        public String account_type;

        public BankDetailsTul() {
        }

        protected BankDetailsTul(Parcel in) {
            this.countryCode = in.readString();
            this.currency = in.readString();
            this.accountNo = in.readString();
            this.sortCode = in.readString();
            this.firstName = in.readString();
            this.lastName = in.readString();
            this.address = in.readString();
            this.city = in.readString();
            this.state = in.readString();
            this.postalCode = in.readString();
            this.dob = in.readString();
            this.updateData = in.readByte() != 0;
            this.documentPath = in.readString();
            this.documnetFile = (File) in.readSerializable();
            this.swift = in.readString();
            this.account_type = in.readString();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.countryCode);
            dest.writeString(this.currency);
            dest.writeString(this.accountNo);
            dest.writeString(this.sortCode);
            dest.writeString(this.firstName);
            dest.writeString(this.lastName);
            dest.writeString(this.address);
            dest.writeString(this.city);
            dest.writeString(this.state);
            dest.writeString(this.postalCode);
            dest.writeString(this.dob);
            dest.writeByte(this.updateData ? (byte) 1 : (byte) 0);
            dest.writeString(this.documentPath);
            dest.writeSerializable(this.documnetFile);
            dest.writeString(this.swift);
            dest.writeString(this.account_type);
        }
    }

}
