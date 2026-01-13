package Nothing;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

public class EmptyBox extends JDialog implements ActionListener, MouseListener, ListSelectionListener, FileFilter {

    private static final long serialVersionUID = -8942290231204795032L;
    JLabel empmsg, empimgicon;
    JButton empclose;
    JButton rateup, ratedown, ratenormal;
    JTextField playlistnameinput;
    JList<String> edllist;
    static public String plist = null;
    public static boolean sleepon = false;
    static public float rate = 1.0f, vary = 1.0f;
    Color allbtc = Color.cyan, allp = Color.red, allr = Color.black;
    static String plistname[] = null, path[], name[];
    static Set<String> pathset, nameset;
    static int idex = -1;
    static boolean flag = false;

    public EmptyBox() {
        super(MyPlayer.mp.frame, true);
        getContentPane().setBackground(Color.darkGray);
        getContentPane().setLayout(null);
    }

    private ImageIcon getResourceIcon(String name) {
        URL url = getClass().getResource(MyPlayer.imgpath + name);
        if (url != null)
            return new ImageIcon(url);
        return null;
    }

    public void empty(String notice) {
        empmsg = new JLabel(notice);
        empmsg.setForeground(Color.white);
        empmsg.setBounds(60, 20, 200, 20);

        ImageIcon icon = getResourceIcon("error.png");
        if (icon != null) {
            empimgicon = new JLabel(icon);
            empimgicon.setBounds(5, 5, 50, 50);
            add(empimgicon);
        }

        add(empmsg);
        empclose = new JButton("OK");
        empclose.setBounds(90, 60, 70, 20);
        add(empclose);

        empclose.addActionListener(this);
        empclose.addMouseListener(this);

        setSize(240, 130);
        setLocationRelativeTo(MyPlayer.mp.frame);
        setVisible(true);
    }

    public void playerRate() {
        empmsg = new JLabel("PLAY RATE CHANGE");
        empmsg.setForeground(Color.white);
        empmsg.setBounds(60, 20, 200, 20);

        rateup = new JButton("INCREASE");
        rateup.setBounds(65, 50, 100, 20);

        ratenormal = new JButton("NORMAL");
        ratenormal.setBounds(65, 80, 100, 20);

        ratedown = new JButton("DECREASE");
        ratedown.setBounds(65, 110, 100, 20);

        empclose = new JButton("OK");
        empclose.setBounds(80, 150, 70, 20);

        add(empmsg);
        add(rateup);
        add(ratedown);
        add(ratenormal);
        add(empclose);

        rateup.addActionListener(this);
        ratenormal.addActionListener(this);
        ratedown.addActionListener(this);
        empclose.addActionListener(this);

        setSize(240, 230);
        setLocationRelativeTo(MyPlayer.mp.frame);
        setVisible(true);
    }

    public void openWindow() {
        setTitle("WELCOME TO My Player");
        setSize(300, 320);
        setLocationRelativeTo(MyPlayer.mp.frame);

        JLabel welcome = new JLabel("WELCOME TO", JLabel.CENTER);
        welcome.setForeground(Color.RED);
        welcome.setFont(new Font("Arial", Font.BOLD, 14));
        welcome.setBounds(0, 10, 300, 20);
        add(welcome);

        // Grouped graphics like in the screenshot
        JLabel jearth = new JLabel(getResourceIcon("earth.gif"));
        jearth.setBounds(120, 40, 60, 90);
        add(jearth);

        JLabel jy = new JLabel(getResourceIcon("yy.gif"));
        jy.setBounds(100, 80, 70, 90);
        add(jy);

        JLabel jm = new JLabel(getResourceIcon("music.gif"));
        jm.setBounds(120, 120, 60, 90);
        add(jm);

        JLabel mypLabel = new JLabel("My Player", JLabel.CENTER);
        mypLabel.setForeground(Color.white);
        mypLabel.setFont(new Font("Arial", Font.BOLD, 18));
        mypLabel.setBounds(0, 210, 300, 30);
        add(mypLabel);

        empclose = new JButton("GO");
        empclose.setBounds(115, 250, 70, 25);
        add(empclose);
        empclose.addActionListener(this);

        setVisible(true);
    }

    public void sleepmodule() {
        empty("Sleep module under development");
    }

    public void helpmodule(String path) {
        empty("Help documentation not available yet");
    }

    public void aboutUs(String path) {
        openWindow();
    }

    public void canSave(String msg) {
        empty("Save functionality coming soon");
    }

    public void loadEditList() {
    }

    public void editList() {
    }

    @Override
    public boolean accept(File f) {
        return f.getName().toLowerCase().endsWith(".abi");
    }

    public void actionPerformed(ActionEvent ae) {
        Object src = ae.getSource();
        if (src == empclose) {
            dispose();
        } else if (src == rateup) {
            if (Play.mediaPlayer != null) {
                vary = Math.min(2.0f, vary + 0.1f);
                Play.mediaPlayer.setRate(vary);
            }
        } else if (src == ratedown) {
            if (Play.mediaPlayer != null) {
                vary = Math.max(0.5f, vary - 0.1f);
                Play.mediaPlayer.setRate(vary);
            }
        } else if (src == ratenormal) {
            if (Play.mediaPlayer != null) {
                vary = 1.0f;
                Play.mediaPlayer.setRate(vary);
            }
        }
    }

    public void valueChanged(ListSelectionEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
        if (e.getSource() instanceof JButton)
            ((JButton) e.getSource()).setBackground(allbtc);
    }

    public void mouseExited(MouseEvent e) {
        if (e.getSource() instanceof JButton)
            ((JButton) e.getSource()).setBackground(null);
    }
}
