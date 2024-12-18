CREATE TABLE IF NOT EXISTS categories (
  category_id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  category_name VARCHAR(255) NOT NULL,
  CONSTRAINT uq_categories_name UNIQUE (category_name)
);

CREATE TABLE IF NOT EXISTS users (
  user_id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  user_name VARCHAR(255) NOT NULL,
  user_email VARCHAR(255) NOT NULL,
  CONSTRAINT uq_user_email UNIQUE (user_email)
);

CREATE TABLE IF NOT EXISTS events (
event_annotation text NOT NULL,
category_id BIGINT NOT NULL REFERENCES categories (category_id) ON DELETE RESTRICT,
event_created_on timestamp NOT NULL,
event_description text NOT NULL,
event_event_date timestamp NOT NULL,
event_id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
user_id BIGINT NOT NULL REFERENCES users (user_id) ON DELETE CASCADE,
event_location_lat real NOT NULL,
event_location_lon real NOT NULL,
event_paid BOOL NOT NULL,
event_participant_limit BIGINT NOT NULL,
event_published_on timestamp,
event_request_moderation BOOL NOT NULL,
event_state VARCHAR(255) NOT NULL,
event_title VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS requests (
request_id BIGINT GENERATED BY DEFAULT AS IDENTITY,
event_id BIGINT NOT NULL REFERENCES events (event_id) ON DELETE CASCADE,
user_id BIGINT NOT NULL REFERENCES users (user_id) ON DELETE CASCADE,
request_status VARCHAR(255) NOT NULL,
request_created timestamp NOT NULL,
CONSTRAINT "request_PK" PRIMARY KEY (user_id, event_id)
);

CREATE TABLE IF NOT EXISTS compilations (
compilation_id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
compilation_pinned BOOL NOT NULL,
compilation_title VARCHAR(255) NOT NULL,
CONSTRAINT uq_compilation_title UNIQUE (compilation_title)
);

CREATE TABLE IF NOT EXISTS compilations_events (
compilation_id BIGINT NOT NULL REFERENCES compilations (compilation_id) ON DELETE CASCADE,
event_id BIGINT REFERENCES events (event_id) ON DELETE set NULL
);
