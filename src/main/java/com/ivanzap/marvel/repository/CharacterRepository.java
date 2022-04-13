package com.ivanzap.marvel.repository;

import com.ivanzap.marvel.domain.tables.Characters;
import com.ivanzap.marvel.domain.tables.CharactersComics;
import com.ivanzap.marvel.model.Character;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.SortOrder;
import org.jooq.exception.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public class CharacterRepository {

    private final DSLContext dsl;

    public CharacterRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public Character findById(int id) {
        return dsl.selectFrom(Characters.CHARACTERS)
                .where(Characters.CHARACTERS.ID.eq(id))
                .fetchAny()
                .into(Character.class);
    }

    public List<Character> findAll(Condition condition) {
        return dsl.selectFrom(Characters.CHARACTERS)
                .where(condition)
                .fetch()
                .into(Character.class);
    }

    public List<Character> findAll(int offset, int limit, String direction, String sortField) {
        return dsl.selectFrom(Characters.CHARACTERS)
                .orderBy(Characters.CHARACTERS.field(sortField).sort(SortOrder.valueOf(direction)))
                .limit(limit)
                .offset(limit * offset)
                .fetch()
                .into(Character.class);
    }

    public List<Character> findByNameContainingIgnoreCase(String name, int offset, int limit, String direction, String sortField) {
        return dsl.selectFrom(Characters.CHARACTERS)
                .where(Characters.CHARACTERS.NAME.likeIgnoreCase("%" + name + "%"))
                .orderBy(Characters.CHARACTERS.field(sortField).sort(SortOrder.valueOf(direction)))
                .limit(limit)
                .offset(limit * offset)
                .fetch()
                .into(Character.class);
    }

    public List<Character> findByComicId(int comicId, String name, int offset, int limit, String direction, String sortField) {
        return dsl.select()
                .from(Characters.CHARACTERS)
                .join(CharactersComics.CHARACTERS_COMICS)
                .on(Characters.CHARACTERS.ID.eq(CharactersComics.CHARACTERS_COMICS.CHARACTER_ID))
                .where(CharactersComics.CHARACTERS_COMICS.COMIC_ID.eq(comicId)
                        .and(Characters.CHARACTERS.NAME.likeIgnoreCase("%" + name + "%")))
                .orderBy(Characters.CHARACTERS.field(sortField).sort(SortOrder.valueOf(direction)))
                .limit(limit)
                .offset(limit * offset)
                .fetch()
                .into(Character.class);
    }

    @Transactional
    public Character insert(Character character) {
        return dsl.insertInto(Characters.CHARACTERS)
                .set(dsl.newRecord(Characters.CHARACTERS, character))
                .returning()
                .fetchOptional()
                .orElseThrow(() -> new DataAccessException("Error inserting entity: " + character.getId()))
                .into(Character.class);
    }

    @Transactional
    public void update(Character character) {
        dsl.update(Characters.CHARACTERS)
                .set(dsl.newRecord(Characters.CHARACTERS, character))
                .where(Characters.CHARACTERS.ID.eq(character.getId()))
                .returning()
                .fetchOptional()
                .orElseThrow(() -> new DataAccessException("Error updating entity: " + character.getId()))
                .into(Character.class);
    }


    public boolean deleteById(int id) {
        return dsl.deleteFrom(Characters.CHARACTERS)
                .where(Characters.CHARACTERS.ID.eq(id))
                .execute() == 1;
    }
}
