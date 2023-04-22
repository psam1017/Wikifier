package wiki.test.history;

import java.util.ArrayList;
import java.util.List;

import wiki.main.dto.WikiContentDTO;

public class WikiVer1 {

	public static void main(String[] args) {
		
		// Version 1 : 일단 실행 가능한 상태
		// 문제점 : 하나의 리스트에서 무식하게 비교(최선, 최악의 경우 O(n^2))
		// 현재 25회 비교.
		
		int rvs = 2;
		List<WikiContentDTO> list1 = new ArrayList<>();
		
		makeDummy(list1);
		
		int count = 0;
		
		for(WikiContentDTO content2 : list1) {
			if(content2.getRvs() == rvs) {
				if(content2.getRvs() == content2.getPreRvs()) {
					count++;
					System.out.println("(new line) " + content2.getRvs() + "-" + content2.getRvsRow() + " : " + content2.getContent());
				}
				else {
					for(WikiContentDTO content1 : list1) {
						count++;
						if(content2.getPreRvs() == content1.getRvs() && content2.getPreRvsRow() == content1.getRvsRow()) {
							System.out.println("(old line) " + content1.getRvs() + "-" + content1.getRvsRow() + " : " + content1.getContent());
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
