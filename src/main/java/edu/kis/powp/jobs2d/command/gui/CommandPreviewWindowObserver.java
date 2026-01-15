package edu.kis.powp.jobs2d.command.gui;

import edu.kis.powp.jobs2d.command.manager.CommandManager;
import edu.kis.powp.observer.Subscriber;

public class CommandPreviewWindowObserver implements Subscriber {

    private CommandPreviewWindow previewWindow;
    private CommandManager commandManager;

    public CommandPreviewWindowObserver(CommandPreviewWindow previewWindow, CommandManager commandManager) {
        this.previewWindow = previewWindow;
        this.commandManager = commandManager;
    }

    @Override
    public void update() {
        previewWindow.updatePreview(commandManager.getCurrentCommand());
    }

    @Override
    public String toString() {
        return "Command Preview Observer";
    }
}