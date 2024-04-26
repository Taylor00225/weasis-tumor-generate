package com.antisciman;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.time.ZonedDateTime;

import static com.antisciman.TumorUtil.ENV_PATH;

public class TumorRunnable implements Runnable{
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    @Getter
    private static boolean done = true;
    private final Window owner;
    TumorRunnable(Window owner) {
        super();
        this.owner = owner;
    }
    @Override
    public void run() {
        if (!done) {
            LOGGER.error("Process is running");
            return;
        }
        done = false;

        JOptionPane.showMessageDialog(
                SwingUtilities.getWindowAncestor(owner),
                "Please wait! It will take a few minutes to run the project.\n" +
                        "Please confirm.",
                "Confirm",
                JOptionPane.INFORMATION_MESSAGE
        );

        String line = STR."\{ZonedDateTime.now()}\n";   // mark date and time
        String img1_path = STR."\{ENV_PATH}\\NLMCXR\\images";
        String img2_path = STR."\{ENV_PATH}\\NLMCXR\\images";
        try {
            Process proc = Runtime.getRuntime().exec(new String[]{STR."\{ENV_PATH}\\demo.bat"});
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            File outputFile = new File(STR."\{ENV_PATH}\\info.txt");

            if (!outputFile.createNewFile()) {
                outputFile.delete();
                outputFile.createNewFile();
            }
            LOGGER.info("Create info.txt");

            BufferedWriter out = new BufferedWriter(new FileWriter(outputFile));
            out.write(line);
            int i = 0;
            while ((line = in.readLine()) != null) {
                if (i == 0) {
                    img1_path = STR."\{img1_path}\\\{line}.png";
                    i++;
                } else if (i == 1) {
                    img2_path = STR."\{img2_path}\\\{line}.png";
                    i++;
                }
                line = STR."\{line}\n";
                out.write(line);
                LOGGER.info(line);
            }

            in.close();
            out.close();
            proc.waitFor();
        } catch (IOException | InterruptedException e) {
            LOGGER.error(e.toString());
            JOptionPane.showMessageDialog(
                    SwingUtilities.getWindowAncestor(owner),
                    e.toString(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }

        JOptionPane.showMessageDialog(
                SwingUtilities.getWindowAncestor(owner),
                "Done.",
                "Finish",
                JOptionPane.INFORMATION_MESSAGE
        );

        TumorInfoBox box = new TumorInfoBox(owner,
                new TumorUtil.OtherImage(img1_path),
                new TumorUtil.OtherImage(img2_path));
        Rectangle bound =
                GraphicsEnvironment.getLocalGraphicsEnvironment()
                        .getDefaultScreenDevice()
                        .getDefaultConfiguration()
                        .getBounds();
        box.setLocation(
                bound.x + (bound.width - box.getWidth()) / 2,
                bound.y + (bound.height - box.getHeight()) / 2);
        box.setVisible(true);
        done = true;
    }

}
