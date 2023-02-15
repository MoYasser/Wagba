package com.example.mobileporg;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "profile")
public class Profile {

    @PrimaryKey
    @NonNull
    private String email;
    private String fullName;
    private String gender;

    public Profile(@NonNull String email, String fullName, String gender) {
        this.fullName = fullName;
        this.email = email;
        this.gender = gender;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
