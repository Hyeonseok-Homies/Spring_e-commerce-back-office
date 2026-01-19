### 고객 및 상품 관리 시스템 팀 프로젝트
#### 이커머스 백오피스 DB 관리 시스템
---
## ■ 프로젝트 소개
#### 🔹 기간 : 26.01.14 ~ 26.01.20
#### 🔹 목적 : 
- 이커머스 서비스의 핵심을 관리하는 백오피스(관리자 페이지) 를 사용하여 데이터를 다룰 수 있다.
- 데이터가 많아질 때를 대비한 검색, 정렬, 페이징 기능까지 구축할 수 있다.
- 관리자가 고객, 상품, 주문 데이터를 쉽고 빠르고 정확하게 관리할 수 있는 기본 시스템을 구축하는 것이다.
  ##### ※ 이커머스 서비스의 핵심이란?  고객, 상품, 주문 데이터를 효율적으로 관리
#### 🔹 팀 소개 : 현석이와 아이들
- 이현석 : Leader, Admin & Oder manager
- 유지현 : Admin & Oder manager
- 전민우 : Admin & review manager
- 윤민기 : Product & Error manager
- 서하나 : CS manager
  
---
## ■ 프로젝트 구성
<details id="1-signup">
<summary><b> 🔹 API </b></summary>
<br>

<details id="1-signup">
<summary><b> 1. 관리자 API </b></summary>
<br>
  
