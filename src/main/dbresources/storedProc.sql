CREATE OR REPLACE PACKAGE svc_catalog_pkg  AS 
  PROCEDURE svc_update_svc_consumer (servc_name IN  SVC_CAT_SERVICE.SVC_NAME%TYPE,
  servc_version IN  SVC_CAT_SERVICE_VERSION.VERSION_ID%TYPE,
  cons_name IN svc_cat_consumer.consumer_name%TYPE,
  cons_desc IN  SVC_CAT_CONSUMER.DESCRIPTION%TYPE,
  decomm_comments IN  varchar2,
  user_name IN  varchar2,
  
  servc_desc IN  svc_cat_service.description%TYPE,
  servc_provider IN  SVC_CAT_SERVICE.PROVIDER%TYPE,
  servc_type IN  SVC_CAT_SERVICE_VERSION.EVENT_TYPE%TYPE,
  servc_networkscope IN  SVC_CAT_SERVICE.NETWORK_SCOPE%TYPE,
  servc_oper IN  SVC_CAT_SERVICE.SVC_OPERATIONS%TYPE,
  
  ver_transport_type IN  SVC_CAT_SERVICE_VERSION.TRANSPORT_TYPE%TYPE,
  ver_canonicalData IN  SVC_CAT_SERVICE_VERSION.CANONICAL_DATA_MODEL%TYPE,
  ver_event_type  IN  SVC_CAT_SERVICE_VERSION.EVENT_TYPE%TYPE,
  dom_name IN  svc_cat_domain.domain_name%TYPE,
  
  servc_security IN  SVC_CAT_SERVICE_VERSION.SVC_SECURITY%TYPE,
  ver_comments IN  VARCHAR2, 
  ver_status_id IN  SVC_CAT_SERVICE_VERSION.status_id%TYPE,
  out_error_code OUT varchar2  ); 
  
END svc_catalog_pkg;


CREATE OR REPLACE PACKAGE BODY svc_catalog_pkg AS

