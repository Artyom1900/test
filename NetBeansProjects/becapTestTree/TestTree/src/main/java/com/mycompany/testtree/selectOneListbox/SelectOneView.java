/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.testtree.selectOneListbox;

import db.DBTree;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import model.ProductInstance;
 

 
@Named
@SessionScoped
public class SelectOneView implements Serializable{
     
    private ProductInstance productInstance;
    private ProductInstance selected;
    private List<ProductInstance> listProductInstance;
     
    @Inject
    private DBTree dBTree;
     
    @PostConstruct
    public void init() {
        System.out.println("public class SelectOneView");
        listProductInstance = new ArrayList<>();
        //listProductInstance = getdBTree().getPI(10);
    }
 
    public void test(){
        System.out.println("print "+ selected);
    }
    
    public void loadFromDB(int key_id){
        System.out.println("public void loadFromDB(int "+key_id+")");
        listProductInstance = getdBTree().getPI(key_id);
        System.out.println(listProductInstance.size());
    }

    public ProductInstance getProductInstance() {
        return productInstance;
    }

    public void setProductInstance(ProductInstance productInstance) {
        this.productInstance = productInstance;
    }

    public List<ProductInstance> getListProductInstance() {
        return listProductInstance;
    }

    public void setListProductInstance(List<ProductInstance> listProductInstance) {
        this.listProductInstance = listProductInstance;
    }

    public DBTree getdBTree() {
        return dBTree;
    }

    public void setdBTree(DBTree dBTree) {
        this.dBTree = dBTree;
    }

    public ProductInstance getSelected() {
        return selected;
    }

    public void setSelected(ProductInstance selected) {
        this.selected = selected;
    }
    
    
    
}