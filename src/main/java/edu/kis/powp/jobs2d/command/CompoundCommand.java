package edu.kis.powp.jobs2d.command;

import edu.kis.powp.jobs2d.Job2dDriver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

//Implementation of ICompoundCommand that allows executing multiple commands in sequence.
public class CompoundCommand implements ICompoundCommand {

    private final List<DriverCommand> commands;

    //Private constructor to ensure controlled creation.
    private CompoundCommand(List<DriverCommand> commands) {
        this.commands = Collections.unmodifiableList(new ArrayList<>(commands));
    }

    @Override
    public void execute(Job2dDriver driver) {
        for (DriverCommand command : commands) {
            command.execute(driver);
        }
    }

    @Override
    public Iterator<DriverCommand> iterator() {
        return commands.iterator();
    }


    public static CompoundCommand fromListOfCommands(List<DriverCommand> commands) {
        return new CompoundCommand(commands);
    }

    public CompoundCommand append(List<DriverCommand> commands) {
        List<DriverCommand> newCommands = new ArrayList<>(this.commands);
        newCommands.addAll(commands);
        return new CompoundCommand(newCommands);
    }

    public int size() {
        return commands.size();
    }

    public boolean isEmpty() {
        return commands.isEmpty();
    }

    public static class Builder {
        private final List<DriverCommand> commands = new ArrayList<>();

        public Builder add(List<DriverCommand> commands) {
            this.commands.addAll(commands);
            return this;
        }

        public Builder add(CompoundCommand compoundCommand) {
            this.commands.addAll(compoundCommand.commands);
            return this;
        }

        public Builder addSetPosition(int x, int y) {
            this.commands.add(new SetPositionCommand(x, y));
            return this;
        }

        public Builder addOperateTo(int x, int y) {
            this.commands.add(new OperateToCommand(x, y));
            return this;
        }

        public CompoundCommand build() {
            return new CompoundCommand(commands);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}