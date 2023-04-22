# Wikifier
node를 응용하여 차분 백업을 수행하는 위키 라이브러리입니다.
   
필요하신 분은 jar 파일만 내려 받아도 사용하실 수 있습니다.   
상세한 사용 방법은 원본 소스 코드와 JavaDoc 주석 및 테스트 코드(예시)를 통해 확인하실 수 있습니다.   
test 패키지를 통해서 사용 예제를 확인해보세요.   
https://github.com/psam1017/Wikifier/tree/main/src/wiki/test/example

<hr>

### 위키 작동 원리(차분 백업)

![wiki_draft](https://user-images.githubusercontent.com/114392605/233770441-f11f7b8c-a7a4-48f2-bb91-828d55c7b4d0.jpg)

최초로 wiki를 만들고자 계획했을 당시의 draft입니다. 일단 위키 사이트라면 누구나 수정하고, 그 내용이 저장되어야 하는데, 그 때마다 중복되는 내용도 모두 저장할 수는 없으니 변경이 된 부분만 수정해야겠다! 하고 생각한 게 돌이켜 보니 차분 백업의 개념이었습니다.

그러면서 가장 많이 참고한 게 깃허브의 형상 통제였습니다. 깃허브에서도 코드 한 줄씩을 비교하여 차분을 백업하고, 이를 웹 사이트를 통해서 통제할 수 있는 기능을 제공하고 있습니다. 직접 이를 사용해보면서 제가 구현하고 싶었던 게 이런 것이라고 생각했습니다.

본격적으로 구현을 하려고 하니 기존의 한 줄을 수정할 수도 있고, 새로운 줄을 추가할 수도 있고, 삭제할 수도 있고, 위치를 변경할 수도 있는데 ... 통제해야 할 case가 생각보다 많았습니다. 머리로는 차마 다 생각할 수 없어서 직접 손으로 하나 하나 그리면서 개념을 정리했던 위 사진인 최초의 메모이자 구상안이었습니다.

그렇게 case를 정리하다 보니 내린 결론은, 수정한 내용을 이전 내용들과 비교해서 새로 추가된 SCI(형상항목)는 그 내용을 저장하고, 기존에 있던 SCI는 그 위치를 기억하자, 였습니다.

![wiki_concept](https://user-images.githubusercontent.com/114392605/233772034-8546e585-16d2-4461-98c8-13449cd554e1.png)

위의 그림은 test 패키지에서 확인할 수 있는 example을 시각화한 자료입니다. "서시"라는 주제에 대하여 각 항목은 rvs, rvsRow, preRvs, preRvsRow를 가지고 있습니다.

rvs는 현재 버전 인덱스,   
rvsRow는 rvs에 대하여 몇 번째 항목인지를 나타내는 인덱스(간단하게 말하자면 몇 번째 줄인지),   
preRvs는 내용이 같은 경우 몇 번째 버전과 같은지를 나타내는 인덱스,   
preRvsRow는 preRvs에 대하여 몇 번째 항목인지를 나타내는 인덱스입니다.

예를 들어, 서시의 2번째 버전의 첫 번째 줄은 1번째 버전의 어떠한 줄과도 일치하지 않습니다. 따라서 이를 그대로 저장하고 있습니다. 또한, 이 경우 preRvs와 preRvsRow는 각각 rvs, rvsRow와 동일합니다. 이는 새로 추가된 내용임을 의미합니다.

하지만, 두 번째 줄은 1번째 버전의 1번째 줄과 내용이 일치합니다. 따라서 모든 문자열을 저장하지 않고, 오직 preRvs와 preRvsRow만을 저장하는 것입니다.   
즉, (2-2)는 (1-2)과 같다고 할 수 있습니다. 이러한 내용을 각각 rvs, rvsRow, preRvs, preRvsRow에 저장하는 것입니다.

![wiki_db](https://user-images.githubusercontent.com/114392605/233770448-729b8c2c-8e44-4887-bc93-07526509ca9a.png)

실제로 이 위키 라이브러리를 적용하여 서비스하고 있는(~2023.11.) 꼼수닷컴의 DB에 저장된 내용입니다.

보시는 것과 같이 subject, rvs, rvsRow, preRvs, preRvsRow, content를 모두 저장하고 있습니다. 각각의 번호는 COMPARATOR로 탐색할 때의 비교 기준이 되기 때문에 적절하게 번호를 부여하고 이를 관리하는 것이 중요합니다.

<hr>

### 유의사항

(1) wikifier는 위에서 언급한 식별번호를 포함하는 DTO를 필요로 하고 있습니다. 이를 WikiContentDTO라고 부르고 있으며 이상의 차분 백업 기능만을 수행하고자 한다면 이것만으로 충분합니다.

하지만 이 위키를 누가 수정했는지, 언제 업데이트되었는지, 삭제되지는 않았는지를 식별하기 위해서는 추가적인 정보가 필요할 것입니다. 이러한 정보를 제어할 수 있도록 wikifier는 WikiInfoDTO를 추가로 제공하고 있습니다. 단, 제공되는 WikiInfoDTO는 subject와 rvs만을 가지고 있기 때문에 이를 상속 받아 추가로 필요한 속성을 명시할 필요가 있습니다.   

```
CREATE TABLE wikiInfo
(
    subject    VARCHAR(100)   NOT NULL,
    rvs        INT UNSIGNED   NOT NULL,
    nickname   VARCHAR(10)    NOT NULL DEFAULT 'noname',
    ip         VARCHAR(128)   NOT NULL DEFAULT '',
    revisedAt  DATETIME       NOT NULL DEFAULT NOW(),
    deletedAt  DATETIME       NULL,
    CONSTRAINT PRIMARY KEY(subject, rvs)
);

CREATE TABLE wikiContents
(
    subject      VARCHAR(100)    NOT NULL,
    rvs          INT UNSIGNED    NOT NULL,
    rvsIndex     INT UNSIGNED	   NOT NULL,
    preRvs       INT UNSIGNED	   NOT NULL,
    preRvsIndex  INT UNSIGNED	   NOT NULL,
    content      VARCHAR(10000)  NULL,
    CONSTRAINT PRIMARY KEY(subject, rvs, rvsIndex),
    CONSTRAINT FOREIGN KEY(subject, rvs)
        REFERENCES wikiInfo(subject, rvs)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT FOREIGN KEY(subject, preRvs, preRvsIndex)
        REFERENCES wikiContents(subject, rvs, rvsIndex)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);
```

이 경우 참조 무결성을 지키기 위해 하나의 메소드, 하나의 트랜잭션 안에서 info를 먼저 저장하고, 해당 info에 대하여 Wikifier의 메소드를 실행하기를 권장합니다. 다수의 사용자가 수정할 때 병행제어를 하지 않아 inconsistency가 발생할 수도 있기 때문입니다.

(2) wikifier는 기본적으로 줄 바꿈을 기준으로 텍스트를 분리합니다. 하지만 생성자를 통해 구분자(seperator)를 변경할 수 있습니다. 자세한 내용은 소스코드를 확인하십시오.

(3) wikifier는 연산 기능까지만 제공하고 있습니다. List를 DB에 저장하기 위해서는 '동적 쿼리'처럼 쿼리 내에서 연산을 수행하시기를 권장합니다. 아래는 mybatis를 이용하여 생성하는 동적 쿼리문입니다.

```
INSERT INTO wikiContents
VALUES 
<foreach item="contents" collection="list" separator=",">
    (#{contents.subject}, #{contents.rvs}, #{contents.rvsIndex}, #{contents.preRvs}, #{contents.preRvsIndex}, #{contents.content})
</foreach>
```
