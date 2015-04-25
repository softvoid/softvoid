CREATE OR REPLACE PROCEDURE EXEC_RWSQ_DB2
(
  I_SGSID          IN VARCHAR2, --公司ID
  I_SRYID          IN VARCHAR2, --人员ID
  I_NSFCKRW        IN NUMBER DEFAULT -1,--是否索取出库(0入库 1出库) -1表示入库，出库统一索取。 0只索取入库 1只索取出库
  I_NXTCSJC_SFNFH  IN NUMBER DEFAULT -1, --建议从客户端传入，提高效率，系统参数,是否需要使用内复核台。-1需要检测 0参数值 1参数值
  I_NXTCSJC_SFRWBX IN NUMBER DEFAULT -1, --建议从客户端传入，提高效率，系统参数,是否多任务并行        -1需要检测 0参数值 1参数值
  I_NRWID          IN NUMBER DEFAULT -1,--是否指定任务单索取任务，-1表示不索取指定任务单
  I_NQR            IN NUMBER DEFAULT 0, --0索取，1确认
  O_NRWID          OUT VARCHAR2, --任务编号
  O_SRYMC          OUT VARCHAR2, --人员名称
  O_NRLT           OUT NUMBER, -- 0表示索取成功 1 索取任务确认单 2 索取失败
  O_SMSG           OUT VARCHAR2
) IS

  V_NBCID         NUMBER; --波次编码
  V_NRWFPID       NUMBER; --任务分配ID
  V_NRWID         NUMBER; --任务编号
  V_NRWLX         NUMBER; --任务类型
  V_NRWZT         NUMBER; --任务状态
  V_NFPYXJ        NUMBER; --任务分配优先级
  V_SFHTID        VARCHAR2(30); --暂存区ID
  V_SFHTBH        VARCHAR2(30); --复核台编号
  V_SRYMC         VARCHAR2(30);
  V_NSFCKRW       NUMBER; --是否出库任务(0入库 1出库)
  V_SSJC          VARCHAR2(30); --时间戳  返回任务单时间戳
  V_NRLT          NUMBER; --返回状态
  V_SMSG          VARCHAR2(200);  --返回错误信息

  V_PARA_SQLX       NUMBER := 0;    --任务索取类型参数   0不打包1定数量打包2定总量打包
  V_PARA_SQRWSL     NUMBER := 1;    --任务索取限制数量参数
  V_PARA_ZDRWSL     NUMBER := 10;   --最大任务数
  V_COUNT           NUMBER;
  V_NRWID_IN   NUMBER(18) :=0;

  V_SGSID   VARCHAR2(4); --公司编码
  V_SRYID   VARCHAR2(20); --人员ID

  INTEGRITY_ERROR EXCEPTION;
  ERRNO  INTEGER;
  ERRMSG VARCHAR2(200);
