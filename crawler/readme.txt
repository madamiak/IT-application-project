This sourcecode allows anyone to parse hotels from trivago.com into database. Schema of DB is placed in "SQL" folder. Requirements:
- Xampp / Lampp / or other server that reads PHP files.
- MySQL server (fe. Apache).

You need to create database schema from file "SQL\database_schema.sql" and single database user called "portier" with no password.

Script can be run by simply executing index.php from any browser.

----------

In file "SQL\hotels.sql" are stored information about 525 hotels. Unfortunatelly only 525, not whole 4000, because trivago allows you to go through only next 21 pages, containing 25 hotels on each.

More info will be on wiki page of the project.