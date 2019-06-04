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

public class Iformat {
	public Iformat() {
		// TODO Auto-generated constructor stub
	}

	private int opcode;
	private int srcreg;
	private int srcdest;
	private int reg1115;
	private int function;
	private short offset;
	private int SEOffset;
	

	public int getFunction() {
		return function;
	}
	public void setFunction(int function) {
		this.function = function;
	}
	
	public int getReg1115() {
		return reg1115;
	}
	public void setReg1115(int reg1115) {
		this.reg1115 = reg1115;
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
	public int getSrcreg() {
		return srcreg;
	}
	public void setSrcreg(int srcreg) {
		this.srcreg = srcreg;
	}
	public int getSrcdest() {
		return srcdest;
	}
	public void setSrcdest(int srcdest) {
		this.srcdest = srcdest;
	}
	public short getOffset() {
		return offset;
	}
	public void setOffset(short offset) {
		this.offset = offset;
	}
}
