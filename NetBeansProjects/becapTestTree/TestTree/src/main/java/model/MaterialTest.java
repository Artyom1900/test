package model;

import java.io.Serializable;

/**
 * Created by Jack on 26.02.2015.
 */
public class MaterialTest implements Serializable {
    public MaterialTest() {
    }
    private int id;
    private ProductInstance productInstance;
    private Rider rider;
    private String windForce;
    private String comment;
    private String designChanges;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ProductInstance getProductInstance() {
        return productInstance;
    }

    public void setProductInstance(ProductInstance productInstance) {
        this.productInstance = productInstance;
    }

    public Rider getRider() {
        return rider;
    }

    public void setRider(Rider rider) {
        this.rider = rider;
    }

    public String getWindForce() {
        return windForce;
    }

    public void setWindForce(String windForce) {
        this.windForce = windForce;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDesignChanges() {
        return designChanges;
    }

    public void setDesignChanges(String designChanges) {
        this.designChanges = designChanges;
    }
}
