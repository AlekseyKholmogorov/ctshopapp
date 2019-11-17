INSERT INTO category ( name) VALUES
    ('coffee'),
    ('tea'),
    ('accessories');
INSERT INTO product (name, region, description, price,quantity, category_id) VALUES
    ('assam', 'india', 'black tea from india', 21.95, 3, 2),
    ('lapsang souchong', 'china', 'black tea from china', 9.98, 0, 2),
    ('bucaramanga', 'colombia', 'coffee from colombia', 26.40, 0, 1),
    ('amecafe', 'brazil', 'coffee from brazil', 18.75, 2, 1);