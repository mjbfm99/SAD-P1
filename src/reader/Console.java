package reader;
import java.util.Observable;
import java.util.Observer;

@SuppressWarnings("deprecation")
public class Console implements Observer {  // View (Observer)

	static enum Opcode {
		OCL, OCR, OCHOME, OCEND, OCDEL, OCBKSP, OCCTRLC, OCINS
	}
	
	static class Command {
		Opcode op;
		int arg;
		
		Command (Opcode op) {
			this.op = op;
		}
		
		Command (Opcode op, int arg){
			this.op = op;
			this.arg = arg;
		}
	}
	
	Line model;
	
	Console(Line line){
		model = line;
	}
	
	public void update  (Observable o, Object arg) {
		//System.out.print("UPDATING");
		Command comm = (Command) arg;
		switch (comm.op) {
			case OCBKSP:
				//System.out.print("\033[D");
				System.out.print("\033[P");
				break;
			case OCCTRLC:
				break;
			case OCDEL:
				System.out.print("\033[P");
				break;
			case OCEND:
				System.out.print("\033[" + comm.arg + "C");
				break;
			case OCHOME:
				System.out.print("\033["+ comm.arg + "D");
				break;
			case OCINS:
				break;
			case OCL:
				System.out.print("\033[D");
				break;
			case OCR:
				System.out.print("\033[C");
				break;
			default:
				break;
		}
	}
	
	
}
