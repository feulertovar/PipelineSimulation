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

public class PipeLine {

	// declare simulated Memory and Register arrays
	private static Integer[] Main_Mem = new Integer[1024];
	private static Integer[] Regs = new Integer[32];

	private static IF_ID_Model IF_ID_WRITE = new IF_ID_Model();
	private static IF_ID_Model IF_ID_READ = new IF_ID_Model();

	private static ID_EX_Model ID_EX_WRITE = new ID_EX_Model();
	private static ID_EX_Model ID_EX_READ = new ID_EX_Model();

	private static EX_MEM_Model EX_MEM_WRITE = new EX_MEM_Model();
	private static EX_MEM_Model EX_MEM_READ = new EX_MEM_Model();

	private static MEM_WB_Model MEM_WB_WRITE = new MEM_WB_Model();
	private static MEM_WB_Model MEM_WB_READ = new MEM_WB_Model();

	public PipeLine() {
		// TODO Auto-generated constructor stub
	}

	// initialize the Main_Mem memory array
	public void initMem() {

		// init simulated memory for pipeline
		for (Integer i = 0; i < Main_Mem.length; i++) {
			Main_Mem[i] = (i & Bitmask.MEMINIT.getMask());
		}

	}

	// initialize the Regs[32] registers
	public void initRegs() {

		// init simulated registers for pipeline
		// Reg zero is equal to 0
		for (Integer i = 0; i < Regs.length; i++) {
			if (i == 0) {
				Regs[i] = 0;
			} else {
				Regs[i] = (i + Bitmask.REGINIT.getMask());
			}

		}

	}

	
	///////////////////////////////////////////////////////////////////
	/// runSim (Worker method called from main) ///
	/// Input : None ///
	/// Output: None ///
	/// Returns Nothing ///
	/// ///
	///////////////////////////////////////////////////////////////////
	public void runSim() throws CloneNotSupportedException {

		// Array of instructions
		int[] Instruct = new int[] { 0x00000000, 0xa1020000, 0x810AFFFC, 0x00831820, 0x01263820, 0x01224820, 0x81180000,
				0x81510010, 0x00624022, 0x00000000, 0x00000000, 0x00000000, 0x00000000 };

		// array length
		int len = Instruct.length;

		// loop through the simulation
		for (int i = 0; i < len; i++) {

			// Program Counter, increment by 4.
			Disassembler.setCurAddress(Disassembler.getCurAddress() + Disassembler.getPcConstant());

			// IF_stage
			IF_stage(Instruct[i]);

			// ID_stage
			ID_stage();

			// EX_stage
			EX_stage();

			// MEM_stage
			MEM_stage();

			// WB_stagev
			WB_stage();

			// Print out everything
			System.out.println();
			System.out.println(
					"_______________________________________________________________________________________________");
			System.out.println("-----------------");
			System.out.println("Clock Cycle " + i);
			System.out.println("-----------------");
			print_out_everything();

			System.out.println(
					"________________________________________________________________________________________________");

			// Copy write to read
			copy_write_to_read();
		}

	}

	public void IF_stage(Integer inst) {

		// Add Instruction to WRITE of IF/ID Pipeline register
		IF_ID_WRITE.setInst(inst);

		// update program counter
		IF_ID_WRITE.setIncrPC(Disassembler.getCurAddress());

	}

