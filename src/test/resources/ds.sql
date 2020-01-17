CREATE DATABASE ds;
use ds;
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
                        `id` int(11) NOT NULL AUTO_INCREMENT,
                        `userrole` int NOT NULL ,
                        `username` varchar(255) NOT NULL,
                        `password` varchar(255) NOT NULL,
                        `name` varchar(255) NOT NULL,
                        `email` varchar(255) NOT NULL,
                        `qq` varchar(50) NOT NULL ,
                        `phone` varchar(11) NOT NULL ,
                        `avatar` varchar(255) NOT NULL ,
                        `sex` int(1) DEFAULT NULL,
                        `token` varchar(255) NOT NULL ,
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

INSERT INTO `user` VALUES ('1', '1','1347638343@qq.com', '1ee04e0b1cb5af7367c80c22e42efd8b', '小马','1347638343@qq.com', '1347638343','13279532337', 'https://img2.woyaogexing.com/2020/01/16/429b82f4f75c4ac584a7d394efdc3326!400x400.jpeg','1','1ee04e0b1cb5af7367c80c22e42efd8b');
INSERT INTO `user` VALUES ( '2','3','134763834@qq.com', '1ee04e0b1cb5af7367c80c22e42efd8b', '小李', '134763834@qq.com', '134763834','13279532337','https://img2.woyaogexing.com/2020/01/16/a1ca7c3d414d490d87bd4a2545ead0a4!400x400.jpeg','0','1ee04e0b1cb5af7367c80c22e42efd8b');