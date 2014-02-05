// mail order database as formatted for SQL MDB
//

create table zipcodes (
  zip      int,
  city     str);
.

create table employees (
  eno      int, 
  ename    str,
  zip      int,
  hstr    str);
.

create table parts(
  pno      int,
  pname    str,
  qoh      int,
  price    int,
  olevel   int);
.

create table customers (
  cno      int,
  cname    str,
  street   str,
  zip      int,
  phone    str);
.
 
create table orders (
  ono      int,
  cno      int,
  eno      int,
  received str,
  shipped  str);
.

create table odetails (
  ono      int,
  pno      int,
  qty      int );
.
