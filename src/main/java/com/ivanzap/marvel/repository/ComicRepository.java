package com.ivanzap.marvel.repository;

import com.ivanzap.marvel.domain.tables.CharactersComics;
import com.ivanzap.marvel.domain.tables.Comics;
import com.ivanzap.marvel.model.Comic;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.SortOrder;
import org.jooq.exception.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class ComicRepository {

    private final DSLContext dsl;

    public ComicRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public Comic findById(int id) {
        return dsl.selectFrom(Comics.COMICS)
                .where(Comics.COMICS.ID.eq(id))
                .fetchAny()
                .into(Comic.class);
    }

    public List<Comic> findAll(Condition condition) {
        return dsl.selectFrom(Comics.COMICS)
                .where(condition)
                .fetch()
                .into(Comic.class);
    }

    public List<Comic> findAll(int offset, int limit, String direction, String sortField) {
        return dsl.selectFrom(Comics.COMICS)
                .orderBy(Comics.COMICS.field(sortField).sort(SortOrder.valueOf(direction)))
                .limit(limit)
                .offset(limit * offset)
                .fetch()
                .into(Comic.class);
    }

    public List<Comic> findByTitleContainingIgnoreCase(String title, int offset, int limit, String direction, String sortField) {
        return dsl.selectFrom(Comics.COMICS)
                .where(Comics.COMICS.TITLE.likeIgnoreCase("%" + title + "%"))
                .orderBy(Comics.COMICS.field(sortField).sort(SortOrder.valueOf(direction)))
                .limit(limit)
                .offset(limit * offset)
                .fetch()
                .into(Comic.class);
    }

    public List<Comic> findByCharacterId(int characterId, String title, int offset, int limit, String direction, String sortField) {
        return dsl.select()
                .from(Comics.COMICS)
                .join(CharactersComics.CHARACTERS_COMICS).on(Comics.COMICS.ID.eq(CharactersComics.CHARACTERS_COMICS.COMIC_ID))
                .where(CharactersComics.CHARACTERS_COMICS.CHARACTER_ID.eq(characterId)
                        .and(Comics.COMICS.TITLE.likeIgnoreCase("%" + title + "%")))
                .orderBy(Comics.COMICS.field(sortField).sort(SortOrder.valueOf(direction)))
                .limit(limit)
                .offset(limit * offset)
                .fetch()
                .into(Comic.class);
    }

    @Transactional
    public Comic insert(Comic comic) {
        return dsl.insertInto(Comics.COMICS)
                .set(dsl.newRecord(Comics.COMICS, comic))
                .returning()
                .fetchOptional()
                .orElseThrow(() -> new DataAccessException("Error inserting entity: " + comic.getId()))
                .into(Comic.class);
    }

    @Transactional
    public void update(Comic comic) {
        dsl.update(Comics.COMICS)
                .set(dsl.newRecord(Comics.COMICS, comic))
                .where(Comics.COMICS.ID.eq(comic.getId()))
                .returning()
                .fetchOptional()
                .orElseThrow(() -> new DataAccessException("Error updating entity: " + comic.getId()))
                .into(Comic.class);
    }

    public boolean deleteById(int id) {
        return dsl.deleteFrom(Comics.COMICS)
                .where(Comics.COMICS.ID.eq(id))
                .execute() == 1;
    }
}
