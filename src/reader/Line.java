package reader;

import java.lang.StringBuilder;
import java.util.Observable;


@SuppressWarnings("deprecation")
public class Line extends Observable { // Model (Observable)
	private int cur_pos;
	//private String content;
	private StringBuilder sb;
	private boolean insertMode;
	
	public Line() {
		cur_pos = 0;
		sb = new StringBuilder("");
		insertMode = false;
	}
	
	public int getPosition() {
		return cur_pos;
	}
	
	public void toggleInsert() {
		insertMode = !insertMode;
	}

	public int getLength() {
		return sb.length();
	}
	
	public void setPosition(int position) {
		//x = position % columns;
		//y = position / columns;
		cur_pos = position;
	}
	
	public void incPosition() {
		if(cur_pos < sb.length()) {
			cur_pos++;
			setChanged();
			notifyObservers(new Console.Command(Console.Opcode.OCR));
		}
	}
	
	public void decPosition() {
		if(cur_pos != 0) {
			cur_pos--;
		}
		setChanged();
		notifyObservers(new Console.Command(Console.Opcode.OCL));
	}
	
	public void goToHome() { // move to beginning of the line and reset cur_pos
		setChanged();
		notifyObservers(new Console.Command(Console.Opcode.OCHOME, cur_pos));
		cur_pos = 0;
	}
	
	public void goToEnd() {
		if(getPosition() != getLength()){
			setChanged();
			notifyObservers(new Console.Command(Console.Opcode.OCEND, getLength() - getPosition()));
			setPosition(getLength());
		}
	}
	
	public void backspace() {
		//Delete last character
		if(sb.length() != 0) {
			//content = content.substring(0, content.length() - 1);
			sb.deleteCharAt(cur_pos - 1);
			decPosition();
			setChanged();
			notifyObservers(new Console.Command(Console.Opcode.OCBKSP));
		}
	}
	
	public void del() {
		if(sb.length() != 0 && getPosition() != sb.length()) {
			sb.deleteCharAt(cur_pos);
			setChanged();
			notifyObservers(new Console.Command(Console.Opcode.OCDEL));
		}
	}
	
	public String getContent() {
		return sb.toString();
	}
	
	public void addChar(char c) {
		if(!insertMode) {
			sb.insert(cur_pos, c); 
			//incPosition();
			cur_pos++;
		}
		else {
			System.out.print("\033[P");
			sb.insert(cur_pos, c);
			if(cur_pos + 1 < sb.length()) {
				sb.deleteCharAt(cur_pos + 1);
			}
			//incPosition();
			cur_pos++;
		}
	}
	
	public String toString() {
		return "Line: " + sb + ", position: " + getPosition();
	}
}
