/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yura.tree;

import com.mycompany.testtree.selectOneListbox.SelectOneView;
import db.DBTree;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

@Named
@SessionScoped
public class ContextViewTree implements Serializable {

    private TreeNode root;
    private TreeNode selectedNode;
    private String name="";
    @Inject
    private DBTree dBTree;
    @Inject
    private SelectOneView selectOneView;

    public ContextViewTree() {
        System.out.println("public ContextView()");
    }
    
    @PostConstruct
    public void init(){
        root = dBTree.getTree();

    }
    
    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {

        this.root = root;
    }

    public TreeNode getSelectedNode() {
        
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    public void displaySelectedSingle() {
        System.out.println("public void displaySelectedSingle()");
        System.out.println(selectedNode.getData().getClass());
        if (selectedNode != null) {
            String str = "";
            ProductTree product = (ProductTree) selectedNode.getData();
            str = "Id: " + product.getId() + " Name: " + product.getName() + " Branch: "+product.getBranch()+
                    " Parent: "+product.getParent();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected", str);
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }
    
    public void printNode() {
        System.out.println("public void printNode()");
        System.out.println(selectedNode);
        ProductTree productTree = (ProductTree) selectedNode.getData();
        selectOneView.loadFromDB(productTree.getId());
    }

    public void deleteNode() {
        System.out.println("public void deleteNode()");
        ProductTree product = (ProductTree) selectedNode.getData();
        if (product.getParent()==null) return;
        if (dBTree.deleteNode(product.getId())) {
        
        selectedNode.getChildren().clear();
        selectedNode.getParent().getChildren().remove(selectedNode);
        selectedNode.setParent(null);
        selectedNode = null; }
        else {
            System.out.println("Error delete this product!");
         FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected", "Error delete this product!");
         FacesContext.getCurrentInstance().addMessage(null, message);
         
         
        }
        
    }

    
    
    public void createNode() {
        System.out.println("public void createNode()");
        if (name.length()==0) return;
        ProductTree product = (ProductTree) selectedNode.getData();
        if (product.getParent() == null) {
            int id;
            id = dBTree.addNewProduct(name, false, Integer.toString(product.getId()));
            ProductTree p = new ProductTree(id, name, "2", Integer.toString(product.getId()));
            TreeNode expenses = new DefaultTreeNode(p, selectedNode);
            System.out.println(expenses);
        } else {
            int id;
            id = dBTree.addNewProduct(name, false, product.getParent());
            ProductTree p = new ProductTree(id, name, product.getBranch(), product.getParent());
            TreeNode expenses = new DefaultTreeNode(p, selectedNode.getParent());
            System.out.println(expenses);
        }
        
        name="";
    }
    
    public void addFolder() {
        System.out.println("public void addFolder()");
        
        int id = 99;
        String name = "FOLDER";
        ProductTree p = new ProductTree(id, name, "1", null);
        TreeNode treeNode = new DefaultTreeNode(p, root);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DBTree getdBTree() {
        return dBTree;
    }

    public void setdBTree(DBTree dBTree) {
        this.dBTree = dBTree;
    }

}
