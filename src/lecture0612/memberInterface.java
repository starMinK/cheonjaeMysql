package lecture0612;

public interface memberInterface {
    String[][] memberInsert(String id, String name, float height, float weight, int age);

    String[][] memberUpdate(String id, String name, float height, float weight, int age);
    void memberDelete(String id);

    String[][] memberSelectAll();
}
