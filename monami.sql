-- Create tables in dependency order --

-- Volunteer Service table (must come first)
CREATE TABLE VOLUNTEER_SERVICE (
    service_id INT PRIMARY KEY,
    service_location VARCHAR(100),
    service_type VARCHAR(50),
    start_date DATE,
    end_date DATE,
    status VARCHAR(20),
    volunteer_id INT
) ENGINE=InnoDB;

-- Volunteer table (referenced by VOLUNTEER_SERVICE)
CREATE TABLE VOLUNTEER (
    volunteer_id INT PRIMARY KEY,
    volunteer_name VARCHAR(50) NOT NULL,
    phone_number VARCHAR(12),
    volunteer_email VARCHAR(100) UNIQUE NOT NULL,
    volunteer_pass VARCHAR(50) NOT NULL
) ENGINE=InnoDB;

-- Now add VOLUNTEER_SERVICE foreign key
ALTER TABLE VOLUNTEER_SERVICE
ADD FOREIGN KEY (volunteer_id) REFERENCES VOLUNTEER(volunteer_id);

-- Admin table (references VOLUNTEER_SERVICE)
-- REFERENCING VOLUNTEER SERVICE THAT REFERENCES VOLUNTEER CAUSES DEPENDENCY CHAIN
CREATE TABLE ADMIN (
    admin_id INT AUTO_INCREMENT PRIMARY KEY,
    admin_name VARCHAR(50) NOT NULL,
    phone_number VARCHAR(12),
    admin_pass VARCHAR(50) NOT NULL,
    service_id INT,
    FOREIGN KEY (service_id) REFERENCES VOLUNTEER_SERVICE(service_id)
) ENGINE=InnoDB;

-- Beneficiary table (independent)
CREATE TABLE BENEFICIARY (
    beneficiary_id INT PRIMARY KEY,
    beneficiary_name VARCHAR(50) NOT NULL,
    phone_number VARCHAR(12)
) ENGINE=InnoDB;

-- Requestor table (independent)
CREATE TABLE REQUESTOR (
    requestor_id INT PRIMARY KEY,
    requestor_name VARCHAR(50) NOT NULL,
    phone_number VARCHAR(12),
    requestor_email VARCHAR(100) UNIQUE NOT NULL,
    requestor_pass VARCHAR(50) NOT NULL
) ENGINE=InnoDB;

-- Service Target (junction table)
CREATE TABLE SERVICE_TARGET (
    service_id INT,
    beneficiary_id INT,
    PRIMARY KEY (service_id, beneficiary_id),
    FOREIGN KEY (service_id) REFERENCES VOLUNTEER_SERVICE(service_id),
    FOREIGN KEY (beneficiary_id) REFERENCES BENEFICIARY(beneficiary_id)
) ENGINE=InnoDB;