package sample;

class Parameter {
	
	
	private String name;//名称
	private String time;//発生日時
	private String depth;//深さ
	private String magnitude;//マグニチュード
	private String source;//引用元
	private String scale;//震度
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getDepth() {
		return depth;
	}
	
	public void setDepth(String depth) {
		this.depth = depth;
	}
	public String getMagnitude() {
		return magnitude;
	}
	public void setMagnitude(String magnitude) {
		this.magnitude = magnitude;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getScale() {
		return scale;
	}
	public void setScale(String scale) {
		this.scale = scale;
	}

}
