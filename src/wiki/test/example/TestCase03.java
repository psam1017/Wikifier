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
		
		wikifier.revise(list2, list1); // list1�� �������� ������ ������ �����ϰ� ���� Ž���� �õ��մϴ�.
		
		
		
		// revise�ϸ鼭 �������� ������ ������ �����ߴ� list1�� �ٽ� ����.
		// connect�� ����ϱ� ���ؼ��� DB���� pastList �Ǵ� allList�� rvs, rvsRow ������ ������ �������ʽÿ�.
		list1 = wikifier.seperate(info1.getSubject(), info1.getRvs(), text1);
		
		
		
		// connect test ����.
		wikifier.connect(list2, list1);
		
		// DB���� ������ info��, connect �޼ҵ带 ���� ������ List�� ����ڿ��� ��ȯ�Ͻʽÿ�.
		System.out.println("----------connect test----------");
		System.out.println(info2.getSubject() + " ver." + info2.getRvs() + "\r\n");
		for(WikiContentDTO dto : list2) {
			System.out.println(dto.getContent());
		}
	}
}