	public void ID_stage() {

		// Read from READ version of IF_ID

		int opcode;
		Rformat instR;
		Iformat instI;

		Disassembler dis = new Disassembler();

		// checkOpcode
		// RFormat = 0
		// IFormat ( 1...3F )

		// Read Instruction from the READ version of the IF/ID pipeline register
		int inst = IF_ID_READ.getInst();

		// Decode instruction stored in IF/ID register
		// Write the values to the WRITE version of the ID/EX pipeline register
		opcode = (inst >>> 26);

		if (opcode == 0) {
			// RFormart
			instR = dis.RDisassembler(inst);
			// Fetch info and populate registers
			ID_EX_WRITE.setALUOP(10);
			ID_EX_WRITE.setALUSrc(0);
			ID_EX_WRITE.setBranch(0);
			ID_EX_WRITE.setFunction(instR.getFunction());
			ID_EX_WRITE.setIncrPC(IF_ID_READ.getIncrPC());
			ID_EX_WRITE.setMemRead(0);
			ID_EX_WRITE.setMemToReg(0);
			ID_EX_WRITE.setMemWrite(0);
			ID_EX_WRITE.setReadReg1Value(Regs[instR.getSrcreg1()]);
			ID_EX_WRITE.setReadReg2Value(Regs[instR.getSrcreg2()]);
			ID_EX_WRITE.setRegDst(1);
			ID_EX_WRITE.setRegWrite(1);
			// sign extended offset must be calculated
			ID_EX_WRITE.setSEOffset(instR.getSEOffset());
			ID_EX_WRITE.setWriteReg_20_16(instR.getSrcreg2());
			ID_EX_WRITE.setWriteReg_15_11(instR.getDestreg());

		} else if (opcode >= 1 | opcode <= 3F) {
			// IFormart
			instI = dis.IDisabssembler(inst);

			if (opcode == 0x20) {
				// Load Byte
				ID_EX_WRITE.setALUOP(00);
				ID_EX_WRITE.setALUSrc(1);
				ID_EX_WRITE.setBranch(0);
				ID_EX_WRITE.setFunction(instI.getFunction());
				ID_EX_WRITE.setIncrPC(IF_ID_READ.getIncrPC());
				ID_EX_WRITE.setMemRead(1);
				ID_EX_WRITE.setMemToReg(1);
				ID_EX_WRITE.setMemWrite(0);
				ID_EX_WRITE.setReadReg1Value(Regs[instI.getSrcreg()]);
				ID_EX_WRITE.setReadReg2Value(Regs[instI.getSrcdest()]);
				ID_EX_WRITE.setRegDst(0);
				ID_EX_WRITE.setRegWrite(1);
				// sign extended offset must be calculated
				ID_EX_WRITE.setSEOffset(instI.getSEOffset());
				ID_EX_WRITE.setWriteReg_20_16(instI.getSrcdest());
				ID_EX_WRITE.setWriteReg_15_11(instI.getReg1115());
			}

			//
			else if (opcode == 0x28) {
				// Store Byte
				ID_EX_WRITE.setALUOP(00);
				ID_EX_WRITE.setALUSrc(1);
				ID_EX_WRITE.setBranch(0);
				ID_EX_WRITE.setFunction(instI.getFunction());
				ID_EX_WRITE.setIncrPC(IF_ID_READ.getIncrPC());
				ID_EX_WRITE.setMemRead(0);
				ID_EX_WRITE.setMemToReg(0);
				ID_EX_WRITE.setMemWrite(1);
				ID_EX_WRITE.setReadReg1Value(Regs[instI.getSrcreg()]);
				ID_EX_WRITE.setReadReg2Value(Regs[instI.getSrcdest()]);
				ID_EX_WRITE.setRegDst(0);
				ID_EX_WRITE.setRegWrite(0);
				// sign extended offset must be calculated
				ID_EX_WRITE.setSEOffset(instI.getSEOffset());
				ID_EX_WRITE.setWriteReg_20_16(instI.getSrcdest());
				ID_EX_WRITE.setWriteReg_15_11(instI.getReg1115());
			} else {

			}

		}

	}

