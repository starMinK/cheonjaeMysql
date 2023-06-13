package lecture0612;

import java.sql.*;
import java.util.Scanner;

public class OracleMemberQuestion {
    //    memberInterface memberOverload = new MemberOverload();
    protected static final String DB_URL = "jdbc:mysql://localhost:3306/mindb";
    protected static final String DB_USER = "root";
    protected static final String DB_PASSWORD = "1234";

    public static void main(String[] args) {
        MemberDAO memberDAO = new MemberDAOImpl();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. 회원 추가");
            System.out.println("2. 회원 수정");
            System.out.println("3. 회원 조회");
            System.out.println("4. 회원 삭제");
            System.out.println("5. 프로그램 종료");
            System.out.print("메뉴 선택: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    memberDAO.addMember();
                    break;
                case 2:
                    memberDAO.updateMember();
                    break;
                case 3:
                    memberDAO.getMember();
                    break;
                case 4:
                    memberDAO.deleteMember();
                    break;
                case 5:
                    System.out.println("프로그램을 종료합니다.");
                    System.exit(0);
                default:
                    System.out.println("유효한 메뉴를 선택하세요.");
            }
        }
    }

    interface MemberDAO {
        void addMember();

        void updateMember();

        void getMember();

        void deleteMember();
    }

    static class MemberDAOImpl implements MemberDAO {
        private Connection conn;

        public MemberDAOImpl() {
            try {
                conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void addMember() {
            Scanner scanner = new Scanner(System.in);
            System.out.println("*** 회원 추가 ***");

            System.out.print("이름: ");
            String name = scanner.nextLine();

            System.out.print("나이: ");
            int age = Integer.parseInt(scanner.nextLine());

            System.out.print("신장: ");
            float height = scanner.nextFloat();

            System.out.println("아이디: ");
            String id = scanner.next();

            try {
                PreparedStatement stmt = conn.prepareStatement("INSERT INTO member (name, age, height, id) VALUES (?, ?, ?, ?)");
                stmt.setString(1, name);
                stmt.setInt(2, age);
                stmt.setFloat(3, height);
                stmt.setString(4, id);
                stmt.executeUpdate();
                System.out.println("회원이 추가되었습니다.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void updateMember() {
            Scanner scanner = new Scanner(System.in);
            System.out.println("===회원 수정 ===");

            System.out.print("수정할 회원의 ID: ");
            String id = scanner.next();

            System.out.print("새로운 이름: ");
            String name = scanner.next();

            System.out.print("새로운 나이: ");
            int age = scanner.nextInt();

            System.out.print("새로운 신장: ");
            String height = scanner.next();

            try {
                PreparedStatement stmt = conn.prepareStatement("UPDATE member SET name = ?, age = ?, height = ? WHERE id = ?");
                stmt.setString(1, name);
                stmt.setInt(2, age);
                stmt.setString(3, height);
                stmt.setString(4, id);
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("회원이 수정되었습니다.");
                } else {
                    System.out.println("해당하는 ID의 회원이 존재하지 않습니다.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void getMember() {
            Scanner scanner = new Scanner(System.in);
            System.out.println("*** 회원 조회 ***");

            System.out.print("조회할 회원의 ID: ");
            String id = scanner.next();

            try {
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM member WHERE id = ?");
                stmt.setString(1, id);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    System.out.println("ID: " + rs.getInt("id"));
                    System.out.println("이름: " + rs.getString("name"));
                    System.out.println("나이: " + rs.getInt("age"));
                    System.out.println("신장: " + rs.getFloat("height"));
                } else {
                    System.out.println("해당하는 ID의 회원이 존재하지 않습니다.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void deleteMember() {
            Scanner scanner = new Scanner(System.in);
            System.out.println("*** 회원 삭제 ***");

            System.out.print("삭제할 회원의 ID: ");
            String id = scanner.next();

            try {
                PreparedStatement stmt = conn.prepareStatement("DELETE FROM member WHERE id = ?");
                stmt.setString(1, id);
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("회원이 삭제되었습니다.");
                } else {
                    System.out.println("해당하는 ID의 회원이 존재하지 않습니다.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
