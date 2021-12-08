package source;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class WindowsEventDemo extends JFrame implements WindowListener, WindowFocusListener, WindowStateListener{

    //IIIS,IIC                                                              min26
    //WFL: foco (ilumina) cual opcion voy a seleccionar
    //WSL: acciones sobre la ventana (maximixar, minimizar,..)

    static final String newline = System.getProperty("line.separator");         //enter
    static final String space = "   ";
    static WindowsEventDemo frame = new WindowsEventDemo("WindowEventDemo");
    JTextArea display;

    //--------------------------------------------------------------------------------------------------------

    public static void main(String[] args) {

        try{
            UIManager.setLookAndFeel("java.swing.plaf.metal.MetalLookAndFeel");
        } catch (UnsupportedLookAndFeelException ex){
            ex.printStackTrace();
        }catch(IllegalAccessException ex){
            ex.printStackTrace();
        }catch (InstantiationException ex){
            ex.printStackTrace();
        }catch (ClassNotFoundException ex){
            ex.printStackTrace();
        }

        UIManager.put("swing.boldMetal", Boolean.FALSE);
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }
    //------------------------------------------------------------------------------------------------------

    private static void createAndShowGUI(){
        //create and set up the window
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        //set up the content pane
        frame.addComponentsToPane();

        //display the window
        frame.pack();

        //frame.setSize(300,300);
        frame.setVisible(true);

    }

    private void addComponentsToPane(){
        display = new JTextArea();
        display.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(display);
        scrollPane.setPreferredSize(new Dimension(500,500));
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        addWindowListener(this);
        addWindowFocusListener(this);
        addWindowStateListener(this);

        checkWM();
    }

    public WindowsEventDemo(String name){
        super(name);
    }

    public void checkWM(){
        Toolkit tk = frame.getToolkit();

        if(!(tk.isFrameStateSupported(Frame.ICONIFIED))){
            displayMessage("Your window manager doesn't support ICONIFIED.");
        }else{
            displayMessage("Your window manager supports ICONIFIED.");
        }

        if(!(tk.isFrameStateSupported(Frame.MAXIMIZED_VERT))){
            displayMessage("Your window manager doesn't support MAXIMIZED_VERT.");
        }else{
            displayMessage("Your window manager supports MAXIMIZED_VERT.");
        }

        if(!(tk.isFrameStateSupported(Frame.MAXIMIZED_HORIZ))){
            displayMessage("Your window manager doesn't support MAXIMIZED_HORIZ.");
        }else{
            displayMessage("Your window manager supports MAXIMIZED_HORIZ.");
        }

        if(!(tk.isFrameStateSupported(Frame.MAXIMIZED_BOTH))){
            displayMessage("Your window manager doesn't support MAXIMIZED_BOTH.");
        }else{
            displayMessage("Your window manager supports MAXIMIZED_BOTH.");
        }

    }

    public void windowClosing(WindowEvent e){
        displayMessage("WindowListener method called: windowClosing.");

        ActionListener task = new ActionListener(){
            boolean alreadyDisposed = false;

            public void actionPerformed(ActionEvent e){
                if(frame.isDisplayable()){
                    alreadyDisposed = true;
                    frame.dispose();
                }
            }
        };
        Timer timer = new Timer(500, task);
        timer.setInitialDelay(2000);
        timer.setRepeats(false);
        timer.start();
    }

    public void windowClosed(WindowEvent e){
        displayMessage("Window Listener method called: windowClosed");
    }

    public void windowOpened(WindowEvent e){
        displayMessage("Window Listener method called: windowOpened");
    }

    public void windowIconified(WindowEvent e){
        displayMessage("Window Listener method called: windowIconified");
    }

    public void windowDeiconified(WindowEvent e){
        displayMessage("Window Listener method called: windowDeiconified");
    }

    public void windowActivated(WindowEvent e){
        displayMessage("Window Listener method called: windowActivated");
    }

    public void windowDeactivated(WindowEvent e){
        displayMessage("Window Listener method called: windowDeactivated");
    }

    public void windowGainedFocus(WindowEvent e){
        displayMessage("Window Listener method called: windowGainedFocus");
    }

    public void windowLostFocus(WindowEvent e){
        displayMessage("Window Listener method called: windowLostFocus");
    }

    public void windowStateChanged(WindowEvent e){
        displayStateMessage("Window Listener method called: windowStateChanged",e);
    }

    private void displayMessage(String msg){
        display.append(msg+ newline);
        System.out.println(msg);
    }

    private void displayStateMessage(String prefix,WindowEvent e){
        int state = e.getNewState();
        int oldState = e.getOldState();
        String msg = prefix
                +newline + space
                +"New state: "
                +convertStateToString(state)
                +newline + space
                +"Old State: "
                +convertStateToString(oldState);
        displayMessage(msg);

    }

    private String convertStateToString(int state){
        if(state== Frame.NORMAL){
            return "NORMAL";
        }
        String strState=" ";

        if((state & Frame.ICONIFIED) !=0){
            strState+= "ICONIFIED";
        }

        if((state & Frame.MAXIMIZED_BOTH) == Frame.MAXIMIZED_BOTH){
            strState+= "MAXIMIZED_BOTH";
        } else {

            if((state & Frame.MAXIMIZED_VERT) !=0){
                strState+= "MAXIMIZED_VERT";
            }

            if((state & Frame.MAXIMIZED_HORIZ) !=0){
                strState+= "MAXIMIZED_HORIZ";
            }
        }

        if("".equals(strState)){
            strState+="UNKNOWN";
        }
        return strState.trim();
    }


}
