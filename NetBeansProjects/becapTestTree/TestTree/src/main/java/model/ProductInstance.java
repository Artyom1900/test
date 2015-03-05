package model;

import java.io.Serializable;

/**
 * Created by Jack on 26.02.2015.
 */
public class ProductInstance implements Serializable{
    public ProductInstance() {
    }

    private int id;
    private Product productWired;
    private String serialNumber;
    private Color color;
    private Size size;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProductWired() {
        return productWired;
    }

    public void setProductWired(Product productWired) {
        this.productWired = productWired;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "ProductInstance{" + "id=" + id + ", productWired=" + productWired + ", serialNumber=" + serialNumber + ", color=" + color + ", size=" + size + '}';
    }
    
}
