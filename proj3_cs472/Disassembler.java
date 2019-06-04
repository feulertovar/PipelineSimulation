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

public class Disassembler {

	public Disassembler() {
		// TODO Auto-generated constructor stub
	}

	private static int curAddress = 0x0;
	private static int pcConstant = 0x4;

	// get program counter constant
	public static int getPcConstant() {
		return pcConstant;
	}

	// update program counter constant
	public static void setPcConstant(int pcConstant) {
		Disassembler.pcConstant = pcConstant;
	}

	// get current memory address
	public static int getCurAddress() {
		return curAddress;
	}

	// set current memory address
	public static void setCurAddress(int curAddress) {
		Disassembler.curAddress = curAddress;
	}
	
	public Rformat RDisassembler(int inst) {
		Rformat instR = new Rformat();

		// Opcode
		instR.setOpcode(inst >>> 26);
		// SrcReg1
		instR.setSrcreg1((inst & Bitmask.SREG1.getMask()) >>> 21);
		// SrcReg2
		instR.setSrcreg2((inst & Bitmask.SREG2.getMask()) >>> 16);
		// DestReg
		instR.setDestreg((inst & Bitmask.DESTREG.getMask()) >>> 11);
		// Function
		instR.setFunction((inst & Bitmask.FUNCTION.getMask()));

		// Calculate Sign Extended Offset
		instR.setOffset((short) ((inst & Bitmask.OFFSET.getMask())));
	
		//
		int offset = instR.getOffset();

		if (offset >= 0) {
			instR.setSEOffset(instR.getOffset() & Bitmask.PEOFFSET.getMask());
		} else if (offset < 0 ) {
			instR.setSEOffset(instR.getOffset() & Bitmask.NEOFFSET.getMask());
		}
		//
		//instR.setSEOffset(instR.getOffset() << 2);
		

		return instR;
	}

	public Iformat IDisabssembler(int inst) {
		Iformat instI = new Iformat();

		// Opcode
		instI.setOpcode(inst >>> 26);
		// SrcReg1
		instI.setSrcreg((inst & Bitmask.SREG1.getMask()) >>> 21);
		// DestReg
		instI.setSrcdest((inst & Bitmask.SRCDEST.getMask()) >>> 16);
		//Regs 11 to 15
		instI.setReg1115((inst & Bitmask.DESTREG.getMask()) >>> 11);
		// Offset
		instI.setOffset((short) ((inst & Bitmask.OFFSET.getMask())));
		//Function
		instI.setFunction((inst & Bitmask.FUNCTION.getMask()));
		
		// Calculate Sign Extended Offset
		int offset = (short)instI.getOffset();

		if (offset >= 0) {
			instI.setSEOffset(instI.getOffset() & Bitmask.PEOFFSET.getMask());
		} else if (offset < 1) {
			instI.setSEOffset(instI.getOffset() & Bitmask.NEOFFSET.getMask());
		}
		
		//only for branches ( CalcBT )
		//instI.setSEOffset(instI.getOffset() << 2);

		return instI;
	}

	// Show output in R-Format
	public void DisplayR(Rformat instR) {

		String strfunc;

		switch (instR.getFunction()) {
		case 0x20:
			strfunc = "add";
			break;
		case 0x22:
			strfunc = "sub";
			break;
		case 0x24:
			strfunc = "and";
			break;
		case 0x25:
			strfunc = "or";
			break;
		case 0x2a:
			strfunc = "slt";
			break;
		case 0x23:
			strfunc = "lw";
			break;
		case 0x2b:
			strfunc = "sw";
			break;
		case 0x4:
			strfunc = "beq";
			break;
		case 0x5:
			strfunc = "bne";
			break;

		default:
			strfunc = "Invalid function " + Integer.toHexString(instR.getFunction());
			break;
		}

		// RFormat
		System.out.println("0x" + Integer.toHexString(Disassembler.curAddress) + " " + strfunc + " " + "$"
				+ instR.getDestreg() + ", " + "$" + instR.getSrcreg1() + ", " + "$" + instR.getSrcreg2() + "\n");

	}

	// Show output in I-Format
	public void DisplayI(Iformat instI) {

		String stropcode;
		boolean branch = false;
		short deCompressedOffset = 0;
		int branchAddress = 0;

		switch (instI.getOpcode()) {
		case 0x20:
			stropcode = "add";
			break;
		case 0x22:
			stropcode = "sub";
		case 0x24:
			stropcode = "and";
			break;
		case 0x25:
			stropcode = "or";
			break;
		case 0x2a:
			stropcode = "slt";
			break;
		case 0x23:
			stropcode = "lw";
			break;
		case 0x2b:
			stropcode = "sw";
			break;
		case 0x4:
			stropcode = "beq";
			branch = true;
			deCompressedOffset = (short) ((instI.getOffset()) << 2);
			break;
		case 0x5:
			stropcode = "bne";
			branch = true;
			deCompressedOffset = (short) ((instI.getOffset()) << 2);
			break;

		default:
			stropcode = "Invalid function " + Integer.toHexString(instI.getOpcode());
			break;
		}

		// IFormat
		if (branch) {

			branchAddress = Disassembler.getCurAddress() + deCompressedOffset + Disassembler.pcConstant;

			System.out.println("0x" + Integer.toHexString(Disassembler.curAddress) + " " + stropcode + " " + "$"
					+ instI.getSrcreg() + ", " + "$" + instI.getSrcdest() + " " + "address " + "0x"
					+ Integer.toHexString(branchAddress) + "\n");

			// Disasembler.setCurAddress(branchAddress);

		} else {
			System.out.println("0x" + Integer.toHexString(Disassembler.curAddress) + " " + stropcode + " " + "$"
					+ instI.getSrcdest() + ", " + instI.getOffset() + "(" + "$" + instI.getSrcreg() + ")" + "\n");
		}

	}

}
