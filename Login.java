package Make;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

//일반 회원가입의 경우 좌석 선택을 할 수 있게, 좌석이 비어있는 경우와 자리잡힌 경우 나누어서.
//관리자 모드 "admin"으로 들어갈 경우 전체 사용한 현황 = 기존의 데이타 베이스의 정보가 나오도록 설정.

class Login_out extends JFrame{
    private JLabel title = new JLabel("Login");
    private JLabel id = new JLabel("I  D");
    private JLabel pass = new JLabel("PASSWORD");

    private JTextField idText = new JTextField();
    private  JPasswordField passText = new JPasswordField();

    private JButton log = new JButton("로그인");
    private JButton gaib = new JButton("회원가입");

    Login_out(){
        setLayout(null);

        ImageIcon icon = new ImageIcon("book.png");
        JLabel lb1 = new JLabel(icon, JLabel.CENTER);
        JLabel lb2 = new JLabel(icon, JLabel.CENTER);
        lb1.setBounds(50,30,50,50);
        lb2.setBounds(250,30,50,50);
        add(lb1);
        add(lb2);

        title.setBounds(150, 30, 50 , 50);
        add(title);

        id.setBounds(80,100,70,50);
        add(id);

        pass.setBounds(50,150,70,50);
        add(pass);

        idText.setBounds(150, 115,120,25);
        add(idText);

        passText.setBounds(150, 160, 120 ,25);
        add(passText);

        log.setBounds(80, 280, 100, 30);
        add(log);
       log.addActionListener(new Login_in());


        gaib.setBounds(220, 280 , 100, 30);
        add(gaib);
        gaib.addActionListener(new Login());

        JPanel panel = new JPanel();
        panel.setBounds(0,0,400,400);
        panel.setBackground(Color.white);
        add(panel);

        setSize(400,400);
        setTitle("로그인 화면");
        setVisible(true);

    }



