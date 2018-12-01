package us.cleansite.very.tinyjwt.json.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import us.cleansite.very.tinyjwt.exception.JsonSerializationException;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
public class SerializerImplTest {

    @Test
    public void testSerialize() {
        String result = new SerializerImpl().serialize(new Data("string", 1));
        assertEquals("{\"string\":\"string\",\"integer\":1}", result);
    }

    /**
     * SuppressWarning("unchecked") required to suppress
     * "Unchecked generics array creation for varargs parameter" during mocking
     */
    @SuppressWarnings("unchecked")
    @PrepareForTest(Mapper.class)
    @Test(expected = JsonSerializationException.class)
    public void testSerializeException() throws Exception {
        String string = "string";
        ObjectMapper mapper = mock(ObjectMapper.class);
        when(mapper.writeValueAsString(string)).thenThrow(JsonProcessingException.class);
        mockStatic(Mapper.class);
        when(Mapper.getInstance()).thenReturn(mapper);
        new SerializerImpl().serialize(string);
    }
}
