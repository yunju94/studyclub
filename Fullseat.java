package Make;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

class Fullseat extends JFrame implements ActionListener {
    private JLabel label = new JLabel();
    private JButton del = new JButton("확인");

    private JTextField textField = new JTextField();


    @Override
    public void actionPerformed(ActionEvent e) {
        setLayout(null);
        label.setText("전화번호를 입력하십시오.");
        label.setBounds(50, 20, 150, 20);
        add(label);
        //////////////////////////
        textField.setBounds(50,50,150,30);
        add(textField);
        ////////////////////////
        del.setText("확인");
        del.setBounds(80, 100, 100, 30);
        add(del);

        del.addActionListener( new delch());

        setSize(300, 200);
        setTitle("자리 빼기");
        setVisible(true);




    }
    class delch extends JFrame implements ActionListener{

        private static Connection conn;
        private static PreparedStatement pstmt;
        private  JLabel label = new JLabel();

        @Override
        public void actionPerformed(ActionEvent e) {

            try {
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/new_schema", "root", "1234");
                pstmt = conn.prepareStatement("update test set num=? where tel=?;");
                pstmt.setString(1, "0");
                pstmt.setString(2, textField.getText());
                pstmt.executeUpdate();

                setLayout(null);
                label.setText("삭제가 완료되었습니다.");
                label.setBounds(75, 50, 150, 20);
                add(label);
                setSize(300, 200);
                setTitle("완료");
                setVisible(true);



            } catch (SQLException e1) {
                label.setText("오류 발생! 전화번호가 다릅니다.");
                e1.printStackTrace();
            }


        }
    }

}


