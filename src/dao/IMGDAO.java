package dao;

public class IMGDAO {
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "IMGDAO [name=" + name + ", url=" + url + ", id=" + id + "]";
	}

	private String name;
	private String url;
	private String id;
}
