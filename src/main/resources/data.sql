insert into brand  values (1,'adidas');
insert into brand  values (2,'nike');

insert into product (id,brand_id,color,image_url,price,stock_quantity) values (1,1,'Black','/images/black-adidas.png',149.99,100);
insert into product (id,brand_id,color,image_url,price,stock_quantity) values (2,1,'White','/images/white-adidas.png',149.99,100);
insert into product (id,brand_id,color,image_url,price,stock_quantity) values (3,2,'Black','/images/black-nike.png',149.99,100);
insert into product (id,brand_id,color,image_url,price,stock_quantity) values (4,2,'White','/images/white-nike.png',149.99,100);

insert into special_day_campaign(id,campaign_name,brand_id,campaign_date,discount_percentage) values (1,'Kış Kampanyası',1,'2018-12-03',10);
insert into special_day_campaign(id,campaign_name,brand_id,campaign_date,discount_percentage) values (2,'Yılbaşı',1,'2018-01-01',40);
insert into special_day_campaign(id,campaign_name,brand_id,campaign_date,discount_percentage) values (3,'23 Nisan Kampanyası',2,'2019-04-23',10);

insert into seasonal_campaign (id, product_id, start_date, end_date, season,price) values (1,3,'2018-12-01','2019-02-28','WINTER',70);
insert into seasonal_campaign (id, product_id, start_date, end_date, season,price) values (2,3,'2019-03-01','2019-05-31','SPRING',50);
insert into seasonal_campaign (id, product_id, start_date, end_date, season,price) values (3,3,'2018-12-01','2019-02-28','WINTER',100);
insert into seasonal_campaign (id, product_id, start_date, end_date, season,price) values (4,3,'2019-06-01','2019-09-30','SUMMER',120);
