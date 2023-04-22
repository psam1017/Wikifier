package wiki.main.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import wiki.main.dto.WikiContentDTO;

public class Wikifier {

	String seperator;
	
	/**
	 * 문단의 형상을 위키 형태로 관리하는 컨트롤러입니다. 기본 단위는 한 줄, 즉 구분자가 줄바꿈입니다. 구분자는 변경할 수 있습니다.
	 * */
	public Wikifier() {
		this.seperator = System.getProperty("line.separator");
	}
	
	/**
	 * 문단의 형상을 위키 형태로 관리하는 컨트롤러입니다. 원하는 구분자를 입력하십시오.
	 * @param seperator : String. 문단을 나눌 구분자입니다.
	 * */
	public Wikifier(String seperator) {
		this.seperator = seperator;
	}

	/**
	 * 원본 텍스트를 구분자(기본 줄바꿈)로 나누어 SCI로 추출하고, 이를 담은 List를 반환합니다.
	 * 
	 * @param subject : String. 위키의 주제입니다.
	 * @param rvs : int. 문단의 버전입니다. 최신 버전을 입력하십시오.
	 * @param contentText : String. 원본 텍스트입니다.
	 * @return List<WikiContentDTO> : SCI를 담은 List를 반환합니다. 만약 최초 작성이라면 return 값을 그대로 DB에 저장하고, 그렇지 않다면 revise 메소드를 사용하십시오.
	 * @see <a href="https://github.com/psam1017/SimpleWikifier">github 바로가기</a>
	 * */
	public List<WikiContentDTO> seperate(String subject, int rvs, String contentText){
		
		List<WikiContentDTO> contents = new ArrayList<>();
		String[] contentArray = contentText.split(seperator, -1); 
		
		for(int i = 1; i <= contentArray.length; i++) {
			WikiContentDTO contentVO = new WikiContentDTO();
			contentVO.setSubject(subject);
			contentVO.setRvs(rvs);
			contentVO.setRvsRow(i - 1);
			contentVO.setPreRvs(rvs);
			contentVO.setPreRvsRow(i - 1);
			contentVO.setContent(contentArray[i - 1]);
			contents.add(contentVO);
		}
		return contents;
	}
	
	/**
	 * 불러오려는 subject와 rvs의 null인 content를 채웁니다. 사용자가 불러오려는 rvs의 content를 보여주기 위해 이 메소드를 사용하십시오.
	 * @param currentList : List<WikiContentDTO>. 불러오려는 subject와 rvs의 SCI가 담긴 List입니다.
	 * @param pastList : List<WikiContentDTO>. 불러오려는 rvs 이전 모든 subject와 rvs의 SCI가 담긴 List입니다. 이 파라미터에는 반드시 content가 null이 아닌 SCI를 rvs, rvsRow 오름차순으로 담으십시오.
	 * @see <a href="https://github.com/psam1017/SimpleWikifier">github 바로가기</a>
	 * */
	public void connect(List<WikiContentDTO> currentList, List<WikiContentDTO> pastList) {
		for(WikiContentDTO current : currentList) {
			if(current.getContent() == null) {
				int index = Collections.binarySearch(pastList, current, WIKI_RVS_COMPARATOR);
				WikiContentDTO past = pastList.get(index);
				current.setContent(past.getContent());
			}
		}
	}
	
	/**
	 * 개정한 문단의 변경된 부분만을 저장할 수 있도록 이전의 모든 rvs와 비교하여 같은 내용의 content는 null로 변경합니다. 더 효율적인 연산을 위해 직전 rvs의 리스트와 비교합니다. 개정된 rvs의 위키를 DB에 저장하기 위해 이 메소드를 사용하십시오.<br>
	 * 만약 최초 작성이라면 seperate의 결과를 그대로 DB에 저장하십시오.
	 * 
	 * @param currentList : List<WikiContentDTO>. 개정하려는 rvs의 subject의 List입니다. 이 List를 얻기 위해 seperate 메소드를 사용하십시오.
	 * @param preList : List<WikiContentDTO>. 개정하려는 rvs 직전 rvs의 List입니다. 이 파라미터에는 반드시 content가 null이 아닌 SCI를 rvs, rvsRow 오름차순으로 담으십시오.
	 * @param pastList : List<WikiContentDTO>. preList 이전 모든 rvs의 List입니다. 이 파라미터에는 반드시 content가 null이 아닌 SCI를 rvs, rvsRow 오름차순으로 담으십시오.
	 * @see <a href="https://github.com/psam1017/SimpleWikifier">github 바로가기</a>
	 * */
	public void revise(List<WikiContentDTO> currentList, List<WikiContentDTO> preList, List<WikiContentDTO> pastList) {
		setPreRvsFromPre(currentList, preList);
		sortByContent(pastList);
		setPreRvsFromPast(currentList, pastList);
	}
	
