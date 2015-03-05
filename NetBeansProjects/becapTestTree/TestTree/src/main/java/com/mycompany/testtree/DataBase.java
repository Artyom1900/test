/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.testtree;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.sql.DataSource;

@Named
@RequestScoped
public class DataBase {

   private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    @Resource(lookup = "jdbc/Fone")
    private DataSource ds;

    public void selectUsers() {
        System.out.println("public class DataBase ");
        try {
            try {
                String sql = "select * from FONE.RIDER";

                conn = ds.getConnection();
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();

                while (rs.next()) {
                    System.out.println("" + rs.getString("PR_ID"));
                }
            } finally {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("Error " + e);
        }
    }
}
