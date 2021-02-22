package com.labdistibuido.Escenario.Mapa;

import java.io.File;

public class Mapa_Obj extends Mapa{
    public Mapa_Obj(int x, int y, File Origen) {
        super(x, y, Origen);
    }

    public Mapa_Obj(int x, int y) {
        super(x,y);
    }

    public boolean Jugable()
    {
        int count = 0;
        count += Contiene(Sim_Obj.JUGADOR_1)?1:0;
        count += Contiene(Sim_Obj.JUGADOR_2)?1:0;
        count += Contiene(Sim_Obj.JUGADOR_3)?1:0;
        count += Contiene(Sim_Obj.JUGADOR_4)?1:0;
        count += Contiene(Sim_Obj.JUGADOR_5)?1:0;
        return count > 1;
    }


}
