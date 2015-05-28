/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analyzehtml;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * でもいっぱいデータベース引くの時間もったいないよな
 *
 * @author makir0n
 *
 * 先に人リストつくってからソートするか10こあってるか
 * ソートしたやつが人かどうか判断していくかなかったら永遠と
 */
public class Alive {

    ArrayList<Page> page_rank = new ArrayList();
    //ArrayList<Integer> aliveId = new ArrayList();
    ArrayList<People> aliveMan = new ArrayList();

    //int lim;

    Alive(ArrayList<Page> list, int people_num) {
        //System.out.println(list.get(0).getValue());
        page_rank = list;
        //lim = people_num;
        setAliveman();//aliveManがセットされる
        //System.out.println(aliveMan);
        //ソートする
        SortPeople st = new SortPeople(aliveMan);
        aliveMan = st.getSortedPeople();
        //System.out.println(aliveMan);
    }

    //idからtitleだしてtitleのfromに存命人物があるか
    void setAliveman() {
        String jdbc_url = "jdbc:mysql://localhost/LINEtest?characterEncoding=UTF-8";
        String user = "root";
        String password = "@xes";

        try (Connection con = DriverManager.getConnection(jdbc_url, user, password);
                Statement stmt = con.createStatement()) {

            //存命人物のidを出してAliveIdへ これと一致するか調べる
            ResultSet rsPage, rsList;
            rsPage = stmt.executeQuery("SELECT * FROM page WHERE page_title LIKE '存命人物';");
            ArrayList<String> AliveId = new ArrayList<String>();
            for (int i = 0; rsPage.next() == true; i++) {//存命なんか３つもでてきた
                AliveId.add(new String(rsPage.getBytes("page_id"), "UTF-8"));
            }

            //存命人物からリンクがきてる人かを調べる
            //idのtitleからfromに存命人物のがあるか
            //idのfrom一覧
            //lst.add(new Page(i, values.get(i) * 1000));
            rsList = stmt.executeQuery("SELECT * FROM page INNER JOIN pagelinks ON page.page_title = pagelinks.pl_title where pagelinks.pl_from =" + AliveId.get(0) + ";");
            while (rsList.next()) {
                String name = new String(rsList.getBytes("page_title"), "UTF-8");
                int id = rsList.getInt("page_id");
                //このidと一致するスコアも入れちゃうか
               //一致するindexが得られるからそのindexのスコアを
                
                //存命人物のidと名前と評価値
                //ランキング順や・・・ 
                //System.err.println(id);//idが4,6,7,9,が人であることが判明→それらの評価も一緒に登録したい
                //System.out.println(page_rank);//String
                ArrayList<Integer> page_rank_id = new ArrayList<>();
                for( Page page: page_rank){
                    page_rank_id.add(page.getId());
                }
                //String strid = String.valueOf(id);
               // System.out.println(strid);
                int index = page_rank_id.indexOf(id);
                double score = page_rank.get(Integer.valueOf(index)).getValue();
                //System.out.println(score);
                aliveMan.add(new People(id, name,score));
                //System.out.print(id + ",");
                //String from = null;
                //from = new String(rsList.getBytes("page_id"), "UTF-8");
                //System.out.println(aliveMan);
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ArrayList<People> getAlives() {
        return aliveMan;
    }

}
