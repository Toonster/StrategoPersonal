import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.channels.ScatteringByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileManager {

    public static void write(Serializable data, String fileName) throws Exception {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)))){
            outputStream.writeObject(data);
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
