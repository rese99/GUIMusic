import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Mysql {
    //https://github.com/rese99/GUIMusic.git
    public static String username = "root";
    public static String password = "123456";

    public static void AddSongSheet(String name) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection ss = DriverManager.getConnection("jdbc:mysql://localhost:3306/music", username, password);
            Statement stmt = ss.createStatement();
            String sql = "create table " + name + "(name varchar(255) not null,id varchar (255) not null);";
            stmt.executeUpdate(sql);
            stmt.close();
            ss.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void RemoveSongSheet(String name) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection ss = DriverManager.getConnection("jdbc:mysql://localhost:3306/music", username, password);
            Statement stmt = ss.createStatement();
            String sql = "drop table " + name + ";";
            stmt.executeUpdate(sql);
            stmt.close();
            ss.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<String> ReadTable() {
        List<String> list = new ArrayList<>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection ss = DriverManager.getConnection("jdbc:mysql://localhost:3306/music", username, password);
            DatabaseMetaData metaData = ss.getMetaData();
            ResultSet rs = metaData.getTables(null, null, null, new String[]{"TABLE"});
            while (rs.next()) {
                list.add(rs.getString("TABLE_NAME"));
            }
            rs.close();
            ss.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<String> ReadTableInfo(String name) {
        List<String> list = new ArrayList<>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/music", username, password);
            Statement ment = con.createStatement();
            String sql = "select * from " + name + ";";
            ResultSet rs = ment.executeQuery(sql);
            while (rs.next()) {
                list.add(rs.getString("name"));
                list.add(rs.getString("id"));
            }
            rs.close();
            ment.close();
            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void InsertTableInfo(String name, String SongName, String id) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection ss = DriverManager.getConnection("jdbc:mysql://localhost:3306/music", username, password);
            Statement ment = ss.createStatement();
            String sql = "insert into " + name + " values(" + "'" + SongName + "'," + id + ");";
            ment.executeUpdate(sql);
            ment.close();
            ss.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void RemoveSong(String name, String id) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection ss = DriverManager.getConnection("jdbc:mysql://localhost:3306/music", username, password);
            Statement ment = ss.createStatement();
            String sql = "delete from " + name + " where id=" + id + ";";
            ment.executeUpdate(sql);
            ment.close();
            ss.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<String> list() {
        List<String> list = new ArrayList<>();
        String a = "推荐歌手";
        String a1 = "入驻歌手";
        String a2 = "华语男歌手";
        String a3 = "华语女歌手";
        String a4 = "华语组合";

        String a5 = "欧美男歌手";
        String a6 = "欧美女歌手";
        String a7 = "欧美组合";

        String a8 = "日本男歌手";
        String a9 = "日本女歌手";
        String a10 = "日本组合";

        String a11 = "韩国男歌手";
        String a12 = "韩国女歌手";
        String a13 = "韩国组合";

        String a14 = "其他男歌手";
        String a15 = "其他女歌手";
        String a16 = "其他组合";
        list.add(a);
        list.add(a1);
        list.add(a2);
        list.add(a3);
        list.add(a4);
        list.add(a5);
        list.add(a6);
        list.add(a7);
        list.add(a8);
        list.add(a9);
        list.add(a10);
        list.add(a11);
        list.add(a12);
        list.add(a13);
        list.add(a14);
        list.add(a15);
        list.add(a16);
        return list;
    }

    public static void create() {
        for (int i = 0; i < list().size(); i++) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection ss = DriverManager.getConnection("jdbc:mysql://localhost:3306/singers", username, password);
                Statement stmt = ss.createStatement();
                String RemoveSql = "DROP TABLE IF EXISTS " + list().get(i) + ";";
                stmt.executeUpdate(RemoveSql);
                String CreateSql = "create table " + list().get(i) + "(name varchar(255) not null,pic mediumblob,url varchar (255) not null);";
                stmt.executeUpdate(CreateSql);
                stmt.close();
                ss.close();
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public static byte[] getImgStr(URL url) throws IOException {
        InputStream in = url.openStream();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        int len = 0;
        byte[] b = new byte[1024];
        while ((len = in.read(b)) != -1) {
            out.write(b, 0, len);
        }

        //接收out
        byte[] array = out.toByteArray();
        in.close();
        out.close();

        return array;
    }

    public static void save(List<String> list, String name) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //获取连接
            String url = "jdbc:mysql://localhost:3306/singers?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
            try {
                Connection connection = DriverManager.getConnection(url, username, password);
                for (int i = 0; i < list.size(); i++) {
                    if (i >= 30) {
                        String sql = "insert into " + name + " values(" + "'" + list.get(i) + "'" + ",null," + "'" + list.get(i + 1) + "'" + ")";
                        PreparedStatement ps = connection.prepareStatement(sql);
                        ps.executeUpdate();
                        i = i + 1;
                    } else {
                        byte[] arr = getImgStr(new URL(list.get(i + 2)));
                        Blob blob = connection.createBlob();
                        blob.setBytes(1, arr);
                        String sql = "insert into " + name + " values(" + "'" + list.get(i) + "'" + ",?," + "'" + list.get(i + 1) + "'" + ")";
                        PreparedStatement ps = connection.prepareStatement(sql);
                        ps.setBlob(1, blob);
                        ps.executeUpdate();
                        i = i + 2;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void SingersInfo() throws Exception {
        List<String> stringList = new ArrayList<>();
        String a = "https://music.163.com/discover/artist/";
        String a1 = "https://music.163.com/discover/artist/signed/";
        String a2 = "https://music.163.com/discover/artist/cat?id=1001";
        String a3 = "https://music.163.com/discover/artist/cat?id=1002";
        String a4 = "https://music.163.com/discover/artist/cat?id=1003";
        String a5 = "https://music.163.com/discover/artist/cat?id=2001";
        String a6 = "https://music.163.com/discover/artist/cat?id=2002";
        String a7 = "https://music.163.com/discover/artist/cat?id=2003";
        String a8 = "https://music.163.com/discover/artist/cat?id=6001";
        String a9 = "https://music.163.com/discover/artist/cat?id=6002";
        String a10 = "https://music.163.com/discover/artist/cat?id=6003";
        String a11 = "https://music.163.com/discover/artist/cat?id=7001";
        String a12 = "https://music.163.com/discover/artist/cat?id=7002";
        String a13 = "https://music.163.com/discover/artist/cat?id=7003";
        String a14 = "https://music.163.com/discover/artist/cat?id=4001";
        String a15 = "https://music.163.com/discover/artist/cat?id=4002";
        String a16 = "https://music.163.com/discover/artist/cat?id=4003";
        stringList.add(a);
        stringList.add(a1);
        stringList.add(a2);
        stringList.add(a3);
        stringList.add(a4);
        stringList.add(a5);
        stringList.add(a6);
        stringList.add(a7);
        stringList.add(a8);
        stringList.add(a9);
        stringList.add(a10);
        stringList.add(a11);
        stringList.add(a12);
        stringList.add(a13);
        stringList.add(a14);
        stringList.add(a15);
        stringList.add(a16);

        for (int j = 0; j < stringList.size(); j++) {
            String url = stringList.get(j);
            List<String> list = new ArrayList<>();
            Document document = Jsoup.connect(url).get();
            Elements elements2 = document.select("ul[class=m-cvrlst m-cvrlst-5 f-cb]");
            Elements elements = elements2.select("img");
            Elements elements1 = elements2.select("a[class=nm nm-icn f-thide s-fc0]");
            int i = 0;
            for (Element element : elements1) {
                String title = element.text().replace("'", "''");
                String href = "https://music.163.com" + element.attr("href").trim();
                list.add(title);
                list.add(href);
                if (i < elements.size()) {
                    list.add(String.valueOf(elements.get(i)).replace("<img src=\"", "").replace("\">", ""));
                    i++;
                }
            }
            System.out.println(list);
            save(list, list().get(j));
        }

    }

    public static ResultSet SelectSingers(String name) {
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //获取连接
            String url = "jdbc:mysql://localhost:3306/singers?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
            try {
                Connection connection = DriverManager.getConnection(url, username, password);
                String sql = "select * from " + name + ";";
                PreparedStatement ps = connection.prepareStatement(sql);
                rs = ps.executeQuery();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return rs;
    }
}
