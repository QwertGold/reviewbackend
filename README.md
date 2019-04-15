
Create two database in your local MySQL using the following commands:
<pre>
CREATE DATABASE review_db COLLATE utf8_bin;
CREATE DATABASE review_db_test COLLATE utf8_bin;
CREATE USER 'review'@'%' IDENTIFIED BY 'review';
GRANT ALL PRIVILEGES ON review_db.* to 'review'@'%';
GRANT ALL PRIVILEGES ON review_db_test.* to 'review'@'%';
</pre>  

