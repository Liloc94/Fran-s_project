DROP TABLE IF EXISTS `user`;
DROP TABLE IF EXISTS `franchise`;
DROP TABLE IF EXISTS `board`;
DROP TABLE IF EXISTS `recommend`;
DROP TABLE IF EXISTS `report`;
DROP TABLE IF EXISTS `favorite`;

CREATE TABLE `user` (
    user_idx INT NOT NULL AUTO_INCREMENT COMMENT 'Auto Increment',
    user_id VARCHAR(50) UNIQUE COMMENT 'unique',
    user_nickname	varchar(12)	null unique,
    user_pwd VARCHAR(255),
    user_name VARCHAR(30),
    user_email VARCHAR(40) NOT NULL COMMENT 'unique',
    regdate DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    user_role TINYINT NOT NULL DEFAULT 0 COMMENT '0: 유저, 1: 관리자',
    user_del TINYINT NOT NULL DEFAULT 0 COMMENT '-1 : 탈퇴, 0 : 활동',
    provider VARCHAR(10) default "일반" comment '소셜 회원가입 시 회원가입한 소셜',
    provider_id VARCHAR(100) unique comment '소셜 회원가입시 소셜에서 넘겨준는 유저의 고유ID',
    PRIMARY KEY (`user_idx`)
);



CREATE TABLE `franchise` (
   `franchise_idx`   int   NOT NULL AUTO_INCREMENT  COMMENT 'autoincress',
   `franchise_title`   varchar(100)   NOT NULL,
   `franchise_sectors`   tinyint   NOT NULL   COMMENT '업종 0: 카페디저트 1: 음식점주점 2:치킨피자 3: 분식패스트푸드 4:판매업',
   `franchise_img`   varchar(255)   NOT NULL   COMMENT '이미지 경로 삽입',
   `franchise_stores`   int   NOT NULL   DEFAULT 0   COMMENT '프렌차이즈별 가지고 있는 매장수',
   `franchise_avg_sales`   int   NOT NULL   COMMENT '프렌차이즈별 월 평균 매출액',
   `franchise_startup_cost`   int   NOT NULL   COMMENT '프렌차이즈 별 창업비용',
   PRIMARY KEY (`franchise_idx`)
);



CREATE TABLE `board` (
    `board_idx` INT NOT NULL AUTO_INCREMENT COMMENT 'Auto Increment',
    `user_idx` INT NOT NULL COMMENT 'Auto Increment',
    `board_sectors` TINYINT NOT NULL COMMENT '업종 0: 카페디저트 1: 음식점주점 2: 치킨피자 3: 분식패스트푸드 4: 판매업',
    `board_big_area` VARCHAR(20) NOT NULL COMMENT '시, 도',
    `board_small_area` VARCHAR(20) NOT NULL COMMENT '시, 군, 구',
    `board_title` VARCHAR(255) NOT NULL DEFAULT 'comment' COMMENT '댓글일 경우 제목이 빈칸으로 넘어오기 때문에 디폴트 값인 comment가 들어갈 수 있게 하기 위해',
    `board_category` TINYINT NOT NULL COMMENT '0: 상권 분석 게시글, 1: 창업 후기 게시글',
    `board_content` TEXT NOT NULL,
    `board_view` INT NOT NULL DEFAULT 0,
    `board_write_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `board_del` TINYINT NOT NULL DEFAULT 0 COMMENT '0: 삭제 안함, -1: 삭제 완료',
    `step` INT NULL DEFAULT 0 COMMENT '댓글의 순번',
    `depth` TINYINT NULL DEFAULT 0 COMMENT '0이면 게시글, 1이면 댓글, 2면 대댓글 3이면 대대댓글...',
    `ref` INT NULL DEFAULT NULL COMMENT '댓글의 부모가 되는 게시글의 번호',
    PRIMARY KEY (`board_idx`),
    FOREIGN KEY (`user_idx`) REFERENCES `user` (`user_idx`)
);

CREATE TABLE `recommend` (
    `like_idx` INT NOT NULL AUTO_INCREMENT COMMENT 'Auto Increment',
    `user_idx` INT NOT NULL COMMENT 'Auto Increment',
    `board_idx` INT NOT NULL COMMENT 'Auto Increment',
    `recommend_yn` TINYINT NOT NULL COMMENT '0: 좋아요, 1: 싫어요',
    `recommend_del_onoff` TINYINT NOT NULL DEFAULT 0 COMMENT '0: 선택 된 상태, -1: 취소한 상태',
    PRIMARY KEY (`like_idx`),
    FOREIGN KEY (`user_idx`) REFERENCES `user` (`user_idx`),
    FOREIGN KEY (`board_idx`) REFERENCES `board` (`board_idx`)
);



CREATE TABLE `report` (
    `report_idx` INT NOT NULL AUTO_INCREMENT COMMENT 'Auto Increment',
    `user_idx` INT NOT NULL COMMENT '신고한 사람',
    `board_idx` INT NOT NULL COMMENT '신고당한 게시글',
    `report_reason` VARCHAR(100) NOT NULL,
    PRIMARY KEY (`report_idx`),
    FOREIGN KEY (`user_idx`) REFERENCES `user` (`user_idx`),
    FOREIGN KEY (`board_idx`) REFERENCES `board` (`board_idx`)
);



CREATE TABLE `favorite` (
    `favorite_idx` INT NOT NULL AUTO_INCREMENT COMMENT 'Auto Increment',
    `user_idx` INT NOT NULL COMMENT 'Auto Increment',
    `favorite_sectors` TINYINT NULL COMMENT '업종 0: 카페디저트 1: 음식점주점 2: 치킨피자 3: 분식패스트푸드 4: 판매업',
    `favorite_big_area` VARCHAR(20) NULL COMMENT '시, 도',
    `favorite_small_area` VARCHAR(20) NULL COMMENT '시, 군, 구',
    PRIMARY KEY (`favorite_idx`),
    FOREIGN KEY (`user_idx`) REFERENCES `user` (`user_idx`)
);


INSERT INTO `user` (user_id, user_nickname, user_pwd, user_name, user_email, regdate, user_role, user_del)
			VALUES ("ddd", "ddd", "1234", "정상필", "mod459@naver.com", NOW(), 1, 0);
INSERT INTO board (user_idx, board_sectors, board_big_area, board_small_area, board_title, board_category, board_content, board_write_date)
VALUES (1,1, '서울', '강남구', '첫 번째 게시글', 0, '첫 번째 게시글 내용입니다.', CURRENT_TIMESTAMP);


SELECT
			B.BOARD_TITLE,
			B.BOARD_SECTORS,
			B.BOARD_BIG_AREA,
			B.BOARD_SMALL_AREA,
			B.USER_IDX,
			B.BOARD_WRITE_DATE,
			B.BOARD_CONTENT
		FROM BOARD B
		JOIN `USER` U ON B.USER_IDX = U.user_idx
		WHERE B.BOARD_IDX = 1