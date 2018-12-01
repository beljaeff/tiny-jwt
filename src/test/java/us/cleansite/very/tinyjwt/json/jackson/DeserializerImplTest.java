package us.cleansite.very.tinyjwt.json.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import us.cleansite.very.tinyjwt.exception.JsonDeserializationException;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
public class DeserializerImplTest {

    @Test
    public void testSerialize() {
        String strData = "{\"string\":\"string\",\"integer\":1}";
        Data data = new DeserializerImpl().deserialize(strData, Data.class);
        assertEquals("string", data.getString());
        assertEquals(Integer.valueOf(1), data.getInteger());
    }

    /**
     * SuppressWarning("unchecked") required to suppress
     * "Unchecked generics array creation for varargs parameter" during mocking
     */
    @SuppressWarnings("unchecked")
    @PrepareForTest(Mapper.class)
    @Test(expected = JsonDeserializationException.class)
    public void testSerializeException() throws IOException {
        ObjectMapper mapper = mock(ObjectMapper.class);
        when(mapper.readValue((String)null, Object.class)).thenThrow(IOException.class);
        mockStatic(Mapper.class);
        when(Mapper.getInstance()).thenReturn(mapper);
        new DeserializerImpl().deserialize(null, Object.class);
    }
}
