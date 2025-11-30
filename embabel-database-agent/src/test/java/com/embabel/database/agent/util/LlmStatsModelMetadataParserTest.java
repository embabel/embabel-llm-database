package com.embabel.database.agent.util;

import com.embabel.common.ai.model.ModelMetadata;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LlmStatsModelMetadataParserTest {


    @Test
    void testParseList() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        LlmStatsModelMetadataParser llmStatsModelMetadataParser = new LlmStatsModelMetadataParser(objectMapper,null);
        //read the json into a map
        Map<String,Object> map = objectMapper.readValue(json, new TypeReference<Map<String, Object>>() {});
        List<ModelMetadata> results = llmStatsModelMetadataParser.parse(List.of(map));
        //check
        assertNotNull(results);
        //get the first
        assertEquals("Jamba 1.5 Large",results.get(0).getName());
    }

    //sample json
    String json = "{\n" +
            "  \"model_id\" : \"jamba-1.5-large\",\n" +
            "  \"name\" : \"Jamba 1.5 Large\",\n" +
            "  \"organization\" : {\n" +
            "    \"id\" : \"ai21\",\n" +
            "    \"name\" : \"AI21 Labs\",\n" +
            "    \"website\" : \"https://ai21.com\"\n" +
            "  },\n" +
            "  \"description\" : \"State-of-the-art hybrid SSM-Transformer instruction following foundation model, offering superior long context handling, speed, and quality.\",\n" +
            "  \"release_date\" : \"2024-08-22\",\n" +
            "  \"announcement_date\" : \"2024-08-22\",\n" +
            "  \"multimodal\" : false,\n" +
            "  \"knowledge_cutoff\" : \"2024-03-05\",\n" +
            "  \"param_count\" : 398000000000,\n" +
            "  \"training_tokens\" : null,\n" +
            "  \"available_in_zeroeval\" : true,\n" +
            "  \"license\" : {\n" +
            "    \"name\" : \"Jamba Open Model License\",\n" +
            "    \"allow_commercial\" : false\n" +
            "  },\n" +
            "  \"model_family\" : null,\n" +
            "  \"fine_tuned_from\" : null,\n" +
            "  \"tags\" : null,\n" +
            "  \"sources\" : {\n" +
            "    \"api_ref\" : \"https://docs.ai21.com/reference/jamba-15-api-ref\",\n" +
            "    \"playground\" : null,\n" +
            "    \"paper\" : null,\n" +
            "    \"scorecard_blog\" : \"https://www.ai21.com/blog/announcing-jamba-model-family\",\n" +
            "    \"repo\" : null,\n" +
            "    \"weights\" : \"https://huggingface.co/ai21labs/AI21-Jamba-1.5-Large\"\n" +
            "  },\n" +
            "  \"benchmarks\" : [ {\n" +
            "    \"benchmark_id\" : \"arc-c\",\n" +
            "    \"name\" : \"ARC-C\",\n" +
            "    \"description\" : \"The AI2 Reasoning Challenge (ARC) Challenge Set is a multiple-choice question-answering benchmark containing grade-school level science questions that require advanced reasoning capabilities. ARC-C specifically contains questions that were answered incorrectly by both retrieval-based and word co-occurrence algorithms, making it a particularly challenging subset designed to test commonsense reasoning abilities in AI systems.\",\n" +
            "    \"categories\" : [ \"general\", \"reasoning\" ],\n" +
            "    \"modality\" : \"text\",\n" +
            "    \"max_score\" : 1.0,\n" +
            "    \"score\" : 0.93,\n" +
            "    \"normalized_score\" : 0.93,\n" +
            "    \"verified\" : false,\n" +
            "    \"self_reported\" : true,\n" +
            "    \"self_reported_source\" : \"https://huggingface.co/ai21labs/AI21-Jamba-1.5-Large\",\n" +
            "    \"analysis_method\" : \"Accuracy\",\n" +
            "    \"verification_date\" : null,\n" +
            "    \"verification_notes\" : null\n" +
            "  }, {\n" +
            "    \"benchmark_id\" : \"arena-hard\",\n" +
            "    \"name\" : \"Arena Hard\",\n" +
            "    \"description\" : \"Arena-Hard-Auto is an automatic evaluation benchmark for instruction-tuned LLMs consisting of 500 challenging real-world prompts curated by BenchBuilder. It includes open-ended software engineering problems, mathematical questions, and creative writing tasks. The benchmark uses LLM-as-a-Judge methodology with GPT-4.1 and Gemini-2.5 as automatic judges to approximate human preference. Arena-Hard achieves 98.6% correlation with human preference rankings and provides 3x higher separation of model performances compared to MT-Bench, making it highly effective for distinguishing between models of similar quality.\",\n" +
            "    \"categories\" : [ \"creativity\", \"general\", \"reasoning\" ],\n" +
            "    \"modality\" : \"text\",\n" +
            "    \"max_score\" : 1.0,\n" +
            "    \"score\" : 0.654,\n" +
            "    \"normalized_score\" : 0.654,\n" +
            "    \"verified\" : false,\n" +
            "    \"self_reported\" : true,\n" +
            "    \"self_reported_source\" : \"https://huggingface.co/ai21labs/AI21-Jamba-1.5-Large\",\n" +
            "    \"analysis_method\" : \"Accuracy\",\n" +
            "    \"verification_date\" : null,\n" +
            "    \"verification_notes\" : null\n" +
            "  }, {\n" +
            "    \"benchmark_id\" : \"gpqa\",\n" +
            "    \"name\" : \"GPQA\",\n" +
            "    \"description\" : \"A challenging dataset of 448 multiple-choice questions written by domain experts in biology, physics, and chemistry. Questions are Google-proof and extremely difficult, with PhD experts reaching 65% accuracy.\",\n" +
            "    \"categories\" : [ \"general\", \"reasoning\" ],\n" +
            "    \"modality\" : \"text\",\n" +
            "    \"max_score\" : 1.0,\n" +
            "    \"score\" : 0.369,\n" +
            "    \"normalized_score\" : 0.369,\n" +
            "    \"verified\" : false,\n" +
            "    \"self_reported\" : true,\n" +
            "    \"self_reported_source\" : \"https://huggingface.co/ai21labs/AI21-Jamba-1.5-Large\",\n" +
            "    \"analysis_method\" : \"Accuracy\",\n" +
            "    \"verification_date\" : null,\n" +
            "    \"verification_notes\" : null\n" +
            "  }, {\n" +
            "    \"benchmark_id\" : \"gsm8k\",\n" +
            "    \"name\" : \"GSM8k\",\n" +
            "    \"description\" : \"Grade School Math 8K, a dataset of 8.5K high-quality linguistically diverse grade school math word problems requiring multi-step reasoning and elementary arithmetic operations.\",\n" +
            "    \"categories\" : [ \"math\", \"reasoning\" ],\n" +
            "    \"modality\" : \"text\",\n" +
            "    \"max_score\" : 1.0,\n" +
            "    \"score\" : 0.87,\n" +
            "    \"normalized_score\" : 0.87,\n" +
            "    \"verified\" : false,\n" +
            "    \"self_reported\" : true,\n" +
            "    \"self_reported_source\" : \"https://huggingface.co/ai21labs/AI21-Jamba-1.5-Large\",\n" +
            "    \"analysis_method\" : \"Accuracy\",\n" +
            "    \"verification_date\" : null,\n" +
            "    \"verification_notes\" : null\n" +
            "  }, {\n" +
            "    \"benchmark_id\" : \"mmlu\",\n" +
            "    \"name\" : \"MMLU\",\n" +
            "    \"description\" : \"Massive Multitask Language Understanding benchmark testing knowledge across 57 diverse subjects including STEM, humanities, social sciences, and professional domains\",\n" +
            "    \"categories\" : [ \"general\", \"language\", \"math\", \"reasoning\" ],\n" +
            "    \"modality\" : \"text\",\n" +
            "    \"max_score\" : 1.0,\n" +
            "    \"score\" : 0.812,\n" +
            "    \"normalized_score\" : 0.812,\n" +
            "    \"verified\" : false,\n" +
            "    \"self_reported\" : true,\n" +
            "    \"self_reported_source\" : \"https://huggingface.co/ai21labs/AI21-Jamba-1.5-Large\",\n" +
            "    \"analysis_method\" : \"Chain-of-Thought accuracy\",\n" +
            "    \"verification_date\" : null,\n" +
            "    \"verification_notes\" : null\n" +
            "  }, {\n" +
            "    \"benchmark_id\" : \"mmlu-pro\",\n" +
            "    \"name\" : \"MMLU-Pro\",\n" +
            "    \"description\" : \"A more robust and challenging multi-task language understanding benchmark that extends MMLU by expanding multiple-choice options from 4 to 10, eliminating trivial questions, and focusing on reasoning-intensive tasks. Features over 12,000 curated questions across 14 domains and causes a 16-33% accuracy drop compared to original MMLU.\",\n" +
            "    \"categories\" : [ \"general\", \"language\", \"math\", \"reasoning\" ],\n" +
            "    \"modality\" : \"text\",\n" +
            "    \"max_score\" : 1.0,\n" +
            "    \"score\" : 0.535,\n" +
            "    \"normalized_score\" : 0.535,\n" +
            "    \"verified\" : false,\n" +
            "    \"self_reported\" : true,\n" +
            "    \"self_reported_source\" : \"https://huggingface.co/ai21labs/AI21-Jamba-1.5-Large\",\n" +
            "    \"analysis_method\" : \"Chain-of-Thought accuracy\",\n" +
            "    \"verification_date\" : null,\n" +
            "    \"verification_notes\" : null\n" +
            "  }, {\n" +
            "    \"benchmark_id\" : \"truthfulqa\",\n" +
            "    \"name\" : \"TruthfulQA\",\n" +
            "    \"description\" : \"TruthfulQA is a benchmark to measure whether language models are truthful in generating answers to questions. It comprises 817 questions that span 38 categories, including health, law, finance and politics. The questions are crafted such that some humans would answer falsely due to a false belief or misconception, testing models' ability to avoid generating false answers learned from human texts.\",\n" +
            "    \"categories\" : [ \"finance\", \"general\", \"healthcare\", \"legal\", \"reasoning\" ],\n" +
            "    \"modality\" : \"text\",\n" +
            "    \"max_score\" : 1.0,\n" +
            "    \"score\" : 0.583,\n" +
            "    \"normalized_score\" : 0.583,\n" +
            "    \"verified\" : false,\n" +
            "    \"self_reported\" : true,\n" +
            "    \"self_reported_source\" : \"https://huggingface.co/ai21labs/AI21-Jamba-1.5-Large\",\n" +
            "    \"analysis_method\" : \"Accuracy\",\n" +
            "    \"verification_date\" : null,\n" +
            "    \"verification_notes\" : null\n" +
            "  }, {\n" +
            "    \"benchmark_id\" : \"wild-bench\",\n" +
            "    \"name\" : \"Wild Bench\",\n" +
            "    \"description\" : \"WildBench is an automated evaluation framework that benchmarks large language models using 1,024 challenging, real-world tasks selected from over one million human-chatbot conversation logs. It introduces two evaluation metrics (WB-Reward and WB-Score) that achieve high correlation with human preferences and uses task-specific checklists for systematic evaluation.\",\n" +
            "    \"categories\" : [ \"general\", \"reasoning\" ],\n" +
            "    \"modality\" : \"text\",\n" +
            "    \"max_score\" : 1.0,\n" +
            "    \"score\" : 0.485,\n" +
            "    \"normalized_score\" : 0.485,\n" +
            "    \"verified\" : false,\n" +
            "    \"self_reported\" : true,\n" +
            "    \"self_reported_source\" : \"https://huggingface.co/ai21labs/AI21-Jamba-1.5-Large\",\n" +
            "    \"analysis_method\" : \"Accuracy\",\n" +
            "    \"verification_date\" : null,\n" +
            "    \"verification_notes\" : null\n" +
            "  } ],\n" +
            "  \"providers\" : [ {\n" +
            "    \"provider_id\" : \"bedrock\",\n" +
            "    \"name\" : \"Bedrock\",\n" +
            "    \"website\" : \"https://aws.amazon.com/bedrock/\",\n" +
            "    \"deprecated\" : false,\n" +
            "    \"deprecated_at\" : null,\n" +
            "    \"pricing\" : {\n" +
            "      \"input_per_million\" : 2.0,\n" +
            "      \"output_per_million\" : 8.0\n" +
            "    },\n" +
            "    \"quantization\" : null,\n" +
            "    \"limits\" : {\n" +
            "      \"max_input_tokens\" : 256000,\n" +
            "      \"max_output_tokens\" : 256000\n" +
            "    },\n" +
            "    \"performance\" : {\n" +
            "      \"throughput\" : \"100.0\",\n" +
            "      \"latency\" : \"0.5\"\n" +
            "    },\n" +
            "    \"features\" : {\n" +
            "      \"web_search\" : null,\n" +
            "      \"function_calling\" : null,\n" +
            "      \"structured_output\" : null,\n" +
            "      \"code_execution\" : null,\n" +
            "      \"batch_inference\" : null,\n" +
            "      \"finetuning\" : null\n" +
            "    },\n" +
            "    \"modalities\" : {\n" +
            "      \"input\" : {\n" +
            "        \"text\" : true,\n" +
            "        \"image\" : false,\n" +
            "        \"audio\" : false,\n" +
            "        \"video\" : false\n" +
            "      },\n" +
            "      \"output\" : {\n" +
            "        \"text\" : true,\n" +
            "        \"image\" : false,\n" +
            "        \"audio\" : false,\n" +
            "        \"video\" : false\n" +
            "      }\n" +
            "    }\n" +
            "  }, {\n" +
            "    \"provider_id\" : \"google\",\n" +
            "    \"name\" : \"Google\",\n" +
            "    \"website\" : \"https://ai.google.dev\",\n" +
            "    \"deprecated\" : false,\n" +
            "    \"deprecated_at\" : null,\n" +
            "    \"pricing\" : {\n" +
            "      \"input_per_million\" : 2.0,\n" +
            "      \"output_per_million\" : 8.0\n" +
            "    },\n" +
            "    \"quantization\" : null,\n" +
            "    \"limits\" : {\n" +
            "      \"max_input_tokens\" : 256000,\n" +
            "      \"max_output_tokens\" : 256000\n" +
            "    },\n" +
            "    \"performance\" : {\n" +
            "      \"throughput\" : \"42.0\",\n" +
            "      \"latency\" : \"0.3\"\n" +
            "    },\n" +
            "    \"features\" : {\n" +
            "      \"web_search\" : null,\n" +
            "      \"function_calling\" : null,\n" +
            "      \"structured_output\" : null,\n" +
            "      \"code_execution\" : null,\n" +
            "      \"batch_inference\" : null,\n" +
            "      \"finetuning\" : null\n" +
            "    },\n" +
            "    \"modalities\" : {\n" +
            "      \"input\" : {\n" +
            "        \"text\" : true,\n" +
            "        \"image\" : false,\n" +
            "        \"audio\" : false,\n" +
            "        \"video\" : false\n" +
            "      },\n" +
            "      \"output\" : {\n" +
            "        \"text\" : true,\n" +
            "        \"image\" : false,\n" +
            "        \"audio\" : false,\n" +
            "        \"video\" : false\n" +
            "      }\n" +
            "    }\n" +
            "  } ],\n" +
            "  \"benchmark_rankings\" : [ {\n" +
            "    \"benchmark_id\" : \"arc-c\",\n" +
            "    \"benchmark_name\" : \"ARC-C\",\n" +
            "    \"models\" : [ {\n" +
            "      \"model_id\" : \"llama-3.1-405b-instruct\",\n" +
            "      \"model_name\" : \"Llama 3.1 405B Instruct\",\n" +
            "      \"score\" : 0.969,\n" +
            "      \"rank\" : 1,\n" +
            "      \"is_current_model\" : false\n" +
            "    }, {\n" +
            "      \"model_id\" : \"claude-3-opus-20240229\",\n" +
            "      \"model_name\" : \"Claude 3 Opus\",\n" +
            "      \"score\" : 0.964,\n" +
            "      \"rank\" : 2,\n" +
            "      \"is_current_model\" : false\n" +
            "    }, {\n" +
            "      \"model_id\" : \"llama-3.1-70b-instruct\",\n" +
            "      \"model_name\" : \"Llama 3.1 70B Instruct\",\n" +
            "      \"score\" : 0.948,\n" +
            "      \"rank\" : 3,\n" +
            "      \"is_current_model\" : false\n" +
            "    }, {\n" +
            "      \"model_id\" : \"nova-pro\",\n" +
            "      \"model_name\" : \"Nova Pro\",\n" +
            "      \"score\" : 0.948,\n" +
            "      \"rank\" : 3,\n" +
            "      \"is_current_model\" : false\n" +
            "    }, {\n" +
            "      \"model_id\" : \"claude-3-sonnet-20240229\",\n" +
            "      \"model_name\" : \"Claude 3 Sonnet\",\n" +
            "      \"score\" : 0.932,\n" +
            "      \"rank\" : 5,\n" +
            "      \"is_current_model\" : false\n" +
            "    }, {\n" +
            "      \"model_id\" : \"jamba-1.5-large\",\n" +
            "      \"model_name\" : \"Jamba 1.5 Large\",\n" +
            "      \"score\" : 0.93,\n" +
            "      \"rank\" : 6,\n" +
            "      \"is_current_model\" : true\n" +
            "    }, {\n" +
            "      \"model_id\" : \"nova-lite\",\n" +
            "      \"model_name\" : \"Nova Lite\",\n" +
            "      \"score\" : 0.924,\n" +
            "      \"rank\" : 7,\n" +
            "      \"is_current_model\" : false\n" +
            "    }, {\n" +
            "      \"model_id\" : \"mistral-small-24b-base-2501\",\n" +
            "      \"model_name\" : \"Mistral Small 3 24B Base\",\n" +
            "      \"score\" : 0.9129,\n" +
            "      \"rank\" : 8,\n" +
            "      \"is_current_model\" : false\n" +
            "    }, {\n" +
            "      \"model_id\" : \"phi-3.5-moe-instruct\",\n" +
            "      \"model_name\" : \"Phi-3.5-MoE-instruct\",\n" +
            "      \"score\" : 0.91,\n" +
            "      \"rank\" : 9,\n" +
            "      \"is_current_model\" : false\n" +
            "    }, {\n" +
            "      \"model_id\" : \"nova-micro\",\n" +
            "      \"model_name\" : \"Nova Micro\",\n" +
            "      \"score\" : 0.902,\n" +
            "      \"rank\" : 10,\n" +
            "      \"is_current_model\" : false\n" +
            "    } ]\n" +
            "  }, {\n" +
            "    \"benchmark_id\" : \"arena-hard\",\n" +
            "    \"benchmark_name\" : \"Arena Hard\",\n" +
            "    \"models\" : [ {\n" +
            "      \"model_id\" : \"qwen3-235b-a22b\",\n" +
            "      \"model_name\" : \"Qwen3 235B A22B\",\n" +
            "      \"score\" : 0.956,\n" +
            "      \"rank\" : 1,\n" +
            "      \"is_current_model\" : false\n" +
            "    }, {\n" +
            "      \"model_id\" : \"qwen3-32b\",\n" +
            "      \"model_name\" : \"Qwen3 32B\",\n" +
            "      \"score\" : 0.938,\n" +
            "      \"rank\" : 2,\n" +
            "      \"is_current_model\" : false\n" +
            "    }, {\n" +
            "      \"model_id\" : \"qwen3-30b-a3b\",\n" +
            "      \"model_name\" : \"Qwen3 30B A3B\",\n" +
            "      \"score\" : 0.91,\n" +
            "      \"rank\" : 3,\n" +
            "      \"is_current_model\" : false\n" +
            "    }, {\n" +
            "      \"model_id\" : \"llama-3.3-nemotron-super-49b-v1\",\n" +
            "      \"model_name\" : \"Llama-3.3 Nemotron Super 49B v1\",\n" +
            "      \"score\" : 0.883,\n" +
            "      \"rank\" : 4,\n" +
            "      \"is_current_model\" : false\n" +
            "    }, {\n" +
            "      \"model_id\" : \"mistral-small-24b-instruct-2501\",\n" +
            "      \"model_name\" : \"Mistral Small 3 24B Instruct\",\n" +
            "      \"score\" : 0.876,\n" +
            "      \"rank\" : 5,\n" +
            "      \"is_current_model\" : false\n" +
            "    }, {\n" +
            "      \"model_id\" : \"qwen-2.5-72b-instruct\",\n" +
            "      \"model_name\" : \"Qwen2.5 72B Instruct\",\n" +
            "      \"score\" : 0.812,\n" +
            "      \"rank\" : 6,\n" +
            "      \"is_current_model\" : false\n" +
            "    }, {\n" +
            "      \"model_id\" : \"phi-4-reasoning-plus\",\n" +
            "      \"model_name\" : \"Phi 4 Reasoning Plus\",\n" +
            "      \"score\" : 0.79,\n" +
            "      \"rank\" : 7,\n" +
            "      \"is_current_model\" : false\n" +
            "    }, {\n" +
            "      \"model_id\" : \"deepseek-v2.5\",\n" +
            "      \"model_name\" : \"DeepSeek-V2.5\",\n" +
            "      \"score\" : 0.762,\n" +
            "      \"rank\" : 8,\n" +
            "      \"is_current_model\" : false\n" +
            "    }, {\n" +
            "      \"model_id\" : \"phi-4\",\n" +
            "      \"model_name\" : \"Phi 4\",\n" +
            "      \"score\" : 0.754,\n" +
            "      \"rank\" : 9,\n" +
            "      \"is_current_model\" : false\n" +
            "    }, {\n" +
            "      \"model_id\" : \"phi-4-reasoning\",\n" +
            "      \"model_name\" : \"Phi 4 Reasoning\",\n" +
            "      \"score\" : 0.733,\n" +
            "      \"rank\" : 10,\n" +
            "      \"is_current_model\" : false\n" +
            "    }, {\n" +
            "      \"model_id\" : \"jamba-1.5-large\",\n" +
            "      \"model_name\" : \"Jamba 1.5 Large\",\n" +
            "      \"score\" : 0.654,\n" +
            "      \"rank\" : 12,\n" +
            "      \"is_current_model\" : true\n" +
            "    } ]\n" +
            "  }, {\n" +
            "    \"benchmark_id\" : \"gpqa\",\n" +
            "    \"benchmark_name\" : \"GPQA\",\n" +
            "    \"models\" : [ {\n" +
            "      \"model_id\" : \"gemini-3-pro-preview\",\n" +
            "      \"model_name\" : \"Gemini 3 Pro\",\n" +
            "      \"score\" : 0.919,\n" +
            "      \"rank\" : 1,\n" +
            "      \"is_current_model\" : false\n" +
            "    }, {\n" +
            "      \"model_id\" : \"grok-4-heavy\",\n" +
            "      \"model_name\" : \"Grok-4 Heavy\",\n" +
            "      \"score\" : 0.884,\n" +
            "      \"rank\" : 2,\n" +
            "      \"is_current_model\" : false\n" +
            "    }, {\n" +
            "      \"model_id\" : \"gpt-5.1-2025-11-13\",\n" +
            "      \"model_name\" : \"GPT-5.1\",\n" +
            "      \"score\" : 0.881,\n" +
            "      \"rank\" : 3,\n" +
            "      \"is_current_model\" : false\n" +
            "    }, {\n" +
            "      \"model_id\" : \"gpt-5.1-instant-2025-11-12\",\n" +
            "      \"model_name\" : \"GPT-5.1 Instant\",\n" +
            "      \"score\" : 0.881,\n" +
            "      \"rank\" : 3,\n" +
            "      \"is_current_model\" : false\n" +
            "    }, {\n" +
            "      \"model_id\" : \"gpt-5.1-thinking-2025-11-12\",\n" +
            "      \"model_name\" : \"GPT-5.1 Thinking\",\n" +
            "      \"score\" : 0.881,\n" +
            "      \"rank\" : 3,\n" +
            "      \"is_current_model\" : false\n" +
            "    }, {\n" +
            "      \"model_id\" : \"grok-4\",\n" +
            "      \"model_name\" : \"Grok-4\",\n" +
            "      \"score\" : 0.875,\n" +
            "      \"rank\" : 6,\n" +
            "      \"is_current_model\" : false\n" +
            "    }, {\n" +
            "      \"model_id\" : \"claude-opus-4-5-20251101\",\n" +
            "      \"model_name\" : \"Claude Opus 4.5\",\n" +
            "      \"score\" : 0.87,\n" +
            "      \"rank\" : 7,\n" +
            "      \"is_current_model\" : false\n" +
            "    }, {\n" +
            "      \"model_id\" : \"gemini-2.5-pro-preview-06-05\",\n" +
            "      \"model_name\" : \"Gemini 2.5 Pro Preview 06-05\",\n" +
            "      \"score\" : 0.864,\n" +
            "      \"rank\" : 8,\n" +
            "      \"is_current_model\" : false\n" +
            "    }, {\n" +
            "      \"model_id\" : \"gpt-5-2025-08-07\",\n" +
            "      \"model_name\" : \"GPT-5\",\n" +
            "      \"score\" : 0.857,\n" +
            "      \"rank\" : 9,\n" +
            "      \"is_current_model\" : false\n" +
            "    }, {\n" +
            "      \"model_id\" : \"grok-4-fast\",\n" +
            "      \"model_name\" : \"Grok 4 Fast\",\n" +
            "      \"score\" : 0.857,\n" +
            "      \"rank\" : 9,\n" +
            "      \"is_current_model\" : false\n" +
            "    }, {\n" +
            "      \"model_id\" : \"jamba-1.5-large\",\n" +
            "      \"model_name\" : \"Jamba 1.5 Large\",\n" +
            "      \"score\" : 0.369,\n" +
            "      \"rank\" : 120,\n" +
            "      \"is_current_model\" : true\n" +
            "    } ]\n" +
            "  }, {\n" +
            "    \"benchmark_id\" : \"gsm8k\",\n" +
            "    \"benchmark_name\" : \"GSM8k\",\n" +
            "    \"models\" : [ {\n" +
            "      \"model_id\" : \"kimi-k2-instruct\",\n" +
            "      \"model_name\" : \"Kimi K2 Instruct\",\n" +
            "      \"score\" : 0.973,\n" +
            "      \"rank\" : 1,\n" +
            "      \"is_current_model\" : false\n" +
            "    }, {\n" +
            "      \"model_id\" : \"o1-2024-12-17\",\n" +
            "      \"model_name\" : \"o1\",\n" +
            "      \"score\" : 0.971,\n" +
            "      \"rank\" : 2,\n" +
            "      \"is_current_model\" : false\n" +
            "    }, {\n" +
            "      \"model_id\" : \"gpt-4.5\",\n" +
            "      \"model_name\" : \"GPT-4.5\",\n" +
            "      \"score\" : 0.97,\n" +
            "      \"rank\" : 3,\n" +
            "      \"is_current_model\" : false\n" +
            "    }, {\n" +
            "      \"model_id\" : \"llama-3.1-405b-instruct\",\n" +
            "      \"model_name\" : \"Llama 3.1 405B Instruct\",\n" +
            "      \"score\" : 0.968,\n" +
            "      \"rank\" : 4,\n" +
            "      \"is_current_model\" : false\n" +
            "    }, {\n" +
            "      \"model_id\" : \"claude-3-5-sonnet-20240620\",\n" +
            "      \"model_name\" : \"Claude 3.5 Sonnet\",\n" +
            "      \"score\" : 0.964,\n" +
            "      \"rank\" : 5,\n" +
            "      \"is_current_model\" : false\n" +
            "    }, {\n" +
            "      \"model_id\" : \"claude-3-5-sonnet-20241022\",\n" +
            "      \"model_name\" : \"Claude 3.5 Sonnet\",\n" +
            "      \"score\" : 0.964,\n" +
            "      \"rank\" : 5,\n" +
            "      \"is_current_model\" : false\n" +
            "    }, {\n" +
            "      \"model_id\" : \"gemma-3-27b-it\",\n" +
            "      \"model_name\" : \"Gemma 3 27B\",\n" +
            "      \"score\" : 0.959,\n" +
            "      \"rank\" : 7,\n" +
            "      \"is_current_model\" : false\n" +
            "    }, {\n" +
            "      \"model_id\" : \"qwen-2.5-32b-instruct\",\n" +
            "      \"model_name\" : \"Qwen2.5 32B Instruct\",\n" +
            "      \"score\" : 0.959,\n" +
            "      \"rank\" : 7,\n" +
            "      \"is_current_model\" : false\n" +
            "    }, {\n" +
            "      \"model_id\" : \"qwen-2.5-72b-instruct\",\n" +
            "      \"model_name\" : \"Qwen2.5 72B Instruct\",\n" +
            "      \"score\" : 0.958,\n" +
            "      \"rank\" : 9,\n" +
            "      \"is_current_model\" : false\n" +
            "    }, {\n" +
            "      \"model_id\" : \"deepseek-v2.5\",\n" +
            "      \"model_name\" : \"DeepSeek-V2.5\",\n" +
            "      \"score\" : 0.951,\n" +
            "      \"rank\" : 10,\n" +
            "      \"is_current_model\" : false\n" +
            "    }, {\n" +
            "      \"model_id\" : \"jamba-1.5-large\",\n" +
            "      \"model_name\" : \"Jamba 1.5 Large\",\n" +
            "      \"score\" : 0.87,\n" +
            "      \"rank\" : 32,\n" +
            "      \"is_current_model\" : true\n" +
            "    } ]\n" +
            "  }, {\n" +
            "    \"benchmark_id\" : \"mmlu\",\n" +
            "    \"benchmark_name\" : \"MMLU\",\n" +
            "    \"models\" : [ {\n" +
            "      \"model_id\" : \"gpt-5-2025-08-07\",\n" +
            "      \"model_name\" : \"GPT-5\",\n" +
            "      \"score\" : 0.925,\n" +
            "      \"rank\" : 1,\n" +
            "      \"is_current_model\" : false\n" +
            "    }, {\n" +
            "      \"model_id\" : \"o1-2024-12-17\",\n" +
            "      \"model_name\" : \"o1\",\n" +
            "      \"score\" : 0.918,\n" +
            "      \"rank\" : 2,\n" +
            "      \"is_current_model\" : false\n" +
            "    }, {\n" +
            "      \"model_id\" : \"gpt-4.5\",\n" +
            "      \"model_name\" : \"GPT-4.5\",\n" +
            "      \"score\" : 0.908,\n" +
            "      \"rank\" : 3,\n" +
            "      \"is_current_model\" : false\n" +
            "    }, {\n" +
            "      \"model_id\" : \"o1-preview\",\n" +
            "      \"model_name\" : \"o1-preview\",\n" +
            "      \"score\" : 0.908,\n" +
            "      \"rank\" : 3,\n" +
            "      \"is_current_model\" : false\n" +
            "    }, {\n" +
            "      \"model_id\" : \"qwen3-vl-235b-a22b-thinking\",\n" +
            "      \"model_name\" : \"Qwen3 VL 235B A22B Thinking\",\n" +
            "      \"score\" : 0.906,\n" +
            "      \"rank\" : 5,\n" +
            "      \"is_current_model\" : false\n" +
            "    }, {\n" +
            "      \"model_id\" : \"claude-3-5-sonnet-20240620\",\n" +
            "      \"model_name\" : \"Claude 3.5 Sonnet\",\n" +
            "      \"score\" : 0.904,\n" +
            "      \"rank\" : 6,\n" +
            "      \"is_current_model\" : false\n" +
            "    }, {\n" +
            "      \"model_id\" : \"claude-3-5-sonnet-20241022\",\n" +
            "      \"model_name\" : \"Claude 3.5 Sonnet\",\n" +
            "      \"score\" : 0.904,\n" +
            "      \"rank\" : 6,\n" +
            "      \"is_current_model\" : false\n" +
            "    }, {\n" +
            "      \"model_id\" : \"gpt-4.1-2025-04-14\",\n" +
            "      \"model_name\" : \"GPT-4.1\",\n" +
            "      \"score\" : 0.902,\n" +
            "      \"rank\" : 8,\n" +
            "      \"is_current_model\" : false\n" +
            "    }, {\n" +
            "      \"model_id\" : \"kimi-k2-0905\",\n" +
            "      \"model_name\" : \"Kimi K2 0905\",\n" +
            "      \"score\" : 0.902,\n" +
            "      \"rank\" : 8,\n" +
            "      \"is_current_model\" : false\n" +
            "    }, {\n" +
            "      \"model_id\" : \"gpt-oss-120b\",\n" +
            "      \"model_name\" : \"GPT OSS 120B\",\n" +
            "      \"score\" : 0.9,\n" +
            "      \"rank\" : 10,\n" +
            "      \"is_current_model\" : false\n" +
            "    }, {\n" +
            "      \"model_id\" : \"jamba-1.5-large\",\n" +
            "      \"model_name\" : \"Jamba 1.5 Large\",\n" +
            "      \"score\" : 0.812,\n" +
            "      \"rank\" : 48,\n" +
            "      \"is_current_model\" : true\n" +
            "    } ]\n" +
            "  }, {\n" +
            "    \"benchmark_id\" : \"mmlu-pro\",\n" +
            "    \"benchmark_name\" : \"MMLU-Pro\",\n" +
            "    \"models\" : [ {\n" +
            "      \"model_id\" : \"deepseek-r1-0528\",\n" +
            "      \"model_name\" : \"DeepSeek-R1-0528\",\n" +
            "      \"score\" : 0.85,\n" +
            "      \"rank\" : 1,\n" +
            "      \"is_current_model\" : false\n" +
            "    }, {\n" +
            "      \"model_id\" : \"deepseek-v3.2-exp\",\n" +
            "      \"model_name\" : \"DeepSeek-V3.2-Exp\",\n" +
            "      \"score\" : 0.85,\n" +
            "      \"rank\" : 1,\n" +
            "      \"is_current_model\" : false\n" +
            "    }, {\n" +
            "      \"model_id\" : \"glm-4.5\",\n" +
            "      \"model_name\" : \"GLM-4.5\",\n" +
            "      \"score\" : 0.846,\n" +
            "      \"rank\" : 3,\n" +
            "      \"is_current_model\" : false\n" +
            "    }, {\n" +
            "      \"model_id\" : \"qwen3-235b-a22b-thinking-2507\",\n" +
            "      \"model_name\" : \"Qwen3-235B-A22B-Thinking-2507\",\n" +
            "      \"score\" : 0.844,\n" +
            "      \"rank\" : 4,\n" +
            "      \"is_current_model\" : false\n" +
            "    }, {\n" +
            "      \"model_id\" : \"qwen3-vl-235b-a22b-thinking\",\n" +
            "      \"model_name\" : \"Qwen3 VL 235B A22B Thinking\",\n" +
            "      \"score\" : 0.838,\n" +
            "      \"rank\" : 5,\n" +
            "      \"is_current_model\" : false\n" +
            "    }, {\n" +
            "      \"model_id\" : \"deepseek-v3.1\",\n" +
            "      \"model_name\" : \"DeepSeek-V3.1\",\n" +
            "      \"score\" : 0.837,\n" +
            "      \"rank\" : 6,\n" +
            "      \"is_current_model\" : false\n" +
            "    }, {\n" +
            "      \"model_id\" : \"qwen3-235b-a22b-instruct-2507\",\n" +
            "      \"model_name\" : \"Qwen3-235B-A22B-Instruct-2507\",\n" +
            "      \"score\" : 0.83,\n" +
            "      \"rank\" : 7,\n" +
            "      \"is_current_model\" : false\n" +
            "    }, {\n" +
            "      \"model_id\" : \"qwen3-next-80b-a3b-thinking\",\n" +
            "      \"model_name\" : \"Qwen3-Next-80B-A3B-Thinking\",\n" +
            "      \"score\" : 0.827,\n" +
            "      \"rank\" : 8,\n" +
            "      \"is_current_model\" : false\n" +
            "    }, {\n" +
            "      \"model_id\" : \"kimi-k2-0905\",\n" +
            "      \"model_name\" : \"Kimi K2 0905\",\n" +
            "      \"score\" : 0.825,\n" +
            "      \"rank\" : 9,\n" +
            "      \"is_current_model\" : false\n" +
            "    }, {\n" +
            "      \"model_id\" : \"qwen3-vl-32b-thinking\",\n" +
            "      \"model_name\" : \"Qwen3 VL 32B Thinking\",\n" +
            "      \"score\" : 0.821,\n" +
            "      \"rank\" : 10,\n" +
            "      \"is_current_model\" : false\n" +
            "    }, {\n" +
            "      \"model_id\" : \"jamba-1.5-large\",\n" +
            "      \"model_name\" : \"Jamba 1.5 Large\",\n" +
            "      \"score\" : 0.535,\n" +
            "      \"rank\" : 65,\n" +
            "      \"is_current_model\" : true\n" +
            "    } ]\n" +
            "  }, {\n" +
            "    \"benchmark_id\" : \"truthfulqa\",\n" +
            "    \"benchmark_name\" : \"TruthfulQA\",\n" +
            "    \"models\" : [ {\n" +
            "      \"model_id\" : \"phi-3.5-moe-instruct\",\n" +
            "      \"model_name\" : \"Phi-3.5-MoE-instruct\",\n" +
            "      \"score\" : 0.775,\n" +
            "      \"rank\" : 1,\n" +
            "      \"is_current_model\" : false\n" +
            "    }, {\n" +
            "      \"model_id\" : \"granite-3.3-8b-instruct\",\n" +
            "      \"model_name\" : \"Granite 3.3 8B Instruct\",\n" +
            "      \"score\" : 0.6686,\n" +
            "      \"rank\" : 2,\n" +
            "      \"is_current_model\" : false\n" +
            "    }, {\n" +
            "      \"model_id\" : \"phi-4-mini\",\n" +
            "      \"model_name\" : \"Phi 4 Mini\",\n" +
            "      \"score\" : 0.664,\n" +
            "      \"rank\" : 3,\n" +
            "      \"is_current_model\" : false\n" +
            "    }, {\n" +
            "      \"model_id\" : \"phi-3.5-mini-instruct\",\n" +
            "      \"model_name\" : \"Phi-3.5-mini-instruct\",\n" +
            "      \"score\" : 0.64,\n" +
            "      \"rank\" : 4,\n" +
            "      \"is_current_model\" : false\n" +
            "    }, {\n" +
            "      \"model_id\" : \"llama-3.1-nemotron-70b-instruct\",\n" +
            "      \"model_name\" : \"Llama 3.1 Nemotron 70B Instruct\",\n" +
            "      \"score\" : 0.5863,\n" +
            "      \"rank\" : 5,\n" +
            "      \"is_current_model\" : false\n" +
            "    }, {\n" +
            "      \"model_id\" : \"qwen-2.5-14b-instruct\",\n" +
            "      \"model_name\" : \"Qwen2.5 14B Instruct\",\n" +
            "      \"score\" : 0.584,\n" +
            "      \"rank\" : 6,\n" +
            "      \"is_current_model\" : false\n" +
            "    }, {\n" +
            "      \"model_id\" : \"jamba-1.5-large\",\n" +
            "      \"model_name\" : \"Jamba 1.5 Large\",\n" +
            "      \"score\" : 0.583,\n" +
            "      \"rank\" : 7,\n" +
            "      \"is_current_model\" : true\n" +
            "    }, {\n" +
            "      \"model_id\" : \"granite-4.0-tiny-preview\",\n" +
            "      \"model_name\" : \"IBM Granite 4.0 Tiny Preview\",\n" +
            "      \"score\" : 0.581,\n" +
            "      \"rank\" : 8,\n" +
            "      \"is_current_model\" : false\n" +
            "    }, {\n" +
            "      \"model_id\" : \"qwen-2.5-32b-instruct\",\n" +
            "      \"model_name\" : \"Qwen2.5 32B Instruct\",\n" +
            "      \"score\" : 0.578,\n" +
            "      \"rank\" : 9,\n" +
            "      \"is_current_model\" : false\n" +
            "    }, {\n" +
            "      \"model_id\" : \"command-r-plus-04-2024\",\n" +
            "      \"model_name\" : \"Command R+\",\n" +
            "      \"score\" : 0.563,\n" +
            "      \"rank\" : 10,\n" +
            "      \"is_current_model\" : false\n" +
            "    } ]\n" +
            "  } ],\n" +
            "  \"comparison_model\" : {\n" +
            "    \"model_id\" : \"gemini-3-pro-preview\",\n" +
            "    \"name\" : \"Gemini 3 Pro\",\n" +
            "    \"organization_name\" : \"Google\",\n" +
            "    \"release_date\" : \"2025-11-18\",\n" +
            "    \"announcement_date\" : \"2025-11-18\",\n" +
            "    \"knowledge_cutoff\" : \"2025-01-31\",\n" +
            "    \"param_count\" : null,\n" +
            "    \"multimodal\" : true,\n" +
            "    \"license\" : {\n" +
            "      \"name\" : \"Proprietary\",\n" +
            "      \"allow_commercial\" : false\n" +
            "    },\n" +
            "    \"benchmarks\" : {\n" +
            "      \"aime-2025\" : 1.0,\n" +
            "      \"arc-agi-v2\" : 0.311,\n" +
            "      \"charxiv-r\" : 0.814,\n" +
            "      \"facts-grounding\" : 0.705,\n" +
            "      \"global-piqa\" : 0.934,\n" +
            "      \"gpqa\" : 0.919,\n" +
            "      \"humanity's-last-exam\" : 0.458,\n" +
            "      \"livecodebench-pro\" : 2439.0,\n" +
            "      \"matharena-apex\" : 0.234,\n" +
            "      \"mmmlu\" : 0.918,\n" +
            "      \"mmmu-pro\" : 0.81,\n" +
            "      \"mrcr-v2-(8-needle)\" : 0.263,\n" +
            "      \"omnidocbench-1.5\" : 0.115,\n" +
            "      \"screenspot-pro\" : 0.727,\n" +
            "      \"simpleqa\" : 0.721,\n" +
            "      \"swe-bench-verified\" : 0.762,\n" +
            "      \"t2-bench\" : 0.854,\n" +
            "      \"terminal-bench-2\" : 0.542,\n" +
            "      \"vending-bench-2\" : 5478.16,\n" +
            "      \"videommmu\" : 0.876\n" +
            "    },\n" +
            "    \"provider\" : {\n" +
            "      \"name\" : \"Google\",\n" +
            "      \"input_cost\" : 2.0,\n" +
            "      \"output_cost\" : 12.0,\n" +
            "      \"max_input_tokens\" : 1048576,\n" +
            "      \"max_output_tokens\" : 65536,\n" +
            "      \"modalities\" : {\n" +
            "        \"input\" : {\n" +
            "          \"text\" : true,\n" +
            "          \"image\" : false,\n" +
            "          \"audio\" : false,\n" +
            "          \"video\" : false\n" +
            "        },\n" +
            "        \"output\" : {\n" +
            "          \"text\" : true,\n" +
            "          \"image\" : false,\n" +
            "          \"audio\" : false,\n" +
            "          \"video\" : false\n" +
            "        }\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}";
}
