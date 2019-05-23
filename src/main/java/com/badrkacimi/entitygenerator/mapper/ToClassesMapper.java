package com.badrkacimi.entitygenerator.mapper;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.logging.Logger;

public class ToClassesMapper {

    private static final Logger LOGGER = Logger.getLogger(String.valueOf(ToClassesMapper.class));


    public static void convertingJsonToPojo( List<File> files) throws MalformedURLException {
        int i =0;
        for (File file : files) {
            URL inputjson = file.toURI().toURL();
            String className = file.getName().replace(".json", "");

            try {
                ToClassMapper.fromJsonFileToJavaClass(inputjson, className);
                i++;


            } catch (Exception e) {
                LOGGER.info("Encountered issue while converting to pojo: " + e.getMessage());
            }

        }
        LOGGER.info("Number of POJOs converted & created: " + " " + (i));
    }
}
