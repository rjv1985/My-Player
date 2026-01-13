//THIS CLASS IS BASICALLY FOR PLAYING THE SONG
package Nothing;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import javafx.application.Platform;
import java.io.*;
import java.util.*;
import javax.swing.SwingUtilities;

public class Play {

    public static MediaPlayer mediaPlayer;
    final static int NORMAL = 1;
    final static int REPEAT = 2;
    final static int RANDOM = 3;
    static boolean SPLAY = false;
    static boolean SPAUSE = false;
    static boolean SSTOP = false;
    static int sincr = 0;
    static int p = 0;
    static String path;
    static int order = 1;
    static double volume = 0.5;
    static int counter = 0;
    static int count = 0;
    static String names[];
    static Set<String> namesset = new LinkedHashSet<>();
    static Set<String> pathsset = new LinkedHashSet<>();
    static String paths[];
    int min, sec;
    static String showplname = "";
    VideoCaller eb;
    static int auto_count = 0;

    public static void mp3files(File[] files) {
        SSTOP = true;
        SPLAY = false;
        for (int i = 0; i < files.length; i++) {
            if (namesset.add(files[i].getName())) {
                pathsset.add(files[i].getAbsolutePath());
            }
        }
        Play.count = namesset.size();
        paths = pathsset.toArray(new String[0]);
        names = namesset.toArray(new String[0]);
        if (paths.length > 0) {
            path = paths[0];
        }
    }

    void removeSong(int idex) {
        if (idex < 0 || idex >= paths.length)
            return;
        pathsset.remove(paths[idex]);
        namesset.remove(names[idex]);
        paths = pathsset.toArray(new String[0]);
        names = namesset.toArray(new String[0]);
    }

    public void locate(String mp3) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
        }
        try {
            // JavaFX Media needs URI format
            File file = new File(mp3.replace("file:///", ""));
            Media media = new Media(file.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setVolume(volume);

            mediaPlayer.setOnReady(() -> {
                Platform.runLater(() -> {
                    updateUIOnReady();
                    Duration duration = mediaPlayer.getMedia().getDuration();
                    PlayerDesign.setslider = (int) duration.toSeconds();
                    PlayerDesign.jslider.setMaximum((int) duration.toSeconds());
                    MyPlayer.mp.runSlider();
                });
            });

            mediaPlayer.setOnEndOfMedia(() -> {
                Platform.runLater(() -> next());
            });

            mediaPlayer.setOnError(() -> {
                System.out.println("Media error: " + mediaPlayer.getError());
                next();
            });

        } catch (Exception e) {
            System.out.println("Error locating media: " + e.getMessage());
            next();
        }
    }

    private void updateUIOnReady() {
        if (MyPlayer.mp.frame != null) {
            SwingUtilities.invokeLater(() -> {
                PlayerDesign.dvtrackname.setText(names[counter]);
                PlayerDesign.dvmediatime.setText(showTime());
                PlayerDesign.dvplaylistname.setText(showplname);
                PlayerDesign.dvtrackno.setText("" + (counter + 1));

                MyPlayer.mp.frame.PlayerDesignMethod(MyPlayer.backimage, false);
                MyPlayer.mp.frame.panel.repaint();
            });
            SPLAY = true;
            SPAUSE = false;
            SSTOP = false;
        }
    }

    public static void loadFiles(File file) {
        try {
            showplname = file.getName().substring(0, file.getName().lastIndexOf('.'));
            BufferedReader reader = new BufferedReader(new FileReader(file));
            pathsset.clear();
            namesset.clear();
            String line;
            while ((line = reader.readLine()) != null) {
                // Simplified loading logic for demo
                if (line.contains("<") && line.contains(">")) {
                    String data = line.substring(line.indexOf("<") + 1, line.indexOf(">"));
                    if (data.contains("?")) {
                        String songPath = data.substring(0, data.indexOf("?"));
                        String songName = data.substring(data.indexOf("?") + 1);
                        pathsset.add(songPath);
                        namesset.add(songName);
                    }
                }
            }
            reader.close();
            paths = pathsset.toArray(new String[0]);
            names = namesset.toArray(new String[0]);
            count = paths.length;
            if (count > 0) {
                Play.path = paths[0];
                Play.counter = 0;
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public String showTime() {
        if (mediaPlayer == null)
            return "00 : 00";
        Duration d = mediaPlayer.getMedia().getDuration();
        int totalSecs = (int) d.toSeconds();
        int m = totalSecs / 60;
        int s = totalSecs % 60;
        return String.format("%02d : %02d", m, s);
    }

    public void play() {
        SSTOP = false;
        SPLAY = true;
        SPAUSE = false;
        if (mediaPlayer != null) {
            mediaPlayer.play();
            PlayerDesign.jpause.setEnabled(true);
            PlayerDesign.jstop.setEnabled(true);
            PlayerDesign.jplay.setEnabled(false);
        }
    }

    public void stop() {
        SSTOP = true;
        SPLAY = false;
        SPAUSE = false;
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        PlayerDesign.jplay.setEnabled(true);
        PlayerDesign.jpause.setEnabled(false);
        PlayerDesign.jstop.setEnabled(false);
        p = 0;
        sincr = 0;
    }

    public void pause() {
        SPAUSE = true;
        SPLAY = false;
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
        PlayerDesign.jpause.setEnabled(false);
        PlayerDesign.jstop.setEnabled(true);
        PlayerDesign.jplay.setEnabled(true);
    }

    public void next() {
        if (count == 0)
            return;
        switch (order) {
            case NORMAL:
            case REPEAT:
                repeatCounter(1);
                break;
            case RANDOM:
                randomCounter();
                break;
        }
        if (counter < paths.length) {
            path = paths[counter];
            locate("file:///" + path);
            play();
        }
    }

    public void previous() {
        if (count == 0)
            return;
        if (counter == 0)
            counter = paths.length;
        switch (order) {
            case NORMAL:
            case REPEAT:
                repeatCounter(-1);
                break;
            case RANDOM:
                randomCounter();
                break;
        }
        if (counter < paths.length) {
            path = paths[counter];
            locate("file:///" + path);
            play();
        }
    }

    public int getstate() {
        if (mediaPlayer == null)
            return 0;
        MediaPlayer.Status status = mediaPlayer.getStatus();
        if (status == MediaPlayer.Status.PLAYING)
            return 1; // Started
        if (status == MediaPlayer.Status.PAUSED)
            return 2; // Prefetched/Paused
        return 0;
    }

    public void repeatCounter(int i) {
        if (count == 0)
            return;
        counter = (counter + i + count) % count;
    }

    public void randomCounter() {
        if (count == 0)
            return;
        counter = (int) (Math.random() * count);
    }

    public void gain(float ad) {
        volume = ad;
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(volume);
        }
    }
}
