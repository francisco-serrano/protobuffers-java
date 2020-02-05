import example.complex.Complex.*;

import java.util.Arrays;

public class ComplexMain {

    public static void main(String[] args) {
        System.out.println("Complex example");

        DummyMessage oneDummy = newDummyMessage(55, "one dummy message");

        ComplexMessage.Builder builder = ComplexMessage.newBuilder();

        builder.setOneDummy(oneDummy);

        builder.addMultipleDummy(newDummyMessage(66, "second dummy"));
        builder.addMultipleDummy(newDummyMessage(67, "third dummy"));
        builder.addMultipleDummy(newDummyMessage(68, "fourth dummy"));

        builder.addAllMultipleDummy(Arrays.asList(
                newDummyMessage(69, "other dummy"),
                newDummyMessage(70, "other other dummy")
        ));

        ComplexMessage message = builder.build();

        System.out.println(message);

        // get example
//        message.getMultipleDummyList();
    }

    public static DummyMessage newDummyMessage(int id, String name) {
        DummyMessage.Builder dummyMessageBuilder = DummyMessage.newBuilder();

        return dummyMessageBuilder.setName(name)
                .setId(id)
                .build();
    }
}
