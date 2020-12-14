package com.labdistibuido.conexion;

import com.labdistibuido.escenario.Escenario;
import com.labdistibuido.escenario.Mapa;
import com.labdistibuido.escenario.Sim_Mov;
import com.labdistibuido.escenario.Sim_Obj;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server extends ServerSocket{

    private final ArrayList<Cliente> Clientes;
    private final Server este = this;
    private Hilo_Esperador Esperador;
    private Hilo_Controlador Controlador;
    private Hilo_Leedor Leedor;



    public Server() throws IOException {
        super(Configuracion.PUERTO);
        //Preparando Escenario
        File A,B;
        Clientes = new ArrayList<>();
        A = new File("src\\data\\Movimiento");
        B = new File("src\\data\\Objetos");
        //TODO : Cargar Mapa
        Escenario.Inicializar(10,10,A,B);
    }

    public void Esperar(){
        Clientes.clear();
        Esperador = new Hilo_Esperador();
        Esperador.start();
    }

    public void Empezar()
    {
        Esperador.Apagar();
        Esperador.interrupt();

        //Mostrar usuarios
        System.out.println("Actualmente hay " + Clientes.size());
        //TODO : Crear comunicacion con clientes

        if (Clientes.size() == 0)
            Cerrar();

        Juego();

    }
    private void Cerrar()
    {
        System.out.println("Cerrando servidor...");
        for (Cliente Cls: Clientes) {
            try {
                Cls.getBOS().close();
                Cls.getBIS().close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    public void Juego()
    {

        for (int i = 0; i < 5 - Clientes.size(); i++) {
            System.out.println("Removiendo : " + (5 - i));
            Escenario.getObj().Remover(5-i);
        }
        Controlador = new Hilo_Controlador();
        Controlador.start();
        Leedor = new Hilo_Leedor();
        Leedor.start();


    }

    private void EnviarEstatus() throws IOException {
        System.out.println("Enviando Estatus");
        for (int i = 0; i < Clientes.size(); i++) {

                    Clientes.get(i).getOOS().writeObject(Escenario.getMov().getArr());
                    Clientes.get(i).getOOS().reset();
                    Clientes.get(i).getOOS().writeObject(Escenario.getObj().getArr());

        }
        //System.out.println("Estatus es : ");
        //Escenario.getObj().Imprimir();
    }
    private void LeerTodo()
    {
        for (int i = 0 ; i < Clientes.size(); i++)
        {
            try {
                if(Clientes.get(i).getBIS().available() != 0)
                {
                //System.out.println("Leyendo del jugador " + (i + 1));
                int mB = Clientes.get(i).getBIS().read();
                char ord = (char) mB;
                //System.out.println("Se leyo de jugador " + (i + 1) + " : " + ord);
                Accion(i + 1, ord);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void RevisarBombas()
    {

        for (int i = 0; i < Escenario.X; i++) {
            for (int j = 0; j < Escenario.Y; j++) {
                int c = Escenario.getObj().getPos(i,j);
                if (c < 0 && c > (Sim_Obj.EXPLOSION + 1))
                    Escenario.getObj().setPos(i,j,c - 1);
                else if (c == Sim_Obj.EXPLOSION)
                {
                    Escenario.getObj().setPos(i,j,Sim_Obj.VACIO);
                }
            }
        }
        int[] PosExp;
        while ( (PosExp = Escenario.getObj().Buscar(Sim_Obj.EXPLOSION + 1)) != null )
        {
            ExplotarBomba(PosExp[0], PosExp[1] );
        }
    }
    private void ExplotarBomba(int x ,  int y)
    {

            //EQ
            Explotar(x,y,true);
            //Der
            Explotar(x + 1,y,false);
            //Izq
            Explotar(x - 1,y,false);
            //Arr
            Explotar(x,y + 1,false);
            //Aba
            Explotar(x,y - 1,false);
    }
    private void Explotar(int x ,  int y, boolean EQ)
    {
        if (Escenario.getMov().getPos(x,y) == Sim_Mov.PASABLE)
        {

            if (!EQ && Escenario.getObj().getPos(x,y) < 0 && Escenario.getObj().getPos(x,y) > Sim_Obj.EXPLOSION)
                ExplotarBomba(x,y);
            Escenario.getObj().setPos(x,y,Sim_Obj.EXPLOSION);
        }
        else if (Escenario.getMov().getPos(x,y) == Sim_Mov.ARBOL)
        {
            Escenario.getMov().setPos(x,y,Sim_Mov.PASABLE);
            Escenario.getObj().setPos(x,y,Sim_Obj.EXPLOSION);
        }
        else if (Escenario.getMov().getPos(x,y) == Sim_Mov.ROCA_BLANDA)
        {
            Escenario.getMov().setPos(x,y,Sim_Mov.PASABLE);
            Escenario.getObj().setPos(x,y,Sim_Obj.EXPLOSION);
        }
        else if (Escenario.getMov().getPos(x,y) == Sim_Mov.ROCA_DURA)
            Escenario.getMov().setPos(x,y,Sim_Mov.ROCA_BLANDA);
    }


    public void Accion(int tag,int mov)
    {
        Mapa M = Escenario.getObj();
        int[] pos_org = M.Buscar(tag).clone();
        int[] pos = M.Buscar(tag).clone();
        switch (mov)
        {
            case 'W':
            case 'w':
                pos[1] --;
                break;
            case 'S':
            case 's':
                pos[1] ++;
                break;
            case 'D':
            case 'd':
                pos[0] ++;
                break;
            case 'A':
            case 'a':
                pos[0] --;
                break;
            case 'B':
            case 'b':
                Clientes.get(tag  - 1).bomba = true;
                break;

        }
        if ( (!Controlador.SeComunicaron[tag - 1])
                && Escenario.getMov().getPos(pos[0],pos[1]) == Sim_Mov.PASABLE
                && Escenario.getObj().getPos(pos[0],pos[1]) == Sim_Obj.VACIO )
        {
            Escenario.getObj().MoverObjetos(pos_org,pos);
            if (Clientes.get(tag - 1).bomba)
            {
                Escenario.getObj().setPos(pos_org[0],pos_org[1], Sim_Obj.BOMBA);
                Clientes.get(tag  - 1).bomba = false;
            }

            Controlador.SeComunicaron[tag - 1] = true;
        }

    }
    
    private class Hilo_Controlador extends Thread
    {
        private int Turno = 0;
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
                    sleep(200);
                    EnviarEstatus();
                    //Revisar bombas
                    RevisarBombas();

                }catch (Exception E)
                {
                    System.out.println(E.getMessage());
                }
                Turno++;
            }
        }
    }

    private class Hilo_Leedor extends Thread
    {
        private boolean encendido = true;
        public void Apagar()
        {
            encendido = false;

        }

        @Override
        public void run() {
            while (encendido)
                LeerTodo();
        }
    }

    private class Hilo_Esperador extends Thread
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
                    Clientes.add(new Cliente(este.accept()));
                    System.out.println("A ingresado el jugador Nro " + Clientes.size() + "!");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
