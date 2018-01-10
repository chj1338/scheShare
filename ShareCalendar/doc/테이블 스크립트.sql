CREATE TABLE COM_USER
(
  USER_NO VARCHAR2(20) NOT NULL,
  USER_NM VARCHAR2(20),
  LOGIN_ID VARCHAR2(20),
  LOGIN_PW VARCHAR2(20),
  CLIENT_IP VARCHAR2(20),
  WRONG_PW_CNT NUMBER(4,0),
  LAST_LOGIN_DT DATE,
  CONSTRAINT COM_USER_PK PRIMARY KEY ( USER_NO ) ENABLE
)
;

-- ����
create table BIBLE_MASTER (
  BOOK_SEQ        NUMBER(5,0),
  BOOK_NM_KOR     varchar(50),
  BOOK_NM_KOR_ABR varchar(50),
  BOOK_NM_ENG     varchar(50),
  BOOK_NM_ENG_ABR varchar(50),
  LAST_PAGE       NUMBER(5,0),
  CONSTRAINT BIBLE_MASTER_PK PRIMARY KEY ( BOOK_SEQ ) ENABLE
);


-- �����б� ���
drop table BIBLE_READER cascade constraints;
 
create table BIBLE_READER (
  USER_ID         varchar(50),
  READ_SEQ        NUMBER(5,0),
  BOOK_NM_KOR_ABR varchar(50),
  BOOK_PAGE       NUMBER(5,0),
  READ_DATE       Date,
  CONSTRAINT BIBLE_READER_PK PRIMARY KEY ( USER_ID, READ_SEQ, BOOK_NM_KOR_ABR, BOOK_PAGE ) ENABLE
);


