/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.com.d2r.cash.bean;

/**
 *
 * @author artyom
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
//import java.sql.Date;
import java.util.LinkedHashMap;
import java.util.List;
import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.servlet.http.HttpSession;
import javax.swing.filechooser.FileSystemView;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.TreeNode;

@ManagedBean(name = "table")
@SessionScoped

public class Table implements Serializable {

//    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        String strTime = "";
//       
    private Date date = new Date();
    private String datee;
    private String idcur;
    private String balansUSD;
    private String balansEUR;
    private String balansUAH;
    private String numUSD;
    private String numUSDex;
    private String outPut;
    private String sqlTable = "SELECT B_ID AS ID, IT_ITEM1, IT_ITEM2, IT_ITEM3, B_SUM AS SUM, B_DATE AS DATE, CUR_CURREN AS CUR, B_NOTE AS NOTE "
            + "FROM BUD.BUDGET, BUD.CURREN, BUD.Item "
            + "WHERE BUDGET.B_CURREN = CURREN.CUR_ID AND BUDGET.B_SOURSE = Item.IT_ID AND BUDGET.B_ROLL LIKE '" + regionUs() + "%' GROUP BY B_ID DESC";

//            "SELECT B_ID AS ID, B_SOURSE AS ITEM, B_SUM AS SUM, B_DATE AS DATE, CUR_CURREN AS CUR "
//            + "FROM BUD.BUDGET, BUD.CURREN "
//            + "WHERE BUDGET.B_CURREN = CURREN.CUR_ID GROUP BY B_ID DESC";
    private String dateS;
    private String datePo;
    private Date datS;
    private Date datPo = new Date();
    private String item;
    private String itemm;
    private boolean roll = true;
    private StreamedContent file;
    private StreamedContent file1;
    private String rolle;
    private double Cusd;
    private double Ceur;
    private boolean curUsdEur = true;
    private boolean cur5 = false;
    private String mass = "";
    private String exce;
    private String id;
    private String add;
    private String[] ar;
    private String apd;
    private String vidCur;

    private String id1;
    private String item1;
    private String sum1;
    private String date1;
    private String cur1;
    private String note1;
    private boolean bat;
    private String excePO;
    private String exceMan;
    private String exceSet;
    private String login;
    private String exceDel;

    public String region() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        String str = session.getAttribute("roll").toString();
        StringBuffer sb = new StringBuffer(str);
        int k = str.length();
        if (str.contains("admin")) {
            sb.delete(k - 5, k);
            //System.out.println(sb);
        } else if (str.contains("user")) {
            sb.delete(k - 4, k);
            //System.out.println(sb);
        }

        String srt = sb.toString();
        //System.out.println(srt);
        return srt;
    }

    public void ddell() {
        String sql = "SELECT B_ID, IT_ITEM1, IT_ITEM2, IT_ITEM3, B_DATE, B_SUM, CUR_CURREN, B_NOTE "
                + "FROM BUD.BUDGET, BUD.CURREN, BUD.Item "
                + "WHERE CURREN.CUR_ID = BUDGET.B_CURREN AND Item.IT_ID = BUDGET.B_SOURSE AND B_ID = '" + getId() + "' AND BUDGET.B_ROLL LIKE '" + regionUs() + "%'";
        //System.out.println("//////////////////////////*************************** " + sql);

        Bd bd = new Bd();
        Budget bud = bd.selDel(sql);
        if (bud == null) {
            exceDel = "*** Неверно выбран ID записи!!!";
            bat = true;
            this.id1 = "";
            this.item1 = "";
            this.sum1 = "";
            this.date1 = "";
            this.cur1 = "";
            this.note1 = "";
        } else {
            this.id1 = bud.getId();
            this.item1 = bud.getItem();
            this.sum1 = bud.getSum();
            this.date1 = bud.getDate();
            this.cur1 = bud.getCur();
            this.note1 = bud.getNote();
            exceDel = "";
            bat = false;
        }
    }

    // Настройки добавить статью
    public void add() {
        // System.out.println("This is metod add()!!!");
        EventsView bean1;
        FacesContext fc1 = FacesContext.getCurrentInstance();
        ELContext elContext1 = fc1.getELContext();
        bean1 = (EventsView) elContext1.getELResolver().getValue(elContext1, null, "treeEventsView");
        TreeNode itemm = bean1.getSelectedNode();
        String srt = "" + itemm;
        // System.out.println("/*/* -------------- " + itemm);
        item = it(itemm);
        this.itemm = "в " + itemm;
        if (srt.equals("null") || srt.equals("Company") || srt.equals("Обмен") || srt.equals("EUR/UAH") || srt.equals("USD/UAH")
                || srt.equals("UAH/EUR") || srt.equals("UAH/USD")) {
            // System.out.println("/*/* -------------- " + itemm);
            exceSet = "" + "*** Неправильно выбранна статья!!!";
            // System.out.println("///////////////" + exceSet);
            bat = true;
        } else {
            exceSet = "";
            bat = false;
        }
        // System.out.println("/*/-*/-*/ " + item);
        ar = item.split("-");
        // System.out.println("/*/-*/-*/ " + ar.length);
        for (int i = 0; i < ar.length; i++) {
            // System.out.println("/*/-*/-*/ " + ar[i]);
        }

