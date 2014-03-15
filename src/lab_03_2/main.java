package lab_03_2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class main {
    public static void main(String[] args) throws IOException {


        //FileManager.getInstance().put("C:\\Users\\Rabo\\IdeaProjects\\FileStorage\\pom.xml");
        //FileManager.getInstance().put("D:\\navi.png");
        new TCPReceiver().start();
        new UDPReceiver().start();
        new UDPSender().start();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = br.readLine();
        while (!s.equals("exit")) {
            try {
                String[] input = s.split(" ");
                if (input[0].equals("put"))
                    FileManager.getInstance().put(input[1]);
                if (input[0].equals("delete"))
                    FileManager.getInstance().delete(input[1]);
            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
            } finally {
                s = br.readLine();
            }

        }
    }
}
