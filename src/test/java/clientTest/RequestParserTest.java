package clientTest;

import static org.junit.jupiter.api.Assertions.*;

import client.request.Request;
import client.request.RequestParser;
import com.beust.jcommander.ParameterException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


class RequestParserTest {

    @Test
    void shouldParseTypeKeyAndValueArgs() {
        // given
        String[] args = {"-t", "set", "-k", "name", "-v", "John"};

        // when
        Request request = RequestParser.parseArgs(args);

        // then
        assertEquals("set", request.getType());
        assertEquals("name", request.getKey());
        assertEquals("John", request.getValue());
        assertNull(request.getFileName());
    }

    @Test
    void shouldParseInputFileArgument() {
        // given
        String[] args = {"-in", "test.json"};

        // when
        Request request = RequestParser.parseArgs(args);

        // then
        assertEquals("test.json", request.getFileName());
    }
    @ParameterizedTest
    @ValueSource(strings = {"in","t","k"})
    void shouldThrowExceptionWithEmptyFlag(String flag) {
        String[] args = {flag};

        assertThrows(ParameterException.class, () -> {
            RequestParser.parseArgs(args);
        });
    }
}
