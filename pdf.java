package Make;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class pdf implements ActionListener {

    String id;
    String name;
    String tel;
    String address;
    String num;
    String date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private static Connection conn;

    private static PreparedStatement pstmt;

    static Connection getconn() throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/new_schema","root","1234");;
        //sql과 자바 연결해주는 class
        return conn;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String dest = "book_table.pdf"; // 문자열 변수 -> 파일명
        try {
            new pdf().createPdf(dest);
        } catch (IOException | SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void createPdf(String dest) throws IOException, SQLException{
        List<Map<String, String>> books = createDummyData();
// PDF 생성
        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf, PageSize.A4); // A4 사이즈
// Font Set
        PdfFont headerFont = null;
        PdfFont bodyFont = null;

        headerFont = PdfFontFactory.createFont("나눔손글씨 행복한 도비.ttf",
                "Identity-H");
        bodyFont = PdfFontFactory.createFont("나눔손글씨 행복한 도비.ttf",
                "Identity-H");

// 실수 배열 생성 -> 테이블 생성
        float[] columnWidths = {1, 1, 1, 1, 1, 1}; // 컬럼 넓이
        Table table = new Table(UnitValue.createPercentArray(columnWidths)); //테이블 넓이 위에 배열 참조
        table.setWidth(UnitValue.createPercentValue(100));
// 테이블 헤더 -> 셀
        Cell hCell1 = new Cell().add(new Paragraph("아이디")).setFont(headerFont);
        Cell hCell2 = new Cell().add(new Paragraph("이름")).setFont(headerFont);
        Cell hCell3 = new Cell().add(new Paragraph("전화번호")).setFont(headerFont);
        Cell hCell4 = new Cell().add(new Paragraph("주소")).setFont(headerFont);
        Cell hCell5 = new Cell().add(new Paragraph("좌석번호")).setFont(headerFont);
        Cell hCell6 = new Cell().add(new Paragraph("등록날짜")).setFont(headerFont);


        table.addHeaderCell(hCell1);
        table.addHeaderCell(hCell2);
        table.addHeaderCell(hCell3);
        table.addHeaderCell(hCell4);
        table.addHeaderCell(hCell5);
        table.addHeaderCell(hCell6);

// 테이블 본문에 값 넣기
        int rowNum = 1;
        for (Map<String, String> book : books) {

            String id = book.get("id");
            String name = book.get("name");
            String tel = book.get("tel");
            String address = book.get("address");
            String num = book.get("num");
            String date = book.get("date");

            // cell 1번

            //cell 2번
            Cell idCell = new Cell().add(new Paragraph(id)).setFont(bodyFont);
            // table 추가
            table.addCell(idCell);

            Cell nameCell = new Cell().add(new Paragraph(name)).setFont(bodyFont);
            table.addCell(nameCell);

            Cell telCell = new Cell().add(new Paragraph(tel)).setFont(bodyFont);
            table.addCell(telCell);

            Cell addressCell = new Cell().add(new Paragraph(address)).setFont(bodyFont);
            table.addCell(addressCell);

            Cell numCell = new Cell().add(new Paragraph(num)).setFont(bodyFont);
            table.addCell(numCell);

            Cell dateCell = new Cell().add(new Paragraph(date)).setFont(bodyFont);
            table.addCell(dateCell);

            rowNum++;
        }
        document.add(table); // 문서에 추가 table(모든 정보)
        document.close(); // close() 문서가 진짜 써짐
    }

    List<Map<String, String>> createDummyData() throws SQLException {


        List<Map<String, String>> books = new ArrayList<>(); //ArrayList 객체 생성 -> MAP -><String,String>
        pstmt = getconn().prepareStatement("SELECT * FROM admin order by date desc;");
        // pstmt 값 = sql과 연결된 커넥션의 set된 ( insert into _ 테이블에 추가) 값


        ResultSet rs =pstmt.executeQuery();
        //값을 받아옴.

        while (rs.next()) {
            setId(rs.getString("id"));
            setName(rs.getString("name"));
            setTel(rs.getString("tel"));
            setAddress(rs.getString("address"));
            setNum(rs.getString("num"));
            setDate(rs.getString("date"));

            Map<String, String> book = new HashMap<>(); // map 객체를 생성
            book.put("id", getId()); // map에 추가
            book.put("name", getName()); // map에 추가
            book.put("tel", getTel()); // map에 추가
            book.put("address", getAddress()); // map에 추가
            book.put("num", getNum()); // map에 추가
            book.put("date", getDate()); // map에 추가

            books.add(book); // List에 추가
        }

        return books;
    }

}
