package org.axway.grapes.maven.utils;

import org.apache.maven.plugin.MojoExecutionException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * FileUtils
 *
 * <p>Provides generic methods </p>
 */
public class FileUtils {

    /**
     * Serialize a content into a targeted file, checking that the parent directory exists.
     *
     * @param folder File
     * @param content String
     * @param fileName String
     */
    public static void serialize(final File folder, final String content, final String fileName) throws MojoExecutionException {
        if(!folder.exists()){
            folder.mkdirs();
        }

        final File output = new File(folder, fileName);
        FileWriter writer = null;

        try {
            writer = new FileWriter(output);
            writer.write(content);
            writer.flush();
        }
        catch (Exception e){
            throw new MojoExecutionException("Failed to serialize the notification in folder " + folder.getPath(), e);
        }
        finally {
            if(writer != null){
                try {
                    writer.close();
                } catch (IOException e) {
                    throw new MojoExecutionException("Failed to close the open file " + output.getPath(), e);
                }
            }
        }
    }
}