	/**
	 * 개정한 문단의 변경된 부분만을 저장할 수 있도록 이전의 모든 rvs와 비교하여 같은 내용의 content는 null로 변경합니다. 직전 rvs의 리스트와 비교하지 않습니다. 개정된 rvs의 위키를 DB에 저장하기 위해 이 메소드를 사용하십시오.<br>
	 * 만약 최초 작성이라면 seperate의 결과를 그대로 DB에 저장하십시오.
	 * 
	 * @param currentList : List<WikiContentDTO>. 개정하려는 rvs의 subject의 List입니다. 이 List를 얻기 위해 seperate 메소드를 사용하십시오.
	 * @param allList : List<WikiContentDTO>. preList 이전 모든 rvs의 List입니다. 이 파라미터에는 반드시 content가 null이 아닌 SCI를 rvs, rvsRow 오름차순으로 담으십시오.
	 * @see <a href="https://github.com/psam1017/SimpleWikifier">github 바로가기</a>
	 * */
	public void revise(List<WikiContentDTO> currentList, List<WikiContentDTO> allList) {
		sortByContent(allList);
		setPreRvsFromAll(currentList, allList);
	}
	
	private void setPreRvsFromPre(List<WikiContentDTO> currentList, List<WikiContentDTO> preList) {
		
		for(WikiContentDTO currentContent : currentList) {
			Iterator<WikiContentDTO> iter = preList.iterator();
			while(iter.hasNext()) {
				WikiContentDTO preContent = iter.next();
				if(currentContent.getContent().equals(preContent.getContent())) {
					currentContent.setPreRvs(preContent.getPreRvs());
					currentContent.setPreRvsRow(preContent.getPreRvsRow());
					currentContent.setContent(null);
					preList.remove(preContent);
					break;
				}
			}
		}
	}
	
	private void sortByContent(List<WikiContentDTO> list) {
		Collections.sort(list, WIKI_CONTENT_COMPARATOR);
	}
	
	private void setPreRvsFromPast(List<WikiContentDTO> currentList, List<WikiContentDTO> pastList) {
		
		for(WikiContentDTO currentContent : currentList) {
			if(currentContent.getContent() != null) {
				int index = Collections.binarySearch(pastList, currentContent, WIKI_CONTENT_COMPARATOR);
				if(index >= 0) {
					WikiContentDTO pastContent = pastList.get(index);
					currentContent.setPreRvs(pastContent.getPreRvs());
					currentContent.setPreRvsRow(pastContent.getPreRvsRow());
					currentContent.setContent(null);
				}
			}
		}
	}
	
	private void setPreRvsFromAll(List<WikiContentDTO> currentList, List<WikiContentDTO> allList) {
		
		for(WikiContentDTO currentContent : currentList) {
			int index = Collections.binarySearch(allList, currentContent, WIKI_CONTENT_COMPARATOR);
			if(index >= 0) {
				WikiContentDTO pastContent = allList.get(index);
				currentContent.setPreRvs(pastContent.getPreRvs());
				currentContent.setPreRvsRow(pastContent.getPreRvsRow());
				currentContent.setContent(null);
			}
		}
	}
	
	private final Comparator<WikiContentDTO> WIKI_RVS_COMPARATOR = new Comparator<WikiContentDTO>() {
		
		// 현재 버전의 PR과 이전 버전의 R이 같은가?
		//     -> 같다면, 현재 버전의 PRI와 이전 버전의 RI가 같은가?
		@Override
		public int compare(WikiContentDTO past, WikiContentDTO current) {
			
			int PR = current.getPreRvs();
			int PRR = current.getPreRvsRow();
			int R = past.getRvs();
			int RR = past.getRvsRow();
			
			if(PR > R) { return -1; }
			else  if(PR < R){ return 1; }
			else{
				if(PRR > RR) { return -1; }
				else if(PRR < RR){ return 1; }
			}
			return 0;
		}
	};
	
	private final Comparator<WikiContentDTO> WIKI_CONTENT_COMPARATOR = new Comparator<WikiContentDTO>() {
		
		// 1. 문자열의 길이가 같은가?
		// 2. 각 char 배열의 값이 같은가?
		@Override
		public int compare(WikiContentDTO w1, WikiContentDTO w2) {
			
			char[] o1 = w1.getContent().toCharArray();
			char[] o2 = w2.getContent().toCharArray();
			
			char c1, c2;
			
			int length = o1.length > o2.length ? o2.length : o1.length;
			
			if(o1.length != o2.length) {
				for(int i = 0; i < length; i++) {
					c1 = o1[i];
					c2 = o2[i];
					
					if(c1 > c2) { return 1; }
					else if (c1 < c2){ return -1; }
				}
				return o1.length > o2.length ? 1 : -1;
			}
			else {
				for(int i = 0; i < length; i++) {
					c1 = o1[i];
					c2 = o2[i];
					
					if(c1 > c2) { return 1; }
					else if(c1 < c2) { return -1; }
				}
				return 0;
			}
		}
	};
}
