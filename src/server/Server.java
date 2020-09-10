package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {


    public static void main(String[] args) {
        Server server = new Server();
    }

    public Server() {
        ServerSocket server = null; //Локалочка
        Socket socket = null;

        try {
            // порт, который будет прослушивать наш сервер
            server = new ServerSocket(3443);
            System.out.println("Сервер работает");
            socket = server.accept();
            System.out.println("Подключен новый пользователь");
            // входящий поток
            Scanner in =  new Scanner(socket.getInputStream());
            // исходящий поток
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner sc = new Scanner(System.in);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        System.out.println("Сервер, напишите свое сообщение");
                        String msg = sc.nextLine();
                        System.out.println("Сообщение успешно отправлено");
                        out.println(msg);
                    }
                }
            }).start();
            while (true) {
                String msg = in.nextLine();
                if (msg.equals("/close")) break;
                System.out.println("Пользователь: " + msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close(); // закрываем розетку клиента
                server.close(); // закрываем розетку сервера
                System.out.println("Сервер закрыт");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

