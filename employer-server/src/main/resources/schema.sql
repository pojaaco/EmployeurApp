DROP TABLE Employee;
DROP TABLE Employer;
DROP SEQUENCE NumberSeq;

--

CREATE TABLE Employer (
    EmployerID BIGINT IDENTITY(1,1) PRIMARY KEY,
    Fund VARCHAR(255) NOT NULL,
    Number VARCHAR(50) NOT NULL,
    Name NVARCHAR(255) NOT NULL,
    NumberIde NVARCHAR(36) NOT NULL,
    StartDate DATE NOT NULL,
    EndDate DATE
);

ALTER TABLE Employer
    ADD CONSTRAINT chk_NumberFormat
        CHECK (Number LIKE '[0-9][0-9][0-9][0-9][0-9][0-9]');

ALTER TABLE Employer
    ADD CONSTRAINT chk_NumberIdeFormat
        CHECK (NumberIde LIKE 'CHE-[0-9][0-9][0-9].[0-9][0-9][0-9].[0-9][0-9][0-9]' OR
               NumberIde LIKE 'ADM-[0-9][0-9][0-9].[0-9][0-9][0-9].[0-9][0-9][0-9]');

--

CREATE TABLE Employee (
    EmployeeID BIGINT IDENTITY(1,1) PRIMARY KEY,
    NumberAvs VARCHAR(36) NOT NULL,
    LastName VARCHAR(255) NOT NULL,
    FirstName VARCHAR(255) NOT NULL,
    StartDate DATE NOT NULL,
    EndDate DATE NOT NULL,
    AvsAiApg DECIMAL(17, 2) NOT NULL,
    Ac DECIMAL(17, 2) NOT NULL,
    Af DECIMAL(17, 2) NOT NULL,
    EmployerID BIGINT,
    CONSTRAINT fk_Employee_Employer FOREIGN KEY (EmployerID) REFERENCES Employer(EmployerID)
);

ALTER TABLE Employee
    ADD CONSTRAINT chk_NumberAvsFormat
        CHECK (NumberAvs LIKE '756.[0-9][0-9][0-9][0-9].[0-9][0-9][0-9][0-9].[0-9][0-9]');

--

CREATE SEQUENCE NumberSeq
    START WITH 1
    INCREMENT BY 1;