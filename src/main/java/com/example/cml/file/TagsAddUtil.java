package com.example.cml.file;

import java.util.HashMap;
import java.util.Map;

/**
 * The  Tags add utils.
 */
public class TagsAddUtil {

    private static Map<String, String> fileExtMap;

    static {
        fileExtMap = new HashMap<>();
        fileExtMap.put(".mp3", "audio");
        fileExtMap.put(".wav", "audio");
        fileExtMap.put(".avi", "video");
        fileExtMap.put(".mpeg", "video");
        fileExtMap.put(".doc", "document");
        fileExtMap.put(".txt", "document");
        fileExtMap.put(".png", "image");
        fileExtMap.put(".jpeg", "image");
    }

    /**
     * Gets extension tag.
     *
     * @param fileName the file name
     * @return the extension tag
     */
    protected static String getExtensionTag(String fileName) {
        for (Map.Entry<String, String> entry : fileExtMap.entrySet()) {
            if (fileName.endsWith(entry.getKey())) {
                return entry.getValue();
            }
        }
        return null;
    }
}
