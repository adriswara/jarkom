
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionListener;
//import java.awt.TextField;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.EventListener;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ClientChApp extends Application {

    Button button = new Button("Send");
    Button disconnect = new Button("Disconnect");
    TextArea textArea = new TextArea();
    InetAddress address = InetAddress.getLocalHost();
    String hostname  = address.getHostName();
 
    
    TextField textField = new TextField(hostname + " : ");

    public ClientChApp() throws UnknownHostException {

    }

    public void disconnectButton() throws SocketException {
        DatagramSocket clientSocket = new DatagramSocket();
        clientSocket.close();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("ChatApp");
        //send button
        button.setOnAction(action -> {
            textArea.appendText(textField.getText());
            //System.out.println(textField.getText());
        });
        disconnect.setOnAction(action -> {
            try {
                disconnectButton();
            } catch (SocketException ex) {
                Logger.getLogger(ClientChApp.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        //generateTampilan
        HBox box = new HBox(textArea, textField, button, disconnect);
        //ukuran screen
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();
        //generate 

        Scene scene = new Scene(box, width, height);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private void sendChat() throws SocketException, UnknownHostException, IOException {
        InetAddress ip = InetAddress.getByName("Localhost");
        DatagramSocket clientSocket = new DatagramSocket();
        while (true) {
            byte[] sendBuffer = new byte[1024];
            byte[] receiveBuffer = new byte[1024];
            textArea.setText("Client + " + textArea);
            sendBuffer = textField.getText().toString().getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, ip, 443);
            clientSocket.send(sendPacket);

            DatagramPacket recievePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            clientSocket.receive(recievePacket);
            String serverData = new String(recievePacket.getData());
            textArea.setText("Server: " + serverData);
        }
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
