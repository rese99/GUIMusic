import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Lrc {
    public static boolean mark = true;
    public static boolean sure = true;
    public static final Pattern pattern = Pattern.compile("(?<=\\[)[0-9]+?\\:[0-9]+?(\\.[0-9]+)?(?=\\])");//匹配[xx:xx.xx]中的内容（不含[]）
    public static AudioInputStream ais;
    public static BufferedImage buffImg;
    public static AudioFormat format;
    public static long time;
    public static Clip clip;
    public static ArrayList<Code> lrc = new ArrayList<Code>();
    public static volatile boolean Draw = true;
    public static String url;

    /**
     * 配置格式
     *
     * @param line
     */

    public static void parseLine(String line) {

        Matcher matcher = pattern.matcher(line);

        String time = "";

        String str = "";

        while (matcher.find()) { // 匹配获取每一句歌词

            time = matcher.group(); // 获取每句歌词前面的时间

            str = line.substring(line.indexOf(time) + time.length() + 1); // 获取后面的歌词

            str = str.replaceAll("^(\\[[0-9]+?\\:[0-9]+?(\\.[0-9]+?)?\\])*", "");  // 处理一行有多个时间

            lrc.add(new Code(strToLong(time), str));

        }

    }


    /**
     * 获取歌词时长，及其转为毫秒数
     *
     * @param timeStr
     * @return
     */

    private static long strToLong(String timeStr) {

        String[] s = timeStr.split(":");

        int min = Integer.parseInt(s[0]);

        String[] ss = s[1].split("\\.");

        int sec = Integer.parseInt(ss[0]);
        int mill = Integer.parseInt(ss[1]);
        return min * 60 * 1000 + sec * 1000 + mill;

    }

    public static String MusicInfo() {
        String url = Lrc.url.replace("http://music.163.com/song/media/outer/url?id=", "https://music.163.com/song?id=");
        Document document = null;
        try {
            document = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements elements = document.select("div[class=cnt]");
        String text = elements.select("em[class=f-ff2]").text();
        String name = elements.select("p[class=des s-fc4]").get(0).text().replace("歌手：", " ");
        return text + name;
    }

    public static void readMp3(URL MusicUrl, URL url) throws Exception {
        Lrc.url = String.valueOf(MusicUrl);
        Frame.label.setIcon(null);
        if (lrc != null && clip != null) {
            stop();
            lrc.clear();
            clip = null;
        }
        byte[] x = new byte[1024 * 6];
        int len;
        String[] p = {"]", "[", "][", "\r\n", "\\n", "\n", "\"[", "\"\n["};
        List<String> lines = new ArrayList<String>();
        String line1 = null;
        String s = null;
        String v;

        URLConnection connection = url.openConnection();
        BufferedInputStream buffer = new BufferedInputStream(connection.getInputStream());
        while ((len = buffer.read(x)) != -1) {
            s = new String(x, 0, len);
        }
        buffer.close();
        s = s.replace(p[4], p[3]);
        s = s.replace(p[6], p[7]);
        lines.add(s);
        BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(String.valueOf(lines).getBytes(StandardCharsets.UTF_8))));

        lines.clear();
        while ((v = reader.readLine()) != null) {
            if (v.contains(p[2])) {
                String q = v.substring(v.lastIndexOf(p[0]) + p[0].length());
                v = v.replace(p[2], p[0] + q + p[3] + p[1]);
                line1 = v.split("\n")[1];
                v = (v.split("\n")[0].trim());
                lines.add(line1 + p[5]);
            }
            lines.add(v + p[5]);
            Collections.sort(lines);
        }
        reader.close();
        BufferedReader reader1 = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(String.valueOf(lines).getBytes(StandardCharsets.UTF_8))));
        while ((v = reader1.readLine()) != null) {
            v = v.replace(", ", "");
            parseLine(v);
        }
        reader1.close();


        try {
            ais = AudioSystem.getAudioInputStream(MusicUrl.openStream());
            format = ais.getFormat(); // 获得此音频输入流中声音数据的音频格式

            if (format.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {

                format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,  // 音频编码技术

                        format.getSampleRate(), // 每秒的样本数

                        16, // 每个样本中的位数

                        format.getChannels(), // 声道数（单声道 1 个，立体声 2 个，等等）

                        format.getChannels() * 2, // 每帧中的字节数

                        format.getSampleRate(), // 每秒的帧数

                        false // 指示是否以 big-endian 字节顺序存储单个样本中的数据（false 意味着 little-endian）

                );
                ais = AudioSystem.getAudioInputStream(format, ais);
            }

            clip = AudioSystem.getClip();
            clip.open(ais);
            time = clip.getMicrosecondLength();
            ais.close();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            JOptionPane.showMessageDialog(null, "该音乐似乎没有版权哦！");
            Frame.play.setSelected(false);
            Frame.play.setText("播放");
            lrc.clear();
        }
        if (Frame.play.isSelected()) {
            if (clip != null) {
                Frame.MusicInfo.setText(MusicInfo());
                start();
            }
        }
    }

    public static void stop() {
        if (clip != null) {
            Draw = false;
            clip.stop();
        }
    }

    public static void start() {
        Draw = true;
        sure = false;
        if (clip != null) {
            clip.start();
            if (clip.getMicrosecondPosition() == time) {
                clip.setMicrosecondPosition(0);
                clip.start();
            }
            if (clip.getMicrosecondPosition() == 0) {
                kk();
            }
        }
    }

    public static void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public static void Order() {
        for (int i = 0; i < Frame.MusicTable.getRowCount(); i++) {
            try {
                readMp3(MusicUrl((String) Frame.MusicTable.getValueAt(i, 1)), LrcUrl((String) Frame.MusicTable.getValueAt(i, 1)));
                Thread.sleep(time);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (i == Frame.MusicTable.getRowCount() - 1) {
                i = 0;
            }
        }
    }

    public static void kk() {
        new Thread(() -> {
            float z;
            int index = 0;
            Font font = new Font("幼圆", Font.PLAIN, 16);
            while (index < lrc.size()) {
                long time = clip.getMicrosecondPosition() / 1000;
                if (lrc.get(index).getTime() > clip.getMicrosecondLength() / 1000) {
                    break;
                }
                if (lrc.get(index).getTime() == time) {
                    Frame.label.removeAll();
                    float y = lrc.get(index).getStr().trim().length();
                    if (index + 1 >= lrc.size()) {
                        z = y * 1000;
                    } else {
                        z = (lrc.get(index + 1).getTime() - lrc.get(index).getTime());
                    }
                    if (lrc.get(index).getStr().trim().length() > 0) {
                        float x = 1 / y;
                        while (x <= 1) {
                            while (!Draw) {
                            }
                            ImageIcon icon = StringTwoColor(lrc.get(index).getStr().trim(), font, Color.RED, Color.BLACK, x);
                            Frame.label.setIcon(icon);
                            try {
                                Thread.sleep((long) (z / y));
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if (x == 1) {
                                break;
                            }
                            x = x + 1 / y;
                            if (x > 1) {
                                x = 1;
                            }
                        }
                    }
                    index++;
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else if (lrc.get(index).getTime() < time) {
                    Frame.label.removeAll();
                    float y = lrc.get(index).getStr().trim().length();
                    if (index + 1 >= lrc.size()) {
                        z = y * 1000;
                    } else {
                        z = (lrc.get(index + 1).getTime() - lrc.get(index).getTime());
                    }
                    if (lrc.get(index).getStr().trim().length() > 0) {
                        float x = 1 / y;
                        while (x <= 1) {
                            while (!Draw) {
                            }
                            ImageIcon icon = StringTwoColor(lrc.get(index).getStr().trim(), font, Color.RED, Color.BLACK, x);
                            Frame.label.setIcon(icon);
                            try {
                                Thread.sleep((long) (z / y));
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if (x == 1) {
                                break;
                            }
                            x = x + 1 / y;
                            if (x > 1) {
                                x = 1;
                            }
                        }
                    }
                    index++;
                }
            }
            Frame.label.setIcon(null);
        }).start();
    }

    public static ImageIcon StringTwoColor(String s, Font font, Color c1, Color c2, float ratio) {

        //获取字符串的宽（显示在屏幕上所占的像素px）
        JLabel label = new JLabel();
        label.setText(s);
        label.setFont(font);

        FontMetrics metrics = label.getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText());
        int height = metrics.getHeight();
        height += label.getFont().getSize();

        //构造一个具有指定尺寸及类型为预定义图像类型之一的 BufferedImage
        buffImg = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
        BufferedImage buffImg1 = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
        BufferedImage buffImg2 = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);

        //通过BufferedImage创建一个 Graphics2D对象
        Graphics2D g1 = buffImg1.createGraphics();
        Graphics2D g2 = buffImg2.createGraphics();

        //设置抗锯齿
        g1.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //设置字体
        g1.setFont(label.getFont());
        g2.setFont(label.getFont());

        //设置颜色
        g1.setColor(c1);
        g2.setColor(c2);

        //画字符串
        g1.drawString(label.getText(), 0, height - label.getFont().getSize());
        g2.drawString(label.getText(), 0, height - label.getFont().getSize());

        //按照比例清除相关的像素点
        if (ratio <= 1 && ratio > 0) {

            int rgb = 0x00000000;

            //清除buffImg1
            for (int y = 0; y < height; y++) {
                for (int x = width - 1; x >= width * ratio; x--) {
                    buffImg1.setRGB(x, y, rgb);
                }
            }

            //清除buffImg2
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width * ratio; x++) {
                    buffImg2.setRGB(x, y, rgb);
                }
            }

            //写入buffImg
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width * ratio; x++) {
                    buffImg.setRGB(x, y, buffImg1.getRGB(x, y));
                }
            }
            for (int y = 0; y < height; y++) {
                for (int x = width - 1; x >= width * ratio; x--) {
                    buffImg.setRGB(x, y, buffImg2.getRGB(x, y));
                }
            }
        }
        Image img = buffImg;
        ImageIcon imgIcon = new ImageIcon(img);
        return imgIcon;
    }

    public static URL MusicUrl(String id) throws Exception {
        URL url = new URL("http://music.163.com/song/media/outer/url?id=" + id);
        return url;
    }

    public static URL LrcUrl(String id) throws Exception {
        URL url = new URL("http://music.163.com/api/song/media?id=" + id);
        return url;
    }


    static class Code {

        private long time;

        private String str;


        public Code(long time, String str) {

            setTime(time);

            setStr(str);

        }


        public long getTime() {

            return time;

        }


        public void setTime(long time) {

            this.time = time;

        }


        public String getStr() {

            return str;

        }


        public void setStr(String str) {

            this.str = str;

        }

    }

}