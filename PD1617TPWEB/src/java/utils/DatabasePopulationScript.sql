
--Script de populção da base de dados

--Insert into t_user

INSERT INTO t_user (username,password,address,balance,account_activation,account_suspension) VALUES ('admin','admin','',0,true,false);
INSERT INTO t_user (username,password,address,balance,account_activation,account_suspension) VALUES ('Carlos Silva','carlos','a21220128@isec.pt',50,true,false);
INSERT INTO t_user (username,password,address,balance,account_activation,account_suspension) VALUES ('Pedro Salgado','pedro','a21220091@isec.pt',295,true,false);
INSERT INTO t_user (username,password,address,balance,account_activation,account_suspension) VALUES ('Joao','coimbra','Joao@gmail.com',112.35,true,false);

--Insert into t_category

INSERT INTO t_category (name,description) VALUES ('Cell Phones','Smartphones, IPhone, Cell Phone');
INSERT INTO t_category (name,description) VALUES ('Computer','LapTops and Desktop');
INSERT INTO t_category (name,description) VALUES ('Smart Watches','Smart Watches');
INSERT INTO t_category (name,description) VALUES ('Backpack','Mens backpack, Womens backpack, Children backpack and all kind of backpack');
INSERT INTO t_category (name,description) VALUES ('Pet supplies','All kind of pet supplies for all kind of animals');

--Insert into t_item

INSERT INTO t_item (name,categoryid,description,startprice,buynowprice,auctionduration,image,ownerid) VALUES ('Xiaomi Redmi Note 3 Pro International Version 4G', 
(select c.id from t_category c where c.name LIKE 'Cell Phones'),
'Android 6.0 Qualcomm Snapdragon 650 64bit Hexa Core 1.8GHz 3GB RAM 32GB ROM 16.0MP + 5.0MP FHD',120.50,173.97,6,'Http://gloimg.gearbest.com/gb/pdm-product-pic/Electronic/2016/08/25/goods-img/1487038024709687908.jpg',
(select u.id from t_user u where u.username LIKE 'Pedro Salgado'));
INSERT INTO t_item (name,categoryid,description,startprice,buynowprice,auctionduration,image,ownerid) VALUES ('ASUS ZenFone 2 ( ZE551ML ) 4G Phablet  -  RED', 
(select c.id from t_category c where c.name LIKE 'Cell Phones'),
'5.5 inch FHD Screen Android 5.0 Intel Z3560 64bit Quad Core 1.8GHz 4GB RAM 16GB ROM 13.0MP',95.80,128.19,6,'Http://gloimg.gearbest.com/gb/pdm-product-pic/Electronic/2016/12/19/source-img/20161219165453_42344.jpeg',
(select u.id from t_user u where u.username LIKE 'Joao'));
INSERT INTO t_item (name,categoryid,description,startprice,buynowprice,auctionduration,image,ownerid) VALUES ('Xiaomi Air 13 Laptop  -  WINDOWS 10', 
(select c.id from t_category c where c.name LIKE 'Computer'),
'Windows 10 13.3 inch IPS Screen Intel Core i5-6200u Dual Core 2.3GHz 8GB RAM 256GB SSD',680.25,796.54,15,'Http://gloimg.gearbest.com/gb/pdm-product-pic/Electronic/2016/08/30/goods-img/1487051190375207549.jpg',
(select u.id from t_user u where u.username LIKE 'Pedro Salgado'));
INSERT INTO t_item (name,categoryid,description,startprice,buynowprice,auctionduration,image,ownerid) VALUES ('Original Xiaomi Mi Band 2 Smart Wristband', 
(select c.id from t_category c where c.name LIKE 'Smart Watches'),
'Bluetooth 4.0 Waterproof Battery 70mAh  Compatability Android 4.4 iOS 7.0 and above system',18,27,2,'Http://gloimg.gearbest.com/gb/pdm-product-pic/Electronic/2016/06/02/goods-img/1487104233801084174.jpg',
(select u.id from t_user u where u.username LIKE 'Joao'));
INSERT INTO t_item (name,categoryid,description,startprice,buynowprice,auctionduration,image,ownerid) VALUES ('TIGERNU T - B3143 - Business Laptop Backpack', 
(select c.id from t_category c where c.name LIKE 'Backpack'),
'Multifunctional Anti-theft Water Resistant for Office Travel',20,26.55,3,'Http://gloimg.gearbest.com/gb/pdm-product-pic/Electronic/2016/08/01/goods-img/1477243321946197768.jpg',
(select u.id from t_user u where u.username LIKE 'Joao'));
INSERT INTO t_item (name,categoryid,description,startprice,buynowprice,auctionduration,image,ownerid) VALUES ('Dog Training Adjustable Ultrasonic Sound Whistle', 
(select c.id from t_category c where c.name LIKE 'Pet supplies'),
'Adjustable pitch, simply tighten or loosen the screw to work out your dogs frequency',0.85,1.54,10,'Http://gloimg.gearbest.com/gb/2014/201412/source-img/1419211468365-P-2282173.jpg',
(select u.id from t_user u where u.username LIKE 'Joao'));

