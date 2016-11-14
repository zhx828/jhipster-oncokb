package org.mskcc.cbio.oncokb.service.impl;

import org.mskcc.cbio.oncokb.service.CommentService;
import org.mskcc.cbio.oncokb.domain.Comment;
import org.mskcc.cbio.oncokb.repository.CommentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Comment.
 */
@Service
@Transactional
public class CommentServiceImpl implements CommentService{

    private final Logger log = LoggerFactory.getLogger(CommentServiceImpl.class);
    
    @Inject
    private CommentRepository commentRepository;

    /**
     * Save a comment.
     *
     * @param comment the entity to save
     * @return the persisted entity
     */
    public Comment save(Comment comment) {
        log.debug("Request to save Comment : {}", comment);
        Comment result = commentRepository.save(comment);
        return result;
    }

    /**
     *  Get all the comments.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Comment> findAll() {
        log.debug("Request to get all Comments");
        List<Comment> result = commentRepository.findAll();

        return result;
    }

    /**
     *  Get one comment by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Comment findOne(Long id) {
        log.debug("Request to get Comment : {}", id);
        Comment comment = commentRepository.findOne(id);
        return comment;
    }

    /**
     *  Delete the  comment by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Comment : {}", id);
        commentRepository.delete(id);
    }
}
