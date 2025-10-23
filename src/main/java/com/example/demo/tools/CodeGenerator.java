package com.example.demo.tools;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

/**
 * A modern code generator using FastAutoGenerator.
 */
public class CodeGenerator {

    public static void main(String[] args) {

        // Get the current project's root directory
        String projectPath = System.getProperty("user.dir");

        // 1. Start the generator
        FastAutoGenerator.create(
                        "jdbc:mysql://localhost:3306/demo", // Your DB URL
                        "demo",       // Your username
                        "demo")   // Your password

                // 2. Global Config (GlobalConfig)
                .globalConfig(builder -> {
                    builder.author("ethan") // Set your name
                            .outputDir(projectPath + "/src/main/java") // Java output path
                            .enableSwagger() // For Spring Boot 3+ Swagger
                            .disableOpenDir(); // Don't open the folder
                })

                // 3. Package Config (PackageConfig)
                .packageConfig(builder -> {
                    builder.parent("com.example.demo") // Your base package
                            .entity("model")
                            .mapper("mapper")
                            .service("service")
                            .serviceImpl("service.impl")
                            .controller("controller")
                            .pathInfo(Collections.singletonMap(OutputFile.xml,
                                    projectPath + "/src/main/src/mapper" // XML output path
                            ));
                })

                // 4. Strategy Config (StrategyConfig)
                .strategyConfig(builder -> {
                    builder.addInclude("users") // Set table name(s)
                            .addTablePrefix("") // No table prefix
                            .addTableSuffix("s")

                            // Entity Strategy
                            .entityBuilder()
                            .enableLombok()
                            .enableTableFieldAnnotation()
                            .naming(NamingStrategy.underline_to_camel)
                            .columnNaming(NamingStrategy.underline_to_camel)

                            // Controller Strategy
                            .controllerBuilder()
                            .enableRestStyle()
                            // Mapper Strategy
                            .mapperBuilder();
                })
                .templateEngine(new FreemarkerTemplateEngine())
                // 6. Execute!
                .execute();
    }
}