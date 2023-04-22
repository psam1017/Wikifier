package wiki.test.history;

import java.util.LinkedList;
import java.util.List;

import wiki.main.dto.WikiContentDTO;

public class WikiVer2 {
	public static void main(String[] args) {
		
		// Version 2 : LinkedList�� ����ϰ�, ã�� ������ ���ָ鼭 �� ��� ������ �ٿ�������.
		// 25ȸ �� -> 15ȸ ��(�ּ��� ��� O(n), �־��� ��� O(n^2)).
		// ������ : ���� ������ ���ϴ� ���� �������� ����. �� �� ȿ������ Ž���� �ʿ���. -> �ϴ� ���� ���� ��� ��������.
		
		int rvs = 2;
		List<WikiContentDTO> list1 = new LinkedList<>();
		
		makeDummy(list1);
		
		int count = 0;
		
		for(int i = 0; i < list1.size(); i++) {
			WikiContentDTO content2 = list1.get(i);
			if(content2.getRvs() == rvs) {
				i--;
				if(content2.getRvs() == content2.getPreRvs()) {
					count++;
					System.out.println("(new line) " + content2.getRvs() + "-" + content2.getRvsRow() + " : " + content2.getContent());
					list1.remove(content2);
				}
				else {
					for(int j = 0; j < list1.size(); j++) {
						count++;
						WikiContentDTO content1 = list1.get(j);
						if(content2.getPreRvs() == content1.getRvs() && content2.getPreRvsRow() == content1.getRvsRow()) {
							System.out.println("(old line) " + content1.getRvs() + "-" + content1.getRvsRow() + " : " + content1.getContent());
							list1.remove(content1);
							break;
						}
					}
				}
			}
			else {
				count++;
			}
		}
		System.out.println("�ݺ� Ƚ�� : " + count);
	}
	
	public static void makeDummy(List<WikiContentDTO> list1) {
		
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
			list1.add(temp);
		}
		
		for(int i = 5; i < contents.length; i++) {
			WikiContentDTO temp = new WikiContentDTO();
			temp.setSubject(subject);
			temp.setRvs(2);
			temp.setRvsRow(i);
			temp.setPreRvs(2);
			temp.setPreRvsRow(i);
			temp.setContent(contents[i]);
			list1.add(temp);
		}
	}
}