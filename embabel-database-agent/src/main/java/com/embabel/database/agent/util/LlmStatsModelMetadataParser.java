package com.embabel.database.agent.util;

import com.embabel.common.ai.model.ModelMetadata;
import com.embabel.common.ai.model.PerTokenPricingModel;
import com.embabel.common.ai.model.PricingModel;
import com.embabel.database.core.repository.LlmModelMetadata;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LlmStatsModelMetadataParser implements ModelMetadataParser {

    private static final Log logger = LogFactory.getLog(LlmStatsModelMetadataParser.class);

    private static final String MODEL_ID = "model_id";
    private static final String NAME = "name";
    private static final String KNOWLEDGE_CUTOFF_DATE = "knowledge_cutoff";


    ObjectMapper objectMapper;

    TagParser tagParser;

    public LlmStatsModelMetadataParser(ObjectMapper objectMapper, TagParser tagParser) {
        this.objectMapper = objectMapper;
        this.tagParser = tagParser;
    }

    @Override
    public List<ModelMetadata> parse(String json) {
        logger.error("not implemented");
        return null;
    }

    @Override
    public List<ModelMetadata> parse(Path path) {
        logger.error("not implemented");
        return null;
    }

    @Override
    public List<ModelMetadata> parse(List<?> list) {
        //expect a list of maps
        List<ModelMetadata> models = new ArrayList<>();
        ((List<Map<String,Object>>) list).forEach(map -> {
           //create a metadata object
            String modelId = map.get(MODEL_ID).toString();
            //get the map ("organization > id")
//            String providerName = map.get(PROVIDER_NAME).toString();
            String modelName = map.get(NAME).toString();
            //date parse
            LocalDate knowledgeCutoffDate = LocalDate.of(1970, 1, 1);
            if (map.containsKey(KNOWLEDGE_CUTOFF_DATE)) {
                knowledgeCutoffDate = LocalDate.parse(map.get(KNOWLEDGE_CUTOFF_DATE).toString());
            } //end if
            // if (providerModels.get(UPDATED_AT) != null && providerModels.get(UPDATED_AT).toString().length() > 0) {
            //     knowledgeCutoffDate = parseLocalDate(providerModels.get(UPDATED_AT).toString());
            // } //end if
//            double pricePerInput = providerModel.get(PRICE_PER_INPUT_TOKEN) != null ? Double.parseDouble(providerModel.get(PRICE_PER_INPUT_TOKEN).toString()) : 0.0;
//            double pricePerOutput = providerModel.get(PRICE_PER_OUTPUT_TOKEN) != null ? Double.parseDouble(providerModel.get(PRICE_PER_OUTPUT_TOKEN).toString()) : 0.0;
//            //bulid the pricingModel
//            PricingModel pricingModel = new PerTokenPricingModel(pricePerInput, pricePerOutput);
//            //tokens
//            Long contextLength = providerModel.get(CONTEXT_LENGTH) != null ? Long.parseLong(providerModel.get(CONTEXT_LENGTH).toString()) : 0l;
//            //task
//            List<String> tags = taskParser.getTags(providerModel);
//            //build the metadata
//            ModelMetadata modelMetadata = LlmModelMetadata.Companion.create(modelName,providerName,knowledgeCutoffDate,pricingModel,contextLength,tags,this.getClass().getSimpleName(),0l,modelId);
//            //add
//            listModelMetadata.add(modelMetadata);
        });
        //return
        return models;
    }
}
