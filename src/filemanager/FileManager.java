package filemanager;

import java.io.*;

public class FileManager {

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
