package org.mskcc.cbio.oncokb.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Article.
 */
@Entity
@Table(name = "article")
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "pmid")
    private String pmid;

    @Column(name = "title")
    private String title;

    @Column(name = "journal")
    private String journal;

    @Column(name = "pub_date")
    private String pubDate;

    @Column(name = "volume")
    private String volume;

    @Column(name = "issue")
    private String issue;

    @Column(name = "pages")
    private String pages;

    @Column(name = "authors")
    private String authors;

    @Column(name = "elocation_id")
    private String elocationId;

    @Column(name = "abstract_content")
    private String abstractContent;

    @Column(name = "link")
    private String link;

    @OneToMany(mappedBy = "article")
    @JsonIgnore
    private Set<Status> statuses = new HashSet<>();

    @OneToMany(mappedBy = "article")
    @JsonIgnore
    private Set<Comment> comments = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPmid() {
        return pmid;
    }

    public Article pmid(String pmid) {
        this.pmid = pmid;
        return this;
    }

    public void setPmid(String pmid) {
        this.pmid = pmid;
    }

    public String getTitle() {
        return title;
    }

    public Article title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getJournal() {
        return journal;
    }

    public Article journal(String journal) {
        this.journal = journal;
        return this;
    }

    public void setJournal(String journal) {
        this.journal = journal;
    }

    public String getPubDate() {
        return pubDate;
    }

    public Article pubDate(String pubDate) {
        this.pubDate = pubDate;
        return this;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getVolume() {
        return volume;
    }

    public Article volume(String volume) {
        this.volume = volume;
        return this;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getIssue() {
        return issue;
    }

    public Article issue(String issue) {
        this.issue = issue;
        return this;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getPages() {
        return pages;
    }

    public Article pages(String pages) {
        this.pages = pages;
        return this;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getAuthors() {
        return authors;
    }

    public Article authors(String authors) {
        this.authors = authors;
        return this;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getElocationId() {
        return elocationId;
    }

    public Article elocationId(String elocationId) {
        this.elocationId = elocationId;
        return this;
    }

    public void setElocationId(String elocationId) {
        this.elocationId = elocationId;
    }

    public String getAbstractContent() {
        return abstractContent;
    }

    public Article abstractContent(String abstractContent) {
        this.abstractContent = abstractContent;
        return this;
    }

    public void setAbstractContent(String abstractContent) {
        this.abstractContent = abstractContent;
    }

    public String getLink() {
        return link;
    }

    public Article link(String link) {
        this.link = link;
        return this;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Set<Status> getStatuses() {
        return statuses;
    }

    public Article statuses(Set<Status> statuses) {
        this.statuses = statuses;
        return this;
    }

    public Article addStatuses(Status status) {
        statuses.add(status);
        status.setArticle(this);
        return this;
    }

    public Article removeStatuses(Status status) {
        statuses.remove(status);
        status.setArticle(null);
        return this;
    }

    public void setStatuses(Set<Status> statuses) {
        this.statuses = statuses;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public Article comments(Set<Comment> comments) {
        this.comments = comments;
        return this;
    }

    public Article addComments(Comment comment) {
        comments.add(comment);
        comment.setArticle(this);
        return this;
    }

    public Article removeComments(Comment comment) {
        comments.remove(comment);
        comment.setArticle(null);
        return this;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Article article = (Article) o;
        if(article.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Article{" +
            "id=" + id +
            ", pmid='" + pmid + "'" +
            ", title='" + title + "'" +
            ", journal='" + journal + "'" +
            ", pubDate='" + pubDate + "'" +
            ", volume='" + volume + "'" +
            ", issue='" + issue + "'" +
            ", pages='" + pages + "'" +
            ", authors='" + authors + "'" +
            ", elocationId='" + elocationId + "'" +
            ", abstractContent='" + abstractContent + "'" +
            ", link='" + link + "'" +
            '}';
    }
}
