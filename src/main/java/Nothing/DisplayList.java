package Nothing;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import java.io.File;

public class DisplayList extends JDialog implements ActionListener, ListSelectionListener, MouseListener {

    private static final long serialVersionUID = -5888458341510383164L;
    Container con;
    JButton jpplay, jpadd, jpremove, jphide;
    JList<String> jplist;
    JPanel panel;
    int idex = -1;
    static public JFileChooser jfd;
    Color dspbtc, allbtc = Color.cyan, allp = Color.red, allr = Color.black;

    public void display() {
        if (Play.pathsset.size() < 0) {
            new EmptyBox().empty("Please select the MP3 file");
        } else {
            con = getContentPane();
            panel = new JPanel();
            panel.setBackground(Color.darkGray);
            panel.setLayout(new FlowLayout());

            jpplay = new JButton("Play");
            jpadd = new JButton("Add");
            jpremove = new JButton("Remove");
            jphide = new JButton("Hide");
            
            jplist = new JList<>(Play.names != null ? Play.names : new String[0]);
            jplist.addListSelectionListener(this);
            jplist.setVisibleRowCount(10);
            jplist.setFixedCellHeight(20);
            jplist.setFixedCellWidth(280);

            jpplay.addActionListener(this);
            jpadd.addActionListener(this);
            jpremove.addActionListener(this);
            jphide.addActionListener(this);

            jpplay.addMouseListener(this);
            jpadd.addMouseListener(this);
            jpremove.addMouseListener(this);
            jphide.addMouseListener(this);
            dspbtc = jpplay.getBackground();

            con.add(panel);
            panel.add(new JScrollPane(jplist));

            panel.add(jpplay);
            panel.add(jpadd);
            panel.add(jpremove);
            panel.add(jphide);
            
            MyPlayer.mp.frame.setEnabled(false);
            setSize(320, 300);
            setLocationRelativeTo(MyPlayer.mp.frame);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setVisible(true);
        }
    }

    public void jpplayAction() {
        if (idex > -1) {
            Play.counter = idex;
            Play.path = Play.paths[Play.counter];
            PlayerDesign.pl.locate(Play.path);
            PlayerDesign.pl.play();
        }
    }

    public void jaddAction() {
        if (jfd == null) jfd = new JFileChooser();
        jfd.setFileFilter(new mp3Filter("Media Files"));
        jfd.setMultiSelectionEnabled(true);
        if (jfd.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            Play.mp3files(jfd.getSelectedFiles());
            dispose();
            display(); // Refresh list
        }
    }

    public void jpremoveAction() {
        if (idex > -1) {
            PlayerDesign.pl.removeSong(idex);
            dispose();
            display();
        }
    }

    public void valueChanged(ListSelectionEvent e) {
        idex = jplist.getSelectedIndex();
    }

    public void actionPerformed(ActionEvent act) {
        Object src = act.getSource();
        if (src == jpplay) {
            jpplayAction();
            MyPlayer.mp.frame.setEnabled(true);
            dispose();
        } else if (src == jpadd) {
            jaddAction();
        } else if (src == jpremove) {
            jpremoveAction();
        } else if (src == jphide) {
            MyPlayer.mp.frame.setEnabled(true);
            dispose();
        }
    }

    public void mouseClicked(MouseEvent arg0) {}
    public void mouseEntered(MouseEvent me) {
        if (me.getSource() instanceof JButton) ((JButton)me.getSource()).setBackground(allbtc);
    }
    public void mouseExited(MouseEvent mx) {
        if (mx.getSource() instanceof JButton) ((JButton)mx.getSource()).setBackground(dspbtc);
    }
    public void mousePressed(MouseEvent mp) {
        if (mp.getSource() instanceof JButton) ((JButton)mp.getSource()).setForeground(allp);
    }
    public void mouseReleased(MouseEvent mr) {
        if (mr.getSource() instanceof JButton) ((JButton)mr.getSource()).setForeground(allr);
    }
}
