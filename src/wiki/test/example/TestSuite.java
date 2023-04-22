package wiki.test.example;

import java.util.List;

import wiki.main.dto.WikiContentDTO;
import wiki.main.dto.WikiInfoDTO;
import wiki.main.service.Wikifier;

public class TestSuite {
	public static void main(String[] args) {
		
		// seperate test
		Wikifier wikifier = new Wikifier();
		TestData dummy = new TestData();
		
		WikiInfoDTO info1 = dummy.getInfo1();
		String text1 = dummy.text1;
		
		List<WikiContentDTO> list1 = wikifier.seperate(info1.getSubject(), info1.getRvs(), text1);
		
		// info와, seperate 메소드를 통해 생성한 content를 DB에 저장하십시오.
		System.out.println("----------seperate test----------");
		System.out.println(info1.getSubject() + " ver." + info1.getRvs() + "\r\n");
		for(WikiContentDTO dto : list1) {
			System.out.println(dto.getContent());
		}
		
		
		
		// revise test
		WikiInfoDTO info2 = dummy.getInfo2();
		String text2 = dummy.text2;
		
		List<WikiContentDTO> list2 = wikifier.seperate(info2.getSubject(), info2.getRvs(), text2);
		
		wikifier.revise(list2, list1);
		
		// info와, revise 메소드를 통해 변경한 content를 DB에 저장하십시오.
		System.out.println("\n\n\n\n\n----------revise test----------");
		System.out.println(info2.getSubject() + " ver." + info2.getRvs() + "\r\n");
		for(WikiContentDTO dto : list2) {
			System.out.println(dto.getPreRvs() + "-" + dto.getPreRvsRow() + " : " + dto.getContent());
		}
		
		
		
		// revise하면서 컨텐츠를 가나다 순으로 정렬했던 list1을 다시 복원.
		// connect를 사용하기 위해서는 DB에서 pastList 또는 allList를 rvs, rvsRow 오름차 순으로 가져오십시오.
		list1 = wikifier.seperate(info1.getSubject(), info1.getRvs(), text1);
		
		
		
		// connect test 시작.
		wikifier.connect(list2, list1);
		
		// DB에서 가져온 info와, connect 메소드를 통해 복원한 content를 사용자에게 보여주십시오.
		System.out.println("\n\n\n\n\n----------connect test----------");
		System.out.println(info2.getSubject() + " ver." + info2.getRvs() + "\r\n");
		for(WikiContentDTO dto : list2) {
			System.out.println(dto.getContent());
		}
	}
}
