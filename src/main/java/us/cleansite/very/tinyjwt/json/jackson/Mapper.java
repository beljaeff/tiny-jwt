package us.cleansite.very.tinyjwt.json.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

class Mapper {
    private static class Holder {
        private static final ObjectMapper INSTANCE = new ObjectMapper();
        static {
            // Don't serialize null fields
            INSTANCE.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        }
    }

    static ObjectMapper getInstance() {
        return Holder.INSTANCE;
    }
}