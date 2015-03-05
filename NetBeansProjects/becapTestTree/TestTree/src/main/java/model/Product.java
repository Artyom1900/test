package model;

import java.io.Serializable;

/**
 * Created by Jack on 26.02.2015.
 */
public class Product implements Serializable {
    public Product() {
    }

    private int id;
    private String name;
    private String hier;
    private Object parent;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHier() {
        return hier;
    }

    public void setHier(String hier) {
        this.hier = hier;
    }

    public Object getParent() {
        return parent;
    }

    public void setParent(Object parent) {
        this.parent = parent;
    }
}
