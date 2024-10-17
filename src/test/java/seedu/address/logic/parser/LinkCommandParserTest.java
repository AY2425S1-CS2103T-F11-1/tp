package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONCERT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CONCERT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.LinkCommand;

public class LinkCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, LinkCommand.MESSAGE_USAGE);

    private LinkCommandParser parser = new LinkCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, "" + PREFIX_CONCERT + INDEX_FIRST_CONCERT, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5 " + PREFIX_CONCERT + INDEX_FIRST_CONCERT,
                MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0 " + PREFIX_CONCERT + INDEX_FIRST_CONCERT,
                MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidConcertInput_failure() {
        // negative index
        assertParseFailure(parser, INDEX_FIRST_PERSON + " " + PREFIX_CONCERT + "-3",
                MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, INDEX_FIRST_PERSON + " " + PREFIX_CONCERT + "0",
                MESSAGE_INVALID_FORMAT);

        // no input
        assertParseFailure(parser, INDEX_FIRST_PERSON + " " + PREFIX_CONCERT,
                MESSAGE_INVALID_FORMAT);

        // not integer
        assertParseFailure(parser, INDEX_FIRST_PERSON + " " + PREFIX_CONCERT + "a",
                MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_repeatedPrefix_failure() {
        String invalidString = INDEX_FIRST_PERSON + " " + PREFIX_CONCERT + INDEX_FIRST_CONCERT
                + " " + PREFIX_CONCERT + INDEX_SECOND_PERSON;

        assertParseFailure(parser, invalidString, Messages
                .getErrorMessageForDuplicatePrefixes(PREFIX_CONCERT));
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        Index concertIndex = INDEX_FIRST_CONCERT;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_CONCERT + concertIndex.getOneBased();

        LinkCommand expectedCommand = new LinkCommand(targetIndex, concertIndex);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