	// Read from READ version of ID_EX
	// Write to EX_MEM_WRITE
	public void EX_stage() {

		// get control signals from ID_EX_READ stage
		EX_MEM_WRITE.setBranch(ID_EX_READ.getBranch());
		EX_MEM_WRITE.setMemRead(ID_EX_READ.getMemRead());
		EX_MEM_WRITE.setMemWrite(ID_EX_READ.getMemWrite());
		EX_MEM_WRITE.setMemToReg(ID_EX_READ.getMemToReg());
		EX_MEM_WRITE.setRegWrite(ID_EX_READ.getRegWrite());
		EX_MEM_WRITE.setSWValue(ID_EX_READ.getReadReg2Value());

		// set Write Register Number
		if (ID_EX_READ.getRegDst() == 0) {

			EX_MEM_WRITE.setWriteRegNum(ID_EX_READ.getWriteReg_20_16());

		} else if (ID_EX_READ.getRegDst() == 1) {

			EX_MEM_WRITE.setWriteRegNum(ID_EX_READ.getWriteReg_15_11());
		}

		// Add handling
		if ((ID_EX_READ.getALUOP() == 10) && (ID_EX_READ.getFunction() == 0x20) && ID_EX_READ.getALUSrc() == 0) {

			EX_MEM_WRITE.setALUResult(ID_EX_READ.getReadReg1Value() + ID_EX_READ.getReadReg2Value());
		}

		// Subtract handling
		if ((ID_EX_READ.getALUOP() == 10) && (ID_EX_READ.getFunction() == 0x22) && ID_EX_READ.getALUSrc() == 0) {

			EX_MEM_WRITE.setALUResult(ID_EX_READ.getReadReg1Value() - ID_EX_READ.getReadReg2Value());
		}

		// Nop 
		if ((ID_EX_READ.getFunction() == 0x00)) {
		}

		// load or store
		if ((ID_EX_READ.getALUOP() == 00) && (ID_EX_READ.getALUSrc() == 1)) {

			// Address to read Data memory from
			EX_MEM_WRITE.setALUResult(ID_EX_READ.getReadReg1Value() + ID_EX_READ.getSEOffset());

		}

	}
	// Read from READ version of EX_MEM_READ
	// Write to MEM_WB_WRITE
	public void MEM_stage() {

		// if sb, write to Main_MEM
		if (EX_MEM_READ.getMemWrite() == 1) {
			Main_Mem[EX_MEM_READ.getALUResult()] = EX_MEM_READ.getSWValue() & Bitmask.ONEBYTE.getMask();
			System.out.println();
			System.out.println("MEM_WRITE");
			System.out.println("=========");
			System.out.println("MEM[0x" + Integer.toHexString(EX_MEM_READ.getALUResult()) + "]" + " = 0x" + Integer.toHexString(Main_Mem[EX_MEM_READ.getALUResult()]) );
			System.out.println("=========");
			System.out.println();
		}

		// if lb, get value for ALUResult index in Main_Mem
		if (EX_MEM_READ.getMemToReg() == 1) {
			MEM_WB_WRITE.setLWDataValue(Main_Mem[EX_MEM_READ.getALUResult()]);
		}   
		//{
		// Read from the Read version of EX_MEM and add to ME_WB_WRITE
		MEM_WB_WRITE.setALUResult(EX_MEM_READ.getALUResult());
		MEM_WB_WRITE.setMemToReg(EX_MEM_READ.getMemToReg());
		MEM_WB_WRITE.setRegWrite(EX_MEM_READ.getRegWrite());
		MEM_WB_WRITE.setWriteRegNum(EX_MEM_READ.getWriteRegNum());

		// }
	}

	// Read from READ version of MEM_WB_READ
	// Use remaining control bits
	public void WB_stage() {
		// Read from the read version of MEM_WB
		if (MEM_WB_READ.getRegWrite() == 1) {
			if (MEM_WB_READ.getMemToReg() == 1) {
				// write back to register
				Regs[MEM_WB_READ.getWriteRegNum()] = MEM_WB_READ.getLWDataValue(); // & Bitmask.ONEBYTE.getMask();
			} else {
				Regs[MEM_WB_READ.getWriteRegNum()] = MEM_WB_READ.getALUResult();// & Bitmask.ONEBYTE.getMask();
			}
		}

	}

