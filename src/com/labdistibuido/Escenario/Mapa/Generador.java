package com.labdistibuido.Escenario.Mapa;

import java.util.Random;
public class Generador {
    public static void Generar(int X , int Y , int porcentaje )
    {
        int [][] mArr = new int[X][Y];

        for (int [] jesimo : mArr) {
            for (int  iesimo : jesimo) {
                iesimo = 4;
            }
        }

        Random R = new Random();
        for (int i = 0; i < X * Y; i++) {
            int k = R.nextInt(100);
            if (k < 70)
            {
                int a = R.nextInt(X) , b = R.nextInt(Y);
                if (mArr[a][b] == -1) continue;
                try
                {
                    if (mArr[a + 1][b + 1] > 1 &&
                            mArr[a + 1][b - 1] > 1 &&
                            mArr[a - 1][b + 1] > 1 &&
                            mArr[a - 1][b - 1] > 1)
                    {
                        mArr[a][b] = -1;
                        mArr[a + 1][b + 1] -- ;
                        mArr[a + 1][b - 1] -- ;
                        mArr[a - 1][b + 1] -- ;
                        mArr[a - 1][b - 1] -- ;
                    }


                }
                catch (Exception ignored){}

            }
        }


    }
}