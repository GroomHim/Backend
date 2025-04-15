-- MEMBER
CREATE TABLE IF NOT EXISTS  MEMBER (
    member_id integer not null auto_increment,
    skin_type_id integer,
    login_id varchar(255),
    password varchar(255),
    salt varchar(255),
    gender varchar(255),
    nickname varchar(20),
    birth varchar(10),
    email varchar(255),
    provider varchar(255),
    refresh_token varchar(200),
    social_token_id varchar(255),
    is_cancel bit,
    role varchar(255),
    reg_dt datetime(6),
    udt_dt datetime(6),
    primary key (member_id)
);

-- PRODUCT
CREATE TABLE IF NOT EXISTS  PRODUCT (
    product_id integer not null auto_increment,
    brand_id integer,
    category_id integer,
    discount_rate float,
    discounted_price integer,
    price integer,
    product_name varchar(255) not null,
    img_url varchar(2048) not null,
    purchase_site_url varchar(2048),
    ingredients TEXT,
    is_public bit,
    is_deleted bit,
    reg_dt datetime(6),
    udt_dt datetime(6),
    primary key (product_id)
);

CREATE TABLE IF NOT EXISTS  PRODUCT_IMG (
    product_img_id integer not null auto_increment,
    product_id integer,
    prio varchar(255),
    img_url varchar(2048),
    type varchar(255) ,
    reg_dt datetime(6),
    udt_dt datetime(6),
    primary key (product_img_id)
);

CREATE TABLE IF NOT EXISTS  BRAND (
    brand_id integer not null auto_increment,
    brand_name varchar(255),
    en_brand_name varchar(255),
    reg_dt datetime(6),
    udt_dt datetime(6),
    primary key (brand_id)
);

-- WISH
CREATE TABLE IF NOT EXISTS  WISH (
    wish_id integer not null auto_increment,
    member_id integer,
    product_id integer,
    reg_dt datetime(6),
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
    reg_dt datetime(6),
    udt_dt datetime(6),
    primary key (agreementId)
);

-- SEARCH
CREATE TABLE IF NOT EXISTS  SEARCH (
    search_id bigint not null auto_increment,
    member_id integer,
    search_word varchar(255),
    reg_dt datetime(6),
    primary key (search_id)
);

-- CATEGORY
CREATE TABLE IF NOT EXISTS  CATEGORY (
    category_id integer not null auto_increment,
    parent_category_id integer,
    category_name varchar(255),
    depth integer,
    is_leaf bit,
    reg_dt datetime(6),
    udt_dt datetime(6),
    primary key (category_id)
);

CREATE TABLE IF NOT EXISTS  EXHIBIT_CATEGORY (
    exhibit_category_id integer not null auto_increment,
    exhibit_category_name varchar(255),
    parent_exhibit_category_id integer,
    is_leaf bit,
    depth integer,
    reg_dt datetime(6),
    udt_dt datetime(6),
    primary key (exhibit_category_id)
);

CREATE TABLE IF NOT EXISTS  PRODUCT_EXHIBIT_CATEGORY_LINK (
    product_exhibit_category_id integer not null auto_increment,
    exhibit_category_id integer,
    product_id integer,
    reg_dt datetime(6),
    primary key (product_exhibit_category_id)
);

-- SKIN-TYPE
CREATE TABLE IF NOT EXISTS  SKIN_TYPE (
    skin_type_id integer not null auto_increment,
    skin_type_name varchar(255),
    description varchar(255),
    rate float,
    reg_dt datetime(6),
    primary key (skin_type_id)
);

CREATE TABLE IF NOT EXISTS  PRODUCT_SKIN_TYPE_LINK (
    product_skin_type_id integer not null auto_increment,
    product_id integer,
    skin_type_id integer,
    reg_dt datetime(6),
    primary key (product_skin_type_id)
);

-- QA & FAQ & NOTICE
CREATE TABLE IF NOT EXISTS QA (
    qa_id integer not null auto_increment,
    member_id integer,
    qa_category_id integer,
    title varchar(50),
    content varchar(255),
    status varchar(255),
    reg_dt datetime(6),
    udt_dt datetime(6),
    primary key (qa_id)
);

CREATE TABLE IF NOT EXISTS  QA_ANSWER (
    qa_answer_id integer not null auto_increment,
    member_id integer,
    qa_id integer,
    answer varchar(255),
    reg_dt datetime(6),
    udt_dt datetime(6),
    primary key (qa_answer_id)
);

CREATE TABLE IF NOT EXISTS  QA_CATEGORY (
    qa_category_id integer not null auto_increment,
    parent_qa_category_id integer,
    qa_category_name varchar(255),
    is_leaf bit,
    reg_dt datetime(6),
    primary key (qa_category_id)
);

CREATE TABLE IF NOT EXISTS  FAQ (
    faq_id integer not null auto_increment,
    faq_category_id integer,
    prio varchar(255),
    question varchar(255),
    answer varchar(255),
    is_public bit,
    reg_dt datetime(6),
    udt_dt datetime(6),
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
    title varchar(255),
    content varchar(255),
    is_public bit,
    reg_dt datetime(6),
    udt_dt datetime(6),
    primary key (notice_id)
);