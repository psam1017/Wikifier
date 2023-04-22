package wiki.test.history;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import wiki.main.dto.WikiContentDTO;

public class WikiVer4 {

	public static void main(String[] args) {
		
		// Version 4 : 이진 탐색 적용. 또한 이때부터 같은 record가 오직 최초의 rvs만을 기억하도록 설계하기 시작함.
		// 이진 탐색의 시간 복잡도는 최선의 경우 O(1), 평균, 최악의 경우 O(logN)
		// 이진 탐색을 활용한 wiki 불러오기의 시간 복잡도는 최선의 경우 O(n), 평균, 최악의 경우 O(NlogN)
		// 13회 ~ 16회 비교.
		
		// 문제점 : 버전이 낮을 수록 조회가 빠르고 버전이 높을 수록 조회가 느리다
		// 방법 1. DB에 최신 테이블을 추가하고 무조건 최신 테이블에 배치. 배치 프로그램을 주기적으로 실행시켜 최신이 아닌 버전은 일반 테이블로 옮기기.
		//     -> 최신 rvs만 조회가 빠르고 그 직전 rvs는 여전히 느림.
		// 방법 2. 저장할 때 시간이 좀 걸리더라도 최신 rvs일수록 content를 가지도록 하고 과거 rvs의 content는 null로 만들기. 
		//     -> 과거 rvs의 조회가 느려짐.
		// 방법 3. 일정 rvs마다 새로 저장.
		//     -> 조회 속도는 빨라지지만 중복되는 내용이 저장되어 용량을 많이 차지함.
		
		// -> ★서비스 환경에 맞는 최적화가 필요.
		
		List<WikiContentDTO> list1 = new ArrayList<>();
		List<WikiContentDTO> list2 = new ArrayList<>();
		
		makeDummy(list1, list2);
		
		for(WikiContentDTO content2 : list2) {
			if(content2.getContent() == null) {
				int index = Collections.binarySearch(list1, content2, COMPARATOR);
				WikiContentDTO content1 = list1.get(index);
				content2.setContent(content1.getContent());
			}
		}
		
		for(WikiContentDTO content2 : list2) {
			System.out.println(content2.getPreRvs() + "-" + content2.getPreRvsRow() + " : " + content2.getContent());
		}
	}
	
	private static final Comparator<WikiContentDTO> COMPARATOR = new Comparator<WikiContentDTO>() {

		@Override
		public int compare(WikiContentDTO past, WikiContentDTO current) {
			
			int result;
			
			int PR = current.getPreRvs();
			int PRR = current.getPreRvsRow();
			int R = past.getRvs();
			int RR = past.getRvsRow();
			
			if(PR > R) {
				result = -1;
			}
			else  if(PR < R){
				result = 1;
			}
			else{
				if(PRR > RR) {
					result = -1;
				}
				else if(PRR < RR){
					result = 1;
				}
				else {
					result = 0;
				}
			}
			return result;
		}
	};
	
	public static void makeDummy(List<WikiContentDTO> list1, List<WikiContentDTO> list2) {
		
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
