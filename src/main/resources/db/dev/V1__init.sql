-- MEMBER
CREATE TABLE IF NOT EXISTS  MEMBER (
    is_cancel bit,
    member_id integer not null auto_increment,
    skin_type_id integer,
    reg_dt datetime(6) not null,
    udt_dt datetime(6) not null,
    birth varchar(10) not null,
    login_id varchar(15) not null,
    nickname varchar(20) not null,
    name varchar(30) not null,
    salt varchar(32),
    email varchar(50) not null,
    password varchar(64),
    refresh_token varchar(200),
    ci varchar(255),
    social_token_id varchar(255),
    gender enum ('M','W') not null,
    provider enum ('APPLE','GOOGLE','GROOMHIM','KAKAO','NAVER') not null,
    role enum ('ADMIN','USER'),
    primary key (member_id)
);

-- PRODUCT
CREATE TABLE IF NOT EXISTS  PRODUCT (
    brand_id integer not null,
    category_id integer,
    discount_rate float(23) not null,
    discounted_price integer not null,
    price integer not null,
    product_id integer not null auto_increment,
    reg_dt datetime(6) not null,
    udt_dt datetime(6) not null,
    delivery_info varchar(50) not null,
    product_name varchar(50) not null,
    img_url varchar(2048) not null,
    purchase_site_url varchar(2048),
    ingredients TEXT,
    primary key (product_id)
);

CREATE TABLE IF NOT EXISTS  PRODUCT_IMG (
    product_img_id integer not null auto_increment,
    product_id integer,
    prio varchar(20) not null,
    img_url varchar(2048) not null,
    type enum ('CONTENT','MAIN') not null,
    reg_dt datetime(6) not null,
    udt_dt datetime(6) not null,
    primary key (product_img_id)
);

CREATE TABLE IF NOT EXISTS  BRAND (
    brand_id integer not null auto_increment,
    brand_name varchar(255) not null,
    en_brand_name varchar(255) not null,
    reg_dt datetime(6) not null,
    udt_dt datetime(6) not null,
    primary key (brand_id)
);

-- WISH
CREATE TABLE IF NOT EXISTS  WISH (
    wish_id integer not null auto_increment,
    member_id integer not null,
    product_id integer not null,
    reg_dt datetime(6) not null,
    primary key (wish_id)
);

-- AGREEMENT
CREATE TABLE IF NOT EXISTS  AGREEMENT (
    agreementId integer not null auto_increment,
    member_id integer,
    location_consent bit,
    marketing_consent bit,
    personal_data_consent bit,
    terms_of_service_consent bit,
    primary key (agreementId)
);

-- SEARCH
CREATE TABLE IF NOT EXISTS  SEARCH (
    search_id bigint not null auto_increment,
    member_id integer,
    reg_dt datetime(6) not null,
    search_word varchar(255),
    primary key (search_id)
);

-- CATEGORY
CREATE TABLE IF NOT EXISTS  CATEGORY (
    category_id integer not null auto_increment,
    parent_category_id integer,
    category_name varchar(15) not null,
    depth integer not null,
    is_leaf bit not null,
    reg_dt datetime(6) not null,
    udt_dt datetime(6) not null,
    primary key (category_id)
);

CREATE TABLE IF NOT EXISTS  EXHIBIT_CATEGORY (
    exhibit_category_id integer not null auto_increment,
    exhibit_category_name varchar(15) not null,
    parent_exhibit_category_id integer,
    is_leaf bit not null,
    depth integer not null,
    reg_dt datetime(6) not null,
    udt_dt datetime(6) not null,
    primary key (exhibit_category_id)
);

CREATE TABLE IF NOT EXISTS  PRODUCT_EXHIBIT_CATEGORY_LINK (
    product_exhibit_category_id integer not null auto_increment,
    exhibit_category_id integer not null,
    product_id integer not null,
    reg_dt datetime(6) not null,
    primary key (product_exhibit_category_id)
);

-- SKIN-TYPE
CREATE TABLE IF NOT EXISTS  SKIN_TYPE (
    skin_type_id integer not null auto_increment,
    skin_type_name varchar(10) not null,
    description varchar(255) not null,
    rate decimal(38,2) not null,
    reg_dt datetime(6) not null,
    primary key (skin_type_id)
);

CREATE TABLE IF NOT EXISTS  PRODUCT_SKIN_TYPE_LINK (
    product_skin_type_id integer not null auto_increment,
    product_id integer not null,
    skin_type_id integer not null,
    reg_dt datetime(6) not null,
    primary key (product_skin_type_id)
);

-- QA & FAQ & NOTICE
CREATE TABLE IF NOT EXISTS QA (
    qa_id integer not null auto_increment,
    member_id integer,
    qa_category_id integer,
    title varchar(50) not null,
    content varchar(255) not null,
    status varchar(10) not null,
    reg_dt datetime(6) not null,
    udt_dt datetime(6) not null,
    primary key (qa_id)
);

CREATE TABLE IF NOT EXISTS  QA_ANSWER (
    qa_answer_id integer not null auto_increment,
    member_id integer,
    qa_id integer,
    answer varchar(255) not null,
    reg_dt datetime(6) not null,
    udt_dt datetime(6) not null,
    primary key (qa_answer_id)
);

CREATE TABLE IF NOT EXISTS  QA_CATEGORY (
    qa_category_id integer not null auto_increment,
    parent_qa_category_id integer,
    qa_category_name varchar(15) not null,
    is_leaf bit not null,
    reg_dt datetime(6) not null,
    primary key (qa_category_id)
);

CREATE TABLE IF NOT EXISTS  FAQ (
    faq_id integer not null auto_increment,
    faq_category_id integer,
    prio varchar(20) not null,
    question varchar(50) not null,
    answer varchar(255) not null,
    is_public bit not null,
    reg_dt datetime(6) not null,
    udt_dt datetime(6) not null,
    primary key (faq_id)
);

CREATE TABLE IF NOT EXISTS  FAQ_CATEGORY (
    faq_category_id integer not null auto_increment,
    faq_category_name varchar(255),
    reg_dt datetime(6),
    udt_dt datetime(6),
    primary key (faq_category_id)
);

CREATE TABLE IF NOT EXISTS  NOTICE (
    notice_id integer not null auto_increment,
    member_id integer,
    title varchar(20) not null,
    content varchar(255) not null,
    is_public bit not null,
    reg_dt datetime(6) not null,
    udt_dt datetime(6) not null,
    primary key (notice_id)
);