-- Category
INSERT INTO CATEGORY (category_id, parent_category_id, category_name, depth, is_leaf, reg_dt, udt_dt)
VALUES
    (1, NULL, '얼굴', 1, FALSE, NOW(), NOW()),
    (2, NULL, '바디', 1, FALSE, NOW(), NOW()),
    (3, NULL, '헤어', 1, FALSE, NOW(), NOW()),
    (4, 1, '스킨/케어', 2, FALSE, NOW(), NOW()),
    (5, 1, '입술', 2, FALSE, NOW(), NOW()),
    (6, 4, '스킨/로션', 3, TRUE, NOW(), NOW()),
    (7, 4, '토너', 3, TRUE, NOW(), NOW()),
    (8, 4, '앰플', 3, TRUE, NOW(), NOW()),
    (9, 5, '립밤', 3, TRUE, NOW(), NOW()),
    (10, 5, '립 스크럽', 3, TRUE, NOW(), NOW()),
    (11, 2, '몸', 2, FALSE, NOW(), NOW()),
    (12, 2, '손/발', 2, FALSE, NOW(), NOW()),
    (13, 11, '바디 로션', 3, TRUE, NOW(), NOW()),
    (14, 11, '바디 크림', 3, TRUE, NOW(), NOW()),
    (15, 11, '샤워 젤', 3, TRUE, NOW(), NOW()),
    (16, 12, '핸드 크림', 3, TRUE, NOW(), NOW()),
    (17, 12, '풋 크림', 3, TRUE, NOW(), NOW()),
    (18, 3, '두피', 2, FALSE, NOW(), NOW()),
    (19, 3, '모발', 2, FALSE, NOW(), NOW()),
    (20, 3, '슬림/이너뷰티', 2, FALSE, NOW(), NOW()),
    (21, 18, '쿨링 스프레이', 3, TRUE, NOW(), NOW()),
    (22, 18, '세럼', 3, TRUE, NOW(), NOW()),
    (23, 18, '------', 3, TRUE, NOW(), NOW()),
    (24, 19, '에센스', 3, TRUE, NOW(), NOW()),
    (25, 19, '왁스', 3, TRUE, NOW(), NOW()),
    (26, 19, '오일', 3, TRUE, NOW(), NOW());

-- Exhibit category
INSERT INTO EXHIBIT_CATEGORY (exhibit_category_id, parent_exhibit_category_id, exhibit_category_name, depth, is_leaf, reg_dt, udt_dt)
VALUES
    (1, NULL, '얼굴', 1, FALSE, NOW(), NOW()),
    (2, NULL, '바디', 1, FALSE, NOW(), NOW()),
    (3, NULL, '헤어', 1, FALSE, NOW(), NOW()),
    (4, 1, '스킨/케어', 2, FALSE, NOW(), NOW()),
    (5, 1, '입술', 2, FALSE, NOW(), NOW()),
    (6, 4, '스킨/로션', 3, TRUE, NOW(), NOW()),
    (7, 4, '토너', 3, TRUE, NOW(), NOW()),
    (8, 4, '앰플', 3, TRUE, NOW(), NOW()),
    (9, 5, '립밤', 3, TRUE, NOW(), NOW()),
    (10, 5, '립 스크럽', 3, TRUE, NOW(), NOW()),
    (11, 2, '몸', 2, FALSE, NOW(), NOW()),
    (12, 2, '손/발', 2, FALSE, NOW(), NOW()),
    (13, 11, '바디 로션', 3, TRUE, NOW(), NOW()),
    (14, 11, '바디 크림', 3, TRUE, NOW(), NOW()),
    (15, 11, '샤워 젤', 3, TRUE, NOW(), NOW()),
    (16, 12, '핸드 크림', 3, TRUE, NOW(), NOW()),
    (17, 12, '풋 크림', 3, TRUE, NOW(), NOW()),
    (18, 3, '두피', 2, FALSE, NOW(), NOW()),
    (19, 3, '모발', 2, FALSE, NOW(), NOW()),
    (20, 3, '슬림/이너뷰티', 2, FALSE, NOW(), NOW()),
    (21, 18, '쿨링 스프레이', 3, TRUE, NOW(), NOW()),
    (22, 18, '세럼', 3, TRUE, NOW(), NOW()),
    (23, 18, '------', 3, TRUE, NOW(), NOW()),
    (24, 19, '에센스', 3, TRUE, NOW(), NOW()),
    (25, 19, '왁스', 3, TRUE, NOW(), NOW()),
    (26, 19, '오일', 3, TRUE, NOW(), NOW());

-- QA Category
INSERT INTO QA_CATEGORY (qa_category_id, parent_qa_category_id, qa_category_name, is_leaf, reg_dt)
VALUES
    (1, NULL, '쇼핑 문의', FALSE, NOW()),
    (2, NULL, '이벤트 문의', FALSE, NOW()),
    (3, NULL, '시스템 문의', FALSE, NOW()),
    (4, 1, '배송문의', TRUE, NOW()),
    (5, 1, '상품문의', TRUE, NOW()),
    (6, 2, '당첨자 활동 문의', TRUE, NOW()),
    (7, 2, '이벤트 제품 배송 문의', TRUE, NOW()),
    (8, 3, '앱 이용 방법', TRUE, NOW()),
    (9, 3, '시스템 오류 문의', TRUE, NOW());

-- FAQ Category
INSERT INTO FAQ_CATEGORY (faq_category_id, faq_category_name, reg_dt, udt_dt)
VALUES
    (1, '쇼핑상품', NOW(), NOW()),
    (2, '쇼핑배송', NOW(), NOW()),
    (3, '앱 기능 문의', NOW(), NOW()),
    (4, '주문/결제', NOW(), NOW()),
    (5, '취소/반품/교환', NOW(), NOW());