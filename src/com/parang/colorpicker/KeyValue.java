package com.parang.colorpicker;

public class KeyValue {
	private int ID;
	private String Text;
	
	public KeyValue(){
		
	}
	
	public KeyValue(int ID, String Text){
		this.ID=ID;
		this.Text=Text;
	}
	
	public String getText() {
		return Text;
	}
	
	public void setText(String text) {
		Text = text;
	}
	
	public long getID() {
		return ID;
	}
	
	public void setID(int iD) {
		ID = iD;
	}
}
