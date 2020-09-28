
public class Line {
	private int cur_pos;
	private String content;
	
	public Line() {
		cur_pos = 0;
		content = "";
	}
	
	public int getPosition() {
		return cur_pos;
	}
	
	public void setPosition(int position) {
		//x = position % columns;
		//y = position / columns;
		cur_pos = position;
	}
	
	public void incPosition() {
		cur_pos++;
	}
	
	public void decPosition() {
		cur_pos--;
	}
	
	public String getContent() {
		return content;
	}
	
	public void addChar(char c) {
		content = content + Character.toString(c);
		incPosition();
	}
	
	public String toString() {
		return content;
	}
}
