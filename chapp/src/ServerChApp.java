
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
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ServerChApp extends Application {

    Button button = new Button("Send");
    Button disconnect = new Button("Disconnect");
    TextArea textArea = new TextArea();
    InetAddress address = InetAddress.getLocalHost();
    String hostname = address.getHostName();

    TextField textField = new TextField();
    String newLine = "\n";//buat new line setiap post text area
    Boolean connected;

    public ServerChApp() throws UnknownHostException {
        connected = true;
    }

    public void disconnectButton() throws SocketException {
        DatagramSocket serverSocket = new DatagramSocket();
        serverSocket.close();
        textArea.appendText("Disconnected,ended by user");
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("ChatApp(Server)");
        //send button

        button.setOnAction(action -> {
            textArea.appendText(hostname + " : " + textField.getText());
            textArea.appendText(newLine);
            try {
                sendChat();
                //System.out.println(textField.getText());
            } catch (UnknownHostException ex) {
                Logger.getLogger(ServerChApp.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ServerChApp.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
//        disconnect.setOnAction(action -> {
//            try {
//                disconnectButton();
//            } catch (SocketException ex) {
//                Logger.getLogger(ClientChApp.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        });

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
        DatagramSocket serverSocket = new DatagramSocket(443);
        while (true) {
            byte[] sendBuffer = new byte[1024];
            byte[] receiveBuffer = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            serverSocket.receive(receivePacket);
            InetAddress IP = receivePacket.getAddress();
            int portNo = receivePacket.getPort();
            String clientData = new String(receivePacket.getData());
            textArea.setText("\nClient : + " + clientData);
            textArea.setText("\nServer : ");
            //send value from field
            DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, IP, portNo);
            serverSocket.send(sendPacket);
            if (connected == false) {
                disconnectButton();
                break;
            }
        }
    }

    public static void main(String[] args) throws SocketException, IOException {

        Application.launch(args);
    }
}
