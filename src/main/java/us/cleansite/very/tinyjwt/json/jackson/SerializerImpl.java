package us.cleansite.very.tinyjwt.json.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import us.cleansite.very.tinyjwt.exception.JsonSerializationException;
import us.cleansite.very.tinyjwt.json.Serializer;

public class SerializerImpl implements Serializer {
    @Override
    public String serialize(Object object) {
        try {
            return Mapper.getInstance().writeValueAsString(object);
        }
        catch(JsonProcessingException jpe) {
            throw new JsonSerializationException(jpe);
        }
    }
}
