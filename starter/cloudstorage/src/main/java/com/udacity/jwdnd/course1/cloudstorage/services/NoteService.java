package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteMapper noteMapper;
    private UserService userService;

    public final Logger logger = LoggerFactory.getLogger(HashService.class);

    public NoteService(NoteMapper noteMapper, UserService userService) {
        this.noteMapper = noteMapper;
        this.userService = userService;
    }

    public Integer insertNote(Note note) {
        return noteMapper.insertNote(note);
    }

    public Integer updateNote(Note note) {
        return noteMapper.updateNote(note);
    }

    public List<Note> getNotesByUserId(Integer userId) {
        return noteMapper.getNotesForUser(userId);
    }

    public List<Note> getNoteListByLoggedInUserId() {
        return getNotesByUserId(userService.getLoggedInUserId());
    }

    public boolean deleteNote(Integer noteId) {
        return noteMapper.deleteNote(noteId);
    }

}
