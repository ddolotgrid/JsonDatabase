package clientTest;

import static org.junit.jupiter.api.Assertions.*;

import client.request.Request;
import client.request.RequestParser;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RequestParserTest {

    @Test
    void shouldParseTypeKeyAndValueArgs() {
        // given
        String[] args = {"-t", "set", "-k", "name", "-v", "John"};

        // when
        Request request = RequestParser.parseArgs(args);

        // then
        assertEquals("set", request.type);
        assertEquals("name", request.key);
        assertEquals("John", request.value);
        assertNull(request.fileName);
    }

    @Test
    void shouldParseInputFileArgument() {
        // given
        String[] args = {"-in", "test.json"};

        // when
        Request request = RequestParser.parseArgs(args);

        // then
        assertEquals("test.json", request.fileName);
    }
}