|분류 | Method | API URL | Request | Response | Status|
| :--- | :--- | :--- | :--- | :--- |:--- |
|관리자 회원 가입 | `POST` | `/api/admins/signup` | { <br>  &nbsp; "name": "김아무개", <br>  &nbsp; "email": "advsad@naver.com", <br>  &nbsp;  "password" : "asdlkfj;lasdfjl;adskjr2134213", <br>  &nbsp; 	"phonenumber":"010-8455-9895", <br>	 &nbsp; "role": "운영관리자"  &nbsp;  <br>}|{<br> &nbsp; "id" : 1,<br>  &nbsp; "name" : "김아무개", <br>  &nbsp; 	"email" : "advsad@naver.com",<br>  &nbsp; 	"phonenumber" : "010-8455-9895",<br>  &nbsp; "role" : "운영관리자",<br>  &nbsp; "status " : "승인대기" &nbsp; <br>}| `201 Created`|
|관리자 로그인 | `POST` | `/api/admins/login` |  {<br>  &nbsp; "email": "advsad@naver.com"<br>  &nbsp; "password" : "asdlkfj;lasdfjl;adskjr2134213"<br>} |&nbsp;&nbsp;[활성 상태일때]<br>{<br>	  &nbsp; "name" : "김아무개",<br>  &nbsp; "status" : "활성"<br>} <br>&nbsp;&nbsp;[그외 비활성화 상태일때] <br>{<br>	 &nbsp; "name" : "김아무개",<br> &nbsp; "status" : "승인대기"<br>}<br>{<br>	 &nbsp; "name" : "김아무개",<br> &nbsp; "status" : "거부"<br>}<br>{<br>	 &nbsp; "name" : "김아무개",<br> &nbsp; "status" : "정지"<br>}<br>{<br>	 &nbsp; "name" : "김아무개",<br> &nbsp; "status" : "비활성"<br>}|`200 OK`|
|관리자 전체 조회 |  `GET` | `/api/admins` | |[<br>&nbsp; {<br> &nbsp; "id" : 1,<br> &nbsp; "name" : "김아무개",<br> &nbsp; "email" : "advsad@naver.com",<br> &nbsp; "phonenumber" : "010-8455-9895",<br> &nbsp; "role" : "운영관리자",<br> &nbsp; "status " : "승인대기"<br>&nbsp; }<br>]|`200 OK`|
|관리자 상세 조회 | `GET` | `/api/admins/{id}` ||{<br> &nbsp;"id" : 1,<br> &nbsp;	"name" : "김아무개",<br> &nbsp;	"email" : "advsad@naver.com",<br> &nbsp;	"phonenumber" : "010-8455-9895",<br> &nbsp;	"role" : "운영관리자",<br> &nbsp;"status " : "승인대기"<br>}|`200 OK`|
|관리자 정보 수정 | `PUT` | `/api/admins/{id}` |{<br> &nbsp; "name": "김아무개수정하겠습니다.",<br> &nbsp;	"email": "advsad@naver.com",<br> &nbsp;	"phonenumber":"010-8455-9895",<br> &nbsp;	"role": "운영관리자",<br> &nbsp; "status" : "활성"<br> }|{<br> &nbsp;"id" : 1,<br> &nbsp;"name": "김아무개수정하겠습니다.",<br> &nbsp;"email": "advsad@naver.com",	<br> &nbsp;"phonenumber":"010-8455-9895",<br> &nbsp;"role": "운영관리자",<br> &nbsp;"status" : "활성"<br> }|`200 OK`<br> `400 Bad Request`|
|관리자 삭제 | `DELETE` | `/api/admins/{id}` ||{<br> &nbsp;	"id" : 1<br>}|`204 No Content`<br> `400 Bad Request`|
|관리자 로그아웃 | `PUT` | `/api/admins/logout` |||`204 No Content`<br> `400 Bad Request`|
|관리자 승인/거부 | `PUT` | `/api/admins/status="승인대기"` |{<br> &nbsp;"id" : 1,<br> &nbsp;"name" : "김아무개",<br> &nbsp;"email" : "advsad@naver.com",<br> &nbsp;"phonenumber" : "010-8455-9895",<br> &nbsp;"role" : "운영관리자",<br> &nbsp;"status " : "활성"<br>}|	{<br> &nbsp;"id" : 1,<br> &nbsp;"name" : "김아무개",<br> &nbsp;"email" : "advsad@naver.com",<br> &nbsp;"phonenumber" : "010-8455-9895",<br> &nbsp;"role" : "운영관리자",<br> &nbsp;"status " : "활성"<br>	}|`200 OK`<br> `400 Bad Request`|
|관리자 내 정보 조회 | `GET` | `/api/admins/{id}` ||{<br> &nbsp;"id" : 1,	<br> &nbsp;"name" : "김아무개",	<br> &nbsp;"email" : "advsad@naver.com",<br> &nbsp;"phonenumber" : "010-8455-9895",<br> &nbsp;"role" : "운영관리자",<br> &nbsp;"status " : "승인대기"<br>}||
|관리자 내 정보 수정 | `PUT` | `/api/admins/{id}` |{<br> &nbsp; "name": "김아무개수정하겠습니다.",<br> &nbsp;"email": "advsad@naver.com",<br> &nbsp;"phonenumber":"010-8455-9895",<br> &nbsp;"role": "운영관리자",<br> &nbsp;"status" : "활성"<br>}|{<br> &nbsp;"id" : 1,<br> &nbsp;"name": "김아무개수정하겠습니다.",<br> &nbsp;"email": "advsad@naver.com",<br> &nbsp;"phonenumber":"010-8455-9895",<br> &nbsp;"role": "운영관리자",<br> &nbsp;"status" : "활성"<br>}|`200 OK`<br> `400 Bad Request`|

</details>
<details id="2-signup">
<summary><b> 2. 고객 API </b></summary>
<br>