    class Login_in extends JFrame implements ActionListener{
        private static Connection conn;
        private static PreparedStatement pstmt;
        JLabel error = new JLabel("아이디 또는 비밀번호가 일치하지 않습니다.");
        @Override
        public void actionPerformed(ActionEvent e) {
            //DB에서 살폈을 적에 있으면 frame과 연결, 없으면 없는 아이디 또는 비밀번호입니다 창이 뜨게.
            //1. db에서 데이터 확인



            try {
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/new_schema", "root", "1234");
                pstmt = conn.prepareStatement(" select * from test where id=?;");
                pstmt.setString(1,idText.getText());

                ResultSet rs = pstmt.executeQuery();

                boolean check = true;
                while (rs.next()){

                    check=false;
                    String real_pass = rs.getString("pass");
                    if (real_pass.equals(passText.getText())){
                        if (idText.getText().equals("admin")){
                            new Admin();
                        }else {
                            new Frame();
                        }


                    }else {
                        setLayout(null);
                        error.setBounds(10,0,300,50);
                        add(error);
                        setSize(300, 100);
                        setVisible(true);

                    }
                }

                if (check){
                    setLayout(null);
                    error.setBounds(10,0,300,50);
                    add(error);
                    setSize(300, 100);
                    setVisible(true);
                }


            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            //2-1. frame과 연결
            //2-2. 없는 번호라는 창 만들기
        }

        class Frame extends JFrame{

            //뭘 만들어야 할까나.
            //독서실. 독서실 자리 차지하기
            //그럴려면 일단 자리가 있는 형태가 필요하겠지.
            private static Connection conn;
            private static PreparedStatement pstmt;

            Timer timer = new Timer();

            public JButton[] bt = new JButton[25];
            void button(){
                for (int i = 0 ; i<= 24 ; i++){
                    bt[i]= new JButton(i+"");
                    bt[i].setBackground(Color.cyan);
                }//0은 빈 좌석으로 자리 표시 시 null값 방지용  1~24까지는 실 좌석.
                //기본좌석과 선택된 좌석의 색깔로 구분을 위해 cyan 색을 삽입
            }

            Frame() {

                button();
                setLayout(null);

                bt[1].setBounds(50, 50, 50, 50);
                bt[2].setBounds(100, 50, 50, 50);
                bt[3].setBounds(150, 50, 50, 50);

                add(bt[1]);
                add(bt[2]);
                add(bt[3]);

                bt[1].addActionListener(new Select());// 좌석 버튼 누를 경우 select라는 좌석 선택 개체가 만들어짐.
                bt[2].addActionListener(new Select());
                bt[3].addActionListener(new Select());

/////////////////////////////////////////////////////////////////
                bt[4].setBounds(50, 100, 50, 50);
                bt[5].setBounds(100, 100, 50, 50);
                bt[6].setBounds(150, 100, 50, 50);

                add(bt[4]);
                add(bt[5]);
                add(bt[6]);

                bt[4].addActionListener(new Select());
                bt[5].addActionListener(new Select());
                bt[6].addActionListener(new Select());
/////////////////////////////////////////////////////////////////
                bt[7].setBounds(250, 50, 50, 50);
                bt[8].setBounds(300, 50, 50, 50);
                bt[9].setBounds(350, 50, 50, 50);

                add(bt[7]);
                add(bt[8]);
                add(bt[9]);

                bt[7].addActionListener(new Select());
                bt[8].addActionListener(new Select());
                bt[9].addActionListener(new Select());
/////////////////////////////////////////////////////////////////
                bt[10].setBounds(250, 100, 50, 50);
                bt[11].setBounds(300, 100, 50, 50);
                bt[12].setBounds(350, 100, 50, 50);

                add(bt[10]);
                add(bt[11]);
                add(bt[12]);

                bt[10].addActionListener(new Select());
                bt[11].addActionListener(new Select());
                bt[12].addActionListener(new Select());
////////////////////////////////////////////////////////////////
                bt[13].setBounds(50, 250, 50, 50);
                bt[14].setBounds(100, 250, 50, 50);
                bt[15].setBounds(150, 250, 50, 50);

                add(bt[13]);
                add(bt[14]);
                add(bt[15]);

                bt[13].addActionListener(new Select());
                bt[15].addActionListener(new Select());
                bt[15].addActionListener(new Select());
/////////////////////////////////////////////////////////////////////
                bt[16].setBounds(50, 300, 50, 50);
                bt[17].setBounds(100, 300, 50, 50);
                bt[18].setBounds(150, 300, 50, 50);

                add(bt[16]);
                add(bt[17]);
                add(bt[18]);

                bt[16].addActionListener(new Select());
                bt[17].addActionListener(new Select());
                bt[18].addActionListener(new Select());

/////////////////////////////////////////////////////////////////////
                bt[19].setBounds(250, 250, 50, 50);
                bt[20].setBounds(300, 250, 50, 50);
                bt[21].setBounds(350, 250, 50, 50);

                add(bt[19]);
                add(bt[20]);
                add(bt[21]);

                bt[19].addActionListener(new Select());
                bt[20].addActionListener(new Select());
                bt[21].addActionListener(new Select());

/////////////////////////////////////////////////////////////////
                bt[22].setBounds(250, 300, 50, 50);
                bt[23].setBounds(300, 300, 50, 50);
                bt[24].setBounds(350, 300, 50, 50);

                add(bt[22]);
                add(bt[23]);
                add(bt[24]);

                bt[22].addActionListener(new Select());
                bt[23].addActionListener(new Select());
                bt[24].addActionListener(new Select());
///////////////////////////////////////////////////////////

                timer.setBounds(180,10,100,20);
                add(timer);

                setSize(500, 500);
                setTitle("자리선정");
                setVisible(true);
                setResizable(false);


                //  System.out.println(getBt()[0].getText());

                try {
                    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/new_schema", "root", "1234");
                    pstmt = conn.prepareStatement("select * from test ;");

                    ResultSet rs = pstmt.executeQuery();

                 while (rs.next()){// 자리가 있는 자리는 붉은 색으로 변경해서 표시
                       int btnum = rs.getInt(7);//sql "num" 값
                      bt[btnum].setBackground(Color.red);
                 }

                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }

            class Select extends JFrame implements ActionListener{//좌석 버튼 누르면 이동.
                private  JFrame jFrame = new JFrame();
                private JLabel info = new JLabel("자리 등록");
                private  JLabel id = new JLabel("아이디");
                private  JTextField id_text = new JTextField();

                private  JLabel name = new JLabel("이름");
                private  JTextField name_text = new JTextField();

                private  JLabel tel = new JLabel("전화번호");
                private  JTextField tel_text = new JTextField();

                private  JLabel address = new JLabel("주소");
                private  JTextField address_text = new JTextField();

                private  JLabel num = new JLabel("좌석번호");
                private  JTextField num_text = new JTextField();

                private  JLabel pri= new JLabel();
                private JButton button_ok = new JButton("확인");

//////////////////////////////////////////////////SEATFULL
                private  JFrame frame = new JFrame();

                private JLabel label = new JLabel();
                private JButton del = new JButton("자리빼기");

                private JLabel seat = new JLabel("내 좌석: ");
                private JLabel seat_mine = new JLabel();//현재 사용중인 자신의 좌석


                @Override
                public void actionPerformed(ActionEvent e) {

                    String input =e.getActionCommand();
                    Integer input_int = Integer.valueOf(input);//문자열을 숫자열로 변경
                    System.out.println(input);

                    try {
                        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/new_schema", "root", "1234");
                        pstmt = conn.prepareStatement("select * from test ;");

                        ResultSet rs = pstmt.executeQuery();


                        while (rs.next()){//sql test라는 테이블을 확인해본다.

                            int btnum = rs.getInt(7); //테이블의 7번 값인 num을 변수"btnum"에 넣는다.
                            String bt_num = btnum+""; // 기본의 input 값이 문자열이므로 비교할 수 있게 문자열로 변경한다.
                            if (bt_num.equals(input)){//만약에 클릭한 좌석 번호와 sql의 번호가 같은 경우 ==
                                if (bt[input_int].getBackground().equals(Color.red)){
                                    frame.setLayout(null);
                                    label.setText(" 이미 사용중인 좌석입니다.");
                                    label.setBounds(50,5,200,30);
                                    frame.add(label);
                                    //////////////

                                    seat.setBounds(50, 35, 50, 30);
                                    frame.add(seat);//"내 좌석"

                                    try {

                                        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/new_schema", "root", "1234");
                                        pstmt = conn.prepareStatement("select * from test where id=?;");

                                        pstmt.setString(1, idText.getText());
                                        ResultSet rs2 = pstmt.executeQuery();

                                        while (rs2.next()) {
                                            String mine = rs2.getString("num");
                                            if (mine.equals("0")){
                                                mine = "자리를 골라주세요";
                                            }
                                            seat_mine.setText(mine);//"내 좌석 번호 표시"
                                        }

                                    } catch (SQLException e1) {
                                        e1.printStackTrace();
                                    }


                                    seat_mine.setBounds(100, 35, 100, 30);
                                    frame.add(seat_mine);

                                    ///////////////
                                    del.setBounds(80, 100, 100, 30); //자리 빼기 위치 선정
                                    frame.add(del);

                                    del.addActionListener(new Fullseat());


                                    frame.setSize(300, 200);
                                    frame.setTitle("사용중인 좌석");
                                    frame.setVisible(true);


                                }



                            }else{//만약에 클릭한 좌석 번호와 sql의 번호가 없는 경우 == 아무도 선택하지 않은 좌석일 경우

                                if (bt[input_int].getBackground().equals(Color.CYAN)){
                                    //클릭한 좌석의 번호의 backgrouond color가 cyan == 선택되지 않은 좌석이다.

                                    //자리 선정하기. 기존의 정보를 불러와서 번호만 새롭게 부여. 번호가 부여된 자리는 빨강.
                                    //1. 기존의 정보를 불러와서 번호와 함께 띄운다.
                                    //2. 확인을 누를 경우 DB에서 번호를 부여시킨다.

                                    //만약에 좌석이 빈 좌석이라면 좌석 예약창 뜨게 하기.

                                    jFrame.setLayout(null);

                                    info.setBounds(150, 10, 200, 30);
                                    jFrame.add(info);

                                    id.setBounds(20, 50, 50, 20);
                                    jFrame.add(id);

                                    id_text.setBounds(100, 50, 100, 20);
                                    jFrame.add(id_text);

                                    //////////////////////////////////////////////////////////
                                    name.setBounds(20, 100, 100, 20);
                                    jFrame.add(name);

                                    name_text.setBounds(100, 100, 100, 20);
                                    jFrame.add(name_text);

                                    ////////////////////////////////////////////////////////
                                    tel.setBounds(15, 150, 100, 20);
                                    jFrame.add(tel);

                                    tel_text.setBounds(100, 150, 100, 20);
                                    jFrame.add(tel_text);

                                    ////////////////////////////////////////////////////////
                                    address.setBounds(15, 200, 100, 20);
                                    jFrame.add(address);

                                    address_text.setBounds(100, 200, 100, 20);
                                    jFrame.add(address_text);
                                    ////////////////////////////////////////////////////////
                                    num.setBounds(15, 250, 100, 20);
                                    jFrame.add(num);

                                    num_text.setBounds(100, 250, 100, 20);

                                    for (int i = 1; i <= 24; i++) {
                                        if (e.getSource() == bt[i]) {
                                            num_text.setText(bt[i].getText());
                                        }
                                    }

                                    jFrame.add(num_text);

                                    ////////////////////////////////////////////////////////

                                    button_ok.setBounds(180, 300, 80, 30);
                                    jFrame.add(button_ok);
                                    jFrame.add(pri);

                                    jFrame.setTitle("자리 등록");
                                    jFrame.setSize(400, 400);
                                    jFrame.setVisible(true);


                                }


                            }
                        }





                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }

                        //1. 기존의 정보를 불러온다. 어떻게? 다른 클래스에서 받아서?
                        try {

                            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/new_schema", "root", "1234");
                            pstmt = conn.prepareStatement("select * from test where id=?;");

                            pstmt.setString(1, Login.idtext.getText());
                            pstmt.setString(1, idText.getText());
                            ResultSet rs = pstmt.executeQuery();

                            while (rs.next()) {
                                id_text.setText(rs.getString("id"));
                                name_text.setText(rs.getString("name"));
                                tel_text.setText(rs.getString("tel"));
                                address_text.setText(rs.getString("address"));
                            }

                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }

                        //2. 확인을 누를 경우 DB에서 번호를 부여시킨다.
                        button_ok.addActionListener(new Upgrade());



                }
                class Upgrade  extends JFrame implements ActionListener{
                    private JLabel ok = new JLabel("등록 완료 되었습니다.");
                    private JLabel no = new JLabel("Error! 다시 확인하십시오.");
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {

                            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/new_schema", "root", "1234");
                            pstmt = conn.prepareStatement("update test set num=? where id=?;");

                            pstmt.setString(1,num_text.getText());
                            pstmt.setString(2,id_text.getText());
                            pstmt.executeUpdate();
                            pstmt.close();

                            ok.setBounds(10, 10, 100, 30);
                            add(ok);

                            setSize(200,100);
                            setVisible(true);
                            setTitle("확인 알림");

                            //////////////
                            SimpleDateFormat sdf = new SimpleDateFormat();
                            Date now = new Date();
                            String nowdate = sdf.format(now);

                            pstmt = conn.prepareStatement("insert into admin values(?, ?, ?, ?, ?, ?);");

                            pstmt.setString(1,id_text.getText());
                            pstmt.setString(2,name_text.getText());
                            pstmt.setString(3,tel_text.getText());
                            pstmt.setString(4,address_text.getText());
                            pstmt.setString(5,num_text.getText());
                            pstmt.setString(6,nowdate);

                            pstmt.executeUpdate();
                            pstmt.close();

                        } catch (SQLException e1) {
                            no.setBounds(10, 10, 100, 30);
                            add(no);

                            setSize(200,100);
                            setVisible(true);
                            setTitle("확인 알림");
                            e1.printStackTrace();
                        }

                    }
                }

            }

        }

    }

}


//로그인 창을 만들자. 회원가입해서 기본 정보를 넣고.
class Login extends JFrame implements ActionListener {//로그인 시작 창 및 회원가입

