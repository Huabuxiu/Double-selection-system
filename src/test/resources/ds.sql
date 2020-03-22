CREATE DATABASE ds;
use ds;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
                        `uid` int(11) NOT NULL AUTO_INCREMENT,
                        `user_role` int NOT NULL ,
                        `username` varchar(255) NOT NULL,
                        `password` varchar(255) NOT NULL,
                        `token` varchar(255) NOT NULL ,
                        PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
                        `sid` int(11) NOT NULL AUTO_INCREMENT,
                        `uid` int(11) NOT NULL ,
                        `name` varchar(255) NOT NULL,
                        `phone` varchar(255) NOT NULL,
                        `e_mail` varchar(255) NOT NULL ,
                        `qq` varchar(255) NOT NULL ,
                        `image` varchar(255) NOT NULL ,
                        `update_time` DATETIME NOT NULL ,
                        PRIMARY KEY (`sid`),
                        FOREIGN KEY (`uid`) REFERENCES user(`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `results`;
CREATE TABLE `results` (
                           `sid` int(11) NOT NULL AUTO_INCREMENT,
                           `rid` int(11) NOT NULL ,
                           `math` int(3) NOT NULL,
                           `english` int(3) NOT NULL,
                           `politics` int(3) NOT NULL,
                           `major` int(3) NOT NULL,
                           `total_score` int(3) NOT NULL,
                           `exam_type` varchar(255) NOT NULL,
                           PRIMARY KEY (`rid`),
                           FOREIGN KEY (`sid`) REFERENCES student(`sid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `education`;
CREATE TABLE `education` (
                           `sid` int(11) NOT NULL AUTO_INCREMENT,
                           `eid` int(11) NOT NULL ,
                           `school` varchar(255) NOT NULL,
                           `major` varchar(255) NOT NULL,
                           `time_start` DATETIME NOT NULL ,
                           `time_end` DATETIME NOT NULL ,
                           `sedu_dec` varchar(255) NOT NULL,
                           PRIMARY KEY (`eid`),
                           FOREIGN KEY (`sid`) REFERENCES student(`sid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `project`;
CREATE TABLE `project` (
                             `uid` int(11) NOT NULL AUTO_INCREMENT,
                             `pid` int(11) NOT NULL ,
                             `name` varchar(255) NOT NULL,
                             `position` varchar(255) NOT NULL,
                             `time_start` DATETIME NOT NULL ,
                             `time_end` DATETIME NOT NULL ,
                             `describe` varchar(255) NOT NULL,
                             PRIMARY KEY (`pid`),
                             FOREIGN KEY (`uid`) REFERENCES user(`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher` (
                           `uid` int(11) NOT NULL AUTO_INCREMENT,
                           `tid` int(11) NOT NULL ,
                           `name` varchar(255) NOT NULL,
                           `title` varchar(255) NOT NULL,
                           `research_direction` varchar(255) NOT NULL,
                           `image` varchar(255) NOT NULL,
                           `research_findings` longtext NOT NULL,
                           PRIMARY KEY (`tid`),
                           FOREIGN KEY (`uid`) REFERENCES user(`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `voluntary`;
CREATE TABLE `voluntary` (
                           `sid` int(11) NOT NULL AUTO_INCREMENT,
                           `vid` int(11) NOT NULL ,
                           `rid` int(11) NOT NULL ,
                           `tid` int(11) NOT NULL ,
                            `level` int NOT NULL ,
                            `progress` int NOT NULL ,
                            `date` datetime NOT NULL ,
                           PRIMARY KEY (`vid`),
                           FOREIGN KEY (`sid`) REFERENCES student(`sid`),
                           FOREIGN KEY (`rid`) REFERENCES results(`rid`),
                           FOREIGN KEY (`tid`) REFERENCES teacher(`tid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;