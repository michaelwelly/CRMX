-- Insert sample data into Reporter table
insert into
  reporter (
    name_str,
    phone_str,
    description_text,
    attributes_json
  )
select
  'Reporter ' || i,
  '123-456-789' || i,
  'Description for reporter ' || i,
  '{}'::json
from
  generate_series(1, 10) as s (i);

-- Insert sample data into Users table
insert into
  users (
    name_str,
    email_str,
    user_name_str,
    user_status,
    user_type,
    access_mask,
    settings
  )
select
  'Пользователь ' || i,
  'user' || i || '@example.com',
  'username' || i,
  'ON_WORK',
  'OPERATOR',
  ('{"mask": "' || i || '"}')::jsonb,
  '{}'::json
from
  generate_series(1, 10) as s (i);

-- Insert sample data into Filial table
insert into
  filial (
    locations,
    title_str,
    description_text,
    attributes_json
  )
select
  ('{"положение": "' || 'Местоположение ' || i || '"}')::jsonb,
  'Название ' || i,
  'Описание филиала ' || i,
  '{}'::json
from
  generate_series(1, 10) as s (i);

-- Insert sample data into Worker table
insert into
  worker (
    name_str,
    contacts_json,
    worker_type,
    collection_id
  )
select
  'Worker ' || i,
  '{}'::json,
  'SPECIALIST',
  i + 10  -- Увеличиваем значение для уникальности
from
  generate_series(1, 10) as s (i);

-- Insert sample data into Field_Service_Team table
insert into
  field_service_team (
    workers,
    name_str,
    attributes_json,
    create_date_dttm,
    is_active_flag
  )
select
  collection_id,
  'Team ' || collection_id,
  '{}'::json,
  now(),
  true
from
  worker;

-- Insert sample data into Media table
insert into
  media (attributes_json, collection_id)
select
  '{}'::json,
  id + 100  -- Увеличиваем значение для уникальности
from
  worker;

-- Insert sample data into Task table
insert into
  task (
    worker_id,
    title_str,
    order_num,
    attributes_json,
    is_complete_flag,
    medias
  )
select
  id,
  'Task ' || id,
  id,
  '{}'::json,
  false,
  id
from
  worker;

-- Insert sample data into Tasks_Template table
insert into
  tasks_template (
    incident_type,
    title_str,
    order_num,
    attributes_json
  )
select
  'NO_POWER',
  'Template ' || i,
  i,
  '{}'::json
from
  generate_series(1, 10) as s (i);

-- Insert sample data into Map table
insert into
  map (
    title_str,
    description_txt,
    attributes_json,
    map_type
  )
select
  'Map ' || i,
  'Description for map ' || i,
  '{}'::json,
  'PRIVATE_HOUSE'
from
  generate_series(1, 10) as s (i);

-- Insert sample data into Incident table
insert into
  incident (
    reporter_id,
    operator_id,
    incident_type,
    registration_dttm,
    location_type,
    incident_status,
    description_text,
    filial_id,
    address_str,
    attributes_json,
    team_id,
    tasks_id
  )
select
  r.id,
  u.id,
  'NO_POWER',
  now(),
  'PRIVATE_HOUSE',
  'REQUEST',
  'Description for incident ' || r.id,
  f.id,
  'Address ' || r.id,
  '{}'::json,
  t.id,
  t.id
from
  generate_series(1, 10) as s (i)
  join reporter r on r.id = i
  join users u on u.id = i
  join filial f on f.id = i
  join task t on t.id = i;