alter table reporter add description_txt TEXT;

alter table tasks_template drop COLUMN incident_type;

alter table tasks_template drop COLUMN attributes_json;

alter table tasks_template drop COLUMN subtasks_json;

alter table users drop COLUMN access_mask;

alter table users drop COLUMN settings;

alter table users drop COLUMN user_status;

alter table users drop COLUMN user_type;

alter table users add access_mask JSONB NOT NULL;

alter table users alter COLUMN  access_mask SET NOT NULL;

alter table incident drop COLUMN address_json;

alter table incident drop COLUMN attributes_json;

alter table incident drop COLUMN incident_status;

alter table incident drop COLUMN incident_type;

alter table incident drop COLUMN location_type;

alter table incident drop COLUMN tasks_id;

alter table incident add address_json JSONB NOT NULL;

alter table incident alter COLUMN  address_json SET NOT NULL;

alter table field_service_team drop COLUMN attributes_json;

alter table field_service_team add attributes_json JSONB;

alter table filial drop COLUMN attributes_json;

alter table filial drop COLUMN locations;

alter table filial add attributes_json JSONB;

alter table incident add attributes_json JSONB NOT NULL;

alter table incident alter COLUMN  attributes_json SET NOT NULL;

alter table map drop COLUMN attributes_json;

alter table map add attributes_json JSONB;

alter table media drop COLUMN attributes_json;

alter table media add attributes_json JSONB;

alter table reporter drop COLUMN attributes_json;

alter table reporter add attributes_json JSONB;

alter table task drop COLUMN attributes_json;

alter table task drop COLUMN incident_id;

alter table task drop COLUMN subtasks_json;

alter table task add attributes_json JSONB;

alter table tasks_template add attributes_json JSONB;

alter table worker drop COLUMN contacts_json;

alter table worker add contacts_json JSONB;

alter table users alter COLUMN email_str TYPE VARCHAR(255) USING (email_str::VARCHAR(255));

alter table users alter COLUMN  email_str SET NOT NULL;

alter table incident alter COLUMN  exec_dttm SET NOT NULL;

alter table incident alter COLUMN  filial_id SET NOT NULL;

alter table task add incident_id JSONB;

alter table incident add incident_status VARCHAR(255) NOT NULL;

alter table incident alter COLUMN  incident_status SET NOT NULL;

alter table incident add incident_type VARCHAR(255) NOT NULL;

alter table incident alter COLUMN  incident_type SET NOT NULL;

alter table incident add location_type VARCHAR(255) NOT NULL;

alter table incident alter COLUMN  location_type SET NOT NULL;

alter table filial add locations JSONB NOT NULL;

alter table filial alter COLUMN  locations SET NOT NULL;

alter table reporter alter COLUMN name_str TYPE VARCHAR(100) USING (name_str::VARCHAR(100));

alter table users alter COLUMN name_str TYPE VARCHAR(255) USING (name_str::VARCHAR(255));

alter table users alter COLUMN  name_str SET NOT NULL;

alter table reporter alter COLUMN phone_str TYPE VARCHAR(100) USING (phone_str::VARCHAR(100));

alter table reporter alter COLUMN  phone_str SET NOT NULL;

alter table users add settings JSONB NOT NULL;

alter table users alter COLUMN  settings SET NOT NULL;

alter table task add subtasks_json JSONB;

alter table tasks_template add subtasks_json JSONB;

alter table incident add tasks_id BIGINT;


alter table incident add CONSTRAINT FK_INCIDENT_ON_TASKS FOREIGN KEY (tasks_id) REFERENCES task (id);

alter table filial alter COLUMN title_str TYPE VARCHAR(255) USING (title_str::VARCHAR(255));

alter table filial alter COLUMN  title_str SET NOT NULL;

alter table users alter COLUMN user_name_str TYPE VARCHAR(255) USING (user_name_str::VARCHAR(255));

alter table users add user_status VARCHAR(255) NOT NULL;

alter table users alter COLUMN  user_status SET NOT NULL;

alter table users add user_type VARCHAR(255) NOT NULL;

alter table users alter COLUMN  user_type SET NOT NULL;

alter table worker alter COLUMN  worker_type SET NOT NULL;