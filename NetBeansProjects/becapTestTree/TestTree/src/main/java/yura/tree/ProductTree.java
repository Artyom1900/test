package yura.tree;

import java.io.Serializable;

public class ProductTree implements Serializable{
    private int id;
    private String name;
    private String branch;
    private String parent;
    
    public ProductTree () {}

    public ProductTree(int id, String name, String branch, String parent) {
        this.id = id;
        this.name = name;
        this.branch = branch;
        this.parent = parent;
    }

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

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "Product{" + "id=" + id + ", name=" + name + ", branch=" + branch + ", parent=" + parent + '}';
    }
    
}
