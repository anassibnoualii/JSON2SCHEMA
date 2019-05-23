package com.badrkacimi.entitygenerator.generator;

import com.badrkacimi.entitygenerator.mapper.ToClassesMapper;
import com.badrkacimi.entitygenerator.reader.FilesLoader;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ClassesGenerator {


    private static final File DIRECTORY = new File("./src/main/resources/json/json-files");

    public static void classesGenerator() throws IOException {

        List<File> files = FilesLoader.gettingJsonFiles(DIRECTORY);

        ToClassesMapper.convertingJsonToPojo(files);

    }
}
