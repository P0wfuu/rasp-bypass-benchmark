package sun.tools.attach;

import java.io.IOException;

public class WindowsVirtualMachine {
    static native void enqueue(long hProcess, byte[] stub, String cmd, String pipename, Object... args) throws IOException;

}
