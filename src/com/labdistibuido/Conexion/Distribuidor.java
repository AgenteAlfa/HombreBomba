package com.labdistibuido.Conexion;

import com.labdistibuido.ConexionAnterior.Cliente;
import com.labdistibuido.Escenario.Mapa.Escenario;
import com.labdistibuido.Escenario.Ventana.Constantes;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Arrays;


public class Distribuidor {

    private ServerSocket SSJuego , SSConexion;
    private ArrayList<Nodo> Nodos;
    private boolean Inicializar;

    private Hilo_Esperador Esperador;
    private Hilo_Juego HJuego; //Envia un objeto en Juego que contiene el escenario y recive un ok (1) de respuesta
    private Hilo_Conexion HConexion; //Lee los datos enviados en Conexion y envia ok (1) de respuesta

    private int Tiempo;
    private char [] Recibido;

    public Distribuidor() throws IOException {
        InicializarBase();
    }
    public Distribuidor(boolean inicializar , int tiempo) throws IOException {
        InicializarBase();
        Inicializar = inicializar;
        File A,B;
        A = new File("src\\data\\Movimiento");
        B = new File("src\\data\\Objetos");
        //TODO : Cargar Mapa
        Tiempo = tiempo;
        Escenario.Inicializar(Constantes.X,Constantes.Y,A,B);
        Esperar();
    }
    private void InicializarBase() throws IOException {
        SSJuego = new ServerSocket(Configuracion.PUERTO_JUEGO);
        SSConexion = new ServerSocket(Configuracion.PUERTO_CONEXION);
        SSJuego.setSoTimeout(3000);
        SSConexion.setSoTimeout(3000);
        Nodos = new ArrayList<>();

    }
    private void Cerrar()
    {
        try {
            SSConexion.close();
            SSJuego.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void EmpezarEn(int tiempo)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(tiempo);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Empezar();
            }
        }).start();
    }

    public void Empezar()
    {
        Esperador.Apagar();
        Esperador.interrupt();

        //Mostrar usuarios
        System.out.println("Actualmente hay " + Nodos.size());
        //TODO : Crear comunicacion con clientes

        if (Nodos.size() == 0)
        {
            Cerrar();
            System.out.println("No tengo nodos que atender >:c");
            return;
        }
        /*
        Recibido = new char[Nodos.size()];
        IniciarJuego();
        */
    }

    private synchronized void setRecibido(int i, char msj)
    {
        Recibido[i] = msj;
    }
    private synchronized void limpiarRecibido()
    {
        Arrays.fill(Recibido,' ');
    }

    public void IniciarJuego()
    {

        for (int i = 0; i < 5 - Nodos.size(); i++) {
            System.out.println("Removiendo : " + (5 - i));
            Escenario.getObj().Remover(5-i);
        }
        HJuego = new Hilo_Juego();
        HJuego.start();
        HConexion = new Hilo_Conexion();
        HConexion.start();


    }

    public void Esperar(){
        Nodos.clear();
        Esperador = new Hilo_Esperador();
        Esperador.start();
        EmpezarEn(Tiempo);
    }



    private class Hilo_Esperador extends Thread
    {
        private boolean encendido = true;
        public void Apagar()
        {
            System.out.println("NODO esperador apagado");
            encendido = false;

        }

        @Override
        public void run() {
            System.out.println("Estoy esperando nodos ....");
            while (encendido)
            {

                try {
                    Nodos.add(new Nodo(  SSJuego.accept() , SSConexion.accept()  ));
                    System.out.println("A ingresado el jugador Nro " + Nodos.size() + "!");
                } catch (Exception ignored) {}
            }
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
                //Espera la comunicacion de cada uno de los jugadores
                try {
                    SeComunicaron[0] = false;
                    SeComunicaron[1] = false;
                    SeComunicaron[2] = false;
                    SeComunicaron[3] = false;
                    SeComunicaron[4] = false;
                    sleep(Configuracion.DELTA);

                    //Revisar bombas
                    Escenario.RevisarBombas();


                }catch (Exception E)
                {
                    System.out.println(E.getMessage());
                }
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
            while (encendido)
            {
                try {
                    for (int i = 0; i < Recibido.length; i++)
                        if (Nodos.get(i).getOISConexion().available() != 0){
                        setRecibido(i, (Character) Nodos.get(i).getOISConexion().readObject());
                    }
                } catch (Exception ignored) {}


            }//LeerTodo();
        }
    }
}
