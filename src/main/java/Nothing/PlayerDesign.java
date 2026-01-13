//THIS CLASS IS BASICALLY FOR OVER ALL DESIGN OF THE MYPLAYER	
package Nothing;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.net.*;
import Nothing.Play;
import javax.swing.filechooser.FileFilter;

public class PlayerDesign extends JFrame
        implements ActionListener, WindowListener, ChangeListener, MouseListener, Runnable {

    private static final long serialVersionUID = 5242600474143713744L;
    static JWindow ww = new JWindow();
    public static String mediapath = null;
    public static JLayeredPane j1;
    JMenuBar menubar;
    JMenu file, tool, playlist, help;
    JMenuItem fopen, fsave, fload, fexit;
    JMenuItem tgoonline, tcomment;
    static JMenuItem pcreation, pedit, premove, pdelete;
    JMenuItem hhelpdoc, haboutus;
    static JSlider jslider;
    JRadioButton jrepeat;
    JRadioButton jnormal;
    JRadioButton jrandom;
    JButton jprogsleep;
    JButton jtheams;
    JButton jtrackmix;
    static JButton jplaylist;
    JButton jsetrate;
    JButton jequilizer;
    static JButton jplay;
    static JButton jpause;
    static JButton jstop;
    JButton jnext;
    JButton jprev;
    JButton jup;
    JButton jdown;
    JToggleButton jmute;
    JLabel jearth;
    JLabel jy;
    JLabel jm;
    JLabel myp;
    JLabel dplaylistname, dtrackname, dtrackno, dmediatime;
    static JLabel dvplaylistname, dvtrackname, dvtrackno, dvmediatime;
    ButtonGroup bg;
    String imgpath = MyPlayer.imgpath;
    String dvplname = "--", dvtkname = "--", dvtkno = "--", dvmdtime = "--";
    URL url1;
    Component comp;
    int decy = 35, songlen;
    public JFileChooser jfd, loadplaylist;
    JProgressBar proc;
    float g = 0.5f, level = 0f;
    float a = 0.5f, b = 0.5f, c = 0.9f;
    long slidervalue;
    public static int setslider = 0;
    int volstate = 50;
    public static int verify1 = 0;
    JPanel panel;
    static Play pl;
    Color bc;
    Thread runslider;
    int i;
    static public boolean newfile = false;
    MyPlayer mp = new MyPlayer();

    public PlayerDesign() {
        super("this is \"NOTHING\"");
        pl = new Play();
    }

    private JLabel bgLabel;

    private ImageIcon getResourceIcon(String path) {
        if (path == null)
            return null;
        String fullPath = imgpath + path;

        // Ensure leading slash for getResource
        if (!fullPath.startsWith("/")) {
            fullPath = "/" + fullPath;
        }

        URL url = getClass().getResource(fullPath);
        if (url == null) {
            url = getClass().getClassLoader().getResource(fullPath.substring(1));
        }

        if (url == null) {
            System.err.println("Resource NOT found: " + fullPath);
            return null;
        } else {
            // System.out.println("Resource found: " + url);
        }

        ImageIcon icon = new ImageIcon(url);
        if (icon.getImageLoadStatus() == MediaTracker.ERRORED) {
            System.err.println("Image load error: " + fullPath);
        }
        return icon;
    }

    public void PlayerDesignMethod(String car, boolean bgFlag) {
        if (car == null)
            return;
        ImageIcon image = null;
        try {
            if (car.startsWith("file:") || car.startsWith("jar:") || car.startsWith("http")) {
                image = new ImageIcon(new URL(car));
            } else if (car.contains(":") && !car.startsWith("/")) {
                // Absolute file path case
                File f = new File(car);
                if (f.exists()) {
                    image = new ImageIcon(car);
                }
            }

            if (image == null) {
                image = getResourceIcon(car);
            }

            if (image == null || image.getIconWidth() <= 0) {
                if (!car.contains("/")) {
                    image = getResourceIcon("car theams/" + car + ".JPG");
                }
            }
        } catch (Exception e) {
            System.err.println("Could not load image: " + car + " - " + e.getMessage());
        }

        if (image != null) {
            if (bgLabel == null) {
                bgLabel = new JLabel(image);
                getLayeredPane().add(bgLabel, Integer.valueOf(Integer.MIN_VALUE));
            } else {
                bgLabel.setIcon(image);
            }
            // Shift background down by 45 pixels (approx 1.2 cm)
            bgLabel.setBounds(0, 49, 806, 420);
        } else {
            System.err.println("Final background image IS NULL for: " + car);
        }
    }

    public void DefineComponents() {
        panel = new JPanel(null);
        panel.setOpaque(false);
        setContentPane(panel);

        menubar = new JMenuBar();
        menubar.setBackground(Color.DARK_GRAY);
        file = new JMenu("File");
        file.setToolTipText("File Menu");
        file.setMnemonic(KeyEvent.VK_F);
        file.setForeground(Color.lightGray);
        file.setBackground(Color.darkGray);

        fopen = new JMenuItem("Open");
        fopen.setMnemonic(KeyEvent.VK_O);
        KeyStroke ks = KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK);
        fopen.setAccelerator(ks);

        fsave = new JMenuItem("Save");
        fsave.setMnemonic(KeyEvent.VK_S);
        ks = KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK);
        fsave.setAccelerator(ks);

        fload = new JMenuItem("Load");
        fload.setMnemonic(KeyEvent.VK_L);
        ks = KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_DOWN_MASK);
        fload.setAccelerator(ks);

        fexit = new JMenuItem("Exit");
        fexit.setMnemonic(KeyEvent.VK_Q);
        ks = KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK);
        fexit.setAccelerator(ks);

        tool = new JMenu("Tool");
        tool.setForeground(Color.lightGray);
        tool.setBackground(Color.darkGray);
        tgoonline = new JMenuItem("Go-Online");
        tcomment = new JMenuItem("Comment");

        jslider = new JSlider();

        playlist = new JMenu("PlayList");
        playlist.setToolTipText("Play List Menu");
        playlist.setMnemonic(KeyEvent.VK_P);
        playlist.setForeground(Color.lightGray);
        playlist.setBackground(Color.darkGray);
        pcreation = new JMenuItem("PlayList Creation");
        pcreation.setMnemonic(KeyEvent.VK_C);
        ks = KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK);
        pcreation.setAccelerator(ks);

        pedit = new JMenuItem("Edit PlayList");
        pedit.setMnemonic(KeyEvent.VK_E);
        ks = KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK);
        pedit.setAccelerator(ks);

        pdelete = new JMenuItem("Delete PlayList");

        help = new JMenu("Help ?");
        help.setToolTipText("Help Menu");
        help.setMnemonic(KeyEvent.VK_H);
        help.setForeground(Color.lightGray);
        help.setBackground(Color.darkGray);
        hhelpdoc = new JMenuItem("Help");
        hhelpdoc.setMnemonic(KeyEvent.VK_H);
        ks = KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_DOWN_MASK);
        hhelpdoc.setAccelerator(ks);

        haboutus = new JMenuItem("About Us");
        haboutus.setMnemonic(KeyEvent.VK_U);
        ks = KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.CTRL_DOWN_MASK);
        haboutus.setAccelerator(ks);

        jnormal = new JRadioButton("NORMAL", true);
        jnormal.setToolTipText("Normal Order");
        jnormal.setBounds(50, 70 - decy, 100, 20);
        jnormal.setOpaque(false);

        jrepeat = new JRadioButton("REPEAT");
        jrepeat.setToolTipText("Repeat Order");
        jrepeat.setBounds(50, 100 - decy, 100, 20);
        jrepeat.setOpaque(false);

        jrandom = new JRadioButton("RANDOM");
        jrandom.setToolTipText("Random Order");
        jrandom.setBounds(50, 130 - decy, 100, 20);
        jrandom.setOpaque(false);

        jprogsleep = new JButton("SLEEP");
        jprogsleep.setToolTipText("Set Sleep");
        jprogsleep.setBounds(250, 70 - decy, 100, 20);

        jtheams = new JButton("THEMES");
        jtheams.setToolTipText("Change Theam");
        jtheams.setBounds(250, 100 - decy, 100, 20);

        jtrackmix = new JButton("TRACK MIX");
        jtrackmix.setBounds(250, 130 - decy, 100, 20);

        jplaylist = new JButton("PLAYLIST");
        jplaylist.setToolTipText("Show Play List");
        jplaylist.setBounds(450, 70 - decy, 100, 20);

        jsetrate = new JButton("PLAYRATE");
        jsetrate.setBounds(450, 100 - decy, 100, 20);

        jequilizer = new JButton("EQUILIZER");
        jequilizer.setBounds(450, 130 - decy, 100, 20);

        jearth = new JLabel(getResourceIcon("earth.gif"));
        jearth.setBounds(700, 10, 60, 90);
        jy = new JLabel(getResourceIcon("yy.gif"));
        jy.setBounds(680, 60, 70, 90);
        jm = new JLabel(getResourceIcon("music.gif"));
        jm.setBounds(700, 110, 60, 90);
        myp = new JLabel("My Player");
        myp.setForeground(Color.white);
        myp.setBounds(700, 180, 100, 50);

        dplaylistname = new JLabel("PLAYLIST");
        dplaylistname.setForeground(Color.cyan);
        dplaylistname.setBounds(220, 230, 100, 20);

        dvplaylistname = new JLabel(dvplname);
        dvplaylistname.setForeground(Color.cyan);
        dvplaylistname.setBounds(420, 230, 150, 20);

        dtrackname = new JLabel("TRACK");
        dtrackname.setForeground(Color.cyan);
        dtrackname.setBounds(220, 250, 150, 20);

        dvtrackname = new JLabel(dvtkname);
        dvtrackname.setForeground(Color.cyan);
        dvtrackname.setBounds(420, 250, 150, 20);

        dtrackno = new JLabel("TRACK NO.");
        dtrackno.setForeground(Color.cyan);
        dtrackno.setBounds(220, 270, 100, 20);

        dvtrackno = new JLabel(dvtkno);
        dvtrackno.setForeground(Color.cyan);
        dvtrackno.setBounds(420, 270, 150, 20);

        dmediatime = new JLabel("TRACK TIME");
        dmediatime.setForeground(Color.cyan);
        dmediatime.setBounds(220, 290, 100, 20);

        dvmediatime = new JLabel(dvmdtime);
        dvmediatime.setForeground(Color.cyan);
        dvmediatime.setBounds(420, 290, 150, 20);

        jplay = new JButton(getResourceIcon("play.png"));
        jplay.setToolTipText("Play");
        jplay.setBounds(141, 288, 20, 20);

        jpause = new JButton(getResourceIcon("pause.png"));
        jpause.setToolTipText("Pause");
        jpause.setBounds(141, 258, 20, 20);

        jstop = new JButton(getResourceIcon("stop.png"));
        jstop.setToolTipText("Stop");
        jstop.setBounds(141, 320, 20, 20);

        jnext = new JButton(getResourceIcon("next.png"));
        jnext.setToolTipText("Next Track");
        jnext.setBounds(171, 288, 20, 20);

        jprev = new JButton(getResourceIcon("prev.png"));
        jprev.setToolTipText("Previous Track");
        jprev.setBounds(111, 288, 20, 20);

        jup = new JButton(getResourceIcon("up.png"));
        jup.setToolTipText("Volume Up");
        jup.setBounds(630, 267, 30, 30);

        jdown = new JButton(getResourceIcon("down.png"));
        jdown.setToolTipText("Volume Down");
        jdown.setBounds(630, 311, 30, 30);

        jmute = new JToggleButton(getResourceIcon("mut.png"));
        jmute.setToolTipText("Mute Volume");
        jmute.setBounds(637, 297, 17, 15);

        jslider.setBounds(210, 328, 365, 15);
        jslider.setForeground(Color.CYAN);
        jslider.setMaximum(100);
        jslider.setValue(0);

        proc = new JProgressBar(1, 0, 100);
        proc.setBounds(665, 267, 10, 75);
        proc.setForeground(Color.cyan);
        proc.setValue(50);
    }

    public void AddAllComponents() {
        setJMenuBar(menubar);
        menubar.add(file);
        menubar.add(playlist);
        menubar.add(help);
        file.add(fopen);
        file.add(fsave);
        file.add(fload);
        file.addSeparator();
        file.add(fexit);
        playlist.add(pcreation);
        playlist.add(pedit);
        help.add(hhelpdoc);
        help.add(haboutus);

        bg = new ButtonGroup();
        bg.add(jnormal);
        bg.add(jrepeat);
        bg.add(jrandom);

        panel.add(jslider);
        panel.add(jplay);
        panel.add(jpause);
        panel.add(jstop);
        panel.add(jnext);
        panel.add(jprev);
        panel.add(jrepeat);
        panel.add(jnormal);
        panel.add(jrandom);
        panel.add(jprogsleep);
        panel.add(jtheams);
        panel.add(jtrackmix);
        panel.add(jplaylist);
        panel.add(jequilizer);
        panel.add(jsetrate);
        panel.add(proc);
        panel.add(jearth);
        panel.add(jm);
        panel.add(jy);
        panel.add(myp);
        panel.add(jup);
        panel.add(jdown);
        panel.add(jmute);
        panel.add(dplaylistname);
        panel.add(dtrackname);
        panel.add(dtrackno);
        panel.add(dmediatime);
        panel.add(dvplaylistname);
        panel.add(dvtrackname);
        panel.add(dvtrackno);
        panel.add(dvmediatime);
        pack();

        jplay.addActionListener(this);
        jstop.addActionListener(this);
        fexit.addActionListener(this);
        fopen.addActionListener(this);
        fsave.addActionListener(this);
        fload.addActionListener(this);
        pcreation.addActionListener(this);
        pedit.addActionListener(this);
        jup.addActionListener(this);
        jdown.addActionListener(this);
        jpause.addActionListener(this);
        jtheams.addActionListener(this);
        jplaylist.addActionListener(this);
        jnext.addActionListener(this);
        jprev.addActionListener(this);
        jnormal.addActionListener(this);
        jrepeat.addActionListener(this);
        jrandom.addActionListener(this);
        jmute.addActionListener(this);
        jequilizer.addActionListener(this);
        jprogsleep.addActionListener(this);
        hhelpdoc.addActionListener(this);
        haboutus.addActionListener(this);
        jtrackmix.addActionListener(this);
        jsetrate.addActionListener(this);
        addWindowListener(this);

        jslider.addChangeListener(this);

        // Add MouseListeners for hover effects (same as original)
        jplay.addMouseListener(this);
        jstop.addMouseListener(this);
        jpause.addMouseListener(this);
        jnext.addMouseListener(this);
        jprev.addMouseListener(this);
        jup.addMouseListener(this);
        jdown.addMouseListener(this);
        jtheams.addMouseListener(this);
        jplaylist.addMouseListener(this);
        jslider.addMouseListener(this);
        myp.addMouseListener(this);
    }

    public void moveSlider() {
        runslider = new Thread(this, "slider-thread");
        runslider.setDaemon(true);
        runslider.start();
    }

    public void run() {
        sliderRunning();
    }

    public void sliderRunning() {
        while (true) {
            if (Play.SPLAY && Play.mediaPlayer != null) {
                try {
                    double current = Play.mediaPlayer.getCurrentTime().toSeconds();
                    SwingUtilities.invokeLater(() -> {
                        jslider.setValue((int) current);
                        int m = (int) current / 60;
                        int s = (int) current % 60;
                        dvmediatime.setText(String.format("%02d : %02d", m, s));
                    });
                    Thread.sleep(1000);
                } catch (Exception e) {
                }
            } else {
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                }
            }
        }
    }

    public void jplayaction() {
        if (Play.path == null) {
            pl.stop();
            new EmptyBox().empty("PLEASE SELECT MEDIA FILE");
        } else if (Play.mediaPlayer != null && Play.SPAUSE) {
            pl.play();
        } else {
            pl.locate(Play.path);
            pl.play();
        }
    }

    public void fopenaction() {
        if (jfd == null)
            jfd = new JFileChooser();
        jfd.setFileFilter(new mp3Filter("Media Files (*.mp3, *.wav)"));
        jfd.setMultiSelectionEnabled(true);
        if (jfd.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            Play.mp3files(jfd.getSelectedFiles());
            jplay.doClick();
        }
    }

    public void jupaction() {
        if (Play.mediaPlayer != null) {
            double vol = Play.mediaPlayer.getVolume();
            vol = Math.min(1.0, vol + 0.05);
            Play.mediaPlayer.setVolume(vol);
            proc.setValue((int) (vol * 100));
        }
    }

    public void jdownaction() {
        if (Play.mediaPlayer != null) {
            double vol = Play.mediaPlayer.getVolume();
            vol = Math.max(0.0, vol - 0.05);
            Play.mediaPlayer.setVolume(vol);
            proc.setValue((int) (vol * 100));
        }
    }

    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == jplay)
            jplayaction();
        else if (src == jstop)
            pl.stop();
        else if (src == jpause)
            pl.pause();
        else if (src == fexit)
            System.exit(0);
        else if (src == fopen)
            fopenaction();
        else if (src == jnext)
            pl.next();
        else if (src == jprev)
            pl.previous();
        else if (src == jup)
            jupaction();
        else if (src == jdown)
            jdownaction();
        else if (src == jnormal)
            Play.order = 1;
        else if (src == jrepeat)
            Play.order = 2;
        else if (src == jrandom)
            Play.order = 3;
        else if (src == jtheams) {
            new SelectTheams().select();
        } else if (src == jplaylist) {
            new DisplayList().display();
        } else if (src == pcreation) {
            new DisplayList().display(); // Playlist creation is handled in DisplayList for now
        } else if (src == pedit) {
            new EmptyBox().editList();
        } else if (src == jprogsleep) {
            new EmptyBox().sleepmodule();
        } else if (src == jsetrate) {
            new EmptyBox().playerRate();
        } else if (src == haboutus) {
            new EmptyBox().aboutUs("");
        } else if (src == hhelpdoc) {
            new EmptyBox().helpmodule("");
        }
    }

    public void stateChanged(ChangeEvent e) {
        if (e.getSource() == jslider && jslider.getValueIsAdjusting()) {
            if (Play.mediaPlayer != null) {
                Play.mediaPlayer.seek(javafx.util.Duration.seconds(jslider.getValue()));
            }
        }
    }

    // Window Events
    public void windowOpened(WindowEvent e) {
    }

    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }

    public void windowClosed(WindowEvent e) {
    }

    public void windowIconified(WindowEvent e) {
    }

    public void windowDeiconified(WindowEvent e) {
    }

    public void windowActivated(WindowEvent e) {
    }

    public void windowDeactivated(WindowEvent e) {
    }

    // Mouse Events for Hover Effects
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == myp) {
            new EmptyBox().openWindow();
        }
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
        if (e.getSource() == jslider) {
            if (Play.mediaPlayer != null) {
                Play.mediaPlayer.seek(javafx.util.Duration.seconds(jslider.getValue()));
            }
        }
    }

    public void mouseEntered(MouseEvent e) {
        Object src = e.getSource();
        if (src instanceof JButton) {
            ((JButton) src).setBackground(Color.DARK_GRAY);
        }
    }

    public void mouseExited(MouseEvent e) {
        Object src = e.getSource();
        if (src instanceof JButton) {
            ((JButton) src).setBackground(null);
        }
    }
}

class mp3Filter extends FileFilter {
    String des = "";

    public mp3Filter(String des) {
        this.des = des;
    }

    public boolean accept(File file) {
        if (file.isDirectory())
            return true;
        String name = file.getName().toLowerCase();
        return name.endsWith(".mp3") || name.endsWith(".wav") || name.endsWith(".mpeg") || name.endsWith(".mpg");
    }

    public String getDescription() {
        return des;
    }
}