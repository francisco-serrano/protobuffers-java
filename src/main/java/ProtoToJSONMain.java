import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import example.simple.Simple;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class ProtoToJSONMain {

    public static void main(String[] args) throws InvalidProtocolBufferException {
        System.out.println("hello world");

        Simple.SimpleMessage.Builder builder = Simple.SimpleMessage.newBuilder();

        // simple fields
        builder.setId(42) // set the id field
                .setIsSimple(true) // set the is_simple field
                .setName("My Simple Message Name"); // set the name field

        // repeated field
        builder.addSampleList(1)
                .addSampleList(2)
                .addSampleList(3)
                .addAllSampleList(Arrays.asList(4, 5, 6));

        // print as JSON
        String jsonString = JsonFormat.printer()
//                .includingDefaultValueFields()
                .print(builder);
        System.out.println(jsonString);

        Simple.SimpleMessage.Builder builder2 = Simple.SimpleMessage.newBuilder();

        // parse JSON into ProtoBuf
        JsonFormat.parser()
                .ignoringUnknownFields()
                .merge(jsonString, builder2);

        System.out.println(builder2);

        Simple.SimpleMessage message = builder.build();

        // write the protocol buffers binary to a file
        try (FileOutputStream outputStream = new FileOutputStream("simple_message.bin")) {
            message.writeTo(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // send as byte array
//        byte[] bytes = message.toByteArray();

        System.out.println("reading from file...");
        try (FileInputStream fileInputStream = new FileInputStream("simple_message.bin")) {
            Simple.SimpleMessage messageFromFile = Simple.SimpleMessage.parseFrom(fileInputStream);

            System.out.println(messageFromFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
