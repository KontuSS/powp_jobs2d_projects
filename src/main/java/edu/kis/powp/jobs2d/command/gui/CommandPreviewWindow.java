package edu.kis.powp.jobs2d.command.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;

import edu.kis.legacy.drawer.panel.DrawPanelController;
import edu.kis.legacy.drawer.shape.LineFactory;
import edu.kis.powp.appbase.gui.WindowComponent;
import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.drivers.adapter.LineDriverAdapter;

public class CommandPreviewWindow extends JFrame implements WindowComponent {
    
    private static final long serialVersionUID = 1L;
    
    private final JPanel previewPanel; 
    private final DrawPanelController drawPanelController;

    public CommandPreviewWindow() {
        this.setTitle("Command Preview");
        this.setSize(400, 400);
        
        Container content = this.getContentPane();
        content.setLayout(new BorderLayout());

        this.previewPanel = new JPanel();
        this.previewPanel.setLayout(new BorderLayout());
        
        this.previewPanel.setBackground(Color.WHITE);
        this.previewPanel.setOpaque(true);

        content.add(previewPanel, BorderLayout.CENTER);

        this.drawPanelController = new DrawPanelController();
        this.drawPanelController.initialize(previewPanel);
    }

    public void updatePreview(DriverCommand command) {
        
        drawPanelController.clearPanel();
        
        if (command != null) {
            LineDriverAdapter driver = new LineDriverAdapter(drawPanelController, LineFactory.getBasicLine(), "preview");
            command.execute(driver);
        }
        
        previewPanel.revalidate();
        previewPanel.repaint();
    }

    @Override
    public void HideIfVisibleAndShowIfHidden() {
        if (this.isVisible()) {
            this.setVisible(false);
        } else {
            this.setVisible(true);
        }
    }
}