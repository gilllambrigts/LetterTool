CREATE TABLE entry (
        id          INTEGER PRIMARY KEY AUTO_INCREMENT,
        submission_date VARCHAR NOT NULL);

CREATE TABLE word (
        id          INTEGER PRIMARY KEY AUTO_INCREMENT,
        entry_id INTEGER NOT NULL,
        word VARCHAR(64) NOT NULL,
        FOREIGN KEY (entry_id) REFERENCES entry(id));