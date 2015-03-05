package model;

import java.io.Serializable;

/**
 * Created by Jack on 26.02.2015.
 */
public class MaterialOut implements Serializable {
    public MaterialOut() {
    }

    private int id;
    private Rider rider;
    private ProductInstance productInstance;
    private String description;
    private long outDate;
    private long dueDate;
    private long returnDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Rider getRider() {
        return rider;
    }

    public void setRider(Rider rider) {
        this.rider = rider;
    }

    public ProductInstance getProductInstance() {
        return productInstance;
    }

    public void setProductInstance(ProductInstance productInstance) {
        this.productInstance = productInstance;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getOutDate() {
        return outDate;
    }

    public void setOutDate(long outDate) {
        this.outDate = outDate;
    }

    public long getDueDate() {
        return dueDate;
    }

    public void setDueDate(long dueDate) {
        this.dueDate = dueDate;
    }

    public long getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(long returnDate) {
        this.returnDate = returnDate;
    }
}
