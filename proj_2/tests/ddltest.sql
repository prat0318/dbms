// SQL-MDL ddl test file
// should parse with no errors

open "don/mdb.database";
.

create table emp ( empno int,
              age   int,
              dept_name str,
              name str
            );
.

// create table dept relation
create table dept ( deptno int,
               dept_name str,
               chairman str
             );
.

// create table some indices

index emp.age;
.
index dept.dept_name;
.

// display schema for emp relation
// useful if you forget fields of emp

show  emp;
.

// display schema for all relations
// useful if you forget entire database

show;
.

script "insert_mydatabase_tuples";
.
commit;
.
close;
.
exit;
.
