package seedu.doit.logic.parser;

import static seedu.doit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.doit.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.doit.logic.parser.CliSyntax.PREFIX_END;
import static seedu.doit.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.doit.logic.parser.CliSyntax.PREFIX_START;
import static seedu.doit.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import seedu.doit.commons.exceptions.IllegalValueException;
import seedu.doit.logic.commands.Command;
import seedu.doit.logic.commands.EditCommand;
import seedu.doit.logic.commands.EditCommand.EditTaskDescriptor;
import seedu.doit.logic.commands.IncorrectCommand;
import seedu.doit.model.tag.UniqueTagList;

//@@author A0146809W
/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements CommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     */

    @Override
    public Command parse(String args) {
        assert args != null;
        ArgumentTokenizer argsTokenizer =
            new ArgumentTokenizer(PREFIX_PRIORITY, PREFIX_START, PREFIX_END, PREFIX_DESCRIPTION, PREFIX_TAG);
        argsTokenizer.tokenize(args);
        List<Optional<String>> preambleFields = ParserUtil.splitPreamble(argsTokenizer.getPreamble().orElse(""), 2);

        Optional<Integer> index = preambleFields.get(0).flatMap(ParserUtil::parseIndex);
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        EditTaskDescriptor editTaskDescriptor = new EditTaskDescriptor();

        try {
            editTaskDescriptor.setName(ParserUtil.parseName(preambleFields.get(1)));
            editTaskDescriptor.setPriority(ParserUtil.parsePriority(argsTokenizer.getValue(PREFIX_PRIORITY)));
            editTaskDescriptor.setStartTime(ParserUtil.parseStartTime(argsTokenizer.getValue(PREFIX_START)));
            editTaskDescriptor.setDeadline(ParserUtil.parseDeadline(argsTokenizer.getValue(PREFIX_END)));
            editTaskDescriptor.setDescription(ParserUtil.parseDescription(argsTokenizer.getValue(PREFIX_DESCRIPTION)));
            editTaskDescriptor.setTags(parseTagsForEdit(ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG))));

            if (!editTaskDescriptor.isAnyFieldEdited()) {
                return new IncorrectCommand(EditCommand.MESSAGE_NOT_EDITED);
            }

            return new EditCommand(index.get(), editTaskDescriptor);

        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }


    }

    /**
     * Parses {@code Collection<String> tags} into an {@code Optional<UniqueTagList>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Optional<UniqueTagList>} containing zero tags.
     */
    private Optional<UniqueTagList> parseTagsForEdit(Collection<String> tags) throws IllegalValueException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}
