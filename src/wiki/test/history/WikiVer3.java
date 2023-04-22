package wiki.test.history;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import wiki.main.dto.WikiContentDTO;

public class WikiVer3 {

	public static void main(String[] args) {
		
		// Version 3 : ���� ������ ������ ����. ���� ã�� ������ �� ���� �������� ����Ʈ�� �����Ͽ� ��. �׿� ���� �ݺ�Ƚ���� �ణ ����.
		// 15ȸ �� -> 10ȸ ��(�ּ��� ��� O(n), �־��� ��� O(n^2) ����).
		// ������ : ���� ������ ������ ��� ������ �Ϸ�Ǿ����� ���� Ž���� �ƴ϶� ���� Ž�� �� ����ȭ �ʿ�.
		
		List<WikiContentDTO> list1 = new LinkedList<>();
		List<WikiContentDTO> list2 = new ArrayList<>();
		
		makeDummy(list1, list2);
		
		int count = 0;
		
		for(WikiContentDTO content2 : list2) {
			if(content2.getContent() == null) {
				for(WikiContentDTO content1 : list1) {
					count++;
					if(content2.getPreRvs() == content1.getRvs() && content2.getPreRvsRow() == content1.getRvsRow()) {
						System.out.println("(old line) " + content1.getRvs() + "-" + content1.getRvsRow() + " : " + content1.getContent());
						list1.remove(content1);
						break;
					}
				}
			}
			else {
				count++;
				System.out.println("(new line) " + content2.getRvs() + "-" + content2.getRvsRow() + " : " + content2.getContent());
			}
		}
		System.out.println("�ݺ� Ƚ�� : " + count);
	}
	
	public static void makeDummy(List<WikiContentDTO> list1, List<WikiContentDTO> list2) {
		
		String subject = "����";
		String[] contents = {"�״� ������ �ϴ��� �췯��", "�� �� �β����� ���⸦,", "�ٻ��� �̴� �ٶ�����", "���� ���ο��ߴ�.", "���� �뷡�ϴ� ��������", "��� �׾� ���� ���� ����ؾ���", "�׸��� ������ �־��� ����", "�ɾ�߰ڴ�.", "", "���� �㿡�� ���� �ٶ��� ��ġ���."};
		
		// dummy list 1
		for(int i = 0; i < 5; i++) {
			WikiContentDTO temp = new WikiContentDTO();
			temp.setSubject(subject);
			temp.setRvs(1);
			temp.setRvsRow(i);
			temp.setPreRvs(1);
			temp.setPreRvsRow(i);
			temp.setContent(contents[i]);
			list1.add(temp);
		}
		
		// dummy list 2
		for(int i = 0; i < 5; i++) {
			WikiContentDTO temp = new WikiContentDTO();
			temp.setSubject(subject);
			temp.setRvs(2);
			temp.setRvsRow(i);
			temp.setPreRvs(1);
			temp.setPreRvsRow(i);
			temp.setContent(null);
			list2.add(temp);
		}
		
		for(int i = 5; i < contents.length; i++) {
			WikiContentDTO temp = new WikiContentDTO();
			temp.setSubject(subject);
			temp.setRvs(2);
			temp.setRvsRow(i);
			temp.setPreRvs(2);
			temp.setPreRvsRow(i);
			temp.setContent(contents[i]);
			list2.add(temp);
		}
	}
}