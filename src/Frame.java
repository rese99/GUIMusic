import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.net.URL;

public class Frame extends JFrame {
    public static JTextField MusicField;
    public static JScrollPane MusicTableScrollPane, FindTableScrollPane, SingsScrollPane;
    public static DefaultTableModel MusicModel;
    public static JTable MusicTable, FindTable, SingerRoot, Singers;
    public static JButton Last, Next, last, next, PlayPatten, Find, Recommend, Singer, SongSheet, Ranking, ControlButton, AddMusic, removeMusic, addMusic;
    public static JToggleButton play;
    public static JPanel ControlPanel, ButtonPanel, TextPanel, buttonPanel, playPanel, MusicPanel, FindPanel, findPanel, BoxPanel, FindTablePanel, SingerRootPanel, SingersPanel;
    public static JLabel label, MusicInfo, Background;
    public static int count = 1, clickNum = 0;
    public static RoutineColor routineColor = new RoutineColor();
    public static Border js = BorderFactory.createEmptyBorder();

    Frame() {
        routineColor.setOpaque(false);

        SingerRoot = new JTable() {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        SingerRoot.setRowHeight(SingerRoot.getRowHeight() + 15);
        SingerRoot.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        SingerRoot.setOpaque(false);
        SingerRoot.setShowGrid(false);
        SingerRoot.setDefaultRenderer(SingerRoot.getColumnClass(-1), routineColor);
        SingerRoot.addMouseMotionListener(new MyMouseListener(SingerRoot, routineColor));
        SingerRoot.addMouseListener(new MyMouseListener(SingerRoot, routineColor));

        SingerRootPanel = new JPanel();
        SingerRootPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        SingerRootPanel.setBounds(0, 0, 85, 570);
        SingerRootPanel.setOpaque(false);
        SingerRootPanel.add(SingerRoot);

        Singers = new JTable() {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        Singers.setRowHeight(Singers.getRowHeight() + 5);
        Singers.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        Singers.setOpaque(false);
        Singers.setShowGrid(false);
        Singers.setDefaultRenderer(Singers.getColumnClass(-1), routineColor);
        Singers.addMouseMotionListener(new MyMouseListener(Singers, routineColor));
        Singers.addMouseListener(new MyMouseListener(Singers, routineColor));
        SingsScrollPane = new JScrollPane(Singers);
        SingsScrollPane.setBorder(js);
        SingsScrollPane.setOpaque(false);
        SingsScrollPane.getViewport().setOpaque(false);
        SingsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        SingsScrollPane.setPreferredSize(new Dimension(815, 570));

        SingersPanel = new JPanel();
        SingersPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        SingersPanel.setBounds(100, 0, 815, 570);
        SingersPanel.setOpaque(false);
        SingersPanel.add(SingsScrollPane);

        FindTable = new JTable(MusicTableInfo.FindTableModel()) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        FindTable.setRowHeight(180);
        for (int i = 0; i < FindTable.getColumnCount(); i++) {
            FindTable.getColumnModel().getColumn(i).setPreferredWidth(180);
        }
        FindTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        FindTable.setShowGrid(false);
        FindTable.setOpaque(false);
        FindTable.setDefaultRenderer(FindTable.getColumnClass(-1), routineColor);
        FindTable.addMouseMotionListener(new MyMouseListener(FindTable, routineColor));
        FindTable.addMouseListener(new MyMouseListener(FindTable, routineColor));

        FindTableScrollPane = new JScrollPane(FindTable);
        FindTableScrollPane.setBorder(js);
        FindTableScrollPane.setOpaque(false);
        FindTableScrollPane.getViewport().setOpaque(false);
        FindTableScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        FindTableScrollPane.setPreferredSize(new Dimension(900, 570));

        FindTablePanel = new JPanel();
        FindTablePanel.setOpaque(false);
        FindTablePanel.setBounds(0, 30, 900, 570);
        FindTablePanel.add(FindTableScrollPane);

        MusicModel = MusicTableInfo.MusicTableInfo();
        MusicTable = new JTable(MusicModel) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        MusicTable.getTableHeader().setReorderingAllowed(false);
        MusicTable.getColumnModel().getColumn(0).setPreferredWidth(300);
        MusicTable.setRowHeight(MusicTable.getRowHeight() + 5);
        MusicTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        MusicTable.setShowGrid(false);
        MusicTable.setOpaque(false);
        MusicTable.setDefaultRenderer(MusicTable.getColumnClass(-1), routineColor);
        MusicTable.addMouseMotionListener(new MyMouseListener(MusicTable, routineColor));
        MusicTable.addMouseListener(new MyMouseListener(MusicTable, routineColor));

        MusicTableScrollPane = new JScrollPane(MusicTable);
        MusicTableScrollPane.setBorder(js);
        MusicTableScrollPane.setOpaque(false);
        MusicTableScrollPane.getViewport().setOpaque(false);
        MusicTableScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        MusicTableScrollPane.setPreferredSize(new Dimension(300, 540));

        label = new JLabel();
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setPreferredSize(new Dimension(900, 100));
        MusicInfo = new JLabel();
        MusicInfo.setFont(new Font("幼圆", Font.PLAIN, 14));
        MusicInfo.setHorizontalAlignment(JLabel.CENTER);
        MusicInfo.setPreferredSize(new Dimension(300, 30));

        Last = new JButton("上一首");
        Next = new JButton("下一首");
        last = new JButton("上一页");
        next = new JButton("下一页");
        PlayPatten = new JButton("单曲循环");
        Find = new JButton("->");
        Find.setSize(Find.getWidth(), 30);
        Recommend = new JButton("推荐");
        Recommend.setSize(Recommend.getWidth(), 30);
        Singer = new JButton("歌手");
        Singer.setSize(Singer.getWidth(), 30);
        SongSheet = new JButton("歌单");
        SongSheet.setSize(Singer.getWidth(), 30);
        Ranking = new JButton("排行");
        Ranking.setSize(Singer.getWidth(), 30);
        ControlButton = new JButton("设置");
        ControlButton.setSize(ControlButton.getWidth(), 30);
        AddMusic = new JButton("我的歌单");
        AddMusic.setSize(ControlButton.getWidth(), 30);
        addMusic = new JButton("添加到歌单");
        addMusic.setSize(addMusic.getWidth(), 30);
        removeMusic = new JButton("删除歌曲");
        removeMusic.setSize(removeMusic.getWidth(), 30);
        play = new JToggleButton("播放");

        buttonPanel = new JPanel();
        buttonPanel.setBounds(0, 0, 300, 150);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 2, 5));
        buttonPanel.add(MusicInfo);
        buttonPanel.add(Last);
        buttonPanel.add(play);
        buttonPanel.add(Next);
        buttonPanel.add(PlayPatten);

        playPanel = new JPanel();
        playPanel.setBounds(300, 0, 900, 150);
        playPanel.add(label, BorderLayout.CENTER);

        ButtonPanel = new JPanel();
        ButtonPanel.setBounds(0, 600, 1200, 150);
        ButtonPanel.setLayout(null);
        ButtonPanel.add(buttonPanel);
        ButtonPanel.add(playPanel);

        ControlPanel = new JPanel();
        ControlPanel.setBounds(0, 0, 300, 30);
        ControlPanel.add(ControlButton);
        ControlPanel.add(Box.createHorizontalStrut(120));
        ControlPanel.add(AddMusic);

        MusicPanel = new JPanel();
        MusicPanel.setBounds(0, 30, 300, 570);
        MusicPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 0));
        MusicPanel.add(MusicTableScrollPane);
        MusicPanel.add(addMusic);
        MusicPanel.add(removeMusic);

        MusicField = new JTextField(26);
        BoxPanel = new JPanel();
        BoxPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        BoxPanel.setBounds(900 - MusicField.getWidth() - Find.getWidth(), 0, MusicField.getWidth() + Find.getWidth(), 30);
        BoxPanel.add(MusicField);
        BoxPanel.add(Find);

        findPanel = new JPanel();
        findPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 1));
        findPanel.setBounds(0, 0, 900, 30);
        findPanel.add(Recommend);
        findPanel.add(Singer);
        findPanel.add(SongSheet);
        findPanel.add(Ranking);
        findPanel.add(BoxPanel);

        FindPanel = new JPanel();
        FindPanel.setLayout(null);
        FindPanel.setBounds(300, 0, 900, 600);
        FindPanel.add(findPanel);
        FindPanel.add(FindTablePanel);

        TextPanel = new JPanel();
        TextPanel.setBounds(0, 0, 1200, 600);
        TextPanel.setLayout(null);
        TextPanel.add(ControlPanel);
        TextPanel.add(MusicPanel);
        TextPanel.add(FindPanel);

        ControlPanel.setOpaque(false);
        ButtonPanel.setOpaque(false);
        TextPanel.setOpaque(false);
        buttonPanel.setOpaque(false);
        playPanel.setOpaque(false);
        MusicPanel.setOpaque(false);
        FindPanel.setOpaque(false);
        findPanel.setOpaque(false);
        BoxPanel.setOpaque(false);

        ImageIcon bg = null;
        try {
            bg = new ImageIcon(new URL("https://gss0.baidu.com/-vo3dSag_xI4khGko9WTAnF6hhy/zhidao/pic/item/4b90f603738da977a4a9dac0b151f8198718e353.jpg"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Background = new JLabel(bg);
        Background.setSize(1200, 750);
        this.getLayeredPane().add(Background, new Integer(Integer.MIN_VALUE));
        JPanel pan = (JPanel) this.getContentPane();
        pan.setOpaque(false);
        pan.add(TextPanel);
        pan.add(ButtonPanel);
        this.setSize(1200, 750);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void ButtonListener() {
        play.setSelected(false);
        Recommend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FindTablePanel.removeAll();
                FindTablePanel.updateUI();
                FindTablePanel.setLayout(new FlowLayout());
                new Thread(() -> {
                    FindTable.setModel(MusicTableInfo.FindTableModel());
                    FindTable.setRowHeight(180);
                    for (int i = 0; i < FindTable.getColumnCount(); i++) {
                        FindTable.getColumnModel().getColumn(i).setPreferredWidth(180);
                    }
                    FindTable.updateUI();
                    FindTablePanel.add(FindTableScrollPane);
                }).start();
            }
        });
        Singer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FindTablePanel.removeAll();
                FindTablePanel.updateUI();
                FindTablePanel.setLayout(null);
                MusicTableInfo.SingerRoot();
            }
        });
        Singers.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JLabel label = (JLabel) Singers.getValueAt(Singers.getSelectedRow(), Singers.getSelectedColumn());
                int x = MusicTableInfo.list.indexOf(label.getText());
                MusicTableInfo.SingersInfo(MusicTableInfo.url.get(x),label.getText());
            }
        });
        MusicTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (clickNum == 1) {
                    try {
                        MusicInfo.setText((String) MusicTable.getValueAt(MusicTable.getSelectedRow(), 0));
                        Lrc.readMp3(Lrc.MusicUrl((String) MusicTable.getValueAt(MusicTable.getSelectedRow(), 1)), Lrc.LrcUrl((String) MusicTable.getValueAt(MusicTable.getSelectedRow(), 1)));
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                    clickNum = 0;
                    return;
                }
                java.util.Timer timer = new java.util.Timer();
                timer.schedule(new java.util.TimerTask() {
                    private int n = 0;

                    public void run() {
                        if (n == 1) {
                            clickNum = 0;
                            n = 0;
                            this.cancel();
                            return;
                        }
                        clickNum++;
                        n++;
                    }
                }, new java.util.Date(), 500);
            }
        });
        addMusic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ButtonMethod.addMusic();
            }
        });
        removeMusic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ButtonMethod.removeMusic();
            }
        });
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ButtonMethod.offset++;
                ButtonMethod.Find();
            }
        });
        last.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ButtonMethod.offset > 1) {
                    ButtonMethod.offset--;
                    ButtonMethod.Find();
                }
            }
        });
        ControlButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ButtonMethod.ControlButton();
            }
        });
        AddMusic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ButtonMethod.AddMusic();
            }
        });
        Last.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("你好");
            }
        });
        Next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(false);
            }
        });
        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (play.isSelected()) {
                    play.setText("暂停");
                    Lrc.start();

                    if (count == 1) {
                        Lrc.loop();
                    }
                } else {
                    play.setText("播放");
                    Lrc.stop();
                }
            }
        });
        Find.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ButtonMethod.Find();
            }
        });
        PlayPatten.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                count++;
                while (true) {
                    if (count == 1) {
                        PlayPatten.setText("单曲循环");
                        Lrc.loop();
                        break;
                    }
                    if (count == 2) {
                        PlayPatten.setText("顺序播放");
                        Lrc.Order();
                        break;
                    }
                    if (count == 3) {
                        PlayPatten.setText("随机播放");
                        break;
                    }
                    if (count > 3) {
                        count = 1;
                    }
                }
            }
        });
    }

    public static void main(String[] args) {
       new Thread(()->{
           Mysql.create();
           try {
               Mysql.SingersInfo();
           } catch (Exception e) {
               e.printStackTrace();
           }
       }).start();
        new Frame();
        ButtonListener();
    }
}
