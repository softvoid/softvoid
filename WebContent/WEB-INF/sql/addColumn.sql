--对rkd添加【是否验收】的字段，为了在Android端体现出验收扫码完成     
alter table rkd add nsfys number(1) default 0; 
comment on column rkd.nsfys is '是否验收（为了在Android端体现出验收扫码完成加的字段）'; 
--alter table rkd drop column nsfys;  