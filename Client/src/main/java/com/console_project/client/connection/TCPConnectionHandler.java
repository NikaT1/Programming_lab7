package com.console_project.client.connection;

import com.console_project.shared.connection_model.CommandResponse;
import com.console_project.shared.connection_model.UserRequest;
import com.console_project.shared.serialization.ObjectSerializer;
import com.console_project.shared.serialization.ObjectSerializerImpl;
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class TCPConnectionHandler<T> implements ConnectionHandler<T> {

    private final String HOST = "localhost";
    private final int MAX_RESPONSE_BUFFER_SIZE = 100000;

    private final int port;
    private SocketChannel socketChannel;
    private final ObjectSerializer<UserRequest<T>> requestSerializer = new ObjectSerializerImpl<>();
    private final ObjectSerializer<CommandResponse> responseSerializer = new ObjectSerializerImpl<>();

    public TCPConnectionHandler(int port) throws IOException {
        this.port = port;
        initConnection(HOST, port);
    }

    private void initConnection(String host, int port) throws IOException {
        socketChannel = SocketChannel.open(new InetSocketAddress(host, port));
        socketChannel.configureBlocking(true);
    }

    @Override
    public CommandResponse sendCommand(UserRequest<T> userRequest) throws IOException {
        byte[] requestBuffer = requestSerializer.serializeObject(userRequest);
        socketChannel.write(ByteBuffer.wrap(requestBuffer));
        ByteBuffer buffer = ByteBuffer.wrap(new byte[MAX_RESPONSE_BUFFER_SIZE]);
        socketChannel.read(buffer);
        return responseSerializer.deserializeObject(buffer.array());
    }

    @Override
    public void reconnection() throws IOException {
        socketChannel = SocketChannel.open(new InetSocketAddress(HOST, port));
    }

    @SneakyThrows
    @Override
    public void close() {
        if (socketChannel != null && socketChannel.isOpen()) {
            socketChannel.close();
        }
    }
}
