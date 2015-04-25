--创建个人信息表
--drop table myself;
create table myself(
       sryid varchar2(8) primary key, --人员id
       suid varchar2(30) unique not null, --用户帐号，类似QQ号
       snickname varchar2(30) default '佚名', --昵称
       sgender varchar2(2) default '男', --性别
       nage varchar2(3) default 0,    --年龄
       sadd varchar2(30) default '湖北 武汉',
       nlevel varchar2(5) default 0,       --等级
       nvip varchar2(5) default 0,         --特权
       bimg blob,              --用户头像
       smemo varchar2(512) default '这个家伙很懒，什么也没留下...',     --用户备注
       sfilename varchar2(30) unique not null	--文件名
)
