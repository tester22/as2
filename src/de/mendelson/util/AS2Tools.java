//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/util/AS2Tools.java,v 1.1 2015/01/06 11:07:51 heller Exp $
package de.mendelson.util;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software. Other product
 * and brand names are trademarks of their respective owners.
 */
/**
 * Some programming tools for mendelson business integration
 *
 * @author S.Heller
 * @version $Revision: 1.1 $
 */
public class AS2Tools {

    /**
     * Replaces the string tag by the string replacement in the sourceString
     *
     * @param source Source string
     * @param tag	String that will be replaced
     * @param replacement String that will replace the tag
     * @return String that contains the replaced values
     */
    public static String replace(String source, String tag, String replacement) {
        if (source == null) {
            return null;
        }
        StringBuilder buffer = new StringBuilder();
        while (true) {
            int index = source.indexOf(tag);
            if (index == -1) {
                buffer.append(source);
                return (buffer.toString());
            }
            buffer.append(source.substring(0, index));
            buffer.append(replacement);
            source = source.substring(index + tag.length());
        }
    }

    /**
     * Creates a temp file in a data stamped folder below the directory temp
     */
    public static synchronized File createTempFile(String prefix, String suffix) throws IOException {
        DateFormat dateformat = new SimpleDateFormat("yyyyMMdd");
        String tempDirStr = new File("temp").getAbsolutePath();
        File targetDir = new File(tempDirStr + File.separator + dateformat.format(new Date()));
        if (!targetDir.exists()) {
            boolean created = targetDir.mkdirs();
            if (!created) {
                throw new IOException("Unable to create directory " + targetDir.getAbsolutePath());
            }
        }
        //create a unique file in the temp subdirectory
        File tempFile = File.createTempFile(prefix, suffix, targetDir);
        return (tempFile);
    }

    /**
     * Creates a temp file in a data stamped folder below the directory temp
     */
    public static synchronized void deleteTempFilesOlderThan(int numberOfDays) {
        //find out all available temp directories
        String tempDirStr = new File("temp").getAbsolutePath();
        File targetDir = new File(tempDirStr);
        long cutoff = TimeUnit.DAYS.toMillis(numberOfDays);
        File[] subdirList = targetDir.listFiles();
        if (subdirList != null) {
            for (File subdir : subdirList) {
                if (subdir.isDirectory() && !subdir.getName().startsWith(".")) {
                    long diff = System.currentTimeMillis() - subdir.lastModified();
                    if (diff > cutoff) {
                        try {
                            FileUtils.deleteDirectory(subdir);
                        } catch (Exception e) {
                            //nop
                        }
                    }
                }
            }
        }
    }

    /**
     * Displays the passed data size in a proper format
     */
    public static String getDataSizeDisplay(long size) {
        StringBuilder builder = new StringBuilder();
        Formatter formatter = new Formatter(builder);
        if (size > 1.048E6) {
            formatter.format(Locale.getDefault(), "%.2f", Float.valueOf((float) size / (float) 1.048E6));
            builder.append(" ").append("MB");
            return (builder.toString());
        } else if (size > 1024L) {
            formatter.format(Locale.getDefault(), "%.2f", Float.valueOf((float) size / 1024f));
            builder.append(" ").append("KB");
            return (builder.toString());
        }
        return (String.valueOf(size) + " Byte");
    }

    /**
     * Displays a duration
     */
    public static String getTimeDisplay(long duration) {
        NumberFormat formatter = new DecimalFormat("0.00");
        if (duration < 1000) {
            return (duration + "ms");
        }
        float timeInSecs = (float) ((float) duration / 1000f);
        return (formatter.format(timeInSecs) + "s");
    }
}
