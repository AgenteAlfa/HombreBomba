package com.labdistibuido.conexion;

import java.io.IOException;
import java.util.ArrayList;

public class Conexion {


    private Server mServer;
    private Cliente mCliente;
    private boolean IsServer;
    public Conexion(){};
    public Conexion(boolean isServer) throws IOException {

        IsServer = isServer;
        if (IsServer)
            mServer = new Server();
        else
        mCliente = new Cliente();

    }
    public boolean EnviarOrden(int Ord)
    {
        return true;
    }

    public boolean CrearPartida()
    {
        if(IsServer)
            mServer.Esperar();
        return IsServer;
    }
    public boolean UnirsePartida()
    {

        return IsServer;
    }
    private void Conectarse()
    {
        if(IsServer)
        {
            mServer.Esperar();
        }
    }
    public Jugador Iniciar()
    {
        if (IsServer)
        {
            mServer.Empezar();
        }
        return null;
    }

    public void Accion(int mov)
    {
        switch (mov)
        {
            case 'W':
            case 'w':
                break;
            case 'S':
            case 's':
                break;
            case 'D':
            case 'd':
                break;
            case 'A':
            case 'a':
                break;
            case ' ':
                break;
        }
    }




}
