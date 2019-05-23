package com.badrkacimi.entitygenerator.mapper;


import com.sun.codemodel.JCodeModel;
import org.jsonschema2pojo.*;
import org.jsonschema2pojo.rules.RuleFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;

public class ToClassMapper {

    // output package
    private static final String PACKAGE_NAME = "com.badrkacimi.pojogen";
    private static final File OUTPUT_CLASSES_DIRECTORY = new File("./src/main/java");

    private static final Logger LOGGER = Logger.getLogger(String.valueOf(ToClassMapper.class));

    public static void fromJsonFileToJavaClass(URL inputJson, String className) throws IOException {

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
        codeModel.build(OUTPUT_CLASSES_DIRECTORY);
        LOGGER.info("to class mapper");
    }
}
