/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.testtree.table;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;

 
@Named
@ViewScoped
public class BasicView implements Serializable {
     
    private List<MatTest> matTest;
    private List<MatOut> matOut;
 
    @PostConstruct
    public void init() {
        matTest = new ArrayList<>();
        matTest.add(new MatTest("Adam", "7 m/s", "super", "non"));
        matTest.add(new MatTest("Adam", "7 m/s", "super", "non"));
        matTest.add(new MatTest("Adam", "7 m/s", "super", "non"));
        matTest.add(new MatTest("Adam", "7 m/s", "super", "non"));
        matTest.add(new MatTest("Adam", "7 m/s", "super", "non"));
        matTest.add(new MatTest("Adam", "7 m/s", "super", "non"));
        matTest.add(new MatTest("Adam", "7 m/s", "super", "non"));
        matTest.add(new MatTest("Adam", "7 m/s", "super", "non"));
        matTest.add(new MatTest("Adam", "7 m/s", "super", "non"));
        matTest.add(new MatTest("Adam", "7 m/s", "super", "non"));
        matTest.add(new MatTest("Adam", "7 m/s", "super", "non"));
        matTest.add(new MatTest("Adam", "7 m/s", "super", "non"));
        matTest.add(new MatTest("Adam", "7 m/s", "super", "non"));
        matTest.add(new MatTest("Adam", "7 m/s", "super", "non"));
        matTest.add(new MatTest("Adam", "7 m/s", "super", "non"));
        
        matOut = new ArrayList<>();
        matOut.add(new MatOut("Adam", "2015-01-01", "2015-01-02"));
        matOut.add(new MatOut("Adam", "2015-01-01", "2015-01-02"));
        matOut.add(new MatOut("Adam", "2015-01-01", "2015-01-02"));
        matOut.add(new MatOut("Adam", "2015-01-01", "2015-01-02"));
        matOut.add(new MatOut("Adam", "2015-01-01", "2015-01-02"));
        matOut.add(new MatOut("Adam", "2015-01-01", "2015-01-02"));
        matOut.add(new MatOut("Adam", "2015-01-01", "2015-01-02"));
        matOut.add(new MatOut("Adam", "2015-01-01", "2015-01-02"));
        matOut.add(new MatOut("Adam", "2015-01-01", "2015-01-02"));
        matOut.add(new MatOut("Adam", "2015-01-01", "2015-01-02"));
        matOut.add(new MatOut("Adam", "2015-01-01", "2015-01-02"));
        matOut.add(new MatOut("Adam", "2015-01-01", "2015-01-02"));
        matOut.add(new MatOut("Adam", "2015-01-01", "2015-01-02"));
        matOut.add(new MatOut("Adam", "2015-01-01", "2015-01-02"));
        matOut.add(new MatOut("Adam", "2015-01-01", "2015-01-02"));
        matOut.add(new MatOut("Adam", "2015-01-01", "2015-01-02"));
        matOut.add(new MatOut("Adam", "2015-01-01", "2015-01-02"));
        matOut.add(new MatOut("Adam", "2015-01-01", "2015-01-02"));
        matOut.add(new MatOut("Adam", "2015-01-01", "2015-01-02"));
        matOut.add(new MatOut("Adam", "2015-01-01", "2015-01-02"));
        matOut.add(new MatOut("Adam", "2015-01-01", "2015-01-02"));
        
        
    }

    public List<MatTest> getMatTest() {
        return matTest;
    }

    public void setMatTest(List<MatTest> matTest) {
        this.matTest = matTest;
    }

    public List<MatOut> getMatOut() {
        return matOut;
    }

    public void setMatOut(List<MatOut> matOut) {
        this.matOut = matOut;
    }
     
    
}