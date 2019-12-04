package dao;

public class TalkDAO {
	
	private String name;
	
	private String talk;
	
	

	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getTalk() {
		return talk;
	}



	public void setTalk(String talk) {
		this.talk = talk;
	}



	@Override
	public String toString() {
		return "TalkDAO [name=" + name + ", talk=" + talk + "]";
	}
}
