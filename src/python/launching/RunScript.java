package python.launching;

import java.io.IOException;

public class RunScript {
    public static void start() throws IOException, InterruptedException

    {
        Process process = new ProcessBuilder()
                .command("echo", "/src/ftp_connect/com/script/ftp/ftpConnect.py")
                .start();

        System.out.println(process.waitFor());
    }
}
