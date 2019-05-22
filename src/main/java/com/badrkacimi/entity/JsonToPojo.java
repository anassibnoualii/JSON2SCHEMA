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
import java.util.logging.Logger;

public class JsonToPojo {

    // output package
    private static final String PACKAGE_NAME = "com.badrkacimi.pojogen";
    private static final File OUTPUT_POJO_DIRECTORY = new File("./src/main/java");
    private static final int INITIAL_INDEX = 0;
    private static final Logger LOGGER = Logger.getLogger(String.valueOf(JsonToPojo.class));

    public static void main(String[] args) throws IOException {

        List<File> files = gettingJsonFiles();

        convertingJsonToPojo(files);

    }

    private static void convertingJsonToPojo( List<File> files) throws MalformedURLException {
        int i =INITIAL_INDEX;
        for (File file : files) {
            URL inputjson = file.toURI().toURL();
            String className = file.getName();

            try {
                convertToJson(inputjson, className.replace(".json", ""));
                i++;


            } catch (Exception e) {
                LOGGER.info("Encountered issue while converting to pojo: " + e.getMessage());
            }

        }
        LOGGER.info("Nombre de POJOs converted & created: " + " " + (i - 1));
    }

    private static List<File> gettingJsonFiles() throws IOException {
        File dir = new File("./src/main/resources/json/json-files");// input
        String[] extensions = new String[]{"json"};
        LOGGER.info("Obtenir tous les json... " + dir.getCanonicalPath() + " y compris ceux dans les sous-repertoires");
        return (List<File>) FileUtils.listFiles(dir, extensions, true);

    }


    private static void convertToJson(URL inputJson, String className) throws IOException {
        JCodeModel codeModel = new JCodeModel();
        GenerationConfig config = new DefaultGenerationConfig() {
            @Override
            public boolean isGenerateBuilders() { // set config option by overriding method
                return true;
            }
            @Override
            public SourceType getSourceType() {
                return SourceType.JSON;
            }
        };
        SchemaMapper mapper = new SchemaMapper(new RuleFactory(config, new Jackson2Annotator(config), new SchemaStore()), new SchemaGenerator());
        mapper.generate(codeModel, className, PACKAGE_NAME, inputJson);
        codeModel.build(OUTPUT_POJO_DIRECTORY);
    }
}