BEGIN
  /*
  功能：下架任务索取或者任务确认前索 (打包)
  注意：
  1)  个人任务根据索取方式参数和打包数量限制来完成多任务的索取。
  2) 出库索取过程,索取未分配任务单,分配内复核台,更新作业状态,更新任务单信息
  3) 入库索取过程,索取未分配任务单,             更新作业状态,更新任务单信息
  4) 目前不支持入库、出库统一索取。
  5)工号有效性检测，入库作业权限检测。

  待完善：
    多任务索取方式中的 定总量任务索取 的方式待完成。
  */

  --获取系统参数中的任务索取类型参数
    BEGIN
      SELECT F_GET_XTCS(84, I_SGSID) INTO V_PARA_SQLX FROM DUAL;
    EXCEPTION
      WHEN NO_DATA_FOUND THEN
        O_SMSG := '获取任务索取类型系统参数出错。';
        O_NRLT := 2;
        RETURN;
    END;
  --获取系统参数中的任务索取限制数量参数
    BEGIN
      SELECT F_GET_XTCS(85, I_SGSID) INTO V_PARA_SQRWSL FROM DUAL;
    EXCEPTION
      WHEN NO_DATA_FOUND THEN
        O_SMSG := '获取任务索取限制数量系统参数出错。';
        O_NRLT := 2;
        RETURN;
    END;

    BEGIN
      SELECT F_GET_XTCS(117, I_SGSID) INTO V_PARA_ZDRWSL FROM DUAL;
    EXCEPTION
      WHEN NO_DATA_FOUND THEN
        O_SMSG := '获取最大任务数系统参数117出错。';
        O_NRLT := 2;
        RETURN;
    END;

  O_NRLT  := 0;
  O_NRWID := '';
  V_SGSID := RTRIM(I_SGSID);
  V_SRYID := RTRIM(I_SRYID);
  V_NRWID_IN := NVL(I_NRWID, 0);



  IF i_nqr = 1 THEN
    -- 检测是否存在未完成任务，如果存在则返回一组任务单号。
    IF F_GET_WWCRWID_DB(V_SGSID,V_SRYID,I_NSFCKRW,O_NRWID) = 0 THEN
      O_SMSG := '已索取待确认任务单' || '['||O_NRWID||']' || ',请检查确认数量,进行确认操作！';
      O_NRLT := 1;
      RETURN;
    END IF;

  ELSIF I_NQR = 0 THEN
    SELECT COUNT(*) INTO V_COUNT
      FROM RYRWZT
     WHERE SGSID =V_SGSID AND SRYID = I_SRYID
       AND ((nrwlx in (0,1,2) AND nrwzt=1 ) OR (nrwlx in (3,4,5) and (nrwzt=1 OR nrwzt=3)))
       AND NSFCKRW=I_NSFCKRW;
    IF V_COUNT +V_PARA_SQRWSL > V_PARA_ZDRWSL THEN
      O_SMSG := '已索任务数大于最大可索取任务数';
      O_NRLT := 1;
      o_nrwid := '-1';
      RETURN;
    END IF;
  /* 1、索取新任务单号
  第一个任务，直接调用 EXEC_RWSQ 过程获取
  */

    EXEC_RWSQ(I_SGSID,I_SRYID,I_NSFCKRW,I_NXTCSJC_SFNFH,I_NXTCSJC_SFRWBX,V_NRWID_IN,
               V_NBCID,V_NRWFPID,V_NRWID,V_NRWLX,V_NRWZT,V_NFPYXJ,V_SFHTID,V_SFHTBH,V_SRYMC,V_NSFCKRW,V_SSJC,
               V_NRLT,V_SMSG,0,1) ;

    O_NRWID := V_NRWID;
    O_SRYMC := V_SRYMC;
    O_NRLT := V_NRLT;
    O_SMSG := V_SMSG;
    /*
    根据任务索取方式，进行多任务索取   0不多任务索取  1定数量打包索取  2定总量打包索取
    */
    if (V_NRWID_IN = -1) and (O_NRLT = 0) and (V_PARA_SQLX <> 0 ) then --非指定单号索取，并且第一次有索取成功，且多任务索取
      if V_PARA_SQLX = 1 then  -- 定数量打包
        while (V_PARA_SQRWSL - 1 >0 )
        loop
          EXEC_RWSQ(I_SGSID,I_SRYID,I_NSFCKRW,I_NXTCSJC_SFNFH,I_NXTCSJC_SFRWBX,V_NRWID_IN,
               V_NBCID,V_NRWFPID,V_NRWID,V_NRWLX,V_NRWZT,V_NFPYXJ,V_SFHTID,V_SFHTBH,V_SRYMC,V_NSFCKRW,V_SSJC,
               V_NRLT,V_SMSG,0,1) ;
          if V_NRLT = 0 then     --再次索取成功
            O_NRWID := O_NRWID ||';'||V_NRWID ;
            V_PARA_SQRWSL := V_PARA_SQRWSL - 1;
          else                   --索取失败（无任务）
            V_PARA_SQRWSL := 1;
          end if;
        end loop;
      elsif V_PARA_SQLX = 2 then  -- 定总量打包  暂时未完成该规则
        V_PARA_SQRWSL :=  1;
      end if;
    end if;
  END IF;

EXCEPTION
  WHEN INTEGRITY_ERROR THEN
    RAISE_APPLICATION_ERROR(ERRNO, ERRMSG);
END;
/
