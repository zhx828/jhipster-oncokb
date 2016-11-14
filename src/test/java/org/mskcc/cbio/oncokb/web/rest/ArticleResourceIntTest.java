package org.mskcc.cbio.oncokb.web.rest;

import org.mskcc.cbio.oncokb.OncokbApp;

import org.mskcc.cbio.oncokb.domain.Article;
import org.mskcc.cbio.oncokb.repository.ArticleRepository;
import org.mskcc.cbio.oncokb.service.ArticleService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ArticleResource REST controller.
 *
 * @see ArticleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OncokbApp.class)
public class ArticleResourceIntTest {

    private static final String DEFAULT_PMID = "AAAAA";
    private static final String UPDATED_PMID = "BBBBB";
    private static final String DEFAULT_TITLE = "AAAAA";
    private static final String UPDATED_TITLE = "BBBBB";
    private static final String DEFAULT_JOURNAL = "AAAAA";
    private static final String UPDATED_JOURNAL = "BBBBB";
    private static final String DEFAULT_PUB_DATE = "AAAAA";
    private static final String UPDATED_PUB_DATE = "BBBBB";
    private static final String DEFAULT_VOLUME = "AAAAA";
    private static final String UPDATED_VOLUME = "BBBBB";
    private static final String DEFAULT_ISSUE = "AAAAA";
    private static final String UPDATED_ISSUE = "BBBBB";
    private static final String DEFAULT_PAGES = "AAAAA";
    private static final String UPDATED_PAGES = "BBBBB";
    private static final String DEFAULT_AUTHORS = "AAAAA";
    private static final String UPDATED_AUTHORS = "BBBBB";
    private static final String DEFAULT_ELOCATION_ID = "AAAAA";
    private static final String UPDATED_ELOCATION_ID = "BBBBB";
    private static final String DEFAULT_ABSTRACT_CONTENT = "AAAAA";
    private static final String UPDATED_ABSTRACT_CONTENT = "BBBBB";
    private static final String DEFAULT_LINK = "AAAAA";
    private static final String UPDATED_LINK = "BBBBB";

    @Inject
    private ArticleRepository articleRepository;

    @Inject
    private ArticleService articleService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restArticleMockMvc;

