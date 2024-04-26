package com.antisciman;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class TumorInfoBox extends JDialog {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    TumorInfoBox(Window owner, TumorUtil.ImageResource img1, TumorUtil.ImageResource img2) {
        super(owner, "Result");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setModal(true);

        JPanel rootPanel = new JPanel(new BorderLayout());

        JPanel jPanel = new JPanel();
        BorderLayout borderLayout = new BorderLayout();
        jPanel.setLayout(borderLayout);

        JPanel jPanel1 = new JPanel();
        JLabel jLabel1 = new JLabel();  // pic1
        JPanel jPanel2 = new JPanel();
        JLabel jLabel2 = new JLabel();  // pic2
        int width = 350, height = 350;
        BufferedImage bufImg1 = img1.getImage();
        BufferedImage bufImg2 = img2.getImage();
        if (bufImg1 != null) {
            jLabel1.setIcon(new ImageIcon(bufImg1.getScaledInstance(
                    width, height, Image.SCALE_SMOOTH)));
        } else {
            String ms1 = STR."Can not find \{img1.getPath()}";
            LOGGER.warn(ms1);
            jLabel1.setText(ms1);
        }
        if (bufImg2 != null) {
            jLabel2.setIcon(new ImageIcon(bufImg2.getScaledInstance(
                    width, height, Image.SCALE_SMOOTH)));
        } else {
            String ms2 = STR."Can not find \{img2.getPath()}";
            LOGGER.warn(ms2);
            jLabel2.setText(ms2);
        }
        jPanel1.add(jLabel1);
        jPanel2.add(jLabel2);
        jPanel.add(jPanel1, BorderLayout.WEST);
        jPanel.add(jPanel2, BorderLayout.EAST);

        JPanel jPanel3 = new JPanel();
        JScrollPane jScrollPane = new JScrollPane();
        JTextArea jTextArea = new JTextArea();
        jTextArea.setEditable(false);
        String message = TumorUtil.DemoMessage.RESULT.toString();
        jTextArea.setText(message);
        jScrollPane.setPreferredSize(new Dimension(2*width + 1, height));
        jScrollPane.getViewport().add(jTextArea);
        jPanel3.add(jScrollPane);
        jPanel.add(jPanel3, BorderLayout.SOUTH);

        rootPanel.add(jPanel, BorderLayout.CENTER);

        getContentPane().add(jPanel);
        pack();
    }
}
