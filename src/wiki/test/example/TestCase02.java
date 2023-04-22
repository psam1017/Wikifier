package wiki.test.example;

import java.util.List;

import wiki.main.dto.WikiContentDTO;
import wiki.main.dto.WikiInfoDTO;
import wiki.main.service.Wikifier;

public class TestCase02 {
	public static void main(String[] args) {

		// seperate
		Wikifier wikifier = new Wikifier();
		TestData dummy = new TestData();
		
		WikiInfoDTO info1 = dummy.getInfo1();
		String text1 = dummy.text1;
		
		List<WikiContentDTO> list1 = wikifier.seperate(info1.getSubject(), info1.getRvs(), text1);
		
		
		
		// revise test
		WikiInfoDTO info2 = dummy.getInfo2();
		String text2 = dummy.text2;
		
		List<WikiContentDTO> list2 = wikifier.seperate(info2.getSubject(), info2.getRvs(), text2);
		
		wikifier.revise(list2, list1);
		
		// info와, revise 메소드를 통해 변경한 List를 DB에 저장하십시오.
		System.out.println("----------revise test----------");
		System.out.println(info2.getSubject() + " ver." + info2.getRvs() + "\r\n");
		for(WikiContentDTO dto : list2) {
			System.out.println(dto.getPreRvs() + "-" + dto.getPreRvsRow() + " : " + dto.getContent());
		}
	}
}
