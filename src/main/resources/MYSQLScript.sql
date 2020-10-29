-- -----------------------------------------------------
-- Schema okonomi_oksen
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `okonomi_oksen` DEFAULT CHARACTER SET utf8mb4;
USE `okonomi_oksen`;

-- -----------------------------------------------------
-- Table `okonomi_oksen`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `okonomi_oksen`.`user` (
                                                      `user_id` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                                      `username` VARCHAR(60),
                                                      `password` VARCHAR(300),
                                                      `privileges` VARCHAR(50)
)
    ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `okonomi_oksen`.`income`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `okonomi_oksen`.`income` (
                                                        `income_id` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                                        `salary` DOUBLE NOT NULL,
                                                        `housingSubsidy` DOUBLE NOT NULL,
                                                        `equities` DOUBLE NOT NULL,
                                                        `others` DOUBLE NOT NULL,
                                                        `total_income` DOUBLE NOT NULL,
                                                        `user_fk_id` INT UNSIGNED,
                                                        FOREIGN KEY (`user_fk_id`)
                                                            REFERENCES `okonomi_oksen`.`user` (`user_id`)
                                                            ON DELETE CASCADE
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `okonomi_oksen`.`expense`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `okonomi_oksen`.`expense` (
                                                         `expense_id` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                                         `residential` DOUBLE,
                                                         `regulars` DOUBLE,
                                                         `transport` DOUBLE,
                                                         `food` DOUBLE,
                                                         `miscellaneous` DOUBLE,
                                                         `loan` DOUBLE,
                                                         `overall_expenses` DOUBLE NOT NULL,
                                                         `income_fk_id` INT UNSIGNED NOT NULL,
                                                         `user_fk_id` INT UNSIGNED,
                                                         FOREIGN KEY (`income_fk_id`)
                                                             REFERENCES `okonomi_oksen`.`income` (`income_id`)
                                                             ON DELETE CASCADE,
                                                         FOREIGN KEY (`user_fk_id`)
                                                             REFERENCES `okonomi_oksen`.`user` (`user_id`)
                                                             ON DELETE CASCADE
)
    ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `okonomi_oksen`.`calculator`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `okonomi_oksen`.`calculator` (
                                                            `calculator_id` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                                            `income` DOUBLE,
                                                            `expense` DOUBLE,
                                                            `surplus` DOUBLE,
                                                            `income_fk_id` INT UNSIGNED,
                                                            `expense_fk_id` INT UNSIGNED,
                                                            FOREIGN KEY (`income_fk_id`)
                                                                REFERENCES `okonomi_oksen`.`income` (`income_id`)
                                                                ON DELETE CASCADE,
                                                            FOREIGN KEY (`expense_fk_id`)
                                                                REFERENCES `okonomi_oksen`.`expense` (`expense_id`)
                                                                ON DELETE CASCADE)
    ENGINE = InnoDB;

INSERT INTO `okonomi_oksen`.`income`
(`salary`, `housingSubsidy`, `equities`, `others`, `total_income`)
    VALUE (20000, 600, 1200, 600, (`salary` + `housingSubsidy` + `equities` + `others`));

INSERT INTO `okonomi_oksen`.`income`
(`salary`, `housingSubsidy`, `equities`, `others`, `total_income`)
    VALUE (15000, 800, 700, 750, (`salary` + `housingSubsidy` + `equities` + `others`));

INSERT INTO `okonomi_oksen`.`income`
(`salary`, `housingSubsidy`, `equities`, `others`, `total_income`)
    VALUE (28000, 200, 600, 500, (`salary` + `housingSubsidy` + `equities` + `others`));

INSERT INTO `okonomi_oksen`.`expense`
(`residential`, `regulars`, `transport`, `food`, `miscellaneous`, `loan`, `overall_expenses`, `income_fk_id`)
VALUES (9000, 2300, 800, 4500, 1200, 1400, (`residential` + `regulars` + `transport` + `food` + `miscellaneous` + `loan`), 1);

INSERT INTO `okonomi_oksen`.`expense`
(`residential`, `regulars`, `transport`, `food`, `miscellaneous`, `loan`, `overall_expenses`, `income_fk_id`)
VALUES (7000, 3100, 1200, 3300, 1500, 1000, (`residential` + `regulars` + `transport` + `food` + `miscellaneous` + `loan`), 2);

INSERT INTO `okonomi_oksen`.`expense`
(`residential`, `regulars`, `transport`, `food`, `miscellaneous`, `loan`, `overall_expenses`, `income_fk_id`)
VALUES (12000, 2400, 1500, 3800, 3500, 500, (`residential` + `regulars` + `transport` + `food` + `miscellaneous` + `loan`), 2);

INSERT INTO `okonomi_oksen`.`calculator` (`income`, `expense`, `surplus`, `income_fk_id`, `expense_fk_id`)
VALUES ((SELECT `total_income` FROM `income` WHERE `income_id` = 1),
        (SELECT `overall_expenses` FROM `expense` Where `expense_id` = 1), (`income` - `expense`),
        1, 1);

INSERT INTO `okonomi_oksen`.`calculator` (`income`, `expense`, `surplus`, `income_fk_id`, `expense_fk_id`)
VALUES ((SELECT `total_income` FROM `income` WHERE `income_id` = 1),
        (SELECT `overall_expenses` FROM `expense` Where `expense_id` = 1), (`income` - `expense`),
        1, 1);

INSERT INTO `okonomi_oksen`.`user` (`username`, `password`)
VALUES ('test1234', '4f7081fc1cc87d6779479b682bee34df47a3c8882da592d61c30b5a382f0ae65');

-- SELECT * FROM user;
DROP DATABASE okonomi_oksen;