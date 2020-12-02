package com.labdistibuido.escenario;

import java.io.File;

public class Escenario {
    private static Mapa_Mov Mov;
    private static Mapa_Obj Obj;

    public void Inicializar(int x ,int y,File M, File O)
    {
        Mov = new Mapa_Mov(x,y,M);
        Obj = new Mapa_Obj(x,y,O);
    }

    public static Mapa_Mov getMov() {
        return Mov;
    }

    public static Mapa_Obj getObj() {
        return Obj;
    }
}
