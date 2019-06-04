/**
 * 
 */
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
public class PipeDrive {

	int[] Instruct = new int[] { 0x8CE90014, 0x032BA020, 0x12A90003, 0x158FFFF7, 0x8D070001 };

	/**
	 * 
	 */
	public PipeDrive() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 * @throws CloneNotSupportedException
	 */

///////////////////////////////////////////////////////////////////
/// main program entry point ///
/// Input : arguments. (these are ignored for this program) ///
/// Output: None ///
/// Returns nothing ///
/// ///
///////////////////////////////////////////////////////////////////
	public static void main(String[] args) throws CloneNotSupportedException {
		// TODO Auto-generated method stub

		PipeDrive p = new PipeDrive();
		p.doIt();

	}

	///////////////////////////////////////////////////////////////////
	/// doIt() (Worker method called from main) ///
	/// Input : None ///
	/// Output: None ///
	/// Returns Nothing ///
	public void doIt() throws CloneNotSupportedException {

		//Pipeline Object
		PipeLine pip = new PipeLine();
		
		// init Main_Mem memory
		pip.initMem();
		// init all 32 registers
		pip.initRegs();
		// run the pipeline simulation
		pip.runSim();
	}
}
