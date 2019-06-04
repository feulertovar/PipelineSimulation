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

public class MEM_WB_Model implements Cloneable {

	public MEM_WB_Model() {
		// TODO Auto-generated constructor stub
	}
	
	public int getALUResult() {
		return ALUResult;
	}
	public void setALUResult(int aLUResult) {
		ALUResult = aLUResult;
	}
	public int getMemToReg() {
		return MemToReg;
	}
	public void setMemToReg(int memToReg) {
		MemToReg = memToReg;
	}
	public int getRegWrite() {
		return RegWrite;
	}
	public void setRegWrite(int regWrite) {
		RegWrite = regWrite;
	}
	public int getLWDataValue() {
		return LWDataValue;
	}
	public void setLWDataValue(int lWDataValue) {
		LWDataValue = lWDataValue;
	}
	public int getWriteRegNum() {
		return WriteRegNum;
	}
	public void setWriteRegNum(int writeRegNum) {
		WriteRegNum = writeRegNum;
	}

	private int ALUResult;
	private int MemToReg;
	private int RegWrite;
	private int LWDataValue;
	private int WriteRegNum;
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();	// return shallow copy
	}
	
}
