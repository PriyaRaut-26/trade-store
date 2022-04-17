DROP TABLE IF EXISTS trade_store;
CREATE TABLE trade_store(
id INT AUTO_INCREMENT  PRIMARY KEY,
trade_id varchar(255) NOT NULL,
version INT NOT NULL,
counter_party_id varchar(255) ,
book_id varchar(255) ,
maturity_date date default NULL,
create_date date default NULL,
expiry char(1)
);
