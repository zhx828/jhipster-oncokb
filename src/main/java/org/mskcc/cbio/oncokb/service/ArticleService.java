package org.mskcc.cbio.oncokb.service;

import org.mskcc.cbio.oncokb.domain.Article;

import java.util.List;

/**
 * Service Interface for managing Article.
 */
public interface ArticleService {

    /**
     * Save a article.
     *
     * @param article the entity to save
     * @return the persisted entity
     */
    Article save(Article article);

    /**
     *  Get all the articles.
     *  
     *  @return the list of entities
     */
    List<Article> findAll();

    /**
     *  Get the "id" article.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Article findOne(Long id);

    /**
     *  Delete the "id" article.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
