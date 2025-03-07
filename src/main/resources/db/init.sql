create table user_balance(user_id uuid, balance double precision);

insert into user_balance(user_id, balance) values (gen_random_uuid(), 999.99);

select * from user_balance;