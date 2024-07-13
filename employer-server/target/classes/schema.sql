DROP TABLE Employee;
DROP TABLE Employer;
DROP SEQUENCE NumberSeq;

CREATE TABLE Employer (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    caisse NVARCHAR(255) NOT NULL,
    number NVARCHAR(50) NOT NULL,
    name NVARCHAR(255) NOT NULL,
    number_ide NVARCHAR(36) NOT NULL,
    starting_date DATE NOT NULL,
    end_date DATE
);

ALTER TABLE Employer
    ADD CONSTRAINT chk_number_format
        CHECK (number LIKE '[0-9][0-9][0-9][0-9][0-9][0-9]');

ALTER TABLE Employer
    ADD CONSTRAINT chk_number_ide_format
        CHECK (number_ide LIKE 'CHE-[0-9][0-9][0-9].[0-9][0-9][0-9].[0-9][0-9][0-9]' OR
               number_ide LIKE 'ADM-[0-9][0-9][0-9].[0-9][0-9][0-9].[0-9][0-9][0-9]');

CREATE TABLE Employee (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    number_avs NVARCHAR(36) NOT NULL,
    last_name NVARCHAR(255) NOT NULL,
    first_name NVARCHAR(255) NOT NULL,
    starting_date DATE NOT NULL,
    end_date DATE NOT NULL,
    amount_of_assurance_avs_ai_apg DECIMAL(17, 2) NOT NULL,
    amount_of_assurance_ac DECIMAL(17, 2) NOT NULL,
    amount_of_assurance_af DECIMAL(17, 2) NOT NULL,
    employer_id BIGINT,
    CONSTRAINT fk_Employee_Employer FOREIGN KEY (employer_id) REFERENCES Employer(id)
);

ALTER TABLE Employee
    ADD CONSTRAINT chk_number_avs_format
        CHECK (number_avs LIKE '756.[0-9][0-9][0-9][0-9].[0-9][0-9][0-9][0-9].[0-9][0-9]');

CREATE SEQUENCE NumberSeq
    START WITH 1
    INCREMENT BY 1;