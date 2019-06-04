package proj3_cs472;
//---------------------------------------------------------------------------
//
//Pipeline Simulation Project
//
//Author: Feuler Tovar
//Date: 04/25/19
//Class: MET CS472, Project 3
//Issues: None known
//
//Description:
//This program will simulate how a pipeline datapath works
//The status of the pipeline and its registers will be printed
//after each cycle

//Assumptions:
//

public class IF_ID_Model implements Cloneable {

	public IF_ID_Model() {
		// TODO Auto-generated constructor stub
	}

	private int inst;
	private int incrPC;

	public int getInst() {
		return inst;
	}

	public void setInst(int inst) {
		this.inst = inst;
	}

	public int getIncrPC() {
		return incrPC;
	}

	public void setIncrPC(int incrPC) {
		this.incrPC = incrPC;
	}

	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();	// return shallow copy
	}
}
