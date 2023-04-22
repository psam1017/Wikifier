package wiki.main.dto;

public class WikiContentDTO {
	
	private String subject;
	private int rvs;
	private int rvsRow;
	private int preRvs;
	private int preRvsRow;
	private String content;
	
	public WikiContentDTO() { ; }
	
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public int getRvs() {
		return rvs;
	}
	public void setRvs(int rvs) {
		this.rvs = rvs;
	}
	public int getRvsRow() {
		return rvsRow;
	}
	public void setRvsRow(int rvsRow) {
		this.rvsRow = rvsRow;
	}
	public int getPreRvs() {
		return preRvs;
	}
	public void setPreRvs(int preRvs) {
		this.preRvs = preRvs;
	}
	public int getPreRvsRow() {
		return preRvsRow;
	}
	public void setPreRvsRow(int preRvsRow) {
		this.preRvsRow = preRvsRow;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
