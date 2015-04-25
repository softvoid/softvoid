CREATE OR REPLACE PROCEDURE EXEC_RWSQ_DB2
(
  I_SGSID          IN VARCHAR2, --��˾ID
  I_SRYID          IN VARCHAR2, --��ԱID
  I_NSFCKRW        IN NUMBER DEFAULT -1,--�Ƿ���ȡ����(0��� 1����) -1��ʾ��⣬����ͳһ��ȡ�� 0ֻ��ȡ��� 1ֻ��ȡ����
  I_NXTCSJC_SFNFH  IN NUMBER DEFAULT -1, --����ӿͻ��˴��룬���Ч�ʣ�ϵͳ����,�Ƿ���Ҫʹ���ڸ���̨��-1��Ҫ��� 0����ֵ 1����ֵ
  I_NXTCSJC_SFRWBX IN NUMBER DEFAULT -1, --����ӿͻ��˴��룬���Ч�ʣ�ϵͳ����,�Ƿ��������        -1��Ҫ��� 0����ֵ 1����ֵ
  I_NRWID          IN NUMBER DEFAULT -1,--�Ƿ�ָ��������ȡ����-1��ʾ����ȡָ������
  I_NQR            IN NUMBER DEFAULT 0, --0��ȡ��1ȷ��
  O_NRWID          OUT VARCHAR2, --������
  O_SRYMC          OUT VARCHAR2, --��Ա����
  O_NRLT           OUT NUMBER, -- 0��ʾ��ȡ�ɹ� 1 ��ȡ����ȷ�ϵ� 2 ��ȡʧ��
  O_SMSG           OUT VARCHAR2
) IS

  V_NBCID         NUMBER; --���α���
  V_NRWFPID       NUMBER; --�������ID
  V_NRWID         NUMBER; --������
  V_NRWLX         NUMBER; --��������
  V_NRWZT         NUMBER; --����״̬
  V_NFPYXJ        NUMBER; --����������ȼ�
  V_SFHTID        VARCHAR2(30); --�ݴ���ID
  V_SFHTBH        VARCHAR2(30); --����̨���
  V_SRYMC         VARCHAR2(30);
  V_NSFCKRW       NUMBER; --�Ƿ��������(0��� 1����)
  V_SSJC          VARCHAR2(30); --ʱ���  ��������ʱ���
  V_NRLT          NUMBER; --����״̬
  V_SMSG          VARCHAR2(200);  --���ش�����Ϣ

  V_PARA_SQLX       NUMBER := 0;    --������ȡ���Ͳ���   0�����1���������2���������
  V_PARA_SQRWSL     NUMBER := 1;    --������ȡ������������
  V_PARA_ZDRWSL     NUMBER := 10;   --���������
  V_COUNT           NUMBER;
  V_NRWID_IN   NUMBER(18) :=0;

  V_SGSID   VARCHAR2(4); --��˾����
  V_SRYID   VARCHAR2(20); --��ԱID

  INTEGRITY_ERROR EXCEPTION;
  ERRNO  INTEGER;
  ERRMSG VARCHAR2(200);
