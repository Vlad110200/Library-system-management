package com.vladl.library.storage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonStorage {

        private static final ObjectMapper mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        public static <T> void save(String fileName, List<T> data){
            try{
                mapper.writerWithDefaultPrettyPrinter()
                        .writeValue(new File(fileName), data);
            } catch (IOException e) {
                throw new RuntimeException("Failed to save file:" + fileName, e);
            }
        }

        public static <T> List<T> load(String fileName, Class<T> clazz){
            try{
                File file = new File(fileName);

                if(!file.exists() || file.length() == 0) {
                    return new ArrayList<>();
                }

                CollectionType type = mapper.getTypeFactory()
                        .constructCollectionType(List.class, clazz);

                return mapper.readValue(file, type);

            } catch (IOException e) {
                throw new RuntimeException("Failed to load file:" + fileName, e);
            }
        }
}
