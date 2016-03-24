package tr.com.kepce.profile;

import java.util.Date;

public class User {

    private String id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Gender gender;
    private String phoneNumber;
    private Date birthday;
    private int weight;
    private int height;

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public Gender getGender() {
        return gender == null ? Gender.UNKNOWN : gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Date getBirthday() {
        return birthday;
    }

    public int getWeight() {
        return weight;
    }

    public int getHeight() {
        return height;
    }
}
