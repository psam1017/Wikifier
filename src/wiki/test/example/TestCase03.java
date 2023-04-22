package wiki.test.example;

import java.util.List;

import wiki.main.dto.WikiContentDTO;
import wiki.main.dto.WikiInfoDTO;
import wiki.main.service.Wikifier;

public class TestCase03 {
	public static void main(String[] args) {
		
		// seperate
		Wikifier wikifier = new Wikifier();
		TestData dummy = new TestData();
		
		WikiInfoDTO info1 = dummy.getInfo1();
		String text1 = dummy.text1;
		
		List<WikiContentDTO> list1 = wikifier.seperate(info1.getSubject(), info1.getRvs(), text1);
		

		
		// revise
		WikiInfoDTO info2 = dummy.getInfo2();
		String text2 = dummy.text2;
		
		List<WikiContentDTO> list2 = wikifier.seperate(info2.getSubject(), info2.getRvs(), text2);
		
		wikifier.revise(list2, list1); // list1의 컨텐츠를 가나다 순으로 정렬하고 이진 탐색을 시도합니다.
		
		
		
		// revise하면서 컨텐츠를 가나다 순으로 정렬했던 list1을 다시 복원.
		// connect를 사용하기 위해서는 DB에서 pastList 또는 allList를 rvs, rvsRow 오름차 순으로 가져오십시오.
		list1 = wikifier.seperate(info1.getSubject(), info1.getRvs(), text1);
		
		
		
		// connect test 시작.
		wikifier.connect(list2, list1);
		
		// DB에서 가져온 info와, connect 메소드를 통해 복원한 List를 사용자에게 반환하십시오.
		System.out.println("----------connect test----------");
		System.out.println(info2.getSubject() + " ver." + info2.getRvs() + "\r\n");
		for(WikiContentDTO dto : list2) {
			System.out.println(dto.getContent());
		}
	}
}
