package Make;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;

public class excel implements ActionListener {

    private static Connection conn;

    private static PreparedStatement pstmt;

    static Connection getconn() throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/new_schema","root","1234");;
        //sql과 자바 연결해주는 class
        return conn;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //워크북 객체생성
        XSSFWorkbook workbook = new XSSFWorkbook();
        //workbook -> sheet 객체 생성
        Sheet sheet = workbook.createSheet("자리 선택");
        //sheet -> Row 객체를 생성 index 0
        Row headRow = sheet.createRow(0);
        //Row -> Cell 생성 하면서 값도 넣어줍니다.
        headRow.createCell(0).setCellValue("아이디");
        headRow.createCell(1).setCellValue("이름");
        headRow.createCell(2).setCellValue("전화번호");
        headRow.createCell(3).setCellValue("주소");
        headRow.createCell(4).setCellValue("좌석번호");
        headRow.createCell(5).setCellValue("등록 날짜");

        try {
            pstmt = getconn().prepareStatement("SELECT * FROM admin order by date desc;");
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        ResultSet rs  = null;
        try {
            rs = pstmt.executeQuery();
            int rowNum = 1;

            while (rs.next()){

                //sheet -> Row 객체를 생성 index 1
                Row r = sheet.createRow(rowNum);
                //Row -> Cell 생성 하면서 값도 넣어줍니다.
                r.createCell(0).setCellValue(rs.getString("id"));
                r.createCell(1).setCellValue(rs.getString("name"));
                r.createCell(2).setCellValue(rs.getString("tel"));
                r.createCell(3).setCellValue(rs.getString("address"));
                r.createCell(4).setCellValue(rs.getString("num"));
                r.createCell(5).setCellValue(rs.getString("date"));

                rowNum++;
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }


        //FileOutputStream 객체를 생성 -> File 객체를 생성자 매개변수로 대입
        //File 객체 생성시 생성자 매개변수 파일 명
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(new File("member.xlsx"));
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        }
        //workbook에 쓰여진 데이터를 File에 쓰기
        try {
            workbook.write(outputStream);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        //workbook 종료
        try {
            workbook.close();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
