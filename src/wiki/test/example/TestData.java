package wiki.test.example;

import java.util.List;

import wiki.main.dto.WikiContentDTO;
import wiki.main.dto.WikiInfoDTO;
import wiki.main.service.Wikifier;

public class TestData {

	Wikifier wikifier = new Wikifier();

	String text1 = "�״� ������ �ϴ��� �췯��\r\n"
			+ "�� �� �β����� ���⸦,\r\n"
			+ "�ٻ��� �̴� �ٶ�����\r\n"
			+ "���� ���ο��ߴ�.\r\n"
			+ "���� �뷡�ϴ� ��������\r\n"
			+ "��� �׾� ���� ���� ����ؾ���\r\n"
			+ "�׸��� ������ �־��� ����\r\n"
			+ "�ɾ�߰ڴ�.\r\n"
			+ "\r\n"
			+ "���� �㿡�� ���� �ٶ��� ��ġ���.";
	
	WikiInfoDTO info1;
	List<WikiContentDTO> list1;
	
	public WikiInfoDTO getInfo1() {
		info1 = new WikiInfoDTO();
		info1.setSubject("����");
		info1.setRvs(1);
		return info1;
	}
	
	String text2 = "�״� ������ �ϴ��� �츣��\r\n"
			+ "�� �� �β����� ���⸦,\r\n"
			+ "�ٻ��� �̴� �ٶ�����\r\n"
			+ "���� ���ο��ߴ�.\r\n"
			+ "���� �뷡�ϴ� ��������\r\n"
			+ "��� �׾� ���� ���� ����ؾ���\r\n"
			+ "�׸��� ������ �־��� ����\r\n"
			+ "�ŷ����߰ڴ�.\r\n"
			+ "\r\n"
			+ "���� �㿡�� ���� �ٶ��� ��ġ���.\r\n"
			+ "\r\n"
			+ "1941.11.20.";
	
	WikiInfoDTO info2;
	List<WikiContentDTO> list2;
	
	public WikiInfoDTO getInfo2() {
		info2 = new WikiInfoDTO();
		info2.setSubject("����");
		info2.setRvs(2);
		return info2;
	}
}