package Nothing;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.io.*;
import java.net.URL;
import java.awt.*;
import java.awt.event.*;

public class SelectTheams extends JDialog
        implements ActionListener, MouseListener, Runnable, ListSelectionListener, FileFilter {

    private static final long serialVersionUID = 1411715968437988341L;
    JButton slapply, slhide;
    JList<String> sllist;
    String[] imagename;
    int idex = -1;
    static int itime = 0;
    Color allbtc = Color.cyan, allp = Color.red, allr = Color.black;

    public SelectTheams() {
        super(MyPlayer.mp.frame, true);
        getContentPane().setBackground(Color.darkGray);
        getContentPane().setLayout(new FlowLayout());
    }

    public void select() {
        if (imagename == null) {
            loadTheam();
        }
        sllist = new JList<>(imagename);
        sllist.addListSelectionListener(this);
        sllist.setVisibleRowCount(10);
        sllist.setFixedCellHeight(20);
        sllist.setFixedCellWidth(280);

        slapply = new JButton("Apply");
        slhide = new JButton("Hide");

        slapply.addActionListener(this);
        slhide.addActionListener(this);

        add(new JScrollPane(sllist));
        add(slapply);
        add(slhide);

        setSize(320, 300);
        setLocationRelativeTo(MyPlayer.mp.frame);
        setVisible(true);
    }

    public void loadTheam() {
        imagename = new String[] {
                "Black and White", "Cool Cyan", "Golden Globe", "Great Meta",
                "Green and Clean", "Hard Pink", "Magic Blue", "Old Yellow",
                "Perpal", "Red Hot", "Royal Blue"
        };
    }

    public void editSongList() {
    }

    public void playerSleep() {
    }

    public void playerSystemSleep() {
    }

    @Override
    public boolean accept(File f) {
        return f.getName().toLowerCase().endsWith(".jpg");
    }

    public void actionPerformed(ActionEvent ae) {
        Object src = ae.getSource();
        if (src == slapply) {
            if (idex > -1) {
                String selected = imagename[idex];
                String path = MyPlayer.imgpath + "car theams/" + selected + ".JPG";
                URL url = getClass().getResource(path);
                if (url != null) {
                    MyPlayer.backimage = url.toExternalForm();
                    MyPlayer.mp.frame.PlayerDesignMethod(url.toExternalForm(), false);
                }
            }
            dispose();
        } else if (src == slhide) {
            dispose();
        }
    }

    public void valueChanged(ListSelectionEvent e) {
        if (e.getSource() == sllist)
            idex = sllist.getSelectedIndex();
    }

    public void run() {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }
}