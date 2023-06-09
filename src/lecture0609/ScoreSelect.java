package lecture0609;

import java.sql.*;

public class ScoreSelect {

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

        //2. DB 조회(Select)
        String sql = "select * from score";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.executeQuery();
            rs = pstmt.executeQuery();
            System.out.println("select 성공");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("select 실패");
        }

        //3. 결과 출력
        try {
            while (rs.next()) {
                String id = rs.getString("id");
                String subject = rs.getString("subject");
                String studentClass = rs.getString("student_class");
                int middleExam = rs.getInt("middle_exam");
                int finalExam = rs.getInt("final_exam");

                System.out.println(id + "\t" + subject + "\t" + studentClass + "\t" + middleExam + "\t" + finalExam);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
