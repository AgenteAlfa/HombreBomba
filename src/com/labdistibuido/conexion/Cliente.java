package com.labdistibuido.conexion;

import com.labdistibuido.escenario.Escenario;
import com.labdistibuido.escenario.Ventana;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.Buffer;
import java.util.Scanner;

public class Cliente extends Socket {
    public boolean bomba = false;
    InputStream BIS;
    OutputStream BOS;
    ObjectInputStream OIS;
    ObjectOutputStream OOS;
    private boolean ventana_apagada = true;
    public HiloLector HL;
    public HiloEscritor HE;

    public ObjectInputStream getOIS() {
        return OIS;
    }

    public ObjectOutputStream getOOS() {
        return OOS;
    }

    public Cliente() throws IOException {
        super(Configuracion.IP, Configuracion.PUERTO);
        Escenario.Inicializar(10,10);
        OIS = new ObjectInputStream(getInputStream());
        BOS = getOutputStream();
    }
    public void UnirsePartida()
    {
        HL = new HiloLector();
        HE = new HiloEscritor();

        HL.start();
        HE.start();
        System.out.println("Te haz unido a la partida!");
    }
    public Cliente(Socket S) throws IOException {
        BIS = new BufferedInputStream(S.getInputStream());
        OOS = new ObjectOutputStream(S.getOutputStream());
    }

    public InputStream getBIS() {
        return BIS;
    }

    public OutputStream getBOS() {
        return BOS;
    }

    private class HiloLector extends Thread
    {
        private boolean Encendido = true;
        @Override
        public void run() {
            try
            {
                while (Encendido)
                //while (OIS.available() > 0)
                {
                    Escenario.getMov().setArr((int[][]) OIS.readObject());
                    Escenario.getObj().setArr((int[][]) OIS.readObject());

                    //System.out.println("MOV");
                    //Escenario.getMov().Imprimir();


                    /*
                    System.out.println("OBJ");
                    Escenario.getObj().Imprimir();
                    */
                }


            }catch (Exception E)
            {
                System.out.println(E.getMessage());
            }
        }
        public void Apagar(){
            Encendido = false;
        }
    }
    private class HiloEscritor extends Thread implements KeyListener
    {


        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            System.out.println("CODIGO : " + e.getKeyCode());
            try {
                BOS.write(e.getKeyCode());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {


        }


    }



}