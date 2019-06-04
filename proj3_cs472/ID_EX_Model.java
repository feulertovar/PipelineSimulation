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

public class ID_EX_Model  implements Cloneable {

	public ID_EX_Model() {
		// TODO Auto-generated constructor stub
	}

	private int RegDst;
	private int ALUSrc;
	private int ALUOP;
	private int MemRead;
	private int MemWrite;
	private int Branch;
	private int MemToReg;
	private int RegWrite;
	private int incrPC;
	private int ReadReg1Value;
	private int ReadReg2Value;
	private int SEOffset;
	private int WriteReg_20_16;
	private int WriteReg_15_11;
	private int Function;
	
	
	public int getRegDst() {
		return RegDst;
	}
	public void setRegDst(int regDst) {
		RegDst = regDst;
	}
	public int getALUSrc() {
		return ALUSrc;
	}
	public void setALUSrc(int aLUSrc) {
		ALUSrc = aLUSrc;
	}
	public int getALUOP() {
		return ALUOP;
	}
	public void setALUOP(int aLUOP) {
		ALUOP = aLUOP;
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
	public int getIncrPC() {
		return incrPC;
	}
	public void setIncrPC(int incrPC) {
		this.incrPC = incrPC;
	}
	public int getReadReg1Value() {
		return ReadReg1Value;
	}
	public void setReadReg1Value(int readReg1Value) {
		ReadReg1Value = readReg1Value;
	}
	public int getReadReg2Value() {
		return ReadReg2Value;
	}
	public void setReadReg2Value(int readReg2Value) {
		ReadReg2Value = readReg2Value;
	}
	public int getSEOffset() {
		return SEOffset;
	}
	public void setSEOffset(int i) {
		SEOffset = i;
	}
	public int getWriteReg_20_16() {
		return WriteReg_20_16;
	}
	public void setWriteReg_20_16(int writeReg_20_16) {
		WriteReg_20_16 = writeReg_20_16;
	}
	public int getWriteReg_15_11() {
		return WriteReg_15_11;
	}
	public void setWriteReg_15_11(int writeReg_15_11) {
		WriteReg_15_11 = writeReg_15_11;  
	}
	public int getFunction() {
		return Function;
	}
	public void setFunction(int function) {
		Function = function;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();	// return shallow copy
	}

}
