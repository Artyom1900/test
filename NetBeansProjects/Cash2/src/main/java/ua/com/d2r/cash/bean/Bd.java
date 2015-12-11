/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.com.d2r.cash.bean;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;

import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;

import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.MimetypesFileTypeMap;
import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.swing.JOptionPane;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.TreeNode;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author artyom
 */
@ManagedBean(name = "bd")
@SessionScoped
public class Bd implements Serializable {

    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private InitialContext ctx;
    private DataSource ds;
//    private StreamedContent file;
//    private String report;

    public Bd() {
//        doun();
//        file = null;
    }

    public void connect() {
        //System.out.println("Connect");
        try {
            ctx = new InitialContext();
            ds = (DataSource) ctx.lookup("jdbc/cashMyBud");

        } catch (NamingException e) {
            
            System.out.println("Cash: Bd - connect() " + e.getMessage());
        }
    }

    public void connectLm() {
        //System.out.println("ConnectLm");
        try {
            ctx = new InitialContext();
            ds = (DataSource) ctx.lookup("jdbc/cashRealm");

        } catch (NamingException e) {
            System.out.println("Cash: Bd - connectLm() " + e.getMessage());
        }
    }

    public List<Budget> Select(String sql) {
        connect();

        List<Budget> list = new LinkedList<>();

        String id;
        String item;
        String sum;
        String date;
        String cur;
        String note;
        try {
            try {

                // System.out.println(sql);
                conn = ds.getConnection();
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();

                while (rs.next()) {
                    id = rs.getString("ID");

                    item = rs.getString("IT_ITEM1") + " - " + rs.getString("IT_ITEM2") + " - " + rs.getString("IT_ITEM3");

                    sum = rs.getString("SUM");

                    date = rs.getString("DATE");

                    cur = rs.getString("CUR");

                    note = rs.getString("NOTE");
                    //System.out.println(id + " " + item + " " + sum + " " + date + " " + cur);

                    list.add(new Budget(id, item, sum, date, cur, note));
                }
                
                
            } catch (SQLException e) {
                System.out.println("Cash: Bd - Select(String sql) " + e);
            }
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("Cash: Bd - Select(String sql) " + e);
            }
        }
        return list;
    }

    public LinkedHashMap<String, String> SelectCur(String sql) {
        connect();
        LinkedHashMap<String, String> list = new LinkedHashMap<>();
        String cur;
        String id;
        try {
            try {
                // System.out.println(sql);
                conn = ds.getConnection();
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();

                while (rs.next()) {

                    cur = rs.getString("CUR_CURREN");
                    //System.out.println(cur);

                    id = rs.getString("CUR_ID");
                    // System.out.println(id);

                    list.put(cur, id);
                }

            } catch (SQLException e) {
                System.out.println("Cash: Bd - SelectCur(String sql) " + e);
            }
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("Cash: Bd - SelectCur(String sql) " + e);
            }
        }
        return list;
    }

    public LinkedHashMap<String, String> SelectStaff(String sql) {
        connect();

        LinkedHashMap<String, String> list = new LinkedHashMap<>();

        String staff;
        String id;

        try {
            try {

                //System.out.println(sql);
                conn = ds.getConnection();
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();

                while (rs.next()) {

                    staff = rs.getString("S_FIO");
                    // System.out.println(staff);

                    id = rs.getString("S_ID");
                    // System.out.println(id);

                    list.put(id, staff);
                }

            } catch (SQLException e) {
                System.out.println("Cash: Bd - SelectStaff(String sql) " + e);
            }
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("Cash: Bd - SelectStaff(String sql) " + e);
            }
        }
        return list;
    }

    public boolean Add(String idcur, String sum, String date, String item, String note, String roll) {
        connect();
        boolean bul = false;
        try {
            try {
                String sql;
                System.out.println("String idcur, String sum, String date, String item, String note, String roll " + 
                        idcur + "  " + sum + "  " + date + "  " + item + "  " + note + "   " + roll);
                sql = "INSERT INTO BUD.BUDGET (B_SOURSE, B_SUM, B_DATE, B_CURREN, B_NOTE, B_ROll) "
                        + "VALUES('" + item + "', '"
                        + sum + "', '" + date + "', '" + idcur + "', '" + note + "', '" + roll + "')";

//                System.out.println(sql);
                conn = ds.getConnection();
                ps = conn.prepareStatement(sql);

                if (ps.executeUpdate() == 1) {
                    bul = true;
                    // System.out.println("bul ------------------ " + bul);
                } else {
                    bul = false;
                    // System.out.println("bul ------------------ " + bul);
                }
            } catch (SQLException e) {
                System.out.println("Cash: Bd - Add(String idcur, String sum, String date, String item, String note, String roll) " + e);
            }
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("Cash: Bd - Add(String idcur, String sum, String date, String item, String note, String roll) " + e);
            }
        }
        return bul;
    }

    public double BalansCur(String sql) {
        connect();
        double balans = 0;
        try {
            try {
                //System.out.println(sql);
                conn = ds.getConnection();
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();

                //System.out.println("ps   ----   " + ps);
                while (rs.next()) {
                    balans = rs.getDouble("SUM(B_SUM)");
                    //System.out.println(balans);
                }

            } catch (SQLException e) {
                System.out.println("Cash: Bd - BalansCur(String sql) " + e);
            }
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("Cash: Bd - BalansCur(String sql) " + e);
            }
        }
        return balans;
    }

    public String check(String sql) {
        connect();

        String id;
        String item;
        String sum;
        String date;
        String cur;
        String str = "";
        try {
            try {

                // System.out.println(sql);
                conn = ds.getConnection();
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();

                while (rs.next()) {
                    id = rs.getString("ID");

                    item = rs.getString("ITEM");

                    sum = rs.getString("SUM");

                    date = rs.getString("DATE");

                    cur = rs.getString("CUR");
                    // System.out.println(id + " " + item + " " + sum + " " + date + " " + cur);
                }

            } catch (SQLException e) {
                System.out.println("Cash: Bd - check(String sql) " + e);
            }
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("Cash: Bd - check(String sql) " + e);
            }
        }
        return str;
    }

    public String SelectLogin(String sql) {
        connect();

        String id = "";
        String login = "";
        String pass = "";

        try {
            try {

                //System.out.println(sql);
                conn = ds.getConnection();
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();

                while (rs.next()) {
                    id = rs.getString("users.id");

                    login = rs.getString("users.username");

                    pass = rs.getString("users.password");

                }

            } catch (SQLException e) {
                System.out.println("Cash: Bd - SelectLogin(String sql) " + e);
            }
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("Cash: Bd - SelectLogin(String sql) " + e);
            }
        }
        return id;
    }

    public void reportSelect(String sql, String exp) {
        try {
            Document document = new Document();
            new File(System.getProperty("user.home"));
            String address = "" + FileSystemView.getFileSystemView().getHomeDirectory();
            PdfWriter.getInstance(document, new FileOutputStream(address + "/Report.pdf")).getAcroForm();

            document.open();
            //System.out.println("********************* document.open() **********************************");
            // Image image = Image.getInstance("1.jpg");
            //  document.add(new Paragraph("Image"));
            //  document.add(image);

            BaseFont bf = BaseFont.createFont("/MyriadPro/MyriadPro-Regular.otf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            final Font font1 = new Font(bf, 16, Font.BOLD);

            Table bean1;
            FacesContext fc1 = FacesContext.getCurrentInstance();
            ELContext elContext1 = fc1.getELContext();
            bean1 = (Table) elContext1.getELResolver().getValue(elContext1, null, "table");
            Date dss = bean1.getDatS();
            Date dpo = bean1.getDatPo();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String strTime1 = simpleDateFormat.format(dss);
            String strTime2 = simpleDateFormat.format(dpo);

            String cur = bean1.getCur();

            EventsView bean2;
            FacesContext fc2 = FacesContext.getCurrentInstance();
            ELContext elContext2 = fc2.getELContext();
            bean2 = (EventsView) elContext2.getELResolver().getValue(elContext1, null, "treeEventsView");
            TreeNode itemm = bean2.getSelectedNode();
            String it = bean1.it(itemm) + "  " + exp + " " + cur;
            //System.out.println("-*/-*/-*/-*/-*/-*/-*/-*/*-/-*/-*/-*/-*/-*/  " + it);
            document.add(new Paragraph("D2R Report", FontFactory.getFont(FontFactory.TIMES_BOLD, 18, Font.BOLD, BaseColor.RED)));
            //document.add(new Paragraph("Дата формирования:" + new Date().toString()));
            document.add(new Paragraph(" " + strTime1 + "---" + strTime2));
            document.add(new Paragraph("-----------------------------------"));
            // document.add(new Paragraph("C - " + dss + "   по -" + dpo));
            PdfPTable table = new PdfPTable(6);
            table.setWidths(new int[]{3, 20, 5, 8, 4, 7});

            document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------------"));
            Paragraph p3 = new Paragraph(it, font1);
            document.add(p3);
            //document.add(new Paragraph(lk, FontFactory.getFont(FontFactory.TIMES_BOLD, 16, Font.BOLD)));
            document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------------"));

            PdfPCell cell = new PdfPCell();
            cell.setColspan(6);

            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(BaseColor.GREEN);
            table.addCell(cell);
            table.addCell("ID");
            table.addCell("ITEM");
            table.addCell("SUM");
            table.addCell("DATE");
            table.addCell("CUR");
            table.addCell("NOTE");

            try {
                try {
                    //System.out.println(sql);
                    connect();
                    conn = ds.getConnection();
                    ps = conn.prepareStatement(sql);
                    rs = ps.executeQuery();
                    while (rs.next()) {

                        table.addCell(rs.getString("ID"));

                        table.addCell(new Phrase(rs.getString("IT_ITEM1") + " - " + rs.getString("IT_ITEM2") + " - " + rs.getString("IT_ITEM3"), new Font(bf, 12)));
                        table.addCell(rs.getString("SUM"));
                        table.addCell(rs.getString("DATE"));
                        table.addCell(rs.getString("CUR"));
                        table.addCell(new Phrase(rs.getString("NOTE"), new Font(bf, 12)));
                    }
                } catch (SQLException e) {
                    System.out.println("Cash: Bd - reportSelect(String sql) " + e);
                }
            } finally {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.out.println("Cash: Bd - reportSelect(String sql) " + e);
                }
            }
            document.add(table);

            document.close();
            //System.out.println("********************* document.close() **********************************");

            //System.out.println("save");
        } catch (DocumentException | IOException e) {
            System.out.println("Cash: Bd - reportSelect(String sql) " + e);
        }
    }

    public void reportSelectAll(LinkedList<Item> list1, LinkedList<Item> list2) {
        try {
            Document document = new Document();
            String address = "" + FileSystemView.getFileSystemView().getHomeDirectory() + "/Report.pdf";
            //System.out.println("**** " + address);
            PdfWriter.getInstance(document, new FileOutputStream(address)).getAcroForm();

            Table t = new Table();
            String st = t.regionUs();
            document.open();
            //System.out.println("********************* document.open() **********************************");
            BaseFont bf = BaseFont.createFont("/MyriadPro/MyriadPro-Regular.otf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            final Font font1 = new Font(bf, 16, Font.BOLD);
            Table bean1;
            FacesContext fc1 = FacesContext.getCurrentInstance();
            ELContext elContext1 = fc1.getELContext();
            bean1 = (Table) elContext1.getELResolver().getValue(elContext1, null, "table");
            Date dss = bean1.getDatS();
            Date dpo = bean1.getDatPo();
            String kk = "Company-Расход-";

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String strTime1 = simpleDateFormat.format(dss);
            String strTime2 = simpleDateFormat.format(dpo);
            PdfPTable table = null;
            String k = "";
            document.add(new Paragraph("D2R Report", FontFactory.getFont(FontFactory.TIMES_BOLD, 18, Font.BOLD, BaseColor.RED)));
            document.add(new Paragraph(" " + strTime1 + "---" + strTime2));

            bean1.show1(kk, strTime1, strTime2);
            String po = bean1.getNumUSDex();

            Table bean2;
            FacesContext fc2 = FacesContext.getCurrentInstance();
            ELContext elContext2 = fc2.getELContext();
            bean1 = (Table) elContext1.getELResolver().getValue(elContext1, null, "table");
            String cur = bean1.getCur();

            kk = "Company-Доход-";
            bean1.show1(kk, strTime1, strTime2);
            //System.out.println("/-*/-/-/* " + po);
            double bal = Double.parseDouble(bean1.getNumUSD());
            //System.out.println("/-*/-/-/* " + bean1.getNumUSDex());
            double kl = bal - Double.parseDouble(po);
            String lk = "Прибыль  " + String.format("%.2f", kl) + " " + cur;

            kk = "Company-Расход-";
            bean1.show1(kk, strTime1, strTime2);

            document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------------"));
            Paragraph p3 = new Paragraph(lk, font1);
            document.add(p3);
            //document.add(new Paragraph(lk, FontFactory.getFont(FontFactory.TIMES_BOLD, 16, Font.BOLD)));
            //document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------------"));

            kk = "Company-Доход-";
            bean1.show1(kk, strTime1, strTime2);

            String ex21 = bean1.getNumUSD();
            String exi21 = "Доход  " + ex21 + " " + cur;

            document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------------"));

            Paragraph p4 = new Paragraph(exi21, font1);
            document.add(p4);
            //document.add(new Paragraph(exi1, FontFactory.getFont(FontFactory.TIMES_BOLD, 16, Font.BOLD)));
            //document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------------"));

            String str2 = null;
            for (Item str : list2) {
                Table tab = new Table();
                kk = "Company-Доход-" + str.getIt_item2();
                //System.out.println("*-*-*-" + kk);
                String[] ar = tab.analizz(kk, strTime1, strTime2, cur);

                String exp = "";
                if (ar[1] == null && ar[0] != null) {
                    // System.out.println("NumUSDex--------" + ar[1]);
                    exp = ar[0];
                } else if (ar[1] != null && ar[0] != null) {
                    //System.out.println("NumUSD----------" + ar[0]);
                    // System.out.println("NumUSDex--------" + ar[1]);
                    exp = ar[0];
                }
                //System.out.println("----------------------------- " + str.getIt_item2());
                String par = "" + str.getIt_item2() + "   " + exp + " " + cur;

                if (!par.equals(str2)) {
                    str2 = par;
                    final Font font = new Font(bf, 14, Font.BOLD);
                    Paragraph p = new Paragraph(par, font);
                    document.add(p);
                    //document.add(new Paragraph(par, FontFactory.getFont(FontFactory.TIMES_BOLD, 14, Font.BOLD)));
                }
            }

            //document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------------"));
            String ex = bean1.getNumUSDex();
            String exi = "Расход  " + po + " " + cur;

            document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------------"));

            Paragraph p1 = new Paragraph(exi, font1);
            document.add(p1);

            //document.add(new Paragraph(exi, FontFactory.getFont(FontFactory.TIMES_BOLD, 16, Font.BOLD)));
            //document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------------"));
            String str1 = null;
            for (Item str : list1) {
                Table tab = new Table();
                kk = "Company-Расход-" + str.getIt_item2();
                //System.out.println("*-*-*-" + kk);
                String[] ar = tab.analizz(kk, strTime1, strTime2, cur);

                String exp = "";
                if (ar[1] != null && ar[0] == null) {
                    //System.out.println("NumUSDex--------" + ar[1]);
                    exp = ar[1];
                } else if (ar[1] != null && ar[0] != null) {
                    //System.out.println("NumUSD----------" + ar[0]);
                    //System.out.println("NumUSDex--------" + ar[1]);
                    exp = ar[1];
                }
                String par = "" + str.getIt_item2() + "   " + exp + " " + cur;

                if (!par.equals(str1)) {
                    str1 = par;
                    final Font font = new Font(bf, 14, Font.BOLD);
                    Paragraph p = new Paragraph(par, font);
                    document.add(p);
                }
            }

            document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------------"));
            document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------------"));

            document.add(p1);
            int i = 0;
            for (Item str : list1) {

                // document.add(new Paragraph("C - " + dss + "   по -" + dpo));
                table = new PdfPTable(6);
                table.setWidths(new int[]{3, 20, 5, 8, 4, 7});
                PdfPCell cell = new PdfPCell(new Paragraph(7));
                Table tab = new Table();
                kk = "Company-Расход-" + str.getIt_item2() + "-" + str.getIt_item3();
                //System.out.println("*-*-*-" + kk);
                String[] ar = tab.analizz(kk, strTime1, strTime2, cur);

                String exp = "";
                if (ar[1] != null && ar[0] == null) {
                    // System.out.println("NumUSDex--------" + ar[1]);
                    exp = ar[1];
                } else if (ar[1] != null && ar[0] != null) {
                    //System.out.println("NumUSD----------" + ar[0]);
                    // System.out.println("NumUSDex--------" + ar[1]);
                    exp = ar[1];
                }
                //String par = "" + str.getIt_item2() + " - " + str.getIt_item3() + "   " + exp + " " + cur;

                String par = null;

                if (str.getIt_item3().equals("")) {
                    if (i != 0) {
                        document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------------"));
                    }
                    par = "" + str.getIt_item2() + "   " + exp + " " + cur;
                    i++;
                } else if (!str.getIt_item3().equals("")) {
                    par = "" + str.getIt_item2() + " - " + str.getIt_item3() + "   " + exp + " " + cur;
                }

                if (par != null) {
                    final Font font = new Font(bf, 14, Font.BOLD);
                    Paragraph p = new Paragraph(par, font);
                    document.add(p);

                    //table.addCell(new Phrase(rs.getString("IT_ITEM1") + " - " + rs.getString("IT_ITEM2") + " - " + rs.getString("IT_ITEM3"), new Font(bf, 12)));
                }

                //if (!str.getIt_item3().equals("")) {
//                final Font font = new Font(bf, 14, Font.BOLD);
//                Paragraph p = new Paragraph(par, font);
//                document.add(p);
                //}
//                if(str.getIt_item3().equals("")){
//                document.add(new Paragraph(par, FontFactory.getFont(FontFactory.TIMES_BOLD, 12, Font.BOLD)));
//                }
                //document.add(new Paragraph("-----------------------------------"));
                //document.add(new Paragraph(" "));
                cell.setColspan(6);

                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(BaseColor.GREEN);
                table.addCell(cell);
                table.addCell("ID");
                table.addCell("ITEM");
                table.addCell("SUM");
                table.addCell("DATE");
                table.addCell("CUR");
                table.addCell("NOTE");

                String id = "";
                String item = "";
                String sum = "";
                String date = "";
                String curs = "";
                String note = "";

                try {
                    try {

                        k = "" + "Item.IT_ITEM2 = '" + str.getIt_item2() + "'";
                        String k1 = "" + "Item.IT_ITEM3 = '" + str.getIt_item3() + "'";
                        String sql = "SELECT L.B_ID, IT_ITEM1, IT_ITEM2, IT_ITEM3, L.B_SUM, L.B_DATE, K.CUR_CURREN, L.B_NOTE "
                                + "FROM BUD.BUDGET AS L, BUD.CURREN AS K, BUD.Item, BUD.BUDGET AS R "
                                + "WHERE Item.IT_ITEM1 = 'Расход' AND Item.IT_ID = L.B_SOURSE AND Item.IT_ID = R.B_SOURSE AND L.B_ROLL LIKE '" + st + "%' "
                                + "AND " + k + " AND " + k1
                                + " AND L.B_DATE BETWEEN '" + strTime1 + "' AND '" + strTime2 + "' "
                                + "AND L.B_CURREN = K.CUR_ID "
                                + "GROUP BY L.B_ID";

                        //System.out.println(sql);
                        connect();
                        conn = ds.getConnection();
                        ps = conn.prepareStatement(sql);
                        rs = ps.executeQuery();
                        //if(rs.next() != true){
                        //document.add(new Paragraph(" "));
                        while (rs.next()) {
                            //        if(rs.next() != true){continue;}
                            //document.add(new Paragraph(" "));

                            id = rs.getString("B_ID");
                            item = rs.getString("IT_ITEM1") + " - " + rs.getString("IT_ITEM2") + " - " + rs.getString("IT_ITEM3");
                            sum = rs.getString("B_SUM");
                            date = rs.getString("B_DATE");
                            curs = rs.getString("CUR_CURREN");
                            note = rs.getString("B_NOTE");

                            //System.out.println("//////////*************-------------------" + id + "****" + item + "*****" + sum + "*****" + date + " ******" + curs);
                            //BaseFont bf = BaseFont.createFont("/MyriadPro/MyriadPro-Regular.otf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                            table.addCell(rs.getString("B_ID"));
                            table.addCell(new Phrase(rs.getString("IT_ITEM1") + " - " + rs.getString("IT_ITEM2") + " - " + rs.getString("IT_ITEM3"), new Font(bf, 12)));

                            table.addCell(rs.getString("B_SUM"));
                            table.addCell(rs.getString("B_DATE"));
                            table.addCell(rs.getString("CUR_CURREN"));
                            table.addCell(new Phrase(rs.getString("B_NOTE"), new Font(bf, 12)));
                            //document.add(new Paragraph("-----------------------------------"));
                        }

                        //} else {continue;}
                        if (id.equals("")) {
                            continue;
                        }

                    } catch (SQLException e) {
                        System.out.println("Cash: Bd - reportSelectAll(LinkedList<Item> list1, LinkedList<Item> list2) " + e);
                    }
                } finally {
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        System.out.println("Cash: Bd - reportSelectAll(LinkedList<Item> list1, LinkedList<Item> list2) " + e);
                    }
                }
                //System.out.println("/*-/*-/*/-*/-*/-*/-/-*/-*/-*/-*/-/-*/-*/*" + table.getId());
                //document.add(new Paragraph("-----------------------------------"));
                //if(rs.getString("B_ID").equals("")){continue;}
                //System.out.println("/////////////////////////////////////////" + table.);
                document.add(new Paragraph(" "));
                document.add(table);
                //document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------------"));
//                document.add(new Paragraph(" "));
//                document.add(new Paragraph(" "));
            }

            kk = "Company-Доход-";
            bean1.show1(kk, strTime1, strTime2);

            String ex1 = bean1.getNumUSD();
            String exi1 = "Доход  " + ex1 + " " + cur;

            document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------------"));
            document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------------"));

            Paragraph p2 = new Paragraph(exi1, font1);
            document.add(p2);
            //document.add(new Paragraph(exi1, FontFactory.getFont(FontFactory.TIMES_BOLD, 16, Font.BOLD)));
            //document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------------"));

//            String str3 = null;
//            for (Item str : list2) {
//                Table tab = new Table();
//                kk = "Company-Доход-" + str.getIt_item2();
//                //System.out.println("*-*-*-" + kk);
//                String[] ar = tab.analizz(kk, strTime1, strTime2, cur);
//
//                String exp = "";
//                if (ar[1] == null && ar[0] != null) {
//                    // System.out.println("NumUSDex--------" + ar[1]);
//                    exp = ar[0];
//                } else if (ar[1] != null && ar[0] != null) {
//                    //System.out.println("NumUSD----------" + ar[0]);
//                    // System.out.println("NumUSDex--------" + ar[1]);
//                    exp = ar[0];
//                }
//                String par = "" + str.getIt_item2() + "   " + exp + " " + cur;
//
//                if (!par.equals(str3) && !str.getIt_item3().equals("")) {
//                    str3 = par;
//                    final Font font = new Font(bf, 14, Font.BOLD);
//                    Paragraph p = new Paragraph(par, font);
//                    document.add(p);
//                    //document.add(new Paragraph(par, FontFactory.getFont(FontFactory.TIMES_BOLD, 14, Font.BOLD)));
//                }
//            }
            //document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------------"));
            //BaseFont bf = BaseFont.createFont("/MyriadPro/MyriadPro-Regular.otf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            int ii = 0;
            for (Item str : list2) {

                // document.add(new Paragraph("C - " + dss + "   по -" + dpo));
                table = new PdfPTable(6);
                table.setWidths(new int[]{3, 20, 5, 8, 4, 7});
                PdfPCell cell = new PdfPCell(new Paragraph(7));
                Table tab = new Table();
                kk = "Company-Доход-" + str.getIt_item2() + "-" + str.getIt_item3();
                //System.out.println("*-*-*-" + kk);
                String[] ar = tab.analizz(kk, strTime1, strTime2, cur);

                String exp = "";
                if (ar[1] == null && ar[0] != null) {
                    // System.out.println("NumUSDex--------" + ar[1]);
                    exp = ar[0];
                } else if (ar[1] != null && ar[0] != null) {
                    //System.out.println("NumUSD----------" + ar[0]);
                    // System.out.println("NumUSDex--------" + ar[1]);
                    exp = ar[0];
                }

                String par = null;
                if (str.getIt_item3().equals("")) {
                    if (ii != 0) {
                        document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------------"));
                        i++;
                    }
//                    document.add(new Paragraph(" "));
//                    document.add(new Paragraph(" "));
                    par = "" + str.getIt_item2() + "   " + exp + " " + cur;

                } else if (!str.getIt_item3().equals("")) {
                    par = "" + str.getIt_item2() + " - " + str.getIt_item3() + "   " + exp + " " + cur;
                }

                if (par != null) {
                    final Font font = new Font(bf, 14, Font.BOLD);
                    Paragraph p = new Paragraph(par, font);
                    document.add(p);

                    //table.addCell(new Phrase(rs.getString("IT_ITEM1") + " - " + rs.getString("IT_ITEM2") + " - " + rs.getString("IT_ITEM3"), new Font(bf, 12)));
                }
//                if(str.getIt_item3().equals("")){
//                document.add(new Paragraph(par, FontFactory.getFont(FontFactory.TIMES_BOLD, 12, Font.BOLD)));
//                }
                //document.add(new Paragraph("-----------------------------------"));
                // document.add(new Paragraph(" "));
                cell.setColspan(6);

                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(BaseColor.GREEN);
                table.addCell(cell);
                table.addCell("ID");
                table.addCell("ITEM");
                table.addCell("SUM");
                table.addCell("DATE");
                table.addCell("CUR");
                table.addCell("NOTE");

                String id = "";
                String item = "";
                String sum = "";
                String date = "";
                String curs = "";
                String note = "";

                try {
                    try {

                        k = "" + "Item.IT_ITEM2 = '" + str.getIt_item2() + "'";
                        String k1 = "" + "Item.IT_ITEM3 = '" + str.getIt_item3() + "'";
                        String sql = "SELECT L.B_ID, IT_ITEM1, IT_ITEM2, IT_ITEM3, L.B_SUM, L.B_DATE, K.CUR_CURREN, L.B_NOTE "
                                + "FROM BUD.BUDGET AS L, BUD.CURREN AS K, BUD.Item, BUD.BUDGET AS R "
                                + "WHERE Item.IT_ITEM1 = 'Доход' AND Item.IT_ID = L.B_SOURSE AND Item.IT_ID = R.B_SOURSE AND L.B_ROLL LIKE '" + st + "%' "
                                + "AND " + k + " AND " + k1
                                + " AND L.B_DATE BETWEEN '" + strTime1 + "' AND '" + strTime2 + "' "
                                + "AND L.B_CURREN = K.CUR_ID "
                                + "GROUP BY L.B_ID";

                        //System.out.println("+++++++++++++++++++++++--------------------- " + sql);
                        connect();
                        conn = ds.getConnection();
                        ps = conn.prepareStatement(sql);
                        rs = ps.executeQuery();
                        //if(rs.next() != true){

                        while (rs.next()) {
                            //        if(rs.next() != true){continue;}
                            document.add(new Paragraph(" "));

                            id = rs.getString("B_ID");
                            item = rs.getString("IT_ITEM1") + " - " + rs.getString("IT_ITEM2") + " - " + rs.getString("IT_ITEM3");
                            sum = rs.getString("B_SUM");
                            date = rs.getString("B_DATE");
                            curs = rs.getString("CUR_CURREN");
                            note = rs.getString("B_NOTE");

                            //System.out.println("//////////*************-------------------" + id + "****" + item + "*****" + sum + "*****" + date + " ******" + curs);
                            //BaseFont bf = BaseFont.createFont("/MyriadPro/MyriadPro-Regular.otf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                            table.addCell(rs.getString("B_ID"));
                            table.addCell(new Phrase(rs.getString("IT_ITEM1") + " - " + rs.getString("IT_ITEM2") + " - " + rs.getString("IT_ITEM3"), new Font(bf, 12)));
                            table.addCell(rs.getString("B_SUM"));
                            table.addCell(rs.getString("B_DATE"));
                            table.addCell(rs.getString("CUR_CURREN"));
                            table.addCell(new Phrase(rs.getString("B_NOTE"), new Font(bf, 12)));
                            //table.addCell(rs.getString("B_NOTE"));
                            //document.add(new Paragraph("-----------------------------------"));
                        }

                        //} else {continue;}
                        if (id.equals("")) {
                            continue;
                        }

                    } catch (SQLException e) {
                        System.out.println("Cash: Bd - reportSelectAll(LinkedList<Item> list1, LinkedList<Item> list2) " + e);
                    }
                } finally {
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        System.out.println("Cash: Bd - reportSelectAll(LinkedList<Item> list1, LinkedList<Item> list2) " + e);
                    }
                }
                //System.out.println("/*-/*-/*/-*/-*/-*/-/-*/-*/-*/-*/-/-*/-*/*" + table.getId());
                //document.add(new Paragraph("-----------------------------------"));
                //if(rs.getString("B_ID").equals("")){continue;}
                //System.out.println("/////////////////////////////////////////" + table.);
                document.add(table);
//                if (b == true) {
//                    document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------------"));
//                    document.add(new Paragraph(" "));
//                    document.add(new Paragraph(" "));
//                    b = false;
//                }
            }

            document.close();
            //System.out.println("********************* document.close() **********************************");

            //System.out.println("save");
        } catch (DocumentException | IOException e) {
            System.out.println("Cash: Bd - reportSelectAll(LinkedList<Item> list1, LinkedList<Item> list2) " + e);
        } catch (InterruptedException ex) {
            Logger.getLogger(Bd.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Cash: Bd - reportSelectAll(LinkedList<Item> list1, LinkedList<Item> list2) " + ex);
        }
    }

    public List<String> selectItem1(String sql) {
        connect();
        List<String> list = new LinkedList<>();

        String item1;

        try {
            try {
                // System.out.println(sql);
                conn = ds.getConnection();
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();

                while (rs.next()) {
                    item1 = rs.getString("ID");
                    list.add(new String(item1));
                }
            } catch (SQLException e) {
                System.out.println("" + e);
            }
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("Cash: Bd - selectItem1(String sql) " + e);
            }
        }
        return list;
    }

    public LinkedList<String> SelectItem2(String sql) {
        connect();
        LinkedList<String> list = new LinkedList<>();
        String item;
        try {
            try {
                // System.out.println(sql);
                conn = ds.getConnection();
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next()) {
                    item = rs.getString("IT_ITEM2");
                    list.add(new String(item));
                }
            } catch (SQLException e) {
                System.out.println("" + e);
            }
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("Cash: Bd - SelectItem2(String sql) " + e);
            }
        }
        return list;
    }

    public LinkedList<String> SelectItem3(String sql) {
        //System.out.println("This is metod SelectItem3()!!!");
        connect();
        LinkedList<String> list = new LinkedList<>();
        String id;
        try {
            try {
                //System.out.println("/**/*/*/*/*/*//"+sql);
                conn = ds.getConnection();
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next()) {
                    id = rs.getString("IT_ID");
                    //System.out.println("/*/*/*/*/*/*" + id);
                    list.add(new String(id));
                }
            } catch (SQLException e) {
                System.out.println("Cash: Bd - SelectItem3(String sql) " + e);
            }
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("Cash: Bd - SelectItem3(String sql) " + e);
            }
        }
        return list;
    }

    public LinkedList<String> SelectItem4(String sql) {
        connect();
        LinkedList<String> list = new LinkedList<String>();
        String item;
        try {
            try {
                // System.out.println(sql);
                conn = ds.getConnection();
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next()) {
                    item = rs.getString("IT_ITEM3");
                    list.add(new String(item));
                }
            } catch (SQLException e) {
                System.out.println("Cash: Bd - SelectItem4(String sql) " + e);
            }
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("Cash: Bd - SelectItem4(String sql) " + e);
            }
        }
        return list;
    }

    //Список по которому формируется дерево
    public LinkedList<Item> SelectItem5() {
        connect();
        LinkedList<Item> list = new LinkedList<Item>();

        String it_id;
        String it_item1;
        String it_item2;
        String it_item3;
        String it_del;

        try {
            try {
                // System.out.println(sql);
                Table table = new Table();
                String str = table.regionUs();
                //System.out.println("--------------------------------------- " + str);
                String sql = "SELECT * FROM BUD.Item WHERE regionUser = '" + str + "' GROUP BY IT_ITEM1, IT_ITEM2, IT_ITEM3 DESC";
                //System.out.println("******************* " + sql);
                conn = ds.getConnection();
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next()) {
                    it_id = rs.getString("IT_ID");
                    it_item1 = rs.getString("IT_ITEM1");
                    it_item2 = rs.getString("IT_ITEM2");
                    it_item3 = rs.getString("IT_ITEM3");
                    it_del = rs.getString("IT_DEL");

                    Item it = new Item(it_id, it_item1, it_item2, it_item3, it_del);
                    list.add(it);
                }
            } catch (SQLException e) {
                System.out.println("Cash: Bd - SelectItem5() " + e);
            }
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("Cash: Bd - SelectItem5() " + e);
            }
        }
        return list;
    }

    public void UpdateItem1(String sql) {
        //System.out.println("This is metod UpdateItem1(String sql)!!!");
        connect();
        //String id;
        try {
            try {
                //System.out.println("/**/*/*/*/*/*//" + sql);
                conn = ds.getConnection();
                ps = conn.prepareStatement(sql);
                ps.executeUpdate();

            } catch (SQLException e) {
                System.out.println("Cash: Bd - UpdateItem1(String sql) " + e);
            }
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("Cash: Bd - UpdateItem1(String sql) " + e);
            }
        }
    }

    public void InsertItem2(String sql) {
        //System.out.println("This is metod InsertItem2(String sql)!!!");
        connect();
        String id;
        try {
            try {
                //System.out.println("/**/*/*/*/*/*//" + sql);
                conn = ds.getConnection();
                ps = conn.prepareStatement(sql);
                ps.executeUpdate();

            } catch (SQLException e) {
                System.out.println("Cash: Bd - InsertItem2(String sql) " + e);
            }
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("Cash: Bd - InsertItem2(String sql) " + e);
            }
        }
    }

    public String itAll(String sql) {
        connect();
        String it = "";
        try {
            try {
                System.out.println(sql);
                conn = ds.getConnection();
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();

                //System.out.println("ps   ----   " + ps);
                while (rs.next()) {
                    it = rs.getString("IT_ID");
                    //System.out.println(balans);
                }

            } catch (SQLException e) {
                System.out.println("Cash: Bd - itAll(String sql) " + e);
            }
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("Cash: Bd - itAll(String sql) " + e);
            }
        }
        System.out.println("idItem: **************: " + it);
        return it;
    }

    public LinkedList<String> itAll1(String sql) {
        connect();
        LinkedList<String> list = new LinkedList<String>();
        String it_id;
        try {
            try {
                // System.out.println(sql);
                conn = ds.getConnection();
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next()) {
                    it_id = rs.getString("IT_ID");
                    list.add(it_id);
                }
            } catch (SQLException e) {
                System.out.println("Cash: Bd - itAll1(String sql) " + e);
            }
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("Cash: Bd - itAll1(String sql) " + e);
            }
        }
        return list;
    }

    public LinkedList<Item> itAll2(String sql) {
        connect();
        LinkedList<Item> list = new LinkedList<Item>();
        String it_id;
        String it_item2;
        String it_item3;
        try {
            try {
                // System.out.println(sql);
                conn = ds.getConnection();
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next()) {
                    it_id = rs.getString("IT_ID");
                    it_item2 = rs.getString("IT_ITEM2");
                    it_item3 = rs.getString("IT_ITEM3");
                    Item item = new Item(it_id, it_item2, it_item3);
                    list.add(item);
                }
            } catch (SQLException e) {
                System.out.println("Cash: Bd - itAll2(String sql) " + e);
            }
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("Cash: Bd - itAll2(String sql) " + e);
            }
        }
        return list;
    }

    public LinkedList<String> listStaff(String sql) {
        connectLm();
        LinkedList<String> list = new LinkedList<String>();
        String staff;
        try {
            try {
                // System.out.println(sql);
                conn = ds.getConnection();
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next()) {
                    staff = rs.getString("fio");
                    list.add(staff);
                }
            } catch (SQLException e) {
                System.out.println("Cash: Bd - listStaff(String sql) " + e);
            }
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("Cash: Bd - listStaff(String sql) " + e);
            }
        }
        return list;
    }

    public void avtoriz(String sql) {
        connectLm();
        try {
            try {
                // System.out.println(sql);
                conn = ds.getConnection();
                ps = conn.prepareStatement(sql);
                ps.executeUpdate();

            } catch (SQLException e) {
                System.out.println("Cash: Bd - avtoriz(String sql) " + e);
            }
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("Cash: Bd - avtoriz(String sql) " + e);
            }
        }
    }

    public String st(String sql) {
        connectLm();
        String staff = "";
        try {
            try {
                // System.out.println(sql);
                conn = ds.getConnection();
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();
                if (rs.next()) {
                    staff = rs.getString("id");
                }
            } catch (SQLException e) {
                System.out.println("Cash: Bd - st(String sql) " + e);
            }
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("Cash: Bd - st(String sql) " + e);
            }
        }
        return staff;
    }

    public void del(String sql) {
        connect();
        try {
            try {
                // System.out.println(sql);
                conn = ds.getConnection();
                ps = conn.prepareStatement(sql);
                ps.executeUpdate();

            } catch (SQLException e) {
                System.out.println("Cash: Bd - del(String sql) " + e);
            }
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("Cash: Bd - del(String sql) " + e);
            }
        }
    }

    public Date calend(String sql) {
        connect();
        Date date = null;
        try {
            try {
                // System.out.println(sql);
                conn = ds.getConnection();
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();
                if (rs.next()) {
                    date = rs.getDate("date");
                }
            } catch (SQLException e) {
                System.out.println("Cash: Bd - calend(String sql) " + e);
            }
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("Cash: Bd - calend(String sql) " + e);
            }
        }
        return date;
    }

    public Budget selDel(String sql) {
        connectLm();
        Budget bud = null;
        String sourse = "";
        try {
            try {
                // System.out.println(sql);
                conn = ds.getConnection();
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();
                if (rs.next()) {
                    sourse = "" + rs.getString("IT_ITEM1") + " - " + rs.getString("IT_ITEM2") + " - " + rs.getString("IT_ITEM3");
                    bud = new Budget(rs.getString("B_ID"), sourse, rs.getString("B_DATE"), rs.getString("B_SUM"), rs.getString("CUR_CURREN"), rs.getString("B_NOTE"));
                }
            } catch (SQLException e) {
                System.out.println("Cash: Bd - selDel(String sql) " + e);
            }
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("Cash: Bd - selDel(String sql) " + e);
            }
        }
        return bud;
    }

    public String selRegionUser(String sql) {
        connectLm();
        String roll = "";
        try {
            try {
                // System.out.println(sql);
                conn = ds.getConnection();
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();
                if (rs.next()) {
                    roll = rs.getString("regionUser");
                }
            } catch (SQLException e) {
                System.out.println("Cash: Bd - selRegionUser(String sql) " + e);
            }
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("Cash: Bd - selRegionUser(String sql) " + e);
            }
        }
        return roll;
    }
}
