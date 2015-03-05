/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.testtree.table;

/**
 *
 * @author artyompc
 */
public class MatTest {
    
    private String tester;
    private String wind;
    private String comment;
    private String changes;

    public MatTest(String tester, String wind, String comment, String changes) {
        this.tester = tester;
        this.wind = wind;
        this.comment = comment;
        this.changes = changes;
    }

    
    public String getTester() {
        return tester;
    }

    public void setTester(String tester) {
        this.tester = tester;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getChanges() {
        return changes;
    }

    public void setChanges(String changes) {
        this.changes = changes;
    }
}
