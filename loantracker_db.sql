drop database loantrackerdb;
drop user loantracker;
create user loantracker with password 'password';
create database loantrackerdb with template=template0 owner=loantracker;
\connect loantrackerdb;
alter default privileges grant all on tables to loantracker;
alter default privileges grant all on sequences to loantracker;

create table lt_users(
user_id integer primary key not null,
first_name varchar(20) not null,
last_name varchar(20) not null,
email varchar(30) not null,
password text not null,
is_admin boolean default false
);

create table lt_accounts(
account_id integer primary key not null check(account_id between 1000000000 and 9999999999),
customer_id integer not null,
opening_balance numeric(10,2) not null
);

alter table lt_accounts add constraint acct_users_fk foreign key (customer_id) references lt_users(user_id);

create table lt_loans(
loan_id integer primary key not null,
customer_id integer not null,
loan_amount numeric(10,2) not null,
disbursement_date timestamp not null default now()
);

alter table lt_loans add constraint loan_user_fk foreign key (customer_id) references lt_users(user_id);

create table lt_loan_payments(
loan_payment_id integer primary key not null,
customer_id integer not null,
loan_id integer not null,
amount numeric(10,2) not null,
note varchar(50) not null,
payment_date timestamp not null default now()
);

alter table lt_loan_payments add constraint loan_loanpayment_fk foreign key (loan_id) references lt_loans(loan_id);
alter table lt_loan_payments add constraint user_loanpayment_fk foreign key (customer_id) references lt_users(user_id);

create sequence lt_users_seq increment 1 start 1;
create sequence lt_accounts_seq increment 1 start 1000000000;
create sequence lt_loan_seq increment 1 start 1;
create sequence lt_loan_payments_seq increment 1 start 1;