package edu.kis.powp.jobs2d.command.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import edu.kis.powp.jobs2d.command.DriverCommand;

/**
 * Maintains a history of commands that have been set as current.
 * 
 * Design Pattern: Memento Pattern - stores command snapshots without exposing
 * CommandManager's internal structure.
 * 
 * Provides thread-safe storage with ability to retrieve commands by index,
 * view the entire history, or clear it.
 */
public class CommandHistory {
    
    private final List<DriverCommand> history = new ArrayList<>();
    
    /**
     * Adds a command to the history.
     * Duplicate consecutive null entries are prevented.
     */
    public synchronized void addCommand(DriverCommand command) {
        // Avoid adding consecutive nulls
        if (command == null && !history.isEmpty() && history.get(history.size() - 1) == null) {
            return;
        }
        history.add(command);
    }
    
    /**
     * Returns an immutable copy of the command history.
     */
    public synchronized List<DriverCommand> getHistory() {
        return Collections.unmodifiableList(new ArrayList<>(history));
    }
    
    /**
     * Returns the number of commands in history.
     * 
     * @return the size of the history
     */
    public synchronized int size() {
        return history.size();
    }
    
    /**
     * Retrieves a command at a specific index in history.
     */
    public synchronized DriverCommand getCommand(int index) {
        return history.get(index);
    }
    
    /**
     * Clears all command history.
     */
    public synchronized void clear() {
        history.clear();
    }
    
    /**
     * Returns whether the history is empty.
     */
    public synchronized boolean isEmpty() {
        return history.isEmpty();
    }
}
