create table user_balance(user_id uuid primary key, balance double precision);

create table operations (oper_id uuid, user_id uuid references user_balance, oper_date timestamp, oper_type integer, oper_amount integer);

insert into user_balance(user_id, balance) values (gen_random_uuid(), 999.99);

select * from user_balance;

drop table user_balance

drop table operations