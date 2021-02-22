package com.labdistibuido.Conexion;

import com.labdistibuido.ConexionAnterior.ConexionConfiguracion;
import com.labdistibuido.Escenario.Mapa.Escenario;
import com.labdistibuido.Escenario.Ventana.Constantes;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;

public class Nodo {
    private Socket SocketConexion , SocketJuego;
    private ObjectInputStream OISConexion , OISJuego;
    private ObjectOutputStream OOSConexion , OOSJuego;

    private ArrayList <String> Participantes;

    private Distribuidor mDistribuidor;

    private Hilo_Conexion Conexion ;
    private Hilo_Juego Juego;


    public Nodo(int Tiempo) throws IOException {

        mDistribuidor = new Distribuidor(true,Tiempo);
        Conectar();
    }
    public Nodo() throws IOException {
Conectar();
    }
    public Nodo(Socket sjuego , Socket sconexion)
    {
        SocketJuego = sjuego; SocketConexion = sconexion;

        try {
            OISConexion = new ObjectInputStream(SocketConexion.getInputStream());
            OISJuego = new ObjectInputStream(SocketJuego.getInputStream());
            OOSConexion = new ObjectOutputStream(SocketConexion.getOutputStream());
            OOSJuego = new ObjectOutputStream(SocketJuego.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void Conectar()
    {




        /*try {
            SocketAddress
                    DirConex = new InetSocketAddress(Configuracion.SERVER_IP,Configuracion.PUERTO_CONEXION),
                    DirJuego = new InetSocketAddress(Configuracion.SERVER_IP,Configuracion.PUERTO_JUEGO);
            SocketConexion = new Socket();
            SocketJuego = new Socket();
            SocketConexion.connect(DirConex);
            SocketJuego.connect(DirJuego);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        try {
            SocketConexion = new Socket(Configuracion.SERVER_IP,Configuracion.PUERTO_CONEXION);
            SocketJuego = new Socket(Configuracion.SERVER_IP,Configuracion.PUERTO_JUEGO);
            System.out.println("NODO : Me conecte");
        } catch (IOException e) {
            e.printStackTrace();
        }



        try {
            OOSConexion = new ObjectOutputStream(SocketConexion.getOutputStream());
            OOSJuego = new ObjectOutputStream(SocketJuego.getOutputStream());

            OISConexion = new ObjectInputStream(SocketConexion.getInputStream());
            OISJuego = new ObjectInputStream(SocketJuego.getInputStream());


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Empezar()
    {
        Conexion = new Hilo_Conexion();
        Juego = new Hilo_Juego();

        Conexion.start();
        Juego.start();
    }


    public ObjectInputStream getOISConexion() {
        return OISConexion;
    }

    public ObjectInputStream getOISJuego() {
        return OISJuego;
    }

    public ObjectOutputStream getOOSConexion() {
        return OOSConexion;
    }

    public ObjectOutputStream getOOSJuego() {
        return OOSJuego;
    }

    private void Reconectar()
    {



    }


    private void Cerrar()
    {

        try {
            OISConexion.close();
            OISJuego.close();
            OOSConexion.close();
            OOSJuego.close();
            SocketJuego.close();
            SocketConexion.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private class Hilo_Juego extends Thread
    {
        public boolean[] SeComunicaron = new boolean[5];
        private boolean encendido = true;
        public void Apagar()
        {
            encendido = false;

        }
        @Override
        public void run() {
            //while (encendido && Escenario.getObj().Jugable())
            while (encendido )
            {

            }
        }
    }
    private class Hilo_Conexion extends Thread
    {
        private boolean encendido = true;
        public void Apagar()
        {
            encendido = false;

        }

        @Override
        public void run() {
            //Acciones antes del juego
            try {
                OOSConexion.writeObject(Configuracion.MI_IP);
                OISConexion.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            while (encendido)
            {
                try {
                    OOSConexion.writeObject(Configuracion.MI_IP);
                    OISConexion.readObject();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }


            }
        }
    }


}
