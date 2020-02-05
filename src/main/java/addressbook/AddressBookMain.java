package addressbook;

import com.example.exercise.AddressBookProtos.*;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Timestamp;
import com.google.protobuf.util.JsonFormat;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.Arrays;
import java.util.Optional;

public class AddressBookMain {
    public static void main(String[] args) {
        System.out.println("address book example");

        AddressBook addressBook = newAddressBook(Arrays.asList(
                newPerson("Tommy Vercetti", 1, "tommy.vercetti@gmail", Arrays.asList(
                        newPhoneNumber("123-456-7890", Person.PhoneType.HOME),
                        newPhoneNumber("098-765-4321", Person.PhoneType.MOBILE)
                ), currentTimestamp()),
                newPerson("Carl Johnson", 2, "carl.johnson@gmail", Arrays.asList(
                        newPhoneNumber("123-456-7890", Person.PhoneType.HOME),
                        newPhoneNumber("098-765-4321", Person.PhoneType.MOBILE)
                ), currentTimestamp()),
                newPerson("Niko Bellic", 3, "niko.bellic@gmail", Arrays.asList(
                        newPhoneNumber("249-412-3456", Person.PhoneType.HOME),
                        newPhoneNumber("249-456-7890", Person.PhoneType.MOBILE)
                ), currentTimestamp())
        ));

        writeAddressBook(addressBook, "address-book.bin");

        Optional<AddressBook> addressBookOptional = readAddressBook("address-book.bin");

        addressBookOptional.ifPresent(book -> System.out.println(getAsJson(book)));
    }

    public static AddressBook newAddressBook(Iterable<Person> people) {
        return AddressBook.newBuilder()
                .addAllPeople(people)
                .build();
    }

    public static Person newPerson(String name, int id, String email, Iterable<Person.PhoneNumber> phoneNumbers, Timestamp timestamp) {
        return Person.newBuilder()
                .setName(name)
                .setId(id)
                .setEmail(email)
                .addAllPhones(phoneNumbers)
                .setLastUpdated(timestamp)
                .build();
    }

    public static Person.PhoneNumber newPhoneNumber(String number, Person.PhoneType phoneType) {
        return Person.PhoneNumber.newBuilder()
                .setNumber(number)
                .setType(phoneType)
                .build();
    }

    public static Timestamp currentTimestamp() {
        Instant now = Instant.now();

        return Timestamp.newBuilder()
                .setSeconds(now.getEpochSecond())
                .setNanos(now.getNano())
                .build();
    }

    public static Optional<AddressBook> readAddressBook(String path) {
        try (FileInputStream inputStream = new FileInputStream(path)) {
            return Optional.of(AddressBook.parseFrom(inputStream));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public static void writeAddressBook(AddressBook addressBook, String path) {
        try (FileOutputStream outputStream = new FileOutputStream(path)) {
            addressBook.writeTo(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Optional<String> getAsJson(AddressBook addressBook) {
        try {
            return Optional.of(JsonFormat.printer().print(addressBook));
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }
}
