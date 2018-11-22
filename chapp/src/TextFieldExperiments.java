
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class TextFieldExperiments extends Application {

    Button button = new Button("SEND");
    TextArea textArea = new TextArea();
    TextField textField = new TextField();

    @Override

    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("ChatApp");

        button.setOnAction(action -> {
            textArea.appendText(textField.getText());
            //System.out.println(textField.getText());
        });

        HBox hbox = new HBox(textArea, textField, button);

        Scene scene = new Scene(hbox, 1000, 1000);
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