procedure svc_update_svc_consumer(
  servc_name IN  SVC_CAT_SERVICE.SVC_NAME%TYPE,
  servc_version IN  SVC_CAT_SERVICE_VERSION.VERSION_ID%TYPE,
  cons_name IN svc_cat_consumer.consumer_name%TYPE,
  cons_desc IN  SVC_CAT_CONSUMER.DESCRIPTION%TYPE,
  decomm_comments IN  varchar2,
  user_name IN  varchar2,
  
  servc_desc IN  svc_cat_service.description%TYPE,
  servc_provider IN  SVC_CAT_SERVICE.PROVIDER%TYPE,
  servc_type IN  SVC_CAT_SERVICE_VERSION.EVENT_TYPE%TYPE,
  servc_networkscope IN  SVC_CAT_SERVICE.NETWORK_SCOPE%TYPE,
  servc_oper IN  SVC_CAT_SERVICE.SVC_OPERATIONS%TYPE,
  
  ver_transport_type IN  SVC_CAT_SERVICE_VERSION.TRANSPORT_TYPE%TYPE,
  ver_canonicalData IN  SVC_CAT_SERVICE_VERSION.CANONICAL_DATA_MODEL%TYPE,
  ver_event_type  IN  SVC_CAT_SERVICE_VERSION.EVENT_TYPE%TYPE,
  dom_name IN  SVC_CAT_DOMAIN.DOMAIN_NAME%TYPE,
  servc_security IN  SVC_CAT_SERVICE_VERSION.SVC_SECURITY%TYPE,
  ver_comments IN  varchar2, 
  ver_status_id IN  svc_cat_service_version.status_id%TYPE,
  
  out_error_code OUT varchar2  
  )
  AS
  log varchar2(1000);
  err_code varchar2(100);
  err_msg varchar2(256);
  consr_id SVC_CAT_CONSUMER.CONSUMER_ID%TYPE;
  servc_id SVC_CAT_SERVICE.SVC_ID%TYPE;
  dom_id SVC_CAT_DOMAIN.DOMAIN_ID%TYPE;
  servc_count NUMBER;
  servc_ver_id SVC_CAT_SERVICE_VERSION.VERSION_ID%TYPE;
  BEGIN
    --Log Parameters
    log := servc_name || ' ' || servc_version || ' ' || cons_name || ' ' || user_name;
    INSERT INTO svc_logs( APPLICATION,LOG_LEVEL, LOG_LINE, log_text, create_user, create_date) VALUES ('ServiceCatalog', 'INFO', '1.1', log, user_name, sysdate);
    
    out_error_code := '00';
    --FIND DOMAIN
    BEGIN 
     SELECT DOMAIN_ID INTO dom_id FROM SVC_CAT_DOMAIN WHERE UPPER(DOMAIN_NAME) = UPPER(dom_name); 
     
      EXCEPTION  
      WHEN NO_DATA_FOUND THEN
        out_error_code:= '01';--Missing domain
        -- Create Domain
        select COALESCE(max(DOMAIN_ID)+1,1) into dom_id from SVC_CAT_DOMAIN;
        log:= 'Creating Domain '||dom_id;
        INSERT INTO svc_logs( APPLICATION,LOG_LEVEL, LOG_LINE, log_text, create_user, create_date) VALUES 
        ('ServiceCatalog', 'INFO', '1.2.1', log, user_name, sysdate);
        
        INSERT INTO SVC_CAT_DOMAIN (DOMAIN_ID,DOMAIN_NAME,DESCRIPTION,CREATE_USER,CREATE_DATE)
        values (dom_id, dom_name ,dom_name, user_name, sysdate);
        
      END;
    -- FIND SERVICE
    BEGIN 
      SELECT SVC_ID INTO servc_id FROM SVC_CAT_SERVICE WHERE UPPER(SVC_NAME) = UPPER(servc_name); 
      
      EXCEPTION  
      WHEN NO_DATA_FOUND THEN
        out_error_code:= '02';--Missing service
        -- Create Service
        select COALESCE(MAX(svc_id)+1,1) into servc_id from SVC_CAT_SERVICE;
        log:= 'Creating Service and Version'||servc_id;
        INSERT INTO svc_logs( APPLICATION,LOG_LEVEL, LOG_LINE, log_text, create_user, create_date) VALUES ('ServiceCatalog', 'INFO', '1.2.2', log, user_name, sysdate);
        
        Insert into SVC_CAT_SERVICE (SVC_ID,DOMAIN_ID,SVC_NAME,DESCRIPTION,NETWORK_SCOPE,SVC_OPERATIONS,PROVIDER, CREATE_USER,CREATE_DATE) values
        (servc_id,dom_id,servc_name,servc_desc, servc_networkscope, servc_oper, servc_provider , user_name, sysdate );
		
        --Create Service Version
        INSERT INTO svc_cat_service_version (svc_id, version_id, status_id,  transport_type, canonical_data_model, event_type,svc_security,additional_comments, create_user, create_date) VALUES
        (servc_id, servc_version , ver_status_id, ver_transport_type, ver_canonicalData, ver_event_type,servc_security,ver_comments, user_name, sysdate);
    END;

    -- FIND SERVICE_VERSION
    BEGIN 
      SELECT VERSION_ID INTO servc_ver_id FROM SVC_CAT_SERVICE_VERSION WHERE SVC_ID = servc_id and VERSION_ID = servc_version; 
      
      EXCEPTION  
      WHEN NO_DATA_FOUND THEN
        out_error_code:= '03';--Missing service Version
        log:= 'Creating Service Version :'||servc_version;
        INSERT INTO svc_logs( APPLICATION,LOG_LEVEL, LOG_LINE, log_text, create_user, create_date) VALUES ('ServiceCatalog', 'INFO', '1.3.1', log, user_name, sysdate);
        
        INSERT INTO svc_cat_service_version (svc_id, version_id, status_id,  transport_type, canonical_data_model, event_type,svc_security,additional_comments, create_user, create_date) VALUES
    		(servc_id, servc_version , ver_status_id, ver_transport_type, ver_canonicalData, ver_event_type,servc_security,ver_comments, user_name, sysdate);
    END;
	  
    -- find Consumer
    BEGIN 
      SELECT CONSUMER_ID INTO consr_id FROM SVC_CAT_CONSUMER WHERE UPPER(CONSUMER_NAME) = UPPER(cons_name); 
      

      EXCEPTION  
      WHEN NO_DATA_FOUND THEN
        out_error_code:= '05';--Missing Consumer
        -- Create Consumer
        select COALESCE(MAX(CONSUMER_ID)+1,1) into consr_id from svc_cat_consumer;
        log:= 'Creating CONSUMER '||consr_id;
        INSERT INTO svc_logs( APPLICATION,LOG_LEVEL, LOG_LINE, log_text, create_user, create_date) VALUES 
        ('ServiceCatalog', 'INFO', '1.4.1', log, user_name, sysdate);
		
        Insert into svc_cat_consumer(CONSUMER_ID, CONSUMER_NAME, DESCRIPTION, CREATE_USER, CREATE_DATE) VALUES
        (consr_id, cons_name, cons_desc, user_name, sysdate);
    END;
    
    -- UPDATE SERVICE_VERSION_CONSR
    UPDATE SVC_CAT_SERVICE_CONSUMER SET SERVICE_ACTIVE = 'F' , DECOMISSION_COMMENTS= decomm_comments, update_user = user_name,
    update_date = sysdate  WHERE SVC_ID = servc_id and CONSUMER_ID = consr_id;
      
    -- FIND SERVICE_VERSION_CONSR
    BEGIN 
      
      SELECT SVC_ID INTO servc_id FROM SVC_CAT_SERVICE_CONSUMER WHERE SVC_ID = servc_id and VERSION_ID = servc_version and CONSUMER_ID = consr_id; 
      UPDATE SVC_CAT_SERVICE_CONSUMER SET SERVICE_ACTIVE = 'Y', DECOMISSION_COMMENTS='' WHERE SVC_ID = servc_id and VERSION_ID = servc_version and CONSUMER_ID = consr_id;
      
      EXCEPTION
      WHEN NO_DATA_FOUND THEN
        INSERT INTO svc_logs( APPLICATION,LOG_LEVEL, LOG_LINE, log_text, create_user, create_date) VALUES 
        ('ServiceCatalog', 'INFO', '1.5', 'Inserting record to DB', user_name, sysdate );
        
        INSERT INTO SVC_CAT_SERVICE_CONSUMER (SVC_ID, VERSION_ID, CONSUMER_ID, SERVICE_ACTIVE, CREATE_USER, CREATE_DATE) VALUES
        (servc_id, servc_version, consr_id, 'Y', user_name, sysdate);
    END;
    
  EXCEPTION  
  WHEN OTHERS THEN
    out_error_code:= '09';--ERROR
    err_code  := SQLCODE;
    err_msg  := SUBSTR(SQLERRM, 1, 255);
    INSERT INTO svc_logs( APPLICATION,LOG_LEVEL, LOG_LINE, log_text, create_user, create_date) VALUES ('ServiceCatalog', 'ERROR', err_code , err_msg , user_name, sysdate );
END svc_update_svc_consumer;
END svc_catalog_pkg;

