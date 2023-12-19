package me.gamenu.carbon.logic.compile;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.zip.*;

public class GZipUtils {
    private static final Charset charset = StandardCharsets.UTF_8;
    public static byte[] compress(String string) throws Exception{
        if (string == null){
            throw new NullPointerException("Received null as input");
        }

        if (string.isEmpty()){
            return string.getBytes(charset);
        }

        byte[] byteArr = stringToBytes(string);


        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        GZIPOutputStream gos = new GZIPOutputStream(byteStream);
        gos.write(byteArr);
        gos.finish();

        return Base64.getEncoder().encode(byteStream.toByteArray());
    }


    public static String decompress(byte[] bytes) throws IOException {
        if (bytes == null) {
            throw new NullPointerException("Received null as input");
        }

        if (bytes.length == 0) {
            return "";
        }

        ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
        GZIPInputStream gis = new GZIPInputStream(byteStream);

        byte[] decoded = Base64.getDecoder().decode(gis.readAllBytes());

        return bytesToString(decoded);
    }

    public static String bytesToString(byte[] bytes){
        return new String(bytes, charset);
    }

    public static byte[] stringToBytes(String string){
        return string.getBytes(charset);
    }
}
