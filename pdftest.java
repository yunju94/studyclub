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
import com.itextpdf.commons.exceptions.ITextException;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.*;
import java.util.*;

import org.slf4j.Logger;



public class pdftest {
    private  String serialNo;
    private  String name;
    private  String m_id;
    private  String bitrh;
    private  String tel;
    private  String age;
    private  String address;
    private  String grade;
    private  String manager;
    private  String filename;

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(int serialNo) {
        this.serialNo = serialNo+"";
    }

    public  String getName() {
        return name;
    }

    public  void setName(String name) {
        this.name = name;
    }

    public String getM_id() {
        return m_id;
    }

    public  void setM_id(String m_id) {
        this.m_id = m_id;
    }

    public String getBitrh() {
        return bitrh;
    }

    public  void setBitrh(String bitrh) {
        this.bitrh = bitrh;
    }

    public String getTel() {
        return tel;
    }

    public  void setTel(String tel) {
        this.tel = tel;
    }

    public String getAge() {
        return age;
    }

    public  void setAge(String age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGrade() {
        return grade;
    }

    public  void setGrade(String grade) {
        this.grade = grade;
    }

    public String getManager() {
        return manager;
    }

    public  void setManager(String manager) {
        this.manager = manager;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    private static Connection conn;

    private static PreparedStatement pstmt;

    static Connection getconn() throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/new_schema","root","1234");;
        //sql과 자바 연결해주는 class
        return conn;
    }


    void pdfadd() throws IOException, SQLException {
        String dest = "book_table.pdf"; // 문자열 변수 -> 파일명
        new pdftest().createPdf(dest);

    }
    public void createPdf(String dest) throws IOException, IOException, SQLException, MalformedURLException {
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
        float[] columnWidths = {1, 1, 1, 1, 1, 1, 1, 1,1}; // 컬럼 넓이
        Table table = new Table(UnitValue.createPercentArray(columnWidths)); //테이블 넓이 위에 배열 참조
        table.setWidth(UnitValue.createPercentValue(100));
// 테이블 헤더 -> 셀
        Cell hCell1 = new Cell().add(new Paragraph("이름")).setFont(headerFont);
        Cell hCell2 = new Cell().add(new Paragraph("아이디")).setFont(headerFont);
        Cell hCell3 = new Cell().add(new Paragraph("생년월일")).setFont(headerFont);
        Cell hCell4 = new Cell().add(new Paragraph("전화번호")).setFont(headerFont);
        Cell hCell5 = new Cell().add(new Paragraph("만 나이")).setFont(headerFont);
        Cell hCell6 = new Cell().add(new Paragraph("주소")).setFont(headerFont);
        Cell hCell7 = new Cell().add(new Paragraph("등급")).setFont(headerFont);
        Cell hCell8 = new Cell().add(new Paragraph("담당자")).setFont(headerFont);
        Cell hCell9 = new Cell().add(new Paragraph("사진")).setFont(headerFont);

        table.addHeaderCell(hCell1);
        table.addHeaderCell(hCell2);
        table.addHeaderCell(hCell3);
        table.addHeaderCell(hCell4);
        table.addHeaderCell(hCell5);
        table.addHeaderCell(hCell6);
        table.addHeaderCell(hCell7);
        table.addHeaderCell(hCell8);
        table.addHeaderCell(hCell9);
// 테이블 본문에 값 넣기
        int rowNum = 1;
        for (Map<String, String> book : books) {

            String name = book.get("name");
            String m_id = book.get("m_id");
            String birth = book.get("birth");
            String tel = book.get("tel");
            String age = book.get("age");
            String address = book.get("address");
            String grade= book.get("grade");
            String manager= book.get("manager");
            String thumbnail= book.get("filename");
            // cell 1번

            //cell 2번
            Cell nameCell = new Cell().add(new Paragraph(name)).setFont(bodyFont);
            // table 추가
            table.addCell(nameCell);

            Cell m_idCell = new Cell().add(new Paragraph(m_id)).setFont(bodyFont);
            table.addCell(m_idCell);

            Cell birthCell = new Cell().add(new Paragraph(birth)).setFont(bodyFont);
            table.addCell(birthCell);

            Cell telCell = new Cell().add(new Paragraph(tel)).setFont(bodyFont);
            table.addCell(telCell);

            Cell ageCell = new Cell().add(new Paragraph(age)).setFont(bodyFont);
            table.addCell(ageCell);

            Cell addressCell = new Cell().add(new Paragraph(address)).setFont(bodyFont);
            table.addCell(addressCell);

            Cell gradeCell = new Cell().add(new Paragraph(grade)).setFont(bodyFont);
            table.addCell(gradeCell);

            Cell managerCell = new Cell().add(new Paragraph(manager)).setFont(bodyFont);
            table.addCell(managerCell);

            ImageData imageData = ImageDataFactory.create(new File(thumbnail).toURI().toURL());
            Image img = new Image(imageData);
            Cell imageCell = new Cell().add(img.setAutoScale(true));
            table.addCell(imageCell);

            rowNum++;
        }
        document.add(table); // 문서에 추가 table(모든 정보)
        document.close(); // close() 문서가 진짜 써짐
    }

     List<Map<String, String>> createDummyData() throws SQLException {

       
        List<Map<String, String>> books = new ArrayList<>(); //ArrayList 객체 생성 -> MAP -><String,String>
        pstmt = getconn().prepareStatement("SELECT * FROM  basicmembers where serialNo =?;");
        // pstmt 값 = sql과 연결된 커넥션의 set된 ( insert into _ 테이블에 추가) 값
        pstmt.setString(1, getSerialNo()); // 물음표(?)가 여기의 1~3 과 매칭된다.
        // set된 pstmt 값인 1번 물음표에 getname을 넣어서 문자열로 값 설정

        ResultSet rs =pstmt.executeQuery();
        //값을 받아옴.

        while (rs.next()) {
            setName(rs.getString("name"));
            setM_id(rs.getString("m_id"));
            setBitrh(rs.getString("birth"));
            setTel(rs.getString("tel"));
            setAge(rs.getString("age"));
            setAddress(rs.getString("address"));
            setGrade(rs.getString("grade"));
            setManager(rs.getString("manager"));
            setFilename(rs.getString("filename"));

            Map<String, String> book = new HashMap<>(); // map 객체를 생성
            book.put("name", getName()); // map에 추가
            book.put("m_id", getM_id()); // map에 추가
            book.put("birth", getBitrh()); // map에 추가
            book.put("tel", getTel()); // map에 추가
            book.put("age", getAge()); // map에 추가
            book.put("address", getAddress()); // map에 추가
            book.put("grade", getGrade()); // map에 추가
            book.put("manager", getManager()); // map에 추가
            book.put("filename", getFilename()); // map에 추가

            books.add(book); // List에 추가
        }

        return books;
    }


    public static void main(String[] args) throws SQLException, IOException {
        pdftest pdftest = new pdftest();
        pdftest.pdfadd();

        ArrayList<String>a = new ArrayList<>();


    }
}
