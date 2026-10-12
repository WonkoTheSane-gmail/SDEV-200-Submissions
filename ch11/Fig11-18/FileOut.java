// FileName FileOut.java
// Written by: Jonathon Meyer
// Written on: 10/05/2026

import java.nio.file.*;
import java.io.*;
import static java.nio.file.StandardOpenOption.*;

public class FileOut
{
    public static void main(String[] args)
    {
        Path file = Paths.get("name.txt");
        String s = "Jonathon Meyer";
        byte[] data = s.getBytes();
        OutputStream output = null;
        try
        {
            output = new BufferedOutputStream(Files.newOutputStream(file, CREATE));
            output.write(data);
            output.flush();
            output.close();
        }
        catch (Exception e)
        {
            System.out.println("Message: " + e);
        }
    }
}