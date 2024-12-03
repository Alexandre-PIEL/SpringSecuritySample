create table customer
(
    email varchar(254) not null primary key,
    password varchar(100) not null,
    role varchar(20) not null
);
insert into customer values
(
    'user@email.com', 
    '{noop}SpringSecuritySample@user',
    'user'
);
insert into customer values
(
    'admin@email.com', 
    '{noop}SpringSecuritySample@admin',
    'admin'
);
