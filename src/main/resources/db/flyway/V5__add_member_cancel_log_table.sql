-- MEMBER_CANCEL_LOG
CREATE TABLE IF NOT EXISTS MEMBER_CANCEL_LOG
(
    member_cancel_log_id integer     not null auto_increment,
    member_id            integer,
    reason               varchar(50) not null,
    reg_dt               datetime(6),
    primary key (member_cancel_log_id)
);