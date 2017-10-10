package dungeonraider.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import dungeonraider.engine.Engine;

public class MouseController implements MouseListener, MouseMotionListener {
	private Engine engine;
	private boolean attack;
	private int mx;
	private int my;
	private int attacks;

	public MouseController(Engine engine) {
		this.engine = engine;
		this.attack = false;
		this.mx = 0;
		this.my = 0;
		this.attacks = 0;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		mx = e.getX();
		my = e.getY();
		attacks += 1;
		//attack = true;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	public int getAttacks() {
		return attacks;
	}

	public void setAttacks(int attacks) {
		this.attacks = attacks;
	}

	public boolean isAttack() {
		return attack;
	}

	public int getMx() {
		return mx;
	}

	public int getMy() {
		return my;
	}


}
