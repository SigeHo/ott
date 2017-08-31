CREATE TABLE IF NOT EXISTS ott.ott_user (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    user_desc VARCHAR(100),
    user_email VARCHAR(100) NOT NULL,
   	created_by INT NOT NULL,
   	create_date TIMESTAMP NOT NULL DEFAULT current_timestamp,
   	updated_by INT,
   	update_date TIMESTAMP NULL
);
  	
CREATE TABLE IF NOT EXISTS ott.ott_role (
    role_id INT AUTO_INCREMENT PRIMARY KEY,
    role_name VARCHAR(50) NOT NULL,
    role_desc VARCHAR(100),
   	created_by INT NOT NULL,
   	create_date TIMESTAMP NOT NULL DEFAULT current_timestamp,
   	updated_by INT,
   	update_date TIMESTAMP NULL
);
 	
CREATE TABLE IF NOT EXISTS ott.ott_permission (
    permission_id INT AUTO_INCREMENT PRIMARY KEY,
    permission_name VARCHAR(100) NOT NULL,
    permission_desc VARCHAR(200) NULL,
    permission_url VARCHAR(200) NOT NULL,
   	created_by INT NOT NULL,
   	create_date TIMESTAMP NOT NULL DEFAULT current_timestamp,
   	updated_by INT,
   	update_date TIMESTAMP NULL
);
  
CREATE TABLE IF NOT EXISTS ott.ott_user_role (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    role_id INT NOT NULL,
   	created_by INT NOT NULL,
   	create_date TIMESTAMP NOT NULL DEFAULT current_timestamp,
   	updated_by INT,
   	update_date TIMESTAMP NULL
);

CREATE TABLE IF NOT EXISTS ott.ott_role_permission (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    role_id INT NOT NULL,
    permission_id INT NOT NULL
);

CREATE TABLE IF NOT EXISTS ott.ott_audit_trail (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    at_user VARCHAR(50) NOT NULL,
    at_timestamp TIMESTAMP NOT NULL,
    at_table_name VARCHAR(50) NOT NULL,
    at_event TEXT NOT NULL,
   	created_by INT NOT NULL,
   	create_date TIMESTAMP NOT NULL DEFAULT current_timestamp,
   	updated_by INT,
   	update_date TIMESTAMP NULL
);

CREATE TABLE ott.ott_snooker_fixture (
    match_id VARCHAR(10),
    match_time TIMESTAMP,
    league_id VARCHAR(10),
    league_name_en VARCHAR(50),
    league_name_cn VARCHAR(50),
    league_name_hk VARCHAR(50),
    league_type VARCHAR(50),
    m_match_level VARCHAR(50),
    match_level VARCHAR(50),
    match_group VARCHAR(50),
    play_a_id VARCHAR(10),
    play_a_name_en VARCHAR(50),
    play_a_name_cn VARCHAR(50),
    play_a_name_hk VARCHAR(50),
    play_b_id VARCHAR(10),
    play_b_name_en VARCHAR(50),
    play_b_name_cn VARCHAR(50),
    play_b_name_hk VARCHAR(50),
    play_a_win_num INT,
    play_b_win_num INT,
    max_frame INT,
    current_frame_num INT,
    current_ score_a INT,
    current_score_b INT,
    current_best_player VARCHAR(10),
    current_cscore_a INT,
    current_cscore_b INT,
    current_match_sort INT,
    winner_id VARCHAR(10),
    win_reason VARCHAR(50),
    quiter_id VARCHAR(10),
    quit_reason VARCHAR(50),
    best_player VARCHAR(10),
    best_score INT,
    status VARCHAR(50),
    current_player VARCHAR(10),
    current_score INT,
    sort INT,
);

CREATE TABLE ott.ott_snooker_rank (
	player_id VARCHAR(10),
	name_cn VARCHAR(50),
	name_en VARCHAR(50),
	name_tr VARCHAR(50),
	nationality VARCHAR(50),
	rank INT(10),
	point1 INT(10),
	point2 INT(10),
	point3 INT(10),
	ptc_point INT(10),
	total_point INT(10)
);

CREATE TABLE IF NOT EXISTS ott.ott_snooker_rank (
	rank_id INT AUTO_INCREMENT PRIMARY KEY, 
	player_id INT UNIQUE,
	name_cn VARCHAR(50),
	name_en VARCHAR(50),
	name_tr VARCHAR(50),
	nationality VARCHAR(50),
	rank INT(10),
	point1 INT(10),
	point2 INT(10),
	point3 INT(10),
	ptc_point INT(10),
	total_point INT(10),
	rank_title VARCHAR(50),
	rank_year VARCHAR(50),
	last_updated_time TIMESTAMP
);

CREATE TABLE IF NOT EXISTS ott.ott_snooker_point (
	point_id INT AUTO_INCREMENT PRIMARY KEY,
	league_id INT,
	league_name_cn VARCHAR(50),
	league_name_en VARCHAR(50),
	league_name_tr VARCHAR(50),
	sn INT(10),
	last_updated_time TIMESTAMP
);

CREATE TABLE IF NOT EXISTS ott.ott_snooker_rank_point (
	id INT AUTO_INCREMENT PRIMARY KEY,
	player_id INT NOT NULL,
	point_id INT NOT NULL UNIQUE
);

insert into ott.ott_user(username, password, user_email, created_by) values('admin','21232f297a57a5a743894a0e4a801fc3','admin@pccw.com', 0);
commit;