|분류 | Method | API URL | Request | Response | Status|
| :--- | :--- | :--- | :--- | :--- |:--- |
|고객 전체 조회 | `GET` | `/api/customers` ||[<br> &nbsp;{<br> &nbsp;"id" : 1,<br> &nbsp;"name" : "김아무개",<br> &nbsp;"email" : "advsad@naver.com",<br> &nbsp;"phonenumber" : "010-8455-9895",<br> &nbsp;"status " : "활성",<br> &nbsp;"createdat " : " 20260113"<br> &nbsp; }<br> &nbsp;]|`200 OK`<br> `400 Bad Request`|
|고객 상세 조회 | `GET` | `/api/admins/login` ||[<br> &nbsp;{<br> &nbsp;"name" : "김아무개",<br> &nbsp;"email" : "advsad@naver.com",<br> &nbsp;"phonenumber" : "010-8455-9895",<br> &nbsp;"status " : "활성",<br> &nbsp;"createdat " : " 20260113"<br> &nbsp;}<br> &nbsp; &nbsp;]|`200 OK`<br> `400 Bad Request`<br>|
|고객 정보 수정 |  `PUT` | `/api/admins` |{<br> &nbsp;"name": "김아무개수정하겠습니다.",<br> &nbsp;"email": "advsad@naver.com",<br> &nbsp;"phonenumber":"010-8455-9895",<br> &nbsp;"status" : "활성"<br>}|{<br> &nbsp;"id" : 1,<br> &nbsp;"name": "김아무개수정하겠습니다.",<br> &nbsp;"email": "advsad@naver.com",<br> &nbsp;"phonenumber":"010-8455-9895",<br> &nbsp;"status" : "활성"<br>}|`200 OK`<br>`400 Bad Request`|
|고객 삭제 | `DELTE` | `/api/admins/{id}` |||`204 No Content`<br> `400 Bad Request`|
|고객 상태 변경 | `PATCH` | `/api/admins/{id}` | {<br> &nbsp;"status": "활성"<br>} |{<br> &nbsp;"id" : 1,<br> &nbsp;"status": "활성"<br>}|`200 OK`|
</details>

<details id="3-signup">
<summary><b> 3. 상품 API </b></summary>
<br>
  
|분류 | Method | API URL | Request | Response | Status|
| :--- | :--- | :--- | :--- | :--- |:--- |
|상품 등록 | `POST` | `/api/products` |{<br> &nbsp;"name" : "휴대폰",	<br> &nbsp;"category" :"전자제품",<br> &nbsp;"price" : 1000000,	<br> &nbsp;"stock" : 20,<br> &nbsp;"status" : "판매중",<br> &nbsp;"createdByAdminId" : 1<br>} |{<br> &nbsp;"id" : 1,<br> &nbsp;"name" : "휴대폰",<br> &nbsp;"category" :"전자제품",<br> &nbsp;"price" : 1000000,<br> &nbsp;"stock" : 20,<br> &nbsp;"status" : "판매중",<br> &nbsp;"createdAt" : 20260113,<br> &nbsp;"updatedAt" : 20260113,<br> &nbsp;"createdByAdminId" : 1<br> &nbsp;}|`201 Created`|
|상품 전체 조회 | `POST` | `/api/products` ||[<br> &nbsp;{<br> &nbsp;"id" : 1,<br> &nbsp;"name" : "휴대폰",<br> &nbsp;"category" :"전자제품",<br> &nbsp;"price" : 1000000,<br> &nbsp;"stock" : 20,<br> &nbsp;"status" : "판매중",<br> &nbsp;"createdAt" : 20260113,<br> &nbsp;"updatedAt" : 20260113,<br> &nbsp;"createdByAdminId" : 1<br> &nbsp;}<br> ]|`200 OK`|
|상품 단건 조회 |  `GET` | `/api/admins` ||{<br> &nbsp;"id" : 1,<br> &nbsp;"name" : "휴대폰",<br> &nbsp;"category" :"전자제품",<br> &nbsp;"price" : 1000000,<br> &nbsp;"stock" : 20,<br> &nbsp;"status" : "판매중",<br> &nbsp;"createdAt" : 20260113,<br> &nbsp;"updatedAt" : 20260113,<br> &nbsp;"createdByAdminId" : 1<br>}|`200 OK`|
|상품 수정 | `GET` | `/api/admins/{id}` |{<br> &nbsp;"name" : "휴대폰",<br> &nbsp;"category" :"전자제품",<br> &nbsp;"price" : 1000000<br> }|{<br> &nbsp;"id" : 1,<br> &nbsp;"name" : "휴대폰",<br> &nbsp;"category" :"전자제품",<br> &nbsp;"price" : 1000000<br>}|`200 OK`|
|관리자 삭제 | `DELETE` | `/api/admins/{id}` ||{<br> &nbsp;"id" : 1<br>}|`204 No Content`|
</details>

