INSERT INTO Employer (caisse, number, name, number_ide, starting_date, end_date)
VALUES
       ('CAISSE_CANTONALE', '000001', '3AAA Treuhand GmbH', 'CHE-111.222.334', '2019-01-01', '2023-12-31'),
       ('CAISSE_PROFESSIONNELLE', '000002', '9Punk GmbH', 'CHE-245.686.565', '2018-01-01', '2023-11-30'),
       ('CAISSE_CANTONALE', '000003', 'Skssg GmbH', 'CHE-532.123.554', '2018-02-01', '2025-11-30');

INSERT INTO Employee (number_avs, last_name, first_name, starting_date, end_date, amount_of_assurance_avs_ai_apg, amount_of_assurance_ac, amount_of_assurance_af, employer_id)
VALUES
       ('756.7566.8458.18', 'KNEZEVIC', 'KATA', '2023-01-01', '2023-12-31', 200000.116, 15000.234, 30000.5, 2),
       ('756.8205.9052.92', 'ACERBI', 'ANNIBALe', '2023-05-01', '2023-12-31', 100000.351, 10000.2222, 20000.234, 2);