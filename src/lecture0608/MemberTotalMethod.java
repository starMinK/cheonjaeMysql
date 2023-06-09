package lecture0608;

import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

/*문제: 회원관리 시스템을 업그레이드 하여 각각의 method를 이용한 객체지향 프로그래밍을 작성
* 1. 회원 정보 조회
* 2. 회원 정보 입력
* 3. 회원 정보 수정
* 4. 회원 정보 삭제
* 5. 회원 정보 종료
* 위 5가지의 시스템을 가진 프로그램을 작성하라.*/
public class MemberTotalMethod {
    private static Connection connection;
    private static PreparedStatement preparedStatement;
    private static ResultSet resultSet;

    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {

        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/minDB";
        String userId = "root";
        String userPassword = "1234";

        String sql = "";
        String id = "";
        String name = "";
        float height = 0.0f;
        float weight = 0.0f;
        int age = 0;

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

        while (true) {
            int work = 0;

            System.out.println("수행하실 작업을 선택해주세요.");
            System.out.println("1: 회원 정보 조회하기\t2: 회원 정보 입력하기");
            System.out.println("3: 회원 정보 수정하기\t4: 회원 정보 삭제하기");
            System.out.println("5: 시스템 종료");
            System.out.println();

            try {
                work = sc.nextInt();
            } catch (InputMismatchException e) {
                e.printStackTrace();
                System.out.println("1~5 사이의 정수만 입력 가능합니다.");
                return;
            }

            switch (work) {
                case 1: //회원 정보 조회
                    sql = "select * from member";
                    try {
                        preparedStatement = connection.prepareStatement(sql);
                        preparedStatement.executeQuery();
                        resultSet = preparedStatement.executeQuery();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    try {
                        while (resultSet.next()) {
                            id = resultSet.getString(1);
                            name = resultSet.getString(2);
                            height = resultSet.getFloat(3);
                            weight = resultSet.getFloat(4);
                            age = resultSet.getInt(5);

                            System.out.println(id + "\t" + name + "\t" + height + "\t" + weight + "\t" + age);
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
                    System.out.println();
                    break;
                case 2: //회원 정보 입력

                    try {
                        System.out.println("아이디를 입력해주세요.");
                        id = sc.next();

                        System.out.println("이름을 입력해주세요.");
                        name = sc.next();

                        System.out.println("신장을 입력해주세요.");
                        height = sc.nextFloat();

                        System.out.println("몸무게를 입력해주세요.");
                        weight = sc.nextFloat();

                        System.out.println("나이를 입력해주세요.");
                        age = sc.nextInt();
                    } catch (InputMismatchException e) {
                        e.printStackTrace();
                        System.out.println("아이디, 이름: String");
                        System.out.println("신장, 몸무게: float");
                        System.out.println("나이: int");
                    }


                    sql = "insert into member value (?, ?, ?, ?, ?)";

                    try {
                        preparedStatement = connection.prepareStatement(sql);

                        preparedStatement.setString(1, id);
                        preparedStatement.setString(2, name);
                        preparedStatement.setFloat(3, height);
                        preparedStatement.setFloat(4, weight);
                        preparedStatement.setInt(5, age);

                        preparedStatement.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                        System.out.println("데이터 삽입 실패");
                    }
                    System.out.println();
                    break;
                case 3: //회원 정보 수정
                    try {
                        System.out.println("수정할 컬럼의 아이디를 입력해주세요.");
                        id = sc.next();

                        System.out.println(id + "의 수정할 이름을 입력해주세요.");
                        name = sc.next();

                        System.out.println(id + "의 수정할 신장을 입력해주세요.");
                        height = sc.nextFloat();

                        System.out.println(id + "의 수정할 몸무게를 입력해주세요.");
                        weight = sc.nextFloat();

                        System.out.println(id + "의 수정할 나이를 입력해주세요.");
                        age = sc.nextInt();
                    } catch (InputMismatchException e) {
                        e.printStackTrace();
                        System.out.println("아이디, 이름: String");
                        System.out.println("신장, 몸무게: float");
                        System.out.println("나이: int");
                    }

                    sql = "update member set name = ?, height = ?, weight = ?, age = ? where id = ?";

                    try {
                        preparedStatement = connection.prepareStatement(sql);

                        preparedStatement.setString(1, name);
                        preparedStatement.setFloat(2, height);
                        preparedStatement.setFloat(3, weight);
                        preparedStatement.setInt(4, age);
                        preparedStatement.setString(5, id);

                        preparedStatement.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                        System.out.println("데이터 수정 실패");
                    }
                    System.out.println();
                    break;
                case 4: //회원 정보 삭제
                    System.out.println("삭제할 컬럼의 아이디를 입력해주세요.");
                    try {
                        id = sc.next();
                    } catch (InputMismatchException e) {
                        e.printStackTrace();
                        System.out.println("아이디는 String형입니다.");
                    }

                    sql = "delete from member where id = ?";

                    try {
                        preparedStatement = connection.prepareStatement(sql);

                        preparedStatement.setString(1, id);
                        preparedStatement.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                        System.out.println("데이터 삭제 실패");
                    }
                    System.out.println();
                    break;
                case 5: //시스템 종료
                    System.out.println("시스템을 종료합니다.");
                    return;
                default:
                    System.out.println("1~5 사이의 숫자를 입력해주세요.");
                    System.out.println();
            }
        }
    }
}
