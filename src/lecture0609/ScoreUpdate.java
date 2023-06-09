package lecture0609;

import java.sql.*;
import java.util.Scanner;

public class ScoreUpdate {

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

        System.out.println("아이디를 입력해주세요.");
        String id = sc.next();

        System.out.println("과목을 입력해주세요.");
        String subject = sc.next();

        System.out.println("학반을 입력해주세요.");
        String studentClass = sc.next();

        System.out.println("중간고사 성적을 입력해주세요.");
        int middleExam = sc.nextInt();

        System.out.println("기말고사 성적을 입력해주세요.");
        int finalExam = sc.nextInt();

        String sql = "update score set subject = ?, student_class = ?, middle_exam = ?, final_exam = ? where id = ?";

        try {
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, subject);
            pstmt.setString(2, studentClass);
            pstmt.setInt(3, middleExam);
            pstmt.setInt(4, finalExam);
            pstmt.setString(5, id);

            pstmt.executeUpdate();

            System.out.println("데이터 수정 성공");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("데이터 수정 실패");
        }
    }
}