Insert into BIBLE_MASTER (BOOK_SEQ,BOOK_NM_KOR,BOOK_NM_KOR_ABR,BOOK_NM_ENG,BOOK_NM_ENG_ABR,LAST_PAGE,BOOK_SE) values (1,'â����','â','Genesis','GEN',50,'�𼼿���');
Insert into BIBLE_MASTER (BOOK_SEQ,BOOK_NM_KOR,BOOK_NM_KOR_ABR,BOOK_NM_ENG,BOOK_NM_ENG_ABR,LAST_PAGE,BOOK_SE) values (2,'��ֱ���','��','Exodus','EXO',40,'�𼼿���');
Insert into BIBLE_MASTER (BOOK_SEQ,BOOK_NM_KOR,BOOK_NM_KOR_ABR,BOOK_NM_ENG,BOOK_NM_ENG_ABR,LAST_PAGE,BOOK_SE) values (3,'������','��','Leviticus','LEV',27,'�𼼿���');
Insert into BIBLE_MASTER (BOOK_SEQ,BOOK_NM_KOR,BOOK_NM_KOR_ABR,BOOK_NM_ENG,BOOK_NM_ENG_ABR,LAST_PAGE,BOOK_SE) values (4,'�μ���','��','Numbers','NUM',36,'�𼼿���');
Insert into BIBLE_MASTER (BOOK_SEQ,BOOK_NM_KOR,BOOK_NM_KOR_ABR,BOOK_NM_ENG,BOOK_NM_ENG_ABR,LAST_PAGE,BOOK_SE) values (5,'�Ÿ��','��','Deuteronomy','DEU',34,'�𼼿���');
Insert into BIBLE_MASTER (BOOK_SEQ,BOOK_NM_KOR,BOOK_NM_KOR_ABR,BOOK_NM_ENG,BOOK_NM_ENG_ABR,LAST_PAGE,BOOK_SE) values (6,'��ȣ����','��','Joshua','JOS',24,'���缭');
Insert into BIBLE_MASTER (BOOK_SEQ,BOOK_NM_KOR,BOOK_NM_KOR_ABR,BOOK_NM_ENG,BOOK_NM_ENG_ABR,LAST_PAGE,BOOK_SE) values (7,'����','��','Judges','JDG',21,'���缭');
Insert into BIBLE_MASTER (BOOK_SEQ,BOOK_NM_KOR,BOOK_NM_KOR_ABR,BOOK_NM_ENG,BOOK_NM_ENG_ABR,LAST_PAGE,BOOK_SE) values (8,'���','��','Ruth','RTH',4,'���缭');
Insert into BIBLE_MASTER (BOOK_SEQ,BOOK_NM_KOR,BOOK_NM_KOR_ABR,BOOK_NM_ENG,BOOK_NM_ENG_ABR,LAST_PAGE,BOOK_SE) values (9,'�繫����','���','1Samuel','SA1',31,'���缭');
Insert into BIBLE_MASTER (BOOK_SEQ,BOOK_NM_KOR,BOOK_NM_KOR_ABR,BOOK_NM_ENG,BOOK_NM_ENG_ABR,LAST_PAGE,BOOK_SE) values (10,'�繫����','����','2Samuel','SA2',24,'���缭');
Insert into BIBLE_MASTER (BOOK_SEQ,BOOK_NM_KOR,BOOK_NM_KOR_ABR,BOOK_NM_ENG,BOOK_NM_ENG_ABR,LAST_PAGE,BOOK_SE) values (11,'���ձ��','�ջ�','1Kings','KI1',22,'���缭');
Insert into BIBLE_MASTER (BOOK_SEQ,BOOK_NM_KOR,BOOK_NM_KOR_ABR,BOOK_NM_ENG,BOOK_NM_ENG_ABR,LAST_PAGE,BOOK_SE) values (12,'���ձ���','����','2Kings','KI2',25,'���缭');
Insert into BIBLE_MASTER (BOOK_SEQ,BOOK_NM_KOR,BOOK_NM_KOR_ABR,BOOK_NM_ENG,BOOK_NM_ENG_ABR,LAST_PAGE,BOOK_SE) values (13,'�����','���','1Chronicles','CH1',29,'���缭');
Insert into BIBLE_MASTER (BOOK_SEQ,BOOK_NM_KOR,BOOK_NM_KOR_ABR,BOOK_NM_ENG,BOOK_NM_ENG_ABR,LAST_PAGE,BOOK_SE) values (14,'������','����','2Chronicles','CH2',36,'���缭');
Insert into BIBLE_MASTER (BOOK_SEQ,BOOK_NM_KOR,BOOK_NM_KOR_ABR,BOOK_NM_ENG,BOOK_NM_ENG_ABR,LAST_PAGE,BOOK_SE) values (15,'������','��','Ezra','EZR',10,'���缭');
Insert into BIBLE_MASTER (BOOK_SEQ,BOOK_NM_KOR,BOOK_NM_KOR_ABR,BOOK_NM_ENG,BOOK_NM_ENG_ABR,LAST_PAGE,BOOK_SE) values (16,'����̾�','��','Nehemiah','NEH',13,'���缭');
Insert into BIBLE_MASTER (BOOK_SEQ,BOOK_NM_KOR,BOOK_NM_KOR_ABR,BOOK_NM_ENG,BOOK_NM_ENG_ABR,LAST_PAGE,BOOK_SE) values (17,'������','��','Esther','EST',10,'���缭');
Insert into BIBLE_MASTER (BOOK_SEQ,BOOK_NM_KOR,BOOK_NM_KOR_ABR,BOOK_NM_ENG,BOOK_NM_ENG_ABR,LAST_PAGE,BOOK_SE) values (18,'���','��','Job','JOB',42,'�ð���');
Insert into BIBLE_MASTER (BOOK_SEQ,BOOK_NM_KOR,BOOK_NM_KOR_ABR,BOOK_NM_ENG,BOOK_NM_ENG_ABR,LAST_PAGE,BOOK_SE) values (19,'����','��','Psalms','PSA',150,'�ð���');
Insert into BIBLE_MASTER (BOOK_SEQ,BOOK_NM_KOR,BOOK_NM_KOR_ABR,BOOK_NM_ENG,BOOK_NM_ENG_ABR,LAST_PAGE,BOOK_SE) values (20,'���','��','Proverbs','PRO',31,'�ð���');
Insert into BIBLE_MASTER (BOOK_SEQ,BOOK_NM_KOR,BOOK_NM_KOR_ABR,BOOK_NM_ENG,BOOK_NM_ENG_ABR,LAST_PAGE,BOOK_SE) values (21,'������','��','Ecclesiastes','ECC',12,'�ð���');
Insert into BIBLE_MASTER (BOOK_SEQ,BOOK_NM_KOR,BOOK_NM_KOR_ABR,BOOK_NM_ENG,BOOK_NM_ENG_ABR,LAST_PAGE,BOOK_SE) values (22,'�ư�','��','SongofSongs','SON',8,'�ð���');
Insert into BIBLE_MASTER (BOOK_SEQ,BOOK_NM_KOR,BOOK_NM_KOR_ABR,BOOK_NM_ENG,BOOK_NM_ENG_ABR,LAST_PAGE,BOOK_SE) values (23,'�̻��','��','Isaiah','ISA',66,'������');
Insert into BIBLE_MASTER (BOOK_SEQ,BOOK_NM_KOR,BOOK_NM_KOR_ABR,BOOK_NM_ENG,BOOK_NM_ENG_ABR,LAST_PAGE,BOOK_SE) values (24,'�����̾�','��','Jeremiah','JER',52,'������');
Insert into BIBLE_MASTER (BOOK_SEQ,BOOK_NM_KOR,BOOK_NM_KOR_ABR,BOOK_NM_ENG,BOOK_NM_ENG_ABR,LAST_PAGE,BOOK_SE) values (25,'�����̾� �ְ�','��','Lamentations','LAM',5,'������');
Insert into BIBLE_MASTER (BOOK_SEQ,BOOK_NM_KOR,BOOK_NM_KOR_ABR,BOOK_NM_ENG,BOOK_NM_ENG_ABR,LAST_PAGE,BOOK_SE) values (26,'������','��','Ezekiel','EZE',48,'������');
Insert into BIBLE_MASTER (BOOK_SEQ,BOOK_NM_KOR,BOOK_NM_KOR_ABR,BOOK_NM_ENG,BOOK_NM_ENG_ABR,LAST_PAGE,BOOK_SE) values (27,'�ٴϿ�','��','Daniel','DAN',12,'������');
Insert into BIBLE_MASTER (BOOK_SEQ,BOOK_NM_KOR,BOOK_NM_KOR_ABR,BOOK_NM_ENG,BOOK_NM_ENG_ABR,LAST_PAGE,BOOK_SE) values (28,'ȣ����','ȣ','Hosea','HOS',14,'������');
Insert into BIBLE_MASTER (BOOK_SEQ,BOOK_NM_KOR,BOOK_NM_KOR_ABR,BOOK_NM_ENG,BOOK_NM_ENG_ABR,LAST_PAGE,BOOK_SE) values (29,'�俤','��','Joel','JOE',3,'������');
Insert into BIBLE_MASTER (BOOK_SEQ,BOOK_NM_KOR,BOOK_NM_KOR_ABR,BOOK_NM_ENG,BOOK_NM_ENG_ABR,LAST_PAGE,BOOK_SE) values (30,'�Ƹ�','��','Amos','AMO',9,'������');
Insert into BIBLE_MASTER (BOOK_SEQ,BOOK_NM_KOR,BOOK_NM_KOR_ABR,BOOK_NM_ENG,BOOK_NM_ENG_ABR,LAST_PAGE,BOOK_SE) values (31,'���ٴ�','��','Obadiah','OBA',1,'������');
Insert into BIBLE_MASTER (BOOK_SEQ,BOOK_NM_KOR,BOOK_NM_KOR_ABR,BOOK_NM_ENG,BOOK_NM_ENG_ABR,LAST_PAGE,BOOK_SE) values (32,'�䳪','��','Jonah','JON',4,'������');
Insert into BIBLE_MASTER (BOOK_SEQ,BOOK_NM_KOR,BOOK_NM_KOR_ABR,BOOK_NM_ENG,BOOK_NM_ENG_ABR,LAST_PAGE,BOOK_SE) values (33,'�̰�','��','Micah','MIC',7,'������');
Insert into BIBLE_MASTER (BOOK_SEQ,BOOK_NM_KOR,BOOK_NM_KOR_ABR,BOOK_NM_ENG,BOOK_NM_ENG_ABR,LAST_PAGE,BOOK_SE) values (34,'����','��','Nahum','NAH',3,'������');
Insert into BIBLE_MASTER (BOOK_SEQ,BOOK_NM_KOR,BOOK_NM_KOR_ABR,BOOK_NM_ENG,BOOK_NM_ENG_ABR,LAST_PAGE,BOOK_SE) values (35,'�Ϲڱ�','��','Habakkuk','HAB',3,'������');
Insert into BIBLE_MASTER (BOOK_SEQ,BOOK_NM_KOR,BOOK_NM_KOR_ABR,BOOK_NM_ENG,BOOK_NM_ENG_ABR,LAST_PAGE,BOOK_SE) values (36,'���ٳ�','��','Zephaniah','ZEP',3,'������');
Insert into BIBLE_MASTER (BOOK_SEQ,BOOK_NM_KOR,BOOK_NM_KOR_ABR,BOOK_NM_ENG,BOOK_NM_ENG_ABR,LAST_PAGE,BOOK_SE) values (37,'�а�','��','Haggai','HAG',2,'������');
Insert into BIBLE_MASTER (BOOK_SEQ,BOOK_NM_KOR,BOOK_NM_KOR_ABR,BOOK_NM_ENG,BOOK_NM_ENG_ABR,LAST_PAGE,BOOK_SE) values (38,'������','��','Zechariah','ZEC',14,'������');
Insert into BIBLE_MASTER (BOOK_SEQ,BOOK_NM_KOR,BOOK_NM_KOR_ABR,BOOK_NM_ENG,BOOK_NM_ENG_ABR,LAST_PAGE,BOOK_SE) values (39,'�����','��','Malachi','MAL',4,'������');
Insert into BIBLE_MASTER (BOOK_SEQ,BOOK_NM_KOR,BOOK_NM_KOR_ABR,BOOK_NM_ENG,BOOK_NM_ENG_ABR,LAST_PAGE,BOOK_SE) values (40,'���º���','��','Matthew','MAT',28,'����/���缭');
Insert into BIBLE_MASTER (BOOK_SEQ,BOOK_NM_KOR,BOOK_NM_KOR_ABR,BOOK_NM_ENG,BOOK_NM_ENG_ABR,LAST_PAGE,BOOK_SE) values (41,'��������','��','Mark','MAR',16,'����/���缭');
Insert into BIBLE_MASTER (BOOK_SEQ,BOOK_NM_KOR,BOOK_NM_KOR_ABR,BOOK_NM_ENG,BOOK_NM_ENG_ABR,LAST_PAGE,BOOK_SE) values (42,'��������','��','Luke','LUK',24,'����/���缭');
Insert into BIBLE_MASTER (BOOK_SEQ,BOOK_NM_KOR,BOOK_NM_KOR_ABR,BOOK_NM_ENG,BOOK_NM_ENG_ABR,LAST_PAGE,BOOK_SE) values (43,'���Ѻ���','��','John','JOH',21,'����/���缭');
Insert into BIBLE_MASTER (BOOK_SEQ,BOOK_NM_KOR,BOOK_NM_KOR_ABR,BOOK_NM_ENG,BOOK_NM_ENG_ABR,LAST_PAGE,BOOK_SE) values (44,'�絵����','��','Acts','ACT',28,'����/���缭');
Insert into BIBLE_MASTER (BOOK_SEQ,BOOK_NM_KOR,BOOK_NM_KOR_ABR,BOOK_NM_ENG,BOOK_NM_ENG_ABR,LAST_PAGE,BOOK_SE) values (45,'�θ���','��','Romans','ROM',16,'���ż�');
Insert into BIBLE_MASTER (BOOK_SEQ,BOOK_NM_KOR,BOOK_NM_KOR_ABR,BOOK_NM_ENG,BOOK_NM_ENG_ABR,LAST_PAGE,BOOK_SE) values (46,'��������','����','1Corinthians','CO1',16,'���ż�');
Insert into BIBLE_MASTER (BOOK_SEQ,BOOK_NM_KOR,BOOK_NM_KOR_ABR,BOOK_NM_ENG,BOOK_NM_ENG_ABR,LAST_PAGE,BOOK_SE) values (47,'�����ļ�','����','2Corinthians','CO2',13,'���ż�');
Insert into BIBLE_MASTER (BOOK_SEQ,BOOK_NM_KOR,BOOK_NM_KOR_ABR,BOOK_NM_ENG,BOOK_NM_ENG_ABR,LAST_PAGE,BOOK_SE) values (48,'�����Ƽ�','��','Galatians','GAL',6,'���ż�');
Insert into BIBLE_MASTER (BOOK_SEQ,BOOK_NM_KOR,BOOK_NM_KOR_ABR,BOOK_NM_ENG,BOOK_NM_ENG_ABR,LAST_PAGE,BOOK_SE) values (49,'�����Ҽ�','��','Ephesians','EPH',6,'���ż�');
Insert into BIBLE_MASTER (BOOK_SEQ,BOOK_NM_KOR,BOOK_NM_KOR_ABR,BOOK_NM_ENG,BOOK_NM_ENG_ABR,LAST_PAGE,BOOK_SE) values (50,'��������','��','Philippians','PHI',4,'���ż�');
Insert into BIBLE_MASTER (BOOK_SEQ,BOOK_NM_KOR,BOOK_NM_KOR_ABR,BOOK_NM_ENG,BOOK_NM_ENG_ABR,LAST_PAGE,BOOK_SE) values (51,'��λ���','��','Colossians','COL',4,'���ż�');
Insert into BIBLE_MASTER (BOOK_SEQ,BOOK_NM_KOR,BOOK_NM_KOR_ABR,BOOK_NM_ENG,BOOK_NM_ENG_ABR,LAST_PAGE,BOOK_SE) values (52,'����δϰ�����','����','1Thessalonians','TH1',5,'���ż�');
Insert into BIBLE_MASTER (BOOK_SEQ,BOOK_NM_KOR,BOOK_NM_KOR_ABR,BOOK_NM_ENG,BOOK_NM_ENG_ABR,LAST_PAGE,BOOK_SE) values (53,'����δϰ��ļ�','����','2Thessalonians','TH2',3,'���ż�');
Insert into BIBLE_MASTER (BOOK_SEQ,BOOK_NM_KOR,BOOK_NM_KOR_ABR,BOOK_NM_ENG,BOOK_NM_ENG_ABR,LAST_PAGE,BOOK_SE) values (54,'�������','����','1Timothy','TI1',6,'���ż�');
Insert into BIBLE_MASTER (BOOK_SEQ,BOOK_NM_KOR,BOOK_NM_KOR_ABR,BOOK_NM_ENG,BOOK_NM_ENG_ABR,LAST_PAGE,BOOK_SE) values (55,'����ļ�','����','2Timothy','TI2',4,'���ż�');
Insert into BIBLE_MASTER (BOOK_SEQ,BOOK_NM_KOR,BOOK_NM_KOR_ABR,BOOK_NM_ENG,BOOK_NM_ENG_ABR,LAST_PAGE,BOOK_SE) values (56,'�𵵼�','��','Titus','TIT',3,'���ż�');
Insert into BIBLE_MASTER (BOOK_SEQ,BOOK_NM_KOR,BOOK_NM_KOR_ABR,BOOK_NM_ENG,BOOK_NM_ENG_ABR,LAST_PAGE,BOOK_SE) values (57,'������','��','Philemon','PHM',1,'���ż�');
Insert into BIBLE_MASTER (BOOK_SEQ,BOOK_NM_KOR,BOOK_NM_KOR_ABR,BOOK_NM_ENG,BOOK_NM_ENG_ABR,LAST_PAGE,BOOK_SE) values (58,'���긮��','��','Hebrews','HEB',13,'���ż�');
Insert into BIBLE_MASTER (BOOK_SEQ,BOOK_NM_KOR,BOOK_NM_KOR_ABR,BOOK_NM_ENG,BOOK_NM_ENG_ABR,LAST_PAGE,BOOK_SE) values (59,'�߰���','��','James','JAM',5,'���ż�');
Insert into BIBLE_MASTER (BOOK_SEQ,BOOK_NM_KOR,BOOK_NM_KOR_ABR,BOOK_NM_ENG,BOOK_NM_ENG_ABR,LAST_PAGE,BOOK_SE) values (60,'���������','����','1Peter','PE1',5,'���ż�');
Insert into BIBLE_MASTER (BOOK_SEQ,BOOK_NM_KOR,BOOK_NM_KOR_ABR,BOOK_NM_ENG,BOOK_NM_ENG_ABR,LAST_PAGE,BOOK_SE) values (61,'������ļ�','����','2Peter','PE2',3,'���ż�');
Insert into BIBLE_MASTER (BOOK_SEQ,BOOK_NM_KOR,BOOK_NM_KOR_ABR,BOOK_NM_ENG,BOOK_NM_ENG_ABR,LAST_PAGE,BOOK_SE) values (62,'����1��','����','1John','JO1',5,'���ż�');
Insert into BIBLE_MASTER (BOOK_SEQ,BOOK_NM_KOR,BOOK_NM_KOR_ABR,BOOK_NM_ENG,BOOK_NM_ENG_ABR,LAST_PAGE,BOOK_SE) values (63,'����2��','����','2John','JO2',1,'���ż�');
Insert into BIBLE_MASTER (BOOK_SEQ,BOOK_NM_KOR,BOOK_NM_KOR_ABR,BOOK_NM_ENG,BOOK_NM_ENG_ABR,LAST_PAGE,BOOK_SE) values (64,'����3��','���','3John','JO3',1,'���ż�');
Insert into BIBLE_MASTER (BOOK_SEQ,BOOK_NM_KOR,BOOK_NM_KOR_ABR,BOOK_NM_ENG,BOOK_NM_ENG_ABR,LAST_PAGE,BOOK_SE) values (65,'���ټ�','��','Jude','JUD',1,'���ż�');
Insert into BIBLE_MASTER (BOOK_SEQ,BOOK_NM_KOR,BOOK_NM_KOR_ABR,BOOK_NM_ENG,BOOK_NM_ENG_ABR,LAST_PAGE,BOOK_SE) values (66,'���Ѱ�÷�','��','Revelation','REV',22,'����');



-- ���� ���� �� ��õ
select 
  AA.BOOK_NM_KOR_ABR||':'||
  (select (nvl(max(BB.BOOK_PAGE),0)+1)
     from BIBLE_READER BB
    where AA.BOOK_NM_KOR_ABR = BB.BOOK_NM_KOR_ABR 
      and BB.USER_ID = 'UK330'
      and BB.READ_SEQ = '1') as RECOM_BOOM
from BIBLE_MASTER AA
where AA.BOOK_SEQ = nvl((select max(A.BOOK_SEQ) as MAX_BOOK_SEQ
                           from BIBLE_MASTER A, BIBLE_READER B
                          where A.BOOK_NM_KOR_ABR = B.BOOK_NM_KOR_ABR
                            and B.USER_ID = 'UK330'
                            and A.BOOK_SE = '������'
                            and B.READ_SEQ = '1' )
                        ,(select min(BOOK_SEQ) from BIBLE_MASTER where BOOK_SE = '������')
                        )
;