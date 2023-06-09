package lecture0609;

import java.sql.*;
import java.util.*;

/*문제: 회원관리 시스템을 업그레이드 하여 각각의 method를 이용한 객체지향 프로그래밍을 작성
 * 1. 회원 정보 조회
 * 2. 회원 정보 입력
 * 3. 회원 정보 수정
 * 4. 회원 정보 삭제
 * 5. 회원 정보 종료
 * 위 5가지의 시스템을 가진 프로그램을 작성하라.*/
public class ScoreTotalMethod {
    private static Connection connection;
    private static PreparedStatement preparedStatement;
    private static ResultSet resultSet;

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/scoredb";
        String userId = "testuser";
        String userPassword = "1234";

        String id = "";
        String subject = "";
        String studentClass = "";
        int middleExam = 0;
        int finalExam = 0;
        String[][] scoreArray = new String[20][5];

        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, userId, userPassword);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("DB 로딩 실패");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("DB 연결 실패");
        }


        String sql = "select * from score";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeQuery();
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            int i = 0;
            while (resultSet.next()) {
                scoreArray[i][0] = resultSet.getString(1);    //학번
                scoreArray[i][1] = resultSet.getString(2);//과목명
                scoreArray[i][2] = resultSet.getString(3);//학반
                scoreArray[i][3] = String.valueOf(resultSet.getInt(4));//중간고사 성적
                scoreArray[i][4] = String.valueOf(resultSet.getInt(5));//기말고사 성적
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("DB Get 실패");
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        int middleTotal = 0;
        int finalTotal = 0;
        int average = 0;
        String[] top3 = new String[3];
        for (int i = 0; i < scoreArray.length; i++) {
            middleTotal += Integer.parseInt(scoreArray[i][3]);
            finalTotal += Integer.parseInt(scoreArray[i][4]);
            for (int j = 0; j < scoreArray[i].length; j++) {
                /*
                * 총점, 평균 석차 구하기*/
            }
        }
    }
}