<details id="4-signup">
<summary><b> 4. 주문 API </b></summary>
<br>
  
|분류 | Method | API URL | Request | Response |Status|
| :--- | :--- | :--- | :--- | :--- |:--- |
|주문 생성 | `POST` | `/api/orders` |{<br> &nbsp;"createdByCustomerId": 10,<br> &nbsp;"createdByProductId": 5,<br> &nbsp;"quantity": 3<br>}|{<br> &nbsp;"id": 101,<br> &nbsp;"orderNo": "20260115-001",<br> &nbsp;"createdByCustomerName": "홍길동",<br> &nbsp;"createdByProductName": "샴푸",<br> &nbsp;"quantity": 3,<br> &nbsp;"unitPrice": 12000,<br> &nbsp;"totalPrice": 36000,<br> &nbsp;"orderedAt": "2026-01-15",<br> &nbsp;"status": "준비중",<br> &nbsp;"createdByAdminName": "김운영"<br>}|`201 Created`| 
|주문 전체 조회 |  `GET` | `/api/orders` ||[<br> &nbsp;{<br> &nbsp;"id": 101,<br> &nbsp;"orderNo": "20260115-001",<br> &nbsp;"createdByCustomerName": "홍길동",<br> &nbsp;"createdByProductName": "샴푸",<br> &nbsp;"quantity": 3,<br> &nbsp;"totalPrice": 36000,<br> &nbsp;"orderedAt": "2026-01-15",<br> &nbsp;"status": "준비중",<br> &nbsp;"createdByAdminName": "김운영"<br> &nbsp;}<br>],<br> &nbsp;"page": {<br> &nbsp;"currentPage": 1,<br> &nbsp;"size": 10,<br> &nbsp;"totalItems": 100,<br> &nbsp;"totalPages": 10<br> &nbsp;}<br>}|`200 OK`|
|주문 상세 조회 | `GET` | `/api/admins/{id}` ||{<br> &nbsp;"id": 101,<br> &nbsp;"orderNo": "20260115-001",<br> &nbsp;"createdByCustomerName": "홍길동",<br> &nbsp;"createdByCustomerEmail": "홍길동",<br> &nbsp;"createdByProductId": "샴푸",<br> &nbsp;"quantity": 3,<br> &nbsp;"totalPrice": 36000,<br> &nbsp;"orderedAt": "2026-01-15",<br> &nbsp;"status": "준비중",<br> &nbsp;"createdByAdminName": "김운영",<br> &nbsp;"createdByAdminEmail": "kim@test.com",<br> &nbsp;"createdByAdminRole": "운영관리자"<br>}|`200 OK`|
|주문 상태 수정 | `PUT` | `/api/admins/{id}` |{<br> &nbsp;"status": "배송중"<br>}|{<br> &nbsp;"id": 101,<br> &nbsp;"orderNo": "20260115-001",<br> &nbsp;"status": "배송중",<br>}|`200 OK`<br>`400 Bad Request`|
|주문 취소| `DELETE` | `/api/admins/{id}` |{<br> &nbsp;"reason": "고객 요청으로 인한 취소"<br>}|{<br> &nbsp;"id": 101,<br> &nbsp;"orderNo": "20260115-001",<br> &nbsp;"status": "취소됨",<br> &nbsp;"reason": "고객 요청으로 인한 취소"<br>}|`200 OK`|
</details>

</details>

<details id="1-signup">
<summary><b>🔹 ERD </b></summary>
<br>


