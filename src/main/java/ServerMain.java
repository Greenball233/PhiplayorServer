import javax.swing.*;
import java.awt.*;
import java.io.*;

public class ServerMain {
    public static PrintStream console;
    public static void main(String[] args) {
        JFrame f = new JFrame();
        JPanel p =new JPanel(new GridLayout(1,1));
        JTextArea out = new JTextArea();
        OutputStream consoleStream = new OutputStream() {
            public void write(int b) throws IOException {
                out.append(String.valueOf((char) b));
            }

            public void write(byte b[]) throws IOException {
                out.append(new String(b));
            }

            public void write(byte b[], int off, int len) throws IOException {
                out.append(new String(b, off, len));
            }
        };
        console = new PrintStream(consoleStream);
        p.add(out);
        f.add(p);
        f.setSize(600,600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        new Server();
    }
}
