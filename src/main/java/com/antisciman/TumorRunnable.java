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
                "Please wait! It will take a few minutes to generate tumors.\n" +
                        "The generation task will begin after confirm.",
                "Confirm",
                JOptionPane.INFORMATION_MESSAGE
        );

        String line = STR."\{ZonedDateTime.now()}\n";   // mark date and time
        StringBuffer stringBuffer = new StringBuffer(line);
        try {
            Process proc = Runtime.getRuntime().exec(new String[]{STR."\{ENV_PATH}\\demo.bat"});
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            File outputFile = new File(STR."\{ENV_PATH}\\output_dcm\\info.txt");

            if (outputFile.createNewFile()) {
                LOGGER.info("Create info.txt");
            }

            BufferedWriter out = new BufferedWriter(new FileWriter(outputFile));
            out.write(line);

            while ((line = in.readLine()) != null) {
                line = STR."\{line}\n";
                stringBuffer.append(line);
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
                "Tumor generation done.",
                "Finish",
                JOptionPane.INFORMATION_MESSAGE
        );

        TumorInfoBox box = new TumorInfoBox(owner);
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
