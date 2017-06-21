package io.deepsense.swagger;

import io.swagger.codegen.ClientOptInput;
import io.swagger.codegen.DefaultGenerator;
import io.swagger.codegen.config.CodegenConfigurator;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;

import java.io.File;
import java.util.Collection;

public class CodegenRunner {

    // TODO Rewrite from java to scala

    /**
     * @param args should have 3 values: <br>
        args[0] - inputSpecPath - swagger.json schema file path <br>
        args[1] - outputDir - path where code will be generated to<br>
        args[2] - codePackage - java package to be used for generated code<br>
        args[3] - codegen - name of the codegen class
     */
    public static void main(String[] args) {
        String inputSpecPath = args[0];
        String outputDir = args[1];
        String codegen = args[2];
        String codePackage = args[3];
        generate(inputSpecPath, outputDir, codegen, codePackage);
    }

    public static Collection<File> generate(String inputSpecPath, String outputDir, String codegen, String codePackage) {
        CodegenConfigurator configurator = new CodegenConfigurator();
        configurator.setInputSpec(inputSpecPath);
        configurator.setOutputDir(outputDir);
        configurator.setLang(codegen);
        configurator.setApiPackage(codePackage + ".api");
        configurator.setInvokerPackage(codePackage + ".api.invoker");
        configurator.setModelPackage(codePackage + ".model");

        final ClientOptInput clientOptInput = configurator.toClientOptInput();
        new DefaultGenerator().opts(clientOptInput).generate();
        return getAllGeneratedFiles(outputDir);
    }

    private static Collection<File> getAllGeneratedFiles(String outputDir) {
        return FileUtils.listFiles(
                new File(outputDir),
                new SuffixFileFilter(".scala"),
                DirectoryFileFilter.DIRECTORY
        );
    }

}
