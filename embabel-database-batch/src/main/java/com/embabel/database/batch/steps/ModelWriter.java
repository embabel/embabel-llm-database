package com.embabel.database.batch.steps;

import com.embabel.database.core.repository.ModelRepository;
import com.embabel.database.core.repository.domain.Model;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class ModelWriter implements ItemWriter<Model> {

    @Autowired
    ModelRepository modelRepository;

    @Value("${embabel.database.batch.pause:true")
    boolean pause = true;

    @Override
    public void write(Chunk<? extends Model> chunk) throws Exception {
        chunk.getItems().forEach(c -> {
            modelRepository.save(c);
        });
        //inject a pause
        if (pause) {
            Thread.sleep(500);//half second pause
        }
    }
}
