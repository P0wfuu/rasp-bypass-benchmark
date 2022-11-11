package com.example.raspbypass.Util;

import java.net.URL;
import java.net.URLClassLoader;
import java.security.ProtectionDomain;

public class Util {
    public static class CoreClassLoader extends URLClassLoader {
        public CoreClassLoader(URL[] urls, ClassLoader parent) {
            super(urls, parent);
        }
        public CoreClassLoader(ClassLoader parent) {
            this(new URL[0], parent);
        }
        public Class defineClass0(String name, byte[] b, int off, int len, ProtectionDomain protectionDomain) {
            return super.defineClass(name, b, off, len, protectionDomain);
        }
    }
    public static CoreClassLoader LOADER = new CoreClassLoader(Util.class.getClassLoader());

    public static byte[] base64Decode(String str) {
        try {
            try {
                Class clazz = Class.forName("sun.misc.BASE64Decoder");
                return (byte[]) clazz.getMethod("decodeBuffer", String.class).invoke(clazz.newInstance(), str);
            } catch (ClassNotFoundException e) {
                Class clazz = Class.forName("java.util.Base64");
                Object decoder = clazz.getMethod("getDecoder").invoke(null);
                return (byte[]) decoder.getClass().getMethod("decode", String.class).invoke(decoder, str);
            }
        } catch (Exception e) {
            return null;
        }
    }
}
