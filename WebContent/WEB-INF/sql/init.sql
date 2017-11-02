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
    role_id INT NOT NULL
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

CREATE TABLE IF NOT EXISTS ott.ott_snooker_score (
	score_id INT AUTO_INCREMENT PRIMARY KEY,
	score_type VARCHAR(10),
    match_id INT,
    season_id INT,
    match_time DATETIME NULL,
    league_id INT,
    league_name_en VARCHAR(50),
    league_name_cn VARCHAR(50),
    league_name_tr VARCHAR(50),
    league_type VARCHAR(50),
   	match_level1 VARCHAR(50),
    match_level2 VARCHAR(50),
    match_group VARCHAR(50),
    player_a_id INT,
    player_b_id INT,
    player_a_name_cn VARCHAR(50),
    player_a_name_en VARCHAR(50),
    player_a_name_tr VARCHAR(50),
    player_b_name_cn VARCHAR(50),
    player_b_name_en VARCHAR(50),
    player_b_name_tr VARCHAR(50),
    player_a_win_num INT,
    player_b_win_num INT,
    max_field INT,
    current_field INT,
    winner_id INT,
    win_reason VARCHAR(100),
    quiter_id INT,
    quit_reason VARCHAR(100),
    best_player INT,
    best_score INT,
    status VARCHAR(50),
    current_player INT,
    current_score INT,
    sort INT,
    last_published_date TIMESTAMP
);

CREATE TABLE IF NOT EXISTS ott.ott_snooker_frame (
	frame_id INT AUTO_INCREMENT PRIMARY KEY,
	frame_type VARCHAR(10),
	match_sort INT,
	sort INT,
	score_a INT,
	score_b INT,
	best_player INT,
	cscore_a INT,
	cscore_b INT,
	last_published_date TIMESTAMP
);

CREATE TABLE IF NOT EXISTS ott.ott_snooker_score_frame (
	id INT AUTO_INCREMENT PRIMARY KEY,
	score_id INT,
	frame_id INT
);

CREATE TABLE IF NOT EXISTS ott.ott_snooker_rank (
	rank_id INT AUTO_INCREMENT PRIMARY KEY, 
	player_id INT UNIQUE,
	name_cn VARCHAR(50),
	name_en VARCHAR(50),
	name_tr VARCHAR(50),
	nationality VARCHAR(50),
	rank INT,
	point1 INT,
	point2 INT,
	point3 INT,
	ptc_point INT,
	total_point INT,
	rank_title VARCHAR(50),
	rank_year VARCHAR(50),
	last_published_date TIMESTAMP
);

CREATE TABLE IF NOT EXISTS ott.ott_snooker_point (
	point_id INT AUTO_INCREMENT PRIMARY KEY,
	league_id INT,
	league_name_cn VARCHAR(50),
	league_name_en VARCHAR(50),
	league_name_tr VARCHAR(50),
	sn INT(10),
	last_published_date TIMESTAMP
);

CREATE TABLE IF NOT EXISTS ott.ott_snooker_rank_point (
	id INT AUTO_INCREMENT PRIMARY KEY,
	player_id INT NOT NULL,
	point_id INT NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS ott.ott_snooker_league (
	league_t_id INT AUTO_INCREMENT PRIMARY KEY,
	league_id INT,
	league_name_cn VARCHAR(50),
	league_name_en VARCHAR(50),
	league_name_tr VARCHAR(50),
	start_time DATETIME NULL,
	end_time DATETIME NULL,
	color VARCHAR(10),
	remark VARCHAR(200),
	money INT,
	last_published_date TIMESTAMP
);

CREATE TABLE IF NOT EXISTS ott.ott_snooker_level (
	level_id INT AUTO_INCREMENT PRIMARY KEY,
	level_rounds VARCHAR(50),
	match_levels VARCHAR(50),
	match_group INT,
	remark VARCHAR(200),
	last_published_date TIMESTAMP
);

CREATE TABLE IF NOT EXISTS ott.ott_snooker_league_level (
	id INT AUTO_INCREMENT PRIMARY KEY,
	league_t_id INT,
	level_id INT
);

CREATE TABLE IF NOT EXISTS ott.ott_snooker_person (
	id INT AUTO_INCREMENT PRIMARY KEY,
	player_id INT,
	name_cn VARCHAR(50),
	name_en VARCHAR(50),
	name_tr VARCHAR(50),
	sex VARCHAR(10),
	nationality VARCHAR(50),
	birthday DATE NULL,
	height INT,
	weight INT,
	score INT,
	max_score_num INT,
	current_rank INT,
	highest_rank INT,
	transfer_time DATE NULL,
	total_money INT,
	win_record INT,
	point INT,
	experience VARCHAR(2000),
	remark VARCHAR(2000),
	last_published_date TIMESTAMP
);

CREATE TABLE IF NOT EXISTS ott.ott_npvr_mapping (
	id INT AUTO_INCREMENT PRIMARY KEY,
	channelNo INT(3),
	sport_type VARCHAR(20),
	fixture_id VARCHAR(20),
	npvr_id VARCHAR(20)
);

insert into ott.ott_user(username, password, user_email, created_by) values('admin','21232f297a57a5a743894a0e4a801fc3','admin@pccw.com', 0);
commit;