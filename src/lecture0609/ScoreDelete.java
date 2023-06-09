package lecture0609;

import java.sql.*;
import java.util.Scanner;

public class ScoreDelete {
    private static Connection conn; //DB연결
    private static PreparedStatement pstmt; //SQL 실행
    private static ResultSet rs; //sql 실행, 결과값 출력 담당

    public static void main(String[] args) {
        //1. DB 연결
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/scoredb";
        String userId = "testuser";
        String userPassword = "1234";

        try {
            Class.forName(driver);
            System.out.println("드라이버 로딩에 성공했습니다.");

            try {
                conn = DriverManager.getConnection(url, userId, userPassword);
                System.out.println("연결에 성공했습니다.");
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("연결에 실패했습니다.");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("드라이버 로딩에 실패했습니다.");
        }

        //2. DB 데이터 입력 및 저장 (Insert)
        Scanner sc = new Scanner(System.in);

        System.out.println("학번을 입력해주세요.");
        String id = sc.next();

        String sql = "delete from score where id = ?";

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.executeUpdate();

            System.out.println("데이터 삭제 성공");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("데이터 삭제 실패");
        }
    }
}
