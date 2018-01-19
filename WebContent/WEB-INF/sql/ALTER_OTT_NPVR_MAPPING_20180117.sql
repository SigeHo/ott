ALTER TABLE `ott`.`ott_npvr_mapping` 
ADD COLUMN `is_override` CHAR(1) NULL DEFAULT 'N' AFTER `actual_end_date_time`;