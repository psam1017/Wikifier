package wiki.test.example;

import java.util.List;

import wiki.main.dto.WikiContentDTO;
import wiki.main.dto.WikiInfoDTO;
import wiki.main.service.Wikifier;

public class TestCase01 {
	public static void main(String[] args) {

		// seperate test
		Wikifier wikifier = new Wikifier();
		TestData dummy = new TestData();
		
		WikiInfoDTO info1 = dummy.getInfo1();
		String text1 = dummy.text1;
		
		List<WikiContentDTO> list1 = wikifier.seperate(info1.getSubject(), info1.getRvs(), text1);
		
		// info와, seperate 메소드를 통해 생성한 List를 DB에 저장하십시오.
		System.out.println("----------seperate test----------");
		System.out.println(info1.getSubject() + " ver." + info1.getRvs() + "\r\n");
		for(WikiContentDTO dto : list1) {
			System.out.println(dto.getContent());
		}
	}
}