    private static Connection conn;
    private static PreparedStatement pstmt;
    private JLabel id = new JLabel("아이디");
    private JLabel password = new JLabel("비밀번호");
    private JLabel password_ch = new JLabel("비밀번호 확인");

    private JLabel name = new JLabel("이름");
    private JLabel age = new JLabel("연령");
    private JLabel tel = new JLabel("전화번호");
    private JLabel address = new JLabel("주소");


    static JTextField idtext = new JTextField();
    private JTextArea id_check = new JTextArea();

    private JPasswordField passtext = new JPasswordField();
    private JPasswordField passtext_ch = new JPasswordField();

    private JTextArea pass_check = new JTextArea();

    private JTextField nametext = new JTextField();
    private JTextField agetext = new JTextField();
    private  JTextField teltext = new JTextField();
    private JTextField addresstext = new JTextField();

    private JButton input = new JButton("입력");

    private JButton checkin = new JButton("중복 확인");

    private JButton check_pass = new JButton("중복 확인");

    @Override
    public void actionPerformed(ActionEvent e) {//회원가입창
        setLayout(null);


        id.setBounds(30,10,100,30);
        idtext.setBounds(100, 10, 130, 30);
        checkin.setBounds(250, 10, 100, 30);
        id_check.setBounds(100, 50, 200, 20);
        add(id); //아이디 입력 라벨
        add(idtext);//아이디 입력
        add(checkin);//아이디 체크 버튼
        add(id_check); //아이디 확인 후 중복인지 내용 뜨는 부분

        id_check.setForeground(Color.red);//중복 내용 체크 부분을 빨강으로 표시
        checkin.addActionListener(new CheckId());//중복 체크를 누를 경우 checkId로 가서 아이디 중복 체크

        //////////////////////////////////////////////////

        password.setBounds(30,80,100,30);
        passtext.setBounds(100, 80, 130, 30);
        add(password);
        add(passtext);

        password_ch.setBounds(10,130,100,30);
        passtext_ch.setBounds(100,130,130,30);
        add(password_ch);
        add(passtext_ch);

        check_pass.setBounds(250, 130, 100, 30);
        add(check_pass);

        pass_check.setBounds(100, 170, 200, 20);
        pass_check.setForeground(Color.red);
        add(pass_check);

        check_pass.addActionListener(new CheckPass());

        /////////////////////////////////////////////////////

        name.setBounds(30, 220, 100, 30);//이름
        nametext.setBounds(100, 220, 130, 30);
        add(name);
        add(nametext);

        ////////////////////////////////////////////////////////////

        age.setBounds(30, 270, 100, 30);//나이
        agetext.setBounds(100, 270, 130, 30);
        add(agetext);
        add(age);

        /////////////////////////////////////////////////////////////////

        tel.setBounds(30, 320, 100, 30);//전화번호
        teltext.setBounds(100, 320, 130, 30);
        add(teltext);
        add(tel);

        //////////////////////////////////////////////////////////////

        address.setBounds(30, 370, 100, 30);//주소
        addresstext.setBounds(100, 370, 130, 30);
        add(addresstext);
        add(address);

        ////////////////////////////////////////////////////////

        input.setBounds(200, 420, 80, 30);
        add(input);

        input.addActionListener(new UPDate());

        ////////////////////////////////////////////////////////
        ImageIcon icon = new ImageIcon("book2.jpg");
        JLabel lb1 = new JLabel(icon, JLabel.CENTER);

        lb1.setBounds(285,190,500,500);

        add(lb1);


        //////////////////////////////////////////////////

        JPanel panel = new JPanel();
        panel.setBackground(Color.white);
        panel.setBounds(0,0,500,500);
        add(panel);


        /////////////////////////////////////////////////////////

        setSize(500, 500);
        setTitle("정보 입력");
        setVisible(true);
        setResizable(false);
        // 빨간색이 있는 곳엔 이미 자리가 있습니다가 뜨고, 없는 곳엔 정보 입력창이 뜨게 하려면....
//데이터 베이스를 돌려서 기존의 이름을 돌려서 자리가 일치 할 경우 이미 자리가 차 있습니다가 뜨도록 만들면...
    }
    class  CheckId implements ActionListener{// checkin.addActionListener(new CheckId());에서 누르면 넘어옴
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/new_schema", "root", "1234");
                pstmt = conn.prepareStatement(" select * from test where id=?;");
                pstmt.setString(1,idtext.getText());

                ResultSet rs = pstmt.executeQuery();
                boolean check= true;
                while (rs.next()){
                    check= false;
                    id_check.setText("이미 존재합니다.");
                }

                if (check){
                    id_check.setText("사용 가능한 아이디입니다.");
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    class  CheckPass implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if (passtext.getText().equals(passtext_ch.getText())){
                pass_check.setText("사용가능한 비밀번호 입니다.");
             }else {
                pass_check.setText("비밀번호가 다릅니다.");
            }
        }
    }


    class UPDate implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            //입력한 정보가 DB에 올라가게.

            try {
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/new_schema", "root", "1234");
                pstmt = conn.prepareStatement(" insert into test values (?,?,?,?, ?, ?, 0);");
                pstmt.setString(1,idtext.getText());
                pstmt.setString(2,passtext.getText());
                pstmt.setString(3,nametext.getText());
                pstmt.setString(4, agetext.getText());
                pstmt.setString(5,teltext.getText());
                pstmt.setString(6, addresstext.getText());


                pstmt.executeUpdate();



            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

}

