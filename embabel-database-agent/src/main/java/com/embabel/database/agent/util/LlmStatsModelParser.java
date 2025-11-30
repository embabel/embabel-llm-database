package com.embabel.database.agent.util;

import com.embabel.database.core.repository.domain.Model;
import com.embabel.database.core.repository.domain.ModelProvider;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class LlmStatsModelParser implements ModelParser {

    private static final Log logger = LogFactory.getLog(LlmStatsModelParser.class);

    @Override
    public List<Model> parseModels(List<Map<String, Object>> rawModels) {
        //loop and parse into the model object

        return List.of();
    }

    @Override
    public List<Model> parseModels(String json) {
        logger.warn(" parseModels(json) not implemented");
        return null;
    }

    @Override
    public Model parseModel(String json) {
        logger.warn(" parseModel(json) not implemented");
        return null;
    }

    private static final String MODEL_ID = "model_id";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String PARAM_COUNT = "param_count";
    private static final String KNOWLEDGE_CUTOFF = "knowledge_cutoff";
    private static final String RELEASE_DATE = "release_date";
    private static final String MULTI_MODAL = "multimodal";
    private static final String PROVIDERS = "providers";

    @Override
    public Model parseModel(Map<String, Object> rawModel) {
        //single Map representing a model
        //create a Model in response
        //pull out the components
        /*
        name
        id
        knowledgeCutoff
        releaseDate
        parameterCount
        multiModal
        description

        //list of tags (strings)
        //list of providers (Provider)
        //organization object
         */
        String id = rawModel.get(MODEL_ID).toString();
        String name = rawModel.get(NAME).toString();
        String description = rawModel.get(DESCRIPTION).toString();
        long paramCount = 0L;
        if (rawModel.get(PARAM_COUNT) != null) {
            try {
                paramCount = Long.parseLong(rawModel.get(PARAM_COUNT).toString());
            }
            catch (Exception e) {
                logger.warn("error parsing " + rawModel.get(PARAM_COUNT),e);
            }
        }
        LocalDate knowledgeCutoff = getLocalDate(rawModel.get(KNOWLEDGE_CUTOFF) != null ? rawModel.get(KNOWLEDGE_CUTOFF).toString() : null);
        LocalDate releaseDate = getLocalDate(rawModel.get(RELEASE_DATE) != null ? rawModel.get(RELEASE_DATE).toString() : null);
        boolean multiModal = Boolean.parseBoolean(rawModel.get(MULTI_MODAL).toString());
        //process the providers
        List<ModelProvider> providers = getProviders(rawModel.get(PROVIDERS) != null ? (List<Map<String,Object>>) rawModel.get(PROVIDERS) : null);
        return null;
    }

    /*
    ModelProvider consists of
    - id
    - inputPerMillion
    - outputPerMillion
    - tags
    - Provider
       - id
       - name
       - website
     */

    private static final String PROVIDER_ID = "provider_id";
    private static final String PROVIDER_NAME = "name";
    private static final String PROVIDER_WEBSITE = "website";
    private static final String PRICING = "pricing";
    private static final String INPUT_PER_MILLION = "input_per_million";
    private static final String OUTPUT_PER_MILLION = "output_per_million";

    private List<ModelProvider> getProviders(List<Map<String,Object>> raw) {
        if (raw != null) {
            //process
            raw.forEach(rawProvider -> {
                //provider
                String providerId = rawProvider.get(PROVIDER_ID).toString();
                String name = rawProvider.get(PROVIDER_NAME).toString();
                String website = rawProvider.get(PROVIDER_WEBSITE).toString();
                //specific implementations
                double inputPerMillion = 0.0;//default
                double outputPerMillion = 0.0;//default
                if (rawProvider.get(PRICING) != null) {
                    Map<String,Object> pricing = (Map<String,Object>) rawProvider.get(PRICING);
                    inputPerMillion = getPrice(pricing.get(INPUT_PER_MILLION) != null ? pricing.get(INPUT_PER_MILLION).toString() : null);
                    outputPerMillion = getPrice(pricing.get(OUTPUT_PER_MILLION) != null ? pricing.get(OUTPUT_PER_MILLION).toString() : null);
                } //end if
            });
        }
        return null;
    }

    private Double getPrice(String value) {
        Double processed = 0.0;
        if (value != null) {
            try {
                processed = Double.parseDouble(value);
            }
            catch (Exception e) {
                logger.warn("error parsing " + value,e);
            }

        }
        return processed;
    }

    private LocalDate getLocalDate(String value) {
        LocalDate localDate = LocalDate.now();//default
        try {
            localDate = LocalDate.parse(value);
        } catch (Exception e) {
            logger.warn("error parsing " + value,e);
        }
        return localDate;
    }

    /*
    "modalities": {
        "input": {
          "text": true,
          "image": true,
          "audio": true,
          "video": true
        },
        "output": {
          "text": true,
          "image": false,
          "audio": false,
          "video": false
        }
      }
     */

    private static final String INPUT = "input";

    String chooseTag(Map<String, Object> modalities) {
        //get the input
        return null;
    }
}
