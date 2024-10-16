package seedu.address.model;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.concert.Concert;
import seedu.address.model.concert.ConcertContact;
import seedu.address.model.concert.UniqueConcertList;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;

/**
 * Wraps all data at the address-book level Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final UniqueConcertList concerts;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid
     * duplication between constructors. See
     * https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html Note that non-static init
     * blocks are not recommended to use. There are other ways to avoid duplication among
     * constructors.
     */
    {
        persons = new UniquePersonList();
        concerts = new UniqueConcertList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}. {@code persons} must not
     * contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    /**
     * Replaces the contents of the concert list with {@code concerts}. {@code concert} ,ust not
     * contain duplicate concerts.
     */
    public void setConcerts(List<Concert> concerts) {
        this.concerts.setConcerts(concerts);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        setConcerts(newData.getConcertList());
    }

    //// concert-level operations
    /**
     * Returns true if a concert with the same identity as {@code concert} exists.
     *
     * @param concert
     * @return
     */
    public boolean hasConcert(Concert concert) {
        requireNonNull(concert);
        return concerts.contains(concert);
    }

    /**
     * Adds a concert.
     *
     * @param concert
     */
    public void addConcert(Concert concert) {
        concerts.add(concert);
    }

    /**
     * Replaces the given concert {@code target} in the list with {@code editedConcert}.
     * {@code target} must exist in the address book. The concert identity of {@code editedConcert}
     * must not be the same as another existing concert in the address book.
     *
     * @param target
     * @param editedConcert
     */
    public void setConcert(Concert target, Concert editedConcert) {
        requireNonNull(editedConcert);

        concerts.setConcert(target, editedConcert);
    }

    public void removePersonFromConcert(Concert concert, Person person) {
        requireAllNonNull(concert, person);

        ConcertContact.removeContactFromConcert(concert, person);
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Adds a person to the address book. The person must not already exist in the address book.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book. The person identity of {@code editedPerson}
     * must not be the same as another existing person in the address book.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}. {@code key} must exist in the address
     * book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("persons", persons).add("concerts", concerts)
                .toString();
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Concert> getConcertList() {
        return concerts.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddressBook)) {
            return false;
        }

        AddressBook otherAddressBook = (AddressBook) other;
        return persons.equals(otherAddressBook.persons)
                 && concerts.equals(otherAddressBook.concerts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(persons.hashCode(), concerts.hashCode());
    }
}
