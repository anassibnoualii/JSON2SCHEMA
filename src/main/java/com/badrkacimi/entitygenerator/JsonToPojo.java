package com.badrkacimi.entitygenerator;

import com.badrkacimi.entitygenerator.generator.ClassesGenerator;
import com.badrkacimi.entitygenerator.reader.FilesLoader;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonToPojo {



    public static void main(String[] args) throws IOException {
        File directory = new File("./src/main/resources/json/json-files");
        List<File> files = FilesLoader.gettingJsonFiles(directory);

       ClassesGenerator.convertingJsonToPojo(files);

    }







}
