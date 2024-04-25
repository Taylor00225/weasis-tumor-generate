package com.antisciman;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.weasis.core.ui.util.WtoolBar;

import javax.swing.*;
import java.awt.event.ActionListener;

import static com.antisciman.TumorUtil.ENV_PATH;

public class TumorToolBar extends WtoolBar {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    @Getter
    private final JButton startButton = new JButton();

    protected TumorToolBar() {
        super("Tumor Toolbar", 400);

        startButton.setToolTipText("Tumor");
        startButton.putClientProperty("JButton.buttonType", "help");
        ActionListener startAction = _ -> {
            if (ENV_PATH == null) {
                LOGGER.error("DemoPath doesnt exit! Please check out the env vars");
                return;
            }
            if (!TumorRunnable.isDone()) {
                String m = "Generating tumors, please wait!";
                JOptionPane.showMessageDialog(
                        SwingUtilities.getWindowAncestor(this), m, "Error", JOptionPane.ERROR_MESSAGE
                );
                LOGGER.error(m);
                return;
            }
            TumorRunnable tumorRunnable = new TumorRunnable(SwingUtilities.getWindowAncestor(this));
            new Thread(tumorRunnable).start();
        };

        startButton.addActionListener(startAction);
        add(startButton);
    }

}
