package us.cleansite.very.tinyjwt.json.jackson;

import us.cleansite.very.tinyjwt.exception.JsonDeserializationException;
import us.cleansite.very.tinyjwt.json.Deserializer;

import java.io.IOException;

public class DeserializerImpl implements Deserializer {
    @Override
    public <T> T deserialize(String data, Class<T> cl) {
        try {
            return Mapper.getInstance().readValue(data, cl);
        }
        catch(IOException ioe) {
            throw new JsonDeserializationException(ioe);
        }
    }
}
