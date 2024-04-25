package com.antisciman;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class TumorUtil {
    public static final String ENV_VAL = "DemoPath";
    public static final String ENV_PATH = System.getenv(ENV_VAL);

    public interface Resource {
        String getPath();
        File getFile();
    }

    public enum DemoMessage implements Resource{
        RESULT(STR."\{ENV_PATH}\\output_dcm\\info.txt");

        private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
        private final String path;
        DemoMessage(String path) {
            if (path == null) {
                throw new NullPointerException();
            }
            this.path = path;
        }

        @Override
        public String getPath() {
            return path;
        }

        @Override
        public File getFile() {
            return new File(path);
        }

        @Override
        public String toString() {
            StringBuffer text = new StringBuffer();
            try {
                BufferedReader in = new BufferedReader(new FileReader(getFile()));
                String read;
                while ((read = in.readLine()) != null) {
                    text.append(read);
                    text.append("\n");
                }
            } catch (IOException e) {
                LOGGER.error(e.toString());
                text.append(e);
            }
            return text.toString();
        }
    }

    public enum DemoImage implements Resource{
        PIC_1(STR."\{ENV_PATH}\\demo.jpg"),
        PIC_2(STR."\{ENV_PATH}\\demo.jpg");

        private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
        private final String path;
        DemoImage(String path) {
            if (path == null) {
                throw new NullPointerException();
            }
            this.path = path;
        }

        @Override
        public String getPath() {
            return path;
        }

        @Override
        public File getFile() {
            return new File(path);
        }

        public BufferedImage getImage() {
            BufferedImage img = null;
            try {
                img = ImageIO.read(getFile());
            } catch (IOException e) {
                LOGGER.error(e.toString());
            }
            return img;
        }

    }
}
