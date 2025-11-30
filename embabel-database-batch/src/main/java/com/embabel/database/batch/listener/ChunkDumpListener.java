package com.embabel.database.batch.listener;

import com.embabel.database.core.repository.ModelRepository;
import com.embabel.database.core.repository.domain.Model;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.kotlin.KotlinModule;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileOutputStream;
import java.util.List;

public class ChunkDumpListener implements ChunkListener {

    private static final Log logger = LogFactory.getLog(ChunkDumpListener.class);

    @Autowired
    ModelRepository modelRepository;

    @Autowired
    ObjectMapper objectMapper;

    String location = "./dump.json";

    @Override
    public void afterChunk(ChunkContext context) {
        //dump the current model repository
        try {
            //write out
            List<Model> models = modelRepository.findAll();
            objectMapper.registerModule(new JavaTimeModule())
                    .registerModule(new KotlinModule.Builder()
                            .build())
                    .writerWithDefaultPrettyPrinter()
                    .writeValue(new FileOutputStream(location),models);
        } catch (Exception e) {
            logger.error("error writing out",e);
        }
    }
}
