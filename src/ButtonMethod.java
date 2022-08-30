import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class ButtonMethod {
    public static int offset = 1;
    public static String id;
    public static boolean mark = false;

    public static void AddMusic() {
        JPopupMenu menu;
        JScrollPane menuscrollpane;
        JButton addMusic = new JButton("新建歌单");
        addMusic.setSize(addMusic.getWidth(), 30);
        JButton RemoveMusic = new JButton("删除歌单");
        RemoveMusic.setSize(RemoveMusic.getWidth(), 30);
        JButton ok = new JButton("确定");
        JButton cancel = new JButton("取消");
        menu = new JPopupMenu();
        menu.setLayout(new FlowLayout(FlowLayout.CENTER, 12, 0));
        menu.setPopupSize(new Dimension(200, 250));
        JTable MySongSheet = new JTable(MusicTableInfo.MySongSheet());
        RoutineColor routineColor = new RoutineColor();
        MySongSheet.setDefaultRenderer(MySongSheet.getColumnClass(-1), routineColor);
        MySongSheet.addMouseMotionListener(new MyMouseListener(MySongSheet, routineColor));
        MySongSheet.addMouseListener(new MyMouseListener(MySongSheet, routineColor));
        MySongSheet.setRowHeight(MySongSheet.getRowHeight() + 5);
        menuscrollpane = new JScrollPane(MySongSheet);
        menuscrollpane.setPreferredSize(new Dimension(200, 220));
        menu.add(menuscrollpane);
        menu.add(addMusic);
        menu.add(RemoveMusic);
        menu.show(Frame.AddMusic, Frame.AddMusic.getWidth() - 200, Frame.AddMusic.getHeight());
        JDialog addMusicDialog = new JDialog();
        addMusicDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        JTextField textField = new JTextField(16);
        addMusic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addMusicDialog.setSize(200, 150);
                JLabel label = new JLabel("给歌单取一个名字吧");
                JPanel panel = new JPanel();
                panel.setSize(200, 150);
                panel.add(label);
                panel.add(textField);
                panel.add(ok);
                panel.add(cancel);
                addMusicDialog.getContentPane().add(panel);
                addMusicDialog.setLocationRelativeTo(Frame.MusicPanel);
                addMusicDialog.setModal(true);
                addMusicDialog.setVisible(true);
            }
        });
        RemoveMusic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int x = MySongSheet.getSelectedRow();
                int y = MySongSheet.getSelectedColumn();
                System.out.println(x);
                if (x < 0) {
                    JOptionPane.showMessageDialog(menu, "没有选择歌单");
                } else {
                    Mysql.RemoveSongSheet((String) MySongSheet.getValueAt(x, y));
                    MySongSheet.setModel(MusicTableInfo.MySongSheet());
                }
            }
        });
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<String> list = Mysql.ReadTable();
                boolean mark = true;
                for (String s : list) {
                    if (textField.getText().trim().equals(s)) {
                        mark = false;
                    }
                }
                if (textField.getText().trim().equals("") || !mark) {
                    JOptionPane.showMessageDialog(addMusicDialog, "请重新取一个名字呢");
                } else {
                    AddMusicSheet(textField.getText().trim());
                    addMusicDialog.dispose();
                }
            }
        });
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addMusicDialog.dispose();
            }
        });
        MySongSheet.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = MySongSheet.getSelectedRow();
                int y = MySongSheet.getSelectedColumn();
                MusicTable((String) MySongSheet.getValueAt(x, y));
            }
        });
    }
    public static void AddMusicSheet(String s) {
        Mysql.AddSongSheet(s);
    }

    public static void ControlButton() {
        JToggleButton security, storage, LrcSet, ChangeBackground, ChangeBackgrounds;
        JButton securitys, storages, LrcSets;
        JPanel panel, panel1, panel2, panel3;
        JPanel MusicPanel = Frame.MusicPanel;
        JPopupMenu menu = new JPopupMenu();
        menu.setPopupSize(new Dimension(MusicPanel.getWidth(), MusicPanel.getHeight()));
        security = new JToggleButton("安全设置v");
        security.setSelected(false);
        securitys = new JButton("设置密码");
        storage = new JToggleButton("边听边存v");
        storage.setSelected(false);
        storages = new JButton("储存位置");
        LrcSet = new JToggleButton("歌词显示v");
        LrcSet.setSelected(false);
        LrcSets = new JButton("歌词显示");
        ChangeBackground = new JToggleButton("更改背景v");
        ChangeBackground.setSelected(false);
        ChangeBackgrounds = new JToggleButton("随机背景关");
        ChangeBackgrounds.setSelected(false);
        panel = new JPanel();
        panel.add(security);
        panel1 = new JPanel();
        panel1.add(storage);
        panel2 = new JPanel();
        panel2.add(LrcSet);
        panel3 = new JPanel();
        panel3.add(ChangeBackground);
        menu.add(panel);
        menu.add(panel1);
        menu.add(panel2);
        menu.add(panel3);
        menu.show(Frame.ControlPanel, 0, Frame.ControlPanel.getHeight());
        security.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (security.isSelected()) {
                    panel.add(securitys);
                    panel.updateUI();
                } else {
                    panel.remove(securitys);
                    panel.updateUI();
                }
            }
        });
        storage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (storage.isSelected()) {
                    panel1.add(storages);
                    panel1.updateUI();
                } else {
                    panel1.remove(storages);
                    panel1.updateUI();
                }
            }
        });
        LrcSet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (LrcSet.isSelected()) {
                    panel2.add(LrcSets);
                    panel2.updateUI();
                } else {
                    panel2.remove(LrcSets);
                    panel2.updateUI();
                }
            }
        });
        ChangeBackground.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ChangeBackground.isSelected()) {
                    ChangeBackground.setText("更改背景");
                    panel3.add(ChangeBackgrounds);
                    if (mark) {
                        ChangeBackgrounds.setSelected(true);
                        ChangeBackgrounds.setText("随机背景开");
                    }
                    panel3.updateUI();
                } else {
                    ChangeBackground.setText("更改背景v");
                    panel3.remove(ChangeBackgrounds);
                    panel3.updateUI();
                }
            }
        });
        ChangeBackgrounds.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ChangeBackgrounds.isSelected()) {
                    ChangeBackgrounds.setText("随机背景开");
                    mark = true;
                    SleepTime();
                } else {
                    ChangeBackgrounds.setText("随机背景关");
                    mark = false;
                }
            }
        });
    }

    public static void SleepTime() {
        JButton ok = new JButton("确定");
        JLabel label = new JLabel("背景间隔");
        JTextField textField = new JTextField(5);
        JLabel label1 = new JLabel("秒");
        JPopupMenu menu = new JPopupMenu();
        menu.add(label);
        menu.add(textField);
        menu.add(label1);
        menu.add(ok);
        menu.pack();
        menu.setLayout(new FlowLayout(FlowLayout.CENTER));
        menu.show(Frame.MusicTableScrollPane, 45, 220);
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = textField.getText().trim();
                char ch = text.charAt(text.length() - 1);
                if (!(ch >= '0' && ch <= '9')) {
                    JOptionPane.showMessageDialog(Frame.MusicTableScrollPane, "错误");
                } else {
                    menu.setVisible(false);
                    Background(Integer.parseInt(textField.getText().trim()));
                }
            }
        });
    }

    public static void Find() {
        JButton last = Frame.last;
        JButton next = Frame.next;
        JTable MusicTable = find();
        MusicTable.getTableHeader().getColumnModel().getColumn(2).setMaxWidth(0);
        MusicTable.getTableHeader().getColumnModel().getColumn(2).setMinWidth(0);
        MusicTable.getTableHeader().getColumnModel().getColumn(2).setPreferredWidth(0);
        JPopupMenu menu = new JPopupMenu();
        menu.setLayout(new FlowLayout(FlowLayout.CENTER, 60, 0));
        menu.setPopupSize(new Dimension(Frame.MusicField.getWidth(), 130));
        JScrollPane scrollPane = new JScrollPane(MusicTable);
        scrollPane.setPreferredSize(new Dimension(Frame.MusicField.getWidth(), 100));
        menu.add(scrollPane);
        menu.add(last);
        menu.add(next);
        menu.show(Frame.MusicField, 0, Frame.MusicField.getHeight());
        RoutineColor routineColor = new RoutineColor();
        MusicTable.setDefaultRenderer(MusicTable.getColumnClass(-1), routineColor);
        MusicTable.addMouseMotionListener(new MyMouseListener(MusicTable, routineColor));
        MusicTable.addMouseListener(new MyMouseListener(MusicTable, routineColor));
        MusicTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int r = MusicTable.getSelectedRow();
                Object value1 = MusicTable.getValueAt(r, 2);
                menu.setVisible(false);
                try {
                    id = (String) value1;
                    Lrc.readMp3(Lrc.MusicUrl((String) value1), Lrc.LrcUrl((String) value1));
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
    }

    public static JTable find() {
        String[][] data = new String[20][3];
        String[] name = {"", "", ""};
        String s = Frame.MusicField.getText().trim();
        String jsonStr = null;
        DefaultTableModel tableModel;
        JTable MusicTable = new JTable();
        try {
            String str = URLEncoder.encode(s, "utf-8").replaceAll("\\+", "");
            // 转换成encode
            URL url = new URL("http://music.163.com/api/search/get/web?csrf_token=hlpretag=&hlposttag=&s=" + str + "&type=1&offset=" + offset + "&total=true&limit=20");
            HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
            httpCon.setConnectTimeout(3000);
            httpCon.setDoInput(true);
            httpCon.setRequestMethod("GET");
            // 获取相应码
            int respCode = httpCon.getResponseCode();
            if (respCode == 200) {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len = 0;
                // 将输入流转移到内存输出流中
                while ((len = httpCon.getInputStream().read(buffer, 0, buffer.length)) != -1) {
                    out.write(buffer, 0, len);
                }
                // 将内存流转换为字符串
                jsonStr = new String(out.toByteArray());

                JSONArray jsonArray = JSONArray.parseArray(JSONObject.parseObject(JSONObject.parseObject(jsonStr).getString("result")).getString("songs"));
                String a = "name\":\"";
                String q = "\",\"alias\"";
                for (int i = 0; i < jsonArray.size(); i++) {
                    String mess = jsonArray.getJSONObject(i).getString("artists");
                    data[i][0] = jsonArray.getJSONObject(i).getString("name");
                    data[i][1] = String.valueOf(way1(mess, a, q));
                    data[i][2] = jsonArray.getJSONObject(i).getString("id");
                }
                tableModel = new DefaultTableModel(data, name);
                MusicTable.setModel(tableModel);
            } else {
                System.out.println("网易云错误，错误码为" + respCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return MusicTable;
    }

    public static StringBuilder way1(String st, String M, String Q) {
        int count = 0;
        String s = null;
        StringBuilder stringBuilder = new StringBuilder();
        while (st.contains(M)) {
            st = st.substring(st.indexOf(M) + M.length());
            count++;
            s = st.substring(0, st.indexOf(Q)) + "    ";
            stringBuilder.append(s);
        }
        return stringBuilder;
    }

    public static void addMusic() {
        JButton ok = new JButton("确定");
        JButton back = new JButton("取消");
        JPopupMenu menu = new JPopupMenu();
        menu.setLayout(new FlowLayout(FlowLayout.CENTER, 12, 0));
        JTable table = new JTable(MusicTableInfo.MySongSheet());
        RoutineColor routineColor = new RoutineColor();
        table.setDefaultRenderer(table.getColumnClass(-1), routineColor);
        table.addMouseMotionListener(new MyMouseListener(table, routineColor));
        table.addMouseListener(new MyMouseListener(table, routineColor));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(200, 220));
        JLabel label = new JLabel("请选择歌单");
        menu.setPopupSize(new Dimension(200, 265));
        menu.add(label);
        menu.add(scrollPane);
        menu.add(ok);
        menu.add(back);
        menu.show(Frame.addMusic, 0, -250);
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Mysql.InsertTableInfo((String) table.getValueAt(table.getSelectedRow(), 0), Frame.MusicInfo.getText(), id);
            }
        });
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menu.setVisible(false);
            }
        });
    }

    public static void removeMusic() {
        Mysql.RemoveSong(Frame.MusicTable.getColumnName(0), (String) Frame.MusicTable.getValueAt(Frame.MusicTable.getSelectedRow(), 1));
        MusicTable(Frame.MusicTable.getColumnName(0));
    }

    public static void MusicTable(String name) {
        List<String> list = Mysql.ReadTableInfo(name);
        Object[][] data = new Object[list.size() / 2][2];
        String[] TableHeard = {name, ""};
        int i = 0;
        while (i < list.size() / 2) {
            data[i][0] = list.get(i * 2);
            data[i][1] = list.get(i * 2 + 1);
            i++;
        }
        DefaultTableModel tableModel = new DefaultTableModel(data, TableHeard);
        Frame.MusicTable.setModel(tableModel);
        Frame.MusicTable.getColumnModel().getColumn(0).setPreferredWidth(300);
    }

    public static void Background(int time) {
        new Thread(() -> {
            List<String> list = BackgroundShop();
            try {
                int i = 0;
                while (mark) {
                    if (i == list.size()) {
                        i = 0;
                    }
                    Frame.Background.setIcon(new ImageIcon(new URL(list.get(i))));
                    i++;
                    Thread.sleep(time * 1000L);
                }
            } catch (IOException | InterruptedException ioException) {
                ioException.printStackTrace();
            }
        }).start();
    }

    public static List<String> BackgroundShop() {
        List<String> list = new ArrayList<>();
        int page = 1;
        try {
            while (page <= 10) {
                Document document = Jsoup.connect("https://bing.ioliu.cn/?p=" + page + "").get();//请求链接
                Elements elements = document.select("a.mark");//元素集合为每一页的略缩图的那网址的页面
                int i = 1;
                for (Element element : elements) {//forEach遍历每一页的略缩图的那网址的页面
                    String href = element.attr("href");//提取每一页的略缩图网址
                    String newURL = "https://bing.ioliu.cn/" + href + "";//拼接成完整的略缩图网址
                    Document document1 = Jsoup.connect(newURL).get();
                    String imageSrc = document1.select("img.target.progressive__img.progressive--not-loaded").attr("data-progressive");
                    list.add(imageSrc);
                }
                page++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
