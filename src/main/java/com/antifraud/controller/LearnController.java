package com.antifraud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/learn")
public class LearnController {
    @Autowired
    private CommandBuilder commandBuilder;

    @Value("${modelRoot}")
    private String modelRoot;

    @RequestMapping(method={RequestMethod.GET,RequestMethod.POST})
    public @ResponseBody Model startLearning(@RequestParam(value="modelName", required=true) String modelName,
                                              @RequestParam(value="eventName", required=false, defaultValue="learn") String eventName,
                                              @RequestParam(value="tableName", required=true) String tableName) {

        Model output = new Model(modelRoot,modelName,eventName,tableName);

        try {
            String command = commandBuilder.build("train_data", output.getPath());
            System.out.println(command);

            Process p = Runtime.getRuntime().exec("ping google.com");

            p.waitFor();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";
            while ((line = reader.readLine())!= null) {
                System.out.println(line);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return output;
    }

    @Component
    public static class CommandBuilder {
        @Value("${header}")
        String header;
        @Value("${sparkHome}")
        String sparkHome;
        @Value("${sparkClassname}")
        String className;
        @Value("${deployParamsYarn}")
        String deployParams;
        @Value("${jarUrl}")
        String jarPath;
        @Value("${jsonMetadata}")
        String jsonMetadata;
        @Value("${dbPassword}")
        String dbPassword;
        @Value("${dbUsername}")
        String dbUsername;

        @Value("${dbUrl}")
        String dbUrl;

        public String build(final String dbTable, final String outputPmml) {
            String splitter = " ";
            StringBuilder sb = new StringBuilder();
            sb.append(header).append(splitter)
                    .append(sparkHome).append(splitter)
                    .append("--class").append(splitter)
                    .append(className).append(splitter)
                    .append(deployParams).append(splitter)
                    .append(jarPath).append(splitter)
                    .append("--json-metadata").append(splitter)
                    .append(jsonMetadata).append(splitter)
                    .append("--outputPmml").append(splitter)
                    .append(outputPmml).append(splitter)
                    .append("--dbPassword").append(splitter)
                    .append(dbPassword).append(splitter)
                    .append("--dbUsername").append(splitter)
                    .append(dbUsername).append(splitter)
                    .append("--dbUrl ").append(splitter)
                    .append(dbUrl).append(splitter)
                    .append("--dbTable").append(splitter)
                    .append(dbTable);
            return sb.toString();
        }

    }



    //sudo -u hdfs /usr/hdp/2.5.0.0-1245/spark2/bin/spark-submit
    // --class org.sparkexample.DataPipeline --master yarn --deploy-mode cluster
    // ./antifraud/bootstrap-1.0-SNAPSHOT.jar --json-metadata hdfs://controlnode.lpr.jet.msk.su:8020/user/hdfs/mlAttributes.json --output-pmml hdfs://controlnode.lpr.jet.msk.su:8020/user/hdfs/model.pmml --dbPassword q1 --dbUsername jafs --dbTable train_data --dbUrl jdbc:postgresql://avm14.lpr.jet.msk.su:5432/jafsdb


}