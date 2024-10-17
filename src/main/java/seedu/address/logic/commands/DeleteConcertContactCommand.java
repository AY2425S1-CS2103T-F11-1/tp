package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CONCERT;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.concert.Concert;
import seedu.address.model.person.Person;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteConcertContactCommand extends Command {

    public static final String COMMAND_WORD = "deletecc";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the index number used in the displayed person list.\n"
            + "Parameters: PERSONINDEX (must be a positive integer)"
            + "[" + PREFIX_CONCERT + "CONCERTINDEX] "
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_CONCERT + "2";

    public static final String MESSAGE_DELETE_PERSON_FROM_CONCERT_SUCCESS = "Deleted Person: %1$s from Concert: %1$s";

    private final Index concertIndex;
    private final Index personIndex;


    public DeleteConcertContactCommand(Index concertIndex, Index personIndex) {
        this.concertIndex = concertIndex;
        this.personIndex = personIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownPersonList = model.getFilteredPersonList();
        List<Concert> lastShownConcertList = model.getFilteredConcertList();

        if (personIndex.getZeroBased() >= lastShownPersonList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        if (concertIndex.getZeroBased() >= lastShownConcertList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CONCERT_DISPLAYED_INDEX);
        }

        Concert concertToEdit = lastShownConcertList.get(concertIndex.getZeroBased());
        Person personToDelete = lastShownPersonList.get(personIndex.getZeroBased());


        model.deletePersonFromConcert(concertToEdit, personToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_FROM_CONCERT_SUCCESS,
                Messages.format(personToDelete), Messages.format(concertToEdit)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteConcertContactCommand)) {
            return false;
        }

        DeleteConcertContactCommand otherDeleteConcertContactCommand = (DeleteConcertContactCommand) other;
        boolean sameConcertIndex = concertIndex.equals(otherDeleteConcertContactCommand.concertIndex);
        boolean samePersonIndex = personIndex.equals(otherDeleteConcertContactCommand.personIndex);
        return samePersonIndex && sameConcertIndex;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("concertIndex", concertIndex)
                .add("personIndex", personIndex)
                .toString();
    }
}
