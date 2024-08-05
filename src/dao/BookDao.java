package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.ConnectionUtil;
import dto.BookDTO;

/*
 * 데이터베이스 입출력
 */
public class BookDao {
    /**
     * 도서의 목록을 조회 합니다.
     * 
     * @return 도서목록
     */
    public List<BookDTO> getBookList() {
        List<BookDTO> list = null;
        String sql = "select * from tb_book";
        // 쿼리를 질의하고 결과를 반환받는 객체
        try (
                Connection con = ConnectionUtil.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery();) {
            list = new ArrayList<BookDTO>();
            // 결과집합으로부터 다음 행이 있는지 확인하고 있으면 true, 없으면 false를 반환
            // 다음행이 있다면 다음행을 읽어 옴
            while (rs.next()) {
                String no = rs.getString("b_no");
                String title = rs.getString("title");
                String author = rs.getString("author");
                int price = rs.getInt("price");
                // 리스트에 담기 위해 책(bookDTO객체)를 생성
                // DTO : 데이터를 담는 그릇과 같은 역할
                // DB테이블이 가진 컬럼과 동일 필드명을 가진 경우가 많음
                BookDTO bookDTO = new BookDTO(title, author, price);
                list.add(bookDTO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 도서입력
     * 
     * @return
     */
    public int insertBook(BookDTO book) {
        int res = 0;

        // ? 를 이용해서 동적쿼리를 생성
        String sql = "insert into tb_book (b_no, title, author, price) "
                + "values ('B' || lpad(seq_tb_book.nextval, 5, 0), ?, ?, ?)";
        // try ()안에 생성된 객체는 try문이 끝나면서 .close()메서드를 호출 = 자원반납
        try (
                // 데이터베이스에 접근
                Connection con = ConnectionUtil.getConnection();
                // 동적인 쿼리 (질의문) 실행
                PreparedStatement pstmt = con.prepareStatement(sql);) {

            // 동적 파라메터 세팅
            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.setInt(3, book.getPrice());

            // executeUpdate : DML문장의 실행결과를 숫자로 반환
            // 몇건이 처리되었는지 숫자로 반환

            res = pstmt.executeUpdate();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return res;
    }

    public int updateBook(BookDTO book) {
        int res = 0;
        String sql = "update      tb_book " +
                "set         title = ?, author = ?, price = ? " +
                "where       b_no = ? ";

        try (
                Connection con = ConnectionUtil.getConnection();
                PreparedStatement stmt = con.prepareStatement(sql);) {
            // 동적 파라메터 세팅
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setInt(3, book.getPrice());
            stmt.setString(4, book.getB_no());

            // 쿼리를 실행하고 몇건이 처리 되었는지 정후 반환
            res = stmt.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return res;
    }

    public static void main(String[] args) {
        BookDao dao = new BookDao();
        BookDTO book = new BookDTO("업데이트책", "업데이트작가", 200000);
        book.setB_no("B00121");
        // 업데이트 테스트
        dao.updateBook(book);

        // 입력 테스트 데이터 생성
        /*
         * int res = dao.insertBook(book);
         * if (res > 0) {
         * System.out.println("성공");
         * } else {
         * System.out.println("실패");
         * }
         */
        /*
         * // 리스트 테스트
         * List<BookDTO> list = dao.getBookList();
         * for (BookDTO book : list) {
         * System.out.println(book);
         * }
         */
    }
}
