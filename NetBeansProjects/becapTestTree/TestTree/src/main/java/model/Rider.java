package model;

import java.io.Serializable;

/**
 * Created by Jack on 26.02.2015.
 */
public class Rider implements Serializable {

    public Rider() {
    }

    private int id;
    private String FName;
    private String LName;
    private String phone;
    private String mail;
    private String login;
    private String password;
    private Object parent;
    private boolean hier;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFName() {
        return FName;
    }

    public void setFName(String FName) {
        this.FName = FName;
    }

    public String getLName() {
        return LName;
    }

    public void setLName(String LName) {
        this.LName = LName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Object getParent() {
        return parent;
    }

    public void setParent(Object parent) {
        this.parent = parent;
    }

    public boolean isHier() {
        return hier;
    }

    public void setHier(boolean hier) {
        this.hier = hier;
    }
}
