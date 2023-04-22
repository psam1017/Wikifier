package wiki.test.history;

import java.util.LinkedList;
import java.util.List;

import wiki.main.dto.WikiContentDTO;

public class WikiVer2 {
	public static void main(String[] args) {
		
		// Version 2 : LinkedList를 사용하고, 찾은 내용을 없애면서 비교 대상 개수를 줄여나가기.
		// 25회 비교 -> 15회 비교(최선의 경우 O(n), 최악의 경우 O(n^2)).
		// 문제점 : 여러 버전을 비교하는 것을 고려하지 않음. 좀 더 효율적인 탐색이 필요함. -> 일단 여러 버전 기능 구현부터.
		
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
		System.out.println("반복 횟수 : " + count);
	}
	
	public static void makeDummy(List<WikiContentDTO> list1) {
		
		String subject = "서시";
		String[] contents = {"죽는 날까지 하늘을 우러러", "한 점 부끄럼이 없기를,", "잎새에 이는 바람에도", "나는 괴로워했다.", "별을 노래하는 마음으로", "모든 죽어 가는 것을 사랑해야지", "그리고 나한테 주어진 길을", "걸어가야겠다.", "", "오늘 밤에도 별이 바람에 스치운다."};
		
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