BEGIN
  /*
  ���ܣ��¼�������ȡ��������ȷ��ǰ�� (���)
  ע�⣺
  1)  �������������ȡ��ʽ�����ʹ��������������ɶ��������ȡ��
  2) ������ȡ����,��ȡδ��������,�����ڸ���̨,������ҵ״̬,����������Ϣ
  3) �����ȡ����,��ȡδ��������,             ������ҵ״̬,����������Ϣ
  4) Ŀǰ��֧����⡢����ͳһ��ȡ��
  5)������Ч�Լ�⣬�����ҵȨ�޼�⡣

  �����ƣ�
    ��������ȡ��ʽ�е� ������������ȡ �ķ�ʽ����ɡ�
  */

  --��ȡϵͳ�����е�������ȡ���Ͳ���
    BEGIN
      SELECT F_GET_XTCS(84, I_SGSID) INTO V_PARA_SQLX FROM DUAL;
    EXCEPTION
      WHEN NO_DATA_FOUND THEN
        O_SMSG := '��ȡ������ȡ����ϵͳ��������';
        O_NRLT := 2;
        RETURN;
    END;
  --��ȡϵͳ�����е�������ȡ������������
    BEGIN
      SELECT F_GET_XTCS(85, I_SGSID) INTO V_PARA_SQRWSL FROM DUAL;
    EXCEPTION
      WHEN NO_DATA_FOUND THEN
        O_SMSG := '��ȡ������ȡ��������ϵͳ��������';
        O_NRLT := 2;
        RETURN;
    END;

    BEGIN
      SELECT F_GET_XTCS(117, I_SGSID) INTO V_PARA_ZDRWSL FROM DUAL;
    EXCEPTION
      WHEN NO_DATA_FOUND THEN
        O_SMSG := '��ȡ���������ϵͳ����117����';
        O_NRLT := 2;
        RETURN;
    END;

  O_NRLT  := 0;
  O_NRWID := '';
  V_SGSID := RTRIM(I_SGSID);
  V_SRYID := RTRIM(I_SRYID);
  V_NRWID_IN := NVL(I_NRWID, 0);



  IF i_nqr = 1 THEN
    -- ����Ƿ����δ���������������򷵻�һ�����񵥺š�
    IF F_GET_WWCRWID_DB(V_SGSID,V_SRYID,I_NSFCKRW,O_NRWID) = 0 THEN
      O_SMSG := '����ȡ��ȷ������' || '['||O_NRWID||']' || ',����ȷ������,����ȷ�ϲ�����';
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
      O_SMSG := '��������������������ȡ������';
      O_NRLT := 1;
      o_nrwid := '-1';
      RETURN;
    END IF;
  /* 1����ȡ�����񵥺�
  ��һ������ֱ�ӵ��� EXEC_RWSQ ���̻�ȡ
  */

    EXEC_RWSQ(I_SGSID,I_SRYID,I_NSFCKRW,I_NXTCSJC_SFNFH,I_NXTCSJC_SFRWBX,V_NRWID_IN,
               V_NBCID,V_NRWFPID,V_NRWID,V_NRWLX,V_NRWZT,V_NFPYXJ,V_SFHTID,V_SFHTBH,V_SRYMC,V_NSFCKRW,V_SSJC,
               V_NRLT,V_SMSG,0,1) ;

    O_NRWID := V_NRWID;
    O_SRYMC := V_SRYMC;
    O_NRLT := V_NRLT;
    O_SMSG := V_SMSG;
    /*
    ����������ȡ��ʽ�����ж�������ȡ   0����������ȡ  1�����������ȡ  2�����������ȡ
    */
    if (V_NRWID_IN = -1) and (O_NRLT = 0) and (V_PARA_SQLX <> 0 ) then --��ָ��������ȡ�����ҵ�һ������ȡ�ɹ����Ҷ�������ȡ
      if V_PARA_SQLX = 1 then  -- ���������
        while (V_PARA_SQRWSL - 1 >0 )
        loop
          EXEC_RWSQ(I_SGSID,I_SRYID,I_NSFCKRW,I_NXTCSJC_SFNFH,I_NXTCSJC_SFRWBX,V_NRWID_IN,
               V_NBCID,V_NRWFPID,V_NRWID,V_NRWLX,V_NRWZT,V_NFPYXJ,V_SFHTID,V_SFHTBH,V_SRYMC,V_NSFCKRW,V_SSJC,
               V_NRLT,V_SMSG,0,1) ;
          if V_NRLT = 0 then     --�ٴ���ȡ�ɹ�
            O_NRWID := O_NRWID ||';'||V_NRWID ;
            V_PARA_SQRWSL := V_PARA_SQRWSL - 1;
          else                   --��ȡʧ�ܣ�������
            V_PARA_SQRWSL := 1;
          end if;
        end loop;
      elsif V_PARA_SQLX = 2 then  -- ���������  ��ʱδ��ɸù���
        V_PARA_SQRWSL :=  1;
      end if;
    end if;
  END IF;

EXCEPTION
  WHEN INTEGRITY_ERROR THEN
    RAISE_APPLICATION_ERROR(ERRNO, ERRMSG);
END;
/
