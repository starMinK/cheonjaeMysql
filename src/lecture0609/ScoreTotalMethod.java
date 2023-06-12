package lecture0609;

import java.sql.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class ScoreTotalMethod {
    private static Connection connection;
    private static PreparedStatement preparedStatement;
    private static ResultSet resultSet;

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        ScoreTotalMethod scoreTotalMethod = new ScoreTotalMethod();

        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/scoredb";
        String userId = "testuser";
        String userPassword = "1234";

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

        scoreTotalMethod.displayTotalScores();
        scoreTotalMethod.displayAverage();
        scoreTotalMethod.displayRanking();
    }

    private void displayTotalScores() {
        String[][] scoreArray = getScoreArrayFromDB();

        int middleTotal = 0;
        int finalTotal = 0;

        for (int i = 0; i < scoreArray.length; i++) {
            middleTotal += Integer.parseInt(scoreArray[i][3]);
            finalTotal += Integer.parseInt(scoreArray[i][4]);
        }

        System.out.println("중간고사 성적 총점: " + middleTotal);
        System.out.println("기말고사 성적 총점: " + finalTotal);
    }

    private void displayAverage() {
        String[][] scoreArray = getScoreArrayFromDB();

        int middleTotal = 0;
        int finalTotal = 0;

        for (int i = 0; i < scoreArray.length; i++) {
            middleTotal += Integer.parseInt(scoreArray[i][3]);
            finalTotal += Integer.parseInt(scoreArray[i][4]);
        }

        int average = (middleTotal + finalTotal) / 40;
        System.out.println("전체 평균: " + average);
    }

    private void displayRanking() {
        String[][] scoreArray = getScoreArrayFromDB();

        Arrays.sort(scoreArray, Comparator.comparingInt(arr -> Integer.parseInt(arr[3])));
        System.out.println("중간고사 성적표");
        int rate = 1;
        for (String[] arr : scoreArray) {
            System.out.println(rate + "등: " + Arrays.toString(arr));
            rate++;
        }
    }

    private String[][] getScoreArrayFromDB() {
        String[][] scoreArray = new String[20][5];
        String sql = "select * from score";

        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            int i = 0;
            while (resultSet.next()) {
                scoreArray[i][0] = resultSet.getString(1);    //학번
                scoreArray[i][1] = resultSet.getString(2);    //과목명
                scoreArray[i][2] = resultSet.getString(3);    //학반
                scoreArray[i][3] = String.valueOf(resultSet.getInt(4));    //중간고사 성적
                scoreArray[i][4] = String.valueOf(resultSet.getInt(5));    //기말고사 성적
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
        }

        return scoreArray;
    }
}
