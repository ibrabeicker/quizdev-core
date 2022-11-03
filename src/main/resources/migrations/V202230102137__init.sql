create table question (
    id_question bigserial primary key,
    name text not null,
    text text not null,
    type text not null,
    choice_answers jsonb,
    code_answer jsonb,
    enabled boolean default true,
    creation_date timestamp with time zone,
    constraint uk_name unique (name)
);
