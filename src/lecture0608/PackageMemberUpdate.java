package lecture0608;

import java.sql.*;
import java.util.Scanner;

public class PackageMemberUpdate {
    private static Connection conn; //DB연결
    private static PreparedStatement pstmt; //SQL 실행
    private static ResultSet rs; //sql 실행, 결과값 출력 담당

    public static void main(String[] args) {
        //1. DB 연결
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/minDB";
        String userId = "root";
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

        System.out.println("이름을 입력해주세요.");
        String name = sc.next();

        System.out.println("키를 입력해주세요.");
        float height = sc.nextFloat();

        System.out.println("몸무게를 입력해주세요.");
        float weigth = sc.nextFloat();

        System.out.println("나이를 입력해주세요.");
        int age = sc.nextInt();

        String sql = "update member set name = ?, height = ?, weight = ?, age = ? where id = ?";

        try {
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, name);
            pstmt.setFloat(2, height);
            pstmt.setFloat(3, weigth);
            pstmt.setInt(4, age);
            pstmt.setString(5, id);

            pstmt.executeUpdate();

            System.out.println("데이터 수정 성공");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("데이터 수정 실패");
        }

        //3. 결과 출력
       /* try {
            while (rs.next()) {
                String id = rs.getString("id");     // rs.getString(1); <-인덱스 순으로도 출력가능 '1' 부터 시작
                String name = rs.getString("name");   //rs.getString(2);
                float height = rs.getFloat("height");
                float weight = rs.getFloat("weight");
                int age = rs.getInt("age");

                System.out.println(id + "\t" + name + "\t" + height + "\t" + weight + "\t" + age);
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
        }*/
    }
}
