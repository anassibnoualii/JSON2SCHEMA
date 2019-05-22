package com.badrkacimi.entity;

import com.sun.codemodel.JCodeModel;
import org.apache.commons.io.FileUtils;
import org.jsonschema2pojo.*;
import org.jsonschema2pojo.rules.RuleFactory;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class JsonToPojo {

    // output package
    private static final String PACKAGE_NAME = "com.badrkacimi.pojogen";
    private static final File OUTPUT_POJO_DIRECTORY = new File("./src/main/java");
    private static final int INITIAL_INDEX = 0;

    public static void main(String[] args) throws IOException {

        List<File> files = gettingJsonFiles();

        convertingJsonToPojo(PACKAGE_NAME, OUTPUT_POJO_DIRECTORY, files, INITIAL_INDEX);

    }

    private static void convertingJsonToPojo(String packageName, File outputPojoDirectory, List<File> files, int i) throws MalformedURLException {
        for (File file : files) {
            URL inputjson = file.toURI().toURL();
            String className = file.getName();

            try {
                convertToJson(inputjson, outputPojoDirectory, packageName, className.replace(".json", ""));
                i++;


            } catch (Exception e) {
                System.out.println("Encountered issue while converting to pojo: " + e.getMessage());
                e.printStackTrace();
            }

        }
        System.out.println("Nombre de POJOs converted & created: " + " " + (i - 1));
    }

    private static List<File> gettingJsonFiles() throws IOException {
        File dir = new File("./src/main/resources/json/json-files");// input
        String[] extensions = new String[]{"json"};
        System.out.println("Obtenir tous les json... " + dir.getCanonicalPath() + " y compris ceux dans les sous-repertoires");
        List<File> files = (List<File>) FileUtils.listFiles(dir, extensions, true);
        return files;
    }


    private static void convertToJson(URL inputJson, File outputPojoDirectory, String packageName, String className) throws IOException {
        JCodeModel codeModel = new JCodeModel();
        URL source = inputJson;
        GenerationConfig config = new DefaultGenerationConfig() {
            @Override
            public boolean isGenerateBuilders() { // set config option by overriding method
                return true;
            }

            public SourceType getSourceType() {
                return SourceType.JSON;
            }
        };
        SchemaMapper mapper = new SchemaMapper(new RuleFactory(config, new Jackson2Annotator(config), new SchemaStore()), new SchemaGenerator());
        mapper.generate(codeModel, className, packageName, source);
        codeModel.build(outputPojoDirectory);
    }
}
