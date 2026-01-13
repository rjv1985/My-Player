//MAIN CLASS OF MYPLAYER
package Nothing;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.io.File;
import com.formdev.flatlaf.FlatDarkLaf;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;

//BIG BOSS CLASS
public class MyPlayer {

    PlayerDesign frame;
    static MyPlayer mp;
    static int width, height;
    static Window ww[];
    static String imgpath = "/Nothing/Images/";
    static String backimage;

    public void Player(String ss, boolean dc) {
        if (mp.frame == null) {
            System.out.println("NULLPOINTEREXCEPTION");
        } else {
            mp.frame.PlayerDesignMethod(ss, dc);
        }
    }

    public void refresh(String labelname, boolean lb) {
        if (mp.frame == null) {
            System.out.println("NULLPOINTEREXCEPTION");
        }
    }

    public void runSlider() {
        mp.frame.moveSlider();
    }

    public static void main(String[] args) {
        // Initialize JavaFX
        new JFXPanel();

        // Use FlatLaf for a modern look while keeping the same components
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }

        mp = new MyPlayer();

        // Load default theme from resources
        URL defaultTheme = mp.getClass().getResource(imgpath + "car theams/Black and White.JPG");
        if (defaultTheme != null) {
            backimage = defaultTheme.toExternalForm();
        }

        SwingUtilities.invokeLater(() -> {
            mp.frame = new PlayerDesign();
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            width = dim.width;
            height = dim.height;

            mp.frame.DefineComponents();
            mp.frame.AddAllComponents();
            mp.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mp.frame.setSize(806, 420);
            mp.frame.setResizable(false);
            mp.frame.setVisible(true);

            if (defaultTheme != null) {
                mp.frame.PlayerDesignMethod(defaultTheme.toExternalForm(), false);
            }

            // Show welcome window in a separate event to not block initialization
            SwingUtilities.invokeLater(() -> new EmptyBox().openWindow());
        });
    }
}
