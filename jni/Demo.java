public class Demo {
    public static void main(String[] args) {
        System.loadLibrary("cmd");
        JniExploit command = new JniExploit();
        String ipconfig = command.exec("ipconfig");
        System.out.println(ipconfig);
    }
}