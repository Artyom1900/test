package db;

import yura.tree.ProductTree;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.sql.DataSource;
import model.Color;
import model.ProductInstance;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import yura.tree.ProductTree;

@Named
@RequestScoped
public class DBTree implements Serializable {

    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private TreeNode root;

    @Resource(lookup = "jdbc/Fone")
    private DataSource ds;

//    @ManagedProperty(value="#{productContextMenuView}")
//    private ContextMenuView contextMenuView; // +setter
    public DBTree() {
    }

    public void selectUsers() {
        System.out.println("selectUsers()");
        try {
            try {
                String sql = "select * from FONE.RIDER";

                conn = ds.getConnection();
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();

                while (rs.next()) {
                    System.out.println("" + rs.getString("RIDER_ID") + " " + rs.getString("RIDER_L_NAME")
                            + " " + rs.getString("RIDER_L_NAME"));
                }
            } finally {
                if (conn != null) {
                    conn.close();
                }
            }
        } catch (SQLException e) {
            System.out.println("Error " + e);
        }
    }

    public TreeNode getTree() {
        System.out.println("public void tree()");
        root = new DefaultTreeNode(new ProductTree(0, "Product", "0", null), null);
        try {
            try {
                String sql = "select pr_id,PR_NAME,LEVEL,parent_id from product start with parent_id is null connect by parent_id = prior pr_id";

                conn = ds.getConnection();
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();

                TreeNode node = root;

                while (rs.next()) {
                    ProductTree product = new ProductTree(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
                    TreeNode treeNode;
                    if (product.getParent() == null) {
                        treeNode = new DefaultTreeNode(product, root);
                        node = treeNode;
                    } else {
                        treeNode = new DefaultTreeNode(product, node);
                    }

                    System.out.println(treeNode);

//                    String s = "";
//                    for (int i=1;i<=4;i++){
//                        s+=rs.getString(i)+"  ";
//                    }
//                    System.out.println(s);
                }
                System.out.println(root);
            } finally {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("Error " + e);
        }

        return root;
    }

    public int addNewProduct(String name, boolean is_hier, String parent) {
        System.out.println("addNewProduct()");
        System.out.println("name= " + name);
        System.out.println("is_hier= " + is_hier);
        System.out.println("parent= " + parent);
        int newId = 0;
        try {
            try {
                String sql = "INSERT INTO FONE.PRODUCT (PR_NAME,IS_HIER,PARENT_ID)  "
                        + "VALUES ('" + name + "','" + is_hier + "', '" + parent + "')";
                System.out.println("Sql= " + sql);
                conn = ds.getConnection();
                // you can select key field by field index
                int[] colIdxes = {1};
                // or by field name
                String[] colNames = {"PR_ID"};

                ps = conn.prepareStatement(sql, colNames);
                //ps.setString(2, name);
                //ps.setString(3, Boolean.toString(is_hier));
                //ps.setString(4, parent);
                int affectedRows = ps.executeUpdate();

                System.out.println("affectedRows: " + affectedRows);

                if (affectedRows == 0) {
                    throw new SQLException("Creating user failed, no rows affected.");
                }

                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        newId = (int) generatedKeys.getLong(1);
                        System.out.println("generatedKeys.getLong(1)= " + newId);
                        //user.setId(generatedKeys.getLong(1));
                    } else {
                        throw new SQLException("Creating user failed, no ID obtained.");
                    }
                }

                // System.out.println("rs.getRow(): "+rs.getRow());
                // System.out.println("rs.getRow(): "+rs.getMetaData());
            } finally {
                if (conn != null) {
                    conn.close();
                }
            }
        } catch (SQLException e) {
            System.out.println("Error " + e);
        }
        return newId;
    }

    public boolean deleteNode(int id) {
        try {
            System.out.println("deleteNode(int " + id + ")");
            String sql = "DELETE FROM FONE.PRODUCT WHERE PR_ID = " + id;
            System.out.println("Sql= " + sql);
            conn = ds.getConnection();
            ps = conn.prepareStatement(sql);
            int affectedRows = 0;
            try {
            affectedRows = ps.executeUpdate();
            } catch (SQLIntegrityConstraintViolationException ex) {
                System.out.println("Error: " + ex);
                
                return false;
            }
            
            System.out.println("affectedRows: " + affectedRows);

            if (affectedRows == 0) {
                //throw new SQLException("Creating user failed, no rows affected.");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DBTree.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (conn!=null) try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBTree.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }
        return true;

    }
    
    public ArrayList<Color> getListColors(){
        ArrayList<Color> colors = new ArrayList<>();
        System.out.println("public ArrayList<Color> getListColors()");
        
        try {
            try {
                String sql = "select * from FONE.COLOR;";

                conn = ds.getConnection();
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();

                while (rs.next()) {
                    Color color = new Color();
                    color.setId(rs.getInt(1));
                    color.setName(rs.getString(2));
                    
                    System.out.println(color);

                    colors.add(color);
                }
            } finally {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("Error " + e);
        }

        return colors;
    }
    
    public List<ProductInstance> getPI(int key_id) {
        List<ProductInstance> list = new ArrayList<ProductInstance>();
        
        try {
            try {
                String sql = "select * from FONE.PRODUCT_INSTANCE where PR_ID="+key_id;

                conn = ds.getConnection();
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();

                while (rs.next()) {
                    ProductInstance productInstance = new ProductInstance();
                    productInstance.setId(rs.getInt(1));
                    productInstance.setSerialNumber(rs.getString(3));
                    list.add(productInstance);
                    System.out.println(productInstance);
                }
            } finally {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("Error " + e);
        }
        
        return list;
    }

}
