package us.cleansite.very.tinyjwt.json;

import us.cleansite.very.tinyjwt.exception.JsonDeserializationException;

public interface Deserializer {
    <T> T deserialize(String data, Class<T> cl) throws JsonDeserializationException;
}