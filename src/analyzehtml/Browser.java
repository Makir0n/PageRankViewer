/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analyzehtml;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

/**
 *
 * @author makir0n
 *
 * サーバにURL投げる JSONが返ってくるから 算出されたランクスコアに合せて HTMLを編集し表示する
 *
 */
public class Browser {

    public static void main(String[] args) {


    //private String URL = "http://ja.wikipedia.org/wiki/%E8%8D%89%E6%B4%A5%E5%B8%82";
    String URL = "http://ja.wikipedia.org/wiki/%E5%9B%9E%E9%8D%8B%E8%82%89";    //最初にセットしておくURL
    //private String URL = "http://imagingrium.com/test/%E5%AD%98%E5%91%BD%E4%BA%BA%E7%89%A9.html";    //最初にセットしておくURL
    JFrame frame = new JFrame();                    //フレーム自体
    JEditorPane webPagePane = new JEditorPane();    //実際にウェブページの表示に使われる
    JTextField address = new JTextField(URL);    //URLが入力されるフィールド
        
//アドレスが変更されたさいのactionListnerを登録する。
        address.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                sendURL sendurl = new sendURL(URL);
                setUrl(webPagePane, address.getText());
            }

        });
        //address.setEditable(true);

        AnalyzeHTML ahtml = new AnalyzeHTML(URL);
        ArrayList<String> linkTitle = ahtml.getLinkPageId();

        //ウェブページ中のリンクがクリックされたときのHyperLinkListnerを登録する
        webPagePane.addHyperlinkListener(new HyperlinkListener() {
            @Override
            public void hyperlinkUpdate(HyperlinkEvent he) {
                if (he.getEventType() != HyperlinkEvent.EventType.ACTIVATED) {
                    return;
                }
                String url = he.getURL().toString();
                setUrl(webPagePane, url);
                address.setText(url);
            }

        });
        //AnalyzeHTML aHTML = new AnalyzeHTML(URL);
        webPagePane.setEditable(false);        //ウェブページの表示使われるJEditorPaneを変更不能に設定する
        setUrl(webPagePane, URL);
        //コンポーネントを登録する
        frame.getContentPane().add(address, BorderLayout.NORTH);
        frame.getContentPane().add(new JScrollPane(webPagePane));
        //フレームの表示関係
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    static void setUrl(JEditorPane webPagePane, String url) {
        try {
            webPagePane.setPage(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void ReWriteHTML() {

    }
    
}
