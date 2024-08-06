# 🎸 취미 추천 SNS 커뮤니티 프로젝트 ⚽️

## ✔️ 목차
- [프로젝트 개요](#%EF%B8%8F-프로젝트-개요)
- [담당 구현 기능](#%EF%B8%8F-담당-구현-기능)
- [협업](#%EF%B8%8F-협업)
- [개발 문서](#%EF%B8%8F-개발-문서)
- [시연 영상](#%EF%B8%8F-시연-영상)

## ✔️ 프로젝트 개요
- `설명`: 설문조사를 통한 개인 맞춤 취미를 추천하고, 소모임 활동을 하는 SNS 기능을 가진다.
- `Git Organizations`: [HANDA](https://github.com/5-HANDA)
- `기간`: 2024.04.18 ~ 2024.07.03
- `구성`: BE 2명, FE 2명
- `핵심 기술 및 라이브러리`: <br>

<img src="https://img.shields.io/badge/Apache Kafka-231F20?style=for-the-badge&logo=Apache Kafka&logoColor=white"> <img src="https://img.shields.io/badge/WebFlux-6DB33F?style=for-the-badge&logo=spring&logoColor=white"> <img src="https://img.shields.io/badge/SSE-0078D4?style=for-the-badge&logo=sse&logoColor=white"> <img src="https://img.shields.io/badge/spring JPA-6DB33F?style=for-the-badge&logo=spring&logoColor=white"> <img src="https://img.shields.io/badge/spring boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"> <img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=OpenJDK&logoColor=white">

<img src="https://img.shields.io/badge/mongodb-47A248?style=for-the-badge&logo=MongoDB&logoColor=white"> <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"> <img src="https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=Redis&logoColor=white"> 

<img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white"> <img src="https://img.shields.io/badge/github-81717?style=for-the-badge&logo=github&logoColor=white"> <img src="https://img.shields.io/badge/swagger-85EA2D?style=for-the-badge&logo=Swagger&logoColor=white"> <img src="https://img.shields.io/badge/postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white"> 

- `화면 이미지`: 
<p float="left">
  <img src="https://github.com/5-HANDA/.github/assets/140376727/f5025bf6-128e-4964-bfb9-5bc219c7dcf1" width=220 height=440 />
  <img src="https://github.com/5-HANDA/.github/assets/140376727/5535d0f2-5f9e-4e6d-b17d-b8795c1f39ee" width=220 height=440 />
  <img src="https://github.com/5-HANDA/.github/assets/99894394/d20150f2-58b7-40e8-8339-65e3859bc147" width=220 height=440 />
</p>

- `주요 기능`: <br>

	- 일반/소셜 회원가입 및 로그인
	- 활동지역 등록 - 카카오 맵 API
	- 취미 설문 조사
	- 게시글 작성, 수정, 삭제
	- 게시글 좋아요, 댓글
    - 소모임 생성, 가입, 탈퇴
	- 소모임 알림
	- 소모임 단체 채팅
<br>

## ✔️ 담당 구현 기능

- `역할`: 채팅, 소모임, 알림, 활동 지역, 구글 회원가입 및 로그인, 홈
- `구현 파트 상세`
1. **MSA, Kafka** :<br>
   - MSA를 통한 서비스 분리와 Kafka를 통한 EDA
   - read-only data 생성을 통한 API 호출 수 축소
2. **채팅 서비스** :<br>
   - webflux를 통한 비동기 통신
   - mongoDB를 통한 비정형 데이터 저장
3. **소모임 서비스** :<br>
    - Kafka를 동한 소모임 관련 이벤트 발생 시 필요한 타 서비스와의 연동
    - 생성/가입/탈퇴 등 사용자 입장과 서비스 정책을 고려한 상세한 에러 처리
4. **알림 서비스** :<br>
   - SSE를 통한 실시간 알림
<br>

## ✔️ 협업
<table>
  <tr>
    <th>그라운드 Rule</th>
    <th>Git 코드리뷰, PR 템플릿, 브랜치 Rule</th>
  </tr>
  <tr>
    <td><img src="https://github.com/user-attachments/assets/da56a816-439f-4409-aac8-8af8f5fbb6b0" width="400" height="370"></td>
    <td><img src="https://github.com/user-attachments/assets/e22e6e7a-6cc4-4500-a47c-268c5f2836c4" width="400" height="370"></td>
  </tr>
  <tr>
    <th>스프린트</th>
    <th>KPT 회고</th>
  </tr>
  <tr>
    <td><img src="https://github.com/user-attachments/assets/5f929ec0-d87d-4ded-bef3-6e9ee88491a5" width="400" height="370"></td>
    <td><img src="https://github.com/user-attachments/assets/3b1bce5b-196a-4066-b071-5125a8bcec45" width="400" height="370"></td>
  </tr>
  <tr>
    <th>데일리 스크럼 & 회고</th>
    <th>트러블 슈팅</th>
  </tr>
  <tr>
    <td><img src="https://github.com/user-attachments/assets/99ff8479-8f3d-4ace-b8b9-de3950e29e27" width="400" height="370"></td>
    <td><img src="https://github.com/user-attachments/assets/d01c1da5-6a65-4d73-b2a7-6b447b6946fd" width="400" height="370"></td>
  </tr>
</table>
<br>


## ✔️ 개발 문서
🔎 자세히 보려면 사진 클릭 🔎
<details>
<summary>
  이벤트 스토밍
</summary>
   <img src="https://github.com/5-HANDA/.github/assets/99894394/aa46b2b0-48e6-4413-83cb-f17a9a061a0e" width="800">
</details>

<details>
<summary>
  와이어 프레임
</summary>
  <img src="https://github.com/user-attachments/assets/0e11a10d-1385-45e4-b16b-5b3fdcb36b9b" width="800"> 
</details>

<details>
<summary>
  ERD 설계서
</summary>
  <img src="https://github.com/2-Nocaffeine/Backend/assets/99894394/6722db0b-b2ad-4727-be17-93969adcd42e" width="800">
</details>

<details>
<summary>
  아키텍처 구조도
</summary>
  <img src="https://github.com/2-Nocaffeine/Backend/assets/99894394/d887eb25-a8bf-48bb-b5c7-10eb81390590" width="800">
</details>

[요구사항 정의서](https://ionized-eagle-cdd.notion.site/996ef5ce8cf34af19b3db85b297033c1?pvs=4)

[API 명세서](https://sugared-visitor-f84.notion.site/API-41985b3b6b3c4053bc693370a380bcdc?pvs=4)
<br>

## ✔️ 시연 영상
https://github.com/user-attachments/assets/ccda92ab-c993-41b6-8a79-8abef5c0923d


