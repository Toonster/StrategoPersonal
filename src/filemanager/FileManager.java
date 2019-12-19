package filemanager;

import java.io.*;
import java.nio.channels.ScatteringByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class FileManager {

    public static File createFile(String fileName) {
        File file = new File(fileName);
        try {
            if (file.createNewFile()) {
                System.out.println("File created");
            } else {
                System.out.println("File already exists");
            }
        } catch (IOException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
        return file;
    }

    public static void write(Serializable data, String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName));
            outputStream.writeObject(data);
            outputStream.close();
            System.out.println("Saved!");
        } catch (IOException e) {
            System.out.println("Caught IOException: " + e.getMessage());
        }
    }

    public static Object read(String fileName) throws Exception {
        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName));
        Object o = inputStream.readObject();
        inputStream.close();
        return o;
    }

}
