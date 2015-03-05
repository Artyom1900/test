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
public class MatOut {
    
    private String tester;
    private String strDate;
    private String strReturnDate;

    public MatOut(String tester, String strDate, String strReturnDate) {
        this.tester = tester;
        this.strDate = strDate;
        this.strReturnDate = strReturnDate;
    }

    public String getTester() {
        return tester;
    }

    public void setTester(String tester) {
        this.tester = tester;
    }

    public String getStrDate() {
        return strDate;
    }

    public void setStrDate(String strDate) {
        this.strDate = strDate;
    }

    public String getStrReturnDate() {
        return strReturnDate;
    }

    public void setStrReturnDate(String strReturnDate) {
        this.strReturnDate = strReturnDate;
    }
    
}