--insert into t_auction

INSERT INTO t_auction (itemid,auctionstate,itemstate,startdate,enddate,lastbid,lastuserid,sellerid) VALUES ((select i.id from t_item i where i.name LIKE 'Xiaomi Redmi Note 3 Pro International Version 4G'),
1,0,current_timestamp,(SELECT current_timestamp + interval '6 hour'),145,
(select u.id from t_user u where u.username LIKE 'Joao'),
(select u.id from t_user u where u.username LIKE 'Pedro Salgado'));
INSERT INTO t_auction (itemid,auctionstate,itemstate,startdate,enddate,lastbid,lastuserid,sellerid) VALUES ((select i.id from t_item i where i.name LIKE 'Dog Training Adjustable Ultrasonic Sound Whistle'),
1,0,current_timestamp,(SELECT current_timestamp + interval '10 hour'),1,
(select u.id from t_user u where u.username LIKE 'Pedro Salgado'),
(select u.id from t_user u where u.username LIKE 'Carlos Silva'));
INSERT INTO t_auction (itemid,auctionstate,itemstate,startdate,enddate,lastbid,lastuserid,sellerid) VALUES ((select i.id from t_item i where i.name LIKE 'ASUS ZenFone 2 ( ZE551ML ) 4G Phablet  -  RED'),
1,0,current_timestamp,(SELECT current_timestamp + interval '6 hour'),97,
(select u.id from t_user u where u.username LIKE 'Pedro Salgado'),
(select u.id from t_user u where u.username LIKE 'Joao'));
INSERT INTO t_auction (itemid,auctionstate,itemstate,startdate,enddate,lastbid,lastuserid,sellerid) VALUES ((select i.id from t_item i where i.name LIKE 'TIGERNU T - B3143 - Business Laptop Backpack'),
1,0,current_timestamp,(SELECT current_timestamp + interval '3 hour'),22,
(select u.id from t_user u where u.username LIKE 'Carlos Silva'),
(select u.id from t_user u where u.username LIKE 'Joao'));


--insert into t_auctionlog

INSERT INTO t_auctionlog (auctionid,description,creationdate) VALUES (
(select a.id from t_auction a INNER JOIN t_item i ON i.id = a.itemid where i.name LIKE 'Xiaomi Redmi Note 3 Pro International Version 4G'),
'The user Joao made a bid of 145.00',current_timestamp);
INSERT INTO t_auctionlog (auctionid,description,creationdate) VALUES (
(select a.id from t_auction a INNER JOIN t_item i ON i.id = a.itemid where i.name LIKE 'Dog Training Adjustable Ultrasonic Sound Whistle'),
'The user Pedro Salgado made a bid of 1.00',current_timestamp);
INSERT INTO t_auctionlog (auctionid,description,creationdate) VALUES (
(select a.id from t_auction a INNER JOIN t_item i ON i.id = a.itemid where i.name LIKE 'ASUS ZenFone 2 ( ZE551ML ) 4G Phablet  -  RED'),
'The user Pedro Salgado made a bid of 97.00',current_timestamp);
INSERT INTO t_auctionlog (auctionid,description,creationdate) VALUES (
(select a.id from t_auction a INNER JOIN t_item i ON i.id = a.itemid where i.name LIKE 'TIGERNU T - B3143 - Business Laptop Backpack'),
'The user Carlos Silva made a bid of 22.00',current_timestamp);

--insert into t_newsletter

INSERT INTO t_newsletter (message,creationdate) VALUES ('The user Carlos Silva have been registered',current_timestamp);
INSERT INTO t_newsletter (message,creationdate) VALUES ('The user Pedro Salgado have been registered',current_timestamp);
INSERT INTO t_newsletter (message,creationdate) VALUES ('The user Joao have been registered',current_timestamp);
INSERT INTO t_newsletter (message,creationdate) VALUES ('The auction for the item Xiaomi Redmi Note 3 Pro International Version 4G have started',current_timestamp);
INSERT INTO t_newsletter (message,creationdate) VALUES ('The auction for the item Dog Training Adjustable Ultrasonic Sound Whistle have started',current_timestamp);
INSERT INTO t_newsletter (message,creationdate) VALUES ('The auction for the item ASUS ZenFone 2 ( ZE551ML ) 4G Phablet  -  RED have started',current_timestamp);
INSERT INTO t_newsletter (message,creationdate) VALUES ('The auction for the item TIGERNU T - B3143 - Business Laptop Backpack have started',current_timestamp);