//        this.itemm = "";
//        this.add = "";
    }

    public void add1() {
        // System.out.println("This is metod add1()!!!");
        String str = regionUs();
        if (ar.length == 3) {
            String ad = getAdd();

            String sql = "INSERT INTO BUD.Item (IT_ITEM1, IT_ITEM2, IT_ITEM3, IT_DEL, regionUser) "
                    + "VALUES('" + ar[2] + "', '" + ad + "', '', '', '" + str + "')";
            Bd bd = new Bd();
            bd.InsertItem2(sql);
            //  System.out.println("--------------------" + sql);
            String page1 = "/Cash2/faces/admin/SettingsItem.xhtml";
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect(page1);
            } catch (IOException e) {
                System.out.println("Cash: Table - add1() " + e);
            }
        } else if (ar.length == 4) {
            String ad = getAdd();

            String sql = "INSERT INTO BUD.Item (IT_ITEM1, IT_ITEM2, IT_ITEM3, IT_DEL, regionUser) "
                    + "VALUES('" + ar[2] + "', '" + ar[3] + "', '" + ad + "', '', '" + str + "')";
            Bd bd = new Bd();
            bd.InsertItem2(sql);
            // System.out.println("--------------------" + sql);
            String page1 = "/Cash2/faces/admin/SettingsItem.xhtml";
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect(page1);
            } catch (IOException e) {
                System.out.println("Cash: Table - add1() " + e);
            }
        }
    }

    //Настройки изменение статьи
    public void upd() {
        EventsView bean1;
        FacesContext fc1 = FacesContext.getCurrentInstance();
        ELContext elContext1 = fc1.getELContext();
        bean1 = (EventsView) elContext1.getELResolver().getValue(elContext1, null, "treeEventsView");
        TreeNode itemm = bean1.getSelectedNode();
        String srt = "" + itemm;
        item = it(itemm);
        this.itemm = "с " + itemm;
        if (srt.equals("null") || srt.equals("Company") || srt.equals("Обмен") || srt.equals("EUR/UAH") || srt.equals("USD/UAH")
                || srt.equals("UAH/EUR") || srt.equals("UAH/USD") || srt.equals("Расход") || srt.equals("Доход")) {
            //  System.out.println("/*/* -------------- " + itemm);
            exceSet = "" + "*** Неправильно выбранна статья!!!";
            //  System.out.println("///////////////" + exceSet);
            bat = true;
        } else {
            exceSet = "";
            bat = false;
        }
        // System.out.println("/*/-*/-*/ " + item);
        ar = item.split("-");
        //  System.out.println("/*/-*/-*/ " + ar.length);
        for (int i = 0; i < ar.length; i++) {
            //     System.out.println("/*/-*/-*/ " + ar[i]);
        }
    }

    public void upd1() {
        //  System.out.println("This is metod upd1!!!");
        String str1 = regionUs();
        if (ar.length == 4) {
            LinkedList<String> list = new LinkedList<String>();
            String up = getApd();
            String sql0 = "";
            sql0 = "SELECT * FROM BUD.Item WHERE IT_ITEM2 = '" + ar[3] + "' AND IT_ITEM1 = '" + ar[2] + "' AND regionUser = '" + str1 + "'";
            //  System.out.println("--------------------" + sql0);
            Bd bd = new Bd();
            list = bd.SelectItem3(sql0);

            for (String str : list) {
                // System.out.println("*-////////////////*-*-*-*-*-*-*-" + str);
                String sql = "";
                sql = "UPDATE BUD.Item SET IT_ITEM2 ='" + up + "' WHERE IT_ID ='" + str + "'";
                //   System.out.println("--------------------" + sql);
                bd.UpdateItem1(sql);
            }
            String page1 = "/Cash2/faces/admin/SettingsItem.xhtml";
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect(page1);
            } catch (IOException e) {
                System.out.println("Cash: Table - upd1() " + e);
            }
        } else if (ar.length == 5) {
            LinkedList<String> list = new LinkedList<>();
            String up = getApd();
            String sql0 = "";
            sql0 = "SELECT * FROM BUD.Item WHERE IT_ITEM2 = '" + ar[3] + "' AND IT_ITEM1 = '" + ar[2] + "' AND IT_ITEM3 = '" + ar[4] + "'  AND regionUser = '" + str1 + "'";
            Bd bd = new Bd();
            list = bd.SelectItem3(sql0);

            for (String str : list) {
                // System.out.println("*-////////////////*-*-*-*-*-*-*-" + str);
                String sql = "";
                sql = "UPDATE BUD.Item SET IT_ITEM3 ='" + up + "' WHERE IT_ID ='" + str + "'";
                //  System.out.println("--------------------" + sql);
                bd.UpdateItem1(sql);
            }
            String page1 = "/Cash2/faces/admin/SettingsItem.xhtml";
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect(page1);
            } catch (IOException e) {
                System.out.println("Cash: Table - upd1() " + e);
            }
        }
    }

    //Настройки удаление статьи
    public void del1() {
        EventsView bean1;
        FacesContext fc1 = FacesContext.getCurrentInstance();
        ELContext elContext1 = fc1.getELContext();
        bean1 = (EventsView) elContext1.getELResolver().getValue(elContext1, null, "treeEventsView");
        TreeNode itemm = bean1.getSelectedNode();
        String srt = "" + itemm;
        item = it(itemm);
        this.itemm = "Удалить " + itemm;
        if (srt.equals("null") || srt.equals("Company") || srt.equals("Обмен") || srt.equals("EUR/UAH") || srt.equals("USD/UAH")
                || srt.equals("UAH/EUR") || srt.equals("UAH/USD") || srt.equals("Расход") || srt.equals("Доход")) {
            //  System.out.println("/*/* -------------- " + itemm);
            exceSet = "" + "*** Неправильно выбранна статья!!!";
            //  System.out.println("///////////////" + exceSet);
            bat = true;
        } else {
            exceSet = "";
            bat = false;
        }
        //  System.out.println("/*/-*/-*/ " + item);
        ar = item.split("-");
        // System.out.println("/*/-*/-*/ " + ar.length);
        for (int i = 0; i < ar.length; i++) {
            //      System.out.println("/*/-*/-*/ " + ar[i]);
        }
    }

    public void del2() {
      //  System.out.println("This is metod del2()!!!!!");
        //  System.out.println("This is metod del2()!!!!!  " + ar.length);
        String str1 = regionUs();
        if (ar.length == 5) {
            LinkedList<String> list = new LinkedList<String>();

            String sql0 = "SELECT * FROM BUD.Item WHERE IT_ITEM2 = '" + ar[3] + "' AND IT_ITEM1 = '" + ar[2] + "' AND IT_ITEM3 = '" + ar[4] + "' AND regionUser = '" + str1 + "'";
            //   System.out.println("*-////////////////*-*-*-*-*-*-*-" + sql0);
            Bd bd = new Bd();
            list = bd.SelectItem3(sql0);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateTec = simpleDateFormat.format(date);
            //System.out.println("--------------------///" + dateTec);

            for (String str : list) {
                //   System.out.println("*-////////////////*-*-*-*-*-*-*-" + str);
                String sql = "";
                sql = "UPDATE BUD.Item SET IT_DEL = 'DEL', IT_DEL_DATE = '" + dateTec + "' WHERE IT_ID ='" + str + "'";
                System.out.println("--------------------1" + sql);
                bd.UpdateItem1(sql);
            }
            String page1 = "/Cash2/faces/admin/SettingsItem.xhtml";
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect(page1);
            } catch (IOException e) {
                System.out.println("Cash: Table - del2() " + e);
            }
        }
        if (ar.length == 4) {
            LinkedList<String> list = new LinkedList<String>();

            String sql0 = "SELECT * FROM BUD.Item WHERE IT_ITEM2 = '" + ar[3] + "' AND IT_ITEM1 = '" + ar[2] + "' AND regionUser = '" + str1 + "'";
            System.out.println("*-////////////////*-*-*-*-*-*-*-" + sql0);
            Bd bd = new Bd();
            list = bd.SelectItem3(sql0);
            
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateTec1 = simpleDateFormat.format(date);
            
            for (String str : list) {
                //   System.out.println("*-////////////////*-*-*-*-*-*-*-" + str);
                String sql = "";
                sql = "UPDATE BUD.Item SET IT_DEL ='DEL', IT_DEL_DATE = '" + dateTec1 + "' WHERE IT_ID ='" + str + "'";
                System.out.println("--------------------2" + sql);
                bd.UpdateItem1(sql);
            }
            String page1 = "/Cash2/faces/admin/SettingsItem.xhtml";
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect(page1);
            } catch (IOException e) {
                System.out.println("Cash: Table - del2() " + e);
            }
        }
    }

    public void calendar() {
        Bd bd = new Bd();
        String sql = "select DATE_SUB(curdate(),INTERVAL DAYOFMONTH(curdate())-1 DAY) AS date";
        this.datS = bd.calend(sql);
    }

    public void del() {
        String sql = "DELETE FROM BUD.BUDGET WHERE B_ID = '" + getId() + "'";
        Bd bd = new Bd();
        bd.del(sql);
        setId("");
    }

    public boolean getCur5() {
        return cur5;
    }

    public void setCur5(boolean cur5) {
        this.cur5 = cur5;
    }

    public void excetS() {
        //  System.out.println("This is metod excetS()!!!");
        EventsView bean1;
        FacesContext fc1 = FacesContext.getCurrentInstance();
        ELContext elContext1 = fc1.getELContext();
        bean1 = (EventsView) elContext1.getELResolver().getValue(elContext1, null, "treeEventsView");
        TreeNode itemm = bean1.getSelectedNode();
        String str = "" + itemm;
        //   System.out.println("/*/* " + str);

        if (str.equals("null") || str.equals("Company") || str.equals("Обмен")) {
            //   System.out.println("/*/* -------------- " + str);
            exceSet = "" + "*** Неправильно выбранна статья!!!";
            //   System.out.println("///////////////" + exceSet);
            bat = true;
        } else {
            exceSet = "";
            bat = false;
        }
    }

    public void exe() {
        //  System.out.println("This is metod exe()!!!");
        EventsView bean1;
        FacesContext fc1 = FacesContext.getCurrentInstance();
        ELContext elContext1 = fc1.getELContext();
        bean1 = (EventsView) elContext1.getELResolver().getValue(elContext1, null, "treeEventsView");
        TreeNode itemm = bean1.getSelectedNode();
        String str = "" + itemm;
        //   System.out.println("/*/* " + str);

        String ds = "" + getDateS();
        String dp = "" + getDatePo();

        PPRBean bean2;
        FacesContext fc2 = FacesContext.getCurrentInstance();
        ELContext elContext2 = fc2.getELContext();
        bean2 = (PPRBean) elContext1.getELResolver().getValue(elContext1, null, "pprBean");
        String curE = "" + bean2.getCursEur();
        String curU = "" + bean2.getCursUsd();

        if (str.equals("null") || str.equals("Company") || str.equals("Обмен")) {
            //     System.out.println("/*/* -------------- " + str);
            exce = "" + "*** Невыбранна статья!!!";
            bat = true;
        } else if (ds.equals("")) {
            exce = "" + "*** Неустановленна дата формирование отчета!!!";
            bat = true;
        } else if (dp.equals("")) {
            exce = "" + "*** Неустановленна дата формирование отчета!!!";
            bat = true;
        } else if (curE.equals("")) {
            exce = "" + "*** Неустановлен курс EUR!!!";
            bat = true;
        } else if (curU.equals("")) {
            exce = "" + "*** Неустановлен курс USD!!!";
            bat = true;
        } else {
            exce = "";
            bat = false;
        }
    }

    public void exep_o() {
        //  System.out.println("This is metod exeP_0()!!!");

        String ds = "" + getDateS();
        String dp = "" + getDatePo();

        PPRBean bean2;
        FacesContext fc2 = FacesContext.getCurrentInstance();
        ELContext elContext2 = fc2.getELContext();
        bean2 = (PPRBean) elContext2.getELResolver().getValue(elContext2, null, "pprBean");
        String curE = "" + bean2.getCursEur();
        String curU = "" + bean2.getCursUsd();

        if (ds.equals("")) {
            excePO = "" + "*** Неустановленна дата формирование отчета!!!";
            bat = true;
        } else if (dp.equals("")) {
            excePO = "" + "*** Неустановленна дата формирование отчета!!!";
            bat = true;
        } else if (curE.equals("")) {
            excePO = "" + "*** Неустановлен курс EUR!!!";
            bat = true;
        } else if (curU.equals("")) {
            excePO = "" + "*** Неустановлен курс USD!!!";
            bat = true;
        } else {
            excePO = "";
            bat = false;
        }
    }

    public void exeM() {
        //  System.out.println("This is metod exeM()!!!");

        EventsView bean1;
        FacesContext fc1 = FacesContext.getCurrentInstance();
        ELContext elContext1 = fc1.getELContext();
        bean1 = (EventsView) elContext1.getELResolver().getValue(elContext1, null, "treeEventsView");
        TreeNode itemm = bean1.getSelectedNode();
        String str = "" + itemm;
        //  System.out.println("/*/* " + str);

        String ds = "" + getDatee();

        PPRBean bean2;
        FacesContext fc2 = FacesContext.getCurrentInstance();
        ELContext elContext2 = fc2.getELContext();
        bean2 = (PPRBean) elContext1.getELResolver().getValue(elContext1, null, "pprBean");
        String sum = "" + bean2.getFirstname();

        double viCur = getCusd();
        if (viCur == 0.0 && (str.equals("EUR/UAH") || str.equals("USD/UAH") || str.equals("UAH/EUR") || str.equals("UAH/USD"))) {
            exceMan = "" + "*** Не введен курс валюты!!!";
            bat = true;
        } else if (str.equals("null") || str.equals("Company")) {
            //   System.out.println("/*/* -------------- " + str);
            exceMan = "" + "*** Невыбранна статья!!!";
            bat = true;
        } else if (ds.equals("")) {
            exceMan = "" + "*** Невыбранна дата транзакции!!!";
            bat = true;
        } else if (sum.equals("")) {
            exceMan = "" + "*** Не введена сумма транзакции!!!";
            bat = true;
        } else {
            exceMan = "";
            bat = false;
        }
    }

    public String regionUser() {
        FacesContext context = FacesContext.getCurrentInstance();
        String str = context.getExternalContext().getUserPrincipal().toString();

        String sql = "SELECT * FROM BUD_jdbcrealm.groups WHERE groupname = '" + rolle() + "' AND username = '" + str + "'";
        //System.out.println("*************** " + sql);
        Bd bd = new Bd();
        String region = bd.selRegionUser(sql);
        //System.out.println("*************** region" + region);
        return region;
    }

    public String regionUs() {
        String region = regionUser();
        String str = "";
        StringBuffer sb = new StringBuffer(region);
        int k = region.length();
        if (region.contains("admin")) {
            str = "" + sb.delete(k - 5, k);

        } else if (region.contains("user")) {
            str = "" + sb.delete(k - 5, k);

        }
        return str;
    }

    private String rolle() {
        String rooll = "";
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        String str = session.getAttribute("roll").toString();

        return str;
    }

    public void curr() {
        EventsView bean1;
        FacesContext fc1 = FacesContext.getCurrentInstance();
        ELContext elContext1 = fc1.getELContext();
        bean1 = (EventsView) elContext1.getELResolver().getValue(elContext1, null, "treeEventsView");
        TreeNode itemm = bean1.getSelectedNode();

        if (itemm.equals("EUR/UAH") || itemm.equals("EUR/USD")) {
            setCur("EUR");
        }
    }

    public boolean getRoll() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        String str = session.getAttribute("roll").toString();
        if ("admin".equals(str)) {
            roll = false;
        } else {
            roll = true;
        }
