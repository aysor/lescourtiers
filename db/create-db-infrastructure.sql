create database loansRegistry;
grant all privileges on database loansRegistry to netology_user;

grant connect on database "loansRegistry" to netology_user;
grant select on all tables in schema public to netology_user;
grant all on schema public to netology_user;