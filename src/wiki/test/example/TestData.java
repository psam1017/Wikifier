package wiki.test.example;

import java.util.List;

import wiki.main.dto.WikiContentDTO;
import wiki.main.dto.WikiInfoDTO;
import wiki.main.service.Wikifier;

public class TestData {

	Wikifier wikifier = new Wikifier();

	String text1 = "죽는 날까지 하늘을 우러러\r\n"
			+ "한 점 부끄럼이 없기를,\r\n"
			+ "잎새에 이는 바람에도\r\n"
			+ "나는 괴로워했다.\r\n"
			+ "별을 노래하는 마음으로\r\n"
			+ "모든 죽어 가는 것을 사랑해야지\r\n"
			+ "그리고 나한테 주어진 길을\r\n"
			+ "걸어가야겠다.\r\n"
			+ "\r\n"
			+ "오늘 밤에도 별이 바람에 스치운다.";
	
	WikiInfoDTO info1;
	List<WikiContentDTO> list1;
	
	public WikiInfoDTO getInfo1() {
		info1 = new WikiInfoDTO();
		info1.setSubject("서시");
		info1.setRvs(1);
		return info1;
	}
	
	String text2 = "죽는 날까지 하늘을 우르러\r\n"
			+ "한 점 부끄럼이 없기를,\r\n"
			+ "잎새에 이는 바람에도\r\n"
			+ "나는 괴로워했다.\r\n"
			+ "별을 노래하는 마음으로\r\n"
			+ "모든 죽어 가는 것을 사랑해야지\r\n"
			+ "그리고 나안테 주어진 길을\r\n"
			+ "거러가야겠다.\r\n"
			+ "\r\n"
			+ "오늘 밤에도 별이 바람에 스치운다.\r\n"
			+ "\r\n"
			+ "1941.11.20.";
	
	WikiInfoDTO info2;
	List<WikiContentDTO> list2;
	
	public WikiInfoDTO getInfo2() {
		info2 = new WikiInfoDTO();
		info2.setSubject("서시");
		info2.setRvs(2);
		return info2;
	}
}
