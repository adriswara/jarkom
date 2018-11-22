/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 *
 * @author Yogi
 */
public class ChatUDPController implements Initializable {

    @FXML
    private TextArea txtChat;
    @FXML
    private TextField txtArea;
    @FXML
    private Button sendChat;
    @FXML
    private Button dcButton;

    @FXML
    private void handleButtonAction(ActionEvent event) throws UnknownHostException, IOException {
        sendChat();
    }

    @FXML
    private void disconnectButton(ActionEvent event) throws SocketException {
        DatagramSocket clientSocket = new DatagramSocket();
        clientSocket.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    private void sendChat() throws SocketException, UnknownHostException, IOException {
        InetAddress ip = InetAddress.getByName("Localhost");
        DatagramSocket clientSocket = new DatagramSocket();
        while (true) {
            byte[] sendBuffer = new byte[1024];
            byte[] receiveBuffer = new byte[1024];
            txtChat.setText("Client + " + txtChat);
            sendBuffer = txtArea.getText().toString().getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, ip, 443);
            clientSocket.send(sendPacket);

            DatagramPacket recievePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            clientSocket.receive(recievePacket);
            String serverData = new String(recievePacket.getData());
            txtChat.setText("Server: " + serverData);
        }
    }

}