	public void print_out_everything() {

		String inst = "inst";
		String incrPC = "incrPC";
		// Controls
		String RegDst = "RegDst";
		String ALUSrc = "ALUSrc";
		String ALUOp = "ALUOp";
		String MemRead = "MemRead";
		String MemWrite = "MemWrite";
		String Branch = "Branch";
		String MemToReg = "MemToReg";
		String RegWrite = "RegWrite";
		// Values
		String ReadReg1Value = "ReadReg1Value";
		String ReadReg2Value = "ReadReg2Value";
		String SEOffset = "SEOffset";
		String WriteReg_20_16 = "WriteReg_20_16";
		String WriteReg_15_11 = "WriteReg_15_11";
		String CalcBTA = "CalcBTA";
		String Zero = "Zero";
		String SWValue = "SWValue";
		String ALUResult = "ALUResult";
		String WriteRegNum = "WriteRegNum";
		String LWDataValue = "LWDataValue";
		String Function = "Function";

		// Print IF/ID Write
		System.out.println();
		System.out.println("IF/ID Write");
		System.out.println("-----------------------------------------------");
		System.out.format(inst + " = 0x" + Integer.toHexString(IF_ID_WRITE.getInst()) + " " + incrPC + " = 0x"
				+ Integer.toHexString(IF_ID_WRITE.getIncrPC()));
		System.out.println();

		// Print IF/ID Read
		System.out.println();
		System.out.println("IF/ID Read");
		System.out.println("-----------------------------------------------");
		System.out.format(inst + " = 0x" + Integer.toHexString(IF_ID_READ.getInst()) + " " + incrPC + " = 0x"
				+ Integer.toHexString(IF_ID_READ.getIncrPC()));
		System.out.println();

		// Print ID/EX Write
		System.out.println();
		System.out.println("ID/EX Write");
		System.out.println("-----------------------------------------------");
		System.out.format("Control: " + RegDst + "=" + ID_EX_WRITE.getRegDst() + "," + ALUSrc + " = "
				+ ID_EX_WRITE.getALUSrc() + ", " + ALUOp + " = " + ID_EX_WRITE.getALUOP() + ", " + MemRead + " = "
				+ ID_EX_WRITE.getMemRead() + ", " + MemWrite + " = " + ID_EX_WRITE.getMemWrite() + ", " + Branch + " = "
				+ ID_EX_WRITE.getBranch() + ", " + MemToReg + " = " + ID_EX_WRITE.getMemToReg() + ", " + RegWrite
				+ " = " + ID_EX_WRITE.getRegWrite());
		System.out.println();
		System.out.format(incrPC + " = 0x" + Integer.toHexString(ID_EX_WRITE.getIncrPC()) + ", " + ReadReg1Value
				+ " = 0x" + Integer.toHexString(ID_EX_WRITE.getReadReg1Value()) + ", " + ReadReg2Value + " = 0x"
				+ Integer.toHexString(ID_EX_WRITE.getReadReg2Value()));
		System.out.println();

		System.out.println(SEOffset + " = 0x" + Integer.toHexString(ID_EX_WRITE.getSEOffset()) + ", " + WriteReg_20_16
				+ " = $" + ID_EX_WRITE.getWriteReg_20_16() + ", " + WriteReg_15_11 + " = $"
				+ ID_EX_WRITE.getWriteReg_15_11() + ", " + Function + " = 0x"
				+ Integer.toHexString(ID_EX_WRITE.getFunction()));
		System.out.println();

		// Print ID/EX Read
		System.out.println();
		System.out.println("ID/EX Read");
		System.out.println("-----------------------------------------------");
		System.out.format("Control: " + RegDst + "=" + ID_EX_READ.getRegDst() + ", " + ALUSrc + " = "
				+ ID_EX_READ.getALUSrc() + ", " + ALUOp + " = " + ID_EX_READ.getALUOP() + ", " + MemRead + " = "
				+ ID_EX_READ.getMemRead() + ", " + MemWrite + " = " + ID_EX_READ.getMemWrite() + ", " + Branch + " = "
				+ ID_EX_READ.getBranch() + ", " + MemToReg + " = " + ID_EX_READ.getMemToReg() + ", " + RegWrite + " = "
				+ ID_EX_READ.getRegWrite());
		System.out.println();
		System.out.format(incrPC + " = 0x" + Integer.toHexString(ID_EX_READ.getIncrPC()) + ", " + ReadReg1Value
				+ " = 0x" + Integer.toHexString(ID_EX_READ.getReadReg1Value()) + ", " + ReadReg2Value + " = 0x"
				+ Integer.toHexString(ID_EX_READ.getReadReg2Value()));
		System.out.println();

		System.out.println(SEOffset + " = 0x" + Integer.toHexString(ID_EX_READ.getSEOffset()) + ", " + WriteReg_20_16
				+ " = $" + Integer.toHexString(ID_EX_READ.getWriteReg_20_16()) + ", " + WriteReg_15_11 + " = $"
				+ Integer.toHexString(ID_EX_READ.getWriteReg_15_11()) + ", " + Function + " = 0x"
				+ Integer.toHexString(ID_EX_READ.getFunction()));
		System.out.println();

		// Print EX/MEM Write
		System.out.println();
		System.out.println("EX/MEM Write");
		System.out.println("-----------------------------------------------");
		System.out.format("Control: " + MemRead + " = " + EX_MEM_WRITE.getMemRead() + ", " + MemWrite + " = "
				+ EX_MEM_WRITE.getMemWrite() + ", " + Branch + " = " + EX_MEM_WRITE.getBranch() + ", " + MemToReg
				+ " = " + EX_MEM_WRITE.getMemToReg() + ", " + RegWrite + " = " + EX_MEM_WRITE.getRegWrite());
		System.out.println();
		System.out.format(CalcBTA + " = 0x" + Integer.toHexString(EX_MEM_WRITE.getCalcBTA()) + ", " + Zero + " = "
				+ EX_MEM_WRITE.isZero() + ", " + ALUResult + " = 0x"
				+ Integer.toHexString(EX_MEM_WRITE.getALUResult()));
		System.out.println();

		System.out.println(SWValue + " = 0x" + Integer.toHexString(EX_MEM_WRITE.getSWValue()) + ", " + WriteRegNum
				+ " = $" + Integer.toHexString(EX_MEM_WRITE.getWriteRegNum()));
		System.out.println();

		// Print EX/MEM Read
		System.out.println();
		System.out.println("EX/MEM Read");
		System.out.println("-----------------------------------------------");
		System.out.format("Control: " + MemRead + " = " + EX_MEM_READ.getMemRead() + ", " + MemWrite + " = "
				+ EX_MEM_READ.getMemWrite() + ", " + Branch + " = " + EX_MEM_READ.getBranch() + ", " + MemToReg + " = "
				+ EX_MEM_READ.getMemToReg() + ", " + RegWrite + " = " + EX_MEM_READ.getRegWrite());
		System.out.println();
		System.out.format(CalcBTA + " = 0x" + Integer.toHexString(EX_MEM_READ.getCalcBTA()) + ", " + Zero + " = "
				+ EX_MEM_READ.isZero() + ", " + ALUResult + " = 0x" + Integer.toHexString(EX_MEM_READ.getALUResult()));
		System.out.println();

		System.out.println(SWValue + " = 0x" + Integer.toHexString(EX_MEM_READ.getSWValue()) + ", " + WriteRegNum
				+ " = $" + Integer.toHexString(EX_MEM_READ.getWriteRegNum()));
		System.out.println();

		// Print MEM/WB Write
		System.out.println();
		System.out.println("MEM/WB Write");
		System.out.println("-----------------------------------------------");
		System.out.format("Control: " + MemToReg + " = " + MEM_WB_WRITE.getMemToReg() + ", " + RegWrite + " = "
				+ MEM_WB_WRITE.getRegWrite());
		System.out.println();
		System.out.format(LWDataValue + " = 0x" + Integer.toHexString(MEM_WB_WRITE.getLWDataValue()) + ", " + ALUResult
				+ " = 0x" + Integer.toHexString(MEM_WB_WRITE.getALUResult()) + ", " + WriteRegNum + " = $"
				+ Integer.toHexString(MEM_WB_WRITE.getWriteRegNum()));
		System.out.println();

		// Print MWM/WB Read
		System.out.println();
		System.out.println("MEM/WB Read");
		System.out.println("-----------------------------------------------");
		System.out.format("Control: " + MemToReg + " = " + MEM_WB_READ.getMemToReg() + ", " + RegWrite + " = "
				+ MEM_WB_READ.getRegWrite());
		System.out.println();
		System.out.format(LWDataValue + " = 0x" + Integer.toHexString(MEM_WB_READ.getLWDataValue()) + ", " + ALUResult
				+ " = 0x" + Integer.toHexString(MEM_WB_READ.getALUResult()) + ", " + WriteRegNum + " = $"
				+ Integer.toHexString(MEM_WB_READ.getWriteRegNum()));
		System.out.println();
		System.out.println();

		// print out Registers
		System.out.println("___REGISTERS___");
		System.out.println("---------------");

		for (int i = 0; i < Regs.length; i++) {

			System.out.print("$" + i + " = 0x" + Integer.toHexString(Regs[i]) + "," + " ");
			if (i == 8 || i == 16 || i == 24) {
				System.out.println();
			}

		}

		System.out.println();

	}

	public void copy_write_to_read() throws CloneNotSupportedException {

		IF_ID_READ = (IF_ID_Model) IF_ID_WRITE.clone();
		ID_EX_READ = (ID_EX_Model) ID_EX_WRITE.clone();
		EX_MEM_READ = (EX_MEM_Model) EX_MEM_WRITE.clone();
		MEM_WB_READ = (MEM_WB_Model) MEM_WB_WRITE.clone();

	}

}
