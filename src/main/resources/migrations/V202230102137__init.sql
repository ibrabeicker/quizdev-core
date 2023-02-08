create table question (
    id_question bigserial primary key,
    name text not null,
    source_code text not null,
    source_type text not null,
    type text not null,
    choices jsonb,
    code_answer jsonb,
--    metadata jsonb,
    enabled boolean default true,
    creation_date timestamp with time zone,
    constraint uk_question01 unique (name)
);

create table question_tag (
    id_question_tag serial primary key,
    name text not null,
    constraint uk_question_tag01 unique (name)
);

create table question_tag_relation (
    id_question_tag_relation bigserial primary key,
    id_question bigint references question on delete cascade,
    id_question_tag integer references question_tag on delete cascade,
    constraint uk_question_tag_relation01 unique (id_question, id_question_tag)
);

create index question_tag_relation_idx01 on question_tag_relation(id_question_tag);

create table submitted_answer (
    id_submitted_answer bigserial primary key,
    id_question bigint references question,
    id_student text not null,
    full_score boolean not null,
    choices jsonb,
    creation_date timestamp with time zone
);

create index submitted_answer_idx01 on submitted_answer(id_question);