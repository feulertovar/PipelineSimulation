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


public class Rformat {

	public Rformat() {
		// TODO Auto-generated constructor stub
	}
	
	private int opcode;
	private int srcreg1;
	private int srcreg2;
	private int destreg;
	private int function;
	private short offset;
	private int SEOffset;
	
	public short getOffset() {
		return offset;
	}
	public void setOffset(short offset) {
		this.offset = offset;
	}
	public int getSEOffset() {
		return SEOffset;
	}
	public void setSEOffset(int sEOffset) {
		SEOffset = sEOffset;
	}

	
	public int getOpcode() {
		return opcode;
	}
	public void setOpcode(int opcode) {
		this.opcode = opcode;
	}
	public int getSrcreg1() {
		return srcreg1;
	}
	public void setSrcreg1(int srcreg1) {
		this.srcreg1 = srcreg1;
	}
	public int getSrcreg2() {
		return srcreg2;
	}
	public void setSrcreg2(int srcreg2) {
		this.srcreg2 = srcreg2;
	}
	public int getDestreg() {
		return destreg;
	}
	public void setDestreg(int destreg) {
		this.destreg = destreg;
	}
	public int getFunction() {
		return function;
	}
	public void setFunction(int function) {
		this.function = function;
	}
}
