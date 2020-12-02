package com.labdistibuido.escenario;

public class Escenario {
    private static Mapa_Mov Mov;
    private static Mapa_Obj Obj;

    public void Inicializar(int x ,int y)
    {
        Mov = new Mapa_Mov(x,y);
        Obj = new Mapa_Obj(x,y);
    }

    public static Mapa_Mov getMov() {
        return Mov;
    }

    public static Mapa_Obj getObj() {
        return Obj;
    }
}
