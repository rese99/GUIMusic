import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MusicTableInfo {
    public static Object[][] data;
    public static String[] TableHeard;

    public static DefaultTableModel MusicTableInfo() {
        List<String> TableList = Mysql.ReadTable();
        List<String> list = Mysql.ReadTableInfo(TableList.get(0));
        data = new Object[list.size() / 2][2];
        TableHeard = new String[]{TableList.get(0), ""};
        int i = 0;
        while (i < list.size() / 2) {
            data[i][0] = list.get(i * 2);
            data[i][1] = list.get(i * 2 + 1);
            i++;
        }
        DefaultTableModel MusicModel = new DefaultTableModel(data, TableHeard);
        return MusicModel;
    }

    public static DefaultTableModel MySongSheet() {
        String name[] = {""};
        List<String> list = Mysql.ReadTable();
        String data[][] = new String[list.size()][1];
        for (int i = 0; i < list.size(); i++) {
            data[i][0] = list.get(i);
        }
        DefaultTableModel tableModel = new DefaultTableModel(data, name);
        return tableModel;
    }

    public static DefaultTableModel FindTableModel() {
        String[] header = {"", "", "", ""};
        Object[][] info = new Object[2][4];
        try {
            JLabel[] labels = new JLabel[Recommend().size() / 3];

            int j = 0;
            for (int i = 0; i < Recommend().size(); i++) {
                labels[j] = new JLabel();
                labels[j].setText(Recommend().get(i));
                labels[j].setIcon(new ImageIcon(new URL(Recommend().get(i + 2))));
                labels[j].setHorizontalTextPosition(JLabel.CENTER);
                labels[j].setVerticalTextPosition(JLabel.BOTTOM);
                i = i + 2;
                j++;
            }

            int m = 0;
            int k = 0;

            for (int i = 0; i < Recommend().size() / 3; i++) {
                info[m][k] = labels[i];
                k++;
                if (k == 4) {
                    k = 0;
                    m++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        DefaultTableModel tableModel = new DefaultTableModel(info, header);
        return tableModel;
    }

    public static List<String> Recommend() throws Exception {
        List<String> list = new ArrayList<>();
        String url = "https://music.163.com/discover";
        Document document = Jsoup.connect(url).get();
        Elements elements = document.select("div[class=g-bd1 f-cb]").select("div[class=g-mn1]").select("div[class=g-mn1c]").select("div[class=g-wrap3]").select("div[class=n-rcmd]").select("ul[class=m-cvrlst f-cb]");
        Elements elements1 = elements.select("p[class=dec]");
        Elements elements2 = elements1.select("a");
        int i = 0;
        Elements element1 = elements.select("img");
        for (Element element : elements2) {
            String title = element.attr("title");
            String href = "https://music.163.com" + element.attr("href").trim();
            list.add(title);
            list.add(href);
            list.add(String.valueOf(element1.get(i)).replace("<img src=\"", "").replace("\">", ""));
            i++;
        }
        return list;
    }

    public static void SingerRoot() {
        Singers("推荐歌手");
        String[] name = {"", ""};
        String[][] info = new String[17][2];
        info[0][0] = "推荐歌手";
        info[0][1] = "https://music.163.com/discover/artist/";
        info[1][0] = "入驻歌手";
        info[1][1] = "https://music.163.com/discover/artist/signed/";
        info[2][0] = "华语男歌手";
        info[2][1] = "https://music.163.com/discover/artist/cat?id=1001";
        info[3][0] = "华语女歌手";
        info[3][1] = "https://music.163.com/discover/artist/cat?id=1002";
        info[4][0] = "华语组合";
        info[4][1] = "https://music.163.com/discover/artist/cat?id=1003";
        info[5][0] = "欧美男歌手";
        info[5][1] = "https://music.163.com/discover/artist/cat?id=2001";
        info[6][0] = "欧美女歌手";
        info[6][1] = "https://music.163.com/discover/artist/cat?id=2002";
        info[7][0] = "欧美组合";
        info[7][1] = "https://music.163.com/discover/artist/cat?id=2003";
        info[8][0] = "日本男歌手";
        info[8][1] = "https://music.163.com/discover/artist/cat?id=6001";
        info[9][0] = "日本女歌手";
        info[9][1] = "https://music.163.com/discover/artist/cat?id=6002";
        info[10][0] = "日本组合";
        info[10][1] = "https://music.163.com/discover/artist/cat?id=6003";
        info[11][0] = "韩国男歌手";
        info[11][1] = "https://music.163.com/discover/artist/cat?id=7001";
        info[12][0] = "韩国女歌手";
        info[12][1] = "https://music.163.com/discover/artist/cat?id=7002";
        info[13][0] = "韩国组合";
        info[13][1] = "https://music.163.com/discover/artist/cat?id=7003";
        info[14][0] = "其他男歌手";
        info[14][1] = "https://music.163.com/discover/artist/cat?id=4001";
        info[15][0] = "其他女歌手";
        info[15][1] = "https://music.163.com/discover/artist/cat?id=4002";
        info[16][0] = "其他组合";
        info[16][1] = "https://music.163.com/discover/artist/cat?id=4003";
        Frame.SingerRoot.setModel(new DefaultTableModel(info, name));
        Frame.SingerRoot.getColumnModel().getColumn(0).setPreferredWidth(85);
        Frame.FindTablePanel.add(Frame.SingerRootPanel);
        Frame.FindTablePanel.add(Frame.SingersPanel);
        Frame.SingerRoot.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Singers((String) Frame.SingerRoot.getValueAt(Frame.SingerRoot.getSelectedRow(), 0));
            }
        });
    }

    public static List<String> url = new ArrayList<>();
    public static List<String> list = new ArrayList<>();
    public static List<Icon> list1 = new ArrayList<>();

    public static void Singers(String name) {
        Frame.SingersPanel.removeAll();
        Frame.SingersPanel.updateUI();
        DefaultTableModel tableModel = new DefaultTableModel();
        ResultSet rs = Mysql.SelectSingers(name);

        url.clear();
        list.clear();
        list1.clear();
        try {
            while (rs.next()) {
                String names = rs.getString("name");
                list.add(names);
                String urls = rs.getString("url");
                url.add(urls);
                InputStream is = rs.getBinaryStream("pic");
                BufferedImage bufImg = null;
                if (is != null) {
                    try {
                        bufImg = ImageIO.read(is);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Image image = bufImg;
                    ImageIcon icon = new ImageIcon(image);
                    list1.add(icon);
                }
            }

            JLabel[] labels = new JLabel[list.size()];
            int j = 0;
            for (int i = 0; i < list.size(); i++) {
                if (i >= 10) {
                    labels[j] = new JLabel();
                    labels[j].setText(list.get(i));
                    labels[j].setHorizontalTextPosition(JLabel.CENTER);
                    labels[j].setVerticalTextPosition(JLabel.BOTTOM);
                } else {
                    labels[j] = new JLabel();
                    labels[j].setText(list.get(i));
                    labels[j].setIcon(list1.get(i));
                    labels[j].setHorizontalTextPosition(JLabel.CENTER);
                    labels[j].setVerticalTextPosition(JLabel.BOTTOM);
                }
                j++;
            }
            String[] header = {"", "", "", "", ""};
            Object[][] info;
            if (j % 5 != 0) {
                info = new Object[j / 5 + 1][5];
            } else {
                info = new Object[j / 5][5];
            }

            int m = 0;
            int k = 0;

            for (int i = 0; i < j; i++) {
                info[m][k] = labels[i];
                k++;
                if (k == 5) {
                    k = 0;
                    m++;
                }
            }
            tableModel.setDataVector(info, header);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Frame.Singers.setModel(tableModel);
        Frame.Singers.setRowHeight(160);
        for (int i = 0; i < Frame.Singers.getColumnCount(); i++) {
            Frame.Singers.getColumnModel().getColumn(i).setPreferredWidth(140);
        }
        Frame.SingersPanel.add(Frame.SingsScrollPane);
    }

    public static void SingersInfo(String url, String singer) {
        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 815, 30);
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 200, 0));
        panel.setOpaque(false);
        JButton song = new JButton("单曲");
        JButton album = new JButton("专辑");
        panel.add(song);
        panel.add(album);
        List<String> list = new ArrayList<>();
        Document document = null;
        try {
            document = Jsoup.connect(url).timeout(3000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements elements = document.select("#artist-top50").select("ul[class=f-hide]").select("a");
        for (Element element : elements) {
            String name = element.text();
            String href = element.attr("href").trim().replace("/song?id=", "");
            list.add(name);
            list.add(href);
        }
        Frame.SingersPanel.removeAll();
        Frame.SingersPanel.updateUI();

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(SingerSong(list, singer));
        scrollPane.setBorder(Frame.js);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(815, 540));
        scrollPane.setOpaque(false);

        Frame.SingersPanel.add(panel);
        Frame.SingersPanel.add(scrollPane);
        song.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scrollPane.setViewportView(SingerSong(list, singer));
            }
        });
        album.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    scrollPane.setViewportView(SingerAlbum(url));
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
    }

    public static JTable SingerSong(List<String> list, String singer) {
        Frame.routineColor.setOpaque(false);
        String[] header = {"歌曲", "歌手"};
        String[][] info = new String[list.size() / 2][2];
        int k = 0;
        for (int i = 0; i < list.size(); i++) {
            info[k][0] = list.get(i);
            info[k][1] = singer;
            k++;
            i = i + 1;
        }
        JTable table = new JTable(new DefaultTableModel(info, header)) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setRowHeight(table.getRowHeight() + 15);
        table.getColumnModel().getColumn(0).setPreferredWidth(407);
        table.getColumnModel().getColumn(1).setPreferredWidth(407);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setOpaque(false);
        table.setShowGrid(false);
        table.setDefaultRenderer(table.getColumnClass(-1), Frame.routineColor);
        table.addMouseMotionListener(new MyMouseListener(table, Frame.routineColor));
        table.addMouseListener(new MyMouseListener(table, Frame.routineColor));
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Lrc.readMp3(Lrc.MusicUrl(list.get(table.getSelectedRow() * 2 + 1)), Lrc.LrcUrl(list.get(table.getSelectedRow() * 2 + 1)));
                } catch (Exception exception) {
                    exception.printStackTrace();
                }

            }
        });
        return table;
    }

    public static JTable SingerAlbum(String url) throws Exception {
        Frame.routineColor.setOpaque(false);
        List<String> album = new ArrayList<>();
        List<String> albumId = new ArrayList<>();
        List<String> img = new ArrayList<>();
        url = url.replace("?id", "/album?id") + "&limit=150&offset=0";
        Document document = Jsoup.connect(url).get();
        Elements names1 = document.select("#m-song-module li");
        for (Element element : names1.select("a.s-fc0")) {
            album.add(element.text());
            albumId.add(element.attr("href").trim());
        }
        for (Element element : names1) {
            String urls = String.valueOf(element.select("img")).replace("<img src=\"", "").replace("\">", "");
            img.add(urls);
        }
        String[] hared = {"", "", "", "", ""};
        Object[][] info;
        if (album.size() % 5 != 0) {
            info = new Object[album.size() / 5 + 1][5];
        } else {
            info = new Object[album.size() / 5][5];
        }
        JLabel[] label = new JLabel[album.size()];
        for (int i = 0; i < album.size(); i++) {
            label[i] = new JLabel();
            label[i].setHorizontalTextPosition(JLabel.CENTER);
            label[i].setVerticalTextPosition(JLabel.BOTTOM);
            label[i].setIcon(new ImageIcon(new URL(img.get(i))));
            label[i].setText(album.get(i));
        }
        int k = 0;
        int j = 0;
        for (int i = 0; i < album.size(); i++) {
            info[k][j] = label[i];
            j++;
            if (j == 5) {
                j = 0;
                k++;
            }
        }
        JTable table = new JTable(new DefaultTableModel(info, hared)) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setRowHeight(160);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(140);
        }
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setOpaque(false);
        table.setShowGrid(false);
        table.setDefaultRenderer(table.getColumnClass(-1), Frame.routineColor);
        table.addMouseMotionListener(new MyMouseListener(table, Frame.routineColor));
        table.addMouseListener(new MyMouseListener(table, Frame.routineColor));
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }
        });
        return table;
    }
    public static void SongSheet() throws Exception {
        Frame.routineColor.setOpaque(false);
        List<String> urls = new ArrayList<>();
        List<String> names = new ArrayList<>();
        List<Icon> pics = new ArrayList<>();
        ResultSet rs = Mysql.ReadSongSheet();
        while (rs.next()) {
            String name = rs.getString("name");
            String url = rs.getString("url");
            names.add(name);
            urls.add(url);
            InputStream is = rs.getBinaryStream("pic");
            BufferedImage bufImg = null;
            if (is != null) {
                try {
                    bufImg = ImageIO.read(is);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Image image = bufImg;
                ImageIcon icon = new ImageIcon(image);
                pics.add(icon);
            }
        }
        JLabel[] labels = new JLabel[names.size()];
        for (int i = 0; i < names.size(); i++) {
            labels[i] = new JLabel();
            labels[i].setText(names.get(i));
            labels[i].setIcon(pics.get(i));
            labels[i].setHorizontalTextPosition(JLabel.CENTER);
            labels[i].setVerticalTextPosition(JLabel.BOTTOM);
        }
        String[] header = {"", "", "", "", ""};
        Object[][] info;
        if (names.size() % 5 != 0) {
            info = new Object[names.size() / 5 + 1][5];
        } else {
            info = new Object[names.size() / 5][5];
        }
        int k = 0;
        int j = 0;
        for (int i = 0; i < names.size(); i++) {
            info[k][j] = labels[i];
            j++;
            if (j == 5) {
                j = 0;
                k++;
            }
        }
        JTable table = new JTable(new DefaultTableModel(info, header)) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setRowHeight(160);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(160);
        }
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setOpaque(false);
        table.setShowGrid(false);
        table.setDefaultRenderer(table.getColumnClass(-1), Frame.routineColor);
        table.addMouseMotionListener(new MyMouseListener(table, Frame.routineColor));
        table.addMouseListener(new MyMouseListener(table, Frame.routineColor));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(Frame.js);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(900, 570));
        Frame.FindTablePanel.add(scrollPane);
    }
}
