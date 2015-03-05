package base;

import db.DBTree;
import java.io.Serializable;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import model.Color;

@Named
@ApplicationScoped
public class BaseContext implements Serializable{
    ArrayList<Color> colors = null;

    public BaseContext() {
        System.out.println("public class BaseContext implements Serializable");
    }
    
    @PostConstruct
    public void init() {
        colors = new DBTree().getListColors();
    }
}
