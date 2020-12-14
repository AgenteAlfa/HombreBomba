package com.labdistibuido.escenario;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.io.IOException;

import javax.swing.JFrame;

import com.labdistibuido.conexion.Cliente;
import com.labdistibuido.sonidos.EfectosDeSonido;
import com.labdistibuido.sonidos.ReproducirSonido;

public class Ventana extends JFrame implements Runnable , KeyListener {
    private final Canvas canvas;
    private Thread thread;
    private boolean corriendo;

    private BufferStrategy bs;
    private Graphics g;

    private final int FPS = 60;
    private double tiempoObjetivo = 1000000000/FPS;
    private double delta = 0;
    private double FPSPROMEDIO = FPS;
    private Cliente mCliente;

    public Ventana(Cliente C) {
        setTitle("Hombre Bomba");
        setSize(Constantes.ANCHO, Constantes.ALTO);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        mCliente = C;
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(Constantes.ANCHO, Constantes.ALTO));
        canvas.setMaximumSize(new Dimension(Constantes.ANCHO, Constantes.ALTO));
        canvas.setMinimumSize(new Dimension(Constantes.ANCHO, Constantes.ALTO));

        add(canvas);
        setVisible(true);
    }

    public void init() {
        EfectosDeSonido.init();
    }

    public void actualizar() {
        // estadoJuego.actualizar();
        // teclado.actualizar();
    }

    private void dibujar() {
        bs = canvas.getBufferStrategy();

        if (bs == null) {
            canvas.createBufferStrategy(3);
            return;
        }
        g = bs.getDrawGraphics();
        /*------------ Dibujar Gráficos ------------*/
        g.setColor(Color.black);
        g.fillRect(0, 0, Constantes.ANCHO, Constantes.ALTO);


        //Dibujando seudo graficos de cuadricula
        DibujarCuadricula();

        // estadoJuego.dibujar()
        

        //g.setColor(Color.WHITE);
        //g.setFont(new Font("TimesRoman", Font.PLAIN, 5));
        //g.drawString("FPS: " + FPSPROMEDIO, 0, 10);

        /*------------------------------------------*/
        g.dispose();
        bs.show();        
    }

    private void DibujarCuadricula()
    {
        int Dx = (Constantes.ANCHO)/Escenario.X - 10;
        int Dy = (Constantes.ALTO)/Escenario.Y - 10;
        //System.out.println("Dim : " + Dx + " - " + Dy);

        for (int i = 0 ; i  < Escenario.X ; i++ )
            for (int j = 0; j < Escenario.Y; j++ ) {
                g.setColor(Color.gray);
                g.fillRect(i*Dx,j*Dy,Dx - 1 , Dy - 1);
                String Cmov = "[" + Escenario.getMov().getPos(i,j) + "]";
                String Cobj = "(" + Escenario.getObj().getPos(i,j)+")";
                g.setColor(Color.black);
                g.setFont(new Font("TimesRoman", Font.PLAIN, 10));
                g.drawString(Cmov,i*Dx + 5,j*Dy + 15);

                g.setColor(Color.blue);
                g.setFont(new Font("TimesRoman", Font.PLAIN, 25));
                //g.drawString(Cmov,i*Dx + 5,j*Dy + 15);
                g.drawString(Cobj,i*Dx + 20, j*Dy + 30);
            }

    }


    @Override
    public void run() {
        long ahora = 0;
        long ultimoTiempo = System.nanoTime();
        long tiempo = 0;
        int cuadros = 0;        

        init();

        /*---- Ejemplo sonido (cargando) ----*/
        long tiempoTitulo = 0;
        ReproducirSonido title = new ReproducirSonido(EfectosDeSonido.title);
        /*------------------------*/

        while (corriendo) {
            ahora = System.nanoTime();
            delta += (ahora - ultimoTiempo)/tiempoObjetivo;
            tiempo += ahora - ultimoTiempo;
            ultimoTiempo = ahora;
            
            if (delta > 1) {
                actualizar();
                dibujar();
                delta--;
                cuadros++;
            }

            if (tiempo > 1000000000) {
                FPSPROMEDIO = cuadros;
                cuadros = 0;
                tiempo = 0;

                /*---- Ejemplo sonido (reproduciendo) ----*/
                tiempoTitulo++;
                if (tiempoTitulo == 2) {
                    title.reproducir();
                }
                /*----------------------------------------*/
            }        

        }

        parar();

    }

    public void inicia() {
        thread = new Thread(this);
        thread.start();
        corriendo = true;
    }

    private void parar() {
        try {
            thread.join();
            corriendo = false;            
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("CODIGO : " + e.getKeyCode());
        try {
            mCliente.getBOS().write(e.getKeyCode());
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
