import java.io.*;
import java.nio.channels.ScatteringByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileManager {

    public static void write(Serializable data, String fileName) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)))) {
            outputStream.writeObject(data);
        } catch (IOException e) {
            System.out.println("Caught IOException: " + e.getMessage());
        }
    }

    public static Object read(String fileName) throws Exception {
        try (ObjectInputStream inputStream = new ObjectInputStream(Files.newInputStream(Paths.get(fileName)))) {
            Object o = inputStream.readObject();
            inputStream.close();
            return o;
        }
    }
}
