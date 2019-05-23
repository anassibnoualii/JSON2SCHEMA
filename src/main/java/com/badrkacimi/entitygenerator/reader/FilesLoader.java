package com.badrkacimi.entitygenerator.reader;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class FilesLoader {

    private static final String[] EXTENSIONS = new String[]{"json"};
    private static final Logger LOGGER = Logger.getLogger(String.valueOf(FilesLoader.class));

    public static List<File> gettingJsonFiles(File file) throws IOException {
        LOGGER.info("Obtenir tous les json... " + file.getCanonicalPath() + " y compris ceux dans les sous-repertoires");
        return (List<File>) FileUtils.listFiles(file, EXTENSIONS, true);

    }
}
