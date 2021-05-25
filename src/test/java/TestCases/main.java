package TestCases;

public class main {
    public static void main(String[] args) {

        String javaPath = System.getProperty("java.home");
        System.out.println("javaPath:"+javaPath);
      /*  Map<String, String> environment = new HashMap<String, String>();
        String javaPath = System.getProperty("java.home");
        environment.put("PATH",
                javaPath + "/bin:" +
                "/home/" + System.getenv("USER") + "/Android/sdk/tools/:" +
                 "/home/"+ System.getenv("USER") + "/Android/sdk/platform-tools/:" +
                 "/usr/local/bin:"+
                  "/usr/local/sbin:"+
                 "/usr/bin:/bin:/usr/sbin:/sbin:/bin:/usr/bin"  +
                 System.getenv("PATH")
                 );
        environment.put("ANDROID_HOME", "/home/" + System.getenv("USER") + "/Android/sdk/");
        environment.put("JAVA_HOME", javaPath);*/
    }
}
