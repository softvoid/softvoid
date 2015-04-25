CREATE OR REPLACE PROCEDURE pro_hwyk
(
   i_nspid   spxx.nspid%TYPE,
   i_sckid_y ck.sckid%TYPE,
   i_shwid_y hwxx.shwid%TYPE,
   i_sckid_m ck.sckid%TYPE,
   i_shwid_m hwxx.shwid%TYPE,
   i_sph     hwspjc.sph%TYPE,
   i_amount  NUMBER,
   i_sczyid  czy.sczyid%TYPE,
   o_errmsg  OUT VARCHAR2
) AUTHID CURRENT_USER IS
   /**
   专门为Android客户端写的货位移库（当然其他的也可以用）。先更新源货位的库存，然后插入新的货位
   作者：Neo
   */
   v_nspid   spxx.nspid%TYPE;
   v_srymc   ryxx.srymc%TYPE;
   v_sbmid   bm.sbmid%TYPE;
   v_sbmmc   bm.sbmmc%TYPE;
   v_dscrq   DATE;
   v_dyxqz   DATE;
   v_nykid   NUMBER;
   v_n4kcksl NUMBER;
   v_n4hwsl  NUMBER;
   v_sfzlx zdylx.slxid%type;
   v_count number(1);
   v_sql varchar2(255);
   v_user varchar2(20);

   integrity_error EXCEPTION;
   errno  INTEGER;
   errmsg VARCHAR2(200);
BEGIN
   SELECT a.nspid, dscrq, dyxqz
   INTO v_nspid, v_dscrq, v_dyxqz
   FROM spxx a, hwspjc b
  WHERE a.nspid = i_nspid AND a.nspid = b.nspid AND b.shwid = i_shwid_y AND b.sph=i_sph;

   SELECT srymc, b.sbmid, sbmmc
   INTO v_srymc, v_sbmid, v_sbmmc
   FROM ryxx a, bm b
  WHERE sryid = i_sczyid AND a.sbmid = b.sbmid;

   --从源头货位下架
   set_hwspjc_xj('0101', i_shwid_y, v_nspid, i_sph, i_amount, i_amount, 0, 1, errmsg, 0);
   write_hwspkpjl('0101', v_nspid, i_sckid_y, i_shwid_y, i_sph, i_sczyid, v_srymc, v_sbmid, v_sbmmc,
          -1, '', -1, '', -i_amount, 'Android客户端移库移出', -i_amount, '', 1, '', '');
   --向不合格库上架
   set_hwspjc_sj('0101', i_shwid_m, v_nspid, i_sph, i_amount, i_amount, i_shwid_y, v_dscrq, v_dyxqz,
         '', '', '', 0, 1, errmsg, 1);
   write_hwspkpjl('0101', v_nspid, i_sckid_m, i_shwid_m, i_sph, i_sczyid, v_srymc, v_sbmid, v_sbmmc,
          -1, '', -1, '', i_amount, 'Android客户端移库移入', i_amount, '', 1, '');

   --移库写入接口
   exec_cjk_yk('0101', i_sckid_m, i_shwid_m, i_sckid_y, i_shwid_y, v_nspid, i_sph, v_dyxqz, v_dscrq,
         i_amount, errmsg);

   v_nykid := f_update_zddjbh_bh('0101', 'YKD');
   --增加移库单记录  此处直接增加状态为完成的移库单
   --增加移库单主表记录
   INSERT INTO ykd
    (nykid, sgsid, nlx, dzdrq, sbz, nykfs, sckid_yc, sckid_yr, ndjzt,sywyid,sshr,dshrq,szdr,sbmid)
    SELECT v_nykid, '0101', 4, SYSDATE, 'Android客户端移库', 3, i_sckid_y, i_sckid_m, 3,
    i_sczyid,i_sczyid,sysdate,i_sczyid,v_sbmid
    FROM dual;
   --增加移库单明细记录
   INSERT INTO ykdmx
    (sgsid, nykid, nhh, nspid, sph, spzwh, dscrq, dyxqz, sckid_yc, shwid_yc, sckid_jhyy, sckid_yy, shwid_jhyy, shwid_yy, n4sl_jhyy, n4sl_yy, sbz)
    SELECT '0101', v_nykid, 1, v_nspid, i_sph, a.spjwh, v_dscrq, v_dyxqz, i_sckid_y, i_shwid_y, i_sckid_m, i_sckid_m, i_shwid_m, i_shwid_m, i_amount, i_amount, 'Android客户端移库'
    FROM spxx a
     WHERE a.nspid = v_nspid;
   SELECT n4kcksl, n4hwsl
   INTO v_n4kcksl, v_n4hwsl
   FROM hwspjc
  WHERE sckid = i_sckid_y AND shwid = i_shwid_y AND sph = i_sph AND nspid = v_nspid;
   IF v_n4kcksl = 0 AND v_n4hwsl = 0 THEN
    DELETE FROM hwspjc
     WHERE sckid = i_sckid_y AND shwid = i_shwid_y AND sph = i_sph AND nspid = v_nspid;
   END IF;
   /*使用PDA移库时，不仅把库存移到目标仓库也需要把仓库绑定改到目标仓库，同时更改分组类型。*/
   begin
     begin
     --查询目的仓库的修改药品的分组类型
     select sfzlx into v_sfzlx from hwxx a, ck b where a.sckid=b.sckid
            and b.nlx=2 and shwid = i_shwid_m and nsfjdhw=0;

     EXCEPTION
      WHEN NO_DATA_FOUND THEN
        v_sfzlx := '601';
     END;
     update spxx set sfzlx = v_sfzlx where nspid = v_nspid;
     --绑定到目的仓库。先把原绑定的仓库做一个临时，然后修改其目的仓库，删除仓库绑定，最后添加
     select user into v_user from dual;
     select count(1) into v_count from all_tables where TABLE_NAME = 'TMP_NEO_YK' and OWNER=v_user; 
     if v_count=1   then 
        execute immediate 'drop table tmp_neo_yk'; 
     end   if;
     execute immediate 'create table tmp_neo_yk(sckid varchar2(16),sgsid varchar2(4),
            nspid number(16),n4sx number(16,4),n4xx number(16,4))';
     execute immediate 'insert into tmp_neo_yk select sckid,sgsid,nspid,n4sx,n4xx from ckspgl 
             where sckid= '||i_sckid_y||' and nspid = '|| i_nspid;
     --更新临时表的货位，注意拼接是引号问题，否则字符串式的数字当作数字处理了。
     v_sql := 'update tmp_neo_yk set sckid= '||''''|| i_sckid_m ||'''';
     execute immediate v_sql;
     --删除原先的仓库绑定
     delete from ckspgl where sckid = i_sckid_y and nspid = i_nspid;
     --把更新过的临时表数据插入到ckspgl表中
     execute immediate 'insert into ckspgl select * from tmp_neo_yk 
             where nspid= '|| i_nspid ||'and sckid= '|| i_sckid_m;
     --删除临时表             
     execute immediate 'drop table tmp_neo_yk';
     --修改药品的分组类型，药品移动到目的货位，那么分组类型也需要改变否则下次来货就会机动货位。
     update spxx set sfzlx=(select sfzlx from hwxx where shwid=i_shwid_m) where nspid=i_nspid;
     commit;
   end;
EXCEPTION
   WHEN INTEGRITY_ERROR THEN
    RAISE_APPLICATION_ERROR(ERRNO, ERRMSG);
   o_errmsg := errmsg;
   ROLLBACK;
END;