//        this.sqlTable = "SELECT B_ID AS ID, IT_ITEM1, IT_ITEM2, IT_ITEM3, B_SUM AS SUM, B_DATE AS DATE, CUR_CURREN AS CUR, B_NOTE AS NOTE "
//                + "FROM BUD.BUDGET, BUD.CURREN, BUD.Item "
//                + "WHERE BUDGET.B_CURREN = CURREN.CUR_ID AND BUDGET.B_SOURSE = Item.IT_ID GROUP BY B_ID DESC";

//                "SELECT B_ID AS ID, B_SOURSE AS ITEM, B_SUM AS SUM, B_DATE AS DATE, CUR_CURREN AS CUR "
//            + "FROM BUD.BUDGET, BUD.CURREN "
//            + "WHERE BUDGET.B_CURREN = CURREN.CUR_ID GROUP BY B_ID DESC";
        return roll;
    }

    public void setRoll(boolean roll) {
        this.roll = roll;
    }

    public Table() {
        addBud(sqlTable);
        addCur();
        addStaff();
        calendar();
        //curr();
        //show();
        // balansUSD();
    }

    private List<Budget> budget;
    private LinkedHashMap<String, String> curs;
    private LinkedHashMap<String, String> staff;
    private String cur = "UAH";
    private String staf = "All";

    public List<Budget> getBudget() {
        budget = null;
        //System.out.println("-------------------+++++++++++++++++++ " + sqlTable);
        addBud(sqlTable);
        // addBud1();
//        for(Budget b: budget){
//                    System.out.println("/*-/*-/*- " + b);
//                }
        return budget;
    }

    public void setBudget(List<Budget> budget) {
        this.budget = budget;
    }

    public void destroyWorld() {
        addMessage("System Error", "Please try again later.");
    }

    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void analiz() {
        //System.out.println("Tgis is metod analiz!!!");
        String page1 = "/Cash2/faces/admin/FerstJSF.xhtml";
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(page1);
        } catch (IOException e) {
            System.out.println("Cash: Table - analiz() " + e);
        }
    }

    public void operation() {
        //System.out.println("Tgis is metod operation!!!");

        this.sqlTable = "SELECT B_ID AS ID, IT_ITEM1, IT_ITEM2, IT_ITEM3, B_SUM AS SUM, B_DATE AS DATE, CUR_CURREN AS CUR, B_NOTE AS NOTE "
                + "FROM BUD.BUDGET, BUD.CURREN, BUD.Item "
                + "WHERE BUDGET.B_CURREN = CURREN.CUR_ID AND BUDGET.B_ROLL LIKE '" + regionUs() + "%' AND BUDGET.B_SOURSE = Item.IT_ID GROUP BY B_ID DESC";

        String page1 = "/Cash2/faces/admin/SecondJSF.xhtml";
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(page1);
        } catch (IOException e) {
            System.out.println("Cash: Table - operation() " + e);
        }
    }

    public void roll() {
        // System.out.println("This is metod roll!!! ");
        boolean bul = false;

        FacesContext context = FacesContext.getCurrentInstance();
//        HttpServletRequest request = null;
//         HttpSession session =  request.getSession(true);
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
//        System.out.println("session.getId()" );
//        if(request.isUserInRole("Admin") && !session.isNew())
//        {
//            bul = true;
//        }

    }

    public void logout() {
        //System.out.println("This is ++++++++++++:  ");
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        session.invalidate();
        String page1 = "/Cash2";
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(page1);
        } catch (IOException e) {
            System.out.println("Cash: Table - logout() " + e);
        }
    }

    public void report() {
        //System.out.println("This is metod Report()");

//        SelectBooleanView bean1;
//        FacesContext fc1 = FacesContext.getCurrentInstance();
//        ELContext elContext1 = fc1.getELContext();
//        bean1 = (SelectBooleanView) elContext1.getELResolver().getValue(elContext1, null, "selectBooleanView");
//        boolean rep = bean1.isValue1();
//       System.out.println("rep ++" + rep);
// Для полного отчета с КомбоБоксом
//        if (rep == true) {
//            String sql = "SELECT B_ID AS ID, B_SOURSE AS ITEM, B_SUM AS SUM, B_DATE AS DATE, CUR_CURREN AS CUR "
//                    + "FROM BUD.BUDGET, BUD.CURREN "
//                    + "WHERE BUDGET.B_CURREN = CURREN.CUR_ID "
//                    + "ORDER BY ITEM ";
//            Bd bd = new Bd();
//            bd.reportSelect(sql);
//        } else if (rep == false) {
        String sql = sqlTable;
        String sql1 = "";
        StringBuffer sb = new StringBuffer(sql);
        int k = sql.length();
        if (sql.contains("GROUP BY")) {
            sql1 = "" + sb.delete(k - 18, k);
        }
        sql1 += "AND IT_ITEM2 <> 'Обмен' AND BUDGET.B_ROLL LIKE '" + regionUs() + "%' ORDER BY IT_ITEM2 DESC";
        //System.out.println("++++++++++++++++++++++++++++++++++++ " + sql1);

        String str = getIt(sql);
        //System.out.println("+++getIt(sql)+++ " + str);

        Date dss = getDatS();
        Date dpo = getDatPo();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strTime1 = simpleDateFormat.format(dss);
        String strTime2 = simpleDateFormat.format(dpo);

        String cur = getCur();

        String[] ar = analizz(str, strTime1, strTime2, cur);

        for (int i = 0; i < ar.length; i++) {
            //System.out.println("********** " + ar[i]);
        }

        String exp = "";
        if (ar[1] == null && ar[0] != null) {
            //System.out.println("NumUSDex--------" + ar[1]);
            exp = ar[0];
        } else if (ar[1] != null && ar[0] == null) {
                    //System.out.println("NumUSD----------" + ar[0]);
            //System.out.println("NumUSDex--------" + ar[1]);
            exp = ar[1];
        }
        //System.out.println("exp = " + exp);
        Bd bd = new Bd();
        bd.reportSelect(sql1, exp);
        //System.out.println("************ " + sql1);

    }

    public String getIt(String sql) {
        String str2 = "";

        StringBuffer sb = new StringBuffer(sql);
        int s = sb.indexOf("Item.IT_ITEM1 =");
        int poS = sb.indexOf("AND Item.IT_ITEM2 =");

        //System.out.println(" s " + s + "; poS " + poS);
        String str = sql.substring(s + 17, poS - 2);
        //System.out.println("---- " + str);

        int po = sb.indexOf(")) ");
        String str1 = sql.substring(poS + 21, po - 1);
        //System.out.println("+++ " + str1);

        str2 = "Company-" + str + "-" + str1;

        return str2;
    }

    public void report1() {
        //System.out.println("This is metod Report()");
        Bd bd = new Bd();
        LinkedList<Item> list1 = new LinkedList<Item>();
        LinkedList<Item> list2 = new LinkedList<Item>();
        String str = regionUs();
        String strDateS = "" + getDateS();
        String strDatePo = "" + getDatee();
        //System.out.println("*********----------------- " + strDateS);
//        String sql1 = "SELECT IT_ITEM2, IT_ITEM3, IT_ID FROM BUD.Item WHERE IT_ITEM1 = 'Expend' GROUP BY IT_ITEM2";
//        String sql2 = "SELECT IT_ITEM2, IT_ITEM3, IT_ID FROM BUD.Item WHERE IT_ITEM1 = 'Income' GROUP BY IT_ITEM2";
        String sql1 = "SELECT IT_ITEM2, IT_ITEM3, IT_ID FROM BUD.Item WHERE IT_ITEM1 = 'Расход' AND IT_ITEM2 <> 'Обмен' AND regionUser = '" + str + "' AND (IT_DEL_DATE = '0000-00-00' "
                + "OR (IT_DEL_DATE BETWEEN '" + strDateS + "' AND '" + strDatePo + "')) ORDER BY IT_ITEM2, IT_ITEM3";
        String sql2 = "SELECT IT_ITEM2, IT_ITEM3, IT_ID FROM BUD.Item WHERE IT_ITEM1 = 'Доход' AND IT_ITEM2 <> 'Обмен' AND regionUser = '" + str + "' AND (IT_DEL_DATE = '0000-00-00' "
                + "OR (IT_DEL_DATE BETWEEN '" + strDateS + "' AND '" + strDatePo + "')) ORDER BY IT_ITEM2, IT_ITEM3";

//        System.out.println("*********************** " + sql1);
//        System.out.println("*********************** " + sql2);
        list1 = bd.itAll2(sql1);
        list2 = bd.itAll2(sql2);

        for (Item item : list1) {
            //System.out.println("88888888 " + item);
        }
        bd.reportSelectAll(list1, list2);
    }

    public void doun() {
        // System.out.println("This is metod doun()S");
        String address = "" + FileSystemView.getFileSystemView().getHomeDirectory() + "/Report.pdf";
        File tempFile = new File(address);
        FileInputStream ff = null;
        try {
            ff = new FileInputStream(tempFile);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Bd.class.getName()).log(Level.SEVERE, null, ex);
            //System.out.println("Cash: Table - doun() " + ex);
        }

        file = new DefaultStreamedContent(ff, "application/pdf", "download_file.pdf");

    }

    public void doun1() {
        //System.out.println("This is metod doun()S");
        String address = "" + FileSystemView.getFileSystemView().getHomeDirectory() + "/Report.pdf";
        File tempFile = new File(address);
        FileInputStream ff = null;
        try {
            ff = new FileInputStream(tempFile);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Bd.class.getName()).log(Level.SEVERE, null, ex);
            //System.out.println("Cash: Table - doun1() " + ex);
        }

        file1 = new DefaultStreamedContent(ff, "application/pdf", "download_file.pdf");

    }

    public StreamedContent getFile() throws InterruptedException {
        show();
        report();
        doun();
        return file;
    }

    private void addStaff() {
        Bd bd = new Bd();
        String sql = "";
        sql = "SELECT S_ID, S_FIO FROM BUD.STAFF";

        staff = bd.SelectStaff(sql);
        // System.out.println("STAFF size "+ staff.size() );
    }

    private void addCur() {
        Bd bd = new Bd();
        String sql = "";
        sql = "SELECT CUR_ID, CUR_CURREN FROM BUD.CURREN";

        curs = bd.SelectCur(sql);
        // System.out.println("CUR size "+ curs.size() );
    }

    private void addBud(String sqlTable) {
        //System.out.println("This is metod private void addBud(String sqlTable)");
        Bd bd = new Bd();
        String sql = "";
//        sql = "SELECT B_ID AS ID, B_SOURSE AS ITEM, B_SUM AS SUM, B_DATE AS DATE, CUR_CURREN AS CUR "
//                + "FROM BUD.BUDGET, BUD.CURREN "
//                + "WHERE BUDGET.B_CURREN = CURREN.CUR_ID GROUP BY B_ID DESC";
        String a = regionUser();
        //String rooll = rolle();
        StringBuffer sb = new StringBuffer(a);
        int k = a.length();
        if (a.contains("admin")) {
            //System.out.println("Yes admin!!!");
            String rooll = "" + sb.delete(k - 5, k);
            String b = sqlTable;
//                    "SELECT B_ID AS ID, IT_ITEM1, IT_ITEM2, IT_ITEM3, B_SUM AS SUM, B_DATE AS DATE, CUR_CURREN AS CUR, B_NOTE AS NOTE "
//                    + "FROM BUD.BUDGET, BUD.CURREN, BUD.Item "
//                    + "WHERE BUDGET.B_CURREN = CURREN.CUR_ID AND BUDGET.B_SOURSE = Item.IT_ID AND BUDGET.B_ROLL LIKE '" + rooll + "%' GROUP BY B_ID DESC";
            //System.out.println("******************************" + b);
            budget = bd.Select(b);
        } else if (a.contains("user")) {
            String rooll = a;
            String b = "SELECT B_ID AS ID, IT_ITEM1, IT_ITEM2, IT_ITEM3, B_SUM AS SUM, B_DATE AS DATE, CUR_CURREN AS CUR, B_NOTE AS NOTE "
                    + "FROM BUD.BUDGET, BUD.CURREN, BUD.Item "
                    + "WHERE BUDGET.B_CURREN = CURREN.CUR_ID AND BUDGET.B_SOURSE = Item.IT_ID AND BUDGET.B_ROLL = '" + rooll + "' GROUP BY B_ID DESC";
            budget = bd.Select(b);
        }
        //System.out.println("Budjet size "+ budget.size() );
    }

    private void addBud1() {
        Bd bd = new Bd();
        String sql = "";
        sql = "SELECT B_ID AS ID, B_SOURSE AS ITEM, B_SUM AS SUM, B_DATE AS DATE, CUR_CURREN AS CUR "
                + "FROM BUD.BUDGET, BUD.CURREN "
                + "WHERE BUDGET.B_CURREN = CURREN.CUR_ID AND BUDGET.B_ROLL LIKE '" + regionUs() + "%' "
                + "ORDER BY BUDGET.B_ID DESC "
                + "LIMIT 5 ";

        budget = bd.Select(sql);
        //System.out.println("Budjet size "+ budget.size() );
    }

    public void show() throws InterruptedException {
        String[] ar = new String[2];
        //System.out.println("This metod show(): --------------------");
        ar = analizUSD();
        if (ar[0] != null && ar[1] == null) {
            setNumUSD(ar[0]);
            setNumUSDex("0.00");
            //System.out.println("NumUSD----------" + ar[0]);
        } else if (ar[1] != null && ar[0] == null) {
            //System.out.println("NumUSDex--------" + ar[1]);
            setNumUSDex(ar[1]);
            setNumUSD("0.00");
        } else if (ar[1] != null && ar[0] != null) {
            //System.out.println("NumUSD----------" + ar[0]);
            //System.out.println("NumUSDex--------" + ar[1]);
            setNumUSDex(ar[1]);
            setNumUSD(ar[0]);
        }
    }

    public void show1(String Exp, String strTime1, String strTime2) throws InterruptedException {
        //System.out.println("This is metod show1()!!!");
        String[] ar = new String[2];
        //System.out.println("This metod show(): --------------------");
        ar = analizz(Exp, strTime1, strTime2, getCur());
        if (ar[0] != null && ar[1] == null) {
            setNumUSD(ar[0]);
            setNumUSDex("0.00");
            //System.out.println("NumUSD----------" + ar[0]);
        } else if (ar[1] != null && ar[0] == null) {
            // System.out.println("NumUSDex--------" + ar[1]);
            setNumUSDex(ar[1]);
            setNumUSD("0.00");
        } else if (ar[1] != null && ar[0] != null) {
            //System.out.println("NumUSD----------" + ar[0]);
            //System.out.println("NumUSDex--------" + ar[1]);
            setNumUSDex(ar[1]);
            setNumUSD(ar[0]);
        }
    }

    public void addBtt() {
        //System.out.println("This metod addBtt(): --------------------");

        //System.out.println("Vibrannau valuta " + idcur);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strTime = "";
        if (strTime.equals("")) {
            outPut = "Невыбранна дата!";
        }
        strTime = simpleDateFormat.format(date);
        //System.out.println(strTime);

        //System.out.println("Date  " +  date1);
        PPRBean bean;
        FacesContext fc = FacesContext.getCurrentInstance();
        ELContext elContext = fc.getELContext();
        bean = (PPRBean) elContext.getELResolver().getValue(elContext, null, "pprBean");
        String sum = bean.getFirstname();
        String note = bean.getComment();

        //System.out.println("Kolichestvo many " + sum);
        EventsView bean1;
        FacesContext fc1 = FacesContext.getCurrentInstance();
        ELContext elContext1 = fc1.getELContext();
        bean1 = (EventsView) elContext1.getELResolver().getValue(elContext1, null, "treeEventsView");
        TreeNode itemm = bean1.getSelectedNode();
        //String item = "" ;//+ itemm;

        // System.out.println("Tree  " + itemm);
        item = it(itemm);
        String rooll = regionUser();

        //System.out.println("Tree ********** " + item);
        Bd bd = new Bd();
        String it = "";
        String[] ar = item.split("-");
        //System.out.println("+-+-+-+-+-+" + ar.length);
        if (ar.length == 4) {
            if (!ar[2].contains("Обмен")) {
                String sql = "Select IT_ID FROM BUD.Item WHERE IT_ITEM1 = '" + ar[2] + "' AND IT_ITEM2 = '" + ar[3] + "' AND IT_ITEM3 = ''";
                //System.out.println("*-*-*-*-*1" + sql);
                it = bd.itAll(sql);
            } else {
                String sql = "Select IT_ID FROM BUD.Item WHERE IT_ITEM1 = 'Расход' AND IT_ITEM2 = '" + ar[2] + "' AND IT_ITEM3 = '" + ar[3] + "'";
                // System.out.println("*-*-*-*-*2" + sql);
                it = bd.itAll(sql);
                boolean bul = bd.Add(idcur, sum, strTime, it, note, rooll);
            }
        }
        if (ar.length == 5) {
            String sql = "Select IT_ID FROM BUD.Item WHERE IT_ITEM1 = '" + ar[2] + "' AND IT_ITEM2 = '" + ar[3] + "' AND IT_ITEM3 ='" + ar[4] + "' AND regionUser = '" + regionUs() +"'";
            //System.out.println("*-*-*-*-*3" + sql);
            it = bd.itAll(sql);
        }

        boolean bul;

        if (item.contains("Обмен")) {
            //System.out.println(" if (item.contains(Convert) { " + it);

            if (!ar[2].contains("Обмен")) {
                String sql = "Select IT_ID FROM BUD.Item WHERE IT_ITEM1 = '" + ar[2] + "' AND IT_ITEM2 = '" + ar[3] + "'";
                //System.out.println("*-*-*-*-*" + sql);
                it = bd.itAll(sql);
            } else {
                String sql = "Select IT_ID FROM BUD.Item WHERE IT_ITEM1 = 'Доход' AND IT_ITEM2 = '" + ar[2] + "' AND IT_ITEM3 ='" + ar[3] + "'";
                //System.out.println("*-*-*-*-*" + sql);
                it = bd.itAll(sql);
                double curU = getCusd();

                if (ar[3].equals("EUR/UAH")) {
                    double rez = curU * Double.parseDouble(sum);
//                     System.out.println("/-*/-*/-*/-*/-*/-*/" + rez);
                    String ret = String.format("%.2f", rez);
                    String c = "" + 3;
                    //System.out.println("s1 = " + c);
                    bul = bd.Add(c, ret, strTime, it, note, rooll);
                } else if (ar[3].equals("EUR/USD")) {
                    //double rez = curU * Double.parseDouble(sum) / curU;
                    //System.out.println("/-*/-*/-*/-*/-*/-*/" + rez);
                    // String ret = "" + rez;
                    // String c = "" + 1;
                    // bul = bd.Add(c, ret, strTime, it, note, rooll);
                } else if (ar[3].equals("USD/UAH")) {
                    double rez = curU * Double.parseDouble(sum);
                    // System.out.println("/-*/-*/-*/-*/-*/-*/" + rez);
                    String ret = String.format("%.2f", rez);
                    String c = "3";
                    //System.out.println("s2 = " + c);
                    bul = bd.Add(c, ret, strTime, it, note, rooll);
                } else if (ar[3].equals("USD/EUR")) {
                    //double rez = curU * Double.parseDouble(sum) / curE;
                    // // System.out.println("/-*/-*/-*/-*/-*/-*/" + rez);
                    // String ret = "" + rez;
                    // String c = "" + 2;
                    // bul = bd.Add(c, ret, strTime, it, note, rooll);
                } else if (ar[3].equals("UAH/EUR")) {
                    double rez = Double.parseDouble(sum) / curU;
                    //System.out.println("/-*/-*/-*/-*/-*/-*/" + rez);
                    String c = "" + 2;
                    String ret = String.format("%.2f", rez);
                    //System.out.println("s3 = " + c);
                    bul = bd.Add(c, ret, strTime, it, note, rooll);
                } else if (ar[3].equals("UAH/USD")) {
                    double rez = Double.parseDouble(sum) / curU;
                    // System.out.println("/-*/-*/-*/-*/-*/-*/" + rez);
                    String ret = String.format("%.2f", rez);
                    String c = "" + 1;
                    //System.out.println("s4 = " + c);
                    bul = bd.Add(c, ret, strTime, it, note, rooll);
                }
                //bul = bd.Add(idcur, sum, strTime, it, note, rooll);
            }

            // System.out.println(" if (item.contains(Convert) { " + it);
            //bul = bd.Add(idcur, sum, strTime, it, note, rooll);
            //bul = bd.Add(idcur, sum, strTime, it, note, rooll);
        } else {
            bul = bd.Add(idcur, sum, strTime, it, note, rooll);
            //System.out.println("idcur " + idcur + "; sum " + sum + "; strTime "+ strTime + "; it " + it + "; note " + note + "; rooll " + rooll);
            if (bul == true) {
                outPut = "Операция прошла успешно!";
            } else {
                outPut = "Ошибка";
            }
        }

        item = "";

        bean.setFirstname(null);
        setDate(null);
        bean1.setSelectedNode(null);
        // System.out.println("setDate(null)-********" + getDate());
        // System.out.println("bean.getFirstname()-********" + bean.getFirstname());

//        System.out.println("idCur:   ***********:  " + idcur);
//        System.out.println("*-*-*-*-*1" + regionUs());
        bean.setComment("");
    }

    public String it(TreeNode item) {
        String r = "";
        return recIt(item, r) + item;
    }

    private String recIt(TreeNode item, String str) {
        if (item.getParent() != null) {
            str += recIt(item.getParent(), str);
            str += item.getParent() + "-";
        }

        return str;
    }

    public void balansUSD() {
        this.balansUSD = calcBalansUSD();
    }

    private String calcBalansUSD() {

        String sql0 = "";
        String sql1 = "";
        String balSql0 = "";
        String balSql1 = "";
        double bal = 0;
        String balans = "";
        double a = 0;
        double b = 0;

        Bd bd = new Bd();
        LinkedList<String> listIn = new LinkedList<String>();
        LinkedList<String> listExp = new LinkedList<String>();
        String sqlIn = "SELECT IT_ID FROM BUD.Item WHERE IT_ITEM1 = 'Доход'";
        // System.out.println(sqlIn);
        String sqlExp = "SELECT IT_ID FROM BUD.Item WHERE IT_ITEM1 = 'Расход'";
        // System.out.println(sqlExp);
        listIn = bd.itAll1(sqlIn);
        listExp = bd.itAll1(sqlExp);
        String str = regionUs();
        for (String str1 : listIn) {
            sql0 = "SELECT SUM(B_SUM) "
                    + "FROM BUD.BUDGET "
                    + "WHERE BUDGET.B_SOURSE = '" + str1 + "' "
                    + "AND BUDGET.B_CURREN = '1' AND BUDGET.B_ROLL LIKE '" + str + "%'";
            a += bd.BalansCur(sql0);
        }

        for (String str2 : listExp) {
            sql1 = "SELECT SUM(B_SUM) "
                    + "FROM BUD.BUDGET "
                    + "WHERE BUDGET.B_SOURSE = '" + str2 + "' "
                    + "AND BUDGET.B_CURREN = '1' AND BUDGET.B_ROLL LIKE '" + str + "%'";
            b += bd.BalansCur(sql1);
        }

        balSql0 = "" + a;
        // System.out.println("(balSql0) " + balSql0);

        balSql1 = "" + b;
        // System.out.println("(balSql1) " + balSql1);

        if (balSql0 == null) {
            balSql0 = "0";
        }
        if (balSql1 == null) {
            balSql1 = "0";
        }

        bal = Double.parseDouble(balSql0) - Double.parseDouble(balSql1);
        balans = String.format("%.2f", bal);

        return balans;
    }

    public void balansEUR() {
        this.balansEUR = calcBalansEUR();
    }

    private String calcBalansEUR() {

        String sql0 = "";
        String sql1 = "";
        String balSql0 = "";
        String balSql1 = "";
        double bal = 0;
        String balans = "";
        double a = 0;
        double b = 0;

        Bd bd = new Bd();
        LinkedList<String> listIn = new LinkedList<String>();
        LinkedList<String> listExp = new LinkedList<String>();
        String sqlIn = "SELECT IT_ID FROM BUD.Item WHERE IT_ITEM1 = 'Доход'";
        //  System.out.println(sqlIn);
        String sqlExp = "SELECT IT_ID FROM BUD.Item WHERE IT_ITEM1 = 'Расход'";
        //  System.out.println(sqlExp);
        listIn = bd.itAll1(sqlIn);
        listExp = bd.itAll1(sqlExp);
        String str = regionUs();
        for (String str1 : listIn) {
            sql0 = "SELECT SUM(B_SUM) "
                    + "FROM BUD.BUDGET "
                    + "WHERE BUDGET.B_SOURSE = '" + str1 + "' "
                    + "AND BUDGET.B_CURREN = '2' AND BUDGET.B_ROLL LIKE '" + str + "%'";
            a += bd.BalansCur(sql0);
        }

        for (String str2 : listExp) {
            sql1 = "SELECT SUM(B_SUM) "
                    + "FROM BUD.BUDGET "
                    + "WHERE BUDGET.B_SOURSE = '" + str2 + "' "
                    + "AND BUDGET.B_CURREN = '2' AND BUDGET.B_ROLL LIKE '" + str + "%'";
            b += bd.BalansCur(sql1);
        }

        //a = bd.BalansCur(sql0);
        balSql0 = "" + a;
        //System.out.println("(balSql0) " + balSql0);

        //b = bd.BalansCur(sql1);
        balSql1 = "" + b;
        //System.out.println("(balSql1) " + balSql1);

        if (balSql0 == null) {
            balSql0 = "0";
        }
        if (balSql1 == null) {
            balSql1 = "0";
        }

        bal = Double.parseDouble(balSql0) - Double.parseDouble(balSql1);
        balans = String.format("%.2f", bal);

        return balans;
    }

    public void balansUAH() {
        this.balansUAH = calcBalansUAH();
    }

    private String calcBalansUAH() {

        String sql0 = "";
        String sql1 = "";
        String balSql0 = "";
        String balSql1 = "";
        double bal = 0;
        String balans = "";
        double a = 0;
        double b = 0;
        Bd bd = new Bd();
        LinkedList<String> listIn = new LinkedList<String>();
        LinkedList<String> listExp = new LinkedList<String>();
        String sqlIn = "SELECT IT_ID FROM BUD.Item WHERE IT_ITEM1 = 'Доход'";
        //  System.out.println(sqlIn);
        String sqlExp = "SELECT IT_ID FROM BUD.Item WHERE IT_ITEM1 = 'Расход'";
        //  System.out.println(sqlExp);
        listIn = bd.itAll1(sqlIn);
        listExp = bd.itAll1(sqlExp);
        String str = regionUs();
        for (String str1 : listIn) {
            sql0 = "SELECT SUM(B_SUM) "
                    + "FROM BUD.BUDGET "
                    + "WHERE BUDGET.B_SOURSE = '" + str1 + "' "
                    + "AND BUDGET.B_CURREN = '3' AND BUDGET.B_ROLL LIKE '" + str + "%'";
            a += bd.BalansCur(sql0);
        }

        for (String str2 : listExp) {
            sql1 = "SELECT SUM(B_SUM) "
                    + "FROM BUD.BUDGET "
                    + "WHERE BUDGET.B_SOURSE = '" + str2 + "' "
                    + "AND BUDGET.B_CURREN = '3' AND BUDGET.B_ROLL LIKE '" + str + "%'";
            b += bd.BalansCur(sql1);
        }
        //a = bd.BalansCur(sql0);
        balSql0 = "" + a;
        //System.out.println("(balSql0) " + balSql0);

        // b = bd.BalansCur(sql1);
        balSql1 = "" + b;
        // System.out.println("(balSql1) " + balSql1);

        if (balSql0 == null) {
            balSql0 = "0";
        }
        if (balSql1 == null) {
            balSql1 = "0";
        }

        bal = Double.parseDouble(balSql0) - Double.parseDouble(balSql1);
        balans = String.format("%.2f", bal);

        return balans;
    }

    public String[] analizUSD() {
        String sql1;
        String sql2;
        String sql3;
        String Exp;
        String balSql1 = "";
        String balSql2 = "";
        String balSql3 = "";
        String CursUsd;
        String CursEur;
        double numUsd;
        String balans = "";
        String[] bal = new String[2];

        double a = 0;
        double b = 0;
        double c = 0;

        EventsView bean1;
        FacesContext fc1 = FacesContext.getCurrentInstance();
        ELContext elContext1 = fc1.getELContext();
        bean1 = (EventsView) elContext1.getELResolver().getValue(elContext1, null, "treeEventsView");
        TreeNode itemm = bean1.getSelectedNode();

//        MultiSelectView bean1;
//        FacesContext fc1 = FacesContext.getCurrentInstance();
//        ELContext elContext1 = fc1.getELContext();
//        bean1 = (MultiSelectView) elContext1.getELResolver().getValue(elContext1, null, "multiSelectView");
//        String selection = bean1.getSelection();
        String item = "";//+ itemm;
        // System.out.println("Tree  " + itemm);
        String str = it(itemm);
        //System.out.println("Tree  " + selection);
        Exp = str;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateS = simpleDateFormat.format(datS);
//        datePo = simpleDateFormat.format(getDatePo());
        //  dateS = "2014-06-01";
        datePo = simpleDateFormat.format(datPo);

        // System.out.println("dateS+++++++++++++datePo" + dateS + "-----" + datePo);
        String k = "";
        Bd bd = new Bd();
        LinkedList<String> list = new LinkedList<String>();
        String[] ar = new String[5];
        ar = Exp.split("-");
        String str1 = regionUs();
        //  System.out.println("+-+-+-+-+-+" + ar.length);
        if (ar.length == 3) {
            String sql = "Select IT_ID FROM BUD.Item WHERE IT_ITEM1 = '" + ar[2] + "' AND IT_ITEM2 <> 'Обмен' AND regionUser = '" + str1 + "'";
            //   System.out.println("*-*-*-*-*" + sql);
            list = bd.itAll1(sql);

            k = "OR " + "(Item.IT_ITEM1 = '" + ar[2] + "')";
            this.sqlTable = "SELECT B_ID AS ID, IT_ITEM1, IT_ITEM2, IT_ITEM3, B_SUM AS SUM, B_DATE AS DATE, CUR_CURREN AS CUR, B_NOTE AS NOTE "
                    + "FROM BUD.BUDGET, BUD.CURREN, BUD.Item "
                    + "WHERE BUDGET.B_CURREN = CURREN.CUR_ID AND BUDGET.B_SOURSE = Item.IT_ID AND (BUDGET.B_SOURSE = '0' " + k
                    + ") AND BUDGET.B_ROLL LIKE '" + str1 + "%' AND IT_ITEM2 <> 'Обмен' AND B_DATE BETWEEN '" + dateS + "' AND '" + datePo + "' ";
            //System.out.println("//////////////// " + sqlTable);
        }
        if (ar.length == 4) {
            String sql = "Select IT_ID FROM BUD.Item WHERE IT_ITEM1 = '" + ar[2] + "' AND IT_ITEM2 = '" + ar[3] + "' AND IT_ITEM2 <> 'Обмен' AND regionUser = '" + str1 + "'";
            // System.out.println("*-*-*-*-*" + sql);
            list = bd.itAll1(sql);

            k = "OR " + "(Item.IT_ITEM1 = '" + ar[2] + "' AND Item.IT_ITEM2 = '" + ar[3] + "')";
            this.sqlTable = "SELECT B_ID AS ID, IT_ITEM1, IT_ITEM2, IT_ITEM3, B_SUM AS SUM, B_DATE AS DATE, CUR_CURREN AS CUR, B_NOTE AS NOTE "
                    + "FROM BUD.BUDGET, BUD.CURREN, BUD.Item "
                    + "WHERE BUDGET.B_CURREN = CURREN.CUR_ID AND BUDGET.B_SOURSE = Item.IT_ID AND (BUDGET.B_SOURSE = '0' " + k
                    + ") AND BUDGET.B_ROLL LIKE '" + str1 + "%' AND IT_ITEM2 <> 'Обмен' AND B_DATE BETWEEN '" + dateS + "' AND '" + datePo + "' "
                    + "GROUP BY B_ID DESC";
            //System.out.println("////////////////// " + sqlTable);
        }
        if (ar.length == 5) {
            String sql = "Select IT_ID FROM BUD.Item WHERE IT_ITEM1 = '" + ar[2] + "' AND IT_ITEM2 = '" + ar[3] + "' AND IT_ITEM3 ='" + ar[4] + "' AND IT_ITEM2 <> 'Обмен' AND regionUser = '" + str1 + "'";
            //  System.out.println("*-*-*-*-*" + sql);
            list = bd.itAll1(sql);

            k = "OR " + "(Item.IT_ITEM1 = '" + ar[2] + "' AND Item.IT_ITEM2 = '" + ar[3] + "' AND Item.IT_ITEM3 = '" + ar[4] + "')";
            this.sqlTable = "SELECT B_ID AS ID, IT_ITEM1, IT_ITEM2, IT_ITEM3, B_SUM AS SUM, B_DATE AS DATE, CUR_CURREN AS CUR, B_NOTE AS NOTE "
                    + "FROM BUD.BUDGET, BUD.CURREN, BUD.Item "
                    + "WHERE BUDGET.B_CURREN = CURREN.CUR_ID AND BUDGET.B_SOURSE = Item.IT_ID AND (BUDGET.B_SOURSE = '0' " + k
                    + ") AND BUDGET.B_ROLL LIKE '" + str1 + "%' AND IT_ITEM2 <> 'Обмен' AND B_DATE BETWEEN '" + dateS + "' AND '" + datePo + "' "
                    + "GROUP BY B_ID DESC";
            //System.out.println("///////////////////////// " + sqlTable);
        }

//        String k = "";
//       // for(String s: list)
//       // {
//            k = "OR " + "(Item.IT_ITEM1 = '" + ar[2] +"' AND Item.IT_ITEM2 = '" + ar[3] + "' AND Item.IT_ITEM3 = '" + ar[4]+ "')";
//            this.sqlTable = "SELECT B_ID AS ID, IT_ITEM1, IT_ITEM2, IT_ITEM3, B_SUM AS SUM, B_DATE AS DATE, CUR_CURREN AS CUR, B_NOTE AS NOTE "
//                    + "FROM BUD.BUDGET, BUD.CURREN, BUD.Item "
//                    + "WHERE BUDGET.B_CURREN = CURREN.CUR_ID AND BUDGET.B_SOURSE = Item.IT_ID AND (BUDGET.B_SOURSE = '0' " + k
//                    + ") AND B_DATE BETWEEN '" + dateS + "' AND '" + datePo + "' "
//                    + "GROUP BY B_ID DESC";
//            System.out.println(sqlTable);
//        //}
        for (String it : list) {
            sql1 = "SELECT SUM(B_SUM) "
                    + "FROM BUD.BUDGET "
                    + "WHERE BUDGET.B_SOURSE = '" + it + "' "
                    + "AND BUDGET.B_CURREN = '1' AND BUDGET.B_ROLL LIKE '" + str1 + "%' "
                    + "AND B_DATE BETWEEN '" + dateS + "' AND '" + datePo + "'";

            sql2 = "SELECT SUM(B_SUM) "
                    + "FROM BUD.BUDGET "
                    + "WHERE BUDGET.B_SOURSE = '" + it + "' "
                    + "AND BUDGET.B_CURREN = '2' AND BUDGET.B_ROLL LIKE '" + str1 + "%' "
                    + "AND B_DATE BETWEEN '" + dateS + "' AND '" + datePo + "'";

            sql3 = "SELECT SUM(B_SUM) "
                    + "FROM BUD.BUDGET "
                    + "WHERE BUDGET.B_SOURSE = '" + it + "' "
                    + "AND BUDGET.B_CURREN = '3' AND BUDGET.B_ROLL LIKE '" + str1 + "%' "
                    + "AND B_DATE BETWEEN '" + dateS + "' AND '" + datePo + "'";

            //System.out.println("Sql1 ------------- " + sql1);
            //System.out.println("Sql2 ------------- " + sql2);
            //System.out.println("Sql3 ------------- " + sql3);
            a += bd.BalansCur(sql1);
            b += bd.BalansCur(sql2);
            c += bd.BalansCur(sql3);

            balSql1 = "" + a;
            balSql2 = "" + b;
            balSql3 = "" + c;
        }

        if (balSql1 == null) {
            balSql1 = "0";
        }
        if (balSql2 == null) {
            balSql2 = "0";
        }
        if (balSql3 == null) {
            balSql3 = "0";
        }

        //System.out.println("balSql1 ------------- " + balSql1);
        //System.out.println("balSql2 ------------- " + balSql2);
        //System.out.println("balSql3 ------------- " + balSql3);
        CursUsd = getCursUsd();
        CursEur = getCursEur();

        //  System.out.println("CursUsd ------------- " + CursUsd);
        //  System.out.println("CursEur ------------- " + CursEur);
        if (getCur().equals("USD")) {
            if (Exp.contains("Доход")) {
                numUsd = Double.parseDouble(balSql3) / Double.parseDouble(CursUsd)
                        + (Double.parseDouble(balSql2) * Double.parseDouble(CursEur) / Double.parseDouble(CursUsd))
                        + Double.parseDouble(balSql1);

                bal[0] = String.format("%.2f", numUsd);
            }

            if (Exp.contains("Расход")) {
                numUsd = Double.parseDouble(balSql3) / Double.parseDouble(CursUsd)
                        + (Double.parseDouble(balSql2) * Double.parseDouble(CursEur) / Double.parseDouble(CursUsd))
                        + Double.parseDouble(balSql1);

                bal[1] = String.format("%.2f", numUsd);
            }
        } else if (getCur().equals("EUR")) {
            if (Exp.contains("Доход")) {
                numUsd = Double.parseDouble(balSql3) / Double.parseDouble(CursEur)
                        + (Double.parseDouble(balSql1) * Double.parseDouble(CursUsd) / Double.parseDouble(CursEur))
                        + Double.parseDouble(balSql2);

                bal[0] = String.format("%.2f", numUsd);
            }

            if (Exp.contains("Расход")) {
                numUsd = Double.parseDouble(balSql3) / Double.parseDouble(CursEur)
                        + (Double.parseDouble(balSql1) * Double.parseDouble(CursUsd) / Double.parseDouble(CursEur))
                        + Double.parseDouble(balSql2);

                bal[1] = String.format("%.2f", numUsd);
            }
        } else if (getCur().equals("UAH")) {
            if (Exp.contains("Доход")) {
                numUsd = Double.parseDouble(balSql2) * Double.parseDouble(CursEur)
                        + (Double.parseDouble(balSql1) * Double.parseDouble(CursUsd))
                        + Double.parseDouble(balSql3);

                bal[0] = String.format("%.2f", numUsd);
            }

            if (Exp.contains("Расход")) {
                numUsd = Double.parseDouble(balSql2) * Double.parseDouble(CursEur)
                        + (Double.parseDouble(balSql1) * Double.parseDouble(CursUsd))
                        + Double.parseDouble(balSql3);

                bal[1] = String.format("%.2f", numUsd);
            }
        }

        return bal;
    }

    public String[] analizz(String Exp, String strTime1, String strTime2, String cur) {
        //System.out.println("This is metod String[] analizz(----)!!!");
        String sql1;
        String sql2;
        String sql3;

        String balSql1 = "";
        String balSql2 = "";
        String balSql3 = "";
        String CursUsd;
        String CursEur;
        double numUsd;
        String balans = "";
        String[] bal = new String[2];

        double a = 0;
        double b = 0;
        double c = 0;
        //   System.out.println("This is metod String[] analizz(String Exp)****************");
//        EventsView bean1;
//        FacesContext fc1 = FacesContext.getCurrentInstance();
//        ELContext elContext1 = fc1.getELContext();
//        bean1 = (EventsView) elContext1.getELResolver().getValue(elContext1, null, "treeEventsView");
//        TreeNode itemm = bean1.getSelectedNode();

//        MultiSelectView bean1;
//        FacesContext fc1 = FacesContext.getCurrentInstance();
//        ELContext elContext1 = fc1.getELContext();
//        bean1 = (MultiSelectView) elContext1.getELResolver().getValue(elContext1, null, "multiSelectView");
//        String selection = bean1.getSelection();
//        String item = "";//+ itemm;
//        System.out.println("Tree  " + itemm);
//        String str = it(itemm);
//        //System.out.println("Tree  " + selection);
//        Exp = str;
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        dateS = simpleDateFormat.format(datS);
////        datePo = simpleDateFormat.format(getDatePo());
//        //  dateS = "2014-06-01";
//        datePo = simpleDateFormat.format(datPo);
        //   System.out.println("dateS+++++++++++++datePo" + strTime1 + "-----" + strTime2);
        //System.out.println("dateS+++++++++++++datePo" + getDatS() + "-----" + getDatPo());
        Bd bd = new Bd();
        LinkedList<String> list = new LinkedList<String>();
        String[] ar = Exp.split("-");
        String str = regionUs();
        for (int i = 0; i < ar.length; i++) {
            //System.out.println("" + i +"  " + ar[i]);
        }
        //  System.out.println("+-+-+-+-+-+" + ar.length);
        if (ar.length == 2) {
            String sql = "Select IT_ID FROM BUD.Item WHERE IT_ITEM1 = '" + ar[1] + "' AND IT_ITEM2 <> 'Обмен' AND regionUser ='" + str + "'";
            // System.out.println("*-*-*-*-*" + sql);
            list = bd.itAll1(sql);

        } else if (ar.length == 3) {
            String sql = "Select IT_ID FROM BUD.Item WHERE IT_ITEM1 = '" + ar[1] + "' AND IT_ITEM2 = '" + ar[2] + "' AND IT_ITEM2 <> 'Обмен' AND regionUser ='" + str + "'";
            //System.out.println("*-*-*-*-*" + sql);
            list = bd.itAll1(sql);

        } else if (ar.length == 4) {
            String sql = "Select IT_ID FROM BUD.Item WHERE IT_ITEM1 = '" + ar[1] + "' AND IT_ITEM2 = '" + ar[2] + "' AND IT_ITEM3 ='" + ar[3] + "' AND IT_ITEM2 <> 'Обмен' AND regionUser ='" + str + "'";
            // System.out.println("*-*-*-*-*" + sql);
            list = bd.itAll1(sql);
        }

//        for(String s: list)
//        {
//            System.out.println("------------- list " + s);
//        }
//        String k = "";
//        for(String s: list)
//        {
//            k += "OR " + "BUDGET.B_SOURSE = '" + s +"'";
//            this.sqlTable = "SELECT B_ID AS ID, IT_ITEM1, IT_ITEM2, IT_ITEM3, B_SUM AS SUM, B_DATE AS DATE, CUR_CURREN AS CUR "
//                    + "FROM BUD.BUDGET, BUD.CURREN, BUD.Item "
//                    + "WHERE BUDGET.B_CURREN = CURREN.CUR_ID AND BUDGET.B_SOURSE = Item.IT_ID AND (BUDGET.B_SOURSE = '1' " + k
//                    + ") AND B_DATE BETWEEN '" + strTime1 + "' AND '" + strTime2 + "' "
//                    + "GROUP BY B_ID DESC";
//            System.out.println(sqlTable);
//        }
        for (String it : list) {
            sql1 = "SELECT SUM(B_SUM) "
                    + "FROM BUD.BUDGET "
                    + "WHERE BUDGET.B_SOURSE = '" + it + "' "
                    + "AND BUDGET.B_CURREN = '1' AND BUDGET.B_ROLL LIKE '" + str + "%' "
                    + "AND B_DATE BETWEEN '" + strTime1 + "' AND '" + strTime2 + "'";

            sql2 = "SELECT SUM(B_SUM) "
                    + "FROM BUD.BUDGET "
                    + "WHERE BUDGET.B_SOURSE = '" + it + "' "
                    + "AND BUDGET.B_CURREN = '2' AND BUDGET.B_ROLL LIKE '" + str + "%' "
                    + "AND B_DATE BETWEEN '" + strTime1 + "' AND '" + strTime2 + "'";

            sql3 = "SELECT SUM(B_SUM) "
                    + "FROM BUD.BUDGET "
                    + "WHERE BUDGET.B_SOURSE = '" + it + "' "
                    + "AND BUDGET.B_CURREN = '3' AND BUDGET.B_ROLL LIKE '" + str + "%' "
                    + "AND B_DATE BETWEEN '" + strTime1 + "' AND '" + strTime2 + "'";

             //System.out.println("Sql1 ------------- " + sql1);
            //System.out.println("Sql2 ------------- " + sql2);
            //System.out.println("Sql3 ------------- " + sql3);
            a += bd.BalansCur(sql1);
            b += bd.BalansCur(sql2);
            c += bd.BalansCur(sql3);

            balSql1 = "" + a;
            balSql2 = "" + b;
            balSql3 = "" + c;
        }

        if (balSql1.equals("")) {
            balSql1 = "0";
        }
        if (balSql2.equals("")) {
            balSql2 = "0";
        }
        if (balSql3.equals("")) {
            balSql3 = "0";
        }

         //System.out.println("balSql1 ------------- " + balSql1);
        //System.out.println("balSql2 ------------- " + balSql2);
        //System.out.println("balSql3 ------------- " + balSql3);
        CursUsd = getCursUsd();
        CursEur = getCursEur();

        //  System.out.println("CursUsd ------------- " + CursUsd);
        //  System.out.println("CursEur ------------- " + CursEur);
        //   System.out.println("/////////////////////*****************--------------------//////////////" + getCur());
        if (cur.equals("USD")) {
            if (Exp.contains("Доход")) {
                numUsd = Double.parseDouble(balSql3) / Double.parseDouble(CursUsd)
                        + (Double.parseDouble(balSql2) * Double.parseDouble(CursEur) / Double.parseDouble(CursUsd))
                        + Double.parseDouble(balSql1);

                bal[0] = String.format("%.2f", numUsd);
            }

            if (Exp.contains("Расход")) {
                numUsd = Double.parseDouble(balSql3) / Double.parseDouble(CursUsd)
                        + (Double.parseDouble(balSql2) * Double.parseDouble(CursEur) / Double.parseDouble(CursUsd))
                        + Double.parseDouble(balSql1);

                bal[1] = String.format("%.2f", numUsd);
            }
        } else if (cur.equals("EUR")) {
            if (Exp.contains("Доход")) {
                numUsd = Double.parseDouble(balSql3) / Double.parseDouble(CursEur)
                        + (Double.parseDouble(balSql1) * Double.parseDouble(CursUsd) / Double.parseDouble(CursEur))
                        + Double.parseDouble(balSql2);

                bal[0] = String.format("%.2f", numUsd);
            }

            if (Exp.contains("Расход")) {
                numUsd = Double.parseDouble(balSql3) / Double.parseDouble(CursEur)
                        + (Double.parseDouble(balSql1) * Double.parseDouble(CursUsd) / Double.parseDouble(CursEur))
                        + Double.parseDouble(balSql2);

                bal[1] = String.format("%.2f", numUsd);
            }
        } else if (cur.equals("UAH")) {
            if (Exp.contains("Доход")) {
                numUsd = Double.parseDouble(balSql2) * Double.parseDouble(CursEur)
                        + (Double.parseDouble(balSql1) * Double.parseDouble(CursUsd))
                        + Double.parseDouble(balSql3);

                bal[0] = String.format("%.2f", numUsd);
            }

            if (Exp.contains("Расход")) {
                numUsd = Double.parseDouble(balSql2) * Double.parseDouble(CursEur)
                        + (Double.parseDouble(balSql1) * Double.parseDouble(CursUsd))
                        + Double.parseDouble(balSql3);

                bal[1] = String.format("%.2f", numUsd);
            }
        }

        for (int i = 0; i < bal.length; i++) {
            //   System.out.println("*-*/-*/-*/-*/-*/-*/-*/- " + bal[i]);
        }
        return bal;
    }

    private String getCursUsd() {
        String CursUsd = "";

        PPRBean bean1;
        FacesContext fc1 = FacesContext.getCurrentInstance();
        ELContext elContext1 = fc1.getELContext();
        bean1 = (PPRBean) elContext1.getELResolver().getValue(elContext1, null, "pprBean");
        CursUsd = bean1.getCursUsd();
        //String item = "" ;//+ itemm;
        //System.out.println("Tree  " + itemm);
        //String str = it(itemm);
        //System.out.println("Tree  " + str);
        return CursUsd;
    }

    private String getCursEur() {
        String CursEur = "";

        PPRBean bean1;
        FacesContext fc1 = FacesContext.getCurrentInstance();
        ELContext elContext1 = fc1.getELContext();
        bean1 = (PPRBean) elContext1.getELResolver().getValue(elContext1, null, "pprBean");
        CursEur = bean1.getCursEur();
        //String item = "" ;//+ itemm;
        //System.out.println("Tree  " + itemm);
        //String str = it(itemm);
        //System.out.println("Tree  " + str);
        return CursEur;
    }

    public String getCur() {
        //System.out.println("getCur()" + this.cur);
        return cur;
    }

    public void setCur(String cur) {
        this.cur = cur;
        idcur = curs.get(cur);

        //System.out.println("Vibrannau valuta" + this.cur);
        //System.out.println("Vibrannau valuta" + this.cur);
    }

    public LinkedHashMap<String, String> getCurs() {
        //  System.out.println(curs);
        return curs;
    }

    public void setCurs(LinkedHashMap<String, String> curs) {
        //  System.out.println(curs);
        this.curs = curs;
    }

    public LinkedHashMap<String, String> getStaff() {
        return staff;
    }

    public void setStaff(LinkedHashMap<String, String> staff) {

        this.staff = staff;
    }

    public String getStaf() {
        return staf;
    }

    public void setStaf(String staf) {
        //  System.out.println(staff.get(staf));
        this.staf = staf;
    }

    public String getCurid() {
        return idcur;
    }

    public void setCurid(String curid) {
        this.idcur = idcur;
    }

    public Date getDate() {

        setDatee(date.toString());
        //System.out.println("gethgjkl;;");
        return date;
    }

    public void setDate(Date date) {
        if (date == null) {
            date = new Date();
        }
        //  System.out.println("setjgnkbhjgk");

        this.date = date;
    }

    public String getBalansUSD() {
        balansUSD();
        return balansUSD;
    }

    public void setBalansUSD(String balansUSD) {
        this.balansUSD = balansUSD;
    }

    public String getBalansEUR() {
        balansEUR();
        return balansEUR;
    }

    public void setBalansEUR(String balansEUR) {
        this.balansEUR = balansEUR;
    }

    public String getBalansUAH() {
        balansUAH();
        return balansUAH;
    }

    public void setBalansUAH(String balansUAH) {
        this.balansUAH = balansUAH;
    }

    public String getNumUSD() {

        return numUSD;
    }

    public void setNumUSD(String numUSD) {

        this.numUSD = numUSD;
    }

    public String getNumUSDex() {
        return numUSDex;
    }

    public void setNumUSDex(String numUSDex) {
        this.numUSDex = numUSDex;
    }

    public String getOutPut() {
        return outPut;
    }

    public void setOutPut(String outPut) {
        this.outPut = outPut;
    }

    public String getSqlTable() {
        return sqlTable;
    }

    public void setSqlTable(String sqlTable) {
        this.sqlTable = sqlTable;
    }

    public String getDateS() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateS = simpleDateFormat.format(datS);
        return dateS;
    }

    public void setDateS(String dateS) {
        this.dateS = dateS;
    }

    public String getDatePo() {
        return datePo;
    }

    public void setDatePo(String datePo) {

        this.datePo = datePo;
    }

    public Date getDatS() {
        //setDatee(datS.toString());
        return datS;
    }

    public void setDatS(Date datS) {

        this.datS = datS;
    }

    public Date getDatPo() {
        return datPo;
    }

    public void setDatPo(Date datPo) {
        this.datPo = datPo;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public StreamedContent getFile1() {
        report1();
        doun1();
        return file1;
    }

    public void setFile1(StreamedContent file1) {
        this.file1 = file1;
    }

    public String getRolle() {
        return rolle;
    }

    public void setRolle(String rolle) {
        this.rolle = rolle;
    }

    public double getCusd() {
        return Cusd;
    }

    public void setCusd(double Cusd) {
        this.Cusd = Cusd;
    }

    public boolean getCurUsdEur() {
        //  System.out.println("getCurUsdEur() " + curUsdEur);
        return curUsdEur;
    }

    public void setCurUsdEur(boolean curUsdEur) {
        //System.out.println("setCurUsdEur() " + curUsdEur);
        this.curUsdEur = curUsdEur;
        //getCurUsdEur();
    }

    public String getMass() {
        EventsView bean1;
        FacesContext fc1 = FacesContext.getCurrentInstance();
        ELContext elContext1 = fc1.getELContext();
        bean1 = (EventsView) elContext1.getELResolver().getValue(elContext1, null, "treeEventsView");

        PPRBean bean;
        FacesContext fc = FacesContext.getCurrentInstance();
        ELContext elContext = fc.getELContext();
        bean = (PPRBean) elContext.getELResolver().getValue(elContext, null, "pprBean");

        this.mass = "Cтатья: " + bean1.getSelectedNode() + " --- " + "Сумма: " + bean.getFirstname();
        return mass;
    }

    public void setMass(String mass) {
        this.mass = mass;
    }

    public String getDatee() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        datee = simpleDateFormat.format(date);
        return datee;
    }

    public void setDatee(String datee) {
        this.datee = datee;
    }

    public String getExce() {
        exe();
        return exce;
    }

    public void setExce(String exce) {
        this.exce = exce;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {

        this.id = id;
    }

    public String getId1() {

        return id1;
    }

    public void setId1(String id1) {
        this.id1 = id1;
    }

    public String getItem1() {
        return item1;
    }

    public void setItem1(String item1) {
        this.item1 = item1;
    }

    public String getSum1() {
        return sum1;
    }

    public void setSum1(String sum1) {
        this.sum1 = sum1;
    }

    public String getDate1() {
        return date1;
    }

    public void setDate1(String date1) {
        this.date1 = date1;
    }

    public String getCur1() {
        return cur1;
    }

    public void setCur1(String cur1) {
        this.cur1 = cur1;
    }

    public String getNote1() {
        return note1;
    }

    public void setNote1(String note1) {
        this.note1 = note1;
    }

    public String getAdd() {
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
    }

    public String getItemm() {
        return itemm;
    }

    public void setItemm(String itemm) {
        this.itemm = itemm;
    }

    public String getApd() {
        return apd;
    }

    public void setApd(String apd) {
        this.apd = apd;
    }

    public String getVidCur() {
        return vidCur;
    }

    public void setVidCur(String vidCur) {
        this.vidCur = vidCur;
    }

    public boolean isBat() {
        return bat;
    }

    public void setBat(boolean bat) {
        this.bat = bat;
    }

    public String getExcePO() {
        exep_o();
        return excePO;
    }

    public void setExcePO(String excePO) {
        this.excePO = excePO;
    }

    public String getExceMan() {
        exeM();
        return exceMan;
    }

    public void setExceMan(String exceMan) {
        this.exceMan = exceMan;
    }

    public String getExceSet() {
        //System.out.println("This is metod getExceMan()!!!");
        //excetS();
        return exceSet;
    }

    public void setExceSet(String exceSet) {
        this.exceSet = exceSet;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getExceDel() {
        return exceDel;
    }

    public void setExceDel(String exceDel) {
        this.exceDel = exceDel;
    }

}
