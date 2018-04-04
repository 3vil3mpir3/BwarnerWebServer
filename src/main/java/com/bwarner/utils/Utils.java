package com.bwarner.utils;

import javax.activation.MimetypesFileTypeMap;
import java.io.*;
import java.util.StringTokenizer;

public final class Utils {

    public static File getFile(BufferedReader reader, String dir) throws IOException{
        return new File(dir+getFilename(reader));
    }

    private static String getFilename (BufferedReader reader) throws IOException{
        String requeststring = reader.readLine();
        StringTokenizer tokens = new StringTokenizer(requeststring);
        tokens.nextToken();
        return tokens.nextToken();
    }

    public static String getMimeType(final File file)
    {
        final MimetypesFileTypeMap typemap = new MimetypesFileTypeMap();
        return typemap.getContentType(file);
    }

    public static byte[] getFileBytes(File file) throws IOException {
        int length = (int) file.length();
        byte[] array = new byte[length];
        InputStream in = new FileInputStream(file);
        int offset = 0;
        while (offset < length) {
            int count = in.read(array, offset, (length - offset));
            offset += count;
        }
        in.close();
        return array;
    }

    private static void bufferBytes(FileInputStream fis, OutputStream os) throws Exception
    {
        byte[] buffer = new byte[1024];
        int bytes = 0;
        while((bytes = fis.read(buffer)) != -1 )
        {
            os.write(buffer, 0, bytes);
        }
    }
}
