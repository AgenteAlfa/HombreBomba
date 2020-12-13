package com.labdistibuido.conexion;

import com.labdistibuido.escenario.Escenario;

public class Jugador {
    private final int TAG;
    private boolean Muerto;
    private Conexion mConexion;
    public Jugador(int tag,Conexion C) {
        TAG = tag;
        Muerto = false;
        mConexion = C;
    }

    public boolean Moverse()
    {
        mConexion.EnviarOrden(0);
        return true;
    }

    public void VerificarMuerte()
    {
        Muerto = !Escenario.getObj().Contiene(TAG);
    }


    public boolean isMuerto() {
        return Muerto;
    }
}