--insert into t_notification

INSERT INTO t_notification (userid,message,status) VALUES (
(select u.id from t_user u where u.username LIKE 'Carlos Silva'),'A user with the name Carlos Silva have been registed',1);
INSERT INTO t_notification (userid,message,status) VALUES (
(select u.id from t_user u where u.username LIKE 'Pedro Salgado'),'A user with the name Pedro Salgado have been registed',1);
INSERT INTO t_notification (userid,message,status) VALUES (
(select u.id from t_user u where u.username LIKE 'Joao'),'A user with the name Joao have been registed',1);

--insert into t_useritem

INSERT INTO t_useritem (userid,itemid,isselling,isbuying,isfollowing) VALUES (
(select u.id from t_user u where u.username LIKE 'Pedro Salgado'),
(select i.id from t_item i where i.name LIKE 'Xiaomi Redmi Note 3 Pro International Version 4G'),
true,false,false);
INSERT INTO t_useritem (userid,itemid,isselling,isbuying,isfollowing) VALUES (
(select u.id from t_user u where u.username LIKE 'Carlos Silva'),
(select i.id from t_item i where i.name LIKE 'Dog Training Adjustable Ultrasonic Sound Whistle'),
true,false,false);
INSERT INTO t_useritem (userid,itemid,isselling,isbuying,isfollowing) VALUES (
(select u.id from t_user u where u.username LIKE 'Joao'),
(select i.id from t_item i where i.name LIKE 'ASUS ZenFone 2 ( ZE551ML ) 4G Phablet  -  RED'),
true,false,false);
INSERT INTO t_useritem (userid,itemid,isselling,isbuying,isfollowing) VALUES (
(select u.id from t_user u where u.username LIKE 'Joao'),
(select i.id from t_item i where i.name LIKE 'TIGERNU T - B3143 - Business Laptop Backpack'),
true,false,false);
INSERT INTO t_useritem (userid,itemid,isselling,isbuying,isfollowing) VALUES (
(select u.id from t_user u where u.username LIKE 'Joao'),
(select i.id from t_item i where i.name LIKE 'Xiaomi Redmi Note 3 Pro International Version 4G'),
false,true,false);
INSERT INTO t_useritem (userid,itemid,isselling,isbuying,isfollowing) VALUES (
(select u.id from t_user u where u.username LIKE 'Pedro Salgado'),
(select i.id from t_item i where i.name LIKE 'Dog Training Adjustable Ultrasonic Sound Whistle'),
false,true,false);
INSERT INTO t_useritem (userid,itemid,isselling,isbuying,isfollowing) VALUES (
(select u.id from t_user u where u.username LIKE 'Carlos Silva'),
(select i.id from t_item i where i.name LIKE 'TIGERNU T - B3143 - Business Laptop Backpack'),
false,true,false);
INSERT INTO t_useritem (userid,itemid,isselling,isbuying,isfollowing) VALUES (
(select u.id from t_user u where u.username LIKE 'Pedro Salgado'),
(select i.id from t_item i where i.name LIKE 'ASUS ZenFone 2 ( ZE551ML ) 4G Phablet  -  RED'),
false,true,false);
INSERT INTO t_useritem (userid,itemid,isselling,isbuying,isfollowing) VALUES (
(select u.id from t_user u where u.username LIKE 'Carlos Silva'),
(select i.id from t_item i where i.name LIKE 'ASUS ZenFone 2 ( ZE551ML ) 4G Phablet  -  RED'),
false,false,true);

--insert into t_message

INSERT INTO t_message (fromid,toid,subject,message,creationdate) VALUES (
(select u.id from t_user u where u.username LIKE 'admin'),
(select u.id from t_user u where u.username LIKE 'Carlos Silva'),
'Alert about ASUS ZenFone 2 ( ZE551ML ) 4G Phablet',
'The auction for the item ASUS ZenFone 2 ( ZE551ML ) 4G Phablet - RED have started' ,
current_timestamp);
INSERT INTO t_message (fromid,toid,subject,message,creationdate) VALUES (
(select u.id from t_user u where u.username LIKE 'admin'),
(select u.id from t_user u where u.username LIKE 'Carlos Silva'),
'Alert about ASUS ZenFone 2 ( ZE551ML ) 4G Phablet',
'A user have made a bind to the item ASUS ZenFone 2 ( ZE551ML ) 4G Phablet - RED have started' ,current_timestamp);