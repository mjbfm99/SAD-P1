package reader;
import static reader.Constants.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

@SuppressWarnings("deprecation")
public class EditableBufferedReader extends BufferedReader { // Controller 
	
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
		Line line = null;
		Console console = null;
		try {
			setRaw();
			line = new Line();
			console = new Console(line);
			int ch = 0;
			line.addObserver(console);
			do {
				ch = read();
				if(ch > 31) {
					line.addChar((char) ch);
					System.out.print("\033[@");
					System.out.print((char) ch);
				}
				else {
					switch (ch) {
					case RIGHT:
						line.incPosition();
						break;
					case LEFT:
						line.decPosition();
						break;
					case HOME:
						line.goToHome();
						break;
					case END:
						line.goToEnd();
						break;
					case BACKSPACE:
						line.backspace();
						break;
					case INSERT:
						line.toggleInsert();
						break;
					case DEL:
						line.del();
						break;
					default:
						break;
					}
				}
			} while(ch != 13 && ch != CTRL_C);
		} finally {
			unsetRaw();
		}
		return line.getContent();
	}
} 
