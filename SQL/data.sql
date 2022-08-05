use music_store_catalog;

insert into artist(name, instagram, twitter)values
    ('Cheng', '@cheng', '@cheng'),
    ('Andrew', '@andrew', '@andrew'),
	('Stone', '@stone', '@stone');

insert into label(name, website) values
    ('Cheng label','www.cheng.com.tw'),
    ('Andrew label','www.andrew.com'),
    ('Stone label','www.stone.com');

insert into album(title, artist_id, release_date, label_id, list_price)values
    ('Fairy tale', '', 2022-08-02, '', 19.99),
    ('Stone rack', '', 2022-08-01, '', 29.99),
    ('Andrew star', '', 2022-08-03, '', 39.99);

insert into track(album_id, title, run_time) values
    ('', 'Mountain Monster', 120),
    ('', 'Freaking drinks', 130),
    ('', 'Wonder island', 150),
    ('', 'Endless ego', 140),
    ('', 'Best scenario', 100),
    ('', 'Long meditation', 120);