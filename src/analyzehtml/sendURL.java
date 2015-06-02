/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analyzehtml;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 *
 * @author makir0n
 */
public class sendURL {

    sendURL(String URL) {
        try (Socket socket = new Socket("localhost", 8001); 
                FileOutputStream fos = new FileOutputStream("client_recv.txt")
                ) {

            int ch;
            OutputStream output = socket.getOutputStream();
                output.write(Byte.valueOf(URL));
            // 終了を示すため、ゼロを送信
            output.write(0);
            // サーバからの返信をclient_recv.txtに出力
            InputStream input = socket.getInputStream();
            while ((ch = input.read()) != -1) {
                fos.write(ch);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
