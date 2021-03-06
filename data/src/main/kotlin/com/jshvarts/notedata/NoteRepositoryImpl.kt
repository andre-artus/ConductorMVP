package com.jshvarts.notedata

import com.jshvarts.notedomain.model.Note
import com.jshvarts.notedomain.repository.NoteRepository
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

/**
 * Room implementation of {@link NoteRepository}.
 */
class NoteRepositoryImpl(private val noteDao: NoteDaoImpl,
                         private val mapper: NoteModelMapperImpl) : NoteRepository {

    override fun insertOrUpdate(note: Note): Completable = Completable.fromAction { noteDao.insertOrUpdate(mapper.toEntity(note)) }

    override fun delete(note: Note): Completable = Completable.fromAction { noteDao.delete(mapper.toEntity(note)) }

    override fun findNoteById(id: Long): Maybe<Note> {
        return noteDao.findNoteById(id)
                .map { mapper.fromEntity(it) }
    }

    override fun getAllNotes(): Single<List<Note>> {
        return noteDao.getAllNotes()
                .map { it.map(mapper::fromEntity) }
    }
}