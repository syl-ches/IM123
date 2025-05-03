SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS SERVICE_TARGET;
DROP TABLE IF EXISTS VOLUNTEER_SERVICE;
DROP TABLE IF EXISTS SERVICE;
DROP TABLE IF EXISTS ADMIN;
DROP TABLE IF EXISTS BENEFICIARY;
DROP TABLE IF EXISTS VOLUNTEER;
DROP TABLE IF EXISTS REQUESTOR;

SET FOREIGN_KEY_CHECKS = 1;


-- 1) Independent lookup tables
CREATE TABLE REQUESTOR (
  requestor_id    INT PRIMARY KEY,
  requestor_name  VARCHAR(50) NOT NULL,
  phone_number    VARCHAR(12),
  requestor_email VARCHAR(100) UNIQUE NOT NULL,
  requestor_pass  VARCHAR(50) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE VOLUNTEER (
  volunteer_id    INT PRIMARY KEY,
  volunteer_name  VARCHAR(50) NOT NULL,
  phone_number    VARCHAR(12),
  volunteer_email VARCHAR(100) UNIQUE NOT NULL,
  volunteer_pass  VARCHAR(50) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE BENEFICIARY (
  beneficiary_id   INT PRIMARY KEY,
  beneficiary_name VARCHAR(50) NOT NULL,
  phone_number     VARCHAR(12)
) ENGINE=InnoDB;

CREATE TABLE ADMIN (
  admin_id     INT AUTO_INCREMENT PRIMARY KEY,
  admin_name   VARCHAR(50) NOT NULL,
  phone_number VARCHAR(12),
  admin_pass   VARCHAR(50) NOT NULL,
  service_id   INT
) ENGINE=InnoDB;

-- 2) SERVICE table (references REQUESTOR)
CREATE TABLE SERVICE (
  service_id       INT PRIMARY KEY,
  service_location VARCHAR(100),
  service_type     VARCHAR(50),
  start_date       DATE,
  end_date         DATE,
  current_status   VARCHAR(20),
  volunteer_id     INT DEFAULT 0,

  CONSTRAINT fk_service_requestor
    FOREIGN KEY (volunteer_id)
    REFERENCES REQUESTOR(requestor_id)
) ENGINE=InnoDB;

-- 3) VOLUNTEER_SERVICE (junction SERVICE ↔ VOLUNTEER)
CREATE TABLE VOLUNTEER_SERVICE (
  service_id   INT,
  volunteer_id INT,
  PRIMARY KEY (service_id, volunteer_id),

  CONSTRAINT fk_vs_service
    FOREIGN KEY (service_id)
    REFERENCES SERVICE(service_id),

  CONSTRAINT fk_vs_volunteer
    FOREIGN KEY (volunteer_id)
    REFERENCES VOLUNTEER(volunteer_id)
) ENGINE=InnoDB;

-- 4) SERVICE_TARGET (junction SERVICE ↔ BENEFICIARY)
CREATE TABLE SERVICE_TARGET (
  service_id      INT,
  beneficiary_id  INT,
  PRIMARY KEY (service_id, beneficiary_id),

  CONSTRAINT fk_st_service
    FOREIGN KEY (service_id)
    REFERENCES SERVICE(service_id),

  CONSTRAINT fk_st_beneficiary
    FOREIGN KEY (beneficiary_id)
    REFERENCES BENEFICIARY(beneficiary_id)
) ENGINE=InnoDB;
