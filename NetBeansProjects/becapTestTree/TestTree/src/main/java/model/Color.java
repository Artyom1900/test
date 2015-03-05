package model;

import java.io.Serializable;

/**
 * Created by Jack on 26.02.2015.
 */
public class Color implements Serializable {

    public Color() {
    }

    private int id;
    private String name;

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
}