    private Article article;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ArticleResource articleResource = new ArticleResource();
        ReflectionTestUtils.setField(articleResource, "articleService", articleService);
        this.restArticleMockMvc = MockMvcBuilders.standaloneSetup(articleResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Article createEntity(EntityManager em) {
        Article article = new Article()
                .pmid(DEFAULT_PMID)
                .title(DEFAULT_TITLE)
                .journal(DEFAULT_JOURNAL)
                .pubDate(DEFAULT_PUB_DATE)
                .volume(DEFAULT_VOLUME)
                .issue(DEFAULT_ISSUE)
                .pages(DEFAULT_PAGES)
                .authors(DEFAULT_AUTHORS)
                .elocationId(DEFAULT_ELOCATION_ID)
                .abstractContent(DEFAULT_ABSTRACT_CONTENT)
                .link(DEFAULT_LINK);
        return article;
    }

    @Before
    public void initTest() {
        article = createEntity(em);
    }

    @Test
    @Transactional
    public void createArticle() throws Exception {
        int databaseSizeBeforeCreate = articleRepository.findAll().size();

        // Create the Article

        restArticleMockMvc.perform(post("/api/articles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(article)))
                .andExpect(status().isCreated());

        // Validate the Article in the database
        List<Article> articles = articleRepository.findAll();
        assertThat(articles).hasSize(databaseSizeBeforeCreate + 1);
        Article testArticle = articles.get(articles.size() - 1);
        assertThat(testArticle.getPmid()).isEqualTo(DEFAULT_PMID);
        assertThat(testArticle.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testArticle.getJournal()).isEqualTo(DEFAULT_JOURNAL);
        assertThat(testArticle.getPubDate()).isEqualTo(DEFAULT_PUB_DATE);
        assertThat(testArticle.getVolume()).isEqualTo(DEFAULT_VOLUME);
        assertThat(testArticle.getIssue()).isEqualTo(DEFAULT_ISSUE);
        assertThat(testArticle.getPages()).isEqualTo(DEFAULT_PAGES);
        assertThat(testArticle.getAuthors()).isEqualTo(DEFAULT_AUTHORS);
        assertThat(testArticle.getElocationId()).isEqualTo(DEFAULT_ELOCATION_ID);
        assertThat(testArticle.getAbstractContent()).isEqualTo(DEFAULT_ABSTRACT_CONTENT);
        assertThat(testArticle.getLink()).isEqualTo(DEFAULT_LINK);
    }

    @Test
    @Transactional
    public void getAllArticles() throws Exception {
        // Initialize the database
        articleRepository.saveAndFlush(article);

        // Get all the articles
        restArticleMockMvc.perform(get("/api/articles?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(article.getId().intValue())))
                .andExpect(jsonPath("$.[*].pmid").value(hasItem(DEFAULT_PMID.toString())))
                .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
                .andExpect(jsonPath("$.[*].journal").value(hasItem(DEFAULT_JOURNAL.toString())))
                .andExpect(jsonPath("$.[*].pubDate").value(hasItem(DEFAULT_PUB_DATE.toString())))
                .andExpect(jsonPath("$.[*].volume").value(hasItem(DEFAULT_VOLUME.toString())))
                .andExpect(jsonPath("$.[*].issue").value(hasItem(DEFAULT_ISSUE.toString())))
                .andExpect(jsonPath("$.[*].pages").value(hasItem(DEFAULT_PAGES.toString())))
                .andExpect(jsonPath("$.[*].authors").value(hasItem(DEFAULT_AUTHORS.toString())))
                .andExpect(jsonPath("$.[*].elocationId").value(hasItem(DEFAULT_ELOCATION_ID.toString())))
                .andExpect(jsonPath("$.[*].abstractContent").value(hasItem(DEFAULT_ABSTRACT_CONTENT.toString())))
                .andExpect(jsonPath("$.[*].link").value(hasItem(DEFAULT_LINK.toString())));
    }

    @Test
    @Transactional
    public void getArticle() throws Exception {
        // Initialize the database
        articleRepository.saveAndFlush(article);

        // Get the article
        restArticleMockMvc.perform(get("/api/articles/{id}", article.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(article.getId().intValue()))
            .andExpect(jsonPath("$.pmid").value(DEFAULT_PMID.toString()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.journal").value(DEFAULT_JOURNAL.toString()))
            .andExpect(jsonPath("$.pubDate").value(DEFAULT_PUB_DATE.toString()))
            .andExpect(jsonPath("$.volume").value(DEFAULT_VOLUME.toString()))
            .andExpect(jsonPath("$.issue").value(DEFAULT_ISSUE.toString()))
            .andExpect(jsonPath("$.pages").value(DEFAULT_PAGES.toString()))
            .andExpect(jsonPath("$.authors").value(DEFAULT_AUTHORS.toString()))
            .andExpect(jsonPath("$.elocationId").value(DEFAULT_ELOCATION_ID.toString()))
            .andExpect(jsonPath("$.abstractContent").value(DEFAULT_ABSTRACT_CONTENT.toString()))
            .andExpect(jsonPath("$.link").value(DEFAULT_LINK.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingArticle() throws Exception {
        // Get the article
        restArticleMockMvc.perform(get("/api/articles/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateArticle() throws Exception {
        // Initialize the database
        articleService.save(article);

        int databaseSizeBeforeUpdate = articleRepository.findAll().size();

        // Update the article
        Article updatedArticle = articleRepository.findOne(article.getId());
        updatedArticle
                .pmid(UPDATED_PMID)
                .title(UPDATED_TITLE)
                .journal(UPDATED_JOURNAL)
                .pubDate(UPDATED_PUB_DATE)
                .volume(UPDATED_VOLUME)
                .issue(UPDATED_ISSUE)
                .pages(UPDATED_PAGES)
                .authors(UPDATED_AUTHORS)
                .elocationId(UPDATED_ELOCATION_ID)
                .abstractContent(UPDATED_ABSTRACT_CONTENT)
                .link(UPDATED_LINK);

        restArticleMockMvc.perform(put("/api/articles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedArticle)))
                .andExpect(status().isOk());

        // Validate the Article in the database
        List<Article> articles = articleRepository.findAll();
        assertThat(articles).hasSize(databaseSizeBeforeUpdate);
        Article testArticle = articles.get(articles.size() - 1);
        assertThat(testArticle.getPmid()).isEqualTo(UPDATED_PMID);
        assertThat(testArticle.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testArticle.getJournal()).isEqualTo(UPDATED_JOURNAL);
        assertThat(testArticle.getPubDate()).isEqualTo(UPDATED_PUB_DATE);
        assertThat(testArticle.getVolume()).isEqualTo(UPDATED_VOLUME);
        assertThat(testArticle.getIssue()).isEqualTo(UPDATED_ISSUE);
        assertThat(testArticle.getPages()).isEqualTo(UPDATED_PAGES);
        assertThat(testArticle.getAuthors()).isEqualTo(UPDATED_AUTHORS);
        assertThat(testArticle.getElocationId()).isEqualTo(UPDATED_ELOCATION_ID);
        assertThat(testArticle.getAbstractContent()).isEqualTo(UPDATED_ABSTRACT_CONTENT);
        assertThat(testArticle.getLink()).isEqualTo(UPDATED_LINK);
    }

    @Test
    @Transactional
    public void deleteArticle() throws Exception {
        // Initialize the database
        articleService.save(article);

        int databaseSizeBeforeDelete = articleRepository.findAll().size();

        // Get the article
        restArticleMockMvc.perform(delete("/api/articles/{id}", article.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Article> articles = articleRepository.findAll();
        assertThat(articles).hasSize(databaseSizeBeforeDelete - 1);
    }
}
