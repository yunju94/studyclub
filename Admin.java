package Make;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Admin extends JFrame {
    private JLabel panel = new JLabel("어서오세요 관리자님.");

    private JTextArea text = new JTextArea();
    private static Connection conn;
    private static PreparedStatement pstmt;

    Admin(){
        setLayout(null);
        panel.setBounds(0,0,300,30);
        add(panel);

        text.setBounds(0,30,600,400);
        add(text);



        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/new_schema", "root", "1234");
            pstmt = conn.prepareStatement(" select * from  admin;");
            ResultSet rs = pstmt.executeQuery();
            List<String> list = new ArrayList<>();
            while (rs.next()){
                String id = rs.getString("id");
                String name = rs.getString("name");
                String tel = rs.getString("tel");
                String address = rs.getString("address");
                String num = rs.getString("num");
                String date = rs.getString("date");

                list.add(id+ "\t" + name + "\t"+ tel + "\t" + address + "\t"+ num + "\t" + date + "\n");
            }

            text.setText(String.valueOf(list));






        } catch (SQLException e1) {
            e1.printStackTrace();
        }



        setTitle("관리자 메뉴");
        setSize(600,500);
        JPanel j = new JPanel();
        j.setBounds(0,0,600,500);
        j.setBackground(Color.white);
        add(j);

        setVisible(true);



    }

}
