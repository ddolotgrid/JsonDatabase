package serverTest;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
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

    @ParameterizedTest
    @ValueSource(strings = {"unknown", ""," ","update","fetch"})
    void shouldReturnEmptyResponseForUnknownCommand(String unknownCommand) {
        // given
        given(request.getType()).willReturn(unknownCommand);

        // when
        var response = commandHandler.executeRequest(request);

        // then
        assertEquals(Response.EMPTY, response);
    }

    @ParameterizedTest
    @ValueSource(strings = {"get","GET","gEt","GEt"})
    @DisplayName("get returns OK response and it's case insensitive")
    void GetShouldReturnOkResponse(String command) {
        // given
        given(request.getType()).willReturn(command);
        given(request.getKey()).willReturn(new JsonPrimitive("someKey"));
        given(database.get(anyList())).willReturn(Response.OK);

        // when
        var response = commandHandler.executeRequest(request);

        // then
        verifyNoInteractions(server);
        assertEquals(Response.OK, response);
    }

    @ParameterizedTest
    @ValueSource(strings = {"delete","DELETE","DElete","deLeTE"})
    @DisplayName("delete returns OK response and it's case insensitive")
    void DeleteShouldReturnOkResponse(String command) {
        // given
        given(request.getType()).willReturn(command);
        given(request.getKey()).willReturn(new JsonPrimitive("someKey"));
        given(database.delete(anyList())).willReturn(Response.OK);

        // when
        var response = commandHandler.executeRequest(request);

        // then
        verifyNoInteractions(server);
        assertEquals(Response.OK, response);
    }


    @ParameterizedTest
    @ValueSource(strings = {"set","SET","sEt","SEt"})
    @DisplayName("set returns OK response and it's case insensitive")
    void SetShouldReturnOkResponse(String command) {
        // given
        JsonElement key = new JsonPrimitive("someKey");
        JsonElement value = new JsonPrimitive("someValue");

        given(request.getType()).willReturn(command);
        given(request.getKey()).willReturn(key);
        given(request.getValue()).willReturn(value);
        given(database.set(anyList(), eq(value))).willReturn(Response.OK);

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

        given(database.delete(anyList())).willReturn(Response.ERROR);

        // when
        var response = commandHandler.executeRequest(request);

        // then
        verifyNoInteractions(server);
        assertEquals(Response.ERROR, response);
    }

    @ParameterizedTest
    @ValueSource(strings = {"exit","EXIT","EXiT"})
    void shouldReturnOkResponseForExit(String command){
        // given
        given(request.getType()).willReturn(command);

        // when
        var response = commandHandler.executeRequest(request);

        // then
        verify(server,times(1)).shutdown();
        assertEquals(Response.OK, response);
    }
}