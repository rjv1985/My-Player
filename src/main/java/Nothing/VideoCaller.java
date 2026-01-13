package Nothing;

import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.MediaView;
import javafx.application.Platform;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VideoCaller extends JFrame implements WindowListener, MouseListener {

    public boolean check_click = false;
    private JFXPanel jfxPanel;
    private MediaView mediaView;

    public VideoCaller(String filename) {
        super(filename);
        jfxPanel = new JFXPanel();
        setLayout(new BorderLayout());
        add(jfxPanel, BorderLayout.CENTER);
    }

    public void create() {
        setLocation((MyPlayer.width / 2) - 300, (MyPlayer.height / 2) - 165);
        setSize(600, 330);
        setVisible(true);
        addWindowListener(this);
    }

    public void videotry() {
        Platform.runLater(() -> {
            if (Play.mediaPlayer != null) {
                mediaView = new MediaView(Play.mediaPlayer);
                BorderPane root = new BorderPane();
                root.setCenter(mediaView);

                // Keep video proportional
                mediaView.setPreserveRatio(true);
                mediaView.fitWidthProperty().bind(root.widthProperty());
                mediaView.fitHeightProperty().bind(root.heightProperty());

                Scene scene = new Scene(root, javafx.scene.paint.Color.BLACK);
                jfxPanel.setScene(scene);
            }
        });
        jfxPanel.addMouseListener(this);
    }

    public void windowActivated(WindowEvent arg0) {
    }

    public void windowClosed(WindowEvent arg0) {
    }

    public void windowClosing(WindowEvent arg0) {
        if (Play.mediaPlayer != null)
            Play.mediaPlayer.stop();
    }

    public void windowDeactivated(WindowEvent arg0) {
    }

    public void windowDeiconified(WindowEvent arg0) {
    }

    public void windowIconified(WindowEvent arg0) {
    }

    public void windowOpened(WindowEvent arg0) {
    }

    public void mouseClicked(MouseEvent click) {
        int clicks = click.getClickCount();
        if (clicks == 1) {
            if (check_click == false) {
                check_click = true;
                PlayerDesign.pl.pause();
            } else {
                check_click = false;
                PlayerDesign.pl.play();
            }
        }
    }

    public void mouseEntered(MouseEvent arg0) {
    }

    public void mouseExited(MouseEvent arg0) {
    }

    public void mousePressed(MouseEvent arg0) {
    }

    public void mouseReleased(MouseEvent arg0) {
    }
}
