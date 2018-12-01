package us.cleansite.very.tinyjwt.json;

import us.cleansite.very.tinyjwt.exception.JsonSerializationException;

public interface Serializer {
    String serialize(Object object) throws JsonSerializationException;
}