package serverTest;

import com.google.gson.JsonPrimitive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import server.Server;
import server.command.CommandHandler;
import server.database.Database;
import server.model.ClientRequest;
import server.model.Response;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommandHandlerTest {
    @Mock
    private Database database;
    @Mock
    private Server server;
    @Mock
    private ClientRequest request;

    private CommandHandler commandHandler;

    @BeforeEach
    void setUp(){
        commandHandler = new CommandHandler(database,server);
    }

    @Test
    void shouldReturnEmptyResponseForUnknownCommand() {
        // given
        given(request.getType()).willReturn("unknown");
        given(request.getKey()).willReturn(new JsonPrimitive("someKey"));

        // when
        var response = commandHandler.executeRequest(request);

        // then
        assertEquals(Response.EMPTY, response);
    }
    @Test
    void shouldReturnNull() {
        // given
        given(request.getType()).willReturn("delete");
        given(request.getKey()).willReturn(new JsonPrimitive("someKey"));
        given(database.delete(anyList())).willReturn(null);

        // when
        var response = commandHandler.executeRequest(request);

        // then
        verifyNoInteractions(server);
        assertNull(response);
    }
    @Test
    void shouldReturnOkResponse() {
        // given
        given(request.getType()).willReturn("get");
        given(request.getKey()).willReturn(new JsonPrimitive("someKey"));
        given(database.get(anyList())).willReturn(Response.builder().response("OK").build());

        // when
        var response = commandHandler.executeRequest(request);

        // then
        verifyNoInteractions(server);
        assertEquals(Response.OK, response);
    }
    @Test
    void shouldReturnERRORResponse() {
        // given
        given(request.getType()).willReturn("delete");
        given(request.getKey()).willReturn(new JsonPrimitive("someKey"));
        given(database.delete(anyList())).willReturn(Response.builder()
                .response("ERROR")
                        .reason("No such key")
                .build());

        // when
        var response = commandHandler.executeRequest(request);

        // then
        verifyNoInteractions(server);
        assertEquals(Response.ERROR, response);
    }

    @Test
    void shouldReturnOkResponseForExit() {
        // given
        given(request.getType()).willReturn("exit");

        // when
        var response = commandHandler.executeRequest(request);

        // then
        verify(server,times(1)).shutdown();
        assertEquals(Response.OK, response);
    }
}