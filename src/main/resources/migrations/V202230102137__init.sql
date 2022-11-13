create table question (
    id_question bigserial primary key,
    name text not null,
    text text not null,
    type text not null,
    choice_answers jsonb,
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

create index question_tag_reglation_idx01 on question_tag_relation(id_question_tag);