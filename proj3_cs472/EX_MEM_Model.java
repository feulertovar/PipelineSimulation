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

public class EX_MEM_Model  implements Cloneable {

	public EX_MEM_Model() {
		// TODO Auto-generated constructor stub
	}
	
	public int getALUResult() {
		return ALUResult;
	}
	public void setALUResult(int aLUResult) {
		ALUResult = aLUResult;
	}
	public int getMemRead() {
		return MemRead;
	}
	public void setMemRead(int memRead) {
		MemRead = memRead;
	}
	public int getMemWrite() {
		return MemWrite;
	}
	public void setMemWrite(int memWrite) {
		MemWrite = memWrite;
	}
	public int getBranch() {
		return Branch;
	}
	public void setBranch(int branch) {
		Branch = branch;
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
	public int getCalcBTA() {
		return CalcBTA;
	}
	public void setCalcBTA(int calcBTA) {
		CalcBTA = calcBTA;
	}
	public boolean isZero() {
		return Zero;
	}
	public void setZero(boolean zero) {
		Zero = zero;
	}
	public int getSWValue() {
		return SWValue;
	}
	public void setSWValue(int sWValue) {
		SWValue = sWValue;
	}
	public int getWriteRegNum() {
		return WriteRegNum;
	}
	public void setWriteRegNum(int writeRegNum) {
		WriteRegNum = writeRegNum;
	}

	private int ALUResult;
	private int MemRead;
	private int MemWrite;
	private int Branch;
	private int MemToReg;
	private int RegWrite;
	private int CalcBTA = 0;
	private boolean Zero = false;
	private int SWValue;
	private int WriteRegNum;
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();	// return shallow copy
	}

}
