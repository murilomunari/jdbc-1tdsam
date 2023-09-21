create sequence SQ_CLIENTE;
create or replace trigger TG_SQ_CLIENTE
    before insert or update on CLIENTE
        for each row begin
            if inserting and :new.ID_CLIENTE is null then
                :new.ID_CLIENTE := SQ_CLIENTE.nextval;
            end if;
        end;