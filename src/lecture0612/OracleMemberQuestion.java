package lecture0612;

import lecture0609.ScoreTotalMethod;

import java.sql.*;
import java.util.Scanner;

public class OracleMemberQuestion {
//    memberInterface memberOverload = new MemberOverload();
    private static Connection conn;
    private static PreparedStatement prestate;
    private static ResultSet resultSet;

    private static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        ScoreTotalMethod scoreTotalMethod = new ScoreTotalMethod();

        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/member";
        String userId = "testuser";
        String userPassword = "1234";

        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, userId, userPassword);
        } catch (ClassNotFoundException e) {
            System.out.println("db can't load");
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("db can't connect");
        }
        System.out.println("choose menu");
        int choose = sc.nextInt();

        switch (choose) {
            case 1:
                memberInsert("asd", "asd", 0.0, 0.0, 1);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + choose);
        }
    }

    private class MemberOverload implements memberInterface {
        @Override
        public String[][] memberInsert(String id, String name, float height, float weight, int age) {

            return new String[0][];
        }

        @Override
        public String[][] memberUpdate(String id, String name, float height, float weight, int age) {
            return new String[0][];
        }

        @Override
        public void memberDelete(String id) {

        }

        @Override
        public String[][] memberSelectAll() {
            return new String[0][];
        }
    }
}
