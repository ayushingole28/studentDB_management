package studentDB_proj;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.*;
import java.awt.*;

public class Gui implements ActionListener {

    JFrame jFrame = null;
    JLabel jLabelsid = null;
    JLabel jLabelname = null;
    JLabel jLabelcourse = null;
    JLabel jLabelfees = null;
    JTextField jTextFieldsid = null;
    JTextField jTextFieldname = null;
    JTextField jTextFieldcourse = null;
    JTextField jTextFieldfees = null;
    JButton jButtonfirst = null;
    JButton jButtonnext = null;
    JButton jButtonprevious = null;
    JButton jButtonlast = null;
    JButton jButtonnew;
    JButton jButtoninsert;
    JButton jButtonupdate;
    JButton jButtondelete;
    Font font;

    Connection con;
    Statement stmt;
    ResultSet rs;
    Gui() {
        jFrame = new JFrame();
        jFrame.setBounds(100, 200, 650, 400);
        font = new Font("Consolas", Font.BOLD, 22);

        jLabelsid = new JLabel("S_ID");
        jLabelsid.setBounds(40, 40, 120, 30);
        jLabelsid.setFont(font);

        jLabelname = new JLabel("Name");
        jLabelname.setBounds(40, 80, 120, 30);
        jLabelname.setFont(font);

        jLabelcourse = new JLabel("Course");
        jLabelcourse.setBounds(40, 120, 120, 30);
        jLabelcourse.setFont(font);

        jLabelfees = new JLabel("Fees");
        jLabelfees.setBounds(40, 160, 120, 30);
        jLabelfees.setFont(font);

        jTextFieldsid = new JTextField();
        jTextFieldsid.setBounds(160, 40, 200, 30);
        jTextFieldsid.setFont(font);

        jTextFieldname = new JTextField();
        jTextFieldname.setBounds(160, 80, 200, 30);
        jTextFieldname.setFont(font);

        jTextFieldcourse = new JTextField();
        jTextFieldcourse.setBounds(160, 120, 200, 30);
        jTextFieldcourse.setFont(font);

        jTextFieldfees = new JTextField();
        jTextFieldfees.setBounds(160, 160, 200, 30);
        jTextFieldfees.setFont(font);

        jButtonfirst = new JButton("First");
        jButtonfirst.setBounds(40, 200, 120, 30);
        jButtonfirst.setFont(font);
        jButtonfirst.addActionListener(this);

        jButtonnext = new JButton("Next");
        jButtonnext.setBounds(170, 200, 130, 30);
        jButtonnext.setFont(font);
        jButtonnext.addActionListener(this);

        jButtonprevious = new JButton("Previous");
        jButtonprevious.setBounds(310, 200, 130, 30);
        jButtonprevious.setFont(font);
        jButtonprevious.addActionListener(this);

        jButtonlast = new JButton("Last");
        jButtonlast.setBounds(450, 200, 130, 30);
        jButtonlast.setFont(font);
        jButtonlast.addActionListener(this);

        jButtonnew = new JButton("New");
        jButtonnew.setBounds(40, 240, 120, 30);
        jButtonnew.setFont(font);
        jButtonnew.addActionListener(this);

        jButtoninsert = new JButton("Insert");
        jButtoninsert.setBounds(170, 240, 130, 30);
        jButtoninsert.setFont(font);
        jButtoninsert.addActionListener(this);

        jButtonupdate = new JButton("Update");
        jButtonupdate.setBounds(310, 240, 130, 30);
        jButtonupdate.setFont(font);
        jButtonupdate.addActionListener(this);

        jButtondelete = new JButton("Delete");
        jButtondelete.setBounds(450, 240, 130, 30);
        jButtondelete.setFont(font);
        jButtondelete.addActionListener(this);

        jFrame.add(jLabelsid);
        jFrame.add(jLabelname);
        jFrame.add(jLabelcourse);
        jFrame.add(jLabelfees);
        jFrame.add(jTextFieldsid);
        jFrame.add(jTextFieldname);
        jFrame.add(jTextFieldcourse);
        jFrame.add(jTextFieldfees);
        jFrame.add(jButtonfirst);
        jFrame.add(jButtonnext);
        jFrame.add(jButtonprevious);
        jFrame.add(jButtonlast);
        jFrame.add(jButtonnew);
        jFrame.add(jButtoninsert);
        jFrame.add(jButtonupdate);
        jFrame.add(jButtondelete);

        jFrame.setLayout(null);
        jFrame.setVisible(true);

        connectToDB();
    }
    public void connectToDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dstud?useTimezone=true&serverTimezone=UTC", "root", "root");
            getData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void getData() {
        try {
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery("select * from list");
            if (rs.next()) {
                showData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showData() {
        try {
            jTextFieldsid.setText(rs.getString(1));
            jTextFieldname.setText(rs.getString(2));
            jTextFieldcourse.setText(rs.getString(3));
            jTextFieldfees.setText(rs.getString(4));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearALL() {
        jTextFieldcourse.setText("");
        jTextFieldfees.setText("");
        jTextFieldsid.setText("");
        jTextFieldname.setText("");
    }

    public void insertData() {
        try {
            int sid = Integer.parseInt(jTextFieldsid.getText());
            String name = jTextFieldname.getText();
            String course = jTextFieldcourse.getText();
            float fees = Float.parseFloat(jTextFieldfees.getText());

            String qry = "insert into list values(" + sid + ",'" + name + "','" + course + "'," + fees + ");";

            int n = stmt.executeUpdate(qry);
            if (n > 0) {
                JOptionPane.showMessageDialog(jFrame, "Data Inserted Successfully");
                getData();
            } else {
                JOptionPane.showMessageDialog(jFrame, "Data Insertion Error");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteData() {
        try {
            int sid = Integer.parseInt(jTextFieldsid.getText());
            String qry = "DELETE FROM list WHERE sid =" + sid + ";";
            int n = stmt.executeUpdate(qry);
            if (n > 0) {
                JOptionPane.showMessageDialog(jFrame, "Row deleted successfully");
                getData();
            } else {
                JOptionPane.showMessageDialog(jFrame, "Data Deletion Error");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateData() {
        try {
            int sid = Integer.parseInt(jTextFieldsid.getText());
            String name = jTextFieldname.getText();
            String course = jTextFieldcourse.getText();
            float fees = Float.parseFloat(jTextFieldfees.getText());

            String qry = "UPDATE list SET name = '" + name + "', course = '" + course + "', fees = " + fees + " WHERE sid = " + sid + ";";

            int n = stmt.executeUpdate(qry);
            if (n > 0) {
                JOptionPane.showMessageDialog(jFrame, "Data Updated Successfully");
                getData();
            } else {
                JOptionPane.showMessageDialog(jFrame, "Data Update Error: No rows affected");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(jFrame, "Error: " + e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent ex) {
        try {
            if (ex.getSource() == jButtonfirst) {
                if (rs.first()) {
                    showData();
                }
            } else if (ex.getSource() == jButtonnext) {
                if (rs.next()) {
                    showData();
                } else {
                    JOptionPane.showMessageDialog(jFrame, "No more records");
                }
            } else if (ex.getSource() == jButtonprevious) {
                if (rs.previous()) {
                    showData();
                } else {
                    JOptionPane.showMessageDialog(jFrame, "No previous records");
                }
            } else if (ex.getSource() == jButtonlast) {
                if (rs.last()) {
                    showData();
                }
            } else if (ex.getSource() == jButtondelete) {
                deleteData();
            } else if (ex.getSource() == jButtonupdate) {
                updateData();
            } else if (ex.getSource() == jButtoninsert) {
                insertData();
            } else if (ex.getSource() == jButtonnew) {
                clearALL();
            }
        } catch (Exception et) {
            et.printStackTrace();
        }
    }
    public static void main(String[] args) {
        Gui  s = new Gui();

    }
}


