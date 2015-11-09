package game;

import gameobject.Bolita;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import punto.Punto;
import rectas.Recta;
import rectas.Rectas;
import rectas.Recta.RectaInvalidaException;


public class Mapa {
	
	private ArrayList<Recta> rectas;
	private ArrayList<Bolita> bolitas;
	
	public Mapa() {
		rectas = new ArrayList<Recta>();
		bolitas = new ArrayList<Bolita>();
		agregarRecta(new Punto(30,50), new Punto(30,530));
		agregarRecta(new Punto(30,530), new Punto(730,530));
		agregarRecta(new Punto(730,50), new Punto(730,530));
		agregarRecta(new Punto(370,50), new Punto(370,530));
		agregarRecta(new Punto(30,290), new Punto(730,290));
		agregarRecta(new Punto(30,50), new Punto(730,50));
		agregarRecta(new Punto(250,30), new Punto(250,500));
		agregarRecta(new Punto(100,150), new Punto(600,150));
		agregarRecta(new Punto(550,34), new Punto(550,550));
		agregarRecta(new Punto(200,470), new Punto(500,470));
		agregarRecta(new Punto(200,450), new Punto(500,470)); //Recta de prueba, no se agrega porque es oblicua
	}
	
	public ArrayList<Bolita> getArrayBolitas() {
		return bolitas;
	}
	
	public ArrayList<Recta> getArrayRectas() {
		return rectas;
	}
	
	public int getCantidadBolitasRestantes() {
		return bolitas.size();
	}
	
	private void agregarRecta(Punto p1, Punto p2) {
		try {
			rectas.add(new Recta(p1,p2));
		}
		catch(RectaInvalidaException ex) {
			System.out.println("No se pudo crear la recta: Parametros invalidos.");
		}
		
	}
	
	public void dibujar(JPanel area){
		for(Iterator<Recta>r=rectas.iterator();r.hasNext();){
			Recta rec=r.next();
			rec.dibujar(area);
		}
		generarBolitas(area);
	}
	
	public void generarBolitas(JPanel area){
		for(Iterator<Recta>r=rectas.iterator();r.hasNext();){
			Recta rec=r.next();
			int cantBolitas=(rec.getLongitud()-10)/20;
			for(int i=0;i<cantBolitas;i++){
				ImageIcon icon = new ImageIcon("img/bolitaNormal.gif");
				JLabel l= new JLabel(icon);
				if(rec.getTipo()==Rectas.HORIZONTAL){
					l.setBounds(rec.getPuntoInicialX() + i*20+10, rec.getPuntoInicialY()-10, 21, 21);
				}
				else if(rec.getTipo()==Rectas.VERTICAL){
					l.setBounds(rec.getPuntoInicialX() - 10,rec.getPuntoInicialY() + i*20+10, 21, 21);
				}
				l.setIcon(icon);
				Bolita b = new Bolita(l,false);
				//System.out.println(b.getCentroCoordenadas().toString());
				bolitas.add(b);
				area.add(l);
			}
		}
	}
	
	public void removerBolita(Bolita b) {
		b.setAliveState(false);
		b.borrarImagen();
		bolitas.remove(b);
	}
}
