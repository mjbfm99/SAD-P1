import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class EditableBufferedReader extends BufferedReader { 

	private final static int DERECHA = -62;
	private final static int IZQUIERDA = -61;
	//private final static int ARRIBA = -64;
	//private final static int ABAJO = -63;
	private final static int HOME = -57;
	private final static int END = -59;
	private final static int SUPR = -78;
	private final static int BACKSPACE = -2;
	//private final static int ESC = -102;
	private final static int CTRL_C = 3;
	private final static int INSERT = -79; 
	
	public EditableBufferedReader(Reader in) {
		super(in);
	}
	
	public void setRaw() {
	    String[] cmd = {"/bin/sh", "-c", "stty raw -echo </dev/tty"};
	       try {
			Runtime.getRuntime().exec(cmd).waitFor();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void unsetRaw(){
		String[] cmd = {"/bin/sh", "-c", "stty cooked echo </dev/tty"};
	       try {
			Runtime.getRuntime().exec(cmd).waitFor();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int read() throws IOException {
		int ret = super.read();
		
		
		if(ret == 27) {
			ret = super.read();
			//System.out.print(" *"+ret+"*");
			ret = super.read();
			//System.out.print(" *"+ret+"*");
			if(ret != 51 && ret != 50) {
				ret = ret - 129;
			}
			else {
				super.read();
				ret = ret - 129;
			}
		}
		else if(ret == 127) {
			ret = ret - 129;
		}
		//System.out.print(" *"+ret+"*");
		return ret;
	}
	
	public String readLine() throws IOException {
		Line linea = new Line();
		setRaw();
		int ch = 0;
		do {
			ch = read();
			if(ch > 31) {
				linea.addChar((char) ch);
				System.out.print("\033[@");
				System.out.print((char) ch);
			}
			else {
				switch (ch) {
				case DERECHA:
					if(linea.getPosition() < linea.getLength()){
						System.out.print("\033[C");
					}
					linea.incPosition();
					break;
				case IZQUIERDA:
					linea.decPosition();
					System.out.print("\033[D");
					break;
				case HOME:
					System.out.print("\033["+linea.goToHome()+"D");
					break;
				case END:
                    if(linea.getPosition() != linea.getLength()){
                        System.out.print("\033["+linea.goToEnd()+"C");
                        linea.setPosition(linea.getLength());
                    }
					break;
				case BACKSPACE:
					linea.backspace();
					System.out.print("\033[D");
					System.out.print("\033[P");
					break;
				case INSERT:
					linea.toggleInsert();
					break;
				case SUPR:
					linea.del();
					System.out.print("\033[P");
					break;
				default:
					break;
				}
					
			}
		} while(ch != 13 && ch != CTRL_C);
		unsetRaw();
		return linea.getContent();
	}
} 