</details>

<details id="1-signup">
<summary><b>🔹 SQL </b></summary>
<br>
  
```
create table admins
(
    adminId      bigint       not null
        primary key,
    Name         varchar(50)  not null,
    Email        varchar(50)  not null,
    Password     varchar(255) not null,
    Phone_Number varchar(13)  not null,
    Role         varchar(30)  not null,
    Status       varchar(30)  not null,
    Created_At   datetime     not null,
    Updated_At   datetime     not null,
    Approved_At  datetime     not null
);  
```

```
create table customers
(
    Id           bigint       not null
        primary key,
    Name         varchar(50)  not null,
    Email        varchar(50)  not null,
    Password     varchar(255) not null,
    Phone_Number varchar(13)  not null,
    Created_At   datetime     not null,
    Updated_At   datetime     not null,
    Status       varchar(30)  not null
);
```
  
```
  create table products
(
    Id                  bigint      not null
        primary key,
    Name                varchar(50) not null,
    Category            varchar(30) not null,
    Price               bigint      not null,
    Stock               bigint      not null,
    Status              varchar(30) not null,
    Created_At          datetime    not null,
    Updated_At          datetime    not null,
    Created_By_Admin_Id bigint      null,
    constraint fk_product_admin
        foreign key (Created_By_Admin_Id) references admins (adminId)
);
```

```
create table orders
(
    Id                     bigint      not null
        primary key,
    Order_No               varchar(30) not null,
    Created_By_Customer_Id bigint      not null,
    Created_By_Product_Id  bigint      not null,
    Quantity               bigint      not null,
    Total_Price            bigint      not null,
    Status                 varchar(20) not null,
    Created_By_Admin_Id    bigint      not null,
    Created_At             datetime    not null,
    Updated_At             datetime    not null,
    constraint fk_order_admin
        foreign key (Created_By_Admin_Id) references admins (adminId),
    constraint fk_order_customer
        foreign key (Created_By_Customer_Id) references customers (Id),
    constraint fk_order_product
        foreign key (Created_By_Product_Id) references products (Id)
);
```

```
create table review

```

</details>

<details id="1-signup">
<summary><b>🔹 메인 프로젝트 구조  </b></summary>
<br>
  
      📁 src/  
        └── 📁main/
            └── 📁java/com/backoffice   
                └── 📁admin/              #관리자 계정 관리
                    └── 📁confige/        #전체 예외 처리 및 비밀번호 보안    
                    └── 📁controller/     
                    └── 📁dto/                 
                    └── 📁entity/            
                    └── 📁repository/          
                    └── 📁service/          
                └── 📁common/             #BaseEntity 관리(중복 처리)
                └── 📁customer/           #CS 고객 관리
                    └── 📁controller/    
                    └── 📁dto/           
                    └── 📁entity/        
                    └── 📁repository/       
                    └── 📁service/       
                └── 📁exception/         #Error 핸들링  
                └── 📁order/             #order 주문 관리
                    └── 📁controller/    
                    └── 📁dto/           
                    └── 📁entity/        
                    └── 📁repository/       
                    └── 📁service/  
                └── 📁product/           #product 상품 관리
                    └── 📁controller/    
                    └── 📁dto/           
                    └── 📁entity/        
                    └── 📁repository/       
                    └── 📁service/  
                └── 📁review/           #review 고객 리뷰 관리
                    └── 📁controller/    
                    └── 📁dto/           
                    └── 📁entity/        
                    └── 📁repository/       
                    └── 📁service/                      
                    
  </details>

#### 🔹 개발 환경 :
- Version : Java 17
- IDE : IntelliJ
- Framework : SpringBoot 2.7.11
- ORM : JPA
#### 🔹 기술 스택 :
- JAVA
- MySQL
- Spring Boot
- GIT
- GitHUB
- GRADLE

---
## ■ Demo Video 링크

발표제작 참고 출처
