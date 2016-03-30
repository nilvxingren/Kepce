package tr.com.kepce.profile;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import tr.com.kepce.BR;

public class User extends BaseObservable {

    @SerializedName("id")
    private String id;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("name")
    private String firstName;
    @SerializedName("surname")
    private String lastName;
    @SerializedName("gender")
    private Gender gender;
    @SerializedName("phone")
    private String phoneNumber;
    @SerializedName("birthday")
    private String birthday;
    @SerializedName("weight")
    private Integer weight;
    @SerializedName("height")
    private Integer height;

    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Bindable
    public String getFirstName() {
        return firstName;
    }

    @Bindable
    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Bindable
    public Gender getGender() {
        return gender == null ? Gender.UNKNOWN : gender;
    }

    @Bindable
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Bindable
    public String getBirthday() {
        return birthday;
    }

    @Bindable
    public Integer getWeight() {
        return weight;
    }

    @Bindable
    public Integer getHeight() {
        return height;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        notifyPropertyChanged(BR.firstName);
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        notifyPropertyChanged(BR.lastName);
    }

    public void setGender(Gender gender) {
        this.gender = gender;
        notifyPropertyChanged(BR.gender);
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        notifyPropertyChanged(BR.phoneNumber);
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
        notifyPropertyChanged(BR.birthday);
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
        notifyPropertyChanged(BR.weight);
    }

    public void setHeight(Integer height) {
        this.height = height;
        notifyPropertyChanged(BR.height);
    }
}
