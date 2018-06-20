INSERT INTO users (user_username, user_email, user_full_name, user_password, user_photo)
VALUES 			  ("mitar123", "mitar123@grand.com", "Mitar Miric", "123456", "default_user_photo.png"),
				  ("saban123", "saban123@grand.com", "Saban Saulic", "123456", "default_user_photo.png"),
				  ("brena123", "brena123@grand.com", "Lepa Brena", "123456", "default_user_photo.png");
				  
INSERT INTO posts (post_content, post_date_posted, post_title, post_author_username, post_photo, post_likes, post_dislikes)
VALUES            ("Gledam Drinu njene vode plave ...", "2018/01/01", "SOKANTNO", "mitar123", "default_post_photo.png", 10, 1),
				  ("Od malena ja sam snjom ispracao leta ...", "1999/01/01", "JOS SOKANTNIJE", "saban123", "default_post_photo.png", 10, 2),
				  ("Duge noge za igranje vitko telo za pevanje ...", "1992/05/16", "SOK I NEVERICA !", "brena123", "default_post_photo.png", 1, 1),
				  ("Palim zadnju cigaretu \n idem u beskraj s njom...", "2001/09/08", "ODE MITAR IZ BOSNE", "mitar123", "default_post_photo.png", 2, 0),
				  ("Luda za tobom sta se tu moze \n kad jos te cuvam ispod koze", "2003/01/01", "STA DA VAM KAZEM", "brena123", "default_post_photo.png", 3, 0);
				  
INSERT INTO tags (tag_name) VALUES ("kornjace"), ("delfini"), ("aligator"), ("orka"), ("panda");